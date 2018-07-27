;(function($, undefined) {
	/**
	 * 进度条控件
	 * options:{
	 * 	content: 进度条中显示的内容
	 * }
	 */
	$.fn.progress = function(options){
		var $finedoprogress = this;
		if(options){
			$finedoprogress.data('options', options);
		}
		
		/**
		 * 设置进度条中显示的内容
		 */
		this.setContent = function(content){
			$finedoprogress.data('options').content = content;
			$('#'+$finedoprogress.attr('id')+'progressmessage').html(content+'&nbsp;&nbsp;(0%)');
		};
		
		/**
		 * 设置进度值
		 */
		this.setProgress = function(progress){
			progress += '';
    		var regu = "^[1-9]{1}[0-9]*$";
    		var re = new RegExp(regu);
    		if (progress.search(re) == -1) {
    			finedo.message.warning("进度值必须是正整数！");
    			return;
    		}
    		if(progress < 0 || progress > 100)
    			return;
			var options = $finedoprogress.data('options');
			if(options.content && options.content != ''){
        		$('#'+$finedoprogress.attr('id')+'progressmessage').html(options.content+'&nbsp;&nbsp;('+progress+'%)');
        	}else{
        		$('#'+$finedoprogress.attr('id')+'progressmessage').html(progress+'%');
        	}
    		$('#'+$finedoprogress.attr('id')+'progress').css('width', progress+'%');
		};
		
        /**
         * 控件初始化
         */
        this.init = function(){
        	if($finedoprogress.data('init') == true)
        		return $finedoprogress;
        	$finedoprogress.data('init', true);
        	var defaults = {
        		content: ''//进度条中显示的内容
            };
        	var options = $.extend(defaults,$finedoprogress.data('options'));
        	$finedoprogress.data('options', options);
        	$finedoprogress.html('<div class="progressbar"><div class="progressbar-progress" id="'+$finedoprogress.attr('id')+'progress"></div><div class="progressbar-message" id="'+$finedoprogress.attr('id')+'progressmessage">0%</div></div>');
        	if(options.content && options.content != ''){
        		$('#'+$finedoprogress.attr('id')+'progressmessage').html(options.content+'&nbsp;&nbsp;(0%)');
        	}
			return $finedoprogress;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义控件获取函数
 */ 
finedo.getProgress = function(controlid, options){
	var progressControl = $('#'+controlid).progress(options);
	return progressControl;
};




