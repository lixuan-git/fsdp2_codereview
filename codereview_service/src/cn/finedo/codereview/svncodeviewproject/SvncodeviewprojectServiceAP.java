package cn.finedo.codereview.svncodeviewproject;

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
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupService;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;
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
@RequestMapping("service/finedo/svncodeviewproject")
public class SvncodeviewprojectServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvncodeviewprojectService svncodeviewprojectservice;
	
	@Autowired
	private FileService fileService;
	
	
	
	/**
	 * @param request
	 * @return
	*TODO 查询项目列表
	 */
	@Proxy(method="queryproject", inarg="SvncodeviewprojectDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncodeviewproject>>")
	@ResponseBody
	@RequestMapping("/queryproject")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> queryproject(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewproject>>();
		SvncodeviewprojectDomain svncodeviewprojectDomain = null;
		 
		try {
			svncodeviewprojectDomain = JsonUtil.request2Domain(request, SvncodeviewprojectDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncodeviewprojectservice.queryproject(svncodeviewprojectDomain);
	}
	
	
	/**
	 * @param request
	 * @return
	*TODO 创建项目
	 */
	@Proxy(method="create", inarg="SvncodeviewprojectListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/create")
	public ReturnValueDomain<String> create(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewprojectListDomain svncodeviewprojectListDomain = null;
		 
		try {
			svncodeviewprojectListDomain = JsonUtil.request2Domain(request, SvncodeviewprojectListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("添加评审小组信息失败");
		}
		return svncodeviewprojectservice.create(svncodeviewprojectListDomain);
	 }
	
	/**
	 * @param request
	 * @return
	*TODO 删除项目
	 */
	@Proxy(method="delete", inarg="SvncodeviewprojectListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewprojectListDomain svncodeviewprojectlistdomain = null;
		
		try {
			svncodeviewprojectlistdomain = JsonUtil.request2Domain(request, SvncodeviewprojectListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("删除评审小组信息失败");
		}
		
		List<DopsSvncodeviewproject> dopssvncodeviewprojectlist= svncodeviewprojectlistdomain.getDopssvncodeviewprojectlist();
		
		return svncodeviewprojectservice.delete(svncodeviewprojectlistdomain);
	}
	/**
	 * @param request
	 * @return
	*TODO 查询权限
	 */
	@Proxy(method="queryrole", inarg="SvncodeviewprojectDomain", outarg="ReturnValueDomain<DopsSvncodeviewproject>")
	@ResponseBody
	@RequestMapping("/queryrole")
	public ReturnValueDomain<DopsSvncodeviewproject> queryrole(HttpServletRequest request) {
		ReturnValueDomain<DopsSvncodeviewproject> ret = new ReturnValueDomain<DopsSvncodeviewproject>();
		SvncodeviewprojectDomain svncodeviewprojectDomain = null;
		 
		try {
			svncodeviewprojectDomain = JsonUtil.request2Domain(request, SvncodeviewprojectDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncodeviewprojectservice.queryrole(svncodeviewprojectDomain);
	}
		
	/**
	 * @param request
	 * @return
	*TODO 查询全局项目成员
	 */
	@Proxy(method="querycgid", inarg="SvncodeviewprojectDomain", outarg="ReturnValueDomain<DopsSvncodeviewproject>")
	@ResponseBody
	@RequestMapping("/querycgid")
	public ReturnValueDomain<DopsSvncodeviewproject> querycgid(HttpServletRequest request) {
		ReturnValueDomain<DopsSvncodeviewproject> ret = new ReturnValueDomain<DopsSvncodeviewproject>();
		SvncodeviewprojectDomain svncodeviewprojectDomain = null;
		 
		try {
			svncodeviewprojectDomain = JsonUtil.request2Domain(request, SvncodeviewprojectDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncodeviewprojectservice.querycgid(svncodeviewprojectDomain);
	}
	
	
	   @Proxy(method="modify", inarg="SvncodeviewprojectDomain", outarg="ReturnValueDomain<String>")
	    @ResponseBody
	    @RequestMapping("/modify")
	    public ReturnValueDomain<String> modify(HttpServletRequest request) {
	        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
	        DopsSvncodeviewproject dopsSvncodeviewproject = null;
	         
	        try {
	            dopsSvncodeviewproject = JsonUtil.request2Domain(request, DopsSvncodeviewproject.class);
	        }catch(Exception e) {
	            logger.error("json数据获取异常",e);
	            return ret.setFail("修改评审小组信息失败");
	        }
	        
	        return svncodeviewprojectservice.modify(dopsSvncodeviewproject);
	    }
}
