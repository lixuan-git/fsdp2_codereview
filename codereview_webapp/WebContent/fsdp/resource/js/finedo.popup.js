/**
 * 消息弹出窗口
 */ 
finedo.message = {
	info:function(content, title, callback, style){
		this.showMessage(content, title, "info", callback, style);
	},
	error:function(content, title, callback, style){
		this.showMessage(content, title, "error", callback, style);
	},
	question:function(content, title, callback, style){
		this.showMessage(content, title, "question", callback, style);
	},
	warning:function(content, title, callback, style){
		this.showMessage(content, title, "warning", callback, style);
	},
	showMessage:function(content, title, type, callback, style){
		var imgclassName = "";
		if(title == undefined){
			if(type == "info"){
				title = "提示";
				imgclassName = "message-info";
			} else if(type == "question"){
				title = "询问";
				imgclassName = "message-question";
			} else if(type == "error"){
				title = "错误";
				imgclassName = "message-error";
			} else if(type == "warning"){
				title = "警告";
				imgclassName = "message-warning";
			}
		}else{
			if(type == "info") imgclassName = "message-info";
			else if(type == "question") imgclassName = "message-question";
			else if(type == "error") imgclassName = "message-error";
			else if(type == "warning") imgclassName = "message-warning";
		}
		$("<div class=\"message-mask\"></div>").css({display:"block",width:"100%",height:$(document).height()}).appendTo("body"); 
		var msgdivhtml = "<div class=\"message\" "+(style != undefined ? "style=\"width:500px;\"" : "")+"><div class=\"message-til\"><strong>"+title+"</strong>" +
			"<input class=\"message-close\" type=\"button\" id=\"fdmessageclosebtn\"/></div><div class=\"message-con\">" +
			"<img class=\""+imgclassName+"\"/><p><label id=\"fdmessagecontent\"></label></p><p class=\"message-center\">";
		if(type == "question"){
			msgdivhtml += "<input type=\"button\" class=\"message-button\" value=\"确定\" id=\"fdmessageokbtn\"/>&nbsp;<input type=\"button\" class=\"message-button\" value=\"取消\" id=\"fdmessagecancelbtn\"/>";
		} else{
			msgdivhtml += "<input type=\"button\" class=\"message-button\" value=\"确定\" id=\"fdmessageokbtn\"/>";
		}
		msgdivhtml += "</p></div></div>";
		$(msgdivhtml).appendTo("body");
		
		//拖动效果
		$(".message").mousedown(function(e){  
            $(this).css("cursor","move");//改变鼠标指针的形状  
            var offset = $(this).offset();//DIV在页面的位置  
            var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离  
            var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离  
            $(document).bind("mousemove",function(ev)//绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件  
            {  
                $(".show").stop();//加上这个之后  
                var _x = ev.pageX - x;//获得X轴方向移动的值  
                var _y = ev.pageY - y;//获得Y轴方向移动的值  
                $(".message").css({left:_x+"px",top:_y+"px"});  
            });  
              
        });  
        $(document).mouseup(function() {  
        	$(".message").css("cursor","default");  
        	$(this).unbind("mousemove");  
        });
		
		$("#fdmessagecontent").html(content);
		$(".message").css({display:"block",left:($(document.body).outerWidth(true) - $(".message").width()) / 2,top:($(window).height()/2-$(".message").height()/2)});
		
		$("#fdmessageclosebtn").bind("click", function(){
			$(".message-mask").remove();
			$(".message").remove();
		});
		$("#fdmessageokbtn").bind("click", function(){
			$(".message-mask").remove();
			$(".message").remove();
			if($.isFunction(callback)){
				callback(true);
			}
		});
		$("#fdmessagecancelbtn").bind("click", function(){
			$(".message-mask").remove();
			$(".message").remove();
			if($.isFunction(callback)){
				callback(false);
			}
		});
	},
	showWaiting:function(content){
		$("<div class=\"showWaiting-mask\"></div>").css({display:"block",width:"100%",height:$(document).height()}).appendTo("body"); 
	    $("<div class=\"showWaiting-mask-msg\"></div>").html(content!=undefined ? content : "加载中，请稍等!").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(document).height()-$(window).height() + $(window).height()/2)});
	},
	hideWaiting:function(){
		$(".showWaiting-mask").remove(); 
		$(".showWaiting-mask-msg").remove();  
	}
};
