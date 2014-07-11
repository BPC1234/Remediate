var selectTableMaxNumberOfTd = 7 ;
var selectTableMinNumberOfTd = 6 ;
var counterForSelectedColumnTable = 0 ;
var whereAdditionalClause = ') AND tx.job_id = ?';
var selectClause;
var fromClause;
var havingClause = ' HAVING ';
var orderByClause = ' ORDER BY ';
var rowCount = 1;
var conditionTr;
var groupByClause =' GROUP BY ';
var errorMessage = '';
var columnSelectBoxArray = [""];
var leftParenthesis = [];
var rightParenthesis = [];
var aggregateFunctionArray = ["NOT SELECT", "AVG()", "COUNT()", "COUNT(DISTINCT)", "SUM()", "MAX()", "MIN()", "BIT_AND()", "BIT_OR()", "BIT_XOR()",
    "GROUP_CONCAT()", "STD()", "STDDEV_POP()", "STDDEV_SAMP()", "STDDEV()", "VAR_POP()", "VAR_SAMP()", "VARIANCE()"];
var signSelectBoxArray = ["AND", "OR", "AND (", "OR (", ") AND", ") OR", ")", "."];
var signSelectBoxArrayForHavingClause = ["AND", "OR","."];
var signSelectBoxArrayForFirstTr = ["AND", "OR", "AND (", "OR (", "."];
var conditionSelectBoxArray = ["=", "<", ">", "<=", ">=", "<>", "!=", "NOT LIKE", "LIKE", "IS NULL", "IS NOT NULL", "NOT IN", "IN",
    "NOT EXISTS", "EXISTS", "NOT BETWEEN", "BETWEEN"];
var conditionSelectBoxArrayForHavingClause = ["=", "<", ">", "<=", ">=", "<>", "!="];
var signSelectBox = '<select id="signSelectBoxId">'
    + '<option class="optionClass" value="AND">AND</option>'
    + '<option class="optionClass" value="OR">OR</option>'
    + '<option class="optionClass" value="AND (">AND (</option>'
    + '<option class="optionClass" value="OR (">OR (</option>'
    + '<option class="optionClass" value=") AND">) AND</option>'
    + '<option class="optionClass" value=") OR">) OR</option>'
    + '<option class="optionClass" value=") OR">).</option>'
    + '<option class="optionClass" value=") OR">.</option>'
    + '</select>';
var conditionSelectBox = '<select id="conditionSelectId">'
    + '<option class="optionClass" value="="> = </option>'
    + '<option class="optionClass" value="<"> < </option>'
    + '<option class="optionClass" value=">"> > </option>'
    + '<option class="optionClass" value="<="> <= </option>'
    + '<option class="optionClass" value=">="> >= </option>'
    + '<option class="optionClass" value="<>"> <> </option>'
    + '<option class="optionClass" value="!="> != </option>'
    + '<option class="optionClass" value="LIKE">LIKE</option>'
    + '<option class="optionClass" value="NOT LIKE">NOT LIKE</option>'
    + '<option class="optionClass" value="IS NULL">IS NULL</option>'
    + '<option class="optionClass" value="IS NOT NULL">IS NOT NULL</option>'
    + '<option class="optionClass" value="IN">IN</option>'
    + '<option class="optionClass" value="NOT IN">NOT IN</option>'
    + '<option class="optionClass" value="EXISTS">EXISTS</option>'
    + '<option class="optionClass" value="NOT EXISTS">NOT EXISTS</option>'
    + '<option class="optionClass" value="BETWEEN">BETWEEN</option>'
    + '<option class="optionClass" value="NOT BETWEEN">NOT BETWEEN</option>'
    + '</select>';
