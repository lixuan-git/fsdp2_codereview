/*
 *评审成员表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

public class DopsProjectmember extends BaseDomain {
    private static final long serialVersionUID = 1L;

    //评审人员表主键
    private String cpid;

    //用户编号
    private String usercode;

    //评审小组表主键
    private String cgid;

    //评审小组表主键 鍚嶇О
    private String cgname;
    
    //添加时间
    private String addtime;
    
    //剔出时间
    private String remtime;
    
    //添加人
    private String addoptuser;
    
    //移除人
    private String remoptuser;
    
    //用户状态
    private String userstate;
    
    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCpid() {
        return this.cpid;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsercode() {
        return this.usercode;
    }

    public void setCgid(String cgid) {
        this.cgid = cgid;
    }

    public String getCgid() {
        return this.cgid;
    }

    public void setCgname(String cgname) {
        this.cgname = cgname;
    }

    public String getCgname() {
        return this.cgname;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getRemtime() {
        return remtime;
    }

    public void setRemtime(String remtime) {
        this.remtime = remtime;
    }

    public String getAddoptuser() {
        return addoptuser;
    }

    public void setAddoptuser(String addoptuser) {
        this.addoptuser = addoptuser;
    }

    public String getRemoptuser() {
        return remoptuser;
    }

    public void setRemoptuser(String remoptuser) {
        this.remoptuser = remoptuser;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }
    
    

}

