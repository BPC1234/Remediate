<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<% final String contextPath = request.getContextPath(); %>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="employee" value="<%=Role.EMPLOYEE.getLabel()%>"/>
<c:set var="legal" value="<%=Role.LEGAL.getLabel()%>"/>
<c:set var="ia_analyst" value="<%=Role.IA_ANALYST.getLabel()%>"/>
<c:set var="ia_manager" value="<%=Role.IA_MANAGER.getLabel()%>"/>
<c:set var="compliance" value="<%=Role.COMPLIANCE.getLabel()%>"/>

<script type="text/javascript">
    $(function() {
        /* For zebra striping */
        $("control table tr:nth-child(odd)").addClass("odd-row");
        /* For cell text alignment */
        $("control table td:first-child, table th:first-child").addClass("first");
        /* For removing the last border */
        $("control table td:last-child, table th:last-child").addClass("last");

    });
/*    <c:set var="cssValue" value="label label-warning"/> var autoCompleteDataSource=[
    <c:forEach items="${employeeList}" var="list" varStatus="loop">
    "<c:out value="${list.firstName}"/> <c:out value="${list.lastName}"/>",
    </c:forEach>*/
    ]
</script>



<script type="text/javascript">
 $("#print").click(function () {
  	var url = "./printTransactionDetail.html?transactionId=${transactionId}";
	if($("#proactiveProjectId").val() > 0)
		url += "&pProjectId=" + $("#proactiveProjectId").val();
	else
		url += "&rProjectId=" + $("#reactiveProjectId").val();
	window.location = url;
 });

$("#email").click(function () {
 	var url = "./emailTransactionDetail.html?transactionId=${transactionId}";
	if($("#proactiveProjectId").val() > 0)
		url += "&pProjectId=" + $("#proactiveProjectId").val();
	else
		url += "&rProjectId=" + $("#reactiveProjectId").val();
	window.location = url;
});
</script>
<div id="tabs1">
<div id="innerDivOftabs1">
<!-- ==================== ACTIVITIES HEADLINE ==================== -->
<div class="containerHeadline">
    <div class="controlButton pull-right"><%--<i id="paneThreeIconCross" class="icon-remove handCursor"></i>--%></div>
    <div class="tabThreeIconDiv pull-left" style="display: none;">
        <i id="paneThreeIconHome" class="icon-tasks handCursor analysisLi" title="<spring:message code="analysis" />" data-placement="top" data-toggle="tooltip"></i>
        <i id="paneThreeIconControlsList" class="icon-wrench handCursor controlsLi" title="<spring:message code="newRiskAssessmentSummary.controls" />" data-placement="top" data-toggle="tooltip"></i>
        <i id="paneThreeIconauditTrailList" class="icon-trash handCursor auditTrailLi" title="<spring:message code="newRiskAssessmentSummary.auditTrail" />" data-placement="top" data-toggle="tooltip"></i>
        <i id="paneThreeIconProcedureList" class="icon-upload-alt handCursor procedureLi" title="<spring:message code="policy.procedure" />" data-placement="top" data-toggle="tooltip"></i>
    </div>
</div>
<!-- ==================== END OF ACTIVITIES HEADLINE ==================== -->

<!-- ==================== ACTIVITIES FLOATING BOX ==================== -->
<!-- ==================== ACTIVITIES FLOATING BOX ==================== -->
<div class="floatingBox">
<div id="paneThreeTotalDiv" class="container-fluid">
    <!-- ==================== ACTIVITIES MENU ==================== -->
    <div class="floatingBoxMenu">
        <ul class="nav nav-tabs">
            <li class="active" id="analysisLi"><a href="#analysis"><spring:message code="analysis" /></a></li>
            <li id="controlsLi"><a href="#controls"><spring:message code="newRiskAssessmentSummary.controls" /></a></li>
            <li id="auditTrailLi"><a href="#auditTrail"><spring:message code="newRiskAssessmentSummary.auditTrail" /></a></li>
            <li id="procedureLi"><a href="#procedure"><spring:message code="policy.procedure" /></a></li>
            <%--<li id="IaLi"><a href="#internalAudit"><spring:message code="transactionDetails.thirdPane.tab.IA" /></a></li>--%>
        </ul>
    </div>
    <!-- ==================== END OF ACTIVITIES MENU ==================== -->

 <div id="tabs1" class="container-fluid">


