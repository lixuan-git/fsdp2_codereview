package cn.finedo.codereview.svncodeviewuser.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: 代码评审用户表集合
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:51:49   
* @version v1.0
*   
*/ 
public class SvncodeviewuserListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvncodeviewuser> dopssvncodeviewuserlist=new ArrayList<DopsSvncodeviewuser>();

	public List<DopsSvncodeviewuser> getDopssvncodeviewuserlist() {
		return dopssvncodeviewuserlist;
	}

	public void setDopssvncodeviewuserlist(List<DopsSvncodeviewuser> dopssvncodeviewuserlist) {
		this.dopssvncodeviewuserlist = dopssvncodeviewuserlist;
	}
}
