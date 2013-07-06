Ext.define('Log.view.Desktop', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.desktop',
			title : 'Centralized Log System',
			titleAlign: 'center',
			header:false,
			bodyCls:'desktopBg',
			//layout : 'fit',
			//autoShow : true,
			//modal : true,
			initComponent : function() {
				this.dockedItems= [{
									xtype: 'toolbar',
									dock: 'top',
									items: [{
												id: 'searchLogBtn',
												//text: 'Search Log',
												tooltip:'search log',
												xtype: 'button',
												cls: 'search72',
												height:72,
												width:72,
												margin :'0 20 0 0'
												
											},{
											    id: 'monitorLogBtn',
												//text: 'Monitor Log',
											    tooltip:'monitor log',
												xtype: 'button',
												//iconCls: 'add16',
												cls:'monitor72',
												height:72,
												width:72,
												margin :'0 20 0 0'
											}
											/*,{
											    id: 'monitorLoadBtn',
												//text: 'Monitor Load',
											    tooltip:'monitor load',
												xtype: 'button',
												//iconCls: 'add16',
												cls: 'monitorLoad72',
												height:72,
												width:72,
												margin :'0 20 0 0'
											}*/,{
											    id: 'emailNotifierBtn',
												//text: 'Email Cfg',
											    tooltip:'email configuration',
												xtype: 'button',
												//iconCls: 'add16',
												cls: 'emailCfg72',
												height:72,
												width:72,
												margin :'0 20 0 0'
												
											},{
											    id: 'taskManager',
												//text: 'Traffic Manager',
											    tooltip:'traffic manager',
												xtype: 'button',
												//iconCls: 'add16',
												cls: 'traffic72',
												height:72,
												width:72,
												margin :'0 20 0 0'
												
											},{
											    id: 'groovyBtn',
												//text: 'Script Manager',
											    tooltip:'Script Manager',
												xtype: 'button',
												//iconCls: 'add16',
												cls: 'script72',
												height:72,
												width:72,
												margin :'0 20 0 0'
												
											},{
												id :'help',
												//text: 'About',
											    tooltip:'about',
												//iconCls: 'add16',
												xtype: 'button',
												cls: 'help72',
												height:72,
												width:72,
												margin :'0 20 0 0'
//												menu: [
//												         {text: 'Help'},
//														 {text: 'About Product' }, 
//														 {text: 'About us' }
//												]
												
											}]
											
								},{
									xtype: 'toolbar',
									id:'toolbarbb',
									dock: 'bottom',
									items: ['-']
								}];
				this.callParent(arguments);
			}
			
		});