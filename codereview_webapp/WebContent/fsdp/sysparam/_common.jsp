<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		  <th width="100" class="require">* 参数类型：</th>
		    <td  width="40%"><input type="text" name="configtypeid" id="configtypeid"  value="${sysparam.configtypeid }" 
		      dataType="Require"  msg="参数类型不能为空，请填写【参数类型】" /></td>
		    <th width="100" class="require">* 参数名称：</th>
		    <td width="40%">
				<input type="text"  value="${sysparam.paramname }" id="paramname" name="paramname" 
				dataType="Require"  msg="参数名称不能为空，请填写【参数名称】" />
			

		  </tr>
		 <tr>
		    <th class="require">* 参数值：</th>
		    <td >
				<input type="text" value="${sysparam.paramvalue }" id="paramvalue" name="paramvalue" 
				dataType="Require"  msg="参数值不能为空，请填写【参数值】" />
			<th class="require">* 数据类型：</th>
		    <td ><input type="text" name="datatype" id="datatype"  value="${sysparam.datatype }" 
		      dataType="Require"  msg="数据类型不能为空，请填写【数据类型】" /></td>

		  </tr>
		  <tr>
		    <th>备注：</th>
		    <td colspan="3"><textarea style="height: 50px" name="remark">${sysparam.remark }</textarea>
			</td>
		  </tr>
		</table>
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
	$.messager.confirm('提示', json.resultdesc+'是否返回列表页', function(which){  
        if (which){ 
        	window.parent.location.href='${ctx}/fsdp/sysparam/list.jsp';
        }  
    });  
	
}
</script>

