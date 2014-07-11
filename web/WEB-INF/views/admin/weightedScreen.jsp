<%@page import="com.dsinv.abac.util.Utils" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    final String contextPath = request.getContextPath();
    final String hiddenId = (String)request.getSession().getAttribute("hiddenId");
    final String fromDate = (String)request.getSession().getAttribute("fromDate");
    final String toDate = (String)request.getSession().getAttribute("toDate");
    final String transactionTypeForSubHeader = (String)request.getSession().getAttribute("transactionTypeForSubHeader");

%>
<script src="<%=contextPath%>/resources/js/proactive-workflow.js"></script>

<title><spring:message code="realtime.monitoring.workflow.title"/></title>
<input type="hidden" id="cpiScore" class="<spring:message code="proactiveProject.cpiScore"/>" value="${proactiveBlockWeightRatio.cpiScore}"/>
<input type="hidden" id="revenues" class="<spring:message code="proactiveProject.revenues"/>" value="${proactiveBlockWeightRatio.revenues}"/>
<input type="hidden" id="salesModelRelationship" class="<spring:message code="proactiveProject.salesModelRelationships"/>" value="${proactiveBlockWeightRatio.salesModelRelationships}"/>
<input type="hidden" id="natureOfBusinessOperations" class="<spring:message code="proactiveProject.natureOfBusiness"/>" value="${proactiveBlockWeightRatio.natureOfBusinessOperations}"/>
<input type="hidden" id="governmentInteraction" class="<spring:message code="proactiveProject.governmentInteraction"/>" value="${proactiveBlockWeightRatio.governmentInteraction}"/>

<div id="realtimeSelectedWeekInfo">
    <%@ include file="/WEB-INF/views/message.jsp" %>
    <div class="row-fluid">
        <div class="span12">
            <!-- ==================== SPAN12 HEADLINE ==================== -->
            <div class="containerHeadline">
                <i class="icon-th"></i>
                <h2><spring:message code="weightedScreen.weightedScore"/></h2>

                <div class="pull-right">
                    <div id="groupSelector" >
                        <div>
                            <div style="float:left; font-size: 13px;padding-left: 20px;padding-top:2px;">
                                <c:if test="${ not empty cName}"><spring:message code="subheader.country"/>: <b>${cName}</b></c:if>
                                &nbsp;&nbsp;
                                <c:if test="${ not empty transactionTypeForSubHeader}"><spring:message code="reactive.project.transaction.type"/>: <b>${transactionTypeForSubHeader} </b></c:if>
                            </div>
                            <div id="datePickerDiv" style="display:none; float:right">
                                <b><spring:message code="newRiskAssessmentSummary.date"/>: </b>
                                <input type="text" id="weightedDatePicker" class=""/>  <b><spring:message code="subheader.to"/></b>
                                <input type="text" id="weightedDatePicker2" class=""/>
                                <button id="searchGroup" class="btn btn-mini btn-success"><spring:message code="subheader.searchGroup"/></button>
                            </div>
                            <div style="float:right" id="specificDateSelector" >
                                <input type="hidden" id ="hiddenId" name="hiddenId">
                                <button id="1Y" class="btn btn-mini btn-primary" onclick="dateDeterminator('1Y')">1Y</button>
                                <button id="2Y" class="btn btn-mini btn-success" onclick="dateDeterminator('2Y')">2Y</button>
                                <button id="3Y" class="btn btn-mini btn-warning" onclick="dateDeterminator('3Y')">3Y</button>
                                <button id="4Y" class="btn btn-mini btn-danger" style="font-size: 12px;" onclick="dateDeterminator('4Y')">4Y</button>
                                <button id="dateBetween" class="btn btn-mini btn-info" onclick="dateDeterminator('dateBetween')"><spring:message code="customize.date"/></button>
                                <c:if test="${ not empty hiddenId}">
                                    <c:if test="${hiddenId == '1Y'}">
                                        <script language="JavaScript">
                                            dateDeterminator('1Y')
                                        </script>
                                    </c:if>
                                    <c:if test="${hiddenId == '2Y'}">
                                        <script language="JavaScript">
                                            dateDeterminator('2Y')
                                        </script>
                                    </c:if>
                                    <c:if test="${hiddenId == '3Y'}">
                                        <script language="JavaScript">
                                            dateDeterminator('3Y')
                                        </script>
                                    </c:if>
                                    <c:if test="${hiddenId == '4Y'}">
                                        <script language="JavaScript">
                                            dateDeterminator('4Y')
                                        </script>
                                    </c:if>
                                    <c:if test="${hiddenId == 'dateBetween' || hiddenId == 'weightedDatePicker' || hiddenId == 'weightedDatePicker2'}">
                                        <script language="JavaScript">
                                            dateDeterminator('dateBetween');
                                            document.getElementById('datePickerDiv').style.display = "block";
                                            document.getElementById('specificDateSelector').style.display = "none";
                                        </script>
                                    </c:if>
                                </c:if>
                                <c:if test="${not empty fromDate}">
                                    <script language="JavaScript">
                                        $(document).ready(function(){
                                            document.getElementById('weightedDatePicker').value = '${fromDate}';
                                            document.getElementById('weightedDatePicker2').value = '${toDate}';
                                        });
                                    </script>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </div>
             </div>
                <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                <div class="floatingBox">
                    <div class="container-fluid">


                        <div class="demoPieChart" id="thirdPieChart"></div>
                        <div class="centerAlign weightedScoreButtonDiv">
                        <div class="weightedScoreButton">
                            <button type="button" class="btn btn-danger pull-right" id="weightedScoreButton"><i class="icon-external-link"></i>
                                &nbsp;&nbsp;<spring:message code="weightedScreen.weightedScore"/>  <%--<span class="label label-pressed">2</span> --%></button>

                        </div>
                        </div>
                    </div>
                </div>
                <!-- ================ END OF SPAN12 FLOATING BOX ==================== -->
            </div>


        </div>
    </div>