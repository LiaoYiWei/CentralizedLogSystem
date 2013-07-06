/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.IAppEnvNodeDao;
import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.entity.AppEnvRuleSimple;
import com.hp.et.log.entity.Application;
import com.hp.et.log.entity.Host;
import com.hp.et.log.entity.RuntimeInstance;


/**
 * 
 * Note: this class should be @Transactional
 */
public class CacheService
{
    private static Logger logger = LoggerFactory.getLogger(CacheService.class);
    private IRuntimeInstanceDao runtimeInstanceDao; 
    private IAppEnvDao appEnvDao; 
    private IAppEnvNodeDao appEnvNodeDao;
    
    private IAppEnvRuleDao appEnvRuleDao;
    
    public IAppEnvNodeDao getAppEnvNodeDao()
    {
        return appEnvNodeDao;
    }

    public void setAppEnvNodeDao(IAppEnvNodeDao appEnvNodeDao)
    {
        this.appEnvNodeDao = appEnvNodeDao;
    }

    public IAppEnvDao getAppEnvDao()
    {
        return appEnvDao;
    }

    public void setAppEnvDao(IAppEnvDao appEnvDao)
    {
        this.appEnvDao = appEnvDao;
    }

    public IRuntimeInstanceDao getRuntimeInstanceDao()
    {
        return runtimeInstanceDao;
    }

    public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao)
    {
        this.runtimeInstanceDao = runtimeInstanceDao;
    }

    public IAppEnvRuleDao getAppEnvRuleDao() {
		return appEnvRuleDao;
	}

	public void setAppEnvRuleDao(IAppEnvRuleDao appEnvRuleDao) {
		this.appEnvRuleDao = appEnvRuleDao;
	}



	@Cacheable("runtimeInstanceCache")
    public RuntimeInstance getRuntimeInstance(String runID)
    {
        logger.info("ENTER getRuntimeInstance");
        RuntimeInstance runtimeInstance = runtimeInstanceDao.findById(runID);
        
        if(runtimeInstance == null)
        {
            return null;
        }        
       
        AppEnvNode envNode = runtimeInstance.getAppEnvNode();
        Host host = envNode.getHost();
        AppEnv appEnv = envNode.getAppEnv();
        Application app = appEnv.getApplication();
        
        RuntimeInstance rstRuntimeInstance = new RuntimeInstance();
        rstRuntimeInstance.setRunId(runID);
        rstRuntimeInstance.setStartTime(runtimeInstance.getStartTime());
        AppEnvNode rstEnvNode = new AppEnvNode();
        rstEnvNode.setNodeId(envNode.getNodeId());
        rstEnvNode.setNodeName(envNode.getNodeName());
        rstEnvNode.setStatus(envNode.getStatus());
        Host rstHost = new Host();
        rstHost.setHostId(host.getHostId());
        rstHost.setHostName(host.getHostName());
        rstHost.setIpAddress(host.getIpAddress());
        rstEnvNode.setHost(rstHost);
        
        AppEnv rstAppEnv = new AppEnv();
        rstAppEnv.setEnvId(appEnv.getEnvId());
        rstAppEnv.setEnvName(appEnv.getEnvName());
        
        Application rstApp = new Application();
        rstApp.setAppId(app.getAppId());
        rstApp.setAppName(app.getAppName());
        rstAppEnv.setApplication(rstApp);
        rstEnvNode.setAppEnv(rstAppEnv);
        rstRuntimeInstance.setAppEnvNode(rstEnvNode);
        
        logger.info("EXIT getRuntimeInstance");
        return rstRuntimeInstance;
    }
    
    @Cacheable("allAppEnvCache")
    public ArrayList<AppEnv> getAllAppEnv()
    {
        logger.info("ENTER getAllAppEnv");
        ArrayList<AppEnv> appEnvs = appEnvDao.findAllAppEnvs();
        ArrayList<AppEnv> rstAppEnvs = new ArrayList();
        
        for(AppEnv appEnv: appEnvs)
        {
            Application app = appEnv.getApplication();
            Application rstApp = new Application();
            rstApp.setAppId(app.getAppId());
            rstApp.setAppName(app.getAppName());
            
            AppEnv rstAppEnv = new AppEnv();
            rstAppEnv.setApplication(rstApp);
            rstAppEnv.setEnvId(appEnv.getEnvId());
            rstAppEnv.setEnvName(appEnv.getEnvName());
            ArrayList<AppEnvNode> rstAppEnvNodes = new ArrayList();
            Collection<AppEnvNode> appEnvNodes = appEnv.getAppEnvNodeCollection();
            for(AppEnvNode appEnvNode: appEnvNodes)
            {
                Host host = appEnvNode.getHost();
                AppEnvNode rstAppEnvNode = new AppEnvNode();
                
                Host rstHost = new Host();
                rstHost.setHostId(host.getHostId());
                rstHost.setHostName(host.getHostName());
                rstHost.setIpAddress(host.getIpAddress());
                rstAppEnvNode.setAppEnv(rstAppEnv);
                rstAppEnvNode.setHost(rstHost);
                rstAppEnvNode.setNodeId(appEnvNode.getNodeId());
                rstAppEnvNode.setNodeName(appEnvNode.getNodeName());
                rstAppEnvNode.setStatus(appEnvNode.getStatus());
                
                //set latestRuntimeInstance 
                RuntimeInstance latestRuntimeInstance = runtimeInstanceDao.findLatestRuntimeInstanceByNodeID(rstAppEnvNode.getNodeId());
                //one node has at least a runtime instance
                if(latestRuntimeInstance != null)
                {
	                RuntimeInstance latestRuntimeInstanceRst = new RuntimeInstance();
	                latestRuntimeInstanceRst.setRunId(latestRuntimeInstance.getRunId());
	                latestRuntimeInstanceRst.setStartTime(latestRuntimeInstance.getStartTime());
	                latestRuntimeInstanceRst.setClientStartTime(latestRuntimeInstance.getClientStartTime());
	                latestRuntimeInstanceRst.setEventCollection(null);
	                latestRuntimeInstanceRst.setAppEnvNode(rstAppEnvNode);
	                
	                rstAppEnvNode.setLatestRuntimeInstance(latestRuntimeInstanceRst);
                }
                else
                {
                	rstAppEnvNode.setLatestRuntimeInstance(null);
                }
                
                rstAppEnvNodes.add(rstAppEnvNode);
            }
            rstAppEnv.setAppEnvNodeCollection(rstAppEnvNodes);
            rstAppEnvs.add(rstAppEnv);
        }
        logger.info("EXIT getAllAppEnv");
        return rstAppEnvs;
    }
    
    @Cacheable("nodeSeverityCache")
    public Integer querySeverityByNodeId(String nodeId)
    {
        logger.info("ENTER querySeverityByNodeId");
        AppEnvNode node = appEnvNodeDao.findById(nodeId);
        if(node == null)
        {
            return null; 
        }
        
        Integer severity = node.getStatus();
        if(severity == null)
        {
            //if this node didn't set severity 
            //set it as trace 
            severity = LogSeverityEnum.TRACE.getIndex();
        }
        logger.info("EXIT querySeverityByNodeId");
        return severity; 
        
    }
    
    @Cacheable("latestRuntimeInstanceCache")
    public RuntimeInstance queryLatestRuntimeInstanceByNodeID(String nodeID)
    {
    	logger.info("ENTER queryLatestRuntimeInstanceByNodeID");
    	RuntimeInstance latestRuntimeInstance = runtimeInstanceDao.findLatestRuntimeInstanceByNodeID(nodeID);
    	if(latestRuntimeInstance != null)
        {
            RuntimeInstance latestRuntimeInstanceRst = new RuntimeInstance();
            latestRuntimeInstanceRst.setRunId(latestRuntimeInstance.getRunId());
            latestRuntimeInstanceRst.setStartTime(latestRuntimeInstance.getStartTime());
            latestRuntimeInstanceRst.setClientStartTime(latestRuntimeInstance.getClientStartTime());
            latestRuntimeInstanceRst.setEventCollection(null);
            latestRuntimeInstanceRst.setAppEnvNode(null);
            
            return latestRuntimeInstanceRst;
        }
    	else
    	{
    		return null;
    	}
    }
    
