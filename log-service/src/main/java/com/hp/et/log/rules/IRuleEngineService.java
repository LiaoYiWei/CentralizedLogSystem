package com.hp.et.log.rules;

import com.hp.et.log.domain.bean.LogEvent;

import com.hp.et.log.rules.RuleEngineService.RuleType;

public interface IRuleEngineService extends IRuleEngineAware{
	public void proceedRule(LogEvent event, RuleType ruleType);
}
