/**
 * 服务接口代理 <本代码为工具自动生成，不要修改>
 * 
 * @version 1.0
 * @since 2017-09-25
 */
package cn.finedo.codereview.svnmng;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.TypeReference;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.common.pojo.DopsSvnlog;
import cn.finedo.codereview.common.pojo.DopsSvnmng;
import cn.finedo.codereview.common.pojo.Treepojo;
import cn.finedo.codereview.entity.ReportParaEntity;
import cn.finedo.codereview.entity.SvnInfoEntity;
import cn.finedo.codereview.entity.SvnParaEntity;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncomment.domain.SvnProjectComment;
import cn.finedo.codereview.svnlog.domain.SvnlogProjectDomain;
import cn.finedo.codereview.svnmng.domain.SvnmngQueryDomain;
import cn.finedo.codereview.svnmng.domain.TreepojoListDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.http.HttpServletRequestWrapper;
import cn.finedo.fsdp.server.util.BeanUtil;
import cn.finedo.fsdp.server.util.ContextUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;


public class SvnmngServiceAPProxy {
    private SvnmngServiceAPProxy() {}

    public static ReturnValueDomain<PageDomain<DopsSvnmng>> query(SvnmngQueryDomain arg) {
        String apuri = "service/finedo/svnmng/query";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.query(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<PageDomain<DopsSvnmng>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvnmng>>>() {});
            return ret;
        }
    }
    
    public static ReturnValueDomain<PageDomain<DopsSvnmng>> queryForTree(SvnmngQueryDomain arg) {
        String apuri = "service/finedo/svnmng/queryForTree";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryForTree(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<PageDomain<DopsSvnmng>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvnmng>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<DopsSvncomment> queryText(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/queryText";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryText(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<DopsSvncomment> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncomment>>() {});
            return ret;
        }
    }
    
    public static ReturnValueDomain<DopsSvncomment> queryTextForTree(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/queryTextForTree";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryTextForTree(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<DopsSvncomment> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncomment>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlog(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/querySvnlog";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querySvnlog(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<SvnlogProjectDomain>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<SvnlogProjectDomain>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<Treepojo>> querySvnlogForTree(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/querySvnlogForTree";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querySvnlogForTree(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<Treepojo>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<Treepojo>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlogDetail(SvnInfoEntity arg) {
        String apuri = "service/finedo/svnmng/querySvnlogDetail";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querySvnlog(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<SvnlogProjectDomain>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<SvnlogProjectDomain>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<SvnProjectComment>> queryJoin(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/queryJoin";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryJoin(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<SvnProjectComment>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<SvnProjectComment>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<DopsSvncomment>> queryFileAllRevision(SvnParaEntity arg) {
        String apuri = "service/finedo/svnmng/queryFileAllRevision";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.queryFileAllRevision(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<DopsSvncomment>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<DopsSvncomment>>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<String> setCheckidForReport(ReportParaEntity arg) {
        String apuri = "service/finedo/svnmng/setCheckidForReport";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.setCheckidForReport(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
            return ret;
        }
    }
    
    public static ReturnValueDomain<DopsSvncodeviewgroupuserrole> querygroupuserrole(DopsSvncodeviewgroupuserrole arg) {
        String apuri = "service/finedo/svnmng/querygroupuserrole";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querygroupuserrole(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<DopsSvncodeviewgroupuserrole> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<DopsSvncodeviewgroupuserrole>>() {});
            return ret;
        }
    }

    public static ReturnValueDomain<List<Treepojo>> gettreenoderights(TreepojoListDomain arg) {
        String apuri = "service/finedo/svnmng/gettreenoderights";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.gettreenoderights(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<List<Treepojo>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<List<Treepojo>>>() {});
            return ret;
        }
    }
    
    public static ReturnValueDomain<PageDomain<DopsSvnlog>> querylatestrevision(SvncodeviewgroupQueryDomain arg) {
        String apuri = "service/finedo/svnmng/querylatestrevision";

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);

        if (ContextUtil.isLocalUri(request, apuri)) {
            SvnmngServiceAP serviceap = BeanUtil.getBean("svnmngServiceAP", SvnmngServiceAP.class);

            // 本域请求，对象调用
            HttpServletRequestWrapper requestwrapper = new HttpServletRequestWrapper(request, token, arg);
            return serviceap.querylatestrevision(requestwrapper);
        } else {
            // 跨域调用，网络调用
            ReturnValueDomain<PageDomain<DopsSvnlog>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<DopsSvnlog>>>() {});
            return ret;
        }
    }

    private static String getToken(HttpServletRequest request) {
        String token;
        LoginDomain logindomain = SessionUtil.getLoginDomain(request);
        if (NonUtil.isNotNon(logindomain)) {
            token = logindomain.getToken();
        } else {
            token = "";
        }

        return token;
    }
}
