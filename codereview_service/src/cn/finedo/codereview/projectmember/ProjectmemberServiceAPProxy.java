/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember;

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
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.projectmember.domain.ProjectmemberListDomain;
import cn.finedo.codereview.projectmember.domain.ProjectmemberQueryDomain;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.domain.FileImportResultDomain;

public class ProjectmemberServiceAPProxy {
	private ProjectmemberServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsProjectmember>> query(ProjectmemberQueryDomain arg) {
		String apuri="service/finedo/svncodeviewperson/query";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.query(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsProjectmember>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsProjectmember>>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> add(ProjectmemberListDomain arg) {
		String apuri="service/finedo/svncodeviewperson/add";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.add(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> update(ProjectmemberListDomain arg) {
		String apuri="service/finedo/svncodeviewperson/update";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.update(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> delete(ProjectmemberListDomain arg) {
		String apuri="service/finedo/svncodeviewperson/delete";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.delete(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<FileImportResultDomain> importexcel(SysEntityfile arg) {
		String apuri="service/finedo/svncodeviewperson/importexcel";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.importexcel(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<FileImportResultDomain> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<FileImportResultDomain>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<SysEntityfile> exportexcel(ProjectmemberQueryDomain arg) {
		String apuri="service/finedo/svncodeviewperson/exportexcel";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			ProjectmemberServiceAP serviceap = BeanUtil.getBean("svncodeviewpersonServiceAP", ProjectmemberServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.exportexcel(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<SysEntityfile> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<SysEntityfile>>() {});
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
