Ext.define("Log.store.email_conf.RuleStore", {
	extend : "Ext.data.Store",
	model : 'Log.model.Rule',
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : 'web/getRulesByEvnId.log',
		reader: {
            type: 'json'
        }
	}
});
