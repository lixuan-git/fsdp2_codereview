package cn.finedo.codereview.svnlog.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvnlog;

/**      
* @Description: svn日志信息实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 下午5:56:42   
* @version v1.0
*   
*/ 
public class SvnlogListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvnlog> dopssvnloglist=new ArrayList<DopsSvnlog>();

	public List<DopsSvnlog> getDopssvnloglist() {
		return dopssvnloglist;
	}

	public void setDopssvnloglist(List<DopsSvnlog> dopssvnloglist) {
		this.dopssvnloglist = dopssvnloglist;
	}
}

