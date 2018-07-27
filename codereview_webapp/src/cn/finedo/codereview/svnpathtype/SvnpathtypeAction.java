package cn.finedo.codereview.svnpathtype;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.codereview.common.pojo.DopsSvnpathtype;
import cn.finedo.codereview.svnpathtype.SvnpathtypeServiceAPProxy;
import cn.finedo.codereview.svnpathtype.domain.SvnpathtypeListDomain;

@Controller
@Scope("singleton")
@RequestMapping("/finedo/svnpathtype")
public class SvnpathtypeAction {
	/**
	 * svn工程类型表修改
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Object modify(HttpServletRequest request, @RequestBody DopsSvnpathtype dopssvnpathtype) throws Exception {
		SvnpathtypeListDomain svnpathtypelistdomain = new SvnpathtypeListDomain();
		List<DopsSvnpathtype> dopssvnpathtypelist = new ArrayList<DopsSvnpathtype>();
		dopssvnpathtypelist.add(dopssvnpathtype);
		svnpathtypelistdomain.setDopssvnpathtypelist(dopssvnpathtypelist);
		ReturnValueDomain<String> ret = SvnpathtypeServiceAPProxy.update(svnpathtypelistdomain);
		return ret;
	}
}
