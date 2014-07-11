<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% final String contextPath = request.getContextPath(); %>
<c:if test="${supportingDocOption == 1}">
<div class="tableDivForFileUploadAjax" id="supportingDocListDiv">
    <c:if test="${supportingDocumentList != null}">
        <div id="supDocDiv" class="alert alert-success">
            <ul>
                <c:forEach items="${supportingDocumentList}" var="list"
                           varStatus="loopStatus">
                    <li>
                        <c:choose>
                            <c:when test="${fn:contains(list.fileName, '.zip')}" >
                                <span class="label label-warning">zip</span>
                            </c:when>

                            <c:when test="${fn:contains(list.fileName, '.docx')||fn:contains(list.fileName, '.doc')}" >
                                <span class="label label-success">docs</span>
                            </c:when>

                            <c:when test="${fn:contains(list.fileName, '.xlsx') || fn:contains(list.fileName, '.xls')}" >
                                <span class="label label-info">xls</span>
                            </c:when>

                            <c:when test="${fn:contains(list.fileName, '.pdf')}" >
                                <span class="label info">pdf</span>
                            </c:when>

                            <c:when test="${fn:contains(list.fileName, '.jpg')||fn:contains(list.fileName, '.jpeg') ||fn:contains(list.fileName, '.png')}" >
                                <span class="label label-primary">image</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-important">others</span>
                            </c:otherwise>
                        </c:choose>
                        <strong>
                            <c:choose>
                                <c:when test="${fn:length(list.fileName) > 26}" >
                                    ${fn:substring(list.fileName, 0, 26)}...

                                </c:when>
                                <c:otherwise>
                                    <c:out value="${list.fileName}"/>
                                </c:otherwise>
                            </c:choose>
                        </strong>
                                <%--<span class="attActions">--%>
                                <i id="${list.id}" class="icon-minus-sign deleteAttachment pull-right"></i>
                                <i id="${list.id}" class="icon-download-alt downloadAttachment pull-right"></i>
                                <i id="${list.id}" class="icon-eye-open viewAttachment pull-right"></i>

                                <%--</span>--%>
                    </li>

                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>
</c:if>
<c:if test="${txPolicyOption == 1}">
<div id="txPolicyListWrapperDiv">
    <c:if test="${txPolicyList != null}">
        <ul>
            <c:forEach items="${txPolicyList}" var="list"
                       varStatus="loopStatus">

                <li>
                    <c:choose>
                        <c:when test="${loopStatus.index%2 == 0}" >
                            <c:set var="cssValue" value="label label-warning"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="cssValue" value="label label-success"/>
                        </c:otherwise>
                    </c:choose>
                    <span class="${cssValue}" >${fn:toUpperCase(fn:substringAfter (list.fileName, "."))}</span>
                    <strong>
                        <c:choose>
                            <c:when test="${fn:length(list.fileName) > 25}" >
                                ${fn:substring(list.fileName, 0, 25)}...
                            </c:when>
                            <c:otherwise>
                                <c:out value="${list.fileName}"/>
                            </c:otherwise>
                        </c:choose>

                    </strong>
                    <i id="${list.id}" class="icon-minus-sign deletePolicyAttachment pull-right"></i>
                    <i id="${list.id}" class="icon-download-alt downloadPolicyAttachment pull-right"></i>
                    <i id="${list.id}" class="icon-eye-open viewPolicyAttachment pull-right"></i>
                </li>

            </c:forEach>
        </ul>
    </c:if>
</div>
</c:if>



