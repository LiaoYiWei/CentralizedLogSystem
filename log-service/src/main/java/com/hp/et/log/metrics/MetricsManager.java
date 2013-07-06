/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.metrics;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.domain.bean.AppEnvMetrics;
import com.hp.et.log.domain.bean.HostInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.domain.bean.NodeReportMetrics;
import com.hp.et.log.domain.bean.RuntimeInstanceInfo;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.entity.RuntimeInstance;

public class MetricsManager 
{
    private static Logger logger = LoggerFactory.getLogger(MetricsManager.class);
    private static MetricsManager instance = new MetricsManager();
    
    private NodeMetricsCollector nodeMetricsCollector = new NodeMetricsCollector();
    private CacheService cacheService; 
    
    public void init()
    {
        nodeMetricsCollector.init();
    }
    public MetricsManager getInstance()
    {
        return instance; 
    }
    
    public CacheService getCacheService()
    {
        return cacheService;
    }


    public void setCacheService(CacheService cacheService)
    {
        this.cacheService = cacheService;
    }
   
    
    public void updateLogMetrics(final LogEventInfo logEventInfo, int length)
    {
        logger.info("ENTER updateLogMetrics");
        if(logEventInfo == null)
        {
            return; 
        }
        NodeAccumulateMetrics metrics = new NodeAccumulateMetrics();
        metrics.setClientLogQueueConsumedSize(logEventInfo.getConsumedSize());
        metrics.setClientLogQueueTotalSize(logEventInfo.getQueueCapacity());
        metrics.setTotalLogs(logEventInfo.getLogEventList().size());        
        //just a rough estimation
        metrics.setTotalSize(length);  
        
        //get runID
        
        String runId = logEventInfo.getRunId();
        RuntimeInstance runtimeInstance = cacheService.getRuntimeInstance(runId);
        if(runtimeInstance == null)
        {
            //should not happen.
            logger.error("could not get runtimeInstance by runID");
            return;
        }
        String nodeId = runtimeInstance.getAppEnvNode().getNodeId();
         
        metrics.setNodeId(nodeId);
        
        nodeMetricsCollector.updateLogMetrics(metrics);
        logger.info("EXIT updateLogMetrics");
    }
    
    public ArrayList<AppEnvMetrics> getAllMetricsData()
    {
        logger.info("ENTER getAllMetricsData");
        ArrayList<AppEnv> appEnvs = cacheService.getAllAppEnv();
        ArrayList<AppEnvMetrics> appEnvMetricsList = new ArrayList();
        
        for(AppEnv appEnv: appEnvs)
        {
            AppEnvMetrics appEnvMetrics = new AppEnvMetrics();
            appEnvMetrics.setAppEnvId(appEnv.getEnvId());
            appEnvMetrics.setAppId(appEnv.getApplication().getAppId());
            appEnvMetrics.setApplicationName(appEnv.getApplication().getAppName());
            appEnvMetrics.setEnvName(appEnv.getEnvName());
            ArrayList<NodeReportMetrics> nodeReportMetricsList = new ArrayList();
            
            for(AppEnvNode appEnvNode: appEnv.getAppEnvNodeCollection())
            {
                String nodeId = appEnvNode.getNodeId();
                NodeReportMetrics nodeReportMetrics = nodeMetricsCollector.getReportMetrics(nodeId);
                if(nodeReportMetrics == null)
                {
                    //we didn't get any data from this node yet 
                    nodeReportMetrics = new NodeReportMetrics();
                    //set the status to be inactive
                    nodeReportMetrics.setActiveStatus(false);
                }
                NodeInfo nodeInfo = new NodeInfo();
                nodeInfo.setName(appEnvNode.getNodeName());
                nodeInfo.setId(appEnvNode.getNodeId());
                RuntimeInstanceInfo latestRuntimeInstanceInfo = new RuntimeInstanceInfo();
                latestRuntimeInstanceInfo.setRunId(appEnvNode.getLatestRuntimeInstance().getRunId());
                latestRuntimeInstanceInfo.setStartTime(appEnvNode.getLatestRuntimeInstance().getStartTime());
                latestRuntimeInstanceInfo.setClientStartTime(appEnvNode.getLatestRuntimeInstance().getClientStartTime());
                nodeInfo.setLatestRuntimeInstance(latestRuntimeInstanceInfo);
                //set node seveirty 
                //status from appEnvNode might not be updated since it's cached 
                //the query for this cache is very heavy, we will only refresh the cache when during registration
                //when user update node severity, we don't want to refresh this cache 
                //so, we keep another cache only for the nodeID-Severity
                //here, we will search fron this cache 
                //and if user update node severity, it will just trigger the nodeSeverityCache cache
                Integer severity = cacheService.querySeverityByNodeId(appEnvNode.getNodeId());
                if(severity == null)
                {
                    //should never be null 
                    throw new RuntimeException("could not get severity");
                }
                
                nodeInfo.setAcceptSeverity(severity);
                
                HostInfo hostInfo = new HostInfo();
                hostInfo.setHostId(appEnvNode.getHost().getHostId());
                hostInfo.setHostName(appEnvNode.getHost().getHostName());
                hostInfo.setIpAddress(appEnvNode.getHost().getIpAddress());
                
                nodeInfo.setHost(hostInfo);
                
                nodeReportMetrics.setNodeInfo(nodeInfo);
                nodeReportMetricsList.add(nodeReportMetrics);
            }
            appEnvMetrics.setNodeMetricsList(nodeReportMetricsList);
            appEnvMetricsList.add(appEnvMetrics);
        }
        
        logger.info("EXIT getAllMetricsData");
        return appEnvMetricsList;
    }


  

}
