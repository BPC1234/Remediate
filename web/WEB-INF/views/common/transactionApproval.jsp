<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/real-time-monitoring-workflow.js"></script>

<title></title>

<%@ include file="/WEB-INF/views/message.jsp" %>


<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="subTab.trxApproval.title"/></h2>

            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">

                <div id="searchBlockId">
                    <div class="span6">
                        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                        <div class="containerHeadline">
                            <i class="icon-ok-sign"></i><h2>Search Parameter</h2>
                            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                        <div class="floatingBox">
                            <div class="container-fluid">
                                <%--<div class="supportingDocumentFileUploadDiv">--%>
                                <form:form id="trxApprovalForm" commandName="transactionApproval" method="post"
                                           enctype="multipart/form-data">
                                    <table id="trxApprovalEntryTable">
                                        <tr>
                                            <td align="right">Document Name :</td>
                                            <td><form:input path="fileName" type="text" class="span12"/></td>
                                        </tr>
                                        <tr>
                                            <td align="right">Comment :</td>
                                            <td><form:textarea path="comment" class="span12"
                                                               placeholder="Write a comment..."/></td>
                                        </tr>
                                        <tr>
                                            <td align="right">File :</td>
                                            <td>
                                                <div class="controls">
                                                    <form:input path="fileData" type="file" id="inputFile"
                                                                style="display: none"/>
                                                    <div class="dummyfile">
                                                        <input type="text" id="fileData" name="fileData"
                                                               class="input disabled span9 trxApprFileWidth"
                                                               readonly="true"/>
                                                        <button id="fileselectbutton" class="btn btn-warning pull-right"
                                                                type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message
                                                                code="browse"/></button>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="right">
                                                <button type="button" class="btn btn-success trxAppSaveBtn"><i
                                                        class="icon-ok"></i>&nbsp;&nbsp;<spring:message
                                                        code="save.button.title"/></button>
                                            </td>
                                        </tr>
                                    </table>
                                </form:form>
                                <%--</div>--%>
                            </div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                    </div>
                </div>


                <div class="span12 leftMarginZero">

                    <!-- ==================== SPAN12 HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-th"></i>

                        <h2><spring:message code="landingPage.transactionApprove.list"/></h2>

                        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

                    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid customFlexigridCss">
                            <p>
                            <table id="transactionApprtovalListTable">
                            </table>
                            </p>
                        </div>
                    </div>
                    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

                </div>
            </div>
        </div>
    </div>

</div>

<!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

<script type="text/javascript">
    setCustomFileAction("#fileData");
    var user =${user};
</script>