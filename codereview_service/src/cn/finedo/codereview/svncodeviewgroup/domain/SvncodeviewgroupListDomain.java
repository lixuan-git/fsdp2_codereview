/**
 * 评审小组表列表领域对象
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.svncodeviewgroup.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;

public class SvncodeviewgroupListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 评审小组表list
	private List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist=new ArrayList<DopsSvncodeviewgroup>();

	public List<DopsSvncodeviewgroup> getDopssvncodeviewgrouplist() {
		return dopssvncodeviewgrouplist;
	}

	public void setDopssvncodeviewgrouplist(List<DopsSvncodeviewgroup> dopssvncodeviewgrouplist) {
		this.dopssvncodeviewgrouplist = dopssvncodeviewgrouplist;
	}
}
