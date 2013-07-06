Ext.define("Log.view.common.window.DeskWindow", {
			extend : "Ext.window.Window",
			alias : "widget.deskWindow",
			title: null,
		    height: 500,
		    width: 800,
		    minimizable: true,
	        maximizable: true,
	        constrain:true,
	        y:20,
	        //constrainHeader: true,
		    layout: 'fit',
		    button:null,
		    initComponent : function() {
		    	var text = this.title;
		    	var toolbar = Ext.getCmp('toolbarbb');
		    	var container = Ext.getCmp('desktop');
		    	container.add(this);
				this.button = Ext.create('Ext.button.Button', {
				    text    : text,
				    scope   : this,
				    handler : function(button, event) {
				    	Ext.WindowManager.bringToFront(this);
				    	if(this.isHidden()){
				    		this.show();
				    	}
						//else{
				    	//	this.hide();
				    	//}
				    }
				});
				toolbar.add(this.button);
				this.callParent(arguments);
				//this.render(Ext.getDom('desktop-body'));
			},
		    minimize: function() {
		    	this.hide();
		    },
		    close:function(){
		    	this.fireEvent('close', this);
		    	var toolbar = Ext.getCmp('toolbarbb');
		    	var container = Ext.getCmp('desktop');
		    	toolbar.remove(this.button,true);
		    	container.remove(this,true);
		    	this.destroy();
		    }
})