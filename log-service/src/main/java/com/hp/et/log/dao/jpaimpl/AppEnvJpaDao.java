package com.hp.et.log.dao.jpaimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.Application;

public class AppEnvJpaDao extends JpaDao<String, AppEnv> implements IAppEnvDao {

	
	/**
	 * find environment by appId and envName
	 * @return AppEnv object
	 */
	public AppEnv getAppEnvByAppIdAndEnvName(String appId, String envName)
	{
		String sql = "SELECT a FROM AppEnv a, Application ap WHERE a.application=ap AND a.application.appId=:appId AND a.envName=:envName";
		Query query = entityManager.createQuery(sql);
		query.setParameter("appId", appId);
		query.setParameter("envName", envName);
		try
		{
			ArrayList<AppEnv> list = (ArrayList<AppEnv>)query.getResultList();
			if(list!=null && list.size()>0)
			{
				return list.get(0);
			}
			return null;
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	/**
	 * find the application by application name
	 * @param appName
	 * @return the application
	 */
	public Application getApplicationByAppName(String appName)
	{
		Query query = entityManager.createNamedQuery("Application.findByAppName");
		query.setParameter("appName", appName);
		try
		{
			ArrayList<Application> list = (ArrayList<Application>)query.getResultList();
			if(list!=null && list.size()>0)
			{
				return list.get(0);
			}
			return null;
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

    public ArrayList<AppEnv> findAllAppEnvs()
    {       
            Query query = entityManager.createNamedQuery("AppEnv.findAll");
            
            return (ArrayList<AppEnv>)query.getResultList();
            
    }
}
