<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% final String contextPath = request.getContextPath(); %>

<script type="text/javascript">
    $(function() {
        /* For zebra striping */
        $("control table tr:nth-child(odd)").addClass("odd-row");
        /* For cell text alignment */
        $("control table td:first-child, table th:first-child").addClass("first");
        /* For removing the last border */
        $("control table td:last-child, table th:last-child").addClass("last");

    });

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
<div id="tabs1" style="float:left;margin: 0px ;padding: 0px;">
<div id="innerDivOftabs1">

<c:if test="${NewRiskAssessmentTxBean.reactiveProjectId > 0}">
    <c:set var="url" value="./reactiveSummaryView.html"/>
</c:if>
<c:if test="${NewRiskAssessmentTxBean.proactiveProjectId > 0}">
    <c:set var="url" value="./newRiskAssessmentsummaryView.html"/>
</c:if>
<c:if test="${NewRiskAssessmentTxBean.realTimeProjectId > 0}">
    <c:set var="url" value="./realTimeSummaryView.html"/>
</c:if>

<form:form id="testAjaxForm" commandName="NewRiskAssessmentTxBean" method="post" action="${url}?ctrlId=${ctrlId}&param=1"
           enctype="multipart/form-data">

<form:input type="hidden" id ="proactiveProjectId" path="proactiveProjectId"/>
<form:input type="hidden" id ="reactiveProjectId" path="reactiveProjectId"/>
<input type="hidden" id="transactionId" name="transactionId" value="${transactionId}"/>
<form:input type="hidden" id="realTimeProjectId" path="realTimeProjectId"/>
<form:input type="hidden" id ="controlId" path="controlId"/>
<input type="hidden" id="maxFileUploadSize" name="maxFileUploadSize" value="${maxFileUploadSize}"/>



<div class="verticalDivForThirdPane ui-widget-header" style="height: 3.6%;width:1%;display: table-cell;">
    <ul class="menu1" style="margin-bottom: 3px;">
        <li id="thirdPane-tabsOne"><a id="trxDetailsList" href="#1" class="current" title='<spring:message code="analysis" />'>
            <label><img src="<%=contextPath%>/resources/images/home.png" style=""/></label>
            <label class="labelWithImage" id="trxDetailsLabel"><spring:message code="analysis" /></label>
        </a></li>
       <%-- <li id="thirdPane-tabsThree"><a id="supportingDocList" href="#2" title='<spring:message code="newRiskAssessmentSummary.supportingDocument" />'>
            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
            <label class="labelWithImage" id="supportingDocLabel"><spring:message code="newRiskAssessmentSummary.supportingDocument" /></label>
        </a></li>--%>
        <li id="thirdPane-tabsTwo"><a id="controlList" href="#2" title='<spring:message code="newRiskAssessmentSummary.controls" />'>
            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
            <label class="labelWithImage" id="controlLabel"><spring:message code="newRiskAssessmentSummary.controls" /></label>
        </a></li>
        <li id="thirdPane-tabsFive"><a id="auditTrailList" href="#2" title='<spring:message code="newRiskAssessmentSummary.auditTrail" />'>
            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
            <label class="labelWithImage" id="auditTrailLabel"><spring:message code="newRiskAssessmentSummary.auditTrail" /></label>
        </a></li>
        <li id="thirdPane-tabsSix"><a id="referenceList" href="#2" title='<spring:message code="adminHeader.references" />'>
            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
            <label class="labelWithImage" id="referemceLabel"><spring:message code="adminHeader.references" /></label>
        </a></li>

<%--        <div class="closeButtonDiv" style="margin-top: 1px; padding-right: 4px ;padding-top:3px;text-align: right">
            <a> <img class="closeImage" id="closeThirdPane" src="<%=contextPath%>/resources/images/close.png" style=" width: 25px; height: 22px;"/></a>
        </div>--%>
    </ul>

</div>
<div id="buttonGroup">
<%--<div class="buttonTab" style="text-align:center" >
    <table class="buttonGroupTable">
     <tr>
         <td><input type="submit" id="cancelButton" name="Submit" class="myButton submitButton" style="border: 0 none;box-shadow: 0 1px 5px #DDDDDD;color: #FFFFFF;padding: 5px;" value="Cancel"/></td>
         <td><input type="submit" class="myButton submitButton" value="Save" style="border: 0 none;box-shadow: 0 1px 5px #DDDDDD;color: #FFFFFF;padding: 5px;"/></td>
         <td><input type="button" id="print" class="myButton submitButton" value='<spring:message code="newRiskAssessmentSummary.print"/>' /></td>
         <td><input type="button" id="email" class="myButton submitButton"  value='<spring:message code="newRiskAssessmentSummary.email"/> '/></td>
     </tr>
    </table>
