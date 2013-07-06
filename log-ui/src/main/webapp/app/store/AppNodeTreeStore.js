Ext.define('Log.store.AppNodeTreeStore', {
			extend : 'Ext.data.TreeStore',
			
			autoload : false,
			proxy : {
				type : 'ajax',
				url : 'web/appnode_query.log'
			},
			sorters : [{
						property : 'leaf',
						direction : 'ASC'
					}, {
						property : 'text',
						direction : 'ASC'
					}],
		});