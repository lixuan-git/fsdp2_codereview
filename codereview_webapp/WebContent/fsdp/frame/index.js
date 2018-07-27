var INDEX_TITLE = "首页";
/**
 * 添加Tab
 * @param node JSON格式，如{action:'http://www.baidu.com',modulename:'百度'}
 * @return
 */
function addTab(node) {
	var layout = $(document.body).layout('panel', 'center');
	var url = node.action;
	var modulename = node.modulename;
	var icon = node.icon;
	var height = $('#tabs').height() - 33;
	if (url != undefined) {
		var content = '<iframe scrolling="auto" frameborder="0" src="' + url
				+ '" style="width:100%;height:' + height + 'px;"></iframe>';
		if(INDEX_TITLE==modulename){
			if ($('#tabs').tabs('exists', modulename)) {
				$('#tabs').tabs('select',modulename);
				var currentTab = $('#tabs').tabs('getSelected');
				var iframe = $(currentTab.panel('options').content);
				$('#tabs').tabs('update', {
					tab : currentTab,
					options : {
						content : iframe
					}
				});
				return;
			}else{
				$('#tabs').tabs('add', {
					title : modulename,
					content : content,
					iconCls : icon
				});
			}
		}else{
			if ($('#tabs').tabs('exists', modulename)) {
				$('#tabs').tabs('close', modulename);
				var closable = true;

				$('#tabs').tabs('add', {
					title : modulename,
					content : content,
					closable : closable,
					iconCls : icon
				});

			} else {
				$('#tabs').tabs('add', {
					title : modulename,
					content : content,
					closable : true,
					iconCls : icon
				});
			}
		}
		
	}

}
$(document.body).ready(function(){
	var context = "";
	var script = $("script");
	$.each(script,function(){
		var src = $(this).attr("src");
		if(null!=src&&src.indexOf("index.js")>-1){
			context = $(this).attr("context");
		}
	});
	var node ={action:context+"/fsdp/frame/portal.jsp",modulename:INDEX_TITLE,icon:'icon-home'};
	addTab(node);
	// 绑定tabs的右键菜单
	$("#tabs").tabs( {
		onContextMenu : function(e, title) {
			e.preventDefault();
			$('#tab_menu').menu('show', {
				left : e.pageX,
				top : e.pageY,
				onClick : function(item) {
					bindTabMenuEvent(item.id);
				}
			});
		}
	});
});
/**
 * 绑定tab右键菜单事件
 */
function bindTabMenuEvent(action) {
	var alltabs = $('#tabs').tabs('tabs');
	var currentTab = $('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs, function(i, n) {
		allTabtitle.push($(n).panel('options').title);
	});
	switch (action) {
	case "tab_menu-refresh":// 刷新当前页面
		var iframe = $(currentTab.panel('options').content);
		$('#tabs').tabs('update', {
			tab : currentTab,
			options : {
				content : iframe
			}
		});
		break;
	case "tab_menu-tabclose":// 关闭当前页面
		var currtab_title = currentTab.panel('options').title;
		if(currtab_title==INDEX_TITLE){
			$.messager.alert("提示",currtab_title+'不允许关闭！','info');
		}else{
			$('#tabs').tabs('close', currtab_title);
		}
		break;
	case "tab_menu-tabcloseall":// 关闭所有
		$.each(allTabtitle, function(i, n) {
			if (n != INDEX_TITLE) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "tab_menu-tabcloseother":// 关闭除当前外其它页面
		var currtab_title = currentTab.panel('options').title;
		$.each(allTabtitle, function(i, n) {
			if (n != currtab_title && n != INDEX_TITLE) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "tab_menu-tabcloseright":// 关闭当前页面右侧页面
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

		if (tabIndex == alltabs.length - 1) {
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i > tabIndex) {
				if (n != INDEX_TITLE) {
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;
	case "tab_menu-tabcloseleft":// 关闭当前页面左侧页面
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i < tabIndex) {
				if (n != INDEX_TITLE) {
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;

	}

}