var freeTextBox = '<input type="text" class="span4 freeText" style="display:none;"/>'
var freeDoubleTextBox = '<div class="betweenDiv"><input type="text" class="span5 freeTextFirst"/><div class="andDiv"> AND </div><input type="text" class="span5 freeTextSecond"/></div>'
$(document).ready(function () {
    $("#selectClauseTable tr  td:not(:first-child)").click(function (event) {
        var status = ($(this).parent().children(':first').find(".selectClauseCheckbox")).prop('checked');
        if (status == false) {
            $($(this).parent().children(':first').find(".selectClauseCheckbox")).prop('checked', true);
        } else {
            $($(this).parent().children(':first').find(".selectClauseCheckbox")).prop('checked', false);
        }
    });

    $("#selectClauseSubmitButton").click(function () {
        fromClause = ' FROM transaction trx ';
        var totalLedger = $("#ledgerCount").val();
        var selectedLedger = '';
        for (var i = 0; i < totalLedger; i++) {
            if ($("#checkbox_" + i).prop('checked') == true) {
                fromClause = fromClause + "JOIN " + $("#checkbox_" + i).attr('name') + " ON ( " + $("#checkbox_" + i).attr('name') + ".transaction_fk = " + "trx.id ) ";
                selectedLedger = selectedLedger + ',' + $("#checkbox_" + i).attr('name');
            }
        }
        selectedLedger = selectedLedger.substr(1);
        getColumnNameByAjaxCall(selectedLedger);
    });

    $(document).on('click', '.ruleRemoveBtn', function (e) {
        var trClassName = $(this).parent('td').parent('tr').attr('class');
        var rowspanCountForTd = $("tr." + trClassName + " td." + trClassName).attr('rowspan');
        var totalTdInThisTr = $(this).parent('td').parent('tr').children('td').length;
        if (totalTdInThisTr == selectTableMaxNumberOfTd) {
            if ($(this).parent('td').parent('tr').next('tr').hasClass(trClassName + "_last") == false)
                $('<td class="' + trClassName + '" rowspan="' + --rowspanCountForTd + '">' + trClassName + '</td>').insertBefore($(this).parent('td').parent('tr').next('tr').children('td').eq(0));
            if ($(this).parent('td').parent('tr').next('tr').hasClass(trClassName + "_last") == true)
                $("#selectedColumnTable tr." + trClassName + "_last").remove();


        } else if (totalTdInThisTr == selectTableMinNumberOfTd) {
            $("tr." + trClassName + " td." + trClassName).attr('rowspan', --rowspanCountForTd);

        }
        $(this).parent('td').parent('tr').remove();

    });

    $(document).on('click', '.conditionRemoveBtn', function (e) { // delete or remove each condition row
        $(this).parent('td').parent('tr').addClass('discard').hide();
        $(".addConditionTable thead td.query").html('<label class="queryShow">' + makeWhereClause() + '</label>');
    });

    $(document).on('change', '.selectBox', function (e) {
        $(".addConditionTable thead td.query").html('<label class="queryShow">' + makeWhereClause() + '</label>');

        if ($(".selectBox #" + $(this).val()).html() == ')' || $(".selectBox #" + $(this).val()).html() == ') OR' || $(".selectBox #" + $(this).val()).html() == ') AND') {
            $(this).parent('td').next('td').next('td').remove();
        }
    });

    $(document).on('click', '#whereClauseSubmitButton', function (e) { // query submit button for testing error in where clause
        errorMessage = '';
        var totalCondition = $(".whereClauseMakerTable tbody tr:visible").length;

        if (totalCondition == 0) { // for empty condition
            alert('Please add a condition');
        } else if (totalCondition == 1) { // When only one condition added but not close the condition
            if ($(".selectBox #" + $(".whereClauseMakerTable tbody:first tr:visible").children('td').eq(3).find('.selectBox').val()).text() != '.') {
                errorMessage = errorMessage + '\n Please Close Condition.';
            }
        } else if (totalCondition > 1) {  // When multiple condition added but not close the last condition
            if ($(".selectBox #" + $(".whereClauseMakerTable tbody tr:last:visible").children('td').eq(3).find('.selectBox').val()).text() != '.' &&
                $(".selectBox #" + $(".whereClauseMakerTable tbody tr:last:visible").children('td').eq(3).find('.selectBox').val()).text() != ')') {
                errorMessage = errorMessage + '\n Please Close Condition.';
            }
            var clause = makeWhereClause();
            getErrorInRightParenthesesClause(clause);
            getErrorInLeftParenthesesClause(clause);
            if (rightParenthesis.length > 0) {
                errorMessage = errorMessage + '\n Close right parentheses.';
            }
        }
        if (errorMessage.length > 1) { // To print the error message
            alert(errorMessage);
        } else {   // allow user to save the rule
            //var fullQuery = selectClause + fromClause + makeWhereClause();
            var fullQuery = makeWhereClause()+whereAdditionalClause;
            var groupByQuery = '';
            groupByQuery = makeGroupByClause();
            var havingQuery = '';
            havingQuery = makeHavingClause();
            var orderByQuery = '';
            orderByQuery = makeOrderByClause();
            if(groupByQuery != '')
                fullQuery =  fullQuery + groupByClause + groupByQuery ;

            if(havingQuery != '')
                fullQuery = fullQuery + havingClause + havingQuery;

            if(orderByQuery != '')
                fullQuery = fullQuery + orderByClause + orderByQuery;

            $("#ruleTitle").val('');
            $("#selectClause").val(selectClause);
            $("#fromClause").val(fromClause);
            $("#whereClause").val(fullQuery);
            $('.ruleCreationModalCall').click(); // Calling the rule modal screen
        }

    });

    $(document).on('click', '.showFreeText', function (e) {
        $(this).parent('td').find(".btn-group").hide();
        $(this).parent('td').find(".freeText").show();
        $(this).parent('td').find(".showColumnSelect").show();
        $(this).hide();

    });

    $(document).on('click', '.showColumnSelect', function (e) {
        $(this).parent('td').find(".btn-group").show();
        $(this).parent('td').find(".freeText").hide();
        $(this).parent('td').find(".showFreeText").show();
        $(this).hide();
    });

    $(document).on('click', '.ruleCreationModalCall', function (e) {

    });

    $(document).on('click', '#saveRule', function (e) {
        var getUrl = "../admin/ruleCreation.html";
        $.ajax({
            type: 'post',
            url: getUrl,
            data: $("#ruleFormId").serialize(),
            async: false,
            success: function (data) {
                window.location = "./ruleCreation.html?opt=1";
            }

        });

    });

    $(document).on('change', '.conditional', function (e) {
        var conditionSelect = $(".conditional #" + $(this).val()).html();
        if (conditionSelect == "BETWEEN" || conditionSelect == "NOT BETWEEN") {
            $(this).parent('td').next('td').find(".btn-group").hide();
            $(this).parent('td').next('td').find(".freeText").hide();
            $(this).parent('td').next('td').find(".showColumnSelect").hide();
            $(this).parent('td').next('td').find(".showFreeText").hide();
            $(this).parent('td').next('td').find('.freeText').hide();

            $(this).parent('td').next('td').find('.betweenDiv').remove();

            $(this).parent('td').next('td').append(freeDoubleTextBox);
        } else if (conditionSelect == "IS NULL" || conditionSelect == "IS NOT NULL") {
            $(this).parent('td').next('td').find(".btn-group").hide();
            $(this).parent('td').next('td').find(".freeText").hide();
            $(this).parent('td').next('td').find(".showColumnSelect").hide();
            $(this).parent('td').next('td').find(".showFreeText").hide();
            $(this).parent('td').next('td').find('.freeText').hide();
            $(this).parent('td').next('td').append('<label></label>');

        } else {
            $(this).parent('td').next('td').find('.betweenDiv').remove();
            $(this).parent('td').next('td').find(".btn-group").show();
            $(this).parent('td').next('td').find(".showFreeText").show();
            $(this).parent('td').next('td').find(".showColumnSelect").hide();
        }

    });
    $(document).on('click', '#addCondition', function (e) {
        var lastTrSelectionValue = getLastVisibleRow('whereClauseMakerTable');
        if (lastTrSelectionValue == '.' || lastTrSelectionValue == ')') {
            alert('You have already end the where clause.');
        } else {

            if ($(".whereClauseMakerTable tbody tr").length > 0) {
                addTrForWhereCondition('forOtherTr');
            } else if ($(".whereClauseMakerTable tbody tr").length == 0) {
                addTrForWhereCondition('first');
            }
        }
    });

    $(document).on('change', '#selectedColumnTable tbody tr td.havingTd .selectBox', function (e) {
        if($(".selectBox #" + $(this).val()).text() != 'NOT SELECT'){
            $(this).parent('div').parent('td').find('div div.havingInnerDiv').show();
      }else{
            $(this).parent('div').parent('td').find('div div.havingInnerDiv').hide();
        }
    });

    $(document).on('click', '.selectColumnName', function (e) {
        if($("#selectedColumnTable").css('display') == 'none')
            $("#selectedColumnTable").show();
        var tableName = $(this).parent('td').parent('tr').children(':first').find(".selectClauseLabel").html();
        var selectId = $(this).parent('td').parent('tr').children().eq(1).find(".checkOpt").attr('id');
        var selectOptionVal = $("#" + selectId).val();
        var columnSelect = $("#" + selectId + " #" + selectOptionVal).html();
        selectColumnNameButtonClickAction(tableName,columnSelect,'');
    });

    $(document).on('click', '#selectColumnSubmitButton', function (e) {
        var selectItem = $("#selectedColumnTable tbody tr").length;
        if (selectItem > 0) {
            columnSelectBoxArray = [""];
            conditionTr = '<tr>'
                + '<td>' + makeSelectClause() + '</td>'
                + '<td>' + conditionSelectBox + '</td>'
                + '<td>' + freeTextBox + '</td>'
                + '<td>' + signSelectBox + '</td>'
                + '<td><input type="button" class="conditionRemoveBtn"></td>'
                + '</tr>';
            $("#ruleDefinitionWhereClauseDiv").show();
            $(".whereClauseSelectionTable").hide();
        } else {
//            alert('Please select columns.');
        }
    });


    ruleInitialize();
});

