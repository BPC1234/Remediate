
$(document)
		.ready(
				function() {
                   /* $("#summeryTableForNewRiskAssesment tbody tr").click(function(){ //click anywhere in a row
                        getTransactionDetails(this);
                        //selectedRow(this);
                        var prevActive = $('.inbox tr.active')
                        prevActive.removeClass('active');
                        $(this).removeClass('unreaded').addClass('active');
                    });*/
                    $("#summeryTable1 tbody tr").click(function(){ //click anywhere in a row
                        var prevActive = $('.inbox tr.active')
                        prevActive.removeClass('active');
                        $(this).removeClass('unreaded').addClass('active');
                    });
                });




