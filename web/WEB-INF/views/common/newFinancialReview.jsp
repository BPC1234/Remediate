<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% final String contextPath = request.getContextPath(); %>

<script src="<%=contextPath%>/resources/js/due-dilligance-landing.js"></script>
<script src="<%=contextPath%>/resources/js/reputational-form-process.js"></script>
<script src="<%=contextPath%>/resources/js/jQuery.print.js"></script>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<title><spring:message code="dueDilligance.landingPage.header.financialReview"/></title>
<div class="row-fluid">

    <div class="span12">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="dueDilligance.landingPage.header.financialReview"/></h2>
            <div class="controlButton pull-right"></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div id="printHeadingDiv" class="searchParameterDiv" style="width: 35%;">
                    <table style="margin: 10px; width: 95%">
                        <tr>
                            <td align="right" style="width: 40%;">Business Name : </td>
                            <td align="left" style="width: 60%;"> Bellwether Technology Corporation</td>
                        </tr>
                        <tr>
                            <td align="right">User Name : </td>
                            <td align="left"> Britanney Castaneda.</td>
                        </tr>
                        <tr>
                            <td align="right"> Created : </td>
                            <td align="left"> <script>document.write(today);</script></td>
                        </tr>
                    </table>
                </div>
                </br>

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
                                        <td >year</td>
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
                                        <td>current asset</td>
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
                                        <td>fixed asset</td>
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
                                        <td>other asset</td>
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
                                        <td>current liabilities</td>
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
                                        <td>fixed liabilities</td>
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
                                        <td>other liabilities</td>
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
                                        <td >year</td>
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
                                        <td>revenue</td>
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
                                        <td>expense</td>
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
                                        <td>line-items</td>
                                    </tr>

                                    <tr>
                                        <td colspan="2"></td>
                                        <td  class = "rightAlign" ><hr></td>
                                    </tr>

                                    <tr>
                                        <td colspan="2" >Total Below-the-Line Items</td>
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
                            <spring:message code="dueDilligance.solvency.analysis.statement.cash.flows"/>
                        </a>
                    </div>
                    <div id="collapseThree" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <div>
                            <div id="cashFlowHeader" class="searchParameterDiv cashFlowTitleDiv">
                               Cash flow statement for XYZ business for the year ended 31<sup>st</sup> of December 2010
                            </div>

                            </div>

                            <div style="text-align: center;">
                                <div class="cashFlowTableWrapperDiv">
                                    <table class="cashFlowTable">
                                    <tr>
                                          <td class="headingRowOfCashFlow">CASH FLOW FROM OPERATING ACTIVITIES</td><td></td>
                                          <td>$</td>
                                    </tr>
                                    <tr>
                                            <td>Cash receipts from customers</td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="83000" /></td>
                                    </tr>
                                    <tr>
                                            <td>Cash paid to suppliers and employees</td><td></td>
                                            <td class="underline">(<fmt:formatNumber type="number" maxFractionDigits="2" value="56000" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Cash generated from operations</td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="27000" /></td>
                                    </tr>
                                    <tr>
                                            <td>Dividends received<sup>*</sup></td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="250" /></td>
                                    </tr>
                                    <tr>
                                            <td>Interest received</td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="500" /></td>
                                    </tr>
                                    <tr>
                                            <td>Interest paid</td><td></td>
                                            <td>(<fmt:formatNumber type="number" maxFractionDigits="2" value="500" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Tax paid</td> <td></td>
                                            <td class="underline">(<fmt:formatNumber type="number" maxFractionDigits="2" value="2450" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Net cash flow from operating activities</td><td></td>
                                            <td class="underline"><fmt:formatNumber type="number" maxFractionDigits="2" value="24800" /></td>
                                    </tr>
                                    <tr>
                                            <td class="headingRowOfCashFlow">CASH FLOW FROM INVESTING ACTIVITIES</td> <td></td>
                                            <td></td>
                                    </tr>
                                    <tr>
                                            <td>Additions to equipment</td> <td></td>
                                            <td>(<fmt:formatNumber type="number" maxFractionDigits="2" value="2500" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Replacement of equipment</td><td></td>
                                            <td>(<fmt:formatNumber type="number" maxFractionDigits="2" value="7000" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Proceeds<sup>**</sup> from sale of equipment</td><td></td>
                                            <td class="underline"><fmt:formatNumber type="number" maxFractionDigits="2" value="500" /></td>
                                    </tr>
                                    <tr>
                                            <td>Net cash flow from investing activities</td><td></td>
                                            <td class="underline">(<fmt:formatNumber type="number" maxFractionDigits="2" value="9000" />)</td>
                                    </tr>
                                    <tr>
                                            <td class="headingRowOfCashFlow">CASH FLOW FROM FINANCING ACTIVITIES</td><td></td>
                                            <td></td>
                                    </tr>
                                    <tr>
                                            <td>Proceeds from capital contributed</td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="3400" /></td>
                                    </tr>
                                    <tr>
                                            <td>Proceeds from loan </td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="16000" /></td>
                                    </tr>
                                    <tr>
                                            <td>Payment of loan</td><td></td>
                                            <td class="underline">(<fmt:formatNumber type="number" maxFractionDigits="2" value="5400" />)</td>
                                    </tr>
                                    <tr>
                                            <td>Net cash flow from financing activities</td><td></td>
                                            <td class="underline"><fmt:formatNumber type="number" maxFractionDigits="2" value="13000" /></td>
                                    </tr>
                                    <tr>
                                            <td class="headingRowOfCashFlow">NET INCREASE/DECREASE IN CASH</td><td></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="28800" /></td>
                                    </tr>
                                    <tr>
                                            <td>Cash at the beginning of the period</td><td></td>
                                            <td class="underline"><fmt:formatNumber type="number" maxFractionDigits="2" value="2430" /></td>
                                    </tr>
                                    <tr>
                                            <td >Cash at the end of the period</td><td></td>
                                            <td class="underline"><fmt:formatNumber type="number" maxFractionDigits="2" value="31230" /></td>
                                    </tr>
                                    </table>
                                </div>
                                <button id="cashFlowPrint" class="btn btn-small btn-warning" type="button" style="width: 100px;margin-right: 30px">
                                    <i class="icon-print"></i>&nbsp;&nbsp;Print
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="accordion-group">
                    <div class="accordion-heading">
                        <div class="collapseIconContainer">
                            <i class="icon-plus"></i>
                        </div>
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseRatioAnalysis" id="ratioAnalysisId">
                            <spring:message code="dueDilligance.solvency.analysis.ratio.analysis"/>
                        </a>
                    </div>
                    <div id="collapseRatioAnalysis" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <div style="text-align: center;">
                             <div style="float: right;">
                               <button id="ratioAnalysisPrint" style="width: 100px;margin-right: 30px" type="button" class="btn btn-small btn-warning"><i class="icon-print"></i>&nbsp;&nbsp;Print</button>
                             </div>
                                <div style="clear: both;"></div>
                                <div style="margin-top: -25px" class="ratioAnalysisFirstInnerDiv">

                                <table id="ratio-analyis" class="table filled ">
                                    <tr class="total-amount slideColor"><td><spring:message code="solvency.analysis.ratio.analysis.total.assets"/>:</td><td id="total-assets" class="total-css"></td></tr>

                                    <tr class=" total-amount slideColor"><td><spring:message code="solvency.analysis.ratio.analysis.total.debit"/>:</td><td id="total-debit" class="total-css"></td></tr>

                                    <tr class="total-amount slideColor"><td><spring:message code="solvency.analysis.ratio.analysis.total.equity"/>:</td><td id="total-equity" class="total-css"></td></tr>

                                    <tr class="total-amount slideColor"><td><spring:message code="solvency.analysis.ratio.analysis.total.revenue"/>:</td><td id="total-revenue" class="total-css"></td></tr>

                                    <tr class="total-amount slideColor"><td><spring:message code="solvency.analysis.ratio.analysis.total.cost"/>:</td><td id="total-cost" class="total-css"></td></tr>
                                </table>
                            </div>
                            </div>
                            <div>
                                <div style="text-align: center;">
                                <div class="ratioAnalysisSecondInnerDiv">
                                <table id="ratio" class="table filled ">
                                    <tr style=" background: none repeat scroll 0 0 #E0FFD1;" class="total-amount " ><td>Net Income = </td><td id="revenue"></td><td> - </td><td id="cost"></td><td>=</td><td id="tincome"></td></tr>
                                    <tr style=" background: none repeat scroll 0 0 #FFF5E6;" class="total-amount " ><td>Debt to Equity = </td><td id="debt"></td><td>/</td><td id="equity"></td><td>=</td><td id="d-t-e"></td></tr>
                                    <tr style=" background: none repeat scroll 0 0 #FAE6FF;" class="total-amount "><td>Debt to Assets = </td><td id="tdebt"></td><td>/</td><td id="assets"></td><td>=</td><td id="d-t-a"></td></tr>
                                    <tr style=" background: none repeat scroll 0 0 #EBF5F0;" class="total-amount "><td>Net Profit Margin = </td><td id="net-income"></td><td>/</td><td id="trevenue"></td><td>=</td><td id="n-p-m"></td></tr>
                                </table>
                                </div>
                              </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                </div>
                </div>
                <!-- ==================== END OF ACCORDION FLOATING BOX ==================== -->
                </div>
                </div>

            </div>
        </div>
        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
    </div>
</div>

<div style="display: none;" id="ratioAnalysisPrintDiv">
    <div id="innerPrintDiv" style="height: 550px;width: 500px ;border: 1px solid #808080;">

    </div>
</div>

<div style="display: none;" id="cashFlowPrintDiv">
    <div id="cashFlowPrintDivInnerPrintDiv" style="height: 550px;width: 500px ;border: 1px solid #808080;">
        <div id="statmentHeaderId">
        </div>
    </div>
</div>
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->