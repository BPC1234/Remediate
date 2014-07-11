var policy;
$(document).ready(function () {
    //      $('#discriptionId').multiselect();

    $("#policyReviewTable").flexigrid({
        url : '../policy/getJASONforPolicyList.html?'
            +'tableName=policyReviewTable'
            +'&fromSearch=1',
        dataType : 'json',
        colModel : [{
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
        }, {
            display : 'Backend data',
            name : 'id',
            width : 1,
            hide : true,
            align : 'center'
        }],
        buttons : [
            { name: '#policyReviewTable', bclass: 'PDF', onpress: printTable },
            { name: '#policyReviewTable', bclass: 'XLS', onpress: toExcel },
            { name: '#policyReviewTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#policyReviewTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#policyReviewTable', bclass: 'GROUPING', onpress: groupByView }
        ],
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
            addGrid($('#policyReviewTable'), this);  //PATCH to get the grids to be responseive
            $("#policyReviewTable tbody tr:first").click();
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });

    $(document).on('click', '#policyReviewTable tr', function(evt) {
        $("#content").css('opacity','0.6');
        $("#loading").css({top: ($('#content').innerHeight())/2, left:($('#content').innerWidth())/2, position:'absolute'});
        $("#loading").show();
        var policyId = $(this).children(":last").text().trim();
        var postUrl = "./makePieChartForPolicyReviewCertification.html?policyId="+policyId;
        $(document).ready(function() {
            $.ajax({
                url : postUrl ,
                type : 'GET',
                dataType: 'json',
                async: false,
                data : {},
                success : function(data) {
                    policy = data;
                    $("#loading").hide();
                    $("#content").css('opacity','1.0');
                    var date = new Date(data.entryTime);
                    $(".pieChartLeftPartDiv table tr").eq(0).find('td').eq(1).text(data.documentName);
                    $(".pieChartLeftPartDiv table tr").eq(1).find('td').eq(1).text(data.policyType);
                    $(".pieChartLeftPartDiv table tr").eq(2).find('td').eq(1).text(data.author);
                    $(".pieChartLeftPartDiv table tr").eq(3).find('td').eq(1).text((date.getMonth()+1)+'/'+date.getDate()+'/'+date.getFullYear());
                    var totalConfirmedPercent = 0;
                    var totalUnconfirmedPercent = 0;
                    var totalNotReviewed = 0;
                    console.log('totalConfirmedPercent='+totalConfirmedPercent+" totalUnconfirmedPercent="+totalUnconfirmedPercent)
                    var labels = ['Not Reviewed','Confirmed','Not Confirmed'];
                    var values;
                    $(".pieChartWrapperDiv").show();
                    $(".pieChartWrapperDiv").css("display","inline-block");
                    if(data.audianceCode == 0){
                        $("#policyPieChart").show();
                        $("#policyPieChartForVendor").show();
                        drawPieChartForEmployee(data,labels);
                        drawPieChartForVendor(data,labels);
                    }else if(data.audianceCode == 1){
                        hideAll();
                        $("#policyPieChart").show();
                        drawPieChartForEmployee(data,labels);
                    }else if(data.audianceCode == 2){
                        hideAll();
                        $("#policyPieChartForVendor").show();
                        drawPieChartForVendor(data,labels);
                    }



                    //$(".base").css("width",'300');
                    //$(".overlay").css("width",'300px');
                }

        });
//        window.location = "./newRiskAssessmentsummaryView.html?cont=1&proactiveProjectId=" + $(this).children(":last").text().trim();
    });


});
 $(document).on("click","#printPieChartButton",function(){
     window.location = "./pieChartPrint.html?policy="+JSON.stringify(policy);

     //printCanvas();
//     $("#pieChartWrapperDiv").print();
 });

});

function labelFormatter(label, series) {
    return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
}

