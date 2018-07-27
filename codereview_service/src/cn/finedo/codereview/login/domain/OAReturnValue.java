package cn.finedo.codereview.login.domain;

import cn.finedo.common.domain.BaseDomain;

public class OAReturnValue<T> extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	private ReturnValue<T> jsondata;

	public ReturnValue<T> getJsondata() {
		return jsondata;
	}

	public void setJsondata(ReturnValue<T> jsondata) {
		this.jsondata = jsondata;
	}
}
