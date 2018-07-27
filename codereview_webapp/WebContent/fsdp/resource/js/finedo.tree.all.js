;(function($, undefined) {
	/**
	 * 树形控件
	 * options:{
	 * 	url: 控件数据加载action，返回数据格式为对象数组
	 * 	onclick: 节点单击事件回调
	 * 	async：true/false，是否异步加载，如果是则点击节点调用服务加载下级，否则不再调用服务加载下级，一次读取数据。默认为false
	 * 	asyncurl：异步加载url，即加载下级节点的url，只有async为true时才生效
	 * 	simple：true/false，json数据对象是否为简单的数据格式，即数组对象没有下级对象，通过pid查找下级节点，默认为false
	 * 	selecttype:(single/multi/''),单选与多选，默认为空
	 * 	cascade:true/false,级联，默认为true
	 * }
	 * 树形控件节点数据格式：
	 * {
	 * 	id:节点id
	 * 	pid:上级节点id
	 * 	name:节点名称，显示当前节点的名称串
	 * 	icon：自定义节点图标
	 * 	open:true/false，节点当前是否已展开
	 * 	children:[]下级节点列表
	 * }
	 * 查询树节点返回json对象结构实例：{
		    "object": [
		        {
		            "id": "XX",
		            "pid": "parentid",
		            "name": 组织节点,
		            "icon": "images/test.png",
		            "open": "true",
		            "children": []
		        }
		    ],
		    "resultcode": "SUCCESS",
		    "resultdesc": "查询组织结构信息成功",
		    "resultlist": [],
		    "resultlistString": "",
		    "success": true
		}
	 */
	$.fn.tree = function(options){
		var $finedotree = this;
		var $clickitem;//当前被点击的对象
		var $treeid = $finedotree.attr('id');
		var eventName = 'selectdata';
		var default_options = {
			async:false,
			simple:false,
			cascade:true
		};
		var fullcheckcls = "node chk checkbox_true_full";
		var fulluncheckcls = "node chk checkbox_false_full";
		var switchopencls = "node switch noline_open";
		var switchclosecls = "node switch noline_close";
		var iconopencls = "node ico_open";
		var iconclosecls = "node ico_close";
		if(finedo.fn.isNon($finedotree.data('options'))){
			var options = $.extend(default_options,options);
			$finedotree.data('options', options);
		}

		this.setUrl = function(url){
			$finedotree.data('options').url = url;
		};

		this.setOnclick = function(onclick){
			$finedotree.data('options').onclick = onclick;
		};

		this.setAsync = function(async){
			$finedotree.data('options').async = async;
		};

		this.setAsyncurl = function(asyncurl){
			$finedotree.data('options').asyncurl = asyncurl;
		};

		this.setSimple = function(simple){
			$finedotree.data('options').simple = simple;
		};

		this.setSelecttype = function(selecttype){
			$finedotree.data('options').selecttype = selecttype;
		};
		
		this.setCascade = function(cascade){
			$finedotree.data('options').cascade = cascade;
		};
		
		this.dataSelected = function(data, selected){
			if(finedo.fn.isNon($finedotree.data('options').selecttype))
				return;
			if(selected == true){
				finedo.fn._triggerEvent(eventName, data);
			}else{
				finedo.fn._triggerEvent('un'+eventName, data);
			}
		};
		
		/**
		 * 级联处理上级
		 */
		this.cascadeParent = function(data, selected){
			if(data.pid == '0' || finedo.fn.isNon(data.pid)){
				return;
			}
			var pdata = $('#'+$treeid+'_'+data.pid).data('data');
			var ischecked = true;
			for(var i = 0; i < pdata.children.length; i++){
				if($('#'+$treeid+'_'+pdata.children[i].id+'_check').attr("treenode_check") == "false"){
					ischecked = false;
					break;
				}
			}
			if(selected == true){
				if(ischecked == true){
					$('#'+$treeid+'_'+pdata.id+'_check').attr("treenode_check", "true");
					$('#'+$treeid+'_'+pdata.id+'_check').removeClass(fulluncheckcls);
					$('#'+$treeid+'_'+pdata.id+'_check').addClass(fullcheckcls);
					$finedotree.dataSelected(pdata ,true);
					$finedotree.cascadeParent(pdata, selected);
				}
			}else{
				if(ischecked == false){
					$('#'+$treeid+'_'+pdata.id+'_check').attr("treenode_check", "false");
					$('#'+$treeid+'_'+pdata.id+'_check').removeClass(fullcheckcls);
					$('#'+$treeid+'_'+pdata.id+'_check').addClass(fulluncheckcls);
					$finedotree.dataSelected(pdata ,false);
					$finedotree.cascadeParent(pdata, selected);
				}
			}
			
		};
		
		/**
		 * 级联处理下级
		 */
		this.cascade = function(data, selected){
			if(finedo.fn.isNon(data.children))
				return;
			for(var i = 0; i < data.children.length; i++){
				if(selected == false){
					$('#'+$treeid+'_'+data.children[i].id+'_check').attr("treenode_check", "false");
					$('#'+$treeid+'_'+data.children[i].id+'_check').removeClass(fullcheckcls);
					$('#'+$treeid+'_'+data.children[i].id+'_check').addClass(fulluncheckcls);
					$finedotree.dataSelected(data.children[i] ,false);
				}else{
					$('#'+$treeid+'_'+data.children[i].id+'_check').attr("treenode_check", "true");
					$('#'+$treeid+'_'+data.children[i].id+'_check').removeClass(fulluncheckcls);
					$('#'+$treeid+'_'+data.children[i].id+'_check').addClass(fullcheckcls);
					$finedotree.dataSelected(data.children[i] ,true);
				}
				$finedotree.cascade(data.children[i], selected);
			}
		};

		/**
		 * 递归遍历所有下级节点
		 */
		this.parseChildren = function(data, node){
			for(var i = 0; i < data.length; i++){
				if(data[i].pid != node.id){
					continue;
				}
				if(finedo.fn.isNon(node.children)){
					node.children = [];
				}
				node.children.push(data[i]);
				$finedotree.parseChildren(data, data[i]);
			}
		};
		
		/**
		 * 格式化数据，将简单的数据格式化为树形结构的数据
		 */
		this.formatData = function(data){
			var formatdata = [];
			for(var i = 0; i < data.length; i++){
				if(data[i].pid != '0' || finedo.fn.isNotNon(data[i].pid)){
					continue;
				}
				formatdata.push(data[i]);
				$finedotree.parseChildren(data, data[i]);
			}
			return formatdata;
		};
		
		/**
		 * 加载树节点
		 */
		this.loadNodes = function(parentNode, data){
			var rootopen = true;
			for(var i = 0; i < data.length; i++){
				rootopen = parentNode == $finedotree && i == 0 ? true : false;
				var node = $('<li id="'+$treeid+'_'+data[i].id+'">');
				node.data('data', data[i]);
				var switchNode, checkNode, aNode;
				if(finedo.fn.isNon(data[i].children)){
					switchNode = $('<span id="'+$treeid+'_'+data[i].id+'_switch" class="node switch noline_docu">');
					aNode = $('<a id="'+$treeid+'_'+data[i].id+'_a" title="'+data[i].name+'">\
							<span id="'+$treeid+'_'+data[i].id+'_ico" class="node ico_docu"></span>\
							<span id="'+$treeid+'_'+data[i].id+'_span">'+data[i].name+'</span>');
				}else{
					switchNode = $('<span id="'+$treeid+'_'+data[i].id+'_switch" class="'+(rootopen ? switchopencls: switchclosecls)+'">');
					aNode = $('<a id="'+$treeid+'_'+data[i].id+'_a" title="'+data[i].name+'">\
							<span id="'+$treeid+'_'+data[i].id+'_ico" class="'+(rootopen ? iconopencls: iconclosecls)+'"></span>\
							<span id="'+$treeid+'_'+data[i].id+'_span">'+data[i].name+'</span>');
				}
				if($(switchNode).hasClass(switchopencls)){
					$(switchNode).attr('treenode_switch', 'close');
				}else{
					$(switchNode).attr('treenode_switch', 'open');
				}
				node.append(switchNode);
				if($finedotree.data('options').selecttype == 'multi'){
					checkNode = $('<span id="'+$treeid+'_'+data[i].id+'_check" class="'+fulluncheckcls+'">');
					$(checkNode).attr('treenode_check', 'false');
					checkNode.bind('click', data[i], function(event){
						if($(this).attr('treenode_check') == 'true'){
							$(this).attr('treenode_check', 'false');
							$(this).removeClass(fullcheckcls);
							$(this).addClass(fulluncheckcls);
							$finedotree.dataSelected(event.data ,false);
							if(finedo.fn.isTrue($finedotree.data('options').cascade)){
								$finedotree.cascade(event.data, false);
								$finedotree.cascadeParent(event.data, false);
							}
						}else{
							$(this).attr('treenode_check', 'true');
							$(this).removeClass(fulluncheckcls);
							$(this).addClass(fullcheckcls);
							$finedotree.dataSelected(event.data ,true);
							if(finedo.fn.isTrue($finedotree.data('options').cascade)){
								$finedotree.cascade(event.data, true);
								$finedotree.cascadeParent(event.data, true);
							}
						}
					});
					node.append(checkNode);
				}else if($finedotree.data('options').selecttype == 'single'){
					aNode.bind('dblclick', data[i], function(event){
						finedo.fn._triggerEvent(eventName, event.data);
					});
				}
				node.append(aNode);
				
				switchNode.bind('click', data[i], function(event){
					var ulid = $treeid+'_'+event.data.id+'_';
					if($(this).attr('treenode_switch') == 'close'){
						$(this).attr('treenode_switch', 'open');
						$(this).removeClass(switchopencls);
						$(this).addClass(switchclosecls);
						$('#'+ulid + 'ico').removeClass(iconopencls);
						$('#'+ulid + 'ico').addClass(iconclosecls);
						$('#'+ulid + 'ul').hide();
					}else{
						$(this).attr('treenode_switch', 'close');
						$(this).removeClass(switchclosecls);
						$(this).addClass(switchopencls);
						$('#'+ulid + 'ico').removeClass(iconclosecls);
						$('#'+ulid + 'ico').addClass(iconopencls);
						$('#'+ulid + 'ul').show();
					}
				});
				if($.isFunction($finedotree.data('options').onclick)){
					aNode.bind('click', data[i], function(event){
						$finedotree.data('options').onclick(event.data);
					});
				}
				
				parentNode.append(node);
				if(finedo.fn.isNotNon(data[i].children)){
					var ulNode = $('<ul id="'+$treeid+'_'+data[i].id+'_ul" style="display:'+(rootopen ? 'block': 'none')+';">');
					node.append(ulNode);
					$finedotree.loadNodes(ulNode, data[i].children);
				}
			}
		};
		
		this.load = function(reqdata){
			finedo.action.doJsonRequest($finedotree.data('options').url, reqdata, function(data){
				var objdata = data;
				if(finedo.fn.isNotNon(data.resultcode)){
					if(data.resultcode != finedo.resultcode.success){
						finedo.message.error(data.resultdesc);
						return;
					}
					objdata = objdata.object;
				}
				if(finedo.fn.isTrue($finedotree.data('options').simple))
					objdata = $finedotree.formatData(objdata);
				$finedotree.loadNodes($finedotree, objdata);
			});
		};
		
		this.test = function(data){
			if(finedo.fn.isTrue($finedotree.data('options').simple))
				data = $finedotree.formatData(data);
			$finedotree.loadNodes($finedotree, data);
		};
		
        /**
         * 控件初始化
         */
        this.init = function(){
        	if($finedotree.data('init') == true)
        		return $finedotree;
        	$finedotree.data('init', true);
        	
        	var attroptions = {};
        	$.each($(this)[0].attributes, function(index, attr) {
        		attroptions[attr.name] = attr.value;
			});
        	if(finedo.fn.isFunction(attroptions.onclick))
        		attroptions.onclick = eval(attroptions.onclick);
        	var options = $.extend(attroptions,$finedotree.data('options'));
			$finedotree.data('options', options);
			
        	$finedotree.load();
			return $finedotree;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义控件获取函数
 */ 
finedo.getTree = function(controlid, options){
	var treeControl = $('#'+controlid).tree(options);
	return treeControl;
};

/**
 * 页面加载后自动加载树控件
 */
$(document.body).ready(function(){
	$(document.body).find(".fdtree").each(function(treeindex,treeitem){
		if(finedo.fn.isNotNon($(treeitem).attr('id')))
			finedo.getTree($(treeitem).attr('id'));
	});
});

