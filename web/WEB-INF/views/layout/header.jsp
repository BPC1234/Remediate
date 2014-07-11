<%@ page import="java.security.Principal" %>
<%@ page import="com.dsinv.abac.entity.Role" %>
<%@ page import="com.dsinv.abac.entity.User" %>
<%@ page import="com.dsinv.abac.Entity" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


<%
    final String contextPath = request.getContextPath();
    final Principal loggedUser = request.getUserPrincipal();
    final Long loggedUserId = (Long) request.getSession().getAttribute("loggUserId");
    final String loggedUserEmail = request.getSession().getAttribute("loggedUserEmail") != null ? (String) request.getSession().getAttribute("loggedUserEmail"):"";
%>
<script src="<%= contextPath %>/resources/js/header.js"></script>

<input id="contextPath" type="hidden" value="<%= contextPath %>"/>
<input id="loggUserId" type="hidden" value="<%= loggedUserId %>"/>

<c:set var="loggedUserName" value="<%=loggedUser.getName()%>"/>
<c:set var="adminUser" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="employee" value="<%=Role.EMPLOYEE.getLabel()%>"/>
<c:set var="legal" value="<%=Role.LEGAL.getLabel()%>"/>
<c:set var="ia_analyst" value="<%=Role.IA_ANALYST.getLabel()%>"/>
<c:set var="ia_manager" value="<%=Role.IA_MANAGER.getLabel()%>"/>
<c:set var="compliance" value="<%=Role.COMPLIANCE.getLabel()%>"/>

<!-- ==================== TOP MENU ==================== -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a id="headerTitle" class="brand" href="#"><img alt="" class="logoDiv"
                                                            src="<%= contextPath %>/resources/images/new_logo.png">&nbsp;<strong
                    class="bpcClass"><spring:message code="adminHeader.bpc"/></strong>&nbsp;<spring:message
                    code="adminHeader.header"/></a>

            <div class="nav pull-right">
                <form class="navbar-form">
                    <div class="input-append">
                        <div class="collapsibleContent">
                            <security:authorize ifAnyGranted="${adminUser}">
                                <a href="#settingsContent" class="sidebar"><span
                                        class="add-on add-on-middle add-on-mini add-on-dark" id="settings"><i
                                        class="icon-cog icon-cogCustom "></i></span></a>
                            </security:authorize>
                            <a href="#profileContent" class="sidebar"><span class="add-on add-on-mini add-on-dark"
                                                                            id="profile"><i class="icon-user"></i><span
                                    class="username">${fn:toUpperCase(fn:substring(loggedUserName,0,1))}${fn:toLowerCase(fn:substring(loggedUserName,1,fn:length(loggedUserName)))}</span></span></a>
                        </div>
                        <a href="#collapsedSidebarContent" class="collapseHolder sidebar"><span
                                class="add-on add-on-mini add-on-dark"><i class="icon-sort-down"></i></span></a>
                    </div>
                </form>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<!-- ==================== END OF TOP MENU ==================== -->

