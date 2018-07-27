<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>新增密码规则</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${validator_js }

<script type="text/javascript">
function save(){
	var form = $("#ajaxForm");
	if(Validator.Validate(form[0],4)){
		var options = {     
	        url:	   form.attr("action"),
	        success:   callback,
	        type:      'POST',
	        dataType:  "json",
		    clearForm: false
	    };	     
		form.ajaxSubmit(options);
	}
}
function callback(json){
	if(json.resultcode == "SUCCESS"){
		$.messager.alert('提示',json.resultdesc);
		//location.href = "${ctx }/fsdp/jsp/securityconf/list.jsp";
		//location.reload();
	}else{
		$.messager.alert('提示','操作失败！');
	}
}
</script>
</head>
<body class="easyui-layout">
<form method="post" action="${ctx }/finedo/securityconf/insertSecurityconf" id="ajaxForm" name="ajaxForm">
<div region="center" style="padding:5px;" border="false">

	<div class="easyui-tabs" style="width:700px;height:250px" fit="true">
		<div title="登录密码规则" style="padding:10px">
		   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
			 <tr>
			    <th width="150">密码类型：</th>
			    <td>
			    	<fsdp:text id="passwdtype" name="passwdtype" value="" datatype="Require" msg="请输入[密码类型](备注：登陆密码、交易密码)"></fsdp:text><br/>
			    </td>
			 </tr>
			 <tr>
			    <th>有效天数：</th>
			    <td>
			    	<fsdp:text id="validday" name="validday" value="" datatype="Integer" msg="请输入[有效天数] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>密码最小长度：</th>
			    <td>
			    	<fsdp:text id="minlength" name="minlength" value="" datatype="Integer" msg="请输入[密码最小长度] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>密码最大长度：</th>
			    <td>
			    	<fsdp:text id="maxlength" name="maxlength" value=""  datatype="Integer" msg="请输入[密码最大长度] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>特殊字符最小数量：</th>
			    <td>
			    	<fsdp:text id="specialnum" name="specialnum" value="" datatype="Integer" msg="请输入[特殊字符最小数量] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>大写字母最小数量：</th>
			    <td>
			    	<fsdp:text id="capitalnum" name="capitalnum" value=""  datatype="Integer" msg="请输入[大写字母最小数量] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>小写字母最小数量：</th>
			    <td>
			    	<fsdp:text id="lowercasenum" name="lowercasenum" value=""  datatype="Integer" msg="请输入[小写字母最小数量] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>数字最小数量：</th>
			    <td>
			    	<fsdp:text id="digitalnum" name="digitalnum" value=""  datatype="Integer" msg="请输入[数字最小数量] 纯数字型"></fsdp:text><br/>
			    </td>
			  </tr>
			  <tr>
			    <th>备注：</th>
			    <td>
			    	<fsdp:textarea id="remark" name="remark" value="备注信息"></fsdp:textarea>
			    </td>
			  </tr>
			</table>
		</div>
	</div>

</div>  
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>  
</div>
</form>
</body>
</html>
