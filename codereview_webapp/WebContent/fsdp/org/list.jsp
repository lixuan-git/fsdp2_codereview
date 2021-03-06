<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<!DOCTYPE html>
<html>
<head>
<title></title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_dialog_js }
${finedo_tree_js }
${finedo_grid_js }
${finedo_commonui_js }
<script>
	var orgid="";
	$(document).ready(function() {
		var a=document.getElementsByName('demand');
	 	a[1].checked=true;
		finedo.getTree('treediv',{
			url:'${ctx}/finedo/organization/queryorgtree',
			async:true,
			selecttype:'multi',
			cascadetype:"down",
			data:{"id":"0"},
			onclick:function(data){
				var type= $("input[name='demand']:checked").val();
				orgid=data.id;
				var param = {orgnode:data.id,
							remark:type
							};
				finedo.getgrid("datagrid").query(param);
			}
		});
	});
	var a=0;
	function importcallback(jsondata) {
		a=a+1;
		if(length==a){
			a=0;
			window.location.reload(true);
		}
		
	}
	function formatOperation(row){
		var operation = '<a href="#" onclick="showModifyDialog(\'' + row.user.userid + '\');">[编辑]</a>&nbsp;';
		operation += '<a href="#" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/user/deleteUser\',\''+row.user.userid+'\')">[删除]</a>&nbsp;';
		 var user='${LOGINDOMAIN_KEY.sysuser.userid}';
		 if(user=="superuser"){
			 operation += '<a href="#" onclick="doPassword(\'' + row.user.userid + '\', \''+row.user.usercode+'\', \''+row.user.phoneno+'\');">[密码重置]</a>&nbsp;';
		 }
		if("锁定" == row.user.state){
			operation += '<a href="#" onclick="doLockUser(\'unlock\',\''+row.user.userid+'\')">[解锁]</a>&nbsp;';
		}else{
			operation += '<a href="#" onclick="doLockUser(\'lock\',\''+row.user.userid+'\')">[锁定]</a>&nbsp;';
			
		}
		return operation;
	}

	// 单元格重载
	function formatUserid(row){
		return '<a href="#" onclick="showDetailDialog(\'' + row.user.userid + '\');">'+row.user.usercode+'</a>&nbsp;';
	}

	// 单元格重载
	function formatPersonname(row){
		return '<a href="#" onclick="showDetailDialog(\'' + row.user.userid + '\');">'+row.user.personname+'</a>&nbsp;';
	}

	// 行信息展开
	function doExpandRow(data){
			return 	"<div class='finedo-data'><ul>" +
					"<li><span class='finedo-data-name'>用户编号</span><span class='finedo-data-con'>" + data.user.userid + "</span></li>" +
					"<li><span class='finedo-data-name'>姓名</span><span class='finedo-data-con'>" + data.user.personname + "</span></li>" +
					"<li><span class='finedo-data-name'>状态</span><span class='finedo-data-con'>" + data.user.state + "</span></li>" +
					"<li><span class='finedo-data-name'>创建时间</span><span class='finedo-data-con'>" + data.user.createtime + "</span></li>" +
					"<li><span class='finedo-data-name'>失效时间</span><span class='finedo-data-con'>" + data.user.expdate + "</span></li>" +
					"<li><span class='finedo-data-name'>性别</span><span class='finedo-data-con'>" + data.user.gender + "</span></li>" +
					"<li><span class='finedo-data-name'>身份证号码</span><span class='finedo-data-con'>" + finedo.fn.replaceNull(data.user.idnumber) + "</span></li>" +
					"<li><span class='finedo-data-name'>邮箱</span><span class='finedo-data-con'>" +  finedo.fn.replaceNull(data.user.email) + "</span></li>" +
					"<li><span class='finedo-data-name'>地址</span><span class='finedo-data-con'>" +  finedo.fn.replaceNull(data.user.address) + "</span></li>" +
				  	"</ul></div>";
	}
	/************** 表格操作函数定义****************/

	/************** 查询函数定义****************/
	function doQuery() {
		opquerycond();
		var param = {usercode: $('#usercode').val(), personname: $('#personname').val(), state: $('#state').val(), phoneno: $('#phoneno').val()};
		finedo.getgrid("datagrid").query(param);
	}

	function doQueryCancel() {
		opquerycond();
	}

	function doQueryFast() {
		var text=$('#query-box-text').val();
		var param = {personname:text};
		finedo.getgrid("datagrid").query(param);
	}

	function doPassword(userid,usercode,phoneno) {
		var param = {
				userid:userid,
				usercode:usercode,
				phoneno:phoneno
				};
		$.ajax({
			dataType:'json',
			type:"post",
			url:"${ctx }/finedo/user/password",
			data: param,     
			success:function(data){
				finedo.message.info("密码重置成功", "密码重置");
			
			}
		}); 

	}

	/************** 查询函数定义****************/
	function doLockUser(locktype, userid){
		finedo.action.doCommand("${ctx}/finedo/user/lockUser?userid="+userid+"&locktype="+locktype, function(data){
			doRefresh();
		}, true);
	}
	/************** 增加、导入、修改、删除、导出函数定义****************/
	function showAddDialog() {
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'新增信息',
			'url':'${ctx}/finedo/user/addpage'
		});
	}

	function showModifyDialog(userid) {
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'修改信息',
			'url':'${ctx}/finedo/user/queryUserModify?userid=' + userid
		});
	}

	function showDetailDialog(userid) {
		finedo.dialog.show({
			width:850,
			height:550,
			'title':'详情信息',
			'url':'${ctx}/finedo/user/queryUserDetail?userid=' + userid
		});
	}

	function doRefresh() {
		$("#datagrid").grid().refresh();
	}

	function doExport() {
		var param=finedo.getgrid("datagrid").getqueryparams();
		$("#downiframe").attr('src', '${ctx }/finedo/user/exportUser' + (param ? '?'+$.param(param) : ''));
	}
	
	function opquerycond(){
		var divdisplay=$('#advanced-search-con').css('display');
		
		if(divdisplay == 'block'){
			$('#advanced-search-con').css('display', 'none');
			$('#advanced-search').attr('class', 'query-as-link');
		
		}else{
			$('#advanced-search-con').css('display', 'block');
			$('#advanced-search').attr('class', 'query-as-hover');
		}
	}
	var length;
	function dodelete(){
		var sitems = finedo.getTree('treediv').getselecteditems();
		if(sitems==""){
			finedo.message.info("你未选取组织机构!", '提示');
			return;
		}
		length=sitems.length;
		finedo.message.question("您确定删除该组织机构吗？", null, function(which){  
	        if (which){ 
	        	finedo.message.showWaiting();
	        	for(var a=0;a<sitems.length;a++){
	        		var id = sitems[a].id
	        		$.ajax({
						type : 'POST',
						url : '${ctx}/finedo/organization/deleteOrg?id='+id,
						success : importcallback,
						dataType : 'json'
					});
	        	}
	        
	        }  
	    });  
	}
	
	function add(){
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'组织机构信息',
			'url':'${ctx }/finedo/organization/addpage'
		});
	}
	
	function query(){
		var type= $("input[name='demand']:checked").val();
		var param = {orgnode:orgid,
					remark:type
					};
		finedo.getgrid("datagrid").query(param);
	}
	
	function showDialog() {
		var sitems = finedo.getTree('treediv').getselecteditems();
		if(sitems==""){
			finedo.message.info("你未选取组织机构!", '提示');
			return;
		}
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'组织机构信息',
			'url':'${ctx}/finedo/organization/queryOrgById?orgid=' + sitems[0].id
		});
	}
	
