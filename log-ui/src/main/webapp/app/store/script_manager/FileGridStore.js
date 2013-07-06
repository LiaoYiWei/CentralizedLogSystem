Ext.define("Log.store.script_manager.FileGridStore",{
	extend:"Ext.data.Store",
	model:"Log.model.FileGridModel",
 	proxy:{
		type:"ajax",
		url:"web/app_load.log",
		reader:{
			type:"json"
		
	// root:"root",
		// totalProperty :'totalProperty'
		},
		writer:{
			type:"json"
		}
	}
, sorters: {property: 'appName', direction: 'ASC'}
});