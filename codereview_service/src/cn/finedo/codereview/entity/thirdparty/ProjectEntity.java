package cn.finedo.codereview.entity.thirdparty;

import java.util.List;

/**      
* @Description: 项目信息封装实体
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:15:30   
* @version v1.0 
*/ 
public class ProjectEntity {
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


}
