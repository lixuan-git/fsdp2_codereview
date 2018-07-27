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
	window.location.href="${ctx }/fsdp/developer/demo/highcharts/pie.jsp?theme="+themeName;
}
$(function () {
    $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: 'Browser market shares at a specific website, 2010'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
    });
});
</script>
</body>
</html>