/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.service;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.IAppEnvNodeDao;
import com.hp.et.log.dao.IApplicationDao;
import com.hp.et.log.dao.IHostDao;
import com.hp.et.log.dao.IRejectHostHistoryDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.ApplicationInstance;
import com.hp.et.log.domain.bean.LogEventInfo;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.entity.Application;
import com.hp.et.log.entity.Host;
import com.hp.et.log.entity.RejectHostHistory;
import com.hp.et.log.entity.RuntimeInstance;
import com.hp.et.log.metrics.MetricsManager;

@Path("/")
public class Authentication
{

	private static Logger logger = LoggerFactory.getLogger(Authentication.class);
	private IApplicationDao applicationDao;
	
	private IAppEnvDao appEnvDao;
	
	private IAppEnvNodeDao appEnvNodeDao;
	
	private IHostDao hostDao;
	
	private IRejectHostHistoryDao rejectHostHistoryDao;
	
	private IRuntimeInstanceDao runtimeInstanceDao;
	
	private MetricsManager metricsManager;
	
	private CacheService cacheService;
	
	
	public CacheService getCacheService()
    {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService)
    {
        this.cacheService = cacheService;
    }

    public MetricsManager getMetricsManager()
    {
        return metricsManager;
    }

    public void setMetricsManager(MetricsManager metricsManager)
    {
        this.metricsManager = metricsManager;
    }

    public Authentication()
	{
	}
    
    public IApplicationDao getApplicationDao()
    {
        return applicationDao;
    }

    public void setApplicationDao(IApplicationDao applicationDao)
    {
        this.applicationDao = applicationDao;
    }

    public IAppEnvDao getAppEnvDao()
    {
        return appEnvDao;
    }

    public void setAppEnvDao(IAppEnvDao appEnvDao)
    {
        this.appEnvDao = appEnvDao;
    }

    public IAppEnvNodeDao getAppEnvNodeDao()
    {
        return appEnvNodeDao;
    }

    public void setAppEnvNodeDao(IAppEnvNodeDao appEnvNodeDao)
    {
        this.appEnvNodeDao = appEnvNodeDao;
    }

    public IHostDao getHostDao()
    {
        return hostDao;
    }

    public void setHostDao(IHostDao hostDao)
    {
        this.hostDao = hostDao;
    }

    public IRuntimeInstanceDao getRuntimeInstanceDao()
    {
        return runtimeInstanceDao;
    }

    public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao)
    {
        this.runtimeInstanceDao = runtimeInstanceDao;
    }

    public IRejectHostHistoryDao getRejectHostHistoryDao() {
		return rejectHostHistoryDao;
	}

	public void setRejectHostHistoryDao(IRejectHostHistoryDao rejectHostHistoryDao) {
		this.rejectHostHistoryDao = rejectHostHistoryDao;
	}

	@POST
    @Path("/register")
    @Consumes("application/java_serializable")
    @Produces("application/java_serializable")   
    @CacheEvict (value = {"allAppEnvCache","latestRuntimeInstanceCache"}, allEntries=true)   
    public String register(ApplicationInstance applicationInstance, @Context HttpServletRequest request) throws Exception
    {    	
        logger.info("ENTER register");
        if(applicationInstance == null)
        {
            //should not happen
            throw new RuntimeException("input param is null");
        }
        
        logger.info(applicationInstance.debugString());
    	//1.validate ip from host table.
        String ipAddress = request.getRemoteAddr();     
        logger.info("requested IP address is:" + ipAddress);
        Host host = this.hostDao.getHostByIpAddress(ipAddress);
        if (host == null)
        {   
        	logger.info("the request ip address is not registed!");
        	//add this host to RejectHostHistory table
        	logger.info("add host to RejectHostHistory table!");
        	RejectHostHistory rejHost = new RejectHostHistory();
        	rejHost.setRejHostName(applicationInstance.getHostName());
        	rejHost.setRejIpAddress(request.getRemoteAddr());
        	rejHost.setRejAppName(applicationInstance.getAppName());
        	rejHost.setRejNodeName(applicationInstance.getNodeName());
        	rejHost.setRejEnvName(applicationInstance.getEnv());
        	rejHost.setRejCreateTime(new Date());
        	this.rejectHostHistoryDao.persist(rejHost);
        	return null;
//        	throw new RuntimeException("IP Address is invalid:" + ipAddress);
        }else 
        {
        	//2.update host_name to host table
        	if (!host.getHostName().equals(applicationInstance.getHostName())) {
        		host.setHostName(applicationInstance.getHostName());
        		this.hostDao.merge(host);
        	}
        }
        
        //3.retrieve/create appID from application table
        Application application = applicationDao.getApplicationByAppName(applicationInstance.getAppName());
        if(application == null) 
        {
        	application = new Application();
        	application.setAppName(applicationInstance.getAppName());
        	this.applicationDao.persist(application);
        }
        //4.create/retrieve app_env_id from app_env table
        AppEnv appEnv = appEnvDao.getAppEnvByAppIdAndEnvName(application.getAppId(), applicationInstance.getEnv());
        if (appEnv == null)
        {
        	appEnv = new AppEnv();
        	appEnv.setApplication(application);        	
        	appEnv.setEnvName(applicationInstance.getEnv());
        	appEnvDao.persist(appEnv);
        }
        
        //5.create/retrieve app_env_node_id from app_env_node table
        AppEnvNode appEnvNode = appEnvNodeDao.findNodeByEnvIdAndNodeName(appEnv.getEnvId(), applicationInstance.getNodeName());
        if(appEnvNode == null)
        {
        	appEnvNode = new AppEnvNode();
        	appEnvNode.setNodeName(applicationInstance.getNodeName());
        	appEnvNode.setHost(host);
        	appEnvNode.setAppEnv(appEnv);
        	appEnvNode.setStatus(LogSeverityEnum.TRACE.getIndex());
        	appEnvNodeDao.persist(appEnvNode);
        }else {
        	//if host ip address is not consistent, please modify the node to associate the current host.
        	if (!host.getHostId().equals(appEnvNode.getHost().getHostId())){
        		appEnvNode.setHost(host);
        		appEnvNodeDao.merge(appEnvNode);
        	}
        }
        
        //6.create new runtime_instance record.
        RuntimeInstance ri = new RuntimeInstance();
        ri.setAppEnvNode(appEnvNode);
        ri.setStartTime(new Date());
        ri.setClientStartTime(new Date(applicationInstance.getAppStartupTimestamp()));
        runtimeInstanceDao.persist(ri);
        logger.info("EXIT register");
        return ri.getRunId();
    }
    
   

	

}
