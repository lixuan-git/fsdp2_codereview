<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	${jquery_js }
	${highcharts_js }
	<c:if test="${!empty param.theme }">
	<script src='${ctx}/resource/js/highcharts/themes/${param.theme }.js' type='text/javascript'></script>
	</c:if>
</head>
<body>
<div>
	 图表主题：
	<button class="btn btn-primary" theme="default" onclick="changeTheme('')">默认</button>
	<button class="btn" theme="grid" onclick="changeTheme('grid')">网格 (grid)</button>
	<button class="btn" theme="grid-light" onclick="changeTheme('grid-light')">grid-light</button>
	<button class="btn" theme="skies" onclick="changeTheme('skies')">天空 (skies)</button>
	<button class="btn" theme="gray" onclick="changeTheme('gray')">灰色 (gray)</button>
	<button class="btn" theme="dark-blue" onclick="changeTheme('dark-blue')">深蓝 (dark-blue)</button>
	<button class="btn" theme="dark-green" onclick="changeTheme('dark-green')">深绿 (dark-green)</button>
	<button class="btn" theme="dark-unica" onclick="changeTheme('dark-unica')">dark-unica</button>
	<button class="btn" theme="sand-signika" onclick="changeTheme('sand-signika')">sand-signika</button>
</div>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<script language="javascript">
function changeTheme(themeName){
	window.location.href="${ctx }/fsdp/developer/demo/highcharts/line.jsp?theme="+themeName;
}
$(function () {
    $('#container').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
});
</script>
</body>
</html>