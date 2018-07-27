/*
 *评审小组表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

public class DopsSvncodeviewgroup extends BaseDomain {
    private static final long serialVersionUID = 1L;

    //评审小组表主键
    private String cgid;

    //小组名称
    private String groupname;

    //仓库地址
    private String svnaddr;

    //项目地址
    private String svnpath;

    //仓库类型
    private String repotype;

    //创建人
    private String createperson;

    //创建时间
    private String optdate;

    //创建时间
    private String optdatebegin;

    //创建时间
    private String optdateend;

    //小组描述信息
    private String groupdesc;
    
    //小组人数
    private int num;
    
    //用户编号
    private String usercode;
    
    //小组类型
    private String grouptype;
    
    private String daytype;
    
    //商务经理
    private String busimng;
    public String getDaytype() {
		return daytype;
	}

	public void setDaytype(String daytype) {
		this.daytype = daytype;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setCgid(String cgid) {
        this.cgid = cgid;
    }

    public String getCgid() {
        return this.cgid;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setSvnaddr(String svnaddr) {
        this.svnaddr = svnaddr;
    }

    public String getSvnaddr() {
        return this.svnaddr;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

    public String getSvnpath() {
        return this.svnpath;
    }

    public void setRepotype(String repotype) {
        this.repotype = repotype;
    }

    public String getRepotype() {
        return this.repotype;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getCreateperson() {
        return this.createperson;
    }

    public void setOptdate(String optdate) {
        this.optdate = optdate;
    }

    public String getOptdate() {
        return this.optdate;
    }

    public void setOptdatebegin(String optdatebegin) {
        this.optdatebegin = optdatebegin;
    }

    public String getOptdatebegin() {
        return this.optdatebegin;
    }

    public void setOptdateend(String optdateend) {
        this.optdateend = optdateend;
    }

    public String getOptdateend() {
        return this.optdateend;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

    public String getGroupdesc() {
        return this.groupdesc;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getBusimng() {
        return busimng;
    }

    public void setBusimng(String busimng) {
        this.busimng = busimng;
    }
    
    
    
}

