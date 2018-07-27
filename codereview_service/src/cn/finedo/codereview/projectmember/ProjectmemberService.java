/**
 * 评审成员表管理服务
 * 
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.common.non.NonUtil;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.projectmember.domain.ProjectmemberListDomain;
import cn.finedo.codereview.projectmember.domain.ProjectmemberQueryDomain;
import cn.finedo.codereview.common.pojo.DopsProjectmember;

@Service
@Transactional
@Scope("singleton")
public class ProjectmemberService {
	private static Logger logger = LogManager.getLogger(); 
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 评审成员表查询
	 * @param ProjectmemberQueryDomain
	 * @return ReturnValueDomain<PageDomain<DopsProjectMember>>
	 */
	public ReturnValueDomain<PageDomain<DopsProjectmember>> query(ProjectmemberQueryDomain svncodeviewpersonquerydomain) {
		ReturnValueDomain<PageDomain<DopsProjectmember>> ret = new ReturnValueDomain<PageDomain<DopsProjectmember>>();
		
		StringBuffer sql=new StringBuffer("SELECT * FROM tb_dops_projectmember");
			
		DopsProjectmember dopssvncodeviewperson=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(svncodeviewpersonquerydomain)) {
			pageparam=svncodeviewpersonquerydomain.getPageparam();
			dopssvncodeviewperson=svncodeviewpersonquerydomain.getDopssvncodeviewperson();
			
			if(NonUtil.isNotNon(dopssvncodeviewperson)) {
				StringBuffer condsql=new StringBuffer();
				
				if(NonUtil.isNotNon(dopssvncodeviewperson.getCpid())) {
					condsql.append(" AND cpid=:cpid");
				}
				if(NonUtil.isNotNon(dopssvncodeviewperson.getUsercode())) {
					condsql.append(" AND usercode=:usercode");
				}
				if(NonUtil.isNotNon(dopssvncodeviewperson.getCgid())) {
					condsql.append(" AND cgid=:cgid");
				}
				
				if(NonUtil.isNotNon(condsql.toString()))
					sql.append(" WHERE 1=1 ").append(condsql);
			}
		}
				
		PageDomain<DopsProjectmember> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), dopssvncodeviewperson, DopsProjectmember.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询评审成员表失败");
		}
		
		return ret.setSuccess("查询评审成员表成功", retpage);
	}
	
    /**
     * 评审成员表新增
     * 
     * @param ProjectmemberListDomain
     * @return ReturnValueDomain<DopsProjectMember>
     */
    public ReturnValueDomain<String> add(ProjectmemberListDomain svncodeviewpersonlistdomain) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        List<DopsSvncodeviewgroupuser> person = new ArrayList<DopsSvncodeviewgroupuser>();

        StringBuffer svnpersonsql = new StringBuffer();
        if (NonUtil.isNon(svncodeviewpersonlistdomain)) {
            return ret.setFail("无评审成员表");
        }
        List<DopsProjectmember> dopssvncodeviewpersonlist = svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
        if (NonUtil.isNon(dopssvncodeviewpersonlist)) {
            return ret.setFail("无评审成员表");
        }
        DopsProjectmember codeviewperson = dopssvncodeviewpersonlist.get(0);
        String usercode = codeviewperson.getUsercode();
        String[] usercodes = usercode.split(",");
        for (int i = 0; i < usercodes.length; i++ ) {
            String code = usercodes[i];
            codeviewperson.setUsercode(code);
            String serchsql = "SELECT * FROM tb_dops_projectmember WHERE usercode in (:usercode) AND cgid=:cgid";
            try {
                person = jdbcTemplate.query(serchsql.toString(), codeviewperson, DopsSvncodeviewgroupuser.class);
            }
            catch (Exception e) {
                logger.error("sql语句异常", e);
                throw new TransactionException(e);
            }
            if (NonUtil.isNotNon(person)) {
                logger.debug("{}已在项目中", code);
                return ret.setFail("该成员已在评审小组中！");
            }
        }
        List<DopsProjectmember> personlist = new ArrayList<DopsProjectmember>();
        for (int i = 0; i < usercodes.length; i++ ) {
            DopsProjectmember dopsProjectmember = new DopsProjectmember();
            DopsProjectmember dpm = dopssvncodeviewpersonlist.get(0);
            String cpid = idutil.getID("dopssvncodeviewperson");
            dopsProjectmember.setCpid(cpid);
            dopsProjectmember.setCgid(dpm.getCgid());
            dopsProjectmember.setUsercode(usercodes[i]);
            dopsProjectmember.setAddoptuser(dpm.getAddoptuser());
            personlist.add(dopsProjectmember);
        }
        svnpersonsql.append("INSERT INTO tb_dops_projectmember (cpid, usercode, cgid,addtime,addoptuser,userstate) VALUES (:cpid, :usercode, :cgid,NOW(),:addoptuser,'1')");
        try {
            jdbcTemplate.batchUpdate(svnpersonsql.toString(), personlist);
        }
        catch (Exception e) {
            logger.error("sql语句异常", e);
            throw new TransactionException(e);
        }
        return ret.setSuccess("评审成员表新增成功");
    }
	
	/**
	 * 评审成员表修改
	 * @param ProjectmemberListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(ProjectmemberListDomain svncodeviewpersonlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		if (NonUtil.isNon(svncodeviewpersonlistdomain)) {
			return ret.setFail("无评审成员表");
		}
		
		List<DopsProjectmember> dopssvncodeviewpersonlist=svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
				
		sql.append("UPDATE tb_dops_projectmember SET cpid=:cpid, usercode=:usercode, cgid=:cgid  WHERE cpid=:cpid");
		
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncodeviewpersonlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("评审成员表修改成功");
	}
	
	/**
	 * 评审成员表删除
	 * @param ProjectmemberListDomain
	 * @return ReturnValueDomain<SysUser>
	 */
	public ReturnValueDomain<String> delete(ProjectmemberListDomain svncodeviewpersonlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		if (NonUtil.isNon(svncodeviewpersonlistdomain)) {
			return ret.setFail("无评审成员表");
		}
		
		List<DopsProjectmember> dopssvncodeviewpersonlist = svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
		
		 sql.append("DELETE FROM tb_dops_projectmember WHERE cpid=:cpid");
		
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncodeviewpersonlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("评审成员表删除成功");
	}
}
