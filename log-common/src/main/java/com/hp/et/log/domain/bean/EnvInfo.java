package com.hp.et.log.domain.bean;

import java.io.Serializable;
import java.util.List;

public class EnvInfo implements Serializable{
	private String id;
	private String name;
	private List<NodeInfo> nodeInfos;

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NodeInfo> getNodeInfos() {
		return nodeInfos;
	}

	public void setNodeInfos(List<NodeInfo> nodeInfos) {
		this.nodeInfos = nodeInfos;
	}

}
