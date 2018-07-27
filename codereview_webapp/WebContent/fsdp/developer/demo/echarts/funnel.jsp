<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title> ${jquery_js } ${echarts_js }
</head>
<body>
<div>
	图表主题：
	<button class="btn btn-primary" theme="default" onclick="changeTheme('default')">default</button>
	<button class="btn" theme="macarons" onclick="changeTheme('macarons')">macarons</button>
	<button class="btn" theme="infographic" onclick="changeTheme('infographic')">infographic</button>
	<button class="btn" theme="shine" onclick="changeTheme('shine')">shine</button>
	<button class="btn" theme="dark" onclick="changeTheme('dark')">dark</button>
	<button class="btn" theme="blue" onclick="changeTheme('blue')">blue</button>
	<button class="btn" theme="green" onclick="changeTheme('green')">green</button>
	<button class="btn" theme="red" onclick="changeTheme('red')">red</button>
	<button class="btn" theme="gray" onclick="changeTheme('gray')">gray</button>
	<button class="btn" theme="helianthus" onclick="changeTheme('helianthus')">helianthus</button>
</div>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<script language="javascript">
	var myChart;
	var curTheme;
	require.config({
		paths : {
			echarts : '${ctx}/resource/js/echarts'
		}
	});

	$(function() {
		require([
			'echarts',
			'echarts/chart/funnel'], function(ec) {
			myChart = ec.init(document.getElementById('container'));
			myChart.setOption(echartOption);
		});
	});
	function changeTheme(theme) {
		myChart.showLoading();
        if (theme != 'default') {
            require(['echarts/theme/' + theme], function(tarTheme){
                curTheme = tarTheme;
                setTimeout(refreshTheme, 500);
            }); 
        }
        else {
            curTheme = {};
            setTimeout(refreshTheme, 500);
        }
	}
	function refreshTheme(){
		myChart.hideLoading();
		myChart.setTheme(curTheme);
	}
	var echartOption= {
		    title : {
		        text: '漏斗图',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    legend: {
		        data : ['展现','点击','访问','咨询','订单']
		    },
		    calculable : true,
		    series : [
		        {
		            name:'漏斗图',
		            type:'funnel',
		            width: '40%',
		            data:[
		                {value:60, name:'访问'},
		                {value:40, name:'咨询'},
		                {value:20, name:'订单'},
		                {value:80, name:'点击'},
		                {value:100, name:'展现'}
		            ]
		        },
		        {
		            name:'金字塔',
		            type:'funnel',
		            x : '50%',
		            sort : 'ascending',
		            itemStyle: {
		                normal: {
		                    // color: 各异,
		                    label: {
		                        position: 'left'
		                    }
		                }
		            },
		            data:[
		                {value:60, name:'访问'},
		                {value:40, name:'咨询'},
		                {value:20, name:'订单'},
		                {value:80, name:'点击'},
		                {value:100, name:'展现'}
		            ]
		        }
		    ]
		};
</script>
</body>
</html>