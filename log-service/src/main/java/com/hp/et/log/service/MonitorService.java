package com.hp.et.log.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;

import com.hp.et.log.dao.IAppEnvNodeDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.AppEnvMetrics;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.MonitorInfo;
import com.hp.et.log.domain.bean.MonitorRegisterInfo;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.metrics.MetricsManager;
import com.hp.et.log.monitor.IMonitorManager;

@Path("/MonitorService")
public class MonitorService {

    private static Logger logger = LoggerFactory.getLogger(MonitorService.class);
	private IMonitorManager monitorMng;
	private IRuntimeInstanceDao runtimeInstanceDao;
	private MetricsManager metricsManager; 
	private IAppEnvNodeDao appEnvNodeDao;
	
	

	public IAppEnvNodeDao getAppEnvNodeDao()
    {
        return appEnvNodeDao;
    }

    public void setAppEnvNodeDao(IAppEnvNodeDao appEnvNodeDao)
    {
        this.appEnvNodeDao = appEnvNodeDao;
    }

    public IRuntimeInstanceDao getRuntimeInstanceDao() {
		return runtimeInstanceDao;
	}

	public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao) {
		this.runtimeInstanceDao = runtimeInstanceDao;
	}

	@Path("/start")
	@POST
	@Produces("application/java_serializable")
	public void startup() {
		monitorMng.startup();
	}

	/**
	 * create a monitor to monitor list
	 * 
	 * @param instanceId
	 * @param filters
	 * @return monitor's id
	 */
	@Path("/startMonitoring")
	@POST
	@Produces("application/java_serializable")
	public Long startMonitoring(MonitorRegisterInfo monitorRegisterInfo,
			@Context HttpServletRequest request) {
	    logger.info("ENTER startMonitoring");
		List<String> nodeIds = monitorRegisterInfo.getNodeIds();
		long rst = -1L;
		if (null != nodeIds && !nodeIds.isEmpty()) {
			rst= monitorMng.startMonitoring(nodeIds,
					monitorRegisterInfo.getFilters());
			
		}
		logger.info("EXIT startMonitoring");
		return rst;
	}

	/**
	 * remove a monitor from monitor list through monitor id
	 * 
	 * @param monitorId
	 */
	@Path("/stopMonitoring")
	@POST
	@Produces("application/java_serializable")
	public void stopMonitoring(MonitorInfo monitorInfo) {
	    logger.info("ENTER stopMonitoring");
		if (null != monitorInfo ) {
				monitorMng.stopMonitoring(monitorInfo.getMonitorId());
		}
		logger.info("EXIT stopMonitoring");
	}

	/**
	 * @param monitorId
	 * @return
	 */
	@Path("/getLogEventsFromMonitorQueue")
	@POST
	@Produces("application/java_serializable")
	public List<LogEvent> getLogEventsFromMonitorQueue(
			MonitorInfo monitorInfo) {
	    logger.info("ENTER getLogEventsFromMonitorQueue");
	    List<LogEvent> logEventList = monitorMng.getLogEventsFromMonitorQueue(monitorInfo.getMonitorId());
	    logger.info("EXIT getLogEventsFromMonitorQueue");
	    return logEventList;
	}

	
	@Path("/getLoadMonitorMetrics")
    @POST
    @Produces("application/java_serializable")
    public List<AppEnvMetrics> getLoadMonitorMetrics() {
        
	    logger.info("ENTER getLoadMonitorMetrics");
	    try
	    {
	        List<AppEnvMetrics> appEnvMetricsList =  metricsManager.getAllMetricsData();
	        logger.info("EXIT getLoadMonitorMetrics");
	        return appEnvMetricsList;
	    }
	    catch(Exception ex)
	    {
	        throw new RuntimeException(ex);
	    }
    }
	
	@Path("/updateNodeSeverity")
    @POST
    @Produces("application/java_serializable")
    @CacheEvict (value = "nodeSeverityCache", allEntries=true)
    public Void updateNodeSeverity(ArrayList<NodeInfo> nodeInfoList) {
        
        logger.info("ENTER updateNodeSeverity");
       
        try
        {
            for(NodeInfo nodeInfo: nodeInfoList)
            {
                logger.debug(nodeInfo.debugString());
                AppEnvNode appEnvNode = appEnvNodeDao.findById(nodeInfo.getId());
                if(appEnvNode == null)
                {
                    logger.warn("Could not find appEnvNode by nodeID");
                }
                appEnvNode.setStatus(nodeInfo.getAcceptSeverity());
                
            }
            
            logger.info("EXIT updateNodeSeverity");
            return null;
        }
        catch(Exception ex)
        {
            logger.error("error handling updateNodeSeverity",ex);
            throw new RuntimeException(ex);
        }
    }
	
	
	public MetricsManager getMetricsManager()
    {
        return metricsManager;
    }

    public void setMetricsManager(MetricsManager metricsManager)
    {
        this.metricsManager = metricsManager;
    }

    public IMonitorManager getMonitorMng() {

		return monitorMng;
	}

	public void setMonitorMng(IMonitorManager monitorMng) {
		this.monitorMng = monitorMng;
	}
	
	
	

}
