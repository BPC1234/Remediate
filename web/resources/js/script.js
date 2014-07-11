$(function() {
	//$("#loading").hide();
	
    var icons = {
        header:"ui-icon-carat-1-e",
        activeHeader: "ui-icon-carat-1-s"
    };
    $( "#accordionID" ).accordion({
        collapsible: true,
        icons: icons,
        heightStyle: "content"
    });

});
/*Added by ayon*/
var dateSelector;
/*end of adding*/

// initially set ////
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

/*$("#backOneYear").click(function () {

    alert("i ama here");
});*/

$(document).ready(function(){
    $('table#workFlowTable tbody td').click(function(){
        $(this).parent().parent().children().each(function(){
            $(this).children().each(function(){
                if($(this).hasClass("redRow"))
                    $(this).removeClass("redRow");
            });
        });
        $(this).parent().children().each(function(){
            $(this).addClass("redRow");
        });

//        alert(" mouse clecked");
    });

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

       // $('table #64').addClass("redRow");
   /* $("#tabs1").css("height",$("#tabs").innerHeight());
    var iw = $('body').innerWidth();
    var tabsHeight = $('#tabs1').innerHeight();
    $( "#tabs" ).resizable({
       maxHeight:tabsHeight,
       minHeight:tabsHeight,
       minWidth: (iw/4),
       maxWidth: 3*(iw/4)
       *//* maxHeight: 250,
        maxWidth: 350,
        minHeight: 150,
        minWidth: 200*//*
    });
   // var rightSideSpace = ($('#tabs').innerWidth() + $('#tabs1').innerWidth());
    $("#tabs ").bind("resize", function (event, ui) {
       // var setWidth = $("#tabs").width();
        //$('#tabs1').width((iw-(iw-rightSideSpace))-setWidth);
       // $('#tabs1').width(rightSideSpace-setWidth);

        var tabsWidth = $('#tabs').innerWidth();
        var tabs1Width = $('#tabs1').innerWidth();
        //var difference = iw-(tabsWidth+tabs1Width);
        $('#tabs1').width(iw-tabsWidth-(20));
        *//*if(difference <= 25){
            $('#tabs1').width(iw-tabsWidth-(25-difference));
        }else{
            $('#tabs1').width(iw-tabsWidth-25);
        }*//*

    });*/

    $("#backOneYear").click(function () {
        var fDate = $("#weightedDatePicker").val();
        var dateObj = $.datepicker.parseDate('dd/mm/yy', fDate );
        var day = dateObj.getDate();
        var month = dateObj.getMonth();
        var year = dateObj.getFullYear();
        var $fromDate = $("#weightedDatePicker");
        $fromDate.datepicker("setDate", new Date(year-1,month,day));
        var $toDate = $("#weightedDatePicker2");
        $toDate.datepicker("setDate", new Date(year,month,day));
        dateDeterminator('weightedDatePicker');

//        alert(" from date: " + selYear-1);

    });

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

    $('.config .dropdown-toggle').dropdown();
    $( "#accordion" ).accordion({
        collapsible: true
    });
    $('html').click(function(event){
        if (event.target.class != 'configureSelectorDiv') {
            $('.configureSelectorDiv').hide();
        }
    });
    $('.tree li').each(function(){
        if($(this).children('ul').length > 0){
            $(this).addClass('parent');
        }
    });
    $('#accordionParent').resizable({
        maxHeight: '80%',
        maxWidth: 350,
        minHeight: 500,
        minWidth: '80%',
        resize: function() {
            $( "#accordionID" ).accordion( "refresh" );
        }
    });
    $('.tree li.parent > a').click(function(){
        $(this).parent().toggleClass('active');
        $(this).parent().children('ul').slideToggle('fast');
    });


    $(".optionsSelectorDiv").hide();
    /*		$(".tablesorter").tablesorter({widthFixed: false});
     $(".tablesorter").tablesorterPager({container: $("#pager"),size:6});
     $(".tablesorter2").tablesorter({widthFixed: true});
     $(".tablesorter2").tablesorterPager({container: $("#pager2"),size:6});
     $(".reactiveTablesorter").tablesorter({widthFixed: true});
     $(".reactiveTablesorter").tablesorterPager({container: $("#pager2"),size:6});*/

    // $(".tablesorter10").tablesorter({widthFixed: true});
    // $(".tablesorter10").tablesorterPager({container: $("#pager10"),size:4});
    $("#realtimeSelectedWeekInfo .tablesorterr").tablesorter({widthFixed: false});
    $("#realtimeSelectedWeekInfo .tablesorterr").tablesorterPager({container: $("#pager2"),size:15});
    $("#changeOption").click(function () {
        if ($(".optionsSelectorDiv").css('display') == "table")
            $(".optionsSelectorDiv").hide();
        else
            $(".optionsSelectorDiv").show();
    });

    $("#optionsId").click(function () {
        $("#optionsId option:selected").each(function () {
            $(".optionsSelectorDiv").hide();
        });
    });
    $("#pager").css({ "position":"inherit"});
    $("#pagesizeId").change(function () {
        $("#pager").css({ "position":"inherit"});
    });

    $("#pager2").css({ "position":"inherit"});
    $("#pagesizeId").change(function () {
        $("#pager2").css({ "position":"inherit"});
    });
    $("#pager10").css({ "position":"inherit"});
    $("#pagesizeId").change(function () {
        $("#pager10").css({ "position":"inherit"});
    });

    $('.map').maphilight({fade: false});

    //setClickEvent();

    /*		$(".tablesorter").bind("sortEnd",function() {
     setClickEvent();
     });
     $(".tablesorter10").bind("sortEnd",function() {
     setClickEvent();
     });
     $(".tablesorter2").bind("sortEnd",function() {
     setClickEvent();
     });*/

    var date = new Date();
    var defaultDate = date.getDate();
    var defaultMonth = date.getMonth();
    var defaultYear = date.getFullYear();



    //setupTablesorter();
    $( "#datepicker" ).datepicker({
        dateFormat: 'dd/mm/yy',
        selectWeek: true,
        inline: true,
        onSelect: function(dateText) {

            var startDate = $.datepicker.parseDate('dd/mm/yy', dateText);
            var selDate = startDate.getDate();
            var selMonth = startDate.getMonth();
            var selYear = startDate.getFullYear();
            var $ret_date = $("#datepicker2");
            $ret_date.datepicker("setDate", new Date(selYear+1,selMonth,selDate));
        }
    }).datepicker("setDate", new Date(defaultYear-1,defaultMonth,defaultDate));
    $( "#datepicker2" ).datepicker({
        dateFormat: 'dd/mm/yy'
    }).datepicker("setDate",  new Date(defaultYear,defaultMonth,defaultDate));
    $( ".datepicker3" ).datepicker({
        dateFormat: 'dd/mm/yy'
    }).datepicker("setDate",  new Date(defaultYear,defaultMonth,defaultDate));
    $( ".datepicker5" ).datepicker();

    $("#dateBetween").click(function(){
        $("#specificDateSelector").hide();
        $("#datePickerDiv").show();
    });

    $("#searchGroup").click(function(){
        $("#datePickerDiv").hide();
        $("#specificDateSelector").show();
    });

    $("#workFlowTable").click(function(){
        setClickEvent();
    });

    $("#summeryTable2").click(function(){
       // window.location = "txDetail.html";
        $("#tabs #ui-id-3").click();
    });

    $("#tablesorterForSupportingDocument tbody tr").click(function(){//click anywhere in a row
        //alert($(this).find(".supportingDocId").text() );
        var postUrl = "./supportingDocumentShow.html?supportingDocumentId=" + $(this).find(".supportingDocumentId").text().trim() ;
        //window.location = postUrl;
        var win = window.open(postUrl, '_blank');
        win.focus();

    });

    $(".icgaWorkFlowTable tbody tr").click(function(){//click anywhere in a row
        var extension = "ctrlId="+$(this).find(".ctrlId").text()
        $("#content").css('opacity','0.6');
        $('#loading').css('left',($('#content').innerWidth() /2));
        $("#loading").show();
        callAjaxForCountryWiseTxDiv(extension);
    });

    function callAjaxForCountryWiseTxDiv(extension) {

        var postUrl = "./ajaxCallForICGACountryWiseTx.html?"+extension;
        $(document).ready(function() {
            $.ajax({
                url : postUrl ,
                type : 'GET',
                async: false,
                data : {},
                success : function(data) {

                    $('#internalControlGapAnalysisControlwiseInfo').replaceWith(data);
                    $("#internalControlGapAnalysisControlwiseInfo").show();
                    $(".tablesorterForAudittrial").flexigrid({


                        colModel : [
                            {display: 'Date', name : 'date', width : 134, sortable : true, align: 'left'},
                            {display: 'User', name : 'user', width : 134, sortable : true, align: 'left'},
                            {display: 'Event', name : 'Event', width : 231, sortable : true, align: 'left'}
                        ],

                        width: 400,
                        height: 200
                    });

                    $(".icgaCountryWiseTxTable").flexigrid({


                        colModel : [
                            {display: 'Country Name', name : 'Country Name', width :950, sortable : true, align: 'left'},
                            {display: 'Total Transaction', name : 'Total Transaction', width : 100, sortable : true, align: 'left'}

                        ],

                        width: 600,
                        height: 200
                    });

                    $("#loading").hide();
                    $("#content").css('opacity','1.0');

                }

            })

        });


    }

    $("#reactiveDetailViewDiv #summeryTable2").click(function(){
        window.location = "ReactiveTransactionDetails.html";
    });

    $(".weightedScore .roundedButtonLabel").click(function () {
        if (year == null) {
            alert("Please select date range. ");
//            window.location = "weightedScreen.html";
        } else {
            window.location = "GlobalMap.html";
        }
    });

    $(".roundedButtonLabel").mouseover(function(){
        var className = $(this).parent().attr('class');
        $(this).children().next().children().addClass("redRow");
        $(this).parent().css({ "background-image":"url(\"../resources/images/"+className+"_on_focus.png\")"});
        if(className == "proactive")
            $("#divisionOfProactive").show();
        if(className != "cpiScore" && className != "revenues" && className != "salesModelRelationships" && className != "natureOfBusinessOperations" && className != "governmentInteraction"){

            if(className == "reactive"){
                $("."+className+ " .roundedButtonLabel").click(function(){
                    window.location = "ReactiveWorkflow.html";
                });
            }else if(className == "realTimeMonitoring"){
                $("."+className+ " .roundedButtonLabel").click(function(){
                    window.location = "RealtimeMonitoringWorkflow.html";
                });
            }/*else if(className == "internalControlsGapAnalysis"){
                $("."+className+ " .roundedButtonLabel").click(function(){
                    window.location = "./internalCtrlGapAnalysis.html";
                });
            }*/else if(className == "reporting"){
                $("."+className+ " .roundedButtonLabel").click(function(){

                });
            }
        }
    });

    $("#realtimeMonitoringWorkflowContentWrapper #workFlowTable").click(function(){
        $("#realtimeSelectedWeekInfo").show();
    });
    $("#internalControlGapAnalysisContentWrapper #workFlowTable").click(function(){
        $("#internalControlGapAnalysisControlwiseInfo").show();
    });


    var className = $(".roundedButtonLabel").parent().attr('class');
    $(".roundedButtonLabel").children().next().show();

    if(className == "proactive")
    //$("#divisionOfProactive").show();
        if(className != "cpiScore" && className != "revenues" && className != "salesModelRelationships" && className != "natureOfBusinessOperations" && className != "governmentInteraction"){
            if(className == "weightedScore"){
                $("."+className+" .roundedButtonLabel").click(function(){
                    window.location = "GlobalMap.html";
                });
            }else{
                $("."+className+ " .roundedButtonLabel").click(function(){
                    window.location = "weightedScreen.html";
                });
            }
        }

    $(".roundedButtonLabel").mouseout(function(){
        var className = $(this).parent().attr('class');
        $(".weight").removeClass("redRow");
        //$(this).children().next().hide();
        $(this).parent().css({ "background-image":"url(\"../resources/images/"+className+"_on_unfocus.png\")"});
    });
    $("#divisionOfProactive").mouseleave(function(){
        $("#divisionOfProactive").hide();
    });


    $("#divisionOfProactive").click(function(event){
        event.stopPropagation();
    });


    $(".configureSelectorDiv").hide();
    $(".configButtonDiv").click(function (event) {
        if ($(".configureSelectorDiv").css('display') == "table")
            $(".configureSelectorDiv").hide();
        else {
            $(".configureSelectorDiv").show();
        }
        event.stopPropagation();
    });

    /*$( "#analyzedControlsTabs" ).tabs();
    $( "#tabs" ).tabs();
    $( "#tabs1" ).tabs();
    $( "#tabs2" ).tabs();
    $( "#subTabsUnderTabs1" ).tabs();*/


    $("#accordionID").accordion( "option", "active", 2 );
    $("#collapseDashbord").click(function (event) {
        if($("#accordionParent").css("width") != "0px"){
            $("#accordionParent").css("width","0%");
            $("#accordionParent").css("display","none");
            $("#collapseDashbord").css("background-position","-24px -23px");
            $(".rightSideDiv").css("width","100%");
        }else{
            $("#accordionParent").css("width","20%");
            $("#collapseDashbord").css("background-position","-1px -23px");
            $(".rightSideDiv").css("width","80%");
            $("#accordionParent").css("display","block");
        }
    });
    /*Added by ayon*/
    var dateSelector = 1;
    // $(".button:eq("+dateSelector+")").addClass("focusedButton");
    // $(".button").click(function(){
    // $(".button").each(function(){
    // if($(this).hasClass("focusedButton"))
    // $(this).removeClass("focusedButton");
    // });
    // $(this).addClass("focusedButton");
    // });

    $(".controlLine .dropdown-menu li a").click(function(){
        $(this).parent().parent().prev().first().text($(this).text());
        $(this).parent().parent().prev().first().val($(this).text());
    });
    $(".reactiveField .dropdown-menu li a").click(function(){
        $(this).parent().parent().prev().first().text($(this).text());
        $(this).parent().parent().prev().first().val($(this).text());
    });

/*    $(".checkboxCTRLine").each(function(){
        $(".btn-group .btn").addClass("disabled");
        $(".controlLine .textarea").prop("disabled",true);
    });*/
/*
    $(".checkboxCTRLine").click(function () {
        if(this.checked){
            $(this).next().next().children("a:first").removeClass("disabled");
            $(this).next().next().next().prop("disabled",false);
        }else{
            $(this).next().next().children("a:first").addClass("disabled");
            $(this).next().next().next().attr("disabled",true);
        }
    });*/

    $(".radioDecissionArea").click(function(){
        if($(this).val() == "Additional Information Required")
            $(".email").show();
        else
            $(".email").hide();
    });

    $("#tableReactiveDiv #reactiveTabelData tr ").click(function(){
        var id = $(this).find("#projectId").text();

        if(id != "")
        window.location = "reactiveSummaryView.html?reactiveProjectId=" + id ;
    });


    // $("#realtimeSelectedWeekInfo #summeryTable2").click(function(){
    // window.location = "RealtimeSummaryView.html";
    // });

    $("#realtimeSelectedWeekInfo #summeryTable2").click(function(){
        window.location = "RealtimeTransactionDetails.html";
    });
    /////////////////////////
    $(document).on('click', '#internalControlGapAnalysisControlwiseInfo #summeryTable2 tbody tr', function(e) {
        var regionId = $(this).find(".regionId").text();
        var ctrlId =  $("#ctrlId").val();
        var icga = 0;
        icga = $("#content #icga").val();
        var extension = "regionId="+regionId+"&ctrlId="+ctrlId;
//        alert(extension);
        window.location = "InternalCtrlGapAnalysisSummaryView.html?icga="+icga+"&" + extension;
    });
  /*  $("#internalControlGapAnalysisControlwiseInfo #summeryTable2").click(function(){
        alert(" #internalControlGapAnalysisControlwiseInfo #summeryTable2.click ");
        window.location = "InternalCtrlGapAnalysisSummaryView.html";
    });*/

    $("#internalCtrlGapAnalysisSummaryDiv #summeryTable2").click(function(){
        window.location = "InternalCtrlGapAnalysisTxDetails.html";
    });

    $("#newRiskAssessment").click(function(){
        window.location = "weightedScreen.html";

    });

    $("#continueRiskAssessment").click(function(){

        window.location = "ContinueRiskAssessmentWorkFlow.html";
    });
    $("#dueDiligence").click(function(){
        /*window.location = "DueDilligance.html";*/
    	window.location = "dueDiligenceLanding.html";
    });
    $("#continueRiskAssessmentContentWrapper #contTableData tr ").click(function(){
        var id = $(this).find("#projectId").text();
        if(id != "")
        window.location = "newRiskAssessmentsummaryView.html?cont=1&proactiveProjectId=" + id;
    });


   /* $("#analyzeByControls").click(function(){
        window.location = "InternalCtrlGapAnalysisAC.html";
    });

    $("#existingControls").click(function(){
        window.location = "./internalCtrlGapAnalysis.html";
    });*/

/*    $("#analyzedByControlNextButton").click(function(){
        window.location = "AnalyzedControlsToInternalCtrlGapAnalysis.html";
    });*/

    $("#continueRiskAssessmentTransactionSummaryDiv #summeryTable2").click(function(){
      //  window.location = "ContRiskAssessmentTxDetail.html";
    });
    /*Start of Testing*/

    $('#content1').find(':input').prop('disabled', true);
    $('#content1 a').click(function(e) {
        e.preventDefault();
    });

    $(".checkboxCTRLine").each(function(){
        if(this.checked){
            $(this).next().next().children("a:first").removeClass("disabled");
            $(this).next().next().next().prop("disabled",false);
            $(".commentRegion").prop("disabled",false);
            $(".btn-file").prop("disabled",false);
            $(".upFile").prop("disabled",false);
        }
    });

    /*End of Testing*/

    $("#Payment").datepicker();

   	if ($("#continueRiskAssessment-grid").length > 0) {
	    var fields=['id', 'regionText', 'transactionType', 'dateBetween', 'createdText', 'createdBy'];
	    var customCol=[{	text: "ID",
					    	width: 50,
					    	sortable: true,
					    	dataIndex: 'id'
					    },
					    {	text: "Region",
				        	width: 140,
				        	sortable: true,
				        	dataIndex: 'regionText'
				        },
				      /*  {	text: "Transaction Type",
				        	width: 200,
				        	sortable: true,
				        	dataIndex: 'transactionType'
				         },*/
				        {	text: "Date Between",
				        	 width: 185,
				        	 sortable: true,
				        	 dataIndex: 'dateBetween'
				         },
				        {	 text: "Created Date",			            	 
				        	 width: 120,
				             sortable: true,
				             dataIndex: 'createdText'
				         },
				        {	 text: "Author",
				             width: 80,
				             sortable: true,
				             dataIndex: 'createdBy'
				         }
				        ];
	    loadPagingGrid (  fields
	    				, './ContinueRiskAssessmentGrid.html'
	    				, 'riskAssessments'
	    				, customCol
	    				, 400
	    				, 'continueRiskAssessment-grid'
	    				, 'Continued Risk Assessment'
	    				, 'Displaying risk assessments {0} - {1} of {2}'
	    				, 'No risk assessments to display'
	    				, function(dv, record, item, index, e) {window.location =  'newRiskAssessmentsummaryView.html?proactiveProjectId=' + record.get("id");}
	    				);
   }

   /* $(".tableReactiveWorkflow").flexigrid({

        colModel : [
            {display: 'Project Name', name : 'projectName',width : 215, sortable : true, align: 'left'},
            {display: 'Region', name : 'region',width : 175, sortable : true, align: 'left'},
            {display: 'Total amount', name : 'amount',width : 135, sortable : true, align: 'left'},
            {display: 'Payment Date', name : 'date',width : 125, sortable : true, align: 'left'},
            {display: 'Transaction Type', name : 'type',width : 205, sortable : true, align: 'left'}

        ],

        width: 500,
        height: 200
    });
*/


    $(".tableNewRisk").flexigrid({

        colModel : [
            {display: 'Year', name : 'year',width : 85, sortable : true, align: 'left'},
            {display: 'CPI Score', name : 'cpi',width : 85, sortable : true, align: 'left'},
            {display: 'Revenues', name : 'revenues',width : 85, sortable : true, align: 'left'},
            {display: 'No. of Agents', name : 'agents',width : 85, sortable : true, align: 'left'},
            {display: 'No. of Customs Brokers', name : 'customerBrokers',width : 145, sortable : true, align: 'left'},
            {display: 'Revenue Attributable to Agents', name : 'revenueAttribute',width : 168, sortable : true, align: 'left'},
            {display: 'No. of Govt. Customers', name : 'govtCust',width : 145, sortable : true, align: 'left'},
            {display: 'Date Last FCPA Audit/Investigation', name : 'audit',width : 175, sortable : true, align: 'left'}
        ],

        width: 973,
        height: 200
    });

    $(".tableNewRiskSummary").flexigrid({

        colModel : [
            {display: 'Transaction ID', name : 'id', sortable : true, align: 'left'},
            {display: 'Date', name : 'date', sortable : true, align: 'left'},
            {display: 'Amount', name : 'amount', sortable : true, align: 'left'},
            //{display: 'Region', name : 'region', sortable : true, align: 'left'},
            {display: 'Document Creator', name : 'docCreator', sortable : true, align: 'left'},
            {display: 'Approver', name : 'approver', sortable : true, align: 'left'},
            {display: 'Date of Approval', name : 'appDate', sortable : true, align: 'left'}
            //{display: 'Decission', name : 'decission', sortable : true, align: 'left'}
        ],

        width: 1095,
        height: 200
    });


    $(".tableReactiveProject").flexigrid({

        colModel : [
            {display: 'Project Name', name : 'name', width : 130, sortable : true, align: 'left'},
            {display: 'Region', name : 'region', sortable : true, align: 'left'},
            {display: 'Total Amount', name : 'amount', sortable : true, align: 'left'},
            {display: 'Payment Date', name : 'date', sortable : true, align: 'left'},
            {display: 'Transaction Type', name : 'ttype', width : 136, sortable : true, align: 'left'},
            {display: '', name : 'edit', width : 96,sortable : true, align: 'left'},
            {display: '', name : 'delete', sortable : true, align: 'left'}

        ],

        width: 846,
        height: 200
    });

    $(".tableUser").flexigrid({

        colModel : [
            {display: 'User Name', name : 'name',sortable : true, align: 'left'},
            {display: 'Role', name : 'role', sortable : true, align: 'left'},
            {display: 'Password', name : 'password', sortable : true, align: 'left'},
            {display: 'Active', name : 'active', sortable : true, align: 'left'},
            {display: '', name : '', sortable : true, align: 'left'},
            {display: '', name : '', sortable : true, align: 'left'}

        ],
        width: 700,
        height: 200
    });
    $(".tablesorter200").flexigrid({

        colModel : [
            {width : 249, sortable : true, align: 'center'},
            {width : 194, sortable : true, align: 'left'},
            {width : 200, sortable : true, align: 'left'},
            {width : 100, sortable : true, align: 'left', hide: true},
            {width : 100, sortable : true, align: 'right'}
        ],

        width: 700,
        height: 200
    });

    $("#changePasswordUserName").prop('readonly', true);

    $(".tablesorterForSupportingDocument").flexigrid({


        colModel : [
            {display: 'File Name', name : 'File Name', width : 134, sortable : true, align: 'left'},
            {display: 'Download', name : 'Download', width : 134, sortable : true, align: 'left'},
            {display: 'Uploaded By', name : 'Uploaded By', width : 100, sortable : true, align: 'left'},
            {display: 'Date', name : 'Date', width : 134, sortable : true, align: 'left'},
            {display: '', name : '', width : 100, sortable : true, align: 'left'}

        ],

        width: 600,
        height: 200
    });

    $('table#tablesorterForSupportingDocument tbody td').click(function(){
        $(this).parent().parent().children().each(function(){
            $(this).children().each(function(){
                if($(this).hasClass("redRow"))
                    $(this).removeClass("redRow");
            });
        });
        $(this).parent().children().each(function(){
            $(this).addClass("redRow");
        });
    });

    $(".icgaWorkFlowTable").flexigrid({


        colModel : [
            {display: 'Control Name', name : 'Control Name', width :950, sortable : true, align: 'left'},
            {display: 'Total Transaction', name : 'Total Transaction', width : 100, sortable : true, align: 'left'}

        ],

        width: 600,
        height: 200
    });

    $(".icgaCountryWiseTxTable").flexigrid({


        colModel : [
            {display: 'Country Name', name : 'Country Name', width :950, sortable : true, align: 'left'},
            {display: 'Total Transaction', name : 'Total Transaction', width : 100, sortable : true, align: 'left'}

        ],

        width: 600,
        height: 200
    });
   /* $(".tableContinueRisk").flexigrid({

        colModel : [
            {display: 'Region', name : 'region',width : 249, sortable : true, align: 'left'},
            *//*{display: 'Transaction Type', name : 'type',width : 194, sortable : true, align: 'left'},*//*
            {display: 'Date Range', name : 'date',width : 200, sortable : true, align: 'left'},
            {display: 'Created Date', name : 'created',width : 100, sortable : true, align: 'left'},
            {display: 'Author', name : 'author',width : 100, sortable : true, align: 'left'}
        ],

        width: 798,
        height: 200
    });
*/
   /* $(".tableRealTimeProject").flexigrid({

        colModel : [
            {display: 'Assignment No', name : 'name', width : 130, sortable : true, align: 'left'},
            {display: 'Assigned To', name : 'assignedto', width : 150,sortable : true, align: 'left'},
            {display: 'Assignment Status', name : 'rassset', width : 120, sortable : true, align: 'left'},
            {display: 'Assignment Size', name : 'nooftrx', width : 100,sortable : true, align: 'right'},
//            {display: 'Reviewed', name : 'review', width : 100, sortable : true, align: 'right'},
            {display: 'Unassign', name : 'unassign', width : 100, sortable : false, align: 'center'}

        ],

        width: 702,
        height: 400
    });
*/
    $('#contTableData').dataTable( {
            "sDom": 'T<"clear">lfrtip'
     } );


    $('.policyTableGrid').dataTable( {
        "sDom": 'T<"clear">lfrtip'
    } );

    var index = 0;

   // $("#subTabsUnderTabs1").removeAttr('class');
    /*$("#subTabsUnderTabs1").tabs("select", "#tabs-1UnderTabs1");*/
    $('.content1').click(function(){

    });
    $('#cancelButton').click(function(){
        var oldURL = document.referrer;
        window.location = oldURL;
    });

    $('#controlListTable').dataTable( {
        "sDom": 'T<"clear">lfrtip'
    } );

    $('#policyListTable').dataTable( {
        "sDom": 'T<"clear">lfrtip'
    } );

    $("#controlListId .DTTT_container").before('<div class="DTTT_container"><a id="addNewOneControl" class="DTTT_button"><span>Add</span><div style="position: absolute; left: 0px; top: 0px; width: 47px; height: 26px; z-index: 99;"></div></a></div>');
    $("#policyListId .DTTT_container").before('<div class="DTTT_container"><a id="addNewPolicy" class="DTTT_button"><span>Add</span><div style="position: absolute; left: 0px; top: 0px; width: 47px; height: 26px; z-index: 99;"></div></a></div>');

    $("#addNewOneControl").click(function(){
        window.location = "addControl.html?controlId=0";
    });

    $("#addNewPolicy").click(function(){
        window.location = "addPolicy.html";
    });

   /* $('.tableRealTimeProject').dataTable( {
        "sDom": 'T<"clear">lfrtip'
    } );*/

    $('#reactiveTabelData').dataTable( {
        "sDom": 'T<"clear">lfrtip'
    } );
});

