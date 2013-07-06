Ext.define("Log.store.task_manager.TaskStore", {
	extend : "Ext.data.Store",
	requires : [ 'Log.model.Task' ],
	model : 'Log.model.Task',
	autoLoad : false,
	// proxy:{
	// type:"ajax",
	// url:"web/app_load.log",
	// reader:{
	// type:"json",
	// root:"root",
	// totalProperty :'totalProperty'
	// },
	// writer:{
	// type:"json"
	// }
	// }
	proxy : {
		type : 'ajax',
		url : 'web/getTask.log',
		reader: {
            type: 'json'
        }
	},
	 sorters: {property: 'taskName', direction: 'ASC'},
     groupField: 'appEnvName'
});
