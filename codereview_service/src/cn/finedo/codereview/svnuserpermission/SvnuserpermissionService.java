/**
 * SVN项目类型服务
 * @version 1.0
 * @since 2018-06-05
 */
package cn.finedo.codereview.svnuserpermission;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;

@Service
@Transactional
@Scope("singleton")
public class SvnuserpermissionService {
	private static Logger logger = LogManager.getLogger();

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private IDUtil idutil;

	/**
	 * svnuserpermission表查询svnpath
	 * @param SvnuserpermissionQueryDomain
	 * @return ReturnValueDomain<PageDomain<DopsSvnuserpermission>>
	 */
	public ReturnValueDomain<List<DopsSvnuserpermission>> querysvnpath(DopsSvnuserpermission dopssvnuserpermission) {
		ReturnValueDomain<List<DopsSvnuserpermission>> ret = new ReturnValueDomain<List<DopsSvnuserpermission>>();
		List<DopsSvnuserpermission> dopssvn = new ArrayList<DopsSvnuserpermission>();//主表
		DopsSvnuserpermission svnuserpermission=new DopsSvnuserpermission();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT svnpath FROM tb_dops_svnuserright");
		if(NonUtil.isNotNon(dopssvnuserpermission)) {
			svnuserpermission.setSvnpath("%"+dopssvnuserpermission.getSvnpath()+"%");
                sql.append(" WHERE svnpath like :svnpath");
            }
		
		try {
			dopssvn = jdbcTemplate.query(sql.toString(),svnuserpermission,DopsSvnuserpermission.class);
			
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询svnuserpermission信息失败");
		}
		return ret.setSuccess("查询svnuserpermission信息成功",  dopssvn);

	}
	

	/**
	 * 添加项目成员到codeviewperson表中
	 * @param svnuserpermission
	 * @return
	 * @authro pt
	 * @date 2018年6月12日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> addgroupnum(DopsSvnuserpermission svnuserpermission) {
		StringBuffer svnusersql=new StringBuffer();
		StringBuffer personsql=new StringBuffer();
		List<DopsProjectmember> codeviewperson = new ArrayList<DopsProjectmember>();//详情
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		personsql.append("INSERT INTO tb_dops_projectmember (cpid, cgid, usercode) VALUES (:cpid,:cgid,:usercode)");		
		
		svnusersql.append("SELECT DISTINCT(u.usercode), g.cgid FROM tb_dops_svnuserright u LEFT   ");
		svnusersql.append("JOIN tb_dops_svncodeviewgroup g  ON u.svnpath = g.svnpath  WHERE u.svnpath =:svnpath");
		
		try {		
			codeviewperson =  jdbcTemplate.query(svnusersql.toString(), svnuserpermission, DopsProjectmember.class);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		List<DopsProjectmember> codeviewpersonlist=new ArrayList<DopsProjectmember>();
		
		try{
			
			for(DopsProjectmember groupperson : codeviewperson) {
				String cpid=idutil.getID("dopssvncodeviewperson");
				DopsProjectmember people=new DopsProjectmember();
				people.setCgid(groupperson.getCgid());
				people.setUsercode(groupperson.getUsercode());
				people.setCpid(cpid);
				codeviewpersonlist.add(people);
			}
		}catch(Exception ex) {
		    logger.error("添加项目成员失败");
			return ret.setFail("添加项目成员失败");
		}
		
		try {		
			jdbcTemplate.batchUpdate(personsql.toString(), codeviewpersonlist);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("添加项目成员失败");
		}
		return ret.setSuccess("添加成功");
	}
	
	
	/**
	 * 编辑操作是项目成员的添加
	 * @param svnuserpermission
	 * @return
	 * @authro pt
	 * @date 2018年6月19日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> editaddgroupnum(DopsSvnuserpermission svnuserpermission) {
		StringBuffer svnusersql=new StringBuffer();
		StringBuffer personsql=new StringBuffer();
		StringBuffer groupsql=new StringBuffer();
		DopsSvnuserpermission userpermission = new DopsSvnuserpermission();//主表
		List<DopsProjectmember> codeviewperson = new ArrayList<DopsProjectmember>();//详情
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		personsql.append("INSERT INTO tb_dops_projectmember (cpid, cgid, usercode) VALUES (:cpid,:cgid,:usercode)");		
		
		svnusersql.append("SELECT DISTINCT(u.usercode) FROM tb_dops_svnuserright u  WHERE u.svnpath =:svnpath  ");
		
		groupsql.append("UPDATE tb_dops_svncodeviewgroup SET  num=:peonum WHERE cgid=:cgid");
		
		try {		
			codeviewperson =  jdbcTemplate.query(svnusersql.toString(), svnuserpermission, DopsProjectmember.class);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		List<DopsProjectmember> codeviewpersonlist=new ArrayList<DopsProjectmember>();
		
		try{
			
			for(DopsProjectmember groupperson : codeviewperson) {
				String cpid=idutil.getID("dopssvncodeviewperson");
				DopsProjectmember people=new DopsProjectmember();
				people.setCgid(svnuserpermission.getCgid());
				people.setUsercode(groupperson.getUsercode());
				people.setCpid(cpid);
				codeviewpersonlist.add(people);
			}
		}catch(Exception ex) {
			logger.error("编辑项目成员失败",ex);
			return ret.setFail("编辑项目成员失败");
		}
		
		try {		
			jdbcTemplate.batchUpdate(personsql.toString(), codeviewpersonlist);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("编辑项目成员失败");
		}
		userpermission.setPeonum(codeviewpersonlist.size());
		userpermission.setCgid(svnuserpermission.getCgid());
		try {		
			jdbcTemplate.update(groupsql.toString(), userpermission);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("编辑项目成员失败");
		}
		
		return ret.setSuccess("添加成功");
	}
	
}
