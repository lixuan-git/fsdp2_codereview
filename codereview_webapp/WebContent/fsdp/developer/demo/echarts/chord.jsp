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
			'echarts/chart/chord',
			'echarts/chart/force'], function(ec) {
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
	var echartOption={
		    title : {
		        text: '测试数据',
		        subtext: 'From d3.js',
		        x:'right',
		        y:'bottom'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: function (params) {
		            if (params.indicator2) { // is edge
		                return params.value.weight;
		            } else {// is node
		                return params.name
		            }
		        }
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            restore : {show: true},
		            magicType: {show: true, type: ['force', 'chord']},
		            saveAsImage : {show: true}
		        }
		    },
		    legend: {
		        x: 'left',
		        data:['group1','group2', 'group3', 'group4']
		    },
		    series : [
		        {
		            type:'chord',
		            sort : 'ascending',
		            sortSub : 'descending',
		            showScale : true,
		            showScaleText : true,
		            data : [
		                {name : 'group1'},
		                {name : 'group2'},
		                {name : 'group3'},
		                {name : 'group4'}
		            ],
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : false
		                    }
		                }
		            },
		            matrix : [
		                [11975,  5871, 8916, 2868],
		                [ 1951, 10048, 2060, 6171],
		                [ 8010, 16145, 8090, 8045],
		                [ 1013,   990,  940, 6907]
		            ]
		        }
		    ]
		};
</script>
</body>
</html>