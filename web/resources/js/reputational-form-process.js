$(document).ready(function () {
    $('#jquery-removable-file-upload-example_1  input[type=file]').removableFileUpload('','_1','businessPartnerQuestionnaireFile');
    $('#jquery-removable-file-upload-example_2  input[type=file]').removableFileUpload('','_2','otherDueDiligenceFile');
    $('#jquery-removable-file-upload-example_3  input[type=file]').removableFileUpload('','_3','executedOrDraftContractFile');


    $('#businessPartnerQuestionnaireFile').customFileInput('businessPartnerQuestionnaireFile','_1');
    $('#otherDueDiligenceFile').customFileInput('otherDueDiligenceFile','_2');
    $('#executedOrDraftContractFile').customFileInput('executedOrDraftContractFile','_3');

    uploadRequiredFile("#fileselectbutton1", "#inputFile1");
    uploadRequiredFile("#fileselectbutton2", "#fileName2");
    uploadRequiredFile("#fileselectbutton3", "#fileName3");
    $('#uniqueControlSelect').multiselect();

    $('#accordion .accordion-heading').click(function() {

        var $previous = $( '#accordion .accordion-heading.active' );

        $previous.removeClass('active').find('i').removeClass('icon-minus').addClass('icon-plus');
        $(this).stop().addClass('active').find('i').removeClass('icon-plus').addClass('icon-minus');

        if($(this).hasClass('active')) {
            $previous.removeClass('active').find('i').removeClass('icon-minus').addClass('icon-plus');
        }

    });


});

function validation(fileID, fileNo) {

    var file = $("#"+fileID)[0].files[0];
    var fileName = file.name;
    var fileSize = file.size;
    //alert("maxFileSize is="+maxFileSize+" and your file is="+fileSize);
    if (fileSize >= maxFileSize) {
        $("#fileSizeValidationFor"+fileID).show();
    } else {
        $("#fileSizeValidationFor"+fileID).hide();
        uploadFormData(fileID, fileNo);
    }
}

function uploadFormData(inputFileId, fileNo) {

    $("#fileUploderDiv").css('opacity', '0.4');
    //$('#loading').css('left', ($('#fileUploderDiv').innerWidth()/ 2));
    $("#loading"+fileNo).show();
    var oMyForm = new FormData();
    oMyForm.append(inputFileId, $("#"+inputFileId)[0].files[0]);
    //alert("-"+inputFileId+"----"+($("#"+inputFileId)[0].files[0]).name+" file size=="+($("#"+inputFileId)[0].files[0]).size);
    $.ajax({
        url:'./ajaxCallForReputationalFormFileUpload.html',
        data:oMyForm,
        dataType:'text',
        processData:false,
        contentType:false,
        type:'POST',
        success:function (data) {
            $("#fileUploderDiv").css('opacity', '1.0');
            $("#loading"+fileNo).hide();
            $("#removeClick"+fileNo).trigger('click');
        }
    });
}

jQuery.fn.removableFileUpload = function (conf,fileNo,inputTypeID) {
    var config = jQuery.extend({
        remove:'remove'
    }, conf, fileNo);

    return this.each(function () {
        var input = $(this);
        var divClassName = "jquery-removable-file-upload"+fileNo;
        var fileId = "uploadButton"+fileNo;
        var removeClick = "removeClick"+fileNo;
        var remove = $('<div class='+divClassName+' style="display: inline-block; float: right; margin-top: 6px; margin-left: 7px; color: red;"><strong>[<a id='+removeClick+' href="#">' + config.remove + '</a>]</strong></div>').insertAfter(input).hide();
        var removeButton = $('<input id='+fileId+' type="button" class="myButton" value="Upload"/>').insertBefore(remove).hide();
        var onchange = function () {

            var file = input.val();
            file = file.substring(file.lastIndexOf('\\') + 1);
            if (file) {
                remove.show();
                removeButton.show();
                removeButton.off('click');
                removeButton.click(function () {
                    validation(inputTypeID,fileNo);
                });
            }
            else {
                remove.hide();
                removeButton.remove();
                $("#fileSizeValidationFor"+inputTypeID).hide();
            }
        };

        input.change(onchange);

        remove.find('a').click(function () {
            remove.hide();
            removeButton.remove();
            $("#fileSizeValidationFor"+inputTypeID).hide();
            input = $('<input type="file" name="' + input.attr('name') + '"/>').replaceAll(input).change(onchange);
            $('.customfile'+fileNo).remove();
            var mainDiv = $('.jquery-removable-file-upload'+fileNo);
            $('<input id='+inputTypeID+' type="file" name='+inputTypeID+'/>').insertBefore(mainDiv);
            $('.jquery-removable-file-upload'+fileNo).remove();
            $('#jquery-removable-file-upload-example'+fileNo+'  input[type=file]').removableFileUpload('',fileNo,inputTypeID);
            $('#'+inputTypeID).customFileInput(inputTypeID,fileNo);
            removeButton.off('click');
            removeButton.click(function () {
                validation();
            });
            return false;
        });
    });
};


(function ($) {
    $.fn.customFileInput = function (inputTypeId,fileNo) {
        var fileInput = $(this)
            .addClass('customfile-input')
            .mouseover(function () {
                upload.addClass('customfile-hover');
            })
            .mouseout(function () {
                upload.removeClass('customfile-hover');
            })
            .focus(function () {
                upload.addClass('customfile-focus');
                fileInput.data('val', fileInput.val());
            })
            .blur(function () {
                upload.removeClass('customfile-focus');
                $(this).trigger('checkChange');
            })
            .bind('disable', function () {
                fileInput.attr('disabled', true);
                upload.addClass('customfile-disabled');
            })
            .bind('enable', function () {
                fileInput.removeAttr('disabled');
                upload.removeClass('customfile-disabled');
            })
            .bind('checkChange', function () {
                if (fileInput.val() && fileInput.val() != fileInput.data('val')) {
                    fileInput.trigger('change');
                }
            })
            .bind('change', function () {
                var fileName = $(this).val().split(/\\/).pop();
                var fileExt = 'customfile-ext-' + fileName.split('.').pop().toLowerCase();
                uploadFeedback
                    .text(fileName)
                    .removeClass(uploadFeedback.data('fileExt') || '')
                    .addClass(fileExt)//add file extension class
                    .data('fileExt', fileExt)
                    .addClass('customfile-feedback-populated');

                uploadButton.text('Change');
            })
            .click(function () {
                fileInput.data('val', fileInput.val());
                setTimeout(function () {
                    fileInput.trigger('checkChange');
                }, 100);
            });

        var divClass="customfile"+fileNo;
        var upload = $('<div class='+divClass+' ></div>');
        var uploadButton = $('<span class="customfile-button" aria-hidden="true">Browse</span>').appendTo(upload);
        var uploadFeedback = $('<span class="customfile-feedback" aria-hidden="true">No file selected...</span>').appendTo(upload);

        if (fileInput.is('[disabled]')) {
            fileInput.trigger('disable');
        }
        upload
            .mousemove(function (e) {
                fileInput.css({
                    'width':325,
                    'top':0
                });
            })
            .insertAfter(fileInput);
        fileInput.appendTo(upload);
        return $(this);
    };
})(jQuery);

function uploadRequiredFile(browserId, fileId){
    $( browserId ).click(function(e){
        $( fileId ).trigger("click");
    });

    $( fileId ).change(function(e){
        var val = $(this).val();
        var file = val.split(/[\\/]/);
        $(fileId).val(file[file.length-1]);
    });


}
