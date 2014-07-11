function Info() {
    this.name = '';
    this.operator = '';
    this.date = '';
    this.AndOr=''
}
$(function () {
    i = 2;
    //$("select").selectBox();

    //$("#datePicker").datepicker({ maxDate: new Date });
    $( "#datePicker3" ).datepicker({
        dateFormat: 'dd/mm/yy',
        selectWeek: true,
        inline: true,

    });
    //$("[name=date]").datepicker({ maxDate: new Date });
    // $("#delete").live("click", function (e) {
    $('body').on('click', '#delete', function(e) {
        if ($('#table1  tbody > tr').length <= 1) {
            return false;
        }
        if ($(this).parent().parent().find("input[name='add']").val()) {
            return false;
        }
        if (confirm("Are you sure want to delete?")) {
            $(this).parent().closest("tr").css("background-color", "#FFFFEA");
            $(this).fadeOut('slow', function () {
                $(this).parent().closest("tr").remove();
            });
        }
    });
    // $("#add").live("click", function (e, index) {
    $('body').on('click', '#add', function(e) {
        $('#table1  tbody > tr:last').after('<tr><td><span>' + (i++) + '&nbsp;</span></td><td><select name="andOr[]"><option>Select</option><option>And</option><option>Or</option></select></td><td><select name="field[]"><option>Select</option><option>Start Date</option><option>End Date</option></select></td><td><select name="operator[]"><option>Select</option><option><</option><option>></option><option>>=</option><option><=</option><option>==</option><option>!=</option></select></td><td><input type="text" name="date"></td><td><input type=button value=" Add " name=add id=add></td><td><input type=button value=" Delete " name=delete id=delete></td></tr>');
        $('#table1  tbody > tr:last').prev().find("input[name='add']").remove();
        //$("select").selectBox();
        //$("[name=date]").datepicker({ maxDate: new Date });
    });

});