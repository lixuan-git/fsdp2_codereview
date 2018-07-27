<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>日期拾取器使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
${datepicker_js }
</head>
<body>
	<h2>日期选择器</h2>
	<p>日期选择器可以通过 &lt;fsdp:date&gt;标签创建(页面需引入datepicker_js)。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker()">&lt;/fsdp:date>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker()"></fsdp:date>
	</div>
	
	<p class="demo-subtitle">2.自定义格式</p>
	<textarea rows="5" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		年月日时分秒：&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分ss秒'})">&lt;/fsdp:date>
		时分秒：&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'H:mm:ss'})">&lt;/fsdp:date>
		年月：&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy年MM月'})">&lt;/fsdp:date>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		年月日时分秒：<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分ss秒'})"></fsdp:date>
		时分秒：<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'H:mm:ss'})"></fsdp:date>
		年月：<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy年MM月'})"></fsdp:date>
	</div>
	
	<p class="demo-subtitle">3.日期范围限制</p>
	<textarea rows="10" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		限制日期的范围是 2014-01-10到2016-12-20：&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({minDate:'2014-01-10',maxDate:'2016-12-20'})">&lt;/fsdp:date>
		限制日期的范围是 2008-3-8 11:30:00 到 2008-3-10 20:59:30：&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2008-03-10 20:59:30'})">&lt;/fsdp:date>
		前面的日期不能大于后面的日期且两个日期都不能大于 2020-10-01：<br/>从&lt;fsdp:date id="starttime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'endtime\\')||\\'2020-10-01\\'}'})" style="width:120px">&lt;/fsdp:date>到&lt;fsdp:date id="endtime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'starttime\\')}',maxDate:'2020-10-01'})" style="width:120px">&lt;/fsdp:date>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		限制日期的范围是 2014-01-10到2016-12-20：<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({minDate:'2014-01-10',maxDate:'2016-12-20'})"></fsdp:date>
		限制日期的范围是 2008-3-8 11:30:00 到 2008-3-10 20:59:30：<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2008-03-10 20:59:30'})"></fsdp:date>
		前面的日期不能大于后面的日期且两个日期都不能大于 2020-10-01：<br/>从<fsdp:date id="starttime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'endtime\\')||\\'2020-10-01\\'}'})" style="width:120px"></fsdp:date>到<fsdp:date id="endtime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'starttime\\')}',maxDate:'2020-10-01'})" style="width:120px"></fsdp:date>
	</div>
	
	<p class="demo-subtitle">4.皮肤</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({skin:'whyGreen'})">&lt;/fsdp:date>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:date name="birthday" id="birthday" onfocus="WdatePicker({skin:'whyGreen'})"></fsdp:date>
	</div>
	<p>更多使用指南请访问WdatePicker”<a href="http://www.my97.net/dp/demo/index.htm" target="_blank">官方网站</a>“。</p>
</body>
</html>