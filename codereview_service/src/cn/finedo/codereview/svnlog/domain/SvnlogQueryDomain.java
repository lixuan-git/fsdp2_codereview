package cn.finedo.codereview.svnlog.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.codereview.common.pojo.DopsSvnlog;

/**      
* @Description: svn日志信息查询实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 下午6:00:08   
* @version v1.0
*   
*/ 
public class SvnlogQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private PageParamDomain pageparam = null;
	
	private DopsSvnlog dopssvnlog = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public DopsSvnlog getDopssvnlog() {
		return dopssvnlog;
	}

	public void setDopssvnlog(DopsSvnlog dopssvnlog) {
		this.dopssvnlog = dopssvnlog;
	}
}

