/**
 * 评审成员表管理服务接口
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember;

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
import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.projectmember.domain.ProjectmemberListDomain;
import cn.finedo.codereview.projectmember.domain.ProjectmemberQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svncodeviewperson")
public class ProjectmemberServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private ProjectmemberService svncodeviewpersonservice;
	
	@Autowired
	private FileService fileService;
	
	
	/**
	 * 评审成员表查询
	 * @param ProjectmemberQueryDomain
	 * @return ReturnValueDomain<DopsProjectMember>对象
	 */
	@Proxy(method="query", inarg="ProjectMemberQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsProjectMember>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<DopsProjectmember>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsProjectmember>> ret = new ReturnValueDomain<PageDomain<DopsProjectmember>>();
		ProjectmemberQueryDomain svncodeviewpersonquerydomain = null;
		 
		try {
			svncodeviewpersonquerydomain = JsonUtil.request2Domain(request, ProjectmemberQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据异常",e);
			return ret.setFail("查询评审成员表失败");
		}
		
		return svncodeviewpersonservice.query(svncodeviewpersonquerydomain);
	}
	 
	/**
	 * 评审成员表新增
	 * 
	 * @param ProjectmemberListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="ProjectMemberListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		ProjectmemberListDomain svncodeviewpersonlistdomain = null;
		 
		try {
			svncodeviewpersonlistdomain = JsonUtil.request2Domain(request, ProjectmemberListDomain.class);
		}catch(Exception e) {
			logger.error("json数据异常",e);
			return ret.setFail("新增评审成员信息失败");
		}
	
		List<DopsProjectmember> dopssvncodeviewpersonlist= svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("usercode", "用户编号", true, DataType.STRING));
		items.add(new ValidateItem("cgid", "评审小组表主键", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewpersonlist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return svncodeviewpersonservice.add(svncodeviewpersonlistdomain);
	 }

	/**
	 * 评审成员表修改
	 * @param ProjectmemberListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="ProjectMemberListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		ProjectmemberListDomain svncodeviewpersonlistdomain = null;
		  
		try {
			svncodeviewpersonlistdomain = JsonUtil.request2Domain(request, ProjectmemberListDomain.class);
		}catch(Exception e) {
			logger.error("json数据异常",e);
			return ret.setFail("编辑评审成员表失败");
		}

		List<DopsProjectmember> dopssvncodeviewpersonlist= svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("cpid", "评审人员表主键", true, DataType.STRING));
		items.add(new ValidateItem("usercode", "用户编号", true, DataType.STRING));
		items.add(new ValidateItem("cgid", "评审小组表主键", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewpersonlist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return svncodeviewpersonservice.update(svncodeviewpersonlistdomain);
	}
	
	/**
	 * 评审成员表删除
	 * 
	 * @param ProjectmemberListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="ProjectMemberListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		ProjectmemberListDomain svncodeviewpersonlistdomain = null;
		
		try {
			svncodeviewpersonlistdomain = JsonUtil.request2Domain(request, ProjectmemberListDomain.class);
		}catch(Exception e) {
			logger.error("json数据异常",e);
			return ret.setFail("删除评审成员表失败");
		}
		
		List<DopsProjectmember> dopssvncodeviewpersonlist= svncodeviewpersonlistdomain.getDopssvncodeviewpersonlist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("cpid", "评审人员表主键", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(dopssvncodeviewpersonlist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return svncodeviewpersonservice.delete(svncodeviewpersonlistdomain);
	}
	
	/**
	 * 批量导入评审成员表
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
			logger.error("json数据异常",e);
			return ret.setFail("批量导入评审成员表失败");
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
		List<DopsProjectmember> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "usercode", "用户编号"));
			headerlist.add(new HeaderDomain("1", "cgid", "评审小组表主键"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, DopsProjectmember.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("usercode", "用户编号", true, DataType.STRING));
			items.add(new ValidateItem("cgid", "评审小组表主键", true, DataType.STRING));
			
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
		
		ProjectmemberListDomain svncodeviewpersonlistdomain=new ProjectmemberListDomain();
		svncodeviewpersonlistdomain.setDopssvncodeviewpersonlist(datalist);
		ReturnValueDomain<String> oneret= svncodeviewpersonservice.add(svncodeviewpersonlistdomain);
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
	  * @param ProjectmemberQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="ProjectMemberQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		ProjectmemberQueryDomain svncodeviewpersonquerydomain = null;
		try {
			svncodeviewpersonquerydomain = JsonUtil.request2Domain(request, ProjectmemberQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据异常",e);
			return ret.setFail("批量导出用户信息失败");
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		svncodeviewpersonquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<DopsProjectmember>> queryret = svncodeviewpersonservice.query(svncodeviewpersonquerydomain);
		
		List<DopsProjectmember> Dopssvncodeviewpersonlist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "usercode", "用户编号"));
		headerlist.add(new HeaderDomain("1", "cgid", "评审小组表主键"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Dopssvncodeviewpersonlist);
		} catch (Exception e) {
			logger.error("生成excel文件失败", e);
			return ret.setFail("生成excel文件失败:" + e.getMessage());
		}
		
		SysEntityfile entityfile=new SysEntityfile();
		entityfile.setFilename(filename);
		entityfile.setFilepath(filepath);
		return ret.setSuccess("生成excel文件成功", entityfile);
	}
}
