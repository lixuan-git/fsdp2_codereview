package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

/**
 * @author 刘青成
 *TODO 项目创建
 */
public class DopsSvncodeviewproject extends BaseDomain{
    private static final long serialVersionUID = 1L;
    /**
     * TODO 项目ID
     */
    private String projectid;
    /**
     * TODO 项目名称
     */
    private String projectname;
    /**
     * TODO 商务经理
     */
    private String busimng;
    /**
     * TODO 项目经理
     */
    private String projectmng;
    /**
     * TODO 立项时间
     */
    private String createtime;
    /**
     * TODO 归属部门
     */
    private String orgcode;
    /**
     * TODO 项目描述
     */
    private String projectdescribe;
    /**
     * TODO 配置库类型
     */
    private String repotype;
    /**
     * TODO 配置库路径
     */
    private String svnpath;
    /**
     * TODO 项目编号
     */
    private String projectcode;
    /**
     * TODO 客户ID
     */
    private String  custid;
    /**
     * TODO 客户名称
     */
    private String  custname;
    /**
     * TODO 项目状态  --是否结项
     */
    private String  status;
    /**
     * TODO 项目状态名称
     */
    private String  statusname;
    /**
     * TODO 项目状态修改时间
     */
    private String  statustime;
    /**
     * TODO 项目类型
     */
    private String  projecttype;
    private String addoptuser;
    private String cgid;
    public String getCgid() {
		return cgid;
	}
	public void setCgid(String cgid) {
		this.cgid = cgid;
	}
	public String getAddoptuser() {
		return addoptuser;
	}
	public void setAddoptuser(String addoptuser) {
		this.addoptuser = addoptuser;
	}
	private String  svnaddr;
	public String getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public String getStatustime() {
		return statustime;
	}
	public void setStatustime(String statustime) {
		this.statustime = statustime;
	}
	public String getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getBusimng() {
		return busimng;
	}
	public void setBusimng(String busimng) {
		this.busimng = busimng;
	}
	public String getProjectmng() {
		return projectmng;
	}
	public void setProjectmng(String projectmng) {
		this.projectmng = projectmng;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getProjectdescribe() {
		return projectdescribe;
	}
	public void setProjectdescribe(String projectdescribe) {
		this.projectdescribe = projectdescribe;
	}
	public String getRepotype() {
		return repotype;
	}
	public void setRepotype(String repotype) {
		this.repotype = repotype;
	}
	public String getSvnaddr() {
		return svnaddr;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
