package cn.finedo.codereview.svncodeviewuser;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.codereview.svncodeviewuser.SvncodeviewuserServiceAPProxy;
import cn.finedo.codereview.svncodeviewuser.domain.SvncodeviewuserQueryDomain;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svncodeviewuser")
public class SvncodeviewuserAction {
	private static Logger logger = LogManager.getLogger(); 

	/**
	 * svn用户权限表查询
	 * @param DopsSvncodeviewuser
	 * @param PageParamDomain
	 */
	@RequestMapping(value="/query",method = RequestMethod.POST)
	@ResponseBody
	public Object query(HttpServletRequest request,@RequestBody DopsSvncodeviewuser dopssvncodeviewuser) throws Exception {
		PageParamDomain pageparam=PageUtil.getPageParam(request);
		SvncodeviewuserQueryDomain svncodeviewuserquerydomain = new SvncodeviewuserQueryDomain();
		svncodeviewuserquerydomain.setDopssvncodeviewuser(dopssvncodeviewuser);
		svncodeviewuserquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> ret=SvncodeviewuserServiceAPProxy.query(svncodeviewuserquerydomain);
		PageDomain<DopsSvncodeviewuser> page = ret.getObject();
		
		return page.getDatalist();
	}
}
