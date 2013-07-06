/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AppEnvMetrics implements Serializable
{
    private String appEnvId; 
    private String appId; 
    private String applicationName;     
    private String envName; 
    
    private ArrayList<NodeReportMetrics> nodeMetricsList = new ArrayList();

    public ArrayList<NodeReportMetrics> getNodeMetricsList()
    {
        return nodeMetricsList;
    }

    public void setNodeMetricsList(ArrayList<NodeReportMetrics> nodeMetricsList)
    {
        this.nodeMetricsList = nodeMetricsList;
    }

    public String getAppEnvId()
    {
        return appEnvId;
    }

    public void setAppEnvId(String appEnvId)
    {
        this.appEnvId = appEnvId;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getApplicationName()
    {
        return applicationName;
    }

    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }

    public String getEnvName()
    {
        return envName;
    }

    public void setEnvName(String envName)
    {
        this.envName = envName;
    }
    
    public String debugString()
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("appId:").append(appId).append("\t");
        strBuf.append("applicationName:").append(applicationName).append("\t");
        strBuf.append("appEnvId:").append(appEnvId).append("\t");
        strBuf.append("envName:").append(envName).append("\t");
        
        for(NodeReportMetrics nodeReportMetrics: nodeMetricsList)
        {
            strBuf.append("\n");
            strBuf.append(nodeReportMetrics.debugString());            
        }
        return strBuf.toString();
    }

}
