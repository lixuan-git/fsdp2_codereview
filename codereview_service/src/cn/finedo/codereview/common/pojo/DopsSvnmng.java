/*
 *svn管理信息表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

public class DopsSvnmng extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//svn标识
	private String svnid;

	//svn名称
	private String svnname;

	//svn根目录地址
	private String svnaddr;

	//管理员账号
	private String mnguser;

	//管理员密码
	private String mngpwd;

	//操作用户标识
	private String opuserid;

	//操作时间
	private String optime;

	//操作时间
	private String optimebegin;

	//操作时间
	private String optimeend;

	//备注
	private String remark;

	//操作流水号
	private String optsn;

	//svn agent最新同步时间
	private String lastsynctime;

	//svn agent最新同步时间
	private String lastsynctimebegin;

	//svn agent最新同步时间
	private String lastsynctimeend;

	//操作用户标识 
	private String opusername;
	
	//项目地址
	private String svnpath;

	public void setSvnid(String svnid) {
		this.svnid = svnid;
	}

	public String getSvnid() {
		return this.svnid;
	}

	public void setSvnname(String svnname) {
		this.svnname = svnname;
	}

	public String getSvnname() {
		return this.svnname;
	}

	public void setSvnaddr(String svnaddr) {
		this.svnaddr = svnaddr;
	}

	public String getSvnaddr() {
		return this.svnaddr;
	}

	public void setMnguser(String mnguser) {
		this.mnguser = mnguser;
	}

	public String getMnguser() {
		return this.mnguser;
	}

	public void setMngpwd(String mngpwd) {
		this.mngpwd = mngpwd;
	}

	public String getMngpwd() {
		return this.mngpwd;
	}

	public void setOpuserid(String opuserid) {
		this.opuserid = opuserid;
	}

	public String getOpuserid() {
		return this.opuserid;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getOptime() {
		return this.optime;
	}

	public void setOptimebegin(String optimebegin) {
		this.optimebegin = optimebegin;
	}

	public String getOptimebegin() {
		return this.optimebegin;
	}

	public void setOptimeend(String optimeend) {
		this.optimeend = optimeend;
	}

	public String getOptimeend() {
		return this.optimeend;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setOptsn(String optsn) {
		this.optsn = optsn;
	}

	public String getOptsn() {
		return this.optsn;
	}

	public void setLastsynctime(String lastsynctime) {
		this.lastsynctime = lastsynctime;
	}

	public String getLastsynctime() {
		return this.lastsynctime;
	}

	public void setLastsynctimebegin(String lastsynctimebegin) {
		this.lastsynctimebegin = lastsynctimebegin;
	}

	public String getLastsynctimebegin() {
		return this.lastsynctimebegin;
	}

	public void setLastsynctimeend(String lastsynctimeend) {
		this.lastsynctimeend = lastsynctimeend;
	}

	public String getLastsynctimeend() {
		return this.lastsynctimeend;
	}

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public String getOpusername() {
		return this.opusername;
	}

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

	
}
