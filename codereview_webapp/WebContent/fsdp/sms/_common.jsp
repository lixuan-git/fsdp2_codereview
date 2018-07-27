<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
	<tr>
		<th class="require" width="100" valign=top>* 接收号码：</th>
		<td>
			<font color=blue>多个号码用逗号 ( , ) 分开</font></br>
			<fsdp:textarea name="phoneno" id="phoneno" value="${sms.phoneno }" datatype="Require" msg="接收号码不能为空，请填写【接收号码】"></fsdp:textarea>
		</td>
	</tr>
	<tr>
		<th class="require" valign=top>短信内容：</th>
		<td>
			<fsdp:textarea value="${sms.smscontent}" id="smscontent" name="smscontent" datatype="Require" msg="短信内容不能为空，请填写【短信内容】"></fsdp:textarea>
		</td>
	</tr>
	<tr>
		<th>定时发送时间：</th>
		<td>
			<fsdp:date value="${sms.effdate }" id="effdate" name="effdate" onfocus="WdatePicker({minDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"></fsdp:date>
		</td>
	</tr>
</table>
<script type="text/javascript">
function save() {
	var form = $("#ajaxForm");
	if (Validator.Validate(form[0], 4)) {
		var options = {
			url : form.attr("action"),
			success : callback,
			type : 'POST',
			dataType : "json",
			clearForm : false
		};
		FINEDO.Mode.create();
		form.ajaxSubmit(options);
	}
}
function callback(json) {
	FINEDO.Mode.destroy();
	message('提示', json.resultdesc);
	if(json.resultcode == FINEDO.RESULTCODE.success){
		$("#ajaxForm").clearForm();
	}
}
</script>
