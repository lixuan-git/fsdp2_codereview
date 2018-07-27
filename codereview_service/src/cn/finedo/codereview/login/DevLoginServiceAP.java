package cn.finedo.codereview.login;

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
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.fsdp.server.util.RequestUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.user.UserService;
import cn.finedo.fsdp.service.user.domain.UserInfoDomain;
import cn.finedo.fsdp.service.user.domain.UserQueryDomain;

@Controller
@Scope("singleton") 
@RequestMapping("/service/codereview/login")
public class DevLoginServiceAP {
	
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private DevLoginService devLoginService;
	
	@Autowired
	private UserService userService;
		
	@Proxy(method="auth", inarg="AccountDomain", outarg="ReturnValueDomain<LoginDomain>")
	@ResponseBody
	@RequestMapping("/auth")
	public ReturnValueDomain<LoginDomain> auth(HttpServletRequest request) {
		ReturnValueDomain<LoginDomain> ret = new ReturnValueDomain<LoginDomain>();
		AccountDomain accountdomain = null;

		try {
			accountdomain = JsonUtil.request2Domain(request, AccountDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		// 数据合法性检查
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("account", "账号", false, DataType.STRING));
		items.add(new ValidateItem("passwd", "密码", false, DataType.PASSWD));
		ReturnValueDomain<LoginDomain> validret = ValidateUtil.check(accountdomain, items);
		if (validret.hasFail()) {
			return validret;
		}

		// 调用OA单点登录
		ret=devLoginService.localauth(accountdomain);
		if (ret.hasFail()) {
			return ret;
		}
		
		LoginDomain logindomain = ret.getObject();
		String token = logindomain.getToken();
		TokenUtil.add(token, logindomain);
		return ret.setSuccess("登录成功", logindomain);		
	}
	
	@Proxy(method="queryalluser", inarg="UserQueryDomain", outarg="ReturnValueDomain<PageDomain<UserInfoDomain>>")
	@ResponseBody
	@RequestMapping("/queryalluser")
	public ReturnValueDomain<PageDomain<UserInfoDomain>> queryalluser(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<UserInfoDomain>> ret = new ReturnValueDomain<PageDomain<UserInfoDomain>>();
		
		UserQueryDomain userquerydomain = null;
		try {
			userquerydomain = JsonUtil.request2Domain(request, UserQueryDomain.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		ret = userService.query(userquerydomain);
				
		return ret;
	}
	
	@Proxy(method="authbytoken", inarg="AccountDomain", outarg="ReturnValueDomain<LoginDomain>")
	@ResponseBody
	@RequestMapping("/authbytoken")
	public ReturnValueDomain<LoginDomain> authbytoken(HttpServletRequest request) {
		ReturnValueDomain<LoginDomain> ret = new ReturnValueDomain<LoginDomain>();
		
		AccountDomain accountdomain = null;
		try {
			accountdomain = JsonUtil.request2Domain(request, AccountDomain.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
				
		ret = devLoginService.getoauser(accountdomain.getOatoken());
		
		if (ret.hasFail()) {
			return ret;
		}		
		
		LoginDomain logindomain = ret.getObject();
		String token = logindomain.getToken();
		TokenUtil.add(token, logindomain);
		
		return ret.setSuccess("登录成功", logindomain);		
	}
}
