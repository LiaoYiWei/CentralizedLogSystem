package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MonitorRegisterInfo implements Serializable{
	private List<String> nodeIds;
	private Map<String,List<Integer>> filters;

	public List<String> getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(List<String> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public Map<String, List<Integer>> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, List<Integer>> filters) {
		this.filters = filters;
	}
}
