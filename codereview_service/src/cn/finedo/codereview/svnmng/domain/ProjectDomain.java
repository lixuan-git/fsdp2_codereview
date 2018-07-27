/**  
* 项目信息实体类
* @company Finedo.cn
* @author Finedo
* @date 2017年8月22日 下午3:15:27
* @Title: ProjectDomain.java
* @Package cn.finedo.codereview.svnmng.domain
* @version V1.0  
*/ 

package cn.finedo.codereview.svnmng.domain;

import cn.finedo.common.domain.BaseDomain;

public class ProjectDomain extends BaseDomain {

    private static final long serialVersionUID = 1L;
    private String projectid  ;
    private String projectcode;
    private String projectname;
    private String custid     ;
    private String custname   ;
    private String status     ;
    private String statusname ;
    private String statustime ;
    private String createtime ;
    private String projecttype;
    private String orgcode    ;
    private String busimng    ;
    private String projectmng ;
    private String svnid;
    private String svnpath;
    public String getProjectid() {
        return projectid;
    }
    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }
    public String getProjectcode() {
        return projectcode;
    }
    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }
    public String getProjectname() {
        return projectname;
    }
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
    public String getCustid() {
        return custid;
    }
    public void setCustid(String custid) {
        this.custid = custid;
    }
    public String getCustname() {
        return custname;
    }
    public void setCustname(String custname) {
        this.custname = custname;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatusname() {
        return statusname;
    }
    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
    public String getStatustime() {
        return statustime;
    }
    public void setStatustime(String statustime) {
        this.statustime = statustime;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getProjecttype() {
        return projecttype;
    }
    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }
    public String getOrgcode() {
        return orgcode;
    }
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
    public String getBusimng() {
        return busimng;
    }
    public void setBusimng(String busimng) {
        this.busimng = busimng;
    }
    public String getProjectmng() {
        return projectmng;
    }
    public void setProjectmng(String projectmng) {
        this.projectmng = projectmng;
    }
    public String getSvnid() {
        return svnid;
    }
    public void setSvnid(String svnid) {
        this.svnid = svnid;
    }
    public String getSvnpath() {
        return svnpath;
    }
    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }
}