</div>--%>
</div>

<div id="tabs-1" class="thirdPane-tabsOne">

<div class="radioDecissionAreaDiv" <%--style="margin-top: 209px"--%>>
    <spring:message code="newRiskAssessmentSummary.irrelevantOrNonResponsive" var="nonRes"/>
    <spring:message code="newRiskAssessmentSummary.furtherReviewRequired" var="furRev"/>
    <spring:message code="newRiskAssessmentSummary.additionalInformationRequired" var="addiRev"/>

    <div class="decisionArea">
        <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
               <c:if test="${NewRiskAssessmentTxBean.decision == nonRes}">checked="checked" </c:if>
               value="<c:out value="${nonRes}"/>"><c:out value="${nonRes}"/>
        </input>
    </div>
    <div class="decisionArea">
        <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
               <c:if test="${NewRiskAssessmentTxBean.decision == furRev}">checked="checked" </c:if>
               value="<c:out value="${furRev}"/>"><c:out value="${furRev}"/>
        </input>
    </div>
    <div class="decisionArea">
        <input type="radio" name="radioDecissionArea" class="radioDecissionArea"
               <c:if test="${NewRiskAssessmentTxBean.decision == addiRev}">checked="checked" </c:if>
               value="<c:out value="${addiRev}"/>"><c:out value="${addiRev}"/>
        </input>

    </div></div>

<div id="btnNcommentBlock1">
    <form:input type="hidden" path="previousDecissionComment" value="${NewRiskAssessmentTxBean.previousDecissionComment}"/>
    <form:textarea path="decisionComment" cssClass="commentRegion"
                   placeholder="Write a comment..."></form:textarea>
</div>

    <br>
    <br>
    <div id="tableDiv1" class="tableDivForFileUploadAjax" >
        <c:if test="${not empty NewRiskAssessmentTxBean.supportingDocumentList}">
            <table id="tablesorterForSupportingDocument" class="tablesorterForSupportingDocument">
                <tbody>
                <c:forEach items="${NewRiskAssessmentTxBean.supportingDocumentList}" var="list"
                           varStatus="loopStatus">
                    <c:if test="${not empty list.fileIconLocation}">
                        <tr>

                            <td>
                                <c:out value="${list.fileName}"/>
                            </td>
                            <td>
                                <img src="<%= contextPath %>/<c:out value='${list.fileIconLocation}'/>" class="fileIcon"/>
                            </td>
                            <td>
                                <c:out value="${list.author}"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${list.entryTime}" pattern="${MMddYYYY}"/>
                            </td>
                            <td style="cursor: pointer;">
                                <c:out value="Delete"/>
                            </td>
                            <td class="supportingDocumentId" style="display: none;">
                                <c:out value="${list.id}"/>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    <br><br>
    <div class="supportingDocumentFileUploadDiv">
        <form:textarea path="supportingDocumentComment" id="supDocComment" cssClass="commentRegion"
                       placeholder="Write a comment..."/>
        <br><br>
        <div id="jquery-removable-file-upload-example" style="width: 400px;">
            <form:input type="file" name="file" id="file" path="fileDataForSupportingDocument" placeholder="  Upload supporting document"></form:input>
            <b id="fileSizeValidation" style="color: red; display: none;"><spring:message code="maxUploadFileSize.warning.message"/>
                <fmt:formatNumber type="number" value="${maxFileUploadSize/(1024*1024)}" maxFractionDigits="2" /> <spring:message code="maxUploadFileSize.warning.message.lastPast"/></b>
        </div>
    </div>

</div>
</br></br>
<div id="tabs-5" class="thirdPane-tabsFive">
    <c:if test="${not empty NewRiskAssessmentTxBean.proactiveTransactionAuditTrialList}">
        <div id="tableDiv1">
            <br/>
            <br/>

            <table class="tablesorterForAudittrial" >

                <c:forEach items="${NewRiskAssessmentTxBean.proactiveTransactionAuditTrialList}" var="list" varStatus="loopStatus">
                <tbody>
                <tr>
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
    </c:if>
</div>


