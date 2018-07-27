package cn.finedo.codereview.entity.thirdparty;

import java.util.List;

/**      
* @Description: 项目集合
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:15:15   
* @version v1.0 
*/ 
public class ProjectsEntity {
    private List<ProjectEntity> projectEntityList;

    public List<ProjectEntity> getProjectEntityList() {
        return projectEntityList;
    }

    public void setProjectEntityList(List<ProjectEntity> projectEntityList) {
        this.projectEntityList = projectEntityList;
    }
    
}
