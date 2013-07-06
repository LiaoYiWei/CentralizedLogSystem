package com.hp.et.log.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.cache.RuleCacheService;
import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.AppClientBasisInfo;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.email.LogReminderSender;
import com.hp.et.log.entity.AppEnvRule;
import com.hp.et.log.entity.AppEnvRuleSimple;
import com.hp.et.log.entity.RuntimeInstance;

public class EmailService {
    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private IRuntimeInstanceDao runtimeInstanceDao;

    private CacheService cacheService;

    private IAppEnvRuleDao appEnvRuleDao;
    
    private RuleCacheService ruleCacheService;
	

    public RuleCacheService getRuleCacheService() {
		return ruleCacheService;
	}

	public void setRuleCacheService(RuleCacheService ruleCacheService) {
		this.ruleCacheService = ruleCacheService;
	}

	public IRuntimeInstanceDao getRuntimeInstanceDao() {
	return runtimeInstanceDao;
    }

    public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao) {
	this.runtimeInstanceDao = runtimeInstanceDao;
    }

    public CacheService getCacheService() {
	return cacheService;
    }

	public void setCacheService(CacheService cacheService) {
	this.cacheService = cacheService;
    }

    public IAppEnvRuleDao getAppEnvRuleDao() {
	return appEnvRuleDao;
    }

    public void setAppEnvRuleDao(IAppEnvRuleDao appEnvRuleDao) {
	this.appEnvRuleDao = appEnvRuleDao;
    }

    /**
     * send email function
     * 
     * @param logs
     */
    public void sendEmail(final LogEvent logEvent) {
    	logger.info("ENTER SendEmail!");
    	RuntimeInstance ri = cacheService.getRuntimeInstance(logEvent.getRunId());
    	String envId = ri.getAppEnvNode().getAppEnv().getEnvId();
    	List<AppEnvRule> rules = ruleCacheService.getRulesByEnvId(envId);
    	if (rules == null || rules.size() == 0){
    		logger.info("This envId has not any email rules!");
    	}else{
    		for (int i=0; i< rules.size(); i++){
    			AppEnvRule rule = rules.get(i);
    			if (rule.judgeSuppression(false)){
    				if (rule.judgeLogByExpression(logEvent)){
    					if (rule.judgeSuppression(true)){
    						AppClientBasisInfo basis = findAppClientBasisInfoByRunId(logEvent.getRunId());
    						LogReminderSender mailSender = new LogReminderSender();
    						mailSender.sendLogReminderEMail(logEvent, rule, basis);
    					}
    				}
    			}
    		}
    	}
    	logger.info("EXIT SendEmail!");
    }
    
    private AppClientBasisInfo findAppClientBasisInfoByRunId(String runId) {
		if (runId != null) {
			RuntimeInstance runtimeInstance = cacheService.getRuntimeInstance(runId);
		    //List<Object[]> list = runtimeInstanceDao.getHostAppEnvNodeByRunId(runId);
		    if (runtimeInstance != null) {
		    	//Object[] infos = list.get(0);
				AppClientBasisInfo basis = new AppClientBasisInfo();
				basis.setAppName(runtimeInstance.getAppEnvNode().getAppEnv().getApplication().getAppName());
				basis.setEnvName(runtimeInstance.getAppEnvNode().getAppEnv().getEnvName());
				basis.setNodeName(runtimeInstance.getAppEnvNode().getNodeName());
				basis.setHostName(runtimeInstance.getAppEnvNode().getHost().getHostName());
				basis.setHostIp(runtimeInstance.getAppEnvNode().getHost().getIpAddress());
				return basis;
		    }
		}
		return null;
    }
}
