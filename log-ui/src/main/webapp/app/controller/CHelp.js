Ext.define("Log.controller.CHelp", {
			extend : "Ext.app.Controller",
			views : ['help.HelpPanel', 'common.window.DeskWindow'],
			stores : [],
			models : [],
			init : function() {
				this.control({
							'desktop toolbar button[id=help]' : {
								click : this.helpButtonClick
							}
						});
			},
			helpButtonClick : function(button) {
				Ext.Ajax.request({
							url : 'help.html',
							success : function(response) {
								if (null != response.responseText
										&& "" != response.responseText) {
									var helpWin = Ext.widget('deskWindow', {
												title : 'Help',
												width : 600,
												height: 280,
												items : [{
															html:response.responseText
														}]
											});
									helpWin.show();
								}
							}
						});

			}
		})