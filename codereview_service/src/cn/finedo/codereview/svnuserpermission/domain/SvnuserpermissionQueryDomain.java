package cn.finedo.codereview.svnuserpermission.domain;


import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;

/**      
* @Description: svnuserright查询实体类
* @author pt  
* @date 2018年6月7日   
* @version v1.0
*   
*/ 
public class SvnuserpermissionQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvnuserpermission dopssvnuserpermission = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvnuserpermission getDopssvnuserpermission() {
		return dopssvnuserpermission;
	}

	public void setDopssvnuserpermission(DopsSvnuserpermission dopssvnuserpermission) {
		this.dopssvnuserpermission = dopssvnuserpermission;
	}


	
}
