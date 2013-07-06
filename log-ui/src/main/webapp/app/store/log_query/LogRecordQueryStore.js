Ext.define('Log.store.log_query.LogRecordQueryStore',{
	 extend : 'Ext.data.Store',
	 pageSize: 1000,
     remoteSort: true,
     proxy: {
         // load using script tags for cross domain, if the data in on the same domain as
         // this page, an HttpProxy would be better
         type	: 'ajax',
         url	: 'web/query_do.log',
         model	: 'Log.model.LogEvent',
         timeout : 120000,
         actionMethods:{read:'POST'},
         reader:{
        	 type : 'json',
        	 totalProperty : 'totalCount',
        	 root	: 'events'
         }
     },
     sorters: [{
         property: 'appName',
         direction: 'DESC'
     }]
})