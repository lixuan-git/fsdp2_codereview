/**  
* OA组织机构信息
* @company Finedo.cn
* @author Finedo
* @date 2017年8月11日 下午7:36:20
* @Title: OAOrganization.java
* @Package cn.finedo.developer.login.domain
* @version V1.0  
*/ 

package cn.finedo.codereview.login.domain;

import cn.finedo.common.domain.BaseDomain;

public class OAOrganization extends BaseDomain {

    private static final long serialVersionUID = 1L;
    
    private String orgcode;
    private String orgname;
    private String parentcode;
    private String parentname;
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
    public String getParentcode() {
        return parentcode;
    }
    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }
    public String getParentname() {
        return parentname;
    }
    public void setParentname(String parentname) {
        this.parentname = parentname;
    }
    
}
