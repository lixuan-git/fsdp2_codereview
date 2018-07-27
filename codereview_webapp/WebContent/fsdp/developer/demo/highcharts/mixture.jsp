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
	window.location.href="${ctx }/fsdp/developer/demo/highcharts/mixture.jsp?theme="+themeName;
}
$(function () {                                                               
    $('#container').highcharts({                                          
        chart: {                                                          
        },                                                                
        title: {                                                          
            text: 'Combination chart'                                     
        },                                                                
        xAxis: {                                                          
            categories: ['Apples', 'Oranges', 'Pears', 'Bananas', 'Plums']
        },                                                                
        tooltip: {                                                        
            formatter: function() {                                       
                var s;                                                    
                if (this.point.name) { // the pie chart                   
                    s = ''+                                               
                        this.point.name +': '+ this.y +' fruits';         
                } else {                                                  
                    s = ''+                                               
                        this.x  +': '+ this.y;                            
                }                                                         
                return s;                                                 
            }                                                             
        },                                                                
        labels: {                                                         
            items: [{                                                     
                html: 'Total fruit consumption',                          
                style: {                                                  
                    left: '40px',                                         
                    top: '8px',                                           
                    color: 'black'                                        
                }                                                         
            }]                                                            
        },                                                                
        series: [{                                                        
            type: 'column',                                               
            name: 'Jane',                                                 
            data: [3, 2, 1, 3, 4]                                         
        }, {                                                              
            type: 'column',                                               
            name: 'John',                                                 
            data: [2, 3, 5, 7, 6]                                         
        }, {                                                              
            type: 'column',                                               
            name: 'Joe',                                                  
            data: [4, 3, 3, 9, 0]                                         
        }, {                                                              
            type: 'spline',                                               
            name: 'Average',                                              
            data: [3, 2.67, 3, 6.33, 3.33],                               
            marker: {                                                     
            	lineWidth: 2,                                               
            	lineColor: Highcharts.getOptions().colors[3],               
            	fillColor: 'white'                                          
            }                                                             
        }, {                                                              
            type: 'pie',                                                  
            name: 'Total consumption',                                    
            data: [{                                                      
                name: 'Jane',                                             
                y: 13,                                                    
                color: Highcharts.getOptions().colors[0] // Jane's color  
            }, {                                                          
                name: 'John',                                             
                y: 23,                                                    
                color: Highcharts.getOptions().colors[1] // John's color  
            }, {                                                          
                name: 'Joe',                                              
                y: 19,                                                    
                color: Highcharts.getOptions().colors[2] // Joe's color   
            }],                                                           
            center: [100, 80],                                            
            size: 100,                                                    
            showInLegend: false,                                          
            dataLabels: {                                                 
                enabled: false                                            
            }                                                             
        }]                                                                
    });                                                                   
});
</script>
</body>
</html>