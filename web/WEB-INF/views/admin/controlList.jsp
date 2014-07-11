<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: amjad
  Date: 11/8/13
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>Controls</title>

<%@ include file="/WEB-INF/views/message.jsp" %>


<div class="row-fluid">
    <div class="controlspan span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2>Controls</h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="customFlexigridCss">
                    <table id="controlListTable" >
                    </table>
                </div>

            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>


    <div id="addNewControl" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h3>Control</h3>
        </div>
        <div class="modal-body">
            <form:form  id="projectForm" name="addConForm" cssClass="form-horizontal contentForm" data-validate="parsley" method="post" action="addControl.html?controlId=0" commandName="control">
                <div class="control-group">
                    <label class="control-label" for="transactionType"><spring:message code="reactive.project.transaction.type" /> <span class="requiredField"> *</span></label>
                    <div class="controls">
                        <form:select path="transactionType" name="items" id="transactionType">
                            <form:options items="${transactionType}" itemLabel="value" itemValue="value"/>
                        </form:select>
                        <ul id="parsley-6212773541919887" class="parsley-error-list" style="display: block;">
                            <li class="equalto" style="display: list-item;"><form:errors path="transactionType"/></li></ul>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="controlName"><spring:message code="adminHeader.control.name" /> </label>
                    <div class="controls">
                        <form:textarea path="name" id="controlName" class="span10 paddingForTextArea" placeholder="Write a control..."></form:textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="user.form.isActive" /> </label>
                    <div class="controls">
                        <form:checkbox path="active" id="activeId"/>
                    </div>
                </div>
                <%-- <div class="formFooter">
                    <button type="submit" class="btn btn-primary"><spring:message code="save.button.title"/></button>
                    <button type="reset" class="btn"><spring:message code="login.button.reset"/></button>
                </div>--%>
            </form:form>

        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button id="saveControl" class="btn btn-primary">Save changes</button>
        </div>
    </div>

</div>