function getColumnNameByAjaxCall(lookupIds) {

    $.ajax({
        url: './getColumnNameByAjaxCall.html?lookupIds=' + lookupIds,
        data: '',
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        async: false,
        success: function (data) {
            var source = $('<div>' + data + '</div>');
            $('#ruleDefinitionDivId').html(source.find('#ruleDefinitionDivId').html());

            for (var i = 0; i < $("#ledgerCount").val(); i++) {
                $('#selectColumnId' + i).multiselect();
            }
        }

    });
}

function getSelectBoxWithNewId(prefix, id, optionList,selectionValue) {
    var select = '<select class="selectBox ' + prefix + '" id="' + prefix + id + '">'
    for (var i = 0; i < optionList.length; i++) {
        if (optionList[i] != '') {
            if(optionList[i] == selectionValue){
                select = select + '<option id="' + id + prefix + i + '" class="optionClass" value="' + id + prefix + i + '" selected="selected">' + optionList[i] + '</option>';
            }else{
                select = select + '<option id="' + id + prefix + i + '" class="optionClass" value="' + id + prefix + i + '">' + optionList[i] + '</option>';
            }

        }

    }
    select = select + '</select>';
    return select;
}

function makeWhereClause() {
    var clause = 'WHERE (';
    $('.whereClauseMakerTable > tbody  > tr').each(function () {
        if ($(this).hasClass('discard') == false) {
            clause = clause + ' ' + $(".selectBox #" + $(this).children('td').eq(0).find('.selectBox').val()).html() + ' ';
            clause = clause + ' ' + $(".selectBox #" + $(this).children('td').eq(1).find('.selectBox').val()).text() + ' ';
            clause = clause + " " + getAppropriateText($(".selectBox #" + $(this).children('td').eq(1).find('.selectBox').val()).html(), $(this).children('td').eq(2))/*$(this).children('td').eq(2).find('.freeText').val())*/ + " ";

            if ($(".selectBox #" + $(this).children('td').eq(3).find('.selectBox').val()).text() == '.')
                clause = clause + ' ';
            else
                clause = clause + ' ' + $(".selectBox #" + $(this).children('td').eq(3).find('.selectBox').val()).text() + ' ';
        }
    });
    return clause + ' )';
}

