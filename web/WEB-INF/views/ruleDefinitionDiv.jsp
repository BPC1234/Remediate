<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>

<div id="ruleDefinitionDivId" class="span12 leftMarginZero">

    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i>

        <h2>Select</h2>

        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">
        <div class="container-fluid">
                <div id="tableSelectionDiv" class="table">
                    <div>
                        <table id="ruleDefinitionTable" >
                            <thead>
                            <tr>
                                <th><spring:message code="tableName"/></th>
                                <th><spring:message code="columnName"/></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                       <c:forEach items="${ruleList}" var="rule" varStatus="firstLoopStatus">
                        <c:choose>
                        <c:when test="${firstLoopStatus.index%2 == 0}" >
                            <tr class="success">
                        </c:when>
                        <c:otherwise>
                            <tr class="error">
                        </c:otherwise>
                        </c:choose>
                    <td>
                        <label class="selectClauseLabel"><c:out value="${rule.tableName}"/></label>

                    </td>
                    <td>
                        <input type="hidden" value="${fn:length(rule.columnNameList)}">
                        <select id="selectColumnId${firstLoopStatus.index}" name="selectColumnId${firstLoopStatus.index}" class="checkOpt">
                            <c:forEach items="${rule.columnNameList}" var="list" varStatus="loopStatus">
                                     <option id="${firstLoopStatus.index}${loopStatus.index}"  class="optionClass" value="${firstLoopStatus.index}${loopStatus.index}">${list.field}</option>
                            </c:forEach>
                        </select>

                    </td>
                    <td id="selectColumnNameTd"><input type="submit" class="selectColumnName" value=" " name="submit" />
                    </td>
                    </tr>

                </c:forEach>
                      <%-- <tr>
                           <td class="zeroPadding selectColumnSubmitButtonTd" colspan="3">
                               <button class="btn btn-mini btn-warning" type="button" id="selectColumnSubmitButton">Select</button>
                           </td>
                       </tr>--%>
                 </tbody>
            </table>
                    </div>
                </div>
               <div id="selectedColumnDiv" class="table">
                <div class="">
                    <table id="selectedColumnTable" style="display:none;">
                        <thead>
                        <tr>
                            <th><spring:message code="tableName"/></th>
                            <th><spring:message code="columnName"/></th>
                            <th><spring:message code="aggregateFunction"/></th>
                            <th><spring:message code="groupByClause"/></th>
                            <th><spring:message code="havingClause"/></th>
                            <th><spring:message code="orderbyClause"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr>
                        <td colspan="3" class="zeroPadding selectColumnSubmitButtonTd">
                            <button id="selectColumnSubmitButton" type="button" class="btn btn-mini btn-warning">Select</button>
                        </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>


        </div>
    </div>
    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->
    <div id="ruleDefinitionWhereClauseDiv" class="span12 leftMarginZero" style="display: none;">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="whereClause"/></h2>

            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="table">
                    <div>
                        <table class="whereClauseSelectionTable">
                            <thead>
                            <tr>
                                <th><spring:message code="tableName"/></th>
                                <th></th>
                             </tr>
                            </thead>
                            <tbody>
                            <tr class="signSelectTr"></tr>
                            <tr class="columnSelectorTr"></tr>
                            <tr class="conditionSelectTr"></tr>
                            <tr class="freeTextTr"></tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="table">
                    <div>
                        <table class="addConditionTable">
                            <thead>
                            <tr class="error">
                            <td><spring:message code="add.condition"/></td><td><input type="submit" id="addCondition" value=" " name="submit"></td>
                            <td class="query"></td>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div>
                    <div>
                        <table class="whereClauseMakerTable">
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="3" class="zeroPadding selectColumnSubmitButtonTd">
                                    <a data-toggle="modal" class="btn btn-small btn-success ruleCreationModalCall" style="display: none;" role="button" href="#saveRuleDiv">Add</a>
                                    <button id="whereClauseSubmitButton" type="button" class="btn btn-mini btn-success"><spring:message code="save.button.title"/></button>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>
   <%-- <div id="ruleDefinitionGroupByClauseDiv" class="span12 leftMarginZero" style="display: none;">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="groupByClause"/></h2>

            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="table">
                    <div>
                        <table class="whereClauseSelectionTable">
                            <thead>
                            <tr>
                                <th><spring:message code="tableName"/></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="signSelectTr"></tr>
                            <tr class="columnSelectorTr"></tr>
                            <tr class="conditionSelectTr"></tr>
                            <tr class="freeTextTr"></tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="table">
                    <div>
                        <table class="addConditionTable">
                            <thead>
                            <tr class="error">
                                <td><spring:message code="add.condition"/></td><td><input type="submit" id="addCondition" value=" " name="submit"></td>
                                <td class="query"></td>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div>
                    <div>
                        <table class="whereClauseMakerTable">
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="3" class="zeroPadding selectColumnSubmitButtonTd">
                                    <a data-toggle="modal" class="btn btn-small btn-success ruleCreationModalCall" style="display: none;" role="button" href="#saveRuleDiv">Add</a>
                                    <button id="querySubmitButton" type="button" class="btn btn-small btn-success"><spring:message code="save.button.title"/></button>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>--%>

</div>

