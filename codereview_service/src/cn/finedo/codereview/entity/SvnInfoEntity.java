package cn.finedo.codereview.entity;

/**      
* @Description: svn信息实体类，传参封装对象。
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:44:14   
* @version v1.0
*   
*/ 
public class SvnInfoEntity {
	/**   
	* @Fields usercode : 用户编号  
	*/ 
	private String usercode;
	/**   
	* @Fields realpwd : 密码  
	*/ 
	private String realpwd;
	/**   
	* @Fields svnaddr : 仓库地址   
	*/ 
	private String svnaddr;
	/**   
	* @Fields svnpath : 项目地址   
	*/ 
	private String svnpath;
	/**   
	* @Fields start : 开始时间   
	*/ 
	private String start;
	/**   
	* @Fields end : 结束时间  
	*/ 
	private String end;
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getRealpwd() {
		return realpwd;
	}
	public void setRealpwd(String realpwd) {
		this.realpwd = realpwd;
	}
	public String getSvnaddr() {
		return svnaddr;
	}
	public void setSvnaddr(String svnaddr) {
		this.svnaddr = svnaddr;
	}
	public String getSvnpath() {
		return svnpath;
	}
	public void setSvnpath(String svnpath) {
		this.svnpath = svnpath;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

}
