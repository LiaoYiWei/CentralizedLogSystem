/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.clientfacade;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import com.hp.et.log.domain.bean.AppEnvMetrics;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.ApplicationInstance;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.MonitorInfo;
import com.hp.et.log.domain.bean.MonitorRegisterInfo;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.restful.tool.ObjectUtility;
import com.hp.et.log.restful.tool.RestfulRequestTemplate;

public class RestfulClientFacade {
	private String httpUrl;
	
	private Object fetchObjWithObj(final String serviceUrl, final String subUrl, final Object inputParam)
	{
	    
	    return new RestfulRequestTemplate(){
	        
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
//                Utility.objToOutputStream(inputParam, outStream);
            	ObjectUtility.objToOutputStream(inputParam, outStream);
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
                if(conn.getResponseCode() == 204)
                {
                    return null;
                }
//                Object obj = Utility.inputStreamToObj(inputStream);
                Object obj = ObjectUtility.inputStreamToObj(inputStream);
                return obj;
                
            }
	        
	    }.execute(serviceUrl, subUrl);
	    
	}
	
	private String fetchStrWithObj(final String serviceUrl, final String subUrl, final Object inputParam)
    {
        
        return (String)new RestfulRequestTemplate(){

            
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
//                Utility.objToOutputStream(inputParam, outStream);                
            	ObjectUtility.objToOutputStream(inputParam, outStream);
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
//                return Utility.inputStreamToStr(inputStream);
            	return ObjectUtility.inputStreamToStr(inputStream);
            }
            
        }.execute(serviceUrl, subUrl);
        
    }
	
	private Object fetchObjWithouInput(String serviceUrl, String subUrl)
	{
	    return new RestfulRequestTemplate(){

            
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
                //do nothing, since no input           
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
                if(conn.getResponseCode() == 204)
                {
                    return null;
                }
//                Object obj = Utility.inputStreamToObj(inputStream);
               Object obj = ObjectUtility.inputStreamToObj(inputStream);
                return obj;
                
            }
            
        }.execute(serviceUrl, subUrl);
	}
	
	private String fetchStrWithouInput(String serviceUrl, String subUrl)
    {
        return (String)new RestfulRequestTemplate(){

            
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
                //do nothing, since no input           
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
//                return Utility.inputStreamToStr(inputStream);
            	return ObjectUtility.inputStreamToStr(inputStream);
                
            }
            
        }.execute(serviceUrl, subUrl);
    }

	public RestfulClientFacade(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	
	
	public void sendLogEvent(LogEventInfo logInfo) {			    
	    this.fetchObjWithObj(httpUrl, "/events/", logInfo);
	    return;
	}

	public String authenticate(ApplicationInstance applicationInstance) {	    
	    return (String)this.fetchStrWithObj(httpUrl, "/register", applicationInstance);		
	}
	
	public String addNewRule(AppEnvRuleInfo ruleInfo) {	    
	    return (String)this.fetchStrWithObj(httpUrl, "/appEnvRule/addNewRule", ruleInfo);		
	}
	
	public List findRulesByEnvId(String envId){	    
	    return (List)this.fetchObjWithObj(httpUrl, "/appEnvRule/findRulesByEnvId", envId);		
	}
	
	public String updateRuleByRuleInfo(AppEnvRuleInfo ruleInfo) {	    
	    return (String)this.fetchStrWithObj(httpUrl, "/appEnvRule/updateRuleByRuleInfo", ruleInfo);		
	}
	
	public void deleteRuleByRuleInfo(AppEnvRuleInfo ruleInfo) {	    
	    this.fetchObjWithObj(httpUrl, "/appEnvRule/deleteRuleByRuleInfo", ruleInfo);
	    return;
	}
	
	
	public Integer feedEnabled(LogEventInfo logEventInfo) {
	    return (Integer) this.fetchObjWithObj(httpUrl, "/events/feedEnabled", logEventInfo);
	    
	}
	
	public List findAllHosts() {
	    
	    return (List) this.fetchObjWithouInput(httpUrl, "/host/findAllHost");
	    
	}
	
	public List findAllApplications() {
	    return (List) this.fetchObjWithouInput(httpUrl, "/application/findAllApplication");
	}
	
    public List findAllEnvsApp(){
	return (List) this.fetchObjWithouInput(httpUrl, "/application/findAllEnvsApp");
    }

	public void updateNodeSeverity(ArrayList<NodeInfo> nodeInfoList){	    
	    this.fetchObjWithObj(httpUrl, "/MonitorService/updateNodeSeverity", nodeInfoList);
	    return;
	}
	
	
	public List findEventByQueryInfo(QueryInformation queryInfo){	    	    
	    return (List) this.fetchObjWithObj(httpUrl, "/events/findEventByQueryInfo", queryInfo);
	}
	
    public Long findEventsCountByQueryInfo(QueryInformation queryInfo)
    {
        return (Long)this.fetchObjWithObj(httpUrl, "/events/findEventsCountByQueryInfo", queryInfo);
    }

	public void downloadGroovyFile (final String appID, final OutputStream outStream) {
	     new RestfulRequestTemplate(){

            
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
//                Utility.strToOutputStream(appID, outStream);                
            	ObjectUtility.strToOutputStream(appID, outStream);
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
               
//                Utility.inputStreamToOutputStream(inputStream, outStream);
            	ObjectUtility.inputStreamToOutputStream(inputStream, outStream);
                return null;
                
            }
            
        }.execute(httpUrl, "/fileTransfer/downloadGroovy/");
        
        return;
	    
	}
	
	public void uploadGroovyFile (final String appID, final InputStream inStream) {
	    
	        new RestfulRequestTemplate(){

            
            @Override
            public void onSubmitRequest(HttpURLConnection conn, OutputStream outStream)
                throws Exception
            {
//                Utility.inputStreamToOutputStream(inStream, outStream);                
            	ObjectUtility.inputStreamToOutputStream(inStream, outStream);
            }

            @Override
            public Object onProcessResponse(HttpURLConnection conn, InputStream inputStream)
                throws Exception
            {
                return null;                
            }
            
        }.execute(httpUrl, "/fileTransfer/uploadGroovy/" + appID);
        
        return;
	    
	}
	
	public List<AppEnvMetrics> getLoadMonitorMetrics() {
	    return (List<AppEnvMetrics>) this.fetchObjWithouInput(httpUrl, "/MonitorService/getLoadMonitorMetrics");
	}
	
	public Long registerMonitor(MonitorRegisterInfo monitorRegisterInfo){	    	    
	    return (Long) this.fetchObjWithObj(httpUrl, "/MonitorService/startMonitoring", monitorRegisterInfo);
	}
	public List getMonitoringLog(MonitorInfo monitorInfo){	    	    
	    return (List)this.fetchObjWithObj(httpUrl, "/MonitorService/getLogEventsFromMonitorQueue",monitorInfo);
	}
	public void stopMonitoring(MonitorInfo monitorInfo){	    	    
	    this.fetchObjWithObj(httpUrl, "/MonitorService/stopMonitoring",monitorInfo);
	}
}
