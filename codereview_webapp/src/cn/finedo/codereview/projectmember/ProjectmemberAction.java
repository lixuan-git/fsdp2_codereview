/**
 * 评审成员表管理Action
 * 
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.projectmember.ProjectmemberServiceAPProxy;
import cn.finedo.codereview.projectmember.domain.ProjectmemberListDomain;
import cn.finedo.codereview.projectmember.domain.ProjectmemberQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.file.FileDownloadUtil;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svncodeviewperson")
public class ProjectmemberAction {
	private static Logger logger = LogManager.getLogger(); 

	/**
	 * 评审成员表查询
	 * @param DopsProjectmember
	 * @param PageParamDomain
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request) throws Exception {
	    DopsProjectmember dopssvncodeviewperson = FormUtil.request2Domain(request, DopsProjectmember.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		ProjectmemberQueryDomain svncodeviewpersonquerydomain = new ProjectmemberQueryDomain();
		svncodeviewpersonquerydomain.setDopssvncodeviewperson(dopssvncodeviewperson);
		svncodeviewpersonquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsProjectmember>> ret=ProjectmemberServiceAPProxy.query(svncodeviewpersonquerydomain);
		PageDomain<DopsProjectmember> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
	}
	
	/**
	 * 评审成员表新增
	 * @param ProjectmemberListDomain
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Object add(HttpServletRequest request) throws Exception {
		DopsProjectmember dopssvncodeviewperson = FormUtil.request2Domain(request, DopsProjectmember.class);
		
		ProjectmemberListDomain svncodeviewpersonlistdomain = new ProjectmemberListDomain();
		List<DopsProjectmember> dopssvncodeviewpersonlist=new ArrayList<DopsProjectmember>();
		dopssvncodeviewpersonlist.add(dopssvncodeviewperson);
		svncodeviewpersonlistdomain.setDopssvncodeviewpersonlist(dopssvncodeviewpersonlist);
	
		ReturnValueDomain<String> ret=ProjectmemberServiceAPProxy.add(svncodeviewpersonlistdomain);
		
		return ret;
	}
		
	/**
	 * 根据主键查询评审成员表
	 * @param DopsProjectmember
	 */
	@RequestMapping("/modifypage")
	public Object modifypage(HttpServletRequest request) throws Exception{
		DopsProjectmember dopssvncodeviewperson=FormUtil.request2Domain(request, DopsProjectmember.class);
		
		ProjectmemberQueryDomain svncodeviewpersonquerydomain= new ProjectmemberQueryDomain();
		svncodeviewpersonquerydomain.setDopssvncodeviewperson(dopssvncodeviewperson);
		svncodeviewpersonquerydomain.setPageparam(null);
		
		ReturnValueDomain<PageDomain<DopsProjectmember>> ret=ProjectmemberServiceAPProxy.query(svncodeviewpersonquerydomain);
		PageDomain<DopsProjectmember> page = ret.getObject();
		List<DopsProjectmember> dopssvncodeviewpersonlist = page.getDatalist();
		
		if (NonUtil.isNotNon(dopssvncodeviewpersonlist)) {
			request.setAttribute("dopssvncodeviewperson", dopssvncodeviewpersonlist.get(0));
		}
		
		return "/groupService/svncodeviewperson/modify.jsp";
	}
	
	/**
	 * 评审成员表修改
	 * @param ProjectmemberListDomain
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Object modify(HttpServletRequest request) throws Exception {
		// 传入的领域对象
		DopsProjectmember dopssvncodeviewperson = FormUtil.request2Domain(request, DopsProjectmember.class);
		
		ProjectmemberListDomain svncodeviewpersonlistdomain = new ProjectmemberListDomain();
		List<DopsProjectmember> dopssvncodeviewpersonlist=new ArrayList<DopsProjectmember>();
		dopssvncodeviewpersonlist.add(dopssvncodeviewperson);
		svncodeviewpersonlistdomain.setDopssvncodeviewpersonlist(dopssvncodeviewpersonlist);
		
		ReturnValueDomain<String> ret=ProjectmemberServiceAPProxy.update(svncodeviewpersonlistdomain);
		
		return ret;
	}

	/**
	 * 评审成员表删除
	 * @param ProjectmemberListDomain
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) throws Exception{
		// 传入的领域对象
		List<DopsProjectmember> dopssvncodeviewpersonlist=new ArrayList<DopsProjectmember>();
		
		String idsstr = request.getParameter("id");
		String[] idarray = idsstr.split(",");
		for(String id : idarray){
		    if(NonUtil.isNon(id)){
		        continue;
		    }
			DopsProjectmember dopssvncodeviewperson = new DopsProjectmember();
			
			dopssvncodeviewperson.setCpid(id);
			dopssvncodeviewpersonlist.add(dopssvncodeviewperson);
		}
		ProjectmemberListDomain svncodeviewpersonlistdomain = new ProjectmemberListDomain();
		svncodeviewpersonlistdomain.setDopssvncodeviewpersonlist(dopssvncodeviewpersonlist);
		
		ReturnValueDomain<String> ret=ProjectmemberServiceAPProxy.delete(svncodeviewpersonlistdomain);
		
		return ret;
	}
	
	/**
	 * 评审成员表详情
	 * @param DopsProjectmember
	 */
	@RequestMapping("/detail")
	public Object detail(HttpServletRequest request) throws Exception{
		// 传入的领域对象
		DopsProjectmember dopssvncodeviewperson=FormUtil.request2Domain(request, DopsProjectmember.class);
		
		ProjectmemberQueryDomain svncodeviewpersonquerydomain= new ProjectmemberQueryDomain();
		svncodeviewpersonquerydomain.setDopssvncodeviewperson(dopssvncodeviewperson);
		svncodeviewpersonquerydomain.setPageparam(null);
		
		ReturnValueDomain<PageDomain<DopsProjectmember>> ret=ProjectmemberServiceAPProxy.query(svncodeviewpersonquerydomain);
		PageDomain<DopsProjectmember> page = ret.getObject();
		List<DopsProjectmember> dopssvncodeviewpersonlist = page.getDatalist();
		
		if (NonUtil.isNotNon(dopssvncodeviewpersonlist)) {
			request.setAttribute("dopssvncodeviewperson", dopssvncodeviewpersonlist.get(0));
		}
				
		return "/groupService/svncodeviewperson/detail.jsp";
	}
	
	/**
	 * 导入
	 */
	@RequestMapping(value="/importexcel")
	@ResponseBody
	public Object importexcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String excelfileid=request.getParameter("excelfileid");
		
		SysEntityfile entityfile=new SysEntityfile();
		entityfile.setFileid(excelfileid);
		
		return ProjectmemberServiceAPProxy.importexcel(entityfile);
	}
	
	/**
	 * 导出
	 */
	@RequestMapping(value="/exportexcel")
	public void exportexcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DopsProjectmember dopssvncodeviewperson=FormUtil.request2Domain(request, DopsProjectmember.class);
		ProjectmemberQueryDomain svncodeviewpersonquerydomain = new ProjectmemberQueryDomain();
		svncodeviewpersonquerydomain.setDopssvncodeviewperson(dopssvncodeviewperson);

		ReturnValueDomain<SysEntityfile> ret=ProjectmemberServiceAPProxy.exportexcel(svncodeviewpersonquerydomain);
		SysEntityfile entityfile=ret.getObject();
		String filepath=entityfile.getFilepath();
		String filename=entityfile.getFilename();
		
		FileDownloadUtil.filedownload(filename, filepath + File.separator + filename, request, response);
	}
}
