package com.hp.et.log.dao;

import java.util.List;

import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.AppEnvRule;

public interface IAppEnvRuleDao extends IDao<String, AppEnvRule> {

	public List findRulesByEnvId(String envId, int type);
}
