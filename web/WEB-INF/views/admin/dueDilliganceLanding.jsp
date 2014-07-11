<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="dueDilligance.landingPage.title" /></title>

</head>
<script src="<%= contextPath %>/resources/js/due-dilligance-landing-page.js"></script>
<body>
<div id="dueDilliganceLandingPageTitle">
<b><strong><spring:message code="dueDilligance.landingPage.form.header" /></strong></b>
</div>
<div id="dueDilliganceLandingPageDiv">

<div id="leftTab">
<ul class="tabrow">
	    <li id="reputationalTab" class="selected"><a href="#"><spring:message code="dueDilligance.landingPage.reputational" /></a></li>
	    <li id="financialTab" ><a href="#"><spring:message code="dueDilligance.landingPage.financial" /></a></li>
	</ul>
</div>
<div id="rightTab">
<ul class="tabrowRight">
	    <li id="newTab" class="selected"><a href="#"><spring:message code="dueDilligance.landingPage.new" /></a></li>
	    <li id="existingTab"><a href="#"><spring:message code="dueDilligance.landingPage.existing" /></a></li>
	</ul>
</div>
	

</div>

<div class="dueDiligenceDiv">
<img id="loading" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display:none;"/>
<img id="upLoading" src="<%=contextPath%>/resources/images/uploading2.gif" style="display:none;"/>

<div id="replaceAjaxDiv">
<br>

 </div>
</div>
<script defer="defer">

    var reputationalVal =  ${isReputational}; 
    var financialVal =  ${isFinancial}; 
    var existingVal =  ${isExisting}; 
    var newForm =  ${isNew}; 
    
    
</script>
</body>
</html>