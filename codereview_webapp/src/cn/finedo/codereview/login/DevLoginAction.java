package cn.finedo.codereview.login;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.finedo.common.cache.CacheUtil;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.server.framework.ServerConfigUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.server.framework.WebConfig;
import cn.finedo.fsdp.server.util.RequestUtil;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.user.domain.UserInfoDomain;
import cn.finedo.fsdp.service.user.domain.UserQueryDomain;
import cn.finedo.fsdp.webapp.common.DataIdentifier;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/codereview/login")
public class DevLoginAction {
	private static Logger logger = LogManager.getLogger();

	@RequestMapping("/auth")
	public String auth(HttpServletRequest request) throws Exception {
		
		// 加载用户信息, user tag标签需要使用
		Map<String, UserInfoDomain> userinfo=(Map<String, UserInfoDomain>)CacheUtil.query(DataIdentifier.USERINFO_KEY);
		if(NonUtil.isNon(userinfo)) {
			UserQueryDomain uqd=new UserQueryDomain();
			PageParamDomain pageparam=new PageParamDomain();
			pageparam.setRownumperpage(1000000);
			uqd.setPageparam(pageparam);
			ReturnValueDomain<PageDomain<UserInfoDomain>> userret=DevLoginServiceAPProxy.queryalluser(uqd);
			
			Map<String, UserInfoDomain> userinfomap=new HashMap<String, UserInfoDomain>();
			for(UserInfoDomain uid : userret.getObject().getDatalist()) {
				userinfomap.put(uid.getUser().getUserid(), uid);
			}
			
			CacheUtil.add(DataIdentifier.USERINFO_KEY, userinfomap);
		}
		
		
		AccountDomain accountdomain = FormUtil.request2Domain(request, AccountDomain.class);
		accountdomain.setIpaddr(RequestUtil.getRemoteIP(request));
		
		String portalpagevalue=ServerFeature.INDEXURI;
		String loginpagevalue=ServerFeature.LOGINURI;
					
		
		accountdomain.setSessionid(request.getParameter("sessionid"));
		ReturnValueDomain<LoginDomain> ret = DevLoginServiceAPProxy.auth(accountdomain);
		
		if (ret.hasFail()) {
			request.getSession().setAttribute("resultdesc",ret.getResultdesc());
			return "redirect:" + loginpagevalue;
		}
		LoginDomain loginDomain = ret.getObject();
		loginDomain.setLoginpage(loginpagevalue);
		
		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession(true);
		session.setAttribute(DataIdentifier.LOGINDOMAIN_KEY, loginDomain);
		TokenUtil.add(loginDomain.getToken(), loginDomain);
		return "redirect:" + portalpagevalue;
	}
	/**
	 * 登录页
	 */
	@RequestMapping("/loginindex")
	public String loginindex(HttpServletRequest request) throws Exception {
		return "forward:/codereview/login.jsp" ;
	}
	
	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) throws Exception {
		return "forward:/codereview/frame/index.jsp" ;
	}

	@RequestMapping("/tools")
	public String tools(HttpServletRequest request) throws Exception {
		return "forward:/codereview/frame/tools/tool.jsp" ;
	}

	@RequestMapping("/consolemng")
	public String consolemng(HttpServletRequest request) throws Exception {
		return "forward:/codereview/frame/consolemng.jsp" ;
	}

	@RequestMapping("/svnmng")
	public String svnmng(HttpServletRequest request) throws Exception {
		return "forward:/codereview/developService/msglist.jsp" ;
	}
}
