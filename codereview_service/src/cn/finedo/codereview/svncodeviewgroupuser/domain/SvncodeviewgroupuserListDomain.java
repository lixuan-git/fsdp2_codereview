package cn.finedo.codereview.svncodeviewgroupuser.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.common.pojo.DopsSvnuserright;
import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: svnuserright信息实体类
* @author pt   
* @date 2018年6月7日  
* @version v1.0
*   
*/ 
public class SvncodeviewgroupuserListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvncodeviewgroupuser> svncodeviewgroupuserlist=new ArrayList<DopsSvncodeviewgroupuser>();

	public List<DopsSvncodeviewgroupuser> getSvncodeviewgroupuserlist() {
		return svncodeviewgroupuserlist;
	}

	public void setSvncodeviewgroupuserlist(List<DopsSvncodeviewgroupuser> svncodeviewgroupuserlist) {
		this.svncodeviewgroupuserlist = svncodeviewgroupuserlist;
	}

	

	
	
}
