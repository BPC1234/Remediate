<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/training-and-certification.js"></script>
<title><spring:message code="mainTab.training.and.certification.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<div id="policyListPage" class="row-fluid">
    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i>

        <h2><spring:message code="training.and.certification"/></h2>

        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">
        <div class="container-fluid">

            <div id="trainingtestDiv" class="centerAlign">
                <div class="span6">
                    <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-ok-sign"></i>

                        <h2><spring:message code="question.set.level"/></h2>

                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                    <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid">
                            <spring:message code="training.retake.message"/>
                        </div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                </div>
            </div>


        </div>
    </div>

</div>
<script>
    /*submitAnswer();*/
    var user =${user};
</script>
