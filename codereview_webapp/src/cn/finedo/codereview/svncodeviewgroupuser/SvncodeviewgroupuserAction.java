/**
 * 
 */
package cn.finedo.codereview.svncodeviewgroupuser;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.svncodeviewgroupuser.domain.SvncodeviewgroupuserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;


@Controller
@Scope("singleton")
@RequestMapping("/finedo/svncodeviewgroupuser")
public class SvncodeviewgroupuserAction {
	private static Logger logger = LogManager.getLogger();
	
	
	/**
	 * 查询项目成员
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月11日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/querynum")
	@ResponseBody
	public Object querynum(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroupuser codeviewgroupnum = FormUtil.request2Domain(request, DopsSvncodeviewgroupuser.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain=new SvncodeviewgroupuserQueryDomain();
		codeviewgroupnumquerydomain.setPageparam(pageparam);
		codeviewgroupnumquerydomain.setSvncodeviewgroupuser(codeviewgroupnum);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret=SvncodeviewgroupuserServiceAPProxy.querynum(codeviewgroupnumquerydomain);
		PageDomain<DopsSvncodeviewgroupuser> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
		
	}
	
	
	/**
	 * 评审小组成员退出
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月13日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) throws Exception{
		DopsSvncodeviewgroupuser viewgroupnum = FormUtil.request2Domain(request, DopsSvncodeviewgroupuser.class);
		SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain = new SvncodeviewgroupuserQueryDomain();
		svncodeviewgroupnumquerydomain.setSvncodeviewgroupuser(viewgroupnum);
		
		ReturnValueDomain<String> ret=SvncodeviewgroupuserServiceAPProxy.delete(svncodeviewgroupnumquerydomain);
		
		return ret;
	}
	
	
	/**
	 * 重新是某个成员加入项目中
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年7月1日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/returnback")
	@ResponseBody
	public Object returnback(HttpServletRequest request) throws Exception{
		DopsSvncodeviewgroupuser viewgroupnum = FormUtil.request2Domain(request, DopsSvncodeviewgroupuser.class);
		SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain = new SvncodeviewgroupuserQueryDomain();
		svncodeviewgroupnumquerydomain.setSvncodeviewgroupuser(viewgroupnum);
		
		ReturnValueDomain<String> ret=SvncodeviewgroupuserServiceAPProxy.returnback(svncodeviewgroupnumquerydomain);
		
		return ret;
	}
	
	
	
	
	
	/**
	 * 删除codeviewperson表中的数据根据cgid来删除
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月19日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/deletecodeviewperson")
	@ResponseBody
	public Object deletecodeviewperson(HttpServletRequest request) throws Exception{
		DopsSvncodeviewgroupuser viewgroupnum = FormUtil.request2Domain(request, DopsSvncodeviewgroupuser.class);
		SvncodeviewgroupuserQueryDomain svncodeviewgroupnumquerydomain = new SvncodeviewgroupuserQueryDomain();
		svncodeviewgroupnumquerydomain.setSvncodeviewgroupuser(viewgroupnum);
		
		ReturnValueDomain<String> ret=SvncodeviewgroupuserServiceAPProxy.deletecodeviewperson(svncodeviewgroupnumquerydomain);
		
		return ret;
	}
	
	
	/**
	 * 根据人员姓名进行模糊查询
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月15日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/queryuserbyname")
	@ResponseBody
	public Object querynumbyname(HttpServletRequest request) throws Exception {
		DopsSvncodeviewgroupuser codeviewgroupnum = FormUtil.request2Domain(request, DopsSvncodeviewgroupuser.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain=new SvncodeviewgroupuserQueryDomain();
		codeviewgroupnumquerydomain.setPageparam(pageparam);
		codeviewgroupnumquerydomain.setSvncodeviewgroupuser(codeviewgroupnum);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret=SvncodeviewgroupuserServiceAPProxy.queryuserbyname(codeviewgroupnumquerydomain);
		PageDomain<DopsSvncodeviewgroupuser> page = ret.getObject();
		
		return PageUtil.build(page.getDatalist(), page.getRowcount());
		
	}
	
}
