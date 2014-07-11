<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/training-and-certification.js"></script>
<title><spring:message code="mainTab.training.and.certification.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="labelOneUser" value="<%=Role.EMPLOYEE.getLabel()%>"/>

<div id="policyListPage" class="row-fluid">
    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i><h2><spring:message code="training.and.certification"/></h2>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">
                    <div class="container-fluid">
                        <div class="centerAlign">
                            <div class="searchParameterDiv">
                                <form:form id="trxSearchForm" commandName="training" method="post" data-validate="parsley">
                                    <table id="trxSearch">
                                        <tr>
                                            <td class="trxSearchFirstTd" align="right"><spring:message
                                                    code="document.name.label"/> :
                                            </td>
                                            <td class="trxSearchSecondTd"><form:input path="documentName"
                                                                                      id="docNameFortraining" type="text"
                                                                                      data-trigger="change"
                                                                                      class="span12 leftPaddingForText"/></td>
                                            <form:errors path="documentName"/>

                                            <td align="right"><spring:message
                                                    code="document.uploaded.by"/> :
                                            </td>
                                            <td class="trxSearchSecondTd"><form:input path="author"
                                                                                      id="authorFortraining"
                                                                                      class="span12 leftPaddingForText" type="text"/></td>
                                        </tr>
                                        <tr>
                                            <td align="right"><spring:message code="newRiskAssessmentSummary.date"/> : </td>
                                            <td class="trxSearchSecondTd"><form:input path="entryTime"
                                                                                      id="entryTimeFortraining"
                                                                                      class="span12 leftPaddingForText" type="text"/>
                                                <form:errors path="entryTime"/>
                                            </td>

                                            <td align="right"><spring:message code="training.type"/>: </td>
                                            <td class="trxSearchSecondTd">
                                                <div class="styled-select">
                                                    <form:select path="trainingType" id="trainingTypeForTraining">
                                                        <form:option value="" label="Select training type"/>
                                                        <form:options items="${trainingType}" itemLabel="value"
                                                                      itemValue="value"/>
                                                    </form:select>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" align="right" valign="bottom">
                                                <button id="trainingSearchSubmitButton" type="button"
                                                        class="btn btn-primary"><spring:message code="search"/></button>
                                            </td>
                                        </tr>
                                    </table>
                                </form:form>
                            </div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                    </div>

                <div class="span12 leftMarginZero">

                    <!-- ==================== SPAN12 HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-th"></i><h2><spring:message code="training.and.certification"/></h2>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
                    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid customflexigridcss trainingList">
                            <table id="trainingListTable">
                            </table>
                        </div>
                    </div>
                    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->


                </div>
            </div>




<a id="modalOpenLink" style="display: none;" href="#uploadNewPolicy" role="button" class="add" data-toggle="modal"></a>
<div id="uploadNewPolicy" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <img id="loadingImage" class="middlePosition" src="<%=contextPath%>/resources/images/image2.gif" style="display: none; z-index:2;"/>
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3>Training Upload</h3>
    </div>
    <div class="modal-body trainingAdd">
        <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="training" enctype="multipart/form-data">
            <div class="control-group">
                <label class="control-label" for="documentName"><spring:message code="training.name.label"/> <span class="requiredField"> *</span></label>
                <div class="controls">
                    <form:input path="documentName"  class="span8 leftPaddingForText" id="documentName" data-validation-minlength="0" data-trigger="change" data-required="true" data-minlength="4" />
                    <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                        <li class="equalto" style="display: list-item;"><form:errors path="documentName"/></li></ul>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputFile"><spring:message code="file.upload.field"/></label>
                <div class="controls">
                    <input type="file" id="inputFile" style="display: none"/>
                    <div class="dummyfile">
                        <form:input path="fileData" id="fileData" type="text" class="input disabled span8 leftPaddingForText" name="fileData" readonly="true"/>
                        <a id="fileselectbutton" class="btn"><spring:message code="browse"/></a>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="implementedDate"><spring:message code="policy.form.implementedDate"/></label>
                <div class="controls">
                    <form:input path="implementedDate"  class="span8 leftPaddingForText" id="implementedDate" data-required="true" data-trigger="change" data-type="dateIso" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="trainingType"><spring:message code="training.type" /> <span class="requiredField"> *</span></label>
                <div class="controls">
                    <form:select path="trainingType" id="trainingType" >
                        <form:options items="${trainingType}" itemValue="value" itemLabel="value"/>
                    </form:select>
                </div>
            </div>
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
        <button id="uploadTraining" class="btn btn-primary">Upload</button>
    </div>
</div>
</div>
<script>
    setCustomFileAction("#fileData");
    setTrainingUploadAction();
    setTrainingDeleteAction();
    getTrainingQuestion();
    var user =${user};
    startTest();
    downloadCertificate();
</script>