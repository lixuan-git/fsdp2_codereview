package cn.finedo.codereview.svnpathtype;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvnpathtype;
import cn.finedo.codereview.svnpathtype.SvnpathtypeService;
import cn.finedo.codereview.svnpathtype.domain.SvnpathtypeListDomain;
import cn.finedo.codereview.svnpathtype.domain.SvnpathtypeQueryDomain;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svnpathtype")
public class SvnpathtypeServiceAP {
	private static Logger logger = LogManager.getLogger();

	@Autowired
	private SvnpathtypeService svnpathtypeservice;

	/**
	 * svn项目类型表查询
	 * 
	 * @param SvnpathtypeQueryDomain
	 * @return ReturnValueDomain<DopsSvnpathtype>对象
	 */
	@Proxy(method = "query", inarg = "SvnpathtypeQueryDomain", outarg = "ReturnValueDomain<PageDomain<DopsSvnpathtype>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<DopsSvnpathtype>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvnpathtype>> ret = new ReturnValueDomain<PageDomain<DopsSvnpathtype>>();
		SvnpathtypeQueryDomain svnpathtypequerydomain = null;

		try {
			svnpathtypequerydomain = JsonUtil.request2Domain(request, SvnpathtypeQueryDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		return svnpathtypeservice.query(svnpathtypequerydomain);
	}

	/**
	 * svn项目类型表新增
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method = "add", inarg = "SvnpathtypeListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvnpathtypeListDomain svnpathtypelistdomain = null;

		try {
			svnpathtypelistdomain = JsonUtil.request2Domain(request, SvnpathtypeListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		return svnpathtypeservice.add(svnpathtypelistdomain);
	}

	/**
	 * svn项目类型表修改
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method = "update", inarg = "SvnpathtypeListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvnpathtypeListDomain svnpathtypelistdomain = null;

		try {
			svnpathtypelistdomain = JsonUtil.request2Domain(request, SvnpathtypeListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		LoginDomain login = TokenUtil.query(TokenUtil.parsetoken(request));

		return svnpathtypeservice.update(svnpathtypelistdomain, login);
	}
}
