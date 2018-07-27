/**
 * 评审小组表管理服务接口
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.svncodeviewgroup;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.date.DateUtil;
import cn.finedo.common.domain.FileImportResultDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ResultDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.service.common.excel.ExcelUtil;
import cn.finedo.fsdp.service.common.excel.HeaderDomain;
import cn.finedo.fsdp.service.file.FileService;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svncodeviewgroup")
public class SvncodeviewgroupServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvncodeviewgroupService svncodeviewgroupservice;
	
	@Autowired
	private FileService fileService;
	
	
	/**
	 * 评审小组表查询
	 * @param ProjectQueryDomain
	 * @return ReturnValueDomain<DopsSvncodeviewgroup>对象
	 */
	@Proxy(method="query", inarg="SvncodeviewgroupQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>();
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = null;
		 
		try {
			svncodeviewgroupquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncodeviewgroupservice.query(svncodeviewgroupquerydomain);
	}
	
	/**
	 * 根据登录人的usercode查询其参与的项目评审小组
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月25日
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>
	 * @version V1.0
	 */
	@Proxy(method="querygroupbyusercode", inarg="SvncodeviewgroupQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>")
	@ResponseBody
	@RequestMapping("/querygroupbyusercode")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> querygroupbyusercode(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>>();
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = null;
		 
		try {
			svncodeviewgroupquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("查询评审小组信息失败");
		}
		
		return svncodeviewgroupservice.querygroupbyusercode(svncodeviewgroupquerydomain);
	}
	 
	
	/**
	 * 评审小组表新增
	 * 
	 * @param ProjectListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="SvncodeviewgroupListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = null;
		 
		try {
			svncodeviewgrouplistdomain = JsonUtil.request2Domain(request, SvncodeviewgroupListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("添加评审小组信息失败");
		}
	
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist= svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("groupname", "小组名称", true, DataType.STRING));
		items.add(new ValidateItem("svnaddr", "仓库地址", true, DataType.STRING));
		items.add(new ValidateItem("svnpath", "项目地址", true, DataType.STRING));
		items.add(new ValidateItem("repotype", "仓库类型", true, DataType.STRING));
		items.add(new ValidateItem("createperson", "创建人", true, DataType.STRING));
		items.add(new ValidateItem("optdate", "创建时间", true, DataType.DATE));
		items.add(new ValidateItem("groupdesc", "小组描述信息", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewgrouplist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return svncodeviewgroupservice.add(svncodeviewgrouplistdomain);
	 }

	/**
	 * 评审小组表修改
	 * @param ProjectListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="SvncodeviewgroupListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = null;
		  
		try {
			svncodeviewgrouplistdomain = JsonUtil.request2Domain(request, SvncodeviewgroupListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("更新评审小组信息失败");
		}

		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist= svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("cgid", "评审小组表主键", true, DataType.STRING));
		items.add(new ValidateItem("groupname", "小组名称", true, DataType.STRING));
		items.add(new ValidateItem("svnaddr", "仓库地址", true, DataType.STRING));
		items.add(new ValidateItem("svnpath", "项目地址", true, DataType.STRING));
		items.add(new ValidateItem("repotype", "仓库类型", true, DataType.STRING));
		items.add(new ValidateItem("createperson", "创建人", true, DataType.STRING));
		items.add(new ValidateItem("optdate", "创建时间", true, DataType.DATE));
		items.add(new ValidateItem("groupdesc", "小组描述信息", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewgrouplist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return svncodeviewgroupservice.update(svncodeviewgrouplistdomain);
	}
	
	/**
	 * 评审小组表删除
	 * 
	 * @param ProjectListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="SvncodeviewgroupListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain = null;
		
		try {
			svncodeviewgrouplistdomain = JsonUtil.request2Domain(request, SvncodeviewgroupListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("删除评审小组信息失败");
		}
		
		List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist= svncodeviewgrouplistdomain.getDopssvncodeviewgrouplist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("cgid", "评审小组表主键", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewgrouplist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return svncodeviewgroupservice.delete(svncodeviewgrouplistdomain);
	}
	
	/**
	 * 批量导入评审小组表
	 * 
	 * @param SysEntityfile
	 * @return ReturnValueDomain<FileImportResultDomain>对象
	 */
	@Proxy(method="importexcel", inarg="SysEntityfile", outarg="ReturnValueDomain<FileImportResultDomain>")
	@ResponseBody
	@RequestMapping(value="/importexcel")
	public ReturnValueDomain<FileImportResultDomain> importexcel(HttpServletRequest request) {
		ReturnValueDomain<FileImportResultDomain> ret=new ReturnValueDomain<FileImportResultDomain>();
		
		SysEntityfile entityfile = null;
		try {
			entityfile = JsonUtil.request2Domain(request, SysEntityfile.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("导入评审小组信息失败");
		}
		
		ReturnValueDomain<SysEntityfile> queryfileret=fileService.query(entityfile);
		entityfile=queryfileret.getObject();
		
		String filename=entityfile.getFilepath() + File.separator + entityfile.getFileid() + entityfile.getFiletype();
				
		// 总记录数
		int rowcount=0;
		// 成功记录数 
		int successcount=0;
		// 失败明细
		List<ResultDomain> faillist=new ArrayList<ResultDomain>();
		List<DopsSvncodeviewgroup> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "groupname", "小组名称"));
			headerlist.add(new HeaderDomain("1", "svnaddr", "仓库地址"));
			headerlist.add(new HeaderDomain("2", "svnpath", "项目地址"));
			headerlist.add(new HeaderDomain("3", "repotype", "仓库类型"));
			headerlist.add(new HeaderDomain("4", "createperson", "创建人"));
			headerlist.add(new HeaderDomain("5", "optdate", "创建时间"));
			headerlist.add(new HeaderDomain("6", "groupdesc", "小组描述信息"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, DopsSvncodeviewgroup.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("groupname", "小组名称", true, DataType.STRING));
			items.add(new ValidateItem("svnaddr", "仓库地址", true, DataType.STRING));
			items.add(new ValidateItem("svnpath", "项目地址", true, DataType.STRING));
			items.add(new ValidateItem("repotype", "仓库类型", true, DataType.STRING));
			items.add(new ValidateItem("createperson", "创建人", true, DataType.STRING));
			items.add(new ValidateItem("optdate", "创建时间", true, DataType.DATE));
			items.add(new ValidateItem("groupdesc", "小组描述信息", true, DataType.STRING));
			
			ReturnValueDomain<String> validret = ValidateUtil.checkForList(datalist, items);
			int failindex=0;
			for(ResultDomain rd : validret.getResultlist()) {
				rd.setResultdesc("[行号:" + failindex + 2 + "]" + rd.getResultdesc());
				faillist.add(rd);
				
				failindex++;
			}
			successcount=rowcount - failindex;
		}catch(Exception ex) {
			ex.printStackTrace();
			return ret.setFail("导入失败:" + ex.getMessage());
		}
		
		if(successcount != rowcount) {
			FileImportResultDomain importresult=new FileImportResultDomain();
			importresult.setRowcount(rowcount);
			importresult.setSuccesscount(successcount);
			importresult.setFailcount(rowcount-successcount);
			importresult.setFaillist(faillist);
						
			return ret.setFail("导入数据合法性校验不通过", importresult);
		}
		
		SvncodeviewgroupListDomain svncodeviewgrouplistdomain=new SvncodeviewgroupListDomain();
		svncodeviewgrouplistdomain.setDopssvncodeviewgrouplist(datalist);
		ReturnValueDomain<String> oneret= svncodeviewgroupservice.add(svncodeviewgrouplistdomain);
		if(oneret.hasFail()) {
			return ret.setFail("导入失败:" + oneret.getResultdesc());
		}
	
		FileImportResultDomain importresult=new FileImportResultDomain();
		importresult.setRowcount(rowcount);
		importresult.setSuccesscount(successcount);
		importresult.setFailcount(rowcount-successcount);
		importresult.setFaillist(faillist);
		
		return ret.setSuccess("导入成功", importresult);
	}
	
	 /**
	  * 批量导出用户信息
	  * 
	  * @param ProjectQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="SvncodeviewgroupQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		SvncodeviewgroupQueryDomain svncodeviewgroupquerydomain = null;
		try {
			svncodeviewgroupquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail(e.getMessage());
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		svncodeviewgroupquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroup>> queryret = svncodeviewgroupservice.query(svncodeviewgroupquerydomain);
		
		List<DopsSvncodeviewgroup> Dopssvncodeviewgrouplist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "groupname", "小组名称"));
		headerlist.add(new HeaderDomain("1", "svnaddr", "仓库地址"));
		headerlist.add(new HeaderDomain("2", "svnpath", "项目地址"));
		headerlist.add(new HeaderDomain("3", "repotype", "仓库类型"));
		headerlist.add(new HeaderDomain("4", "createperson", "创建人"));
		headerlist.add(new HeaderDomain("5", "optdate", "创建时间"));
		headerlist.add(new HeaderDomain("6", "groupdesc", "小组描述信息"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Dopssvncodeviewgrouplist);
		} catch (Exception e) {
			logger.error("生成excel文件失败", e);
			return ret.setFail("生成excel文件失败:" + e.getMessage());
		}
		
		SysEntityfile entityfile=new SysEntityfile();
		entityfile.setFilename(filename);
		entityfile.setFilepath(filepath);
		return ret.setSuccess("生成excel文件成功", entityfile);
	}
	
	
	@Proxy(method="updategroup", inarg="SvncodeviewgroupListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/updategroup")
	public ReturnValueDomain<String> updategroup(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupListDomain viewgrouplistdomain = null;
		 
		try {
			viewgrouplistdomain = JsonUtil.request2Domain(request, SvncodeviewgroupListDomain.class);
		}catch(Exception e) {
			logger.error("json数据获取异常",e);
			return ret.setFail("更新评审小组信息失败");
		}
	
		return svncodeviewgroupservice.updategroup(viewgrouplistdomain);
	 }
	
	   /**
     * 评审小组表查询
     * @param ProjectQueryDomain
     * @return ReturnValueDomain<DopsSvncodeviewgroup>对象
     */
    @Proxy(method="querywholegroup", inarg="SvncodeviewgroupQueryDomain", outarg="ReturnValueDomain<DopsSvncodeviewgroup>")
    @ResponseBody
    @RequestMapping("/querywholegroup")
    public ReturnValueDomain<DopsSvncodeviewgroup> querywholegroup(HttpServletRequest request) {
        return svncodeviewgroupservice.querywholegroup();
    }
}
