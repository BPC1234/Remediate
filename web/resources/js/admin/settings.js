var userId=0;
var optionIndex=1;
var options = "";
$(document).ready(function () {

//    User list grid start
    $("#userTableId").flexigrid({
        url : 'getUserJASON.html',
        dataType : 'json',
        colModel : [ {
            display : 'Name',
            name : 'userName',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Role',
            name : 'role',
            width : 'auto',
            sortable : true,
            align : 'left'
        }, {
            display : 'Active',
            name : 'userActiveCheckBoxHtml',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'userEditButtonHtml',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : '',
            name : 'userDeleteButtonHtml',
            width : 'auto',
            sortable : false,
            align : 'center'
        }, {
            display : 'Backend data',
            name : 'id',
            width : 1,
            hide : true,
            align : 'center'
        }],
        buttons : [{ name: '#userTableId', bclass: 'ADD'}],
        searchitems : [ {
            display : 'Control',
            name : 'name'
        }, {
            display : 'Transaction Type',
            name : 'transactionType',
            isdefault : true
        } ],
        sortname : "user_name",
        sortorder : "asc",
        usepager : true,
        title : '',
        useRp : true,
        rp : 10,
        showTableToggleBtn : true,
        height:300 ,
        onSuccess: function () {
            addGrid($('#userTableId'), this);  //PATCH to get the grids to be responseive
        },
        getGridClass: function (g) { //PATCH to get the grids to be responseive
            this.g = g; //Is this the only way to expose the grid class ?
            return g;
        }
    });


    $(document).on('click', '.ADD', function() {
        $('#modalOpenLinkForUser').trigger('click');
        $("#fullname").val("");
        $("#password").val("");
        $("#passwordConf").val("");
        $("#email").val("");
        $("#activeUser").prop('checked', false);
    });

    $(document).on('click', '#saveUser', function() {

        var getUrl = "addUser.html?id=" + userId;
        $.ajax({
            type:'post',
            url:getUrl,
            data: $("#projectForm").serialize(),
            async:false,
            success:function (data) {
                if(data.length > 0){
                for(var i=0; i < data.length; i++){
                    var obj = data[i];
                    console.log(obj.fieldName+"  msg:"+obj.message);

                    $("."+obj.fieldName).show();
                    $("."+obj.fieldName).text(obj.message);
                }
                }else{
                var saveOrUpdate ;
                if(userId > 0)
                    saveOrUpdate = 'User Updated '
                else
                    saveOrUpdate = 'User Saved '

                $('#addNewUser').modal('toggle');
                $("#userTableId").flexReload();
                showMessageByForce("#userListPageId","alert-success",saveOrUpdate);
                userId=0;
            }

      }
        });
    });

/*

    $(document).on('click', '.userEditButtonCss', function() {

        userId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "getUser.html?userId=" + userId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#fullname").val($(data).attr("userName"));
                $("#password").val($(data).attr("password"));
                $("#email").val($(data).attr("email"));
                if($(data).attr("active")) {
                    $("#activeUser").prop('checked', true);
                } else {
                    $("#activeUser").prop('checked', false);
                }
            }


        });

        // document.addConForm.submit();
    });

*/


    $(document).on('click', '#userDeleteButtonHtml', function() {
        var userId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "userDelete.html?id=" + userId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                showMessageByForce("#userListPageId","alert-error","User Deleted ");
                $("#userTableId").flexReload();
            }


        });
    });

    $(document).on('click', '#userEditBtn', function() {

        userId = $(this).parent().parent().parent().children(":last").children(":last").text();
        var getUrl = "getUser.html?userId=" + userId;
        $.ajax({
            type:'get',
            url:getUrl,
            async:false,
            success:function (data) {
                $("#fullname").val($(data).attr("userName"));
                $("#userRole").val($(data).attr("role"))
                $("#email").val($(data).attr("email"));
                $("#password").val($(data).attr("password"));
                $("#passwordConf").val($(data).attr("password"));
                $("#autoCompleteBoxForUser").val($(data).attr("employeeName"));
                $(".userTypeId").val($(data).attr("userTypeId"));

                if($(data).attr("active")) {
                    $("#activeUser").prop('checked', true);
                } else {
                    $("#activeUser").prop('checked', false);
                }
                $('#modalOpenLinkForUser').trigger('click');
            }


        });
    });

    $('#autoCompleteBoxForUser').typeahead({
        source: function (query, process) {
            states = [];
            map = {};
            var data = JSON.parse($("#jasonValue").val());
            $.each(data, function (i, state) {
                map[state.empName] = state;
                states.push(state.empName);
            });

            process(states);
        },
        updater: function (item) {
            $(".userTypeId").val(map[item].empId);
//            empNameForAutoComplete = map[item].empName;
            return item;
        },
        matcher: function (item) {
            $(".userTypeId").val(0);
            $(".userTypeId").hide();
            if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) === 0) {
                return true;
            }
        },
        sorter: function (items) {
            return items.sort();
        },
        highlighter: function (item) {
            var regex = new RegExp( '(' + this.query + ')', 'gi' );
            return item.replace( regex, "<strong>$1</strong>" );
        }
    });

    $(document).on('keypress', '#fullname', function() {
     $(".userName").hide();
    });
});


