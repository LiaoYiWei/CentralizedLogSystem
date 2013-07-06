Ext.define("Log.controller.CScriptManager", {
	extend : "Ext.app.Controller",
	views : ["script_manager.FileGrid"],
	stores : ["script_manager.FileGridStore"],
	models : ["FileGridModel"],
	init : function() {
		this.getGrid = function(button) {
			return button.ownerCt.ownerCt;
		};
		this.control({
			'desktop toolbar button[id=groovyBtn]' : {
				click : this.groovyFile
			},
			"filegrid button[text=download]" : {
				click : function(button) {
			    var grid = this.getGrid(button);
					var store = grid.getStore();
					var records = grid.getSelectionModel().getSelection();
					var data = [];
					Ext.Array.each(records, function(model) {
								data.push(model.get("appId"));
							});
					if (data.length > 0) {
			   			 document.location.href = ("web/downloadInstance.log?appId=" + records[0].data["appId"] + "&runCode=" + Math.random());
					}else {
						Ext.Msg.alert("message", "must choose a record!");
					}
				}
			},
			"filegrid button[text=upload]" : {
				click : function(button) {
					var grid = this.getGrid(button);
					var store = grid.getStore();
					var records = grid.getSelectionModel().getSelection();
					 var winSave = null;
					 var uploadForm = null;
					var idStr = records[0].data["appId"];
					
					uploadForm = Ext.create('Ext.form.Panel', {   
				        width: 400,   
				        bodyPadding: 10,   
				        frame: true, 
				        buttonAlign:'center',
				        items: [{   
				            xtype: 'filefield',   
				            name: 'file',   
				            fieldLabel: 'File',   
				            labelWidth: 50,   
				            msgTarget: 'side',   
				            allowBlank: false,   
				            anchor: '100%',   
				            buttonText: 'Select a File...'  
				        }],   
				    
				        buttons: [{   
				            text: 'Upload',   
				            handler: function() {   
				                var form = this.up('form').getForm();   
				                if(form.isValid()){   
				                    form.submit({   
				                        url: 'web/upload_do.log?appId='+idStr,   
				                        waitMsg: 'Uploading your file...',   
				                        success: function(fp, o) {   
				                            Ext.Msg.alert('Success', 'Your file has been uploaded.',function(btn) {
				                            if (btn == 'ok') {
				                                winSave.hide();
				                                store.reload();
				                            }
				                    		});   
				                        },
						                failure: function(){
						                	Ext.MessageBox.hide();
						 	                Ext.Msg.alert('message', "check the network");
						                }   
				                    });   
				                }   
				            }   
				        }, new Ext.Button({
				                text: 'cancel',
				                handler: function (){
				                    winSave.hide();
				                }
				            })]   
				    });   

					winSave = Ext.create('Ext.window.Window', {
					    title: 'upload file',
					    height: 100,
					    width: 430,
					    layout: 'fit',
					    items: uploadForm
					});
					winSave.show();					
				}
			}
		});
	},
	groovyFile: function(){
				if(Ext.ComponentQuery.query('filegrid').length==0){
					var win = Ext.widget('deskWindow',{
						title: 'Script Manager'
					});
					var fileGridStore = Ext.data.StoreManager.lookup('script_manager.FileGridStore');
					win.add({
				        xtype: 'filegrid',
				        border: false,
				        store:fileGridStore
					});
					win.show();	
					var myMask = new Ext.LoadMask(win,{
						msg:"Please wait...",
						store:fileGridStore
					});
					fileGridStore.load();
					
					//myMask.show();
				}
			}
});