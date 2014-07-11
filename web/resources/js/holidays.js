var holidayId = 0;
$(document).ready(function () {
    //      $('#discriptionId').multiselect();
$("#holidayTable").flexigrid({
    url: 'holidayList.html?'
        + 'tableName=holidayTable',
    dataType: 'json',
    colModel: [
        {
            display: 'Description',
            name: 'description',
            width: 'auto',
            sortable: true,
            align: 'left'
        },
        {
            display: 'Date',
            name: 'dateStr',
            width: 150,
            sortable: true,
            align: 'left'
        },{
            display: '',
            name: 'editButtonHtml',
            width: 100,
            sortable: true,
            align: 'left'
        },{
            display: '',
            name: 'deleteButtonHtml',
            width: 100,
            sortable: true,
            align: 'left'
        },
        {
            display: 'Backend Data',
            name: 'id',
            width: 0,
            align: 'right',
            sortable: true,
            hide: true
        }
    ],
    searchitems: [
        {
            display: 'Description',
            name: 'description'
        },{
            display: 'Date',
            name: 'holiday_date'
        }
    ],
    buttons: [
        { name: '#holidayTable', bclass: 'ADD'},
        { name: '#holidayTable', bclass: 'PDF', onpress: printTable },
        { name: '#holidayTable', bclass: 'XLS', onpress: toExcel },
        { name: '#holidayTable', bclass: 'CSV', onpress: tableToCSV },
        { name: '#holidayTable', bclass: 'RESET', onpress: resetTableData },
        { name: '#holidayTable', bclass: 'GROUPING', onpress: groupByView }
    ],
    sortname: "description",
    sortorder: "asc",
    usepager: true,
    title: '',
    useRp: true,
    rp: 10,
    showTableToggleBtn: true,
    height: 335,
    onSuccess: function () {
        addGrid($('#holidayTable'), this);  //PATCH to get the grids to be responseive

    },
    getGridClass: function (g) { //PATCH to get the grids to be responseive
        this.g = g; //Is this the only way to expose the grid class ?
        return g;
    }
 });

/*
    $(".ADD").before('<a href="#addHoliday" role="button" class="add" data-toggle="modal">ADD</a>');
    $("span.ADD").remove();
*/


    $(document).on('click', '.ADD', function() {
        $('#modalOpenLink').trigger('click');

        $("#description").val("");


    });


    $(document).on('click', '#saveHoliday', function() {

        var getUrl = "addHoliday.html?id=" + holidayId;
        $.ajax({
            type:'post',
            url:getUrl,
            data: $("#projectForm").serialize(),
            async:false,
            success:function (data) {
                var saveOrUpdate ;
                if(holidayId > 0)
                    saveOrUpdate = 'Holiday Updated '
                else
                    saveOrUpdate = 'Holiday Saved '

                $('#addHoliday').modal('toggle');
                $("#holidayTable").flexReload();
                showMessageByForce("#holidayMainDiv","alert-success",saveOrUpdate);
                holidayId = 0;

            }

        });
    });

    $(document).on('click', '.holidayEditButtonCss', function() {

        holidayId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "getHoliday.html?holidayId=" + holidayId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#description").val($(data).attr("description"));
                var stringDate = $(data).attr("holidayDate");
                var date = new Date(stringDate)
                var day = date.getDate();
                var month = date.getMonth()+1;
                var year = date.getFullYear();
                $("#holidayDateFieldId").val(month+"/"+day+"/"+year);
            }
        });
    });

    $(document).on('click', '#holidayDeleteButtonHtml', function() {
        var holidayId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "holidayDelete.html?id=" + holidayId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#holidayTable").flexReload();
                showMessageByForce("#holidayMainDiv","alert-error","Holiday Deleted ");
            }
        });
    });

});