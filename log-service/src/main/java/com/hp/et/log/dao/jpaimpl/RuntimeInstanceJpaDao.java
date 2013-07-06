package com.hp.et.log.dao.jpaimpl;

import java.util.List;
import javax.persistence.Query;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.RuntimeInstance;

public class RuntimeInstanceJpaDao extends JpaDao<String, RuntimeInstance> implements IRuntimeInstanceDao
{

    private static final Class<Query> RuntimeInstance = null;

    /**
     * get the application id by runId
     * @param runId
     * @return	the appId of application object
     */
    public String getAppIdByRunId(String runId)
    {
        String sql = "SELECT ri.appEnvNode.appEnv.application.appId FROM RuntimeInstance ri" + " WHERE ri.runId=:runId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("runId", runId);
        List<String> list = (List<String>)query.getResultList();
        if (list != null && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }
    
//    public List getHostAppEnvNodeByRunId(String runId){
//		String sqlString = "SELECT a.app_name,e.env_name,n.node_name,h.host_name,h.ip_address "+
//			"FROM app_env e, app_env_node n, application a, host h,runtime_instance r "+
//			"WHERE a.app_id=e.app_id AND n.env_id=e.env_id AND n.node_id=r.node_id AND n.host_id=h.host_id "+
//			 "AND r.run_id=:runId";
//		Query query = entityManager.createNativeQuery(sqlString);
//		query.setParameter("runId", runId);
//	        List<String> list = (List<String>)query.getResultList();
//		if(list != null && list.size() > 0){
//		    return list;
//		}
//		return null;
//    }

	public com.hp.et.log.entity.RuntimeInstance findLatestRuntimeInstanceByNodeID(
			String nodeID) {
		
		String sql = "SELECT ri  FROM RuntimeInstance ri WHERE ri.appEnvNode.nodeId=:nodeID order by ri.startTime desc";
        Query query = entityManager.createQuery(sql).setParameter("nodeID", nodeID).setMaxResults(1);
        List<RuntimeInstance> list = (List<RuntimeInstance>)query.getResultList();
        if (list != null && list.size() == 1)
        {
            return list.get(0);
        }
        
		return null;
	}

//    public com.hp.et.log.entity.RuntimeInstance findUlRuntimeInstanceByID(String runID)
//    {
//
//        String sql = "SELECT runtime FROM RuntimeInstance runtime join fetch runtime.appEnvNode env WHERE runtime.runId=:runId";
//        Query query = entityManager.createQuery(sql);
//        query.setParameter("runId", runID);
//        return (RuntimeInstance)query.getSingleResult();
//        
//    }

//	public String findEnvIdByRunId(String runId){
//		String sql = "SELECT ae.envId FROM RuntimeInstance as ri, AppEnvNode as an, AppEnv as ae" +
//				" WHERE ri.appEnvNode.nodeId=an.nodeId AND ri.appEnvNode.appEnv.envId=ae.envId and ri.runId=:runId";
//		Query query = entityManager.createQuery(sql);
//		query.setParameter("runId", runId);
//		List list = query.getResultList();
//	    if (list != null && list.size() > 0)
//	    {
//	    	return list.get(0).toString();
//	    }
//		return null;
//	}
}