function getAppropriateText(condition, textObject) {
    var text;
    var count = 0;
    if (textObject.find('.showColumnSelect').css('display') == 'none') {
        text = $(".selectBox #" + textObject.find('.selectBox').val()).text();
        count++;
    } else if (textObject.find('.showFreeText').css('display') == 'none') {
        text = textObject.find('.freeText').val();
    }

    if (condition == 'IN' || condition == 'NOT IN' || condition == 'EXISTS' || condition == 'NOT EXISTS') {
        text = " (" + text + ") ";
    } else if (condition == 'BETWEEN' || condition == 'NOT BETWEEN') {
        text = " '" + textObject.find('.freeTextFirst').val() + "' AND '" + textObject.find('.freeTextSecond').val() + "'";
    } else if (condition == 'IS NULL' || condition == 'IS NOT NULL') {
        text = " ";
    } else if (count == 0) {
        text = " '" + text + "' ";
    }
    return text;
}
function getErrorInRightParenthesesClause(whereClause) {
    rightParenthesis = [];
    $('.whereClauseMakerTable > tbody  > tr').each(function () {
        if ($(this).hasClass('discard') == false) {
            var selectItem = $(".selectBox #" + $(this).children('td').eq(3).find('.selectBox').val()).text();
            if (selectItem == 'AND (' || selectItem == 'OR (') {
                rightParenthesis.push('(');
            } else if (selectItem == ') AND' || selectItem == ') OR' || selectItem == ')') {
                rightParenthesis.splice(rightParenthesis.length - 1, 1);
            }
        }
    });
}
function getErrorInLeftParenthesesClause(whereClause) {
    leftParenthesis = [];
    $('.whereClauseMakerTable > tbody  > tr').each(function () {
        if ($(this).hasClass('discard') == false) {
            var selectItem = $(".selectBox #" + $(this).children('td').eq(3).find('.selectBox').val()).text();
            if (selectItem == 'AND (' || selectItem == 'OR (') {
                leftParenthesis.push('(');
            } else if (selectItem == ') AND' || selectItem == ') OR' || selectItem == ')') {
                if (leftParenthesis.length == 0) {
                    errorMessage = errorMessage + '\n Invalid declaration of parentheses.';
                } else {
                    leftParenthesis.splice(leftParenthesis.length - 1, 1);
                }

            }
        }
    });
}
function getLastVisibleRow(className) {
    var lastTdSelectItem;
    $('.' + className + ' > tbody  > tr:visible').each(function () {
        lastTdSelectItem = $(".selectBox #" + $(this).children('td').eq(3).find('.selectBox').val()).text();
    });
    return lastTdSelectItem;
}
function addTrForWhereCondition(trStatus) {

    var totalTr = $(".whereClauseMakerTable tbody tr").length;
    conditionTr = '<tr>'
        + '<td>' + getSelectBoxWithNewId('columnName', totalTr, columnSelectBoxArray,'') + '</td>'
        + '<td>' + getSelectBoxWithNewId('conditional', totalTr, conditionSelectBoxArray,'') + '</td>'
        + '<td>' + getSelectBoxWithNewId('columnNameSecond', totalTr, columnSelectBoxArray,'')
        + '&nbsp;&nbsp;<a class="showFreeText">Free text</a> &nbsp;&nbsp;' + freeTextBox + '&nbsp;&nbsp;<a class="showColumnSelect" style="display:none;">Column Select</a></td>';

    if (trStatus == 'first') {
        conditionTr = conditionTr + '<td>' + getSelectBoxWithNewId('sign', totalTr, signSelectBoxArrayForFirstTr,'') + '</td>';
    } else {
        conditionTr = conditionTr + '<td>' + getSelectBoxWithNewId('sign', totalTr, signSelectBoxArray,'') + '</td>'
            + '<td><input type="button" class="conditionRemoveBtn"></td>';
    }
    conditionTr = conditionTr + '</tr>';
    $(".whereClauseMakerTable tbody:last").append(conditionTr);
    $('#sign' + totalTr).multiselect();
    $('#conditional' + totalTr).multiselect();
    $('#columnName' + totalTr).multiselect();
    $('#columnNameSecond' + totalTr).multiselect();
}
function makeSelectClause() {
    selectClause = 'SELECT trx.id transactionId ';
    var select = '<select id="whereClauseOption" class="checkOpt">'

    $('#selectedColumnTable > tbody  > tr').each(function () {
        var firstTr = $(this).children('td').eq(0);
        var totalNumberOfTd = $(this).children('td').length;
        var tableName;
        var columnName;
        var optionValue;
        if (totalNumberOfTd == selectTableMaxNumberOfTd) {
            if ($(this).hasClass('zeroHeight') == false) {
                tableName = $(this).children('td').eq(0).html();
                optionValue = tableName + '.' + $(this).children('td').eq(1).html();
                select = select + '<option class="optionClass" value=" ' + optionValue + ' ">' + optionValue + '</option>';
                columnSelectBoxArray.push(optionValue);
                selectClause = selectClause + ", " + checkIsSelectAggregateFn(this,optionValue);

            }
        } else if (totalNumberOfTd == selectTableMinNumberOfTd) {
            tableName = $(this).attr('class');
            optionValue = tableName + '.' + $(this).children('td').eq(0).html();
            select = select + '<option class="optionClass" value=" ' + optionValue + ' ">' + optionValue + '</option>';
            columnSelectBoxArray.push(optionValue);
            selectClause = selectClause + ", " + checkIsSelectAggregateFn(this,optionValue);
        }
    });
    select = select + "</select>";
    return select;
}
function addTrForSelectColumn(tableName,columnSelect,rowSpanCount,ruleObject){
    //var count = $("#selectedColumnTable tbody tr").length;
    counterForSelectedColumnTable++;
    rowSpanCount = $('#selectedColumnTable > tbody tr.' + tableName + ' td').eq(0).attr('rowspan');
    var backgroundFilledClass = 'danger';
    if (rowSpanCount % 2 == 0) {
        backgroundFilledClass = 'success';
    }
        var tr = '<tr class="' + tableName + '"><td class="' + columnSelect + ' filled '+backgroundFilledClass+'">' + columnSelect + '</td>'
            + '<td>'+getSelectBoxWithNewId('aggregateFn',counterForSelectedColumnTable,aggregateFunctionArray,ruleObject.aggregateFnValue)+'</td>'
            + '<td>'+(ruleObject.groupBySelect == true ? '<input type="checkbox" class="groupByCheckBox" checked>' : '<input type="checkbox" class="groupByCheckBox">')+ '</td>'
            + '<td class="havingTd"><div class="betweenDiv">'
            +  getSelectBoxWithNewId('havingAggrFnSelection',counterForSelectedColumnTable,aggregateFunctionArray,ruleObject.havingAggregateFn)
            +  '&nbsp;&nbsp;<div class="havingInnerDiv">'
            +  getSelectBoxWithNewId('havingCondition',counterForSelectedColumnTable,conditionSelectBoxArrayForHavingClause,'')
            +  '&nbsp;&nbsp;'
            +  '<input type="text" class="span4 freeText" value="' +ruleObject.havingCompareValue+ '"/>'
            +  '&nbsp;&nbsp;'
            +  getSelectBoxWithNewId('havingConditionExt',counterForSelectedColumnTable,signSelectBoxArrayForHavingClause,ruleObject.havingExtraCondition)
            +  '</div></div></td>'
            + '<td>'+(ruleObject.orderBySelect == true ? '<input type="checkbox" class="orderByCheckBox" checked>' : '<input type="checkbox" class="orderByCheckBox">')+ '</td>'
            + '<td><input type="button" class="ruleRemoveBtn"></td></tr>';

    $(tr).insertBefore('#selectedColumnTable > tbody tr.' + tableName + '_last');
    $('#aggregateFn' + counterForSelectedColumnTable).multiselect();
    $('#selectedColumnTable > tbody tr.' + tableName + ' td').eq(0).attr('rowspan', ++rowSpanCount);

    $("#havingAggrFnSelection"+counterForSelectedColumnTable).multiselect();
    $("#havingCondition"+counterForSelectedColumnTable).multiselect();
    $("#havingConditionExt"+counterForSelectedColumnTable).multiselect();

    var selectItemOfHavingAggregateFn = $(".selectBox #" + $(tr).children('td.havingTd').find("div.betweenDiv #havingAggrFnSelection"+counterForSelectedColumnTable).val()).text();
    if(ruleObject.havingAggregateFn != 'NOT SELECT'){
        $(tr).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv').show();
    }
    if(selectItemOfHavingAggregateFn == 'NOT SELECT') {
        $("#havingAggrFnSelection"+counterForSelectedColumnTable).parent('div').parent('td').find('div div.havingInnerDiv').hide();
    }
}
function checkIsSelectAggregateFn(thisObject,optionValue){
  var newOptionValue = '';
  var totalNumberOfTd = $(thisObject).children('td').length;
  if (totalNumberOfTd == selectTableMaxNumberOfTd)
      var aggregateFn = $(".selectBox #" + $(thisObject).children('td').eq(2).find('.selectBox').val()).text();
  else if(totalNumberOfTd == selectTableMinNumberOfTd)
      var aggregateFn = $(".selectBox #" + $(thisObject).children('td').eq(1).find('.selectBox').val()).text();

    if(aggregateFn =='AVG()'){
        newOptionValue = newOptionValue + ' AVG('+optionValue + ') ';
    }else if(aggregateFn =='COUNT()'){
        newOptionValue = newOptionValue + ' COUNT('+optionValue + ') ';
    }else if(aggregateFn =='COUNT(DISTINCT)'){
        newOptionValue = newOptionValue + ' COUNT(DISTINCT '+optionValue + ') ';
    }else if(aggregateFn =='SUM()'){
        newOptionValue = newOptionValue + ' SUM('+optionValue + ') ';
    }else if(aggregateFn =='MAX()'){
        newOptionValue = newOptionValue + ' MAX('+optionValue + ') ';
    }else if(aggregateFn =='MIN()'){
        newOptionValue = newOptionValue + ' MIN('+optionValue + ') ';
    }else if(aggregateFn =='BIT_AND()'){
        newOptionValue = newOptionValue + ' BIT_AND('+optionValue + ') ';
    }else if(aggregateFn =='BIT_OR()'){
        newOptionValue = newOptionValue + ' BIT_OR('+optionValue + ') ';
    }else if(aggregateFn =='BIT_XOR()'){
        newOptionValue = newOptionValue + ' BIT_XOR('+optionValue + ') ';
    }else if(aggregateFn =='GROUP_CONCAT()'){
        newOptionValue = newOptionValue + ' GROUP_CONCAT('+optionValue + ') ';
    }else if(aggregateFn =='STD()'){
        newOptionValue = newOptionValue + ' STD('+optionValue + ') ';
    }else if(aggregateFn =='STDDEV_POP()'){
        newOptionValue = newOptionValue + ' STDDEV_POP('+optionValue + ') ';
    }else if(aggregateFn =='STDDEV_SAMP()'){
        newOptionValue = newOptionValue + ' STDDEV_SAMP('+optionValue + ') ';
    }else if(aggregateFn =='STDDEV()'){
        newOptionValue = newOptionValue + ' STDDEV('+optionValue + ') ';
    }else if(aggregateFn =='VAR_POP()'){
        newOptionValue = newOptionValue + ' VAR_POP('+optionValue + ') ';
    }else if(aggregateFn =='VAR_SAMP()'){
        newOptionValue = newOptionValue + ' VAR_SAMP('+optionValue + ') ';
    }else if(aggregateFn =='VARIANCE()'){
        newOptionValue = newOptionValue + ' VARIANCE('+optionValue + ') ';
    }else{
        newOptionValue = newOptionValue + optionValue ;
    }
    if(newOptionValue != '')
    return newOptionValue;
}
function makeGroupByClause(){
    var clause = '';
    $('#selectedColumnTable > tbody  > tr').each(function () {
        var totalNumberOfTd = $(this).children('td').length;
        if ($(this).hasClass('zeroHeight') == false){
         if (totalNumberOfTd == selectTableMaxNumberOfTd) {
             if($(this).children('td').eq(3).find('.groupByCheckBox').prop('checked') == true)
                 clause = clause + ', ' + $(this).attr('class') + '.' + $(this).children('td').eq(1).text();

         }else  if (totalNumberOfTd == selectTableMinNumberOfTd) {
             if($(this).children('td').eq(2).find('.groupByCheckBox').prop('checked') == true)
                 clause = clause + ', ' + $(this).attr('class') + '.' + $(this).children('td').eq(0).text();

         }
        }

    });

    return clause.substr(1);
}
function makeOrderByClause(){
    var clause = '';
    $('#selectedColumnTable > tbody  > tr').each(function () {
        var totalNumberOfTd = $(this).children('td').length;
        if ($(this).hasClass('zeroHeight') == false){
         if (totalNumberOfTd == selectTableMaxNumberOfTd) {
             if($(this).children('td').eq(5).find('.orderByCheckBox').prop('checked') == true)
                 clause = clause + ', ' + $(this).attr('class') + '.' + $(this).children('td').eq(1).text();

         }else  if (totalNumberOfTd == selectTableMinNumberOfTd) {
             if($(this).children('td').eq(4).find('.orderByCheckBox').prop('checked') == true)
                 clause = clause + ', ' + $(this).attr('class') + '.' + $(this).children('td').eq(0).text();

         }
        }

    });

    return clause.substr(1);
}
function makeHavingClause(){
    var clause = '';
    $('#selectedColumnTable > tbody  > tr').each(function () {
        var totalNumberOfTd = $(this).children('td').length;
        if (totalNumberOfTd == selectTableMaxNumberOfTd){
            tableName = $(this).children('td').eq(0).html();
            optionValue = tableName + '.' + $(this).children('td').eq(1).html();
        }else if (totalNumberOfTd == selectTableMinNumberOfTd){
            optionValue = tableName + '.' + $(this).children('td').eq(0).html();
        }

        if ($(this).hasClass('zeroHeight') == false){
                if($(".selectBox #" + $(this).children('td.havingTd').find('div.betweenDiv .havingAggrFnSelection').val()).text() != 'NOT SELECT'){
                    var selectedHavingFn = $(".selectBox #" + $(this).children('td.havingTd').find('div.betweenDiv .havingAggrFnSelection').val()).text();
                    clause = clause + ' '
                        +selectedHavingFn.replace(')',' '+optionValue+') ')
                        + ' ' + $(".selectBox #" + $(this).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv .havingCondition').val()).text()
                        + ' ' + $(this).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv .freeText').val()
                        + ' ' + ($(".selectBox #" + $(this).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv .havingConditionExt').val()).text() == '.' ? ' ': $(".selectBox #" + $(this).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv .havingConditionExt').val()).text());
                }
            }
        });
      return clause;
}
function selectColumnNameButtonClickAction(tableName,columnSelect,ruleObject){
    var existTr = false;
    existTr = $("#selectedColumnTable tr").hasClass(tableName);
    if (existTr == false) {
        counterForSelectedColumnTable++;
        var tr = '<tr class="' + tableName + '"><td rowspan="1" class="' + tableName + '">' + tableName + '</td><td class="'
            + columnSelect + ' filled success">' + columnSelect + '</td>'
            + '<td>'+getSelectBoxWithNewId('aggregateFn',counterForSelectedColumnTable,aggregateFunctionArray,ruleObject.aggregateFnValue)+'</td>'
            + '<td>'+(ruleObject.groupBySelect == true ? '<input type="checkbox" class="groupByCheckBox" checked>' : '<input type="checkbox" class="groupByCheckBox">')+ '</td>'
            + '<td class="havingTd"><div class="betweenDiv">'
            +  getSelectBoxWithNewId('havingAggrFnSelection',counterForSelectedColumnTable,aggregateFunctionArray,ruleObject.havingAggregateFn)
            +  '&nbsp;&nbsp;<div class="havingInnerDiv">'
            +  getSelectBoxWithNewId('havingCondition',counterForSelectedColumnTable,conditionSelectBoxArrayForHavingClause,'')
            +  '&nbsp;&nbsp;'
            +  '<input type="text" class="span4 freeText" value="' +ruleObject.havingCompareValue+ '"/>'
            +  '&nbsp;&nbsp;'
            +  getSelectBoxWithNewId('havingConditionExt',counterForSelectedColumnTable,signSelectBoxArrayForHavingClause,ruleObject.havingExtraCondition)
            +  '</div></div></td>'
            + '<td>'+(ruleObject.orderBySelect == true ? '<input type="checkbox" class="orderByCheckBox" checked>' : '<input type="checkbox" class="orderByCheckBox">')+ '</td>'
            + '<td><input type="button" class="ruleRemoveBtn"></td></tr>';
        $('#selectedColumnTable > tbody:last').append(tr);
        $('#aggregateFn' + counterForSelectedColumnTable).multiselect();
        $('#selectedColumnTable > tbody:last').append('<tr class="' + tableName + '_last zeroHeight"><td></td><td></td><td></td></tr>');

        $("#havingCondition"+counterForSelectedColumnTable).multiselect();
        $("#havingAggrFnSelection"+counterForSelectedColumnTable).multiselect();
        $("#havingConditionExt"+counterForSelectedColumnTable).multiselect();
        var selectItemOfHavingAggregateFn = $(".selectBox #" + $(tr).children('td.havingTd').find("div.betweenDiv #havingAggrFnSelection"+counterForSelectedColumnTable).val()).text();
        if(ruleObject.havingAggregateFn != 'NOT SELECT'){
            $(tr).children('td.havingTd').find('div.betweenDiv div.havingInnerDiv').show();
        }
        if(selectItemOfHavingAggregateFn == 'NOT SELECT'){
            $("#havingAggrFnSelection"+counterForSelectedColumnTable).parent('div').parent('td').find('div div.havingInnerDiv').hide();
        }

    } else if (existTr == true) {

        if ($('#selectedColumnTable > tbody tr.' + tableName + ' td.' + columnSelect).hasClass(columnSelect) == true) {
            $.prompt("You have already select " + tableName + '.' + columnSelect + ' .');
        } else {
            var rowSpanCount = 1;
            addTrForSelectColumn(tableName,columnSelect,rowSpanCount,ruleObject);
        }
    }
}

function ruleInitialize(){ // method to
    $("#selectClauseSubmitButton").click();
    var list = jSonObject; //come from ruleCreation.jsp page
    $.each(list, function( index, value ) {
    var ruleObject = value
    selectColumnNameButtonClickAction(ruleObject.tableName,ruleObject.columnName,ruleObject);
    });
    $("#selectedColumnTable").show();
    $("#selectColumnSubmitButton").click();
    $("#addCondition").click();

}


