/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;



import java.util.ArrayList;
import java.util.LinkedList;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.restful.tool.SequenceGenerator;

public class LogQueue
{
    private int batchSize = 10; 
    private int queueCapacity; 
    private long logSequence = 0; //log sequence always starts from 0
    private IExtraQueuePolicy extraQueuePolicy; 
    private ISuppressLogPolicy suppressLogPolicy; 
    
    private LinkedList<LogEvent> queue = new LinkedList();
    
    
    public ISuppressLogPolicy getSuppressLogPolicy()
    {
        return suppressLogPolicy;
    }


    public void setSuppressLogPolicy(ISuppressLogPolicy suppressLogPolicy)
    {
        this.suppressLogPolicy = suppressLogPolicy;
    }


    public int getBatchSize()
    {
        return batchSize;
    }


    public void setBatchSize(int batchSize)
    {
        this.batchSize = batchSize;
    }


    public int getQueueCapacity()
    {
        return queueCapacity;
    }


    public void setQueueCapacity(int queueCapacity)
    {
        this.queueCapacity = queueCapacity;
    }


    public IExtraQueuePolicy getExtraQueuePolicy()
    {
        return extraQueuePolicy;
    }


    public void setExtraQueuePolicy(IExtraQueuePolicy extraQueuePolicy)
    {
        this.extraQueuePolicy = extraQueuePolicy;
    }


    public LinkedList<LogEvent> getQueue()
    {
        return queue;
    }


    public void setQueue(LinkedList<LogEvent> queue)
    {
        this.queue = queue;
    }

    public LogQueue(){
        
    }

    public LogQueue(int queueCapacity, int extraCapacity,int batchSize,  IExtraQueuePolicy extraQueuePolicy )
    {
        this.queueCapacity = queueCapacity; 
        this.batchSize = batchSize; 
        this.extraQueuePolicy = extraQueuePolicy;
    }
    
    
    public boolean  push(LogEvent logEvent)
    {
        if(suppressLogPolicy != null && suppressLogPolicy.suppressLog(logEvent))
        {
            //System.out.println("log is suppressed!");
            //if log is suppressed, we return true, push succeed. 
            return true;
        }
        synchronized(queue)
        {
            int size = queue.size();
            if(size >= queueCapacity)
            {
                //if the size exceed queueCapacity, need to check according to the policy
                //if the plicy could accept this logEvent ,then, add the logEvent (occupy the extraCapacity.
                // if the policy could NOT accept this logEvent, could NOT add it, return false
                if(extraQueuePolicy!= null && extraQueuePolicy.accept(this, logEvent))
                {
                    queue.add(logEvent);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                //if the size doesn't exceed queueCapacity, always add it 
                queue.add(logEvent);
            }
            
            queue.notifyAll();
            return true;
        }
    }
    
    public LogEventInfo get() 
    {
        synchronized(queue)
        {
            while(queue.size() == 0)
            {
                try
                {
                    queue.wait();
                }
                catch (InterruptedException e)
                {
                    // this should never happen since we never call Thread's interrupt function 
                    throw new RuntimeException(e);
                }
            }
            
            //if runs to here, means the queue is not empty
            ArrayList<LogEvent> logEvents = new ArrayList();
            int size = queue.size();
            int fetchSize = batchSize<size?batchSize:size;
            for(int i=0; i<fetchSize; i++)
            {
                LogEvent event = queue.removeFirst();
                //long logSequence = SequenceGenerator.getSequence(LogEvent.EVENT_SEQ_ID).nextVal();
                event.setLogSequence(logSequence);
                logSequence++;                
                logEvents.add(event);
            }

            LogEventInfo logEventInfo = new LogEventInfo();
            logEventInfo.setLogEventList(logEvents);
            logEventInfo.setConsumedSize(queue.size());
            logEventInfo.setQueueCapacity(queueCapacity);
            
            return logEventInfo;
        }
    }
    
    public int size() {
        synchronized(queue)
        {
            return queue.size();
        }
    }
    
   

}
