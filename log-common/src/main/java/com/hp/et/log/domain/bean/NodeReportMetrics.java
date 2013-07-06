/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.domain.bean;

import java.io.Serializable;

public class NodeReportMetrics implements Serializable
{
    private NodeInfo nodeInfo; 
    
    private String lastRuntimeInstanceID = ""; 
    
    private String lastRuntimeInstanceStartDate = ""; 
    
    private long clientLogQueueConsumedSize = 0; 
    
    private long clientLogQueueTotalSize=0; 
    
    private float logsPerSec = 0; 
    
    private float sizePerSec = 0;
    
    private float avgLogsPerSec = 0; 
    
    private float avgSizePerSec = 0; 
    
    private boolean activeStatus = true; 
    

    public boolean isActiveStatus()
    {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus)
    {
        this.activeStatus = activeStatus;
    }

    public long getClientLogQueueTotalSize()
    {
        return clientLogQueueTotalSize;
    }

    public void setClientLogQueueTotalSize(long clientLogQueueTotalSize)
    {
        this.clientLogQueueTotalSize = clientLogQueueTotalSize;
    }

    public float getAvgLogsPerSec()
    {
        return avgLogsPerSec;
    }

    public void setAvgLogsPerSec(float avgLogsPerSec)
    {
        this.avgLogsPerSec = avgLogsPerSec;
    }

    public float getAvgSizePerSec()
    {
        return avgSizePerSec;
    }

    public void setAvgSizePerSec(float avgSizePerSec)
    {
        this.avgSizePerSec = avgSizePerSec;
    }

    public float getLogsPerSec()
    {
        return logsPerSec;
    }

    public void setLogsPerSec(float logsPerSec)
    {
        this.logsPerSec = logsPerSec;
    }

    public float getSizePerSec()
    {
        return sizePerSec;
    }

    public void setSizePerSec(float sizePerSec)
    {
        this.sizePerSec = sizePerSec;
    }

   

    public String getLastRuntimeInstanceID()
    {
        return lastRuntimeInstanceID;
    }

    public void setLastRuntimeInstanceID(String lastRuntimeInstanceID)
    {
        this.lastRuntimeInstanceID = lastRuntimeInstanceID;
    }

    public String getLastRuntimeInstanceStartDate()
    {
        return lastRuntimeInstanceStartDate;
    }

    public void setLastRuntimeInstanceStartDate(String lastRuntimeInstanceStartDate)
    {
        this.lastRuntimeInstanceStartDate = lastRuntimeInstanceStartDate;
    }

   

    public long getClientLogQueueConsumedSize()
    {
        return clientLogQueueConsumedSize;
    }

    public void setClientLogQueueConsumedSize(long clientLogQueueConsumedSize)
    {
        this.clientLogQueueConsumedSize = clientLogQueueConsumedSize;
    }

    public NodeInfo getNodeInfo()
    {
        return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo)
    {
        this.nodeInfo = nodeInfo;
    }

    public String debugString()
    {
        StringBuffer strBuf = new StringBuffer();
        if(nodeInfo != null)
        {
            strBuf.append(nodeInfo.debugString());
        }
        strBuf.append("\n");
        strBuf.append("clientLogQueueConsumedSize:").append(clientLogQueueConsumedSize).append("\n");
        strBuf.append("clientLogQueueTotalSize:").append(clientLogQueueTotalSize).append("\n");
        strBuf.append("logsPerSec:").append(logsPerSec).append("\n");
        strBuf.append("sizePerSec:").append(sizePerSec).append("\n");
        strBuf.append("avgLogsPerSec:").append(avgLogsPerSec).append("\n");
        strBuf.append("avgSizePerSec:").append(avgSizePerSec).append("\n");
        strBuf.append("activeStatus:").append(activeStatus).append("\n");
        
        return strBuf.toString();
    } 
    
    
}
