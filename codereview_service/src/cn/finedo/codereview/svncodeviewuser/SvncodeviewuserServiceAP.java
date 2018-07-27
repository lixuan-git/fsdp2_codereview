package cn.finedo.codereview.svncodeviewuser;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.codereview.svncodeviewuser.domain.SvncodeviewuserQueryDomain;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svncodeviewuser")
public class SvncodeviewuserServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvncodeviewuserService svncodeviewuserservice;
	
	/**
	 * svn代码评审用户表查询
	 * 
	 * @param SvncodeviewuserQueryDomain
	 * @return ReturnValueDomain<DopsSvncodeviewuser>对象
	 */
	@Proxy(method = "query", inarg = "SvncodeviewuserQueryDomain", outarg = "ReturnValueDomain<PageDomain<DopsSvncodeviewuser>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewuser>>();
		SvncodeviewuserQueryDomain svncodeviewuserquerydomain = null;

		try {
			svncodeviewuserquerydomain = JsonUtil.request2Domain(request, SvncodeviewuserQueryDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		return svncodeviewuserservice.query(svncodeviewuserquerydomain);
	}

}
