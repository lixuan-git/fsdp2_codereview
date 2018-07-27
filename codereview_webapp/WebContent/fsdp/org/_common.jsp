<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  <tr>
		  	<th class="require">* 上级组织：</th>
		    <td>
		    	<input type="hidden" value="${org.parentorgid}" name="parentorgid" id="parentorgid">
		    	<fsdp:text value="${org.parentorgname}" id="parentorgname" name="parentorgname" onclick="chooseParentOrg()" datatype="Require"  msg="上级组织不能为空，请选择【上级组织】" ></fsdp:text>
		    </td>
		  </tr>
		   <tr>
			<th class="require">* 机构类型：</th>
		    <td >
		    	<fsdp:select datasource="组织机构类型" name="orgtype" selectedvalue="${org.orgtype}"  datatype="Require"  msg="机构类型不能为空，请选择【机构类型】" ></fsdp:select>
		    </td>
		  </tr>
		  <tr>
			<th class="require">* 机构名称：</th>
		    <td >
		    <fsdp:text value="${org.orgname}" id="orgname" name="orgname" datatype="Require"  msg="机构名称不能为空，请填写【机构名称】" ></fsdp:text>
		    </td>
		  </tr>
		 <tr>
		    <th width="100">联系人：</th>
		    <td>
		    	<fsdp:text value="${org.linkman}" id="linkman" name="linkman" />
		    </td>
		    </tr>
		  <tr>
			<th>联系电话：</th>
		    <td >
		    	<fsdp:text name="phoneno" id="phoneno" value="${org.phoneno}"/>
		    </td>
		  </tr>
		  <tr>
			<th>联系地址：</th>
		    <td >
		    	<fsdp:text  name="address" id="address" value="${org.address}"  />
		    </td>
		  </tr>
		  <tr>
			<th>机构介绍：</th>
		    <td >
		    	<fsdp:text  name="orgdesc" id="orgdesc" value="${org.orgdesc}" />
		    </td>
		  </tr>
		  
		  <tr>
		    <th>备注：</th>
		    <td>
		    	<textarea style="height: 50px" name="remark" id="remark" >${org.remark}</textarea>
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
	$.messager.confirm('提示', json.resultdesc+'，是否返回列表页', function(which){  
        if (which){ 
        	window.parent.location.href='${ctx}/fsdp/org/list.jsp';
        }  
    });  
	
}
function chooseParentOrg(){
	finedo.choose.singleOrg(function(data){
		$("#parentorgid").val(data.id);
		$("#parentorgname").val(data.name);
	});
}
</script>

