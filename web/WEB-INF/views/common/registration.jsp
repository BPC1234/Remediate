<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>

<div>
    <div class="row-fluid marginLeft">

        <div class="span6">
            <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
            <div class="containerHeadline">
                <i class="icon-ok-sign"></i><h2><spring:message code="registration.form.header"/></h2>
                <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
            </div>
            <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

            <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
            <div class="floatingBox">
                <div class="container-fluid">
                    <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" commandName="user">
                        <div class="control-group">
                            <label class="control-label" for="fullname"><spring:message code="user.form.name" /> <span class="requiredField"> *</span></label>
                            <div class="controls">
                                <form:input path="userName"  class="span10 leftPaddingForText" id="fullname" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                                <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                    <li class="equalto redText" style="display: list-item;"><form:errors path="userName"/></li></ul>


                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="password"><spring:message code="user.form.password" /> <span class="requiredField"> *</span></label>
                            <div class="controls">
                                <form:password path="password" id="password" name="password" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" class="span10 leftPaddingForText"/>
                                <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                    <li class="equalto redText" style="display: list-item;"><form:errors path="password"/></li></ul>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="passwordConf"><spring:message code="user.form.confirmPassword" /> <span class="requiredField"> *</span></label>
                            <div class="controls">
                                <form:password path="confirmPassword" id="passwordConf" name="passwordConf" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" data-equalto="#password" class="span10 leftPaddingForText"/>
                                <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                    <li class="equalto redText" style="display: list-item;"><form:errors path="password"/></li></ul>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="passwordConf"><spring:message code="user.form.email" /> <span class="requiredField"> *</span></label>
                            <div class="controls">
                                <form:input path="email"  id="email" name="email" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="email" class="span10 leftPaddingForText" data-minlength="3" />
                                <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                    <li class="equalto redText" style="display: list-item;"><form:errors path="email"/></li></ul>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="passwordConf"><spring:message code="user.form.personType" /> <span class="requiredField"> *</span></label>
                            <div class="controls">
                                <form:select path="userType" id="userRole" >
                                    <form:options items="${personTypes}"/>
                                </form:select>
                                <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                    <li class="equalto redText" style="display: list-item;"><form:errors path="userType"/></li></ul>
                            </div>
                        </div>
                        <%--<div class="control-group">
                            <label class="control-label" for="passwordConf"><spring:message code="user.form.isActive" /> </label>
                            <div class="controls">
                                <form:checkbox path="active" />
                            </div>
                        </div>--%>
                        <div class="formFooter">
                            <button type="submit" class="btn btn-primary"><spring:message code="save.button.title"/></button>
                            <button type="reset" class="btn"><spring:message code="login.button.reset"/></button>
                        </div>
                    </form:form>
                </div>
            </div>
            <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
        </div>
    </div>
    <!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>
<script>
    $("#userRole").children('option').eq(2).remove();
    $("#userRole").children('option').eq(2).remove();
    $("#userRole").children('option').eq(2).remove();

</script>
