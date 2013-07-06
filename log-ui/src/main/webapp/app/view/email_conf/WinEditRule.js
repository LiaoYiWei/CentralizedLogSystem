String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}
function componentLostFocusEvent(component) {
	var value = component.getValue() + "";
	component.setValue(value.trim());
}
Ext.define('Log.view.email_conf.WinEditRule', {
	extend : 'Ext.window.Window',
	alias : 'widget.winEditRule',
	defaultType : 'textfield',
	modal : true,
	layout : 'fit',
	items : [{
		xtype : 'form',
		bodyStyle : "background-color: transparent;",
		items : [{
					xtype : 'fieldset',
					layout : {
						type : 'table',
						columns : 1
					},
					margin : '5 7 10 5',
					frame : true,
					items : [{
								xtype : 'textfield',
								fieldLabel : 'Rule Name',
								maxLength : 50,
								msgTarget : 'under',
								name : 'ruleName',
								allowBlank : false,
								listeners : {
									blur : componentLostFocusEvent
								}
							}, {
								xtype : 'logLevelCmb',
								fieldLabel : 'log level',
								name : 'severity',
								labelWidth : 100
							}, {
								xtype : 'numberfield',
								fieldLabel : 'suppression time',
								name : 'suppressionTime',
								value : 1,
								maxValue : 24,
								minValue : 1,
								allowBlank : false,
								msgTarget : 'under',
								maxLength : 2
							}]
				}, {
					xtype : 'fieldset',
					layout : {
						type : 'table',
						// The total column count must be
						// specified here
						columns : 2
					},
					margin : '5 7 10 5',
					frame : true,
					items : [{
								xtype : 'textarea',
								fieldLabel : 'message',
								name : 'message',
								maxLength : 500,
								listeners : {
									blur : componentLostFocusEvent
								}
							}, {
								xtype : 'includeOrExcludeCmb',
								name : 'msgStatus',
								width : 80
							}, {
								xtype : 'textarea',
								fieldLabel : 'throwableMessage',
								name : 'throwableMessage',
								maxLength : 500,
								listeners : {
									blur : componentLostFocusEvent
								}
							}, {
								xtype : 'includeOrExcludeCmb',
								name : 'throwMsgStatus',
								width : 80
							}]
				}, {
					xtype : 'fieldset',
					margin : '5 7 10 5',
					layout : 'column',
					frame : true,
					items : [{
						xtype : 'textarea',
						fieldLabel : 'email PDL',
						msgTarget : 'under',
						allowBlank : false,
						name : 'emailPdl',
						maxLength : 500,
						listeners : {
							blur : componentLostFocusEvent
						},
						validator : function(value) {
							var regex = /^(\w+)([\-+.][\w]+)*@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/;
							var regexText = 'This field should be an e-mail address in the format "user@example.com,user1@example.com"'
							if (null != value && "" != value) {
								var emails = value.trim().split(',');
								if (null != emails && emails.length > 0) {
									for (i = 0; i < emails.length; i++) {
										if (null != emails[i]
												&& "" != emails[i]) {
											if (false == regex.test(emails[i]
													.trim())) {
												return regexText;
											}
										}
									}
								}
							}
							return true;
						}
					}]
				}]

	}],
	buttons : [{
				text : 'OK',
				action : 'save'
			}, {
				text : 'Cancel',
				formBind : true, // only enabled once the form is
				action : 'cancel'
			}]
})