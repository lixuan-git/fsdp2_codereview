package cn.finedo.codereview.entity.thirdparty;

import java.util.List;

/**      
* @Description: 项目信息实体，JDBC参数
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:15:58   
* @version v1.0 
*/ 
public class ProjectEntityForSql {
    /**   
    * @Fields projectid : 项目唯一编号   
    */ 
    private String projectid;
    /**
     * @Fields svnaddr : 仓库地址
     */
    private String svnaddr;

    /**
     * @Fields svnpath : 项目地址
     */
    private String svnpath;

    /**
     * @Fields projectname : 项目名称
     */
    private String projectname;

    /**
     * @Fields customer : 客户信息
     */
    private String customer;

    /**
     * @Fields dept : 归属部门
     */
    private String dept;

    /**
     * @Fields projectdesc : 项目描述
     */
    private String projectdesc;

    /**   
    * @Fields userEntityList : 项目的基本人员信息   
    */ 
    private List<UserEntity> userEntityList;
    
    
    /**
     * @Fields role : projectmng项目经理 customerprojectmng客户项目经理 bzmng商务经理 repomnguser仓库管理员
     */
    private String role;

    /**
     * @Fields pwd : 仓库管理员 svn密码
     */
    private String pwd;

    /**
     * @Fields usercode : svn账号
     */
    private String usercode;

    /**
     * @Fields phonenu : 手机号码
     */
    private String phonenu;

    /**
     * @Fields email : 邮箱
     */
    private String email;

    private String username;

    /**
     * @Fields repotype : 仓库类型
     */
    private String repotype;
    
    /**   
    * @Fields svnid : 仓库id  
    */ 
    private String svnid;
    /**   
    * @Fields cpid : 项目成员id   
    */ 
    private String cpid;
    
    /**   
    * @Fields groupname : 项目名称   
    */ 
    private String groupname;

    public String getSvnaddr() {
        return svnaddr;
    }

    public void setSvnaddr(String svnaddr) {
        this.svnaddr = svnaddr;
    }

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }


    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getProjectdesc() {
        return projectdesc;
    }

    public void setProjectdesc(String projectdesc) {
        this.projectdesc = projectdesc;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPhonenu() {
        return phonenu;
    }

    public void setPhonenu(String phonenu) {
        this.phonenu = phonenu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepotype() {
        return repotype;
    }

    public void setRepotype(String repotype) {
        this.repotype = repotype;
    }

    public String getSvnid() {
        return svnid;
    }

    public void setSvnid(String svnid) {
        this.svnid = svnid;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "ProjectEntityForSql [projectid=" + projectid + ", svnaddr=" + svnaddr + ", svnpath=" + svnpath + ", projectname=" + projectname + ", customer=" + customer + ", dept=" + dept + ", projectdesc=" + projectdesc + ", userEntityList=" + userEntityList + ", role=" + role + ", pwd=" + pwd + ", usercode=" + usercode + ", phonenu=" + phonenu + ", email=" + email + ", username=" + username + ", repotype=" + repotype + ", svnid=" + svnid + ", cpid=" + cpid + ", groupname=" + groupname + "]";
    }
    
    

}
