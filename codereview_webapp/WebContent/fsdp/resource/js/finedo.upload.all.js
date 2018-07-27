;(function($, undefined) {
	/**
	 * 上传控件
	 * options:{
	 * 	data: 请求参数，Json格式(文件上传时的附带参数)
	 * 	files: 文件ID串，多个以逗号分开
	 * 	showicon: true/false,默认为true，是否显示图标
	 * 	showname: true/false,默认为true，是否显示原始名称
	 * 	showsize: true/false,默认为true，是否显示文件大小
	 * 	onclick: 点击文件的回调函数
	 * 	onuploadcomplete: 上传成功后回调函数
	 * 	filter: 上传文件类型限制，不传不限制，如:doc,xls,xlsx,docx,txt,png,jpg
	 * 	multi: true/false,默认为true，是否支持多文件
	 * 	multidown: true/false，是否支持批量下载，默认为false不支持批量下载
	 * 	editable: true/false，是否可编辑，默认为true，不支持只提供下载预览功能，可编辑增加上传与删除功能
	 * 	deletecallback: 删除回调函数
	 * 	entityid: 关联id
	 * 	oninitcomplete: 控件初始化完成回调
	 * }
	 */
	$.fn.file = function(options){
		var $finedofile = this;
		if(options){
			$finedofile.data('options', options);
		}
		var $fileuploadurl = '/finedo/file/upload';
		$fileuploadurl = ctx && ctx!='' ? ctx+$fileuploadurl : $fileuploadurl;
		var $fileloadurl = '/finedo/file/query';
		$fileloadurl = ctx && ctx!='' ? ctx+$fileloadurl : $fileloadurl;
		var $filedeleteurl = '/finedo/file/delete';
		$filedeleteurl = ctx && ctx!='' ? ctx+$filedeleteurl : $filedeleteurl;
		var $filedownloadurl = '/finedo/file/download';
		$filedownloadurl = ctx && ctx!='' ? ctx+$filedownloadurl : $filedownloadurl;
		var $filedownloadmultiurl = '/finedo/file/downloadmulti';
		$filedownloadmultiurl = ctx && ctx!='' ? ctx+$filedownloadmultiurl : $filedownloadmultiurl;
		
		var $filelist = [];
        /**
         * 支持多文件操作
         */
        this.setMulti = function(isMulti){
        	$finedofile.data('options').multi = isMulti;
        };
        
        /**
         * 设置文件点击事件
         */
        this.setOnclick = function(onclick){
        	$finedofile.data('options').onclick = onclick;
        };
        
        /**
         * 设置上传成功回调函数
         */
        this.setOnuploadcomplete = function(onuploadcomplete){
        	$finedofile.data('options').onuploadcomplete = onuploadcomplete;
        };
        
        /**
         * 是否显示图标
         */
        this.showIcon = function(isshow){
        	$finedofile.data('options').showicon = isshow;
        };

        /**
         * 是否显示原文件名称
         */
        this.showName = function(isshow){
        	$finedofile.data('options').showname = isshow;
        };

        /**
         * 是否显示文件大小
         */
        this.showSize = function(isshow){
        	$finedofile.data('options').showsize = isshow;
        };
        
        /**
         * 设置上传文件格式
         */
        this.setFilter = function(filter){
        	$finedofile.data('options').filter = filter;
        };
        
        /**
         * 设置上传时的附带信息
         */
        this.setData = function(data){
        	$finedofile.data('options').data = data;
        };

        /**
         * 设置控件标题
         */
        this.setTitle = function(title){
        	$finedofile.data('options').title = title;
        };
        
        /**
         * 设置删除后的回调函数，返回删除文件清单
         */
        this.setDeletecallback = function(deletecallback){
        	$finedofile.data('options').deletecallback = deletecallback;
        };
        
        /**
         * 设置是否支持可编辑
         */
        this.setEditable = function(editable){
        	$finedofile.data('options').editable = editable;
        	if(!finedo.fn.isTrue(editable)){
        		$('#'+$finedofile.attr('id')+'uploadbtn').hide();
        	}
        	if(!finedo.fn.isTrue($finedofile.data('options').editable) || !finedo.fn.isTrue($finedofile.data('options').multidown)){
        		$('#'+$finedofile.attr('id')+'uploadnav').hide();
        	}
        };
        
        /**
         * 设置关联id
         */
        this.setEntityid = function(entityid){
        	$finedofile.data('options').entityid = entityid;
        };
        
        /**
         * 设置控件初始化完成的回调函数
         */
        this.setOninitcomplete = function(oninitcomplete){
        	$finedofile.data('options').oninitcomplete = oninitcomplete;
        };
        
        /**
         * 设置控件的值，如果是input，则将上传控件中所有文件的ID（以逗号分开）设置为input的value
         * data值结构为[{"fileid":"FD111"},{"fileid":"FD222"}]
         */
        this.setValue = function(){
        	var controltype = $finedofile.get(0).tagName.toUpperCase();
        	if(controltype != 'INPUT')
        		return;
        	var data = $filelist;
        	var val = '';
        	for(var i = 0; i < data.length; i++){
        		if(i > 0)
        			val += ',';
        		val += data[i].fileid;
        	}
        	$finedofile.val(val);
        };
        
        /**
         * 判断是在对象内部显示控件，还是在对象后面追加显示控件
         * 如果引用的对象为div则在对象内容显示控件，如果引用的是input则在控件后面追加显示控件
         */
        this.isInner = function(){
        	var controltype = $finedofile.get(0).tagName.toUpperCase();
        	if(controltype == 'INPUT')
        		return false;
        	return true;
        };
        
        /**
         * 执行上传操作
         */
        this.upload = function(){
        	if($finedofile.uploadCheck() == false)
        		return;
        	var options = $finedofile.data('options');
        	url = options.url || $fileuploadurl;
        	if(finedo.fn.isNotNon(options.data)){
        		if(url.indexOf('?') == -1)
        			url += '?' + $.param(options.data);
        		else
        			url += '&' + $.param(options.data);
        	}
        	if(finedo.fn.isNotNon(options.entityid)){
        		if(url.indexOf('?') == -1)
        			url += '?entityid=' + options.entityid;
        		else
        			url += '&entityid=' + options.entityid;
        	}
        	$('#'+$finedofile.attr('id')+'uploadprogressbar').show();
        	var uploadControl = $('#'+$finedofile.attr('id')+'uploadfrm').ajaxSubmit({
        		success: function(retdata){
        			if(finedo.resultcode.success != retdata.resultcode){
        				finedo.message.error(retdata.resultdesc);
        				return;
        			}
        			//如果只能上传一个文件，上传成功后删除之前的文件
        			if(!finedo.fn.isTrue(options.multi)){
        				if($filelist.length > 0){
	        				var deldata =$filelist.concat();
	        				$finedofile.deleteFile(deldata, true);
        				}
        			}
        			$finedofile.showFiles([retdata.object]);
        			if($.isFunction(options.onuploadcomplete)){
                		options.onuploadcomplete(retdata.object);
                	}else if(finedo.fn.isFunction(options.onuploadcomplete)){
                		var fun = eval(options.onuploadcomplete);
                		fun(retdata.object);
                	}
        		},
        		complete: function(context, xhr, e){
        			//content.status(0:中止上传;200:上传完成;404:上传Action不存在。。。
        			$finedofile.removeData('uploadControl');
        			$('#'+$finedofile.attr('id')+'uploadprogress').css('width', '0%');
        			$('#'+$finedofile.attr('id')+'uploadprogressnum').html('0%');
        			$('#'+$finedofile.attr('id')+'uploadprogressbar').hide();
        			var uploadfileitem = $('#'+$finedofile.attr('id')+'filename');
        			var newuploadfileitem = uploadfileitem.clone();
        			newuploadfileitem.val('');
        			uploadfileitem.after(newuploadfileitem);
        			uploadfileitem.remove();
        			newuploadfileitem.bind('change', function(event){$finedofile.upload();});
        			if(context.status != '' && context.status != '0' && context.status != '200'){
        				finedo.message.error('文件上传异常，错误编码：'+context.status+"，错误描述："+context.statusText);
        			}
        		},
        		uploadProgress: function(event, position, total, percentComplete) {
        			$('#'+$finedofile.attr('id')+'uploadprogress').css('width', percentComplete+'%');
        			$('#'+$finedofile.attr('id')+'uploadprogressnum').html(percentComplete+'%');
        		},
        		url: url,
                type: 'post',
                dataType: 'json'
        	});
        	$finedofile.data('uploadControl', uploadControl);
        };
        
        /**
         * 文件上传验证，验证文件类型
         */
        this.uploadCheck = function(){
        	var filename = $('#'+$finedofile.attr('id')+'filename').val();
        	if(!filename || filename == ''){
        		finedo.message.warning('请选择要上传的文件！');
        		return false;
        	}
        	if(filename.length > 100){
        		finedo.message.warning('文件名称不能超过100个字符，请修改文件名再上传！');
        		return false;
        	}
        	var options = $finedofile.data('options');
        	if(finedo.fn.isNon(options.filter))
        		return true;
        	var fileExtIndex = filename.lastIndexOf('.');
        	if(fileExtIndex == -1){
        		finedo.message.warning('请选择正确格式的文件，如：'+options.filter);
        		return false;
        	}
        	var fileExt = filename.substr(fileExtIndex);
        	if(options.filter.indexOf(fileExt) == -1){
        		finedo.message.warning('请选择正确格式的文件，如：'+options.filter);
        		return false;
        	}
        	return true;
        };
        
        /**
         * 中止上传
         */
        this.abortUpload = function(){
        	if(!$finedofile.data('uploadControl') || !$finedofile.data('uploadControl').data('jqxhr'))
        		return;
        	$finedofile.data('uploadControl').data('jqxhr').abort();
        };
        
        /**
         * 是否支持预览
         */
        this.canPreview = function(data){
        	//目前只支持图片预览
        	return finedo.fn.isPicture(data.filetype);
        };
        
        /**
         * 显示文件清单
         * data数据结构：[{"fileid":"FD00027","filesize":"0.13","optsn":"FD00730","downloadcount":"0","filepath":"D:\workspace\java64\upload\9c14b2be.jpg","filetype":".jpg","lastdownloadtime":"","createtime":"2014-12-06","filename":"合肥移动0.jpg","creator":"ADMIN","entityid":"FSDP_FILEUPLOAD","entityname":"文件上传","remark":""}]
         */
        this.showFiles = function(data){
        	if(!data)
        		return;
        	var options = $finedofile.data('options');
        	for(var fileindex = 0; fileindex < data.length; fileindex++){
            	var fileitem = $('<div class="upload-fileitem">');
            	fileitem.attr('id', data[fileindex].fileid);
            	var filediv = $('<div class="upload-fileitem-name">');
            	var filenamediv = $('<div>');
            	
            	//显示图标
            	if(finedo.fn.isTrue(options.showicon)){
            		filenamediv.addClass('upload-icon '+(data[fileindex].filetype.length > 0 ? data[fileindex].filetype.substr(1) : data[fileindex].filetype));
            	}else{
            		filenamediv.addClass('upload-noicon');
            	}
            	//显示原名
            	if(finedo.fn.isTrue(options.showname)){
            		filenamediv.append(data[fileindex].filename);
            	}else{
            		filenamediv.append(data[fileindex].fileid+data[fileindex].filetype);
            	}
            	//显示大小
            	if(finedo.fn.isTrue(options.showsize)){
            		filenamediv.append('('+data[fileindex].hfilesize+')');
            	}
            	if($.isFunction(options.onclick)){
            		filenamediv.click(data[fileindex], function(event){options.onclick(event.data);});
            	}else if(finedo.fn.isFunction(options.onclick)){
            		var fun = eval(options.onclick);
            		filenamediv.click(data[fileindex], function(event){fun(event.data);});
            	}
            	filediv.append(filenamediv);
            	fileitem.append(filediv);
            	
            	//预览按钮
            	var fileopdiv = $('<div class="upload-fileitem-name">');
            	
            	if($finedofile.canPreview(data[fileindex])){
	            	var previewitem = $('<div class="upload-op-icon">预览</div>');
	            	fileopdiv.append(previewitem);
	            	fileopdiv.append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
	            	previewitem.click(data[fileindex], function(event){$finedofile.preview(event.data);});
            	}
            	
            	var downloaditem = $('<div class="upload-op-icon upload-op-download">下载</div>');
            	fileopdiv.append(downloaditem);
            	if(finedo.fn.isTrue($finedofile.data('options').editable)){
                	var deleteitem = $('<div class="upload-op-icon upload-op-del">删除</div>');
                	fileopdiv.append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
                	fileopdiv.append(deleteitem);
        			deleteitem.bind('click', data[fileindex], function(event){
        				$finedofile.deleteFile([event.data]);
        			});
            	}
            	fileitem.append(fileopdiv);
            	
            	downloaditem.bind('click', data[fileindex], function(event){
    				$finedofile.download([event.data]);
    			});
            	
            	fileitem.data('datarows', data[fileindex]);
            	$('#'+$finedofile.attr('id')+'filelist').append(fileitem);
            	$filelist.push(data[fileindex]);
        	}
        	$finedofile.setValue();
        };

        /**
         * 清空文件列表
         */
        this.clear = function(){
        	$('#'+$finedofile.attr('id')+'filelist').html('');
        	$filelist = [];
        	$finedofile.setValue();
        };
        
        /**
         * 移动文件清单
         * data数据结构：[{"fileid":"FD00027"}, {"fileid":"FD00028"}]
         */
        this.removeFiles = function(data){
        	for(var i = 0; i < data.length; i++){
        		$('#'+data[i].fileid).remove();
        		for(var j = 0; j < $filelist.length; j++){
        			if(data[i].fileid == $filelist[j].fileid){
        				$filelist.splice(j,1);
        				break;
        			}
        		}
        	}
        	$finedofile.setValue();
        };
        
        /**
         * 获取控件中所有文件对象数组
         * 返回数据格式为：[{"fileid":"FD00027"}, {"fileid":"FD00028"}]
         */
        this.getFileList = function(){
        	/*var filelist = [];
        	$(".upload-fileitem").each(function () {
        		filelist.push($(this).data('datarows'));
        	});*/
        	return $filelist;
        };
        
        /**
         * 检查数据格式
         */
        this.checkFileList = function(data){
        	if(!data || !data.length){
        		//finedo.message.warning('非文件对象数组，如：[{"fileid":"FD0001"},{"fileid":"FD0002"}]');
        		return false;
        	}
        	if(data.length == 0){
        		//finedo.message.warning('文件数组为空！');
        		return false;
        	}
        	for(var i = 0; i < data.length; i++){
	        	if(!data[i].fileid){
	        		finedo.message.warning('文件对象中无fileid属性，请确认格式是否正确！');
	        		return false;
	        	}
        	}
        	return true;
        };
        
        /**
         * 根据文件ID列表加载文件信息
         * 格式为：[{"fileid":"FD000000000000000505"},{"fileid":"FD0002"}]
         */
        this.load = function(data){
        	if(!data && !data.length)
        		return;
        	finedo.action.doJsonRequest($fileloadurl, {'filelist':data}, function(retdata){
        		if(finedo.resultcode.success != retdata.resultcode){
    				finedo.message.error(retdata.resultdesc);
    				return;
    			}
    			
    			$finedofile.showFiles(retdata.object.filelist);
    			if(retdata.object.filelist.length == 0){
    				if(!finedo.fn.isTrue($finedofile.data('options').editable)){
    					$('#'+$finedofile.attr('id')+'uploadnav').hide();
    				}
    			}
    			if($.isFunction($finedofile.data('options').oninitcomplete)){
    				$finedofile.data('options').oninitcomplete(retdata.object.filelist);
    				$finedofile.setOninitcomplete(undefined);
            	}else if(finedo.fn.isFunction($finedofile.data('options').oninitcomplete)){
            		var fun = eval($finedofile.data('options').oninitcomplete);
            		fun(retdata.object.filelist);
    				$finedofile.setOninitcomplete(undefined);
            	}
        	});
        };

        /**
         * 根据文件ID列表删除文件信息，如果不需要询问是否删除，则直接删除同时不调用删除回调函数
         * 格式为：[{"fileid":"FD0001"},{"fileid":"FD0002"}]
         */
        this.deleteFile = function(data, noaskdelete){
        	if(!$finedofile.checkFileList(data))
        		return;
        	var options = $finedofile.data('options');
        	var filelist = '';
        	for(var i = 0; i < data.length; i++){
        		if(i > 0)
        			filelist += ',';
        		if(finedo.fn.isTrue(options.showname)){
        			filelist += data[i].filename;
            	}else{
            		filelist += data[i].fileid+data[i].filetype;
            	}
        	}
        	if(noaskdelete){
        		finedo.action.doJsonRequest($filedeleteurl, {"filelist":data}, function(retdata){
            		if(finedo.resultcode.success != retdata.resultcode){
        				finedo.message.error(retdata.resultdesc);
        				return;
        			}
        			
        			$finedofile.removeFiles(data);
        		});
        		return;
        	}
        	finedo.message.question('是否删文件【'+filelist+'】？', null, function(which){
        		if(which == false)
        			return;
            	finedo.action.doJsonRequest($filedeleteurl, {"filelist":data}, function(retdata){
            		if(finedo.resultcode.success != retdata.resultcode){
        				finedo.message.error(retdata.resultdesc);
        				return;
        			}
        			
        			$finedofile.removeFiles(data);
        			if($.isFunction(options.deletecallback)){
                		options.deletecallback(data);
                	}else if(finedo.fn.isFunction(options.deletecallback)){
                		var fun = eval(options.deletecallback);
                		fun(data);
                	}
            	});
        	});
        };
        
        /**
         * 删除所有文件，不需要传参，自动从页面中获取已加载的所有文件
         */
        this.deleteAll = function(){
        	var data = $finedofile.getFileList();
        	$finedofile.deleteFile(data);
        };
        
        /**
         * 文件预览
         */
        this.preview = function(data){
        	var downurl = $filedownloadurl.indexOf('?') == -1 ? $filedownloadurl+'?fileid='+data.fileid : $filedownloadurl+'&fileid='+data.fileid;
        	finedo.dialog.previewPicture(downurl);
        };
        
        /**
         * 文件下载
         */
        this.download = function(data){
        	if(finedo.fn.isNon(data))
        		return;
        	if(!$finedofile.checkFileList(data))
        		return;
        	var fileid = '', token = '';
        	for(var i = 0; i < data.length; i++){
        		if(i > 0)
        			fileid +=',';
        		fileid += data[i].fileid;
        		if(token != '' && data[i].remark)
        			token = data[i].remark;
        	}
        	var downurl = $filedownloadurl;
        	var key = 'fileid';
        	if(data.length > 1){
        		downurl = $filedownloadmultiurl;
        		key = 'fileids';
        	}
        	downurl = downurl.indexOf('?') == -1 ? downurl+'?'+key+'='+fileid+'&token='+token : downurl+'&'+key+'='+fileid+'&token='+token;
        	$('#'+$finedofile.attr('id')+'downframe').attr('src', downurl);
        };
        
        /**
         * 下载所有
         */
        this.downloadAll = function(){
        	var data = $finedofile.getFileList();
        	$finedofile.download(data);
        };

        /**
         * 显示模式：list(列表)、view(视图)
         */
        this.viewModel = function(modelType){
        	
        };
        
        /**
         * 控件初始化
         */
        this.init = function(){
        	if($finedofile.data('init') == true)
        		return $finedofile;
        	$finedofile.data('init', true);
        	var defaults = {
        		showicon : true, //是否显示图标
        		showname : true, //是否显示原始名称
        		showsize : true, //是否显示文件大小
        		multi: true,
        		multidown: false,
        		editable: true
            };
        	var attroptions = {};
        	$.each($(this)[0].attributes, function(index, attr) {
        		attroptions[attr.name] = attr.value;
			});
        	var options = $.extend(attroptions,$finedofile.data('options'));
        	options = $.extend(defaults,options);
        	$finedofile.data('options', options);
        	var isinner = $finedofile.isInner();
        	if(isinner){
        		$finedofile.html('');
        	}else{
        		$($finedofile).attr('type', 'hidden');
        		$finedofile.val('');
        		$finedofile.after($('<div id="'+$finedofile.attr('id')+'finedofilecontrol">'));
        	}
        	var uploadcontent = $('<div class="upload-filelist-content">');
        	if(finedo.fn.isNotNon($finedofile.data('options').width))
        		$(uploadcontent).width($finedofile.data('options').width);
        	if(finedo.fn.isNotNon($finedofile.data('options').height))
        		$(uploadcontent).height(height);
        	//初始化控件头部
        	if(finedo.fn.isTrue($finedofile.data('options').editable) || finedo.fn.isTrue($finedofile.data('options').multidown)){
	        	var nav = $('<div class="upload-nav" id="'+$finedofile.attr('id')+'uploadnav">');
	        	var navleft = $('<div class="upload-nav-left">');
	        	if(finedo.fn.isTrue($finedofile.data('options').editable)){
		        	var uploadbtn = $('<input type="button" class="upload-btn" id="'+$finedofile.attr('id')+'uploadbtn" value="上传文件">');
		        	uploadbtn.bind('click', function(event){
		            	/*if(!finedo.fn.isTrue(options.multi)){
		            		if($finedofile.getFileList().length >= 1){
		            			finedo.message.warning('只能上传一个文件，请删除已有文件后再上传！');
		                		return false;
		            		}
		            	}*/
		        		$('#'+$finedofile.attr('id')+'filename').trigger('click');
		        	});
		        	navleft.append(uploadbtn);
	        	}
	        	if(finedo.fn.isTrue($finedofile.data('options').multidown)){
	            	var downloadbtn = $('<input type="button" class="upload-downbtn-all" value="全部下载">');
	            	downloadbtn.bind('click', function(event){$finedofile.downloadAll();});
	            	navleft.append(downloadbtn);
	        	}
	        	nav.append(navleft);
	        	
	        	var navright = $('<div class="upload-nav-rig">');
	        	var listdivbtn = $('<div class="upload-nav-switch" title="列表预览">');
	        	var viewdivbtn = $('<div class="upload-nav-switch upload-nav-view" title="缩略图预览">');
	        	listdivbtn.bind('click', function(event){$finedofile.viewModel('list');});
	        	viewdivbtn.bind('click', function(event){$finedofile.viewModel('view');});
	        	navright.append(listdivbtn);
	        	navright.append(viewdivbtn);
	        	nav.append(navright);
	        	
	        	uploadcontent.append(nav);
        	}
        	
        	uploadcontent.append($('<div id="'+$finedofile.attr('id')+'filelist">'));
        	var form = $('<form name="'+$finedofile.attr('id')+'uploadfrm" id="'+$finedofile.attr('id')+'uploadfrm" method="post" enctype="multipart/form-data">');
			var uploaddiv = $('<div class="upload-control" id="'+$finedofile.attr('id')+'uploadprogressbar" style="display:none;">');
			uploaddiv.append($('<div>'+
					'<div class="upload-progress-load upload-progress-display">'+
					'<div class="upload-progress-color" id="'+$finedofile.attr('id')+'uploadprogress"></div></div>'+
					'<label class="upload-disabled" id="'+$finedofile.attr('id')+'uploadprogressnum">0%</label></div>'));
			
			uploadcontent.append(uploaddiv);
			if(isinner){
				$finedofile.append(uploadcontent);
        	}else{
        		$('#'+$finedofile.attr('id')+'finedofilecontrol').append(uploadcontent);
        	}
			
			form.append($('<iframe name="'+$finedofile.attr('id')+'downframe" id="'+$finedofile.attr('id')+'downframe" style="display:none;"></iframe>'));
			//form添加到body最后，防止form相嵌
			form.append($('<input type="file" name="'+$finedofile.attr('id')+'filename" id="'+$finedofile.attr('id')+'filename" style="opacity: 0;">'));
			$('body').append(form);
			$('#'+$finedofile.attr('id')+'filename').bind('change', function(event){$finedofile.upload();});
			//判断是否设置value值，有值加载文件列表
			var fileids = $finedofile.data('options').value;
			var fileidlist = [];
			if(finedo.fn.isNotNon(fileids)){
				var fileidAry = fileids.split(",");
				for(var i = 0; i < fileidAry.length; i++){
					if(finedo.fn.isNon(fileidAry[i]))
						continue;
					fileidlist.push({"fileid":fileidAry[i]});
				}
			}
			var entityid = $finedofile.data('options').entityid;
			if(finedo.fn.isNotNon(entityid)){
				var relidAry = entityid.split(",");
				for(var i = 0; i < relidAry.length; i++){
					if(finedo.fn.isNon(relidAry[i]))
						continue;
					fileidlist.push({"entityid":relidAry[i]});
				}
			}
			
			if(fileidlist.length > 0)
				$finedofile.load(fileidlist);
			else{
				if(!finedo.fn.isTrue($finedofile.data('options').editable)){
					$('#'+$finedofile.attr('id')+'uploadnav').hide();
				}
			}
			return $finedofile;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义控件获取函数
 */ 
finedo.getFileControl = function(controlid, options){
	var fileControl = $('#'+controlid).file(options);
	return fileControl;
};




