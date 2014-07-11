<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="row-fluid">

    <div class="span6">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="policy.procedure" /></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="policy" enctype="multipart/form-data">
                    <div class="control-group">
                        <label class="control-label" for="documentName"><spring:message code="document.name.label" /> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="documentName"  class="span10 leftPaddingForText" id="documentName" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="documentName"/></li></ul>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputFile"><spring:message code="file.upload.field"/></label>
                        <div class="controls">
                            <input type="file" id="inputFile" style="display: none"/>
                            <div class="dummyfile">
                                <form:input path="fileData" id="fileData" type="text" class="input disabled span10 leftPaddingForText" name="fileData" readonly="true"/>
                                <a id="fileselectbutton" class="btn"><spring:message code="browse"/></a>
                            </div>
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