/**
 * 服务接口代理
 * <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2018-06-20
 */
package cn.finedo.codereview.svncommend;

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
import cn.finedo.codereview.svncommend.domain.SvncommendQueryDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.codereview.svncommend.domain.SvncommendListDomain;

public class SvncommendServiceAPProxy {
	private SvncommendServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<DopsSvncommend>> querylist(SvncommendQueryDomain arg) {
		String apuri="service/finedo/svncommend/querylist";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommendServiceAP serviceap = BeanUtil.getBean("svncommendServiceAP", SvncommendServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querylist(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<PageDomain<DopsSvncommend>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvncommend>>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<DopsSvncommend> querydetail(SvncommendQueryDomain arg) {
		String apuri="service/finedo/svncommend/querydetail";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommendServiceAP serviceap = BeanUtil.getBean("svncommendServiceAP", SvncommendServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querydetail(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<DopsSvncommend> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncommend>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> create(SvncommendListDomain arg) {
		String apuri="service/finedo/svncommend/create";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommendServiceAP serviceap = BeanUtil.getBean("svncommendServiceAP", SvncommendServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.create(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<DopsSvncommend> querynumber(SvncommendQueryDomain arg) {
		String apuri="service/finedo/svncommend/querynumber";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommendServiceAP serviceap = BeanUtil.getBean("svncommendServiceAP", SvncommendServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.querynumber(requestwrapper);
		}else {
			// 跨域调用，网络调用
			ReturnValueDomain<DopsSvncommend> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncommend>>() {});
			return ret;
		}
	}
	
	public static ReturnValueDomain<String> poll(SvncommendListDomain arg) {
		String apuri="service/finedo/svncommend/poll";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token=getToken(request);
				
		if(ContextUtil.isLocalUri(request, apuri)) {
			SvncommendServiceAP serviceap = BeanUtil.getBean("svncommendServiceAP", SvncommendServiceAP.class);
			
			// 本域请求，对象调用
			HttpServletRequestWrapper requestwrapper=new HttpServletRequestWrapper(request, token, arg);
			return serviceap.poll(requestwrapper);
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
