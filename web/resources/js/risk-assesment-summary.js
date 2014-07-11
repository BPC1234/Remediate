

var maxFileSize =0;
var selectionSecondPaneA;
var selectionSecondPaneLI;
var selectionThirdPaneA;
var selectionThirdPaneLI;
var minWidthForIfCondition = 40;
$(document).ready(function () {

    /*for the purpose of the page loading time calling*/
    var projectTrxId = $(tableRowNo+txId).children('.txId').text();

    var newExtension  = "txId="+$(tableRowNo+txId).children('.txId').text()
        +"&dt="+$(tableRowNo+txId).children('.createdDate').text()
        +"&amn="+$(tableRowNo+txId).children('.amount').text()
        +"&rgnName="+ $(tableRowNo+txId).children('.regionName').text()
        +"&dc="+ $(tableRowNo+txId).children('.documentCreator').text()
        +"&app="+ $(tableRowNo+txId).children('.approver').text()
        +"&dtOfApp="+ $(tableRowNo+txId).children('.dateOfApproval').text()
        /*+"&decission="+$(this).find(".decission").text()*/
        +"&proactiveProjectId="+$("#proactiveProjectId").val()
        +"&reactiveProjectId="+$("#reactiveProjectId").val()
        +"&projectType="+$(tableRowNo+txId).children('.projectType').text()
        +"&projectId="+$(tableRowNo+txId).children('.projectId').text()
        +"&transactionType="+$(tableRowNo+txId).children('.transactionType').text()
        +"&ctrlId="+$("#controlId").val()
        +"&transactionId="+$(tableRowNo+txId).children('.transactionId').text()
        +"&serial="+$(tableRowNo+txId).children('.serial').text()
        +"&rtProjectId="+$("#realTimeProjectId").val();

    $("div#contentWrapper").css('background','none');
    $("#content").css('opacity','0.6');
    $('#loading').css('left',(($('#content').innerWidth())/2) );
    $("#loading").show();
    //alert('---------not from script------'+ newExtension);
    callAjaxForNewRiskAssesmentSummaryView(newExtension);


    $("#summeryTableForNewRiskAssesment tbody tr").click(function(){ //click anywhere in a row
        var txRow = this;
        if(isPartialData) {
            $.prompt("Are you sure you want to exit the transaction? All unsaved information will be lost.", {
                title: " ",
                buttons: { "Yes": true, "No": false },
                submit: function(e,v,m,f){
                    if (v)
                    {
                        isPartialData = false;
                        getTransactionDetails(txRow);
                        selectedRow(txRow);
                    }
                    else
                    {
                        //Redirect to the this page
                    }
                }
            });

        } else {
            getTransactionDetails(txRow);
            selectedRow(txRow);
        }
    });


});

