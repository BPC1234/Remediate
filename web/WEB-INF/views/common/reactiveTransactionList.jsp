<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>

<script src="<%= contextPath %>/resources/js/jquery-ui.js"></script>
<script src="<%= contextPath %>/resources/js/custom-risk-assesment-summary.js"></script>
<script src="<%=contextPath%>/resources/js/real-time-monitoring-workflow.js"></script>

<input type="hidden" id="proactiveProjectId" value="${proactiveProjectId}"/>
<input type="hidden" id="reactiveProjectId" value="${reactiveProjectId}"/>
<input type="hidden" id="realTimeProjectId" value="${realTimeProjectId}"/>

<input type="hidden" id="transactionId" value="${transactionId}"/>
<input type="hidden" id ="controlId" value="${ctrlId}"/>
<input type="hidden" id ="serialNoForTableRowSelection" value="${serialNoForTableRowSelection}"/>
<input type="hidden" id ="projectStatus" value="${projectStatus}"/>
<input type="hidden" id ="pageNo" value="${pageNo}"/>

<title><spring:message code="newRiskAssessmentSummary.summaryOfThirdPartyTransactions"/></title>
<!-- ==================== WHOLE TABS START ==================== -->
<div class="row-fluid">
    <div id="mainDivOfthree" class="span12">
        <img id="loading" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display:none;"/>
        <input id="autoCompleteInputTextBox" type="hidden" class="span12" style="margin: 0 auto;" value='[<c:forEach items="${NewRiskAssessmentTxBean.employeeList}" var="list" varStatus="loop">{"empId" : "${list.id}","empName" : "${list.firstName} ${list.lastName}"}<c:if test="${!loop.last}">,</c:if></c:forEach>]'>

        <div class="floatingBox">
            <div class="container-fluid">
                <!-- ==================== FIRST TAB START ==================== -->
                <div class="span4" id="tabOne">
                    <!-- ==================== ACTIVITIES HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <div class="pull-right"><i id="paneOneIconCross" class="icon-remove handCursor"></i></div>
                        <div class="tabOneIconDiv pull-left" style="display: none;">
                            <i id="paneOneIconTrxList" class="icon-th-large handCursor summaryOfThirdPartyTransactionsLi" title="<spring:message code="newRiskAssessmentSummary.summaryOfThirdPartyTransactions" />" data-placement="top" data-toggle="tooltip"></i>
                            <i id="paneOneIconHome" class="icon-list-ol handCursor transactionListLi" title="<spring:message code="newRiskAssessmentSummary.transactionList" />" data-placement="top" data-toggle="tooltip"></i>
                        </div>
                    </div>
                    <!-- ==================== END OF ACTIVITIES HEADLINE ==================== -->

                    <!-- ==================== ACTIVITIES FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div id="paneOneTotalDiv" class="container-fluid">
                            <!-- ==================== ACTIVITIES MENU ==================== -->
                            <div class="floatingBoxMenu">
                                <ul class="nav nav-tabs">
                                    <li <c:if test="${urlParam == 0}">class="active"</c:if> id="summaryOfThirdPartyTransactionsLi"><a href="#summaryOfThirdPartyTransactions"><spring:message code="newRiskAssessmentSummary.summaryOfThirdPartyTransactions" /></a></li>
                                    <li <c:if test="${urlParam == 1}">class="active"</c:if>id="transactionListLi"><a href="#transactionList"><spring:message code="newRiskAssessmentSummary.transactionList" /></a></li>
                                </ul>
                            </div>
                            <!-- ==================== END OF ACTIVITIES MENU ==================== -->

                            <div id="tabOneInsidPaneOne" class="infoDiv container-fluid">
                                <!-- ====================transactionList CONTENT ==================== -->
                                <ul class="floatingBoxContainers" id="transactionList" <c:if test="${urlParam == 0}">style="display:none"</c:if>>

                                    <li>
                                        <div id="tabs-2" class="customFlexigridCss">
                                            <c:set var="count" value="0" scope="page" />
                                            <table id="summeryTableForNewRiskAssesment">
                                            </table>
                                        </div>
                                    </li>
                                </ul>
                                <!-- ==================== END OF transactionList CONTENT ==================== -->

                                <!-- ==================== summaryOfThirdPartyTransactions CONTENT ==================== -->
                                <ul class="floatingBoxContainers" id="summaryOfThirdPartyTransactions" <c:if test="${urlParam == 1}">style="display:none"</c:if>>
                                    <div id="supDocDiv" class="warning customFlexigridCss">
                                        <table id="summeryTable1">
                                        </table>
                                    </div>
                                </ul>
                                <!-- ==================== END OF summaryOfThirdPartyTransactions CONTENT ==================== -->

                            </div>
                        </div>
                    </div>
                    <!-- ==================== END OF ACTIVITIES FLOATING BOX ==================== -->
                </div>
                <!-- ==================== END OF FIRST TAB ==================== -->

                <!-- ==================== Second  tabs==================== -->
                <div id="tabTwo" class="span4">
                </div>
                <!-- end of Second tabs-->
                <!-- ==================== ACTIVITIES CONTAINER ==================== -->
                <div class="span4" id="tabThree">
                </div>
                <!-- ==================== END OF ACTIVITIES FLOATING BOX ==================== -->
            </div>
        </div>   <!--end container-fluid div-->
    </div>   <!--end floatingBox div-->
</div>   <!--end span12 div-->

<!-- ==================== END OF WHOLE TABS ==================== -->

<script defer="defer">
    //var maxFileSize = ${maxFileUploadSize};

    var tabOne = true;
    var tabTwo = true;
    var tabThree = true;
    //var txId =  ${serialNoForTableRowSelection}; /*here txId is replaced by serial Don't Confused*/
    var tableRowNo = 'table #';
    $(".buttonGroup").show();
    var user=${user};
    addEmployeeBtn();
</script>
