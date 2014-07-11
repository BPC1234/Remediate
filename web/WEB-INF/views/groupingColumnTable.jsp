<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    final String contextPath = request.getContextPath();
%>

<div id='jqxWidget' class="bDiv">
    <div id="groupTable"></div>
</div>

<%--protocol 1--%>

<script type='text/javascript'>
    $(document).ready(function () {

    var totalWidth = ${totalWidth};
    var widthList =[
        <c:forTokens items="${widthList}" delims="," var="name">
        '<c:out value="${name}"/>',
        </c:forTokens>
    ];

    var dbColumnField =[
    <c:forTokens items="${finalHeaderlist}" delims="," var="name">
    '<c:out value="${name}"/>',
    </c:forTokens>
    ];
    var count=0;
    var widthCount = 0;
    var column = [
      <c:forTokens items="${printColList}" delims="," var="name">
        {
            text: '<c:out value="${name}"/>', datafield:dbColumnField[count++],width:widthList[widthCount++]
        },
        </c:forTokens>
       ];

    var myArray = [
        <c:forEach items="${arrayList}" var="item">
        {
            <c:forEach items="${item}" var="itemVal">
              <c:out value="${itemVal.key}"/> :"<c:out value="${itemVal.value}"/>",
            </c:forEach>
    },
    </c:forEach>
    ];

    var source =
    {
        localdata: myArray,
        datatype: "array"
    };
    $("#groupTable").jqxGrid(
            {
                width: (totalWidth-3),
                height: 340,
                source: source,
                groupable: true,

                columns: column,
                groups:[dbColumnField[0]]
            });


    });
</script>