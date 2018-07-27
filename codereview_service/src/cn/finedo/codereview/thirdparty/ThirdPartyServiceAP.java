package cn.finedo.codereview.thirdparty;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.entity.thirdparty.ProjectsEntity;
import cn.finedo.codereview.entity.thirdparty.UserEntity;
import cn.finedo.codereview.entity.thirdparty.UsersEntity;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/thirdparty/")
public class ThirdPartyServiceAP {
    private static Logger logger = LogManager.getLogger();
    
    @Autowired
    private ThirdPartyService thirdPartyService;
    
    @Proxy(method = "syncproject", inarg = "", outarg = "ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping(value = "syncproject", method = RequestMethod.POST)
    public ReturnValueDomain<String> syncproject(HttpServletRequest request) {
        ProjectsEntity projectsEntity =null;
        try {
            projectsEntity = JsonUtil.request2Domain(request, ProjectsEntity.class);
        }
        catch (Exception e) {
            logger.error("项目参数解析失败！",e);
            throw new RuntimeException(e);
        }
        return thirdPartyService.syncproject(projectsEntity);
    }
    
    @Proxy(method = "syncuser", inarg = "", outarg = "ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping(value = "syncuser", method = RequestMethod.POST)
    public ReturnValueDomain<String> syncuser(HttpServletRequest request) {
        UsersEntity usersEntity =null;
        try {
            usersEntity = JsonUtil.request2Domain(request, UsersEntity.class);
        }
        catch (Exception e) {
            logger.error("成员参数解析失败！",e);
            throw new RuntimeException(e);
        }
        return thirdPartyService.syncuser(usersEntity);
    }
    
}
