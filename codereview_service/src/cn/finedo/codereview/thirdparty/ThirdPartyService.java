package cn.finedo.codereview.thirdparty;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvnmng;
import cn.finedo.codereview.common.pojo.DopsSvnuser;
import cn.finedo.codereview.entity.thirdparty.ProjectEntity;
import cn.finedo.codereview.entity.thirdparty.ProjectEntityForSql;
import cn.finedo.codereview.entity.thirdparty.ProjectsEntity;
import cn.finedo.codereview.entity.thirdparty.UserEntity;
import cn.finedo.codereview.entity.thirdparty.UsersEntity;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.sec.DES;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;


/**      
* @Description: 外部接口，同步项目信息、成员信息
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:17:26   
* @version v1.0 
*/ 
@Service
@Transactional
@Scope("singleton")
public class ThirdPartyService {
    private static Logger logger = LogManager.getLogger();

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IDUtil idutil;

    /**
    * 同步项目数据接口
    * @author zhusf
    * @param @param projectsEntity
    * @param @return
    * @return ReturnValueDomain<String>
    */ 	
    public ReturnValueDomain<String> syncproject(ProjectsEntity projectsEntity) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        // tb_dops_project tb_dops_svnmng tb_dops_projectsvn tb_dops_svncodeviewgroup
        // tb_dops_projectmember
        // tb_dops_svnuser tb_dops_svncodeviewgroupuserrole
        List<ProjectEntity> projectEntityList = projectsEntity.getProjectEntityList();
        for (ProjectEntity projectEntity : projectEntityList) {
            List<UserEntity> userEntityList = projectEntity.getUserEntityList();
            // 先判断项目存不存在
            try {
                String sql = "select projectid from tb_dops_project where projectid = :projectid";
                List<DopsSvncodeviewproject> projectList = jdbcTemplate.query(sql, projectEntity, DopsSvncodeviewproject.class);
                if (NonUtil.isNotNon(projectList)) {
                    deleteOneProject(projectEntity);
                }
                addOneProject(projectEntity);
                insertOrUpdateUserPersonTable(userEntityList);
                insertOrUpdateSvnuserTable(userEntityList);
            }
            catch (Exception e) {
                logger.error("{}项目数据异常！", e);
                deleteOneProject(projectEntity);
                ret.setFail("项目数据入库异常！");
            }

        }
        return ret.setSuccess("同步项目数据成功");

    }

    /**
    * 同步用户数据接口
    * @author zhusf
    * @param @param usersEntity
    * @param @return
    * @return ReturnValueDomain<String>
    */ 	
    public ReturnValueDomain<String> syncuser(UsersEntity usersEntity) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        List<UserEntity> userEntityList = usersEntity.getUserEntityList();
        try {
            insertOrUpdateUserPersonTable(userEntityList);
        }
        catch (Exception e) {
            logger.error("同步用户数据失败！", e);
            ret.setFail("同步用户数据失败！");
        }
        return ret.setSuccess("okle");
    }
    
    /**
    * 添加或更新一个项目
    * @author zhusf
    * @param @param projectEntity
    * @return void
    */ 	
    public void addOneProject(ProjectEntity projectEntity) {
        ProjectEntityForSql entityForSql = transEntity(projectEntity);
        // 新增项目
        List<UserEntity> userEntityList = projectEntity.getUserEntityList();
        UserEntity projectmng = null;
        UserEntity repomnguser = null;
        UserEntity customerprojectmng = null;
        UserEntity bzmng = null;
        for (UserEntity userEntity : userEntityList) {
            if ("repomnguser".equals(userEntity.getRole()) && NonUtil.isNotNon(userEntity.getPwd())) {
                repomnguser = userEntity;
            }
            // 项目经理
            if ("projectmng".equals(userEntity.getRole())) {
                projectmng = userEntity;
            }
        }
        // 异常判断 projectmng repomnguser必须存在
        if (NonUtil.isNon(projectmng) || NonUtil.isNon(repomnguser)) {
            logger.error("项目人员信息不全！");
            throw new RuntimeException("项目人员信息不全！");
        }
        //
        entityForSql.setRepotype(projectmng.getRepotype());
        entityForSql.setUsercode(projectmng.getUsercode());
        insertProjectTable(entityForSql);

        // 插入tb_dops_svnmng
        // 判断
        String sql2 = "select * from tb_dops_svnmng where svnaddr = :svnaddr";
        List<DopsSvnmng> svnmngList = jdbcTemplate.query(sql2, entityForSql, DopsSvnmng.class);
        if (NonUtil.isNotNon(svnmngList)) {
            // 更新svnmng表 更新管理员的账号
            entityForSql.setUsercode(repomnguser.getUsercode());
            updateSvnmngTable(entityForSql);
            entityForSql.setSvnid(svnmngList.get(0).getSvnid());
        } else {
            // 插入svnmng表
            InsertSvnmngTable(entityForSql);
        }

        // 插入多张表
        entityForSql.setUsercode(projectmng.getUsercode());
        InsertFourProjectTables(entityForSql);
    }


    /**
    * 插入项目表
    * @author zhusf
    * @param @param entityForSql
    * @return void
    */ 	
    public void insertProjectTable(ProjectEntityForSql entityForSql) {
        String sql1 = "insert into tb_dops_project (projectid,projectname,custname,orgcode,projectmng,projectcode,status,statusname,statustime,createtime,projectdescribe,repotype,svnaddr,svnpath) values (:projectid,:projectname,:customer,:dept,:usercode,'tmp','tmpstatus','tmp',now(),now(),:projectdesc,:repotype,:svnaddr,:svnpath)";
        try {
            jdbcTemplate.update(sql1, entityForSql);
        }
        catch (DataAccessException e) {
            logger.error("插入project表失败！", e);
            throw new TransactionException(e);
        }
    }

    /**
    * 更新仓库表
    * @author zhusf
    * @param @param entityForSql
    * @return void
    */ 	
    public void updateSvnmngTable(ProjectEntityForSql entityForSql) {
        String sql = "update tb_dops_svnmng set mnguser = :usercode where svnaddr = :svnaddr";
        try {
            jdbcTemplate.update(sql, entityForSql);
        }
        catch (DataAccessException e) {
            logger.error("更新svnmng表失败！", e);
            throw new TransactionException(e);
        }

    }

    /**
    * 插入仓库表
    * @author zhusf
    * @param @param entityForSql
    * @return void
    */ 	
    public void InsertSvnmngTable(ProjectEntityForSql entityForSql) {
        String svnid = UUID.randomUUID().toString();
        entityForSql.setSvnid(svnid);
        String sql = "insert into tb_dops_svnmng (svnid,mnguser,svnaddr) values (:svnid,:mnguser,:svnaddr)";
        try {
            jdbcTemplate.update(sql, entityForSql);
        }
        catch (DataAccessException e) {
            logger.error("插入svnmng表失败！", e);
            throw new TransactionException(e);
        }

    }

    /**
    * 插入项目相关的表
    * @author zhusf
    * @param @param entityForSql
    * @return void
    */ 	
    public void InsertFourProjectTables(ProjectEntityForSql entityForSql) {
        //插入到oa项目与svn项目关联表
        String sql1 = "insert into tb_dops_projectsvn (projectid,svnid,svnpath) values (:projectid,:svnid,:svnpath)";
        //插入项目表
        String sql2 = "INSERT INTO tb_dops_svncodeviewgroup (cgid, groupname,svnaddr, svnpath, repotype, createperson, optdate, groupdesc,grouptype) VALUES (:projectid, :projectname,:svnaddr,:svnpath,:repotype,'interface',NOW(), :projectdesc,'common')";
        String cpid = idutil.getID("dopssvncodeviewperson");
        entityForSql.setCpid(cpid);
        //插入项目成员表
        String sql3 = "INSERT INTO tb_dops_projectmember (cpid, usercode,cgid, addtime, addoptuser, userstate) VALUES (:cpid, :usercode,:projectid,NOW(),'interface','1')";
        //插入项目角色表
        String sql4 = "INSERT INTO tb_dops_svncodeviewgroupuserrole (usercode, grouprole,svnpath, svnid)   VALUES (:usercode, 'pm',:svnpath,:svnid)";
        try {
            jdbcTemplate.update(sql1, entityForSql);
            jdbcTemplate.update(sql2, entityForSql);
            jdbcTemplate.update(sql3, entityForSql);
            jdbcTemplate.update(sql4, entityForSql);
        }
        catch (DataAccessException e) {
            logger.error("插入多张项目信息人员表失败！", e);
            throw new TransactionException(e);
        }
    }

    /**
    * 删除一个项目
    * @author zhusf
    * @param @param projectEntity
    * @return void
    */ 	
    public void deleteOneProject(ProjectEntity projectEntity) {
        logger.debug("删除项目,项目编号{}", projectEntity.getProjectid());
        ProjectEntityForSql entityForSql = transEntity(projectEntity);
        entityForSql.setProjectid(projectEntity.getProjectid());
        try {
            String sql = "select t.svnpath,g.svnid from tb_dops_project t right join tb_dops_svnmng g on t.svnaddr = g.svnaddr where t.projectid = :projectid";
            List<DopsSvncodeviewgroupuserrole> roleList = jdbcTemplate.query(sql, entityForSql, DopsSvncodeviewgroupuserrole.class);
            if (NonUtil.isNotNon(roleList)) {
                String sql5 = "delete from tb_dops_svncodeviewgroupuserrole where svnid = :svnid and svnpath = :svnpath";
                jdbcTemplate.batchUpdate(sql5, roleList);
            }
            String sql1 = "delete from tb_dops_project where projectid = :projectid";
            String sql2 = "delete from tb_dops_projectsvn where projectid = :projectid";
            String sql3 = "delete from tb_dops_svncodeviewgroup where cgid = :projectid";
            String sql4 = "delete from tb_dops_projectmember where cgid = :projectid";
            jdbcTemplate.update(sql1, entityForSql);
            jdbcTemplate.update(sql2, entityForSql);
            jdbcTemplate.update(sql3, entityForSql);
            jdbcTemplate.update(sql4, entityForSql);
        }
        catch (DataAccessException e) {
            logger.error("删除项目失败！", e);
            throw new TransactionException(e);
        }

    }


    /**
     * 定义一个对象，专门用于设置JDBC参数
     * 
     * @author zhusf
     * @param @param
     *            entity
     * @param @return
     * @return ProjectEntityForSql
     */
    public ProjectEntityForSql transEntity(ProjectEntity entity) {
        ProjectEntityForSql entityForSql = new ProjectEntityForSql();
        entityForSql.setProjectid(entity.getProjectid());
        entityForSql.setCustomer(entity.getCustomer());
        entityForSql.setDept(entity.getDept());
        entityForSql.setProjectdesc(entity.getProjectdesc());
        entityForSql.setProjectid(entity.getProjectid());
        entityForSql.setProjectname(entity.getProjectname());
        entityForSql.setSvnaddr(entity.getSvnaddr());
        entityForSql.setSvnpath(entity.getSvnpath());
        return entityForSql;

    }

    /**
    * 插入或更新用户数据表
    * @author zhusf
    * @param @param userEntityList
    * @return void
    */ 	
    public void insertOrUpdateUserPersonTable(List<UserEntity> userEntityList) {
        try {
            for (UserEntity userEntity : userEntityList) {
                String querysql = "select usercode from tb_sys_user where usercode = :usercode";
                List<DopsSvnuser> userList = jdbcTemplate.query(querysql, userEntity, DopsSvnuser.class);
                StringBuffer sb = new StringBuffer();
                if (NonUtil.isNon(userList)) {
                    sb.append("insert into tb_sys_user (optsn,userid,usercode,personid,effdate,expdate,state,loginpasswd,createtime,lastbptime,lastlptime) values ('tmp',:usercode,:usercode,:usercode,now(),now(),'tmp','tmp',now(),now(),now())");
                }
                StringBuffer sb1 = new StringBuffer();
                String querysql1 = "select personid as usercode from tb_sys_person where personid = :usercode";
                List<DopsSvnuser> personList = jdbcTemplate.query(querysql1, userEntity, DopsSvnuser.class);
                if (NonUtil.isNon(personList)) {
                    sb1.append("insert into tb_sys_person (optsn,personid,personname,phoneno,email,gender) values ('tmp',:usercode,:username,:phonenu,:email,'tmp')");
                } else {
                    sb1.append("update tb_sys_person set personname = :username,phoneno = :phonenu,email = :email where personid = :usercode");
                }
                if (NonUtil.isNotNon(sb.toString())) {
                    jdbcTemplate.update(sb.toString(), userEntity);
                }
                jdbcTemplate.update(sb1.toString(), userEntity);
            }
        }
        catch (DataAccessException e) {
            logger.error("插入用户表失败！", e);
            throw new TransactionException(e);
        }
    }

    /**
    * 插入或更新用户密码表
    * @author zhusf
    * @param @param userEntityList
    * @return void
    */ 	
    public void insertOrUpdateSvnuserTable(List<UserEntity> userEntityList) {
        for (UserEntity userEntity : userEntityList) {
            // 仓库管理员才需要更新插入此表
            if (!"repomnguser".equals(userEntity.getRole())) {
                continue;
            }
            String sql = "select usercode from tb_dops_svnuser where usercode = :usercode";
            List<DopsSvnuser> svnUserList = null;
            try {
                svnUserList = jdbcTemplate.query(sql, userEntity, DopsSvnuser.class);
            }
            catch (DataAccessException e) {
                logger.error("查询svnuser表失败！", e);
                throw new TransactionException(e);
            }
            String pwd = userEntity.getPwd();
            String encryptPwd = "";
            try {
                encryptPwd = new String(DES.encryptString(pwd));
            }
            catch (Exception e) {
                logger.error("密码加密失败!");
                throw new RuntimeException(e);
            }
            userEntity.setPwd(encryptPwd);
            StringBuffer sb = new StringBuffer();
            if (NonUtil.isNon(svnUserList)) {
                // 插入
                sb.append("insert into tb_dops_svnuser (usercode,passwd,lastmdtime,opuserid,optime,username,optsn,userid) values (:usercode,:pwd,now(),'tmp',now(),:username,'tmp',:usercode)");
            } else {
                // 更新
                sb.append("update tb_dops_svnuser set passwd = :pwd,username = :username where usercode = :usercode");
            }
            try {
                jdbcTemplate.update(sb.toString(), userEntity);
            }
            catch (DataAccessException e) {
                logger.error("添加或更新用户失败", e);
                throw new TransactionException(e);
            }
        }
    }

}
