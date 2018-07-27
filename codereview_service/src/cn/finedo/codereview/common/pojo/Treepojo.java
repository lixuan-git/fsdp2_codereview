package cn.finedo.codereview.common.pojo;

import java.util.List;

import cn.finedo.common.domain.BaseDomain;


/**
 * @Description: 前台树控件封装实体类
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年6月7日 下午2:43:40
 * @version v1.0
 */
public class Treepojo extends BaseDomain {
    private static final long serialVersionUID = 1L;

    private String id;// 当前id

    private String pid;// 父节点

    private List<Treepojo> children;

    private String name;// 节点名称

    private String directoryUrl;// 路径

    private int treelvl = 0;
    
    private String open;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<Treepojo> getChildren() {
        return children;
    }

    public void setChildren(List<Treepojo> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public int getTreelvl() {
        return treelvl;
    }

    public void setTreelvl(int treelvl) {
        this.treelvl = treelvl;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
    
    

}
