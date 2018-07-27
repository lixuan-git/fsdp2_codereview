package cn.finedo.codereview.svncodeviewproject.domain;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;

public class SvncodeviewprojectDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	/**
	 * TODO 分页信息
	 */
	private PageParamDomain pageparam = null;
	/**
	 * TODO 项目信息
	 */
	private DopsSvncodeviewproject svncodeviewproject = null;
	public PageParamDomain getPageparam() {
		return pageparam;
	}
	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}
	public DopsSvncodeviewproject getSvncodeviewproject() {
		return svncodeviewproject;
	}
	public void setSvncodeviewproject(DopsSvncodeviewproject svncodeviewproject) {
		this.svncodeviewproject = svncodeviewproject;
	}
	
	
	
}
