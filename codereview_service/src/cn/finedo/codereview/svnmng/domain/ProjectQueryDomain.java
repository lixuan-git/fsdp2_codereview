/**  
* 项目查询实体类
* @company Finedo.cn
* @author Finedo
* @date 2017年8月22日 下午6:42:55
* @Title: ProjectQueryDomain.java
* @Package cn.finedo.codereview.svnmng.domain
* @version V1.0  
*/ 

package cn.finedo.codereview.svnmng.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.codereview.svnmng.domain.ProjectDomain;

public class ProjectQueryDomain extends BaseDomain {

    private static final long serialVersionUID = 1L;

    // 分页信息
    private PageParamDomain pageparam = null;
    
    private ProjectDomain project;

    public PageParamDomain getPageparam() {
        return pageparam;
    }

    public void setPageparam(PageParamDomain pageparam) {
        this.pageparam = pageparam;
    }

    public ProjectDomain getProject() {
        return project;
    }

    public void setProject(ProjectDomain project) {
        this.project = project;
    }
    
}
