package cn.finedo.codereview.svncodeviewgroupuser.domain;


import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.common.pojo.DopsSvnuserright;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;

/**      
* @Description: svnuserright查询实体类
* @author pt  
* @date 2018年6月7日   
* @version v1.0
*   
*/ 
public class SvncodeviewgroupuserQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvncodeviewgroupuser svncodeviewgroupuser = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvncodeviewgroupuser getSvncodeviewgroupuser() {
		return svncodeviewgroupuser;
	}

	public void setSvncodeviewgroupuser(DopsSvncodeviewgroupuser svncodeviewgroupuser) {
		this.svncodeviewgroupuser = svncodeviewgroupuser;
	}

	

	

	

	
}
