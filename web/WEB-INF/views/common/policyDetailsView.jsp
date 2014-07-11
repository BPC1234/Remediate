<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/policy.js"></script>

<title><spring:message code="policyDetails.header"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>
<input type="hidden" id="policyType" value="${policyType}">
<div id="holidayMainDiv" class="row-fluid">
    <div class="span12">
        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2></h2>
            <%-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>--%>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="customFlexigridCss">
                    <table id="policyDetailsTable">
                    </table>
                </div>
            </div>
        </div>

        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->
    </div>
</div>