package cn.finedo.codereview.svncommend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupServiceAPProxy;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewproject.SvncodeviewprojectServiceAPProxy;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;

@Controller
@Scope("prototype")
@RequestMapping("/finedo/svncommend")
public class SvncommendAction {
	private static Logger logger = LogManager.getLogger(); 
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 查询列表
	 */
	@RequestMapping("/querylist")
	@ResponseBody
	public Object querylist(HttpServletRequest request) throws Exception {
		DopsSvncommend dopsSvncommend = FormUtil.request2Domain(request, DopsSvncommend.class);
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		SvncommendQueryDomain svncommendquerydomain = new SvncommendQueryDomain();
		svncommendquerydomain.setDopssvncommend(dopsSvncommend);
		svncommendquerydomain.setPageparam(pageparam);
		ReturnValueDomain<PageDomain<DopsSvncommend>> ret=SvncommendServiceAPProxy.querylist(svncommendquerydomain);
		PageDomain<DopsSvncommend> page = ret.getObject();
		return PageUtil.build(page.getDatalist(), page.getRowcount());
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 推荐代码
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Object create(HttpServletRequest request) throws Exception {
		DopsSvncommend dopsSvncommend = FormUtil.request2Domain(request, DopsSvncommend.class);
		SvncommendListDomain svncommendListDomain = new SvncommendListDomain();
		List<DopsSvncommend> dopssvncommendlist=new ArrayList<DopsSvncommend>();
		dopssvncommendlist.add(dopsSvncommend);
		svncommendListDomain.setDopssvncommendlist(dopssvncommendlist);
		ReturnValueDomain<String> ret=SvncommendServiceAPProxy.create(svncommendListDomain);
		return ret;
	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 查询详情
	 */
	@RequestMapping("/querydetail")
	@ResponseBody
	public Object querydetail(HttpServletRequest request) throws Exception {
		DopsSvncommend dopsSvncommend = FormUtil.request2Domain(request, DopsSvncommend.class);
		SvncommendQueryDomain svncommendqueryDomain = new SvncommendQueryDomain();
		svncommendqueryDomain.setDopssvncommend(dopsSvncommend);
		ReturnValueDomain<DopsSvncommend> ret=SvncommendServiceAPProxy.querydetail(svncommendqueryDomain);
		return ret;
	}
	
	@RequestMapping("/querynumber")
	@ResponseBody
	public Object querynumber(HttpServletRequest request) throws Exception {
		DopsSvncommend dopsSvncommend = FormUtil.request2Domain(request, DopsSvncommend.class);
		SvncommendQueryDomain svncommendqueryDomain = new SvncommendQueryDomain();
		svncommendqueryDomain.setDopssvncommend(dopsSvncommend);
		ReturnValueDomain<DopsSvncommend> ret=SvncommendServiceAPProxy.querynumber(svncommendqueryDomain);
		return ret;
	}
	/**
	 * @param request
	 * @return
	 * @throws Exception
	*TODO 投票
	 */
	@RequestMapping("/poll")
	@ResponseBody
	public Object poll(HttpServletRequest request) throws Exception {
		// 传入的领域对象
		DopsSvncommend dopssvncommend = FormUtil.request2Domain(request, DopsSvncommend.class);
		SvncommendListDomain svncommendlistdomain = new SvncommendListDomain();
		List<DopsSvncommend> dopssvnconnendlist=new ArrayList<DopsSvncommend>();
		dopssvnconnendlist.add(dopssvncommend);
		svncommendlistdomain.setDopssvncommendlist(dopssvnconnendlist);
		
		ReturnValueDomain<String> ret=SvncommendServiceAPProxy.poll(svncommendlistdomain);
		
		return ret;
	}
}
