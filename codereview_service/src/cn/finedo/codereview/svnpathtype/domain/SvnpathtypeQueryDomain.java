package cn.finedo.codereview.svnpathtype.domain;

import cn.finedo.codereview.common.pojo.DopsSvnpathtype;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;

/**      
* @Description: 项目类型查询实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:52:05   
* @version v1.0
*   
*/ 
public class SvnpathtypeQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvnpathtype dopssvnpathtype = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvnpathtype getDopssvnpathtype() {
		return dopssvnpathtype;
	}

	public void setDopssvnpathtype(DopsSvnpathtype dopssvnpathtype) {
		this.dopssvnpathtype = dopssvnpathtype;
	}
}
