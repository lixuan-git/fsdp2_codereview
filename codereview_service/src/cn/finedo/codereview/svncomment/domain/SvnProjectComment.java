package cn.finedo.codereview.svncomment.domain;


import java.util.List;

import cn.finedo.codereview.common.pojo.DopsSvncomment;


/**
 * @Description: 评论工程查询实体类
 * @author zhusf@finedo.com
 * @date 2018年5月2日 上午10:52:23
 * @version v1.0
 */
public class SvnProjectComment {
    /**
     * @Fields svnpath : 项目地址
     */
    private String svnpath;

    /**
     * @Fields dopsSvncommentList : 评审集合
     */
    private List<DopsSvncomment> dopsSvncommentList;

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

    public String getSvnpath() {
        return svnpath;
    }

    public void setSvnpath(String svnpath) {
        this.svnpath = svnpath;
    }

    public List<DopsSvncomment> getDopsSvncommentList() {
        return dopsSvncommentList;
    }

    public void setDopsSvncommentList(List<DopsSvncomment> dopsSvncommentList) {
        this.dopsSvncommentList = dopsSvncommentList;
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

}
