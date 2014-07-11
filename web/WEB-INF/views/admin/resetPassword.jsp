<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="resetPasswordDiv">
<div class="row-fluid">
    <div class="span6">

      <%--  <!-- ==================== NOTIFICATIONS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-info-sign"></i><h2>Reset your password</h2>
            <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF NOTIFICATIONS HEADLINE ==================== -->--%>

        <!-- ==================== NOTIFICATIONS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="alert">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <%--<h4>Warning!</h4>--%>
                    Enter a new password for amjadict07@gmail.com. We highly recommend you create a unique password - one that you don't use for any other websites.
                    Note: You can't reuse your old password once you change it.
                </div>

            </div>
        </div>
    </div>
    <div class="span6">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="reset.your.password"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="user">
                    <div class="control-group">
                        <label class="control-label" for="givenPassword"><spring:message code="reset.password.newpassword"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:password path="givenPassword"  class="span10 leftPaddingForText" id="givenPassword" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="givenPassword"/></li></ul>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="confirmPassword"><spring:message code="reset.password.confirmpassword" /> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:password path="confirmPassword" name="confirmPassword" id="confirmPassword" data-validation-minlength="0"  data-trigger="change" data-required="true" class="span10 leftPaddingForText"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"> <form:errors path="confirmPassword" cssStyle="color: red"/></li></ul>
                        </div>
                    </div>


                    <div class="formFooter">
                        <button type="submit" class="btn btn-primary"><spring:message code="reset.password.button.title"/></button>

                    </div>
                </form:form>
            </div>
        </div>
        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
    </div>
</div>
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>