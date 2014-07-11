
<%
	final String contextPath = request.getContextPath();
%>
<html>
<head>
<title>Insert title here</title>
<script src="<%=contextPath%>/resources/js/due-dilligance-landing.js">
	
</script>
</head>


<body>

	<div id="sheetDiv">
		<div id="sheetTitle">
			<b><strong>Income Statement</strong></b>
		</div>
		<div id="assetDiv">
			<table>
				<tr style="background-color: #66CCFF;">
					<td colspan="2" id="asset">Revenue</td>
					<td id="year" style="text-align: right;"><input type="text"
						id="selectedIncYear1" class="year" value="" placeholder="Year"
						size="10" /></td>
					<td id="addIncYearBtn"><input type="submit" id="addIncYear"
						value=" " name="submit" /></td>
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
                
			</table>

		</div>
		<!-- Income Statement block end -->
		<!-- Expenses block start -->
		<div id="liabilitiesDiv">
			<table>
				<tr style="background-color: #66CCFF;">
					<td colspan="2" id="asset">Expenses</td>
					<td id="year" style="text-align: right;"><input type="text"
						id="selectedExpYear1" class="year" value="" placeholder="Year"
						size="10" /></td>
					<td id="addExpYearBtn"><input type="submit" id="addExpYear"
						value=" " name="submit" /></td>
					<td style="color: white;">add year</td>
				</tr>

				<tr id="expLevel">
					<td></td>
					<td id="exp"><input type="text" id="expName" value=""
						placeholder="Expenses" size="15" /></td>
					<td style="text-align: right;"><input type="text"
						id="expAmount1" value="" placeholder="Amount" class="amount"
						size="10" /></td>
					<td id="addExpMoreBtn"><input type="submit" id="addExpMore"
						value=" " name="submit" /></td>
					<td>add expense</td>
				</tr>


			</table>

		</div>
		<!-- Expenses block end -->

		<!-- Below the line items start -->
		<div id="liabilitiesDiv">
			<table>
				<tr style="background-color: #66CCFF;">
					<td colspan="2" id="asset">Below-the-Line Items</td>
					<td id="year" style="text-align: right;"><input type="text"
						id="selectedLineItemYear1" class="year" value=""
						placeholder="Year" size="10" /></td>
					<td id="addLineItemYearBtn"><input type="submit"
						id="addLineItemYear" value=" " name="submit" /></td>
					<td style="color: white;">add year</td>
				</tr>
				<!-- Current Liabilities block start -->

				<tr id="lineItemLevel">
					<td></td>
					<td id="lineItem"><input type="text" id="lineItemName"
						value="" placeholder="Line Items" size="15" /></td>
					<td style="text-align: right;"><input type="text"
						id="lineItemAmount1" value="" placeholder="Amount" class="amount"
						size="10" /></td>
					<td id="addLineItemBtn"><input type="submit"
						id="addLineItemMore" value=" " name="submit" /></td>
					<td>add line-items</td>
				</tr>

			</table>

		</div>
		<!-- Below the line items end -->
		<div id=liabilitiesDiv>
			<table>
				<tr>
					<td><input type="submit" id="totalIncome" value="Total"></td>
				</tr>
			</table>
		</div>

	</div>


</body>
</html>