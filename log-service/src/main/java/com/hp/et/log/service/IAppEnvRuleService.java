package com.hp.et.log.service;

import java.util.List;

import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.AppEnvRule;

public interface IAppEnvRuleService {

	public abstract String addNewRule(AppEnvRuleInfo ruleInfo);

	public abstract List<AppEnvRule> findRulesByEnvId(String envId);

	public abstract String updateRuleByRuleInfo(AppEnvRuleInfo ruleInfo);

	public abstract void deleteRuleByRuleInfo(AppEnvRuleInfo ruleInfo);

}