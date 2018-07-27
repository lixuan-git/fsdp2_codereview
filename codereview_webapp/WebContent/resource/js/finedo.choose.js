finedo.choose = {
	/**
	 * 组织机构单选
	 */
	singleOrg:function(ccallback){
		finedo.dialog.show({'selecttype':'single',
			width:300,
			'title':'双击选择',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/org/choose.jsp?selecttype=single',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}
	
	/**
	 * 组织机构多选
	 */
	,multiOrg:function(ccallback){
		finedo.dialog.show({'selecttype':'multi',
			width:300,
			'title':'组织机构选择',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/org/choose.jsp?selecttype=multi',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}
	
	/**
	 * 单选角色
	 */
	,singleRole:function(ccallback){
		finedo.dialog.show({'selecttype':'single',
			width:1000,height:600,
			'title':'选择岗位角色（双击表格行）',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/role/choose.jsp?selecttype=single',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}
	
	/**
	 * 多选角色
	 */
	,multiRole:function(ccallback){
		finedo.dialog.show({'selecttype':'multi',
			width:1000,height:600,
			'title':'选择角色',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/role/choose.jsp?selecttype=multi',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}

	/**
	 * 单选用户
	 */
	,singleUser:function(ccallback){
		finedo.dialog.show({'selecttype':'single',
			width:900,height:600,
			'title':'选择用户（双击表格行）',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/user/choose.jsp?selecttype=single',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}

	/**
	 * 多选用户
	 */
	,multiUser:function(ccallback){
		finedo.dialog.show({'selecttype':'multi',
			width:1000,height:600,
			'title':'选择用户',
			'loadtype':'iframe',
			'url':ctx+'/fsdp/user/choose.jsp?selecttype=multi',
			callback:function(data){
				if($.isFunction(ccallback))
					ccallback(data);
			}
		});
	}

};
