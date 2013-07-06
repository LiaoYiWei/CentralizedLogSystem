Ext.define('Log.controller.CEmailConfig', {
			extend : 'Ext.app.Controller',
			views : ['email_conf.EmailEnvGrid', 'email_conf.EmailEnvWindow',
					'email_conf.EnvRulesGrid', 'email_conf.WinEditRule',
					'common.combobox.IncludeOrExcludeCmb',
					'common.combobox.LogLevelCmb'],
			models : ['EnvAppName', 'Rule'],
			stores : ['EnvAppNameStore', 'email_conf.RuleStore'],
			init : function() {
				this.control({
							'desktop toolbar button[id=emailNotifierBtn]' : {
								click : this.emailConfig
							},
							'#emailAppFilter' : {
								change : this.queryStrChange
							},
							'evnRulesGrid toolbar button[action=add]' : {
								click : this.addRuleButtonClick
							},
							'evnRulesGrid' : {
								itemdblclick : this.evnRulesGridItemdbClick
							},
							'winEditRule button[action=save]' : {
								click : this.winEditRuleSaveButtonClick
							},
							'winEditRule button[action=cancel]' : {
								click : this.winEditRuleCancelButtonClick
							},
							'winEmail button[action=save]' : {
								click : this.emailEnvWindowSaveButtonClick
							},
							'winEmail button[action=cancel]' : {
								click : this.emailEnvWindowCancelButtonClick
							}
						});
			},
			emailConfig : function() {
				var store = Ext.create('Log.store.EnvAppNameStore');
				store.load();
				Ext.widget('winEmail', {
							id : 'emailConfigWindow',
							items : [{
										xtype : 'emailEnvGrid',
										anchor : '100%',
										flex : 1,
										store : store
									}]
						}).show();
			},
			queryStrChange : function(textField, newValue, oldValue, eOpts) {
				var emailEnvGrid = textField.up("emailEnvGrid");
				var store = emailEnvGrid.getStore();
				store.clearFilter(true);
				store.filter([{
							filterFn : function(item) {
								var searchStr = newValue;
								return item.get("appName").indexOf(searchStr.toLowerCase()) != -1 || item.get("appName").indexOf(searchStr.toUpperCase()) != -1;
							}
						}]);

			},
			addRuleButtonClick : function(button) {
				var grid = button.up('evnRulesGrid');
				var record = Ext.create('Log.model.Rule', {
							envId : grid.envId
						});
				var editWin = Ext.widget('winEditRule', {
							title : 'Add Rule',
							width : 400,
							grid : grid
						});
				editWin.down('form').loadRecord(record);
				editWin.show();
			},
			evnRulesGridItemdbClick : function(grid, record) {
				var editWin = Ext.widget('winEditRule', {
							title : 'Edit Rule',
							width : 400,
							grid : grid
						});
				editWin.down('form').loadRecord(record);
				editWin.show();
			},
			winEditRuleSaveButtonClick : function(button) {
				var win = button.up('window'), form = win.down('form'), model = form
						.getRecord(), values = form.getValues();
				model.set(values);
				var s = '';
				if (!form.getForm().isValid()) {
//					Ext.iterate(form.getValues(), function(key, value) {
//								s += Ext.util.Format.format("{0} = {1}<br />",
//										key, value);
//							}, this);
//
//					Ext.Msg.alert('Form Values', s);
					return
				}
				// var errors = model.validate();
				// if (errors.getCount() > 0) {
				// errors.each(function(item) {
				// alert(item);
				// });
				// return;
				// }
				// model.setProxy(new Ext.data.proxy.Ajax({
				// url : 'web/createOrUpdateRule.log'
				// }));
				Ext.Ajax.request({
							url : 'web/createOrUpdateRule.log',
							params : {
								model : Ext.JSON.encode(model.data)
							},
							success : function(response) {
								if (null != response.responseText
										&& "" != response.responseText) {
									Ext.MessageBox.alert('WARING',
											response.responseText);
								}
								win.grid.getStore().load();
							},
							failure : function(response, opts) {
								if (null != response.responseText
										&& "" != response.responseText) {
									Ext.MessageBox.alert('WARING',
											response.responseText);
								}
								win.grid.getStore().load();
							}

						});
				win.close();

			},
			winEditRuleCancelButtonClick : function(button) {
				var win = button.up('window');
				win.close();
			},
			emailEnvWindowSaveButtonClick : function(button) {
				var win = button.up('window');
				var grid = win.down('emailEnvGrid');
				var record = grid.getSelectionModel().getSelection()[0];
				if (null == record) {
					Ext.MessageBox.alert('WARING',
							'You should choose at least one node! ');
				}
				var store = Ext.create('Log.store.email_conf.RuleStore');
				var envRulesGrid = Ext.widget('evnRulesGrid', {
							store : store,
							envId : record.data.envId
						});
				var myMask = new Ext.LoadMask(envRulesGrid, {
							store : store,
							msg : "Please wait..."
						});
				store.on('beforeload', function() {
							store.proxy.extraParams = {
								envId : record.data.envId
							};
						});
				store.load();
				Ext.widget('deskWindow', {
							width : 660,
							height : 400,
							title : record.data.appName + '-'
									+ record.data.envName,
							items : [envRulesGrid]
						}).show();
				win.close();
			},
			emailEnvWindowCancelButtonClick : function(button) {
				var win = button.up('window');
				win.close();
			}
		});