function test(com, grid) {
    if (com == 'Delete') {
        confirm('Delete ' + $('.trSelected', grid).length + ' items?')
    } else if (com == 'Add') {
        alert('Add New Item');
    }
}

function setupTablesorter() {

    $('#tableDiv .tablesorter').each(function (i, e) {
        //alert(2);
        var myHeaders = {}
        $(this).find('.nosort').each(function (i, e) {
            //alert(3);
            myHeaders[$(this).index()] = { sorter: false };
        });
    });
    $('table .tablesorter2').each(function (i, e) {
        var myHeaders = {}
        $(this).find('th.nosort').each(function (i, e) {
            myHeaders[$(this).index()] = { sorter: false };
        });
    });
    // $('table.tablesorter10').each(function (i, e) {
    // var myHeaders = {}
    // $(this).find('th.nosort').each(function (i, e) {
    // myHeaders[$(this).index()] = { sorter: false };
    // });
    // });    
}

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

    $('table#summeryTable tbody td').click(function(){
        $(this).parent().parent().children().each(function(){
            $(this).children().each(function(){
                if($(this).hasClass("redRow"))
                    $(this).removeClass("redRow");
            });
        });
        $(this).parent().children().each(function(){
            $(this).addClass("redRow");
        });
    });$('table#summeryTable2 tbody td').click(function(){
        $(this).parent().parent().children().each(function(){
            $(this).children().each(function(){
                if($(this).hasClass("redRow"))
                    $(this).removeClass("redRow");
            });
        });
        $(this).parent().children().each(function(){
            $(this).addClass("redRow");
        });
    });



}