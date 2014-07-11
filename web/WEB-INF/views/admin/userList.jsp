<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>--%>

<%@ include file="/WEB-INF/views/message.jsp" %>


<%
    final String contextPath = request.getContextPath();

%>
<script src="<%= contextPath %>/resources/js/admin/settings.js"></script>


<title>User List</title>

<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="userListPageId"class="row-fluid">
    <div class="span12">
        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="userList.pageHeader"/></h2>
            <%-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>--%>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div id="realtimeworkflowtableDiv" class="customFlexigridCss">
                    <table id="userTableId">
                    </table>
                </div>
            </div>
        </div>

        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->
    </div>

    <a id="modalOpenLinkForUser" style="display: none;" href="#addNewUser" role="button" class="add" data-toggle="modal"></a>

    <div id="addNewUser" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>User Form</h3>
        </div>
        <div class="modal-body">
            <form:form  id="projectForm" cssClass="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="user" >
                <div class="control-group">
                    <label class="control-label" for="fullname"><spring:message code="user.form.name" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="userName"   cssClass="span10 leftPaddingForText" id="fullname"  data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4"/>
                        <label class="userName" style="font-weight: 500!important;color: red;display: none;" ></label>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="userName"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="password"><spring:message code="user.form.password" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:password path="givenPassword" id="password" name="password" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" class="span10 leftPaddingForText"/>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="password"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="passwordConf"><spring:message code="user.form.confirmPassword" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:password path="confirmPassword" id="passwordConf" name="passwordConf" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" data-equalto="#password" cssClass="span10 leftPaddingForText"/>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="password"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="passwordConf"><spring:message code="user.form.email" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:input path="email"  id="email" name="email" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="email" class="span10 leftPaddingForText" data-minlength="3" />
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="password"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="passwordConf"><spring:message code="employee.name" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <input id="jasonValue" type="hidden" class="span10" value='[<c:forEach items="${employeeList}" var="list" varStatus="loop">{"empId" : "${list.id}","empName" : "${list.empName}"}<c:if test="${!loop.last}">,</c:if></c:forEach>]'>
                        <input id="autoCompleteBoxForUser" type="text" class="span10 leftPaddingForText"  data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4"/>
                        <form:input path="userTypeId" type="hidden" class="span10 userTypeId" />
                        <label class="userTypeId" style="font-weight: 500!important;color: red;display: none;" ></label>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="userRole"><spring:message code="user.form.role" /> <span class="requiredField"> *</span></label>
                    <div class="controls userRoleCss ">
                        <form:select path="role" id="userRole" >
                            <form:options items="${roles}" itemValue="label" itemLabel="value" />
                        </form:select>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="role"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="activeUser"><spring:message code="user.form.isActive" /> </label>
                    <div class="controls">
                        <form:checkbox path="active" id="activeUser"/>
                    </div>
                </div>

            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button id="saveUser" class="btn btn-primary">Save changes</button>
        </div>
    </div>
</div>
