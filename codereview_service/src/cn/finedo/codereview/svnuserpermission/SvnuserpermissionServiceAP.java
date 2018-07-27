
package cn.finedo.codereview.svnuserpermission;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;


@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svnuserpermission")
public class SvnuserpermissionServiceAP {
	
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvnuserpermissionService svnuserrightservice;
	
	/**
	 * svnuserright表查询svnpath
	 * @param SvncodeviewgroupnumQueryDomain
	 * @return ReturnValueDomain<DopsSvnuserright>对象
	 */
	@Proxy(method = "querysvnpath", inarg = "DopsSvnuserpermission", outarg = "ReturnValueDomain<List<DopsSvnuserpermission>>")
	@ResponseBody
	@RequestMapping("/querysvnpath")
	public ReturnValueDomain<List<DopsSvnuserpermission>> querysvnpath(HttpServletRequest request) {
		ReturnValueDomain<List<DopsSvnuserpermission>> ret = new ReturnValueDomain<List<DopsSvnuserpermission>>();
		DopsSvnuserpermission dopssvn = new DopsSvnuserpermission();

		try {
			dopssvn = (DopsSvnuserpermission) JsonUtil.request2Domain(request, DopsSvnuserpermission.class);
		} catch (Exception e) {
			logger.error("json数据异常 ",e);
			return ret.setFail("查询svnuserright表信息失败");
		}

		return svnuserrightservice.querysvnpath(dopssvn);
	}
	
	


	/**
	 * 添加项目成员到codeviewperson表中
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月12日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	@Proxy(method="addgroupnum", inarg="DopsSvnuserpermission", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/addgroupnum")
	public ReturnValueDomain<String> addgroupnum(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		DopsSvnuserpermission svnuserright = null;
		
		try {
			svnuserright = JsonUtil.request2Domain(request, DopsSvnuserpermission.class);
		}catch(Exception e) {
			logger.error("json数据异常 ",e);
			return ret.setFail("添加小组信息失败");
		}
		
		return svnuserrightservice.addgroupnum(svnuserright);
	}
	
	
	/**
	 * 编辑操作时项目成员的添加
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月19日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	@Proxy(method="editaddgroupnum", inarg="DopsSvnuserpermission", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/editaddgroupnum")
	public ReturnValueDomain<String> editaddgroupnum(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		DopsSvnuserpermission svnuserright = null;
		
		try {
			svnuserright = JsonUtil.request2Domain(request, DopsSvnuserpermission.class);
		}catch(Exception e) {
			logger.error("json数据异常 ",e);
			return ret.setFail("编辑小组信息失败");
		}
		
		return svnuserrightservice.editaddgroupnum(svnuserright);
	}
}
