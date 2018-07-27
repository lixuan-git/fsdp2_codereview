package cn.finedo.codereview.entity.thirdparty;

/**      
* @Description: 用户信息实体
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:16:41   
* @version v1.0 
*/ 
public class UserEntity {
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

    @Override
    public String toString() {
        return "UserEntity [role=" + role + ", pwd=" + pwd + ", usercode=" + usercode + ", phonenu=" + phonenu + ", email=" + email + ", username=" + username + ", repotype=" + repotype + "]";
    }

    

}
