/**
 * SVN项目类型服务
 * @version 1.0
 * @since 2018-06-05
 */
package cn.finedo.codereview.svncodeviewgroupuser;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.svncodeviewgroupuser.domain.SvncodeviewgroupuserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;

@Service
@Transactional
@Scope("singleton")
public class SvncodeviewgroupuserService {
	private static Logger logger = LogManager.getLogger();

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private IDUtil idutil;

	/**
	 * 查询项目成员
	 * @param dopssvn2
	 * @return
	 * @authro pt
	 * @date 2018年6月11日
	 * @return ReturnValueDomain<List<DopsSvnuserright>>
	 * @version V1.0
	 */
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> querynum(SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>();
		StringBuffer sql=new StringBuffer();
			
		DopsSvncodeviewgroupuser svncodeviewgroupnum=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(codeviewgroupnumquerydomain)) {
			pageparam=codeviewgroupnumquerydomain.getPageparam();
			svncodeviewgroupnum=codeviewgroupnumquerydomain.getSvncodeviewgroupuser();
			if(NonUtil.isNon(svncodeviewgroupnum)){
			    return ret.setFail("参数错误");
			}
            sql.append("SELECT t.usercode as userid ,p.personname as username,p.email,c.createperson,t.userstate,t.remtime,t.addtime,t.remoptuser FROM");
            sql.append("  tb_dops_projectmember t LEFT JOIN tb_sys_user u ON t.usercode = u.usercode");
            sql.append(" LEFT JOIN tb_sys_person p ON u.personid = p.personid LEFT JOIN ");
            sql.append(" tb_dops_svncodeviewgroup c ON t.cgid=c.cgid WHERE c.cgid=:cgid");
		}
		PageDomain<DopsSvncodeviewgroupuser> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), svncodeviewgroupnum, DopsSvncodeviewgroupuser.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询项目成员信息失败");
		}
		//过滤离职人员
		List<DopsSvncodeviewgroupuser> userList = retpage.getDatalist();
		Iterator<DopsSvncodeviewgroupuser> it = userList.iterator();
		while(it.hasNext()){
		    DopsSvncodeviewgroupuser user = it.next();
		    if(NonUtil.isNon(user.getUsername())){
		        it.remove();
		    }
		}
		return ret.setSuccess("查询项目成员成功", retpage);
	}
	
	
	/**
	 * 项目成员退出
	 * @param svncodeviewgrouplistdomain
	 * @return
	 * @authro pt
	 * @date 2018年6月13日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> delete(SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer groupsql=new StringBuffer();
		
		if (NonUtil.isNon(svncodeviewgroupnumquerydomain)) {
			return ret.setFail("无项目成员表");
		}
		
		DopsSvncodeviewgroupuser viewgroupnum =svncodeviewgroupnumquerydomain.getSvncodeviewgroupuser();
		//删除小组成员信息
		 groupsql.append("update tb_dops_projectmember set userstate = '0',remtime =NOW(),remoptuser=:remoptuser  where usercode = :userid and cgid = :cgid");
		
		
		try {
			jdbcTemplate.update(groupsql.toString(), viewgroupnum);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("项目成员退出成功");
	}
	
	/**
	 * 重新添加某个成员进入项目组中
	 * @param svncodeviewgroupnumquerydomain
	 * @return
	 * @authro pt
	 * @date 2018年7月1日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> returnback(SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer groupsql=new StringBuffer();
		
		if (NonUtil.isNon(svncodeviewgroupnumquerydomain)) {
			return ret.setFail("无项目成员表");
		}
		
		DopsSvncodeviewgroupuser viewgroupnum =svncodeviewgroupnumquerydomain.getSvncodeviewgroupuser();
		//重新小组成员信息
		 groupsql.append("update tb_dops_projectmember set userstate = '1',addtime =NOW()  where usercode = :userid and cgid = :cgid");
		
		
		try {
			jdbcTemplate.update(groupsql.toString(), viewgroupnum);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("项目成员加入成功");
	}
	
	/**
	 * 删除codeviewperson表中的数据根据cgid来删除
	 * @param svncodeviewgroupnumquerydomain
	 * @return
	 * @authro pt
	 * @date 2018年6月19日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	public ReturnValueDomain<String> deletecodeviewperson(SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer groupsql=new StringBuffer();
		
		if (NonUtil.isNon(svncodeviewgroupnumquerydomain)) {
			return ret.setFail("无项目成员表");
		}
		
		DopsSvncodeviewgroupuser viewgroupnum =svncodeviewgroupnumquerydomain.getSvncodeviewgroupuser();
		//删除小组成员信息
		 groupsql.append("DELETE FROM tb_dops_projectmember WHERE cgid=:cgid");
		
		try {
			jdbcTemplate.update(groupsql.toString(), viewgroupnum);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		return ret.setSuccess("项目成员删除成功");
	}
	
	/**
	 * 根据人员姓名进行模糊查询
	 * @param codeviewgroupnumquerydomain
	 * @return
	 * @authro pt
	 * @date 2018年6月15日
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewgroupnum>>
	 * @version V1.0
	 */
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> queryuserbyname(SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>();
		StringBuffer sql=new StringBuffer();
		DopsSvncodeviewgroupuser groupnum=new DopsSvncodeviewgroupuser();	
		DopsSvncodeviewgroupuser svncodeviewgroupnum=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(codeviewgroupnumquerydomain)) {
			pageparam=codeviewgroupnumquerydomain.getPageparam();
			svncodeviewgroupnum=codeviewgroupnumquerydomain.getSvncodeviewgroupuser();
			sql.append("SELECT u.usercode as userid ,p.personname as username FROM tb_sys_user u LEFT JOIN tb_sys_person p ON u.personid=p.personid");
			if(NonUtil.isNotNon(svncodeviewgroupnum)) {
				
				groupnum.setUsername("%"+svncodeviewgroupnum.getUsername()+"%");
				groupnum.setUserstate(svncodeviewgroupnum.getUserstate());
				sql.append("  WHERE personname LIKE :username");
			}
		}
		PageDomain<DopsSvncodeviewgroupuser> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), groupnum, DopsSvncodeviewgroupuser.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询成员信息失败");
		}
		
		return ret.setSuccess("查询员工成功", retpage);
	}
}
