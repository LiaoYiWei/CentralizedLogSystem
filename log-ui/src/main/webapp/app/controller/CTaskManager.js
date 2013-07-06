Ext.define('Log.controller.CTaskManager', {
			extend : 'Ext.app.Controller',
			views : ['task_manager.TaskGridPanel', 'task_manager.WinTaskEdit'],
			models : ['Task'],
			stores : ['task_manager.TaskStore'],
			task : null,
			init : function() {
				this.control({
							'desktop toolbar button[id=taskManager]' : {
								click : this.taskManager
							},
							'taskGridPanel' : {
								itemdblclick : this.taskGridPanelItemdblclick
							},
							'winTaskEdit button[action=save]' : {
								click : function(button, e, eOpts) {
									var win = button.up('winTaskEdit');
									var nodeId = win.nodeId;
									var logLevel = win.down('combo').getValue();

									Ext.Ajax.disableCaching = true;
									Ext.Ajax.request({
												url : 'web/changLogLevel.log',
												params : {
													nodeId : nodeId,
													logLevel : logLevel
												},
												success : function(response) {
													win.close();
													Ext.MessageBox.alert('INFO','Change successfully!');
												}
											});
								}
							}, 
							'#appQueryField' : {
								 change: this.queryStrChange
								
							}, 
							'#activeSelectRadio' : {
								 change: this.activeChange
							}
						});
			},
			taskManager : function() {
				var me = this;
				if (Ext.ComponentQuery.query('taskGridPanel').length == 0) {
					var taskGridPanel = Ext.widget('taskGridPanel');
					var winTask = Ext.widget('deskWindow', {
								title : 'Traffic Manager',
								width : 1000,
								items : [taskGridPanel],
								listeners : {
									'close' : function() {
										Ext.TaskManager.stop(me.task);
									}
								}
							});
					var store = taskGridPanel.getStore();
					

					me.task = Ext.TaskManager.start({
								run : function() {
									store.load();
								},
								interval : 5000
							});
					winTask.show();
				}
			},
			taskGridPanelItemdblclick : function(view, record, item, index, e,
					eOpts) {
				var win = Ext.widget('winTaskEdit', {
							title : record.data.taskName,
							nodeId : record.data.id,
							logLevel: record.data.serverity
						});
				win.show();
			}, 
			queryStrChange: function ( textField, newValue,oldValue, eOpts ) {
				
				var taskGridPanel = textField.up("taskGridPanel");
				var store = taskGridPanel.getStore();
				store.clearFilter(true);
				store.filter([{
              		filterFn: function(item) {
              			var searchStr = newValue;
              			//alert(searchStr);
              			return item.get("appEnvName").indexOf(searchStr) != -1; 
              	     }
              	}, {
              		filterFn: function(item) {
              			var isChecked = Ext.ComponentQuery.query('#activeSelectRadio')[0].getValue();
              			if(isChecked){
              				return item.get("activeStatus");
              			}
              			else {
              				return true;
              			}
              				
              		}
              		
              	 }]);
				
			 }, 
			 activeChange: function( checkboxField, newValue,oldValue, eOpts ) {
				 var taskGridPanel = checkboxField.up("taskGridPanel");
				 var store = taskGridPanel.getStore();
				 store.clearFilter(true);
				 store.filter([{
	              		filterFn: function(item) {
	              			var searchStr = Ext.ComponentQuery.query('#appQueryField')[0].getValue();
	              			//alert(searchStr);
	              			return item.get("appEnvName").indexOf(searchStr) != -1; 
	              	     }
	              	}, {
	              		filterFn: function(item) {
	              			var isChecked = newValue;
	              			if(isChecked){
	              				return item.get("activeStatus");
	              			}
	              			else {
	              				return true;
	              			}
	              				
	              		}
	              		
	              	 }]);
				 
			 }
		});