$(document).ready(function() {
    $('table').each(function(){
        var tableID = $(this).attr('id');
        if(tableID != "undefined"){
            var rows=$(this).find('tbody tr').length;
            var no_rec_per_page=5;
            var no_pages= Math.ceil(rows/no_rec_per_page);
            var $pagenumbers=$('<div id="pages"></div>');
            $('<span class="page prevFirst" style="text-decoration:underline;">Prev</span>').appendTo($pagenumbers);
            for(i=0;i<no_pages;i++)
            {
                if(i == 0)
                    $('<span class="page selectedPagination">'+(i+1)+'</span>').appendTo($pagenumbers);
                else
                    $('<span class="page">'+(i+1)+'</span>').appendTo($pagenumbers);
            }
            $('<span class="page nextLast" style="text-decoration:underline;">Next</span>').appendTo($pagenumbers);
            $pagenumbers.insertAfter('#'+tableID);
            $('.page').hover(
                function(){
                    $(this).addClass('hover');
                },
                function(){
                    $(this).removeClass('hover');
                }
            );
            $('#'+tableID).find('tbody tr').hide();
            var tr=$('#'+tableID+' tbody tr');
            for(var i=0;i<=no_rec_per_page-1;i++)
            {
                $(tr[i]).show();
            }
            //Add user in every pagination
            $(tr[rows-1]).show();

            if(no_pages == '1')
                $('#'+tableID+' +#pages').hide();

            var linkNo = $('#'+tableID+' +#pages .selectedPagination').text();
            //	alert(linkNo);
            if(linkNo == '1')
                $('#'+tableID+' +#pages .prevFirst').hide();
            else
                $('#'+tableID+' +#pages .prevFirst').show();

            if(linkNo == no_pages)
                $('#'+tableID+' +#pages .nextLast').hide();
            else
                $('#'+tableID+' +#pages .nextLast').show();


            $('#'+tableID+' +#pages .page').click(function(event){
                var linkNo = $('#'+tableID+' +#pages .selectedPagination').text();

                var this_no_pages = no_pages;
                //alert(this_no_pages);
                var text = $(this).text();
                //alert(text);
                if(text == "Prev"){
                    linkNo--;
                    var nextLink = $('#'+tableID+' +#pages .selectedPagination').text();
                    nextLink = parseInt(nextLink,10);

                    if(nextLink > 1){
                        var this_link = $('#'+tableID+' +#pages .selectedPagination');
                        this_link.removeClass('selectedPagination');
                        this_link.prev().addClass('selectedPagination');

                        var spanTableId = $(this).parent().prev().attr('id');
                        $('#'+spanTableId).find('tbody tr').hide();
                        for(var i=(nextLink-2)*no_rec_per_page;i<(nextLink-1)*no_rec_per_page;i++)
                        {
                            $(tr[i]).show();
                        }
                    }
                }else if(text == "Next"){
                    linkNo++;
                    var nextLink = $('#'+tableID+' +#pages .selectedPagination').text();
                    nextLink = parseInt(nextLink,10);
                    if(nextLink < this_no_pages){
                        var this_link = $('#'+tableID+' +#pages .selectedPagination');
                        this_link.removeClass('selectedPagination');
                        this_link.next().addClass('selectedPagination');

                        var spanTableId = $(this).parent().prev().attr('id');
                        $('#'+spanTableId).find('tbody tr').hide();
                        for(i=(nextLink)*no_rec_per_page;i<(nextLink+1)*no_rec_per_page;i++)
                        {
                            $(tr[i]).show();
                        }
                    }
                }else{
                    linkNo = text;
                    var spanTableId = $(this).parent().prev().attr('id');
                    $('#'+spanTableId).find('tbody tr').hide();
                    for(i=($(this).text()-1)*no_rec_per_page;i<=$(this).text()*no_rec_per_page-1;i++)
                    {
                        $(tr[i]).show();
                    }
                    //$('#'+tableID+" .page").removeClass('selectedPagination');
                    var this_link = $('#'+tableID+' +#pages .selectedPagination');
                    this_link.removeClass('selectedPagination');

                    $(this).addClass('selectedPagination');
                }

                if(linkNo == '1')
                    $('#'+tableID+' +#pages .prevFirst').hide();
                else
                    $('#'+tableID+' +#pages .prevFirst').show();

                if(linkNo == no_pages)
                    $('#'+tableID+' +#pages .nextLast').hide();
                else
                    $('#'+tableID+' +#pages .nextLast').show();

                //Add user in every pagination
                $(tr[rows-1]).show();
            });

        }
    });
});

