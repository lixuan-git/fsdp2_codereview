/**
 * 查询相关svnuserright信息
 */
package cn.finedo.codereview.svnuserpermission;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.FormUtil;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svnuserpermission")
public class SvnuserpermissionAction {
	private static Logger logger = LogManager.getLogger();
	
	
	/**
	 * 从svnuserright表中查询SVNpath信息
	 * @param request
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月7日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/querysvnpath")
	@ResponseBody
	public Object querysvnpath(HttpServletRequest request) throws Exception {
		DopsSvnuserpermission dopssvnuserright= FormUtil.request2Domain(request, DopsSvnuserpermission.class);
		ReturnValueDomain<List<DopsSvnuserpermission>> ret=SvnuserpermissionServiceAPProxy.querysvnpath(dopssvnuserright);
		return ret;
	}
	
	
	
	/**
	 * 添加项目成员到codeviewperson表中
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月12日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/addgroupnum")
	@ResponseBody
	public Object addgroupnum(HttpServletRequest request) throws Exception {
		DopsSvnuserpermission svnuserright = FormUtil.request2Domain(request, DopsSvnuserpermission.class);
		
		ReturnValueDomain<String>  ret=SvnuserpermissionServiceAPProxy.addgroupnum(svnuserright);
		
		return ret;
	}
	
	/**
	 * 编辑页面操作时项目成员的添加
	 * @param request
	 * @return
	 * @throws Exception
	 * @authro pt
	 * @date 2018年6月19日
	 * @return Object
	 * @version V1.0
	 */
	@RequestMapping("/editaddgroupnum")
	@ResponseBody
	public Object editaddgroupnum(HttpServletRequest request) throws Exception {
		DopsSvnuserpermission svnuserright = FormUtil.request2Domain(request, DopsSvnuserpermission.class);
		
		ReturnValueDomain<String>  ret=SvnuserpermissionServiceAPProxy.editaddgroupnum(svnuserright);
		
		return ret;
	}

}
