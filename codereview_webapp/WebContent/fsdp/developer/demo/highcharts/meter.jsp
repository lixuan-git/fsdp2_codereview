<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	${jquery_js }
	${highcharts_js }
	${highcharts_more_js }
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
	window.location.href="${ctx }/fsdp/developer/demo/highcharts/meter.jsp?theme="+themeName;
}
$(function () {
	
    $('#container').highcharts({
	
	    chart: {
	        type: 'gauge',
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false
	    },
	    
	    title: {
	        text: 'Speedometer'
	    },
	    
	    pane: {
	        startAngle: -150,
	        endAngle: 150,
	        background: [{
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#FFF'],
	                    [1, '#333']
	                ]
	            },
	            borderWidth: 0,
	            outerRadius: '109%'
	        }, {
	            backgroundColor: {
	                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
	                stops: [
	                    [0, '#333'],
	                    [1, '#FFF']
	                ]
	            },
	            borderWidth: 1,
	            outerRadius: '107%'
	        }, {
	            // default background
	        }, {
	            backgroundColor: '#DDD',
	            borderWidth: 0,
	            outerRadius: '105%',
	            innerRadius: '103%'
	        }]
	    },
	       
	    // the value axis
	    yAxis: {
	        min: 0,
	        max: 200,
	        
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: 'km/h'
	        },
	        plotBands: [{
	            from: 0,
	            to: 120,
	            color: '#55BF3B' // green
	        }, {
	            from: 120,
	            to: 160,
	            color: '#DDDF0D' // yellow
	        }, {
	            from: 160,
	            to: 200,
	            color: '#DF5353' // red
	        }]        
	    },
	
	    series: [{
	        name: 'Speed',
	        data: [80],
	        tooltip: {
	            valueSuffix: ' km/h'
	        }
	    }]
	
	}, 
	// Add some life
	function (chart) {
		if (!chart.renderer.forExport) {
		    setInterval(function () {
		        var point = chart.series[0].points[0],
		            newVal,
		            inc = Math.round((Math.random() - 0.5) * 20);
		        
		        newVal = point.y + inc;
		        if (newVal < 0 || newVal > 200) {
		            newVal = point.y - inc;
		        }
		        
		        point.update(newVal);
		        
		    }, 3000);
		}
	});
});
</script>
</body>
</html>