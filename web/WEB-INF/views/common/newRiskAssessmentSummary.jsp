<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>
<html>
<head>
    <title><spring:message code="newRiskAssessmentSummary.title" /></title>
</head>
<script src="<%=contextPath%>/resources/js/real-time-monitoring-workflow.js"></script>
<script src="<%= contextPath %>/resources/js/risk-assesment-summary.js"></script>
<body>

<input type="hidden" id="proactiveProjectId" value="${proactiveProjectId}"/>
<input type="hidden" id="reactiveProjectId" value="${reactiveProjectId}"/>
<input type="hidden" id="realTimeProjectId" value="${realTimeProjectId}"/>

<input type="hidden" id="transactionId" value="${transactionId}"/>
<input type="hidden" id ="controlId" value="${ctrlId}"/>
<input type="hidden" id ="serialNoForTableRowSelection" value="${serialNoForTableRowSelection}"/>

<div id="content">
<!-- Left side Tabs Start from here -->
<div id="tabs" style="float:left;">
    <ul>
        <li><a href="#tabs-2"><b><spring:message code="newRiskAssessmentSummary.transactionList" /></b></a></li>
        <li><a href="#tabs-1"><b><spring:message code="newRiskAssessmentSummary.summaryOfThirdPartyTransactions" /></b></a></li>
        <%--<li><a href="#tabs-3"><b>Transaction Details</b></a></li>--%>
    </ul>
    <img id="loading" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display:none;"/>
    <img id="upLoading" src="<%=contextPath%>/resources/images/uploading2.gif" style="display:none;"/>
    <%--
    <div id="tabs-3">
        <div id="detailSection" style="width:100%">
            <div class="firstColumn" style="float:left;">
                <div class="detailContent"><div class="contentLabel">Transaction ID : </div><div class="contentValue"><label><b>10101</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Date : </div><div class="contentValue"><label><b>21/05/2013</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Amount : </div><div class="contentValue"><label><b>$20,000</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Region : </div><div class="contentValue"><label><b>North American</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Document Creator : </div><div class="contentValue"><label><b> jhon Smith</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Approver : </div><div class="contentValue"><label><b> Alex Liu</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Date of Approval : </div><div class="contentValue"><label><b>21/05/2013</b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Violated Test : </div><div class="contentValue"><label><b> Yes </b></label></div></div>
                <div class="detailContent"><div class="contentLabel">Intended Payee : </div><div class="contentValue"><label><b> Rixford Brett </b></label></div></div>
            </div>
        </div>
    </div>--%>
    <div id="tabs-2">

        <div id="tableDiv">
            <br/>
            <br/>
            <div id="newRiskAssTrxDiv">
                <c:set var="count" value="0" scope="page" />
            <table id="summeryTableForNewRiskAssesment" class="tableNewRiskSummary">
                <tbody>
                <c:forEach items="${NewRiskAssessmentTxBean.proactiveTxList}" var="list" varStatus="loopStatus">
                    <c:set var="count" value="${count + 1}" scope="page"/>

                    <%--<tr id="<c:out value='${list.trxId}'/>">--%>
                    <tr id="${count}">
                        <td class="transactionId"><c:out value="${list.trxId}"/></td>
                        <td class="createdDate"><fmt:formatDate value="${list.trxDate}" pattern="${MMddYYYY}"/></td>
                        <td class="amount"><c:out value="${list.amount}"/></td>
                       <%-- <td class="regionName"><c:out value="${list.regionName}"/></td>--%>
                        <td class="documentCreator">John Smith</td>
                        <td class="approver">Alex Liu</td>
                        <td class="dateOfApproval"><fmt:formatDate value="${list.createdDate}" pattern="${MMddYYYY}"/></td>
                        <%--<td class="decission"><c:out value="${list.decision}"/></td>--%>
                        <td class="txId" style="display: none;"><c:out value="${list.id}"/></td>

                        <td class="serial" style="display: none;"><c:out value="${count}"/></td>

                        <c:choose>
                            <c:when test="${not empty list.project}">
                                <td class="projectType" style="display: none"><c:out value="${list.project}"/></td>
                                <td class="projectId" style="display: none"><c:out value="${list.projectId}"/></td>
                                <td class="transactionType" style="display: none"><c:out value="${list.transactionType}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td class="projectType" style="display: none"><c:out value="-"/></td>
                                <td class="projectId" style="display: none"><c:out value="0"/></td>
                                <td class="transactionType" style="display: none"><c:out value="0"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

           </div>
        </div>
    </div>

    <div id="tabs-1">

        <div class="tableContainer">
            <br/>

            </br>
            <div id="subTableDiv">
                <div>
                    <div>
                        <div>
                            <table id="summeryTable1" class="tablesorter">
                                <thead>
                                <tr>
                                    <th>Rule wise Transaction count</th>
                                    <th>Rule definition</th>
                                    <!-- (with extra rule) -->
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>12</td>
                                    <td>Transaction involving structured payments</td>
                                </tr>
                                <tr>
                                    <td>15</td>
                                    <td>Transaction on weekends or holidays</td>
                                </tr>
                                <tr>
                                    <td>10</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on "Do
                                        Not Use/Do Not Pay" or "Inactive" lists
                                    </td>
                                </tr>
                                <tr>
                                    <td>30</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on OFAC
                                        Specially Designated Nationals list
                                    </td>
                                </tr>
                                <tr>
                                    <td>13</td>
                                    <td>Transaction with entity (including narrative of transaction) appearing on
                                        Politically Exposed Persons list
                                    </td>
                                </tr>
                                <tr>

                                    <td>15</td>
                                    <td>Transaction with entity not appearing on "Vendor Master File"/"Employee Master
                                        File"/"Customer Master File"
                                    </td>
                                </tr>
                                <tr>
                                    <td>25</td>
                                    <td>Transaction without associated transaction narrative/description</td>
                                </tr>
                                <tr>
                                    <td>20</td>
                                    <td>Transactions with duplicate document numbers</td>
                                </tr>
                                <tr>
                                    <td>24</td>
                                    <td>Transaction amount equal to approver limit</td>
                                </tr>
                                <tr>
                                    <td>14</td>
                                    <td>Transaction with blank entity name</td>
                                </tr>
                                <tr>
                                    <td>10</td>
                                    <td>Transaction narrative responsive to keyword search</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- Left side Tabs Ends here -->

<div id="tabs1" style="float:left;">

</div>

<script defer="defer">
    var maxFileSize = ${maxFileUploadSize};
    var txId =  ${serialNoForTableRowSelection}; /*here txId is replaced by serial Don't Confused*/
    if(txId > ${count}){
        txId = 1; //set the serial first for selection table row
    }

    var tableRowNo = 'table #';
    var projectTrxId = $(tableRowNo+txId).children('.txId').text();
    $(tableRowNo+txId).parent().children().each(function(){
        $(tableRowNo+txId).children().addClass("redRow");

    });

</script>
</div>

</div><!-- content is closed -->

</body>
</html>