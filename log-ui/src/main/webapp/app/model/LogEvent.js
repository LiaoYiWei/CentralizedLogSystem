Ext.define('Log.model.LogEvent',{
	extend: 'Ext.data.Model',
	alias : 'widget.logEvent',
	fields:[
			{name: 'id', mapping: 'id'},
			{name: 'severity', mapping: 'severity'},
			{name: 'message', mapping: 'message'},
			{name: 'runId', mapping: 'runId'}, 
			{name: 'throwableMessage', mapping: 'throwableMessage'},
			{name: 'messageType', mapping: 'messageType'}, 
			{name: 'loggerName', mapping: 'loggerName'},
			{name: 'threadName', mapping: 'threadName'}, 
			{name: 'appName', mapping: 'appName'},
			{name: 'env', mapping: 'env'},
			{name: 'timestamp', mapping: 'timestamp'},
			{name: 'extraProps', mapping: 'extraProps'},
			{name: 'nodeName', mapping: 'nodeName'},
			{name: 'logSequence', mapping: 'logSequence'},
			{name: 'messageType', mapping: 'messageType'},
			
			{name: 'attribute1Name', mapping: 'attribute1Name'},
			{name: 'attribute2Name', mapping: 'attribute2Name'},
			{name: 'attribute3Name', mapping: 'attribute3Name'},
			{name: 'attribute4Name', mapping: 'attribute4Name'},
			{name: 'attribute5Name', mapping: 'attribute5Name'},
			
			{name: 'attribute1Value', mapping: 'attribute1Value'},
			{name: 'attribute2Value', mapping: 'attribute2Value'},
			{name: 'attribute3Value', mapping: 'attribute3Value'},
			{name: 'attribute4Value', mapping: 'attribute4Value'},
			{name: 'attribute5Value', mapping: 'attribute5Value'}]
});