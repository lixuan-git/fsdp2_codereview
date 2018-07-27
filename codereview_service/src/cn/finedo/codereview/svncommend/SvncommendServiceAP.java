package cn.finedo.codereview.svncommend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewproject.SvncodeviewprojectService;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendQueryDomain;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.fsdp.service.file.FileService;

@Controller
@Scope("prototype")
@RequestMapping("service/finedo/svncommend")
public class SvncommendServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvncommendService svncommendservice;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * @param request
	 * @return
	*TODO 查询列表
	 */
	@Proxy(method="querylist", inarg="SvncommendQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncommend>>")
	@ResponseBody
	@RequestMapping("/querylist")
	public ReturnValueDomain<PageDomain<DopsSvncommend>> querylist(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncommend>> ret = new ReturnValueDomain<PageDomain<DopsSvncommend>>();
		SvncommendQueryDomain svncommendDomain = null;
		try {
			svncommendDomain = JsonUtil.request2Domain(request, SvncommendQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncommendservice.querylist(svncommendDomain);
	}
	/**
	 * @param request
	 * @return
	*TODO 查询详情
	 */
	@Proxy(method="querydetail", inarg="SvncommendQueryDomain", outarg="ReturnValueDomain<DopsSvncommend>")
	@ResponseBody
	@RequestMapping("/querydetail")
	public ReturnValueDomain<DopsSvncommend> querydetail(HttpServletRequest request) {
		ReturnValueDomain<DopsSvncommend> ret = new ReturnValueDomain<DopsSvncommend>();
		SvncommendQueryDomain svncommendDomain = null;
		try {
			svncommendDomain = JsonUtil.request2Domain(request, SvncommendQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncommendservice.querydetail(svncommendDomain);
	}
	/**
	 * @param request
	 * @return
	*TODO 推荐代码
	 */
	@Proxy(method="create", inarg="SvncommendListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/create")
	public ReturnValueDomain<String> create(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommendListDomain svncommendListDomain = null;
		 
		try {
			svncommendListDomain = JsonUtil.request2Domain(request, SvncommendListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("添加评审小组信息失败");
		}
		return svncommendservice.create(svncommendListDomain);
	 }
	
	/**
	 * @param request
	 * @return
	*TODO 查询是否是评审人员以及剩余投票次数
	 */
	@Proxy(method="querynumber", inarg="SvncommendQueryDomain", outarg="ReturnValueDomain<DopsSvncommend>")
	@ResponseBody
	@RequestMapping("/querynumber")
	public ReturnValueDomain<DopsSvncommend> querynumber(HttpServletRequest request) {
		ReturnValueDomain<DopsSvncommend> ret = new ReturnValueDomain<DopsSvncommend>();
		SvncommendQueryDomain svncommendDomain = null;
		try {
			svncommendDomain = JsonUtil.request2Domain(request, SvncommendQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncommendservice.querynumber(svncommendDomain);
	}
	/**
	 * @param request
	 * @return 
	*TODO 投票
	 */
	@Proxy(method="poll", inarg="SvncommendListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/poll")
	public ReturnValueDomain<String> poll(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommendListDomain svncommendlistdomain = null;
		  
		try {
			svncommendlistdomain = JsonUtil.request2Domain(request, SvncommendListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("更新评审小组信息失败");
		}

		List<DopsSvncommend> dopssvncommendlist= svncommendlistdomain.getDopssvncommendlist();
		return svncommendservice.poll(svncommendlistdomain);
	}
}
