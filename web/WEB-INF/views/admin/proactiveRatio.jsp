<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="formFluid">
<div class="row-fluid">

    <div class="span6">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="proactiveProject.ratio.setup.title"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="proactiveRatio.html" commandName="proactiveBlockWeightRatio">
                      <div class="control-group">
                        <label class="control-label" for="maxVal"><spring:message code="proactiveProject.cpiScore"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="cpiScore" name="maxVal" id="maxVal" data-validation-minlength="0" data-type="number" data-max="100" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="max = 100"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="cpiScore"/></li></ul>
                        </div>
                       </div>
                    <div class="control-group">
                        <label class="control-label" for="maxVal"><spring:message code="proactiveProject.revenues"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="revenues" name="maxVal" id="maxVal" data-validation-minlength="0" data-type="number" data-max="100" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="max = 100"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="revenues"/></li></ul>
                        </div>
                       </div>
                     <div class="control-group">
                        <label class="control-label" for="maxVal"><spring:message code="proactiveProject.salesModelRelationships"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="salesModelRelationships" name="maxVal" id="maxVal" data-validation-minlength="0" data-type="number" data-max="100" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="max = 100"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="salesModelRelationships"/></li></ul>
                        </div>
                       </div>
                    <div class="control-group">
                        <label class="control-label" for="maxVal"><spring:message code="proactiveProject.governmentInteraction"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="governmentInteraction" name="maxVal" id="maxVal" data-validation-minlength="0" data-type="number" data-max="100" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="max = 100"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="governmentInteraction"/></li></ul>
                        </div>
                       </div>
                    <div class="control-group">
                        <label class="control-label" for="maxVal"><spring:message code="proactiveProject.natureOfBusiness"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="natureOfBusinessOperations" name="maxVal" id="maxVal" data-validation-minlength="0" data-type="number" data-max="100" data-trigger="change" data-required="true" class="span10 leftPaddingForText" placeholder="max = 100"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="natureOfBusinessOperations"/></li></ul>
                        </div>
                       </div>
                    <div class="formFooter">
                        <button type="submit" class="btn btn-primary"><spring:message code="button.update"/></button>
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