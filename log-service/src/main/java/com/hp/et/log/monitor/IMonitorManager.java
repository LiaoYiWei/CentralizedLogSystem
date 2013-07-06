package com.hp.et.log.monitor;

import java.util.List;
import java.util.Map;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.filter.IFilter;


public interface IMonitorManager {

	/**
	 * @param monitorId
	 * @return 
	 */
	public List<LogEvent> getLogEventsFromMonitorQueue(
			long monitorId);

	/**
	 * @param logEvent
	 */
	public void putLogEvent2MonitorQueue(LogEvent logEvent);
	
	/**
	 * start the monitoring service
	 */
	public void startup();

	/**
	 * stop the monitoring service
	 */
	public void shutdown();

	/**
	 * create a monitor to monitor list
	 * 
	 * @param instanceId
	 * @param map
	 * @return monitor's id
	 */
	public Long startMonitoring(List<String> instanceId,
			Map<String, List<Integer>> map);

	/**
	 * remove a monitor from monitor list through monitor id
	 * 
	 * @param monitorId
	 */
	public void stopMonitoring(Long monitorId);

	

}