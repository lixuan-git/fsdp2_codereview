package cn.finedo.codereview.svnuserpermission.domain;

import java.util.ArrayList;
import java.util.List;
import cn.finedo.codereview.common.pojo.DopsSvnuserpermission;
import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: svnuserright信息实体类
* @author pt   
* @date 2018年6月7日  
* @version v1.0
*   
*/ 
public class SvnuserpermissionListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvnuserpermission> dopssvnuserrightlist=new ArrayList<DopsSvnuserpermission>();

	public List<DopsSvnuserpermission> getDopssvnuserrightlist() {
		return dopssvnuserrightlist;
	}

	public void setDopssvnuserrightlist(List<DopsSvnuserpermission> dopssvnuserrightlist) {
		this.dopssvnuserrightlist = dopssvnuserrightlist;
	}

	
}
