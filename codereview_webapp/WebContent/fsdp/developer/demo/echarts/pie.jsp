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
			'echarts/chart/funnel',
			'echarts/chart/pie'], function(ec) {
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
		        text: '某站点用户访问来源',
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'访问来源',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ]
		        }
		    ]
		};
</script>
</body>
</html>