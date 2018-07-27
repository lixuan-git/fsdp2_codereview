package cn.finedo.codereview.login.domain;


import java.util.List;

import cn.finedo.common.domain.BaseDomain;

public class ReturnValue<T> extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	private String retCode;
	private String retDesc;
	private T retVal;
	private String content;
	private String totalrecords;
	private String roleid;
	private List<T> list=null;
	public List<T> getList() {
		return list;
	}
	
	private String failNum;
	
	

	public String getFailNum() {
		return failNum;
	}





	public void setFailNum(String failNum) {
		this.failNum = failNum;
	}





	public String getRoleid() {
		return roleid;
	}





	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}





	public void setList(List<T> list) {
		
		this.list = list;
		if(list!=null){
			this.totalrecords=String.valueOf(list.size());
		}
	}
	
	
	public List<T> getList(String classname){
		return list;
	}
	
	
	public ReturnValue(){};
	
	public ReturnValue(String retCode,String retDesc){
		this.retCode=retCode;
		this.retDesc=retDesc;
	}
	
	public String getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(String totalrecords) {
		this.totalrecords = totalrecords;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	


	public void setRetVal(T retVal) {
		this.retVal = retVal;
	}
	public T getRetVal() {
		return retVal;
	}
}
