package com.hp.et.log.clientfacade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.hp.et.log.domain.bean.AppEnvMetrics;
import com.hp.et.log.domain.bean.ApplicationInstance;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.domain.bean.QueryInformation;



public class TestRestfulClientFacade
{
    String serviceUrl = "http://localhost:8080/log-service-0.0.1-SNAPSHOT";
    @Test
    public void testSendLogEvent() {
        try {
            RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
            LogEvent event = new LogEvent();
            event.setRunId("34"); 
            event.setMessage("Test");
            event.setSeverity(LogSeverityEnum.DEBUG.getIndex());
            event.setTimestamp(System.currentTimeMillis());
            event.setMessageType(LogEvent.MESSAGE_TYPE_LOG_SYSTEM);
            event.setSeverity(LogSeverityEnum.INFO.getIndex());
            
            client.sendLogEvent(null);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testFindAllHosts()
    {
        try {
            RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
            
            List hosts = client.findAllHosts();
            System.out.println(hosts.size());
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindAllApplications()
    {
        try {
            RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
            
            List rsts = client.findAllApplications();
            System.out.println(rsts.size());
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testAuthenticate()
    {
        try {
            RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
            
            ApplicationInstance appInstance = new ApplicationInstance();
            appInstance.setAppName("TEST-APP");
            appInstance.setEnv("DEV");
            appInstance.setHostName("ZHOU-QI-HOST");
            appInstance.setNodeName("defaultNode");
            String runID = client.authenticate(appInstance);
            System.out.println(runID);
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFindEventByQueryInfo() {
        try {
            RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
            QueryInformation queryInfo = new QueryInformation();
            List<Integer> hostIdList = new ArrayList<Integer>();
            hostIdList.add(2);
            hostIdList.add(1);
            List<Integer> appIdList = new ArrayList<Integer>();
            appIdList.add(2);
            appIdList.add(1);
//            queryInfo.setHostIds(hostIdList);
//            queryInfo.setAppIds(appIdList);
//            queryInfo.setEnvId(2);
            queryInfo.setTime(1400);
            client.findEventByQueryInfo(queryInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDownloadGroovyFile() throws Exception
    {
        File outputFile = new File("c:/tmp/copy.groovy");
        if(!outputFile.exists()){
            outputFile.createNewFile();
        }
        OutputStream oStream = new FileOutputStream(outputFile);
        RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
        client.downloadGroovyFile("1", oStream);
        
    }
    
    @Test
    public void testUploadGroovyFile() throws Exception
    {
        File inputFile = new File("c:/tmp/copy.groovy");
        
        InputStream iStream = new FileInputStream(inputFile);
        RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
        client.uploadGroovyFile("1", iStream);
        
    }
    
    @Test
    public void testFeedEnabled() throws Exception
    {        
     
        LogEventInfo logEventInfo = new LogEventInfo ();
        
        RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
        Integer rst = client.feedEnabled(logEventInfo);
        System.out.println(rst);
        
    }
    
    
    @Test
    public void testGetLoadMonitorMetrics()
    {
        RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
        List<AppEnvMetrics> appEnvMetricsList = client.getLoadMonitorMetrics();
        
        for(AppEnvMetrics appEnvMetrics: appEnvMetricsList)
        {
            System.out.println(appEnvMetrics.debugString());
        }
        
    }
    
    @Test
    public void testUpdateNodeSeverity()
    {
        RestfulClientFacade client = new RestfulClientFacade(serviceUrl);
        ArrayList<NodeInfo> nodeInfoList = new ArrayList();
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setId("297e693538bcb9f20138bcbb73af0003");        
        nodeInfo.setAcceptSeverity(LogSeverityEnum.WARN.getIndex());
        nodeInfoList.add(nodeInfo);
        client.updateNodeSeverity(nodeInfoList);
    }

}
