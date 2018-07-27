<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		  <th width="80" class="require">* 节点名称：</th>
		    <td><input type="text" name="nodename" id="nodename"  value="${node.nodename }" 
		      dataType="Require"  msg="节点名称不能为空，请填写【节点名称】" />
		      <input type="hidden" name="viewid" value="WEB应用"/>
		      </td>
		      </tr>
		     <tr>
		    <th class="require">* 上级节点：</th>
		    <td>
		   <select id="parentnodeid" name="parentnodeid" style="width: 100%" dataType="Require"  msg="上级节点不能为空，请选择【上级节点】">
		   			<option value="">---选择上级节点---</option>
		   			<option value="0">作为一级节点</option>
		   			<c:forEach items="${nodelist }" var="node1">
		   				<c:if test="${node1.parentnodeid eq '0' }">
		   					<option value="${node1.nodeid }" <c:if test="${node1.nodeid eq node.parentnodeid }">selected="selected"</c:if>>${node1.nodename }</option>
		   				    <c:forEach items="${nodelist }" var="node2">
		   				    	<c:if test="${node2.parentnodeid eq node1.nodeid }">
		   				    		<option value="${node2.nodeid }" <c:if test="${node2.nodeid eq node.parentnodeid }">selected="selected"</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;┗${node2.nodename }</option>
		   				    		<c:forEach items="${nodelist }" var="node3">
				   				    	<c:if test="${node3.parentnodeid eq node2.nodeid }">
				   				    		<option value="${node3.nodeid }" <c:if test="${node3.nodeid eq node.parentnodeid }">selected="selected"</c:if>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗${node3.nodename }</option>
				   				    	</c:if>
				   				    </c:forEach>
		   				    	
		   				    	</c:if>
		   				    </c:forEach>
		   				
		   				</c:if>
		   				
		   			</c:forEach>
		   			
		   		</select>
		    	
		  </tr>
		 <tr>
		    <th class="require">所属权限：</th>
		    <td>
		   
		    	<select id="rightid" name="rightid" style="width: 100%" dataType="Require"  msg="所属权限不能为空，请选择【所属权限】">
		   			<option value="">---选择所属权限---</option>
		   			<c:forEach items="${modulelist }" var="module">
		   			<optgroup label="${module.modulename }">
		   				<c:forEach items="${modulerightlist }" var="right">
		   				<c:if test="${right.moduleid eq module.moduleid }">
		   				<option value="${right.rightid }" <c:if test="${right.rightid  eq node.rightid }">selected="selected"</c:if>>${right.rightname }</option>
		   				</c:if>
		   				</c:forEach>
		   			</optgroup>
		   			</c:forEach>
		   			
		   			
		   		</select>
		  </tr>
		  <tr>
		    <th>权限入口：</th>
		    <td >
				<input type="text" value="${node.rightentry}" id="rightentry" name="rightentry" 
				dataType="Require"  msg="权限入口不能为空，请填写【权限入口】" /></td>
				</tr>
		 <tr>
		 <tr>
		    <th>用于导航：</th>
		    <td >
		    
				<label><input type="radio" value="否" id="isnavigation" name="isnavigation" <c:if test="${node.isnavigation eq '否' or empty node.isnavigation}">checked="checked"</c:if>/> 否</label>
				<label><input type="radio" value="是" id="isnavigation" name="isnavigation" <c:if test="${node.isnavigation eq '是'}">checked="checked"</c:if>/> 是</label>
				</td>
				</tr>
		 <tr>
		 <tr>
		    <th class="require">* 排序编号：</th>
		    <td >
				<input type="text" value="${node.orderseq}" id="orderseq" name="orderseq" 
				dataType="Require"  msg="排序编号不能为空，请填写【排序编号】" /></td>
				</tr>
		 
		  <tr>
		    <th>备注：</th>
		    <td colspan="2"><textarea style="height: 50px" name="remark">${node.remark }</textarea>
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