<div id="tabs-2" class="thirdPane-tabsSix">
        <div id="policySubTitle"><spring:message code="policy.procedure"/></div>
        <table id="policyTableGrid" class="display" border="0" cellspacing="0" cellpadding="0"
               aria-describedby="example_info">
            <thead>
            <tr role="row">
                <th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width: 10px;" aria-sort="ascending" aria-label="Browser: activate to sort column ascending">
                </th>
                <th class="sorting" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width: 130px; "
                    aria-label="Rendering engine: activate to sort column descending"><spring:message code="policy.name"/>
                </th>
                <th class="sorting" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width:40px;" aria-label="Browser: activate to sort column ascending"><spring:message code="document.download"/>
                </th>

                <th class="sorting" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width: 160px;text-align: center;" aria-label="Browser: activate to sort column ascending"><spring:message code="document.uploaded.by"/>
                </th>
                <th class="sorting" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width: 34px;" aria-label="Browser: activate to sort column ascending"><spring:message code="document.date"/>
                </th>
                <th class="sorting" role="columnheader" tabindex="0" aria-controls="" rowspan="1"
                    colspan="1" style="width: 34px;display: none;" aria-label="Browser: activate to sort column ascending"><spring:message code="document.date"/>
                </th>

            </tr>
            </thead>
            <tbody role="alert" aria-live="polite" aria-relevant="all">
            <c:forEach items="${NewRiskAssessmentTxBean.policyList}" var="policy" varStatus="loop">
                <tr>
                    <td><c:out value="${loop.index + 1}"/></td>
                    <td style="text-align: justify;"><div><label><c:out value="${policy.documentName}" /></label></div></td>
                    <td ><div><img src="<%= contextPath %>/<c:out value='${policy.fileIconLocation}'/>" class="fileIcon"/></div></td>
                    <td><div><c:out value="${policy.createdBy}"/></div></td>
                    <td><div><fmt:formatDate value="${policy.created}" pattern="${MMddYYYY}"/></div></td>
                    <td class="policyId" style="display: none;"><c:out value="${policy.id}"/></td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
</div>
<div id="tabs-2" class="thirdPane-tabsTwo">
    <div id="control" class="datagrid">
        <table class="controlTable">
            <c:forEach items="${NewRiskAssessmentTxBean.controlListForTable}" var="control" varStatus="loopStatus">
                <c:choose>
                    <c:when test="${loopStatus.index%2 == 0}" >
                        <tr class="alt">
                    </c:when>
                    <c:otherwise>
                        <tr style="border: 1px solid #5c9ccc; border-left-width:0px; border-right-width:0px;">
                    </c:otherwise>
                </c:choose>


                <td valign="top"><c:out value="${loopStatus.index+1}"/></td>
                <td style="text-align:justify;width:auto;">
                    <label><c:out value="${control.name}"/></label>
                </td>
                <td>
                    <select name="<c:out value="${control.id}"/>" class="checkOpt" >
                        <c:forEach items="${conrolSel}" var="contPro" varStatus="loop">
                            <c:choose>
                                <c:when test="${loop.index == control.optionValue}" >
                                    <option value="${loop.index}" selected="selected">${contPro}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${loop.index}">${contPro}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="old_${control.id}" value="${control.optionValue}"/>
                </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="btnNcommentBlock">
        <form:input type="hidden" path="previousControlComment" value="${NewRiskAssessmentTxBean.previousControlComment}"/>
        <form:textarea path="controlComment" id="commentRegion" cssClass="commentRegion" placeholder="Write a comment..." />
        </br>
        <br>
       
    </div>
    <br/>
    <br/>
    <br/>

</div>

<div id="tabs-3" class="thirdPane-tabsThree">
</div>
</form:form>
</div>
</div>

