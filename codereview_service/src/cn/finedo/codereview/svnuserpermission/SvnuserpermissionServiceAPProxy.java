/**
 * 
 */
package cn.finedo.codereview.svnuserpermission;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.TypeReference;
import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;


public class SvnuserpermissionServiceAPProxy {
	
	private SvnuserpermissionServiceAPProxy() {
	}
			
	public static ReturnValueDomain<List<DopsSvnuserpermission>> querysvnpath(DopsSvnuserpermission arg) {
		String apuri="service/finedo/svnuserpermission/querysvnpath";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvnuserpermissionServiceAP serviceap = BeanUtil.getBean("svnuserpermissionServiceAP", SvnuserpermissionServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querysvnpath(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<List<DopsSvnuserpermission>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<DopsSvnuserpermission>>>() {});
			return ret;
		}
	}
	
	
	
	
	public static ReturnValueDomain<String> addgroupnum(DopsSvnuserpermission arg) {
		String apuri="service/finedo/svnuserpermission/addgroupnum";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvnuserpermissionServiceAP serviceap = BeanUtil.getBean("svnuserpermissionServiceAP", SvnuserpermissionServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.addgroupnum(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	
	public static ReturnValueDomain<String> editaddgroupnum(DopsSvnuserpermission arg) {
		String apuri="service/finedo/svnuserpermission/editaddgroupnum";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvnuserpermissionServiceAP serviceap = BeanUtil.getBean("svnuserpermissionServiceAP", SvnuserpermissionServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.editaddgroupnum(requestwrapper);
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
