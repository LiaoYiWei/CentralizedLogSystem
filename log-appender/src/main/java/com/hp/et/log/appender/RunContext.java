/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

import com.hp.et.log.domain.bean.ApplicationInstance;
import com.hp.et.log.domain.bean.LogSeverityEnum;

public class RunContext
{
    //private static RunContext instance = new RunContext();
    
//    public static RunContext getInstance()
//    {
//        return instance; 
//        
//    }
	
//    private RunContext() 
//    {
//        
//    }
    
    
    private String serverUrl; 
    
    private ApplicationInstance applicationInstance; 
    
    private int severity = LogSeverityEnum.TRACE.getIndex();
    
    
    
    public  int getSeverity()
    {
        return severity;
    }
    
    public  void setSeverity(int severity)
    {
        this.severity = severity;
    }
    
    
    
    public ApplicationInstance getApplicationInstance()
    {
        return applicationInstance;
    }
    public void setApplicationInstance(ApplicationInstance applicationInstance)
    {
        this.applicationInstance = applicationInstance;
    }
    public String getServerUrl()
    {
        return serverUrl;
    }
    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
    
    
}
