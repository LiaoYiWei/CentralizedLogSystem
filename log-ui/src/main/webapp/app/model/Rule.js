Ext.define('Log.model.Rule', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'ruleId',               type: 'string' , defaultValue: null},
        	{name: 'ruleName',               type: 'string'},
        	{name: 'suppressionTime',               type: 'int',defaultValue: 1},
            {name: 'emailPdl',        type: 'string'},
            {name: 'severity',       type: 'int', defaultValue: 0},
            {name: 'messageType',     type: 'string'},
            {name: 'throwableMessage',     type: 'string'},
            {name: 'message',     type: 'string'},
            {name: 'throwMsgStatus',     type: 'int', defaultValue: 0},
            {name: 'msgStatus',     type: 'int', defaultValue: 0},
            {name: 'envId',     type: 'string'},
            {name: 'appName',     type: 'string'},
            {name: 'envName',     type: 'string'},
            {name: 'createTime',     type: 'string'},
            {name: 'updateTime',     type: 'string'}
        ],
        validations: [
        {type: 'presence',  field: 'ruleName'},
        {type: 'presence',  field: 'emailPdl'},
        {type: 'length',    field: 'ruleName', max : 100},
        {type: 'length',    field: 'message', max : 500},
//      {type: 'inclusion', field: 'gender',   list: ['Male', 'Female']},
//      {type: 'exclusion', field: 'username', list: ['Admin', 'Operator']},
        {type: 'format',    field: 'emailPdl', matcher: /(([\w0-9]{1,50})@([\w0-9]{1,10}.[\w]{1,3}),?)+/}
    ]
    });