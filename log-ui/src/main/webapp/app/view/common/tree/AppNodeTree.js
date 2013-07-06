Ext.define('Log.view.common.tree.AppNodeTree', {
			extend : 'Ext.tree.Panel',
			alias : 'widget.appNodeTree',
			title : 'Application Node',
			width:'200',
			height:'300',
			anchor: '100% -25',
			autoShow : true,
			autoScroll : true,
			collapsible : true,
			rootVisible : false,
			header:false,
			useArrows : true,
			//store : Ext.create('Log.store.AppNodeTreeStore'),
			store: 'AppNodeTreeStore', 
			
//			initComponent : function(){
//				var datastore = this.getStore();
//				//this.store = Ext.create('Log.store.AppNodeTreeStore');
//				var loadMask = new Ext.LoadMask(this, {  
//				    msg     : 'loading data .... ',  
//				    disabled    : false,  
//				    store   : datastore
//				});  
//				this.callParent(arguments);
//			},
			
			
			dockedItems : [{
						xtype : 'toolbar',
						items : [{
									text : 'Expand All',
									handler : function() {
										var tree = this.up('treepanel');
										tree.mask('Expanding tree...');
										var toolbar = this.up('toolbar');
										toolbar.disable();
										tree.expandAll(function() {
													tree.unmask();
													toolbar.enable();
												});
									}
								}, {
									text : 'Collapse All',
									handler : function() {
										var tree = this.up('treepanel');
										var toolbar = this.up('toolbar');
										toolbar.disable();

										tree.collapseAll(function() {
													toolbar.enable();
												});
									}
								}]
					}],
			listeners : {
				checkchange : {
					fn : function(node, checked) {
						var updateCheckStatus = function(parentNode, checked) {
							Ext.each(parentNode.childNodes, function(childNode,
											index, allItems) {
										childNode.set('checked', checked);
										updateCheckStatus(childNode, checked);
									});
						};
						updateCheckStatus(node,checked);
					}

				}, 
				
				beforerender: {
					fn: function (comp, eOpts) {
						var loadMask = new Ext.LoadMask(this, {  
						    msg     : 'loading data...',  
						    disabled    : false,  
						    store   : this.getStore()  
						});  
//						loadMask.show();
						//log.console("on active");
					}
				}
			}
		});