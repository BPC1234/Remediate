<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    final String contextPath = request.getContextPath();
%>
<script src="<%= contextPath %>/resources/js/due-dilligance-landing.js"></script>
<title></title>
<%@ include file="/WEB-INF/views/message.jsp" %>

<%--<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i>

            <h2><spring:message code="dueDilligance.existing.reputational.review"/></h2>

            <div class="controlButton pull-right">&lt;%&ndash;<i class="icon-remove removeElement"></i>&ndash;%&gt;</div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">

                <div id="exisSearchBlockId">
                    <div class="span6">
                        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
                        <div class="containerHeadline">
                            <i class="icon-ok-sign"></i><h2>Search Parameter</h2>
                            <div class="controlButton pull-right">&lt;%&ndash;<i class="icon-remove removeElement"></i>&ndash;%&gt;</div>
                            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

                        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
                        <div class="floatingBox">
                            <div class="container-fluid">
                                <table class="filled">
                                    <tr>
                                        <td align="right">Vendor Id:</td>
                                        <td align="left"><input class="span12" type="text" id="vendorId" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td align="right">Name:</td>
                                        <td align="left"><input class="span12" type="text" id="searchName" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td align="right">Last Reviewed Date From :</td>
                                        <td align="left"><input class="span12" type="text" id="lastReviewedFrom" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td align="right">To :</td>
                                        <td align="left"><input class="span12" type="text" id="lastReviewedTo" value="" /></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><input class="" type="checkbox" id="" /></td>
                                        <td align="left">Not reviewed yet</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="right">
                                            <button type="button"  id="newReputationalReviewCancel" class="btn btn-success"><i class="icon-ok"></i> <spring:message code="reactiveProject.button.submit"/></button>
                                        </td>
                                    </tr>
                                </table>
                                &lt;%&ndash;</div>&ndash;%&gt;
                            </div>
                        </div>
                        <!-- ==================== END OF TEXT INPUTS FLOATING BOX ==================== -->
                    </div>
                </div>


                <div class="span12 leftMarginZero">

                    <!-- ==================== SPAN12 HEADLINE ==================== -->
                    <div class="containerHeadline">
                        <i class="icon-th"></i>

                        <h2><spring:message code="reputational.review.list"/></h2>

                        <div class="controlButton pull-right">&lt;%&ndash;<i class="icon-remove removeElement"></i>&ndash;%&gt;</div>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

                    <!-- ==================== SPAN12 FLOATING BOX ==================== -->
                    <div class="floatingBox">
                        <div class="container-fluid">
                            <p>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Address</th>
                                    <th>Last Reviewed Date</th>
                                    <th>Vendor Id </th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr>
                                    <td class=" ">Alex Bob</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">05/11/2011</td>
                                    <td class=" ">1</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Lila Kotian</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">06/04/2009</td>
                                    <td class=" ">2</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Alex Bob</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">02/03/2008</td>
                                    <td class=" ">3</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Jhon Smith</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">07/09/2001</td>
                                    <td class=" ">4</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Alex Liu</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">5</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Lyric–Marty</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">6</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Bonnie–Brilane</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">7</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Wallace–Wilfred</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">8</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Zabby–Zenith</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">9</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Wilkinson–Yule</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">10</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Wilkinson� Yule</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">11</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                <tr>
                                    <td class=" ">Wilkinson–Yule</td>
                                    <td class=" ">Newyork, USA</td>
                                    <td class=" ">10/10/2010</td>
                                    <td class=" ">12</td>
                                    <td id="projectId" style="display:none" class="center "></td>
                                </tr>
                                </tbody>
                            </table>
                            </p>
                        </div>
                    </div>
                    <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

                </div>
            </div>
        </div>
    </div>

</div>--%>


<a id="modalOpenForSwitch" style="display: none;" href="#newRepuReviewSwitchModalDiv" role="button" class="add" data-toggle="modal"></a>
<div id="newRepuReviewSwitchModalDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-body">
        <form:form class="form-horizontal contentForm" data-validate="parsley" method="post" action="" commandName="policy" enctype="multipart/form-data">
            <table>
                <tr>
                    <td colspan="2">
                        <dl>
                            <dt>BPC Basic</dt>
                            <dd>Consists of searches for politically exposed persons (PEPs), against select watch lists and adverse media searches for evidence of reputational, ties to government and adverse conduct. </dd>
                            <dt>BPC Plus</dt>
                            <dd>Consists of BPC Basic plus additional information.</dd>
                            <dt>BPC Advanced</dt>
                            <dd>Consists of BPC Plus in addition to on the ground diligence.</dd>
                        </dl>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
    <div class="modal-body alert alert-success" style="margin-bottom: 0px; text-align: center;">
    <p style="margin-bottom: 0px;">
        <button id="bpcBasicButton" type="button" class="btn btn-small btn-primary">BPC Basic</button>
        <button id="bpcPlusButton" type="button" class="btn btn-small btn-success">BPC Plus</button>
        <button id="bpcAdvancedButton" type="button" class="btn btn-small btn-warning">BPC Advanced</button>
    </p>
    </div>
</div>
</div>

<script>
    $(document).ready(function(){
         $("#modalOpenForSwitch").click();
         $('#bpcBasicButton').click( function() {
           window.location = "../riskasst/newReputationalForm.html?endPoint=4";
         });
        $('#bpcPlusButton,#bpcAdvancedButton').click( function() {
            window.location = "../riskasst/newReputationalForm.html?endPoint=26";
         });
    });
</script>
