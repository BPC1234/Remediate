function isNumber(num) {
	return (/(^[0-9]+(\.[0-9]+)?$)/.test(num));
}

function isNotNumber(num) {
	return !isNumber(num);
}

function isName(name) {
	//return (/(^[a-zA-Z ]+[a-zA-Z0-9 ]+$)/.test(name));
	var firstChar=name.charAt(0);
	if((firstChar>='a'&& firstChar<='z')||(firstChar>='A' && firstChar<='Z')){
		if(isAlphaNumeric(name)) return true;
	}
}

function isAlphaNumeric(an) {
	return (/(^[0-9a-zA-Z ]+$)/.test(an));
}

function isNotAlphaNumeric(an) {
	return !isAlphaNumeric(an);
}


 function loadGrid (fields, url, root, customCol, height, width, renderBlock){

    	Ext.define(root, {
            extend: 'Ext.data.Model',
            fields: fields 
 		});
    	
    	var store_model = new Ext.data.Store({
            model: root,
            proxy: {
                type: 'ajax',
                url: url,
                reader: {
                    type: 'json',
                    root: root
                }
            }
  		});
    	
    	var grid_model = Ext.create('Ext.grid.Panel', {
            store: store_model,
            columns: customCol,
            height: height,
            width: width,
            renderTo: renderBlock,
            loadMask: true,
            scroll: false,
            viewConfig: {
                stripeRows: true,
                autoScroll: true,
                getRowClass: function(record, index, rowParams, store) {
                    return 'break-text';
                }
            },
            listeners: {
                itemclick : function() {
			//empty
                }
            }
        });

    	store_model.load();
 }
 
 function loadPagingGrid (fields, url, root, customCol, height, renderBlock, title, displayMessage, emptyMsg, clickAction){
	   	Ext.define(root, {
						 extend: 'Ext.data.Model'
						,fields: fields 
	   	});
	   	
	   	var store_model = new Ext.data.Store({
	            model: root
	           ,pageSize: 10
	           ,proxy: {
	               type: 'ajax',
	               url: url,
	               reader: {
	                   type: 'json',
	                   root: root
	               }
	           }
	 		});
	   	
	   	var grid_model = Ext.create('Ext.grid.GridPanel', {
	            store: store_model
	           ,columns: customCol
	           ,columnLines: true
	           ,title:title
	           ,height: height
	          // ,width: 600
	           ,renderTo: renderBlock
	           ,loadMask: true
	           ,scroll: false
	           ,viewConfig: {
	                stripeRows: true
	               ,autoScroll: true
	               ,forceFit: true
	               ,getRowClass: function(record, index, rowParams, store) {
	                   return 'break-text';
	               }
	           }
	           ,listeners: {
	                itemclick : function() {/*empty*/}
	               ,itemdblclick: clickAction
	           }
	           ,bbar:new Ext.PagingToolbar({ 
	        	   store: store_model,
	        	   displayInfo: true,
	               displayMsg: displayMessage,
	               emptyMsg: emptyMsg,
	           })
	       });

	   	store_model.load({params:{start:0, limit:10}});
	   	if ($("#ext-gen1043").length > 0) {
	   		document.getElementById('ext-gen1043').style.width="25px";
        }
}
 
 function deleteLinkClicked(url){
	 if(confirm("Are you sure ?")){
		 window.location = url;
	 }
 }
 
 function addError(obj, msg) {
     $(obj).addClass('errorinput');
     $(obj).parent().next().html(msg);
     return true;
 }
 
 function clearErrors(){
	$("input, select").removeClass('errorinput');
    $(".inputerrormsg").html('');       		
 }
