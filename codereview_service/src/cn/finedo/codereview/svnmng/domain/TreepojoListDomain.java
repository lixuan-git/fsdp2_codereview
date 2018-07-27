package cn.finedo.codereview.svnmng.domain;


import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.Treepojo;


/**      
* @Description: 树对象集合
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年6月7日 下午3:11:47   
* @version v1.0 
*/ 
public class TreepojoListDomain extends BaseDomain {
    private static final long serialVersionUID = 1L;

    private List<Treepojo> treelist;

    public List<Treepojo> getTreelist() {
        return treelist;
    }

    public void setTreelist(List<Treepojo> treelist) {
        this.treelist = treelist;
    }

}
