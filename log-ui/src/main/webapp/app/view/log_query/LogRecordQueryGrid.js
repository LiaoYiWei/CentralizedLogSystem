 function onRenderCell(value, cell, record, rowIndex, colIndex, store){
    	
//    	cell.attr = 'style="white-space:normal;"'; 
    	cell.style = 'white-space:normal;'; 
    	cell.autosize = true;

    	var logLevel = '';
    	var level = record.data.severity;
    	switch(level){
    		case 0:
    			logLevel = 'TRACE';
    			break;
    		case 1:
    			logLevel = 'DEBUG';
    			break;
    		case 2:
    			logLevel = 'INFO';
    			break;
    		case 3:
    			logLevel = 'WARN';
    			break;
    		case 4:
    			logLevel = 'ERROR';
    			break;
    		default:
    			logLevel = 'FATAL';
    	};
    		
    	if(level!=null && level >= 3){
    		logLevel = '<font color="red">'+logLevel+'</font>';
    	}
    	var bodyString = '';
    	var BLANK = '&nbsp;&nbsp;&nbsp;';
    	
    	
    	 bodyString= Ext.Date.format(new Date(record.data.timestamp),'Y-m-d H:i:s.u');
    	 bodyString = bodyString +BLANK+'['+record.data.appName+']';
    	 bodyString = bodyString +BLANK+'['+record.data.env+']';
    	 bodyString = bodyString +BLANK+'['+record.data.nodeName+']';
//    			bodyString = bodyString +BLANK+'['+record.data.runId+']';
    			bodyString = bodyString +BLANK+'['+record.data.logSequence+']';
    			bodyString = bodyString +BLANK+'['+record.data.messageType+']';
    			bodyString = bodyString +BLANK+'['+record.data.threadName+']';
    	
    			if(record.data.attribute1Name!=null&&record.data.attribute1Name!=''){
    				bodyString = bodyString +BLANK+'['+record.data.attribute1Name+':'+record.data.attribute1Value+']';
    			}
    			if(record.data.attribute2Name!=null&&record.data.attribute2Name!=''){
    				bodyString = bodyString +BLANK+'['+record.data.attribute2Name+':'+record.data.attribute2Value+']';
    			}
    			if(record.data.attribute3Name!=null&&record.data.attribute3Name!=''){
    				bodyString = bodyString +BLANK+'['+record.data.attribute3Name+':'+record.data.attribute3Value+']';
    			}
    			if(record.data.attribute4Name!=null&&record.data.attribute4Name!=''){
    				bodyString = bodyString +BLANK+'['+record.data.attribute4Name+':'+record.data.attribute4Value+']';
    			}
    			if(record.data.attribute5Name!=null&&record.data.attribute5Name!=''){
    				bodyString = bodyString +BLANK+'['+record.data.attribute5Name+':'+record.data.attribute5Value+']';
    			}
    			
    			bodyString = bodyString +BLANK+logLevel;
    			bodyString = bodyString +BLANK+record.data.loggerName;
    			if(record.data.message != null && record.data.message != ''){
	    			var message = record.data.message.replace(/(^\s*)|(\s*$)/g, "");
	    			bodyString = bodyString +BLANK+BLANK +'-'+message;
    			}
    			if(record.data.throwableMessage!=null && record.data.throwableMessage!=''){
    				bodyString = bodyString + '<br/>' + record.data.throwableMessage;
    			}
    			
    	return bodyString;   	
    }
Ext.define('Log.view.log_query.LogRecordQueryGrid',{
	extend : 'Ext.grid.Panel',
	alias  : 'widget.logRecordQueryGrid',
		
    title: 'Log Query',
    autoScroll:false,
    forcefit: true,
    closable : true,
    header:false,
    time : 'undefined',
    specificTime : 'undefined',
    messageType	 : 'undefined',
    columns : [{
    	renderer :onRenderCell,
    	flex : 1
      }],

    initComponent : function(){
    	var store = this.store;
    	this.bbar=Ext.create('Ext.toolbar.Paging', {
    		store : store,
    		pageSize : 1000,
    		displayInfo : true,
    		displayMsg : 'Displaying topics {0} - {1} of {2}',
    		emptyMsg : "No log to display"
    	});   
		this.callParent(arguments);
    },
    listeners : {
    	'itemdblclick' : function(view, record, item, index){
    		var form = Ext.create('Log.view.log_query.DeepQueryForm',{
    				record : record,
    				anchor : '100% 100%',
    				time   : this.time,
    				specificTime : this.specificTime,
				});
    		var win = Ext.create('Ext.window.Window',{
    			title : 'Deep Query Logs',
    			modal:true,
    			id : 'winFurtherQuery',
    			layout : 'anchor',
    			width : 320,
    			height : 500,
    			items : form,
    			buttons:[{
    		        text: 'OK',
    		        formBind: true
    		       },{
    		    	   text : 'Cancel'

    		       }]
    		});
    		win.show();
    	}
    }
});