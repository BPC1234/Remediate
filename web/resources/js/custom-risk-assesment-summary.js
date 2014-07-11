var maxHeightOfThreePanel = '610px';
var subtractValueForPadding=0;
var maxFileSize =0;
var selectionSecondPaneA;
var selectionSecondPaneLI;
var selectionThirdPaneA;
var selectionThirdPaneLI;
var minWidthForIfCondition = 40;
var initialWidhtOfPaneOne ="33%";
var initialWidhtOfPaneTwo ="33%";
var initialWidhtOfPaneThree ="33%";
var minWidhtOfPaneOne ="29px";
var minWidhtOfAnyPane =29;
var minWidhtOfPaneTwo ="40%";
var minWidhtOfPaneThree ="35%";
var selectionFirstPaneA;
var selectionFirstPaneLI;
var status="Completed";
var animationDelayTime=300;
var empIdForAutoComplete;
var empNameForAutoComplete;
employeeJason = [];

$(document).ready(function () {


    $( "#subHeaderPrintButton" ).click(function() {
        var url = "../riskasst/printTransactionDetail.html?transactionId="+$("#transactionId").val();
        if($("#proactiveProjectId").val() > 0)
            url += "&pProjectId=" + $("#proactiveProjectId").val();
        else
            url += "&rProjectId=" + $("#reactiveProjectId").val();
        window.location = url;
    });
    $( "#subHeaderEmailButton" ).click(function() {
        var url = "../riskasst/emailTransactionDetail.html?transactionId="+$("#transactionId").val();
        if($("#proactiveProjectId").val() > 0)
            url += "&pProjectId=" + $("#proactiveProjectId").val();
        else
            url += "&rProjectId=" + $("#reactiveProjectId").val();
        window.location = url;
    });

    $( document).on("click", "#summeryTable1 tbody tr" ,function() {
        $("#summeryTable1 tbody tr").removeClass("colorTrTd");
        selectedRow('#summeryTable1',this);
        $("#serialNoForTableRowSelection").val("tr:first");
        var newUrl = '../realtime/trxListInDetails.html?proactiveProjectId='+$("#proactiveProjectId").val()
            + '&reactiveProjectId='+$("#reactiveProjectId").val()
            + '&realTimeProjectId='+$("#realTimeProjectId").val()
            + '&tableName=summeryTableForNewRiskAssesment'
            + "&myRealTimeSummaryView="+$("#myRealTimeSummaryView").val()
            + "&ctrlId="+$("#controlId").val()
            + '&tCount='+$(this).find("[abbr=trxCount]").find('div').text()
            + '&ruleId='+$(this).find("[abbr=ruleIdStr]").find('div').text();

        $("#summeryTableForNewRiskAssesment").flexOptions({url:newUrl,newp: 1}).flexReload();
        $("#paneOneTotalDiv div ul #transactionListLi a").click();
    });
    var newExtension='';
    //callAjaxForNewRiskAssesmentSummaryView(newExtension);
    $('#tabOne').width("33%");
    $('#tabThree').width("33%");
    $('#tabTwo').width("33%");

    selectionFirstPaneLI = 'summaryOfThirdPartyTransactionsLi';
    $(".tabOneIconDiv i").click(function(e){
        $("#paneOneIconCross").show();
        $("#paneOneTotalDiv").show();
        $(".tabOneIconDiv").hide(); //to hide img
        $("#tabOne .containerHeadline").height('20px');
        $("#tabOne .floatingBox").height(maxHeightOfThreePanel);
        var widthOfTwo = $('#tabTwo').innerWidth();
        var widthOfThree = $('#tabThree').innerWidth();
        var widthOfOne = $('#tabOne').innerWidth();
        selectionFirstPaneLI = $(this).attr("class").split(' ')[2];
        openThisTabAndCloseOthersForFirstPane($(this).attr("class").split(' ')[2]);
        if(widthOfThree <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
            resizeThirdPane();
        if(widthOfTwo <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
            resizeSecondPane();
        if(widthOfOne <= minWidthForIfCondition){
            increasePaneFromClose(tabOne,tabTwo,tabThree,"tabOne",'open');
        }
        tabOne = true;

        e.preventDefault();
    });
    $( "#paneOneIconCross").click(function(){
        $("#paneOneIconCross").hide();
        $(".tabOneIconDiv").show(); //to show img
        $("#tabOne .floatingBox").height('590px');
        increasePaneFromClose(tabOne,tabTwo,tabThree,"tabOne",'close');
        $("#paneOneTotalDiv").hide();
    });
    /*end of First pane*/

});

function getTransactionDetails(id) {
 var extension = "txId="+$(id).find("[abbr=transactionId]").find('div').text()
        +"&proactiveProjectId="+$("#proactiveProjectId").val()
        +"&reactiveProjectId="+$("#reactiveProjectId").val()
        +"&projectType="+$(id).find("[abbr=projectType]").find('div').text()
        +"&projectId="+$(id).find("[abbr=projectId]").find('div').text()
        +"&ctrlId="+$("#controlId").val()
        +"&transactionId="+$(id).find("[abbr=trxId]").find('div').text()
        +"&serial="+$(id).attr("id")
        +"&rtProjectId="+$("#realTimeProjectId").val()
        +"&pageNo="+$("#summeryTableForNewRiskAssesment").parent().parent('div').children('div.pDiv').children("div.pDiv2").children("div.pGroup").children("span.pcontrol").children('input').val();
    $("#content").css('opacity','0.6');
    $('#loading').css('left',(($('#content').innerWidth())/2) );
    $("#loading").show();
    callAjaxForNewRiskAssesmentSummaryView(extension);

    if($(id).hasClass("requiredFieldFocusClass")) {
        if(isEmptyTextOfId("commentRegion")) {
            addRequiredFocusClass("id", "commentRegion");
        }
        isDecisionSelected();
        isAllControlsAnswered();
        if(isEmptyTextOfId("decisionComment")) {
            addRequiredFocusClass("id", "decisionComment");
        }
    }
}

function selectedRow(tableIdOrClass,thisObject) {
   //$(tableIdOrClass+' tbody tr').removeClass("trSelected colorTrTd");
    var selectedRow='';
    $(tableIdOrClass+' tbody tr').each(function() {
        if($(this).hasClass("colorTrTd")){
            selectedRow = $(this).attr('id');
        }
    });
    $('#'+selectedRow).removeClass("trSelected");
    $('#'+selectedRow).removeClass("colorTrTd");
    if(!$('#'+selectedRow).next('tr').hasClass('erow')){
        if(!$('#'+selectedRow).hasClass('reviewedColor')){
            $('#'+selectedRow).addClass('erow');
        }
    }
    $(tableIdOrClass+' tbody tr td').removeClass("sorted");
    $(thisObject).removeClass('erow');
    $(thisObject).removeClass('trSelected');
    $(thisObject).addClass('colorTrTd');

}

function callAjaxForNewRiskAssesmentSummaryView(extension) {
    $("#content").css('opacity','0.6');
    $("#loading").css({top: ($('#content').innerHeight())/2, left:($('#content').innerWidth())/2, position:'absolute'});
    $("#loading").show();
    var postUrl = "../realtime/ajaxCallForNewRiskAssesment.html?"+extension;
    $(document).ready(function() {
        $.ajax({
            url : postUrl ,
            type : 'GET',
            async: false,
            data : {},
            success : function(data) {

                var source = $('<div>' + data + '</div>');
                $('#tabTwo').html(source.find('#tabTwo').html());
                $('#tabThree').html(source.find('#tabs1').html());
                $("#loading").hide();
                $("#content").css('opacity','1.0');
                $("#tabs1").css('opacity','1.0');

                setAutoComplete();
                removeEmployeeEvt();

                customTabAction('#tabTwo');
                customTabAction('#tabThree');
                setCustomFileAction("#fileDataForSupportingDocument");
                setUploadButton("#policyInput", "#policySelection", "#transactionPolicy")
                setIconActionInsuppDoc();
                setIconActionInPolicyList();
                $('#uniqueSelect').multiselect();
                setActionForDeleteViewAttachment();
                setActionForDeleteViewAttachmentInPolicy();
                $("#mainDivOfthree").show();

                $("#paneTwoTotalDiv div.floatingBoxMenu ul li:first").addClass("active");
                $("#paneTwoTotalDiv div.floatingBoxMenu ul li:first a").click();

                $('#paneOneTotalDiv div.floatingBoxMenu ul li a').click(function() {
                selectionFirstPaneLI =  $(this).parent('li').attr("id");
                });
                $('#paneTwoTotalDiv div.floatingBoxMenu ul li a').click(function() {
                selectionSecondPaneLI =  $(this).parent('li').attr("id");
                });
                $('#paneThreeTotalDiv div.floatingBoxMenu ul li a').click(function() {
                selectionThirdPaneLI =  $(this).parent('li').attr("id");
                });

                $(".radioDecissionArea").click(function(){
                    if($(this).val() == moreInformationRequired) {
                       $("#internalAudit").show();
                    } else {
                        $("#internalAudit").hide();
                    }
                });
                if($('input:radio[class="radioDecissionArea"]:checked').val() == moreInformationRequired) {
                    $("#internalAudit").show();
                }

//                printInConsole('decision: '+  $(".radioDecissionArea :checked ").val());
                printInConsole('decision: '+  $('input:radio[class="radioDecissionArea"]:checked').val());

              /*  for(var i= 0 ; i < $('#totalControl').val() ; i++) {
                    $('#contId'+i).multiselect();
                }*/

               /*Start Second Pane colapsible*/
                selectionSecondPaneLI = "AdditionalInforormationLi";
                $(".tabTwoIconDiv i").click(function(e){
                    $("#paneTwoIconCross").show();
                    $("#paneTwoTotalDiv").show();
                    $(".tabTwoIconDiv").hide(); //to hide img
                    $("#tabTwo .containerHeadline").height('20px');
                    $("#tabTwo .floatingBox").height(maxHeightOfThreePanel);
                    selectionSecondPaneLI = $(this).attr("class").split(' ')[2];
                    openThisTabAndCloseOthersForSecondPane($(this).attr("class").split(' ')[2]);
                    var widthOfTwo = $('#tabTwo').innerWidth();
                    var widthOfThree = $('#tabThree').innerWidth();
                    var widthOfOne = $('#tabOne').innerWidth();
                    if(widthOfThree <= minWidthForIfCondition & widthOfTwo <= minWidthForIfCondition)
                        resizeThirdPane();
                    if(widthOfTwo <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
                        resizeFirstPane();
                    if(widthOfTwo <= minWidthForIfCondition){
                        increasePaneFromClose(tabOne,tabTwo,tabThree,"tabTwo",'open');
                    }
                    tabTwo = true;
                    e.preventDefault();
                });
                $( "#paneTwoIconCross").click(function(){
                    $("#paneTwoIconCross").hide();
                    $(".tabTwoIconDiv").show(); //to show img
                    $("#tabTwo .containerHeadline").height('60px');
                    $("#tabTwo .floatingBox").height('570px');
                    increasePaneFromClose(tabOne,tabTwo,tabThree,"tabTwo",'close');
                    $("#paneTwoTotalDiv").hide();
                });
                /*end of Second pane colapsible*/

                /*Start Third Pane colapsible*/
                selectionThirdPaneLI = "analysisLi"
                $(".tabThreeIconDiv i").click(function(e){
                    $("#paneThreeIconCross").show();
                    $("#paneThreeTotalDiv").show();
                    $(".tabThreeIconDiv").hide(); //to hide img
                    $("#tabThree .containerHeadline").height('20px');
                    selectionThirdPaneLI = $(this).attr("class").split(' ')[2];
                        var widthOfTwo = $('#tabTwo').innerWidth();
                        var widthOfThree = $('#tabThree').innerWidth();
                        var widthOfOne = $('#tabOne').innerWidth();

                        if(widthOfThree <= minWidthForIfCondition & widthOfTwo <= minWidthForIfCondition)
                            resizeSecondPane();
                        if(widthOfThree <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
                            resizeFirstPane();
                        if(widthOfThree <= minWidthForIfCondition){
                            increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'open');
                        }
                        tabThree = true;
                    e.preventDefault();
                });
                 $( "#paneThreeIconCross").click(function(){
                    $("#paneThreeIconCross").hide();
                    $(".tabThreeIconDiv").show(); //to show img
                    $("#tabThree .floatingBox").addClass('commonDivHeight');
                    $("#tabThree .containerHeadline").height('60px');
                    increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'close');
                    $("#paneThreeTotalDiv").hide();
                });

                /*end of third pane colapsible*/


                /* Resize of pane One*/
//                var bodyWidth = $('#mainHeadline').width();
                var bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
                var tabsHeight = $('#tabOne').height();
                subtractValueForPadding = (bodyWidth*0.020);
                var subtractValue = subtractValueForPadding + (minWidhtOfAnyPane*2);

                 $( "#tabOne" ).resizable({
                 maxHeight:tabsHeight,
                 minHeight:tabsHeight,
                 minWidth: minWidhtOfAnyPane,
                 maxWidth: (bodyWidth-subtractValue)
                 });
                 $("#tabOne").bind("resize", function (event, ui) {
//                 var bodyWidth = $('#mainHeadline').width();
                 var bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
                 var widthOfOne = $('#tabOne').innerWidth();
                 if(widthOfOne <= 60){
                 openThisTabAndCloseOthersForFirstPane('aa');
                     $("#paneOneIconCross").hide();
                     $(".tabOneIconDiv").show(); //to show img
                     $("#tabOne .floatingBox").addClass('commonDivHeight');
                     $("#tabOne .containerHeadline").height('40px');
                     $("#tabOne .floatingBox").height('590px');
                     $("#paneOneTotalDiv").hide();
                 }else if(widthOfOne > 61){
                 openThisTabAndCloseOthersForFirstPane(selectionFirstPaneLI);
                     $("#paneOneIconCross").show();
                     $("#paneOneTotalDiv").show();
                     $(".tabOneIconDiv").hide(); //to hide img
                     $("#tabOne .containerHeadline").height('20px');
                     $("#tabOne .floatingBox").height(maxHeightOfThreePanel);
                 }
                 if(widthOfOne > (bodyWidth-100)){   //resize pane Two and pane three
                 $("#tabTwo").width(minWidhtOfAnyPane);
                     $("#paneTwoIconCross").hide();
                     $(".tabTwoIconDiv").show(); //to show img
                     $("#tabTwo .containerHeadline").height('60px');
                     $("#paneTwoTotalDiv").hide();
                     openThisTabAndCloseOthersForSecondPane('');

                 $("#tabThree").width(minWidhtOfAnyPane);
                     $("#paneThreeIconCross").hide();
                     $(".tabThreeIconDiv").show(); //to show img
                     $("#tabThree .floatingBox").addClass('commonDivHeight');
                     $("#tabThree .containerHeadline").height('80px');
                     $("#tabThree .floatingBox").height('550px');
                     //increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'close');
                     $("#paneThreeTotalDiv").hide();
                     openThisTabAndCloseOthers('');
                 }
                 if(widthOfOne > minWidhtOfAnyPane && (widthOfOne < (bodyWidth-100))){
//                 var tabsWidth = $('#mainHeadline').width();
                   var tabsWidth  = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
                   var tabOneWidth = $('#tabOne').width();
                 var tabTwoWidth = $('#tabTwo').width();
                 resizeSecondPane();
                 openThisTabAndCloseOthers(selectionThirdPaneLI);
                     $("#paneThreeIconCross").show();
                     $("#paneThreeTotalDiv").show();
                     $(".tabThreeIconDiv").hide(); //to hide img

                     $("#tabThree .containerHeadline").height('20px');
                     $("#tabThree .floatingBox").height(maxHeightOfThreePanel);
                     $('#tabThree').width(((bodyWidth-tabOneWidth)/2));
                 }

                 });

                /* Resize of pane Two*/

                var maxWidthOfSecondPane = 0;
//                var bodyWidth = $('#mainHeadline').width();
                var bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();

                var widthOfFirstPane = $('#tabOne').width();
                var tabsTwoHeight = $('#tabTwo').height();
                maxWidthOfSecondPane = bodyWidth-(widthOfFirstPane+minWidhtOfAnyPane+ subtractValueForPadding);
                $("#tabTwo").resizable();
                $("#tabTwo").resizable('destroy');
                $("#tabTwo").resizable({
                    maxHeight:tabsTwoHeight,
                    minHeight:tabsTwoHeight,
                    minWidth: minWidhtOfAnyPane,
                    maxWidth: maxWidthOfSecondPane
                });

                $("#tabTwo").bind("resize", function (event, ui) {
//                    bodyWidth = $('#mainHeadline').width();
                    bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
                    var widthOfTwo = $('#tabTwo').innerWidth();
                    if(widthOfTwo <= 60){
                        $("#tabTwo").width(minWidhtOfAnyPane);
                        $("#paneTwoIconCross").hide();
                        $(".tabTwoIconDiv").show(); //to show img
                        $("#tabTwo .containerHeadline").height('60px');
                        $("#tabTwo .floatingBox").height('570px');
                        $("#paneTwoTotalDiv").hide();
                        resizeThirdPane();
                    }else if(widthOfTwo > 61){
                        openThisTabAndCloseOthersForSecondPane(selectionSecondPaneLI);
                        $("#paneTwoIconCross").show();
                        $("#paneTwoTotalDiv").show();
                        $(".tabTwoIconDiv").hide(); //to hide img
                        $("#tabTwo .containerHeadline").height('20px');
                        $("#tabTwo .floatingBox").height(maxHeightOfThreePanel);
                        resizeThirdPane();
                    }
                    var tabsThreeWidth = $('#tabThree').width();
                    if(tabsThreeWidth <60){
                        $("#tabThree").width(minWidhtOfAnyPane);
                        $("#paneThreeIconCross").hide();
                        $(".tabThreeIconDiv").show(); //to show img
                        $("#tabThree .containerHeadline").height('80px');
                        $("#tabThree .floatingBox").height('550px');
                        $("#paneThreeTotalDiv").hide();
                        openThisTabAndCloseOthers('');

                    }

                });
            }
        })

    });






}
function isEmptyTextOfId(id) {
    if ($("#" + id).text() != "") {
        if ($(this).hasClass(requiredFieldFocus)) {
            $(this).removeClass(requiredFieldFocus);
        }
        return false;
    } else return true;
}

function openThisTabAndCloseOthers(liId){
    $("#analysisLi").removeClass('active');
    $("#controlsLi").removeClass('active');
    $("#auditTrailLi").removeClass('active');
    $("#procedureLi").removeClass('active');
//    $("#internalAuditLi").removeClass('active');
    if(liId != ''){
    $("#"+ liId).addClass('active'); //open selected tab
    $("#"+ liId +' a').trigger('click');
    }
}
function openThisTabAndCloseOthersForSecondPane(liId){
    $("#AdditionalInforormationLi").removeClass('active');
    $("#EmployeeInformationLi").removeClass('active');
    $("#customerMasterFileLi").removeClass('active');
    if(liId != ''){
        $("#"+ liId).addClass('active'); //open selected tab
        $("#"+ liId +' a').trigger('click');
    }

}

function validation(){

    var file = $("#file")[0].files[0];
    var fileName = file.name;
    var fileSize = file.size;
    //alert("maxFileSize is="+maxFileSize+" and your file is="+fileSize);
    if(fileSize >= maxFileSize)
    {
        $("b").show();
    } else{
        $("#fileSizeValidation").hide();
        uploadFormData();
    }
}

function uploadFormData(){

    $("#tabs1").css('opacity','0.6');
    $('#upLoading').css('left',($('#tabs').innerWidth()+$('#tabTwo').innerWidth()+(($('#tabs1').innerWidth())/2)));
    $("#upLoading").show();

    var oMyForm = new FormData();
    var file = $("#file")[0].files[0];
/*    var fileName = file.name;
    var fileSize = file.size;
    alert("file==="+fileName+" file size=="+fileSize);*/

    oMyForm.append("file", $("#file")[0].files[0]);
    oMyForm.append("commentRegion",$("#testAjaxForm #supDocComment").val());
    oMyForm.append("proactiveProjectId",$("#testAjaxForm #proactiveProjectId").val());
    oMyForm.append("reactiveProjectId",$("#testAjaxForm #reactiveProjectId").val());
    //oMyForm.append("transactionId",$(tableRowNo+txId).children('.txId').text());
    oMyForm.append("realTimeProjectId",$("#testAjaxForm #realTimeProjectId").val());
    //oMyForm.append("controlId",$("#controlId").val());
    $.ajax({
        url: './ajaxCallForFileUpload.html',
        data: oMyForm,
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data){
            var source = $('<div>' + data + '</div>');
            $('#supportingDocListDiv').html(source.find('#supportingDocListDiv').html());
           // $('#supportingDocListDiv').replaceWith(data);
            setActionForDeleteViewAttachment();

            $("#tabs1").css('opacity','1.0');
            $("#upLoading").hide();
            $("#removeClick").trigger('click');
        }
    });
}

function detectBrowser(){
    var browser;
    if($.browser.mozilla)
        browser = "Firefox";
    else if($.browser.msie)
        browser = "Internet Explorer";
    else if($.browser.opera)
        browser = "Opera";
    else if($.browser.safari)
        browser = "Safari";
    else
        browser = "Unknown";
    return browser;
}
function setMaxWidthOfSecondPane(maxWidthOfSecondPane,tabsTwoHeight){
    $( "#tabTwo" ).resizable({
        maxHeight:tabsTwoHeight,
        minHeight:tabsTwoHeight,
        minWidth: minWidhtOfAnyPane,
        maxWidth: maxWidthOfSecondPane
    });
}
function resizeThirdPane(){
//    bodyWidth = $('#mainHeadline').width();
    bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
    widthOfFirstPane = $('#tabOne').width();
    tabsTwoHeight = $('#tabTwo').height();
    maxWidthOfSecondPane = bodyWidth-(widthOfFirstPane + minWidhtOfAnyPane + subtractValueForPadding);
    setMaxWidthOfSecondPane(maxWidthOfSecondPane,tabsTwoHeight);
    var tabsTwoWidth = $('#tabTwo').width();
    var tabsOneWidth = $('#tabOne').width();
    openThisTabAndCloseOthers(selectionThirdPaneLI);

    $("#paneThreeIconCross").show();
    $("#paneThreeTotalDiv").show();
    $(".tabThreeIconDiv").hide(); //to hide img
    $("#tabThree .containerHeadline").height('20px');
    $("#tabThree .floatingBox").height(maxHeightOfThreePanel);
    //alert('body = '+bodyWidth+'tabOne ='+tabsOneWidth+'tabTwo be width'+(bodyWidth-tabsOneWidth-tabsTwoWidth));
    $('#tabThree').width(((bodyWidth-subtractValueForPadding -tabsOneWidth-tabsTwoWidth)));
}
function resizeSecondPane(){
//    var bodyWidth = $('#mainHeadline').width()-10;
    var bodyWidth = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width()-10;
    var tabsWidth = $('#tabOne').width();
    openThisTabAndCloseOthersForSecondPane(selectionSecondPaneLI);
    $("#paneTwoIconCross").show();
    $("#paneTwoTotalDiv").show();
    $(".tabTwoIconDiv").hide(); //to hide img
    $("#tabTwo .containerHeadline").height('20px');
    //alert('body = '+bodyWidth+'tabOne ='+tabsWidth+'tabTwo be width'+(bodyWidth-tabsWidth-2)/2);
    $('#tabTwo').width((bodyWidth-tabsWidth-2)/2);
}
function resizeFirstPane(){
    openThisTabAndCloseOthersForFirstPane(selectionFirstPaneLI);
    $(".verticalDiv .menu1 a").removeClass("current");
    $("#"+selectionFirstPaneA).addClass("current");
    $(".verticalDiv .menu1 a .labelWithImage").show();
    $(".verticalDiv .menu1 li img ").hide(); //to show img

}

function loadAnalysisTab(id) {
    $.ajax({
        url: '../riskasst/deleteSupportingDoc.html?supportingDocId='+id.trim(),
        async:false,
        type: 'GET',
        success: function(data){
            var source = $('<div>' + data + '</div>');
            $('#supportingDocListDiv').html(source.find('#supportingDocListDiv').html());
            //$('#supportingDocListDiv').replaceWith(data);
            setActionForDeleteViewAttachment();
        }
    });
}
function deletePolicyAttachment(id) {
    $.ajax({
        url: '../policy/deleteTransactionPolicy.html?txPolicyId='+id.trim(),
        async:false,
        type: 'GET',
        success: function(data){
            var source = $('<div>' + data + '</div>');
            $('#txPolicyListWrapperDiv').html(source.find('#txPolicyListWrapperDiv').html());
            setActionForDeleteViewAttachmentInPolicy();
        }
    });
}
function setActionForDeleteViewAttachment(){
    $(".deleteAttachment").off("click");
    $('.deleteAttachment').click(function(){
        loadAnalysisTab($(this).attr('id'));
    });

    $(".viewAttachment").off("click");
    $('.viewAttachment').click(function(){
        var postUrl = "../riskasst/supportingDocumentShow.html?supportingDocumentId=" +$(this).attr('id');
        //window.location = postUrl;
        var win = window.open(postUrl, '_blank');
        win.focus();
    });
    $(".downloadAttachment").off("click");
    $('.downloadAttachment').click(function(){
        var postUrl = "../riskasst/supportingDocumentShow.html?supportingDocumentId=" +$(this).attr('id');
        window.location = postUrl;
    });
}
function setActionForDeleteViewAttachmentInPolicy(){
    $(".downloadPolicyAttachment").off("click");
    $('.deletePolicyAttachment').click(function(){
        deletePolicyAttachment($(this).attr('id'));
    });
    $(".viewPolicyAttachment").off("click");
    $('.viewPolicyAttachment').click(function(){
        var postUrl = "../policy/downloadTransactionPolicy.html?policyId=" +$(this).attr('id');
        //window.location = postUrl;
        var win = window.open(postUrl, '_blank');
        win.focus();
    });
    $(".downloadPolicyAttachment").off("click");
    $('.downloadPolicyAttachment').click(function(){
        var postUrl = "../policy/downloadTransactionPolicy.html?policyId=" +$(this).attr('id');
        window.location = postUrl;
    });

}

function expandPane(paneId,maxWidthOfPaneOne) {
    //alert('-------paneId------'+paneId);
    $( paneId+' .containerHeadline').height('20px');
    $( paneId ).animate({
        width: maxWidthOfPaneOne
    }, animationDelayTime);
}
function openThisTabAndCloseOthersForFirstPane(liId){
    $("#transactionListLi").removeClass('active');
    $("#summaryOfThirdPartyTransactionsLi").removeClass('active');
    $("#"+liId).addClass('active'); //open selected tab
    $("#"+liId+' a').trigger('click');
}
function increasePaneFromClose(tab1,tab2,tab3,closeTab,closeOrOpen){
    if('close' == closeOrOpen){
        if(closeTab =='tabOne'){
            tabOne = false;
            $("#tabOne").animate({
                width: minWidhtOfPaneOne
            }, animationDelayTime, function() {
                // Animation complete.
                // resizeSecondPane();
                // resizeThirdPane();
            });
            // expandPane('#tabOne',minWidhtOfPaneOne);

        }else if(closeTab =='tabTwo'){
            tabTwo = false;
            $("#tabTwo").animate({
                width: minWidhtOfPaneOne
            }, animationDelayTime, function() {
                // Animation complete.
                //resizeThirdPane();
            });
//            expandPane('#tabTwo',minWidhtOfPaneOne);
        }else if(closeTab =='tabThree'){
            tabThree = false;
            expandPane('#tabThree',minWidhtOfPaneOne);
        }
    }
    if('open' == closeOrOpen){
        if(closeTab =='tabOne'){
            tabOne = true;
        }else if(closeTab =='tabTwo'){
            tabTwo = true;
        }else if(closeTab =='tabThree'){
            tabThree = true;
        }
    }
    var count = 0;
    if(tabOne == false)
        count++;
    if(tabTwo == false)
        count++;
    if(tabThree == false)
        count++;



//    var wi = $('#mainHeadline').width();
    var wi = $('#tabOne').parent('div.container-fluid').parent('div.floatingBox').width();
    var restTotal = wi-10- (minWidhtOfAnyPane*count);
    var pointToBeResize= 0;
    if(count == 1)
        pointToBeResize = restTotal/2;
    if(count == 2)
        pointToBeResize = restTotal;
    if(count== 0)
        pointToBeResize = restTotal/3;
    //alert('tab1--'+tabOne+"---tab2---"+tabTwo+" tab3--"+tabThree+"--count--"+count);

    if('open' == closeOrOpen && count > 0){
        // alert('in open----point to resize---'+pointToBeResize+" count ="+count);

        if(tabOne == true){
            expandPane('#tabOne',pointToBeResize);
        }
        if(tabThree == true){
            expandPane('#tabThree',pointToBeResize-3);
        }
        if(tabTwo == true){
            expandPane('#tabTwo',pointToBeResize);
        }

    }else if('open' == closeOrOpen && count == 0){
        //alert('in open--all are ture --point to resize---'+pointToBeResize);

        if(tabOne == true){
            expandPane('#tabOne',initialWidhtOfPaneOne);
        }
        if(tabThree == true){
            expandPane('#tabThree',initialWidhtOfPaneThree);
        }
        if(tabTwo == true){
            expandPane('#tabTwo',initialWidhtOfPaneTwo);
        }

    }
    if('close' == closeOrOpen){
        //alert('total size---'+wi+' in CLOSE----point to resize---'+pointToBeResize);
        if(tabOne == true){
            //  alert('in CLOSE tab1----point to resize---'+pointToBeResize);
            expandPane('#tabOne',pointToBeResize);
        }
        if(tabThree == true){
            // alert('in CLOSE tab2----point to resize---'+pointToBeResize);
            expandPane('#tabTrhee',pointToBeResize-1);
        }
        if(tabTwo == true){
            // alert('in CLOSE tab3----point to resize---'+pointToBeResize);
            expandPane('#tabTwo',pointToBeResize);
        }

    }

}
function setAutoComplete(){
    $("#addEmployee").hide();
    $('#autoCompleteTextBox').typeahead({
        source: function (query, process) {
            states = [];
            map = {};
           var data = JSON.parse($("#autoCompleteInputTextBox").val());
            $.each(data, function (i, state) {
                map[state.empName] = state;
                states.push(state.empName);
            });

            process(states);
        },
        updater: function (item) {
            empIdForAutoComplete = map[item].empId;
            empNameForAutoComplete = map[item].empName;
            $("#addEmployee").show();
            return item;
        },
        matcher: function (item) {
            $("#addEmployee").hide();
            if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) === 0) {
                return true;
            }
        },
        sorter: function (items) {
            return items.sort();
        },
        highlighter: function (item) {
            var regex = new RegExp( '(' + this.query + ')', 'gi' );
            return item.replace( regex, "<strong>$1</strong>" );
        }
    });
}


function addEmployeeBtn() {
    $(document).on('click',"#addEmployee",function(){
        console.log('--'+empIdForAutoComplete);
        if($("#selectEmployeeDive #" +empIdForAutoComplete).length > 0) {
            $.prompt(empNameForAutoComplete + " already selected.");
        } else {
            option={};
            option['id'] = empIdForAutoComplete;
            option['name'] = empNameForAutoComplete;
            employeeJason.push(option);
            $("#selectEmployeeDive").append('<div class="employeeList" id="'+empIdForAutoComplete+'"> <i class="icon-check darkGreen"></i>&nbsp;&nbsp;'+empNameForAutoComplete+'<span class="removeEmpBtn" style=" margin-left: 10px;" ></span></div>');
            $("#autoCompleteTextBox").val('');
        }
    });

}


