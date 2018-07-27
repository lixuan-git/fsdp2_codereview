/**
 * svn管理信息表管理Action
 * 
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svnmng;

import java.util.ArrayList;
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

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.common.pojo.DopsSvnlog;
import cn.finedo.codereview.common.pojo.DopsSvnmng;
import cn.finedo.codereview.common.pojo.Treepojo;
import cn.finedo.codereview.entity.ReportParaEntity;
import cn.finedo.codereview.entity.SvnInfoEntity;
import cn.finedo.codereview.entity.SvnParaEntity;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupServiceAPProxy;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewgroupuser.SvncodeviewgroupuserServiceAPProxy;
import cn.finedo.codereview.svncodeviewgroupuser.domain.SvncodeviewgroupuserQueryDomain;
import cn.finedo.codereview.svncomment.domain.SvnProjectComment;
import cn.finedo.codereview.svnlog.domain.SvnlogProjectDomain;
import cn.finedo.codereview.svnmng.domain.SvnmngQueryDomain;
import cn.finedo.codereview.svnmng.domain.TreepojoListDomain;
import cn.finedo.codereview.util.KeyUtil;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.FormUtil;
import cn.finedo.common.sec.DES;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.webapp.common.utils.PageUtil;
import cn.finedo.svnagent.entity.SvnFile;
import cn.finedo.svnagent.util.SvnUtil;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svnmng")
public class SvnmngAction {
	private static Logger logger = LogManager.getLogger(); 
    
    /**   
    * @Title: readSvnFile   
    * @Description: 读取文件内容和文件信息  
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @param @throws Exception    设定文件   
    * @return ReturnValueDomain<DopsSvncomment>    返回类型   
    * @throws     
    */ 
    @RequestMapping(value="/readSvnFile",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<DopsSvncomment> readSvnFile(@RequestBody SvnParaEntity svnParaEntity, HttpServletRequest request) throws Exception{
    	LoginDomain login = SessionUtil.getLoginDomain(request) ;
		String usercode = login.getSysuser().getUsercode() ;
		svnParaEntity.setUsercode(usercode);
		ReturnValueDomain<DopsSvncomment> ret=SvnmngServiceAPProxy.queryText(svnParaEntity);
		return ret;
    }
    
    /**
    * 树状结构读取文件内容
    * @author zhusf
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @param @throws Exception
    * @return ReturnValueDomain<DopsSvncomment>
    */ 	
    @RequestMapping(value="/readSvnFileForTree",method = RequestMethod.POST)
    public @ResponseBody ReturnValueDomain<DopsSvncomment> readSvnFileForTree(@RequestBody SvnParaEntity svnParaEntity, HttpServletRequest request) throws Exception{
        LoginDomain login = SessionUtil.getLoginDomain(request) ;
        String usercode = login.getSysuser().getUsercode() ;
        svnParaEntity.setUsercode(usercode);
        ReturnValueDomain<DopsSvncomment> ret=SvnmngServiceAPProxy.queryTextForTree(svnParaEntity);
        return ret;
    }
    
    /**   
    * @Title: querySvnlog   
    * @Description: 查询用户的代码仓库日志   
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @param @throws Exception    设定文件   
    * @return ReturnValueDomain<List<SvnlogProjectDomain>>    返回类型   
    * @throws     
    */ 
    @RequestMapping(value="/querySvnlog",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlog(@RequestBody SvnParaEntity svnParaEntity,HttpServletRequest request) throws Exception{
		LoginDomain login = SessionUtil.getLoginDomain(request) ;
		String usercode = login.getSysuser().getUsercode() ;
		svnParaEntity.setUsercode(usercode);
		ReturnValueDomain<List<SvnlogProjectDomain>> ret=SvnmngServiceAPProxy.querySvnlog(svnParaEntity);
		return ret;
    }
    
    @RequestMapping(value="/querySvnlogDetail",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlog(@RequestBody SvnInfoEntity svnInfoEntity,HttpServletRequest request) throws Exception{
		LoginDomain login = SessionUtil.getLoginDomain(request) ;
		String usercode = login.getSysuser().getUsercode() ;
		svnInfoEntity.setUsercode(usercode);
		ReturnValueDomain<List<SvnlogProjectDomain>> ret=SvnmngServiceAPProxy.querySvnlogDetail(svnInfoEntity);
		return ret;
    }
    
    /**   
    * @Title: queryJoin   
    * @Description: 查询用户评论的和被评论的文件   
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @param @throws Exception    设定文件   
    * @return ReturnValueDomain<List<SvnProjectComment>>    返回类型   
    * @throws     
    */ 
    @RequestMapping(value="/queryJoin",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<List<SvnProjectComment>> queryJoin(@RequestBody SvnParaEntity svnParaEntity,HttpServletRequest request) throws Exception{
    	LoginDomain login = SessionUtil.getLoginDomain(request) ;
		String usercode = login.getSysuser().getUsercode() ;
		svnParaEntity.setUsercode(usercode);
		ReturnValueDomain<List<SvnProjectComment>> ret=SvnmngServiceAPProxy.queryJoin(svnParaEntity);
		return ret;
    }
    
    
    /**   
    * @Title: queryFileAllRevision   
    * @Description: 查询文件的所有版本 
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @param @throws Exception    设定文件   
    * @return ReturnValueDomain<List<DopsSvncomment>>    返回类型   
    * @throws     
    */ 
    @RequestMapping(value="/queryFileAllRevision",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<List<DopsSvncomment>> queryFileAllRevision(@RequestBody SvnParaEntity svnParaEntity,HttpServletRequest request) throws Exception{
    	LoginDomain login = SessionUtil.getLoginDomain(request);
		String usercode = login.getSysuser().getUsercode();
		svnParaEntity.setUsercode(usercode);
		ReturnValueDomain<List<DopsSvncomment>> ret=SvnmngServiceAPProxy.queryFileAllRevision(svnParaEntity);
		return ret;
    }
    
    /**
    * 报表接口缓存验证id
    * @author zhusf
    * @param @param svnParaEntity
    * @param @param request
    * @param @return
    * @return ReturnValueDomain<String>
    */ 	
    @RequestMapping(value="/setCheckidForReport",method = RequestMethod.POST)
	public @ResponseBody ReturnValueDomain<String> setCheckidForReport(@RequestBody ReportParaEntity reportParaEntity,HttpServletRequest request){
    	LoginDomain login = SessionUtil.getLoginDomain(request);
    	String usercode = login.getSysuser().getUsercode();
    	reportParaEntity.setUserid(usercode);
    	return SvnmngServiceAPProxy.setCheckidForReport(reportParaEntity);
    }
    
    /**
    * 查询登录用户是否有评审小组的权限
    * @author zhusf
    * @param @param groupuserrole
    * @param @param request
    * @param @return
    * @return ReturnValueDomain<DopsSvncodeviewgroupuserrole>
    */ 	
    @RequestMapping(value="/querygroupuserrole",method = RequestMethod.POST)
    public @ResponseBody ReturnValueDomain<DopsSvncodeviewgroupuserrole> querygroupuserrole(@RequestBody DopsSvncodeviewgroupuserrole groupuserrole,HttpServletRequest request){
        return SvnmngServiceAPProxy.querygroupuserrole(groupuserrole);
    }
    
    /**
    * 查询最近提交的文件
    * @author zhusf
    * @param @param request
    * @param @return
    * @param @throws Exception
    * @return Object
    */ 	
    @RequestMapping(value="/querylatestrevision",method = RequestMethod.POST)
    public @ResponseBody Object querylatestrevision(HttpServletRequest request) throws Exception{
        DopsSvncodeviewgroup group = FormUtil.request2Domain(request, DopsSvncodeviewgroup.class);
        PageParamDomain pageparam=PageUtil.getPageParam(request);
        SvncodeviewgroupQueryDomain codeviewgroupquerydomain=new SvncodeviewgroupQueryDomain();
        codeviewgroupquerydomain.setPageparam(pageparam);
        codeviewgroupquerydomain.setDopssvncodeviewgroup(group);
        ReturnValueDomain<PageDomain<DopsSvnlog>> ret=SvnmngServiceAPProxy.querylatestrevision(codeviewgroupquerydomain);
        PageDomain<DopsSvnlog> page = ret.getObject();
        return PageUtil.build(page.getDatalist(), page.getRowcount());
    }
    
    /**
    * 以树状显示的方式查询svn日志
    * @author zhusf
    * @param @param request
    * @param @return
    * @param @throws Exception
    * @return Object
    */ 	
    @RequestMapping("/querySvnlogForTree")
    @ResponseBody
    public Object querySvnlogForTree(HttpServletRequest request) throws Exception{
        ReturnValueDomain<List<Treepojo>> treeret = new ReturnValueDomain<List<Treepojo>>(); //构造返回结果
        //当前树的层级编号，从0开始
        String id = request.getParameter("id");
        //项目路径
        String svnpath = request.getParameter("svnpath");
        SvnmngQueryDomain queryDomain = new SvnmngQueryDomain();
        DopsSvnmng dopsSvnmngTmp = new DopsSvnmng();
        dopsSvnmngTmp.setSvnpath(svnpath);
        queryDomain.setDopssvnmng(dopsSvnmngTmp);
        if (NonUtil.isNotNon(id) && id.equals("0") && NonUtil.isNotNon(svnpath)) { //网页第一次加载，查询仓库
            //查询项目的仓库信息
            ReturnValueDomain<PageDomain<DopsSvnmng>> ret = SvnmngServiceAPProxy.queryForTree(queryDomain);
            List<DopsSvnmng> list= ret.getObject().getDatalist(); // 仓库的集合
            List<Treepojo> treelist = new ArrayList<Treepojo>();
            for (DopsSvnmng dops : list) {
                //设置根节点
                Treepojo tree = new Treepojo();
                tree.setId(dops.getSvnid());
                tree.setPid("0");
                tree.setName(dops.getSvnpath());
                //tree.setDirectoryUrl(dops.getSvnaddr());
                tree.setDirectoryUrl(dops.getSvnaddr().concat(svnpath.substring(1)));
                tree.setOrgnode(dops.getSvnid());
                //tree.setOptsn("/");
                tree.setOptsn(svnpath);
                tree.setTreelvl(1);
                tree.setHisid(dops.getSvnid());
                treelist.add(tree);
            }
            TreepojoListDomain tlistdomain = new TreepojoListDomain();
            //设置根节点
            tlistdomain.setTreelist(treelist);
            ReturnValueDomain<List<Treepojo>> qret = SvnmngServiceAPProxy.gettreenoderights(tlistdomain);
            if(qret.isFail())
                return qret;
            treelist = qret.getObject();
            for(Treepojo treenode : treelist){
                if(!"0".equals(treenode.getHisid())){
                    treenode.setName(treenode.getName());
                }
            }
            treeret.setSuccess(ret.getResultdesc(), treelist);
        } else {
            String pid = request.getParameter("id"); //点击的节点的父节点id
            String directoryurl = request.getParameter("directoryUrl");
            String svnid = request.getParameter("orgnode");
            //获取树的层级
            int treelvl = Integer.parseInt(request.getParameter("treelvl"));
            //获取最大层级
            int maxlvl = Integer.parseInt(request.getParameter("maxlvl"));
            List<Treepojo> treelist = new ArrayList<Treepojo>();
            if(treelvl >= maxlvl){
                return treeret.setSuccess("只能获取"+maxlvl+"级目录", treelist);
            }
            
            DopsSvnmng svnmng = new DopsSvnmng();
            svnmng.setSvnid(svnid);
            SvnmngQueryDomain querydomain = new SvnmngQueryDomain();
            querydomain.setDopssvnmng(svnmng);
            //找到仓库的管理员、密码和仓库根路径
            ReturnValueDomain<PageDomain<DopsSvnmng>> ret = SvnmngServiceAPProxy.query(querydomain);
            List<DopsSvnmng> list = ret.getObject().getDatalist(); // 仓库的集合
            DopsSvnmng dopsCurrent = list.get(0); //用户点击的仓库
            String repositoryUrl = dopsCurrent.getSvnaddr();
            directoryurl = directoryurl.replace(repositoryUrl, "");
            if(!directoryurl.startsWith("/"))
                directoryurl = "/"+directoryurl;
            List<SvnFile> viewFiles = SvnUtil.viewFiles(dopsCurrent.getMnguser(), new String(DES.decryptString(dopsCurrent.getMngpwd())), repositoryUrl, directoryurl);
            for (int i = 0; i < viewFiles.size(); i++) {
                Treepojo tree = new Treepojo();
                SvnFile svnFile = viewFiles.get(i);
                tree.setId(KeyUtil.getId());
                tree.setPid(pid);// 父節點
                tree.setName(svnFile.getName());
                if("file".equals(viewFiles.get(i).getKind())){
                    tree.setOpen("true");
                }
                //设置树的当前所在目录
                tree.setDirectoryUrl(svnFile.getDownloadPath());
                tree.setOrgnode(svnid);
                String optsn = svnFile.getDownloadPath().replace(repositoryUrl, "/");
                optsn = optsn.replace("//", "/");
                tree.setOptsn(optsn);
                //树的层级递增
                tree.setTreelvl(treelvl+1);
                tree.setHisid(svnid);
                treelist.add(tree);
            }
            
            TreepojoListDomain tlistdomain = new TreepojoListDomain();
            tlistdomain.setTreelist(treelist);
            ReturnValueDomain<List<Treepojo>> qret = SvnmngServiceAPProxy.gettreenoderights(tlistdomain);
            if(qret.isFail())
                return qret;
            //treelist为树的结构和数据
            treelist = qret.getObject();
            for(Treepojo treenode : treelist){
                if(!"0".equals(treenode.getHisid())){
                    treenode.setName(treenode.getName());
                }
            }
            treeret.setSuccess(ret.getResultdesc(), treelist);
        }
        return treeret;
    }
    
}