<div id="tabs2" style="float:left;margin: 0px ;padding: 0px;">
<c:if test="${fn:length(NewRiskAssessmentTxBean.additionalInfoList) >0 || fn:length(NewRiskAssessmentTxBean.employeeInfoList) > 0
 || fn:length(NewRiskAssessmentTxBean.cusromerList) >0 || fn:length(NewRiskAssessmentTxBean.vendorMasterFileList) >0 }">
    <div id="subTabsUnderTabs1">
        <div id="innerDivOfSubTabsUnderTabs1">
            <div class="verticalDivForSecondPane ui-widget-header" style="height: 3.6%;width:1%;display: table-cell;">
                <ul class="menu1" style="margin-bottom: 3px;">
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.additionalInfoList) > 0}">
                        <li class="tabs-1UnderTabs1"><a id="trxDetailsAddiInfoList" href="#1" class="current" title='<spring:message code="newRiskAssessmentSummary.transactionDetails.AdditionalInforormation" />'>
                            <label><img src="<%=contextPath%>/resources/images/home.png" style=""/></label>
                            <label class="labelWithImage" id="trxDetailsAddiInfoLabel"><spring:message code="newRiskAssessmentSummary.transactionDetails.AdditionalInforormation" /></label>
                        </a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.employeeInfoList) > 0}">
                        <li class="tabs-2UnderTabs1"><a id="employeeInfoList" href="#2" title='<spring:message code="newRiskAssessmentSummary.transactionDetails.EmployeeInformation" />'>
                            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
                            <label class="labelWithImage" id="employeeInfoLabel"><spring:message code="newRiskAssessmentSummary.transactionDetails.EmployeeInformation" /></label>
                        </a></li>
                    </c:if>
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.cusromerList) > 0}">
                        <li class="tabs-3UnderTabs1"><a id="customerMasterFileList" href="#2" title='<spring:message code="newRiskAssessmentSummary.transactionDetails.customerMasterFile" />'>
                            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
                            <label class="labelWithImage" id="customerMasterFileLabel"><spring:message code="newRiskAssessmentSummary.transactionDetails.customerMasterFile" /></label>
                        </a></li>
                    </c:if>
                    <%--<c:if test="${fn:length(NewRiskAssessmentTxBean.vendorMasterFileList) > 0}">
                        <li class="tabs-4UnderTabs1"><a id="vendorMasterFileList" href="#2" title='<spring:message code="newRiskAssessmentSummary.transactionDetails.vendorMasterFile" />'>
                            <label><img src="<%=contextPath%>/resources/images/icon-generic.gif" style=""/></label>
                            <label class="labelWithImage" id="vendorMasterFileLabel"><spring:message code="newRiskAssessmentSummary.transactionDetails.vendorMasterFile" /></label>
                        </a></li>
                    </c:if>--%>

                  <%--  <div class="closeButtonDivForSecondPane" style="margin-top: 1px; padding-right: 4px ;padding-top:3px;text-align: right">
                        <a> <img class="closeImage" id="closeSecondPane" src="<%=contextPath%>/resources/images/close.png"/></a>
                    </div>--%>
                </ul>
            </div>
            <div id="tabs-1UnderTabs1">
              <div class="additionalInfoDiv">
                <table border="0" class="additionalInfoTable">
                <c:forEach items="${NewRiskAssessmentTxBean.additionalInfoList}" var="additionalInfoList" varStatus="loopStatus">
                <tr>
                    <td class="tdRight"><c:out value="${additionalInfoList.header}"/> :</td>
                    <td class="tdLeft tdFontBold"><c:out value="${additionalInfoList.value}"/></td>
                </tr>
                </c:forEach>
                </table>
              </div>
            </div>
            <div id="tabs-2UnderTabs1">
                <div class="additionalInfoDiv">
                  <c:if test="${fn:length(NewRiskAssessmentTxBean.employeeInfoList) > 0}">
                    <table border="0" class="additionalInfoTable">
                        <c:forEach items="${NewRiskAssessmentTxBean.employeeInfoList}" var="employeeInfoList" varStatus="loopStatus">
                            <tr>
                                <td class="tdRight"><c:out value="${employeeInfoList.header}"/> :</td>
                                <td class="tdLeft tdFontBold"><c:out value="${employeeInfoList.value}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                  </c:if>
                </div>
            </div>
            <div id="tabs-3UnderTabs1">
                <div class="additionalInfoDiv">
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.cusromerList) > 0}">
                        <table border="0" class="additionalInfoTable">
                            <c:forEach items="${NewRiskAssessmentTxBean.cusromerList}" var="cusromerList" varStatus="loopStatus">
                                <tr>
                                    <td class="tdRight"><c:out value="${cusromerList.header}"/> :</td>
                                    <td class="tdLeft tdFontBold"><c:out value="${cusromerList.value}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div>
            <div id="tabs-4UnderTabs1">
                <div class="additionalInfoDiv">
                    <c:if test="${fn:length(NewRiskAssessmentTxBean.vendorMasterFileList) > 0}">
                        <table border="0" class="additionalInfoTable">
                            <c:forEach items="${NewRiskAssessmentTxBean.vendorMasterFileList}" var="vendorMasterFileList" varStatus="loopStatus">
                                <tr>
                                    <td class="tdRight"><c:out value="${vendorMasterFileList.header}"/> :</td>
                                    <td class="tdLeft tdFontBold"><c:out value="${vendorMasterFileList.value}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</c:if>
</div>

