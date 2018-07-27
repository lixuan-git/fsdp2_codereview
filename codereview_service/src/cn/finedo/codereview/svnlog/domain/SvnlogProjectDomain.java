package cn.finedo.codereview.svnlog.domain;


import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvnlog;


/**
 * @Description: 项目，日志信息封装实体类
 * @author zhusf@finedo.com
 * @date 2018年5月2日 下午6:01:19
 * @version v1.0
 */
public class SvnlogProjectDomain extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * @Fields svnpath : 项目地址
     */
    private String svnpath;

    /**
     * @Fields dopsSvnlogList : svn日志集合
     */
    private List<DopsSvnlog> dopsSvnlogList;

    /**
     * @Fields count : 评论总数。
     */
    private int count;

    /**
     * @Fields solvedcount :已解决的数目。
     */
    private int solvedcount;

    /**
     * @Fields unsolvedcount : 未解决的数目。
     */
    private int unsolvedcount;

    /**
     * @Fields filesize : 日志文件数目
     */
    private String filesize;
    
    /**   
    * @Fields cgid : 评审小组编号   
    */ 
    private String cgid;
    
    /**   
    * @Fields ismanager : 是否是项目经理   
    */ 
    private boolean ismanager;

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

    public List<DopsSvnlog> getDopsSvnlogList() {
        return dopsSvnlogList;
    }

    public void setDopsSvnlogList(List<DopsSvnlog> dopsSvnlogList) {
        this.dopsSvnlogList = dopsSvnlogList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSolvedcount() {
        return solvedcount;
    }

    public void setSolvedcount(int solvedcount) {
        this.solvedcount = solvedcount;
    }

    public int getUnsolvedcount() {
        return unsolvedcount;
    }

    public void setUnsolvedcount(int unsolvedcount) {
        this.unsolvedcount = unsolvedcount;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getCgid() {
        return cgid;
    }

    public void setCgid(String cgid) {
        this.cgid = cgid;
    }

    public boolean isIsmanager() {
        return ismanager;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

}
