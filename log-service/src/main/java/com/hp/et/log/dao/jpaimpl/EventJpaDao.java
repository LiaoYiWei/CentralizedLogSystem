package com.hp.et.log.dao.jpaimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.hp.et.log.dao.IEventDao;
import com.hp.et.log.dao.JpaDao;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.Event;

public class EventJpaDao extends JpaDao<String, Event> implements IEventDao {
	
	public List findEventByQueryInfo(QueryInformation queryInfo)
	{
		Query qu = getQueryByQueryInfo(queryInfo, false);
		List listEvent = qu.getResultList();
		return listEvent;
	}
	
	public Long getEventCountByQueryInfo(QueryInformation queryInfo)
	{
		Query qu = getQueryByQueryInfo(queryInfo, true);
		List<Long> list= qu.getResultList();
		if (list != null && list.size()>0){
			return list.get(0);
		}
		return 0L;
	}
	
    private Query getQueryByQueryInfo(QueryInformation queryInfo, boolean count)
    {
        Date start = null;
        Date end = null;

        StringBuffer qlStrBuffer = new StringBuffer("SELECT");
        if (count)
        {
            qlStrBuffer.append( " COUNT(e.eventId)");
        }
        else
        {
            qlStrBuffer.append( " e , ri , ap , ae, h , an  ");
        }
        qlStrBuffer.append(" FROM Event as e, RuntimeInstance as ri, AppEnvNode as an, AppEnv as ae, Application as ap, Host as h" +
        		" WHERE e.runtimeInstance.runId=ri.runId AND e.runtimeInstance.appEnvNode.appEnv.application.appId=ap.appId" +
        		" AND e.runtimeInstance.appEnvNode.appEnv.envId=ae.envId AND e.runtimeInstance.appEnvNode.nodeId=an.nodeId" +
        		" AND an.host.hostId=h.hostId");

        if (queryInfo != null)
        {
            if (queryInfo.getRunId() != null && !("".equals(queryInfo.getRunId())))
            {
                qlStrBuffer.append( " AND ri.runId=:runId");
            }
            if (queryInfo.getNodes() != null && queryInfo.getNodes().size() > 0)
            {
                ArrayList<String> nodes = (ArrayList<String>)queryInfo.getNodes();
                qlStrBuffer.append( " AND ri.appEnvNode.nodeId in (");
                for (int i = 0; i < nodes.size(); i++)
                {
                	if (queryInfo.getNodes().get(i)!=null && !("".equals(queryInfo.getNodes().get(i))))
                	{
                		if (i > 0)
                		{
                			qlStrBuffer.append( ",");
                		}
                		qlStrBuffer.append( "'" + nodes.get(i) + "'");
                	}
                }
                qlStrBuffer.append( ")");
            }
            if (queryInfo.getTime() != null)
            {
                qlStrBuffer.append( " AND e.createTime > (sysdate-1/(24*60)*" + queryInfo.getTime() + ")");
            }
            else if (queryInfo.getSpecificTime() != null && !("".equals(queryInfo.getSpecificTime())))
            {
                try
                {
                    String startStr = queryInfo.getSpecificTime() + " 00:00:00";
                    String endStr = queryInfo.getSpecificTime() + " 23:59:59";
                    start = new SimpleDateFormat("yyyy-MM-dd").parse(startStr);
                    end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endStr);

                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                qlStrBuffer.append( " AND e.createTime>=:start AND e.createTime<=:end");
            }
            if (queryInfo.getLogLevel() != null
                    && queryInfo.getLogLevel() >= LogSeverityEnum.valueOf("TRACE").getIndex()
                    && queryInfo.getLogLevel() <= LogSeverityEnum.valueOf("FATAL").getIndex())
            {
                qlStrBuffer.append(" AND e.severity>=" + queryInfo.getLogLevel());
            }
            if (queryInfo.getAppName() != null && !("".equals(queryInfo.getAppName()))){
        		qlStrBuffer.append(" AND ap.appName=:appName");
        	}
        	if (queryInfo.getEnvName()!=null && !("".equals(queryInfo.getEnvName()))){
        		qlStrBuffer.append(" AND ae.envName=:envName");
        	}
        	if (queryInfo.getNodeName()!=null && !("".equals(queryInfo.getNodeName()))){
        		qlStrBuffer.append(" AND an.nodeName=:nodeName");
        	}
        	if (queryInfo.getThreadName()!=null && !("".equals(queryInfo.getThreadName()))){
        		qlStrBuffer.append(" AND e.threadName=:threadName");
        	}
        	if (queryInfo.getLoggerName()!=null && !("".equals(queryInfo.getLoggerName()))){
        		qlStrBuffer.append(" AND e.loggerName=:loggerName");
        	}
        	if (queryInfo.getMessage()!=null && !("".equals(queryInfo.getMessage()))){
        		qlStrBuffer.append(" AND e.message like :msg");
        	}
        	if (queryInfo.getMessageType()!=null && !("".equals(queryInfo.getMessageType()))){
        		qlStrBuffer.append(" AND e.messageType=:msgType");
        	}
        	if (queryInfo.getThrowableMessage()!=null && !("".equals(queryInfo.getThrowableMessage()))){
        		qlStrBuffer.append(" AND e.throwableMessage like :throwableMsg");
        	}
        	if (queryInfo.getAttribute1Name()!=null && !("".equals(queryInfo.getAttribute1Name()))
        			&& queryInfo.getAttribute1Value()!=null && !("".equals(queryInfo.getAttribute1Value()))){
        		qlStrBuffer.append(" AND e.attribute1Name=:attr1Name AND e.attribute1Value=:attr1Value");
        	}
        	if (queryInfo.getAttribute2Name()!=null && !("".equals(queryInfo.getAttribute2Name()))
        			&& queryInfo.getAttribute2Value()!=null && !("".equals(queryInfo.getAttribute2Value()))){
        		qlStrBuffer.append(" AND e.attribute2Name=:attr2Name AND e.attribute2Value=:attr2Value");
        	}
        	if (queryInfo.getAttribute3Name()!=null && !("".equals(queryInfo.getAttribute3Name()))
        			&& queryInfo.getAttribute3Value()!=null && !("".equals(queryInfo.getAttribute3Value()))){
        		qlStrBuffer.append(" AND e.attribute3Name=:attr3Name AND e.attribute3Value=:attr3Value");
        	}
        	if (queryInfo.getAttribute4Name()!=null && !("".equals(queryInfo.getAttribute4Name()))
        			&& queryInfo.getAttribute4Value()!=null && !("".equals(queryInfo.getAttribute4Value()))){
        		qlStrBuffer.append(" AND e.attribute4Name=:attr4Name AND e.attribute3Value=:attr4Value");
        	}
        	if (queryInfo.getAttribute5Name()!=null && !("".equals(queryInfo.getAttribute5Name()))
        			&& queryInfo.getAttribute5Value()!=null && !("".equals(queryInfo.getAttribute5Value()))){
        		qlStrBuffer.append(" AND e.attribute5Name=:attr5Name AND e.attribute4Value=:attr5Value");
        	}
        }

        qlStrBuffer.append(" ORDER BY e.createTime,e.logSequence");

        Query query = entityManager.createQuery(qlStrBuffer.toString());
        if (start != null && end != null)
        {
            query.setParameter("start", start);
            query.setParameter("end", end);
        }
        if (queryInfo.getRunId() != null && !("".equals(queryInfo.getRunId())))
        {
            query.setParameter("runId", queryInfo.getRunId());
        }
        if (queryInfo.getAppName() != null && !("".equals(queryInfo.getAppName()))){
    		query.setParameter("appName", queryInfo.getAppName());
    	}
        if (queryInfo.getEnvName()!=null && !("".equals(queryInfo.getEnvName()))){
    		query.setParameter("envName", queryInfo.getEnvName());
    	}
    	if (queryInfo.getNodeName()!=null && !("".equals(queryInfo.getNodeName()))){
    		query.setParameter("nodeName", queryInfo.getNodeName());
    	}
        if (queryInfo.getThreadName()!=null && !("".equals(queryInfo.getThreadName()))){
    		query.setParameter("threadName", queryInfo.getThreadName());
    	}
        if (queryInfo.getLoggerName()!=null && !("".equals(queryInfo.getLoggerName()))){
    		query.setParameter("loggerName", queryInfo.getLoggerName());
    	}
        if (queryInfo.getMessage()!=null && !("".equals(queryInfo.getMessage()))){
    		query.setParameter("msg", "%"+queryInfo.getMessage()+"%");
    	}
        if (queryInfo.getThrowableMessage()!=null && !("".equals(queryInfo.getThrowableMessage()))){
    		query.setParameter("throwableMsg", "%"+queryInfo.getThrowableMessage()+"%");
    	}
        if (queryInfo.getMessageType()!=null && !("".equals(queryInfo.getMessageType()))){
    		query.setParameter("msgType", queryInfo.getMessageType());
    	}
        if (queryInfo.getAttribute1Name()!=null && !("".equals(queryInfo.getAttribute1Name()))
    			&& queryInfo.getAttribute1Value()!=null && !("".equals(queryInfo.getAttribute1Value()))){
    		query.setParameter("attr1Name", queryInfo.getAttribute1Name());
    		query.setParameter("attr1Value", queryInfo.getAttribute1Value());
    	}
    	if (queryInfo.getAttribute2Name()!=null && !("".equals(queryInfo.getAttribute2Name()))
    			&& queryInfo.getAttribute2Value()!=null && !("".equals(queryInfo.getAttribute2Value()))){
    		query.setParameter("attr2Name", queryInfo.getAttribute2Name());
    		query.setParameter("attr2Value", queryInfo.getAttribute2Value());
    	}
    	if (queryInfo.getAttribute3Name()!=null && !("".equals(queryInfo.getAttribute3Name()))
    			&& queryInfo.getAttribute3Value()!=null && !("".equals(queryInfo.getAttribute3Value()))){
    		query.setParameter("attr3Name", queryInfo.getAttribute3Name());
    		query.setParameter("attr3Value", queryInfo.getAttribute3Value());
    	}
    	if (queryInfo.getAttribute4Name()!=null && !("".equals(queryInfo.getAttribute4Name()))
    			&& queryInfo.getAttribute4Value()!=null && !("".equals(queryInfo.getAttribute4Value()))){
    		query.setParameter("attr4Name", queryInfo.getAttribute4Name());
    		query.setParameter("attr4Value", queryInfo.getAttribute4Value());
    	}
    	if (queryInfo.getAttribute5Name()!=null && !("".equals(queryInfo.getAttribute5Name()))
    			&& queryInfo.getAttribute5Value()!=null && !("".equals(queryInfo.getAttribute5Value()))){
    		query.setParameter("attr5Name", queryInfo.getAttribute5Name());
    		query.setParameter("attr5Value", queryInfo.getAttribute5Value());
    	}
        if (count == false && queryInfo.getPageStart() >= 0 && queryInfo.getLimit() > 0)
        {
            query.setFirstResult(queryInfo.getPageStart());
            query.setMaxResults(queryInfo.getLimit());
        }
        return query;
    }
    
}
