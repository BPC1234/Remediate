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
                            <div id="questionsDiv">
                                <div class="question">
                                    <div style="text-align: right; width: 85%">
                                      <%--  Set No Of Questions: <input type="text" id="noOfQuestions" width="10"/>--%>
                                        <button id="trainingDetails" class="btn btn-mini btn-info" type="button">Training Details</button>
                                        <button id="addQuestionBtn" class="btn btn-mini btn-primary" type="button">Add Question</button>
                                    </div>

                                    <div id="queryDiv">
                                        <c:forEach items="${questions}" var="list" varStatus="loop">
                                            <div class="aQuestionDiv">
                                                <input type="hidden" id="questionId" value="${list.id}"/>
                                                <div class="bpcQuestion">
                                                    <c:out value="${loop.index+1}"/>. <c:out value="${list.text}" />
                                                    <span class="badge badge-success editQuestion"
                                                          >edit</span>
                                                    <span class="badge badge-important deleteQuestion" >delete</span>
                                                </div>
                                                <div class="answerCss">
                                                    <div class="control-group">
                                                        <label class="control-label"></label>
                                                        <c:forEach items="${list.trainingQuestionAnswers}" var="option"
                                                                   varStatus="optionIndex">

                                                            <div class="controls">
                                                                <input id="demo_box_<c:out value="${loop.index}"/><c:out value="${optionIndex.index}"/>"
                                                                       class="css-radio"
                                                                       name="myRadio_<c:out value="${loop.index}"/>"
                                                                       type="radio" <c:if
                                                                        test="${option.correct == true}"> checked="checked" </c:if>/>
                                                                <label for="demo_box_<c:out value="${loop.index}"/><c:out value="${optionIndex.index}"/>"
                                                                       class="css-label radio"><c:out
                                                                        value="${option.optionText}"/></label>
                                                                <input type="hidden" class="optionId" value="${option.id}"/>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>


                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                </div>
            </div>


        </div>
    </div>
    <a id="modalOpenLink" style="display: none;" href="#addQuestion" role="button" class="add" data-toggle="modal"></a>
    <div id="addQuestion" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Add Question</h3>
        </div>
        <div class="modal-body">
            <input type="hidden" id="trainingId" name="trainingId" value="${trainingId}"/>
            <form:form  id="questionForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="question">
                <div class="control-group" id="questionTextDiv">
                    <label class="control-label" for="text"><spring:message code="fcpa.training.question.level"/> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:textarea path="text" id="text" class="span10 paddingForTextArea" placeholder="FCPA Question...." ></form:textarea>
                    </div>
                </div>
            <div class="controls" id="optionsDiv">

            </div>
                <div class="control-group" id="addMoreOption">
                    <div id="addQAnswerDiv">
                        <input id="addQAnswer" type="button" name="submit" value=" "/>Option
                    </div>
                </div>

            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Reset</button>
            <button id="addNewQuestion" class="btn btn-primary">Save</button>
        </div>
    </div>

    <a id="trainingDetailsA" style="display: none;" href="#trainingDetailsModal" role="button" class="add" data-toggle="modal"></a>
    <div id="trainingDetailsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <%--<h3>Add Question</h3>--%>
        </div>
        <div class="modal-body">
            <form:form  cssClass="form-horizontal contentForm" commandName="trainingObj">
                <div class="control-group">
                    <label class="control-label"><spring:message code="number.of.question"/> :</label>
                    <div class="controls">
                        <form:input path="noOfQuestion" id="numberOfQuestion" cssClass="span10"/>
                    </div>
                </div>
            <div class="controls" >

            </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="pass.number"/> :</label>
                    <div class="controls">
                        <form:input path="passNumber" id="passNumber" cssClass="span10"/>
                    </div>
                </div>

            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Reset</button>
            <button id="saveDetail" class="btn btn-primary">Save</button>
        </div>
    </div>


    <a id="questionEditModel" style="display: none;" href="#editQuestion" role="button" class="add" data-toggle="modal"></a>
    <div id="editQuestion" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Add Question</h3>
        </div>
        <div class="modal-body">
            <input type="hidden" id="qId" name="qId" value=""/>
            <form:form  id="questionForm" class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="question">
                <div class="control-group" id="optionTextDiv">
                    <label class="control-label" for="text"><spring:message code="fcpa.training.question.level"/></label>
                    <div class="controls">
                        <textarea id="editText" class="span10 "></textarea>
                    </div>
                </div>
                <div id="editOptionsDiv">

                </div>
                <div class="controls" id="optionsDiv"></div>

            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Reset</button>
            <button id="editQuestionBtn" class="btn btn-primary">Save</button>
        </div>
    </div>

</div>
<script>
    addNewQuestion();
    addQuestionAnswer();
    saveTrainingDetails();
    var user =${user};
    var trainingObj = '${trainingObj}';
    saveEditedQuestion();
</script>
