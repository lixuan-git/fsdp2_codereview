package cn.finedo.codereview.svncodeviewproject;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupServiceAPProxy;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;

/**
 * @author 刘青成
 *TODO
 */
@Controller
@Scope("prototype")
@RequestMapping("/finedo/svncodeviewproject")
public class SvncodeviewprojectAction {
	private static Logger logger = LogManager.getLogger(); 
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO
	 */
	@RequestMapping("/queryproject")
	@ResponseBody
	public Object queryproject(HttpServletRequest request) throws Exception {
		DopsSvncodeviewproject dopsSvncodeviewproject = FormUtil.request2Domain(request, DopsSvncodeviewproject.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		SvncodeviewprojectDomain svncodeviewprojectDomain = new SvncodeviewprojectDomain();
		svncodeviewprojectDomain.setSvncodeviewproject(dopsSvncodeviewproject);
		svncodeviewprojectDomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> ret=SvncodeviewprojectServiceAPProxy.queryproject(svncodeviewprojectDomain);
		PageDomain<DopsSvncodeviewproject> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 新建项目
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Object create(HttpServletRequest request) throws Exception {
		DopsSvncodeviewproject dopsSvncodeviewproject = FormUtil.request2Domain(request, DopsSvncodeviewproject.class);
		SvncodeviewprojectListDomain svncodeviewprojectListDomain = new SvncodeviewprojectListDomain();
		List<DopsSvncodeviewproject> dopssvncodeviewprojectlist=new ArrayList<DopsSvncodeviewproject>();
		dopssvncodeviewprojectlist.add(dopsSvncodeviewproject);
		svncodeviewprojectListDomain.setDopssvncodeviewprojectlist(dopssvncodeviewprojectlist);
		ReturnValueDomain<String> ret=SvncodeviewprojectServiceAPProxy.create(svncodeviewprojectListDomain);
		return ret;
	}
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 删除项目
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) throws Exception{
		DopsSvncodeviewproject dopssvncodeviewproject = FormUtil.request2Domain(request, DopsSvncodeviewproject.class);
		// 传入的领域对象
		List<DopsSvncodeviewproject> dopssvncodeviewprojectlist=new ArrayList<DopsSvncodeviewproject>();
		
		String idsstr = dopssvncodeviewproject.getProjectid();
		String[] idarray = idsstr.split(",");
		for(String id : idarray){
			DopsSvncodeviewproject svncodeviewproject = new DopsSvncodeviewproject();
			
			svncodeviewproject.setProjectid(id);
			dopssvncodeviewprojectlist.add(svncodeviewproject);
		}
		SvncodeviewprojectListDomain svncodeviewprojectlistdomain = new SvncodeviewprojectListDomain();
		svncodeviewprojectlistdomain.setDopssvncodeviewprojectlist(dopssvncodeviewprojectlist);
		
		ReturnValueDomain<String> ret=SvncodeviewprojectServiceAPProxy.delete(svncodeviewprojectlistdomain);
		
		return ret;
	}
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 查询权限
	 */
	@RequestMapping("/queryrole")
	@ResponseBody
	public Object queryrole(HttpServletRequest request) throws Exception {
		DopsSvncodeviewproject dopsSvncodeviewproject = FormUtil.request2Domain(request, DopsSvncodeviewproject.class);
		SvncodeviewprojectDomain svncodeviewprojectDomain = new SvncodeviewprojectDomain();
		svncodeviewprojectDomain.setSvncodeviewproject(dopsSvncodeviewproject);
		ReturnValueDomain<DopsSvncodeviewproject> ret=SvncodeviewprojectServiceAPProxy.queryrole(svncodeviewprojectDomain);
		return ret;
	}
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 查询全局项目成员
	 */
	@RequestMapping("/querycgid")
	@ResponseBody
	public Object querycgid(HttpServletRequest request) throws Exception {
		DopsSvncodeviewproject dopsSvncodeviewproject = FormUtil.request2Domain(request, DopsSvncodeviewproject.class);
		SvncodeviewprojectDomain svncodeviewprojectDomain = new SvncodeviewprojectDomain();
		svncodeviewprojectDomain.setSvncodeviewproject(dopsSvncodeviewproject);
		ReturnValueDomain<DopsSvncodeviewproject> ret=SvncodeviewprojectServiceAPProxy.querycgid(svncodeviewprojectDomain);
		return ret;
	}
	

    @RequestMapping(value="/modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(@RequestBody DopsSvncodeviewproject dopsSvncodeviewproject, HttpServletRequest request) throws Exception {
        return SvncodeviewprojectServiceAPProxy.modify(dopsSvncodeviewproject);
    }
	
	
}
