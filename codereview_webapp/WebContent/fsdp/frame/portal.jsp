<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>桌面设置</title> ${jquery_js } ${easyui_js } ${easyui_portal_js
}${style_css }
<script>
	$(function() {
		$('#pp').portal({
			  border:false,
              fit:true,
              onStopDrag:getLayoutstr
              
		});
		getlayout();
	});
	
	function getlayout() {
		var layouturl = '${ctx }/finedo/deskmode/queryusermode';
		if('${param.show}'=='yes'){
			layouturl = '${ctx }/finedo/deskmode/questsysmode';			
		}
		$.ajax({
			type : "GET",
			url : layouturl,
			dataType : "json",
			success : function(data) {		
				 $.each(data,function(m,value) {
		//alert("url:"+value.modeurl);
		var modeurl = value.modeurl;
	var t = value.modename;
	var modeid = value.modeid;
	//alert(modeid);
	var modecolumn = value.modecolumn;
	var p = $('<div/>').appendTo('body');
							//<%-- 此处的j是代表在那一列中的，如果不用数据库中的列，系统自动排列请将j=m%2;m为对象的index，2为html中定义的列数 --%>
							var j = modecolumn;
							//alert("我的位置是："+j);
							//alert("test"+'${param.show}');
							if('${param.show}'=='yes'){
							p.panel({
								id:modeid,
								href:'${ctx}'+modeurl,
								title:t,
								height:350,
								closable:true,
								collapsible:true,
								onBeforeClose:function (){return  confirm("是否屏蔽此模块？");},
								onClose: function (){$(this).remove();
				        		  getLayoutstr();	
								}
							});
							}else{
								p.panel({
									id:modeid,
									title:t,
									href:'${ctx}'+modeurl,
									height:350,
									closable:false,
									collapsible:true
								});
							}
							
							$('#pp').portal('add', {
								panel:p,
								columnIndex:j
							});					
					 
				 });
					$('#pp').portal('resize');
			}
		}
	);
	}
		
	 function getLayoutstr(){
		 //alert("获取布局了");
         //获取布局。
         var layoutstr = "";
         var divs = $("div[id^='idno']");
         //alert("divs:"+divs.length);
         for(var i=0; i<divs.length; i++){
            var id = divs.eq(i).attr("id");
            layoutstr += $("#"+id).parents("div").parents("div").attr("id")+","+id+";";
          //  alert(layoutstr);
         }//<%--此处是获取移动后页面布局的把方法，一定要注意layoutstr的值获取 --%>
         $.ajax({
 			type : "GET",
 			url : '${ctx }/finedo/deskmode/setlayout?layout='+layoutstr,
 			dataType : "json"
         });
	 }
	 
	function remove() {
		$('#pp').portal('remove', $('#pgrid'));
		$('#pp').portal('resize');
	}

function htmlappend(){
	   var divshow = $("#pp");
    //divshow.text("");// 清空数据
   
	var data = "<div style=\"width: 30%;\"><div title=\"待办工作\" collapsible=\"true\" style=\"height: 308px; overflow: hidden; padding: 4px; background: #fff; width: 100%\"	iconCls=\"icon-task\" type=\"module\">	<div id=\"ptable\" style=\"text-align: left; width: 100%\">		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"	class=\"listtable\" style=\"width: 100%\"></table></div></div></div><div style=\"width: 30%;\"><div title=\"待办工作\" collapsible=\"true\" style=\"height: 308px; overflow: hidden; padding: 4px; background: #fff; width: 100%\"	iconCls=\"icon-task\" type=\"module\">	<div id=\"ptable\" style=\"text-align: left; width: 100%\">		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"	class=\"listtable\" style=\"width: 100%\"></table></div></div></div>";
    divshow.append(data);
}

</script>
</head>
<body class="easyui-layout">

	<div region="center" border="false">
<c:if test="${param.show eq 'yes'}">
	<p style="text-align: center;"><input type="button" value="保存桌面设置"   onclick="javascript:getLayoutstr();"/></p>
	
	</c:if>
		<div id="pp" style="position: relative">
			<div id="d0">
				</div>
			<div id="d1" >
			</div>
			<!-- 此处是设置页面有几列值的,，如果增加请按如下格式<div id="dx"></div> X是列值最多0-9，不能是2位数， 此处与js中的控制列数的相互对应，否则会出现空白或者展示不全-->
		</div>
	</div>
</body>
</html>