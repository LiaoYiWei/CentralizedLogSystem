package com.hp.et.log.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvRule;
import com.hp.et.log.entity.AppEnvRuleSimple;
import com.hp.et.log.service.IAppEnvRuleService;

public class RuleCacheService {

	private static Logger logger = LoggerFactory.getLogger(RuleCacheService.class);
	private Map<String, Map<String, AppEnvRule>> envRuleMap = new HashMap();
	
//	private static final RuleCacheService ruleCacheService = new RuleCacheService();
	
	private IAppEnvDao appEnvDao;
	
	private IAppEnvRuleDao appEnvRuleDao;
	
	private CacheService cacheService;
	
	public IAppEnvDao getAppEnvDao() {
		return appEnvDao;
	}


	public void setAppEnvDao(IAppEnvDao appEnvDao) {
		this.appEnvDao = appEnvDao;
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
	 * return the instance of RuleCacheService
	 * @return
	 */
//	public static RuleCacheService getCacheService(){
//		return ruleCacheService;
//	}
	
	/**
	 * return true if the specified envId has rules in map, otherwise return false
	 * @param envId
	 * @return
	 */
	public synchronized boolean hasRulesByEnvId(String envId){
		if (envId!=null && envRuleMap!=null && !envRuleMap.isEmpty()){
			if (envRuleMap.get(envId)!=null && !envRuleMap.get(envId).isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * return the rules list by specified envId from map
	 * @param envId
	 * @return
	 */
	public synchronized List<AppEnvRule> getRulesByEnvId(String envId) {
		List<AppEnvRule> ruleList = new ArrayList();
		if (!envRuleMap.containsKey(envId)) {
			ruleList = appEnvRuleDao.findRulesByEnvId(envId,
					AppEnvRuleInfo.SIMPLE.intValue());
			putRulesByEnvId(envId, ruleList);
		} else {

			Iterator itr = envRuleMap.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Map<String, AppEnvRule>> entry = (Entry<String, Map<String, AppEnvRule>>) itr
						.next();
				if (entry.getKey().equals(envId)) {
					Map<String, AppEnvRule> map = entry.getValue();
					Iterator it = map.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, AppEnvRule> ent = (Entry<String, AppEnvRule>) it
								.next();
						ruleList.add(ent.getValue());
					}
				}
			}

		}
		return ruleList;
	}
	
	/**
	 * update the specified rule by envId in map
	 * @param envId
	 * @param rule
	 */
	public synchronized String updateRuleByEnvId(String envId, AppEnvRule rule){
		if (rule==null){
			logger.info("rule is null!");
			return null;
		}
		this.appEnvRuleDao.merge(rule);
		appEnvRuleDao.flush();
		//update the rule cache
		
		Map<String, AppEnvRule> rulesMap = envRuleMap.get(envId);
		String ruleId = rule.getRuleId();
		if(rulesMap != null)
		{
			rulesMap.put(ruleId, rule);
		}
	
		
		return ruleId;
	}
	
	/**
	 * put the rules by specified envId to map
	 * @param envId
	 * @param rules
	 */
	private synchronized void putRulesByEnvId(String envId, List<AppEnvRule> rules){
		Map<String, AppEnvRule> mapRule = new HashMap<String, AppEnvRule>();
		for (Iterator it=rules.iterator(); it.hasNext();){
			AppEnvRule ruleInfo = (AppEnvRule) it.next();
			mapRule.put(ruleInfo.getRuleId(), ruleInfo);
		}
		envRuleMap.put(envId, mapRule);
	}
	
	public synchronized String addNewRule(String envId, AppEnvRule rule){
		logger.info("ENTER addNewRule");
		if (rule == null){
			logger.info("rule is null!");
			return null;
		}
		logger.info("Begin to persist rule");
		this.appEnvRuleDao.persist(rule);
		appEnvRuleDao.flush();
		logger.info("End to persist rule");		
		String ruleId = rule.getRuleId();
		Map<String, AppEnvRule> rulesMap = envRuleMap.get(envId);
		if(rulesMap != null)
		{
			rulesMap.put(ruleId, rule);
		}

		return ruleId;
	}
	
	public synchronized void deleteRuleByEnvId(String envId, String ruleId){
		AppEnvRuleSimple simple = (AppEnvRuleSimple) this.appEnvRuleDao.findById(ruleId);
		this.appEnvRuleDao.remove(simple);
		appEnvRuleDao.flush();
		Map<String, AppEnvRule> rulesMap = envRuleMap.get(envId);
		if(rulesMap != null)
		{
			rulesMap.remove(ruleId);
		}
	}
	
	public synchronized void clearCache()
	{
		envRuleMap = new HashMap();
	}
	
}
