package cn.finedo.codereview.common.pojo;

import cn.finedo.common.domain.BaseDomain;

/**      
* @Description: 代码评审用户表
* @author zhusf@finedo.com   
* @date 2018年5月10日 下午12:24:18   
* @version v1.0
*   
*/ 
public class DopsSvncodeviewuser extends BaseDomain{
	private static final long serialVersionUID = 1L;
	/**   
	* @Fields cuid : 代码评审用户表主键 
	*/ 
	private String cuid;
	/**   
	* @Fields usercode : 用户编号 
	*/ 
	private String usercode;
	/**   
	* @Fields email : 邮箱地址   
	*/ 
	private String email;
	public String getCuid() {
		return cuid;
	}
	public void setCuid(String cuid) {
		this.cuid = cuid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
