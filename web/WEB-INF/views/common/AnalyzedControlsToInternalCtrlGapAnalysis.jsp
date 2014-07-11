<html>
<head>

    <title>Internal Control Gap Analysis Workflow</title>
    <%--<link rel="stylesheet" href="jquery-ui-1.10.2.custom/css/smoothness/jquery-ui-1.10.2.custom.min.css" />
    <script src="jquery-ui-1.10.2.custom/js/jquery-1.9.1.js"></script>
    <script src="jquery-ui-1.10.2.custom/js/jquery-ui-1.10.2.custom.min.js"></script>

    <script src="js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
    <script src="js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>
    <script src="script.js"></script>
    <script src="jquery.maphilight.min.js"></script>

    <link href='js/__jquery.tablesorter/themes/blue/style.css' rel='stylesheet' type='text/css'>

    <link href='jquery-treeview/css/style.css' rel='stylesheet' type='text/css'>
    <link href='style.css' rel='stylesheet' type='text/css'>

    <script src="tablejs.js" type="text/javascript"></script>
    <link href="tablecss.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="jquery-ui.css" />
    <script src="jquery-ui.js"></script>
    <script src="bootstrap-dropdown.js"></script>
    <link href='js/bootstrap/css/bootstrap.css' rel='stylesheet' type='text/css'>

    <script src="js/pagination.js"></script><style>
    .ui-widget{
        margin-bottom: 0;
        margin-left: 10% !important;
        margin-top: 0;
    }
    .config {
        margin-top: -5px;

    }
    .config .dropdown-menu li a{
        color:black;
    }
    .config .dropdown-menu{
    left: 84px;
    top: 27px;
    margin-left: -71px;
    width: 233px;
    }
    .config .dropdown{
        list-style:none;
    }
    .config .dropdown.open .dropdown-toggle{
        backgroung:none;
    }

    table {
    border-collapse: inherit;
    border-spacing: 2px;
    }
    </style>--%>
</head>
<body>
<div id="wrapper">

    <div id="content">
        <div id="internalControlGapAnalysisContentWrapperAC">
            <div class="tableContainer">
                <label><font style="font-size: 19px; line-height: 50px"><b>Internal Control Gap Analysis
                    Workflow</b></font></label>
                <div id="subTableDiv">
                    <br/><br/>

                    <div id="tableDiv">
                        <table id="workFlowTable" class="tablesorter">
                            <thead>
                            <tr>
                                <th class="nosort">Control Name</th>
                                <th class="nosort">Total Transaction</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Transaction involving structured payments</td>
                                <td>12</td>
                            </tr>
                            <tr>
                                <td>Transaction with entity (including narrative of transaction) appearing on "Do Not
                                    Use/Do Not Pay" or "Inactive" lists
                                </td>
                                <td>15</td>
                            </tr>
                            <tr>
                                <td>Transaction on weekends or holidays</td>
                                <td>30</td>
                            </tr>
                            <tr>
                                <td>Transaction with entity not appearing on "Vendor Master File"/"Employee Master
                                    File"/"Customer Master File
                                </td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td>Transaction without associated transaction narrative/description</td>
                                <td>20</td>
                            </tr>
                            <tr>
                                <td>Transaction with entity (including narrative of transaction) appearing on "Do Not
                                    Use/Do Not Pay" or "Inactive" lists
                                </td>
                                <td>25</td>
                            </tr>
                            <tr>
                                <td>Transaction with entity not appearing on "Vendor Master File"/"Employee Master
                                    File"/"Customer Master File
                                </td>
                                <td>25</td>
                            </tr>
                            <tr>
                                <td>Transaction without associated transaction narrative/description</td>
                                <td>25</td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <br/><br/>
                </div>
                <br/>
                <br/>
                <br/>

                <div id="subTableDiv">
                    <div id="internalControlGapAnalysisControlwiseInfoAC" style="display:none;   padding: 0 4%;">
                        <label><font style="font-size: 22px; padding-left: 115px;"><b>Country wise Transactions of
                            Selected Control</b></font></label>
                        <table id="summeryTable2" class="tablesorter2">
                            <thead>
                            <tr>
                                <th>Country Name</th>
                                <th>Total Transaction</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Russia</td>
                                <td>15</td>
                            </tr>
                            <tr>
                                <td>North America</td>
                                <td>2</td>
                            </tr>
                            <tr>
                                <td>South Korea</td>
                                <td>3</td>
                            </tr>
                            <tr>
                                <td>Japan</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>Thiland</td>
                                <td>8</td>
                            </tr>
                            <tr>
                                <td>China</td>
                                <td>10</td>
                            </tr>
                            <tr>
                                <td>Siberia</td>
                                <td>4</td>
                            </tr>
                            <tr>
                                <td>Suyden</td>
                                <td>1</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        <br/><br/><br/>
    </div>
</div>
</body>
</html>
