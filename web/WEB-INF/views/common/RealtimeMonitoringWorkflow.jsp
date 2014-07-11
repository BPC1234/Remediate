<%@page import="com.dsinv.abac.util.Utils" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    final String contextPath = request.getContextPath();

%>
<script src="<%=contextPath%>/resources/js/real-time-monitoring-workflow.js"></script>


<title><spring:message code="realtime.monitoring.workflow.title"/></title>
<input type="hidden" id="loggedUser" value="${loggedUser}"/>
<div id="realtimeSelectedWeekInfo">
    <%@ include file="/WEB-INF/views/message.jsp" %>
    <div class="row-fluid">
        <div class="span12">
            <!-- ==================== SPAN12 HEADLINE ==================== -->
            <div class="containerHeadline">
                <i class="icon-th"></i>

                <h2><spring:message code="subTabId.revAssignment"/></h2>
                <%-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>--%>
            </div>
            <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

            <!-- ==================== SPAN12 FLOATING BOX ==================== -->
            <div class="floatingBox">
                <div class="container-fluid">
                    <div id="realtimeworkflowtableDiv" class="customFlexigridCss">
                        <table id="workFlowTable" class="tableRealTimeProject realTimeProjectWorkFlix">
                        </table>
                    </div>
                </div>
            </div>
           <%--================ END OF SPAN12 FLOATING BOX ==================== -->--%>
            <div id="analystList">
                <option value="null">select analyst</option>
                    <c:forEach items="${iaAnalystList}" var="list" varStatus="loop">
                        <option value="<c:out value="${list. user_name}"/>"><c:out value="${list. user_name}"/></option>
                    </c:forEach>
            </div>
        </div>




    </div>
</div>

<script>
    var user =${user};

</script>

