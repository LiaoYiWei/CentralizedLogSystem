Ext.define('Log.view.email_conf.EmailEnvWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.winEmail',
			title : 'Email Config',
			autoShow : true,
			modal : true,
			layout : 'fit',
			width : 360,
			height : 500,
			buttons : [{
				text : 'OK',
				action : 'save'
			}, {
				text : 'Cancel',
				action : 'cancel'
			}]
})