$(document).ready(function () {
    var column;
    var buttons;

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
    }, {
        display : 'Backend data',
        name : 'id',
        width : 1,
        hide : true,
        align : 'center'
    }];

    buttons = [
        { name: '#trainingReview', bclass: 'PDF', onpress: printTable },
        { name: '#trainingReview', bclass: 'XLS', onpress: toExcel },
        { name: '#trainingReview', bclass: 'CSV', onpress: tableToCSV },
        { name: '#trainingReview', bclass: 'RESET', onpress: resetTableData },
        { name: '#trainingReview', bclass: 'GROUPING', onpress: groupByView }
    ];


    $("#trainingReview").flexigrid({
        url : '../training/getJASONforTrainingList.html?'
            + 'tableName=trainingReview'
            + '&fromSearch=1',
        dataType : 'json',
        colModel : column,
        buttons :buttons,
        searchitems : [ {
            display : 'Name',
            name : 'documentName'
        }, {
            display : 'Type',
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
            addGrid($('#trainingReview'), this);  //PATCH to get the grids to be responseive
            $("#trainingReview tbody tr:first").click();
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });


    $(document).on('click', '#trainingReview tr', function(evt) {
        $("#content").css('opacity','0.6');
        $("#loading").css({top: ($('#content').innerHeight())/2, left:($('#content').innerWidth())/2, position:'absolute'});
        $("#loading").show();
        var trainingId = $(this).children(":last").text().trim();
        var postUrl = "../training/trainingPieChart.html?trainingId="+trainingId;
        $(document).ready(function() {
            $.ajax({
                url : postUrl ,
                type : 'GET',
                async: false,
                data : {},
                success : function(data) {
                    $("#loading").hide();
                    $("#content").css('opacity','1.0');
                    var date = new Date(data.entryTime);

                    $(".trainingPieChart table tr").eq(0).find('td').eq(1).text(data.documentName);
                    $(".trainingPieChart table tr").eq(1).find('td').eq(1).text(data.trainingType);
                    $(".trainingPieChart table tr").eq(2).find('td').eq(1).text(data.author);
                    $(".trainingPieChart table tr").eq(3).find('td').eq(1).text(date.getMonth()+'/'+date.getDate()+'/'+date.getFullYear());
                    $(".trainingPieChart table tr").eq(4).find('td').eq(1).text(data.totalEmployeeWithemail);
                    $(".trainingPieChart table tr").eq(5).find('td').eq(1).text(data.totalParticipantEmployee);
                    $(".trainingPieChart table tr").eq(6).find('td').eq(1).text(data.totalEmployeeNeedRetake);
                    $(".trainingPieChart table tr").eq(7).find('td').eq(1).text(data.totalOutstanding);

                    var totalParticipantEmployee = (data.totalParticipantEmployee*100)/data.totalEmployeeWithemail;
                    var totalEmployeeNeedRetake = (data.totalEmployeeNeedRetake*100)/data.totalEmployeeWithemail;
                    //var certified = totalParticipantEmployee - totalEmployeeNeedRetake;
                    var notParticipant =  ((data.totalEmployeeWithemail -data.totalParticipantEmployee)*100)/data.totalEmployeeWithemail;
                    $("#trainingPieChart").html('<div class="demoPieChart" id="trainingPieChart"></div>');
                    $("#trainingSurveyDiv").show();
                    $("#trainingPieChart").show();
                    $(".pieChartWrapperDiv").css("display","inline-block");
                    console.log("notParticipant:"+notParticipant+" totalParticipantEmployee:"+totalParticipantEmployee+" totalEmployeeNeedRetake:"+totalEmployeeNeedRetake)
                    var dataPie = [];
                    dataPie[0]={label:'Not Participated',data:notParticipant};
                    dataPie[1]={label:'Participated',data:totalParticipantEmployee};
                    dataPie[2]={label:'Retake Required', data:totalEmployeeNeedRetake};

                    $.plot('#trainingPieChart', dataPie, {
                        series: {
                            pie: {
                                show: true,
                                radius:0.9,
//                                innerRadius: 0.4,
                                label: {

                                    show: true,
                                    radius:7/8,
                                    formatter: labelFormatter,
                                    background: {
                                        opacity: 0.8
                                    }
                                }
                            }
                        },
                        legend: {
                            show: true
                        }
                    });
                    $("#trainingPieChart .legend").append("<div class='text-center' style='width:75%;font-weight: 500 !important;font-style: italic;'>Pie chart based on Employee information.</div>");
                    var table=  '<table class="infoTableForPieChart"><tr><td align="right">Total employees :</td>'
                        +'<td align="left">'+data.totalEmployee+'</td></tr><tr><td align="right">Notification :</td>'
                        +'<td align="left">'+data.totalEmployeeWithemail+'</td></tr><tr><td align="right">Participated :</td>'
                        +'<td align="left">'+data.totalParticipantEmployee+'</td></tr><tr><td align="right">Retake Required :</td>'
                        +'<td align="left">'+data.totalEmployeeNeedRetake+'</td></tr><tr><td align="right">Not Participated :</td>'
                        +'<td align="left">'+(data.totalEmployeeWithemail - (data.totalParticipantEmployee+data.totalEmployeeNeedRetake))+'</td></tr></table>';
                    $("#trainingPieChart .legend").append(table);
                }

            });
        });


    });



    function labelFormatter(label, series) {
        return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
    }
});