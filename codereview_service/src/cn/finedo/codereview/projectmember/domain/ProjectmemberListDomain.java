/**
 * 评审成员表列表领域对象
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.projectmember.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsProjectmember;

public class ProjectmemberListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 评审成员表list
	private List<DopsProjectmember> dopssvncodeviewpersonlist=new ArrayList<DopsProjectmember>();

	public List<DopsProjectmember> getDopssvncodeviewpersonlist() {
		return dopssvncodeviewpersonlist;
	}

	public void setDopssvncodeviewpersonlist(List<DopsProjectmember> dopssvncodeviewpersonlist) {
		this.dopssvncodeviewpersonlist = dopssvncodeviewpersonlist;
	}
}
