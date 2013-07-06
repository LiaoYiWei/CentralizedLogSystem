Ext.define('Log.view.email_conf.EnvRulesGrid', {
	extend : 'Ext.grid.Panel',
	alias : "widget.evnRulesGrid",
	header : false,
	title : 'Application States',
	multiSelect : false,
	store : 'email_conf.RuleStore',
	columns : [{
				header : 'RuleName',
				sortable : true,
				width : 200,
				dataIndex : 'ruleName'
			}, {
				header : 'SuppressionTime',
				sortable : true,
				dataIndex : 'suppressionTime',
				renderer : function(value) {
					return value + 'h';
				}
			}, {
				header : 'EmailPDL',
				width : 300,
				sortable : true,
				dataIndex : 'emailPdl'
			}, {
				xtype : 'actioncolumn',
				width : 30,
				resizable : false,
				sortable : false,
				items : [{
					icon : 'resources/icons/button/delete.gif',
					tooltip : 'Delete Rule',
					handler : function(grid, rowIndex, colIndex) {
						Ext.Msg.show({
							title : 'Info',
							msg : 'Do you want delete it(Y/N)?',
							buttons : Ext.Msg.YESNO,
							fn : function(btn, text) {
								if (btn == 'yes') {
									var model = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : 'web/deleteRule.log',
										params : {
											model : Ext.JSON.encode(model.data)
										},
										success : function(response) {
											if (null != response.responseText
													&& "" != response.responseText) {
												Ext.MessageBox.alert('WARING',
														response.responseText);
											}
											grid.getStore().load();
										},
										failure : function(response) {
											if (null != response.responseText
													&& "" != response.responseText) {
												Ext.MessageBox.alert('WARING',
														response.responseText);
											}
											grid.getStore().load();
										}

									});
								}
							}
						});

					}
				}]
			}],
	tbar : [{
				text : 'Add Rule',
				action : 'add',
				icon : 'resources/icons/button/add.png'
			}]
});