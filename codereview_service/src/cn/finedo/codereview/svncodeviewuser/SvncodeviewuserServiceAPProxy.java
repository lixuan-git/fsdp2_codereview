package cn.finedo.codereview.svncodeviewuser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.TypeReference;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.codereview.svncodeviewuser.domain.SvncodeviewuserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

public class SvncodeviewuserServiceAPProxy {
	private SvncodeviewuserServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> query(SvncodeviewuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewuser/query";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewuserServiceAP serviceap = BeanUtil.getBean("svncodeviewuserServiceAP", SvncodeviewuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.query(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewuser>>>() {});
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
