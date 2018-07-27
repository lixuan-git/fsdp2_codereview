/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2018-07-07
 */
package cn.finedo.codereview.svncodeviewproject;

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
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.svncodeviewproject.domain.SvncodeviewprojectListDomain;

public class SvncodeviewprojectServiceAPProxy {
	private SvncodeviewprojectServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> queryproject(SvncodeviewprojectDomain arg) {
		String apuri="service/finedo/svncodeviewproject/queryproject";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.queryproject(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewproject>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewproject>>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> create(SvncodeviewprojectListDomain arg) {
		String apuri="service/finedo/svncodeviewproject/create";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.create(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> delete(SvncodeviewprojectListDomain arg) {
		String apuri="service/finedo/svncodeviewproject/delete";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.delete(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<DopsSvncodeviewproject> queryrole(SvncodeviewprojectDomain arg) {
		String apuri="service/finedo/svncodeviewproject/queryrole";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.queryrole(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<DopsSvncodeviewproject> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncodeviewproject>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<DopsSvncodeviewproject> querycgid(SvncodeviewprojectDomain arg) {
		String apuri="service/finedo/svncodeviewproject/querycgid";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querycgid(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<DopsSvncodeviewproject> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncodeviewproject>>() {});
			return ret;
		}
	}
	
	   public static ReturnValueDomain<String> modify(DopsSvncodeviewproject arg) {
	        String apuri="service/finedo/svncodeviewproject/modify";
	        
	        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	        String token=getToken(request);
	                
	        if(ContextUtil.isLocalUri(request, apuri)) {
	            SvncodeviewprojectServiceAP serviceap = BeanUtil.getBean("svncodeviewprojectServiceAP", SvncodeviewprojectServiceAP.class);
	            
	            // 本域请求，对象调用
	            HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
	            return serviceap.modify(requestwrapper);
	        }else {
	            // 跨域调用，网络调用
	            ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
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
