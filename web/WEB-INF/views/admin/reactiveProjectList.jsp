<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/reactive-workflow.js"></script>
<title></title>

<%@ include file="/WEB-INF/views/message.jsp" %>


<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2><spring:message code="reactiveProjectList.title"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid customflexigridcss" >

                <table id="reactiveProjectListAdminTable">
                </table>

            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>


    <div id="addReactiveProject" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Reactive Project</h3>
        </div>
        <div class="modal-body">
            <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="reactiveProject">
                <div class="control-group">
                    <label class="control-label" for="projectName"><spring:message code="reactiveProject.projectName"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="projectName"  class="span10 leftPaddingForText" id="projectName" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="projectName"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="items"><spring:message code="reactiveProject.region" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:select path="region.id" name="items" id="items">
                            <form:options items="${regionList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="region.id"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="amount"><spring:message code="reactiveProject.totalAmount"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="amount" name="amount" id="amount" data-validation-minlength="0" data-type="number" data-trigger="change" data-required="true" class="span10 leftPaddingForText"/>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="amount"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="datepickerField"><spring:message code="reactiveProject.paymentDate"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="paymentDate" type="text" name="datepickerField" id="datepickerField" data-type="dateIso" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="MM/DD/YYYY"/>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="paymentDate"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="transactionType"><spring:message code="reactiveProject.transactionType"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:select path="transactionType" cssClass="items"  class="transactionType" id="transactionType">
                            <form:options items="${transactionTypeList}" itemValue="value" itemLabel="value"/>
                        </form:select>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="transactionType"/></li></ul>
                    </div>
                </div>

            </form:form>

        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button id="saveReactiveProject" class="btn btn-primary">Save changes</button>
        </div>
    </div>

</div>