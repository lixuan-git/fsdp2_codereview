package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvncomment;

/**
 * @Description: svn文件评论表
 * @author zhusf@finedo.com
 * @date 2018年5月2日 上午10:42:19
 * @version v1.0
 * 
 */
public class DopsSvncomment extends BaseDomain {
	private static final long serialVersionUID = 1L;
	// 数据库字段
	/**
	 * @Fields commentid : 评论表主键
	 */
	private String commentid;
	/**
	 * @Fields projectid : 工程id
	 */
	private String projectid;
	/**
	 * @Fields projectname : 工程名
	 */
	private String projectname;
	/**
	 * @Fields repurl : 仓库地址
	 */
	private String repurl;
	/**
	 * @Fields filename : 文件名
	 */
	private String filename;
	/**
	 * @Fields revision : 版本号
	 */
	private String revision;
	/**
	 * @Fields rownumber : 行号
	 */
	private String rownumber;
	/**
	 * @Fields codecomment : 评论内容
	 */
	private String codecomment;
	/**
	 * @Fields personid : 人员id
	 */
	private String personid;
	/**
	 * @Fields optdate : 操作时间
	 */
	private String optdate;
	/**
	 * @Fields state : 评论状态。0：未解决。1：已解决。
	 */
	private String finishstate;
	/**
	 * @Fields remark : 备注
	 */
	private String remark;
	/**
	 * @Fields commenttype : 评论类型。
	 */
	private String commenttype;
	/**
	 * @Fields submitperson : 提交人
	 */
	private String submitperson;
	/**
	 * @Fields ismustmodify : 是否必须修改
	 */
	private String ismustmodify;
	/**
	 * @Fields iscommon : 是否为共性问题
	 */
	private String iscommon;
	/**
	 * @Fields acceptstate : 接受状态，接受，拒绝。
	 */
	private String acceptstate;
	/**   
	* @Fields startrownumber : 评审的开始行号 
	*/ 
	private String startrownumber;
	
	/**   
	* @Fields mousedownx : 鼠标按下横坐标   
	*/ 
	private String mousedownx;
	/**   
	* @Fields mousedowny : 鼠标按下纵坐标   
	*/ 
	private String mousedowny;
	/**   
	* @Fields mouseupx : 鼠标放开横坐标   
	*/ 
	private String mouseupx;
	/**   
	* @Fields mouseupy : 鼠标放开纵坐标   
	*/ 
	private String mouseupy;

	// 非数据库字段
	/**
	 * @Fields personname : 最后提交时间。
	 */
	private String lastchangeddate;

	/**
	 * @Fields personname : 提交人姓名。
	 */
	private String personname;
	/**
	 * @Fields codecontent : 代码内容。
	 */
	private String codecontent;

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
	 * @Fields svnpath : 项目地址。
	 */
	private String svnpath;
	/**
	 * @Fields pwd : 密码。
	 */
	private String pwd;
	/**
	 * @Fields svnlogid :svn日志表主键。
	 */
	private String svnlogid;
	/**
	 * @Fields message : 提交信息
	 */
	private String message;

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRepurl() {
		return repurl;
	}

	public void setRepurl(String repurl) {
		this.repurl = repurl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRownumber() {
		return rownumber;
	}

	public void setRownumber(String rownumber) {
		this.rownumber = rownumber;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getOptdate() {
		return optdate;
	}

	public void setOptdate(String optdate) {
		this.optdate = optdate;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getSubmitperson() {
		return submitperson;
	}

	public void setSubmitperson(String submitperson) {
		this.submitperson = submitperson;
	}

	public String getCommenttype() {
		return commenttype;
	}

	public void setCommenttype(String commenttype) {
		this.commenttype = commenttype;
	}

	public String getCodecontent() {
		return codecontent;
	}

	public void setCodecontent(String codecontent) {
		this.codecontent = codecontent;
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

	public String getSvnpath() {
		return svnpath;
	}

	public void setSvnpath(String svnpath) {
		this.svnpath = svnpath;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLastchangeddate() {
		return lastchangeddate;
	}

	public void setLastchangeddate(String lastchangeddate) {
		this.lastchangeddate = lastchangeddate;
	}

	public String getSvnlogid() {
		return svnlogid;
	}

	public void setSvnlogid(String svnlogid) {
		this.svnlogid = svnlogid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsmustmodify() {
		return ismustmodify;
	}

	public void setIsmustmodify(String ismustmodify) {
		this.ismustmodify = ismustmodify;
	}

	public String getIscommon() {
		return iscommon;
	}

	public void setIscommon(String iscommon) {
		this.iscommon = iscommon;
	}

	public String getAcceptstate() {
		return acceptstate;
	}

	public void setAcceptstate(String acceptstate) {
		this.acceptstate = acceptstate;
	}

	public String getCodecomment() {
		return codecomment;
	}

	public void setCodecomment(String codecomment) {
		this.codecomment = codecomment;
	}

	public String getFinishstate() {
		return finishstate;
	}

	public void setFinishstate(String finishstate) {
		this.finishstate = finishstate;
	}
	
	public String getStartrownumber() {
		return startrownumber;
	}

	public void setStartrownumber(String startrownumber) {
		this.startrownumber = startrownumber;
	}
	
	


    public String getMousedownx() {
        return mousedownx;
    }

    public void setMousedownx(String mousedownx) {
        this.mousedownx = mousedownx;
    }

    public String getMousedowny() {
        return mousedowny;
    }

    public void setMousedowny(String mousedowny) {
        this.mousedowny = mousedowny;
    }

    public String getMouseupx() {
        return mouseupx;
    }

    public void setMouseupx(String mouseupx) {
        this.mouseupx = mouseupx;
    }

    public String getMouseupy() {
        return mouseupy;
    }

    public void setMouseupy(String mouseupy) {
        this.mouseupy = mouseupy;
    }

    public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		DopsSvncomment ds = (DopsSvncomment) obj;
		// 去重处理，去除文件名、版本号相同的记录
//		if (this.getFilename().compareTo(ds.getFilename()) == 0 && this.getRevision().equals(ds.getRevision())) {
//			return true;
//		}
		if (this.getFilename().compareTo(ds.getFilename()) == 0) {
            return true;
        }
		return false;
	}

}
