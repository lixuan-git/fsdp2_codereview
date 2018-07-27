package cn.finedo.codereview.entity.thirdparty;

import java.util.List;

/**      
* @Description: 用户集合
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月12日 上午11:16:56   
* @version v1.0 
*/ 
public class UsersEntity {
    private List<UserEntity> userEntityList;

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
