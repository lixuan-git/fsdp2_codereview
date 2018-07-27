finedo.login = {
	developerLogin:function(){
		var url = ctx+"/finedo/auth/developerlogin";
		$.post(url, null,  finedo.login.developerlogincallback, "json");
		
	},
		
	developerlogincallback:function(jsondata){
		if(jsondata.resultcode == "SUCCESS"){
			window.location.href = ctx+"/finedo/auth/index";
		}
	}
};

$(document).ready(function() {
	finedo.login.developerLogin();
});