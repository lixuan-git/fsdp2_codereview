package cn.finedo.codereview.svncodeviewproject.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.common.domain.BaseDomain;

/**
 * @author 刘青成
 *TODO
 */
public class SvncodeviewprojectListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private List<DopsSvncodeviewproject> dopssvncodeviewprojectlist=new ArrayList<DopsSvncodeviewproject>();
	public List<DopsSvncodeviewproject> getDopssvncodeviewprojectlist() {
		return dopssvncodeviewprojectlist;
	}
	public void setDopssvncodeviewprojectlist(
			List<DopsSvncodeviewproject> dopssvncodeviewprojectlist) {
		this.dopssvncodeviewprojectlist = dopssvncodeviewprojectlist;
	}
	
	
	
}
