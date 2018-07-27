package cn.finedo.codereview.entity.selfcheck;

import java.util.ArrayList;
import java.util.List;

/**      
* @Description: 检查结果详情
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月13日 下午5:07:52   
* @version v1.0 
*/ 
public class IssueDetailEntity {
    /**   
    * @Fields rownumber : 行号 
    */ 
    private String rownumber;
    /**   
    * @Fields contents : 检查到的文本内容   
    */ 
    private List<String> contents = new ArrayList<String>();
    public String getRownumber() {
        return rownumber;
    }
    public void setRownumber(String rownumber) {
        this.rownumber = rownumber;
    }
    public List<String> getContents() {
        return contents;
    }
    public void setContents(List<String> contents) {
        this.contents = contents;
    }
   
    
    

}
