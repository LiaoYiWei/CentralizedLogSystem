package com.hp.et.log.dao.jpaimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.hp.et.log.dao.IAppEnvNodeDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.entity.AppEnvNode;

public class AppEnvNodeJpaDao extends JpaDao<String, AppEnvNode> implements IAppEnvNodeDao {
	
	/**
	 * find the AppEnvNode by hostId and envId and nodeName
	 * @param hostId
	 * @param envId
	 * @param nodeName
	 * @return
	 */
	public AppEnvNode findNodeByEnvIdAndNodeName(String envId, String nodeName)
	{
		String sql = "SELECT a FROM AppEnvNode a, AppEnv ae, Host h WHERE a.host=h AND a.appEnv=ae AND a.appEnv.envId=:envId AND a.nodeName=:nodeName";
		Query query = entityManager.createQuery(sql);
		query.setParameter("envId", envId);
		query.setParameter("nodeName", nodeName);
		try
		{
			ArrayList<AppEnvNode> list = (ArrayList<AppEnvNode>)query.getResultList();
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


	public List<AppEnvNode> getNodesByEnvId(String envId) {
		
		String qlString = "SELECT an FROM AppEnvNode as an, AppEnv as ae WHERE an.appEnv.envId=ae.envId AND ae.envId=:envId";
		
		Query query = entityManager.createQuery(qlString);
		query.setParameter("envId", envId);
		List<AppEnvNode> nodes = query.getResultList();
		System.out.println("node list==========="+ nodes.size());
		return nodes;
	}
}
