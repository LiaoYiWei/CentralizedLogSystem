/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.appender;

import java.net.UnknownHostException;
import java.util.Date;

import com.hp.et.log.clientfacade.RestfulClientFacade;
import com.hp.et.log.domain.bean.ApplicationInstance;
import com.hp.et.log.domain.bean.LogEventInfo;

/**
 * Description goes here.
 */
public class AppenderUtil
{
    public static RunContext getRunContext(String serviceHttpUrl, String appName, String env, String node)
    {
        RunContext runContext = new RunContext();
        runContext.setServerUrl(serviceHttpUrl);

        ApplicationInstance appInstance = new ApplicationInstance();
        appName = appName.trim();
        appInstance.setAppName(appName);
        appInstance.setEnv(env);
        appInstance.setHostName(AppenderUtil.getLocalHostName());
        appInstance.setNodeName(node);
        runContext.setApplicationInstance(appInstance);
        return runContext;
    }
    public static void authenticateApp(RunContext runContext)
    {
        RestfulClientFacade client = new RestfulClientFacade(runContext.getServerUrl());
        ApplicationInstance appInstance = runContext.getApplicationInstance();
        appInstance.setAppStartupTimestamp(new Date().getTime()); //set app startup timestamp
        String runId = client.authenticate(appInstance);
        //String runId = "34";
        if (runId == null || runId.length() == 0)
        {
            throw new RuntimeException("could not startup Centralized Appender successfully, authenticatation failure!");
        }
        
        appInstance.setRunId(runId);
        
    }
    
    
    
    public static String getLocalHostName()
    {
        String hostName = "";
        java.net.InetAddress addr;
        try
        {
            addr = java.net.InetAddress.getLocalHost();
            hostName = addr.getHostName();
            
        }
        catch (UnknownHostException e)
        {
            //if we could not get hostname for any reason
            //doesn't matter.since the server authenticate by IP
        }
        return hostName;
    }
    public static int queryFeedSeverity(RunContext context, int queueCapacity, int consumedSize)
    {
        LogEventInfo logEventInfo = new LogEventInfo();
        logEventInfo.setRunId(context.getApplicationInstance().getRunId());
        logEventInfo.setQueueCapacity(queueCapacity);
        logEventInfo.setConsumedSize(consumedSize);
        RestfulClientFacade clientFacade = new RestfulClientFacade(context.getServerUrl());
        int severity = clientFacade.feedEnabled(logEventInfo);
        return severity; 
        //context.setSeverity(severity);
        
    }

}
