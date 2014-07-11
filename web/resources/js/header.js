$(document).ready(function () {
removeSelectionAndSelectCurrentMenu();
$(".mainmenu").show();


});
function removeSelectionAndSelectCurrentMenu(){
    $("#"+$("#mainMenuSelectionTxtId").val()).addClass('active');
}