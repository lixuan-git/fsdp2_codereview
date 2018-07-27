package cn.finedo.codereview.entity;

/**      
* @Description: svn参数封装对象。
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:49:14   
* @version v1.0
*   
*/ 
public class SvnParaEntity {
	/**   
	* @Fields svnid : svn编号 
	*/ 
	private String svnid;
	/**   
	* @Fields svnaddr : 仓库地址   
	*/ 
	private String svnaddr;
	/**   
	* @Fields filename : 全路径文件名   
	*/ 
	private String filename;
	/**   
	* @Fields usercode : 用户标识  
	*/ 
	private String usercode;
	/**   
	* @Fields passwd : 用户的svn明文密码   
	*/ 
	private String passwd;
	/**   
	* @Fields directoryUrl : svn全路径  
	*/ 
	private String directoryUrl;
	/**   
	* @Fields rownumber : 代码行号   
	*/ 
	private String rownumber;
	/**   
	* @Fields start : 开始时间  
	*/ 
	private String start;
	/**   
	* @Fields end : 结束时间   
	*/ 
	private String end;
	
	/**   
	* @Fields type 我评论的 被评论的
	*/ 
	private String type;
	/**   
	* @Fields commentid : 评论id   
	*/ 
	private String commentid;
	/**   
	* @Fields revision : 版本号   
	*/ 
	private String revision;
	/**   
	* @Fields submitperson : 提交人   
	*/ 
	private String submitperson;
	/**   
	* @Fields svnlogid : svnlog表主键  
	*/ 
	private String svnlogid;
	/**   
	* @Fields svnpath : 项目地址   
	*/ 
	private String svnpath;
	public String getSvnid() {
		return svnid;
	}
	public void setSvnid(String svnid) {
		this.svnid = svnid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getDirectoryUrl() {
		return directoryUrl;
	}
	public void setDirectoryUrl(String directoryUrl) {
		this.directoryUrl = directoryUrl;
	}
	public String getRownumber() {
		return rownumber;
	}
	public void setRownumber(String rownumber) {
		this.rownumber = rownumber;
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
	public String getSvnaddr() {
		return svnaddr;
	}
	public void setSvnaddr(String svnaddr) {
		this.svnaddr = svnaddr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommentid() {
		return commentid;
	}
	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getSubmitperson() {
		return submitperson;
	}
	public void setSubmitperson(String submitperson) {
		this.submitperson = submitperson;
	}
	public String getSvnlogid() {
		return svnlogid;
	}
	public void setSvnlogid(String svnlogid) {
		this.svnlogid = svnlogid;
	}
    public String getSvnpath() {
        return svnpath;
    }
    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }
	
}
