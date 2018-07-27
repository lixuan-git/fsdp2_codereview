/**
 * 评审小组表管理Action
 * 
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.svncodeviewgroup;

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
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.file.FileDownloadUtil;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupServiceAPProxy;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svncodeviewgroup")
public class SvncodeviewgroupAction {
	private static Logger logger = LogManager.getLogger(); 

	/**
	 * 评审小组表查询
	 * @param DopsSvncodeviewgroup
	 * @param PageParamDomain
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroup dopssvncodeviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = new SvncodeviewgroupQueryDomain();
		svncodeviewgroupquerydomain.setDopssvncodeviewgroup(dopssvncodeviewgroup);
		svncodeviewgroupquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret=SvncodeviewgroupServiceAPProxy.query(svncodeviewgroupquerydomain);
		PageDomain<DopsSvncodeviewgroup> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
	}
	
	/**
	 * 根据登录人的usercode查询其参与的项目评审小组
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月25日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/querygroupbyusercode")
	@ResponseBody
	public Object querygroupbyusercode(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroup dopssvncodeviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = new SvncodeviewgroupQueryDomain();
		svncodeviewgroupquerydomain.setDopssvncodeviewgroup(dopssvncodeviewgroup);
		svncodeviewgroupquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret=SvncodeviewgroupServiceAPProxy.querygroupbyusercode(svncodeviewgroupquerydomain);
		PageDomain<DopsSvncodeviewgroup> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
	}
	
	
	/**
	 * 评审小组表新增
	 * @param SvncodeviewgroupListDomain
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Object add(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroup dopssvncodeviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = new SvncodeviewgroupListDomain();
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		dopssvncodeviewgrouplist.add(dopssvncodeviewgroup);
		svncodeviewgrouplistdomain.setDopssvncodeviewgrouplist(dopssvncodeviewgrouplist);
	
		ReturnValueDomain<String> ret=SvncodeviewgroupServiceAPProxy.add(svncodeviewgrouplistdomain);
		
		return ret;
	}
		
	/**
	 * 根据主键查询评审小组表
	 * @param DopsSvncodeviewgroup
	 */
	@RequestMapping("/modifypage")
	public Object modifypage(HttpServletRequest request) throws Exception{
		DopsSvncodeviewgroup dopssvncodeviewgroup=FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain= new SvncodeviewgroupQueryDomain();
		svncodeviewgroupquerydomain.setDopssvncodeviewgroup(dopssvncodeviewgroup);
		svncodeviewgroupquerydomain.setPageparam(null);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret=SvncodeviewgroupServiceAPProxy.query(svncodeviewgroupquerydomain);
		PageDomain<DopsSvncodeviewgroup> page = ret.getObject();
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist = page.getDatalist();
		
		if (NonUtil.isNotNon(dopssvncodeviewgrouplist)) {
			request.setAttribute("dopssvncodeviewgroup", dopssvncodeviewgrouplist.get(0));
		}
		
		return "/groupService/svncodeviewgroup/modify.jsp";
	}
	
	/**
	 * 评审小组表修改
	 * @param SvncodeviewgroupListDomain
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Object modify(HttpServletRequest request) throws Exception {
		// 传入的领域对象
		DopsSvncodeviewgroup dopssvncodeviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = new SvncodeviewgroupListDomain();
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		dopssvncodeviewgrouplist.add(dopssvncodeviewgroup);
		svncodeviewgrouplistdomain.setDopssvncodeviewgrouplist(dopssvncodeviewgrouplist);
		
		ReturnValueDomain<String> ret=SvncodeviewgroupServiceAPProxy.update(svncodeviewgrouplistdomain);
		
		return ret;
	}

	/**
	 * 评审小组表删除
	 * @param SvncodeviewgroupListDomain
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) throws Exception{
		DopsSvncodeviewgroup dopssvncodeviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		// 传入的领域对象
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		
		String idsstr = dopssvncodeviewgroup.getCgid();
		String[] idarray = idsstr.split(",");
		for(String id : idarray){
			DopsSvncodeviewgroup svncodeviewgroup = new DopsSvncodeviewgroup();
			
			svncodeviewgroup.setCgid(id);
			dopssvncodeviewgrouplist.add(svncodeviewgroup);
		}
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = new SvncodeviewgroupListDomain();
		svncodeviewgrouplistdomain.setDopssvncodeviewgrouplist(dopssvncodeviewgrouplist);
		
		ReturnValueDomain<String> ret=SvncodeviewgroupServiceAPProxy.delete(svncodeviewgrouplistdomain);
		
		return ret;
	}
	
	/**
	 * 评审小组表详情
	 * @param DopsSvncodeviewgroup
	 */
	@RequestMapping("/detail")
	public Object detail(HttpServletRequest request) throws Exception{
		// 传入的领域对象
		DopsSvncodeviewgroup dopssvncodeviewgroup=FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain= new SvncodeviewgroupQueryDomain();
		svncodeviewgroupquerydomain.setDopssvncodeviewgroup(dopssvncodeviewgroup);
		svncodeviewgroupquerydomain.setPageparam(null);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret=SvncodeviewgroupServiceAPProxy.query(svncodeviewgroupquerydomain);
		PageDomain<DopsSvncodeviewgroup> page = ret.getObject();
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist = page.getDatalist();
		
		if (NonUtil.isNotNon(dopssvncodeviewgrouplist)) {
			request.setAttribute("dopssvncodeviewgroup", dopssvncodeviewgrouplist.get(0));
		}
				
		return "/groupService/svncodeviewgroup/detail.jsp";
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
		
		return SvncodeviewgroupServiceAPProxy.importexcel(entityfile);
	}
	
	/**
	 * 导出
	 */
	@RequestMapping(value="/exportexcel")
	public void exportexcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DopsSvncodeviewgroup dopssvncodeviewgroup=FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = new SvncodeviewgroupQueryDomain();
		svncodeviewgroupquerydomain.setDopssvncodeviewgroup(dopssvncodeviewgroup);

		ReturnValueDomain<SysEntityfile> ret=SvncodeviewgroupServiceAPProxy.exportexcel(svncodeviewgroupquerydomain);
		SysEntityfile entityfile=ret.getObject();
		String filepath=entityfile.getFilepath();
		String filename=entityfile.getFilename();
		
		FileDownloadUtil.filedownload(filename, filepath + File.separator + filename, request, response);
	}
	
	/**
	 * 修改小组信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月11日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/updategroup")
	@ResponseBody
	public Object updategroup(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroup svnviewgroup = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
		SvncodeviewgroupListDomain viewgrouplistdomain = new SvncodeviewgroupListDomain();
		List<DopsSvncodeviewgroup> viewgrouplist=new ArrayList<DopsSvncodeviewgroup>();
		viewgrouplist.add(svnviewgroup);
		viewgrouplistdomain.setDopssvncodeviewgrouplist(viewgrouplist);;
		ReturnValueDomain<String> ret=SvncodeviewgroupServiceAPProxy.updategroup(viewgrouplistdomain);
		return ret;
	}
	
	   /**
     * 查询全局评审小组
     * @param DopsSvncodeviewgroup
     * @param PageParamDomain
     */
    @RequestMapping("/querywholegroup")
    @ResponseBody
    public Object querywholegroup(HttpServletRequest request) throws Exception {
        SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = new SvncodeviewgroupQueryDomain();
        return SvncodeviewgroupServiceAPProxy.querywholegroup(svncodeviewgroupquerydomain);
    }
	
}
