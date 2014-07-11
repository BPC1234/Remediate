<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	final String contextPath = request.getContextPath();
%>
<script src="<%=contextPath%>/resources/js/due-dilligance-landing.js"></script>

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


<div id="replaceAjaxDiv">
	<h3>
		<strong><spring:message
				code="dueDilligance.landingPage.financialReviewSummary" /></strong>
	</h3>
	<br />
	<div id="notaccordion">
		<h3 style="margin-top: 10px; text-align: left; font-size: 15px">
			<a href="#"><strong><spring:message
						code="dueDilligance.landingPage.financialReviewSummary" /></strong></a>
		</h3>
		<div style="display: none">
			<br />
			<div class="boxBorder" style=" text-align: center; margin-left: 78px; padding: 10px 10px;">
				<div id="relationshipEntry"
					style="display: inline-block; text-align: center; margin-left: 0px; margin-right: 0px;">
					<div id="relationshipEntryBox" style="height: 309px;">

						<div id="relationshipEntryDiv" class="reputationalFormProcess">
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="user.form.name" />
									:
								</div>
								<div class="reputationalFormProcessContentValue">
									<label><b>Alex Liu</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.address" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>Gulshan, Dhaka H#179 R#45</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.country" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>Customer</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.city" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>20130613</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.state" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b> Jhon Smith, Lila Kotian</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.zip" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>Gulshan, Dhaka H#179 R#45</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.tin" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>Customer</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message
										code="dueDilligance.newRelationShip.bankAccountNo" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>20130613</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message
										code="dueDilligance.newRelationShip.bankAccountLocation" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b> Jhon Smith, Lila Kotian</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.bankName" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>Customer</b></label>
								</div>
							</div>
							<div class="relationshipEntryContent">
								<div class="reputationalFormProcessContentLabel">
									<spring:message code="dueDilligance.newRelationShip.KeyOfficer" />
									:
								</div>
								<div class="reputationalFormProcessContentValue" style="">
									<label><b>20130613</b></label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="boxBorder"
				style="display: inline-block; text-align: center; margin-left: 78px; margin-top: 15px; padding: 10px 10px;">
				<div style="display: inline-block;">
					<table style="display: inline-block;">
						<tr>
							<td style="padding: 26px 15x;"><img
								style="width: 35px; height: 25px;"
								src="<%=contextPath%>/resources/images/docx.png"
								class="fileIcon" /></td>
							<td><b>FbiBallanceSheet.docs</b></td>
							<td>&nbsp;&nbsp;<input class="financialReview" type="button"
								value="Download"></td>
						</tr>
					</table>
					<br />
					<div
						style="text-align: left;padding-bottom: 4px; padding-top: 20px; padding-bottom: 4px; font-size: 14px; font-family: times new roman;">
						<strong>Transaction Type Wise Summary</strong>
					</div>

					<div id="financialReviewSummaryBlock" style="display: inline-block;">
						<div id="demo"	class="CSSTableGenerator">

							<table>
								<tr>
									<td>Type</td>
									<td>Count</td>
									<td>Dr.</td>
									<td>Cr.</td>
								</tr>
								<tr>
									<td class="tdLeft">DEBIT</td>
									<td>45</td>
									<td>$48000.48</td>
									<td>$14600.00</td>
								</tr>
								<tr>
									<td class="tdLeft">CREDIT</td>
									<td>37</td>
									<td>$41800.66</td>
									<td>$12400.00</td>
								</tr>

								<tr>
									<td class="tdLeft">CHECK</td>
									<td>14</td>
									<td>$8500</td>
									<td>$0.00</td>
								</tr>
								<tr>
									<td class="tdLeft">DSLIP</td>
									<td>9</td>
									<td>$78009.78</td>
									<td>$4500</td>
								</tr>
								<tr style="font-weight: bold; text-align: right;">
									<td><b>Total</b></td>
									<td><b>115</b></td>
									<td><b>$7845109.78</b></td>
									<td><b>$541100.00</b></td>
								</tr>
							</table>
						</div>
						<div
							style="display: inline-block; text-align: left; padding-bottom: 4px; padding-top: 20px; padding-bottom: 4px; font-size: 14px; font-family: times new roman;">
							<strong>Transaction Medium Wise Summary</strong>
						</div>
						<div id="demo" style="display: inline-block;"
							class="CSSTableGenerator">

							<table >
								<tr>
									<td>Type</td>
									<td>Count</td>
									<td>Dr.</td>
									<td>Cr.</td>
								</tr>
								<tr>
									<td class="tdLeft">ATM WITHDRAWAL</td>
									<td>28</td>
									<td>$48570.75</td>
									<td>$6620.00</td>
								</tr>
								<tr>
									<td class="tdLeft">CAPITAL ONE ONLINE PMT</td>
									<td>5</td>
									<td>$6500.66</td>
									<td>$100.00</td>
								</tr>

								<tr>
									<td class="tdLeft">BAGEL BOSS HEWLETT NY</td>
									<td>3</td>
									<td>$4120.23</td>
									<td>$210.00</td>
								</tr>
								<tr>
									<td class="tdLeft">CHASE EPAY ONUS</td>
									<td>12</td>
									<td>$4501.78</td>
									<td>$110</td>
								</tr>
								<tr>
									<td class="tdLeft">CHECK # 713 OHLBY CHECKPAYMT</td>
									<td>7</td>
									<td>$33200.45</td>
									<td>$412.00</td>
								</tr>
								<tr>
									<td class="tdLeft">CHECK 512</td>
									<td>6</td>
									<td>$48800.41</td>
									<td>$320.00</td>
								</tr>
								<tr>
									<td class="tdLeft">GE Capital CC PYMT</td>
									<td>3</td>
									<td>$5500.14</td>
									<td>$320.00</td>
								</tr>
								<tr>
									<td class="tdLeft">HALE AND HEARTY SO NEW YORK NY</td>
									<td>7</td>
									<td>$700.14</td>
									<td>$35.00</td>
								</tr>
								<tr>
									<td class="tdLeft">NON-CHASE ATM WITHDRAW</td>
									<td>15</td>
									<td>$78400.14</td>
									<td>$4325.00</td>
								</tr>
								<tr>
									<td class="tdLeft">TD AMERITRADE ACH OUT</td>
									<td>6</td>
									<td>$7400.14</td>
									<td>$325.00</td>
								</tr>
								<tr>
									<td><b>Total</b></td>
									<td><b>115</b></td>
									<td><b>$7845109.78</b></td>
									<td><b>$541100.00</b></td>
								</tr>

							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>