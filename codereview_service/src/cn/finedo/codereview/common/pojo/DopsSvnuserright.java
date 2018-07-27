/*
 *svn用户权限表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

public class DopsSvnuserright extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//权限标识
	private String rightid;

	//svn路径
	private String svnpath;

	//权限(r、rw)
	private String rights;

	//操作员工号
	private String opuserid;

	//操作时间
	private String optime;

	//操作时间
	private String optimebegin;

	//操作时间
	private String optimeend;

	//备注
	private String remark;

	//操作流水
	private String optsn;

	//svn用户组标识
	private String userid;

	//操作员工号 鍚嶇О
	private String opusername;
	
	private String svnid;
	
	private String usercode;
	
	//项目成员人数
	private int peonum;
	
	//项目名称
	private String projectname;
	
	//评审小组编号
	private String cgid;
	
	public String getCgid() {
		return cgid;
	}

	public void setCgid(String cgid) {
		this.cgid = cgid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public int getPeonum() {
		return peonum;
	}

	public void setPeonum(int peonum) {
		this.peonum = peonum;
	}

	public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getSvnid() {
        return svnid;
    }

    public void setSvnid(String svnid) {
        this.svnid = svnid;
    }

    public void setRightid(String rightid) {
		this.rightid = rightid;
	}

	public String getRightid() {
		return this.rightid;
	}

	public void setSvnpath(String svnpath) {
		this.svnpath = svnpath;
	}

	public String getSvnpath() {
		return this.svnpath;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getRights() {
		return this.rights;
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

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public String getOpusername() {
		return this.opusername;
	}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
