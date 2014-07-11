<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/admin/admin.js"></script>
<title><spring:message code="policy.procedure"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="labelOneUser" value="<%=Role.EMPLOYEE.getLabel()%>"/>

<div id="policyListPage" class="row-fluid">
    <a id="policyReminderHref" style="display: none;" href="#policyReminder" role="button" class="add" data-toggle="modal"></a>
     <div class="span12 zeroMarging">
         <!-- ==================== SPAN12 HEADLINE ==================== -->
         <div class="containerHeadline">
             <i class="icon-th"></i><h2><spring:message code="policy.procedure"/></h2>
             <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
         </div>
         <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

         <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                          <div class="centerAlign">
                               <div class="searchParameterDiv">
                                <form:form id="policyForm" commandName="policy" method="post" data-validate="parsley">
                                    <table id="trxSearch">
                                        <tr>
                                            <td class="trxSearchFirstTd" align="right"><spring:message
                                                    code="document.name.label"/> :
                                            </td>
                                            <td class="trxSearchSecondTd"><form:input path="documentName"
                                                                                      id="docName" type="text"
                                                                                      data-trigger="change"
                                                                                      class="span12 leftPaddingForText"/></td>
                                            <form:errors path="documentName"/>
                                        <%--</tr>
                                        <tr>--%>
                                            <td align="right"><spring:message
                                                    code="document.uploaded.by"/> :
                                            </td>
                                            <td class="trxSearchSecondTd"><form:input path="author"
                                                                                      id="author"
                                                                                      class="span12 leftPaddingForText" type="text"/></td>
                                        </tr>
                                        <tr>
                                            <td align="right"><spring:message code="newRiskAssessmentSummary.date"/> : </td>
                                            <td class="trxSearchSecondTd"><form:input path="entryTime"
                                                                                      id="entryTime"
                                                                                      class="span12 leftPaddingForText" type="text"/>
                                                <form:errors path="entryTime"/>
                                            </td>
                                       <%-- </tr>
                                        <tr>--%>
                                            <td align="right"><spring:message code="policy.type"/>: </td>
                                            <td class="trxSearchSecondTd">
                                                <div class="styled-select">
                                                    <form:select path="policyType" id="pType">
                                                        <form:option value="" label="Select Policy type"/>
                                                        <form:options items="${policyList}" itemLabel="value"
                                                                      itemValue="value"/>
                                                    </form:select>
                                                </div>
                                             </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" align="right" valign="bottom">
                                                <button id="policySearchSubmitButton" type="button"
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
                        <i class="icon-th"></i><h2><spring:message code="policy.procedure"/></h2>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
                    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid customflexigridcss .topCss">
                            <table id="policyListTable">
                            </table>
                        </div>
                    </div>
                    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

                </div>
                </div>
            </div>


    <a id="modalOpenLink" style="display: none;" href="#uploadNewPolicy" role="button" class="add" data-toggle="modal"></a>
    <div id="uploadNewPolicy" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <img id="loadingImage" class="middlePosition" src="<%=contextPath%>/resources/images/image2.gif" style="display: none; z-index:2;"/>
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Policy Upload</h3>
        </div>
        <div class="modal-body plicyUpload">
            <form:form  id="projectForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="policy" enctype="multipart/form-data">
                <div class="control-group">
                    <label class="control-label" for="documentName"><spring:message code="document.name.label" /> <span class="requiredField"> *</span></label>
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
                            <form:input path="fileData" id="fileData" type="text" class="input disabled span9 leftPaddingForText" name="fileData" readonly="true"/>
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
                    <label class="control-label" for="policyType"><spring:message code="policy.type" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:select path="policyType" id="policyType" >
                            <form:options items="${policyList}" itemValue="value" itemLabel="value"/>
                        </form:select>
                    </div>
                </div>
             <div class="control-group">
                    <label class="control-label" for="audiance"><spring:message code="policy.form.Audiance" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <select id="audiance" >
                            <option value="0" >ALL</option>
                            <option value="1" >Employee</option>
                            <option value="2" >Vendor</option>
                        </select>
                    </div>
                </div>
            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button id="uploadPolicy" class="btn btn-primary">Upload</button>
        </div>
    </div>

    <a id="modalOpenForPolicyConfirmation" style="display: none;" href="#policyConfirmationModalDiv" role="button" class="add" data-toggle="modal"></a>
    <div id="policyConfirmationModalDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <img id="loadingImageForPolicyConfirm" class="middlePosition" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display: none; z-index:2;"/>
      <input type="hidden" id="policyIdStr" value=""/>
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Policy Confirmation</h3>
        </div>
        <div class="modal-body">
            <form:form class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="policy" enctype="multipart/form-data">
                <h4><spring:message code="terms.and.conditions"/></h4>
                <table>
                <tr>
                <td colspan="2">
                      <dl>
                          <dd>I acknowledge that I have received the XYZ Code of Conduct and understand that I am obligated to read the Code and to comply with the principles, policies and laws outlined in the Code, including any amendments made by XYZ Company. </dd>
                          </br>
                          <dd>I understand that a current copy of the Code of Conduct is posted on XYZ’s website.</dd>
                          </br>
                          <dd>I understand that my agreement to comply with the XYZ Code of Conduct neither constitutes nor should be construed to constitute either a contract of employment for a definite term or a guarantee of continued employment.</dd>
                      </dl>
                </td>
                </tr>
                </table>
            </form:form>
        </div>
        <div class="modal-body policyConfirmation">
            <input id="confirmPolicyCheckbox" class="css-checkbox" type="checkbox">
            <label class="css-label" for="confirmPolicyCheckbox"> I understand with the above terms and conditions.</label>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button  style="display: none;" id="policyConfirmButton" class="btn btn-primary">Confirm</button>
        </div>
    </div>


<div id="policyReminder" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3>A new policy requires your attention.</h3>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="alert alert-success"  style="margin-bottom: 7px;">
                <strong>Total number of outstanding policies : ${outstandingPolicies}</strong>
            </div>
            <div class="alert alert-error"  style="margin-bottom: 7px;">
                <strong>Total number of signed policies : ${signedPolicies}</strong>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Not Now</button>
        </div>
    </div>
</div>

 </div>
 <script>
 setCustomFileAction("#fileData");
 setPostAction();
 setDeleteAction();
 setPolicyViewAction();
 var user =${user};
 </script>

 <script>
 $(document).ready(function(){
     var count = ${fn:length(unreadedPolicyList)};
    // alert(count)
     if(count > 0 )
         $("#policyReminderHref").click();

 });
 </script>
