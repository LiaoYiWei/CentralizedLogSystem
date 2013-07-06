function appEnvsCell(value, cell, record, rowIndex, colIndex, store){
	var BLANK = ' ';
	cell.style = 'white-space:normal';
	cell.autoSize = true;
	var envStr = record.data.appName + BLANK + record.data.envName;
	return envStr;
}

Ext.define('Log.view.email_conf.EmailEnvGrid',{	
	extend : 'Ext.grid.Panel',
	alias  : 'widget.emailEnvGrid',
	height	: 460,
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'top',

	    items: [{
	        xtype: 'textfield',
	        id : 'emailAppFilter',
	        fieldLabel: 'App',
	        labelPad: 2,
	        labelWidth: 40,
	        margin: '10 10 10 10'
	    }]			
	}],
	columns : [
		{	text	  : 'appName',
			flex	  : 1,
			dataIndex : 'appName'},
		{	text	  : 'envName',
			flex 	  : 1,
			dataIndex : 'envName'	
			},],
	})