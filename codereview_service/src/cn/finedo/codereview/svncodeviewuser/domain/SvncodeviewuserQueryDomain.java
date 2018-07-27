package cn.finedo.codereview.svncodeviewuser.domain;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;

/**      
* @Description: 评论评审用户表查询实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:52:05   
* @version v1.0
*   
*/ 
public class SvncodeviewuserQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvncodeviewuser dopssvncodeviewuser = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvncodeviewuser getDopssvncodeviewuser() {
		return dopssvncodeviewuser;
	}

	public void setDopssvncodeviewuser(DopsSvncodeviewuser dopssvncodeviewuser) {
		this.dopssvncodeviewuser = dopssvncodeviewuser;
	}
}

