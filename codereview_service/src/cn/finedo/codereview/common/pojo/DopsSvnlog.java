package cn.finedo.codereview.common.pojo;


import java.util.List;

import cn.finedo.common.domain.BaseDomain;


/**
 * @Description: svn日志信息表
 * @author zhusf@finedo.com
 * @date 2018年5月2日 上午10:42:46
 * @version v1.0
 */
public class DopsSvnlog extends BaseDomain {
    private static final long serialVersionUID = 1L;

    // 数据库字段
    /**
     * @Fields svnlogid : svn日志表主键
     */
    private String svnlogid;

    /**
     * @Fields svnaddr : 仓库地址
     */
    private String svnaddr;

    /**
     * @Fields svnpath : 项目地址
     */
    private String svnpath;

    /**
     * @Fields filename : 文件名
     */
    private String filename;

    /**
     * @Fields usercode : 用户编号
     */
    private String usercode;

    /**
     * @Fields submitperson : 提交人
     */
    private String submitperson;

    /**
     * @Fields revision : 版本号
     */
    private String revision;

    /**
     * @Fields lastchangeddate : 当前文件最后修改时间
     */
    private String lastchangeddate;

    /**
     * @Fields optdate : 操作时间
     */
    private String optdate;

    /**
     * @Fields message : 日志描述信息
     */
    private String message;

    /**
     * @Fields action : 增删改
     */
    private String action;

    // 非数据库字段
    /**
     * @Fields count : 评论总数
     */
    private int count;

    /**
     * @Fields solvedcount : 已解决的评论总数
     */
    private int solvedcount;

    /**
     * @Fields unsolvedcount : 未解决的评论总数
     */
    private int unsolvedcount;

    /**
     * @Fields projecttype : 项目类型
     */
    private String projecttype;

    /**
     * @Fields dopsSvncommentList : 当前文件的评论
     */
    private List<DopsSvncomment> dopsSvncommentList;

    /**
     * @Fields filesize : 个数
     */
    private String filesize;
    
    /**   
    * @Fields projectid : 项目编号   
    */ 
    private String projectid;

    public String getSvnlogid() {
        return svnlogid;
    }

    public void setSvnlogid(String svnlogid) {
        this.svnlogid = svnlogid;
    }

    public String getSvnaddr() {
        return svnaddr;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSvnaddr(String svnaddr) {
        this.svnaddr = svnaddr;
    }

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getOptdate() {
        return optdate;
    }

    public void setOptdate(String optdate) {
        this.optdate = optdate;
    }

    public String getSubmitperson() {
        return submitperson;
    }

    public void setSubmitperson(String submitperson) {
        this.submitperson = submitperson;
    }

    public String getLastchangeddate() {
        return lastchangeddate;
    }

    public void setLastchangeddate(String lastchangeddate) {
        this.lastchangeddate = lastchangeddate;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype;
    }

    public List<DopsSvncomment> getDopsSvncommentList() {
        return dopsSvncommentList;
    }

    public void setDopsSvncommentList(List<DopsSvncomment> dopsSvncommentList) {
        this.dopsSvncommentList = dopsSvncommentList;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
    
    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DopsSvnlog ds = (DopsSvnlog)obj;
        // 去重处理，去除文件名、版本号相同的记录
        if (this.getFilename().compareTo(ds.getFilename()) == 0) {
            return true;
        }
        return false;
    }

}
