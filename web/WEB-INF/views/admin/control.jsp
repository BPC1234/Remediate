<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="controlFormDiv">
<div class="row-fluid">

    <div class="span6">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="adminHeader.control"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="control">
                    <div class="control-group">
                        <label class="control-label" for="transactionType"><spring:message code="reactive.project.transaction.type" /><span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:select path="transactionType" name="items" id="transactionType">
                            <form:options items="${transactionType}" itemLabel="value" itemValue="value"/>
                            </form:select>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="transactionType"/></li></ul>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="controlName"><spring:message code="adminHeader.control.name" /> </label>
                        <div class="controls">
                            <form:textarea path="name" id="controlName" class="span10 leftPaddingForText" placeholder="Write a control..."></form:textarea>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="user.form.isActive" /> </label>
                        <div class="controls">
                            <form:checkbox path="active" />
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
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
</div>