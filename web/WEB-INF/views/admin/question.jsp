<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/admin/admin.js"></script>
<title><spring:message code="mainTab.training.and.certification.title"/></title>

<%@ include file="/WEB-INF/views/message.jsp" %>

<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="labelOneUser" value="<%=Role.EMPLOYEE.getLabel()%>"/>



<div id="policyListPage" class="row-fluid">
    <!-- ==================== SPAN12 HEADLINE ==================== -->
    <div class="containerHeadline">
        <i class="icon-th"></i><h2><spring:message code="training.and.certification"/></h2>
        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
    </div>
    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->
    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
    <div class="floatingBox">
        <div class="container-fluid">

            <div id="transactionSearchBlockId" class="centerAlign">
                <div class="span6">
                    <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-ok-sign"></i>

                        <h2><spring:message code="add.question.level"/></h2>

                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                    <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid">
                            <div id="questionsDiv">
                                 <div class="question">
                                     BPC Question
                                     <textarea id="questionText" class="span12 paddingForTextArea" placeholder="BPC Question" name="questionText">

                                     </textarea>
                                 </div>
                                 <div class="question" >
                                     Correct Answer
                                     <textarea id="correctanswer" class="span12 paddingForTextArea" placeholder="Correct Answer" name="questionText">

                                     </textarea>
                                </div>
                                <div class="question" >
                                    Wrong Answer(S)
                                    <textarea   class="span12 paddingForTextArea wronganswer" placeholder="Correct Answer" name="questionText">

                                    </textarea>
                                </div>
                                <div class="question" >
                                    (Optional)
                                    <textarea   class="span12 paddingForTextArea wronganswer" placeholder="Wrong Answer" name="questionText">

                                    </textarea>
                                </div>
                                <div class="question" >
                                    (Optional)
                                    <textarea  class="span12 paddingForTextArea wronganswer" placeholder="Wrong Answer" name="questionText">

                                    </textarea>
                                </div>
                                <div class="question" >
                                    Add Comments
                                    <textarea   class="span12 paddingForTextArea wronganswer" placeholder="Enter description and / or link to source for this question." name="questionText">

                                    </textarea>
                                </div>

                                <div id="buttonDiv" >
                                    <button class="btn" aria-hidden="true" data-dismiss="modal">Reset</button>
                                    <button id="uploadTraining" class="btn btn-primary">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                </div>
            </div>


        </div>
    </div>

</div>
