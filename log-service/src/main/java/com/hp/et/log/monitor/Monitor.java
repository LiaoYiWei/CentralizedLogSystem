package com.hp.et.log.monitor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.filter.FilterEnum;
import com.hp.et.log.filter.IFilter;


public class Monitor {
	private final int MAX_QUEUE_SIZE = 3000;
	private long startTime;
	private long latestRequestTime;
	private long monitorId;
	private List<String> nodeIds;
	private Queue<LogEvent> logEvents = new LinkedList<LogEvent>();;
	private List<IFilter<LogEvent,List<Integer>>> filters = new ArrayList<IFilter<LogEvent,List<Integer>>>();
	private Lock lock = new ReentrantLock();

	public Monitor() {

	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getLatestRequestTime() {
		return latestRequestTime;
	}

	public void setLatestRequestTime(long latestRequestTime) {
		this.latestRequestTime = latestRequestTime;
	}

	public long getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(long monitorId) {
		this.monitorId = monitorId;
	}

	public List<IFilter<LogEvent,List<Integer>>> getFilters() {
		return filters;
	}

	public List<String> getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setFilters(Map<String,List<Integer>> filters) {
		if(!this.filters.isEmpty()){
			this.filters.clear();
		}
		for(Entry<String,List<Integer>> filter : filters.entrySet()){
			String className = FilterEnum.getClassName(filter.getKey());
			try {
				IFilter<LogEvent,List<Integer>> iFilter = (IFilter<LogEvent,List<Integer>>)Class.forName(className).newInstance();
				iFilter.putValues(filter.getValue());
				this.filters.add(iFilter);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private LogEvent filterLogEvent(LogEvent logEventRecord) {
		if (null != logEventRecord) {
			for (IFilter<LogEvent,List<Integer>> filter : filters) {
				if (null == filter.doFilter(logEventRecord)) {
					return null;
				}
			}
			return logEventRecord;
		}
		return null;
	}

	public void putLogEvent2Queue(LogEvent logEvent) {
		logEvent = filterLogEvent(logEvent);
		if (logEvent != null) {
			lock.lock();
			if(logEvents.size() > MAX_QUEUE_SIZE){
				logEvents.poll();
			}
			logEvents.add(logEvent);
			lock.unlock();
		}
	}

	public List<LogEvent> getLogEventsFromQueue() {
		List<LogEvent> logEventsTemp = new ArrayList<LogEvent>();
		LogEvent logEvent = null;
		lock.lock();
		while (null != (logEvent = logEvents.poll())) {
			logEventsTemp.add(logEvent);
		}
		lock.unlock();
		return logEventsTemp;
	}
}
