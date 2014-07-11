var ia_analystRole = 'IA_ANALYST';
var ia_managerRole = 'IA_MANAGER';
var employeeRole = 'EMPLOYEE';
var complianceRole = 'COMPLIANCE';
var adminRole = 'ROLE_ADMIN';
var legalRole = 'LEGAL';
var moreInformationRequired='More information required';
var globalExportParam = '';
var date = new Date();
var today = (date.getMonth() > 9 ? (date.getMonth() + 1): '0'+(date.getMonth() + 1))+'/'+ (date.getDate() > 9 ? date.getDate() : '0'+date.getDate() )+'/'+date.getFullYear();
$(document).ready(function () {

    $('#login').click(function(){
        $('#loginForm').submit();
        return false;
    });

    $("#logoutDivId").click(function () {
        window.location = $("#contextPath").val() + "/logout.html";
    });

    $("#changePasswordDivId").click(function () {
        window.location = "../admin/changePassword.html";
    });

    $("#changeProfileDivId").click(function () {
        window.location = "../admin/addUser.html?id=" + $("#loggUserId").val();
    });

    $("#userMangDivId").click(function () {
        window.location = "../admin/userList.html";
    });

    $("#weightiningDivId").click(function () {
        window.location = "../admin/proactiveRatio.html";
    });

    $("#controlDivId").click(function () {
        window.location = "../admin/controls.html";
    });


  /*  $("#policiesDivId").click(function(){
        window.location ="./policyList.html";
    });*/

/*    $("#policiesDivId").click(function(){
        window.location ="./policyList.html";
    });*/

    $("#reactiveProjectMangDivId").click(function(){
        window.location ="../reactive/reactiveProjectList.html";
    });

    $("#realTimeMonIntervalSetUpDivId").click(function(){
        window.location ="../admin/realTimeMonitoringIntervalSetup.html";
    });

    $("#holidayId").click(function(){
        var regionId = $("#discriptionId").val();
        window.location ="../admin/holidays.html?regionId=" + 162;
    });

    $("#importTrxDivId").click(function(){
        window.location ="../admin/importTransactionsFromCSV.html";
    });

    $("#ruleCreationDivId").click(function(){
        window.location ="../admin/ruleCreation.html";
    });

    $( "#datepickerField" ).datepicker(); // for reactive project
    $( "#holidayDateFieldId" ).datepicker(); // for reactive project
    $('#items').multiselect();
//      $('#transactionType').multiselect();
//      $('#userRole').multiselect();


//    added by amjad start

//    control list grid start
    $("#controlListTable").flexigrid({
        url : 'getControlJASON.html',
        dataType : 'json',
        colModel : [ {
            display : 'Control',
            name : 'name',
            width : 650,
            sortable : true,
            align : 'left'
        }, {
            display : 'Transaction Type',
            name : 'transactionType',
            width : 220,
            sortable : true,
            align : 'left'
        }, {
            display : 'Active',
            name : 'activeCheckBoxHtml',
            width : 50,
            sortable : true,
            align : 'left'
        }, {
            display : '',
            name : 'editButtonHtml',
            width : 80,
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'deleteButtonHtml',
            width : 80,
            sortable : false,
            align : 'center'
        }, {
            display : 'Backend data',
            name : 'id',
            width : 10,
            hide : true,
            align : 'center'
        }],
         buttons : [ {
         name : 'Add',
         bclass : 'addControl'

         },
         {
         separator : true
         }
         ],
        searchitems : [ {
            display : 'Control',
            name : 'name'
        }, {
            display : 'Transaction Type',
            name : 'transaction_type',
            isdefault : true
        } ],
        sortname : "transaction_type",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300
    });

    $(".addControl").before('<a href="#addNewControl" role="button" class="add addControlIcon" data-toggle="modal">Add</a>');
    $(".addControl").remove();

    $('#sheetTypeSelId').multiselect();

    $(document).on('click', '.addControlIcon', function() {
        $("#controlName").val("");
        $("#transactionType").val("");
        $("#activeId").prop('checked', false);
    });

    $(document).on('click', '#saveControl', function() {

        var getUrl = "addControl.html?controlId=0";
        $.ajax({
            type:'post',
            url:getUrl,
            data: $("#projectForm").serialize(),
            async:false,
            success:function (data) {
                $('#addNewControl').modal('toggle');
                $("#controlListTable").flexReload();

            }

        });
     });

    $(document).on('click', '.editControlButtonCss', function() {

        var controlId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "getControl.html?controlId=" + controlId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#controlName").html($(data).attr("name"));
                $("#transactionType").html($(data).attr("transaction_type"));
                if($(data).attr("active")) {
                    $("#activeId").prop('checked', true);
                } else {
                    $("#activeId").prop('checked', false);
                }
            }


        });

        // document.addConForm.submit();
    });

    $(document).on('click', '#deleteButtonHtml', function() {
        var controlId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "controlDelete.html?controlId=" + controlId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
//                  alert($(data).attr("name"));
                $("#controlListTable").flexReload();
            }


        });
    });
