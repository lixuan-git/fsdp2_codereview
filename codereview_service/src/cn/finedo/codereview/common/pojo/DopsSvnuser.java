/*
 *svn用户信息表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvnuser;

public class DopsSvnuser extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//用户标识
	private String userid;

	//用户编码(svn连接用户)
	private String usercode;

	//密码
	private String passwd;
	
	//新密码
	private String newpasswd;

	public String getNewpasswd() {
		return newpasswd;
	}

	public void setNewpasswd(String newpasswd) {
		this.newpasswd = newpasswd;
	}

	//密码最后一次修改时间
	private String lastmdtime;

	//密码最后一次修改时间
	private String lastmdtimebegin;

	//密码最后一次修改时间
	private String lastmdtimeend;

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

	//用户名
	private String username;

	//操作流水号
	private String optsn;

	//操作员工号 鍚嶇О
	private String opusername;
	
	private String svnid;

	public String getSvnid() {
        return svnid;
    }

    public void setSvnid(String svnid) {
        this.svnid = svnid;
    }

    public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setLastmdtime(String lastmdtime) {
		this.lastmdtime = lastmdtime;
	}

	public String getLastmdtime() {
		return this.lastmdtime;
	}

	public void setLastmdtimebegin(String lastmdtimebegin) {
		this.lastmdtimebegin = lastmdtimebegin;
	}

	public String getLastmdtimebegin() {
		return this.lastmdtimebegin;
	}

	public void setLastmdtimeend(String lastmdtimeend) {
		this.lastmdtimeend = lastmdtimeend;
	}

	public String getLastmdtimeend() {
		return this.lastmdtimeend;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
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

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((usercode == null) ? 0 : usercode.hashCode());
        result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DopsSvnuser svnuser = (DopsSvnuser)obj;
        if(!svnuser.getUsercode().equals(this.getUsercode()))
            return false;
        if(!svnuser.getPasswd().equals(this.getPasswd()))
            return false;
        return true;
    }
}
