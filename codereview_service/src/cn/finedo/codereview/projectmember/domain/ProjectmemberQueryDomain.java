/**
 * 评审成员表查询领域对象
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.codereview.common.pojo.DopsProjectmember;

public class ProjectmemberQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	// 用户信息
	private DopsProjectmember dopssvncodeviewperson = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsProjectmember getDopssvncodeviewperson() {
		return dopssvncodeviewperson;
	}

	public void setDopssvncodeviewperson(DopsProjectmember dopssvncodeviewperson) {
		this.dopssvncodeviewperson = dopssvncodeviewperson;
	}
}
