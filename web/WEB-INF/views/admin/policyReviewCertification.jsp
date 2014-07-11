<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/jQuery.print.js"></script>
<script src="<%= contextPath %>/resources/js/policy-review-certification.js"></script>
<title><spring:message code="menu.reporting.policyReviewCertification"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<div id="policyListPage" class="row-fluid">
    <img id="loading" src="<%=contextPath%>/resources/images/LoadingWheel.gif" style="display:none;"/>
    <div class="span12 zeroMarging">
        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2></h2>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">

                <div id="transactionSearchBlockId" class="centerAlign">
                        <div class="pieChartWrapperDiv"  id="pieChartWrapperDiv">
                                <div class="pieChartLeftPartDiv">
                                     <table>
                                         <tr>
                                             <td align="right"><spring:message code="policy.name"/> :</td>
                                             <td align="left"></td>
                                         </tr>
                                         <tr>
                                             <td align="right"><spring:message code="policy.type"/> :</td>
                                             <td align="left"></td>
                                         </tr>
                                         <tr>
                                             <td align="right"><spring:message code="document.uploaded"/> :</td>
                                             <td align="left"></td>
                                         </tr>
                                         <tr>
                                             <td align="right"><spring:message code="document.uploaded.date"/> Date :</td>
                                             <td align="left"></td>
                                         </tr>
                                     </table>
                                    <button id="printPieChartButton" type="button" class="btn btn-small btn-success"><i class="icon-print"></i> &nbsp;&nbsp;Print</button>
                                 </div>
                                <div class="demoPieChart" id="policyPieChart" style="float: left;"></div>
                                <div class="demoPieChart" id="policyPieChartForVendor" style="float: left;"></div>
                            <div style="clear: both;"></div>
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
                            <table id="policyReviewTable">
                            </table>
                        </div>
                    </div>
                    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

                </div>
            </div>
        </div>
    </div>
  </div>
 </div>