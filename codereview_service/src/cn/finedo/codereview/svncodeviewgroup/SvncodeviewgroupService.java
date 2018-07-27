/**
 * 评审小组表管理服务
 * 
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.svncodeviewgroup;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.common.non.NonUtil;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class SvncodeviewgroupService {
	private static Logger logger = LogManager.getLogger(); 
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 评审小组查询
	 * @param svncodeviewgroupquerydomain
	 * @return
	 * @authro pt
	 * @date 2018年6月26日
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>
	 * @version V1.0
	 */
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> query(SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>();
		
		StringBuffer sql=new StringBuffer();
//		sql.append("SELECT t.cgid,t.createperson,t.groupdesc,t.groupname,t.svnaddr,");	
//		sql.append("t.svnpath,COUNT(s.cgid) AS num FROM tb_dops_svncodeviewgroup t");
//		sql.append(" LEFT JOIN tb_dops_projectmember s ON t.cgid = s.cgid  ");
		//sql.append("select s1.*,(select p.personname from fsdp.tb_sys_person p left join fsdp.tb_sys_user u on p.personid = u.personid where u.usercode = s2.usercode) as usercode,(select p.personname from fsdp.tb_sys_person p left join fsdp.tb_sys_user u on p.personid = u.personid where u.usercode = s2.busimng) busimng,s2.repotype from (SELECT t.cgid,t.createperson,t.groupdesc,t.groupname,t.svnaddr,t.svnpath,COUNT(s.cgid) AS num FROM tb_dops_svncodeviewgroup t  LEFT JOIN tb_dops_projectmember s ON t.cgid = s.cgid");
		//sql.append("select s2.projectname groupname,s2.projectdescribe groupdesc,s1.svnaddr,s1.svnpath,CONCAT(s2.usercode,':',(select p.personname from fsdp.tb_sys_person p left join fsdp.tb_sys_user u on p.personid = u.personid where u.usercode = s2.usercode)) as usercode,CONCAT(s2.busimng,':',(select p.personname from fsdp.tb_sys_person p left join fsdp.tb_sys_user u on p.personid = u.personid where u.usercode = s2.busimng)) busimng,s2.repotype from (SELECT t.cgid,t.createperson,t.groupdesc,t.groupname,t.svnaddr,t.svnpath,COUNT(s.cgid) AS num FROM tb_dops_svncodeviewgroup t  LEFT JOIN tb_dops_projectmember s ON t.cgid = s.cgid");
		sql.append("select s2.projectname groupname,s2.projectdescribe groupdesc,s1.svnaddr,s1.svnpath,CONCAT(s2.usercode,':',(select p.personname from tb_sys_person p left join tb_sys_user u on p.personid = u.personid where u.usercode = s2.usercode)) as usercode,CONCAT(s2.busimng,':',(select p.personname from tb_sys_person p left join tb_sys_user u on p.personid = u.personid where u.usercode = s2.busimng)) busimng,s2.repotype from (SELECT t.cgid,t.createperson,t.groupdesc,t.groupname,t.svnaddr,t.svnpath,COUNT(s.cgid) AS num FROM tb_dops_svncodeviewgroup t  LEFT JOIN tb_dops_projectmember s ON t.cgid = s.cgid");
		DopsSvncodeviewgroup dopssvncodeviewgroup=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(svncodeviewgroupquerydomain)) {
			pageparam=svncodeviewgroupquerydomain.getPageparam();
			dopssvncodeviewgroup=svncodeviewgroupquerydomain.getDopssvncodeviewgroup();
			
			if(NonUtil.isNotNon(dopssvncodeviewgroup)) {
				StringBuffer condsql=new StringBuffer();
				
				if(NonUtil.isNotNon(dopssvncodeviewgroup.getCgid())) {
					condsql.append(" AND t.cgid=:cgid");
				}
				if(NonUtil.isNotNon(dopssvncodeviewgroup.getGroupname())) {
					condsql.append(" AND t.groupname=:groupname");
				}
				
				if(NonUtil.isNotNon(dopssvncodeviewgroup.getSvnpath())) {
					condsql.append(" AND t.svnpath=:svnpath");
				}
				
				if(NonUtil.isNotNon(dopssvncodeviewgroup.getCreateperson())) {
					condsql.append(" AND t.createperson=:createperson");
				}
				
				if(NonUtil.isNotNon(condsql.toString()))
					sql.append(" WHERE 1=1 ").append(condsql);
			}
		}
		//sql.append(" group BY t.cgid ) s1 left join (select e.usercode,e.svnpath,g.svnaddr from tb_dops_svncodeviewgroupuserrole e left join  tb_dops_svnmng g on e.svnid = g.svnid where e.grouprole = 'pm') s2 on s1.svnpath = s2.svnpath and s1.svnaddr = s2.svnaddr");
		sql.append(" group BY t.cgid ) s1 left join (select projectmng usercode,svnaddr,svnpath, repotype,busimng,projectname,projectdescribe from tb_dops_project) s2 on s1.svnpath = s2.svnpath and s1.svnaddr = s2.svnaddr");
		PageDomain<DopsSvncodeviewgroup> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), dopssvncodeviewgroup, DopsSvncodeviewgroup.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return ret.setSuccess("查询评审小组表成功", retpage);
	}
	
	
	
	/**
	 * 根据登录人的usercode查询其参与的项目评审小组
	 * @param svncodeviewgroupquerydomain
	 * @return
	 * @authro pt
	 * @date 2018年6月25日
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>
	 * @version V1.0
	 */
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> querygroupbyusercode(SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>();
		if(NonUtil.isNon(svncodeviewgroupquerydomain)) {
			return ret.setFail("参数校验失败");
		}
		PageParamDomain pageparam=svncodeviewgroupquerydomain.getPageparam();
		DopsSvncodeviewgroup dopssvncodeviewgroup=svncodeviewgroupquerydomain.getDopssvncodeviewgroup();
		PageDomain<DopsSvncodeviewgroup> retpage=null;
		if(NonUtil.isNon(dopssvncodeviewgroup)){
		    return ret.setFail("参数校验失败");
		}
        // 先判断是否是评审小组系统管理者角色
        String sql1 = "select * from tb_dops_svncodeviewgroupuserrole where usercode = :usercode";
        List<DopsSvncodeviewgroupuserrole> groupuserroleList = null;
        try {
            groupuserroleList = jdbcTemplate.query(sql1, dopssvncodeviewgroup, DopsSvncodeviewgroupuserrole.class);
        }
        catch (DataAccessException e1) {
            logger.error("查询评审小组权限表失败", e1);
            return ret.setFail("查询评审小组权限表失败");
        }
        if(NonUtil.isNon(groupuserroleList)){
            return ret.setFail("查询评审小组权限表失败");
        }
        boolean smflag = false;
        for (DopsSvncodeviewgroupuserrole ele : groupuserroleList) {
            //"sm"评审小组系统管理员 "pm"项目管理员
            if("sm".equals(ele.getGrouprole())){
                smflag = true;
                break;
            }
        }
        StringBuffer sb = new StringBuffer();
        if(smflag){
            //查询管理员的评审小组
            sb.append("select s1.*,s3.num from (select distinct p.cgid,p.createperson,p.groupdesc,p.groupname,p.svnaddr,p.svnpath from tb_dops_svncodeviewgroup p left join tb_dops_projectmember n on p.cgid = n.cgid where p.grouptype = 'common') s1 left join (select count(*) num,s2.cgid from tb_dops_projectmember s2 GROUP BY s2.cgid) s3 on s1.cgid = s3.cgid");
        }else{
            //查询项目经理的评审小组
            sb.append("select s4.* from (select s1.*,s3.num from (select distinct p.cgid,p.createperson,p.groupdesc,p.groupname,p.svnaddr,p.svnpath from tb_dops_svncodeviewgroup p left join tb_dops_projectmember n on p.cgid = n.cgid ) s1 left join (select count(*) num,s2.cgid from tb_dops_projectmember s2 GROUP BY s2.cgid) s3 on s1.cgid = s3.cgid) s4 left join tb_dops_svncodeviewgroupuserrole s5 on s5.svnpath = s4.svnpath where s5.usercode = :usercode");
        }
		try {
			retpage =  jdbcTemplate.queryForPage(sb.toString(), dopssvncodeviewgroup, DopsSvncodeviewgroup.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return ret.setSuccess("查询评审小组表成功", retpage);
	}
	
	
	/**
	 * 评审小组表新增
	 * @param svncodeviewgrouplistdomain
	 * @return
	 * @authro pt
	 * @date 2018年6月26日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> add(SvncodeviewgroupListDomain svncodeviewgrouplistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		StringBuffer svnaddrsql=new StringBuffer();
		DopsSvncodeviewgroup codeviewgroup=new DopsSvncodeviewgroup();
		List<DopsSvncodeviewgroup> svncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		if (NonUtil.isNon(svncodeviewgrouplistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		if (NonUtil.isNon(dopssvncodeviewgrouplist)) {
			return ret.setFail("无评审小组表");
		}
		DopsSvncodeviewgroup group=dopssvncodeviewgrouplist.get(0);
		
		//查询仓库地址
		svnaddrsql.append("SELECT svnaddr FROM tb_dops_svnmng WHERE svnid =(SELECT DISTINCT svnid FROM tb_dops_svnuserright WHERE svnpath=:svnpath)");
		 
		try {
			codeviewgroup=jdbcTemplate.queryForObject(svnaddrsql.toString(),group, DopsSvncodeviewgroup.class);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("添加评审小组失败");
		}
		
		try{
			String cgid=idutil.getID("dopssvncodeviewgroup");
			for(DopsSvncodeviewgroup dopssvncodeviewgroup : dopssvncodeviewgrouplist) {
				dopssvncodeviewgroup.setCgid(cgid);
				dopssvncodeviewgroup.setGroupname(dopssvncodeviewgrouplist.get(0).getGroupname());
				dopssvncodeviewgroup.setGroupdesc(dopssvncodeviewgrouplist.get(0).getGroupdesc());
				dopssvncodeviewgroup.setSvnpath(dopssvncodeviewgrouplist.get(0).getSvnpath());
				dopssvncodeviewgroup.setSvnaddr(codeviewgroup.getSvnaddr());
				svncodeviewgrouplist.add(dopssvncodeviewgroup);
			}
		}catch(Exception ex) {
			logger.error("添加评审小组失败",ex);
			return ret.setFail("添加评审小组失败");
		}
		
  		 sql.append("INSERT INTO tb_dops_svncodeviewgroup (cgid, groupname, svnaddr, svnpath, repotype, createperson, optdate, ");
  		 sql.append("groupdesc)   VALUES (:cgid, :groupname, :svnaddr, :svnpath, :repotype, :createperson, NOW(), :groupdesc)");
		try {
			jdbcTemplate.batchUpdate(sql.toString(), svncodeviewgrouplist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("评审小组表新增成功");
	}
	
	/**
	 * 评审小组更新
	 * @param svncodeviewgrouplistdomain
	 * @return
	 * @authro pt
	 * @date 2018年6月26日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> update(SvncodeviewgroupListDomain svncodeviewgrouplistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		if (NonUtil.isNon(svncodeviewgrouplistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		 sql.append("UPDATE tb_dops_svncodeviewgroup SET cgid=:cgid, groupname=:groupname, svnaddr=:svnaddr, svnpath=:svnpath,");
		 sql.append("repotype=:repotype, createperson=:createperson, optdate=NOW(), groupdesc=:groupdesc WHERE cgid=:cgid");
		
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncodeviewgrouplist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("评审小组表修改成功");
	}
	
	/**
	 * 评审小组删除
	 * @param svncodeviewgrouplistdomain
	 * @return
	 * @authro pt
	 * @date 2018年6月26日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> delete(SvncodeviewgroupListDomain svncodeviewgrouplistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		StringBuffer groupsql=new StringBuffer();
		if (NonUtil.isNon(svncodeviewgrouplistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist = svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		//删除小组信息
		sql.append("DELETE FROM tb_dops_svncodeviewgroup WHERE cgid=:cgid");
		
		//删除小组成员信息
		groupsql.append("DELETE FROM tb_dops_projectmember WHERE cgid=:cgid");
		
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncodeviewgrouplist);
			jdbcTemplate.batchUpdate(groupsql.toString(), dopssvncodeviewgrouplist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("评审小组表删除成功");
	}
	
	
	/**
	 * 更新小组信息
	 * @param viewgrouplistdomain
	 * @return
	 * @authro pt
	 * @date 2018年6月11日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> updategroup(SvncodeviewgroupListDomain viewgrouplistdomain) {
		StringBuffer upsql = new StringBuffer();
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		if (NonUtil.isNon(viewgrouplistdomain)) {
			return ret.setFail("信息填写有误");
		}
		List<DopsSvncodeviewgroup> viewgrouplist=viewgrouplistdomain.getDopssvncodeviewgrouplist();
		if(NonUtil.isNon(viewgrouplist)){
			return ret.setFail("信息修改错误");
		}
		DopsSvncodeviewgroup svnviewgroup=viewgrouplist.get(0);
		
		upsql.append("UPDATE tb_dops_svncodeviewgroup SET  groupname=:groupname, svnaddr=:svnaddr, svnpath=:svnpath, ");
		upsql.append(" repotype=:repotype, createperson=:createperson, optdate=NOW(), groupdesc=:groupdesc WHERE cgid=:cgid ");
		try {
			jdbcTemplate.update(upsql.toString(), svnviewgroup);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		return ret.setSuccess("修改小组信息表成功");
	}
	
	
	/**
	* 通过用户编号查询 可以操作的评审小组
	* @author zhusf
	* @param @param person
	* @param @return
	* @param @throws Exception
	* @return List<DopsSvncodeviewgroup>
	*/ 	
    public List<DopsSvncodeviewgroup> querygroupbyusercode(DopsProjectmember person) throws Exception {
        List<DopsSvncodeviewgroup> groupList = new ArrayList<DopsSvncodeviewgroup>();
        // 先判断是否是评审小组系统管理者角色
        String sql1 = "select * from tb_dops_svncodeviewgroupuserrole where usercode = :usercode";
        List<DopsSvncodeviewgroupuserrole> groupuserroleList = null;
        try {
            groupuserroleList = jdbcTemplate.query(sql1, person, DopsSvncodeviewgroupuserrole.class);
        }
        catch (DataAccessException e1) {
            logger.error("查询评审小组权限表失败", e1);
            throw new RuntimeException("查询评审小组权限表失败");
        }
        if (NonUtil.isNon(groupuserroleList)) {
            return groupList;
        }
        boolean smflag = false;
        for (DopsSvncodeviewgroupuserrole ele : groupuserroleList) {
            // "sm"评审小组系统管理员 "pm"项目管理员
            if ("sm".equals(ele.getGrouprole())) {
                smflag = true;
                break;
            }
        }
        StringBuffer sb = new StringBuffer();
        if (smflag) {
            // 查询管理员的评审小组
            sb.append("select distinct p.cgid,p.createperson,p.groupdesc,p.groupname,p.svnaddr,p.svnpath from tb_dops_svncodeviewgroup p left join tb_dops_projectmember n on p.cgid = n.cgid where p.grouptype = 'common'");
            //sb.append("select s1.*,s3.num from (select distinct p.cgid,p.createperson,p.groupdesc,p.groupname,p.svnaddr,p.svnpath from tb_dops_svncodeviewgroup p left join tb_dops_projectmember n on p.cgid = n.cgid where p.grouptype = 'common') s1 left join (select count(*) num,s2.cgid from tb_dops_projectmember s2 GROUP BY s2.cgid) s3 on s1.cgid = s3.cgid");
        } else {
            // 查询项目经理的评审小组
            sb.append("select s4.* from (select s1.* from (select distinct p.cgid,p.createperson,p.groupdesc,p.groupname,p.svnaddr,p.svnpath from tb_dops_svncodeviewgroup p left join tb_dops_projectmember n on p.cgid = n.cgid ) s1 ) s4 left join tb_dops_svncodeviewgroupuserrole s5 on s5.svnpath = s4.svnpath where s5.usercode = :usercode");
        }
        try {
            long start = System.currentTimeMillis();
            groupList = jdbcTemplate.query(sb.toString(), person, DopsSvncodeviewgroup.class);
        }
        catch (Exception e) {
            logger.error("sql语句异常", e);
            throw new RuntimeException("查询评审小组权限表失败");
        }
        return groupList;

    }

    /**
    * 查询全局评审小组
    * @author zhusf
    * @param @return
    * @return ReturnValueDomain<DopsSvncodeviewgroup>
    */ 	
    public ReturnValueDomain<DopsSvncodeviewgroup> querywholegroup() {
        ReturnValueDomain<DopsSvncodeviewgroup> ret = new ReturnValueDomain<DopsSvncodeviewgroup>();
        String sql = "select * from tb_dops_svncodeviewgroup where grouptype = 'all'";
        DopsSvncodeviewgroup group = null;
        try {
            group = jdbcTemplate.queryForObject(sql, DopsSvncodeviewgroup.class);
        }
        catch (DataAccessException e) {
            logger.error("查询全局评审小组失败", e);
            return ret.setFail("查询全局评审小组失败");
        }
        return ret.setSuccess("查询全局评审小组成功", group);
    }
	
}
