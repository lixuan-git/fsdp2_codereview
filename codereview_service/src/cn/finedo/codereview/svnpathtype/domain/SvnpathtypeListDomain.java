package cn.finedo.codereview.svnpathtype.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvnpathtype;
import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: 项目类型信息实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:51:49   
* @version v1.0
*   
*/ 
public class SvnpathtypeListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvnpathtype> dopssvnpathtypelist=new ArrayList<DopsSvnpathtype>();

	public List<DopsSvnpathtype> getDopssvnpathtypelist() {
		return dopssvnpathtypelist;
	}

	public void setDopssvnpathtypelist(List<DopsSvnpathtype> dopssvnpathtypelist) {
		this.dopssvnpathtypelist = dopssvnpathtypelist;
	}
}
