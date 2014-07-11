<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/training-and-certification.js"></script>
<title><spring:message code="mainTab.training.and.certification.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<div id="policyListPage" class="row-fluid">
    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i>

        <h2><spring:message code="training.and.certification"/></h2>

        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">
        <div class="container-fluid">

            <div id="trainingtestDiv" class="centerAlign">
                <div class="span6">
                    <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-ok-sign"></i>

                        <h2><spring:message code="question.set.level"/></h2>

                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                    <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid">
                            <form:form  id="questionForm" method="post" action="">
                            <input type="hidden" id="trainingId" name="trainingId" value="${trainingId}"/>
                            <div id="questionsDiv">
                                <div class="question">
                                    <div id="queryDiv">
                                        <c:forEach items="${questionSet}" var="question" varStatus="loop">
                                            <div class="bpcQuestion">
                                                <c:out value="${loop.index+1}"/>. <c:out value="${question.text}"/>
                                                <input type="hidden" id="question[${loop.index}].id" value="<c:out value="${question.id}"/>" />
                                            </div>
                                            <div class="answerCss">
                                                <div class="control-group">
                                                    <label class="control-label"></label>
                                                      <c:forEach items="${question.trainingQuestionAnswers}" var="option" varStatus="optionIndex">
                                                        <div class="controls">
                                                            <input id="demo_box_<c:out value="${loop.index}"/><c:out value="${optionIndex.index}"/>" class="css-radio" name="myRadio_<c:out value="${question.id}"/>" type="radio" value="<c:out value="${option.id}"/>"/>
                                                            <label for="demo_box_<c:out value="${loop.index}"/><c:out value="${optionIndex.index}"/>" class="css-label radio"><c:out value="${option.optionText}"/></label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div id="submitDiv">
                                    <c:if test="${!empty questionSet}">
                                        <button type="submit" id="submitAnswer" class="btn btn-mini btn-primary" >Submit</button>
                                    </c:if>
                                    <c:if test="${empty questionSet}" >
                                        Questions to be uploaded shortly
                                    </c:if>
                                </div>
                            </div>
                            </form:form>
                        </div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                </div>
            </div>


        </div>
    </div>

</div>
<script>
    /*submitAnswer();*/
    var user =${user};
</script>
