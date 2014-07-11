<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div id="realtimeinterval">
<div class="row-fluid">

    <div class="span6">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="realTimeMonitoringIntervalSetup.form.header"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="realTimeInterval"  enctype="multipart/form-data">
                    <div class="control-group">
                        <label class="control-label" for="executeTime"><spring:message code="realTimeMonitoringIntervalSetup.form.realTimeMonitoringInterval"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="executeTime" name="executeTime" id="executeTime" data-validation-minlength="0" data-type="number" data-trigger="change" data-required="true" class="span10"/>
                            <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                                <li class="equalto" style="display: list-item;"><form:errors path="executeTime"/></li></ul>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="assignmentSize"><spring:message code="realTimeMonitoringIntervalSetup.form.assignment.size"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="assignmentSize"  id="assignmentSize" data-trigger="change"  class="span10"/>
                            <p></p>
                            <form:errors path="assignmentSize"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="holidayDateFieldId"><spring:message code="realTimeMonitoringIntervalSetup.form.lastProjectCreation"/> <span class="requiredField"> *</span></label>
                        <div class="controls">
                            <form:input path="lastProjectCreated" name="holiday" id="holidayDateFieldId" data-trigger="change"  class="span10"/>
                            <p></p>
                            <form:errors path="lastProjectCreated"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <form:input path="importFile" type="file" id="inputFile" style="display: none"/>
                            <div class="dummyfile">
                                <input type="text" id="importFileId" name="importFileId" class="input disabled span8" readonly="true"/>
                                <button id="fileselectbutton" class="btn btn-warning" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                            </div>
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
<script type="text/javascript">
    setCustomFileAction("#importFileId");
</script>