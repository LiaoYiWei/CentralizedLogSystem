Ext.define('Log.model.Task', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'id',               type: 'string'},
        	{name: 'taskName',               type: 'string'},
        	{name: 'appEnvName',               type: 'string'},
            {name: 'clientLogQueueConsumedSize',        type: 'int'},
            {name: 'clientLogQueueTotalSize',       type: 'int'},
            {name: 'logsPerSec',     type: 'int'},
            {name: 'sizePerSec',     type: 'int'},
            {name: 'avgLogsPerSec',     type: 'float'},
            {name: 'avgSizePerSec',     type: 'float'},
            {name: 'serverity',     type: 'string'},
            {name: 'activeStatus',     type: 'boolean'},
            {name: 'lastRuntimeInstanceStartDate',     type: 'string'}
        ]
    });