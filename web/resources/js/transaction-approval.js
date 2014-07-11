var maxFileSize =0;
$(document).ready(function () {
    makeTableGrid();
    $('#jquery-removable-file-upload-example input[type=file]').removableFileUpload();
    $('#file').customFileInput();
    $("#trxApprovalAccordion").addClass("ui-accordion ui-accordion-icons ui-widget ui-helper-reset")
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


    $("#trxApprovalAccordion").find("h3").click();
});


function validation(){

    var file = $("#file")[0].files[0];
    var fileName = file.name;
    var fileSize = file.size;
    alert("maxFileSize is="+maxFileSize+" and your file is="+fileSize);
    if(fileSize >= maxFileSize)
    {
        $("b").show();
    } else{
        $("#fileSizeValidation").hide();
        uploadFormData();
    }
}

function uploadFormData(){

    $("body").css('opacity','0.6');
    $('#upLoading').css('left',(($('body').innerWidth())/2));
    $("#upLoading").show();

    var oMyForm = new FormData();
    //alert("file==="+fileName+" file size=="+fileSize);
    oMyForm.append("file", $("#file")[0].files[0]);
    oMyForm.append("commentRegion",$("#trxApprovalForm #comment").val());
    $.ajax({
        url: './trxApprovalFileUpload.html',
        data: oMyForm,
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data){
            $('#docList').replaceWith($(data).find("#docList").html());
            $("body").css('opacity','1.0');
            $("#upLoading").hide();
            $("#removeClick").trigger('click');
            makeTableGrid();
        }
    });
}

jQuery.fn.removableFileUpload = function (conf) {
    var config = jQuery.extend({
        remove:    'remove'
    }, conf);

    return this.each(function () {
        var input        = $(this);

        var remove        = $('<div class="jquery-removable-file-upload" style="float: right; margin-top: 6px; margin-right: 24px; color: red;"><strong>[<a id="removeClick" href="#">' + config.remove + '</a>]</strong></div>').insertAfter(input).hide();
        var removeButton = $('<input id="uploadButton" type="button" class="myButton" value="Upload"/>').insertBefore(remove).hide();

       // var removeButton = $('<input id="uploadButton" type="button" class="myButton" value="Upload"/>');

        var onchange    = function () {

            var file = input.val();
            file = file.substring(file.lastIndexOf('\\') + 1);
            if (file) {
                remove.show();/*.find('strong').text(file);*/
                removeButton.show();
                removeButton.off('click');
                removeButton.click(function(){
                    validation();
                });
            }
            else {
                remove.hide();
                removeButton.remove();
                $("#fileSizeValidation").hide();
            }
        };

        input.change(onchange);

        remove.find('a').click(function () {
            remove.hide();
            removeButton.remove();
            $("#fileSizeValidation").hide();
            input = $('<input type="file" name="' + input.attr('name') + '"/>').replaceAll(input).change(onchange);
            //$('#trxApprovalForm #file').reset();
            $('.customfile').remove();
            var mainDiv = $('.jquery-removable-file-upload');
            $('<input id="file" type="file" name="fileDataForSupportingDocument" />').insertBefore(mainDiv);
            $('.jquery-removable-file-upload').remove();
            $('#jquery-removable-file-upload-example input[type=file]').removableFileUpload();
            $('#file').customFileInput();
            removeButton.off('click');
            removeButton.click(function(){
                validation();
            });
            return false;
        });
    });
};

/**
 * --------------------------------------------------------------------
 * jQuery customfileinput plugin
 * Author: Scott Jehl, scott@filamentgroup.com
 * Copyright (c) 2009 Filament Group
 * licensed under MIT (filamentgroup.com/examples/mit-license.txt)
 * --------------------------------------------------------------------
 */
