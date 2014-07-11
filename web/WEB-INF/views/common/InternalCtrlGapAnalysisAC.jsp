<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/icga-workflow.js"></script>
<title><spring:message code="icga.analyzeByControl.title"/></title>

<div class="span12 row-fluid" id="existingControlMainTab">
    <!-- ==================== ACTIVITIES HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i><h2><spring:message code="icga.analyzeByControl.title"/></h2>
        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
        <div class="controlButton pull-right"></div>
    </div>
    <!-- ==================== END OF ACTIVITIES HEADLINE ==================== -->

    <!-- ==================== ACTIVITIES FLOATING BOX ==================== -->
    <div class="floatingBox">
        <div class="container-fluid">
            <!-- ==================== ACTIVITIES MENU ==================== -->
            <div class="floatingBoxMenu">
                <ul class="nav nav-tabs">
                    <li id="thirdPartyPaymentLi" class="active"><a href="#thirdPartyPaymentTab"><spring:message code="proactiveWorkflow.thirdPartyPayments"/></a></li>
                    <li id="generalLedgerLi" class=""><a href="#generalLedgerTab"><spring:message code="proactiveWorkflow.generalLedgerTransaction"/></a></li>
                    <li id="customerLi" class=""><a href="#customerTab"><spring:message code="proactiveWorkflow.customerTransaction"/></a></li>
                </ul>
            </div>
            <!-- ==================== END OF ACTIVITIES MENU ==================== -->
            <div class="infoDiv container-fluid">
                <!-- ====================third Party Payment Tab CONTENT ==================== -->
                <ul class="floatingBoxContainers" id="thirdPartyPaymentTab">
                    <li>
                        <p class="headLine">
                        <table id="thirdPartyTable" class="table table-hover">
                            <c:forEach items="${thirdPartyTransactionControlList}" var="control" varStatus="loopStatus">
                                <c:choose>
                                    <c:when test="${loopStatus.index%2 == 0}" >
                                        <tr class="success">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="info">
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <input type="checkbox" class="analyzedControlsCheckbox" name="<c:out value="${control.id}"/>"/>
                                </td>
                                <td>
                                    <label class="analyzedControlsLabel"><c:out value="${control.name}"/></label>
                                </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </p>
                    </li>
                </ul>
                <!-- ==================== END OF third Party Payment Tab CONTENT ==================== -->

                <!-- ====================general Ledger Tab CONTENT ==================== -->
                <ul class="floatingBoxContainers" id="generalLedgerTab">
                    <li>
                        <p class="headLine">
                        <table id="generalLedgerTable" class="table table-hover">
                            <c:forEach items="${generalLedgerTransactionControlList}" var="control" varStatus="loopStatus">
                                <c:choose>
                                    <c:when test="${loopStatus.index%2 == 0}" >
                                        <tr class="success">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="info">
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <input type="checkbox" class="analyzedControlsCheckbox" name="<c:out value="${control.id}"/>"/>
                                </td>
                                <td>
                                    <label class="analyzedControlsLabel"><c:out value="${control.name}"/></label>
                                </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </p>
                    </li>
                </ul>
                <!-- ==================== END OF general Ledger Tab CONTENT ==================== -->

                <!-- ====================customer Tab CONTENT ==================== -->
                <ul class="floatingBoxContainers" id="customerTab">
                    <li>
                        <p class="headLine">
                        <table id="customerTable" class="table table-hover">
                            <c:forEach items="${customerTransactionControlList}" var="control" varStatus="loopStatus">
                                <c:choose>
                                    <c:when test="${loopStatus.index%2 == 0}" >
                                        <tr class="success">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="info">
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <input type="checkbox" class="analyzedControlsCheckbox" name="<c:out value="${control.id}"/>"/>
                                </td>
                                <td>
                                    <label class="analyzedControlsLabel"><c:out value="${control.name}"/></label>
                                </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </p>
                    </li>
                </ul>
                <!-- ==================== END OF customer Tab CONTENT ==================== -->


            </div>
            </br>
            <div class="pull-right analyzeByControlSubmitDiv">
            <button type="button" id="analyzeByControlSubmitButton" class="btn btn-success"><i class="icon-ok"></i>&nbsp;&nbsp;<spring:message code="reactiveProject.button.submit"/></button>
            </div>
        </div>
    </div>
    <!-- ==================== END OF ACTIVITIES FLOATING BOX ==================== -->
</div>
