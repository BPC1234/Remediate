<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table id="commonTable" style="font-size:9px;border-collapse:collapse;">
    <thead>
    <tr>
        <c:forTokens items="${printColList}" delims="," var="headerName">
            <th <%--style="border:1px solid #000000"--%>><c:out value="${headerName}"/></th>
        </c:forTokens>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="list" varStatus="loopStatus">
        <tr>
            <c:forTokens items="${finalHeaderlist}" delims="," var="name">
                <c:set var="column" value="${fn:trim(name)}"/>
                <td <%--style="border:1px solid #000000"--%>><c:out value="${list[column]}"/></td>
            </c:forTokens>
        </tr>
    </c:forEach>
    </tbody>
</table>

