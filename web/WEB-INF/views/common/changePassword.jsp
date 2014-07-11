<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="chagePassSpan">
    <div class="row-fluid">

            <div class="span6">
                <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                <div class="containerHeadline">
                    <i class="icon-ok-sign"></i><h2><spring:message code="changePassword.form.header"/></h2>
                    <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                </div>
                <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                <div class="floatingBox">
                    <div class="container-fluid">
                            <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="./changePassword.html" commandName="user">
                            <div class="control-group">
                                <label class="control-label" for="fullname"><spring:message code="user.form.name" /><span class="requiredField"> *</span></label>
                                <div class="controls">
                                    <form:input path="userName"  class="span10 leftPaddingForText" id="fullname" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="password"><spring:message code="user.form.password"/><span class="requiredField"> *</span></label>
                                <div class="controls">
                                    <form:password path="password" id="password" name="password" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" class="span10 leftPaddingForText"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="passwordConf"><spring:message code="user.form.confirmPassword" /><span class="requiredField"> *</span></label>
                                <div class="controls">
                                    <form:password path="confirmPassword" id="passwordConf" name="passwordConf" data-validation-minlength="0" data-trigger="change" data-required="true" data-type="alphanum" data-minlength="2" data-equalto="#password" class="span10 leftPaddingForText"/>
                                </div>
                            </div>
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
</div>
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
