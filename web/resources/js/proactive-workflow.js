$(document).ready(function () {

    $("#proactiveProjectListTable").flexigrid({
        url : 'getJASONforContinueRiskAss.html',
        dataType : 'json',
        colModel : [ {
            display : 'Region',
            name : 'regionName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Date Range',
            name : 'dateRange',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Created Date',
            name : 'createdDate',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Creator',
            name : 'author',
            width : 'auto',
            sortable : false,
            align : 'center'
        },{
            display : 'Backend data',
            name : 'id',
            width : 0,
            hide : true,
            align : 'center'
        }],
        searchitems : [ {
            display : 'Region',
            name : 'regionName'
        }, {
            display : 'Creator',
            name : 'author',
            isdefault : true
        } ],
        sortname : "regionName",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300,
        onSuccess: function() {
            addGrid($('#proactiveProjectListTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass:function(g) { //PATCH to get the grids to be responseive
            this.g=g; //Is this the only way to expose the grid class ?
            return g;
        }
    });
    $(document).on('click', '#proactiveProjectListTable tr', function(evt) {
        window.location = "../riskasst/newRiskAssessmentsummaryView.html?cont=1&proactiveProjectId=" + $(this).children(":last").text().trim();
    });

    $("#proactiveBlockWeightTable").flexigrid({
        url : 'getJASONforProactiveWorkflowList.html?cName='+$("#cNameId").val()+"&wScore="+$("#wScoreId").val(),
        //url : 'getJASONforProactiveWorkflowList.html',
        dataType : 'json',
        colModel : [ {
            display : 'Year',
            name : 'date',
            width : 111,
            sortable : true,
            align : 'left'
        }, {
            display : 'CPI Score',
            name : 'cpiScoreStr',
            width : 100,
            sortable : true,
            align : 'left'
        }, {
            display : 'Revenues',
            name : 'revenuesStr',
            width : 100,
            sortable : true,
            align : 'left'
        },{
            display : 'No. of Customs Brokers',
            name : 'noOfCustomBrokers',
            width : 130,
            sortable : false,
            align : 'center'
        }, {
            display : 'No. of Agents',
            name : 'noOfAgents',
            width : 130,
            sortable : false,
            align : 'center'
        }, {
            display : 'Revenue Attributable to Agents',
            name : 'revenueAttributableToAgents',
            width : 150,
            sortable : false,
            align : 'center'
        }, {
            display : 'No. of Govt. Customers',
            name : 'noOfGovtCustomers',
            width : 150,
            sortable : false,
            align : 'center'
        }, {
            display : 'Date Last FCPA Audit/Investigation',
            name : 'dateLastFCPAAuditInvestigation',
            width : 193,
            sortable : false,
            align : 'center'
        },{
            display : 'Backend data',
            name : 'id',
            width : 1,
            hide : true,
            align : 'center'
        }],
        searchitems : [ {
            display : 'Year',
            name : 'date'
        }, {
            display : 'Revenues',
            name : 'revenuesStr',
            isdefault : true
        } ],
        sortname : "revenuesStr",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300
    });


    $("#proactiveBlockWeightTable").click(function(){
        setClickEvent();
    });

    /*var dataPie = [],
        pieSeries = Math.floor(Math.random() * 6) + 3;

    for (var i = 0; i < pieSeries; i++) {
        dataPie[i] = {
            label: "Series" + (i + 1)
            data: Math.floor(Math.random() * 100) + 1
        }
    }
*/

    var dataPie = [];
    dataPie[0]={label:$("#cpiScore").attr('class'),data:$("#cpiScore").val()};
    dataPie[1]={label:$("#revenues").attr('class'),data:$("#revenues").val()};
    dataPie[2]={label:$("#salesModelRelationship").attr('class'),data:$("#salesModelRelationship").val()};
    dataPie[3]={label:$("#natureOfBusinessOperations").attr('class'),data:$("#natureOfBusinessOperations").val()};
    dataPie[4]={label:$("#governmentInteraction").attr('class'),data:$("#governmentInteraction").val()};
    $.plot('#thirdPieChart', dataPie, {
        series: {
            pie: {
                show: true,
                radius:0.9,
                innerRadius:0.3,
                label: {

                    show: true,
                    radius:7/8,
                    formatter: labelFormatter,
                    background: {
                        opacity: 0.8
                    }
                }
            }
        },
        legend: {
            show: true
        }
    });
    function labelFormatter(label, series) {
        return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
    };
    $(".demoPieChart").show();
    $("#weightedScoreButton").click(function () {
        if (year == null) {
            $.prompt("Please select date range. ");
        } else {
            window.location = "GlobalMap.html";
        }
    });


    var date = new Date();
    var defaultDate = date.getDate();
    var defaultMonth = date.getMonth();
    var defaultYear = date.getFullYear();
    //setupTablesorter();

    $("#weightedDatePicker").datepicker({
        dateFormat: 'dd/mm/yy',
        selectWeek: true,
        changeYear: true,
        inline: true,
        onSelect: function(dateText) {

            var startDate = $.datepicker.parseDate('dd/mm/yy', dateText);
            var selDate = startDate.getDate();
            var selMonth = startDate.getMonth();
            var selYear = startDate.getFullYear();
            var $ret_date = $("#weightedDatePicker2");
            $ret_date.datepicker("setDate", new Date(selYear+1,selMonth,selDate));
            dateDeterminator('weightedDatePicker');

        }
    }).datepicker("setDate", new Date(defaultYear-1,defaultMonth,defaultDate));

    $("#weightedDatePicker2").datepicker({
        dateFormat: 'dd/mm/yy',
        selectWeek: true,
        changeYear: true,
        inline: true,
        onSelect: function(dateText) {
            dateDeterminator('weightedDatePicker2');

        }
    }).datepicker("setDate", new Date(defaultYear-1,defaultMonth,defaultDate));


    $("#dateBetween").click(function(){
        $("#specificDateSelector").hide();
        $("#datePickerDiv").show();
    });

    $("#searchGroup").click(function(){
        $("#datePickerDiv").hide();
        $("#specificDateSelector").show();
    });


});
function setClickEvent(){
    $('.buttonUnderTable').click(function(){
        window.location = "saveProactiveProject.html?tType=1";
    });
    $('.buttonUnderTable2').click(function(){
        window.location = "saveProactiveProject.html?tType=2";
    });
    $('.buttonUnderTable3').click(function(){
        window.location = "saveProactiveProject.html?tType=3";
    });
}

var extension = null;
var year = null;
function dateDeterminator(datepickerID) {
    unCheckAll();
    if((datepickerID != 'weightedDatePicker') && (datepickerID != 'weightedDatePicker2')){
        document.getElementById(datepickerID).style.backgroundColor = "#303030" ;
        document.getElementById(datepickerID).style.color = "#FFFFFF" ;
    }
    document.getElementById('hiddenId').value = datepickerID;
    year = datepickerID;


    function unCheckAll(){
        document.getElementById('1Y').style.backgroundColor = "#C0C0C0" ;
        document.getElementById('1Y').style.color = "#3B4C56" ;
        document.getElementById('2Y').style.backgroundColor = "#C0C0C0" ;
        document.getElementById('2Y').style.color = "#3B4C56" ;
        document.getElementById('3Y').style.backgroundColor = "#C0C0C0" ;
        document.getElementById('3Y').style.color = "#3B4C56" ;
        document.getElementById('4Y').style.backgroundColor = "#C0C0C0" ;
        document.getElementById('4Y').style.color = "#3B4C56" ;
        document.getElementById('dateBetween').style.backgroundColor = "#C0C0C0" ;
        document.getElementById('dateBetween').style.color = "#3B4C56" ;

        var path = $(location).attr('href');
        if (path.indexOf("weightedScreen") < 0) {
            $('#4Y').attr("disabled", true);
            $('#3Y').attr("disabled", true);
            $('#2Y').attr("disabled", true);
            $('#1Y').attr("disabled", true);
            $('#dateBetween').attr("disabled", true);
            $('#searchGroup').attr("disabled", true);
            $('#weightedDatePicker').attr("disabled", true);
            $('#weightedDatePicker2').attr("disabled", true);



        }

    }
    if(datepickerID == 'weightedDatePicker' || datepickerID == 'weightedDatePicker2'){
        extension = "&fromDate="+document.getElementById('weightedDatePicker').value+"&toDate="+
            document.getElementById('weightedDatePicker2').value;

    }
    callAjax(datepickerID,extension);
}
function callAjax(datepickerID,fromDate,toDate) {
    var postUrl = "ajaxCall.html?id=" + datepickerID + (extension != null ? extension: '') ;
    $('#dcTable').html("loading");
    $.ajax({
        type: 'GET',
        url:  postUrl,
        success: function(msg) {
            $('#dcTable').html(msg);
        }
    });
}