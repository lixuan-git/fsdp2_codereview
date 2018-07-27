<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0">
<title>ssh://192.168.1.128</title>

</head>
<body>

<!-- Include gateone.js somewhere on your page -->
<script src="http://192.168.1.128:8000/static/gateone.js"></script>

<!-- Decide where you want to put Gate One -->
<div id="gateone_container" style="position: relative; width: 70em; height: 40em;">
    <div id="gateone"></div>
</div>

<!-- Call GateOne.init() at some point after the page is done loading -->
<script>
window.onload = function() {
	// Initialize Gate One:
   	var authobj = {
	    'api_key':			'${gateoneauth.api_key}',
	    'upn':				'${gateoneauth.upn}',
	    'timestamp':		'${gateoneauth.timestamp}',
	    'signature':		'${gateoneauth.signature}',
	    'signature_method': '${gateoneauth.signature_method}',
	    'api_version':		'${gateoneauth.api_version}'
	}

    GateOne.init({url: 'http://192.168.1.128:8000/', theme: 'white', showTitle: true, showToolbar: false, audibleBell: false, auth: authobj});
}
</script>
</body>
</html>