//    @Cacheable("appEnvRuleCache")
//    public List getAppEnvRuleByEnvId(String envId)
//    {
//        logger.info("ENTER appEnvRuleCache");
//        List ruleList = appEnvRuleDao.findRulesByEnvId(envId, AppEnvRuleInfo.SIMPLE.intValue());
//        List runlInfos = simpleRulesConvert2RuleInfo(ruleList, envId);
//        logger.info("EXIT appEnvRuleCache");
//        return runlInfos;
//    }
//    
//    @Cacheable("envIdByRunIdCache")
//    public String getEnvIdByRunId(String runId)
//    {
//        logger.info("ENTER envIdByRunIdCache");
//        String envId = runtimeInstanceDao.findEnvIdByRunId(runId);
//        logger.info("EXIT envIdByRunIdCache");
//        return envId;
//    }
    
//    /**
//	 * convert the simple rule list to rule info bean list
//	 * @param ruleList
//	 * @return
//	 */
//	private List<AppEnvRuleInfo> simpleRulesConvert2RuleInfo(List ruleList, String envId){
//		List<AppEnvRuleInfo> ruleInfos = new ArrayList<AppEnvRuleInfo>();
//		AppEnv env = appEnvDao.findById(envId);
//		if (env==null){
//			logger.info("The AppEnv is null by envId = "+envId);
//			return null;
//		}
//		for (Iterator ite=ruleList.iterator();ite.hasNext();){
//			AppEnvRuleSimple simple = (AppEnvRuleSimple) ite.next();
//			AppEnvRuleInfo info = new AppEnvRuleInfo();
//			if (env != null){
//				info.setEnvId(env.getEnvId());
//				info.setEnvName(env.getEnvName());
//				Application app = env.getApplication();
//				if (app!=null){
//					info.setAppName(app.getAppName());
//				}
//			}
//			info.setCreateTime(simple.getCreateTime());
//			info.setEmailPdl(simple.getEmailPdl());
//			info.setMessage(simple.getMessage());
//			info.setMessageType(simple.getMessageType());
//			info.setMsgStatus(simple.getMsgStatus());
//			info.setRuleId(simple.getRuleId());
//			info.setRuleName(simple.getRuleName());
//			info.setSeverity(simple.getSeverity());
//			info.setSuppressionTime(simple.getSuppressionTime());
//			info.setThrowableMessage(simple.getThrowableMessage());
//			info.setThrowMsgStatus(simple.getThrowMsgStatus());
//			info.setUpdateTime(simple.getUpdateTime());
//			ruleInfos.add(info);
//		}
//		return ruleInfos;
//	}
}

