package com.hp.et.log.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.aspectj.util.LangUtil.ProcessController.Thrown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.cache.RuleCacheService;
import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvRule;
import com.hp.et.log.entity.AppEnvRuleSimple;
import com.hp.et.log.entity.Application;


@Path("/appEnvRule")
public class AppEnvRuleResource {

	private static Logger logger = LoggerFactory.getLogger(AppEnvRuleResource.class);
	
	private IAppEnvRuleDao appEnvRuleDao;
	
	private IAppEnvDao appEnvDao;
	
	private RuleCacheService ruleCacheService;
	
	private CacheService cacheService;
	
	public IAppEnvRuleDao getAppEnvRuleDao() {
		return appEnvRuleDao;
	}

	public void setAppEnvRuleDao(IAppEnvRuleDao appEnvRuleDao) {
		this.appEnvRuleDao = appEnvRuleDao;
	}
	
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

	public RuleCacheService getRuleCacheService() {
		return ruleCacheService;
	}

	public void setRuleCacheService(RuleCacheService ruleCacheService) {
		this.ruleCacheService = ruleCacheService;
	}

	@POST
    @Path("/addNewRule")
    @Consumes("application/java_serializable")
    @Produces("application/java_serializable") 
    @CacheEvict (value = {"appEnvRuleCache"}, allEntries=true)
    public String addNewRule(AppEnvRuleInfo ruleInfo, @Context HttpServletRequest request) throws Throwable
    {    	
		logger.info("ENTER addNewRule");
		if (ruleInfo!=null){
			List<AppEnvRule> rules = ruleCacheService.getRulesByEnvId(ruleInfo.getEnvId());
			if (rules != null){
				for (int i=0; i<rules.size(); i++){
					AppEnvRule rule = rules.get(i);
					if ((rule.getRuleName().trim()).equals(ruleInfo.getRuleName().trim())){
						logger.info("ERROR INFO : Rule name has been existed in this AppEnv!");
						return "ERROR INFO : Rule name has been existed in this AppEnv!";
					}
				}
			}
		}
		String info = null;
		try {
			AppEnvRuleSimple rule = convert2AppEnvRuleSimple(ruleInfo);
			info = ruleCacheService.addNewRule(ruleInfo.getEnvId(), rule);
		}catch(Throwable ex){
			logger.error(ex.toString());
			throw ex;
		}
		logger.info("EXIT addNewRule");
		return info;
    }
	
	@POST
    @Path("/findRulesByEnvId")
    @Produces("application/java_serializable")   
    @CacheEvict (value = {"appEnvRuleCache"}, allEntries=true)
    public List<AppEnvRuleInfo> findRulesByEnvId(String envId) throws Throwable
    {    	
		logger.info("ENTER findRulesByEnvId");
		List<AppEnvRuleInfo> infos = null;
		try {
			List<AppEnvRule> rules = ruleCacheService.getRulesByEnvId(envId);
			infos = simpleRulesConvert2RuleInfo(rules, envId);
		}catch(Throwable ex){
			logger.error(ex.toString());
			throw ex;
		}
		logger.info("EXIT findRulesByEnvId");
		return infos;
    }
	
	@POST
    @Path("/updateRuleByRuleInfo")
    @Produces("application/java_serializable")   
    @CacheEvict (value = {"appEnvRuleCache"}, allEntries=true)
    public String updateRuleByRuleInfo(AppEnvRuleInfo ruleInfo) throws Throwable
    {    	
		logger.info("ENTER updateRuleByRuleInfo");
		if (ruleInfo!=null){
			List<AppEnvRule> rules = ruleCacheService.getRulesByEnvId(ruleInfo.getEnvId()); 
			//cacheService.getAppEnvRuleByEnvId(ruleInfo.getEnvId());
			if (rules != null){
				for (int i=0; i<rules.size(); i++){
					AppEnvRule rule = rules.get(i);
					if (!(rule.getRuleId().equals(ruleInfo.getRuleId())) && (rule.getRuleName().trim()).equals(ruleInfo.getRuleName().trim())){
						logger.info("ERROR INFO : Rule name has been existed in this AppEnv!");
						return "ERROR INFO : Rule name has been existed in this AppEnv!";
					}
				}
			}
		}
		String ruleId = null;
		try {
			AppEnvRuleSimple rule = convert2AppEnvRuleSimple(ruleInfo);
			ruleId = ruleCacheService.updateRuleByEnvId(ruleInfo.getEnvId(), rule);
		}catch(Throwable ex){
			logger.error(ex.toString());
			throw ex;
		}
		logger.info("EXIT updateRuleByRuleInfo");
		return ruleId;
    }
	
