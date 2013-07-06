Ext.define('Log.view.log_monitor.WinMonitor', {
			extend : 'Ext.window.Window',
			alias : 'widget.winMonitor',
			title : 'Select monitor',
			layout : 'fit',
			autoShow : true,
			modal : true,
			width : 300,
			height:500,
			initComponent : function() {
				var combo = Ext.widget('logLevelCmb', {
							id : 'logLevelCmb',
							anchor : '100%'
						});
				combo.setValue(0);
				this.items = [{
							layout : {
								type : 'anchor',
								padding : '5'
							},
							bodyStyle : "background-color: transparent;",
							items : [combo, {
										id : 'mAppNodeTree',
										xtype : 'appNodeTree'
									}]
						}], this.buttons = [{
							text : 'OK',
							action : 'save'
						}, {
							text : 'Cancel',
							scope : this,
							handler : this.close
						}];

				this.callParent(arguments);
			}
		});