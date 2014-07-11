<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% final String contextPath = request.getContextPath(); %>
<script src="<%= contextPath %>/resources/js/jQuery.print.js"></script>

<!-- ==================== COMMON ELEMENTS ROW ==================== -->
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="row-fluid">

    <div class="span12">
        <!-- ==================== TEXT INPUTS HEADLINE ==================== -->
        <div class="containerHeadline">
            <i class="icon-ok-sign"></i><h2><spring:message code="dueDilligance.landingPage.header.ReputationalReview"/></h2>
            <div class="controlButton pull-right"></div>
            <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
        </div>
        <!-- ==================== END OF TEXT INPUTS HEADLINE ==================== -->

        <!-- ==================== TEXT INPUTS FLOATING BOX ==================== -->
        <div class="floatingBox">
            <div class="container-fluid">
                <div class="containerHeadline">
                    <i class="icon-table"></i><h2></h2>
                    <div class="controlButton pull-right"></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div>
                </div>

                <div class="floatingBox">
                    <div class="container-fluid">
                       <div class="customFlexigridCss">
                        <table id="reputationalFormSearchTable"></table>
                       </div>

                        <%--<table id="reputationalFormSearchTable" class="table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Business Name</th>
                                <th>User Name</th>
                                <th>Created</th>
                                <th>Others Documents</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Arizona Beverage Company</td>
                                <td>Serena Buchanan</td>
                                <td>01-15-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/csv.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Bellwether Technology Corporation</td>
                                <td>Vivian Christensen</td>
                                <td>03-28-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/pdf.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>Brocade Communications Systems</td>
                                <td>Nyssa Dickson</td>
                                <td>02-12-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/xlsx.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                           <tr>
                                <td>4</td>
                                <td>Bucyrus International</td>
                                <td>Savage Heredia</td>
                                <td>01-18-2014</td>
                               <td><img src="<%= contextPath %>/resources/images/pdf.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>Cognizant Technology Solutions</td>
                                <td>Macdonald Rhineland-Palatinate</td>
                                <td>02-20-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/text.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            <tr>
                                <td>6</td>
                                <td>Carnival Corporation & plc</td>
                                <td>Dickson Bremen</td>
                                <td>01-29-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/docx.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td>Corrections Corporation of America</td>
                                <td>Allen Puntarenas</td>
                                <td>03-19-2014</td>
                                <td><img src="<%= contextPath %>/resources/images/jpeg.png" class="fileIcon"> &nbsp;Contract</td>
                            </tr>
                            </tbody>
                        </table>--%>
                    </div>
                </div>

            </div>
        </div>
      </div>

</div>
<!-- ==================== END OF COMMON ELEMENTS ROW ==================== -->
<script type="text/javascript">
    $("#reputationalFormSearchTable").flexigrid({
        url :'getJASONforReputationalReview.html?'
                +'tableName=reputationalFormSearchTable',
        dataType : 'json',
        colModel : [ {
            display : 'Business Name',
            name : 'businessName',
            width : 'auto',
            sortable : true,
            align : 'left'
        },{
            display : 'User Name',
            name : 'userName',
            width : 'auto',
            sortable : true,
            align : 'center'
        }, {
            display : 'Created',
            name : 'createdDateStr',
            width : 'auto',
            sortable : true,
            align : 'center'
        }, {
            display : 'Others Documents',
            name : 'otherDoc',
            width : 100,
            sortable : true,
            align : 'center'
        }/*, {
            display : '',
            name : 'approver',
            width : 50,
            sortable : true,
            align : 'center'
        }*/],
        buttons: [{ name: '#reputationalFormSearchTable', bclass: 'PDF', onpress: printTable },
            { name: '#reputationalFormSearchTable', bclass: 'XLS', onpress: toExcel },
            { name: '#reputationalFormSearchTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#reputationalFormSearchTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#reputationalFormSearchTable', bclass: 'GROUPING', onpress: groupByView }
        ],
        usepager : false,
        title : '',
        useRp : false,
        rp : 10,
        showTableToggleBtn : true,
        height:280 ,
        onSuccess: function() {
            addGrid($('#reputationalFormSearchTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass:function(g) { //PATCH to get the grids to be responseive
            this.g=g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $(document).on('click','#reputationalFormSearchTable tbody tr',function(){
    window.location = "./newReputationalFormPrint.html?"
            +"cName="+$(this).find('td').eq(0).text()
            +"&uName="+$(this).find('td').eq(1).text()
            +"&created="+$(this).find('td').eq(2).text()
            +"&endPoint=4"
    });
</script>