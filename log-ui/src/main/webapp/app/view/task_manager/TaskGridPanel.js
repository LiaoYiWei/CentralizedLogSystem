function customerRenderCell(value, metaData, record) {
				if (record.data.activeStatus == false) {
					//metaData.style = 'background-color: #FF734C;';
					metaData.css = 'inactive';
				} else {
					//metaData.style = 'background-color: #53FF48;';
					metaData.css='active';
				}
				return value;
			}
Ext.define('Log.view.task_manager.TaskGridPanel', {
			extend : 'Ext.grid.Panel',
			alias : "widget.taskGridPanel",
			width : 900,
			header : false,
			height : 450,
			frame : true,
			title : 'Application States',
			iconCls : 'icon-grid',
			store : 'task_manager.TaskStore',
			features : [{
						id : 'group',
						ftype : 'groupingsummary',
						groupHeaderTpl : '{name}',
						hideGroupedHeader : true,
						enableGroupingMenu : false
					}],
			dockedItems: [{
			    xtype: 'toolbar',
			    dock: 'top',
			    
			    items: [{
			    	id: 'appQueryField', 
			        xtype: 'textfield',
			        name: 'name',
			        fieldLabel: 'App',
			        labelPad: 2,
			        labelWidth: 40,
			        margin: '10 10 10 10'
			    }, 
			    {
			    	id: 'activeSelectRadio', 
			    	xtype: 'checkboxfield', 
			    	boxLabel: 'Only Active'
			    		
			    }
			    ]			
			}],
// weird, it should work, but didn't , I have no time to research any more.			
//			viewConfig: {
//				
//				getRowClass: function(record, rowIndex, rp, ds){ // rp = rowParams
//					
//					if (record.data.activeStatus == false) {
//						return "inactive";
//					} else {
//						return "active";
//					}
//			    }
//
//			},
			
			columns : [{
				width : 150,
				//locked : true,
				// tdCls : 'task',
				header : 'Node',
				sortable : true,
				dataIndex : 'taskName',
				hideable : false,
				summaryRenderer : function(value, summaryData, dataIndex) {
					return ((value === 0 || value > 1)
							? '(' + value + ' Node)'
							: '(1 Node)');
				},
				renderer : customerRenderCell
			},
			{
				header : 'LogLevel',
				width : 100,
				sortable : true,
				dataIndex : 'serverity',
				renderer : function(value, metaData, record) {
					if (record.data.activeStatus == false) {
						metaData.css = 'inactive';
					} else {
						metaData.css = 'active';
					}
					
					switch(value){
						case '0': return "TRACE";
						break;
						case '1': return "DEBUG";
						break;
						case '2': return "INFO";
						break;
						case '3': return "WARNING";
						break;
						case '4': return "ERROR";
						break;
						case '5': return "FATAL";
						break;
						case '6': return "DISABLE";
						break;
					}
					
					
				}
			},
			{
				header : 'StartTime',
				width : 130,
				sortable : true,
				dataIndex : 'lastRuntimeInstanceStartDate', 
				renderer : customerRenderCell
			},
			{
				header : 'ClientLogQueue<br/>ConsumedSize',
				width : 100,
				sortable : true,
				dataIndex : 'clientLogQueueConsumedSize',
				summaryType : 'sum', 
				renderer : customerRenderCell
			}, {
				header : 'ClientLogQueue<br/>TotalSize',
				width : 110,
				sortable : true,
				dataIndex : 'clientLogQueueTotalSize',
				summaryType : 'sum',
				renderer : customerRenderCell
			}, {
				header : 'LogsPerSeconds',
				width : 110,
				sortable : true,
				dataIndex : 'logsPerSec',
				summaryType : 'sum',
				renderer : customerRenderCell
			}, {
				header : 'SizePerSeconds<br/>(bytes/s)',
				width : 110,
				sortable : true,
				dataIndex : 'sizePerSec',
				summaryType : 'sum',
				renderer : customerRenderCell
			}, {
				header : 'AvgLogsPerSec',
				width : 110,
				sortable : true,
				dataIndex : 'avgLogsPerSec',
				summaryType : 'average',
				renderer : customerRenderCell
			}, {
				header : 'AvgSizePerSec<br/>(bytes/s)',
				width : 110,
				sortable : true,
				dataIndex : 'avgSizePerSec',
				summaryType : 'average',
				renderer : customerRenderCell
			}]
		});