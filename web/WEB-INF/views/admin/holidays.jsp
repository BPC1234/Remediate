<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/holidays.js"></script>

<title><spring:message code="adminHeader.holiday.list"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="holidayMainDiv" class="row-fluid">
    <div class="span12">
        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="adminHeader.holiday.list"/></h2>
            <%-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>--%>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="customFlexigridCss">
                    <table id="holidayTable">
                    </table>
                </div>
            </div>
        </div>

        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->
    </div>
    <a id="modalOpenLink" style="display: none;" href="#addHoliday" role="button" class="add" data-toggle="modal"></a>

    <div id="addHoliday" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Holiday Form</h3>
        </div>
        <div class="modal-body">
            <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="holiday">
                <div class="control-group">
                    <label class="control-label" for="discriptionId"><spring:message code="subheader.country"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:select path="region.id" name="items" id="discriptionId" disabled="true">
                            <form:options items="${regionList}" itemValue="id" itemLabel="name" />
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="holidaydescription"><spring:message code="adminHeader.holiday.rule.weekend"/> <span class="requiredField"> *</span></label>
                    <div class="controls" id="weekendId">
                        <form:select path="region.weekend.dayOne" name="daySelectOne" id="dayOneId" >
                            <form:options items="${dayNames}"/>
                        </form:select>
                        <form:select path="region.weekend.dayTwo" name="daySelectTwo" id="dayTwoId">
                            <form:options items="${dayNames}"/>
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="holidaydescription"><spring:message code="adminHeader.holiday.rule.description"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="description" name="holidaydescription"  data-validation-minlength="4" data-trigger="change" data-required="true" class="span10 leftPaddingForText" data-minlength="4"/>
                        <form:errors path="description"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="holiday"><spring:message code="newRiskAssessmentSummary.date"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <%--<form:input path="holidayDate" name="holiday" id="holidayDateFieldId" data-validation-minlength="0" data-type="date" data-trigger="change" data-required="true" class="span10"/>--%>
                        <form:input path="holidayDate" name="holiday" id="holidayDateFieldId" type="text" placeholder="MM/DD/YYYY" class="span10 parsley-validated leftPaddingForText" data-required="true" data-trigger="change" data-type="dateIso"/>
                        <p></p>
                        <form:errors path="holidayDate"/>
                    </div>
                </div>
                <div class="formFooter">
                    <button id="saveHoliday" type="button" class="btn btn-primary"><spring:message code="save.button.title"/></button>
                    <button type="reset" class="btn"><spring:message code="login.button.reset"/></button>
                </div>
            </form:form>
        </div>
    </div>
</div>