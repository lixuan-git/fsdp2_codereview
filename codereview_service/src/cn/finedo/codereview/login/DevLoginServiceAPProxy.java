/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2016-10-26
 */
package cn.finedo.codereview.login;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.TypeReference;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.fsdp.service.user.domain.UserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.fsdp.service.user.domain.UserInfoDomain;

public class DevLoginServiceAPProxy {
	private DevLoginServiceAPProxy() {
	}
			
	public static ReturnValueDomain<LoginDomain> auth(AccountDomain arg) {
		String apuri="service/codereview/login/auth";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			DevLoginServiceAP serviceap = BeanUtil.getBean("devLoginServiceAP", DevLoginServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.auth(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<LoginDomain> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<LoginDomain>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<PageDomain<UserInfoDomain>> queryalluser(UserQueryDomain arg) {
		String apuri="service/codereview/login/queryalluser";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			DevLoginServiceAP serviceap = BeanUtil.getBean("devLoginServiceAP", DevLoginServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.queryalluser(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<UserInfoDomain>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<UserInfoDomain>>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<LoginDomain> authbytoken(AccountDomain arg) {
		String apuri="service/codereview/login/authbytoken";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			DevLoginServiceAP serviceap = BeanUtil.getBean("devLoginServiceAP", DevLoginServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.authbytoken(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<LoginDomain> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<LoginDomain>>() {});
			return ret;
		}
	}
	
	private static String getToken(HttpServletRequest request) {
		String token;
		LoginDomain logindomain = SessionUtil.getLoginDomain(request);
		if(NonUtil.isNotNon(logindomain)) {
			token=logindomain.getToken();
		}else {
			token="";
		}
		
		return token;
	}
}
