var questionhtml="";
var optionIndex=1;
var options = "";
var editOption = "";
$(document).ready(function () {

    $("#addQuestionBtn").click(function(){
        $("#text").val("");
        $("#optionsDiv").text("");
        questionhtml="";
        $('#modalOpenLink').trigger('click');
    });

    var column;
    var buttons;

    if(user.role == employeeRole || user.role == ia_analystRole || user.role == ia_managerRole){
        column = [ {
            display : 'Name',
            name : 'documentName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Type',
            name : 'trainingType',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Download',
            name : 'fileIconLocation',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Uploaded',
            name : 'author',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Date',
            name : 'entryDate',
            width : 'auto',
            sortable : true,
            align : 'center'
        }, {
            display : '',
            name : 'testStartHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : 'Backend data',
            name : 'id',
            width : 1,
            hide : true,
            align : 'center'
        }];
        buttons = [
            { name: '#trainingListTable', bclass: 'PDF', onpress: printTable },
            { name: '#trainingListTable', bclass: 'XLS', onpress: toExcel },
            { name: '#trainingListTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#trainingListTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#trainingListTable', bclass: 'GROUPING', onpress: groupByView }
        ];
    }else if(user.role == complianceRole || user.role == legalRole || user.role == adminRole){
        column = [ {
            display : 'Name',
            name : 'documentName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Training Type',
            name : 'trainingType',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Download',
            name : 'fileIconLocation',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Uploaded',
            name : 'author',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Date',
            name : 'entryDate',
            width : 'auto',
            sortable : true,
            align : 'center'
        }/*, {
            display : '',
            name : 'testStartHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        }*/, {
            display : '',
            name : 'questionSetHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'policyDeleteHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : 'Backend data',
            name : 'id',
            width : 1,
            hide : true,
            align : 'center'
        }];

        buttons = [ {
            name : '#trainingListTable',bclass : 'ADD' },
            { name: '#trainingListTable', bclass: 'PDF', onpress: printTable },
            { name: '#trainingListTable', bclass: 'XLS', onpress: toExcel },
            { name: '#trainingListTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#trainingListTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#trainingListTable', bclass: 'GROUPING', onpress: groupByView }
        ];
    }

    $("#trainingListTable").flexigrid({
        url : 'getJASONforTrainingList.html?'
            + 'tableName=trainingListTable'
            + '&fromSearch=1',
        dataType : 'json',
        colModel : column,
        buttons :buttons,
        searchitems : [ {
            display : 'Name',
            name : 'documentName'
        }, {
            display : 'Training Type',
            name : 'trainingType',
            isdefault : true
        }, {
            display : 'Uploaded',
            name : 'author'
        }, {
            display : 'Date',
            name : 'entryDate'

        } ],
        sortname : "documentName",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300,
        onSuccess: function () {
            addGrid($('#trainingListTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $(document).on('click', '.ADD', function() {
        $.prompt('Are you sure to add a new training?', {
            title: "",
            buttons: { "Yes": true, "No": false },
            submit: function(e,v,m,f){
                if (v) {
                    $('#modalOpenLink').trigger('click');
                    $("#documentName").val('');
                    $("#fileData").val('');
                }
            }
        });


    });
    downLoadTraining();


    $("#trainingSearchSubmitButton").click(function(){
        var newUrl = 'getJASONforTrainingList.html?'
            +'documentName='+$("#docNameFortraining").val()
            +'&author='+$("#authorFortraining").val()
            +'&entryTime='+$("#entryTimeFortraining").val()
            +'&trainingType='+$("#trainingTypeForTraining").val()
            +'&tableName=trainingListTable'
            +'&fromSearch=1';
        $("#trainingListTable").flexOptions({url: newUrl,newp: 1});
        $("#trainingListTable").flexReload();
    });

    $("#entryTime").datepicker();
    $("#implementedDate").datepicker({ dateFormat: "mm-dd-yy"}).datepicker("setDate",new Date());
    $("#entryTime").val("");

    $("#entryTimeFortraining").datepicker();
    $("#entryTimeFortraining").val("");

    $('#docNameFortraining,#authorFortraining,#entryTimeFortraining').keypress(function(e){
        if(e.which == 13){//Enter key pressed
            $('#trainingSearchSubmitButton').click();//Trigger search button click event
        }

    });

    $("#trainingDetails").click(function(){
        $('#trainingDetailsA').trigger('click');

    });

    $( ".bpcQuestion" ).find('span').hide();

    $( ".bpcQuestion" ).mouseleave(function(){
        var question = $(this);
        $(question).find('span').hide();
    });
    $( ".bpcQuestion" ).mouseover(function(){
        var question = $(this);
        $(question).find('span').show();
    });

    $( ".editQuestion" ).click(function(e){
        $("#editOptionsDiv").html('');
        var questionText = $(e.target).parent('div').text();
        questionText = questionText.replace("edit", "");
        questionText = questionText.replace("delete", "");
        $("#editText").val(questionText.trim());
        editOption = "";

        $(e.target).parent('div').parent('div').find(".css-label").each(function(event){
            var optionText = $(this).text();
            var optionId = $(this).parent().find(".optionId").val();
            setOptionTextBlock(optionId , optionText);
        });
        var questionId = $(e.target).parent("div").parent("div").find("#questionId").val();

        $("#qId").val(questionId);
        $("#editOptionsDiv").append(getOptionTextBlock());
        $('#questionEditModel').trigger('click');

    });
    $( ".deleteQuestion" ).click(function(e){
        deleteQuestion(e);
        $(e.target).parent('div').parent('div').remove() ;
    });



});

function setTrainingUploadAction(){
    $(document).on('click', '#uploadTraining', function() {
        var oMyForm = new FormData();
        oMyForm.append("file", $("#inputFile")[0].files[0]);
        oMyForm.append("documentName",$("#documentName").val());
        oMyForm.append("trainingType",$("#trainingType").val());
        oMyForm.append("implementedDate",$("#implementedDate").val());
        $('#uploadNewPolicy').css('opacity','0.8');
        $("#loadingImage").show();
        $.ajax({
            url: '../training/uploadTraining.html',
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#uploadNewPolicy').modal('toggle');
                $('#uploadNewPolicy').css('opacity','1.0');
                $("#loadingImage").hide();
                showMessageByForce("#policyListPage","alert-success","Training Saved ");
                $("#trainingListTable").flexReload();
            }
        });
    });
}

function setTrainingDeleteAction(){
    $(document).on('click', '#trainingDeleteButtonHtml', function() {
        var policyId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "deleteTraining.html?trainingId=" + policyId;
        $.prompt('Are you sure to delete the training?', {
            title: "",
            buttons: { "Yes": true, "No": false },
            submit: function(e,v,m,f){
                if (v) {
                     $.ajax({
                     type:'get',
                     url:getUrl,
                     async:false,
                     success:function (data) {
                     showMessageByForce("#policyListPage","alert-error","Training Deleted ");
                     $("#trainingListTable").flexReload();
                     }
                     });
                }
            }
        });


    });
}

function getTrainingQuestion(){
    $(document).on('click', '#questionSetButtonHtml', function() {
        var trainingId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "trainingQuestion.html?trainingId=" + trainingId;
        document.location=getUrl;
    });
}

function downLoadTraining(){

    $(document).on('click', '#trainingListTable [abbr=fileIconLocation]', function() {
        var postUrl = "../training/trainingShow.html?trainingId=" +$(this).parent().children(":last").text();
        window.location = postUrl;
    });
}


function setAQuestion(){
    $(document).on('click', '#addNewQuestion', function() {
        var oMyForm = new FormData();
        oMyForm.append("file", $("#inputFile")[0].files[0]);
        oMyForm.append("documentName",$("#documentName").val());
        oMyForm.append("trainingType",$("#trainingType").val());
        $('#uploadNewPolicy').css('opacity','0.8');
        $("#loadingImage").show();
        $.ajax({
            url: './addQuestion.html',
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#uploadNewPolicy').modal('toggle');
                $('#uploadNewPolicy').css('opacity','1.0');
                $("#loadingImage").hide();
                showMessageByForce("#policyListPage","alert-success","Training Saved ");
                $("#trainingListTable").flexReload();
            }
        });
    });
}
function addNewQuestion(){
    $(document).on('click', '#addNewQuestion', function() {
        var isCorrect;
        var optionText='';
        var lastRadioId = 0;
        var lastId = 0;
        var flag = false;
        jsonOpston = [];
        lastItedIdSt = $("#queryDiv div:last").find("input:first").prop("id");
        lastItedName = $("#queryDiv div:last").find("input:first").prop("name");
        if( typeof lastItedIdSt != "undefined" ){
            lastRadioId = parseInt(lastItedName.substring(lastItedName.lastIndexOf("_") + 1, lastItedName.length))+1;
            lastId = parseInt(lastItedIdSt.substring(lastItedIdSt.lastIndexOf("_") + 1, lastItedIdSt.length))  + 1;
        }
        var charCode= "a".charCodeAt(0);

        if($("#text").val() == '') {
            return ;
        }

        $(".optionLabel").each(function(){
            optionText = $(this).text();
            isCorrect = $("#"+$(this).prop("for")).prop("checked");
            if(isCorrect == true) {
                flag = true;
            }
            option={};
            option['optionText'] = optionText;
            option['isCorrect'] = isCorrect;
            option['trainingQuestion'] = null;
            jsonOpston.push(option);
            setOptionS(lastRadioId, lastId++, charCode++, optionText, isCorrect );
        });
        if(flag == false) {
            // no option value is checked;
            options = "";

            return;
        }
        questionhtml = '<div class="bpcQuestion">'+(lastRadioId+1)+'. '+ $("#text").val()+' </div>' + '<div class="answerCss"><div class="control-group"><label class="control-label"></label><div class="controls">' + getOptionsHtml()+'</div></div></div>';

        var oMyForm = new FormData();
        oMyForm.append("query", $("#text").val());
        oMyForm.append("jsonOpston", JSON.stringify(jsonOpston));

        $('#addQuestion').css('opacity','0.8');
        $.ajax({
            url: '../training/addAQuestion.html?trainingId='+$("#trainingId").val(),
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#addQuestion').modal('toggle');
                $('#addQuestion').css('opacity','1.0');
                showMessageByForce("#policyListPage","alert-success","Training Saved ");
                $("#queryDiv").append(questionhtml);
                options="";
            }
        });
    });
}

