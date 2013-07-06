package com.hp.et.log.dao.jpaimpl;

import java.util.List;

import javax.persistence.Query;

import com.hp.et.log.dao.IAppEnvRuleDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.domain.bean.AppEnvRuleInfo;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.AppEnvRule;
import com.hp.et.log.entity.AppEnvRuleAdvance;
import com.hp.et.log.entity.AppEnvRuleSimple;

public class AppEnvRuleJpaDao extends JpaDao<String, AppEnvRule> implements IAppEnvRuleDao{


	public List findRulesByEnvId(String envId, int type) {
		StringBuffer qlString = new StringBuffer("SELECT ar.* FROM app_env_rule ar");
		if (envId!=null){
			qlString.append(" WHERE ar.env_id=:envId");
		}
		if (type == AppEnvRuleInfo.SIMPLE.intValue()){
			qlString.append(" AND ar.DISCRIMINATOR='AppEnvRuleSimple'");
		}else if (type == AppEnvRuleInfo.ADVANCE.intValue()){
			qlString.append(" AND ar.DISCRIMINATOR='AppEnvRuleAdvance'");
		}
		
		Query query = null;
		if (type == AppEnvRuleInfo.SIMPLE.intValue()){
			query = entityManager.createNativeQuery(qlString.toString(), AppEnvRuleSimple.class);
		}else if (type == AppEnvRuleInfo.ADVANCE.intValue()){
			query = entityManager.createNativeQuery(qlString.toString(), AppEnvRuleAdvance.class);
		}else{
			query = entityManager.createNativeQuery(qlString.toString());
		}
		if (envId!=null){
			query.setParameter("envId", envId);
		}
		List list = query.getResultList();
		return list;
	}
}
