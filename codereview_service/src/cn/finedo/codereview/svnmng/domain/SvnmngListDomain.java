/**
 * svn管理信息表列表领域对象
 *
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svnmng.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvnmng;

public class SvnmngListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// svn管理信息表list
	private List<DopsSvnmng> dopssvnmnglist=new ArrayList<DopsSvnmng>();

	public List<DopsSvnmng> getDopssvnmnglist() {
		return dopssvnmnglist;
	}

	public void setDopssvnmnglist(List<DopsSvnmng> dopssvnmnglist) {
		this.dopssvnmnglist = dopssvnmnglist;
	}
}
