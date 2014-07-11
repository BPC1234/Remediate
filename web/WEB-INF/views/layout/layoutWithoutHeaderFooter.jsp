<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ page import="com.dsinv.abac.util.Utils"%>

<%
	final String contextPath = request.getContextPath();
/*	pageContext.setAttribute("amlMessage", Utils.getMessage(request));
	pageContext.setAttribute("amlInlineMessage", Utils.getInlineMessage(request));
	pageContext.setAttribute("amlConfirmationMessage", Utils.getConfirmationMessage(request));
	pageContext.setAttribute("amlConfirmationForwardUrl", Utils.getConfirmationForwardUrl(request));
	pageContext.setAttribute("amlConfirmationCancelUrl", Utils.getConfirmationCancelUrl(request));*/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
						"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%-- <title><tiles:insertAttribute name="title" ignore="true"/></title> --%>
	<link type="text/css" href="<%= contextPath %>/resources/ext4/resources/css/ext-all.css" rel="stylesheet" />
	<link type="text/css" href="<%= contextPath %>/resources/styles/base.css" rel="stylesheet" />   
	<link type="text/css" href="<%= contextPath %>/resources/styles/cupertino/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="<%= contextPath %>/resources/ext4/ext-all.js"></script>
	<script type="text/javascript" src="<%= contextPath %>/resources/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%= contextPath %>/resources/js/jquery-ui-1.8.18.custom.min.js"></script>
	
	<%-- <link rel="stylesheet" href="http://static.jquery.com/ui/css/demo-docs-theme/ui.theme.css" type="text/css" media="all">--%>
	<%-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script> --%>
	<%-- <script src="http://code.jquery.com/ui/1.8.22/jquery-ui.min.js" type="text/javascript"></script> --%>

	<script type="text/javascript" src="<%= contextPath %>/resources/js/jquery.media.js"></script>
	<script type="text/javascript" src="<%= contextPath %>/resources/js/ctr-common.js"></script>

</head>
<body onload="passMaxInactiveTimeForIFrame(${pageContext.session.maxInactiveInterval})">
	<input id="contextPath" type="hidden" value="<%= contextPath %>"/>
	
	<c:if test="${amlInlineMessage != ''}">
		<div id="inlineMsgDiv">${amlInlineMessage}</div>
	</c:if>
    <tiles:insertAttribute name="body" />
    
	<c:if test="${amlMessage != ''}">
		<input id="amlMessage" type="hidden" value="${amlMessage}"/>
	</c:if>
	<c:if test="${amlConfirmationMessage != ''}">
		<input id="amlConfirmationMessage" type="hidden" value="${amlConfirmationMessage}"/>
		<input id="amlConfirmationForwardUrl" type="hidden" value="${amlConfirmationForwardUrl}"/>
		<input id="amlConfirmationCancelUrl" type="hidden" value="${amlConfirmationCancelUrl}"/>
	</c:if>
    
    
</body>
</html>
