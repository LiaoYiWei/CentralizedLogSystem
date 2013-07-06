package com.hp.et.log.dao.jpaimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hp.et.log.dao.IHostDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.entity.Host;

public class HostJpaDao extends JpaDao<String, Host> implements IHostDao{

	
	
	/**
	 * find all the hosts
	 * @return the collection for all host
	 */
	public List<Host> findAllHost()
	{
		Query query = entityManager.createNamedQuery("Host.findAll");
		try
		{
			return (List<Host>)query.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	/**
	 * get host information by ip address
	 * @param ipAddress
	 * @return host object
	 */
	public Host getHostByIpAddress(String ipAddress)
	{
		Query query = entityManager.createNamedQuery("Host.findByIpAddress");
		query.setParameter("ipAddress", ipAddress);
		try
		{
			ArrayList<Host> list =  (ArrayList<Host>) query.getResultList();
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
