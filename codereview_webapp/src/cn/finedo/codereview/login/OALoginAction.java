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
import cn.finedo.fsdp.server.framework.ServerConfigUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.server.framework.WebConfig;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.LoginServiceAPProxy;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.user.domain.UserInfoDomain;
import cn.finedo.fsdp.service.user.domain.UserQueryDomain;
import cn.finedo.fsdp.webapp.common.DataIdentifier;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/auth")
public class OALoginAction {
	private static Logger logger = LogManager.getLogger();

	private void initUser() {
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
	}
	
	@RequestMapping("/login")
	public String auth(HttpServletRequest request) throws Exception {
		initUser();
		
		String portalpagevalue=ServerFeature.INDEXURI;
		String loginpagevalue=ServerFeature.LOGINURI;
		
		String oatokenid=request.getParameter("tokenid");
		AccountDomain accountdomain=new AccountDomain();
		accountdomain.setOatoken(oatokenid);
	
		ReturnValueDomain<LoginDomain> ret = DevLoginServiceAPProxy.authbytoken(accountdomain);
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
	
	@RequestMapping("/todo")
	public String todo(HttpServletRequest request) throws Exception {
		initUser();
		String loginpagevalue=ServerFeature.LOGINURI;
		
		String tokenid = request.getParameter("tokenid");
		String redirect = request.getParameter("redirect");
		LoginDomain loginDomain = SessionUtil.getLoginDomain(request);
		if(NonUtil.isNotNon(loginDomain)){
			String token = loginDomain.getToken();
			if(!token.equals(tokenid)){
				try{
					LoginServiceAPProxy.quit();
					TokenUtil.delete(loginDomain.getToken());
				}catch(Exception e){}
				loginDomain = null;
				request.getSession().invalidate();
				logger.debug("登录操作员变化，重新登录!");
			}
		}
		if(NonUtil.isNon(loginDomain)){
			String oatokenid=request.getParameter("tokenid");
			AccountDomain accountdomain=new AccountDomain();
			accountdomain.setOatoken(oatokenid);
			ReturnValueDomain<LoginDomain> ret = DevLoginServiceAPProxy.authbytoken(accountdomain);
			if (ret.hasFail()) {
				request.getSession().setAttribute("resultdesc",ret.getResultdesc());
				return "redirect:" + loginpagevalue;
			}
			
			loginDomain = ret.getObject();
			loginDomain.setLoginpage(loginpagevalue);
			
			HttpSession session = request.getSession();
			session.invalidate();
			session = request.getSession(true);
			session.setAttribute(DataIdentifier.LOGINDOMAIN_KEY, loginDomain);
			TokenUtil.add(loginDomain.getToken(), loginDomain);
		}
		return "redirect:" + redirect;
	}
}
