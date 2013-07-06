function renderLog(value, metadata, record) {
	var BLANK = ' ';
	metadata.style = 'white-space:normal';
	metadata.autoSize = true;
	var logLevel = '';
	var level = record.data.severity;
	switch (level) {
		case 0 :
			logLevel = 'TRACE';
			break;
		case 1 :
			logLevel = 'DEBUG';
			break;
		case 2 :
			logLevel = 'INFO';
			break;
		case 3 :
			logLevel = 'WARN';
			break;
		case 4 :
			logLevel = 'ERROR';
			break;
		default :
			logLevel = 'FATAL';
	};
	if (level != null && level >= 3) {
		logLevel = '<font color="red">' + logLevel + '</font>';
	}
	var bodyString = '';
	bodyString = Ext.Date.format(new Date(parseFloat(record.data.timestamp)),
			'Y-m-d H:i:s.u');
	bodyString = bodyString + BLANK + '[' + record.data.appName + ']';
	bodyString = bodyString + BLANK + '[' + record.data.nodeName + ']';
	// bodyString = bodyString + BLANK + '[' + record.data.runId + ']';
	bodyString = bodyString + BLANK + '[' + record.data.env + ']';
	bodyString = bodyString +BLANK+'['+record.data.logSequence+']';
	bodyString = bodyString +BLANK+'['+record.data.messageType+']';
	bodyString = bodyString + BLANK + '[' + record.data.threadName + ']';
	if (record.data.attribute1Name != null && record.data.attribute1Name != '') {
		bodyString = bodyString + BLANK + '[' + record.data.attribute1Name
				+ ':' + record.data.attribute1Value + ']';
	}
	if (record.data.attribute2Name != null && record.data.attribute2Name != '') {
		bodyString = bodyString + BLANK + '[' + record.data.attribute2Name
				+ ':' + record.data.attribute2Value + ']';
	}
	if (record.data.attribute3Name != null && record.data.attribute3Name != '') {
		bodyString = bodyString + BLANK + '[' + record.data.attribute3Name
				+ ':' + record.data.attribute3Value + ']';
	}
	if (record.data.attribute4Name != null && record.data.attribute4Name != '') {
		bodyString = bodyString + BLANK + '[' + record.data.attribute4Name
				+ ':' + record.data.attribute4Value + ']';
	}
	if (record.data.attribute5Name != null && record.data.attribute5Name != '') {
		bodyString = bodyString + BLANK + '[' + record.data.attribute5Name
				+ ':' + record.data.attribute5Value + ']';
	}
	bodyString = bodyString + BLANK + logLevel;
	bodyString = bodyString + BLANK + record.data.loggerName;
	bodyString = bodyString + BLANK + '-&nbsp;&nbsp;' + record.data.message;
	if(record.data.throwableMessage!=null&&record.data.throwableMessage!=''){
				bodyString = bodyString + '<br/>' + record.data.throwableMessage;
			}
	return bodyString
}
Ext.define('Log.view.log_monitor.MonitorGrid', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.monitorGrid',
			nodeIds : [],
			logLevels : [],
			monitorId : -1,
			task : -1,
			closable : false,
			header:false,
			//store : 'log_monitor.LogStore',
			
			autoScroll : true,
			columns : [{
						text : "id",
						width : 0,
						dataIndex : 'id',
						hidden : true,
						sortable : true
					}, {
						text : "log",
						flex : 1,
						renderer : renderLog,
						sortable : true
					}],
			// override
			initComponent : function() {
				//this.startMonitoring();
				this.callParent();
			},
			tbar : [{
						tooltip : 'restart monitor',
						itemId : 'start',
						disabled : true,
						iconCls :'monitor_start_disabled'
					}, {
						tooltip	 : 'stop monitor',
						itemId : 'stop',
						iconCls :'monitor_stop_normal'
					}]
		});