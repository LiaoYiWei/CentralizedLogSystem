Ext.define("Log.store.EnvAppNameStore",{
	extend:"Ext.data.Store",
	model:"Log.model.EnvAppName",
 	proxy:{
		type:"ajax",
		url:"web/queryEnvApp.log",
		
		reader:{
			type:"json"}
	}, 
	sorters: {
		property: 'appName', 
		direction: 'ASC'}
});