</script>
</head>
<div style=" width: 18%; float: left; border:1px solid #ddd; position: fixed; top:20px; left:10px; bottom:10px; ">
	<div class="tree-name">组织结构</div>
	<div class="tree-btn">
		<input type="button" class="query-btn-nextstep tree-btn-mar" value="添加" onclick="add()">
		<input type="button" class="query-btn-nextstep tree-btn-mar" value="删除" onclick="dodelete()">
		<input type="button" class="query-btn-nextstep tree-btn-mar" value="修改" onclick="showDialog()">
	</div>
	<ul id="treediv" class="fdtree" style=" margin-left:5px;" ></ul>
</div>
<div style=" width: 78%; float: right; margin-top:20px;  margin-right:2%;">
	<div class="query-title">
		<!-- 标题 -->
   <div class="query-title-name">系统用户管理 </div>
    	
    	<div class="query-boxbig">
	    	<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="新建用户">
	    	<input type="button" class="query-btn-nextstep" onclick="doExport();" value="批量导出">
       	 	
    		<div class="query-fast">
	        	<input type="text" class="query-fast-text" id="query-box-text" value="">
	            <input type="button" class="query-fast-magnifier" onclick="doQueryFast();">
	        </div>
	        <input type="button" class="query-btn-nextstep" onclick="opquerycond();" value="高级搜索">
    	</div>
   	</div>
   	<div class="radio-div" >
   		<span onclick="query();">
   			<label><input type="radio" value="本级" name="demand" style="margin-left: 10px;" >&nbsp;本级</label>
   			<label><input type="radio" value="本级及下级" name="demand" style="margin-left: 10px;">&nbsp;本级及下级</label>	
   		</span>	
   	</div>
   		<!-- 高级搜索栏  -->
    <div class="query-advanced-search-con" id="advanced-search-con" style="display:none; width: 78%; float: right; margin-top:20px;margin-left:20%;"">
    	<div class="query-common-query" id="common-con">
        	<table class="finedo-table">
				<tr>
					<td class="finedo-label-title">用户编号</td>
					<td>
						<input class="finedo-text" type="text" id="usercode" name="usercode">
					</td>
					<td class="finedo-label-title">姓名</td>
					<td>
						<input class="finedo-text" type="text" id="personname" name="personname">
					</td>
				</tr>
				<tr>
					<td class="finedo-label-title">状态</td>
					<td>
						<fsdp:radio id="state" datasource="用户状态" />
					</td>
					<td class="finedo-label-title">联系电话</td>
					<td>
						<input class="finedo-text" type="text" id="phoneno" name="phoneno">
					</td>
				</tr>
			</table>
        </div>
        <div class="query-operate">
            <input class="finedo-button-blue" type="button" value="查    询" onclick="doQuery();">&nbsp;&nbsp;&nbsp;&nbsp;
           	<input class="finedo-button-blue" type="button" value="取    消" onclick="doQueryCancel();">
        </div>
    </div>
       <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/user/queryUser" selecttype="multi" expand="doExpandRow">
		<fsdp:field code="user.usercode" name="用户编号" width="100" formatter="formatUserid" order="true"></fsdp:field>
		<fsdp:field code="user.personname" name="姓名" width="80" formatter="formatPersonname"></fsdp:field>
		<fsdp:field code="stdrole.orgname" name="所属机构" width="120"></fsdp:field>
		<fsdp:field code="stdrole.rolename" name="基本角色" width="120"></fsdp:field>
		<fsdp:field code="user.phoneno" name="联系方式" width="120"></fsdp:field>
		<fsdp:field code="user.email" name="邮箱" width="180"></fsdp:field>
		<fsdp:field code="user.state" name="用户状态" width="60"></fsdp:field>
		<fsdp:field code="operation" name="操作" width="200" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>
<iframe id="downiframe" name="downiframe" style="display:none" ></iframe>
<body>

</body>
</html>
