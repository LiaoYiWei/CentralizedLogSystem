package com.hp.et.log.domain.bean;

import java.io.Serializable;

public class NodeInfo implements Serializable{
	private String id;
	private String name;
	//private String hostId;
	private HostInfo host;
	private int acceptSeverity; 
	
	private RuntimeInstanceInfo latestRuntimeInstance;
	
	
	
	
	public RuntimeInstanceInfo getLatestRuntimeInstance() {
		return latestRuntimeInstance;
	}

	public void setLatestRuntimeInstance(RuntimeInstanceInfo latestRuntimeInstance) {
		this.latestRuntimeInstance = latestRuntimeInstance;
	}

	public HostInfo getHost() {
		return host;
	}

	public void setHost(HostInfo host) {
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getAcceptSeverity()
    {
        return acceptSeverity;
    }

    public void setAcceptSeverity(int acceptSeverity)
    {
        this.acceptSeverity = acceptSeverity;
    }

    public String debugString()
	{
	    StringBuffer strBuf = new StringBuffer();
	    strBuf.append("nodeId:").append(id).append("\t");
	    strBuf.append("name:").append(name).append("\t");
	   // strBuf.append("hostId:").append(hostId).append("\t");
	    if(host != null)
	    {
	        strBuf.append(host.debugString());
	    }
	    
	    return strBuf.toString();
	}
}
