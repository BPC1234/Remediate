
var projectId=0;
$(document).ready(function () {

    $("#reactiveProjectListTable").flexigrid({
        url : 'getJASONforReactiveProjectList.html',
        dataType : 'json',
        colModel : [ {
            display : 'Project Name',
            name : 'projectName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Region',
            name : 'regionName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Total Amount',
            name : 'amount',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Payment Date',
            name : 'paymentDateStr',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : 'Transaction Type',
            name : 'transactionType',
            width : 'auto',
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
            display : 'Region',
            name : 'regionName'
        }, {
            display : 'Project Name',
            name : 'projectName',
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
        onSuccess: function () {
            addGrid($('#reactiveProjectListTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });
    $("#reactiveProjectListAdminTable").flexigrid({
        url : 'getJASONforReactiveProjectAdminList.html',
        dataType : 'json',
        colModel : [ {
            display : 'Project Name',
            name : 'projectName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Region',
            name : 'regionName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Total Amount',
            name : 'amount',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Payment Date',
            name : 'paymentDateStr',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : 'Transaction Type',
            name : 'transactionType',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'reactiveProjectEditHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'reactiveProjectDeleteHtmlButton',
            width : 'auto',
            sortable : false,
            align : 'center'
        },{
            display : 'Backend data',
            name : 'id',
            width : 1,
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
            display : 'Region',
            name : 'regionName'
        }, {
            display : 'Project Name',
            name : 'projectName',
            isdefault : true
        } ],
        sortname : "regionName",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300 ,
        onSuccess: function () {
            addGrid($('#reactiveProjectListAdminTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $(".addControl").before('<a href="#addReactiveProject" role="button" class="add addControlIcon" data-toggle="modal">Add</a>');
    $(".addControl").remove();

    $(document).on('click', '#reactiveProjectListTable tr', function(evt) {
        window.location = "../reactive/reactiveSummaryView.html?reactiveProjectId=" + $(this).children(":last").text().trim();
    });


    $(document).on('click', '.addControlIcon', function() {
        $("#projectName").val("");
        $("#amount").val("");
        $("#datepickerField").val("");


    });


    $(document).on('click', '#saveReactiveProject', function() {

        var getUrl = "../reactive/addReactiveProject.html?projectId=" + projectId;
        $.ajax({
            type:'post',
            url:getUrl,
            data: $("#projectForm").serialize(),
            async:false,
            success:function (data) {
                $('#addReactiveProject').modal('toggle');
                $("#reactiveProjectListAdminTable").flexReload();
                projectId=0;
            }

        });
    });


    $(document).on('click', '.projectEditButtonCss', function() {

        projectId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "getReactiveProject.html?projectId=" + projectId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#projectName").val($(data).attr("projectName"));
                $("#amount").val($(data).attr("amount"));
                var paymentDate =  $(data).attr("paymentDate");
                var myDate = new Date('MM-dd-YY',1000*paymentDate);
                alert(myDate);   alert(paymentDate);
                $("#datepickerField").val($(data).attr("paymentDate"));
            }
        });
    });


    $(document).on('click', '#reactiveProjectDeleteButton', function() {
        var projectId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "reactiveProjectDelete.html?projectId=" + projectId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#reactiveProjectListAdminTable").flexReload();
            }
        });
    });

});