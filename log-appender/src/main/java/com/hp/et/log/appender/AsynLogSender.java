/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.hp.et.log.clientfacade.RestfulClientFacade;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.LogSeverityEnum;

public class AsynLogSender implements ILogSender, Runnable
{

    
    private final  int SHUTDOWN = 7;
    private static int MAX_THREADS = 3;
    private static int MIN_QUEUE_CAPACITY = 10000;
    
    private RunContext runContext;
    private int threads = 1;
    
    private LogQueue logQueue;
    
    //private int feedSeverity = LogSeverityEnum.TRACE.getIndex();
    
    //private ExecutorService executorPool; 
    
    private int heartBeatIntervalSec = 3; //3 seconds by default
    
    private Thread heartBeatThd = null;
    private Thread sendEventThread = null;
    
    //private ReadWriteLock feedSeverityLock = new  ReentrantReadWriteLock();
    
    public  void sendLog( final LogEvent logEvent)
    {
        synchronized(this)
        {
            int severity = runContext.getSeverity(); 
            if(logEvent.getSeverity() < severity)
            {
                //do not add this log
                return; 
            }
            
            boolean pushSuccess = logQueue.push(logEvent);
            if(!pushSuccess)
            {
                //if could not push successfully 
                sendSystemError();
            } 
        }
    }
    public void startup(final RunContext context)
    {
        try
        {
            this.runContext = context;
            threads = (MAX_THREADS < threads) ? MAX_THREADS : threads;

            AppenderUtil.authenticateApp(context); //throw RuntimeException if could not authenticate

//            executorPool = Executors.newFixedThreadPool(threads);
//
//            for (int i = 0; i < threads; i++)
//            {
//                //start thread tasks
//                executorPool.submit(this);
//            }
            sendEventThread = new Thread(this);
            sendEventThread.start();
            int severity = AppenderUtil.queryFeedSeverity(context,logQueue.getQueueCapacity(), logQueue.size());
            //set severity to global Context
            context.setSeverity(severity);

            heartBeatThd = new Thread(new Runnable()
            {

                public void run()
                {
                    try
                    {
                        while (!Thread.currentThread().isInterrupted())
                        {
                            heartBeat();
                            Thread.sleep(heartBeatIntervalSec * 1000);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        System.out.println("-------------interrupted heart beat thread!-----------");
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
            heartBeatThd.start();
        }
        catch (Exception ex)
        {
            System.out.println("Centralized log system init failure");
            throw new RuntimeException(ex);
        }
        
    }
   
    
   
    private void heartBeat ()
    {
      
        int previousSeverity = runContext.getSeverity();
        
        //System.out.println(Thread.currentThread().getName() + "-------------Begin to queryFeedSeverity------------------");
        //long startTime = System.currentTimeMillis(); 
        int currentSeverity = AppenderUtil.queryFeedSeverity(runContext,logQueue.getQueueCapacity(), logQueue.size());
        //long endTime = System.currentTimeMillis();
        //System.out.println(Thread.currentThread().getName() + "-------------End to queryFeedSeverity------------------"+ (endTime-startTime)/1000);
       
        if(currentSeverity == SHUTDOWN){
            if(sendEventThread != null && sendEventThread.isAlive()){
                sendEventThread.interrupt();
            }
            Thread.currentThread().interrupt();
            return;
        }
        
        
        if(currentSeverity == previousSeverity)
        {
            //severity no change 
            //do nothing 
            return;
        }
        
        //severity need to be updated. 
        synchronized(this)
        {
            runContext.setSeverity(currentSeverity);
            sendStateChangeLog(previousSeverity, currentSeverity); //send enable state log
        }
     }
    
    private void sendStateChangeLog(int previousSeverity,int currentSeverity)
    {
        LogSeverityEnum previousEnum = LogSeverityEnum.fromIndex(previousSeverity);
        LogSeverityEnum currentEnum = LogSeverityEnum.fromIndex(currentSeverity);
        if(previousEnum == null || currentEnum == null)
        {
            //impossible
            return; 
        }
        
        LogEvent stateChangeEvent = new LogEvent();
        stateChangeEvent.setRunId(runContext.getApplicationInstance().getRunId());
        stateChangeEvent.setLoggerName(AsynLogSender.class.getName());
        stateChangeEvent.setThreadName(Thread.currentThread().getName());
        stateChangeEvent.setTimestamp(System.currentTimeMillis());
        stateChangeEvent.setMessageType(LogEvent.MESSAGE_TYPE_LOG_SYSTEM);
        
        String msg = "log feeding severity change from " + previousEnum.getName() + " to " + currentEnum.getName();
        stateChangeEvent.setMessage(msg);
        stateChangeEvent.setSeverity(LogSeverityEnum.valueOf("INFO").getIndex());
        
        logQueue.push(stateChangeEvent);
    }
    
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                LogEventInfo logInfo = logQueue.get();

                try {
                    logInfo.setRunId(runContext.getApplicationInstance().getRunId());
                    RestfulClientFacade clientFacade = new RestfulClientFacade(runContext.getServerUrl());

                    //System.out.println(Thread.currentThread().getName()+"-----------START to send log to server-----------------: batch size:"+ logInfo.getLogEventList().size());
                    // long startTime = System.currentTimeMillis();
                    clientFacade.sendLogEvent(logInfo);
                    // long endTime = System.currentTimeMillis();
                    // System.out.println(Thread.currentThread().getName()+"-----------Send done-----------------------------------: duration:" + (endTime-startTime)/1000);
                } catch (Throwable ex) {
                    // if there is any exception
                    // ignore
                    //System.out.println(ex);
                    if (ex instanceof InterruptedException) {
                        throw ex;
                    }
                }

            }
        } catch (Throwable e) {
            System.out.println("-------------interrupted send event thread!-----------");
            e.printStackTrace();
        }
    }
    
    private void sendSystemError()
    {
         
        LogEvent errorEvent = new LogEvent();
        errorEvent.setRunId(runContext.getApplicationInstance().getRunId());
        errorEvent.setLoggerName(AsynLogSender.class.getName());
        errorEvent.setThreadName(Thread.currentThread().getName());
        errorEvent.setTimestamp(System.currentTimeMillis());
        errorEvent.setMessageType(LogEvent.MESSAGE_TYPE_LOG_SYSTEM);
        errorEvent.setMessage("Client side error stack is full! Failed to send log! ");
        errorEvent.setSeverity(LogSeverityEnum.valueOf("ERROR").getIndex());
        logQueue.push(errorEvent); //don't care whether it's suppressed or not. 
        
    }
    
   
    
    public int getHeartBeatIntervalSec()
    {
        return heartBeatIntervalSec;
    }
    public void setHeartBeatIntervalSec(int heartBeatIntervalSec)
    {
        this.heartBeatIntervalSec = heartBeatIntervalSec;
    }
    public int getThreads()
    {
        return threads;
    }
    public void setThreads(int threads)
    {
        this.threads = threads;
    }
    public LogQueue getLogQueue()
    {
        return logQueue;
    }
    public void setLogQueue(LogQueue logQueue)
    {
        this.logQueue = logQueue;
    }
    
    
}
