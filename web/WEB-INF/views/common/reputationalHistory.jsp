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


<div class="row-fluid">
    <div class="span12">

        <!-- ==================== SPAN12 HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-th"></i><h2><spring:message code="dueDilligance.reputationalReviewHistory" /></h2>
            <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF SPAN12 HEADLINE ==================== -->

        <!-- ==================== SPAN12 FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
            <div class="filled">
            <div class="containerHeadline">
                <i class="icon-table"></i><h2>Search Parameter</h2>
                <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
            </div>
            <div class="floatingBox">
            <div class="container-fluid">
            <div id="replaceAjaxDiv">
              <div id="repuHistorySearch" class="filled">
                <div class="filled">

                <table class="filled">
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
              </div>
            </div>
            </div>
           </div>
          </div>
         </div>
           <div  id="repuHisSearchresultDiv" class="filled">
                    <div class="containerHeadline">
                        <i class="icon-table"></i><h2></h2>
                        <div class="controlButton pull-right"><%--<i class="icon-remove removeElement"></i>--%></div>
                        <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                    </div>
                    <div class="floatingBox table">
                        <div class="container-fluid">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Project Name</th>
                                    <th>Date Of Last Review</th>
                                    <th>City</th>
                                    <th>State</th>
                                    <th>Zip Code</th>
                                    <th>Country</th>
                                    <th>Bank Account No</th>
                                    <th>Bank Account Location</th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr class="odd_gradeX">
                                    <td>Reputational Project One</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>

                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Two</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                <tr class="odd_gradeX">
                                    <td>Reputational Project Three</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>

                                </tr>
                                <tr class="even_gradeC">
                                    <td>Reputational Project Four</td>
                                    <td>8/21/12</td>
                                    <td>Los Angeles</td>
                                    <td>California</td>
                                    <td>555CA</td>
                                    <td>United States</td>
                                    <td>AC120929</td>
                                    <td>Los Angeles</td>
                                    <td><a href=""><I>View</I></a></td>
                                </tr>
                                </tbody>
                            </table>
                </div>
            </div>

            </div>
            </div>
            </div>
            </div>

            </div>
        </div>
        <!-- ==================== END OF SPAN12 FLOATING BOX ==================== -->

    </div>
</div>
<%--
<div id="replaceAjaxDiv">
<h3><strong><spring:message code="dueDilligance.reputationalReviewHistory" /></strong></h3>
	<div id="notaccordion">
		<h3 style="margin-top: 10px; text-align: left; font-size: 15px"><strong><a href="#">Reputational Review History</a></strong></h3>
		<div style="display: none">
			<br />
			<div id="searchBox" class="boxBorder" style="text-align:center; display: inline-block; padding-top: 10px; padding-bottom: 0px; margin-left: 6px;">
				<table border="0" align="center" style="display: inline-block;">
					<tr>
						<td align="left">Name: <input class="searcVendor" size="43"
							type="text" id="searchName" value="" /></td>
					</tr>
					<tr>
						<td align="left">Last Reviewed Date From : <input class="searcVendor"
							size="10" type="text" id="lastReviewedFrom" value="" /> to: <input
							class="searcVendor" size="10" type="text" id="lastReviewedTo"
							value="" />
						</td>
					</tr>
					<tr>
						<td align="left"><input class="searcVendor" size="30"
							type="checkbox" id="" />Not reviewed yet</td>
					</tr>
					<tr>
						<td align="right"><input class="reputationalReview"
							type="button" name="searchVen" value='Search' /></td>
					</tr>
				</table>
			</div>

			</br> </br>
			<div id="searchBox" class="boxBorder" style="text-align: center; display: inline-block; padding-top: 10px; padding-bottom: 0px; margin-left: 6px;">
			<div id="repuWrap" style="display: inline-block; text-align: center; margin-left: 0px; padding-left: 0px; padding-right: 0px;">

				<div id="demo" style="height: 587px; text-align: center;">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="repuHisTable">
						<thead>
							<tr>
								<th>Project Name</th>
								<th>Date Of Last Review</th>
								<th>City</th>
								<th>State</th>
								<th>Zip Code</th>
								<th>Country</th>
								<th>Bank Account No</th>
								<th>Bank Account Location</th>
								<th></th>
							</tr>
						</thead>

						<tbody>
							<tr class="odd_gradeX">
								<td>Reputational Project One</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>

							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Two</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
							<tr class="odd_gradeX">
								<td>Reputational Project Three</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>

							</tr>
							<tr class="even_gradeC">
								<td>Reputational Project Four</td>
								<td>8/21/12</td>
								<td>Los Angeles</td>
								<td>California</td>
								<td>555CA</td>
								<td>United States</td>
								<td>AC120929</td>
								<td>Los Angeles</td>
								<td><a href=""><I>View</I></a></td>
							</tr>
						</tbody>
					</table><br/><br/><br/><br/><br/><br/><br/><br/>
				</div>
			</div>
		</div>
	</div>
</div>
</div>--%>
