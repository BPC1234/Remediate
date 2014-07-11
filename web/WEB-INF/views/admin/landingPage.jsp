<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ page import="com.dsinv.abac.entity.PolicyType" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%
    final String contextPath = request.getContextPath();
%>

<title><spring:message code="landingPage.transactionMonitoring" /></title>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="employee" value="<%=Role.EMPLOYEE.getLabel()%>"/>
<c:set var="legal_role" value="<%=Role.LEGAL.getLabel()%>"/>
<c:set var="compliance_role" value="<%=Role.COMPLIANCE.getLabel()%>"/>
<c:set var="ia_manager_role" value="<%=Role.IA_MANAGER.getLabel()%>"/>
 <div id="landingCss">
<div class="row-fluid dashBoardUlDiv">

    <!-- ==================== MASTER ACTIONS LIST ==================== -->
    <ul class="masterActions customAction">
        <security:authorize ifAnyGranted="${adminUser}">
            <li id="trxMoniDash">
                <i class="icon-ok-sign cyanText" style="margin-left: 0px;"></i>
                <h1 class="cyanText">TOTAL ASSIGN.: ${totalProject}</h1>
                <p><spring:message code="completed"/> : ${completeAssignment}</p>

                    <%--<p><spring:message code="transaction.monitoring.review.unassign"/> : ${unAssignedAssignment}</p>--%>
                <p><spring:message code="assigned"/> : ${inProgressAssignment}</p>
                <p>&nbsp;&nbsp; </p>
                    <%--<div class="notifyCircle cyan trxMoniDashTolTip">${unAssignedAssignment}</div>--%>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="${adminUser}">
            <li id="trxSearchDash">
                <i class="icon-edit greenSeaText" style="margin-left: 0px;"></i>
                <h1 class="greenText">News</h1>

                <p>&nbsp;&nbsp; </p>
                <p>&nbsp;&nbsp; </p>
                <p>&nbsp;&nbsp; </p>
                    <%--<div class="notifyCircle green">254</div>--%>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="${adminUser},${compliance_role},${ia_manager_role}">
            <li id="abacPolicy">
                <i class="icon-briefcase maroon" style="margin-left: 0px;"></i>
                <h1 class="maroon">Reports</h1>

                <p>&nbsp;&nbsp; </p>
                <p>&nbsp;&nbsp; </p>
                <p>&nbsp;&nbsp; </p>
                    <%--<div class="notifyCircle green">254</div>--%>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="${compliance_role},${ia_manager_role},${legal_role}">
            <li id="trainingDash" class="active">
                <i class="icon-laptop greenText" style="margin-left: 0px;"></i>
                <h1 class="redText">Training</h1>
                <p>TO BE START : ${totalCourseToBeStart} </p>
                <p>TO BE RETAKE : ${totalCourseToBeRetake} </p>
                <p>CERTIFICATE : ${totalCoursePassed}</p>
            </li>
        </security:authorize>

        <security:authorize ifAnyGranted="${legal_role}, ${compliance_role}, ${ia_manager_role}">
            <li id="policyDash" class="active">
                <i class="icon-inbox greenSeaText" style="margin-left: 0px;"></i>
                <h1 class="redText">Policies</h1>
                <p>CONFIRMED : ${totalConfirmedPolicy}</p>
                <p>NOT CONFIRMED : ${totalUnConfirmedPolicy}</p>
                <p>NOT REVIEWED : ${totalNotReviewedPolicy}</p>
            </li>
        </security:authorize>

    </ul>
    <!-- ==================== END OF MASTER ACTIONS LIST ==================== -->

</div>

<div  class="bpcCss" >
    <img src="<%= contextPath %>/resources/images/newbpc.png" />
</div>
</div>
