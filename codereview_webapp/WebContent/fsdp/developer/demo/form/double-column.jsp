<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>综合表单-单列布局</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${datepicker_js }
${colorpicker_js }
${ueditor_js }
</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="综合表单-单列布局" style="width:auto;padding:10px;" fit="true">
	<form method="post" action="${ctx }/finedo/user/add" id="ajaxForm" >		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
		  
		  <tr>
		    <th width="100">输入框：</th>
		    <td >
		    	<fsdp:text id="text1" name="text1"></fsdp:text>
			<th width="100">日期：</th>
		    <td >
		    	<fsdp:date id="date1" name="date1"></fsdp:date>
		    </td>

		  </tr>
		 <tr>
		    <th>单选框：</th>
		    <td >
		    	<fsdp:radiobox datasource="" name="radio1"></fsdp:radiobox>
		    </td>
			<th>复选框：</th>
		    <td >
		    	<fsdp:checkbox datasource="" name="checkbox1"></fsdp:checkbox>
		    </td>
		  </tr>
		 
		  <tr>
		    <th>文件上传：</th>
		    <td colspan="3">
		    	<fsdp:file name="file1"></fsdp:file>
		    </td>
		  </tr>
		  <tr>
		    <th>富文本：</th>
		    <td  colspan="3">
		    	<fsdp:htmleditor id="editor"></fsdp:htmleditor>
		    </td>

		  </tr>
		   <tr>
		    
			<th>颜色选择器：</th>
		    <td >
		    	<fsdp:colorpicker id="color1"></fsdp:colorpicker>
		    </td>
<th>下拉列表：</th>
		    <td >
		    	<fsdp:select datasource="" name="select1"></fsdp:select>
		    </td>
		  </tr>
		</table>
	</form>
  </div>
</div>  
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

</script>
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>  
   <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="window.history.go(-1)">取消</a>  
</div>  
</body>
</html>
