Ext.define('Log.view.task_manager.WinTaskEdit', {
			extend : 'Ext.window.Window',
			alias : 'widget.winTaskEdit',
			layout : {
				type : 'vbox',
				padding : '5',
				align : 'stretch'

			},
			logLevel:'0',
			resizable : false,
			modal : true,
			initComponent : function() {
				var combo = Ext.widget('combo', {
							name : 'Loglevel',
							fieldLabel : 'Log Level',
							store : Ext.create('Ext.data.Store', {
										fields : ['abbr', 'name'],
										data : [{
													"abbr" : "0",
													"name" : "TRACE"
												}, {
													"abbr" : "1",
													"name" : "DEBUG"
												}, {
													"abbr" : "2",
													"name" : "INFO"
												}, {
													"abbr" : "3",
													"name" : "WARN"
												}, {
													"abbr" : "4",
													"name" : "ERROR"
												}, {
													"abbr" : "5",
													"name" : "FATAL"
												}, {
													"abbr" : "6",
													"name" : "DISABLE"
												}]
									}),
							displayField : 'name',
							valueField : 'abbr'
						});
				combo.setValue(this.logLevel);
				this.items = [combo];
				this.buttons = [{
							text : 'OK',
							action : 'save'
						}, {
							text : 'Cancel',
							scope : this,
							handler : this.close
						}];
				this.callParent();
			}
		});