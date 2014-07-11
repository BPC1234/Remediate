<div id="breadcrumb">
    <ul id="breadcrumb">
        <c:url var="home" value="/welcome"/>
        <c:url value="/images/site/breadcrumb-home.png" var="homeimage"/>
        <c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
            <c:choose>
                <c:when test="${status.index==0}">
                    <li><a id="first" href="${home}"><img src="${homeimage}"/></a></li>
                </c:when>
                <c:when test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
                    <li id="current">${bc.name}</li>
                </c:when>
                <c:otherwise>
                    <li><a href="${bc.value}">${bc.name}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>