function getTransactionDetails(id) {
    var extension = "txId="+$(id).find(".txId").text()
        +"&dt="+$(id).find(".createdDate").text()
        +"&amn="+$(id).find(".amount").text()
        +"&rgnName="+$(id).find(".regionName").text()
        +"&dc="+$(id).find(".documentCreator").text()
        +"&app="+$(id).find(".approver").text()
        +"&dtOfApp="+$(id).find(".dateOfApproval").text()
        /*+"&decission="+$(this).find(".decission").text()*/
        +"&proactiveProjectId="+$("#proactiveProjectId").val()
        +"&reactiveProjectId="+$("#reactiveProjectId").val()
        +"&projectType="+$(id).find(".projectType").text()
        +"&projectId="+$(id).find(".projectId").text()
        +"&transactionType="+$(id).find(".transactionType").text()
        +"&ctrlId="+$("#controlId").val()
        +"&transactionId="+$(id).find(".transactionId").text()
        +"&serial="+$(id).find(".serial").text()
        +"&rtProjectId="+$("#realTimeProjectId").val();

    //$("div#contentWrapper").removeAttr('id');
    $("#content").css('opacity','0.6');
    $('#loading').css('left',(($('#content').innerWidth())/2) );
    $("#loading").show();
    //alert('---------from script.js------'+ extension);
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

function selectedRow(id) {

    $(id).parent().children().each(function () {
        $(this).children().each(function () {
            if ($(this).hasClass("redRow"))
                $(this).removeClass("redRow");
        });
    });

    $(id).children().each(function () {
        $(this).addClass("redRow");
    });
}

function callAjaxForNewRiskAssesmentSummaryView(extension) {
    var postUrl = "../realtime/ajaxCallForNewRiskAssesment.html?"+extension;
    $(document).ready(function() {
        $.ajax({
            url : postUrl ,
            type : 'GET',
            async: false,
            data : {},
            success : function(data) {
                $('#tabs1').html($(data).filter("#tabs1").html());
                $('#subTabsUnderTabs1').replaceWith($(data).filter("#tabs2").html());
                $("#content").css('opacity','1.0');
                $("#tabs1").css('opacity','1.0');
                $(".content1").children('a').click();
                $(".tablesorterForAudittrial").flexigrid({


                    colModel : [
                        {display: 'Date', name : 'date', width : 134, sortable : true, align: 'left'},
                        {display: 'User', name : 'user', width : 134, sortable : true, align: 'left'},
                        {display: 'Event', name : 'Event', width : 231, sortable : true, align: 'left'}
                    ],
                    width: 400,
                    height: 200
                });

                $("#loading").hide();
                var iw = $('body').innerWidth();
                var tabsWidth = $('#tabs').innerWidth();
                var tabs1Width = $('#tabs1').innerWidth();
                $('#tabs1').width(iw-tabsWidth-(20));
                $(".tablesorterForSupportingDocument").flexigrid({


                    colModel : [
                        {display: 'File Name', name : 'File Name', width : 134, sortable : true, align: 'left'},
                        {display: 'Download', name : 'Download', width : 134, sortable : true, align: 'left'},
                        {display: 'Uploaded By', name : 'Uploaded By', width :100, sortable : true, align: 'left'},
                        {display: 'Date', name : 'Date', width : 134, sortable : true, align: 'left'},
                        {display: '', name : 'Date', width : 100, sortable : true, align: 'left'}

                    ],

                    width: 700,
                    height: 200
                });

                $('#policyTableGrid').dataTable( {
                    "sDom": 'T<"clear">lfrtip'
                } );

                $('#policyTableGrid_wrapper .DTTT_container').hide();
                $('#policyTableGrid_wrapper #policyTableGrid_filter').hide();




                $("#tablesorterForSupportingDocument tbody tr").click(function(event){//click anywhere in a row
                    selectedRow(this);
                    if($(event.target).html().trim() != "Delete"){
                        var postUrl = "./supportingDocumentShow.html?supportingDocumentId=" + $(this).find(".supportingDocumentId").text().trim() ;
                        //window.location = postUrl;
                        var win = window.open(postUrl, '_blank');
                        win.focus();
                    } else {
                        //Delete clicked

                        loadAnalysisTab($(this).find(".supportingDocumentId").text().trim());
                    }

                });


                $("#policyTableGrid tbody tr").click(function(){//click anywhere in a row
                    var postUrl = "./policyShow.html?policyId=" + $(this).find(".policyId").text().trim() ;
                    var win = window.open(postUrl, '_blank');
                    win.focus();
                });

                $("#cancelButton").click(function(){
                    var oldURL = document.referrer;
                    //alert(oldURL);
                    window.location = oldURL;
                });

                $('#jquery-removable-file-upload-example input[type=file]').removableFileUpload();
                $('#file').customFileInput();
                $('#tabs1').width("34%");
                $('#tabs').width("33%");
                $('#subTabsUnderTabs1').width("33%");
                $("#tabs").show();
                $("#tabs1").show();
                var windowHeight = $(window).height();
                var headerHeight = $("#header").height();
                var dolphincontainerHeight = $("#dolphincontainer").height();
                var groupSelectorHeight = $("#groupSelector").height();
                var footerHeight = $("#footer").height();
                $("#content").height((windowHeight-(headerHeight+dolphincontainerHeight+groupSelectorHeight+footerHeight)));
                $(".contentClass").height((windowHeight-(headerHeight+dolphincontainerHeight+groupSelectorHeight+footerHeight)));
                /*Start Third Pane*/
                selectionThirdPaneA ="trxDetailsList";
                selectionThirdPaneLI ="thirdPane-tabsOne";

                $(".verticalDivForThirdPane .menu1 li img ").hide(); //to hide img
                openThisTabAndCloseOthers("thirdPane-tabsOne");
                $(".verticalDivForThirdPane .menu1 a #trxDetailsLabel").css("color","#ffffff");
                $(".verticalDivForThirdPane .menu1 a").click(function(e){
                    $(".verticalDivForThirdPane .menu1 a").removeClass("current");
                    $(".verticalDivForThirdPane .menu1 a label").css("color","#000000");
                    $(this).addClass("current");
                    $(this).children('label').css("color","#ffffff");
                    openThisTabAndCloseOthers($(this).parent('li').attr('id'));
                    if("closeButtonDiv" != $(this).parent('').attr('class')){
                        selectionThirdPaneA = $(this).attr('id');
                        selectionThirdPaneLI = $(this).parent('li').attr('id');
                        // increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'open');
                        $("#closeThirdPane").show();
                        $(".verticalDivForThirdPane .menu1 a .labelWithImage").show();
                        $(".verticalDivForThirdPane .menu1 li img ").hide(); //to hide img

                        var widthOfTwo = $('#subTabsUnderTabs1').innerWidth();
                        var widthOfThree = $('#tabs1').innerWidth();
                        var widthOfOne = $('#tabs').innerWidth();

                        if(widthOfThree <= minWidthForIfCondition & widthOfTwo <= minWidthForIfCondition)
                            resizeSecondPane();
                        if(widthOfThree <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
                            resizeFirstPane();
                        if(widthOfThree <= minWidthForIfCondition){
                            increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'open');
                        }
                        tabThree = true;
                    }

                    e.preventDefault();
                });
                $( "#closeThirdPane" ).click(function() {
                    $("#closeThirdPane").hide();
                    $(".verticalDivForThirdPane .menu1 li img ").show(); //to show img
                    increasePaneFromClose(tabOne,tabTwo,tabThree,"tabThree",'close');
                    $(".verticalDivForThirdPane .menu1 a .labelWithImage").hide();

                });
                /*end of third pane*/

                /*Start Second Pane*/
                selectionSecondPaneA = "trxDetailsAddiInfoList";
                selectionSecondPaneLI = 'tabs-1UnderTabs1';
                $(".verticalDivForSecondPane .menu1 li img ").hide(); //to hide img
                openThisTabAndCloseOthersForSecondPane("tabs-1UnderTabs1");
                $(".verticalDivForSecondPane .menu1 a #trxDetailsAddiInfoLabel").css("color","#ffffff");
                $(".verticalDivForSecondPane .menu1 a").click(function(e){
                    $(".verticalDivForSecondPane .menu1 a").removeClass("current");
                    $(".verticalDivForSecondPane .menu1 a label").css("color","#000000");
                    $(this).addClass("current");
                    $(this).children('label').css("color","#ffffff");

                    openThisTabAndCloseOthersForSecondPane($(this).parent('li').attr('class'));
                    if("closeButtonDivForSecondPane" != $(this).parent('').attr('class')){
                        selectionSecondPaneA = $(this).attr('id');
                        selectionSecondPaneLI = $(this).parent('li').attr('class');
                        //increasePaneFromClose(tabOne,tabTwo,tabThree,"tabTwo",'open');
                        $("#closeSecondPane").show();
                        $(".verticalDivForSecondPane .menu1 a .labelWithImage").show();
                        $(".verticalDivForSecondPane .menu1 li img ").hide(); //to hide img
                        /*tabTwo = true;*/

                        var widthOfTwo = $('#subTabsUnderTabs1').innerWidth();
                        var widthOfThree = $('#tabs1').innerWidth();
                        var widthOfOne = $('#tabs').innerWidth();

                        if(widthOfThree <= minWidthForIfCondition & widthOfTwo <= minWidthForIfCondition)
                            resizeThirdPane();
                        if(widthOfTwo <= minWidthForIfCondition & widthOfOne <= minWidthForIfCondition)
                            resizeFirstPane();
                        if(widthOfTwo <= minWidthForIfCondition){
                            increasePaneFromClose(tabOne,tabTwo,tabThree,"tabTwo",'open');
                        }
                        tabTwo = true;
                    }
                    e.preventDefault();
                });
                $( "#closeSecondPane" ).click(function() {
                    $("#closeSecondPane").hide();
                    $(".verticalDivForSecondPane .menu1 li img ").show(); //to show img
                    increasePaneFromClose(tabOne,tabTwo,tabThree,"tabTwo",'close');
                    $(".verticalDivForSecondPane .menu1 a .labelWithImage").hide();
                });
                /*end of Second pane*/
                $("#decisionComment").focusout(function(){
                    if($('#decisionComment').hasClass(requiredFieldFocus)) {
                        if($('#decisionComment').val() != "")  {
                            $("#decisionComment").removeClass(requiredFieldFocus);
                        }
                    }
                });
                $("#commentRegion").focusout(function(){
                    if($('#commentRegion').hasClass(requiredFieldFocus)) {
                        if($('#commentRegion').val() != "")  {
                            $("#commentRegion").removeClass(requiredFieldFocus);
                        }
                    }
                });

                /* Resize of pane One*/
                var bodyWidth = $('#content').width();
                var tabsHeight = $('#tabs').height();

                var browserName = detectBrowser();
                var subtractValue = 0;
                if(browserName === "Firefox")
                    subtractValue = 59;
                else if(browserName === "Safari")
                    subtractValue = 60;

                $( "#tabs" ).resizable({
                    maxHeight:tabsHeight,
                    minHeight:tabsHeight,
                    minWidth: minWidhtOfAnyPane,
                    maxWidth: (bodyWidth-subtractValue)
                    /*maxHeight: 250,
                     maxWidth: 350,
                     minHeight: 150,
                     minWidth: 200*/
                });
                $("#tabs").bind("resize", function (event, ui) {
                    var bodyWidth = $('#content').width();
                    var widthOfOne = $('#tabs').innerWidth();
                    if(widthOfOne <= 60){
                        openThisTabAndCloseOthersForFirstPane('aa');
                        $(".verticalDiv .menu1 a").removeClass("current");
                        $("#close").hide();
                        $(".verticalDiv .menu1 li img ").show(); //to show img
                        $(".verticalDiv .menu1 a .labelWithImage").hide();
                    }else if(widthOfOne > 61){
                        openThisTabAndCloseOthersForFirstPane(selectionFirstPaneLI);
                        $("#"+selectionFirstPaneA).addClass("current");
                        $(".verticalDiv .menu1 a .labelWithImage").show();
                        $(".verticalDiv .menu1 li img ").hide();
                        $("#close").show();
                    }
                    if(widthOfOne > (bodyWidth-100)){   //resize pane Two and pane three
                        $("#subTabsUnderTabs1").width(minWidhtOfAnyPane);
                        $(".verticalDivForSecondPane .menu1 a .labelWithImage").hide();
                        $(".verticalDivForSecondPane .menu1 li img ").show(); //to show img
                        $(".verticalDivForSecondPane .menu1 a").removeClass("current");
                        openThisTabAndCloseOthersForSecondPane('');

                        $("#tabs1").width(minWidhtOfAnyPane);
                        $(".verticalDivForThirdPane .menu1 a .labelWithImage").hide();
                        $(".verticalDivForThirdPane .menu1 li img ").show(); //to show img
                        $(".verticalDivForThirdPane .menu1 a").removeClass("current");
                        openThisTabAndCloseOthers('');

                    }
                    if(widthOfOne > minWidhtOfAnyPane && (widthOfOne < (bodyWidth-100))){
                        var tabsWidth = $('#tabs').width();
                        resizeSecondPane();
                        openThisTabAndCloseOthers(selectionThirdPaneLI);
                        $(".verticalDivForThirdPane .menu1 a").removeClass("current");
                        $("#"+selectionThirdPaneA).addClass("current");
                        $(".verticalDivForThirdPane .menu1 a .labelWithImage").show();
                        $(".verticalDivForThirdPane .menu1 li img ").hide(); //to show img
                        $('#tabs1').width(((bodyWidth-tabsWidth)/2));
                    }

                });


                /* Resize of pane Two*/
                var maxWidthOfSecondPane = 0;
                var bodyWidth = $('#content').width();
                var widthOfFirstPane = $('#tabs').width();
                var tabsTwoHeight = $('#subTabsUnderTabs1').height();
                maxWidthOfSecondPane = bodyWidth-(widthOfFirstPane+minWidhtOfAnyPane);
                $( "#subTabsUnderTabs1" ).resizable({
                    maxHeight:tabsTwoHeight,
                    minHeight:tabsTwoHeight,
                    minWidth: minWidhtOfAnyPane,
                    maxWidth: maxWidthOfSecondPane
                });
                $("#subTabsUnderTabs1").bind("resize", function (event, ui) {
                    bodyWidth = $('#content').width();
                    var widthOfTwo = $('#subTabsUnderTabs1').innerWidth();
                    if(widthOfTwo <= 60){
                        $("#subTabsUnderTabs1").width(minWidhtOfAnyPane);
                        $(".verticalDivForSecondPane .menu1 a .labelWithImage").hide();
                        $(".verticalDivForSecondPane .menu1 li img ").show(); //to show img
                        $(".verticalDivForSecondPane .menu1 a").removeClass("current");
                        openThisTabAndCloseOthersForSecondPane('');
                        resizeThirdPane();
                    }else if(widthOfTwo > 61){
                        openThisTabAndCloseOthersForSecondPane(selectionSecondPaneLI);
                        $(".verticalDivForSecondPane .menu1 a").removeClass("current");
                        $("#"+selectionSecondPaneA).addClass("current");
                        $(".verticalDivForSecondPane .menu1 a .labelWithImage").show();
                        $(".verticalDivForSecondPane .menu1 li img ").hide(); //to show img
                        resizeThirdPane();
                    }
                    var tabsThreeWidth = $('#tabs1').width();
                    if(tabsThreeWidth <60){
                        $("#tabs1").width(minWidhtOfAnyPane);
                        $(".verticalDivForThirdPane .menu1 a .labelWithImage").hide();
                        $(".verticalDivForThirdPane .menu1 li img ").show(); //to show img
                        $(".verticalDivForThirdPane .menu1 a").removeClass("current");
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

function openThisTabAndCloseOthers(tabId){
    $(".thirdPane-tabsOne").hide();
    $(".thirdPane-tabsTwo").hide();
    $(".thirdPane-tabsFive").hide();
    $(".thirdPane-tabsThree").hide();
    $(".thirdPane-tabsSix").hide();
    $("."+tabId).show(); //open selected tab
}
function openThisTabAndCloseOthersForSecondPane(tabId){
    $("#tabs-1UnderTabs1").hide();
    $("#tabs-2UnderTabs1").hide();
    $("#tabs-3UnderTabs1").hide();
    $("#tabs-4UnderTabs1").hide();
    $("#"+tabId).show(); //open selected tab
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
    $('#upLoading').css('left',($('#tabs').innerWidth()+$('#subTabsUnderTabs1').innerWidth()+(($('#tabs1').innerWidth())/2)));
    $("#upLoading").show();

    var oMyForm = new FormData();

    //alert("file==="+fileName+" file size=="+fileSize);

    oMyForm.append("file", $("#file")[0].files[0]);
    oMyForm.append("commentRegion",$("#testAjaxForm .supportingDocumentFileUploadDiv #supDocComment").val());
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
            $('.tableDivForFileUploadAjax').replaceWith(data);
            $(".tablesorterForSupportingDocument").flexigrid({
                colModel : [
                    {display: 'File Name', name : 'File Name', width : 134, sortable : true, align: 'left'},
                    {display: 'Download', name : 'Download', width : 134, sortable : true, align: 'left'},
                    {display: 'Uploaded By', name : 'Uploaded By', width :100, sortable : true, align: 'left'},
                    {display: 'Date', name : 'Date', width : 134, sortable : true, align: 'left'},
                    {display: '', name : 'Date', width : 100, sortable : true, align: 'left'}

                ],

                width: 700,
                height: 200
            });


            $("#tablesorterForSupportingDocument tbody tr").click(function(event){//click anywhere in a row
                selectedRow(this)
                if($(event.target).html().trim() != "Delete"){
                    var postUrl = "./supportingDocumentShow.html?supportingDocumentId=" + $(this).find(".supportingDocumentId").text().trim() ;
                    //window.location = postUrl;
                    var win = window.open(postUrl, '_blank');
                    win.focus();
                } else {
                    //Delete clicked

                    loadAnalysisTab($(this).find(".supportingDocumentId").text().trim());
                }
            });
            $("#tabs1").css('opacity','1.0');
            $("#upLoading").hide();
            $("#removeClick").trigger('click');
        }
    });
}

jQuery.fn.removableFileUpload = function (conf) {
    var config = jQuery.extend({
        remove:    'remove'
    }, conf);

    return this.each(function () {
        var input        = $(this);

        var remove        = $('<div class="jquery-removable-file-upload" style="float: right; margin-top: 6px; margin-right: 24px; color: red;"><strong>[<a id="removeClick" href="#">' + config.remove + '</a>]</strong></div>').insertAfter(input).hide();
        var removeButton = $('<input id="uploadButton" type="button" class="myButton" value="Upload"/>').insertBefore(remove).hide();

        // var removeButton = $('<input id="uploadButton" type="button" class="myButton" value="Upload"/>');

        var onchange    = function () {

            var file = input.val();
            file = file.substring(file.lastIndexOf('\\') + 1);
            if (file) {
                remove.show();/*.find('strong').text(file);*/
                removeButton.show();
                removeButton.off('click');
                removeButton.click(function(){
                    validation();
                });
            }
            else {
                remove.hide();
                removeButton.remove();
                $("#fileSizeValidation").hide();
            }
        };

        input.change(onchange);

        remove.find('a').click(function () {
            remove.hide();
            removeButton.remove();
            $("#fileSizeValidation").hide();
            input = $('<input type="file" name="' + input.attr('name') + '"/>').replaceAll(input).change(onchange);
            //$('#testAjaxForm #file').reset();
            $('.customfile').remove();
            var mainDiv = $('.jquery-removable-file-upload');
            $('<input id="file" type="file" name="fileDataForSupportingDocument" />').insertBefore(mainDiv);
            $('.jquery-removable-file-upload').remove();
            $('#jquery-removable-file-upload-example input[type=file]').removableFileUpload();
            $('#file').customFileInput();
            removeButton.off('click');
            removeButton.click(function(){
                validation();
            });
            return false;
        });
    });
};

/**
 * --------------------------------------------------------------------
 * jQuery customfileinput plugin
 * Author: Scott Jehl, scott@filamentgroup.com
 * Copyright (c) 2009 Filament Group
 * licensed under MIT (filamentgroup.com/examples/mit-license.txt)
 * --------------------------------------------------------------------
 */
(function( $ ){
    $.fn.customFileInput = function(){
        //apply events and styles for file input element
        var fileInput = $(this)
            .addClass('customfile-input') //add class for CSS
            .mouseover(function(){ upload.addClass('customfile-hover'); })
            .mouseout(function(){ upload.removeClass('customfile-hover'); })
            .focus(function(){
                upload.addClass('customfile-focus');
                fileInput.data('val', fileInput.val());
            })
            .blur(function(){
                upload.removeClass('customfile-focus');
                $(this).trigger('checkChange');
            })
            .bind('disable',function(){
                fileInput.attr('disabled',true);
                upload.addClass('customfile-disabled');
            })
            .bind('enable',function(){
                fileInput.removeAttr('disabled');
                upload.removeClass('customfile-disabled');
            })
            .bind('checkChange', function(){
                if(fileInput.val() && fileInput.val() != fileInput.data('val')){
                    fileInput.trigger('change');
                }
            })
            .bind('change',function(){
                //get file name
                var fileName = $(this).val().split(/\\/).pop();
                //get file extension
                var fileExt = 'customfile-ext-' + fileName.split('.').pop().toLowerCase();
                //update the feedback
                uploadFeedback
                    .text(fileName) //set feedback text to filename
                    .removeClass(uploadFeedback.data('fileExt') || '') //remove any existing file extension class
                    .addClass(fileExt) //add file extension class
                    .data('fileExt', fileExt) //store file extension for class removal on next change
                    .addClass('customfile-feedback-populated'); //add class to show populated state
                //change text of button
                uploadButton.text('Change');
            })
            .click(function(){ //for IE and Opera, make sure change fires after choosing a file, using an async callback
                fileInput.data('val', fileInput.val());
                setTimeout(function(){
                    fileInput.trigger('checkChange');
                },100);
            });

        //create custom control container
        var upload = $('<div class="customfile"></div>');
        //create custom control button
        var uploadButton = $('<span class="customfile-button" aria-hidden="true">Browse</span>').appendTo(upload);
        //create custom control feedback
        var uploadFeedback = $('<span class="customfile-feedback" aria-hidden="true">No file selected...</span>').appendTo(upload);

        //match disabled state
        if(fileInput.is('[disabled]')){
            fileInput.trigger('disable');
        }


        //on mousemove, keep file input under the cursor to steal click
        upload
            .mousemove(function(e){
                fileInput.css({
                    // 'left': e.pageX - upload.offset().left - fileInput.outerWidth() + 20 //position right side 20px right of cursor X)
                    //'top': e.pageY - upload.offset().top - $(window).scrollTop() - 3
                    'width': 325,
                    'top':0
                });
            })
            .insertAfter(fileInput); //insert after the input
        //$('<input id="uploadButton" type="button" class="myButton" value="Upload"/>').insertAfter(upload);
        fileInput.appendTo(upload);

        //return jQuery
        return $(this);
    };
})( jQuery );

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

    /*var N= navigator.appName;
     var UA= navigator.userAgent;
     var temp;
     var browserVersion= UA.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
     if(browserVersion && (temp= UA.match(/version\/([\.\d]+)/i))!= null)
     browserVersion[2]= temp[1];
     browserVersion= browserVersion? [browserVersion[1], browserVersion[2]]: [N, navigator.appVersion,'-?'];*/
    return browser;
}
function setMaxWidthOfSecondPane(maxWidthOfSecondPane,tabsTwoHeight){
    $( "#subTabsUnderTabs1" ).resizable({
        maxHeight:tabsTwoHeight,
        minHeight:tabsTwoHeight,
        minWidth: minWidhtOfAnyPane,
        maxWidth: maxWidthOfSecondPane
    });
}
function resizeThirdPane(){
    bodyWidth = $('#content').width();
    widthOfFirstPane = $('#tabs').width();
    tabsTwoHeight = $('#subTabsUnderTabs1').height();
    maxWidthOfSecondPane = bodyWidth-(widthOfFirstPane+minWidhtOfAnyPane);
    setMaxWidthOfSecondPane(maxWidthOfSecondPane,tabsTwoHeight);
    var tabsTwoWidth = $('#subTabsUnderTabs1').width();
    var tabsOneWidth = $('#tabs').width();
    openThisTabAndCloseOthers(selectionThirdPaneLI);
    $(".verticalDivForThirdPane .menu1 a").removeClass("current");
    $("#"+selectionThirdPaneA).addClass("current");
    $(".verticalDivForThirdPane .menu1 a .labelWithImage").show();
    $(".verticalDivForThirdPane .menu1 li img ").hide(); //to show img
    $('#tabs1').width(((bodyWidth-tabsOneWidth-tabsTwoWidth-4)));
}
function resizeSecondPane(){
    var bodyWidth = $('#content').width();
    var tabsWidth = $('#tabs').width();
    openThisTabAndCloseOthersForSecondPane(selectionSecondPaneLI);
    $(".verticalDivForSecondPane .menu1 a").removeClass("current");
    $("#"+selectionSecondPaneA).addClass("current");
    $(".verticalDivForSecondPane .menu1 a .labelWithImage").show();
    $(".verticalDivForSecondPane .menu1 li img ").hide(); //to show img
    $('#subTabsUnderTabs1').width((bodyWidth-tabsWidth-2)/2);
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
        url: './deleteSupportingDoc.html?supportingDocId='+id,
        async:false,
        type: 'GET',
        success: function(data){
            $('.tableDivForFileUploadAjax').replaceWith(data);
            $(".tablesorterForSupportingDocument").flexigrid({
                colModel : [
                    {display: 'File Name', name : 'File Name', width : 134, sortable : true, align: 'left'},
                    {display: 'Download', name : 'Download', width : 134, sortable : true, align: 'left'},
                    {display: 'Uploaded By', name : 'Uploaded By', width :100, sortable : true, align: 'left'},
                    {display: 'Date', name : 'Date', width : 134, sortable : true, align: 'left'}

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
            $("#tablesorterForSupportingDocument tbody tr").click(function(event){//click anywhere in a row
                if($(event.target).html().trim() != "Delete"){
                    var postUrl = "./supportingDocumentShow.html?supportingDocumentId=" + $(this).find(".supportingDocumentId").text().trim() ;
                    //window.location = postUrl;
                    var win = window.open(postUrl, '_blank');
                    win.focus();
                } else {
                    //Delete clicked

                    loadAnalysisTab($(this).find(".supportingDocumentId").text().trim());
                }
            });
            $("#tabs1").css('opacity','1.0');
            $("#upLoading").hide();
            $("#removeClick").trigger('click');
        }
    });
}