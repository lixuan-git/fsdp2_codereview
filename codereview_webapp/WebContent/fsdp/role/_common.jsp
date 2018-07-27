<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	<div class="easyui-tabs" style="width:700px;height:400px;" fit="true" border="false">
		<div title="基本信息" style="padding:10px">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  <tr>
		    <th width="100">角色类型：</th>
		    <td width="40%">
		    	<c:set value="组织岗位角色" var="roletype"></c:set>
		    	<c:if test="${!empty role.sysrole.roletype}">
		    	<c:set value="${role.sysrole.roletype}" var="roletype"></c:set>
		    	</c:if>
		    	<fsdp:radiobox datasource="岗位角色类型" name="roletype" selectedvalue="${roletype}" onclick="switchType()"></fsdp:radiobox>
	    	</td>
		    <th width="100" id="orgTd">组织节点：</th>
		    <td width="40%">
		    	<input type="hidden" value="${role.sysrole.orgid}" name="orgid" id="orgid">
		    	<fsdp:text value="${role.sysrole.orgname}" id="orgname" name="orgname" onclick="chooseOrg()"></fsdp:text>
		    </td>
		  </tr>
		 
		  <tr>
		    <th class="require">* 角色名称：</th>
		    <td ><fsdp:text id="rolename" name="rolename" value="${role.sysrole.rolename}" datatype="Require"  msg="角色名称不能为空，请填写【角色名称】"></fsdp:text></td>
		    <th>限定人数：</th>
		    <td ><fsdp:text id="usercount" name="usercount" maxlength="6" value="${role.sysrole.usercount}" onkeypress="checkInteger(this)" onblur="checkInteger(this)"></fsdp:text></td>
		    
		  </tr>
		 <tr>
		 
		 	<th>角色级别：</th>
		 	<td>
		 	<c:set value="十岗" var="rolelvl"></c:set>
		    	<c:if test="${!empty role.sysrole.rolelvl}">
		    	<c:set value="${role.sysrole.rolelvl}" var="rolelvl"></c:set>
		    	</c:if>
		       <fsdp:radiobox datasource="岗位角色级别" name="rolelvl" selectedvalue="${rolelvl}"></fsdp:radiobox>
			</td>	
		    <th>状态：</th>
		    <td>
		    <c:set value="有效" var="state"></c:set>
		    	<c:if test="${!empty role.sysrole.state}">
		    	<c:set value="${role.sysrole.state}" var="state"></c:set>
		    	</c:if>
		    	<fsdp:radiobox datasource="岗位角色状态" name="state" selectedvalue="${state}" ></fsdp:radiobox>
		    </td>
			
		  </tr>
		 
		  <tr>
		    <th>职责描述：</th>
		    <td colspan="3">
			<fsdp:textarea name="dutydesc" style="height:50px;" id="dutydesc" value="${role.sysrole.dutydesc}"></fsdp:textarea>
			</td>
		  </tr>
		  <tr>
		    <th>备注：</th>
		    <td colspan="3">
		    <fsdp:textarea name="remark" style="height:50px;" id="remark" value="${role.sysrole.remark}"></fsdp:textarea>
			</td>
		  </tr>
		</table>
		</div>
		<div title="角色权限" style="padding:10px">
			<div id="rightview" class="easyui-tabs" style="width:auto;height:350px;" tabPosition="left" >
				<div title="模块权限" style="padding:10px">
					<ul class="easyui-tree" data-options="animate:true,lines:true,checkbox:true" id="righttree">
					<c:forEach var="userright" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}">
					<c:if test="${userright.parentnodeid eq '0' and (userright.isnavigation eq '是' or (userright.isnavigation eq '否' and !empty userright.rightid))}">
					<c:set value="false" var="ischecked"></c:set>
					<c:forEach items="${role.rolerightslist}" var="roleright">
						<c:if test="${userright.nodeid eq roleright.nodeid}">
						<c:set value="true" var="ischecked"></c:set>
						</c:if>
					</c:forEach>
					
				    <li data-options="id:'${userright.nodeid}',name:'${userright.nodename }'<c:if test="${ischecked eq 'true'}">,checked:true</c:if>">
						<span>${userright.nodename }</span>
						<ul>
							<c:forEach var="userright2" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}">
							<c:set value="false" var="ischecked"></c:set>
							<c:forEach items="${role.rolerightslist}" var="roleright">
								<c:if test="${userright2.nodeid eq roleright.nodeid}">
								<c:set value="true" var="ischecked"></c:set>
								</c:if>
							</c:forEach>
							<c:if test="${userright2.parentnodeid eq userright.nodeid and (!empty userright2.rightid)}">
							<li data-options="id:'${userright2.rightid}',name:'${userright2.nodename }'<c:if test="${ischecked eq 'true'}">,checked:true</c:if>">
								<span>${userright2.nodename }</span>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
			        </c:forEach>
				  </ul>
				</div>
				<!-- 
				<div title="数据权限" style="padding:10px">
					<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
				</div>
				<div title="栏目权限" style="padding:10px">
					This is the help content.
				</div>
				 -->
			</div>

		</div>
	</div>
	<input type="hidden" id="rightid" name="rightid"/>
<script type="text/javascript">
function save(){
	var form = $("#ajaxForm");
	getChecked();
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
        	window.parent.location.href='${ctx}/fsdp/role/newlist.jsp';
        }  
    });  
}

function getChecked(){
	var nodes = $('#righttree').tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	$("#rightid").val(s);
}

function switchType(){
	var val=$('input:radio[name="roletype"]:checked').val();
	if(val == '组织岗位角色'){
		$("#orgTd").attr("class","require").html("* 组织节点：");
		$("#orgname").attr("datatype","Require").attr("msg","组织节点不能为空，请选择【组织节点】").css('background-color',"").attr("disabled",false);
	}else{
		$("#orgTd").attr("class","").html("组织节点：");
		$("#orgname").attr("datatype","").attr("msg","").css('background-color',"#ddd").attr("disabled",true);
	}
	
}
switchType();

function checkInteger(input){
	var obj = $(input);
	obj.val(obj.val().replace(/[^\d.]/g,""));//清除“数字”和“.”以外的字符   
	obj.val(obj.val().replace(/^\./g,""));//验证第一个字符是数字而不是.   
	obj.val(obj.val().replace(/\.{2,}/g,"."));//只保留第一个. 清除多余的.  
	obj.val(obj.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
}
function chooseOrg(){
	finedo.choose.singleOrg(function(data){
		$("#orgid").val(data.id);
		$("#orgname").val(data.name);
	});
}
</script>