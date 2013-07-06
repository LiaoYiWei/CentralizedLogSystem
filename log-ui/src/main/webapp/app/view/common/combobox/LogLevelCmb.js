Ext.define('Log.view.common.combobox.LogLevelCmb', {
			extend : 'Ext.form.ComboBox',
			alias : 'widget.logLevelCmb',
			fieldLabel : 'Log Level',
			queryMode : 'local',
			displayField : 'name',
			valueField : 'abbr',
			editable : 'false',
			store : 'LogLevelStore',
			labelWidth:113
		});