function startTest(){
    $(document).on('click', '#trainingStart', function() {
        var trainingId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "../training/startTest.html?trainingId=" + trainingId;
        document.location=getUrl;
    });
}

function downloadCertificate(){
    $(document).on('click', '#trainingCertificateDownload', function() {
        var trainingId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "../training/downloadCertificate.html?trainingId=" + trainingId;
        document.location=getUrl;
    });
}


function certificate(){
    $(document).on('click', '#trainingCertificateDownload', function() {
        var trainingId = $("#trainingId").val();
        var getUrl = "../training/downloadCertificate.html?trainingId=" + trainingId;
        document.location=getUrl;
    });
}


function addQuestionAnswer(){
    $(document).on('click', '#addQAnswer', function() {
        var text = $("#textBlock").val();
        if( $("#textBlock").length ) {
            if($("#textBlock").val() =='') {
                return;
            }
            $("#optionsDiv").append('<input id="demo_box_'+optionIndex +'" class="css-radio" name="myRadio" type="radio" /><label for="demo_box_'+optionIndex+'" class="css-label optionLabel radio">'+text+'</label>');
            optionIndex++;
            $("#textBlock").val("");
        } else {
            $("#addMoreOption").before('<div class="control-group  textAreaDiv"> <label class="control-label">Option</label><div class="controls"><textarea class="span10 " id ="textBlock"></textarea></div></div>');
        }
    });
}

