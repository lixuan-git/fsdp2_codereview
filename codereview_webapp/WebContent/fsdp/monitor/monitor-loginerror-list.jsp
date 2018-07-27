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

<script>
function doClear(){
	finedo.action.doCommand('${ctx}/finedo/auth/clearLoginerrornum', doSearch, true);
	
}

function doSearch() {
	finedo.getgrid("datagrid").query("");
}
</script>
</head>

<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">异常登录监控信息</div>
        
        <div class="query-boxbig">
        	<input type="button" class="query-btn-nextstep" onclick="doClear();" value="清除全部异常登录锁定账号">
        </div>      	
   	</div>
  	
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/auth/loginerrornum" selecttype="none" pagination="false" >
    	<fsdp:field code="ipaddr" name="登录账号" width="80"></fsdp:field>
		<fsdp:field code="errornum" name="登录失败次数" width="80"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>