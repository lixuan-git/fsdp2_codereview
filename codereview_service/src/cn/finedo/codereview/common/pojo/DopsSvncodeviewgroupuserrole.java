package cn.finedo.codereview.common.pojo;


import cn.finedo.common.domain.BaseDomain;


/**
 * @Description: 成员小组权限表
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年6月25日 上午9:50:38
 * @version v1.0
 */
public class DopsSvncodeviewgroupuserrole extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * @Fields usercode : 用户编号
     */
    private String usercode;

    /**
     * @Fields grouprole : 角色
     */
    private String grouprole;

    /**
     * @Fields svnpath : 项目名称
     */
    private String svnpath;
    
    /**   
    * @Fields svnid : 仓库编号  
    */ 
    private String svnid;
    
    /**
     * TODO 剩余投票次数
     */
    private String pollnumber;
    
    /**
     * TODO 上次投票时间
     */
    private String lasttime;



	public String getPollnumber() {
		return pollnumber;
	}

	public void setPollnumber(String pollnumber) {
		this.pollnumber = pollnumber;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getGrouprole() {
        return grouprole;
    }

    public void setGrouprole(String grouprole) {
        this.grouprole = grouprole;
    }

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

    public String getSvnid() {
        return svnid;
    }

    public void setSvnid(String svnid) {
        this.svnid = svnid;
    }

}
