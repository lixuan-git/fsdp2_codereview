package cn.finedo.codereview.svncommend.domain;

import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.common.domain.PageParamDomain;

public class SvncommendQueryDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvncommend dopssvncommend = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvncommend getDopssvncommend() {
		return dopssvncommend;
	}

	public void setDopssvncommend(DopsSvncommend dopssvncommend) {
		this.dopssvncommend = dopssvncommend;
	}
}
