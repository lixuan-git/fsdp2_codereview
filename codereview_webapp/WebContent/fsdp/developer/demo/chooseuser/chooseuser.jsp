<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/fsdp/common/taglibs.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
${style_css }
${jquery_js}
${easyui_js }
</head>
<body>
<h2>我是测试</h2>

<div id="win" class="easyui-window" title="选择人员" href="${ctx}/fsdp/component/userchoose.jsp" style="width:600px;height:400px"
        data-options="modal:true,closed:true,iconCls:'icon-save',resizable:false">
   <div id="content"></div>
</div>

<input type="button" value="测试" onclick="createProduct();"/>
<input type="text" id="username" name="username"/>
<input type="hidden" id="userid" name="userid"/>

<script>


function createProduct(){
        $('#win').window('open');//打开这个窗口（就是createForm.jsp）为什么能打开这个窗口
 
}

	</script>




</body>



</html>