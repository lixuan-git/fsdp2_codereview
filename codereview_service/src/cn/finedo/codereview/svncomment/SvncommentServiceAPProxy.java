/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2018-05-02
 */
package cn.finedo.codereview.svncomment;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.TypeReference;

import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.svncomment.SvncommentServiceAP;
import cn.finedo.codereview.svncomment.domain.SvncommentListDomain;
import cn.finedo.codereview.svncomment.domain.SvncommentQueryDomain;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

public class SvncommentServiceAPProxy {
	private SvncommentServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsSvncomment>> query(SvncommentQueryDomain arg) {
		String apuri="service/finedo/svncomment/query";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.query(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncomment>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncomment>>>() {});
			return ret;
		}
	}
	
   public static ReturnValueDomain<PageDomain<DopsSvncomment>> queryforcount(SvncommentQueryDomain arg) {
        String apuri="service/finedo/svncomment/queryforcount";
        
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token=getToken(request);
                
        if(ContextUtil.isLocalUri(request, apuri)) {
            SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
            
            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryforcount(requestwrapper);
        }else {
            // 跨域调用，网络调用
            ReturnValueDomain<PageDomain<DopsSvncomment>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncomment>>>() {});
            return ret;
        }
    }
	

	public static ReturnValueDomain<String> add(SvncommentListDomain arg) {
		String apuri="service/finedo/svncomment/add";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.add(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
   public static ReturnValueDomain<String> addcommentforcount(SvncommentListDomain arg) {
        String apuri="service/finedo/svncomment/addcommentforcount";
        
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token=getToken(request);
                
        if(ContextUtil.isLocalUri(request, apuri)) {
            SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
            
            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
            return serviceap.addcommentforcount(requestwrapper);
        }else {
            // 跨域调用，网络调用
            ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
            return ret;
        }
    }
	
	public static ReturnValueDomain<String> update(SvncommentListDomain arg) {
		String apuri="service/finedo/svncomment/update";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.update(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> updateNoAuth(SvncommentListDomain arg) {
		String apuri="service/finedo/svncomment/updateNoAuth";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.update(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> delete(SvncommentListDomain arg) {
		String apuri="service/finedo/svncomment/delete";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommentServiceAP serviceap = BeanUtil.getBean("svncommentServiceAP", SvncommentServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.update(requestwrapper);
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
