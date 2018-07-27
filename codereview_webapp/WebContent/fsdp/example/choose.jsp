<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_dialog_js }

<script type="text/javascript">

/************** 查询函数定义****************/

$(document.body).ready(function(){
	$("#personname").blur(function(){
		if(this.value==""){
			this.value="请输入姓名";
		}
	}); 
	
	$("#personname").focus(function(){
		if(this.value=="请输入姓名"){
			this.value="";
		}
	}); 
});

function doQuery(){
	var text=$('#personname').val();
	var param = {personname:text};
	finedo.getgrid("datagrid").query(param);
}

function doQueryFast(event) {
	if(event.keyCode != 13)
		return;
	doQuery();
}
/************** 查询函数定义****************/

</script>
</head>

<body class="scrollcss">

<div class="index">
	<!-- 工具栏  -->
	<div class="table-title">
		<!-- 标题 -->
    	<div class="common-name icon-class-query">选择用户<br/>
			<ul>
            	<li class="link-li">全部</li>
            </ul>
        </div>
        
        <!-- 快捷查询 -->
        <div style="width:220px" class="query-box">
        	<input type="text" style="width:200px" class="query-box-text" id="personname" name="personname" value="请输入姓名" onkeypress="doQueryFast(event);">
            <input type="button" class="query-box-magnifier" onclick="doQuery();">
        </div>
    </div>
   
    <!-- 表格栏  -->
    <fsdp:grid className="table" id="datagrid" url="${ctx }/finedo/example/query" selecttype="${param.selecttype }">
		<fsdp:field code="usercode" name="用户编号" width="100"></fsdp:field>
		<fsdp:field code="personname" name="姓名" width="80"></fsdp:field>
		<fsdp:field code="phoneno" name="联系方式" width="120"></fsdp:field>
		<fsdp:field code="email" name="邮箱" width="200"></fsdp:field>
		<fsdp:field code="state" name="用户状态" width="80"></fsdp:field>
	</fsdp:grid>
</div>

</body>
</html>
