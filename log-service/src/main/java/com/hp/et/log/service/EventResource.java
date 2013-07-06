package com.hp.et.log.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;


import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hp.et.log.monitor.IMonitorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.dao.IEventDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.entity.AppEnvRuleSimple;
import com.hp.et.log.entity.Application;
import com.hp.et.log.entity.Event;
import com.hp.et.log.entity.Host;
import com.hp.et.log.entity.RuntimeInstance;
import com.hp.et.log.metrics.MetricsManager;

@Path("/events")
public class EventResource {
	private static Logger logger = LoggerFactory.getLogger(EventResource.class); 
	
	private IEventDao eventDao;
	
	private IAppEnvRuleDao appEnvRuleDao;
	
	private IRuntimeInstanceDao runtimeInstanceDao;
	
	private EventExecuteService eventExecuteService;
	
	private ExecutorService executorService;

	private MetricsManager metricsManager; 
	
	private CacheService cacheService;


	
	
	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public IAppEnvRuleDao getAppEnvRuleDao() {
		return appEnvRuleDao;
	}

	public IRuntimeInstanceDao getRuntimeInstanceDao() {
		return runtimeInstanceDao;
	}

	public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao) {
		this.runtimeInstanceDao = runtimeInstanceDao;
	}

	public void setAppEnvRuleDao(IAppEnvRuleDao appEnvRuleDao) {
		this.appEnvRuleDao = appEnvRuleDao;
	}

	public MetricsManager getMetricsManager()
    {
        return metricsManager;
    }

	public void setMetricsManager(MetricsManager metricsManager)
    {
        this.metricsManager = metricsManager;
    }



    public IEventDao getEventDao()
    {
        return eventDao;
    }

    public void setEventDao(IEventDao eventDao)
    {
        this.eventDao = eventDao;
    }
    
    private boolean isLatestRunID(String runID)
    {
    	logger.debug("ENTER isLatestRunID");
    	logger.info("runtimeInstance ID:" + runID);
    	if(runID == null)
    	{
    		logger.error("runID is null");
    		throw new RuntimeException("runID is null");
    	}
    	RuntimeInstance runtimeInstance = cacheService.getRuntimeInstance(runID);
        if(runtimeInstance == null)
        {
            //should not happen.
            logger.error("could not get runtimeInstance by runID");
            throw new RuntimeException("runtimeInstance is null");
        }
        String nodeId = runtimeInstance.getAppEnvNode().getNodeId();
        
        RuntimeInstance latestRuntimeInstance = cacheService.queryLatestRuntimeInstanceByNodeID(nodeId);
        if(latestRuntimeInstance == null)
        {
        	logger.error("could not find latest runtime instance, something wrong");
        	throw new RuntimeException("could not get latest runtime instance for node");
        	
        }
        
        if(runID.equals(latestRuntimeInstance.getRunId()))
        {
        	//this is to deal with thread which was not killsed by client container 
        	//When client container redeploy the application, the sender thread won't stop 
        	//it will keep sending data to server while the redeployed new sender will also send data to server
        	//which means , a node with two runtime instance feeding data to server
        	//ideally, there should be a solution in client side to detect redeployment, and close the sender thread when undeployment happen
        	//however, we still don't have a good solution on this 
        	//we have to use "unmanaged" thread in container to send log to server 
        	//although this is not recommended in container and will cause this kind of issue
        	//we confirm this issue and we put the checking logic in service side 
        	//we detect that this node has another latest runtime instance, we don't save data for previous runtime instance any more
        	logger.info("A new runtime instance Id " + latestRuntimeInstance.getRunId() + " detected!");
        	logger.debug("EXIT isLatestRunID");
        	return true;
        }
        else
        {
        	logger.debug("EXIT isLatestRunID");
        	return false;
        }

    	
    }

    @POST
	@Consumes("application/java_serializable")
	@Produces("application/java_serializable") 
    public Integer createEvent(final LogEventInfo logInfo, @HeaderParam("Content-Length") int length)
    {
        //add the remainingSize when asynLogSender
        logger.info("ENTER createEvent");
        logger.debug("-----Content length--------"+length);
        try
        {
            if(!isLatestRunID(logInfo.getRunId()))
            {
            	logger.warn("Is NOT latest RunID, quit processing");
            	return 0;
            }
            ArrayList<LogEvent> logs = logInfo.getLogEventList();
            for (final LogEvent logEvent : logs)
            {

                //for each log into system, push to queue to save the log
                executorService.submit(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            eventExecuteService.createEvent(logEvent);
                        }
                        catch (Throwable ex)
                        {
                            //if the task runs into exception 
                            //transaction will be rollback. 
                            //that's all. 
                            logger.error("Error in createEvent", ex);
                        }
                    }
                });
                
            }
            //update log metrics
            metricsManager.updateLogMetrics(logInfo,length);
            
            logger.info("EXIT createEvent");
            return 0;
        }
        catch (Exception ex)
        {
            logger.error("error",ex);
            throw new RuntimeException(ex);
        }
    }

    @POST
    @Path("/findEventByQueryInfo")
    @Produces("application/java_serializable")
	public List<LogEvent> findEventByQueryInfo(QueryInformation info) {
        logger.info("ENTER findEventByQueryInfo");
        try
        {
        	if(info.getTime() == null && (info.getSpecificTime() == null || info.getSpecificTime()=="")) {
        	    logger.warn("TIME should not be null.");
        	    logger.info("EXIT findEventByQueryInfo");
        		return null;
        	}
    		List events = eventDao.findEventByQueryInfo(info);
    		List<LogEvent> logEvents = convertEvent2LogEvent(events);
    		logger.info("EXIT findEventByQueryInfo");
    		return logEvents;
        }
        catch(Exception ex)
        {
            logger.error("error for query", ex);
            throw new RuntimeException(ex);
        }
	}

    @POST
    @Path("/findEventsCountByQueryInfo")
    @Produces("application/java_serializable")
    public Long findEventCountByQueryInfo(QueryInformation info)
    {
        logger.info("ENTER findEventCountByQueryInfo");
        if (info.getTime() == null && (info.getSpecificTime() == null || info.getSpecificTime() == ""))
        {
            logger.warn("TIME should not be null.");
            logger.info("EXIT findEventCountByQueryInfo");
            return null;
        }
        
        Long cnt = eventDao.getEventCountByQueryInfo(info);
        logger.info("EXIT findEventCountByQueryInfo");
        return cnt;
    }
    
    private List<LogEvent> convertEvent2LogEvent(List events){
    	List<LogEvent> logEvents = new ArrayList<LogEvent>();
    	for (Iterator ite=events.iterator();ite.hasNext();){
    		Object[] rsts = (Object[]) ite.next();
    		
    		Event e = (Event) rsts[0];
    		RuntimeInstance ri = (RuntimeInstance)rsts[1];
    		Application app = (Application)rsts[2];
    		AppEnv env = (AppEnv)rsts[3];
    		Host host = (Host)rsts[4];
    		AppEnvNode node = (AppEnvNode)rsts[5];
    		
    		
    		LogEvent logEvent = new LogEvent();
			logEvent.setId(e.getEventId());
			logEvent.setSeverity(e.getSeverity());
			logEvent.setMessage(e.getMessage());
			logEvent.setLoggerName(e.getLoggerName());
			logEvent.setThreadName(e.getThreadName());
			logEvent.setThrowableMessage(e.getThrowableMessage());
			logEvent.setMessageType(e.getMessageType());
			logEvent.setTimestamp(e.getCreateTime().getTime());
			logEvent.setLogSequence(e.getLogSequence());
			logEvent.setRunId(ri.getRunId());
			logEvent.setAppName(app.getAppName());
			logEvent.setEnv(env.getEnvName());
			logEvent.setHost(host.getHostName());
			logEvent.setNodeName(node.getNodeName());
			
			logEvent.setAttribute1Name(e.getAttribute1Name());
			logEvent.setAttribute1Value(e.getAttribute1Value());
			logEvent.setAttribute2Name(e.getAttribute2Name());
			logEvent.setAttribute2Value(e.getAttribute2Value());
			logEvent.setAttribute3Name(e.getAttribute3Name());
			logEvent.setAttribute3Value(e.getAttribute3Value());
			logEvent.setAttribute4Name(e.getAttribute4Name());
			logEvent.setAttribute4Value(e.getAttribute4Value());
			logEvent.setAttribute5Name(e.getAttribute5Name());
			logEvent.setAttribute5Value(e.getAttribute5Value());
			
			logEvents.add(logEvent);
		}
		return logEvents;
    }
    
    @POST
    @Path("/feedEnabled")
    @Consumes("application/java_serializable")
    @Produces("application/java_serializable")    
    public Integer feedEnabled(LogEventInfo logEventInfo)
    {
        logger.info("ENTER feedEnabled");
        
        try
        {
        	if(!isLatestRunID(logEventInfo.getRunId()))
            {
            	logger.warn("Is NOT latest RunID, quit processing");
            	//we should inform client side to stop sending 
            	//but at this moment, we simply send disable status to client
            	return LogSeverityEnum.SHUTDOWN.getIndex(); 
            }
        	
            RuntimeInstance runtimeInstance = cacheService.getRuntimeInstance(logEventInfo.getRunId());
            String nodeId = runtimeInstance.getAppEnvNode().getNodeId();
            logger.info("nodeId is:" + nodeId);
            
            metricsManager.updateLogMetrics(logEventInfo,0);
            
            Integer severity = cacheService.querySeverityByNodeId(nodeId);
            logger.info("severity is:" + severity);
            
            logger.info("EXIT feedEnabled");
            return severity; 
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        
        
    }

	public EventExecuteService getEventExecuteService() {
		return eventExecuteService;
	}

	public void setEventExecuteService(EventExecuteService eventExecuteService) {
		this.eventExecuteService = eventExecuteService;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

}
