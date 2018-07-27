/**
 *	项目成员 
 */
package cn.finedo.codereview.common.pojo;

/**
 * @author taoge
 *
 */
public class DopsSvncodeviewgroupuser {
	
	//员工工号
	private String userid;
	
	//姓名
	private String username;
	
	//email
	private String email;

	//项目名称
	private String projectname;
	
	//小组创建人
	private String createperson;
	
	//小组id 
	private String cgid;
	
	//小组人数
	private int grouppeonum;
	
	//状态
	private String userstate;
	
	//添加时间
    private String addtime;
    
    //剔出时间
    private String remtime;
    
    //添加人
    private String addoptuser;
    
    //移除人
    private String remoptuser;
    
    //员工编号
    private String usercode;
    
    //项目id
    private String cpid;
	
	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getRemtime() {
		return remtime;
	}

	public void setRemtime(String remtime) {
		this.remtime = remtime;
	}

	public String getAddoptuser() {
		return addoptuser;
	}

	public void setAddoptuser(String addoptuser) {
		this.addoptuser = addoptuser;
	}

	public String getRemoptuser() {
		return remoptuser;
	}

	public void setRemoptuser(String remoptuser) {
		this.remoptuser = remoptuser;
	}

	public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }

    public int getGrouppeonum() {
		return grouppeonum;
	}

	public void setGrouppeonum(int grouppeonum) {
		this.grouppeonum = grouppeonum;
	}

	public String getCgid() {
		return cgid;
	}

	public void setCgid(String cgid) {
		this.cgid = cgid;
	}

	public String getCreateperson() {
		return createperson;
	}

	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