<c:if test="${NewRiskAssessmentTxBean.reactiveProjectId > 0}">
    <c:set var="url" value="./reactiveSummaryView.html"/>
</c:if>
<c:if test="${NewRiskAssessmentTxBean.proactiveProjectId > 0}">
    <c:set var="url" value="./newRiskAssessmentsummaryView.html"/>
</c:if>
<c:if test="${NewRiskAssessmentTxBean.realTimeProjectId > 0}">
    <c:set var="url" value="./realTimeSummaryView.html"/>
</c:if>

<form:form id="testAjaxForm" name="testAjaxForm" commandName="NewRiskAssessmentTxBean" method="post" action="${url}?ctrlId=${ctrlId}&param=1"
           enctype="multipart/form-data">

<form:input type="hidden" id ="proactiveProjectId" path="proactiveProjectId"/>
<form:input type="hidden" id ="reactiveProjectId" path="reactiveProjectId"/>
<input type="hidden" id="transactionId" name="transactionId" value="${transactionId}"/>
<form:input type="hidden" id="realTimeProjectId" path="realTimeProjectId"/>
<form:input type="hidden" id ="controlId" path="controlId"/>
<input type="hidden" id="maxFileUploadSize" name="maxFileUploadSize" value="${maxFileUploadSize}"/>

