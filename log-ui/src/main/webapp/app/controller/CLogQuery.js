Ext.define('Log.controller.CLogQuery', {
	extend : 'Ext.app.Controller',
	views : ['common.tree.AppNodeTree', 'common.combobox.LogLevelCmb',
			'common.combobox.MessageTypeCmb', 'common.combobox.TimeCateriaCmb',
			'log_query.WinQuery', 'log_query.LogRecordQueryGrid',
			'log_query.DeepQueryForm', 'common.window.DeskWindow'],
	models : ['LogEvent'],
	stores : ['AppNodeTreeStore', 'LogLevelStore', 'LastTimeStore',
			'MessageTypeStore', 'log_query.LogRecordQueryStore'],
	init : function() {
		this.control({
					'desktop toolbar button[id=searchLogBtn]' : {
						click : this.searchLog
					},
					'winQuery button[action=submit]' : {
						click : this.queryLogs
					},
					'#winFurtherQuery button[text=OK]' : {
						click : this.furtherQueryLogs
					},
					'#winFurtherQuery button[text=Reset]' : {
						click : this.resetFurtherForm
					},
					'#winFurtherQuery button[text=Cancel]' : {
						click : this.closeFurtherWin
					}
				});
	},
	searchLog : function() {
		var view = Ext.widget('winQuery', {
					id : 'winQuery'
				});
		// var treeStore = Ext.getCmp('qAppNodeTree').getStore();
		// treeStore.getRootNode().removeAll(true);
		// treeStore.load();
		view.show();
	},
	resetFurtherForm : function(button) {
		button.up('window').down('form').getForm().reset();
	},
	closeFurtherWin : function(button) {
		button.up('window').close();
	},
	furtherQueryLogs : function(button) {
		var win = button.up('window');
		var form = button.up('window').down('form').getForm();
		var fields = new Array();
		fields = form.getFields().items;

		var store = Ext.create('Log.store.log_query.LogRecordQueryStore');
		var furtherQueryGrid = Ext.create(
				'Log.view.log_query.LogRecordQueryGrid', {
					store : store
				});
		var myMask = new Ext.LoadMask(furtherQueryGrid, {
					store : store,
					msg : "Please wait..."
				});
		store.on('beforeload', function() {
			store.proxy.extraParams = {
				time : form.time,
				specificTime : form.specificTime,
				messageType : form.messageType
			};
			Ext.Array.each(fields, function(field, index) {
						if ('logCheckbox' == field.getName() && field.checked) {
							var text = field.nextSibling();
							var tmpProxy = store.proxy;

							var textName = text.getName();
							var ifAttrField = false;
							if (textName.indexOf('attribute') >= 0) {
								ifAttrField = true;
							}
							if (ifAttrField) {
								tmpProxy.extraParams[text.name] = text.fieldName;
								tmpProxy.extraParams[text.fieldValue] = text.value;
							} else {
								tmpProxy.extraParams[text.name] = text.value;
							}
						};
					});
		});
		store.load({
					params : {
						start : 0,
						limit : 1000
					}
				});
		win.close();
		Ext.widget('deskWindow', {
					title : 'LogQuery',
					items : [furtherQueryGrid]
				}).show();
	},

	queryLogs : function() {
		var lastTime = Ext.getCmp('time1').getValue();
		var specificTime = Ext.getCmp('date1').getValue();
		var messageType = Ext.getCmp('qMessageTypeCmb').getValue();
		var loglevel = Ext.getCmp('qLogLevleCmb').getValue();
		var checkedText = Ext.getCmp('qAppNodeTree').getView().getChecked(), nodeIds = [];
		var win = Ext.getCmp('winQuery');
		Ext.Array.each(checkedText, function(rec) {
					if (rec.get('leaf') != null && rec.get('leaf') == true) {
						nodeIds.push(rec.get('id'));
					}
				});
		if(nodeIds.length == 0){
			Ext.MessageBox.alert('WARING','You should choose at least one node! ');
			return;
		}
		var store = Ext.create('Log.store.log_query.LogRecordQueryStore');
		var queryGrid = Ext.create('Log.view.log_query.LogRecordQueryGrid', {
					store : store,
					time : lastTime,
					specificTime : specificTime,
					messageType : messageType
				});
		store.on('beforeload', function() {
					store.proxy.extraParams = {
						time : lastTime,
						specificTime : specificTime,
						logLevel : loglevel,
						messageType : messageType,
						nodes : nodeIds
					};
				});
		var myMask = new Ext.LoadMask(queryGrid, {
					store : store,
					msg : "Please wait..."
				});
		store.load({
					params : {
						start : 0,
						limit : 1000
					}
				});

		win.close();
		Ext.widget('deskWindow', {
					title : 'LogQuery',
					items : [queryGrid]
				}).show();

	}
});