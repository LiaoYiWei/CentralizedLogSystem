/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.it.et.log.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.hp.et.log.clientfacade.RestfulClientFacade;
import com.hp.et.log.configure.Configure;
import com.hp.et.log.domain.bean.AppEnvMetrics;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.ApplicationInfo;
import com.hp.et.log.domain.bean.EnvApp;
import com.hp.et.log.domain.bean.EnvInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.MonitorInfo;
import com.hp.et.log.domain.bean.MonitorRegisterInfo;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.domain.bean.NodeReportMetrics;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.it.et.log.bean.AppNode;
import com.hp.it.et.log.bean.Application;
import com.hp.it.et.log.bean.EventsPagingBean;
import com.hp.it.et.log.bean.ExtJSFormResult;
import com.hp.it.et.log.bean.FileUploadBean;
import com.hp.it.et.log.bean.TaskNode;
import com.hp.it.et.log.utils.BusinessUtils;

@Controller  
public class AjaxCtrl
{
    private String serverUrl = Configure.getProperty("log.server.url");
    private ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(value = "/test.log", method = RequestMethod.GET)  
    public @ResponseBody Application test(@RequestParam String loginName) {  
        Application app = new Application();
        app.setAppName("testApp");
        app.setDesc("this is a test app");
        app.setId(-1);
        return app;  
    }  
    