<div class="infoDiv container-fluid">
    <spring:message code="newRiskAssessmentSummary.irrelevantOrNonResponsive" var="nonRes"/>
    <spring:message code="newRiskAssessmentSummary.furtherReviewRequired" var="furRev"/>
    <spring:message code="newRiskAssessmentSummary.additionalInformationRequired" var="addiRev"/>

    <!-- ====================analysis CONTENT ==================== -->
    <ul class="floatingBoxContainers" id="analysis">
        <li class="">
            <div class="radioDecissionAreaDiv <security:authorize access="hasAnyRole( '${employee}')">readOnly</security:authorize>">
                <spring:message code="newRiskAssessmentSummary.irrelevantOrNonResponsive" var="nonRes"/>
                <spring:message code="newRiskAssessmentSummary.furtherReviewRequired" var="furRev"/>
                <spring:message code="newRiskAssessmentSummary.additionalInformationRequired" var="addiRev"/>

                <div class="decisionArea alert alert-success">
                    <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
                           <c:if test="${NewRiskAssessmentTxBean.decision == nonRes}">checked="checked" </c:if>
                           value="<c:out value="${nonRes}"/>"><c:out value="${nonRes}"/>
                    </input>
                </div>
                <div class="decisionArea alert alert-info">
                    <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
                           <c:if test="${NewRiskAssessmentTxBean.decision == furRev}">checked="checked" </c:if>
                           value="<c:out value="${furRev}"/>"><c:out value="${furRev}"/>
                    </input>
                </div>
                <div class="decisionArea alert alert-error">
                    <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
                           <c:if test="${NewRiskAssessmentTxBean.decision == addiRev}">checked="checked" </c:if>
                           value="<c:out value="${addiRev}"/>"><c:out value="${addiRev}"/>
                    </input>

                </div>
            </div>
            <c:if test="${assignedEmployee==1}">
        <div id="internalAudit" style="display:none" class="assignedEmployeeMainDiv">
            <input type="hidden" id="employeeJason" name ="employeeJason" value=""/>
            <div class="tableDivForFileUploadAjax <security:authorize access="hasAnyRole( '${employee}')">readOnly</security:authorize>" >

                <div class="alert alert-success assignedEmployeeWrapperDiv">
                    <div class="assignedEmployeeTitleDiv">
                        <spring:message code="assigned.employees"/>
                    </div>
                    <div id="selectEmployeeDive" class="assignedEmployeeShownMainDiv">
                        <c:forEach items="${NewRiskAssessmentTxBean.assignedEmployeeList}" var="emp" varStatus="loopStatus">
                            <div class="employeeList" id="<c:out value="${emp.id}"/>"><i class="icon-check darkGreen"></i>&nbsp;&nbsp;<c:out value="${emp.firstName}"/>  <c:out value="${emp.lastName}"/>
                                <span class="removeEmpBtn" style=" margin-left: 10px;" ></span>
                            </div>
                        </c:forEach>
                    </div>
                    <table id="autoCompleteTable">
                        <tr>
                            <td>
                                <input id="autoCompleteTextBox" type="text" class="span12" style="margin: 0 auto;" autocomplete="off" />
                            </td>
                            <td>
                                <input id="addEmployee" type="button"  value=""/>
                            </td>
                        </tr>

                    </table>
                </div>
            </div>
        </div>
            </c:if>
            <div id="btnNcommentBlock1" class="alert fontCss">
                <c:if test="${not empty NewRiskAssessmentTxBean.transactionCommentList}">
                <div class="decisionCommentShowDiv">
                    <table class="table tablesorterForDecisionComment" id="decisionCommentTable" >
                        <tbody>
                        <tr style="background: none repeat scroll 0 0 #FFEBCC">
                        <td style="text-align: center !important;" colspan="4" >
                         Decision Comments
                        </td>
                        </tr>
                        <c:forEach items="${NewRiskAssessmentTxBean.transactionCommentList}" var="list" varStatus="loop">
                            <c:set var="countForColor" value="${loop.index}"/>
                            <c:if test="${countForColor == 6}">
                                <c:set var="countForColor" value="0"/>
                            </c:if>
                            <c:if test="${countForColor == 0}">
                                <c:set var="countForColorClass" value="lightRed"/>
                            </c:if>
                            <c:if test="${countForColor == 1}">
                                <c:set var="countForColorClass" value="blue"/>
                            </c:if>
                            <c:if test="${countForColor == 2}">
                                <c:set var="countForColorClass" value="green"/>
                            </c:if>
                            <c:if test="${countForColor == 3}">
                                <c:set var="countForColorClass" value="darkGreen"/>
                            </c:if>
                            <c:if test="${countForColor == 4}">
                                <c:set var="countForColorClass" value="mediumpurple"/>
                            </c:if>
                            <c:if test="${countForColor == 5}">
                                <c:set var="countForColorClass" value="maroon"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${loop.index%2 == 0}" >
                                    <tr class="success">
                                </c:when>
                                <c:otherwise>
                                    <tr class="info">
                                </c:otherwise>
                            </c:choose>

                            <td align="center" valign="middle">
                               <div class="commentIconDiv"> <i class="icon-tasks ${countForColorClass}"/></div>
                            </td>
                            <td>
                                <c:out value="${list.commenterName}"/>:
                            </td>
                            <td>
                                <c:out value="${list.comment}"/>
                            </td>
                            <td><fmt:formatDate value="${list.commentDate}" pattern="${MMddYYYY}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </c:if>
                 <form:input type="hidden" path="previousDecissionComment" value="${NewRiskAssessmentTxBean.previousDecissionComment}"/>
                <form:textarea path="decisionComment" class="span12 paddingForTextArea"
                               placeholder="Write a comment for decission..."></form:textarea>
            </div>

         </li>
            <div class="tableDivForFileUploadAjax" id="supportingDocListDiv">
                <c:if test="${NewRiskAssessmentTxBean.supportingDocumentList != null}">
                <div id="supDocDiv" class="alert alert-success">
                <ul>
                        <c:forEach items="${NewRiskAssessmentTxBean.supportingDocumentList}" var="list"
                                   varStatus="loopStatus">

                            <li>
                                <c:choose>
                                    <c:when test="${loopStatus.index%2 == 0}" >
                                        <c:set var="cssValue" value="label label-warning"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="cssValue" value="label label-success"/>
                                    </c:otherwise>
                                </c:choose>
                                <span class="${cssValue}" >${fn:toUpperCase(fn:substringAfter (list.fileName, "."))}</span>
                                <strong>
                                    <c:choose>
                                    <c:when test="${fn:length(list.fileName) > 22}" >
                                        ${fn:substring(list.fileName, 0, 22)}...

                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${list.fileName}"/>
                                    </c:otherwise>
                                    </c:choose>
                                </strong>
                                <%--<span class="attActions">--%>
                                <security:authorize access="hasAnyRole( '${adminUser}','${legal}','${compliance}')">
                                <i id="${list.id}" class="icon-minus-sign deleteAttachment pull-right"></i>
                                </security:authorize>
                                <security:authorize access="hasAnyRole( '${adminUser}','${legal}','${compliance}','${ia_analyst}','${ia_manager}')">
                                <i id="${list.id}" class="icon-download-alt downloadAttachment pull-right"></i>
                                <i id="${list.id}" class="icon-eye-open viewAttachment pull-right"></i>
                                </security:authorize>
                                <%--</span>--%>
                            </li>

                        </c:forEach>
                    </ul>
                    </div>
                 </c:if>
            </div>

        <li>
            <div class="radioDecissionAreaDiv alert alert-info">
                <div class="decisionArea alert alert-error">
                <form:textarea path="supportingDocumentComment" id="supDocComment" class="span12 paddingForTextArea"
                               placeholder="Write a comment for supporting document..."/>
                 </div>
            <div class="control-group alert">
                <div class="controls">
                    <form:input path="fileDataForSupportingDocument" type="file" name="file" id="inputFile" placeholder="  Upload supporting document" style="display: none"/>
                    <div class="dummyfile">
                        <input  id="fileDataForSupportingDocument" type="text" class="input disabled span8" name="fileDataForSupportingDocument" readonly="true" placeholder="  Upload supporting document"/>
                        <button id="fileselectbutton" class="btn btn-warning" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                    </div>
                </div>
            </div>
