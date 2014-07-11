var noColumn = 5;
var td = "";
$(document)
		.ready(
				function() {
					
					 var counter = 1;
			            var counterOfRev = 1;
			            var revNo = 1;
			            var expNo = 1;
			            var itemNo = 1;
			            var currAssNo = 1;
			            var fixAssNo = 1;
			            var othAssNo = 1;
			            var currLibNo = 1;
			            var fixLibNo = 1;
			            var othLibNo = 1;


					$(".showWC" ).hide();
					$('#historyId').off('click');
					$('#historyId').click(function() {
						window.location = "./reputationalHistory.html";

					});

					/*$('#repuHisTable').dataTable({
						"sDom" : 'T<"clear">lfrtip'
					});

				*//*	$('#existingReputationalReview').dataTable({
						"sDom" : 'T<"clear">lfrtip'
					});
*//*
					$('#frVendorTable').dataTable({
						"sDom" : 'T<"clear">lfrtip'
					});*/

					$('#repuHisTable_filter').hide();

					$("#reviewFromDate").datepicker();
					$("#reviewToDate").datepicker();

					if ($("#lastReviewedFrom").length > 0) {
						$("#lastReviewedFrom").datepicker();
					}
					if ($("#lastReviewedTo").length > 0) {
						$("#lastReviewedTo").datepicker();
					}

					/*$("#newReputationalReviewSubmit").click(function() {
						window.location = "./reputationalFormProcess.html";

					});*/

					$("#financialReviewBtn").click(function() {
						window.location = "./financialReviewSummary.html";
					});
					
					/* Adding all Cancel Action */

					$("#newReputationalReviewCancel").click(function() {
						/*window.location = "./newReputationalForm.html";*/
                        window.location = "./reputationalFormProcess.html";

					});
					$("#reputationalReviewSave").click(function() {
						window.location = "./newReputationalForm.html";
					});
					$("#reputationalReviewUpdate")
							.click(
									function() {
										loadDivForDueDilligance("./existingVendorsForReputaionalReview.html");
									});
					$("#newFinancialReviewCancel").click(function() {
						window.location = "./newFinancialProject.html";
					});
					$("#financialReviewProcessCancleBtn")
							.click(
									function() {
										loadDivForDueDilligance("./newFinancialReviewProcess.html");
									});
					$("#existingFincialReviewUpdate")
							.click(
									function() {
										loadDivForDueDilligance("./existingVendorsForFinancialReview.html");
									});

					$("#demo #existingReputationalReview a")
							.click(
									function() {
										loadDivForDueDilligance("./reputationalFormProcess.html");
									});

					/*adding all ancor's click action*/
					$("#worldComplinceDiv a").click(function(){
						$( ".hideWC" ).hide(1000);
						 $("#selectedWCText").text($(this).parent().prev().text());
						$( ".showWC" ).show(1000);
						return false;
					});
					
					$("#notaccordion")
							.addClass(
									"ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
							.find("h3")
							.addClass(
									"ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
							.hover(function() {
								$(this).toggleClass("ui-state-hover");
							})
							.prepend(
									'<span class="ui-icon ui-icon-triangle-1-e"></span>')
							.click(
									function() {
										$(this)
												.toggleClass(
														"ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
												.find(".ui-icon")
												.toggleClass(
														"ui-icon-triangle-1-e ui-icon-triangle-1-s")
												.end()
												.next()
												.toggleClass(
														"ui-accordion-content-active")
												.slideToggle();
										return false;
									})
							.next()
							.addClass(
									"ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
							.css("padding-bottom", "40px");

					$("#notaccordion").find("h3").click();

					/* accordion for reputational review */

					$("#reputationalAccordion")
							.addClass(
									"ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
							.find("h3")
							.addClass(
									"ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
							.hover(function() {
								$(this).toggleClass("ui-state-hover");
							})
							.prepend(
									'<span class="ui-icon ui-icon-triangle-1-e"></span>')
							.click(
									function() {
										$(this)
												.toggleClass(
														"ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
												.find(".ui-icon")
												.toggleClass(
														"ui-icon-triangle-1-e ui-icon-triangle-1-s")
												.end()
												.next()
												.toggleClass(
														"ui-accordion-content-active")
												.slideToggle();
										return false;
									})
							.next()
							.addClass(
									"ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
							.css("padding-bottom", "40px");

					$("#reputationalAccordion").find("h3").click();

					/* accordion for financial review */

					$("#financialAccordion")
							.addClass(
									"ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
							.find("h3")
							.addClass(
									"ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
							.hover(function() {
								$(this).toggleClass("ui-state-hover");
							})
							.prepend(
									'<span class="ui-icon ui-icon-triangle-1-e"></span>')
							.click(
									function() {
										$(this)
												.toggleClass(
														"ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
												.find(".ui-icon")
												.toggleClass(
														"ui-icon-triangle-1-e ui-icon-triangle-1-s")
												.end()
												.next()
												.toggleClass(
														"ui-accordion-content-active")
												.slideToggle();
										return false;
									})
							.next()
							.addClass(
									"ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
							.css("padding-bottom", "40px");

					$("#financialAccordion").find("h3").click();

					$(".DTTT_container").remove();
					$('#existingReputationalReview_filter').remove();
					/* $('#existingReputationalReview th').removeClass(); */
					
//					edited by amjad 
					
					 $("#addYear").click(function(e) {

		               
		                if(counter>=noColumn){
		                    $.prompt("Only 5 years allow");
		                    return false;
		                }  
		                counter++; 
		                
		                $("#addYearBtn").parent('tr').prev().children('td:last-child').after('<td style="text-align:right;" class="col'+counter+'"><input type="button" id ="yearNo'+counter+'" class ="removeDataOfYear"/></td>');
		                $("#addYearBtn").before('<td class="yearAdd" class="col'+counter+'"><input type="text" id ="selectedYear'+counter+'" value ="" placeholder ="Year" class="year"  size="10"/></td>');
		                $("#addMoreBtn").before('<td style = "text-align:right;" class="col'+counter+'"><input type="text" id ="amount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addMoreFixBtn").before('<td style = "text-align:right;" class="col'+counter+'"><input type="text" id ="fixedAmount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addMoreOtherBtn").before('<td style = "text-align:right;"class="col'+counter+'"><input type="text" id ="otherAmount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $('#addMoreBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');
		                $('#addMoreFixBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');
		                $('#addMoreOtherBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');
		                
		                $('#addMoreBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                $('#addMoreFixBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalFixedAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                $('#addMoreOtherBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalOtherAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                
		                $("#netTotalAmount1").parent('tr').children(':last-child').after('<td id="netTotalAmount'+counter+'" class="amount col'+counter+'">$0</td>');
		                $("#totalLiabilities1").parent('tr').children(':last-child').after('<td id="totalLiabilities'+counter+'" class="amount col'+counter+'">$0</td>');
		                
		                $("#addLibMoreBtn").before('<td style = "text-align:right;"  class="col'+counter+'"><input type="text" id ="libAmount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addLibMoreFixBtn").before('<td style = "text-align:right;" class="col'+counter+'"><input type="text" id ="libFixedAmount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addLibMoreOtherBtn").before('<td style = "text-align:right;" class="col'+counter+'"><input type="text" id ="libOtherAmount'+counter+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		            
		                $('#addLibMoreBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalLibAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                $('#addLibMoreFixBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalLibFixedAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                $('#addLibMoreOtherBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id="totalLibOtherAmount'+counter+'" class="rightAlign col'+counter+'">$0</td>');
		                
		                
		                
		                $('#addLibMoreBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');
		                $('#addLibMoreFixBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');
		                $('#addLibMoreOtherBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counter+'" ><hr></td>');


                         $('#addLibYearBtn').before('<td class="yearAdd"  class="col'+counter+'"></td>');
					 });
		       
		            $("#addMore").click(function(e) {
		            	getTotalCurrentAssets("amount","totalAmount");
		            	getTotalAssets("amount","netTotalAmount");
		                assetName = $("#assetName").val();
		                if(isEmptyAmount('amount') > 0) {
		                	getAmount("amount", currAssNo);
		                	$("#assetLevel").before('<tr><td></td><td class="textInput"> '+assetName+'</td>'+td+'</tr>');
		                	currAssNo++;
		                }
		                
		               
		                $("#assetName").val("");
		                
		                 
		               
		            });
		            
		            $(document).on('click', '.removeBtn', function(e) {
			           	var id = $(this).attr("id");
			           	doMinusAsset(id.substring(0,id.length - 1));
			           	//alert($("#"+id).val());
			           	$("#"+id).parent("td").parent("tr").remove();
		            });
		            
		            $(document).on('click', '.removeDataOfYear', function(e) {
			           	var id = $(this).attr("id");
                        $('#selectedYear'+id.substring(id.length, id.length-1)).parent('td').remove();
                        $('#selectedIncYear'+id.substring(id.length, id.length-1)).parent('td').remove();
                        id = id.substring(id.length-1, id.length);

                        deleteDataOfColumn(id);
			           	noColumn++;
			           	
		            });
		            
		            $("#addMoreFix").click(function(e) {
		            	getTotalCurrentAssets("fixedAmount","totalFixedAmount");
		            	getTotalAssets("fixedAmount","netTotalAmount");
		                assetName = $("#fixAssetName").val();
		                if(isEmptyAmount('fixedAmount') > 0) {
		                	getAmount("fixedAmount", fixAssNo);
		                	$("#fixAssetLevel").before('<tr><td></td><td class="textInput" > '+assetName+'</td>'+td+'</tr>');
		                	fixAssNo++;
		                }
		                 $("#fixAssetName").val("");
		                 
		            });
		            $("#addMoreOther").click(function(e) {
		            	getTotalCurrentAssets("otherAmount","totalOtherAmount");
		            	getTotalAssets("otherAmount","netTotalAmount");
		                assetName = $("#otherAssetName").val();
		                if(isEmptyAmount('otherAmount') > 0) {
		                	getAmount("otherAmount", othAssNo);
		                	$("#otherAssetLevel").before('<tr><td></td><td class="textInput" > '+assetName+'</td>'+td+'</tr>');
		                	othAssNo++;
		                }
		                 $("#otherAssetName").val("");
		                 
		            });
		           
		/*liabilities block start*/
		   
		            $("#addLibMore").click(function(e) {
		            	getTotalCurrentAssets("libAmount","totalLibAmount");
		            	getTotalAssets("libAmount","totalLiabilities");
		                libName = $("#libName").val();
		                if(isEmptyAmount('libAmount') > 0) {
		                	getAmount("libAmount", currLibNo);
		                	$("#libLevel").before('<tr><td></td><td class="textInput" > '+libName+'</td>'+td+'</tr>');
		                	
		                	currLibNo++;
		                }
		                $("#libName").val("");
		                
		               
		            });
		            $("#addLibMoreFix").click(function(e) {
		            	getTotalCurrentAssets("libFixedAmount","totalLibFixedAmount");
		            	getTotalAssets("libFixedAmount","totalLiabilities");
		                libName = $("#fixLibName").val();
		                if(isEmptyAmount('libFixedAmount') > 0) {
		                	getAmount("libFixedAmount", fixLibNo);
		                	$("#fixLibLevel").before('<tr><td></td><td class="textInput" > '+libName+'</td>'+td+'</tr>');
		                	fixLibNo++;
		                }
		                $("#fixLibName").val("");
		                
		            });
		            $("#addLibMoreOther").click(function(e) {
		            	getTotalCurrentAssets("libOtherAmount","totalLibOtherAmount");
		            	getTotalAssets("libOtherAmount","totalLiabilities");
		                libName = $("#otherLibName").val();
		                if(isEmptyAmount('libOtherAmount') > 0) {
		                	getAmount("libOtherAmount", othLibNo);
		                	$("#otherLibLevel").before('<tr><td></td><td class="textInput" > '+libName+'</td>'+td+'</tr>');
		                	othLibNo++;
		                }
		                $("#otherLibName").val("");
		                
		            });
		            
		       
		/*liabilities block end*/
		           
		/*income statement start*/
		           
		            /*revenue block start*/
		            $("#addIncYear").click(function(e) {
//		                $("#selectedIncYear"+counterOfRev).prop("disabled", true);
		               
		                if(counterOfRev>=noColumn){
		                        $.prompt("Only 5 years allow");
		                        return false;
		                 }  
		                counterOfRev++; 
		                $("#addIncYearBtn").parent('tr').prev().children('td:last-child').after('<td style="text-align:right;" class="col'+counterOfRev+'"><input type="button" id ="yearNo'+counterOfRev+'" class ="removeDataOfYear"/></td>');
		                $("#addIncYearBtn").before('<td class="yearAdd" class="col'+counterOfRev+'"><input type="text" id ="selectedIncYear'+counterOfRev+'" value ="" placeholder ="Year" class="year"  size="10"/></td>');
		                $("#addIncBtn").before('<td style = "text-align:right;" class="col'+counterOfRev+'"><input type="text" id ="incAmount'+counterOfRev+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addExpMoreBtn").before('<td style = "text-align:right;" class="col'+counterOfRev+'"><input type="text" id ="expAmount'+counterOfRev+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                $("#addLineItemBtn").before('<td style = "text-align:right;" class="col'+counterOfRev+'"><input type="text" id ="lineItemAmount'+counterOfRev+'" value ="" placeholder ="Amount" class ="amount"  size="10"/></td>');
		                
		                $('#addIncBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counterOfRev+'"><hr></td>');
		                $('#addExpMoreBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counterOfRev+'" ><hr></td>');
		                $('#addLineItemBtn').parent('tr').next('tr').children(':last-child').after('<td  class = "rightAlign col'+counterOfRev+'" ><hr></td>');
		                
		                $('#addIncBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id = "totalIncAmount'+counterOfRev+'"  class = "rightAlign col'+counterOfRev+'">$0</td');
		                $('#addExpMoreBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id = "totalExpAmount'+counterOfRev+'"  class = "rightAlign col'+counterOfRev+'">$0</td');
		                $('#addLineItemBtn').parent('tr').next('tr').next('tr').children(':last-child').after('<td id = "totalLineItemAmount'+counterOfRev+'"  class = "rightAlign col'+counterOfRev+'">$0</td');
		            });
		            $("#addIncMore").click(function(e) {
		            	
		            	getTotalCurrentAssets("incAmount","totalIncAmount");
		                incName = $("#incName").val();
		                if(isEmptyAmount('incAmount') > 0) { 
		                	getAmount("incAmount", revNo);
		                	$("#incLevel").before('<tr><td></td><td id="inc" > '+incName+'</td>'+td+'</tr>');
		                	revNo++;
		                }
		                
		                $("#incName").val("");
		                
		            });
		            /*revenue block end*/
		           
		            $("#addExpMore").click(function(e) {
		            	getTotalCurrentAssets("expAmount","totalExpAmount");
		                expName = $("#expName").val();
		                if(isEmptyAmount('expAmount') > 0) { 
		                	getAmount("expAmount", expNo);
		                	$("#expLevel").before('<tr><td></td><td class="textInput" > '+expName+'</td>'+td+'</tr>');
		                	expNo++;
		                }
		                $("#expName").val("");
		                
		               
		            });
		            /*expense block end*/
		           
		       
		            $("#addLineItemMore").click(function(e) {
		            	getTotalCurrentAssets("lineItemAmount","totalLineItemAmount");
		                lineItemName = $("#lineItemName").val();
		                if(isEmptyAmount('lineItemAmount') > 0) { 
		                	getAmount("lineItemAmount", itemNo);
		                	$("#lineItemLevel").before('<tr><td></td><td class="textInput" > '+lineItemName+'</td>'+td+'</tr>');
		                	itemNo++;
		                }
		                
		                $("#lineItemName").val("");
		                
		               
		            });
		      
		            $('#sheetDiv').hide();
		            $('#incomeSt').hide();
		            $('#fileType').change(function() {
		            	$('#sheetDiv').hide();
				        $('#incomeSt').hide();
		            	if($("#fileType").val() == "balanceSt") {
		            		$('#sheetDiv').show();
		            	}
		            	if($("#fileType").val() == "incomeSt") {
		            		$('#incomeSt').show();
		            	}
		            });
		            
		            $('#searchVenBtn').click( function() {
		            	name = $('#searchName').val();
		            	zip = $('#searchZip').val();
		            	countryCode = $('#countryCode').val();
		            	getVendorListInAjax(name, zip, countryCode,"existingVendorsList.html");
		            });

                    $('#searcVendorForFinancialReview').click( function() {
                        name = $('#searchName').val();
                        zip = $('#searchZip').val();
                        countryCode = $('#countryCode').val();
                        getVendorListInAjax('a', '2', '11','existingVendorsListForFinancialReview.html');
                    });

                    $("#ratioAnalysisId").click(function(){
                        setTotalAmountsInRatioSheet();
                        calculateDebtToEquityRatios();
                        calculateDebtToAssetRatios();
                        calculateNetIncome();
                        calculateNetProfitMargin();
                    });

                    $("#ratioAnalysisPrint").click(function(){

                    $("#ratioAnalysisPrintDiv #innerPrintDiv ").append($("#printHeadingDiv"));
                        $("#ratioAnalysisPrintDiv #innerPrintDiv #printHeadingDiv").css("width","400px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv #printHeadingDiv").css("margin-top","40px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv #printHeadingDiv").css("margin-left","50px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv #printHeadingDiv").css("margin-bottom","10px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv #printHeadingDiv table tbody tr td").css("font-size","11px");

                    $("#ratioAnalysisPrintDiv #innerPrintDiv ").append($(".ratioAnalysisFirstInnerDiv"));
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisFirstInnerDiv").css("width","600px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisFirstInnerDiv").css("margin-top","120px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisFirstInnerDiv").css("margin-left","150px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisFirstInnerDiv table tbody tr td").css("font-size","10px");
                    $("#ratioAnalysisPrintDiv #innerPrintDiv ").append($(".ratioAnalysisSecondInnerDiv"));

                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisSecondInnerDiv").css("width","600px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisSecondInnerDiv").css("margin-top","40px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisSecondInnerDiv").css("margin-left","150px");
                        $("#ratioAnalysisPrintDiv #innerPrintDiv .ratioAnalysisSecondInnerDiv table tbody tr td").css("font-size","10px");
                        $("#ratioAnalysisPrintDiv").print();
                    });

                    $("#cashFlowPrint").click(function(){
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv #statmentHeaderId").append($("#cashFlowHeader").html());
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv").append($(".cashFlowTableWrapperDiv").html());
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv #statmentHeaderId").css("margin-top","10px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv #statmentHeaderId").css("margin-left","40px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv #statmentHeaderId").css("margin-bottom","50px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv #statmentHeaderId").css("font-size","10px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable").css("margin-left","30px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable").css("width","400px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable").css("font-size","10px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable").css("font-size","10px");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable").css("text-align","left");
                        $("#cashFlowPrintDiv #cashFlowPrintDivInnerPrintDiv .cashFlowTable tbody tr td:last-child").css("text-align","right");

                        $("#cashFlowPrintDiv").print();
                    });


        });

function getVendorListInAjax(name, zip, countryCode,url) {
	var contUrl = url+"?name="+ name+"&zip="+zip+"&countryCode="+countryCode;
	$.ajax({
		type: 'GET',
		url:contUrl,
		success: function(result) {
			$("#ajaxResponse").replaceWith(result);
            $('#existingReputationalReview').dataTable({
                "sDom" : 'T<"clear">lfrtip'
            });
            $('table#existingReputationalReview tbody td').mouseover(function(){
                $(this).parent().parent().children().each(function(){
                    $(this).children().each(function(){
                        if($(this).hasClass("tableHover"))
                            $(this).removeClass("tableHover");
                        $('table#existingReputationalReview tbody tr').removeClass('tableHover');

                    });
                });
                $(this).parent().children().each(function(){
                    $(this).addClass("tableHover");
                });
            });
            $('table#existingReputationalReview tbody td').click(function(){
                $(this).parent().parent().children().each(function(){
                    $(this).children().each(function(){
                        if($(this).hasClass("tableRowSelect"))
                            $(this).removeClass("tableRowSelect");
                        $('table#existingReputationalReview tbody tr').removeClass('tableRowSelect');

                    });
                });
                $(this).parent().children().each(function(){
                    $(this).addClass("tableRowSelect");
                });
                window.location="./reputationalFormProcess.html";

            });
            $('#existingFinancialReview').dataTable({
                "sDom" : 'T<"clear">lfrtip'
            });
            $('table#existingFinancialReview tbody td').mouseover(function(){
                $(this).parent().parent().children().each(function(){
                    $(this).children().each(function(){
                        if($(this).hasClass("tableHover"))
                            $(this).removeClass("tableHover");
                        $('table#existingFinancialReview tbody tr').removeClass('tableHover');

                    });
                });
                $(this).parent().children().each(function(){
                    $(this).addClass("tableHover");
                });
            });
        }
	});
}

function getAmount(amount, itemNo){
	td = "";
	var flag = 0;
    var curAssAmount;
    for(var i = 1; i <= noColumn; i++) {
        curAssAmount = $("#"+amount+i).val();
        if(typeof curAssAmount != 'undefined'  ) {
        	flag++;
        	if(curAssAmount != '') {
        		curAssAmount = '$'+curAssAmount;
        	}
            td+='<td id="'+amount+itemNo+i+'" class="rightAlign col'+i+'">'+curAssAmount+'</td>';
            $("#"+amount+i).val("");
        }   
    } 
    if(flag > 0) {
    	td+='<td style="color:blue;"><input type="button"  id="'+amount+itemNo+i+'"  class ="removeBtn"value=" " /></td>';
    }
    
}


function getTotalCurrentAssets(amountID, totalAmountId) {
	for( var yearNo = 1 ; yearNo <= noColumn; yearNo++) {
		var sum =0;
		var newAmount = 0;
		newAmount = $('#'+amountID+yearNo).val();
		
		if(typeof newAmount != 'undefined') {
			 sum = $('#'+totalAmountId+yearNo).text();
			 sum = sum.substring(1,sum.length);
			 if(sum != '') {
				 sum = parseInt(sum); 
			 }
			 if($.isNumeric(newAmount)){
				 sum +=parseInt(newAmount); 
			 }
			 $('#'+totalAmountId+yearNo).html('$' + sum);
		}
	} 

}


function doMinusAsset(amountID) {
	for( var yearNo = 1 ; yearNo <= noColumn; yearNo++) {
		var total =0;
		var newAmount = 0;
		newAmount = $('#'+amountID+yearNo).text();
		newAmount = newAmount.substring(1,newAmount.length);
		
		if(newAmount != '') {
			 totalAmountStr = $('#total'+amountID.charAt(0).toUpperCase()+amountID.substring(1, amountID.length-1) +yearNo).text();
			 totalAmountSubStr = totalAmountStr.substring(1,totalAmountStr.length);
			 if(totalAmountSubStr != '') {
				 total = parseInt(totalAmountSubStr); 
			 }
			 if($.isNumeric(newAmount)){
				 total -=parseInt(newAmount); 
			 }
			 
			 if((amountID.indexOf("lib")) == 0) {
				 doMinusFromNetValue(newAmount, "totalLiabilities"+yearNo);
			 }else {
				 doMinusFromNetValue(newAmount, "netTotalAmount" + yearNo);
			 }
			 
			 $('#total'+amountID.charAt(0).toUpperCase()+amountID.substring(1, amountID.length-1)+yearNo).html('$'+total);	 
		}
	} 
}
function isEmptyAmount(amountId){ 

	for( var yearNo = 1 ; yearNo <= noColumn; yearNo++) {
		if(typeof $("#"+amountId + yearNo).val() != 'undefined') {
			 if($("#"+amountId + yearNo).val() !=''){
				 return 1;
			 }
		}
	}
	return 0;
}

function getTotalAssets(assetId, totalAsssetId) {
	var sum = 0;
	var temp = 0;
	for( var yearNo = 1 ; yearNo <= noColumn; yearNo++) {
		if(typeof $("#"+assetId + yearNo).val() != 'undefined') {
			 
			 if($("#"+totalAsssetId + yearNo).text() !=''){
				 sum = $("#"+totalAsssetId + yearNo).text();
				 sum = sum.substring(1,sum.length);
				 sum = parseInt(sum);
			 }
			 if($.isNumeric($("#"+assetId + yearNo).val())){
				 temp  = $("#"+assetId + yearNo).val();
				 sum +=parseInt(temp); 
			 }
			 
			 $('#'+totalAsssetId+yearNo).html('$'+sum);
		}
	}
}

function doMinusFromNetValue(amount, netValueId) {
	console.log("netValueId: " + netValueId)   ;
    var sum =0;
	netValueStr = $('#'+netValueId).text();
	netValueSubStr = netValueStr.substring(1,netValueStr.length);
	sum = parseInt(netValueSubStr); 
	sum -=parseInt(amount); 
	$('#'+netValueId).html('$'+sum);
}
function deleteDataOfColumn(id) {
	$('.col'+id).remove();
}

function getNetAmount(totalFieldId){
   var tatalVal = 0;
    for( var yearNo = 1 ; yearNo <= noColumn; yearNo++) {
        if(typeof $("#"+totalFieldId + yearNo).text() != 'undefined' && $("#"+totalFieldId + yearNo).text() != '') {
            amountCar = $("#"+totalFieldId + yearNo).text();
            amountCar =  amountCar.substring(1,amountCar.length);
            if($.isNumeric(amountCar)){
                tatalVal += parseInt(amountCar) ;
                console.log("tatal amount: " + tatalVal);
            }
        }
    }
    return tatalVal;
}

function setTotalAmountsInRatioSheet() {
    setAmountOnRatioAnalysis(getNetAmount("netTotalAmount"), "total-assets");
    setAmountOnRatioAnalysis(getNetAmount("totalLibOtherAmount"), "total-equity");
    setAmountOnRatioAnalysis(getNetAmount("totalLibAmount") + getNetAmount("totalLibFixedAmount"), "total-debit");
    setAmountOnRatioAnalysis(getNetAmount("totalIncAmount"), "total-revenue");
    setAmountOnRatioAnalysis(getNetAmount("totalExpAmount"), "total-cost");

}

function setAmountOnRatioAnalysis(amount, fieldId) {
    $('#'+fieldId).html('$'+amount);
}

function calculateDebtToEquityRatios() {
    $('#debt').html($('#total-debit').html());
    $('#equity').html($('#total-equity').html());
    if($.isNumeric(removeDollarSign($('#total-debit').html()))){
        debt = parseInt(removeDollarSign($('#total-debit').html())) ;
        printInConsole("debt: " + debt);
    }
    if($.isNumeric(removeDollarSign($('#total-equity').html()))){
        equity = parseInt(removeDollarSign($('#total-equity').html())) ;
        printInConsole("equity: " + equity);
    }
    ratio = 0;
    if(debt > 0 && equity > 0) {
        ratio = debt/ equity;
    }
    $('#d-t-e').html(roundNumber((ratio*100),'2')+'%') ;
}

function calculateDebtToAssetRatios() {
    $('#tdebt').html($('#total-debit').html());
    $('#assets').html($('#total-assets').html());
    if($.isNumeric(removeDollarSign($('#total-debit').html()))){
        debt = parseInt(removeDollarSign($('#total-debit').html())) ;
        printInConsole("debt: " + debt);
    }
    if($.isNumeric(removeDollarSign($('#total-assets').html()))){
        assets = parseInt(removeDollarSign($('#total-assets').html())) ;
        printInConsole("assets: " + assets);
    }
    ratio = 0;
    if(debt > 0 && assets > 0) {
        ratio = debt/ assets;
    }
    $('#d-t-a').html(roundNumber((ratio*100),'2')+'%') ;
}

function calculateNetIncome() {
    var revenue=0;
    var cost=0
    $('#revenue').html($('#total-revenue').html());
    $('#cost').html($('#total-cost').html());
    if($.isNumeric(removeDollarSign($('#total-revenue').html()))){
        revenue = parseInt(removeDollarSign($('#total-revenue').html())) ;
        printInConsole("revenue: " + revenue);
    }
    if($.isNumeric(removeDollarSign($('#total-cost').html()))){
        cost = parseInt(removeDollarSign($('#total-cost').html())) ;
        printInConsole("cost: " + cost);
    }
    var  income = 0;
    income = revenue- cost;
    $('#tincome').html('$' + income) ;
}
function calculateNetProfitMargin() {
    var tincome=0;
    var revenue=0;
    $('#net-income').html($('#tincome').html());
    $('#trevenue').html($('#total-revenue').html());
    if($.isNumeric(removeDollarSign($('#trevenue').html()))){
        revenue = parseInt(removeDollarSign($('#trevenue').html())) ;
        printInConsole("revenue: " + revenue);
    }
    if($.isNumeric(removeDollarSign($('#tincome').html()))){
        tincome = parseInt(removeDollarSign($('#tincome').html())) ;
        printInConsole("tincome: " + tincome);
    }
    var  profitMargin = 0;
    if(tincome > 0 && revenue > 0) {
        profitMargin =tincome / revenue;
    }
    $('#n-p-m').html(roundNumber((profitMargin*100),'2')+'%') ;
}

function removeDollarSign(str){
    return str.substring(1,str.length)
}
function roundNumber(num,digitAfterDecimal){
    return (num.toString().indexOf(".") !== -1) ? num.toFixed(digitAfterDecimal) : num;
}

