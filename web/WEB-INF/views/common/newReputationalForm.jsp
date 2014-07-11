<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% final String contextPath = request.getContextPath(); %>
<script src="<%= contextPath %>/resources/js/jQuery.print.js"></script>
<script src="<%= contextPath %>/resources/js/due-dilligance-landing.js"></script>

<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2><spring:message code="dueDilligance.new.reputational.review"/></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
            <div id="replaceAjaxDiv">
            <div id="reputationalHistory" style="text-align: right;"><a class="hrefLinkColor" id="historyId"><i class="icon-external-link"></i>&nbsp;&nbsp;<strong>History?</strong></a>
            </div>

            <div class="boxBorder">
            <form id="form" <%--method="post" action="./reputationalFormProcess.html"--%>  data-validate="parsley">
            <div id="relationshipEntryDiv">
            <table border="1" BORDERCOLOR="D2D8D8" class="newReputationalFormTable">
            <tr>
                <td align="right" class="maxTdLength">1. <spring:message code="dueDiligance.newReputational.form.businessName"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right">2. <spring:message code="dueDiligance.newReputational.form.headquartersAddress"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.telephone"/></td>
                <td align="left"><input type="text"  data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="right"><spring:message code="dueDiligance.newReputational.form.facsimile"/></td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.websiteURL"/></td>
                <td colspan="4" align="left"><input type="text" data-type="url" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right">3. <spring:message code="dueDiligance.newReputational.form.primaryContactName"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDilligance.newRelationShip.address"/>
                    <p style="font-weight: 500;"><spring:message code="dueDiligance.newReputational.form.leftParanthesis"/>
                        <spring:message code="dueDiligance.newReputational.form.ifFifferentFromAbove"/>
                        <spring:message code="dueDiligance.newReputational.form.rightParanthesis"/>
                    </p>
                </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change"  class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.officePhone"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="right"><spring:message code="dueDiligance.newReputational.form.mobile"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.emailaddress"/></td>
                <td colspan="4" align="left"><input type="text" data-type="email" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.businessform"/></td>
                <td colspan="2" align="left">
                    <p><input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.soleProprietor"/></label></p>

                    <p><input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.corporation"/></label></p>

                    <p><input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.partnership"/></label></p>

                    <p><input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.LLCOrLLP"/></label></p>
                </td>
                <td align="right" class="subItem"><spring:message
                        code="dueDiligance.newReputational.form.localCompanyRegistryFilingAttached"/></td>
                <td align="left">
                    <p><input type="radio" name="localCompantRegistryRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label></p>

                    <p><input type="radio" name="localCompantRegistryRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label></p>
                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td align="right">4. <spring:message
                        code="dueDiligance.newReputational.form.countryStateOrProvinceOfIncorporation"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <c:if test="${endPoint > 4}">
            <tr>
                <td align="right">5. <spring:message code="dueDiligance.newReputational.form.pleaseListAllLocationsMsg"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right">6. <spring:message code="dueDiligance.newReputational.form.countryOrCountries"/></td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right">7. <spring:message code="dueDiligance.newReputational.form.percentageOfContractorsTimeMsg"/> :</td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    8. <spring:message code="dueDiligance.newReputational.form.ownersOrPrincipals"/>
                    <p style="font-weight: 500;" class="subItem"><spring:message
                            code="dueDiligance.newReputational.form.ownershipPercentagesMustTotalMsg"/></p>
                </td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="3" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td align="left"><label>%</label></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    9. <spring:message code="dueDiligance.newReputational.form.membersOfTheBoardOfDirectors"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    10. <spring:message code="dueDiligance.newReputational.form.companyOfficers"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.presidentOrCEO"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.chiefFinancialOfficer"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.ChiefOperatingOfficer"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.salesOrMarketingDirector"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.headOfGovtRelationsOrLobbying"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    11. <spring:message code="dueDiligance.newReputational.form.allManagerskeyEmployeesMsg"/>
                </td>
            </tr>

            <tr>
                <td rowspan="2" align="right" class="subItem"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td rowspan="2" align="right" class="subItem" style="width: 60px;"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="2" align="right" class="subItem"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td rowspan="2" align="right" class="subItem" style="width: 60px;"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="2" align="right" class="subItem"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td rowspan="2" align="right" class="subItem" style="width: 60px;"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="2" align="right" class="subItem"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td rowspan="2" align="right" class="subItem" style="width: 60px;"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDalliance.newReputational.form.position"/>
                    </p>
                </td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="6" align="left">
                    12. <spring:message code="dueDiligance.newReputational.form.parentCompaniesMsg"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="6" align="left" style="border-width: 0px 0px 0px 0px;">
                    <spring:message code="dueDiligance.newReputational.form.forEachParentCompany"/>
                </td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    13. <spring:message code="dueDiligance.newReputational.form.subsidiariesJointVenturesMsg"/>
                </td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="user.form.name"/> </td>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="6" align="left" style="border-width: 0px 0px 0px 0px;">
                    <spring:message code="dueDiligance.newReputational.form.forEachSubsidiariesJointVenturesMsg"/>
                </td>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    14. <spring:message code="dueDiligance.newReputational.form.businessReferences"/>
                    <p style="font-weight: 500;" class="subItem"><spring:message
                            code="dueDiligance.newReputational.form.mustProvideAtLeastMsg"/></p>
                </td>
            </tr>
            <tr>
                <td rowspan="3" align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.business"/>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.project"/>
                    </p>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.primaryContact"/>
                    </p>
                </td>
                <td colspan="4" align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td><spring:message code="dueDiligance.newReputational.form.phone"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="3" align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.business"/>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.project"/>
                    </p>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.primaryContact"/>
                    </p>
                </td>
                <td colspan="4" align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td><spring:message code="dueDiligance.newReputational.form.phone"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="3" align="right" class="subItem"><spring:message code="dueDiligance.newReputational.form.business"/>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.project"/>
                    </p>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.primaryContact"/>
                    </p>
                </td>
                <td colspan="4" align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td><spring:message code="dueDiligance.newReputational.form.phone"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="6" align="left">
                    15. <spring:message code="dueDiligance.newReputational.form.contractorsPrimaryBusinessBankingInstitution"/>
                </td>
            </tr>
            <tr>
                <td rowspan="3" align="right" class="subItem"><spring:message code="user.form.name"/>
                    <p>
                        <spring:message code="dueDilligance.newRelationShip.address"/>
                    </p>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.primaryContact"/>
                    </p>
                </td>
                <td colspan="4" align="left" ><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td colspan="4" align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td align="left"><input type="text" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
                <td><spring:message code="dueDiligance.newReputational.form.phone"/></td>
                <td align="left"><input type="text" data-type="number" data-required="true" data-trigger="change" class="relationshipEntryInput span12" /></td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
                <td rowspan="2" colspan="5" align="left" class="subItem">
                    <p>16. <spring:message code="dueDiligance.newReputational.form.isThisTheLocationMsg"/> ?

                        <input type="radio" name="businessFormRadio"><label><spring:message
                                code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                                code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.ifNotPleaseIdentifyTheBankMsg"/> :
                    </p>
                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
            </tr>

            <tr>
                <td colspan="6" align="left">
                    17. <spring:message code="dueDiligance.newReputational.form.otherBankingOrCreditReferences"/> :
                </td>
            </tr>

            <tr>
                <td rowspan="2" colspan="5" align="left">
                    <p>18. <spring:message code="dueDiligance.newReputational.form.numberOfYearsInBusiness"/> ?

                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="less"/> <spring:message code="than"/> <spring:message code="two"/> <spring:message code="years"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="two"/> <spring:message code="to"/> <spring:message code="five"/> <spring:message code="years"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="five"/> <spring:message code="to"/> <spring:message code="one"/><spring:message code="zero"/> <spring:message code="years"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="more"/> <spring:message code="than"/> <spring:message code="one"/><spring:message code="zero"/> <spring:message code="years"/></label>

                    </p>
                    <p class="subItem">
                        <spring:message code="dueDiligance.newReputational.form.numberOfEmployees"/> :

                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="less"/> <spring:message code="than"/> <spring:message code="one"/><spring:message code="zero"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="one"/><spring:message code="zero"/><spring:message code="hifen"/><spring:message code="five"/><spring:message code="zero"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="five"/><spring:message code="zero"/><spring:message code="hifen"/><spring:message code="one"/><spring:message code="zero"/><spring:message code="zero"/></label>
                        <input type="radio" name="businessFormRadio">
                        <label><spring:message code="more"/><spring:message code="than"/><spring:message code="one"/><spring:message code="zero"/><spring:message code="zero"/></label>

                    </p>
                    <p class="subItem">
                        <label><spring:message code="dueDiligance.newReputational.form.typesOfServicesPerformed"/> :</label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>
                </td>
                <td></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td rowspan="2" colspan="5" align="left">
                    <p>19. <spring:message code="dueDiligance.newReputational.form.hasTheContractorsBusinessMsg"/> ?

                        <input type="radio" name="businessFormRadio"><label><spring:message
                                code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                                code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    <p class="subItem">
                        <spring:message code="dueDiligance.newReputational.form.IfYesListAllOtherMsg"/> :
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>
                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td colspan="6" align="left">
                    20. <spring:message code="dueDiligance.newReputational.form.pleaseDescribeTheContractorsEexperienceMsg"/> :
                </td>
            </tr>

            <tr>
                <td colspan="5" align="left">
                    21. <spring:message code="dueDiligance.newReputational.form.relationshipsWithGovernmentOfficials"/> :
                    <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.hasAnyPersonListedInMsg"/>.</a>
                    </p>
                    <p>
                        <label>(a) <spring:message code="dueDiligance.newReputational.form.aGovernmentOrAnyDepartmentMsg"/> </label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>

                    </p>
                    </br>
                    <p>
                        <label>(b) <spring:message code="dueDiligance.newReputational.form.aGovernmentOwnedOrControlledEentity"/> </label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>

                    </p>
                    </br>
                    <p>
                        <label>(c) <spring:message code="dueDiligance.newReputational.form.aPublicInternationalOrganization"/> </label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>

                    </p>
                    </br>
                    <p>
                        <label>(e) <spring:message code="dueDiligance.newReputational.form.aCandidateForPoliticalOffice"/> </label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>

                    </p>
                    </br>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.IfYesToAnyOfTheAboveProvideDetails"/> :
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>

                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="5" align="left">
                    22. <spring:message code="dueDiligance.newReputational.form.doesAnyOfficerOrEmployee"/>
                    <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.doesAnyOfficerOrEmployeeMsg"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesProvideDetails"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>

                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="5" align="left">
                    23. <spring:message code="dueDiligance.newReputational.form.wilYouRetainOrHave"/>
                    <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.wilYouRetainOrHaveMsg"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesIdentifyTheIndividualMsg"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>

                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="5" align="left">
                    <p>
                        24. <spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOowner"/>
                        <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOownerMsg"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesProvideDetails"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>
                    </br>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOownerMsg3"/>
                        <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOownerMsg4"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesProvideDetails"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>
                    </br>
                    <p>
                        <spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOownerMsg5"/>
                        <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.hasTheContractorAnOownerMsg6"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesProvideDetails"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>

                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>

            <tr>
                <td colspan="5" align="left">
                    26. <spring:message code="dueDiligance.newReputational.form.doesTheContractorAnyOwnerMsg7"/>
                    <a style="font-weight: 500 "><spring:message code="dueDiligance.newReputational.form.doesTheContractorAnyOwnerMsg8"/> ?</a>
                    </p>
                    <p>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.yes"/></label>
                        <input type="radio" name="businessFormRadio"><label><spring:message
                            code="dueDiligance.newReputational.form.no"/></label>
                    </p>
                    </br>
                    <p>
                        <label><spring:message code="dueDiligance.newReputational.form.ifYesProvideDetails"/> : </label>
                        <textarea class="relationshipEntryInput span12 subItem" ></textarea>
                    </p>

                </td>
                <td><span class="inputerrormsg"></span></td>
            </tr>
            </c:if>

            <tr>
                <td colspan="6" align="center" class = "middleColumn">
                    <h4><spring:message code="new.reputational.review.upload.documents"/></h4>
                </td>

            </tr>
            <tr>
                <td align="right" class="subItem"><spring:message code="new.reputational.review.business.partner.questionnaire"/></td>
                <td colspan="4" align="left">
                    <input id="fileDataForBPQ" type="file" name="file" id="inputFile" style="display: none"/>
                    <div class="dummyfile">
                    <input  id="fileDataForBPQDummy" type="text" class="input disabled span8" name="fileDataForBPQDummy" readonly="true" placeholder="  Upload supporting document"/>
                    <button id="fileDataForBPQBrowse" class="btn btn-primary" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                    </div>
                </td>
            </tr>

            <tr>
                <td align="right" class="subItem"><spring:message code="new.reputational.review.other.due.diligence.documents"/></td>
                <td colspan="4" align="left">
                    <input id="fileDataForDDD" type="file" name="file" style="display: none"/>
                    <div class="dummyfile">
                        <input id="fileDataForDDDDummy" type="text" class="input disabled span8" readonly="true" placeholder="  Upload supporting document"/>
                        <button id="fileDataForDDDBrowse" class="btn btn-primary" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                    </div>
                </td>
            </tr>

            <tr>
                <td align="right" class="subItem"><spring:message code="new.reputational.review.executed.or.draft.contact"/></td>
                <td colspan="4" align="left">
                    <input id="fileDataForEDC" type="file" name="file" style="display: none"/>
                    <div class="dummyfile">
                        <input  id="fileDataForEDCDummy" type="text" class="input disabled span8" name="fileDataForEDCDummy" readonly="true" placeholder="  Upload supporting document"/>
                        <button id="fileDataForEDCBrowse" class="btn btn-primary" type="button"><i class="icon-cloud-upload"></i>&nbsp;&nbsp;<spring:message code="browse"/></button>
                    </div>
                </td>
            </tr>

            <tr>
                <td colspan="6" align="center" class = "middleColumn">
                    <button type="button" id="newReputationalReviewCancel" class="btn btn-warning"><i class="icon-remove"></i><spring:message code="reactiveProject.button.cancel"/> </button>
                    <button type="button"  id="newReputationalReviewSubmit" class="btn btn-success"><i class="icon-ok"></i> <spring:message code="reactiveProject.button.submit"/></button>

                </td>

            </tr>
            </table>
            </div>
            </form>
            </div>
            </div>
            </div>
            </div>
            <script type="text/javascript">
                $("input:text").keyup(function(){
                    if($(this).val().length > 0){
                        ($(this).parent('td').next('td').html(''));
                        ($(this).removeClass('blank'));
                    }else{
                        $(this).parent('td').next('td').html('<label style="font-family: times new roman;font-weight: bold; color: red;">required</label>');
                        $(this).addClass('blank');
                    }
                });
                $("textarea").keyup(function(){
                    if($(this).val().length > 0){
                        ($(this).parent('p').parent('td').next('td').html(''));
                        ($(this).removeClass('blank'));
                    }else{
                        $(this).parent('p').parent('td').next('td').html('<label style="font-family: times new roman;font-weight: bold; color: red;">required</label>');
                        $(this).addClass('blank');
                    }
                });

                function validate() {
                    /*return $("input:text,textarea,select").removeClass('blank').filter(function() {
                     return !/\S+/.test($(this).val());
                     }).addClass('blank').size() == 0;*/
                    return $("input:text").removeClass('blank').filter(function() {
                        return !/\S+/.test($(this).val());
                    }).addClass('blank').parent('td').next('td').html('<label style="font-family: times new roman;font-weight: bold; color: red;">required</label>');
                }
                function validateForTextArea() {
                    return $("textarea").removeClass('blank').filter(function() {
                        return !/\S+/.test($(this).val());
                    }).addClass('blank').parent('p').parent('td').next('td').html('<label style="font-family: times new roman;font-weight: bold; color: red;">required</label>');
                }

                $('#form').submit(function (e) {
                    //alert('submitting form.....');
                    //$("#reportPrintDiv").print();

                   /* validate();
                    validateForTextArea();
                    var anyFieldIsEmpty = $("form :input[type='text']").filter(function() {
                        return $.trim(this.value).length === 0;
                    }).length > 0;

                    if (anyFieldIsEmpty) {
                        alert('empty');
                        //e.preventDefault();
                    }else{
                        alert('not empty');

                    }*/
                });
                $('#newReputationalReviewSubmit').click(function(){
                 //validate();
                 //validateForTextArea();
                   $("#form").submit();
                    var checked = false;
                    $("#form :input[type='text']").each(function() {
                        if($(this).hasClass('parsley-error')==true){
                            checked = true;
                            return false;
                        }
                    });
                    if(checked == false)
                  //$("#reportPrintDiv").print();
                  window.location = "./newReputationalFormPrint.html?endPoint="+${endPoint};

                 });
            </script>
        <script defer="defer">
        /*    var maxFileSize = ${maxFileUploadSize};*/
        setUploadButton("#fileDataForBPQ","#fileDataForBPQBrowse","#fileDataForBPQDummy");
        setUploadButton("#fileDataForDDD","#fileDataForDDDBrowse","#fileDataForDDDDummy");
        setUploadButton("#fileDataForEDC","#fileDataForEDCBrowse","#fileDataForEDCDummy");
        </script>
            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>
</div>