function setOptionS(lastRadioId, lastId, charCode, optionText, isCorrect ) {
    if(isCorrect == true) {
        options += '<input type="radio" checked="checked"  name="myRadio_'+lastRadioId+'" class="css-radio" id="demo_box_'+lastId+'">'
    } else {
        options += '<input type="radio"  name="myRadio_'+lastRadioId+'" class="css-radio" id="demo_box_'+lastId+'">'
    }
    options += ' <label class="css-label radio" for="demo_box_'+lastId+'">'+optionText+' </label>'

}

function getOptionsHtml() {
    return options;
}

function saveTrainingDetails(){
    $(document).on('click', '#saveDetail', function() {

        var oMyForm = new FormData();
        oMyForm.append("numberOfQuestion", $("#numberOfQuestion").val());
        oMyForm.append("passNumber", $("#passNumber").val());

        $('#trainingDetailsModal').css('opacity','0.8');
        $.ajax({
            url: '../training/ajaxSveTrainingDetails.html?trainingId='+$("#trainingId").val(),
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#trainingDetailsModal').modal('toggle');
                $('#trainingDetailsModal').css('opacity','1.0');
                showMessageByForce("#policyListPage","alert-success","Training Saved ");


            }
        });
    });
}
function deleteQuestion(e) {
    var questionId = $(e.target).parent("div").parent("div").find("#questionId").val();

    $.ajax({
        url: '../training/deleteQuestionAjax.html?trainingId='+$("#trainingId").val()+'&questionId=' + questionId,
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data){
            showMessageByForce("#policyListPage","alert-success","Question delete successfully ");
        }
    });
}

function setOptionTextBlock(optionId , optionText) {
    editOption += ' <div class="control-group" ><div class="controls"><input type="hidden" class="opClass" name="opClass" value="'+optionId+'"/><textarea class="editedOptionText span10" >'+optionText+'</textarea></div></div>';
}
function getOptionTextBlock() {
    return editOption;
}

function saveEditedQuestion() {
    $(document).on('click', '#editQuestionBtn', function() {
        editedOptionJson = [];
        var questionText = $("#editText").val();

        $(".editedOptionText").each(function(){
            var optionId = $(this).parent().find(".opClass").val();
            var optionText = $(this).val();
            option={};
            option['optionText'] = optionText;
            option['optionId'] = optionId;
            editedOptionJson.push(option);
        });

        questionId = $("#qId").val();
        var oMyForm = new FormData();
        oMyForm.append("query", questionText);
        oMyForm.append("opstonsJSON", JSON.stringify(editedOptionJson));

        $('#editQuestion').css('opacity','0.8');
        $.ajax({
            url: '../training/editQuestionAjax.html?trainingId='+$("#trainingId").val()+'&questionId=' + questionId,
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#editQuestion').modal('toggle');
                $('#editQuestion').css('opacity','1.0');
                showMessageByForce("#policyListPage","alert-success","Question updated successfully ");
            }
        });

    });
}