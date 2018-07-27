package cn.finedo.codereview.svncomment.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.codereview.common.pojo.DopsSvncomment;

/**      
* @Description: 评论查询实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:52:05   
* @version v1.0
*   
*/ 
public class SvncommentQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvncomment dopssvncomment = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvncomment getDopssvncomment() {
		return dopssvncomment;
	}

	public void setDopssvncomment(DopsSvncomment dopssvncomment) {
		this.dopssvncomment = dopssvncomment;
	}
}
