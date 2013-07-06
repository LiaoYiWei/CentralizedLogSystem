/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.metrics;

public class NodeAccumulateMetrics implements Cloneable
{
    
    private String nodeId; 
    private long totalLogs=0; 
    private long totalSize=0;
    private long clientLogQueueConsumedSize=0;
    private long clientLogQueueTotalSize=0; 
    private long createTimestamp = System.currentTimeMillis();
    private long latestTimestamp = System.currentTimeMillis();
    
    
    public long getLatestTimestamp()
    {
        return latestTimestamp;
    }
    public void setLatestTimestamp(long latestTimestamp)
    {
        this.latestTimestamp = latestTimestamp;
    }
    public long getClientLogQueueTotalSize()
    {
        return clientLogQueueTotalSize;
    }
    public void setClientLogQueueTotalSize(long clientLogQueueTotalSize)
    {
        this.clientLogQueueTotalSize = clientLogQueueTotalSize;
    }
    public String getNodeId()
    {
        return nodeId;
    }
    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }
    public long getTotalLogs()
    {
        return totalLogs;
    }
    public void setTotalLogs(long totalLogs)
    {
        this.totalLogs = totalLogs;
    }
    public long getTotalSize()
    {
        return totalSize;
    }
    public void setTotalSize(long totalSize)
    {
        this.totalSize = totalSize;
    }
    
    
    
    public long getClientLogQueueConsumedSize()
    {
        return clientLogQueueConsumedSize;
    }
    public void setClientLogQueueConsumedSize(long clientLogQueueConsumedSize)
    {
        this.clientLogQueueConsumedSize = clientLogQueueConsumedSize;
    }
    public void accumulate(NodeAccumulateMetrics nodeMetrics)
    {
        this.totalLogs += nodeMetrics.totalLogs;
        this.totalSize += nodeMetrics.totalSize;
        this.clientLogQueueConsumedSize = nodeMetrics.clientLogQueueConsumedSize;
        this.clientLogQueueTotalSize = nodeMetrics.clientLogQueueTotalSize;
        this.latestTimestamp = nodeMetrics.latestTimestamp;
    } 
    
    
    public Object clone()
    {
        // we could call super.clone() directly since all of the attributes are primitive java type
        //by default, they will be cloned to new object. 
        //however, maybe in future, we will add some Object in this class 
        //then, we still have to override clone to clone the Object. 
        //to avoid any issues in future, let's just do that at this moment.
        NodeAccumulateMetrics rstMetrics = new NodeAccumulateMetrics();
        rstMetrics.nodeId = this.nodeId; 
        rstMetrics.clientLogQueueConsumedSize = this.clientLogQueueConsumedSize; 
        rstMetrics.clientLogQueueTotalSize = this.clientLogQueueTotalSize;
        rstMetrics.totalLogs = this.totalLogs; 
        rstMetrics.totalSize = this.totalSize;
        rstMetrics.createTimestamp = this.createTimestamp;
        rstMetrics.latestTimestamp = this.latestTimestamp;
        return rstMetrics;
    }
    public long getCreateTimestamp()
    {
        return createTimestamp;
    }
    public void setCreateTimestamp(long createTimestamp)
    {
        this.createTimestamp = createTimestamp;
    }
    
    
    
}
