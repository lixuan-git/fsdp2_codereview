<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_grid_js }
<script type='text/javascript' src='${ctx}/resource/js/contextmenu/AeroWindow-Contextmenu v0.2.js?version=20150215001'></script>
<link href='${ctx}/resource/js/contextmenu/skins/default/AeroWindow-Contextmenu.css?version=20150215001' rel='stylesheet' type='text/css' />

${finedo_pager_js }
<script type="text/javascript">
/************** 表格操作函数定义****************/
// 表格"操作"单元格重载
function formatOperation(row){
	var operation = '<a href="#" onclick="showModifyDialog(\'' + row.userid + '\');">[编辑]</a>&nbsp;';
	operation += '<a href="#" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/example/delete\',\''+row.userid+'\')">[删除]</a>&nbsp;';
	return operation;
}

// 单元格重载
function formatUserid(row){
	return '<a href="#" onclick="showDetailDialog(\'' + row.userid + '\');">'+row.usercode+'</a>&nbsp;';
}

// 行信息展开
function doExpandRow(data){
		return 	"<div class='data'><ul>" +
				"<li><span class='data-name'>用户编号</span><span class='data-con'>" + 	finedo.fn.replaceNull(data.userid) + "</span></li>" +
				"<li><span class='data-name'>姓名</span><span class='data-con'>" + 		finedo.fn.replaceNull(data.personname) + "</span></li>" +
				"<li><span class='data-name'>状态</span><span class='data-con'>" + 		finedo.fn.replaceNull(data.state) + "</span></li>" +
				"<li><span class='data-name'>创建时间</span><span class='data-con'>" + 	finedo.fn.replaceNull(data.createtime) + "</span></li>" +
				"<li><span class='data-name'>失效时间</span><span class='data-con'>" + 	finedo.fn.replaceNull(data.expdate) + "</span></li>" +
				"<li><span class='data-name'>性别</span><span class='data-con'>" + 		finedo.fn.replaceNull(data.gender) + "</span></li>" +
				"<li><span class='data-name'>身份证号码</span><span class='data-con'>" + 	finedo.fn.replaceNull(data.idnumber) + "</span></li>" +
				"<li><span class='data-name'>邮箱</span><span class='data-con'>" +  		finedo.fn.replaceNull(data.email) + "</span></li>" +
				"<li><span class='data-name'>地址</span><span class='data-con'>" +  		finedo.fn.replaceNull(data.address) + "</span></li>" +
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
/************** 查询函数定义****************/

/************** 增、删、改函数定义****************/
function showAddDialog() {
	finedo.dialog.show({
		width:850,
		height:550,
		'title':'新增信息',
		'url':'add.jsp'
	});
}

function showModifyDialog(userid) {
	finedo.dialog.show({
		width:850,
		height:550,
		'title':'修改信息',
		'url':'${ctx}/finedo/example/modifypage?userid=' + userid
	});
}

function showDetailDialog(userid) {
	finedo.dialog.show({
		width:850,
		height:550,
		'title':'详情信息',
		'url':'${ctx}/finedo/example/detail?userid=' + userid
	});
}


function doExport() {
	alert($("#datagrid").text());
	return;
	var param=finedo.getgrid("datagrid").getqueryparams();
	
	$("#downiframe").attr('src', '${ctx }/finedo/example/exportexcel' + (param ? '?' + $.param(param) : ''));
}
/************** 增、删、改函数定义****************/
$(function(){
	/*$('#datagrid').WinContextMenu({
		cancel:'.cancel',
		items:[{
			id:'Item1',
			text:'Item1项',
			disable:!0,//新增加
			action:function(){alert('first-item1');}//按照项添加
		},
		{
			id:'copyrow',
			text:'复制整行',
			action:function(e){alert(e.target.tagName);}//按照项添加
		}],
		action:function(e){alert(e.id);}//自由设计项事件回调
	});	
	
	finedo.getpager("pagerdiv", {"url":"${ctx }/finedo/example/query", success:function(data){
		alert(data.total);
	}}).load({"queryparams":{"userid":"2013041"}});*/
});
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
    <fsdp:grid id="datagrid" url="${ctx }/finedo/example/query" scrollload="true" updateurl="${ctx }/finedo/example/modify" selecttype="multi" expand="doExpandRow" pagesize="50">
		<fsdp:field code="usercode" name="用户编号" width="100" formatter="formatUserid" order="true"></fsdp:field>
		<fsdp:field code="personname" name="姓名" width="80"></fsdp:field>
		<fsdp:field code="phoneno" name="联系方式" width="120"></fsdp:field>
		<fsdp:field code="email" name="邮箱" width="200"></fsdp:field>
		<fsdp:field code="state" name="用户状态" width="80" edit="true"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>

<div id="pagerdiv"></div>
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