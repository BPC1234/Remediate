<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/icga-workflow.js"></script>
<title><spring:message code="landingPage.existingControls"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>


<div class="row-fluid">
    <img id="loading" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display:none;"/>
    <div class="span12" style="margin-left: 0px;">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div id="projectWiseControlTableDiv">
         <input type="hidden" value="${controlId}" id="controlId">
         <p id ="ctrlName" class="">&nbsp;<i class="icon-tasks lightRed"></i>&nbsp; ${controlName}</p>
        <div class="floatingBox">
            <div class="container-fluid customFlexigridCss">
                <table id="projectWiseControlTable">
                </table>
            </div>
        </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>
</div>