	@POST
    @Path("/deleteRuleByRuleInfo")
    @Produces("application/java_serializable")
    @CacheEvict (value = {"appEnvRuleCache"}, allEntries=true)
    public void deleteRuleByRuleInfo(AppEnvRuleInfo ruleInfo) throws Throwable
    {    	
		logger.info("ENTER deleteRuleByRuleInfo");
		if (ruleInfo==null || ruleInfo.getEnvId()==null || ruleInfo.getRuleId()==null){
			logger.info("ruleInfo is not valid!");
			return;
		}
		try {
			ruleCacheService.deleteRuleByEnvId(ruleInfo.getEnvId(), ruleInfo.getRuleId());
		}catch(Throwable ex){
			logger.error(ex.toString());
			throw ex;
		}
		logger.info("EXIT deleteRuleByRuleInfo");
		return;
    }
	
//	@POST
//    @Path("/syncEnvRulesByEnvId")
//    @Produces("application/java_serializable")   
//    @CacheEvict (value = {"appEnvRuleCache"}, allEntries=true)
//    public void syncEnvRulesByEnvId(List<AppEnvRuleInfo> infos)
//    {    	
//		logger.info("ENTER syncEnvRules");
//		if (infos==null || infos.size()==0){
//			logger.info("There is no rule need to be synchronized!");
//			return;
//		}
//		String envId = infos.get(0).getEnvId();
//		if (envId==null){
//			logger.info("Please specify the env id!");
//			return;
//		}
//		//delete all rules by envId
//		List list = appEnvRuleDao.findRulesByEnvId(envId, AppEnvRuleInfo.SIMPLE.intValue());
//		for (Iterator its=list.iterator(); its.hasNext();){
//			AppEnvRuleSimple simple = (AppEnvRuleSimple) its.next();
//			appEnvRuleDao.remove(simple);
//		}
//		//insert all rules
//		for (Iterator it=infos.iterator(); it.hasNext();){
//			AppEnvRuleInfo info = (AppEnvRuleInfo) it.next();
////			AppEnvRuleSimple rule = convert2AppEnvRuleSimple(info);
////			appEnvRuleDao.persist(rule);
//		}
//		logger.info("EXIT syncEnvRules");
//		return;
//    }
	private List<AppEnvRuleInfo> simpleRulesConvert2RuleInfo(List ruleList, String envId){
		List<AppEnvRuleInfo> ruleInfos = new ArrayList<AppEnvRuleInfo>();
		AppEnv env = appEnvDao.findById(envId);
		if (env==null){
			logger.info("The AppEnv is null by envId = "+envId);
		}
		for (Iterator ite=ruleList.iterator();ite.hasNext();){
			AppEnvRuleSimple simple = (AppEnvRuleSimple) ite.next();
			AppEnvRuleInfo info = new AppEnvRuleInfo();
			info.setEnvId(envId);
//			if (env != null){
//				info.setEnvId(env.getEnvId());
//				info.setEnvName(env.getEnvName());
//				Application app = env.getApplication();
//				if (app!=null){
//					info.setAppName(app.getAppName());
//				}
//			}
			info.setCreateTime(simple.getCreateTime());
			info.setEmailPdl(simple.getEmailPdl());
			info.setMessage(simple.getMessage());
			info.setMessageType(simple.getMessageType());
			info.setMsgStatus(simple.getMsgStatus());
			info.setRuleId(simple.getRuleId());
			info.setRuleName(simple.getRuleName());
			info.setSeverity(simple.getSeverity());
			info.setSuppressionTime(simple.getSuppressionTime());
			info.setThrowableMessage(simple.getThrowableMessage());
			info.setThrowMsgStatus(simple.getThrowMsgStatus());
			info.setUpdateTime(simple.getUpdateTime());
			ruleInfos.add(info);
		}
		return ruleInfos;
	}
	
	private AppEnvRuleSimple convert2AppEnvRuleSimple(AppEnvRuleInfo info){
		logger.info("ENTER convert2AppEnvRuleSample");
		AppEnvRuleSimple envRule = new AppEnvRuleSimple();
		//check the message status if the message is not null 
		if (info.getMessage()!=null && info.getMessage()!=""){
			if (info.getMsgStatus()==null || info.getMsgStatus()<AppEnvRuleInfo.INCLUDE || info.getMsgStatus()>AppEnvRuleInfo.EXCLUDE){
				logger.info("Please choose the message status with include or exclude! ruleId = "+info.getRuleId());
			}
			envRule.setMsgStatus(info.getMsgStatus());
		}
		//check the throwable status if the throwable message is not null
		if (info.getThrowableMessage()!=null && info.getThrowableMessage()!=""){
			if (info.getThrowMsgStatus()==null || info.getThrowMsgStatus()<AppEnvRuleInfo.INCLUDE || info.getThrowMsgStatus()>AppEnvRuleInfo.EXCLUDE){
				logger.info("Please choose the error message status with include or exclude! ruleId = " + info.getRuleId());
			}
			envRule.setThrowMsgStatus(info.getThrowMsgStatus());
		}
		AppEnv appEnv = this.appEnvDao.getReferenceById(info.getEnvId());
		envRule.setAppEnv(appEnv);
		if(info.getRuleId()!=null && !"".equals(info.getRuleId())){
		    envRule.setRuleId(info.getRuleId());
		}
		envRule.setSeverity(info.getSeverity());
		envRule.setEmailPdl(info.getEmailPdl());
		envRule.setRuleName(info.getRuleName());
		envRule.setSuppressionTime(info.getSuppressionTime());
		envRule.setMessage(info.getMessage());
		envRule.setMessageType(info.getMessageType());
		envRule.setThrowableMessage(info.getThrowableMessage());
		if (envRule.getCreateTime()==null){
			envRule.setCreateTime(new Date());
		}
		envRule.setUpdateTime(new Date());
		logger.info("EXIT convert2AppEnvRuleSample");
		return envRule;
	}
}
