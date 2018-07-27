package cn.finedo.codereview.entity.selfcheck;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 文件检查封装对象
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年7月12日 上午10:53:35
 * @version v1.0
 */
public class SelfcheckEntity {
    /**
     * @Fields filename : 文件名
     */
    private String filename;

    /**
     * @Fields revision : 版本号
     */
    private String revision;
    

    /**
     * @Fields results : 检查结果
     */
    private List<IssueDetailEntity> results;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public List<IssueDetailEntity> getResults() {
        return results;
    }

    public void setResults(List<IssueDetailEntity> results) {
        this.results = results;
    }

    

    

    

}