    @RequestMapping(value = "/appnode_query.log", method = RequestMethod.GET)  
    public @ResponseBody ArrayList<AppNode> appnode_query() {

        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                serverUrl);
        List<ApplicationInfo> applications = (List<ApplicationInfo>) restfulClientFacade
                .findAllApplications();
        ArrayList<AppNode> list = new ArrayList();
        if (null != applications) {
            for (ApplicationInfo appInfo : applications) {

                AppNode appnode = new AppNode();
                appnode.setText(appInfo.getAppName());
                appnode.setCls("folder");
                appnode.setLeaf(false);
                appnode.setChecked(false);
                List childList = new ArrayList();
                List<EnvInfo> envInfos = appInfo.getEnvInfos();
                if (null != envInfos) {
                    for (EnvInfo evnInfo : envInfos) {
                        AppNode appnode1 = new AppNode();
                        appnode1.setText(evnInfo.getName());
                        appnode1.setCls("folder");
                        appnode1.setLeaf(false);
                        appnode1.setChecked(false);
                        List childList1 = new ArrayList();
                        List<NodeInfo> nodeInfos = evnInfo.getNodeInfos();
                        if (null != nodeInfos) {
                            for (NodeInfo nodeInfo : nodeInfos) {
                                AppNode appnode2 = new AppNode();
                                appnode2.setId(nodeInfo.getId());
                                appnode2.setText(nodeInfo.getName());
                                // appnode2.setCls("folder");
                                appnode2.setLeaf(true);
                                appnode2.setChecked(false);
                                childList1.add(appnode2);
                            }
                        }
                        appnode1.setChildren(childList1);
                        childList.add(appnode1);
                    }
                }
                appnode.setChildren(childList);
                list.add(appnode);
            }
        }
        //JsonUtil.ConvertListToTreeJOSN(list, res);
        return list;
    }

    @RequestMapping(value = "/getTask.log")
	public @ResponseBody List<TaskNode> getTasks() {
		List<TaskNode> taskNodes = null;
		RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
				serverUrl);
		List<AppEnvMetrics> appEnvMetrics = (List<AppEnvMetrics>) restfulClientFacade
				.getLoadMonitorMetrics();
		if (null != appEnvMetrics && !appEnvMetrics.isEmpty()) {
			taskNodes = new ArrayList<TaskNode>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (AppEnvMetrics appEM : appEnvMetrics) {
				String appEnvName = appEM.getApplicationName() + " - " + appEM.getEnvName();
				List<NodeReportMetrics> nodeReportMetrics = appEM.getNodeMetricsList();
				if(null != appEM && !nodeReportMetrics.isEmpty()){
					
					for(NodeReportMetrics nodeRM : nodeReportMetrics){
						TaskNode tn = new TaskNode();
						tn.setAppEnvName(appEnvName);
						tn.setId(nodeRM.getNodeInfo().getId());
						tn.setTaskName(nodeRM.getNodeInfo().getName());
						tn.setClientLogQueueConsumedSize(nodeRM.getClientLogQueueConsumedSize());
						tn.setClientLogQueueTotalSize(nodeRM.getClientLogQueueTotalSize());
						tn.setLogsPerSec(nodeRM.getLogsPerSec());
						tn.setSizePerSec(nodeRM.getSizePerSec());
						tn.setAvgLogsPerSec(nodeRM.getAvgLogsPerSec());
						tn.setAvgSizePerSec(nodeRM.getAvgSizePerSec());
						tn.setServerity(nodeRM.getNodeInfo().getAcceptSeverity());
						tn.setActiveStatus(nodeRM.isActiveStatus());
						tn.setLastRuntimeInstanceStartDate(df.format(nodeRM.getNodeInfo().getLatestRuntimeInstance().getClientStartTime()));
						taskNodes.add(tn);
					}
				}
			}
		}
		return taskNodes;
	}
   
    @RequestMapping(value = "/query_do.log")
    public @ResponseBody
    EventsPagingBean query_do(@ModelAttribute QueryInformation queryInfo)
    {
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(serverUrl);

        if (queryInfo.getSpecificTime() != null && !("".equals(queryInfo.getSpecificTime())))
        {

            queryInfo.setSpecificTime(queryInfo.getSpecificTime().substring(0, queryInfo.getSpecificTime().indexOf("T")));//format:2012-07-03T00:00:00
        }
        else
        {
        	queryInfo.setTime(queryInfo.getTime());
        }
        List list = restfulClientFacade.findEventByQueryInfo(queryInfo);
        Long logCount = restfulClientFacade.findEventsCountByQueryInfo(queryInfo);
        EventsPagingBean eventsPaging = new EventsPagingBean(logCount, list);

        return eventsPaging;
    }
    
    @RequestMapping(value = "/getLog_do.log")
    public @ResponseBody List<LogEvent> getLog_do(@RequestParam String monitorId) {
        if (null != monitorId && !"".equals(monitorId)) {
            MonitorInfo monitorInfo = new MonitorInfo();
            monitorInfo.setMonitorId(Long.parseLong(monitorId));
            RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                    serverUrl);
            List<LogEvent> logEvents = (List<LogEvent>) restfulClientFacade
                    .getMonitoringLog(monitorInfo);
            return logEvents;
        }
        else
        {
            return null;
        }
    }
    
    @RequestMapping(value = "/stopMonitoring_do.log")
    public @ResponseBody String stopMonitoring_do(@RequestParam String monitorId) {
        if (null != monitorId && !"".equals(monitorId)) {
            MonitorInfo monitorInfo = new MonitorInfo();
            monitorInfo.setMonitorId(Long.parseLong(monitorId));
            RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                    serverUrl);
            restfulClientFacade.stopMonitoring(monitorInfo);
            
        }
        return "success";
    }
    
    @RequestMapping(value = "/monitor_do.log")
    public @ResponseBody String monitor_do(@RequestParam String[] logLevels, @RequestParam String[] nodeIds) {

       // String[] logLevels = req.getParameterValues("logLevels");
       // String[] nodeIds = req.getParameterValues("nodeIds");

        if (logLevels == null || nodeIds == null || nodeIds.length < 1) {
            return "";
        }
        List<Integer> iLogLevels = new ArrayList<Integer>();
        for(String s : logLevels){
        	iLogLevels.add(Integer.parseInt(s));
        }
        Map<String, List<Integer>> filters = new HashMap<String, List<Integer>>();
        filters.put("severity", iLogLevels);
        MonitorRegisterInfo monitorRegisterInfo = new MonitorRegisterInfo();
        monitorRegisterInfo.setFilters(filters);
        monitorRegisterInfo.setNodeIds(Arrays.asList(nodeIds));
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                serverUrl);
        Long monitorId = restfulClientFacade
                .registerMonitor(monitorRegisterInfo);
       
        return monitorId.toString();
    }
    
    @RequestMapping(value = "/app_load.log")
    public @ResponseBody List app_load() {
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                serverUrl);
        List list = restfulClientFacade.findAllApplications();
        return list;
    }

    @RequestMapping(value = "/host_load.log")
    public @ResponseBody List host_load(HttpServletRequest req, HttpServletResponse res) {
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                serverUrl);
        List list = restfulClientFacade.findAllHosts();
        return list;
    }
    
    @RequestMapping(value = "/downloadInstance.log")
    public void downloadInstance(HttpServletRequest req, HttpServletResponse res) {
        // appID
        String appId = req.getParameter("appId");
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                serverUrl);
        String fileName = "RuleEngine" + appId + ".groovy";
        res.reset();
        res.setContentType("application/x-msdownload;");
        res.setHeader("Content-Disposition", "attachment;filename=\""
                + fileName + "\"");
        OutputStream outPut = null;
        try {
            outPut = res.getOutputStream();
            restfulClientFacade.downloadGroovyFile(appId, outPut);
            outPut.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                outPut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    @RequestMapping(value = "/upload_do.log")
    public  @ResponseBody String  upload_do(FileUploadBean uploadItem,BindingResult result){   
            
    	ExtJSFormResult extjsFormResult = new ExtJSFormResult();   
    	
    	if (result.hasErrors()){   
    		extjsFormResult.setSuccess(false);   
    		return extjsFormResult.toString();   
    	}   
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                 serverUrl);
        InputStream uploadedStream = null;
        FileItem fileItem = uploadItem.getFile().getFileItem();
        try {
        	if (null == fileItem) {
                throw new Exception("upload file is null");
            }
            String fileName = fileItem.getName();
            fileName = BusinessUtils.fileRealName(fileName);

            uploadedStream = fileItem.getInputStream();
            if (null == uploadedStream) {
                throw new Exception("upload file is null");
            }
            
                restfulClientFacade.uploadGroovyFile(uploadItem.getAppId(), uploadedStream);
                extjsFormResult.setSuccess(true);   
            } catch (Exception ex) {
            	extjsFormResult.setSuccess(false); 
                ex.printStackTrace();
            }
         
     
         return extjsFormResult.toString();   


    }   
    @RequestMapping(value = "/changLogLevel.log")
    public  @ResponseBody String changeLogLevel(String nodeId, String logLevel){   
       
    	if(null == nodeId || "".equals(nodeId) || null == logLevel || "".equals(logLevel)){
    		return null;
    	}
    	   RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
                   serverUrl);
    	  
    	   ArrayList<NodeInfo> nodeInfoList = new ArrayList();
           NodeInfo nodeInfo = new NodeInfo();
           nodeInfo.setId(nodeId);        
           nodeInfo.setAcceptSeverity(Integer.parseInt(logLevel));
           nodeInfoList.add(nodeInfo);
           restfulClientFacade.updateNodeSeverity(nodeInfoList);
           return "success";
    }
    
    @RequestMapping(value = "/queryEnvApp.log",method = RequestMethod.GET)
    public @ResponseBody List envApp_query() {
	RestfulClientFacade restfulClientFacade = new RestfulClientFacade(
		serverUrl);
	return restfulClientFacade.findAllEnvsApp();
    }
    
    @RequestMapping(value = "/getRulesByEvnId.log")
    public @ResponseBody List<AppEnvRuleInfo> getRulesByEvnId(String envId)
    {
        if (null == envId || "".equals(envId))
        {
            return null;
        }
        RestfulClientFacade restfulClientFacade = new RestfulClientFacade(serverUrl);
        List<AppEnvRuleInfo> rules = null;
        List list = restfulClientFacade.findRulesByEnvId(envId);
        return list;
    }
    
    @RequestMapping(value = "/createOrUpdateRule.log")
    public @ResponseBody String createOrUpdateRule(@RequestParam("model") String body)
    {
        if(null == body || "".equals(body)){
            return  null;
        }
        AppEnvRuleInfo ruleInfo = null;
        try
        {
            ruleInfo = mapper.readValue(body, AppEnvRuleInfo.class);
           
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(null == ruleInfo){
            return null;
        }
        
        if(null == ruleInfo.getRuleId() || "".equals(ruleInfo.getRuleId()))
        {
            ruleInfo.setRuleId(null);
            RestfulClientFacade restfulClientFacade = new RestfulClientFacade(serverUrl);
            String ruleId = restfulClientFacade.addNewRule(ruleInfo);
            return ruleId;
        }
        else{
           RestfulClientFacade restfulClientFacade = new RestfulClientFacade(serverUrl);
           String ruleId = restfulClientFacade.updateRuleByRuleInfo(ruleInfo);
           return ruleId;
        }
    }
    
    @RequestMapping(value = "/deleteRule.log")
    public @ResponseBody void deleteRule(@RequestParam("model") String body)
    {
        if(null == body || "".equals(body)){
            return ;
        }
        AppEnvRuleInfo ruleInfo = null;
        try
        {
            ruleInfo = mapper.readValue(body, AppEnvRuleInfo.class);
           
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(null == ruleInfo || null == ruleInfo.getRuleId() || "".equals(ruleInfo.getRuleId())){
            return;
        }
       RestfulClientFacade restfulClientFacade = new RestfulClientFacade(serverUrl);
       restfulClientFacade.deleteRuleByRuleInfo(ruleInfo);
    }
}
