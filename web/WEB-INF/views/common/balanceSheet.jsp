
<%
	final String contextPath = request.getContextPath();
%>
<html>
<head>
<title>Insert title here</title>
<script
	src="<%=contextPath%>/resources/js/due-dilligance-landing.js">
	
	</script>
</head>


<body>

	<div id="sheetDiv">
		<div id="sheetTitle">
			<b><strong>Balance Sheet</strong></b>
		</div>
		<div id="assetDiv">
			<table>
				<tr style="background-color:#66CCFF;">
					<td colspan="2" id="asset" >Assets</td>
					<td id="year" style = "text-align:right;"><input type="text" id ="selectedYear1" class="year" value ="" placeholder ="Year"  size="10"/></td>
				    <td id = "addYearBtn"><input type="submit" id="addYear" value =" " name="submit"/></td>
				    <td style="color:white;">add year</td>
				</tr>
				<!-- Current assets block start -->
				<tr>
				  
					<td colspan="3" id ="assetType">Current Assets</td>
				</tr>
				<tr id = "assetLevel">
				  <td></td>
					<td id="assetItem" ><input type="text" id ="assetName"  value ="" placeholder ="Current Assets" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="amount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addMoreBtn"><input type="submit" id="addMore" value =" " name="submit"/></td>
					<td>add asset</td>
				</tr>
				<!-- Current assets block end -->
				<!-- fixed assets block start -->
				<tr>
					<td colspan="3" id ="assetType">Fixed (Long-Term) Assets</td>
				</tr>
				<tr id = "fixAssetLevel">
				  <td></td>
					<td id="assetItem" ><input type="text" id ="fixAssetName"  value ="" placeholder ="Fixed Assets" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="fixedAmount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addMoreFixBtn"><input type="submit" id="addMoreFix" value =" " name="submit"/></td>
					<td>add asset</td>
				</tr>
				<!-- fixed assets block end -->
				<!-- other assets block start -->
				<tr>
					<td colspan="3" id ="assetType">Other Assets</td>
				</tr>
				<tr id = "otherAssetLevel">
				  <td></td>
					<td id="assetItem" ><input type="text" id ="otherAssetName"  value ="" placeholder ="Other Assets" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="otherAmount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addMoreOtherBtn"><input type="submit" id="addMoreOther" value =" " name="submit"/></td>
					<td>add asset</td>
				</tr>
				<!-- other assets block end -->
			</table>

		</div>
        <!-- liabilities block start -->
		<div id="liabilitiesDiv">
		<table>
				<tr style="background-color:#66CCFF;">
					<td colspan="2" id="asset" >Liabilities and Owner's Equity</td>
					<td id="year" style = "text-align:right;"><input type="text" id ="selectedLibYear1" class="year" value ="" placeholder ="Year"  size="10"/></td>
				    <td id = "addLibYearBtn"><input type="submit" id="addLibYear" value =" " name="submit"/></td>
				    <td style="color:white;">add year</td>
				</tr>
				<!-- Current Liabilities block start -->
				<tr>
				  
					<td colspan="3" id ="assetType">Current Liabilities </td>
				</tr>
				<tr id = "libLevel">
				  <td></td>
					<td id="libItem" ><input type="text" id ="libName"  value ="" placeholder ="Current Liabilities" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="libAmount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addLibMoreBtn"><input type="submit" id="addLibMore" value =" " name="submit"/></td>
					<td>add liabilities</td>
				</tr>
				<!-- Current assets block end -->
				<!-- Long-Term Liabilities block start -->
				<tr>
					<td colspan="3" id ="libType">Long-Term Liabilities</td>
				</tr>
				<tr id = "fixLibLevel">
				  <td></td>
					<td id="libItem" ><input type="text" id ="fixLibName"  value ="" placeholder ="Long-Term Liabilities" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="libFixedAmount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addLibMoreFixBtn"><input type="submit" id="addLibMoreFix" value =" " name="submit"/></td>
					<td>add liabilities</td>
				</tr>
				<!-- Long-Term Liabilities block end -->
				<!-- Owner's Equity block start -->
				<tr>
					<td colspan="3" id ="libType">Owner's Equity</td>
				</tr>
				<tr id = "otherLibLevel">
				  <td></td>
					<td id="libItem" ><input type="text" id ="otherLibName"  value ="" placeholder ="Owner's Equity" size="15"/></td>
					<td style = "text-align:right;"><input type="text" id ="libOtherAmount1" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>
					<td id = "addLibMoreOtherBtn"><input type="submit" id="addLibMoreOther" value =" " name="submit"/></td>
					<td>add liabilities</td>
				</tr>
				<!-- Owner's Equity block end -->
			</table>

		</div>
		<!-- liabilities block end -->
		
		<div id=liabilitiesDiv>
			<table>
				<tr>
					<td><input type="submit" id="totalBalance" value="Total"></td>
				</tr>
			</table>
		</div>
		</div>


</body>
</html>