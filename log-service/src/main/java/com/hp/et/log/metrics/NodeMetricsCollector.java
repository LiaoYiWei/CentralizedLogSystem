/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.metrics;

import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.domain.bean.NodeReportMetrics;


public class NodeMetricsCollector implements Runnable
{
    private static Logger logger = LoggerFactory.getLogger(NodeMetricsCollector.class);
    private static int SCAN_INTERVAL = 5000;
    private static long TIME_OUT = 15000;

    //Here we keep two hashmap instead of one hashmap to improve performnace
    //since we have to make sure the createLog thread get response asap. 
    private HashMap<String, NodeAccumulateMetrics> nodeMetricsMap = new HashMap();
    private HashMap<String, NodeReportMetrics> reportMetricsMap = new HashMap();
    
    public NodeMetricsCollector()
    {
        //it's not good to start thread in constructor
        //but this is the easist solution since for this case it won't impact anything
        
    }
    
    public void init()
    {
        new Thread(this).start();
    }

    public void updateLogMetrics(NodeAccumulateMetrics nodeMetrics)
    {
        logger.info("ENTER updateLogMetrics");
        synchronized (nodeMetricsMap)
        {
            String nodeId = nodeMetrics.getNodeId();
            NodeAccumulateMetrics sumMetrics = nodeMetricsMap.get(nodeId);
            if (sumMetrics == null)
            {
                nodeMetricsMap.put(nodeId, nodeMetrics);
            }
            else
            {
                sumMetrics.accumulate(nodeMetrics);
            }
        }
        
        logger.info("EXIT updateLogMetrics");
    }

    public void run()
    {
        try
        {
            logger.info("Thread starting ....");
            long previousTimestamp = 0;
            HashMap<String, NodeAccumulateMetrics> previousSampleMap = null;
            while (Thread.currentThread().isAlive())
            {
                logger.info("Take a snapshot for the current metrics");
                HashMap<String, NodeAccumulateMetrics> snapshotMap = snapshot();
                
                long currentTimestamp = System.currentTimeMillis(); 
                if(previousTimestamp != 0)
                {
                    calculateMetrics(previousSampleMap, snapshotMap, previousTimestamp, currentTimestamp);
                }

                previousTimestamp = currentTimestamp; 
                previousSampleMap = snapshotMap;
                Thread.sleep(SCAN_INTERVAL);
            }
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("Thread stoped ....");

    }

    private void calculateMetrics(HashMap<String, NodeAccumulateMetrics> previousMetricsMap,HashMap<String, NodeAccumulateMetrics> currentMetricsMap, long previousTimestamp, long currentTimestamp)
    {
        logger.info("ENTER calculateMetrics");
        
        float durationSec = (currentTimestamp-previousTimestamp)/1000;
         
        for(Entry<String, NodeAccumulateMetrics> entry: currentMetricsMap.entrySet())
        {
            String nodeId = entry.getKey(); 
            NodeAccumulateMetrics currentMetrics = entry.getValue(); 
            float wholeSec =  (currentTimestamp - currentMetrics.getCreateTimestamp())/1000;
            NodeAccumulateMetrics previousMetrics = previousMetricsMap.get(nodeId);
            
            NodeReportMetrics reportMetrics = new NodeReportMetrics();
            
            
            reportMetrics.setAvgLogsPerSec(currentMetrics.getTotalLogs()/wholeSec);                
            reportMetrics.setAvgSizePerSec(currentMetrics.getTotalSize()/wholeSec);
            long consumedSize = currentMetrics.getClientLogQueueConsumedSize();
            long totalSize = currentMetrics.getClientLogQueueTotalSize();
            reportMetrics.setClientLogQueueConsumedSize(consumedSize < totalSize ? consumedSize : totalSize);
            reportMetrics.setClientLogQueueTotalSize(totalSize);
            
            if(previousMetrics != null)
            {
                reportMetrics.setLogsPerSec((currentMetrics.getTotalLogs()-previousMetrics.getTotalLogs())/durationSec);
                reportMetrics.setSizePerSec((currentMetrics.getTotalSize()-previousMetrics.getTotalSize())/durationSec);                    
            }
            //long currentTimestamp = System.currentTimeMillis();
            
            if((currentTimestamp - currentMetrics.getLatestTimestamp()) > TIME_OUT)
            {
                reportMetrics.setActiveStatus(false);
            }
            else
            {
                reportMetrics.setActiveStatus(true);
            }
            
            reportMetrics(nodeId,reportMetrics);
        }
        
        logger.info("EXIT calculateMetrics");
    }

    private void reportMetrics(String nodeId, NodeReportMetrics reportMetrics)
    {
        logger.debug("ENTER reportMetrics");
        
        synchronized(reportMetricsMap)
        {
            reportMetricsMap.put(nodeId, reportMetrics);
        }
        
        logger.debug("EXIT reportMetrics");
    }

    private HashMap<String, NodeAccumulateMetrics> snapshot()
    {
        logger.info("ENTER snapshot");
        HashMap<String, NodeAccumulateMetrics> snapshotMap = new HashMap();
        synchronized(nodeMetricsMap)
        {            
            for(Entry<String,NodeAccumulateMetrics> entry: nodeMetricsMap.entrySet())
            {
                snapshotMap.put(entry.getKey(), (NodeAccumulateMetrics)entry.getValue().clone());
            }           
        }
        logger.info("EXIT snapshot");
        return snapshotMap;
    }
    
    public NodeReportMetrics getReportMetrics(String nodeId)
    {
        logger.info("ENTER getReportMetrics");
        NodeReportMetrics reportMetrics = null;
        synchronized(reportMetricsMap)
        {
            reportMetrics = reportMetricsMap.get(nodeId);
        }
        
        logger.info("EXIT getReportMetrics");
        return reportMetrics;
    }

}
