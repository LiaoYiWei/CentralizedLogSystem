Ext.define("Log.view.script_manager.FileGrid", {
			extend : "Ext.grid.Panel",
			alias : "widget.filegrid",
			selModel : {
				selType : "checkboxmodel"
			},
			border : 0,
			frame:true,
			scroll:true,
			loadMask:true,
			tbar: [
			  { xtype: 'button', text: 'download'},'-', { xtype: 'button', text: 'upload'}
			],
			columns : [{
						text:"App ID",
						dataIndex:"appId",
						width:200,
						field : {
							xtype : "textfield"
						}
					},{
						text : "App Name",
						dataIndex : "appName",
						width : 200,
						field : {
							xtype : "textfield"
						}
					}],
			initComponent : function() {
				this.callParent(arguments);
			}
		});