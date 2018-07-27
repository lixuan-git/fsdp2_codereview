package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

/**
 * @author 刘青成
 *TODO 优秀代码推荐
 */
public class DopsSvncommend  extends BaseDomain{
    private static final long serialVersionUID = 1L;
	/**
	 * TODO 文件ID
	 */
	private String commendid;
	/**
	 * TODO 推荐人
	 */
	private String commend;
	/**
	 * TODO 项目名称
	 */
	private String groupname;
	/**
	 * TODO 所属部门
	 */
	private String orgcoed;
	/**
	 * TODO 小组成员
	 */
	private String groupuser;
	/**
	 * TODO 推荐理由
	 */
	private String commendreason;
	/**
	 * TODO 推荐代码
	 */
	private String code;
	/**
	 * TODO 票数
	 */
	private String pollnumber;
	/**
	 * TODO 创建人
	 */
	private String creatuser;
	/**
	 * TODO 创建时间
	 */
	private String creattime;
	/**
	 * TODO 开始时间
	 */
	private String begintime;
	/**
	 * TODO 结束时间
	 */
	private String endtime;
	public String getCommendid() {
		return commendid;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setCommendid(String commendid) {
		this.commendid = commendid;
	}
	public String getCommend() {
		return commend;
	}
	public void setCommend(String commend) {
		this.commend = commend;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getOrgcoed() {
		return orgcoed;
	}
	public void setOrgcoed(String orgcoed) {
		this.orgcoed = orgcoed;
	}
	public String getGroupuser() {
		return groupuser;
	}
	public void setGroupuser(String groupuser) {
		this.groupuser = groupuser;
	}
	public String getCommendreason() {
		return commendreason;
	}
	public void setCommendreason(String commendreason) {
		this.commendreason = commendreason;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getPollnumber() {
		return pollnumber;
	}
	public void setPollnumber(String pollnumber) {
		this.pollnumber = pollnumber;
	}
	public String getCreatuser() {
		return creatuser;
	}
	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

}