(function( $ ){
    $.fn.customFileInput = function(){
        //apply events and styles for file input element
        var fileInput = $(this)
            .addClass('customfile-input') //add class for CSS
            .mouseover(function(){ upload.addClass('customfile-hover'); })
            .mouseout(function(){ upload.removeClass('customfile-hover'); })
            .focus(function(){
                upload.addClass('customfile-focus');
                fileInput.data('val', fileInput.val());
            })
            .blur(function(){
                upload.removeClass('customfile-focus');
                $(this).trigger('checkChange');
            })
            .bind('disable',function(){
                fileInput.attr('disabled',true);
                upload.addClass('customfile-disabled');
            })
            .bind('enable',function(){
                fileInput.removeAttr('disabled');
                upload.removeClass('customfile-disabled');
            })
            .bind('checkChange', function(){
                if(fileInput.val() && fileInput.val() != fileInput.data('val')){
                    fileInput.trigger('change');
                }
            })
            .bind('change',function(){
                //get file name
                var fileName = $(this).val().split(/\\/).pop();
                //get file extension
                var fileExt = 'customfile-ext-' + fileName.split('.').pop().toLowerCase();
                //update the feedback
                uploadFeedback
                    .text(fileName) //set feedback text to filename
                    .removeClass(uploadFeedback.data('fileExt') || '') //remove any existing file extension class
                    .addClass(fileExt) //add file extension class
                    .data('fileExt', fileExt) //store file extension for class removal on next change
                    .addClass('customfile-feedback-populated'); //add class to show populated state
                //change text of button
                uploadButton.text('Change');
            })
            .click(function(){ //for IE and Opera, make sure change fires after choosing a file, using an async callback
                fileInput.data('val', fileInput.val());
                setTimeout(function(){
                    fileInput.trigger('checkChange');
                },100);
            });

        //create custom control container
        var upload = $('<div class="customfile" style="margin-bottom: 0px;"></div>');
        //create custom control button
        var uploadButton = $('<span class="customfile-button" aria-hidden="true">Browse</span>').appendTo(upload);
        //create custom control feedback
        var uploadFeedback = $('<span class="customfile-feedback" aria-hidden="true">No file selected...</span>').appendTo(upload);

        //match disabled state
        if(fileInput.is('[disabled]')){
            fileInput.trigger('disable');
        }


        //on mousemove, keep file input under the cursor to steal click
        upload
            .mousemove(function(e){
                fileInput.css({
                   // 'left': e.pageX - upload.offset().left - fileInput.outerWidth() + 20 //position right side 20px right of cursor X)
                    //'top': e.pageY - upload.offset().top - $(window).scrollTop() - 3
                'width': 325,
                 'top':0
                });
            })
            .insertAfter(fileInput); //insert after the input
        //$('<input id="uploadButton" type="button" class="myButton" value="Upload"/>').insertAfter(upload);
        fileInput.appendTo(upload);

        //return jQuery
        return $(this);
    };
})( jQuery );

function makeTableGrid(){
    $(".tablesorterForTrxApproval").flexigrid({
        colModel : [
            {display: 'File Name', name : 'File Name', width : 200, sortable : true, align: 'left'},
            {display: 'Download', name : 'Download', width : 150, sortable : true, align: 'left'},
            {display: 'Uploaded By', name : 'Uploaded By', width :150, sortable : true, align: 'left'},
            {display: 'Date', name : 'Date', width : 340, sortable : true, align: 'left'}

        ],

        width: 420,
        height: 200
    });
    $('table#tablesorterForTrxApproval tbody td').click(function(){
        $(this).parent().parent().children().each(function(){
            $(this).children().each(function(){
                if($(this).hasClass("redRow"))
                    $(this).removeClass("redRow");
            });
        });
        $(this).parent().children().each(function(){
            $(this).addClass("redRow");
        });
    });
    $("#tablesorterForTrxApproval tbody tr").click(function(){//click anywhere in a row
        // var postUrl = "./supportingDocumentShow.html?supportingDocumentId=" + $(this).find(".supportingDocumentId").text().trim() ;
        // window.location = postUrl;
        //var win = window.open(postUrl, '_blank');
        //win.focus();
    });
}

