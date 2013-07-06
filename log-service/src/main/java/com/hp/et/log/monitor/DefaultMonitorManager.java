package com.hp.et.log.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hp.et.log.cache.CacheService;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.entity.RuntimeInstance;

/**
 * @author liaoyiw
 * 
 */
public class DefaultMonitorManager implements IMonitorManager {

	private volatile long idGenertator = 0L;
	private static IMonitorManager monitorManager = new DefaultMonitorManager();
	private final Queue<LogEvent> logBuffer = new ConcurrentLinkedQueue<LogEvent>();
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private final Map<Long, Monitor> monitors = new ConcurrentHashMap<Long, Monitor>();
	private final Map<String, List<Monitor>> logMonitorMapper = new ConcurrentHashMap<String, List<Monitor>>();

	// private DispatchLogTask dispatchLogTask = new DispatchLogTask();
	private MonitorTimeoutTask monitorTimeoutTask = new MonitorTimeoutTask();

	//private IRuntimeInstanceDao runtimeInstanceDao = null;
	
	private CacheService cacheService;
	
	private int timeout = 10000;

	private DefaultMonitorManager() {

	}
	
	

//	public IRuntimeInstanceDao getRuntimeInstanceDao() {
//		return runtimeInstanceDao;
//	}
//
//	public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao) {
//		this.runtimeInstanceDao = runtimeInstanceDao;
//	}

	



    /**
	 * @return
	 */
	public static IMonitorManager getInstance() {
		return monitorManager;
	}

	public CacheService getCacheService()
    {
        return cacheService;
    }



    public void setCacheService(CacheService cacheService)
    {
        this.cacheService = cacheService;
    }



    /*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#startup()
	 */
	public synchronized void startup() {
		// dispatchLogTask.start();
		monitorTimeoutTask.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#shutdown()
	 */
	public synchronized void shutdown() {
		// if (dispatchLogTask.isAlive()) {
		// dispatchLogTask.setbRun(false);
		// }
		if (monitorTimeoutTask.isAlive()) {
			monitorTimeoutTask.setbRun(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#startMonitoring(int, java.util.List)
	 */
	public synchronized Long startMonitoring(List<String> nodeIds,
			Map<String, List<Integer>> filters) {
		if (nodeIds == null || nodeIds.isEmpty()) {
			return -1L;
		}
		// create and register a monitor
		Monitor monitor = new Monitor();
		monitor.setFilters(filters);
		monitor.setMonitorId(this.generateMonitorId());
		monitor.setNodeIds(nodeIds);
		monitor.setStartTime(System.currentTimeMillis());
		monitor.setLatestRequestTime(System.currentTimeMillis());
		this.monitors.put(monitor.getMonitorId(), monitor);
		for (String nodeId : nodeIds) {
			// associate a instance id to monitors
			List<Monitor> monitors = logMonitorMapper.get(nodeId);
			if (null == monitors) {
				monitors = Collections
						.synchronizedList(new ArrayList<Monitor>());
				monitors.add(monitor);
				logMonitorMapper.put(nodeId, monitors);
			} else {
				if (!monitors.contains(monitor)) {
					monitors.add(monitor);
				}
			}
		}
		return monitor.getMonitorId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#stopMonitoring(java.lang.Long)
	 */
	public synchronized void stopMonitoring(Long monitorId) {
		Monitor monitor = monitors.remove(monitorId);
		if (null != monitor) {
			List<String> nodeIds = monitor.getNodeIds();
			for (String nodeId : nodeIds) {
				List<Monitor> monitors = logMonitorMapper.get(nodeId);
				if (null != monitors && !monitors.isEmpty()) {
					if (monitors.contains(monitor)) {
						monitors.remove(monitor);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#getLogEventsFromMonitorQueue(long)
	 */
	public List<LogEvent> getLogEventsFromMonitorQueue(long monitorId) {
		Monitor monitor = this.monitors.get(monitorId);
		if(monitor == null){
			return null;
		}
		monitor.setLatestRequestTime(System.currentTimeMillis());
		return monitor.getLogEventsFromQueue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.et.log.monitor.IMonitorManager#putLogEvent2BufferQueue(com.hp.et.log.persistence.bean.LogEventRecord)
	 */
	public void putLogEvent2MonitorQueue(LogEvent logEvent) {
		if (null != logEvent) {
			RuntimeInstance runtimeInstance = cacheService.getRuntimeInstance(logEvent.getRunId());
			if (null != runtimeInstance) {
				logEvent.setAppName(runtimeInstance.getAppEnvNode().getAppEnv().getApplication().getAppName());
				logEvent.setNodeName(runtimeInstance.getAppEnvNode().getNodeName());
				logEvent.setEnv(runtimeInstance.getAppEnvNode().getAppEnv().getEnvName());
				String nodeId = runtimeInstance.getAppEnvNode().getNodeId();
				List<Monitor> monitors = logMonitorMapper.get(nodeId);
				if (null != monitors && !monitors.isEmpty()) {
					for (Monitor monitor : monitors) {
						monitor.putLogEvent2Queue(logEvent);
					}
				}
			}
		}
	}

	private synchronized long generateMonitorId() {
		return idGenertator++;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	// private class DispatchLogTask extends Thread {
	//
	// private boolean bRun = true;
	//
	// public void setbRun(boolean bRun) {
	// this.bRun = bRun;
	// }
	//
	// public void run() {
	// while (bRun) {
	// LogEvent logEventRecord = logBuffer.poll();
	// if (null != logEventRecord) {
	// List<Monitor> monitors = logMonitorMapper
	// .get(logEventRecord.getRunId());
	// if (null != monitors) {
	// for (Monitor monitor : monitors) {
	// monitor.putLogEvent2Queue(logEventRecord);
	// }
	// }
	// } else {
	// try {
	// this.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// }

	private class MonitorTimeoutTask extends Thread {

		private boolean bRun = true;

		public void setbRun(boolean bRun) {
			this.bRun = bRun;
		}

		public void run() {
			while (bRun) {
				Set<Entry<Long, Monitor>> entrys = monitors.entrySet();
				if (null != entrys & !entrys.isEmpty()) {
					for (Entry<Long, Monitor> entry : entrys) {
						Monitor monitor = entry.getValue();
						if (System.currentTimeMillis()
								- monitor.getLatestRequestTime() > timeout) {
							stopMonitoring(monitor.getMonitorId());
						}
					}
				}
				try {
					this.sleep(timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
