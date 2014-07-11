<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	final String contextPath = request.getContextPath();
%>
<script src="<%=contextPath%>/resources/js/due-dilligance-landing.js"></script>

<title>Existing Vendor for Financial Review</title>
<style>
.dataTables_filter input[type="text"] {
	margin-bottom: 0;
	padding: 0px 0px;
}

.dataTables_length select {
	height: 27px;
}

input[type="text"].searcVendor {
	margin-bottom: 0;
	height: 15px;
	padding: 4px 6px;
}

td {
	padding: 3px;
}

input[type="button"].reputationalReview {
	background-color: #5CADFF;
	border-radius: 4px 4px 4px 4px;
	padding: 3px;
}

input[type="button"]:HOVER.reputationalReview {
	background-color: #4297d7;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
}
</style>

</head>
<body>

	<div id="replaceAjaxDiv">
		<h3>
			<strong><spring:message code="dueDilligance.landingPage.searchVendorsForFinancialReview" /></strong>
		</h3>
		<br />
		<div id="notaccordion">
			<h3 style="margin-top: 10px; text-align: left; font-size: 15px">
				<a href="#"><strong><spring:message
							code="dueDilligance.landingPage.VendorsForFinancialReview" /></strong></a>
			</h3>
			<div style="display: none">
				<br />
				<div id="searchBox" class="boxBorder"
					style="display: inline-block; padding-top: 10px; padding-bottom: 0px; margin-left: 6px;">
					<form:form>
						<table border="0" align="center" style="display: inline-block;">
							<tr>
								<td align="left" width=""><spring:message
										code="vendor.name"></spring:message> :</td>
								<td align="left"><input class="searcVendor" size="45"
									type="text" id="searchName" value="" /></td>
							</tr>
							<tr>
								<td align="left"><spring:message code="vendor.zip">
									</spring:message>:</td>
								<td align="left"><input class="searcVendor" size="15"
									type="text" id="searchZip" value="" /></td>
							</tr>
							<tr>
								<td align="left"><spring:message code="vendor.country"></spring:message>:</td>
								<td align="left">
									<%-- 	<select>
									<option value="">${countryList}</option>
								</select> --%> 
								<form:select path="countryCode"
										items="${countryList}" itemLabel="name" itemValue="code" id="countryCode"></form:select>
								</td>
							</tr>
							<tr>
								<td align="left" colspan="2"><input class="searcVendor"
									size="30" type="checkbox" id="" />Not reviewed yet</td>
							</tr>
							<tr>
								<td align="right" colspan="2"><input id="searcVendorForFinancialReview"
									class="reputationalReview" type="button" name="searchVen"
									value='Search' /></td>
							</tr>
						</table>
					</form:form>
				</div>

				</br> </br>
				<div id="ajaxResponse"></div>

			</div>
		</div>
	</div>
</body>
</html>