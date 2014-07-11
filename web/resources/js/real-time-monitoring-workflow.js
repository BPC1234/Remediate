var status="Completed";
var loggedUser;
var assignedPerson;
var isCoded=true;
var isPartialData=false;
var requiredMessage = "";
var isControlNoOrSC=false;
var realTimeProjectId=0;
var updatedTrxDetails= false;
var saveContSel;
var saveConCom;
var saveDes;
var saveDesCom;
var prevContSel ;
var prevConCom ;
var prevDes ;
var prevDesCom ;
var requiredFieldFocus = "requiredFieldFocusClass";
var transactionIds="Transaction ";
var ruleId = '';
var summeryTableForNewRiskAssesmentFlexigridObject;
var isTrue='0';
var inCodedTxLiObj = null;
var summeryTableForNewRiskAssesmentJSonData = new Object();
var trxId = 0;
var colorClassArr = ['maroon','mediumpurple','darkGreen','green','blue','lightRed'];
iaAnalystDop = '';

var analyst = "null";


function assignProject(rtProjectId) {
    iaAnalystDop = '<div class="userRoleCss" id="analystDiv"><select id="selectAnalyst" name="selectAnalyst">'+ $("#analystList").html()+'</select></div>'
    $(document).on("change","#selectAnalyst", function(){
        analyst = $("#selectAnalyst").val();
    });

    if(user.role == adminRole) {
        $.prompt('Click "Y" to confirm assignment Or Assign to ' + iaAnalystDop, {
            title: "",
            buttons: {"Yes": true, "No": false },
            submit: function (e, v, m, f) {
                if (v) {
                    analyst = $("#selectAnalyst").val();
                    window.location = "./getAssignmentReview.html?rtProjectId=" + rtProjectId + "&flag=" + v + "&analyst=" + analyst ;
                }
                else {
                    //Redirect to the list page
                }
            }
        });
    }else if(user.role == ia_managerRole || user.role == legalRole){
        window.location = "./getAssignmentReview.html?rtProjectId=" + rtProjectId;
    }  else {
        $.prompt("Click to confirm assignment", {
            title: "",
            buttons: {"Yes": true, "No": false },
            submit: function (e, v, m, f) {
                if (v) {
                    window.location = "./getAssignmentReview.html?rtProjectId=" + rtProjectId + "&flag=" + v + "&analyst=" + analyst ;
                }
                else {
                    //Redirect to the list page
                }
            }
        });
    }

}
function setDecisionAndCommentMessage(isDecisionSet) {
    if (isDecisionSet) {
        var resComment = $('#decisionCommentTable tr:nth-child(2)').html();
        if (typeof resComment == "undefined") {
            isCoded = false;
            addRequiredFocusClass("id", "decisionComment");
           /* if (requiredMessage == "") {
                requiredMessage += "Please ";
            }
            requiredMessage += "\n Give a comment with decision.";
*/
        } else {
            //There are some comment in responsive comment box;
        }

    } else {
        isCoded = false;
        if (requiredMessage == "") {
            requiredMessage += "Please ";
        }
        requiredMessage += "\n Select a decision with comment.";

        if ($('#decisionComment').val() == "") {
            addRequiredFocusClass("id", "decisionComment");
        }

    }
}
function setControlAndCommentMessage(isAllContAns, contComment) {
    if (isAllContAns) {
        if (isControlNoOrSC) {
            contComment = $("#commentRegion").val();
            if (contComment == "") {
                isCoded = false;
                addRequiredFocusClass("id", "commentRegion");
                if (requiredMessage == "") {
                    requiredMessage += "Please ";
                }
                requiredMessage += "\n Write a comment for 'No' or 'See Comment' for controls.";
            } else {
                //there are some text in control comment text box;
            }
        } else {
            //
        }
    } else {
        isCoded = false;
        if (requiredMessage == "") {
            requiredMessage += "Please ";
        }
        if ($("#commentRegion").val() == "") {
            addRequiredFocusClass("id", "commentRegion");
        }
        requiredMessage += "\n Answer all control and write comment for 'No' or 'See Comment'.";
    }
}
$(function () {

    var isUpdateControl=false;
    var contComment="";

    loggedUser= $("#loggedUser").val();
    // $(document).on('click', '#paneOneTotalDiv #tabOneInsidPaneOne ul li div div.flexigrid', function() { //click anywhere in a row

    $(document).on('click', '#summeryTableForNewRiskAssesment tbody tr', function() { //click anywhere in a row
        var txRow = this;
        if(isPartialData) {
            $.prompt("Are you sure you want to exit the transaction? All unsaved information will be lost.", {
                title: "",
                buttons: { "Yes": true, "No": false },
                submit: function(e,v,m,f){
                    if (v)
                    {
                        isPartialData = false;
                        getTransactionDetails(txRow);
                        selectedRow('#summeryTableForNewRiskAssesment',txRow);

                    }
                    else
                    {
//                        alert('');

                        //Redirect to the this page
                    }
                }
            });

        } else {
            getTransactionDetails(txRow);
            selectedRow('#summeryTableForNewRiskAssesment',txRow);
//            alert('last part')

        }
    });
    $("#analystList").hide();
    var columns;
    var searchItems;
    if (user.role == ia_managerRole ||user.role == legalRole) {
        columns = [
            {
                display: 'Assignment No',
                name: 'projectName',
                width: 'auto',
//            width : 'auto',
                sortable: true,
                align: 'left'
            },
            {
                display: 'Assignment Size',
                name: 'noOfTransaction',
//            width : 130,
                width: 'auto',
                sortable: true,
                align: 'right',
                hide: false
            },
            {
                display: 'Reviewed',
                name: 'review',
//            width : 120,
                width: 'auto',
                sortable: true,
                align: 'right'
            }  ,
            {
                display: 'Backend Data',
                name: 'id',
                width: 1,
                align: 'right',
                hide: true
            }
        ];

        searchItems = [
            {
                display: 'Assignment Size',
                name: 'no_of_transaction'
            },
            {
                display: 'AssignmentNo',
                name: 'project_name'
            }];

    } else  if(user.role == ia_analystRole || user.role == adminRole){
    columns = [{
        display : 'Assignment No',
        name : 'projectName',
        width : 'auto',
//            width : 'auto',
        sortable: true,
        align: 'left'
    },
        {
            display: 'Assigned To',
            name: 'assignTo',
//            width : 250,
            width: 'auto',
            sortable: true,
            align: 'left'
        },
        {
            display: 'Assignment Status',
            name: 'status',
//            width : 150,
            width: 'auto',
            sortable: true,
            align: 'left'
        },
        {
            display: 'Assignment Size',
            name: 'noOfTransaction',
//            width : 130,
            width: 'auto',
            sortable: true,
            align: 'right',
            hide: false
        },
        {
            display: 'Reviewed',
            name: 'review',
//            width : 120,
            width: 'auto',
            sortable: true,
            align: 'right'
        },
        {
            display: 'Outstanding Transactions',
            name: 'outstandingTransactions',
//            width : 150,
            width: 'auto',
            sortable: true,
            align: 'right'
        },
        {
            display: '',
            name: 'unAssignLabel',
//            width : 400,
            width: 'auto',
            sortable: false,
            align: 'center'

        } , {
            display : 'Backend Data',
            name : 'id',
            width:1,
            align : 'right',
            hide : true
        }];

        searchItems = [
            {
                display: 'Assignment Size',
                name: 'no_of_transaction'
            },
            {
                display: 'AssignmentNo',
                name: 'project_name'
            },
            {
                display: 'Assign',
                name: 'assign_to'
            },
            {
                display: 'Assignment Status',
                name: 'status',
                isdefault: true
            }
        ];
    }else  if(user.role == employeeRole || user.role == complianceRole){
    columns = [{
        display : 'Assignment No',
        name : 'projectName',
        width : 'auto',
        sortable: true,
        align: 'left'
    },
        {
            display: 'Assignment Size',
            name: 'noOfTransaction',
//            width : 130,
            width: 'auto',
            sortable: true,
            align: 'right',
            hide: false
        }, {
            display : 'Backend Data',
            name : 'id',
            width:1,
            align : 'right',
            hide : true
        }];

        searchItems =  '';
    }


    $(".realTimeProjectWorkFlix").flexigrid({
        url : 'assignmentList.html?tableName=realTimeProjectWorkFlix',
        dataType : 'json',
        colModel : columns,
        searchitems: searchItems,
        buttons: [
            { name: '.realTimeProjectWorkFlix', bclass: 'PDF', onpress: printTable },
            { name: '.realTimeProjectWorkFlix', bclass: 'XLS', onpress: toExcel },
            { name: '.realTimeProjectWorkFlix', bclass: 'CSV', onpress: tableToCSV },
            { name: '.realTimeProjectWorkFlix', bclass: 'RESET', onpress: resetTableData },
            { name: '.realTimeProjectWorkFlix', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "projectName",
        sortorder: "asc",
        usepager: true,
        title: 'Select Assignment to Begin Review',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 260,
        onSuccess: function () {
            addGrid($('.realTimeProjectWorkFlix'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

   var  myTxTableColumns = [{
        display : 'Assignment No',
        name : 'projectName',
        width : 'auto',
        sortable: true,
        align: 'left'
    },
        {
            display: 'Assignment Size',
            name: 'noOfTransaction',
//            width : 130,
            width: 'auto',
            sortable: true,
            align: 'right',
            hide: false
        }, {
            display : 'Backend Data',
            name : 'id',
            width:1,
            align : 'right',
            hide : true
        }];




    $("#myTransactionTable").flexigrid({
        url : 'myAssignmentList.html?tableName=myTransactionTable',
        dataType : 'json',
        colModel : myTxTableColumns,

        searchitems: [
        {
            display: 'Assignment Size',
            name: 'no_of_transaction'
        },
        {
            display: 'AssignmentNo',
            name: 'project_name'
        }
        ],
        buttons: [
            { name: '#myTransactionTable', bclass: 'PDF', onpress: printTable },
            { name: '#myTransactionTable', bclass: 'XLS', onpress: toExcel },
            { name: '#myTransactionTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#myTransactionTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#myTransactionTable', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "projectName",
        sortorder: "asc",
        usepager: true,
        title: 'Select Assignment to Begin Review',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 260,
        onSuccess: function () {
            addGrid($('#myTransactionTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });


    $('#transactionListLi a').click(function () {
        hideGroupingAndShowFlexigrid("#summeryTable1");
        if (isTrue == '0') {
            addGrid($('#summeryTableForNewRiskAssesment'), summeryTableForNewRiskAssesmentFlexigridObject);
            isTrue = '1';
        }

    });
    $('#summaryOfThirdPartyTransactionsLi a').click(function(){
//        var count = $(".summeryTableForNewRiskAssesment div.jqx-position-relative div div#groupsheader div").size();
            hideGroupingAndShowFlexigrid("#summeryTableForNewRiskAssesment");
      });
    globalExportParam = "&myRealTimeSummaryView="+$("#myRealTimeSummaryView").val();
    console.log('globalExportParam:'+globalExportParam)
    $("#summeryTableForNewRiskAssesment").flexigrid({
        width: 'auto',
        url: '../realtime/trxListInDetails.html?proactiveProjectId=' + $("#proactiveProjectId").val()
            + '&reactiveProjectId=' + $("#reactiveProjectId").val()
            + '&realTimeProjectId=' + $("#realTimeProjectId").val()
            + '&tableName=summeryTableForNewRiskAssesment'
            + "&ctrlId="+$("#controlId").val()
            + "&myRealTimeSummaryView="+$("#myRealTimeSummaryView").val()
            + '&ruleId='+ruleId,
        dataType : 'json',
        colModel : [ {
            display : 'Tx Id',
            name : 'trxId',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Date',
            name : 'dateStr',
            width : 80,
            sortable : true,
            align : 'left'
        }, {
            display : 'Amount',
            name : 'amountStr',
            width : 'auto',
            sortable : true,
            align : 'left'
        }/*, {
            display : 'Doc Creator',
            name : 'docCreator',
            sortable : true,
            align : 'left',
            hide : false
        }*/, {
            display : 'Approver',
            name : 'approver',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Date of App.',
            name : 'dateOfApprover',
            width : 'auto',
            sortable : true,
            align : 'right'
        }, {
            display : 'Rule Violated',
            name : 'ruleViolated',
            width : 'auto',
            sortable : true,
            align : 'center'

            } ,
            {
                display: 'Backend Data',
                name: 'transactionId',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            },
            {
                display: 'project Id',
                name: 'projectId',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            },
            {
                display: 'project Type',
                name: 'projectType',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            },
            {
                display: 'reviewed',
                name: 'reviewed',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            }
        ],

        searchitems: [
            {
                display: 'Transaction Id',
                name: 'trx.id'
            },
            {
                display: 'Date',
                name: 'transaction_date'
            },
            {
                display: 'Amount',
                name: 'amount'
            },
            {
                display: 'Approver',
                name: 'approver'

            },
            {
                display: 'Date Of Approver',
                name: 'trx.created'

            },
            {
                display: 'Rule Violated',
                name: 'rule_code'

            }
        ],
        buttons: [
            { name: '#summeryTableForNewRiskAssesment', bclass: 'PDF', onpress: printTable },
            { name: '#summeryTableForNewRiskAssesment', bclass: 'XLS', onpress: toExcel },
            { name: '#summeryTableForNewRiskAssesment', bclass: 'CSV', onpress: tableToCSV },
            { name: '#summeryTableForNewRiskAssesment', bclass: 'RESET', onpress: resetTableData },
            { name: '#summeryTableForNewRiskAssesment', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "trxId",
        sortorder: "asc",
        usepager: true,
        title: '',
        useRp: true,
        newp: $("#pageNo").val(),
        //page:$("#pageNo").val(),
        rp: 10,
        showTableToggleBtn: true,
        height: 260,
        preProcess:function (jsondata) {
                summeryTableForNewRiskAssesmentJSonData = jsondata;
                return jsondata;
        },
        onSuccess: function () {
            summeryTableForNewRiskAssesmentFlexigridObject = this;
            addGrid($('#summeryTableForNewRiskAssesment'), this);  //PATCH to get the grids to be responseive
            var rowSelectionNo = $("#serialNoForTableRowSelection").val();
            var newRowSelection = $("#serialNoForTableRowSelection").val();
            if (rowSelectionNo != 'tr:first')
                newRowSelection = "#" + rowSelectionNo;
            var tableRowNo = ".flexigrid div.bDiv #summeryTableForNewRiskAssesment tbody ";

            /* auto selection of table row */
            selectedRow('#summeryTableForNewRiskAssesment',$(tableRowNo+newRowSelection));
            highlightedRowForReviewed();

            var newExtension  = "txId="+$(tableRowNo+newRowSelection).find("[abbr=transactionId]").find('div').text()
                +"&proactiveProjectId="+$("#proactiveProjectId").val()
                +"&reactiveProjectId="+$("#reactiveProjectId").val()
                +"&projectType="+$(tableRowNo+newRowSelection).find("[abbr=projectType]").find('div').text()
                +"&projectId="+$(tableRowNo+newRowSelection).find("[abbr=projectId]").find('div').text()
                +"&ctrlId="+$("#controlId").val()
                +"&transactionId="+$(tableRowNo+newRowSelection).find("[abbr=trxId]").find('div').text()
                +"&serial="+rowSelectionNo
                +"&rtProjectId="+$("#realTimeProjectId").val()
                +"&pageNo="+$("#summeryTableForNewRiskAssesment").parent().parent('div').children('div.pDiv').children("div.pDiv2").children("div.pGroup").children("span.pcontrol").children('input').val()
                +"&ruleId=0"+ruleId;

            callAjaxForNewRiskAssesmentSummaryView(newExtension);
            highlightTransactions();
            setPopoverAction();
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });


    $("#summeryTable1").flexigrid({
        url: '../realtime/trxCountListInDetails.html?proactiveProjectId=' + $("#proactiveProjectId").val()
            + '&reactiveProjectId=' + $("#reactiveProjectId").val()
            + '&realTimeProjectId=' + $("#realTimeProjectId").val()
            + "&ctrlId="+$("#controlId").val()
            + "&myRealTimeSummaryView="+$("#myRealTimeSummaryView").val()
            + '&tableName=summeryTable1',
        dataType: 'json',
        colModel: [
            {
                display: 'Tx Count',
                name: 'trxCount',
                width: 60,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Rule',
                name: 'rule',
                width: 'auto',
                sortable: true,
                align: 'left'
            },
            {
                display: 'Backend Data',
                name: 'ruleIdStr',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            }
        ],

        searchitems: [
            {
                display: 'Rule',
                name: 'st.rule_title'
            }
        ],
        buttons: [
            { name: '#summeryTable1', bclass: 'PDF', onpress: printTable },
            { name: '#summeryTable1', bclass: 'XLS', onpress: toExcel },
            { name: '#summeryTable1', bclass: 'CSV', onpress: tableToCSV },
            { name: '#summeryTable1', bclass: 'RESET', onpress: resetTableData },
            { name: '#summeryTable1', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "trxCount",
        sortorder: "asc",
        usepager: true,
        title: '',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 260,
        onSuccess: function () {
            addGrid($('#summeryTable1'), this);  //PATCH to get the grids to be responseive

        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $('.trxAppSaveBtn').click(function () {
        $("#trxApprovalForm").submit();
    });

    $(document).on('click', '#trxApprovalDeleteButtonHtml', function () {
        var trxAppId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "transactionApprovalDelete.html?trxAppId=" + trxAppId;
        window.location = getUrl;
    });

    $("#transactionApprtovalListTable").flexigrid({
        url: 'getTrxApprovalJASONList.html',
        dataType: 'json',
        colModel: [
            {
                display: 'Name',
                name: 'fileName',
                width: 453,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Download',
                name: 'fileIconLocation',
                width: 150,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Uploaded',
                name: 'author',
                width: 200,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Date',
                name: 'entryDate',
                width: 100,
                sortable: false,
                align: 'center'
            },
            {
                display: '',
                name: 'trxApproveDeleteHtmlButton',
                width: 161,
                sortable: false,
                align: 'center'
            },
            {
                display: 'Backend data',
                name: 'id',
                width: 10,
                hide: true,
                align: 'center'
            }
        ],
        searchitems: [
            {
                display: 'Name',
                name: 'fileName'
            },
            {
                display: 'Uploaded',
                name: 'author',
                isdefault: true
            }
        ],
        sortname: "file_name",
        sortorder: "asc",
        usepager: true,
        title: '',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 260
    });


    $(document).on('click', '#realtimeworkflowtableDiv .realTimeProjectWorkFlix tr', function (evt) {

        var rtProjectId = $(this).children(":last").text();
        assignedPerson = $(this).find("td[abbr='assignTo']").children(":first").text();
        assignedPerson = assignedPerson.trim();
        var assignObj =  $(this).find("td[abbr='assignTo']").children(":first");
        var statusObj =  $(this).find("td[abbr='status']").children(":first");
        var unAssignedBtn = $(evt.target).val();
        var noOfRevew = parseInt($(this).find("td[abbr='review']").text());
        var assignmentSize = parseInt($(this).find("td[abbr='noOfTransaction']").text());

        if(unAssignedBtn == "un-assign") {
            doUnassignment( assignObj,statusObj, rtProjectId) ;
        } else {
            if(assignedPerson != "" || user.role == ia_managerRole || user.role == legalRole) {
                getAssignmentSummary(noOfRevew, assignmentSize, rtProjectId);
            }else {
                assignProject(rtProjectId);
            }
        }
    });

    $(document).on('click', '#myTransactionTable tr', function (evt) {
        var rtProjectId = $(this).children(":last").text();
        window.location = "../realtime/myRealTimeSummaryView.html?rtProjectId=" + rtProjectId;
    });


    $(document).on('click', '.radioDecissionArea', function(e) {
        isPartialData = true;
        if($('.radioDecissionArea').hasClass(requiredFieldFocus)) {
            $(".radioDecissionArea").removeClass(requiredFieldFocus);
        }

    });
    $(document).on('click', '#commentRegion', function(e) {
        isPartialData = true;

    });
    $(document).on('click', '#decisionComment', function(e) {
        isPartialData = true;

    });

    $(document).on('change', '.checkOpt', function(e) {
        isPartialData = true;
        if($(this).text() != "") {
            if($(this).hasClass(requiredFieldFocus)) {
                $(this).removeClass(requiredFieldFocus);
            }
        }

    });

    $("#assignedBtn").click(function() {
        var isDecisionSet = isDecisionSelected();
        setDecisionAndCommentMessage(isDecisionSet);
        var isAllContAns = isAllControlsAnswered();
        setControlAndCommentMessage(isAllContAns, contComment);
        var isFoundTx = allTransactionIsCoded();
        printInConsole("inCodedTxLiObj: " + inCodedTxLiObj.length);
        if(isPartialData) {
            $.prompt("Please save transaction details.");
        } else if(inCodedTxLiObj.length == 0) {
            $.prompt("Assignment completed Successfully.");
            isPartialData = false;
        } else {
            $.prompt("You have not completed the assignment.");
        }
        requiredMessage = "";
    });

    $( "#subHeaderSaveButton" ).click(function() {
        var myRealTimeSummaryView = $("#myRealTimeSummaryView").val();
        console.log("myRealTimeSummaryView:"+myRealTimeSummaryView)
        if(myRealTimeSummaryView ==1){
            $("<input type='hidden' name ='myRealTimeSummaryView' value='1'/>").insertAfter("#testAjaxForm #maxFileUploadSize")
            document.testAjaxForm.method = "post";
            document.testAjaxForm.submit();
        }else{


        if($("#ipboard_body").find("#projectStatus").val() == status) {
            $.prompt("This assignment has been completed.");
        }else {
            $("#employeeJason").val(JSON.stringify(getAssignedEmployJason()));
            document.testAjaxForm.method = "post";
            document.testAjaxForm.submit();
        }}
    });


    /*    $( "#draggable" ).draggable();
     $( "#droppable" ).droppable({
     drop: function( event, ui ) {
     alert('dropped')
     }
     });*/

    $(".assignNo").click(function(){

        var url = '../realtime/groupingColumn.html'
        //window.location = url;
        $.ajax({
            type: 'get',
            url: url,
            async: false,
            success: function (data) {
                data = '<div class="bDiv">'+data+'<div>';
                //alert(data)
                //alert($(data).find("#groupTable").html());
                $(".hDiv").hide();
                $(".bDiv").hide();
                $(data).insertAfter(".bDiv");
            }

        });
    });



    $(".assignSize").click(function(){


    });


});



function isAllControlsAnswered() {
    var isAllSelect = true;
    isControlNoOrSC = false;
    $('.checkOpt option:selected').each(function()            // check all controls are answered;
    {
        var option = this;
        var value = $(option).val();

        if(value.substring(value.length - 1, value.length) == 0){                            //  not answered
            isAllSelect = false;
            $(option).parent().parent().addClass(requiredFieldFocus);
        }else {

            if($(this).text() == "No" || $(this).text() == "See Comment") {         //  2 for No 4 for selet comment
                isControlNoOrSC = true;
            } else {
                //
            }

        }
    });
    if(isAllSelect) {
        return true;
    } else {
        return false;
    }
}

function isDecisionSelected() {
    if ($(".radioDecissionArea").is(":checked")) {
        return true;
    } else {
        addRequiredFocusClass("class", "radioDecissionArea");
        return false;
    }
}

function getPrevDecAndControl() {
    prevContSel = $('.checkOpt option:selected');
    prevConCom = $("#commentRegion").val();
    prevDes = $(".radioDecissionArea:checked").val();
    prevDesCom = $("#decisionComment").val();
}
function getSaveDecAndControl() {
    saveContSel = $('.checkOpt option:selected');
    saveConCom = $("#commentRegion").val();
    saveDes = $(".radioDecissionArea:checked").val();
    saveDesCom = $("#decisionComment").val();
}
function isUpdated() {

    if(prevConCom != saveConCom) {
        return true;
    } else if (prevDes != saveDes) {
        return true;
    } else if (prevDesCom != saveDesCom) {
        return true;
    }
    else {
        for(var i = 0; i < prevContSel.length; i++ ) {
            if($(prevContSel[i]).text() != $(saveContSel[i]).text()) {
                return true;
            }
        }

    }
    return false ;

}

function addRequiredFocusClass(property, name) {
    if(property == "id") {
        $('#'+name).addClass(requiredFieldFocus);
    } else if(property == "class") {
        $('.'+name).addClass(requiredFieldFocus);
    }
}

function allTransactionIsCoded() {
    var rTPId =  $("#realTimeProjectId").val();
    inCodedTxLiObj = getIncodedTransactionList(rTPId);
    isPartialData = false ;
    return  highlightTransactions();
}

function getIncodedTransactionList(realTimeTxId) {
    var inCoTxLiObj;
    var getUrl = "./doCompleteAssignment.html?rtProjectId=" + realTimeTxId;
    $.ajax({
        type:'GET',
        url:getUrl,
        async:false,
        success:function (data) {
            inCoTxLiObj = data;
        }
    });

    return inCoTxLiObj;
}

function highlightTransactions() {
    var isFoundTx = true;
    if(inCodedTxLiObj != null) {
        $(inCodedTxLiObj).each(function () {
            var rtTxId = $(this).attr("realTimeTxId");
            $("#summeryTableForNewRiskAssesment tr").each(function() {
                if (parseInt($('td:last', this).text()) == rtTxId) {
                    isFoundTx = false;
                    $(this).addClass(requiredFieldFocus);

                }
            })
        });
    } else {
        // no incoded transaction found.
    }
    return isFoundTx;
}

function doAssignmentUnassign(assignObj,statusObj, rtProjectId) {
    var getUnAssignmentUrl =  "./doUnassignmentProject.html?rtProjectId=" + rtProjectId;
    $.ajax({
        type:'GET',
        url:getUnAssignmentUrl,
        async:false,
        success:function (data) {
            $(assignObj).html($(data).attr("assignTo"));
            $(statusObj).html($(data).attr("status"));
        }

    });
}

function doUnassignment( assignObj,statusObj, rtProjectId) {
    if (assignedPerson == loggedUser || (assignedPerson != "" && user.role== adminRole)) {
        $.prompt('Click "Y" to confirm unassignment.', {
            title: "",
            buttons: { "Yes": true, "No": false },
            submit: function(e,v,m,f){
                // use e.preventDefault() to prevent closing when needed or return false.
                // e.preventDefault();
                if (v) {
                    doAssignmentUnassign(assignObj,statusObj,rtProjectId);
                }
                else {
                    window.location = "./getAssignmentReview.html?rtProjectId=" + rtProjectId;
                }
            }
        });

    } else if (assignedPerson != "") {
        //alert("The project has assigned to " + assignedPerson +"." );
        $.prompt("The project has assigned to " + assignedPerson +"." );
    } else if(assignedPerson != loggedUser) {
        $.prompt("The project has not been assigned to you.");
    }
}

function getAssignmentSummary(noOfRevew, assignmentSize, rtProjectId) {
    if (assignedPerson == loggedUser || user.role == adminRole|| user.role == ia_managerRole|| user.role == legalRole) {
        printInConsole("No of Reviewed: " + noOfRevew + "assignment size: " + assignmentSize)
        if (noOfRevew < assignmentSize) {
            window.location = "../realtime/getAssignmentReview.html?rtProjectId=" + rtProjectId;
        } else {
            $.prompt("This assignment is reviewed in maximum level.");
        }
    } else {
        $.prompt("This project has already been assigned to " + assignedPerson);
    }
}

function setPopoverAction(){
    $("#summeryTableForNewRiskAssesment tbody tr").mouseover(function(){
     trxId = parseInt(($(this).attr('id')).replace('row',''));
    });
    $("#summeryTableForNewRiskAssesment tbody tr").popover({
        trigger : 'hover',
        placement : 'right', //placement of the popover. also can use top, bottom, left or right
//        title : '<div style="text-align:center; color:red; font-size:11px;">Preview rgre ertgertg erert </div>', //this is the top title bar of the popover. add some basic css
        html: 'true', //needed to show html of course
        content :function () {
            var object=0;
            var ruleTitle = '';
            var completeDiv='<table class="fontColor"><tbody>'
            for(var i=0; i< summeryTableForNewRiskAssesmentJSonData.rows.length; i++){
                object = parseInt(summeryTableForNewRiskAssesmentJSonData.rows[i].id);
                if( object == trxId){
                    var classCount = colorClassArr.length-1;
                    ruleTitle =(summeryTableForNewRiskAssesmentJSonData.rows[i].cell.ruleExplanation)
                    var arr = ruleTitle.split('#');
                    for(var j=0; j<arr.length; j++){
                        if(j==0)
                        completeDiv = completeDiv+'<tr><td valign="top"><i data-placement="top" class="icon-tasks '+colorClassArr[classCount--]+'" ></i></td><td>'+arr[j]+'<div class="row divider lightRed"></div></td></tr>';
                        else
                        completeDiv = completeDiv+'<tr style="border-top: 2px solid #FA7466;"><td valign="top"><i data-placement="top"class="icon-tasks '+colorClassArr[classCount--]+'" ></i></td><td>'+arr[j]+'<div class="row divider lightRed"></div></td></tr>';
                    if(classCount==0)
                        classCount = colorClassArr.length-1;
                    }
                    completeDiv = completeDiv+'</tbody></table>';
                }

            }
            return completeDiv;
        },
        container:'body',
        delay: { show: 300, hide: 300 }
    });
}

function getAssignedEmployJason() {
    employeeJason = [];
    $(".employeeList").each(function(){
        option={};
        option['id'] = $(this).prop("id");
        option['name'] = $(this).text().trim;
        employeeJason.push(option);
    });

    return employeeJason;
}

function highlightedRowForReviewed(){
$("#summeryTableForNewRiskAssesment tbody tr").each(function(){
    if($(this).find("[abbr=reviewed]").find('div').text() == 'true'){
        $(this).removeClass("trSelected");
//        $(this).removeClass("colorTrTd");
        $(this).removeClass('erow');
        $(this).addClass('reviewedColor');
    }
});
}
function removeEmployeeEvt(){
    $( ".employeeList" ).find('span').hide();
    $(document).on('mouseleave',".employeeList",function(){
        var employee = $(this);
        $(employee).find('span').hide();
    });
    $(document).on('mouseover',".employeeList",function(){
        var employee = $(this);
        if(user.role == adminRole || user.role == ia_analystRole) {
        $(employee).find('span').show();
        }else{
        $(employee).find('span').hide();
        }
    });
    $(document).on('click',".removeEmpBtn",function(e){
        $(e.target).parent('div').remove() ;
    });
}

