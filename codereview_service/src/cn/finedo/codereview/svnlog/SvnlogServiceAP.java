/**
 * svn日志信息服务接口
 *
 * @version 1.0
 * @since 2018-05-02
 */
package cn.finedo.codereview.svnlog;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.ReturnValueDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svnlog/")
public class SvnlogServiceAP {
	private static Logger logger = LogManager.getLogger();

	@Autowired
	private SvnlogService svnlogservice;

	@Proxy(method = "syncsvnlog1", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("syncsvnlog1")
	public ReturnValueDomain<String> syncsvnlogone1(HttpServletRequest request) {
		return svnlogservice.syncsvnlogForDate();
	}

	@Proxy(method = "sendEmail", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("sendEmail")
	public ReturnValueDomain<String> sendEmail(HttpServletRequest request) {
		return svnlogservice.sendEmail();
	}
	
//    @Proxy(method = "syncforcount", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
//    @ResponseBody
//    @RequestMapping("syncforcount")
//    public ReturnValueDomain<String> syncforcount(HttpServletRequest request) {
//        return svnlogservice.syncforcount();
//    }
    
    @Proxy(method = "selfcheck", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping("selfcheck")
    public ReturnValueDomain<String> selfcheck(HttpServletRequest request) {
        return svnlogservice.selfcheck();
    }
//    
    @Proxy(method = "syncgroup", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping("syncgroup")
    public ReturnValueDomain<String> syncgroup(HttpServletRequest request) {
        return svnlogservice.syncgroup();
    }
////    
//    @Proxy(method = "syncgroupauth", inarg = "SvnlogListDomain", outarg = "ReturnValueDomain<String>")
//    @ResponseBody
//    @RequestMapping("syncgroupauth")
//    public ReturnValueDomain<String> syncgroupauth(HttpServletRequest request) {
//        return svnlogservice.syncgroupauth();
//    }

}