<%--                <div id="jquery-removable-file-upload-example" style="width: 400px;">
                    <form:input type="file" name="file" id="file" path="fileDataForSupportingDocument"></form:input>
                    <b id="fileSizeValidation" style="color: red; display: none;"><spring:message code="maxUploadFileSize.warning.message"/>
                        <fmt:formatNumber type="number" value="${maxFileUploadSize/(1024*1024)}" maxFractionDigits="2" /> <spring:message code="maxUploadFileSize.warning.message.lastPast"/></b>
                </div>--%>
        </div>
      </li>
    </ul>
    <!-- ==================== END OF analysis CONTENT ==================== -->

    <!-- ====================controls CONTENT ==================== -->
    <ul class="floatingBoxContainers <security:authorize access="hasAnyRole( '${employee}')">readOnly</security:authorize>" id="controls" style="display:none">
        <li>
            <p class="headLine">
             <div class="filled info">
                <table id="controlTable" class="controlTable table">
                    <c:forEach items="${NewRiskAssessmentTxBean.controlListForTable}" var="control" varStatus="loopStatus">
                        <c:choose>
                            <c:when test="${loopStatus.index%2 == 0}" >
                                <div class="odd">
                            </c:when>
                            <c:otherwise>
                                <div class="even">
                            </c:otherwise>
                        </c:choose>

                        <div valign="top" class="sl"><c:out value="${loopStatus.index+1}"/></div>

                        <div id="controlText" class="middle">
                            <%--<label class="control-label">--%>${control.name}<%--</label>--%>
                            <div class="controls wareper">
                                <div id="controlOptions" class="styled-select-control">
                                    <select id="contId${loopStatus.index}" name="contId${loopStatus.index}" class="checkOpt">
                                        <c:forEach items="${conrolSel}" var="contPro" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${loop.index == control.optionValue}" >
                                                    <option id="${loop.index}" class="optionClass" value="${loop.index}" selected="selected">${contPro}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option id="${loop.index}"  class="optionClass" value="${loop.index}">${contPro}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="old_${control.id}" value="${control.optionValue}"/>
                            </div>
                        </div>

                    </div>

                    </c:forEach>
                </table>
            <input type="hidden" id="totalControl" value="${fn:length(NewRiskAssessmentTxBean.controlListForTable)}"/>
            </br>
            <div class="radioDecissionAreaDiv alert alert-info">
                <div class="decisionArea alert alert-error">
                    <form:input type="hidden" path="previousControlComment" value="${NewRiskAssessmentTxBean.previousControlComment}"/>
                    <form:textarea path="controlComment" id="commentRegion" class="span12 paddingForTextArea"
                                   placeholder="Write a comment..."/>
                </div>
            </div>
            <%--<div id="btnNcommentBlock">
                <form:input type="hidden" path="previousControlComment" value="${NewRiskAssessmentTxBean.previousControlComment}"/>
                <form:textarea path="controlComment" id="commentRegion" cssClass="commentRegion" placeholder="Write a comment..." />

            </div>--%>
         </div>
       </p>
     </li>
    </ul>
    <!-- ==================== END OF controls CONTENT ==================== -->

    <!-- ==================== auditTrail CONTENT ==================== -->
    <ul class="floatingBoxContainers" id="auditTrail" style="display:none">
       <c:if test="${not empty NewRiskAssessmentTxBean.proactiveTransactionAuditTrialList}">
        <li>
                <div id="tableDiv1" class="filled warning">
                    <table id="auditTrialTableId" class="table tablesorterForAudittrial" >
                        <thead>
                        <th><spring:message code="newRiskAssessmentSummary.date"/></th>
                        <th><spring:message code="user.header"/></th>
                        <th><spring:message code="event"/></th>
                        </thead>
                        <tbody>
                        <c:forEach items="${NewRiskAssessmentTxBean.proactiveTransactionAuditTrialList}" var="list" varStatus="loopStatus">
                        <c:choose>
                        <c:when test="${loopStatus.index%2 == 0}" >
                        <tr class="success">
                            </c:when>
                            <c:otherwise>
                        <tr class="info">
                            </c:otherwise>
                            </c:choose>

                            <td><fmt:formatDate value="${list.changeDate}" pattern="${MMddYYYY}"/></td>
                            <td>
                                <c:out value="${list.changeAuthor}"/>
                            </td>
                            <td>
                                <c:out value="${list.changeAction}"/>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
         </li>
       </c:if>
    </ul>
    <!-- ==================== END OF auditTrail CONTENT ==================== -->

    <!-- ==================== procedure CONTENT ==================== -->
    <ul class="floatingBoxContainers" id="procedure" style="display:none">
        <div class="controls">

        </div>
        <div class="tableDivForFileUploadAjax" >
            <%--<c:if test="${NewRiskAssessmentTxBean.policyList != null}">--%>
                <div id="supDocDiv" class="alert alert-success <security:authorize access="hasAnyRole( '${employee}')">readOnly</security:authorize>">

                    <form:textarea path="policyComment" class="span12 paddingForTextArea" placeholder="Write a comment ..."></form:textarea>
                    <form:input path="transactionPolicy" type="file" name="file" id="policyInput" placeholder="  Upload policy" style="display: none"/>
                    <div class="dummyfile">
                        <input  id="transactionPolicy" type="text" class="input disabled span8" name="transactionPolicy" readonly="true" placeholder="  Upload policy"/>
                        <button id="policySelection" class="btn btn-warning" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                    </div>
                    <div id="txPolicyListWrapperDiv">
                    <ul>
                        <c:forEach items="${NewRiskAssessmentTxBean.policyList}" var="list"
                                   varStatus="loopStatus">

                            <li>
                                   <c:choose>
                                    <c:when test="${loopStatus.index%2 == 0}" >
                                        <c:set var="cssValue" value="label label-warning"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cssValue" value="label label-success"/>
                                        </c:otherwise>
                                        </c:choose>
                                         <span class="${cssValue}" >${fn:toUpperCase(fn:substringAfter (list.fileName, "."))}</span>
                                <strong>
                                    <c:choose>
                                        <c:when test="${fn:length(list.fileName) > 25}" >
                                            ${fn:substring(list.fileName, 0, 25)}...
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${list.fileName}"/>
                                        </c:otherwise>
                                    </c:choose>

                                </strong>
                                <security:authorize access="hasAnyRole( '${adminUser}','${legal}','${compliance}')">
                                <i id="${list.id}" class="icon-minus-sign deletePolicyAttachment pull-right"></i>
                                </security:authorize>
                                <i id="${list.id}" class="icon-download-alt downloadPolicyAttachment pull-right"></i>
                                <i id="${list.id}" class="icon-eye-open viewPolicyAttachment pull-right"></i>
                            </li>

                        </c:forEach>
                    </ul>
                  </div>
                </div>
            <%--</c:if>--%>
        </div>
    </ul>
    <!-- ==================== END OF procedure CONTENT ==================== -->
