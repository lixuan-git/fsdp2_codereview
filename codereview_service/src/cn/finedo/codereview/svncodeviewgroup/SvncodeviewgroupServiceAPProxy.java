/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2018-06-11
 */
package cn.finedo.codereview.svncodeviewgroup;

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
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.domain.FileImportResultDomain;

public class SvncodeviewgroupServiceAPProxy {
	private SvncodeviewgroupServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> query(SvncodeviewgroupQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/query";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.query(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>>() {});
			return ret;
		}
	}
	
	
	
	public static ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> querygroupbyusercode(SvncodeviewgroupQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/querygroupbyusercode";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querygroupbyusercode(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> add(SvncodeviewgroupListDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/add";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.add(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> update(SvncodeviewgroupListDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/update";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.update(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> delete(SvncodeviewgroupListDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/delete";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
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
		String apuri="service/finedo/svncodeviewgroup/importexcel";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.importexcel(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<FileImportResultDomain> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<FileImportResultDomain>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<SysEntityfile> exportexcel(SvncodeviewgroupQueryDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/exportexcel";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.exportexcel(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<SysEntityfile> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<SysEntityfile>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> updategroup(SvncodeviewgroupListDomain arg) {
		String apuri="service/finedo/svncodeviewgroup/updategroup";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.updategroup(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<DopsSvncodeviewgroup> querywholegroup(SvncodeviewgroupQueryDomain arg) {
        String apuri="service/finedo/svncodeviewgroup/querywholegroup";
        
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token=getToken(request);
                
        if(ContextUtil.isLocalUri(request, apuri)) {
            SvncodeviewgroupServiceAP serviceap = BeanUtil.getBean("svncodeviewgroupServiceAP", SvncodeviewgroupServiceAP.class);
            
            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querywholegroup(requestwrapper);
        }else {
            // 跨域调用，网络调用
            ReturnValueDomain<DopsSvncodeviewgroup> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncodeviewgroup>>() {});
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
