/**
 * 
 */
package cn.finedo.codereview.svncodeviewgroupuser;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.TypeReference;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupServiceAP;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewgroupuser.domain.SvncodeviewgroupuserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

public class SvncodeviewgroupuserServiceAPProxy {
	private SvncodeviewgroupuserServiceAPProxy() {
	}
	
	
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> querynum(SvncodeviewgroupuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroupuser/querynum";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupuserServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupuserServiceAP", SvncodeviewgroupuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querynum(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>>() {});
			return ret;
		}
	}
	
	
	public static ReturnValueDomain<String> delete(SvncodeviewgroupuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroupuser/delete";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupuserServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupuserServiceAP", SvncodeviewgroupuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.delete(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	
	
	public static ReturnValueDomain<String> returnback(SvncodeviewgroupuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroupuser/returnback";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupuserServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupuserServiceAP", SvncodeviewgroupuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.returnback(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	
	public static ReturnValueDomain<String> deletecodeviewperson(SvncodeviewgroupuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroupuser/deletecodeviewperson";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupuserServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupuserServiceAP", SvncodeviewgroupuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.deletecodeviewperson(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> queryuserbyname(SvncodeviewgroupuserQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroupuser/queryuserbyname";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupuserServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupuserServiceAP", SvncodeviewgroupuserServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.queryuserbyname(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>>() {});
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
