Ext.define('Log.view.common.combobox.TimeCateriaCmb', {
			extend : 'Ext.form.FieldContainer',
			alias : 'widget.timeCateriaCmb',
			title : 'Time Cateria',
			collasible : true,
			layout : 'fit',
			initComponent : function() {
				var store = Ext.create('Log.store.LastTimeStore');

				this.items = [{
							xtype : 'fieldcontainer',
							bodyStyle : "background-color: transparent;",
							layout : 'column',
							items : [{
										xtype : 'radiofield',
										name : 'timeCateria',
										id : 'radio1',
										columnWidth: 0.05,
										checked : true,
										handler : function() {
											var radio1 = Ext.getCmp('radio1');
											var date1 = Ext.getCmp('date1');
											var time1 = Ext.getCmp('time1');
											if (radio1.getValue()) {
												date1.reset();
												date1.setDisabled(true);
												time1.setDisabled(false);
												time1.setValue('15');
											} else {
												time1.clearValue();
												time1.setDisabled(true);
												date1.setDisabled(false);
												date1.setValue(new Date());
											}
										}
									}, {
										xtype : 'combobox',
										columnWidth: 0.95,
										id : 'time1',
										fieldLabel : 'Last Time',
										displayField : 'name',
										valueField : 'timeValue',
										editable : 'false',
										store : store,
										value : '15'
									}]
						}, {
							xtype : 'fieldcontainer',
							bodyStyle : "background-color: transparent;",
							layout : 'column',
							items : [{
										xtype : 'radiofield',
										name : 'timeCateria',
										id : 'radio2',
										columnWidth: 0.05,
										handler : function() {
											var radio1 = Ext.getCmp('radio1');
											var date1 = Ext.getCmp('date1');
											var time1 = Ext.getCmp('time1');
											if (radio1.getValue()) {
												date1.reset();
												date1.setDisabled(true);
												time1.setDisabled(false);
												time1.setValue('15');
											} else {
												time1.clearValue();
												time1.setDisabled(true);
												date1.setDisabled(false);
												date1.setValue(new Date());
											}
										}
									}, {
										xtype : 'datefield',
										fieldLabel : 'Specific Time',
										columnWidth: 0.95,
										disabled : true,
										id : 'date1'

									}]
						}];
				this.callParent(arguments);
			}
		})