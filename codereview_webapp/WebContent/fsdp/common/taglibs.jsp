<%@ page trimDirectiveWhitespaces="true" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tlds/fsdp.tld" prefix="fsdp" %>

<%-- Webåºç¨Contextè·¯å¾ --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="version" value="20150215001" />

<%-- å¨å±CSSæ ·å¼ --%>
<c:set var="style_css" value="<link href='${ctx }/fsdp/resource/themes/easyui/style.css?version=${version }' rel='stylesheet' type='text/css' />"/>

<%-- JQueryåºç¡åº --%>
<c:set var="jquery_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/jquery/jquery-1.11.0.js?version=${version }'></script><script src='${ctx}/fsdp/resource/js/jquery/jquery.cookie.js?version=${version }' type='text/javascript'></script><script src='${ctx}/fsdp/resource/js/jquery/jquery.form.js?version=${version }' type='text/javascript'></script>"  />

<%-- æ¥æéæ©å¨åº  --%>
<c:set var="datepicker_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/datepicker/WdatePicker.js?version=${version }'></script>"  />

<%-- é¢è²éæ©å¨åº --%>
<c:set var="colorpicker_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/colorpicker/colorPicker.js?version=${version }'></script>"  />

<%-- å¯ææ¬ç¼è¾å¨åº --%>
<c:set var="ueditor_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/ueditor/ueditor.config.js?version=${version }'></script><script type='text/javascript' src='${ctx}/fsdp/resource/js/ueditor/ueditor.all.min.js?version=${version }'></script><script type='text/javascript' src='${ctx}/fsdp/resource/js/ueditor/lang/zh-cn/zh-cn.js?version=${version }'></script>"  />

<%-- highchartsæ¥è¡¨åº --%>
<c:set var="highcharts_js" value="<script src='${ctx}/fsdp/resource/js/highcharts/highcharts.js?version=${version }' type='text/javascript'></script>"  />
<c:set var="highcharts_more_js" value="<script src='${ctx}/fsdp/resource/js/highcharts/highcharts-more.js?version=${version }' type='text/javascript'></script>"  />
<c:set var="highcharts_3d_js" value="<script src='${ctx}/fsdp/resource/js/highcharts/highcharts-3d.js?version=${version }' type='text/javascript'></script>"  />

<%-- echartsæ¥è¡¨åº --%>
<c:set var="echarts_js" value="<script src='${ctx}/fsdp/resource/js/echarts/echarts.js?version=${version }' type='text/javascript'></script>"  />

<%-- easy uiåº --%>
<c:set value="ui-pepper-grinder" var="SYSTEM_SKIN"></c:set>
<c:if test="${!empty tet}">
<c:set value="${tet}" var="SYSTEM_SKIN"></c:set>
</c:if>
<c:set var="easyui_js" value="<link rel='stylesheet' href='${ctx}/fsdp/resource/js/easyui/themes/${SYSTEM_SKIN }/easyui.css?version=${version }'><link rel='stylesheet' href='${ctx}/fsdp/resource/js/easyui/themes/icon.css?version=${version }'><script src='${ctx}/fsdp/resource/js/easyui/jquery.easyui.min.js?version=${version }' type='text/javascript'></script>"  />
<c:set var="easyui_portal_js" value="<link rel='stylesheet' href='${ctx}/fsdp/resource/js/easyui-portal/portal.css?version=${version }'><script src='${ctx}/fsdp/resource/js/easyui-portal/jquery.portal.js?version=${version }' type='text/javascript'></script>"  />


<%-------------------------------------------------------------------------------%>

<%-- FSDP æ ¸å¿å¬å±JSåº --%>
<c:set var="finedo_js" value="<script type='text/javascript'>var ctx='${ctx }';</script><script type='text/javascript' src='${ctx}/fsdp/resource/js/finedo.easyui.js?version=${version }'></script>"  />

<%-- FSDP æ°æ®éªè¯JSåº --%>
<c:set var="validator_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/finedo.validator.js?version=${version }'></script>"  />

<!-- FSDP MD5加密JS库 -->
<c:set var="finedo_md5_js" value="<script type='text/javascript' src='${ctx}/fsdp/resource/js/finedo.md5.js?version=${version }'></script>"  />

