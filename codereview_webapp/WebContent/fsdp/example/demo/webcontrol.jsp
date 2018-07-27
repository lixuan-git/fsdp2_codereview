<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>Web组件测试</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_date_js }
${finedo_choose_js }
${finedo_grid_js }
</head>

<body>
<ul class="finedo-ul">
	<li>
		<span class="finedo-label-title">按钮</span>
		<fsdp:button id="button1" label="按钮"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-yellow"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-red"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-deepred"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-blue"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-deepblue"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-green"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-deepgreen"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-grey"></fsdp:button>
		<fsdp:button id="button1" label="按钮" classname="finedo-button-deepgrey"></fsdp:button>
		<fsdp:button id="button1" label="按钮点击事件" onclick="finedo.message.info('点我了!');"></fsdp:button>
	</li>
	
	<li>
		<span class="finedo-label-title">checkbox</span>
		<fsdp:checkbox id="checkbox1" datasource="用户状态" /> <br>
		<fsdp:checkbox id="checkbox2" datasource="第三方平台类型" /> <br>
		<fsdp:checkbox id="checkbox3" datasource="岗位角色级别" selectedvalue="一岗,十岗"/> <br>
		<fsdp:checkbox id="checkbox4" datasource="数据类型" selectedvalue="文本型,浮点型,颜色型"/>
		<input class="finedo-button" onclick="finedo.message.info($('#checkbox4').val());" value="查看选项值"></input>
		<br>
	</li>
	
	<li>
		<span class="finedo-label-title">日期</span>
		<fsdp:date id="date1"></fsdp:date>
		<fsdp:date id="date2" datefmt="HH:mm:ss"></fsdp:date>
		<fsdp:date id="date3" datefmt="yyyy-MM-dd HH:mm:ss"></fsdp:date>
		<fsdp:date id="date2" datefmt="yyyy-MM-dd 23:59:59"></fsdp:date>
		<br>
	</li>
	
	<li>
		<span class="finedo-label-title">单选下拉框</span>
		<fsdp:select id="singleselect1" datasource="用户状态"></fsdp:select><input class="finedo-button" onclick="finedo.message.info($('#singleselect1').val());" value="查看选项值"></input><br>
		<fsdp:select id="singleselect2" datasource="岗位角色级别" selectedvalue="十岗"></fsdp:select><input class="finedo-button" onclick="finedo.message.info($('#singleselect2').val());" value="查看选项值"></input>
	</li>
	
	<li>
		<span class="finedo-label-title">多选下拉框</span>
		<fsdp:select id="multiselect1" datasource="用户状态" type="multi"></fsdp:select><input class="finedo-button" onclick="finedo.message.info($('#multiselect1').val());" value="查看选项值"></input><br>
		<fsdp:select id="multiselect2" datasource="岗位角色级别" type="multi" selectedvalue="一岗,十岗"></fsdp:select><input class="finedo-button" onclick="finedo.message.info($('#multiselect2').val());" value="查看选项值"></input>
	</li>
	
	<li>
		<span class="finedo-label-title">文本域</span>
		<fsdp:textarea id="textarea1" style="width:700px">测试文本域</fsdp:textarea>
	</li>
	
	<li>
		<span class="finedo-label-title">文本</span>
		<fsdp:text id="text1" style="width:500px"></fsdp:text>
	</li>
	
	<li>
		<span class="finedo-label-title">单选对话框</span>
		
		<input type="hidden" id="choose1" name="choose1" value="">
		<input type="text" class="finedo-text" id="choose1_name" name="choose1_name" value="">
		
		<input type="button" class="finedo-button" value="选择" onclick="openChoose1();">
		<input type="button" class="finedo-button" value="清空" onclick="clearChoose1();">
		
		<script>
		function openChoose1() {
			finedo.choose.singleUser(function(row) {
				//alert(JSON.stringify(row));
				if(row.length > 0) {
					$('#choose1').val(row[0].user.userid);
					$('#choose1_name').val(row[0].user.personname);
				}
			});
		}
		
		function clearChoose1() {
			$('#choose1').val('');
			$('#choose1_name').val('');
		}
		</script>
	</li>
	
	<li>
		<span class="finedo-label-title">多选对话框</span>
		
		<input type="hidden" id="choose2" name="choose2" value="">
		<input type="text" class="finedo-text" id="choose2_name" name="choose2_name" value="">
		
		<input type="button" class="finedo-button" value="选择" onclick="openChoose2();">
		<input type="button" class="finedo-button" value="清空" onclick="clearChoose2();">
		
		<script>
		function openChoose2() {
			finedo.choose.multiUser(function(row) {
				//alert(JSON.stringify(row));
				var ids="";
				var names="";
				if(row.length > 0) {
					for(var i=0; i<row.length; i++) {
						if(i == 0) {
							ids=row[i].user.userid;
							names=row[i].user.personname;
						}else {
							ids = ids + "," + row[i].user.userid;
							names = names + "," + row[i].user.personname;
						}
					}
				}
				
				$('#choose2').val(ids);
				$('#choose2_name').val(names);
			});
		}
		
		function clearChoose2() {
			$('#choose2').val('');
			$('#choose2_name').val('');
		}
		</script>
	</li>
	
	
	<li>
		<span class="finedo-label-title">动态表格</span>
		<div id="dynamicgriddiv" style="width: 100%"></div>
		<script>
			var gridstr='<table id="datagrid" url="${ctx }/finedo/staticdata/queryStaticdata" class="finedo-datagrid">';
			gridstr=gridstr + '<thead>';
			gridstr=gridstr + '<tr>';
			gridstr=gridstr + '<th code="typename" width="100">类型名称</th>';
			gridstr=gridstr + '<th code="datatype" width="80">数据类型</th>';
			gridstr=gridstr + '<th code="configtype" width="120">配置类型</th>';
			gridstr=gridstr + '<th code="lvl" width="200">级别</th>';
			gridstr=gridstr + '</tr>';
			gridstr=gridstr + '</thead>';
			gridstr=gridstr + '</table>';
			
			$("#dynamicgriddiv").empty();
			$("#dynamicgriddiv").append(gridstr);
			
			$(function() {
				finedo.getgrid('datagrid').load();
			});
		</script>
	</li>
	
</ul>

</body>
</html>