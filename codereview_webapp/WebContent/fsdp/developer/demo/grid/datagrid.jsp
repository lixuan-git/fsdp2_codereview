<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>数据列表使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>数据表格</h2>
	<p>数据列表可以通过 &lt;fsdp:grid&gt;及&lt;fsdp:field&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:grid fit="false" pagination="false" url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表" height="220">
			&lt;fsdp:field code="productid" name="产品编号">&lt;/fsdp:field>
			&lt;fsdp:field code="productname" name="产品名称">&lt;/fsdp:field>
			&lt;fsdp:field code="unitcost" name="单价">&lt;/fsdp:field>
			&lt;fsdp:field code="listprice" name="总额">&lt;/fsdp:field>
		&lt;/fsdp:grid>
	</textarea>
	<div style="padding:5px 0;height: 220px;">
		<fsdp:grid fit="false" pagination="false"  url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表" height="220">
			<fsdp:field code="productid" name="产品编号" width="100"></fsdp:field>
			<fsdp:field code="productname" name="产品名称" width="100"></fsdp:field>
			<fsdp:field code="unitcost" name="单价" width="100"></fsdp:field>
			<fsdp:field code="listprice" name="总额" width="100"></fsdp:field>
		</fsdp:grid>
	</div>
	
	<p class="demo-subtitle">2.分页</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:grid pagination="true" url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表" height="220">
			&lt;fsdp:field code="productid" name="产品编号">&lt;/fsdp:field>
			&lt;fsdp:field code="productname" name="产品名称">&lt;/fsdp:field>
			&lt;fsdp:field code="unitcost" name="单价">&lt;/fsdp:field>
			&lt;fsdp:field code="listprice" name="总额">&lt;/fsdp:field>
		&lt;/fsdp:grid>
	</textarea>
	<div style="padding:5px 0;height: 220px;">
		<fsdp:grid pagination="true" url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表">
			<fsdp:field code="productid" name="产品编号" width="100"></fsdp:field>
			<fsdp:field code="productname" name="产品名称" width="100"></fsdp:field>
			<fsdp:field code="unitcost" name="单价" width="100"></fsdp:field>
			<fsdp:field code="listprice" name="总额" width="100"></fsdp:field>
		</fsdp:grid>
	</div>
	
	<p class="demo-subtitle">3.工具架</p>
	<textarea rows="22" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:grid pagination="true" url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表" toolbar="#tb">
			&lt;fsdp:field code="productid" name="产品编号">&lt;/fsdp:field>
			&lt;fsdp:field code="productname" name="产品名称">&lt;/fsdp:field>
			&lt;fsdp:field code="unitcost" name="单价">&lt;/fsdp:field>
			&lt;fsdp:field code="listprice" name="总额">&lt;/fsdp:field>
		&lt;/fsdp:grid>
		&lt;fsdp:toolbar id="tb">
			&lt;fsdp:buttonbar>
				&lt;fsdp:button iconcls="icon-add" name="添加" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'">&lt;/fsdp:button>
				&lt;fsdp:button iconcls="icon-save" name="导出" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'">&lt;/fsdp:button>
				&lt;fsdp:button iconcls="icon-help" name="帮助" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'">&lt;/fsdp:button>
			&lt;/fsdp:buttonbar>
			&lt;fsdp:searchbar>
				 &lt;fsdp:text name="usercode" id="usercode" style="width:150px;" label="产品编号">&lt;/fsdp:text>&nbsp; 
	             &lt;fsdp:text name="menuname" id="menuname" style="width:150px;" label="产品名称">&lt;/fsdp:text>&nbsp;
	                    &lt;fsdp:button name="查询" iconcls="icon-search">&lt;/fsdp:button>
			&lt;/fsdp:searchbar>
		&lt;/fsdp:toolbar>
	</textarea>
	<div style="padding:5px 0;height: 350px;">
		<fsdp:grid pagination="true" url="${ctx }/fsdp/developer/demo/grid/datagrid_data1.json" title="商品列表" toolbar="#tb">
			<fsdp:field code="productid" name="产品编号" width="100"></fsdp:field>
			<fsdp:field code="productname" name="产品名称" width="100"></fsdp:field>
			<fsdp:field code="unitcost" name="单价" width="100"></fsdp:field>
			<fsdp:field code="listprice" name="总额" width="100"></fsdp:field>
		</fsdp:grid>
		<fsdp:toolbar id="tb">
			<fsdp:buttonbar>
				<fsdp:button iconcls="icon-add" name="添加" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'"></fsdp:button>
				<fsdp:button iconcls="icon-save" name="导出" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'"></fsdp:button>
				<fsdp:button iconcls="icon-help" name="帮助" plain="true" onclick="location.href='${ctx }/fsdp/user/add.jsp'"></fsdp:button>
			</fsdp:buttonbar>
			<fsdp:searchbar>
				 <fsdp:text name="usercode" id="usercode" style="width:150px;" label="产品编号"></fsdp:text>&nbsp; 
	             <fsdp:text name="menuname" id="menuname" style="width:150px;" label="产品名称"></fsdp:text>&nbsp;
	                    <fsdp:button name="查询" iconcls="icon-search"></fsdp:button>
			</fsdp:searchbar>
		</fsdp:toolbar>
	</div>
</body>
</html>