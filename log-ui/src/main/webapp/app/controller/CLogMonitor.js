Ext.define('Log.controller.CLogMonitor', {
	extend : 'Ext.app.Controller',
	views : ['common.tree.AppNodeTree', 'common.combobox.LogLevelCmb',
			'log_monitor.WinMonitor', 'log_monitor.MonitorGrid',
			'common.window.DeskWindow'],
	models : ['LogEvent'],
	stores : ['AppNodeTreeStore', 'LogLevelStore', 'log_monitor.LogStore'],
	init : function() {
		this.control({
					'desktop toolbar button[id=monitorLogBtn]' : {
						click : this.monitorLog
					},
					'winMonitor button[action=save]' : {
						click : this.registerMonitor
					},
					'monitorGrid toolbar button[itemId=start]' : {
						click :this.startButtonClick
					},
					'monitorGrid toolbar button[itemId=stop]' : {
						click : this.stopButtonClick
					}
				});
	},
	monitorLog : function() {
		var view = Ext.widget('winMonitor', {
					id : 'winMonitor'
				});
		view.show();
	},
	registerMonitor : function(button, e) {
		var me = this;
		var tree = Ext.getCmp('mAppNodeTree');
		var cmb = Ext.getCmp('logLevleCmb');
		var logLevel = null;
		var logLevels = null;
		var logLevelValues = [0, 1, 2, 3, 4, 5];
		var records = tree.getView().getChecked(), nodeIds = [];

		Ext.Array.each(records, function(rec) {
					if (rec.get('leaf') != null && rec.get('leaf') == true) {
						nodeIds.push(rec.get('id'));
					}
				});
		if(nodeIds.length == 0){
			Ext.MessageBox.alert('WARING','You should choose at least one node! ');
			return;
		}
		logLevel = Ext.getCmp('logLevelCmb').getValue();
		if (null != logLevel) {
			logLevels = logLevelValues.slice(logLevel, logLevelValues.length);
		}
		var mGrid = Ext.widget('monitorGrid', {
					title : 'monitor',
					nodeIds : nodeIds,
					logLevels : logLevels,
					store:Ext.create('Log.store.log_monitor.LogStore')
				});
		Ext.getCmp('winMonitor').close();
		Ext.widget('deskWindow', {
					title : 'Monitor',
					items : [mGrid],
					listeners : {
						'close' : function() {
							me.stopMonitoring(mGrid);
						}
					}
				}).show();
		this.startMonitoring(mGrid);
	},
	startMonitoring : function(grid) {
		if (grid.nodeIds != null && grid.logLevels != null) {
			Ext.Ajax.request({
						url : 'web/monitor_do.log',
						params : {
							nodeIds : grid.nodeIds,
							logLevels : grid.logLevels
						},
						success : function(response) {
							grid.monitorId = response.responseText;
							console.log('after register:   ' + grid.monitorId);
						}
					});
			grid.task = Ext.TaskManager.start({
				run : function() {
					var thisObject = grid;
					var tempstore = thisObject.getStore();
					if (tempstore == null) {
						return;
					}
					if (thisObject.monitorId != null
							&& thisObject.monitorId != -1) {
						Ext.Ajax.disableCaching = true;
						Ext.Ajax.request({
							url : 'web/getLog_do.log',
							params : {
								monitorId : thisObject.monitorId
							},
							success : function(response) {
								if (response.responseText != null
										&& response.responseText != "") {
									var logs = Ext.JSON
											.decode(response.responseText);
									if (logs.length > 0) {
										if (tempstore.getCount() > 1000) {
											var itemArray = tempstore.data
													.getRange(0, logs.length
																	- 1);
											tempstore.data.removeAll(itemArray);
										}
										tempstore.loadData(logs, true);
										tempstore.sync();
										thisObject.getView().focusRow(tempstore
												.getCount()
												- 1);

									}
								}
							}
						});
					}
				},
				interval : 1000
			});
		}
	},
	stopMonitoring : function(grid) {
		var thisObject = grid;
		if (thisObject.task != null) {
			Ext.TaskManager.stop(thisObject.task);
		}
		Ext.Ajax.request({
					url : 'web/stopMonitoring_do.log',
					params : {
						monitorId : thisObject.monitorId
					},
					success : function(response) {
						if (response.responseText == 'success') {
							console.log('stop monitor successfully');
						}
					}
				});
	},startButtonClick : function(button){
		var grid = button.up('monitorGrid');
		var stopBtn = grid.query('button[itemId=stop]')[0];
		stopBtn.setIconCls('monitor_stop_normal');
		stopBtn.setDisabled(false);
		button.setDisabled(true);
		button.setIconCls('monitor_start_disabled');
		this.stopMonitoring(grid);
		this.startMonitoring(grid);			
		Ext.MessageBox.alert('INFO','Monitor has been restarted');
	},stopButtonClick : function(button){
		var grid = button.up('monitorGrid');
		var startBtn = grid.query('button[itemId=start]')[0];
		startBtn.setIconCls('monitor_start_normal');
		startBtn.setDisabled(false);
		button.setDisabled(true);
		button.setIconCls('monitor_stop_disabled');
		this.stopMonitoring(grid);	
		Ext.MessageBox.alert('INFO','Monitor has been stopped!');
	}
});