<%@ page import="org.hibernate.Session" %>
<%@ page import="com.dsinv.abac.util.Utils" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%	final String contextPath = request.getContextPath();
%>
<%--<script src="<%= contextPath %>/resources/js/landing-page-new.js"></script>--%>

<div id="dolphincontainer" style="display: none;">
    <div id="dolphinnav">
        <ul>
            <li class="view4"><a id="transactionMonitoring" class="aHref" href="" title=""><span><spring:message code="realtime.workFlow"/></span></a></li>
            <li class="view1"><a id="newRiskAss" class="aHref current" href="" title=""><span><spring:message code="landingPage.riskAssessment"/></span></a></li>
            <li class="view3"><a id="icga" class="aHref" href="" title=""><span><spring:message code="landingPage.internalControls" /></span></a></li>
            <li class="view2"><a id="investigations" class="aHref" href="" title=""><span><spring:message code="landingPage.investigations" /></span></a></li>
            <%--<li class="view4"><a id="transactionMonitoring" class="aHref" href="" title=""><span><spring:message code="landingPage.transactionMonitoring" /></span></a></li>--%>
            <li class="view5"><a id="reporting" class="aHref" href="" title=""><span><spring:message code="landingPage.reporting" /></span></a></li>
            <%--<li class="view6"><a id="transactionSearch" class="aHref" href="" title=""><span><spring:message code="mainTab.transactionSearch.title" /></span></a></li>--%>
            <li class="view7"><a id="trainingCertification" class="aHref" href="" title=""><span><spring:message code="mainTab.trainingCertification" /></span></a></li>
        </ul>
    </div>
   <div id="view1" class="subMenuDiv" style="float:left; width:100%;">
<%--                <ul  style="">
                    <li id="newRiskAssessment"><a class="newRiskAssessment" href="#view11"><spring:message code="landingPage.newRiskAssessment" /></a></li>
                    <li id="continueRisk"><a class="continueRisk" href="#view12"><spring:message code="landingPage.continueRiskAssessment" /></a></li>
                    <li id="thirdPartyReview"><a class="thirdPartyReview" href="#view13"><spring:message code="thirdPartyReview" /></a></li>
                   </ul>
                <div id="view11" style="padding: 0px 0px;"></div>
                <div id="view12" style="padding: 0px 0px;"></div>
                <div id="view13" style="padding: 0px 0px;"></div>--%>

        <div class="invertedshiftdown2">
            <ul>
                <li id="newRiskAssessment"><a class="newRiskAssessment" href="#view11"><spring:message code="landingPage.newRiskAssessment" /></a></li>
                <li id="continueRisk"><a class="continueRisk" href="#view12"><spring:message code="landingPage.continueRiskAssessment" /></a></li>
                <li id="thirdPartyReview"><a class="thirdPartyReview" href="#view13"><spring:message code="thirdPartyReview" /></a></li>
            </ul>
        </div>
   </div>
       <div id="view2" class="subMenuDiv" style="float:left; width:100%;">
        <div class="invertedshiftdown2">
          <ul  style="">
            <li id="investig"><a class="investig" href="#view21"><spring:message code="landingPage.investigations" /></a></li>
          </ul>
        </div>
      </div>
        <div id="view3" class="subMenuDiv" style="float:left; width:100%;">
          <div class="invertedshiftdown2">
            <ul  style="">
                <li id="existingControls"><a class="existingControls" href="#view31"><spring:message code="landingPage.existingControls" /></a></li>
                <li id="analyzeByControls"><a class="analyzeByControls" href="#view32"><spring:message code="landingPage.analyzeByControls" /></a></li>
            </ul>
          </div>
        </div>
        <div id="view4" class="subMenuDiv" style="float:left; width:100%;">
           <div class="invertedshiftdown2">
            <ul  style="">
                <li id="trxMonitoring"><a class="trxMonitoring" href="#view41"><spring:message code="realtime.transaction.summary.view"/></a></li>
                <li id="trxApprove"><a class="trxApprove" href="#view42"><spring:message code="landingPage.transactionApprove" /></a></li>
            </ul>
          </div>
        </div>
        <div id="view5" class="subMenuDiv" style="float:left; width:100%;">
          <div class="invertedshiftdown2">
            <ul  style="">
                <li><a class="" href="#view51"><spring:message code="landingPage.reporting" /></a></li>
            </ul>
            <div id="view51" style="padding: 0px 0px;"></div>
          </div>
        </div>
    <div id="view6" class="subMenuDiv" style="float:left; width:100%;">
        <div class="invertedshiftdown2">
            <ul  style="">
                <li id="search"><a class="search" href="#view41"><spring:message code="search.button.title" /></a></li>
            </ul>
        </div>
    </div>
    <div id="view7" class="subMenuDiv" style="float:left; width:100%;">
        <div class="invertedshiftdown2">
            <ul  style="">
                <li id=""><a class="" href="#view41"><spring:message code="mainTab.trainingCertification" /></a></li>
            </ul>
        </div>
    </div>


    </div>

<script defer="defer">

 var mainTabId = '';
 var subTabId = '';
 mainTabId = '${mainTabId}';
 subTabId = '${subTabId}';
</script>

