<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${SYSPARAM_KEY['系统标题'] }</title>
${style_css }
${jquery_js }
${finedo_core_js }

<script>
// 显示、隐藏全部权限视图DIV
function showallview(){
	var displayflag=$('#head-menu').css('display');
	if(displayflag == 'block'){
		$('#head-menu').css('display', 'none');
	}else{
		$('#head-menu').css('display', 'block');
	}
}
	
// 显示全部权限视图DIV
function showviewdiv(){
	$('#head-menu').css('display', 'block');
}
// 隐藏全部权限视图DIV
function hideviewdiv(){
	$('#head-menu').css('display', 'none');
}

// 选中权限视图节点效果
function chooseviewnode(object){
	$(".head-menu").find("a").attr("class","a-link");
	$(object).attr("class","a-hover");
}

// 显示左侧二级权限视图节点
function showsubmenu(nodeid) {
	var submenu_id="submenu_" + nodeid;
	
	// 显示指定节点, 隐藏其他节点
	var submenulist=$("div[id^='submenu_']");
	for(var i=0; i<submenulist.length; i++) {
		var objid=submenulist.eq(i).attr('id');
		if(submenu_id == objid) {
			$("#" + objid).attr("style", "display:block");
			
			// 显示第一个节点
			$('#mainFrame').attr('src', $('#' + objid).find('.submenu-a').eq(0).attr('href'));
		}else {
			$("#" + objid).attr("style", "display:none");
		}
	}
}

window.onresize = function(){
	if($('#menu').css('display') == 'block') {
		$('#menu').css('display', 'none');
	}else {
		$('#menu').css('display', 'block');
	}

	hidebottom();
};

function submenu(obj){
	$(".submenu-con").find("a").attr("class","submenu-a");
	$(obj).attr("class","submenu-hover");
}	

//左侧菜单显示隐藏
function hidebottom(){
	var displayflag=$('#menu').css('display');
	if(displayflag == 'block'){
		$('#menu').css('display', 'none');
		$('#icon-openstop').attr('class', 'icon-openstop-hide');
		$('#right').css('width', document.body.scrollWidth);
	}else{
		$('#menu').css('display', 'block');
		$('#icon-openstop').attr('class', 'icon-openstop');
		$('#right').css('width', document.body.scrollWidth-250);
	}
}

function showcom(){
	if($('#head-comm').css('display') == 'block'){
		$('#head-comm').css('display', 'none');
	}else{
		$('#head-comm').css('display', 'block');
	}
}

function hidecomm(){
	$('#head-comm').css('display', 'none');
}

function showcommdiv(){
	$('#head-comm').css('display', 'block');
}

function hidemicro(){
	$('#micro-search-menu').css('display', 'none');
	$('#head-micro-search').attr('class', 'head-micro-search');
}

function showmicrodiv(){
	$('#micro-search-menu').css('display', 'block');
}

function showmicro(){
	var obj=document.getElementById('micro-search-menu').style.display;
	if($('#micro-search-menu').css('display') == 'block'){
		$('#micro-search-menu').css('display', 'none');
		$('#head-micro-search').attr('class', 'head-micro-search');
	}else{
		$('#micro-search-menu').css('display', 'block');
		$('#head-micro-search').attr('class', 'head-micro-search-hover');
	}
}

$(window).load(function() {
	$("#right").width(document.body.scrollWidth-250);
	var searchobj=document.getElementById('search');
 	searchobj.onfocus=function(){
		this.value='';
	};
	
	searchobj.onblur=function(){
		this.value='请输入查询条件';
	};
});
</script>
</head>

<body style="overflow:hidden;">