function drawPieChart(divIdOrClass,values,labels,pieChartName){


    /*$(".pieChartLeftPartDiv table tr").eq(4).find('td').eq(1).text(data.totalEmployeeWithemail);
    $(".pieChartLeftPartDiv table tr").eq(5).find('td').eq(1).text(data.totalConfirmed);
    $(".pieChartLeftPartDiv table tr").eq(6).find('td').eq(1).text(data.totalUnconfirmed);
    $(".pieChartLeftPartDiv table tr").eq(7).find('td').eq(1).text(data.totalOutstanding);
*/
    var dataPie = [];
    var color=['#DE000F','#7D0096','#00A36A']
    for(var i=0; i<values.length; i++){
        dataPie[i]={label:labels[i],data:values[i],color:color[i]};
//        dataPie[1]={label:'Confirmed',data:totalConfirmedPercent};
//        dataPie[2]={label:'Not Confirmed',data:totalUnconfirmedPercent};
    }

    $.plot(divIdOrClass, dataPie, {
        series: {
            pie: {
                show: true,
                radius:0.9,
                innerRadius:.4,
//                tilt:.3,
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
        },  grid: {
            hoverable: true
        }
    });

    $(divIdOrClass+" .legend").append("<div class='text-center' style='width:75%;font-weight: 500 !important;font-style: italic;'>Pie chart based on "+pieChartName+" information.</div>");

}
function drawPieChartForEmployee(data,labels){
    totalConfirmedPercent = (data.totalConfirmedByEmployee*100)/data.totalEmployeeWithemail;
    totalUnconfirmedPercent =(data.totalUnconfirmedByEmployee*100)/data.totalEmployeeWithemail;
    totalNotReviewed = 100- (totalConfirmedPercent+totalUnconfirmedPercent);
    var values=[totalNotReviewed,totalConfirmedPercent,totalUnconfirmedPercent];
    drawPieChart("#policyPieChart",values,labels,"Employee");
    var table=  '<table class="infoTableForPieChart"><tr><td align="right">Total Employees :</td>'
        +'<td align="left">'+data.totalEmployee+'</td></tr><tr><td align="right">Notification :</td>'
        +'<td align="left">'+data.totalEmployeeWithemail+'</td></tr><tr><td align="right">Confirmed :</td>'
        +'<td align="left">'+data.totalConfirmedByEmployee+'</td></tr><tr><td align="right">Not Confirmed :</td>'
        +'<td align="left">'+data.totalUnconfirmedByEmployee+'</td></tr><tr><td align="right">Not Reviewed :</td>'
        +'<td align="left">'+(data.totalEmployeeWithemail-(data.totalConfirmedByEmployee+data.totalUnconfirmedByEmployee))+'</td></tr></table>';
    $("#policyPieChart .legend").append(table);
    $("#policyPieChart canvas:first").attr("id",'canvasEmployeePieChart');
}
function drawPieChartForVendor(data,labels){
    totalConfirmedPercent = (data.totalConfirmedByVendor*100)/data.totalVendorWithEmail;
    totalUnconfirmedPercent =(data.totalUnconfirmedByVendor*100)/data.totalVendorWithEmail;
    totalNotReviewed = 100- (totalConfirmedPercent+totalUnconfirmedPercent);
    var values=[totalNotReviewed,totalConfirmedPercent,totalUnconfirmedPercent];
    drawPieChart("#policyPieChartForVendor",values,labels,"Vendor");
    var table=  '<table class="infoTableForPieChart"><tr><td align="right">Total Vendors :</td>'
        +'<td align="left">'+data.totalVendor+'</td></tr><tr><td align="right">Notification :</td>'
        +'<td align="left">'+data.totalVendorWithEmail+'</td></tr><tr><td align="right">Confirmed :</td>'
        +'<td align="left">'+data.totalConfirmedByVendor+'</td></tr><tr><td align="right">Not Confirmed :</td>'
        +'<td align="left">'+data.totalUnconfirmedByVendor+'</td></tr><tr><td align="right">Not Reviewed :</td>'
        +'<td align="left">'+(data.totalVendorWithEmail - (data.totalConfirmedByVendor+data.totalUnconfirmedByVendor))+'</td></tr></table>';
    $("#policyPieChartForVendor .legend").append(table);
}
function hideAll(){
    $("#policyPieChart").hide();
    $("#policyPieChartForVendor").hide();
}

function PrintDiv() {
    var divToPrint = document.getElementById('pieChartWrapperDiv');
    console.log('Width:'+$("#pieChartWrapperDiv").innerWidth()+' height:'+$("#pieChartWrapperDiv").innerHeight());
    var popupWin = window.open('', '_blank', 'width='+$("#pieChartWrapperDiv").innerWidth()+',height='+600);
    popupWin.document.open();
    popupWin.document.write('<html><body onload="window.print()">' + $("pieChartWrapperDiv").innerHTML() + '</html>');
    popupWin.document.close();
    }

function printCanvas()
{
   // var dataUrl = $("#pieChartWrapperDiv canvas.base").toDataURL();
    var dataUrl = document.getElementById('canvasEmployeePieChart').toDataURL(); //attempt to save base64 string to server using this var
    var windowContent = '<!DOCTYPE html>';
    windowContent += '<html>'
    windowContent += '<head><title>Print canvas</title></head>';
    windowContent += '<body>'
    windowContent += '<img style="position: absolute;top:140px;left:40px" width=410 height=160 src="' + dataUrl + '">';
    windowContent += $("#pieChartWrapperDiv").html()
    windowContent += '</body>';
    windowContent += '</html>';
    //$("#transactionSearchBlockId").append(windowContent);
    var printWin = window.open('','','width=700,height=600');
    printWin.document.open();
    printWin.document.write(windowContent);
    printWin.document.close();
    printWin.focus();
    printWin.print();
    printWin.close();
}