<!-- ==================== SIDEBAR ==================== -->
<div class="hiddenContent">
    <!-- ==================== SIDEBAR COLLAPSED ==================== -->
    <div id="collapsedSidebarContent">
        <div class="sidebarDivider"></div>
        <div class="sidebarContent">
            <ul class="collapsedSidebarMenu">
                <li><a href="#tasksContent" class="sidebar">Tasks
                    <div class="notifyCircle cyan">3</div>
                    <i class="icon-chevron-sign-right"></i></a></li>
                <li><a href="#notificationsContent" class="sidebar">Notifications
                    <div class="notifyCircle orange">9</div>
                    <i class="icon-chevron-sign-right"></i></a></li>
                <li><a href="#messagesContent" class="sidebar">Messages
                    <div class="notifyCircle red">12</div>
                    <i class="icon-chevron-sign-right"></i></a></li>
                <li><a href="#settingsContent" class="sidebar">Settings<i class="icon-chevron-sign-right"></i></a></li>
                <li><a href="#profileContent" class="sidebar"><%=loggedUser.getName()%><i
                        class="icon-chevron-sign-right"></i></a></li>
                <security:authorize ifAnyGranted="${adminUser}">
                    <li class="sublevel"><a href="#">edit profile<i class="icon-user"></i></a></li>
                </security:authorize>
                <security:authorize ifAnyGranted="${adminUser}">
                    <li class="sublevel"><a href="#">change password<i class="icon-lock"></i></a></li>
                </security:authorize>
                <li class="sublevel"><a href="#">logout<i class="icon-off"></i></a></li>
            </ul>
        </div>
    </div>
    <!-- ==================== END OF SIDEBAR COLLAPSED ==================== -->
    <!-- ==================== SIDEBAR SETTINGS ==================== -->

    <div id="settingsContent">
        <div class="sidebarDivider"></div>
        <div class="sidebarContent">
            <a href="#collapsedSidebarContent" class="showCollapsedSidebarMenu"><i class="icon-chevron-sign-left"></i>

                <h1> Settings</h1></a>

            <h1>Settings</h1>

            <footer>
                <div id="userMangDivId" class="profileSettingBlock"><i class="icon-male"></i><spring:message
                        code="adminHeader.userManagement"/></div>
                <div id="weightiningDivId" class="profileSettingBlock"><i class="icon-shield"></i><spring:message
                        code="proactiveProject.ratio.setup.title2"/></div>
                <div id="controlDivId" class="profileSettingBlock"><i class="icon-cog"></i><spring:message
                        code="adminHeader.controls"/></div>
                <%--<div id="policiesDivId" class="profileSettingBlock"><i class="icon-tags"></i><spring:message code="adminHeader.policies"/></div>--%>
                <div id="reactiveProjectMangDivId" class="profileSettingBlock"><i class="icon-cogs"></i><spring:message
                        code="landingPage.investigations"/></div>
                <div id="realTimeMonIntervalSetUpDivId" class="profileSettingBlock"><i
                        class="icon-asterisk"></i><spring:message code="adminHeader.realTimeMonitoringIntervalSetup"/>
                </div>
                <div id="holidayId" class="profileSettingBlock"><i class="icon-asterisk"></i><spring:message
                        code="adminHeader.holiday.rule.level"/></div>
            </footer>
            <footer>
                <div id="importTrxDivId" class="profileSettingBlock"><i class="icon-upload-alt"></i><spring:message
                        code="adminHeader.import.transaction"/></div>
                <div id="ruleCreationDivId" class="profileSettingBlock"><i class="icon-qrcode"></i><spring:message
                        code="adminHeader.rule"/></div>
            </footer>
        </div>
    </div>
    <!-- ==================== END OF SIDEBAR SETTINGS ==================== -->

    <!-- ==================== SIDEBAR PROFILE ==================== -->
    <div id="profileContent">
        <div class="sidebarDivider"></div>
        <div class="sidebarContent">
            <a href="#collapsedSidebarContent" class="showCollapsedSidebarMenu"><i class="icon-chevron-sign-left"></i>

                <h1> My account</h1></a>

            <h1>My account</h1>

            <div class="profileBlock">
                <div class="">
                    <div class="usernameHolder">${fn:toUpperCase(fn:substring(loggedUserName,0,1))}${fn:toLowerCase(fn:substring(loggedUserName,1,fn:length(loggedUserName)))}
                    </div>
                </div>
                <div class="profileInfo">
                    <%--<p><i class="icon-map-marker"></i> Piestany, SK</p>--%>

                    <p><i class="icon-envelope-alt"></i><div id="userEmail"><%=loggedUserEmail%></div></p>

                    <%--<p><i class="icon-globe"></i> tattek.com</p>--%>

                    <p class="aboutMe">
                    </p>
                </div>
                <footer style="margin-top: 36px">
                    <security:authorize ifAnyGranted="${adminUser}">
                        <div id="changeProfileDivId" class="profileSettingBlock editProfile"><i class="icon-user"></i>edit
                            profile
                        </div>
                    </security:authorize>
                    <security:authorize ifAnyGranted="${adminUser}">
                        <div id="changePasswordDivId" class="profileSettingBlock changePassword"><i
                                class="icon-lock"></i>change
                            password
                        </div>
                    </security:authorize>
                    <div id="logoutDivId" class="profileSettingBlock logout <security:authorize access="hasAnyRole( '${compliance}','${legal}','${ia_analyst}','${ia_manager}','${employee}')"> logoutCss</security:authorize>"><i class="icon-off"></i>logout</div>
                </footer>
            </div>
        </div>
    </div>
    <!-- ==================== END OF SIDEBAR PROFILE ==================== -->

