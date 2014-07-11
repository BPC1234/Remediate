$(document)
		.ready(
				function() {


$("#transactionSearchTable").flexigrid({
                        url :'getJASONforTrxSearchDataList.html?trxId='+$("#transactionId").val()
                            +'&nameOfThirdParty='+$("#nameOfThirdParty").val()
                            +'&amount='+$("#amount").val()
                            +'&transactionType='+$("#transactionType").val()
                            +'&tableName=transactionSearchTable'
                            +'&freeText='+$("#freeText").val()
                            +'&fromSearch=1',
                        dataType : 'json',
                        colModel : [ {
                            display : 'Transaction Id',
                            name : 'trxIdStr',
                            width : 'auto',
                            sortable : true,
                            align : 'left'
                        },{
                            display : 'Amount',
                            name : 'trxAmountStr',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        }, {
                            display : 'Approver Name',
                            name : 'approver',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        }, {
                            display : 'Third party/customer/employee',
                            name : 'customerName',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        }/*,{
                            display : 'Employee Name',
                            name : 'employeeName',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        },{
                            display : 'Vendor Name',
                            name : 'vendorName',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        }*/, {
                            display : 'Transaction Date',
                            name : 'trxDateStr',
                            width : 'auto',
                            sortable : true,
                            align : 'center'
                        },{
                            display : 'Backend data',
                            name : 'trxIdStr',
                            width:1,
                            hide : true,
                            align : 'center'
                        },{
                            display : 'Backend data',
                            name : 'realTimeProjectIdStr',
                            width:1,
                            hide : true,
                            align : 'center'
                        }],
                        searchitems : [ {
                            display : 'Transaction Id',
                            name : 'trx.id'
                        }, {
                            display : 'Amount',
                            name : 'amount'
                        }, {
                            display : 'Approver Name',
                            name : 'approver'
                        }, {
                            display : 'Third party/customer/employee',
                            name : 'cml.entity_name'
                        }/*, {
                            display : 'Employee Name',
                            name : 'eml.entity_name'
                        }, {
                            display : 'Vendor Name',
                            name : 'vml.entity_name'
                        }*/, {
                            display : 'Transaction Date',
                            name : 'trx.transaction_date'
                        }],
                        buttons: [{ name: '#transactionSearchTable', bclass: 'PDF', onpress: printTable },
                            { name: '#transactionSearchTable', bclass: 'XLS', onpress: toExcel },
                            { name: '#transactionSearchTable', bclass: 'CSV', onpress: tableToCSV },
                            { name: '#transactionSearchTable', bclass: 'RESET', onpress: resetTableData },
                            { name: '#transactionSearchTable', bclass: 'GROUPING', onpress: groupByView }
                        ],
                        sortname : "trxIdStr",
                        sortorder : "asc",
                        usepager : true,
                        title : '',
                        useRp : true,
                        rp : 10,
                        showTableToggleBtn : true,
                        height:250 ,
                        onSuccess: function() {
                        addGrid($('#transactionSearchTable'), this);  //PATCH to get the grids to be responseive
                            $("#transactionSearchTable tbody").find('tr').each(function() {
                                var realTimeProjectId = $(this).children(":last").text().trim();
                                    if(realTimeProjectId == '')selectMultipleRows('#transactionSearchTable',this);
                            });

                        },
                        getGridClass:function(g) { //PATCH to get the grids to be responseive
                        this.g=g; //Is this the only way to expose the grid class ?
                        return g;
                        }
                    });
                    $("#transactionId,#nameOfThirdParty,#transactionDate,#freeText,#amount,#approver").keypress(function(e){
                        if(e.which == 13){//Enter key pressed
                            $('#trxSearchSubmitButton').click();//Trigger search button click event
                        }
                    });

                    $("#trxSearchSubmitButton").click(function(){
                        var newUrl = 'getJASONforTrxSearchDataList.html?trxId='+$("#transactionId").val()
                            +'&nameOfThirdParty='+$("#nameOfThirdParty").val()
                            +'&amount='+$("#amount").val()
                            +'&transactionDate='+$("#transactionDate").val()
                            +'&transactionType='+$("#transactionType").val()
                            +'&freeText='+$("#freeText").val()
                            +'&tableName=transactionSearchTable'
                            +'&approver='+$("#approver").val()
                            +'&fromSearch=1';
                        $("#transactionSearchTable").flexOptions({url: newUrl,newp: 1});
                        $("#transactionSearchTable").flexReload();
                    });
					$("#transactionDate").datepicker();
                    $("#transactionDate").val("")

                    $(document).on('click','#transactionSearchTable tbody tr',function(){
                        var realTimeProjectId = $(this).children(":last").text().trim();

                       if(realTimeProjectId != ''){
                           var trxId = $(this).children().eq(5).text().trim();
                           var postUrl = "./realTimeSummaryViewFromTxSearch.html?rtProjectId="+realTimeProjectId+"&trxId="+trxId;
                           $(document).ready(function() {
                               $.ajax({
                                   url : postUrl ,
                                   type : 'GET',
                                   async: false,
                                   data : {},
                                   success : function(data) {
                                       window.location = "./realTimeSummaryView.html?rtProjectId="+realTimeProjectId;
                                   }

                               });
                           });
                       }else{
                           $.prompt("No Realtime Project Found.");
                       }

                    });

                });

function selectMultipleRows(tableIdOrClass,thisObject) {
    var selectedRow='';
    $(tableIdOrClass+' tbody tr').each(function() {
        if($(this).hasClass("txSearchRowSelectColor")){
            selectedRow = $(this).attr('id');
        }
    });
    /*if(!$('#'+selectedRow).next('tr').hasClass('erow')){
        $('#'+selectedRow).addClass('erow');
    }*/
    $(tableIdOrClass+' tbody tr td').removeClass("sorted");
    $(thisObject).removeClass('erow');
    $(thisObject).removeClass('trSelected');
    $(thisObject).addClass('txSearchRowSelectColor');

}
