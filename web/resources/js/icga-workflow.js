$(document).ready(function () {
    $("#existingIcgaControlListTable").flexigrid({
        url :  'getJASONforExistingControlList.html?'
             + '&tableName=existingIcgaControlListTable'
             + '&controlIds='+$("#controlIds").val(),
        dataType : 'json',
        colModel : [ {
            display : 'Control',
            name : 'name',
            width : 'auto',
            sortable: true,
            align: 'left'
        },{
            display : 'Transaction Type',
            name : 'transactionType',
            width : 200,
            sortable: true,
            align: 'left'
        },
            {
                display: 'Total',
                name: 'totalUsed',
                width : 60,
                sortable: true,
                align: 'right'
            },
            {
                display: 'Backend Data',
                name: 'id',
                hide: true,
                width: 1,
                sortable: false,
                align: 'left'
            }],

        buttons: [
            { name: '#existingIcgaControlListTable', bclass: 'PDF', onpress: printTable },
            { name: '#existingIcgaControlListTable', bclass: 'XLS', onpress: toExcel },
            { name: '#existingIcgaControlListTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#existingIcgaControlListTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#existingIcgaControlListTable', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "projectName",
        sortorder: "asc",
        usepager: true,
        title: 'Existing Controls',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 180,
        onSuccess: function () {
            addGrid($('#existingIcgaControlListTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });


    $(document).on('click', '#existingIcgaControlListTable tr', function(evt) {
        $("#content").css('opacity','0.6');
        $("#loading").css({top: ($('#content').innerHeight())/2, left:($('#content').innerWidth())/2, position:'absolute'});
        $("#loading").show();
        var controlId = $(this).children(":last").text().trim();
        var postUrl = "./projectWiseControlCount.html?controlId="+controlId;
        $(document).ready(function() {
            $.ajax({
                url : postUrl ,
                type : 'GET',
                async: false,
                data : {},
                success : function(data) {

                    var source = $('<div>' + data + '</div>');
                    $('#projectWiseControlTableDiv').html(source.find('#projectWiseControlTableDiv').html());
                    $("#loading").hide();
                    $("#content").css('opacity','1.0');
                    setGridForProjectWiseControlList(controlId);
                }
            })

        });
//        window.location = "./newRiskAssessmentsummaryView.html?cont=1&proactiveProjectId=" + $(this).children(":last").text().trim();
    });

    $(document).on('click', '#projectWiseControlTable tr', function(evt) {
        var projectType = $(this).children(":last").text().trim();
        var projectId = $(this).children().eq(3).text().trim();
        var controlId = $("#controlId").val();
//        alert("project type="+projectType+" projectId="+projectId);
        if(projectType == '0'){
            window.location = "../riskasst/newRiskAssessmentsummaryView.html?proactiveProjectId="+projectId+"&controlIds="+controlId;
        }else if(projectType == '1'){
            window.location = "../reactive/reactiveSummaryView.html?reactiveProjectId="+projectId+"&controlIds="+controlId;
        }else if(projectType == '2'){
            window.location = "../realtime/realTimeSummaryView.html?rtProjectId="+projectId+"&controlIds="+controlId;
        }

    });
    $("#thirdPartyTable tr  td:not(:first-child), #generalLedgerTable tr td:not(:first-child), #customerTable tr td:not(:first-child)").click(function(event){
        var status = ($(this).parent().children(':first').find(".analyzedControlsCheckbox")).prop('checked');
        if(status == false){
            $($(this).parent().children(':first').find(".analyzedControlsCheckbox")).prop('checked', true);
        }else{
            $($(this).parent().children(':first').find(".analyzedControlsCheckbox")).prop('checked', false);
        }
    });

    $(document).on('click', '#analyzeByControlSubmitButton', function(evt) {

        var controlIds = '';
        $("#thirdPartyTable").find('tbody tr').each(function() {
            var status = ($(this).children('td:first').find(".analyzedControlsCheckbox")).prop('checked');
           if(status == true){
               controlIds = controlIds+"," + ($(this).children('td:first').find(".analyzedControlsCheckbox")).attr("name")

           }
        });
        $("#generalLedgerTable").find('tbody tr').each(function() {
            var status = ($(this).children('td:first').find(".analyzedControlsCheckbox")).prop('checked');
            if(status == true){
                controlIds = controlIds+"," + ($(this).children('td:first').find(".analyzedControlsCheckbox")).attr("name")

            }
        });

        $("#customerTable").find('tbody tr').each(function() {
            var status = ($(this).children('td:first').find(".analyzedControlsCheckbox")).prop('checked');
            if(status == true){
                controlIds = controlIds+"," + ($(this).children('td:first').find(".analyzedControlsCheckbox")).attr("name")

            }
        });


        controlIds = controlIds.substr(1,controlIds.length);
        if(controlIds.length>0){
            window.location = "./existingCtrlGapAnalysis.html?controlIds="+controlIds;
        }else{
            $.prompt("Please select any control.");
        }
    });
    setGridForProjectWiseControlList($("#controlId").val());
});

function setGridForProjectWiseControlList(controlId){
    $("#projectWiseControlTable").flexigrid({
        url : 'getJASONforProjectWiseControlList.html?controlId='+controlId
              +'&tableName=projectWiseControlTable',
        dataType : 'json',
        colModel : [ {
            display : 'Assignment No.',
            name : 'projectName',
            width : 285,
            sortable: true,
            align: 'left'
          },  {
                display: 'Transaction ID',
                name: 'transactionIds',
                width : 800,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Total',
                name: 'total',
                width : 100,
                sortable: true,
                align: 'left'
            },
            {
                display: 'Project Id',
                name: 'projectId',
                hide: true,
                width: 0,
                sortable: false,
                align: 'left'
            },
            {
                display: 'Project Type',
                name: 'projectType',
                hide: true,
                width: 0,
                sortable: false,
                align: 'left'
            }],

        searchitems: [
            {
                display: 'Project Name',
                name: 'projectName'
            }
        ],
        buttons: [
            { name: '#projectWiseControlTable', bclass: 'PDF', onpress: printTable },
            { name: '#projectWiseControlTable', bclass: 'XLS', onpress: toExcel },
            { name: '#projectWiseControlTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#projectWiseControlTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#projectWiseControlTable', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "projectName",
        sortorder: "asc",
        usepager: true,
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 160,
        onSuccess: function () {
            addGrid($('#projectWiseControlTable'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

}
