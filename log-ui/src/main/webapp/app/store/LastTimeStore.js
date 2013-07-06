Ext.define('Log.store.LastTimeStore', {
			extend : 'Ext.data.Store',
			fields : [{name : 'timeValue',type : 'String'},
			          {name : 'name',type : 'String'}],
			data :
				[{name : '15m',	timeValue : '15'},
				 {name : '30m',	timeValue : '30'},
				 {name : '1h',	timeValue : '60'},
				 {name : '2h',	timeValue : '120'},
				 {name : '4h',	timeValue : '240'},
				 {name : '8h',	timeValue : '480'},
				 {name : '12h',	timeValue : '720'},
				 {name : '16h',	timeValue : '960'},
				 {name : '20h',	timeValue : '1200'},
				 {name : '1d',	timeValue : '1440'}
				]
		})