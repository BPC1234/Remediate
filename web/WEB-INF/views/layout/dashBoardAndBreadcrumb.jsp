<%@ page import="java.security.Principal" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>

<!-- ==================== TITLE LINE FOR HEADLINE ==================== -->

<!-- ==================== END OF TITLE LINE ==================== -->

<!-- ==================== BREADCRUMBS / DATETIME ==================== -->
<ul class="breadcrumb">
    <li> <i class="icon-home"></i>
    <c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
        <%--<c:if test="${status.index==0}"><a href="${bc.value}">${bc.name}</a></c:if>--%>
        <%--<c:if test="${status.index>0}"><li class="active">${bc.name}</c:if>--%>
        <li class="active"><a href="${bc.value}">${bc.name}</a></li>
        <c:if test="${status.index < fn:length(breadcrumb.tree)-1}">
            <span class="divider"><i class="icon-angle-right"></i></span>
        </c:if>
        </li>
    </c:forEach>
    <li class="moveDown pull-right">
               <div class="buttonGroup pull-right"><button type="button" id="cancelButtonForTxDetails" class="btn btn-mini btn-info subHeaderButton"><i class="icon-remove"></i><spring:message code="reactiveProject.button.cancel"/></button></div>
               <div class="buttonGroup pull-right"><button type="button" id="subHeaderEmailButton" class="btn btn-mini btn-primary subHeaderButton"><i class="icon-mail-forward"></i><spring:message code="newRiskAssessmentSummary.email"/></button></div>
               <div class="buttonGroup pull-right"><button type="button" id="subHeaderPrintButton" class="btn btn-mini btn-warning subHeaderButton"><i class="icon-print"></i><spring:message code="newRiskAssessmentSummary.print"/></button></div>
               <div class="buttonGroup pull-right"><button type="button" id="subHeaderSaveButton" class="btn btn-mini btn-success subHeaderButton"><i class="icon-ok"></i><spring:message code="save.button.title"/></button></div>
           <%--</div>--%>
    </li>
</ul>
<script>
    var currentUrl = location.href;
    $(document).ready(function () {
        $("#cancelButtonForTxDetails").click(function(){
        if(currentUrl.indexOf("myRealTimeSummaryView.html") >= 0){
            window.location = "../realtime/myTransactions.html";
        }else if(currentUrl.indexOf("realTimeSummaryView.html") >= 0 && currentUrl.indexOf("controlIds") >= 0){
            window.location = "../icga/existingCtrlGapAnalysis.html?controlIds=0";
        }else if(currentUrl.indexOf("realTimeSummaryView.html") >= 0){
            window.location = "../realtime/RealtimeMonitoringWorkflow.html";
        }
        });
    });
</script>
<!-- ==================== END OF BREADCRUMBS / DATETIME ==================== -->
