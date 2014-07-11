var isReputational = 0;
var isFinancial = 0;
var isNew = 0;
var isExixting = 0;

$(document).ready(
		function() {
			isReputational = reputationalVal;
			isFinancial = financialVal;
			isNew = newForm;
			isExisting = existingVal;
			//alert('isReputational  = ' + isReputational + " isFinancial="
			//		+ isFinancial + "isNew= " + isNew + " isExisting ="
			//		+ isExisting);
			callAjaxForDueDilliganceDiv();
			$("div#contentWrapper").removeAttr('id'); // remove background
														// image

			$("#leftTab li").click(function(e) {
				e.preventDefault();
				$("#leftTab li").removeClass("selected");
				$(this).addClass("selected");
				if ($(this).attr('id') == 'reputationalTab') {
					isReputational = 1;
					isFinancial = 0;
				} else if ($(this).attr('id') == 'financialTab') {
					isFinancial = 1;
					isReputational = 0;

				}
				callAjaxForDueDilliganceDiv();

			});

			$("#rightTab li").click(function(e) {
				e.preventDefault();
				$("#rightTab li").removeClass("selected");
				$(this).addClass("selected");

				if ($(this).attr('id') == 'newTab') {
					isNew = 1;
					isExixting = 0;
				} else if ($(this).attr('id') == 'existingTab') {
					isExixting = 1;
					isNew = 0;

				}
				callAjaxForDueDilliganceDiv();
			});
			
			/*accordion for reputational review*/
			
			$("#reputationalAccordion").addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
			.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
			.hover(function () { $(this).toggleClass("ui-state-hover"); })
				.prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>')
				.click(function () {
				    $(this)
						.toggleClass("ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
						.find(".ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").end()
						.next().toggleClass("ui-accordion-content-active").slideToggle();
				    return false;
				})
				.next()
				.addClass("ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
				.css("padding-bottom","11px");

			
		    $("#reputationalAccordion").find("h3").click();	
		    
		    /*accordion for financial review*/
			
		    $("#financialAccordion").addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
			.find("h3")
			.addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
			.hover(function () { $(this).toggleClass("ui-state-hover"); })
				.prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>')
				.click(function () {
				    $(this)
						.toggleClass("ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
						.find(".ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").end()
						.next().toggleClass("ui-accordion-content-active").slideToggle();
				    return false;
				})
				.next()
				.addClass("ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
				.css("padding-bottom","11px");

		    $("#financialAccordion").find("h3").click();	
		});

function callAjaxForDueDilliganceDiv() {
	if (isReputational == 1 && isNew == 1) {
		var extension = "./newReputationalForm.html";
		loadDivForDueDilligance(extension);
	} else if (isReputational == 1 && isExixting == 1) {
		var extension = "./existingVendorsForReputaionalReview.html";
		loadDivForDueDilligance(extension);
	} else if (isFinancial == 1 && isNew == 1) {
		var extension = "./newFinancialProject.html";
		loadDivForDueDilligance(extension);
	} else if (isFinancial == 1 && isExixting == 1) {
		var extension = "./existingVendorsForFinancialReview.html";
		loadDivForDueDilligance(extension);
	}

}

function loadDivForDueDilligance(extension) {
	$("#content").css('opacity', '0.6');
	$('#loading').css('left', ($('#content').innerWidth() / 2));
	$("#loading").show();
	$(document).ready(function() {
		$.ajax({
			url : extension,
			type : 'GET',
			async : false,
			data : {},
			success : function(data) {
				$('#replaceAjaxDiv').replaceWith(data);
				$("#loading").hide();
				$("#content").css('opacity', '1.0');
				
				$('#historyId').off('click');
				$('#historyId').click(function() {
					var ext = "./reputationalHistory.html";
					loadDivForDueDilligance(ext);
				
				});
				
				$('#repuHisTable').dataTable({
					"sDom" : 'T<"clear">lfrtip'
				});

				$('#existingReputationalReview').dataTable({
					"sDom" : 'T<"clear">lfrtip'
				});
				
				$('#frVendorTable').dataTable({
					"sDom" : 'T<"clear">lfrtip'
				});
				
				$('#repuHisTable_filter').hide();

				$("#reviewFromDate").datepicker();
				$("#reviewToDate").datepicker();

				if ($("#lastReviewedFrom").length > 0) {
			        $("#lastReviewedFrom").datepicker();
			    }
			    if ($("#lastReviewedTo").length > 0) {
			        $("#lastReviewedTo").datepicker();
			    }
			    
			    $("#newReputationalReviewSubmit").click(function() {
			    	var ext =  "./reputationalFormProcess.html";
					loadDivForDueDilligance(ext);
				});
			    
			    $("#newFinancialReviewSubmit").click(function() {
			    	var ext =  "./newFinancialReviewProcess.html"; 
					loadDivForDueDilligance(ext);
				});
			    
			    $("#financialReviewBtn").click(function() {
			    	var ext =  "./financialReviewSummary.html"; 
					loadDivForDueDilligance(ext);
				});
			    
			    /* Adding all Cancel Action */
			    
			    $("#newReputationalReviewCancel").click(function() {
					loadDivForDueDilligance("./newReputationalForm.html");
				});
			    $("#reputationalReviewSave").click(function() {
					loadDivForDueDilligance("./newReputationalForm.html");
				});
			    $("#reputationalReviewUpdate").click(function() {
					loadDivForDueDilligance("./existingVendorsForReputaionalReview.html");
				});
			    $("#newFinancialReviewCancel").click(function() {
					loadDivForDueDilligance("./newFinancialProject.html");
				});
			    $("#financialReviewProcessCancleBtn").click(function() {
					loadDivForDueDilligance("./newFinancialReviewProcess.html");
				});
			    $("#existingFincialReviewUpdate").click(function() {
					loadDivForDueDilligance("./existingVendorsForFinancialReview.html");
				});
			    
			    $("#demo #existingReputationalReview a").click(function() {
			    	loadDivForDueDilligance("./reputationalFormProcess.html");	
			    });
			    
			   /* $("#repuWrap #demo #repuHisTable a").click(function() {
			    	loadDivForDueDilligance("./reputationalFormProcess.html");
			    	
			    });*/
			    
			    
			    
			    

			    $("#notaccordion").addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
				.find("h3")
				.addClass("ui-accordion-header ui-helper-reset ui-state-default ui-corner-top ui-corner-bottom")
				.hover(function () { $(this).toggleClass("ui-state-hover"); })
					.prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>')
					.click(function () {
					    $(this)
							.toggleClass("ui-accordion-header-active ui-state-active ui-state-default ui-corner-bottom")
							.find("> .ui-icon").toggleClass("ui-icon-triangle-1-e ui-icon-triangle-1-s").end()
							.next().toggleClass("ui-accordion-content-active").slideToggle();
					    return false;
					})
					.next()
					.addClass("ui-accordion-content  ui-helper-reset ui-widget-content ui-corner-bottom")
					.css("padding-bottom","11px");

			    $("#notaccordion").find("h3").click();			    
			}
		 })
	 });
} 
