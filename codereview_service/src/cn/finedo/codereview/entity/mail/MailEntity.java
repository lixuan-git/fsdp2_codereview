package cn.finedo.codereview.entity.mail;

import cn.finedo.codereview.svnlog.domain.SvnlogProjectDomain;

/**      
* @Description: 邮件封装实体类
* @author zhusf@finedo.com   
* @date 2018年5月11日 下午5:17:40   
* @version v1.0
*   
*/ 
public class MailEntity {
	/**   
	* @Fields emailaddress : 邮箱地址 
	*/ 
	private String emailaddress;
	/**   
	* @Fields usercode : 用户编号  
	*/ 
	private String usercode;
	/**   
	* @Fields subject : 邮件主题   
	*/ 
	private String subject;
	/**   
	* @Fields svnlogProjectDomain : 邮件正文   
	*/ 
	private SvnlogProjectDomain svnlogProjectDomain;
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public SvnlogProjectDomain getSvnlogProjectDomain() {
		return svnlogProjectDomain;
	}
	public void setSvnlogProjectDomain(SvnlogProjectDomain svnlogProjectDomain) {
		this.svnlogProjectDomain = svnlogProjectDomain;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	@Override
	public String toString() {
		return "MailEntity [emailaddress=" + emailaddress + ", usercode=" + usercode + ", subject=" + subject
				+ ", svnlogProjectDomain=" + svnlogProjectDomain + "]";
	}
	

	

}