//    control list grid end

    $("#trxMoniDash").click(function(){
        window.location = "../realtime/RealtimeMonitoringWorkflow.html";
    });

    $("#uploadDash").click(function(){
        window.location = "policyList.html";
    });

    $('.trxMoniDashTolTip').tooltip({
        title: 'Un-assigned'
    });

   /* $("#trxSearchDash").click(function(){
        window.location = "transactionSearch.html";
    });*/

    $("#headerTitle").click(function(){
        window.location = "landingPage.html";
    });

    $("#policyDash").click(function(){
        window.location = "../policy/policyList.html";
    });

    $("#trainingDash").click(function(){
        window.location = "../training/trainingList.html";
    });

});


function setIconActionInsuppDoc(){
    $('.viewAttachment').tooltip({
        title: 'View Attachment'
    });

    $('.deleteAttachment').tooltip({
        title: 'Delete Attachment'
    });

    $('.downloadAttachment').tooltip({
        title: 'Download Attachment'
    });
}
function setIconActionInPolicyList(){
    $('.viewPolicyAttachment').tooltip({
        title: 'View Attachment'
    });
    $('.downloadPolicyAttachment').tooltip({
        title: 'Download Attachment'
    });
}
function makeXlsWithoutFlexigrid(tableID){
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; })}

        $('#content').css('opacity','0.6');
        var ctx = { worksheet: name || 'Worksheet', table:$(tableID).html()}
        document.getElementById("dlink").href = uri + base64(format(template, ctx));
        document.getElementById("dlink").download = tableID;
        document.getElementById("dlink").click();
        $('#content').css('opacity','1.0');

}
function makeCsvWithoutFlexigrid(tableID){
    $('#content').css('opacity','0.6');
    var csv = $(tableID).table2CSV({delivery:'value'});
    document.getElementById("dlink").href = 'data:text/csv;charset=UTF-8,'+ encodeURIComponent(csv);
    document.getElementById("dlink").download = tableID;
    document.getElementById("dlink").click();
    $('#content').css('opacity','1.0');

}

function makePdfWithoutFlexigrid(tableID){
    $('#content').css('opacity','0.6');
    newWin = window.open("");
    newWin.document.write($(tableID).innerHTML);
    newWin.print();
    newWin.close();
    window.location = window.location;
    $('#content').css('opacity','1.0');
}

function showMessageByForce(pageId,messgType,messg){
    var div ='<div class="alert '+messgType+' successfulOrErrorMessages" id="allMessage">'
        +'<button data-dismiss="alert" class="close" type="button">&times;</button>'
        +'<strong>'+messg+'Successfully.</strong></div>';
    if(!$(".successfulOrErrorMessages").length ){
    }else{
        $(".successfulOrErrorMessages").remove();
    }
    $(pageId).before(div);
}

function printInConsole(msg){
    console.log(msg);
}