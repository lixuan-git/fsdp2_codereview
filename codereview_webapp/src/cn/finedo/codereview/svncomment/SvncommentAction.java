/**
 * svn文件评论信息表管理Action
 * 
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svncomment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.entity.SvnParaEntity;
import cn.finedo.codereview.svncomment.domain.SvncommentListDomain;
import cn.finedo.codereview.svncomment.domain.SvncommentQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;


/**
 * @Description: svn评审表
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年6月5日 下午6:34:01
 * @version v1.0
 */
@Controller
@Scope("singleton")
@RequestMapping("/finedo/svncomment")
public class SvncommentAction {
    private static Logger logger = LogManager.getLogger();

    /**
     * svn评论表新增
     * 
     * @param SvncommentListDomain
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request)
        throws Exception {
        DopsSvncomment dopssvncomment = FormUtil.request2Domain(request, DopsSvncomment.class);
        LoginDomain login = SessionUtil.getLoginDomain(request);
        dopssvncomment.setPersonid(login.getSysuser().getPersonid());
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);
        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.add(svncommentlistdomain);

        return ret;
    }

    /**
     * svn评论表新增
     */
    @RequestMapping("/addcomment")
    @ResponseBody
    public Object addcomment(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        LoginDomain login = SessionUtil.getLoginDomain(request);
        dopssvncomment.setPersonid(login.getSysuser().getPersonid());
        // 0标识未解决
        dopssvncomment.setFinishstate("0");
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);

        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.add(svncommentlistdomain);

        return ret;
    }
    
    /**
     * 评审覆盖率统计
     */
    @RequestMapping("/addcommentforcount")
    @ResponseBody
    public Object addcommentforcount(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        LoginDomain login = SessionUtil.getLoginDomain(request);
        dopssvncomment.setPersonid(login.getSysuser().getPersonid());
        // 0标识未解决
        dopssvncomment.setFinishstate("0");
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);

        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.addcommentforcount(svncommentlistdomain);

        return ret;
    }

    /**
     * svn评论表查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object query(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        PageParamDomain pageparam = null;
        SvncommentQueryDomain svncommentquerydomain = new SvncommentQueryDomain();
        svncommentquerydomain.setDopssvncomment(dopssvncomment);
        svncommentquerydomain.setPageparam(pageparam);

        ReturnValueDomain<PageDomain<DopsSvncomment>> ret = SvncommentServiceAPProxy.query(svncommentquerydomain);
        PageDomain<DopsSvncomment> page = ret.getObject();

        return page.getDatalist();
    }
    
    /**
     * svn评论表查询
     */
    @RequestMapping("/querycommentforcount")
    @ResponseBody
    public Object querycommentforcount(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        LoginDomain login = SessionUtil.getLoginDomain(request);
        dopssvncomment.setPersonid(login.getSysuser().getPersonid());
        PageParamDomain pageparam = null;
        SvncommentQueryDomain svncommentquerydomain = new SvncommentQueryDomain();
        svncommentquerydomain.setDopssvncomment(dopssvncomment);
        svncommentquerydomain.setPageparam(pageparam);

        ReturnValueDomain<PageDomain<DopsSvncomment>> ret = SvncommentServiceAPProxy.queryforcount(svncommentquerydomain);
        return ret;
    }

    /**
     * svn评论表修改
     */
    @RequestMapping("/modify")
    @ResponseBody
    public Object modify(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);
        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.update(svncommentlistdomain);
        return ret;
    }

    /**
     * svn评论表修改
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);
        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.delete(svncommentlistdomain);
        return ret;
    }

    /**
     * svn评论表修改，不认证用户。
     */
    @RequestMapping("/modifyNoAuth")
    @ResponseBody
    public Object modifyNoAuth(HttpServletRequest request, @RequestBody DopsSvncomment dopssvncomment)
        throws Exception {
        SvncommentListDomain svncommentlistdomain = new SvncommentListDomain();
        List<DopsSvncomment> dopssvncommentlist = new ArrayList<DopsSvncomment>();
        dopssvncommentlist.add(dopssvncomment);
        svncommentlistdomain.setDopssvncommentlist(dopssvncommentlist);
        ReturnValueDomain<String> ret = SvncommentServiceAPProxy.updateNoAuth(svncommentlistdomain);
        return ret;
    }

   
    /**
    * 日期处理
    * @author zhusf
    * @param @param request
    * @param @param svnParaEntity
    * @param @return
    * @param @throws Exception
    * @return Object
    */ 	
    @RequestMapping(value = "/handleTime", method = RequestMethod.POST)
    @ResponseBody
    public Object handleTime(HttpServletRequest request, @RequestBody SvnParaEntity svnParaEntity)
        throws Exception {

        ReturnValueDomain<List<String>> ret = new ReturnValueDomain<List<String>>();
        List<String> list = new ArrayList<String>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        // 1标识一天 7标识一周
        if ("7".equals(svnParaEntity.getStart())) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
        }
        else if ("1".equals(svnParaEntity.getStart())) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        String datenow = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
        list.add(date);
        list.add(datenow);
        return ret.setSuccess("", list);
    }

}
