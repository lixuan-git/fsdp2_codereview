<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		  <th width="80" class="require">* 权限名称：</th>
		    <td><input type="text" name="rightname" id="rightname"  value="${rights.rightname }" 
		      dataType="Require"  msg="权限名称不能为空，请填写【权限名称】" />
		      </td>
		      </tr>
		 <tr>
		    <th class="require">* 所属模块：</th>
		    <td>
		   		<select id="moduleid" name="moduleid" style="width: 100%" dataType="Require"  msg="所属模块不能为空，请填写【所属模块】">
		   			<option value="">---选择所属模块---</option>
		   			<c:forEach items="${modulelist }" var="module">
		   				<option value="${module.moduleid }" <c:if test="${module.moduleid eq rights.moduleid }">selected="selected"</c:if>>${module.modulename }</option>
		   			</c:forEach>
		   			
		   		</select>
		  </tr>
		 
		 <tr>
			<th>权限说明：</th>
		    <td ><textarea  name="remark" id="remark" style="height: 50px" 
		      >${rights.remark }</textarea></td>

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