</div>

</form:form>
</div>
 </div>
</div>
</div>
</div>
<!-- ==================== Example tabs==================== -->
<div id="tabTwo" class="span4 infoTabs">
    <div class="containerHeadline">
        <div class="pull-right"><%--<i id="paneTwoIconCross" class="icon-remove handCursor"></i>--%></div>
        <div class="tabTwoIconDiv pull-left" style="display: none;">
            <i id="paneTwoIconHome" class="icon-folder-open handCursor AdditionalInforormationLi" title="<spring:message code="newRiskAssessmentSummary.transactionDetails.AdditionalInforormation" />" data-placement="top" data-toggle="tooltip"></i>
            <i id="paneTwoIconEmplInfoList" class="icon-info handCursor EmployeeInformationLi" title="<spring:message code="newRiskAssessmentSummary.transactionDetails.EmployeeInformation" />" data-placement="top" data-toggle="tooltip"></i>
            <i id="paneTwoIconCusMasFileList" class="icon-legal handCursor customerMasterFileLi" title="<spring:message code="newRiskAssessmentSummary.transactionDetails.customerMasterFile" />" data-placement="top" data-toggle="tooltip"></i>
        </div>
    </div>
    <div class="floatingBox">
        <div id="paneTwoTotalDiv" class="container-fluid">
            <div class="floatingBoxMenu">
                <ul class="nav nav-tabs">
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.additionalInfoList) > 0}">
                        <li id="AdditionalInforormationLi" ><a href="#AdditionalInforormation"><spring:message code="newRiskAssessmentSummary.transactionDetails.AdditionalInforormation" /></a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.employeeInfoList) > 0}">
                        <li id="EmployeeInformationLi"><a href="#EmployeeInformation"><spring:message code="newRiskAssessmentSummary.transactionDetails.EmployeeInformation" /></a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.cusromerList) > 0}">
                        <li id="customerMasterFileLi"><a href="#customerMasterFile"><spring:message code="newRiskAssessmentSummary.transactionDetails.customerMasterFile" /></a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.vendorMasterFileList) > 0}">
                        <li id="vendorMasterFileLi"><a href="#vendorMasterFile"><spring:message code="newRiskAssessmentSummary.transactionDetails.vendorMasterFile" /></a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.violationList) > 0}">
                        <li id="violationLi"><a href="#violationDiv"><spring:message code="newRiskAssessmentSummary.transactionDetails.violation" /></a></li>
                    </c:if>
               </ul>
            </div>
            <div class=" infoDiv container-fluid">
                <ul class="floatingBoxContainers" id="AdditionalInforormation">
                    <li>
                        <c:if test="${fn:length(NewRiskAssessmentTxBean.additionalInfoList) > 0}">
                        <p class="headLine">
                        <div class="filled warning">
                        <dl id="dlIdOne" class="dl-horizontal filled info">
                        <c:forEach items="${NewRiskAssessmentTxBean.additionalInfoList}" var="additionalInfoList" varStatus="loopStatus">
                            <dt><c:out value="${additionalInfoList.header}"/> :</dt>
                            <dd>
                                <c:choose>
                                   <c:when test="${fn:containsIgnoreCase(additionalInfoList.header,'PO Amount') == true}" >
                                       <fmt:setLocale value="en_US"/>
                                       <fmt:formatNumber value="${additionalInfoList.value}" type="currency"/>
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(additionalInfoList.header,'Unit Qty') == true}" >
                                       <fmt:formatNumber value="${additionalInfoList.value}" type="number"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${additionalInfoList.value}"/>
                                    </c:otherwise>
                                    </c:choose>
                            </dd>
                            </c:forEach>
                        </dl>
                        </div>
                        </p>
                        </c:if>
                    </li>
                </ul>

                <ul class="floatingBoxContainers" id="EmployeeInformation" style="display:none">
                    <li>
                        <c:if test="${fn:length(NewRiskAssessmentTxBean.employeeInfoList) > 0}">
                        <p class="headLine">
                        <div class="filled success">
                        <dl id="dlIdTwo" class="dl-horizontal filled warning">
                          <%--  <c:forEach items="${NewRiskAssessmentTxBean.employeeInfoList}" var="employeeInfoList" varStatus="loopStatus">
                                <dt><c:out value="${employeeInfoList.header}"/> :</dt>
                                <dd><c:out value="${employeeInfoList.value}"/></dd>
                            </c:forEach>--%>
                              <c:forEach items="${NewRiskAssessmentTxBean.employeeInfoList}" var="item">
                                  <c:forEach items="${item}" var="itemVal">
                                      <c:if test="${not empty itemVal.value && itemVal.key !='is_debit' && itemVal.key !='employee_id'  && itemVal.key !='id' && itemVal.key !='transaction_fk'}">
                                          <dt><c:out value="${fn:replace(itemVal.key,'_',' ')}"/> :</dt>
                                          <dd><c:out value="${itemVal.value}"/></dd>
                                      </c:if>
                                  </c:forEach>
                              </c:forEach>
                        </dl>
                        </div>
                        </p>
                      </c:if>
                    </li>
                </ul>

                <ul class="floatingBoxContainers" id="customerMasterFile" style="display:none">
                    <li>
                        <c:if test="${fn:length(NewRiskAssessmentTxBean.cusromerList) > 0}">
                        <p class="headLine">
                            <div class="filled info">
                            <dl id="dlIdThree" class="dl-horizontal filled success">
                               <%-- <c:forEach items="${NewRiskAssessmentTxBean.cusromerList}" var="cusromerList" varStatus="loopStatus">
                                    <dt><c:out value="${cusromerList.header}"/> :</dt>
                                    <dd><c:out value="${cusromerList.value}"/></dd>
                                </c:forEach>--%>
                                   <c:forEach items="${NewRiskAssessmentTxBean.cusromerList}" var="item">
                                       <c:forEach items="${item}" var="itemVal">
                                           <c:if test="${not empty itemVal.value && itemVal.key !='is_debit' && itemVal.key !='customer_id' && itemVal.key !='id' && itemVal.key !='transaction_fk'}">
                                               <dt><c:out value="${fn:replace(itemVal.key,'_',' ')}"/> :</dt>
                                               <dd><c:out value="${itemVal.value}"/></dd>
                                           </c:if>
                                       </c:forEach>
                                   </c:forEach>
                            </dl>
                        </div>
                        </p>
                        </c:if>
                    </li>
                </ul>

                <ul class="floatingBoxContainers" id="vendorMasterFile" style="display:none">
                    <li>
                        <c:if test="${fn:length(NewRiskAssessmentTxBean.vendorMasterFileList) > 0}">
                        <p class="headLine">
                            <div class="filled info">
                            <dl id="dlIdFour" class="dl-horizontal filled success">
                               <%-- <c:forEach items="${NewRiskAssessmentTxBean.vendorMasterFileList}" var="list" varStatus="loopStatus">
                                    <dt><c:out value="${list.key}"/> :</dt>
                                    <dd><c:out value="${list.value}"/></dd>
                                </c:forEach>--%>
                                   <c:forEach items="${NewRiskAssessmentTxBean.vendorMasterFileList}" var="item">
                                       <c:forEach items="${item}" var="itemVal">
                                           <c:if test="${itemVal.key =='entity_status' ||  (not empty itemVal.value && itemVal.key !='is_debit' && itemVal.key !='id'&& itemVal.key !='vendor_id' && itemVal.key !='transaction_fk')}">
                                           <dt><c:out value="${fn:replace(itemVal.key,'_',' ')}"/> :</dt>
                                           <dd><c:out value="${itemVal.value}"/></dd>
                                           </c:if>
                                       </c:forEach>
                                   </c:forEach>
                            </dl>
                        </div>
                        </p>
                        </c:if>
                    </li>
                </ul>

                <ul class="floatingBoxContainers" id="violationDiv" style="display:none">
                    <li>
                        <c:if test="${fn:length(NewRiskAssessmentTxBean.violationList) > 0}">
                        <p class="headLine">
                            <div class="container-fluid">
                              <table class="table table-condensed table-striped gradientHeader inbox">
                                  <thead>
                                  <tr>
                                      <th><spring:message code="rule.title"/></th>
                                  </tr>
                                  </thead>
                                  <c:forEach items="${NewRiskAssessmentTxBean.violationList}" var="list" varStatus="loopStatus">
                                      <c:choose>
                                          <c:when test="${loopStatus.index%2 == 0}" >
                                              <tr class="error">
                                          </c:when>
                                          <c:otherwise>
                                              <tr class="info">
                                          </c:otherwise>
                                      </c:choose>
                                   <td><c:out value="${list.ruleTitle}"/></td>
                                   </tr>
                                </c:forEach>
                              </table>
                        </div>
                        </p>
                        </c:if>
                    </li>
                </ul>

            </div>
        </div>
    </div>

</div>
<!-- example tabs-->
<script type="text/javascript">

</script>
