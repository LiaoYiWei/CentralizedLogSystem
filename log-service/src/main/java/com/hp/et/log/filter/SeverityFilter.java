package com.hp.et.log.filter;

import java.util.ArrayList;
import java.util.List;

import com.hp.et.log.domain.bean.LogEvent;


public class SeverityFilter implements IFilter<LogEvent,List<Integer>> {

	public List<Integer> serverities = new ArrayList<Integer>();

	public LogEvent doFilter(LogEvent logEventRecord) {
		if (null != logEventRecord) {
			if (serverities.contains(logEventRecord.getSeverity())) {
				return logEventRecord;
			}
		}
		return null;
	}

	public void putValues(List<Integer> values) {
		serverities.addAll(values);
	}

}
