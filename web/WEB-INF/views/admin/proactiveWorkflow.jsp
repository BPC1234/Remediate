<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/proactive-workflow.js"></script>


<title><spring:message code="proactiveWorkflow.pageHeader" /></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<div class="row-fluid">
    <div class="span12">
        <input type="hidden" id="cNameId" value="${cName}"/>
        <input type="hidden" id="wScoreId" value="${wScore}"/>
        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2><spring:message code="proactiveWorkflow.pageHeader"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid customflexigridcss">
                <p>
                <table id="proactiveBlockWeightTable">
                </table>
                </p>
                <div class="pull-right proactiveBlockWeightButtonDiv">
                    <button type="button" class="btn btn-success buttonUnderTable"><i class="icon-ok"></i>&nbsp;&nbsp;<spring:message code="proactiveWorkflow.customerTransaction"/></button>
                    <button type="button" class="btn btn-primary buttonUnderTable2"><i class="icon-ok"></i>&nbsp;&nbsp;<spring:message code="proactiveWorkflow.thirdPartyPayments"/></button>
                    <button type="button" class="btn btn-warning buttonUnderTable3"><i class="icon-ok"></i>&nbsp;&nbsp;<spring:message code="proactiveWorkflow.generalLedgerTransaction"/></button>

                </div>
            </div>

        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>
</div>

<%--

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <title><spring:message code="proactiveWorkflow.title" /></title>
</head>
<body>

<div class="tableContainer">
    <label><font style="font-size: 19px; line-height: 50px"><spring:message code="proactiveWorkflow.pageHeader" /></font></label>

    <div id="subTableDiv">
        <label><font style="font-size: 16px; ">${cName}</font></label>

        <br/><br/><br/>

        <div id="proactiveBlockWeightDiv">
            <center>
                <table id="workFlowTable" class="tableNewRisk">
                    <tbody>
                    <c:forEach items="${proactiveBlockWeightList}" var="weight" varStatus="loopStatus">
                        <tr>
                            <td><c:out value="${weight.date}"/></td>
                            <td><c:out value="${weight.cpiScore}"/>%</td>
                            <td><c:out value="${weight.revenues}"/>%</td>
                            <td>35</td>
                            <td>75</td>
                            <td>$500</td>
                            <td>50</td>
                            <td>12/12/2012</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </center>
        </div>
        <br/><br/>

        <div id="buttonDiv">
            <button class="buttonUnderTable myButton" ><spring:message code="proactiveWorkflow.customerTransaction" /> </button>
            <button class="buttonUnderTable2 myButton"><spring:message code="proactiveWorkflow.thirdPartyPayments" /></button>
            &nbsp;&nbsp;
            <button class="buttonUnderTable3 myButton "><spring:message code="proactiveWorkflow.generalLedgerTransaction" /></button>
        </div>
    </div>
</div>


<br/><br/><br/>

<div id="accordion" class="ruleHolder">
    <h3><spring:message code="proactiveWorkflow.temporaryRules" /></h3>

    <div>
        <label id="ruleLabel"> StartDate >= 11/11/2011 and EndDate <=11/11/2012
        </label>
        <input type="button" id="deleteRule" value="Delete Rule"/>
    </div>
    <h3><spring:message code="proactiveWorkflow.insertTemporaryRules" /></h3>

    <div>
        <div id="rule">

            <form id="form1">
                <div id="all">
                    <div id="msg" class="hide">&nbsp;</div>
                    <table id="table1">
                        <thead>
                        <tr>
                            <th><spring:message code="proactiveWorkflow.slNo" /></th>
                            <th><spring:message code="proactiveWorkflow.and/Or" /></th>
                            <th><spring:message code="proactiveWorkflow.field" /></th>
                            <th><spring:message code="proactiveWorkflow.operator" /></th>
                            <th><spring:message code="proactiveWorkflow.field" /></th>
                            <th><spring:message code="proactiveWorkflow.add" /></th>
                            <th><spring:message code="proactiveWorkflow.delete" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><span>1&nbsp;</span></td>

                            <td>
                                <select class="ruleSelector" name="andOr[]" style="display:none">
                                    <option><spring:message code="proactiveWorkflow.select" /></option>
                                    <option><spring:message code="proactiveWorkflow.and" /></option>
                                </select>

                            </td>
                            <td>
                                <select class="ruleSelector" name="field[]">
                                    <option><spring:message code="proactiveWorkflow.select" /></option>
                                    <option><spring:message code="proactiveWorkflow.startDate" /></option>
                                    <option><spring:message code="proactiveWorkflow.endDate" /></option>

                                </select>
                            <td>
                                <select class="ruleSelector" name="operator[]">
                                    <option><spring:message code="proactiveWorkflow.select" /></option>
                                    <option><spring:message code="proactiveWorkflow.lessThan" /></option>
                                    <option><spring:message code="proactiveWorkflow.greaterThan" /></option>
                                    <option><spring:message code="proactiveWorkflow.greaterThanOrEqual" /></option>
                                    <option><spring:message code="proactiveWorkflow.lessThanOrEqual" /></option>
                                    <option><spring:message code="proactiveWorkflow.equalsTo" /></option>
                                    <option><spring:message code="proactiveWorkflow.notEqual" /></option>
                                </select>
                            </td>
                            <td><input type="text" name="field" id="field"/></td>
                            <td><input type="button" value=" Add " name="add" id="add"/></td>
                            <td><input type="button" value=" Delete " name="delete" id="delete"/></td>

                        </tr>
                        </tbody>
                        <tfoot>

                        <tr>
                            <td></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td><input type="button" value=" Submit " id="submit" name="submit"/></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>

                        </tfoot>
                    </table>
                    <p>

                    <div id="disp">&nbsp;</div>
                    </p>
                </div>
            </form>
        </div>
    </div>

</div>

</body>
</html>--%>
