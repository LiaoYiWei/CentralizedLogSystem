Ext.Loader.setConfig({
			enable : true
		});

Ext.application({
			requires : ['Ext.container.Viewport'],
			name : 'Log',
			appFolder : 'app',
			controllers : ['CMenu', 'CLogQuery', 'CLogMonitor',
					'CScriptManager', 'CTaskManager','CEmailConfig','CHelp'],
			launch : function() {
				if(typeof Ext != 'undefined'){
				  Ext.core.Element.prototype.unselectable = function(){return this;};	
				  Ext.view.TableChunker.metaRowTpl = [	
				   '<tr class="' + Ext.baseCSSPrefix + 'grid-row {addlSelector} {[this.embedRowCls()]}" {[this.embedRowAttr()]}>',	
				    '<tpl for="columns">',	
				     '<td class="{cls} ' + Ext.baseCSSPrefix + 'grid-cell ' + Ext.baseCSSPrefix + 'grid-cell-{columnId} {{id}-modified} {{id}-tdCls} {[this.firstOrLastCls(xindex, xcount)]}" {{id}-tdAttr}><div class="' + Ext.baseCSSPrefix + 'grid-cell-inner ' + Ext.baseCSSPrefix + 'unselectable" style="{{id}-style}; text-align: {align};">{{id}}</div></td>',	
				    '</tpl>',	
				   '</tr>'
				  ];
				 } ;
				Ext.create('Ext.container.Viewport', {
							layout : 'fit',
							items : [{
								// title: 'Centralized Log System',
								// titleAlign: 'center',
								id:     'desktop', 
								xtype : 'desktop'
									/*
									 * dockedItems: [{ xtype: 'toolbar', dock:
									 * 'top', items: [{ id: 'searchLogBtn',
									 * text: 'Search Log', iconCls: 'add16'
									 * 
									 * },{ id: 'monitorLogBtn', text: 'Monitor
									 * Log', iconCls: 'add16', menu: [{text:
									 * 'Cut Menu Item'}] },{ id:
									 * 'monitorLoadBtn', text: 'Monitor Load',
									 * iconCls: 'add16' },{ id:
									 * 'emailNotifierBtn', text: 'Email Cfg',
									 * iconCls: 'add16',
									 * 
									 * },{ id: 'groovyBtn', text: 'Groovy
									 * Control', iconCls: 'add16', }, '-',{
									 * xtype:'splitbutton', text: 'About',
									 * iconCls: 'add16', menu: [ {text: 'Help'},
									 * {text: 'About Product' }, {text: 'About
									 * us' } ] }] }]
									 */
									// html : '<h1 class="x-panel-header">Log
									// FrameWork</h1>',
								}]

						});
			}
		});