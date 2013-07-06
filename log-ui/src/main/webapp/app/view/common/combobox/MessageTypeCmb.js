Ext.define('Log.view.common.combobox.MessageTypeCmb',{
			alias  : 'widget.messageTypeCmb',
			extend : 'Ext.form.ComboBox',		
			fieldLabel : 'Message Type',
			queryMode : 'local',
			displayField : 'name',
			valueField : 'attr',
			editable : 'false',
			store : 'MessageTypeStore',
			labelWidth:113
});