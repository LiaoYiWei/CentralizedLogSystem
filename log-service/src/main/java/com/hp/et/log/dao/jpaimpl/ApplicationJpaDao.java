package com.hp.et.log.dao.jpaimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hp.et.log.dao.IApplicationDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.entity.Application;

public class ApplicationJpaDao extends JpaDao<String, Application> implements IApplicationDao {

	
	
	/**
	 * find all the applications
	 * @return the collection for all application
	 */
	public List<Application> findAllApplication()
	{
		Query query = entityManager.createNamedQuery("Application.findAll");
		try
		{
			return (ArrayList<Application>)query.getResultList();
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
}
