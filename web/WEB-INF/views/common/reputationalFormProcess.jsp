<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%=contextPath%>/resources/js/due-dilligance-landing.js"></script>
<script src="<%=contextPath%>/resources/js/reputational-form-process.js"></script>

<style>
    input[type="button"].reputationalReview {
        background-color: #5CADFF;
        border-radius: 4px 4px 4px 4px;
        padding: 3px;
    }

    input[type="button"]:HOVER.reputationalReview {
        background-color: #0000FF;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    }
</style>

<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="dueDilligance.new.reputational.review"/></h2>

            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <p>

                <div id="notaccordion">
                    <div class="alert alert-info">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <strong> <spring:message code="user.form.name"/> : Lia Kotian </strong>
                    </div>

                    <div class="containerHeadline">
                        <i class="icon-table"></i>

                        <h2>
                            <strong><spring:message code="review.reputational.worldComplienceResultHeader"/></strong>
                        </h2>

                        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <div class="floatingBox table">
                        <div class="container-fluid">
                            <table class="table">
                                <tbody>
                                <c:forEach items="${newReputationalReviewBusinessPartner.wcList}" var="aWC"
                                           varStatus="loopStatus">
                                    <c:choose>
                                        <c:when test="${loopStatus.index%2 == 0}">
                                            <tr class="warning">
                                        </c:when>
                                        <c:otherwise>
                                            <tr class="info">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="cellBorder"><c:out value="${aWC.findedResult}"/></td>
                                    <td class="cellBorder"><a href="" class="hrefLinkColor">Select</a></td>
                                    <td id="wcID" style="display: none"><c:out value="${aWC.id}"/></td>
                                    </tr>
                                </c:forEach>

                                <tr>
                                    <td colspan="2" align="center" class="cellBorder hideWC"
                                        style="text-align: center;">
                                        <a href="#" class="hrefLinkColor">None Of The Above</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>


                    <div class="containerHeadline">
                        <i class="icon-table"></i>

                        <h2><spring:message code="new.reputational.review.upload.documents"/></h2>

                        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>

                    <div class="floatingBox table">
                        <div class="container-fluid">
                            <table class="table table-hover">

                                <tbody>
                                <tr>
                                    <td><spring:message
                                            code="new.reputational.review.business.partner.questionnaire"/></td>
                                    <td>
                                        <div class="control-group">
                                            <div class="controls">
                                                <input type="file" id="inputFile1" style="display: none">

                                                <div class="dummyfile">
                                                    <input id="filename1" type="text" class="input disabled span10"
                                                           name="filename1" readonly>
                                                    <a id="fileselectbutton1" class="btn">Browse</a>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="margin-left: 10px; margin-top: 17px;">
                                            <b id="fileSizeValidationForotherDueDiligenceFile1"
                                               style="color: red; display: none;"><spring:message
                                                    code="maxUploadFileSize.warning.message"/> <fmt:formatNumber
                                                    type="number" value="${maxFileUploadSize/(1024*1024)}"
                                                    maxFractionDigits="2"/> <spring:message
                                                    code="maxUploadFileSize.warning.message.lastPast"/></b>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><spring:message
                                            code="new.reputational.review.other.due.diligence.documents"/></td>
                                    <td>
                                        <div class="control-group">
                                            <div class="controls">
                                                <input type="file" id="inputFile2" style="display: none">

                                                <div class="dummyfile">
                                                    <input id="fileName2" type="text" class="input disabled span10"
                                                           name="fileName2" readonly>
                                                    <a id="fileselectbutton2" class="btn">Browse</a>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="margin-left: 10px; margin-top: 17px;">
                                            <b id="fileSizeValidationForotherDueDiligenceFile"
                                               style="color: red; display: none;"><spring:message
                                                    code="maxUploadFileSize.warning.message"/> <fmt:formatNumber
                                                    type="number" value="${maxFileUploadSize/(1024*1024)}"
                                                    maxFractionDigits="2"/> <spring:message
                                                    code="maxUploadFileSize.warning.message.lastPast"/></b>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><spring:message code="new.reputational.review.executed.or.draft.contact"/></td>
                                    <td>
                                        <div class="control-group">
                                            <div class="controls">
                                                <input type="file" id="inputFile3" style="display: none">

                                                <div class="dummyfile">
                                                    <input id="fileName3" type="text" class="input disabled span10"
                                                           name="fileName3" readonly>
                                                    <a id="fileselectbutton3" class="btn">Browse</a>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="margin-left: 10px; margin-top: 17px;">
                                            <b id="fileSizeValidationForexecutedOrDraftContractFile"
                                               style="color: red; display: none;"><spring:message
                                                    code="maxUploadFileSize.warning.message"/> <fmt:formatNumber
                                                    type="number" value="${maxFileUploadSize/(1024*1024)}"
                                                    maxFractionDigits="2"/> <spring:message
                                                    code="maxUploadFileSize.warning.message.lastPast"/></b>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>


                    <script defer="defer">
                        var maxFileSize = ${maxFileUploadSize};
                    </script>
                </div>
            </div>
            </p>
        </div>
    </div>
    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

</div>

<div id="solvencyDivId" class="row-fluid">
    <!-- ==================== ACCORDION CONTAINER ==================== -->
    <div class="span6">
        <!-- ==================== ACCORDION HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-folder-open"></i><h2><spring:message code="dueDilligance.solvencyAnalysis"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF ACCORDION HEADLINE ==================== -->

        <!-- ==================== ACCORDION FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="accordion" id="accordion">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <div class="collapseIconContainer">
                                <i class="icon-plus"></i>
                            </div>
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                Balance Sheet
                            </a>
                        </div>
                        <div id="collapseOne" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <div id="assetIncomeDiv">
                                    <table>
                                        <tr><td colspan="3"></td></tr>
                                        <tr class = "alert alert-success">
                                            <td colspan="2" class="assetAdd">Assets</td>
                                            <td class="yearAdd" style="text-align: right;"><input type="text"
                                                                                            id="selectedYear1" class="year" value="" placeholder="Year"
                                                                                            size="10" /></td>
                                            <td id="addYearBtn"><input type="submit" id="addYear"
                                                                       value=" " name="submit" />
                                            </td>
                                            <td >add year</td>
                                        </tr>
                                        <!-- Current assets block start -->
                                        <tr>
                                            <td colspan="3" class="totalText">Current Assets</td>
                                        </tr>
                                        <tr id="assetLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="assetName"
                                                                      value="" placeholder="Current Assets" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="amount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addMoreBtn"><input type="submit" id="addMore"
                                                                       value=" " name="submit" /></td>
                                            <td>add asset</td>
                                        </tr>
                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td   colspan="2" >Total current assets</td>
                                            <td id = "totalAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                        <!-- Current assets block end -->
                                        <!-- fixed assets block start -->
                                        <tr>
                                            <td colspan="3" class="totalText">Fixed (Long-Term) Assets</td>
                                        </tr>
                                        <tr id="fixAssetLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="fixAssetName"
                                                                      value="" placeholder="Fixed Assets" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="fixedAmount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addMoreFixBtn"><input type="submit" id="addMoreFix"
                                                                          value=" " name="submit" /></td>
                                            <td>add asset</td>
                                        </tr>
                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td  colspan="2" >Total fixed assets</td>
                                            <td id = "totalFixedAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                        <!-- fixed assets block end -->
                                        <!-- other assets block start -->
                                        <tr>
                                            <td colspan="3" class="totalText">Other Assets</td>
                                        </tr>
                                        <tr id="otherAssetLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="otherAssetName"
                                                                      value="" placeholder="Other Assets" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="otherAmount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addMoreOtherBtn"><input type="submit"
                                                                            id="addMoreOther" value=" " name="submit" /></td>
                                            <td>add asset</td>
                                        </tr>

                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td   colspan="2" >Total other assets</td>
                                            <td id = "totalOtherAmount1"  class = "rightAlign">$0</td>
                                        </tr>

                                        <tr  class="alert alert-info">

                                            <td   colspan="2" >Total assets</td>
                                            <td id="netTotalAmount1" class="amount">$0</td>
                                        </tr>
                                        <!-- other assets block end -->
                                        <tr style="height: 20px;" ></tr>
                                        <tr id="liaLevel">
                                            <td class = "alert alert-success" colspan="2" class="assetAdd">Liabilities and Owner's Equity</td>
                                            <td class="yearAdd alert alert-success" style="text-align: right; "></td>
                                            <td id="addLibYearBtn"></td>
                                            <td style="color: white;"></td>
                                        </tr>

                                        <!-- Current Liabilities block start -->
                                        <tr>

                                            <td colspan="3" class="totalText">Current Liabilities</td>
                                        </tr>
                                        <tr id="libLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="libName" value=""
                                                                    placeholder="Current Liabilities" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="libAmount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addLibMoreBtn"><input type="submit" id="addLibMore"
                                                                          value=" " name="submit" /></td>
                                            <td>add liabilities</td>
                                        </tr>

                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td  colspan="2" >Total current liabilities</td>
                                            <td id = "totalLibAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                        <!-- Current assets block end -->
                                        <!-- Long-Term Liabilities block start -->
                                        <tr>
                                            <td colspan="3" class="totalText">Long-Term Liabilities</td>
                                        </tr>
                                        <tr id="fixLibLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="fixLibName"
                                                                    value="" placeholder="Long-Term Liabilities" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="libFixedAmount1" value="" placeholder="Amount"
                                                                                  class="amount" size="10" /></td>
                                            <td id="addLibMoreFixBtn"><input type="submit"
                                                                             id="addLibMoreFix" value=" " name="submit" /></td>
                                            <td>add liabilities</td>
                                        </tr>
                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td   colspan="2" >Total long-term liabilities</td>
                                            <td id = "totalLibFixedAmount1"  class = "rightAlign">$0</td>
                                        </tr>

                                        <!-- Long-Term Liabilities block end -->
                                        <!-- Owner's Equity block start -->
                                        <tr>
                                            <td colspan="3" class="totalText">Owner's Equity</td>
                                        </tr>
                                        <tr id="otherLibLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="otherLibName"
                                                                    value="" placeholder="Owner's Equity" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="libOtherAmount1" value="" placeholder="Amount"
                                                                                  class="amount" size="10" /></td>
                                            <td id="addLibMoreOtherBtn"><input type="submit"
                                                                               id="addLibMoreOther" value=" " name="submit" /></td>
                                            <td>add liabilities</td>
                                        </tr>
                                        <tr >
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>
                                        <tr>
                                            <td  colspan="2" >Total Owners equity</td>
                                            <td id = "totalLibOtherAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                        <tr  class="alert alert-info">
                                            <p></p>
                                            <td  colspan="2" >Total Liabilities and Owners Equity</td>
                                            <td id = "totalLiabilities1"class="amount">$0</td>
                                        </tr>
                                        <!-- Owner's Equity block end -->
                                    </table>

                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <div class="collapseIconContainer">
                                <i class="icon-plus"></i>
                            </div>
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                Income Statement
                            </a>
                        </div>
                        <div id="collapseTwo" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <div id="assetDiv">
                                    <table>
                                        <tr><td colspan="3"></td></tr>
                                        <tr  class = "alert alert-success">
                                            <td colspan="2" class="assetAdd">Revenue</td>
                                            <td class="yearAdd" ><input
                                                    type="text" id="selectedIncYear1" class="year" value=""
                                                    placeholder="Year" size="10" /></td>
                                            <td id="addIncYearBtn"><input type="submit"
                                                                          id="addIncYear" value=" " name="submit" /></td>
                                            <td >add year</td>
                                        </tr>

                                        <tr id="incLevel">
                                            <td></td>
                                            <td id="inc"><input type="text" id="incName" value=""
                                                                placeholder="Revenue" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="incAmount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addIncBtn"><input type="submit" id="addIncMore"
                                                                      value=" " name="submit" /></td>
                                            <td>add revenue</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>

                                        <tr>
                                            <td   colspan="2" >Total Revenues</td>
                                            <td id = "totalIncAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                        <tr >
                                            <td  class = "alert alert-success" colspan="2" class="assetAdd">Expenses</td>
                                            <td class="yeaAdd" style="text-align: right;"><!-- <input
                                        type="text" id="selectedExpYear1" class="year" value=""
                                        placeholder="Year" size="10" /> --></td>
                                            <td id="addExpYearBtn"><!-- <input type="submit"
                                        id="addExpYear" value=" " name="submit" /> --></td>
                                            <td style="color: white;"></td>
                                        </tr>

                                        <tr id="expLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="expName" value=""
                                                                placeholder="Expenses" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="expAmount1" value="" placeholder="Amount" class="amount"
                                                                                  size="10" /></td>
                                            <td id="addExpMoreBtn"><input type="submit"
                                                                          id="addExpMore" value=" " name="submit" /></td>
                                            <td>add expense</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>

                                        <tr>
                                            <td   colspan="2" >Total Expense</td>
                                            <td id = "totalExpAmount1"  class = "rightAlign">$0</td>
                                        </tr>

                                        <tr >
                                            <td class = "alert alert-success" colspan="2" class="assetAdd">Below-the-Line Items</td>
                                            <td class="yearAdd" style="text-align: right;"><!-- <input
                                        type="text" id="selectedLineItemYear1" class="year" value=""
                                        placeholder="Year" size="10" /> --></td>
                                            <td id="addLineItemYearBtn"><!-- <input type="submit"
                                        id="addLineItemYear" value=" " name="submit" /> --></td>
                                            <td style="color: white;"></td>
                                        </tr>
                                        <!-- Current Liabilities block start -->

                                        <tr id="lineItemLevel">
                                            <td></td>
                                            <td class="textInput"><input type="text" id="lineItemName"
                                                                     value="" placeholder="Line Items" size="15" /></td>
                                            <td style="text-align: right;"><input type="text"
                                                                                  id="lineItemAmount1" value="" placeholder="Amount"
                                                                                  class="amount" size="10" /></td>
                                            <td id="addLineItemBtn"><input type="submit"
                                                                           id="addLineItemMore" value=" " name="submit" /></td>
                                            <td>add line-items</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2"></td>
                                            <td  class = "rightAlign" ><hr></td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" >Total Expense</td>
                                            <td id = "totalLineItemAmount1"  class = "rightAlign">$0</td>
                                        </tr>
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <div class="collapseIconContainer">
                                <i class="icon-plus"></i>
                            </div>
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                Financial Statement
                            </a>
                        </div>
                        <div id="collapseThree" class="accordion-body collapse">
                            <div class="accordion-inner">
                                Financial statement not work now
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ==================== END OF ACCORDION FLOATING BOX ==================== -->
    </div>
    </div>
