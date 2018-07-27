/**  
* OA用户信息
* @company Finedo.cn
* @author Finedo
* @date 2017年8月11日 下午8:18:31
* @Title: OAUser.java
* @Package cn.finedo.developer.login.domain
* @version V1.0  
*/ 

package cn.finedo.codereview.login.domain;

import cn.finedo.common.domain.BaseDomain;

public class OAUser extends BaseDomain {

    private static final long serialVersionUID = 1L;
    private String basepostname;
    private String birthday    ;
    private String effdate     ;
    private String email       ;
    private String expdate     ;
    private String gender      ;
    private String optdate     ;
    private String optrid      ;
    private String orgcode     ;
    private String orgname     ;
    private String personid    ;
    private String remark      ;
    private String roles       ;
    private String svcnum      ;
    private String userRoles   ;
    private String userid      ;
    private String username    ;
    private String validate    ;
    public String getBasepostname() {
        return basepostname;
    }
    public void setBasepostname(String basepostname) {
        this.basepostname = basepostname;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getEffdate() {
        return effdate;
    }
    public void setEffdate(String effdate) {
        this.effdate = effdate;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getExpdate() {
        return expdate;
    }
    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getOptdate() {
        return optdate;
    }
    public void setOptdate(String optdate) {
        this.optdate = optdate;
    }
    public String getOptrid() {
        return optrid;
    }
    public void setOptrid(String optrid) {
        this.optrid = optrid;
    }
    public String getOrgcode() {
        return orgcode;
    }
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
    public String getOrgname() {
        return orgname;
    }
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    public String getPersonid() {
        return personid;
    }
    public void setPersonid(String personid) {
        this.personid = personid;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getSvcnum() {
        return svcnum;
    }
    public void setSvcnum(String svcnum) {
        this.svcnum = svcnum;
    }
    public String getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getValidate() {
        return validate;
    }
    public void setValidate(String validate) {
        this.validate = validate;
    }
    
}
