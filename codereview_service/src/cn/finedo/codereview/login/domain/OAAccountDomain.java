package cn.finedo.codereview.login.domain;

import cn.finedo.common.domain.BaseDomain;

public class OAAccountDomain extends BaseDomain
{
  private static final long serialVersionUID = 1L;
  private String hostIP;
  private String loginDate;
  private String logintype;
  private String orgCode;
  private String orgname;
  private String orgtype;
  private String sessionid;
  private String userID;
  private String userName;
  private String usertype;
  private String email;

  public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getHostIP()
  {
    return this.hostIP;
  }
  public void setHostIP(String hostIP) {
    this.hostIP = hostIP;
  }
  public String getLoginDate() {
    return this.loginDate;
  }
  public void setLoginDate(String loginDate) {
    this.loginDate = loginDate;
  }
  public String getLogintype() {
    return this.logintype;
  }
  public void setLogintype(String logintype) {
    this.logintype = logintype;
  }
  public String getOrgCode() {
    return this.orgCode;
  }
  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }
  public String getOrgname() {
    return this.orgname;
  }
  public void setOrgname(String orgname) {
    this.orgname = orgname;
  }
  public String getOrgtype() {
    return this.orgtype;
  }
  public void setOrgtype(String orgtype) {
    this.orgtype = orgtype;
  }
  public String getSessionid() {
    return this.sessionid;
  }
  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }
  public String getUserID() {
    return this.userID;
  }
  public void setUserID(String userID) {
    this.userID = userID;
  }
  public String getUserName() {
    return this.userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getUsertype() {
    return this.usertype;
  }
  public void setUsertype(String usertype) {
    this.usertype = usertype;
  }
}