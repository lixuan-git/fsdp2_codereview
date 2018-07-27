/**
 * svn管理信息表管理服务接口
 *
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svnmng;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
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
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.fsdp.service.file.FileService;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svnmng")
public class SvnmngServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvnmngService svnmngservice;
	
	@Autowired
	private FileService fileService;
	
	   /**
     * svn管理信息表查询
     * @param SvnmngQueryDomain
     * @return ReturnValueDomain<DopsSvnmng>对象
     */
    @Proxy(method="query", inarg="SvnmngQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvnmng>>")
    @ResponseBody
    @RequestMapping("/query")
    public ReturnValueDomain<PageDomain<DopsSvnmng>> query(HttpServletRequest request) {
        ReturnValueDomain<PageDomain<DopsSvnmng>> ret = new ReturnValueDomain<PageDomain<DopsSvnmng>>();
        SvnmngQueryDomain svnmngquerydomain = null;
         
        try {
            svnmngquerydomain = JsonUtil.request2Domain(request, SvnmngQueryDomain.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        return svnmngservice.query(svnmngquerydomain);
    }
    
    @Proxy(method="queryForTree", inarg="SvnmngQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvnmng>>")
    @ResponseBody
    @RequestMapping("/queryForTree")
    public ReturnValueDomain<PageDomain<DopsSvnmng>> queryForTree(HttpServletRequest request) {
        ReturnValueDomain<PageDomain<DopsSvnmng>> ret = new ReturnValueDomain<PageDomain<DopsSvnmng>>();
        SvnmngQueryDomain svnmngquerydomain = null;
         
        try {
            svnmngquerydomain = JsonUtil.request2Domain(request, SvnmngQueryDomain.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        return svnmngservice.queryForTree(svnmngquerydomain);
    }
	
    @Proxy(method="gettreenoderights", inarg="TreepojoListDomain", outarg="ReturnValueDomain<List<Treepojo>>")
    @ResponseBody
    @RequestMapping("/gettreenoderights")
    public ReturnValueDomain<List<Treepojo>> gettreenoderights(HttpServletRequest request) {
        ReturnValueDomain<List<Treepojo>> ret = new ReturnValueDomain<List<Treepojo>>();
        TreepojoListDomain treelist = null;
        try {
            treelist = JsonUtil.request2Domain(request, TreepojoListDomain.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        return svnmngservice.gettreenoderights(treelist.getTreelist());
    }
    
    /**   
    * @Title: queryText   
    * @Description: 读取文件内容和文件相关信息   
    * @param @param request
    * @param @return    设定文件   
    * @return ReturnValueDomain<DopsSvncomment>    返回类型   
    * @throws     
    */ 
    @Proxy(method="queryText", inarg="SvnParaEntity", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/queryText")
	public ReturnValueDomain<DopsSvncomment> queryText(HttpServletRequest request) {
		ReturnValueDomain<DopsSvncomment> ret = new ReturnValueDomain<DopsSvncomment>();
		SvnParaEntity svnParaEntity = null;
		try {
			svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		return svnmngservice.queryText(svnParaEntity);
	}
    
    @Proxy(method="queryTextForTree", inarg="SvnParaEntity", outarg="ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping("/queryTextForTree")
    public ReturnValueDomain<DopsSvncomment> queryTextForTree(HttpServletRequest request) {
        ReturnValueDomain<DopsSvncomment> ret = new ReturnValueDomain<DopsSvncomment>();
        SvnParaEntity svnParaEntity = null;
        try {
            svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        return svnmngservice.queryTextForTree(svnParaEntity);
    }
    
    /**   
    * @Title: querySvnlog   
    * @Description: 查询用户的svn日志信息  
    * @param @param request
    * @param @return    设定文件   
    * @return ReturnValueDomain<List<SvnlogProjectDomain>>    返回类型   
    * @throws     
    */ 
    @Proxy(method="querySvnlog", inarg="SvnParaEntity", outarg="ReturnValueDomain<List<SvnlogProjectDomain>>")
	@ResponseBody
	@RequestMapping("/querySvnlog")
	public ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlog(HttpServletRequest request) {
		ReturnValueDomain<List<SvnlogProjectDomain>> ret = new ReturnValueDomain<List<SvnlogProjectDomain>>();
		SvnParaEntity svnParaEntity = null;
		try {
			svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		return svnmngservice.querySvnlog(svnParaEntity);
	}
    
    @Proxy(method="querySvnlogForTree", inarg="SvnParaEntity", outarg="ReturnValueDomain<List<SvnlogProjectDomain>>")
    @ResponseBody
    @RequestMapping("/querySvnlogForTree")
    public ReturnValueDomain<List<Treepojo>> querySvnlogForTree(HttpServletRequest request) {
        ReturnValueDomain<List<Treepojo>> ret = new ReturnValueDomain<List<Treepojo>>();
        SvnParaEntity svnParaEntity = null;
        try {
            svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        //return svnmngservice.querySvnlogForTree(svnParaEntity);
        return null;
    }
    
    @Proxy(method="querySvnlogDetail", inarg="SvnParaEntity", outarg="ReturnValueDomain<List<SvnlogProjectDomain>>")
	@ResponseBody
	@RequestMapping("/querySvnlogDetail")
	public ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlogDetail(HttpServletRequest request) {
		ReturnValueDomain<List<SvnlogProjectDomain>> ret = new ReturnValueDomain<List<SvnlogProjectDomain>>();
		SvnInfoEntity svnInfoEntity = null;
		try {
			svnInfoEntity = JsonUtil.request2Domain(request, SvnInfoEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		return svnmngservice.querySvnlogDetail(svnInfoEntity);
	}
    
    
    /**   
    * @Title: queryJoin   
    * @Description: 查询用户被评论的和自己评论的。   
    * @param @param request
    * @param @return    设定文件   
    * @return ReturnValueDomain<List<SvnProjectComment>>    返回类型   
    * @throws     
    */ 
    @Proxy(method="queryJoin", inarg="SvnParaEntity", outarg="ReturnValueDomain<List<SvnProjectComment>>")
	@ResponseBody
	@RequestMapping("/queryJoin")
	public ReturnValueDomain<List<SvnProjectComment>> queryJoin(HttpServletRequest request) {
		ReturnValueDomain<List<SvnProjectComment>> ret = new ReturnValueDomain<List<SvnProjectComment>>();
		SvnParaEntity svnParaEntity = null;
		 
		try {
			svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		return svnmngservice.queryJoin(svnParaEntity);
	}
    
    
    /**   
    * @Title: queryFileAllRevision   
    * @Description: 查询文件的所有版本    
    * @param @param request
    * @param @return    设定文件   
    * @return ReturnValueDomain<List<DopsSvncomment>>    返回类型   
    * @throws     
    */ 
    @Proxy(method="queryFileAllRevision", inarg="SvnParaEntity", outarg="ReturnValueDomain<List<DopsSvncomment>>")
	@ResponseBody
	@RequestMapping("/queryFileAllRevision")
	public ReturnValueDomain<List<DopsSvncomment>> queryFileAllRevision(HttpServletRequest request) {
		ReturnValueDomain<List<DopsSvncomment>> ret = new ReturnValueDomain<List<DopsSvncomment>>();
		SvnParaEntity svnParaEntity = null;
		 
		try {
			svnParaEntity = JsonUtil.request2Domain(request, SvnParaEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		
		return svnmngservice.queryFileAllRevision(svnParaEntity);
	}
    
    @Proxy(method="validateUserForReport", inarg="ReportParaEntity", outarg="ReturnValueDomain<String>")
   	@ResponseBody
   	@RequestMapping("/validateuserforreport")
   	public ReturnValueDomain<String> validateUserForReport(HttpServletRequest request) {
    	ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
    	ReportParaEntity reportParaEntity = new ReportParaEntity();
    	reportParaEntity.setUserid(request.getParameter("userid"));
    	reportParaEntity.setValidatestr(request.getParameter("validatestr"));
//   		try {
//   			reportParaEntity = JsonUtil.request2Domain(request, ReportParaEntity.class);
//		}catch(Exception e) {
//			logger.error(e.getMessage());
//			return ret.setFail(e.getMessage());
//		}
   		return svnmngservice.validateUserForReport(reportParaEntity);
   	}
    
    @Proxy(method="setCheckidForReport", inarg="ReportParaEntity", outarg="ReturnValueDomain<String>")
   	@ResponseBody
   	@RequestMapping("/setCheckidForReport")
   	public ReturnValueDomain<String> setCheckidForReport(HttpServletRequest request) {
   		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
   		ReportParaEntity reportParaEntity = null;
   		try {
   			reportParaEntity = JsonUtil.request2Domain(request, ReportParaEntity.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
   		return svnmngservice.setCheckidForReport(reportParaEntity);
   	}
    
    @Proxy(method="querygroupuserrole", inarg="ReportParaEntity", outarg="ReturnValueDomain<String>")
    @ResponseBody
    @RequestMapping("/querygroupuserrole")
    public ReturnValueDomain<DopsSvncodeviewgroupuserrole> querygroupuserrole(HttpServletRequest request) {
        ReturnValueDomain<DopsSvncodeviewgroupuserrole> ret = new ReturnValueDomain<DopsSvncodeviewgroupuserrole>();
        DopsSvncodeviewgroupuserrole groupuserrole = null;
        try {
            groupuserrole = JsonUtil.request2Domain(request, DopsSvncodeviewgroupuserrole.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        return svnmngservice.querygroupuserrole(groupuserrole);
    }
    
    @Proxy(method="querylatestrevision", inarg="SvnmngQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvnmng>>")
    @ResponseBody
    @RequestMapping("/querylatestrevision")
    public ReturnValueDomain<PageDomain<DopsSvnlog>> querylatestrevision(HttpServletRequest request) {
        ReturnValueDomain<PageDomain<DopsSvnlog>> ret = new ReturnValueDomain<PageDomain<DopsSvnlog>>();
        SvncodeviewgroupQueryDomain svncodeviewgroupQueryDomain = null;
         
        try {
            svncodeviewgroupQueryDomain = JsonUtil.request2Domain(request, SvncodeviewgroupQueryDomain.class);
        }catch(Exception e) {
            logger.error(e.getMessage());
            return ret.setFail(e.getMessage());
        }
        
        return svnmngservice.querylatestrevision(svncodeviewgroupQueryDomain);
    }

}
