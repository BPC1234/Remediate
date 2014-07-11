<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/compliance.js"></script>
<title><spring:message code="mainTab.training.and.certification.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="labelOneUser" value="<%=Role.EMPLOYEE.getLabel()%>"/>

<div id="policyListPage" class="row-fluid">
    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="span12 zeroMarging">
    <div class="containerHeadline">
        <i class="icon-th"></i><h2><spring:message code="training.and.certification"/></h2>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">

        <div class="container-fluid">

            <div id="transactionSearchBlockId" class="centerAlign">
                <div id="trainingSurveyDiv" class="pieChartWrapperDiv"  >
                    <div class="trainingPieChart">
                        <table>
                            <tr>
                                <td align="right"><spring:message code="policy.name"/> :</td>
                                <td align="left">sumon<%--<label class="policyName"></label>--%></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="training.type"/> :</td>
                                <td align="left">habib<%--<label class="policyType"></label>--%></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="document.uploaded"/> :</td>
                                <td align="left"><%--<label class="uploadedBy"></label>--%></td>
                            </tr>
                            <tr>
                                <td align="right"><spring:message code="document.uploaded.date"/> Date :</td>
                                <td align="left"><%--<label class="uploadedDate"></label>--%></td>
                            </tr>
                        </table>
                    </div>
                    <div class="demoPieChart" id="trainingPieChart" style="float: left;"></div>
                    <div style="clear: both;"></div>
                </div>
                <%--<div class="span12 leftMarginZero">--%>
            <div class="span12 leftMarginZero">


                <!-- ==================== SPAN12 HEADLINE ==================== -->
                <div class="containerHeadline">
                    <i class="icon-th"></i><h2><spring:message code="training"/></h2>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                </div>
                <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
                <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                <div class="floatingBox">
                    <div class="container-fluid customflexigridcss">
                        <table id="trainingReview">
                        </table>
                    </div>
                </div>
                <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

            </div>
        </div>
    </div>

</div>
</div>
</div>
<script>
    var user =${user};
</script>