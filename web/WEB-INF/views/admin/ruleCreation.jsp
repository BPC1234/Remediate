<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/rule.js"></script>

<title><spring:message code="mainTab.ruleCreation.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="mainTab.transactionSearch.title"/></h2>

            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">

                <div id="transactionSearchBlockId" class="centerAlign">
                    <div class="span6">
                        <input type="hidden" id="ledgerCount" value="${fn:length(lookupList)}">
                        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                        <div class="containerHeadline">
                            <i class="icon-ok-sign"></i><h2>Select Statement</h2>
                            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                        <div class="floatingBox">
                            <div class="container-fluid">
                                <p class="headLine">
                                <table id="selectClauseTable" class="table table-hover">
                                    <c:forEach items="${lookupList}" var="list" varStatus="loopStatus">
                                        <c:choose>
                                            <c:when test="${loopStatus.index%2 == 0}" >
                                                <tr class="success">
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="info">
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                        <c:choose>
                                            <c:when test="${list.selectTableName == true}" >
                                                <input type="checkbox" class="selectClauseCheckbox" checked id="checkbox_${loopStatus.index}" name="<c:out value="${list.ledgerName}"/>" />
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" class="selectClauseCheckbox" id="checkbox_${loopStatus.index}" name="<c:out value="${list.ledgerName}"/>"/>
                                            </c:otherwise>
                                        </c:choose>
                                        </td>
                                        <td>
                                            <label class="selectClauseLabel"><c:out value="${list.ledger}"/></label>
                                        </td>
                                        </tr>

                                    </c:forEach>
                                    <tr>
                                     <td colspan="2" align="right" valign="bottom" class="zeroPadding selectColumnSubmitButtonTd">
                                       <button id="selectClauseSubmitButton" type="button" class="btn btn-mini btn-primary"><i class="icon-search"></i>&nbsp;&nbsp;&nbsp;<spring:message code="reactiveProject.button.submit"/></button>
                                     </td>
                                    </tr>
                                </table>
                                </p>
                            </div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                    </div>
                </div>


                <div id="ruleDefinitionDivId" class="span12 leftMarginZero">
                </div>
                <div id="ruleDefinitionWhereDivId" class="span12 leftMarginZero">
                </div>

            </div>
        </div>
    </div>
    <div id="saveRuleDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3><spring:message code="adminHeader.rule"/></h3>
        </div>
        <div class="modal-body">
            <form:form  id="ruleFormId" class="form-horizontal" data-validate="parsley" method="post" commandName="rule">
                <div class="control-group">
                    <label class="control-label" for="ruleTitle"><spring:message code="rule.title" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="ruleTitle"  class="span10 leftPaddingForText" id="ruleTitle" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="ruleTitle"/></li></ul>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="selectClause"><spring:message code="rule.selectClause" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:textarea path="selectClause"  class="span10" id="selectClause" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="selectClause"/></li></ul>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="fromClause"><spring:message code="rule.fromClause" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:textarea path="fromClause"  id="fromClause" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="fromClause"/></li></ul>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="whereClause"><spring:message code="rule.whereClause" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:textarea path="whereClause"  id="whereClause" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="whereClause"/></li></ul>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" ><spring:message code="user.form.isActive" /> </label>
                    <div class="controls">
                        <form:checkbox path="active" id="activeRole"/>
                    </div>
                </div>


            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="close"/></button>
            <button id="saveRule" class="btn btn-primary"><spring:message code="save.button.title"/></button>
        </div>
    </div>
</div>
<%--
<input type="hidden" id="jsonListId" value="${list}">--%>
<script>
    var jSonObject = ${list};
</script>