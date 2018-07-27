<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<%@ page import="cn.finedo.fsdp.service.login.domain.LoginDomain" %>
<%@ page import="cn.finedo.fsdp.server.util.SessionUtil" %>

<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }

<%
LoginDomain login = SessionUtil.getLoginDomain(request);
%>


</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="当前位置：上传文件测试" style="width:auto;padding:10px;" fit="true">
	<form method="post" action="${ctx }/finedo/file/upload" id="uploadform" name="uploadform" enctype="multipart/form-data">	
		<table width="80%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  <tr align=left>
		    <th width="20%" class="require">* 选择文件：</th>
		    <td>
		    	<input onchange="uploadform.submit();" type="file" value="" id="uploadfile" name="uploadfile" datatype="Require" msg="导入文件不能为空"/>
		    	
		    	<a href="${ctx }/finedo/file/download?fileid=FD000000000000001102&token=<%=login.getToken() %>" >测试下载</a>
		    	<a href="${ctx }/finedo/file/downloadmulti?fileids=FD000000000000002402,FD000000000000002403&token=<%=login.getToken() %>" >测试批量下载</a>
		    	
		    	<img src="${ctx }/finedo/file/downloadthumbnail?fileid=FD000000000000002403&token=<%=login.getToken() %>"></img>
		    	<img src="${ctx }/finedo/file/download?fileid=FD000000000000002403&token=<%=login.getToken() %>"></img>
		    
		    </td>
		  </tr>
		</table>
	</form>
	
	
	<button onclick="$('#downframe').attr('src', '${ctx }/finedo/file/download?fileid=FD000000000000002205&token=<%=login.getToken() %>');" id=downbutton name=downbutton >测试IE下载乱码问题</button>
  
  </div>
</div>
</body>

<iframe id="downframe" name="downframe" style="display:none" />
</html>

