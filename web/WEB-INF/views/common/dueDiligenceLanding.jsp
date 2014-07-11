<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% final String contextPath = request.getContextPath(); %>
<script src="<%= contextPath %>/resources/js/due-dilligance-landing.js"></script>

<div id="replaceAjaxDiv">
<h3><strong><spring:message code="dueDilligance.landingPage.form.header" /></strong></h3>
<br/>
<div id="reputationalAccordion">
<h3 class="accordionHeader"><a href="#"><strong><spring:message code="dueDilligance.landingPage.header.ReputationalReview" /></strong></a></h3>
<div style="display:none">
<div align="center">
<br/>
 <div id="rectangleDiv">
	<strong><a class="hrefLinkColor" href="./newReputationalForm.html" ><spring:message code="dueDilligance.new.reputational.review" /></a></strong>
 </div>  
  <p/>
 <div id="rectangleDiv">
	<strong><a class="hrefLinkColor" href="./existingVendorsForReputaionalReview.html"><spring:message code="dueDilligance.existing.reputational.review" /></a></strong> 
 </div>
</div>    
</div>
</div>

<div id="financialAccordion">
<h3 class="accordionHeader"><a href="#"><strong><spring:message code="dueDilligance.landingPage.header.financialReview" /></strong></a></h3>
<div style="display:none">
<div align="center">
<br/>
<%-- <div id="rectangleDiv">
	<strong><a class="hrefLinkColor" href="./newFinancialProject.html"><spring:message code="dueDilligance.newFinancialReviewForm" /></a></strong>
 </div>  
  <p/>--%>
 <div id="rectangleDiv">
	<strong><a class="hrefLinkColor" href="./existingVendorsForFinancialReview.html"><spring:message code="dueDilligance.landingPage.header.financialReview" /></a></strong>
 </div>
   
</div> 
</div>
</div>
</div>