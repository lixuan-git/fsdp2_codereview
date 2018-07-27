package cn.finedo.codereview.entity;

/**      
* @Description: 报表传参实体类
* @author zhusf@finedo.com   
* @date 2018年5月29日 下午5:51:10   
* @version v1.0
*   
*/ 
public class ReportParaEntity {
	/**   
	* @Fields validatestr : 验证id
	*/ 
	private String validatestr;
	/**   
	* @Fields userid : 用户编号   
	*/ 
	private String userid;
	public String getValidatestr() {
		return validatestr;
	}
	public void setValidatestr(String validatestr) {
		this.validatestr = validatestr;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	

}
