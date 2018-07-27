<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		  <th width="80" class="require">* 模块名称：</th>
		    <td><input type="text" name="modulename" id="modulename"  value="${module.modulename }" 
		      dataType="Require"  msg="模块名称不能为空，请填写【模块名称】" />
		      <input type="hidden" name="packageid" value="${module.packageid }"/>
		      </td>
		      </tr>
		 <tr>
		    <th class="require">模块状态：</th>
		    <td>
		   
		    	<fsdp:select datasource="功能模块状态" name="state"  selectedvalue="${module.state }" style="width:100%"></fsdp:select>
		  </tr>
		 <tr>
		    <th class="require">* 当前版本：</th>
		    <td >
				<input type="text" value="${module.version}" id="version" name="version" 
				dataType="Require"  msg="当前版本不能为空，请填写【当前版本】" /></td>
				</tr>
		 <tr>
			<th>模块介绍：</th>
		    <td ><textarea  name="moduledesc" id="moduledesc" style="height: 50px" 
		      >${module.moduledesc }</textarea></td>

		  </tr>
		  <tr>
		    <th>备注：</th>
		    <td colspan="2"><textarea style="height: 50px" name="remark">${module.remark }</textarea>
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
        	window.history.go(-1);
        }  
    });  
	
}
</script>

