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
			'echarts/chart/radar'], function(ec) {
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
		        text: '预算 vs 开销（Budget vs spending）',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'right',
		        y : 'bottom',
		        data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
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
		    polar : [
		       {
		           indicator : [
		               { text: '销售（sales）', max: 6000},
		               { text: '管理（Administration）', max: 16000},
		               { text: '信息技术（Information Techology）', max: 30000},
		               { text: '客服（Customer Support）', max: 38000},
		               { text: '研发（Development）', max: 52000},
		               { text: '市场（Marketing）', max: 25000}
		            ]
		        }
		    ],
		    calculable : true,
		    series : [
		        {
		            name: '预算 vs 开销（Budget vs spending）',
		            type: 'radar',
		            data : [
		                {
		                    value : [4300, 10000, 28000, 35000, 50000, 19000],
		                    name : '预算分配（Allocated Budget）'
		                },
		                 {
		                    value : [5000, 14000, 28000, 31000, 42000, 21000],
		                    name : '实际开销（Actual Spending）'
		                }
		            ]
		        }
		    ]
		};
</script>
</body>
</html>