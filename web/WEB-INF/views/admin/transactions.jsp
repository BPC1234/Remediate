<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%	final String contextPath = request.getContextPath(); %>
<body>
<div align="center">
	<div align="center">
		<H6>Status of Transaction Import</H6>
		Total File: <b>${totalFile} </b><br>
		Successful Import: <b>${passedFile} </b><br>
		Failed Import: <b>${failedFile} </b><br>
	</div>
</div>
<p>go to <a href="./landingPage.html" style="color: green;">home page</a></p>
</body>