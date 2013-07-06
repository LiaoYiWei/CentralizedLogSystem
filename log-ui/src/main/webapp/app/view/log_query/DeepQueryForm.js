function initAdditionalAttr(fName, fValue, attrName, attrValue) {
	var additionalField = Ext.create('Ext.form.FieldContainer', {
				layout :{
					type:'column',
					manageOverflow:0
				},
				width:300,
				heigth:10,
				items : [{
							xtype : 'deepQueryCheckbox',
							columnWidth : 0.05
						}, {
							xtype : 'textfield',
							columnWidth : 0.90,
							fieldName : attrName,
							fieldValue : fValue,
							name : fName,
							value : attrValue,
							fieldStyle : 'color:grey',
							fieldLabel : attrName,
							labelWidth : 113
						}]
			});
	return additionalField;
}
function initAttrFields(record, items) {
	if (record.data.attribute1Name != null && record.data.attribute1Name != '') {
		items.push(initAdditionalAttr('attribute1Name', 'attribute1Value',
				record.data.attribute1Name, record.data.attribute1Value));
	}
	if (record.data.attribute2Name != null && record.data.attribute2Name != '') {
		items.push(initAdditionalAttr('attribute2Name', 'attribute2Value',
				record.data.attribute2Name, record.data.attribute2Value));
	}
	if (record.data.attribute3Name != null && record.data.attribute3Name != '') {
		items.push(initAdditionalAttr('attribute3Name', 'attribute3Value',
				record.data.attribute3Name, record.data.attribute3Value));
	}
	if (record.data.attribute4Name != null && record.data.attribute4Name != '') {
		items.push(initAdditionalAttr('attribute4Name', 'attribute4Value',
				record.data.attribute4Name, record.data.attribute4Value));
	}
	if (record.data.attribute5Name != null && record.data.attribute5Name != '') {
		items.push(initAdditionalAttr('attribute5Name', 'attribute5Value',
				record.data.attribute5Name, record.data.attribute5Value));
	}
};

// load log information
function initLogInfo(boxId, textId, textName, lableName, valueInfo, textType) {
	if (lableName == null || lableName == '') {
		return null;
	}
	var attrName = Ext.create('Ext.form.FieldContainer', {
				xtype : 'fieldcontainer',
				layout :{
					type:'column',
					manageOverflow:0
				},
				width:300,
				heigth:10,
				items : [{
							xtype : 'deepQueryCheckbox',
							columnWidth : 0.05
						}, {
							xtype : textType,
							name : textName,
							fieldStyle : 'color:grey',
							id : textId,
							fieldLabel : lableName,
							readOnly : true,
							value : valueInfo,
							labelWidth : 113,
							columnWidth : 0.90
						}]
			});
	return attrName;
}

// load specific combox
function initSpecComb(boxId, valueInfo, combox) {
	var fieldCombox = Ext.create('Ext.form.FieldContainer', {
				xtype : 'fieldcontainer',
				layout :{
					type:'column',
					manageOverflow:0
				},
				width:300,
				heigth:10,
				items : [{
							xtype : 'deepQueryCheckbox',
							columnWidth : 0.05
						}, combox]
			});
	return fieldCombox;
}

Ext.define('Log.view.log_query.DeepQueryCheckbox', {
			extend : 'Ext.form.field.Checkbox',
			alias : 'widget.deepQueryCheckbox',
			name : 'logCheckbox',
			style : 'color:grey',
			margin : '0 5 0 0',
			listeners : {
				'change' : function(box, newValue) {
					var field = box.nextSibling();
					if (newValue) {
						field.setReadOnly(false);
						field.setFieldStyle('color:black');
					} else {
						field.setReadOnly(true);
						field.setFieldStyle('color:grey');
					}
				}
			}
		});

Ext.define('Log.view.log_query.DeepQueryForm', {
			extend : 'Ext.form.Panel',
			alias : 'widget.deepQueryForm',
			header : false,
			//layout : 'fit',
			border : false,
			autoScroll : true,
			autoShow : true,
			id : 'formId',
			bodyStyle : "padding: 8px; background-color: transparent;",
			record : 'undefined',
			initComponent : function() {
				var logEvent = this.record;

				var levelCom = Ext.widget('logLevelCmb', {
							id : 'LevleCmbId',
							name : 'logLevel',
							fieldStyle : 'color:grey'
						});
				levelCom.setEditable(false);
				levelCom.setReadOnly(true);
				var level = logEvent.data.severity;
				levelCom.setValue(level);

				var mTypeCmb = Ext.widget('messageTypeCmb', {
							id : 'qMessageTypeCmb',
							name : 'messageType',
							value : '' + logEvent.data.messageType,
							fieldStyle : 'color:grey',
							readOnly : true,
							columnWidth : 0.90

						});

				this.items = [
						initLogInfo('appBox', 'appText', 'appName', 'App Name',
								logEvent.data.appName, 'textfield'),
						initLogInfo('envBox', 'envText', 'envName', 'Env Name',
								logEvent.data.env, 'textfield'),
						initLogInfo('nodeBox', 'nodeText', 'nodeName',
								'Node Name', logEvent.data.nodeName,
								'textfield'),
						initLogInfo('threadBox', 'threadText', 'threadName',
								'Thread Name', logEvent.data.threadName,
								'textfield'),
						initSpecComb('messageTypeBox', '', mTypeCmb),
						initSpecComb('levelBox', level, levelCom),
						initLogInfo('loggerBox', 'loggerText', 'loggerName',
								'Logger Name', logEvent.data.loggerName,
								'textfield'),
						initLogInfo('msgBox', 'msgText', 'message', 'Message',
								logEvent.data.message, 'textareafield'),
						initLogInfo('thwMsgBox', 'thwMsgText',
								'throwableMessage', 'Error Message',
								logEvent.data.throwableMessage, 'textareafield')];
				initAttrFields(logEvent, this.items);
				this.callParent(arguments);
			}
		});
