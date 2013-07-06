Ext.define('Log.view.log_query.WinQuery', {
			extend : 'Ext.window.Window',
			alias : 'widget.winQuery',
			title : 'Query Logs',
			layout : {
				type : 'fit',
				padding : '5'
			},
			autoShow : true,
			modal : true,
			width : 300,
			height : 500,
			initComponent : function() {
				var combo = Ext.widget('logLevelCmb', {
							id : 'qLogLevleCmb',
							anchor : '100%'
						});
				combo.setValue(0);

				var mTypeCmb = Ext.widget('messageTypeCmb', {
							id : 'qMessageTypeCmb',
							value : '',
							anchor : '100%'
						});

				this.items = [{
							xtype : 'fieldset',
							layout : 'anchor',

							bodyStyle : "background-color: transparent;",
							items : [mTypeCmb, {
										xtype : 'timeCateriaCmb',
										id : 'qTimeCateriaCmb',
										anchor : '100%'
									}, combo, {
										id : 'qAppNodeTree',
										xtype : 'appNodeTree',
										anchor : '100% -110'
									}]
						}];

				this.buttons = [{
							text : 'OK',
							action : 'submit'
						}, {
							text : 'Cancel',
							scope : this,
							handler : function() {
								Ext.getCmp('winQuery').close();
							}
						}];

				this.callParent(arguments);
			}
		});