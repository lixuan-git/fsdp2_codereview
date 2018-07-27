/**
 * svn管理信息表查询领域对象
 *
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svnmng.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.codereview.common.pojo.DopsSvnmng;

public class SvnmngQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	// 用户信息
	private DopsSvnmng dopssvnmng = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvnmng getDopssvnmng() {
		return dopssvnmng;
	}

	public void setDopssvnmng(DopsSvnmng dopssvnmng) {
		this.dopssvnmng = dopssvnmng;
	}
}