</div>
<!-- ==================== END OF SIDEBAR ==================== -->
<input id="mainMenuSelectionTxtId" type="hidden" value="${mainTabId}"/>
<input type="hidden" id="submenuId" value="${subTabId}">
<!-- ==================== MAIN MENU ==================== -->
<div class="mainmenu noDisplay">
    <div class="container-fluid">
        <ul class="nav">
            <li class="collapseMenu"><a href="#"><i class="icon-double-angle-left"></i>hide menu<i
                    class="icon-double-angle-right deCollapse"></i></a></li>
            <li class="divider-vertical firstDivider"></li>
            <li class="left-side" id="dashboard"><a href="landingPage.html"><%--<i class="icon-dashboard"></i>--%>
            <spring:message code="dashboard"/></a></li>

            <security:authorize ifAnyGranted="${adminUser}, ${ia_analyst}, ${ia_manager}, ${employee}, ${compliance}, ${legal} ">
                <li class="divider-vertical"></li>
                <li id="transactionMonitoring" class="dropdown assignment">

                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"
                       id="transactionMonitoringId"><%--<i class="icon-list"></i>--%>
                        <spring:message code="realtime.workFlow"/>
                    </a>
                    <ul class="dropdown-menu">
                        <security:authorize access="hasAnyRole('${ia_analyst}', '${ia_manager}', '${adminUser}', '${legal}')">
                        <li><a tabindex="-1" id="trxMoniId" href="../realtime/RealtimeMonitoringWorkflow.html"><spring:message
                                code="realtime.transaction.summary.view"/></a></li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('${adminUser}', '${ia_analyst}')">
                            <li><a tabindex="-1" href="../realtime/transactionApprove.html"><spring:message
                                code="landingPage.transactionApprove"/></a></li>
                            <li><a tabindex="-1" href="../realtime/transactionSearch.html"><spring:message
                                code="mainTab.transactionSearch.title"/></a></li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('${adminUser}','${employee}','${ia_analyst}','${legal}','${ia_manager}','${compliance}')">
                            <li><a tabindex="-1" href="../realtime/myTransactions.html"><spring:message
                                code="menu.my.transactions"/></a></li>
                        </security:authorize>
                    </ul>
                </li>
            </security:authorize>


            <security:authorize ifAnyGranted="${adminUser}, ${compliance}">
                <li class="divider-vertical"></li>
                <li id="newRiskAss" class="dropdown riskMenu">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"
                       id="formElements"><%--<i class="icon-list"></i>--%>
                        <spring:message code="landingPage.riskAssessment"/>
                            <%--<span class="label label-pressed">3</span>--%></a>
                    <ul class="dropdown-menu">
                <security:authorize access="hasAnyRole('${compliance}', '${adminUser}')">
                        <li><a tabindex="-1" href="../riskasst/weightedScreen.html"><spring:message
                                code="landingPage.newRiskAssessment"/></a>
                        </li>
                        <li><a tabindex="-1" href="../riskasst/ContinueRiskAssessmentWorkFlow.html"><spring:message
                                code="landingPage.continueRiskAssessment"/></a></li>
                </security:authorize>
                <security:authorize access="hasAnyRole('${compliance}','${adminUser}')">
                        <li class="dropdown-submenu"><a tabindex="-1" href="#"><spring:message
                                code="thirdPartyReview"/></a>
                            <ul class="dropdown-menu sub-menu">
                                <li><a href="../riskasst/existingVendorsForReputaionalReview.html"><spring:message
                                        code="dueDilligance.new.reputational.review"/></a></li>
                                <li><a href="../riskasst/newReputationalFormSearch.html"><spring:message
                                    code="dueDilligance.landingPage.header.ReputationalReview"/></a></li>
                                <li><a href="../riskasst/financialReview.html"><spring:message
                                        code="dueDilligance.landingPage.header.financialReview"/></a></li>
                            </ul>
                        </li>
                </security:authorize>
                    </ul>
                </li>
            </security:authorize>
            <security:authorize ifAnyGranted="${adminUser}, ${ia_manager}, ${ia_analyst}">
                <li class="divider-vertical"></li>
                <li id="icga" class="dropdown internal">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"
                       id="interface"><%--<i class="icon-pencil"></i>--%>
                        <spring:message code="landingPage.internalControls"/>
                            <%--<span class="label label-pressed">2</span>--%></a>
                    <ul class="dropdown-menu">
                        <li><a href="../icga/existingCtrlGapAnalysis.html?controlIds=0"><spring:message
                                code="landingPage.existingControls"/></a>
                        </li>
                        <li><a tabindex="-1" href="../icga/InternalCtrlGapAnalysisAC.html?icga=0"><spring:message
                                code="landingPage.analyzeByControls"/></a></li>
                    </ul>
                </li>
            </security:authorize>
            <security:authorize ifAnyGranted="${adminUser}, ${legal}">
                <li class="divider-vertical"></li>
                <li id="wiltleblower"><a href="#"><%--<i class="icon-tint"></i>--%><spring:message
                        code="landingPage.whistle.blower"/></a></li>
            </security:authorize>

            <security:authorize ifAnyGranted="${adminUser}, ${legal}">
                <li class="divider-vertical"></li>
                <li id="investigations"><a href="../reactive/ReactiveWorkflow.html"><%--<i class="icon-tint"></i>--%><spring:message
                        code="landingPage.investigations"/></a></li>
            </security:authorize>
            <security:authorize ifAnyGranted="${compliance}, ${adminUser}, ${ia_analyst}, ${ia_manager}">
                <li class="divider-vertical"></li>
                <li id="reporting" class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" ><spring:message code="landingPage.reporting"/></a>
                    <ul class="dropdown-menu reporting parentdiv">
                        <security:authorize access="hasAnyRole('${compliance}', '${adminUser}')">
                            <li class="dropdown-submenu "><a tabindex="-1" href="#"><spring:message code="menu.compliance"/></a>
                                 <ul class="dropdown-menu sub-menu" style="min-width:170px;">
                                     <li><a href="../report/policyReviewCertification.html"><spring:message
                                             code="menu.reporting.policyReviewCertification"/></a></li>
                                     <li><a href="../report/trainingReview.html"><spring:message
                                             code="menu.reporting.trainingReviewCertification"/></a></li>
                                 </ul>
                             </li>
                        </security:authorize>
                         <security:authorize access="hasAnyRole('${ia_analyst}','${ia_manager}', '${adminUser}')">
                                <li><a href="#"><spring:message code="menu.reporting.transaction"/></a></li>
                        </security:authorize>
                    </ul>
                </li>
                    <ul class="dropdown-menu">
                        <li><a tabindex="-1" id="compliance" href="#"><spring:message
                                code="menu.compliance"/></a></li>
                        <li><a tabindex="-1" href="transactionApprove.html"><spring:message
                                code="landingPage.transactionApprove"/></a></li>
                    </ul>
                </li>
            </security:authorize>



    <li class="divider-vertical"></li>
            <security:authorize ifAnyGranted="${adminUser},${employee},${compliance},${ia_analyst},${ia_manager}, ${legal}">
                <li class="divider-vertical"></li>
                <li id="tranningAndCertification"><a href="../training/trainingList.html"><%--<i class="icon-th-large"></i>--%>
                    <spring:message code="mainTab.training.and.certification.title"/></a></li>
            </security:authorize>

            <security:authorize ifAnyGranted=" ${adminUser},${employee}, ${ia_analyst}, ${ia_manager}, ${compliance}, ${legal}">
                    <li class="divider-vertical"></li>
                    <li id="policyAndProcedure"><a href="../policy/policyList.html"><%--<i class="icon-th-large"></i>--%>
                        <spring:message code="procedures.and.certification"/></a></li>
            </security:authorize>

            <li class="divider-vertical"></li>

        </ul>
    </div>
</div>

<script>
    $(document).ready(function () {
     if(!$("#userEmail").html().length > 0){
         console.log("user email NOT found.Try to retrieve...")
           var user;
             $.ajax({
                 url : "../admin/getUserObject.html" ,
                 type : 'GET',
                 dataType: 'json',
                 async: false,
                 data : {},
                 success : function(data) {
                     user = data;
                     $("#userEmail").html(user.email);
           }
        })
      }
     });


</script>
<!-- ==================== END OF MAIN MENU ==================== -->
