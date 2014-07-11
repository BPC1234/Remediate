<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.dsinv.abac.util.Utils" %>
<%@ page import="com.dsinv.abac.entity.Role" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
    final String contextPath = request.getContextPath();
    pageContext.setAttribute("abacMessage", Utils.getMessage(request));
%>
<c:set var="admin" value="<%=Role.ADMIN.getLabel()%>"/>
<c:set var="employee" value="<%=Role.EMPLOYEE.getLabel()%>"/>
<c:set var="legal" value="<%=Role.LEGAL.getLabel()%>"/>
<c:set var="ia_analyst" value="<%=Role.IA_ANALYST.getLabel()%>"/>
<c:set var="ia_manager" value="<%=Role.IA_MANAGER.getLabel()%>"/>
<c:set var="compliance" value="<%=Role.COMPLIANCE.getLabel()%>"/>

<link rel="shortcut icon" type="image/x-icon" href="<%= contextPath %>/resources/images/new_logo.png" />
<html class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery-1.9.1.min.js"></script>


    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-slider.js"></script>                   <!-- bootstrap slider plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.sparkline.min.js"></script>               <!-- small charts plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.flot.min.js"></script>                    <!-- charts plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.flot.resize.min.js"></script>             <!-- charts plugin / resizing extension -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.flot.pie.min.js"></script>                <!-- charts plugin / pie chart extension -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/wysihtml5-0.3.0_rc2.min.js"></script>            <!-- wysiwyg plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-wysihtml5-0.0.2.min.js"></script>      <!-- wysiwyg plugin / bootstrap extension -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-tags.js"></script>                     <!-- multivalue input tags -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.tablesorter.min.js"></script>             <!-- tablesorter plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.tablesorter.widgets.min.js"></script>     <!-- tablesorter plugin / widgets extension -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/jquery.tablesorter.pager.min.js"></script>       <!-- tablesorter plugin / pager extension -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/raphael.2.1.0.min.js"></script>                  <!-- vector graphic plugin / for justgage.js -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/justgage.js"></script>                           <!-- justgage plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-multiselect.js"></script>              <!-- multiselect plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-datepicker.js"></script>               <!-- datepicker plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-colorpicker.js"></script>              <!-- colorpicker plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/parsley.min.js"></script>                        <!-- parsley validator plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/formToWizard.js"></script>                       <!-- form wizard plugin -->

    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap.min.js"></script>
    <script src="<%= contextPath %>/resources/kamarel/js/vendor/bootstrap-editable.min.js"></script>             <!-- editable fields plugin -->
    <script src="<%= contextPath %>/resources/kamarel/js/thekamarel.min.js"></script>                            <!-- main project js file -->
    <%--<script src="<%= contextPath %>/resources/js/Flexigrid-master/js/flexigrid.pack.js"  type="text/javascript"></script>--%>
    <script src="<%= contextPath %>/resources/js/table2CSV.js"></script>
    <script src="<%= contextPath %>/resources/js/Flexigrid-master/js/flexigrid.js"  type="text/javascript"></script>

    <script src="<%= contextPath %>/resources/js/custom-script.js"></script>
    <link href='<%= contextPath %>/resources/styles/jquery-impromptu.css' rel='stylesheet' type='text/css'>

    <script src="<%= contextPath %>/resources/js/jquery-impromptu.js"  type="text/javascript"></script>


    <%--kamarel theme--%>
    <link rel="stylesheet" href="<%= contextPath %>/resources/kamarel/css/bootstrap-responsive.min.css">
    <%--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>--%>
    <link rel="stylesheet" href="<%= contextPath %>/resources/kamarel/css/styles.css">
    <%--<link href='<%= contextPath %>/resources/js/Flexigrid-master/css/flexigrid.pack.css' rel='stylesheet' type='text/css'>--%>
    <link href='<%= contextPath %>/resources/js/Flexigrid-master/css/flexigrid.css' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="<%= contextPath %>/resources/styles/customCss.css">
    <script src="<%= contextPath %>/resources/js/jquery.blockUI.js"  type="text/javascript"></script>

    <script src="<%= contextPath %>/resources/jqxGrid/jqxcore.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxdata.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxbuttons.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxscrollbar.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxmenu.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.grouping.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.selection.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.pager.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxlistbox.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxdropdownlist.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.sort.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.columnsresize.js"></script>
    <script src="<%= contextPath %>/resources/jqxGrid/jqxgrid.filter.js"></script>

    <link rel="stylesheet" type="text/css" href="<%= contextPath %>/resources/styles/jqx.base.css">


</head>
<body id="ipboard_body" class="dashboard wysihtml5-supported">
    <tiles:insertAttribute name="header"/>
    <c:if test="${abacMessage != ''}">
        <div id="inlineMsgDiv">${abacMessage}</div>
    </c:if>
    <div id="content" class="content">
        <security:authorize access="hasAnyRole('${ia_analyst}', '${ia_manager}', '${employee}', '${admin}', '${legal}', '${compliance}')">
            <tiles:insertAttribute name="dashboard"/>
        </security:authorize>
            <div class="container-fluid">
            <tiles:insertAttribute name="body"/>
                    <a id="dlink"  style="display:none;"></a>
                    <div id="question" style="display:none;">
                        <form id="exportInputForm" data-validate="parsley">
                        <table class="exportModal">
                            Export from a range
                            <tr>
                                <td valign="top">Start:</td>
                                <td ><input id="from" type="text" placeholder="must be a number" data-trigger="change" data-type="number" data-validation-minlength="0"/></td>
                            </tr>
                            <tr>
                                <td valign="top">End:</td>
                                <td ><input id="to" type="text" placeholder="" data-type="number" data-validation-minlength="0" data-trigger="change"/></td>
                            </tr>
                            <tr>
                                <td align="right"><input id="exportOkButton" type="button" class="btn btn-mini btn-primary" value="OK"></td>
                                <td align="left"><input id="exportCancelButton" type="button" class="btn btn-mini btn-success" value="Cancel"></td>
                            </tr>
                         </table>
                            </form>
                    </div>
                <%--</div>--%>
            </div>
    </div>
    <%--<tiles:insertAttribute name="footer"/>--%>

</body>
</html>