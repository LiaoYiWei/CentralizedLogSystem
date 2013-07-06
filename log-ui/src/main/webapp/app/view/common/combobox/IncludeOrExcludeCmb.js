Ext.define('Log.view.common.combobox.IncludeOrExcludeCmb', {
			extend : 'Ext.form.ComboBox',
			alias : 'widget.includeOrExcludeCmb',
			displayField : 'name',
			valueField : 'abbr',
			store : Ext.create('Ext.data.Store', {
						fields : ['abbr', 'name'],
						data : [{
									"abbr" : 0,
									"name" : "INCLUDE"
								}, {
									"abbr" : 1,
									"name" : "EXCLUDE"
								}]
					})

		});