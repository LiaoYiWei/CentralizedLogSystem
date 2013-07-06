Ext.define('Log.store.MessageTypeStore', {
			extend : 'Ext.data.Store',
			fields : ['name', 'attr'],
			data : [{
					"name" : "ALL",
					"attr" : ""
				},{
						"name" : "LOG_SYSTEM",
						"attr" : "LOG_SYSTEM"
					}, {
						"name" : "REPORT_APP",
						"attr" : "REPORT_APP"
					},]
		})