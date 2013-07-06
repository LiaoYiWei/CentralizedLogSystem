package com.hp.et.log.rules;

import java.util.HashMap;

import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.dao.jpaimpl.RuntimeInstanceJpaDao;
import com.hp.et.log.domain.bean.LogEvent;



public class RuleEngineService implements IRuleEngineService{
	
	
	
	
	
	private HashMap<String, IRuleEngine> appRuleEngineMap = new HashMap();

	private IAppRuleEngineLoader appRuleEngineLoader; 
	
	public enum RuleType {
		DROOLS, GROOVY
	};

	private IRuntimeInstanceDao runtimeInstanceDao;
	
	

	public HashMap<String, IRuleEngine> getAppRuleEngineMap()
    {
        return appRuleEngineMap;
    }

    public void setAppRuleEngineMap(HashMap<String, IRuleEngine> appRuleEngineMap)
    {
        this.appRuleEngineMap = appRuleEngineMap;
    }

    public IRuntimeInstanceDao getRuntimeInstanceDao()
    {
        return runtimeInstanceDao;
    }

    public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao)
    {
        this.runtimeInstanceDao = runtimeInstanceDao;
    }

    public void proceedRule(LogEvent event, RuleType ruleType) {
		// Get the runId of the event
		String runId = event.getRunId();

		String appId = null;

		// Get the application id
		try {
			appId = runtimeInstanceDao.getAppIdByRunId(runId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Validate the appId
		if (appId == null) {
			System.out.println("Invalid appId");
			return;
		}

		switch (ruleType) {
		case GROOVY:
			proceedGroovyRule(event, appId);
			break;
		case DROOLS:
			proceedDroolsRule(event, appId);
			break;

		}

	}

	private synchronized IRuleEngine loadRuleEngine(String appId) {
		
		IRuleEngine appRuleEngine = appRuleEngineMap.get(appId);
		if(appRuleEngine != null)
		{
			return appRuleEngine;
		}
		IRuleEngine ruleEngine = appRuleEngineLoader.loadRuleEngine(appId);
		
		appRuleEngineMap.put(appId, ruleEngine);
		return ruleEngine;
		
	}

	private void proceedGroovyRule(LogEvent event, String appId) {

		try {
			IRuleEngine ruleEngine = loadRuleEngine(appId);
			ruleEngine.parseEvent(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	private void proceedDroolsRule(LogEvent event, String appId) {

	}

	public void ruleEngineTrigger(String appId) {
		synchronized(this)
		{
			IRuleEngine ruleEngine = appRuleEngineLoader.loadRuleEngine(appId);
			appRuleEngineMap.put(appId, ruleEngine);
			
		}
		
	}

	public IAppRuleEngineLoader getAppRuleEngineLoader() {
		return appRuleEngineLoader;
	}

	public void setAppRuleEngineLoader(IAppRuleEngineLoader appRuleEngineLoader) {
		this.appRuleEngineLoader = appRuleEngineLoader;
	}

	

	
}