<!-------- 头部 -------->
<div class="head">
	<div class="logo">${SYSPARAM_KEY['系统标题'] }<span class="version">${SYSPARAM_KEY['版本号'] }</span></div>
    
    <!-- 显示前6个一级权限视图节点 -->
    <div class="head-nav">
        <ul>
	        <c:set var="nodecount" value="0"></c:set>
	        <c:forEach var="userright" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}" >
		        <c:if test="${userright.parentnodeid eq '0' and userright.isnavigation eq '是' and nodecount < 6 }">
		       		<c:set var="nodecount" value="${nodecount + 1 }"></c:set>
		       		
		       		<c:if test="${nodecount eq 1 }">
		       		<script>
			       		// 加载第一个节点的子节点
			       		window.onload=function(){
			       			showsubmenu("${userright.nodeid }");
			       		}
		       		</script>
		       		</c:if>
		       		
					<li><a href="#" onclick="showsubmenu('${userright.nodeid}')" id="${userright.nodeid}">${userright.nodename }</a></li>
				</c:if>
	        </c:forEach>
        </ul>
    </div>
    
    <!-------- 显示所有一级权限视图节点 -------->
    <div class="head-operate" onMouseOut="hideviewdiv()" >
    	<input class="head-more" type="button" value="" onClick="showallview()" >
        <div class="head-menu" id="head-menu" onMouseOver="showviewdiv()" style=" display:none;" >
            <c:forEach var="userright" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}" >
            	<c:if test="${userright.parentnodeid eq '0' and userright.isnavigation eq '是'}">
            		<a href="#" onclick="chooseviewnode(this); showsubmenu('${userright.nodeid}')" class="a-link">${userright.nodename }</a>
            	</c:if>
            </c:forEach>
        </div>
    </div>
    
    <!--------常用菜单-------->
    <%--
    <div class="head-common-menu"  onMouseOut="hidecomm()" onClick="showcom()">
    	<input class="head-common" type="button" value="常用" >
        <div class="head-comm"  id="head-comm" style=" display:none;" onMouseOver="showcommdiv()" >
        	<a href="#">任务执行<input type="button" class="head-comm-close" value=""></a>
            <a href="#">项目执行<input type="button" class="head-comm-close" value=""></a>
            <a href="#">我的任务<input type="button" class="head-comm-close" value=""></a>
            <a href="#">代办事项<input type="button" class="head-comm-close" value=""></a>
            <a href="#">任务查询<input type="button" class="head-comm-close" value=""></a>
            <a href="#">调休申请<input type="button" class="head-comm-close" value=""></a>
            <span class="head-add-div"><input class="head-comm-add" type="button" value="添加"><input class="head-comm-del" type="button" value="清空"></span>
        </div>
    </div>
    --%>
    
    <!--------搜索-------->
    <div class="head-common-menu">
    	<form  >
    	<input type="button" value="快搜" class="head-micro-search" id="head-micro-search" onClick="showmicro(this)">
        <input type="text" class="head-search" value="请输入查询条件" id="search">
        <input type="button" value="" class="head-query">
        <div class="micro-search-menu" id="micro-search-menu" style=" display:none;" onMouseOver="showmicrodiv()" onMouseOut="hidemicro()">
        	<a href="#"><span class="span"></span>用户</a>
        </div>
        
        </form>
    </div>
    <div class="head-right"><input type="button" class="head-close" value="" title="退出" onClick="window.location.href='${ctx }/finedo/auth/logout'"></div>
</div>

<!-------- 左侧二级权限视图节点 -------->
<div class="menu" id="menu" style=" display:block; height:100%; ">
    <div class="infor"><span class="font13">${LOGINDOMAIN_KEY.sysuser.personname}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span title="${LOGINDOMAIN_KEY.sysorg.orgname}">${fsdp:substr(LOGINDOMAIN_KEY.sysorg.orgname, 12)}</span></div>
    
    <c:forEach var="userright1" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}" >
    	<c:if test="${userright1.parentnodeid eq '0' and userright1.isnavigation eq '是'}">
    	<div class="submenu" id="submenu_${userright1.nodeid }" style="display:none">
		    <div class="submenu-name font13">${userright1.nodename }</div>
		    	
	    	<div class="submenu-con">
			    <ul>
		    	<c:forEach var="userright2" items="${sessionScope.LOGINDOMAIN_KEY.userrightlist}" >
			        <c:if test="${userright2.parentnodeid eq userright1.nodeid and userright2.isnavigation eq '是'}">
			        	<li><a class="submenu-a" onClick="submenu(this)" href="${ctx }${userright2.rightentry }" target="mainFrame"><span class="menu-icon"></span>${userright2.nodename }</a></li>
			        </c:if>
			    </c:forEach>
			    </ul>
		    </div>
		</div>
		</c:if>
    </c:forEach>
    
    <div class="menu-bottom">
    	<ul>
        	<li title="设置"><a class="icon-shortcut" href="${ctx}/fsdp/jsp/config/config-personal.jsp" target="mainFrame"></a></li>
            <li class="icon-shortcut icon-password" title="密码修改" ><a href="right-password.html" target="mainFrame"></a></li>
            <li class="icon-shortcut icon-skin-peeler" title="换肤"></li>
            <li class="icon-shortcut icon-news" title="消息"></li>
        </ul>
    </div>
</div>

<input class="icon-openstop" id="icon-openstop" type="button" value="" onClick="hidebottom()">
<div style="float:right; position:fixed; right:0; top:54px; bottom:0; z-index:-100; +height:650px; +overflow-y:auto; " id="right">
	<iframe src="#" height="100%" width="100%" frameborder="0" scrolling="yes" id="mainFrame" name="mainFrame" ></iframe> 
</div>

</body>
</html>
