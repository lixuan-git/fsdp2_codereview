<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_grid_js }

<script type="text/javascript">
/************** 表格操作函数定义****************/
// 表格"操作"单元格重载
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
/************** 增、删、改函数定义****************/

</script>
</head>

<body class="query-body">

<div style="width:100%;">
	<!-- 工具栏  -->
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

	<!-- 高级搜索栏  -->
    <div class="query-advanced-search-con" id="advanced-search-con" style="display:none; ">
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
		<fsdp:field code="stdrole.orgname" name="所属机构" width="150"></fsdp:field>
		<fsdp:field code="stdrole.rolename" name="基本角色" width="120"></fsdp:field>
		<fsdp:field code="user.phoneno" name="联系方式" width="120"></fsdp:field>
		<fsdp:field code="user.email" name="邮箱" width="200"></fsdp:field>
		<fsdp:field code="user.state" name="用户状态" width="60"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>

<iframe id="downiframe" name="downiframe" style="display:none" ></iframe>
</body>
</html>

<script>
/**************  展开与隐藏 搜索条件框 ****************/
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
</script>