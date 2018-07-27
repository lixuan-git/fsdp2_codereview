<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>综合表单-单列布局</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${datepicker_js }
${colorpicker_js }
${ueditor_js }
${validator_js }
<script type="text/javascript">
function save(){
	var form = $("#ajaxForm");
	if(Validator.Validate(form[0],4)){
		//var options = {     
	    //    url:	   form.attr("action"),
	    //    success:   callback,
	    //   type:      'POST',
	    //    dataType:  "json",
		//    clearForm: false
	    //};	     
		//form.ajaxSubmit(options);
		alert("表单校验通过！");
	}
}
function callback(json){
	if(json.statusCode==Return.statusCode.ok){
		$.messager.alert('提示',json.message);
		parent.$('#treegrid').treegrid("reload");
	}else{
		$.messager.alert('提示',json.message);
	}
}
</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="综合表单-单列布局" style="width:auto;padding:10px;" fit="true">
	<form method="post" action="${ctx }/finedo/user/add" id="ajaxForm" >		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		    <th width="100" class="require">* 必填较验：</th>
		    <td >
		    	<fsdp:text id="text1" name="text1" datatype="Require" msg="请填写内容"></fsdp:text></td>
		    
			<th width="100">日期较验：</th>
		    <td >
		    	<fsdp:date id="date1" name="date1" datatype="Date" msg="日期格式不正确，请选择日期"></fsdp:date>
		    </td>

		  </tr>
		  <tr>
		    <th>邮箱格式较验：</th>
		    <td >
		    	<fsdp:text id="text1" name="text1" datatype="Email" msg="邮箱格式不正确，请填写正确的邮箱"></fsdp:text></td>
		    
			<th>URL格式较验：</th>
		    <td >
		    	<fsdp:text id="date1" name="date1" datatype="Url" msg="URL格式不正确，请填写正确的URL"></fsdp:text>
		    </td>

		  </tr>
		  <tr>
		    <th>QQ格式校验：</th>
		    <td >
		    	<fsdp:text id="text1" name="text1" datatype="QQ" msg="QQ格式不正确，请填写正确的QQ"></fsdp:text></td>
		    
			<th>手机格式较验：</th>
		    <td >
		    	<fsdp:text id="date1" name="date1" datatype="Mobile" msg="手机格式不正确，请填写正确的手机号码"></fsdp:text>
		    </td>

		  </tr>
		  <tr>
		    <th>长度区间较验：</th>
		    <td >
		    	<fsdp:text id="text1" name="text1" datatype="LimitB" msg="字符长度在5-10之间" min="5" max="10"></fsdp:text></td>
		    
			<th>数值区间校验：</th>
		    <td >
		    	<fsdp:text id="date1" name="date1" datatype="Range" msg="数值大小长度在20-30之间" min="20" max="30"></fsdp:text>
		    </td>

		  </tr>
		  
		</table>
	</form>
  </div>
</div>  
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>  
</div>  
</body>
</html>
