$(document).ready(function () {


    $("#policyDetailsTable").flexigrid({
        url: 'getJASONforPolicyDetails.html?policyType='+$("#policyType").val()
            +'&tableName=policyDetailsTable',
        dataType: 'json',
        colModel: [
            {
                display: 'Policy Name',
                name: 'documentName',
                width: 'auto',
                sortable: true,
                align: 'left'
            },{
                display: 'Policy Type',
                name: 'policyType',
                width: 'auto',
                sortable: true,
                align: 'left'
            },
            {
                display: 'Implemented Date',
                name: 'entryDate',
                width: 'auto',
                sortable: true,
                align: 'left'
            },{
                display: 'Notification went out Date',
                name: 'notificationWentOutDateStr',
                width: 'auto',
                sortable: true,
                align: 'left'
            },{
                display: 'No. of mployees(Confirmed)',
                name: 'noOfEmployeeConfirmed',
                width: 'auto',
                sortable: true,
                align: 'left'
            },{
                display: 'No. of mployees(Unconfirmed)',
                name: 'noOfEmployeeUnconfirmed',
                width: 'auto',
                sortable: true,
                align: 'left'
            },
            {
                display: 'Backend Data',
                name: 'id',
                width: 0,
                align: 'right',
                sortable: true,
                hide: true
            }
        ],
        searchitems: [
            {
                display: 'Policy Name',
                name: 'documentName'
            },{
                display: 'Policy Type',
                name: 'policyType'
            },{
                display: 'Implemented Date',
                name: 'entryDate'
            }
        ],
        buttons: [
            { name: '#policyDetailsTable', bclass: 'PDF', onpress: printTable },
            { name: '#policyDetailsTable', bclass: 'XLS', onpress: toExcel },
            { name: '#policyDetailsTable', bclass: 'CSV', onpress: tableToCSV },
            { name: '#policyDetailsTable', bclass: 'RESET', onpress: resetTableData },
            { name: '#policyDetailsTable', bclass: 'GROUPING', onpress: groupByView }
        ],
        sortname: "documentName",
        sortorder: "asc",
        usepager: true,
        title: '',
        useRp: true,
        rp: 10,
        showTableToggleBtn: true,
        height: 280,
        onSuccess: function () {
            addGrid($('#policyDetailsTable'), this);  //PATCH to get the grids to be responseive

        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });
});


