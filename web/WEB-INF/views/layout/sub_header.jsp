<%@ page import="org.hibernate.Session" %>
<%@ page import="com.dsinv.abac.util.Utils" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%	final String contextPath = request.getContextPath();
    final String hiddenId = (String)request.getSession().getAttribute("hiddenId");
    final String fromDate = (String)request.getSession().getAttribute("fromDate");
    final String toDate = (String)request.getSession().getAttribute("toDate");
    final String transactionTypeForSubHeader = (String)request.getSession().getAttribute("transactionTypeForSubHeader");

%>
<div id="groupSelector" style="float:left; display: block;" >
    <div>
        <div style="float:left; font-size: 13px;padding-left: 20px;padding-top:2px;">
            <c:if test="${ not empty cName}"><spring:message code="subheader.country"/>: <b>${cName}</b></c:if>
             &nbsp;&nbsp;
                <c:if test="${ not empty transactionTypeForSubHeader}"><spring:message code="reactive.project.transaction.type"/>: <b>${transactionTypeForSubHeader} </b></c:if>
        </div>
        <div id="datePickerDiv" style="display:none; float:right">
            <b><spring:message code="newRiskAssessmentSummary.date"/>: </b><input type="text" id="weightedDatePicker" style="margin-bottom:0px !important;padding:0px !important"/>  <b><spring:message code="subheader.to"/></b> <input type="text" id="weightedDatePicker2"  style="margin-bottom:0px !important;padding:0px !important"/>
            <button id="searchGroup" class="button"><spring:message code="subheader.searchGroup"/></button>
        </div>
        <div style="float:right" id="specificDateSelector" >
            <input type="hidden" id ="hiddenId" name="hiddenId">
            <button id="1Y" class="button" style="font-size: 12px;" onclick="dateDeterminator('1Y')">1Y</button>
            <button id="2Y" class="button" style="font-size: 12px;" onclick="dateDeterminator('2Y')">2Y</button>
            <button id="3Y" class="button" style="font-size: 12px;" onclick="dateDeterminator('3Y')">3Y</button>
            <button id="4Y" class="button" style="font-size: 12px;" onclick="dateDeterminator('4Y')">4Y</button>
            <button id="dateBetween" class="button" style="font-size: 12px;" onclick="dateDeterminator('dateBetween')"><spring:message code="customize.date"/></button>
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
        <div class="buttonTab" style="text-align:center;float:right; display: none;" >
<%--            <table class="buttonGroupTable">
                <tr>
                    <td><input type="submit" id="cancelButton" name="Submit" class="subHeaderButton" value="Cancel"/></td>
                    <td><input type="button" id="subHeaderSaveButton" class="subHeaderButton" value="Save"/></td>
                    <td><input type="button" id="subHeaderPrintButton" class="subHeaderButton" value='<spring:message code="newRiskAssessmentSummary.print"/>' /></td>
                    <td><input type="button" id="email" class="subHeaderButton"  value='<spring:message code="newRiskAssessmentSummary.email"/> '/></td>
                </tr>
            </table>--%>
        </div>
    </div>
</div>