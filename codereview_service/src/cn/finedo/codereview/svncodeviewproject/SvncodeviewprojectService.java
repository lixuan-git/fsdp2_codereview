package cn.finedo.codereview.svncodeviewproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewgroupuserrole.SvncodeviewgroupuserroleService;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;

/**
 * @author 刘青成
 *TODO
 */
@Service
@Transactional
@Scope("singleton")
public class SvncodeviewprojectService {
	private static Logger logger = LogManager.getLogger(); 
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	@Autowired
	private SvncodeviewgroupuserroleService svncodeviewgroupuserroleService;
	
	public ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> queryproject(SvncodeviewprojectDomain svncodeviewprojectDomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewproject>>();
		if(NonUtil.isNon(svncodeviewprojectDomain)) {
			return ret.setFail("参数校验失败");
		}
		PageParamDomain pageparam=svncodeviewprojectDomain.getPageparam();
		DopsSvncodeviewproject dopssvncodeviewproject=svncodeviewprojectDomain.getSvncodeviewproject();
		PageDomain<DopsSvncodeviewproject> retpage=null;
		if(NonUtil.isNon(dopssvncodeviewproject)){
		    return ret.setFail("参数校验失败");
		}
        StringBuffer sb = new StringBuffer();
        Map<String, Object> args=new HashMap<String, Object>();
        args.put("usercode", dopssvncodeviewproject.getProjectmng());
        args.put("grouprole", "sm");
        int result=0;
        // 先判断是否是系统管理者角色
        String sql1 = "SELECT COUNT(0) FROM tb_dops_svncodeviewgroupuserrole  WHERE usercode = :usercode  AND grouprole=:grouprole";
        //查询项目
        sb.append("SELECT a.projectid,a.projectname,a.createtime,a.orgcode,a.busimng,a.projectmng,a.projectdescribe, (SELECT g.cgid FROM tb_dops_projectsvn t RIGHT JOIN tb_dops_svncodeviewgroup g ON (t.svnpath = g.svnpath AND g.svnaddr = (SELECT s.svnaddr FROM tb_dops_svnmng s WHERE s.svnid = t.svnid)) WHERE t.projectid = a.projectid) AS cgid FROM tb_dops_project a WHERE 1=1 AND a.projectid IN (SELECT projectid FROM tb_dops_projectsvn)  AND a.projectname LIKE  :projectname");
        try {
             result = jdbcTemplate.queryForInt(sql1, args);
        }
        catch (DataAccessException e1) {
            logger.error("查询项目经理权限表失败", e1);
            return ret.setFail("查询项目经理权限表失败");
        }
        if(result<=0){
            sb.append(" AND a.projectmng=:projectmng");
        }
            try {
			retpage =  jdbcTemplate.queryForPage(sb.toString(), dopssvncodeviewproject, DopsSvncodeviewproject.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询项目信息失败");
		}
		
		return ret.setSuccess("查询项目信息表成功", retpage);
	}
	
	
	/**
	 * @param svncodeviewgrouplistdomain
	 * @return
	*TODO 创建项目
	 */
	public ReturnValueDomain<String> create(SvncodeviewprojectListDomain svncodeviewprojectlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		StringBuffer sql1=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		StringBuffer sql3=new StringBuffer();
		StringBuffer sql4=new StringBuffer();
		StringBuffer svnaddrsql=new StringBuffer();
		DopsSvncodeviewproject codeviewgroup=new DopsSvncodeviewproject();
		List<DopsSvncodeviewproject> svncodeviewprojectlist=new ArrayList<DopsSvncodeviewproject>();
		List<DopsSvncodeviewgroup> svncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		if (NonUtil.isNon(svncodeviewprojectlistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncodeviewproject> dopssvncodeviewprojectlist=svncodeviewprojectlistdomain.getDopssvncodeviewprojectlist();
		
		if (NonUtil.isNon(dopssvncodeviewprojectlist)) {
			return ret.setFail("无评审小组表");
		}
		DopsSvncodeviewproject project=dopssvncodeviewprojectlist.get(0);
		String svnpath="/"+getFromIndex(dopssvncodeviewprojectlist.get(0).getSvnpath(),"/",3);
		try{
			String cgid=idutil.getID("coderproject");
			DopsSvncodeviewgroup dopssvncodeviewgroup=new DopsSvncodeviewgroup();
			for(DopsSvncodeviewproject dopssvncodeviewproject : dopssvncodeviewprojectlist) {
				dopssvncodeviewproject.setProjectid(cgid);
				dopssvncodeviewproject.setProjectname(dopssvncodeviewprojectlist.get(0).getProjectname());
				dopssvncodeviewproject.setProjectmng(dopssvncodeviewprojectlist.get(0).getProjectmng());
				dopssvncodeviewproject.setBusimng(dopssvncodeviewprojectlist.get(0).getBusimng());
				dopssvncodeviewproject.setOrgcode(dopssvncodeviewprojectlist.get(0).getOrgcode());
				dopssvncodeviewproject.setRepotype(dopssvncodeviewprojectlist.get(0).getRepotype());
				dopssvncodeviewproject.setSvnaddr(dopssvncodeviewprojectlist.get(0).getSvnpath().replace(svnpath, "/"));
				dopssvncodeviewproject.setSvnpath(svnpath);
				dopssvncodeviewproject.setAddoptuser(dopssvncodeviewprojectlist.get(0).getAddoptuser());
				dopssvncodeviewproject.setProjectdescribe(dopssvncodeviewprojectlist.get(0).getProjectdescribe());
				svncodeviewprojectlist.add(dopssvncodeviewproject);
				dopssvncodeviewgroup.setCgid(cgid);
				dopssvncodeviewgroup.setGroupname(getFromIndex(svnpath,"/",2));
				dopssvncodeviewgroup.setSvnaddr(dopssvncodeviewprojectlist.get(0).getSvnaddr());
				dopssvncodeviewgroup.setSvnpath(svnpath);
				dopssvncodeviewgroup.setRepotype(dopssvncodeviewprojectlist.get(0).getRepotype());
				dopssvncodeviewgroup.setCreateperson(dopssvncodeviewprojectlist.get(0).getAddoptuser());
				dopssvncodeviewgroup.setGroupdesc(dopssvncodeviewprojectlist.get(0).getProjectdescribe());
				svncodeviewgrouplist.add(dopssvncodeviewgroup);
			}
		}catch(Exception ex) {
			logger.error("添加项目失败",ex);
			return ret.setFail("添加项目失败");
		}
		int num=0;
		Map<String, Object> args=new HashMap<String, Object>();
        args.put("svnaddr", svncodeviewgrouplist.get(0).getSvnaddr());
		String rolesql=" SELECT COUNT(0) FROM tb_dops_svnmng WHERE svnaddr=:svnaddr";
		   try {
			   num = jdbcTemplate.queryForInt(rolesql, args);
	        }
	        catch (DataAccessException e1) {
	            logger.error("查询项目经理权限表失败", e1);
	            return ret.setFail("查询项目经理权限表失败");
	        }
	        if(num<=0){
	        	  return ret.setFail("没有svnid项目创建失败");
	        }
		
		
		//project表插入数据
  		    sql.append("INSERT INTO tb_dops_project (projectid, projectname,projectcode, createtime, orgcode, busimng, projectmng, projectdescribe,repotype,svnaddr,svnpath,status,statusname,statustime) ");
            sql.append("  VALUES (:projectid, :projectname,:projectid, NOW(), :orgcode, :busimng, :projectmng, :projectdescribe, :repotype,:svnaddr,:svnpath,'NEW','立项', NOW())");
  		 // projectsvn表插入数据
            sql1.append("INSERT INTO tb_dops_projectsvn (projectid, svnid,svnpath)  VALUES (:projectid,(SELECT svnid FROM tb_dops_svnmng WHERE svnaddr=:svnaddr),:svnpath)");
  		  //group表插入数据
  		    sql2.append("INSERT INTO tb_dops_svncodeviewgroup (cgid, groupname,svnaddr, svnpath, repotype, createperson, optdate, groupdesc,grouptype) ");
          sql2.append("  VALUES (:cgid, :groupname,:svnaddr,:svnpath,:repotype,:createperson,NOW(), :groupdesc,'common')");
        //projectmember表插入数据
		    sql3.append("INSERT INTO  tb_dops_projectmember (cpid, usercode,cgid, addtime, addoptuser, userstate) ");
        sql3.append("  VALUES (:projectid, :projectmng,:projectid,NOW(),:addoptuser,'1')");
      //svncodeviewgroupuserrole表插入数据
	    sql4.append("INSERT INTO  tb_dops_svncodeviewgroupuserrole (usercode, grouprole,svnpath, svnid) ");
        sql4.append("  VALUES (:projectmng, 'pm',:svnpath,(SELECT svnid FROM tb_dops_svnmng WHERE svnaddr=:svnaddr))");
  		    try {
			jdbcTemplate.batchUpdate(sql.toString(), svncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql1.toString(), svncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql2.toString(), svncodeviewgrouplist);
			jdbcTemplate.batchUpdate(sql3.toString(), svncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql4.toString(), svncodeviewprojectlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("项目新增成功");
	}
	
	
	
	
	
	/**
	 * @param svncodeviewgrouplistdomain
	 * @return
	*TODO 删除项目
	 */
	public ReturnValueDomain<String> delete(SvncodeviewprojectListDomain svncodeviewprojectlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		StringBuffer groupsql=new StringBuffer();
		StringBuffer sql1=new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		StringBuffer sql3=new StringBuffer();
		if (NonUtil.isNon(svncodeviewprojectlistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncodeviewproject> dopssvncodeviewprojectlist = svncodeviewprojectlistdomain.getDopssvncodeviewprojectlist();
		
		//删除小组信息
		sql.append("DELETE FROM tb_dops_project WHERE projectid=:projectid");
		groupsql.append("DELETE FROM tb_dops_projectsvn WHERE projectid=:projectid");
		sql1.append("DELETE FROM 	tb_dops_svncodeviewgroup  WHERE cgid=:projectid");
		sql2.append("DELETE FROM tb_dops_projectmember WHERE cgid=:projectid");
		sql3.append("DELETE FROM tb_dops_svncodeviewgroupuserrole WHERE svnpath=(SELECT svnpath FROM tb_dops_projectsvn WHERE projectid=:projectid) AND svnid=(SELECT svnid FROM tb_dops_projectsvn WHERE projectid=:projectid)");
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql1.toString(), dopssvncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql2.toString(), dopssvncodeviewprojectlist);
			jdbcTemplate.batchUpdate(sql3.toString(), dopssvncodeviewprojectlist);
			jdbcTemplate.batchUpdate(groupsql.toString(), dopssvncodeviewprojectlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("评审小组表删除成功");
	}
	/**
	 * @param svncodeviewprojectDomain
	 * @return
	*TODO 查询权限
	 */
	public ReturnValueDomain<DopsSvncodeviewproject> queryrole(SvncodeviewprojectDomain svncodeviewprojectDomain) {
		ReturnValueDomain<DopsSvncodeviewproject> ret = new ReturnValueDomain<DopsSvncodeviewproject>();
		DopsSvncodeviewproject dopssvncodeviewproject=svncodeviewprojectDomain.getSvncodeviewproject();	
		DopsSvncodeviewproject resultproject= new DopsSvncodeviewproject();
	     Map<String, Object> args=new HashMap<String, Object>();
	        args.put("usercode", dopssvncodeviewproject.getProjectmng());
	        args.put("grouprole", "sm");
	        int result=0;
	        // 先判断是否是系统管理者角色
	        String sql1 = "SELECT COUNT(0) FROM tb_dops_svncodeviewgroupuserrole  WHERE usercode = :usercode  AND grouprole=:grouprole";
	        try {
	             result = jdbcTemplate.queryForInt(sql1, args);
	             resultproject.setProjectcode(String.valueOf(result));
	        }
	        catch (DataAccessException e1) {
	            logger.error("查询项目经理权限表失败", e1);
	            return ret.setFail("查询项目经理权限表失败");
	        }
		return  ret.setSuccess("查询项目经理权限表成功", resultproject);
		
	}
	/**
	 * @param svncodeviewprojectDomain
	 * @return
	*TODO 查询全局小组成员cgid
	 */
	public ReturnValueDomain<DopsSvncodeviewproject> querycgid(SvncodeviewprojectDomain svncodeviewprojectDomain) {
		ReturnValueDomain<DopsSvncodeviewproject> ret = new ReturnValueDomain<DopsSvncodeviewproject>();
		DopsSvncodeviewproject dopssvncodeviewproject=svncodeviewprojectDomain.getSvncodeviewproject();	
		DopsSvncodeviewproject resultproject= new DopsSvncodeviewproject();
	        String  result=null;
	        // 先判断是否是系统管理者角色
	        String sql1 = "SELECT cgid FROM tb_dops_svncodeviewgroup WHERE grouptype='all'";
	        List<DopsSvncodeviewgroup> list =null;
	        try {
	             list= jdbcTemplate.query(sql1,DopsSvncodeviewgroup.class);
	        }
	        catch (DataAccessException e1) {
	            logger.error("查询项目经理权限表失败", e1);
	            return ret.setFail("查询项目经理权限表失败");
	        }
	        if(NonUtil.isNotNon(list)){
	        	resultproject.setProjectid(list.get(0).getCgid());
	        }
		return  ret.setSuccess("查询项目经理权限表成功", resultproject);
		
	}
	
  /**
 * @param str
 * @param modelStr
 * @param count
 * @return
*TODO 截取SVNpath
 */
private String getFromIndex(String str, String modelStr, Integer count) {
  // 对子字符串进行匹配
  Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
  int index = 0;
  // matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
  while (slashMatcher.find()) {
      index++ ;
      // 当modelStr字符第count次出现的位置
      if (index == count) {
          break;
      }
  }
  // matcher.start();返回以前匹配的初始索引。
  index = slashMatcher.start();
  return str.substring(index+1);
}


public ReturnValueDomain<String> modify(DopsSvncodeviewproject dopsSvncodeviewproject) {
    ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
    if(NonUtil.isNon(dopsSvncodeviewproject)){
        return ret.setFail("前台参数错误");
    }
    String sql = "select t.projectid,p.svnaddr,p.svnpath from tb_dops_project t RIGHT JOIN tb_dops_svncodeviewgroup p on t.svnaddr = p.svnaddr and t.svnpath = p.svnpath where p.cgid = :projectid";
    DopsSvncodeviewproject project = null;
    try {
        project = jdbcTemplate.queryForObject(sql,dopsSvncodeviewproject, DopsSvncodeviewproject.class);
    }
    catch (DataAccessException e1) {
        logger.error("更新项目失败！");
        return ret.setFail("更新项目失败！");
    }
    StringBuffer stringbuffer = new StringBuffer();
    if(NonUtil.isNon(project.getProjectid())){
        //不存在，新建项目.为了兼容存在svn项目，oa项目不存在的这种情况。实际流程中不会有这种情况
        String projectid = idutil.getID("coderproject");
        dopsSvncodeviewproject.setProjectid(projectid);
        dopsSvncodeviewproject.setSvnaddr(project.getSvnaddr());
        dopsSvncodeviewproject.setSvnpath(project.getSvnpath());
        stringbuffer.append("insert into tb_dops_project (projectid,projectname,projectmng,busimng,projectdescribe,repotype,svnaddr,svnpath) values (:projectid,:projectname,:projectmng,:busimng,:projectdescribe,'svn',:svnaddr,:svnpath)");
    }else{
        //存在，修改项目
        dopsSvncodeviewproject.setProjectid(project.getProjectid());
        stringbuffer.append("update tb_dops_project set projectname = :projectname,projectmng = :projectmng,busimng = :busimng,projectdescribe=:projectdescribe where projectid = :projectid");
    }
    try {
        jdbcTemplate.update(stringbuffer.toString(),dopsSvncodeviewproject);
    }
    catch (DataAccessException e) {
        logger.error("更新项目失败！",e);
        return ret.setFail("更新项目失败！");
    }
    
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE tb_dops_svncodeviewgroupuserrole SET usercode=:projectmng WHERE svnpath=(SELECT svnpath FROM tb_dops_project WHERE projectid=:projectid) AND svnid=(SELECT svnid FROM tb_dops_svnmng WHERE svnaddr=(SELECT svnaddr FROM tb_dops_project WHERE projectid=:projectid))AND grouprole='pm'");
    try {
        jdbcTemplate.update(sb.toString(),dopsSvncodeviewproject);
    }
    catch (DataAccessException e) {
        logger.error("更新项目权限表失败！");
        return ret.setFail("更新项目权限表失败！");
    }
    
    return ret.setSuccess("更新项目成功！");
}

}
