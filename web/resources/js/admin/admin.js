var userId=0;
var optionIndex=1;
var options = "";
$(document).ready(function () {

//    User list grid start
$("#userTableId").flexigrid({
    url : 'getUserJASON.html',
    dataType : 'json',
    colModel : [ {
        display : 'Name',
        name : 'userName',
        width : 'auto',
        sortable : true,
        align : 'left'
    }, {
        display : 'Role',
        name : 'role',
        width : 'auto',
        sortable : true,
        align : 'left'
    }, {
        display : 'Active',
        name : 'userActiveCheckBoxHtml',
        width : 'auto',
        sortable : false,
        align : 'center'
    }, {
        display : '',
        name : 'userEditButtonHtml',
        width : 'auto',
        sortable : false,
        align : 'center'
    }, {
        display : '',
        name : 'userDeleteButtonHtml',
        width : 'auto',
        sortable : false,
        align : 'center'
    }, {
        display : 'Backend data',
        name : 'id',
        width : 1,
        hide : true,
        align : 'center'
    }],
    buttons : [{ name: '#userTableId', bclass: 'ADD'}],
    searchitems : [ {
        display : 'Control',
        name : 'name'
    }, {
        display : 'Transaction Type',
        name : 'transactionType',
        isdefault : true
    } ],
    sortname : "user_name",
    sortorder : "asc",
    usepager : true,
    title : '',
    useRp : true,
    rp : 10,
    showTableToggleBtn : true,
    height:300 ,
    onSuccess: function () {
        addGrid($('#userTableId'), this);  //PATCH to get the grids to be responseive
    },
    getGridClass: function (g) { //PATCH to get the grids to be responseive
        this.g = g; //Is this the only way to expose the grid class ?
        return g;
    }
});

/*
$(".addControl").before('<a href="#addNewUser" role="button" class="add addControlIcon" data-toggle="modal">Add</a>');
$(".addControl").remove();

*/
    $(document).on('click', '.ADD', function() {
        $('#modalOpenLinkForUser').trigger('click');
        $("#fullname").val("");
        $("#password").val("");
        $("#passwordConf").val("");
        $("#email").val("");
        $("#activeUser").prop('checked', false);
    });

    $(document).on('click', '#saveUser', function() {

    var getUrl = "addUser.html?id=" + userId;
    $.ajax({
        type:'post',
        url:getUrl,
        data: $("#projectForm").serialize(),
        async:false,
        success:function (data) {
            var saveOrUpdate ;
            if(userId > 0)
                saveOrUpdate = 'User Updated '
            else
                saveOrUpdate = 'User Saved '

            $('#addNewUser').modal('toggle');
            $("#userTableId").flexReload();
            showMessageByForce("#userListPageId","alert-success",saveOrUpdate);
            userId=0;
        }

    });
});


$(document).on('click', '.userEditButtonCss', function() {

    userId = $(this).parent().parent().parent().children(":last").children(":last").text();
    var getUrl = "getUser.html?userId=" + userId;
    $.ajax({
        type:'get',
        url:getUrl,
        async:false,
        success:function (data) {
            $("#fullname").val($(data).attr("userName"));
            $("#password").val($(data).attr("password"));
            $("#email").val($(data).attr("email"));
            if($(data).attr("active")) {
                $("#activeUser").prop('checked', true);
            } else {
                $("#activeUser").prop('checked', false);
            }
        }


    });

    // document.addConForm.submit();
});



$(document).on('click', '#userDeleteButtonHtml', function() {
    var userId = $(this).parent().parent().parent().children(":last").children(":last").text();
    var getUrl = "userDelete.html?id=" + userId;
    $.ajax({
        type:'get',
        url:getUrl,
        async:false,
        success:function (data) {
            showMessageByForce("#userListPageId","alert-error","User Deleted ");
            $("#userTableId").flexReload();
        }


    });
});
    var column;
    var buttons;

    if(user.role == employeeRole || user.role == ia_analystRole|| user.role == ia_managerRole ){
        column = [{
            display : 'Name',
            name : 'fileName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Policy Type',
            name : 'policyType',
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
            display : 'Policy Status',
            name : 'policyStatus',
            width : 'auto',
            sortable : false,
            align : 'center'
        }*/, {
            display : '',
            name : 'policyViewHtmlButton',
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
            { name: '#policyListTable', bclass: 'PDF', onpress: printTable },
            { name: '#policyListTable', bclass: 'XLS', onpress: toExcel },
            { name: '#policyListTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#policyListTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#policyListTable', bclass: 'GROUPING', onpress: groupByView }
        ];
    }else if(user.role == legalRole || user.role == complianceRole || user.role == adminRole){
        column = [{
            display : 'Name',
            name : 'fileName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Policy Type',
            name : 'policyType',
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
        },{
            display : '',
            name : 'policyViewHtmlButton',
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
            name : '#policyListTable',bclass : 'ADD' },
            { name: '#policyListTable', bclass: 'PDF', onpress: printTable },
            { name: '#policyListTable', bclass: 'XLS', onpress: toExcel },
            { name: '#policyListTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#policyListTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#policyListTable', bclass: 'GROUPING', onpress: groupByView }
        ];
    }

    $("#policyListTable").flexigrid({
        url : 'getJASONforPolicyList.html?'
            +'tableName=policyListTable'
            +'&fromSearch=1',
        dataType : 'json',
        colModel : column,
        buttons : buttons,
        searchitems : [
          {
            display : 'Name',
            name : 'fileName',
            isdefault : true
        },{
            display : 'Policy Type',
            name : 'policyType',
            isdefault : true
        },{
            display : 'Uploaded',
            name : 'author',
            isdefault : true
        },{
            display : 'Date',
            name : 'entryDate',
            isdefault : true
        } ],
        sortname : "fileName",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300,
        onSuccess: function () {
            addGrid($('#policyListTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $(document).on('click', '.ADD', function() {
        $.prompt('Are you sure to add a new policy?', {
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
//    downLoadTraining();

    $('#docName,#author,#entryTime').keypress(function(e){
        if(e.which == 13){//Enter key pressed
            $('#policySearchSubmitButton').click();//Trigger search button click event
        }

    });

    $("#policySearchSubmitButton").click(function(){
        var newUrl = 'getJASONforPolicyList.html?'
            +'documentName='+$("#docName").val()
            +'&author='+$("#author").val()
            +'&entryTime='+$("#entryTime").val()
            +'&policyType='+$("#pType").val()
            +'&tableName=policyListTable'
            +'&fromSearch=1';
        $("#policyListTable").flexOptions({url: newUrl,newp: 1});
        $("#policyListTable").flexReload();
    });
    $("#entryTime").datepicker();
    $("#implementedDate").datepicker({ dateFormat: "mm-dd-yy"}).datepicker("setDate",new Date());
    $("#entryTime").val("");

    $('#docNameFortraining,#authorFortraining,#entryTimeFortraining').keypress(function(e){
        if(e.which == 13){//Enter key pressed
            $('#trainingSearchSubmitButton').click();//Trigger search button click event
        }

    });

    $(document).on('click', 'userEditBtn', function() {
        $('#modalOpenLinkForUser').trigger('click');
      /*  $("#fullname").val("");
        $("#password").val("");
        $("#passwordConf").val("");
        $("#email").val("");
        $("#activeUser").prop('checked', false);*/
    });


});

$(document).on('click',"#policyConfirmButton",function(){
     var policyId = $("#policyIdStr").val();
        $('#policyConfirmationModalDiv').css('opacity','0.8');
        $("#loadingImageForPolicyConfirm").show();
        $.ajax({
            url : "../policy/underStandPolicy.html?policyId="+policyId,
            type : 'GET',
            async: false,
            data : {},
            success: function(data){
                $('#policyConfirmationModalDiv').modal('toggle');
                $('#policyConfirmationModalDiv').css('opacity','1.0');
                $("#loadingImageForPolicyConfirm").hide();
                showMessageByForce("#policyListPage","alert-success","Policy Confirmed Successfully.");
                $("#policyListTable").flexReload();

            }
        });
    });

   $(document).on('click',"#confirmPolicyCheckbox",function(){
        var checked = $("#confirmPolicyCheckbox").prop("checked");
        if(checked == true){
            $("#policyConfirmButton").show();
        }else{
            $("#policyConfirmButton").hide();
        }
    });

    $(document).on('click', '#policyDownloadButtonHtml', function() {
        var policyId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var postUrl = "../policy/policyShow.html?policyId="+policyId;
        window.location = postUrl;
        $("#policyListTable").flexOptions({newp: 1,query:''}).flexReload();
    });



function removeSelectionAndSelectCurrentMenu(){
       $("#"+$("#mainMenuSelectionTxtId").val()).addClass('active');
}
function setPostAction(){
    $(document).on('click', '#uploadPolicy', function() {
        var oMyForm = new FormData();
        //alert("file==="+fileName+" file size=="+fileSize);
        oMyForm.append("file", $("#inputFile")[0].files[0]);
        oMyForm.append("documentName",$("#documentName").val());
        oMyForm.append("policyType",$("#policyType").val());
        oMyForm.append("implementedDate",$("#implementedDate").val());
        oMyForm.append("audiance",$("#audiance").val());
//        $('#uploadNewPolicy').modal('toggle');
        $('#uploadNewPolicy').css('opacity','0.8');
        $("#loadingImage").show();
        $.ajax({
            url: './uploadPolicy.html',
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function(data){
                $('#uploadNewPolicy').modal('toggle');
                $('#uploadNewPolicy').css('opacity','1.0');
                $("#loadingImage").hide();
                showMessageByForce("#policyListPage","alert-success","Policy Saved ");
                $("#policyListTable").flexReload();

            }
        });
    });
}

function setDeleteAction(){
    $(document).on('click', '#policyDeleteButtonHtml', function() {
        var policyId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "deletePolicy.html?policyId=" + policyId;
        $.prompt('Are you sure to delete the policy?', {
            title: "",
            buttons: { "Yes": true, "No": false },
            submit: function(e,v,m,f){
                if (v) {
                    $.ajax({
                        type:'get',
                        url:getUrl,
                        async:false,
                        success:function (data) {
                            showMessageByForce("#policyListPage","alert-error","Policy Deleted ");
                            $("#policyListTable").flexReload();
                        }
                    });
                }
            }
        });


    });
}


function setPolicyViewAction(){
    $(document).on('click', '#policyViewButtonHtml', function() {
        var policyId = $(this).parent().parent().parent().children(":last").children(":last").text();
        $("#modalOpenForPolicyConfirmation").click();
        $("#policyIdStr").val(policyId);
    });
}

