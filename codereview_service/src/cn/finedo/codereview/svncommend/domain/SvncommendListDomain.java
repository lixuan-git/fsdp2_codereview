package cn.finedo.codereview.svncommend.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.common.domain.BaseDomain;

/**
 * @author 刘青成
 *TODO 代码推荐实体类
 */
public class SvncommendListDomain extends BaseDomain  {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvncommend> dopssvncommendlist=new ArrayList<DopsSvncommend>();

	public List<DopsSvncommend> getDopssvncommendlist() {
		return dopssvncommendlist;
	}

	public void setDopssvncommendlist(List<DopsSvncommend> dopssvncommendlist) {
		this.dopssvncommendlist = dopssvncommendlist;
	}
}
