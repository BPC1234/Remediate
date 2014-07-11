<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% final String contextPath = request.getContextPath(); %>
<script src="<%= contextPath %>/resources/js/jQuery.print.js"></script>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="row-fluid">

    <div class="span12">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2></h2>
            <div class="controlButton pull-right"></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="searchParameterDiv" style="width: 35%;">
                    <table style="margin: 10px; width: 95%">
                        <tr>
                            <td align="right" style="width: 40%;">Business Name : </td>
                            <td align="left" style="width: 60%;"> ${companyName}</td>
                        </tr>
                        <tr>
                            <td align="right">User Name : </td>
                            <td align="left"> ${userName}</td>
                        </tr>
                        <tr>
                            <td align="right"> Created : </td>
                            <td align="left"> ${created}</td>
                        </tr>
                     </table>
                </div>
                <table class="reputationalFormPrint" border="1" BORDERCOLOR="D2D8D8" style="padding: 5px;">
                    <thead>
                    <tr>
                        <th><spring:message code="due.dilligence.newRiskAss.report.header.no"/></th>
                        <th><spring:message code="due.dilligence.newRiskAss.report.header.category"/></th>
                        <th><spring:message code="due.dilligence.newRiskAss.report.header.specificEvaluationItem"/></th>
                        <th><spring:message code="due.dilligence.newRiskAss.report.header.result"/></th>
                        <th><spring:message code="due.dilligence.newRiskAss.report.header.notes"/></th>
                    </tr>
                    </thead>
                    <tr>
                        <td>1</td>
                        <td style="width: 15%">Reputation</td>
                        <td style="width: 65%">The BP has a negative reputation or indications of misconduct have appeared.</td>
                        <td style="width: 5%">
                            <div class="styled-select">
                            <select id="firstSelect">
                                <option value="Yes">Yes</option>
                                <option value="No">No</option>
                            </select>
                            </div>
                        </td>
                        <td style="width: 10%"><input type="text" class="span12" placeholder="Notes here..." value="" style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Past Conduct/History</td>
                        <td>The BP has a clear or documented history of violating applicable anti-bribery and anti-corruption laws such as the FCPA and UKBA (e.g. a publicized report of violation or criminal conviction).</td>
                        <td>
                            <div class="styled-select">
                            <select id="secondSelect">
                                <option value="Yes">Yes</option>
                                <option value="No">No</option>
                            </select>
                            </div>
                        </td>
                        <td style="width: 10%"><input type="text" class="span12" placeholder="Notes here..." value="" style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Ties to Government </td>
                        <td>The BP himself/herself or the owner, directors, officers, employees or affiliates of the BP has connection to a government official who has authority over any aspect of our business and/or emphasizes such a connection.</td>
                        <td>
                            <div class="styled-select">
                                <select id="thirdSelect">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </div>
                        </td>
                        <td><input type="text" class="span12" value="" placeholder="Notes here..." style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>Experience/Expertise</td>
                        <td>
                            Your due diligence process reveals problems or deficiencies relating to the BP's relevant capabilities, experience or expertise, e.g. showing that:
                            <dl>
                                <dd>- The BP lacks qualified staff or agents</dd>
                                <dd>- The BP has multiple agents engaged in overlapping work</dd>
                                <dd>- The BP lacks a solid track record or reliable references</dd>
                                <dd>- The BP has obtained excellent business results despite a lack of expertise</dd>
                            </dl>
                        </td>
                        <td>
                            <div class="styled-select">
                                <select id="fourthSelect">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </div>
                        </td>
                        <td><input type="text" class="span12" value="" placeholder="Notes here..." style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <c:if test="${endPoint > 4}">
                    <tr>
                        <td>5</td>
                        <td>Unusual Requests/Red Flags</td>
                        <td>Red Flags have appeared, such as the following:
                            <dl>
                                <dd>- Requests for payments in cash or advanced payment</dd>
                                <dd>- Requests for unusually large payments or commissions exceeding normal value in the local market</dd>
                                <dd>- Requests for payments to be made to another country or to third parties</dd>
                                <dd>- Requests for payments for mediation, brokering, good offices between you and government officials without offering any substantive, value added services</dd>
                                <dd>- The BP resists entering into a written agreement or fails to maintain written records (e.g. bookkeeping, issuance of receipts, etc.)</dd>
                                <dd>- Requests for reimbursements for poorly documented expenses</dd>
                                <dd>- The BP suggests that "facilitation payments" or "grease payments" are acceptable or necessary in the local market</dd>
                                <dd>- Requests for political or charitable contributions</dd>
                                <dd>- Any other unusual requests, proposals or actions that seem likely to indicate or lead to bribery by the BP</dd>
                            </dl>
                        </td>
                        <td>
                            <div class="styled-select">
                                <select id="fifthSelect">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </div>
                        </td>
                        <td><input type="text" class="span12" value="" placeholder="Notes here..." style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>Questionable Accounting</td>
                        <td>There is a lack of accuracy, detail, clarity or openness or other apparent problems regarding the BP's accounting (e.g. an auditor raises questions or concerns about the financial reliability of the BP).</td>
                        <td>
                            <div class="styled-select">
                                <select id="sixthSelect">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </div>
                        </td>
                        <td><input type="text" class="span12" value="" placeholder="Notes here..." style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>Knowledge of Anti-Bribery Laws and Regulations</td>
                        <td>There is a lack of knowledge and awareness of compliance with anti-bribery and anti-corruption laws and regulations (e.g. the BP refuses to agree to the company's anti-bribery and anti-corruption policies).</td>
                        <td>
                            <div class="styled-select">
                                <select id="seventhSelect">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </div>
                        </td>
                        <td><input type="text" class="span12" value="" placeholder="Notes here..." style="padding-left: 5px;padding-right: 5px"></td>
                    </tr>
                    </c:if>
                    <tr>
                        <td align="center" colspan="5">
                            <button type="button" id="reputationalPrintFormSave" class="btn btn-warning"><i class="icon-save"></i>&nbsp;&nbsp;<spring:message code="save.button.title"/> </button>
                            <button type="button"  id="reputationalPrintFormPrint" class="btn btn-success"><i class="icon-print"></i>&nbsp;&nbsp;<spring:message code="newRiskAssessmentSummary.print"/></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
    </div>
</div>

<div id="reportPrintDiv" style="display: none;">
    <table id="reportPrintTable" border="1" BORDERCOLOR="D2D8D8" style="padding: 5px;font-size: 9px!important;">
       <thead>
        <tr>
            <td style="font-weight: bold"><spring:message code="due.dilligence.newRiskAss.report.header.no"/></td>
            <td style="font-weight: bold"><spring:message code="due.dilligence.newRiskAss.report.header.category"/></td>
            <td style="font-weight: bold"><spring:message code="due.dilligence.newRiskAss.report.header.specificEvaluationItem"/></td>
            <td style="font-weight: bold"><spring:message code="due.dilligence.newRiskAss.report.header.result"/></td>
            <td style="font-weight: bold"><spring:message code="due.dilligence.newRiskAss.report.header.notes"/></td>
        </tr>
        </thead>
        <tr>
            <td>1</td>
            <td>Reputation</td>
            <td>The BP has a negative reputation or indications of misconduct have appeared.</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>2</td>
            <td>Past Conduct/History</td>
            <td>The BP himself/herself or the owner, directors, officers, employees or affiliates of the BP has connection to a government official who has authority over any aspect of our business and/or emphasizes such a connection.</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>3</td>
            <td>Ties to Government </td>
            <td>The BP himself/herself or the owner, directors, officers, employees or affiliates of the BP has connection to a government official who has authority over any aspect of our business and/or emphasizes such a connection.</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>4</td>
            <td>Experience/Expertise</td>
            <td>
                Your due diligence process reveals problems or deficiencies relating to the BP's relevant capabilities, experience or expertise, e.g. showing that:
                <dl>
                    <dd>- The BP lacks qualified staff or agents</dd>
                    <dd>- The BP has multiple agents engaged in overlapping work</dd>
                    <dd>- The BP lacks a solid track record or reliable references</dd>
                    <dd>- The BP has obtained excellent business results despite a lack of expertise</dd>
                </dl>
            </td>
            <td></td>
            <td></td>
        </tr>
        <c:if test="${endPoint > 4}">
        <tr>
            <td>5</td>
            <td>Unusual Requests/Red Flags</td>
            <td>Red Flags have appeared, such as the following:
                <dl>
                    <dd>- Requests for payments in cash or advanced payment</dd>
                    <dd>- Requests for unusually large payments or commissions exceeding normal value in the local market</dd>
                    <dd>- Requests for payments to be made to another country or to third parties</dd>
                    <dd>- Requests for payments for mediation, brokering, good offices between you and government officials without offering any substantive, value added services</dd>
                    <dd>- The BP resists entering into a written agreement or fails to maintain written records (e.g. bookkeeping, issuance of receipts, etc.)</dd>
                    <dd>- Requests for reimbursements for poorly documented expenses</dd>
                    <dd>- The BP suggests that "facilitation payments" or "grease payments" are acceptable or necessary in the local market</dd>
                    <dd>- Requests for political or charitable contributions</dd>
                    <dd>- Any other unusual requests, proposals or actions that seem likely to indicate or lead to bribery by the BP</dd>
                </dl>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>6</td>
            <td>Questionable Accounting</td>
            <td>There is a lack of accuracy, detail, clarity or openness or other apparent problems regarding the BP's accounting (e.g. an auditor raises questions or concerns about the financial reliability of the BP).</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>7</td>
            <td>Knowledge of Anti-Bribery Laws and Regulations</td>
            <td>There is a lack of knowledge and awareness of compliance with anti-bribery and anti-corruption laws and regulations (e.g. the BP refuses to agree to the company's anti-bribery and anti-corruption policies).</td>
            <td></td>
            <td></td>
        </tr>
        </c:if>
        </tbody>
    </table>

</div>
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
<script type="text/javascript">
    $('#reputationalPrintFormPrint').click(function(){

    $("#reportPrintTable tbody tr").eq(0).find('td').eq(3).text($("#firstSelect").val());
    $("#reportPrintTable tbody tr").eq(1).find('td').eq(3).text($("#secondSelect").val());
    $("#reportPrintTable tbody tr").eq(2).find('td').eq(3).text($("#thirdSelect").val());
    $("#reportPrintTable tbody tr").eq(3).find('td').eq(3).text($("#fourthSelect").val());
    $("#reportPrintTable tbody tr").eq(0).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(0).find('td').eq(4).find('input').val());
    $("#reportPrintTable tbody tr").eq(1).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(1).find('td').eq(4).find('input').val());
    $("#reportPrintTable tbody tr").eq(2).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(2).find('td').eq(4).find('input').val());
    $("#reportPrintTable tbody tr").eq(3).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(3).find('td').eq(4).find('input').val());
    if($(".reputationalFormPrint tbody tr").length-1 > 4){
    $("#reportPrintTable tbody tr").eq(4).find('td').eq(3).text($("#fifthSelect").val());
    $("#reportPrintTable tbody tr").eq(5).find('td').eq(3).text($("#sixthSelect").val());
    $("#reportPrintTable tbody tr").eq(6).find('td').eq(3).text($("#seventhSelect").val());
    $("#reportPrintTable tbody tr").eq(4).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(4).find('td').eq(4).find('input').val());
    $("#reportPrintTable tbody tr").eq(5).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(5).find('td').eq(4).find('input').val());
    $("#reportPrintTable tbody tr").eq(6).find('td').eq(4).text($(".reputationalFormPrint tbody tr").eq(6).find('td').eq(4).find('input').val());
    }

    $("#reportPrintDiv .searchParameterDiv").remove();
    $(".searchParameterDiv").clone().insertBefore("#reportPrintDiv table");

        $("#reportPrintDiv .searchParameterDiv").css("width","300px");
        $("#reportPrintDiv .searchParameterDiv").css("margin-left","180px");
        $("#reportPrintDiv .searchParameterDiv").css("margin-bottom","10px");
//        $("#reportPrintDiv .searchParameterDiv").css("border","1px solid #D2D8D8");
        $("#reportPrintDiv .searchParameterDiv table tbody tr td").css("font-size","9px");

    $("#reportPrintDiv").print();
    });
</script>