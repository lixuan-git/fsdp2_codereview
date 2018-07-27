/**
 * svn评论信息表管理服务接口
 *
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svncomment;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.svncomment.SvncommentService;
import cn.finedo.codereview.svncomment.domain.SvncommentListDomain;
import cn.finedo.codereview.svncomment.domain.SvncommentQueryDomain;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svncomment")
public class SvncommentServiceAP {
	private static Logger logger = LogManager.getLogger();

	@Autowired
	private SvncommentService svncommentservice;

	/**
	 * svn评论信息表查询
	 * 
	 * @param SvncommentQueryDomain
	 * @return ReturnValueDomain<DopsSvncomment>对象
	 */
	@Proxy(method = "query", inarg = "SvncommentQueryDomain", outarg = "ReturnValueDomain<PageDomain<DopsSvncomment>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<DopsSvncomment>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncomment>> ret = new ReturnValueDomain<PageDomain<DopsSvncomment>>();
		SvncommentQueryDomain svncommentquerydomain = null;

		try {
			svncommentquerydomain = JsonUtil.request2Domain(request, SvncommentQueryDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		return svncommentservice.query(svncommentquerydomain);
	}
	
	   /**
     * svn评论信息表查询 覆盖率统计
     * 
     * @param SvncommentQueryDomain
     * @return ReturnValueDomain<DopsSvncomment>对象
     */
    @Proxy(method = "queryforcount", inarg = "SvncommentQueryDomain", outarg = "ReturnValueDomain<PageDomain<DopsSvncomment>>")
    @ResponseBody
    @RequestMapping("/queryforcount")
    public ReturnValueDomain<PageDomain<DopsSvncomment>> queryforcount(HttpServletRequest request) {
        ReturnValueDomain<PageDomain<DopsSvncomment>> ret = new ReturnValueDomain<PageDomain<DopsSvncomment>>();
        SvncommentQueryDomain svncommentquerydomain = null;

        try {
            svncommentquerydomain = JsonUtil.request2Domain(request, SvncommentQueryDomain.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }

        return svncommentservice.queryforcount(svncommentquerydomain);
    }

	/**
	 * svn评论信息表新增
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method = "add", inarg = "SvncommentListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommentListDomain svncommentlistdomain = null;

		try {
			svncommentlistdomain = JsonUtil.request2Domain(request, SvncommentListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		return svncommentservice.add(svncommentlistdomain);
	}
	
    @Proxy(method = "addcommentforcount", inarg = "SvncommentListDomain", outarg = "ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping("/addcommentforcount")
    public ReturnValueDomain<String> addcommentforcount(HttpServletRequest request) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        SvncommentListDomain svncommentlistdomain = null;

        try {
            svncommentlistdomain = JsonUtil.request2Domain(request, SvncommentListDomain.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        return svncommentservice.addcommentforcount(svncommentlistdomain);
    }

	/**
	 * svn评论信息表修改
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method = "update", inarg = "SvncommentListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommentListDomain svncommentlistdomain = null;

		try {
			svncommentlistdomain = JsonUtil.request2Domain(request, SvncommentListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		LoginDomain login = TokenUtil.query(TokenUtil.parsetoken(request));

		return svncommentservice.update(svncommentlistdomain, login);
	}
	
	/**
	 * svn评论信息表修改
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method = "delete", inarg = "SvncommentListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommentListDomain svncommentlistdomain = null;

		try {
			svncommentlistdomain = JsonUtil.request2Domain(request, SvncommentListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		LoginDomain login = TokenUtil.query(TokenUtil.parsetoken(request));

		return svncommentservice.delete(svncommentlistdomain, login);
	}

	/**
	 * svn评论信息表修改
	 * 
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method = "updateNoAuth", inarg = "SvncommentListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/updateNoAuth")
	public ReturnValueDomain<String> updateNoAuth(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncommentListDomain svncommentlistdomain = null;

		try {
			svncommentlistdomain = JsonUtil.request2Domain(request, SvncommentListDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		LoginDomain login = TokenUtil.query(TokenUtil.parsetoken(request));

		return svncommentservice.updateNoAuth(svncommentlistdomain, login);
	}

}
