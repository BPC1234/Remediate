<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% final String contextPath = request.getContextPath(); %>
<script src="<%= contextPath %>/resources/js/due-dilligance-landing.js"></script>

<style>
#uploadBox {
    margin-left: 237px;
}

input[type="button"].financialReview {
    background-color: #5CADFF;
    border-radius: 4px 4px 4px 4px;
    padding: 3px;
}

input[type="button"]:HOVER.financialReview {
    background-color: #0000FF;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
}
</style>


<div id="replaceAjaxDiv">
<h3><strong><spring:message code="dueDilligance.newFinancialReviewForm" /></strong></h3>
    <div id="notaccordion">
        <h3 style="margin-top: 10px; text-align: left; font-size: 15px"><a href="#"><strong>New Financial Review</strong></a></h3>
        <div style="display: none">
        <br />
        <form:form method="post" commandName="newFinancialReviewBusinessPartner" action="./financialFormProcess.html">
            <div class="boxBorder" style="margin-left: 78px; padding: 10px 10px;">
                <div id="relationshipEntry" style="text-align: -moz-center; margin-left: 0px; margin-right: 0px;">
                    <table border="0" width = "100%" style ="border-spacing:2px;">
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="user.form.name" /> :</div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "name"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                       
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.address" /> :</div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "addressLine"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.country" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "countryName"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.city" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "city"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.state" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "state"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.zip" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "zip"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.tin" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "tin"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.bankAccountNo" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "bankAccountNo"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.bankAccountLocation" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "bankAccountLocation"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.bankName" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "bankName"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                        <tr>
                            <td class = "fc">&nbsp;</td>
                            <td class = "middleColumn"><div class="relationshipEntryContentLabel"><spring:message code="dueDilligance.newRelationShip.KeyOfficer" /> : </div></td>
                            <td class = "middleColumn"><form:input type="text" class="relationshipEntryInput" disabled="true" path = "keyOfficer"/></td>
                            <td class = "lastColumn"><span class="inputerrormsg"></span></td>
                        </tr>
                    </table>   
                </div>
                    <table border="0" align="center">
                        <tr style>
                            <td align="left" style="padding: 10px; font-size: 17px;"><strong>File
                                    Type:</strong></td>
                            <td style="padding: 10px; font-size: 17px"><select
                                    id="fileType">
                                        <option value="empty">Please select</option>
                                        <option value="balanceSt">Balance Sheet</option>
                                        <option value="incomeSt">Income Statement</option>
                                </select></td>
                         
                        </tr>
                     
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan="2" align="right"><input class="financialReview"
                                type="button" id="financialReviewProcessCancleBtn" value='Cancle' />&nbsp;&nbsp;</td>
                            <td align="justify"><input class="financialReview"
                                type="button" id="financialReviewBtn" value='Review' /></td>
                        </tr>
                    </table>   
                    </div>
           
        </form:form>
       
        <div class="boxBorder" style="margin-left: 78px; padding: 10px 10px;">
                    <!-- balance sheet  start -->
                <div id="sheetDiv">
                    <div id="sheetTitle">
                        <b><strong>Balance Sheet</strong></b>
                    </div>
                    <div id="assetDiv">
                        <table>
                            <tr style="background-color: #66CCFF;">
                                <td colspan="2" id="asset">Assets</td>
                                <td id="year" style="text-align: right;"><input type="text"
                                    id="selectedYear1" class="year" value="" placeholder="Year"
                                    size="10" /></td>
                                <td id="addYearBtn"><input type="submit" id="addYear"
                                    value=" " name="submit" />
                                </td>
                                <td style="color: white;">add year</td>
                            </tr>
                            <!-- Current assets block start -->
                            <tr>
                                <td colspan="3" id="assetType">Current Assets</td>
                            </tr>
                            <tr id="assetLevel">
                                <td></td>
                                <td id="assetItem"><input type="text" id="assetName"
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total current assets</td>
                            	<td id = "totalAmount1"  class = "rightAlign">$0</td>
                            </tr>
                            <!-- Current assets block end -->
                            <!-- fixed assets block start -->
                            <tr>
                                <td colspan="3" id="assetType">Fixed (Long-Term) Assets</td>
                            </tr>
                            <tr id="fixAssetLevel">
                                <td></td>
                                <td id="assetItem"><input type="text" id="fixAssetName"
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total fixed assets</td>
                            	<td id = "totalFixedAmount1"  class = "rightAlign">$0</td>
                            </tr>
                            <!-- fixed assets block end -->
                            <!-- other assets block start -->
                            <tr>
                                <td colspan="3" id="assetType">Other Assets</td>
                            </tr>
                            <tr id="otherAssetLevel">
                                <td></td>
                                <td id="assetItem"><input type="text" id="otherAssetName"
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total other assets</td>
                            	<td id = "totalOtherAmount1"  class = "rightAlign">$0</td>
                            </tr>
                            <tr class="totalValue">
                             
                            	<td id="totalTxt" colspan="2" >Total assets</td>
                            	<td id="netTotalAmount1" class="amount">$0</td>
                            </tr>
                            <!-- other assets block end -->
                            
                            <tr>
                                <td style="background-color: #66CCFF;" colspan="2" id="asset">Liabilities and Owner's Equity</td>
                                <td id="year" style="text-align: right;"><!-- <input type="text"
                                    id="selectedLibYear1" class="year" value="" placeholder="Year"
                                    size="10" /> --></td>
                                <td id="addLibYearBtn"><!-- <input type="submit" id="addLibYear"
                                    value=" " name="submit" /> -->
                                    </td>
                                <td style="color: white;"></td>
                            </tr>
                            
                            <!-- Current Liabilities block start -->
                            <tr>

                                <td colspan="3" id="assetType">Current Liabilities</td>
                            </tr>
                            <tr id="libLevel">
                                <td></td>
                                <td id="libItem"><input type="text" id="libName" value=""
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total current liabilities</td>
                            	<td id = "totalLibAmount1"  class = "rightAlign">$0</td>
                            </tr>
                            <!-- Current assets block end -->
                            <!-- Long-Term Liabilities block start -->
                            <tr>
                                <td colspan="3" id="libType">Long-Term Liabilities</td>
                            </tr>
                            <tr id="fixLibLevel">
                                <td></td>
                                <td id="libItem"><input type="text" id="fixLibName"
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total long-term liabilities</td>
                            	<td id = "totalLibFixedAmount1"  class = "rightAlign">$0</td>
                            </tr>
                            
                            <!-- Long-Term Liabilities block end -->
                            <!-- Owner's Equity block start -->
                            <tr>
                                <td colspan="3" id="libType">Owner's Equity</td>
                            </tr>
                            <tr id="otherLibLevel">
                                <td></td>
                                <td id="libItem"><input type="text" id="otherLibName"
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
                            	<td id="totalTextBalanceSheet" colspan="2" >Total Owners equity</td>
                            	<td id = "totalLibOtherAmount1"  class = "rightAlign">$0</td>
                            </tr>
                          <tr class="totalValue">
                          <p></p>
                          	<td id="totalTxt" colspan="2" >Total Liabilities and Owners Equity</td>
                          	<td id = "totalLiabilities1"class="amount">$0</td>
                          </tr>
                            <!-- Owner's Equity block end -->
                        </table>

                    </div>
                
                </div>
                <!-- balance sheet end -->
                <!-- income statement start -->
                <div id="incomeSt">
                    <div id="sheetDiv">
                        <div id="sheetTitle">
                            <b><strong>Income Statement</strong></b>
                        </div>
                        <div id="assetDiv">
                            <table>
                                <tr style="background-color: #66CCFF;">
                                    <td colspan="2" id="asset">Revenue</td>
                                    <td id="year" style="text-align: right;"><input
                                        type="text" id="selectedIncYear1" class="year" value=""
                                        placeholder="Year" size="10" /></td>
                                    <td id="addIncYearBtn"><input type="submit"
                                        id="addIncYear" value=" " name="submit" /></td>
                                    <td style="color: white;">add year</td>
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
                                	<td id="totalTxt" colspan="2" >Total Revenues</td>
                                	<td id = "totalIncAmount1"  class = "rightAlign">$0</td>
                                </tr>
                                 <tr >
                                    <td style="background-color: #66CCFF;" colspan="2" id="asset">Expenses</td>
                                    <td id="year" style="text-align: right;"><!-- <input
                                        type="text" id="selectedExpYear1" class="year" value=""
                                        placeholder="Year" size="10" /> --></td>
                                    <td id="addExpYearBtn"><!-- <input type="submit"
                                        id="addExpYear" value=" " name="submit" /> --></td>
                                    <td style="color: white;"></td>
                                </tr>

                                <tr id="expLevel">
                                    <td></td>
                                    <td id="exp"><input type="text" id="expName" value=""
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
                                	<td id="totalTxt" colspan="2" >Total Expense</td>
                                	<td id = "totalExpAmount1"  class = "rightAlign">$0</td>
                                </tr>
                                
 								 <tr >
                                    <td style="background-color: #66CCFF;" colspan="2" id="asset">Below-the-Line Items</td>
                                    <td id="year" style="text-align: right;"><!-- <input
                                        type="text" id="selectedLineItemYear1" class="year" value=""
                                        placeholder="Year" size="10" /> --></td>
                                    <td id="addLineItemYearBtn"><!-- <input type="submit"
                                        id="addLineItemYear" value=" " name="submit" /> --></td>
                                    <td style="color: white;"></td>
                                </tr>
                                <!-- Current Liabilities block start -->

                                <tr id="lineItemLevel">
                                    <td></td>
                                    <td id="lineItem"><input type="text" id="lineItemName"
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
                                	<td id="totalTxt" colspan="2" >Total Expense</td>
                                	<td id = "totalLineItemAmount1"  class = "rightAlign">$0</td>
                                </tr>
                            </table>

                        </div>
                    
                     

                    </div>

                </div>
                <!-- income statement end -->           
       
        </div>
      </div>
     
    </div>
</div>

