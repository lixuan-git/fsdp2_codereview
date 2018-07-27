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
			'echarts/chart/eventRiver'], function(ec) {
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
	var echartOption=  {
		    title : {
		        text: 'Event River',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'item',
		        enterable: true
		    },
		    legend: {
		        data:['财经事件', '政治事件']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis : [
		        {
		            type : 'time',
		            boundaryGap: [0.05,0.1]
		        }
		    ],
		    series : [
		        {
		            "name": "财经事件", 
		            "type": "eventRiver", 
		            "weight": 123, 
		            "eventList": [
		                {
		                    "name": "阿里巴巴上市", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-01", 
		                            "value": 14, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-02", 
		                            "value": 34, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-03", 
		                            "value": 60, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-04", 
		                            "value": 40, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-05", 
		                            "value": 10, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }, 
		                {
		                    "name": "阿里巴巴上市2", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-02", 
		                            "value": 10, 
		                            "detail": {
		                                "link": "www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-03", 
		                            "value": 34, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-04", 
		                            "value": 40, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-05", 
		                            "value": 10, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }, 
		                {
		                    "name": "三星业绩暴跌", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-03", 
		                            "value": 24, 
		                            "detail": {
		                                "link": "www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-04", 
		                            "value": 34, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-05", 
		                            "value": 50, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-06", 
		                            "value": 30, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-07", 
		                            "value": 20, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }
		            ]
		        }, 
		        {
		            "name": "政治事件", 
		            "type": "eventRiver", 
		            "weight": 123, 
		            "eventList": [
		                {
		                    "name": "Apec峰会", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-06", 
		                            "value": 14, 
		                            "detail": {
		                                "link": "www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-07", 
		                            "value": 34, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-08", 
		                            "value": 60, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-09", 
		                            "value": 40, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-10", 
		                            "value": 20, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }, 
		                {
		                    "name": "运城官帮透视", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-08", 
		                            "value": 4, 
		                            "detail": {
		                                "link": "www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-09", 
		                            "value": 14, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-10", 
		                            "value": 30, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-11", 
		                            "value": 20, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-12", 
		                            "value": 10, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }, 
		                {
		                    "name": "底层公务员收入超过副部长", 
		                    "weight": 123, 
		                    "evolution": [
		                        {
		                            "time": "2014-05-11", 
		                            "value": 4, 
		                            "detail": {
		                                "link": "www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-12", 
		                            "value": 24, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-13", 
		                            "value": 40, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-14", 
		                            "value": 20, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-15", 
		                            "value": 15, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }, 
		                        {
		                            "time": "2014-05-16", 
		                            "value": 10, 
		                            "detail": {
		                                "link": "http://www.baidu.com", 
		                                "text": "百度指数", 
		                                "img": '${ctx}/fsdp/resource/images/logo.png'
		                            }
		                        }
		                    ]
		                }
		            ]
		        }
		    ]
		};
</script>
</body>
</html>