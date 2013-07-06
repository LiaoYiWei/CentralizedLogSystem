Ext.define('Log.store.LogLevelStore', {
			extend : 'Ext.data.Store',
			fields : ['abbr', 'name'],
			data : [{
						"abbr" : 0,
						"name" : "TRACE"
					}, {
						"abbr" : 1,
						"name" : "DEBUG"
					}, {
						"abbr" : 2,
						"name" : "INFO"
					}, {
						"abbr" : 3,
						"name" : "WARN"
					}, {
						"abbr" : 4,
						"name" : "ERROR"
					}, {
						"abbr" : 5,
						"name" : "FATAL"
					}]
		})