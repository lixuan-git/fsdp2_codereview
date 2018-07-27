package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: 项目类型表
* @author zhusf@finedo.com   
* @date 2018年5月10日 下午2:58:41   
* @version v1.0
*   
*/ 
public class DopsSvnpathtype extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	/**   
	* @Fields svnpath : 项目地址  
	*/ 
	private String svnpath;
	/**   
	* @Fields projecttype : 项目类型 
	*/ 
	private String projecttype;
	public String getSvnpath() {
		return svnpath;
	}
	public void setSvnpath(String svnpath) {
		this.svnpath = svnpath;
	}
	public String getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	
	
	
	
	
}
