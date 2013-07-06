/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.domain.bean;

import java.io.Serializable;

public class ApplicationInstance implements Serializable
{
	private long appStartupTimestamp;
	
    private String appName;     

	private String env; 
    
    private String hostName;
    
    private String nodeName;
    
    private String runId; 
    
    public String getRunId()
    {
        return runId;
    }

    public void setRunId(String runId)
    {
        this.runId = runId;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getEnv()
    {
        return env;
    }

    public void setEnv(String env)
    {
        this.env = env;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }
    
    public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
	
	public long getAppStartupTimestamp() {
		return appStartupTimestamp;
	}

	public void setAppStartupTimestamp(long appStartupTimestamp) {
		this.appStartupTimestamp = appStartupTimestamp;
	}

	/**
	 * Note, we don't want to override toString. Maybe toString will be used for other purpose to marshal/unmarshal JAXB
	 * Description goes here.
	 *
	 * @return
	 */
	public String debugString()
	{
	    
	    StringBuffer strBuf = new StringBuffer(); 
	    strBuf.append("appName:").append(appName).append("\t");
	    strBuf.append("env:").append(env).append("\t");
	    strBuf.append("hostName:").append(hostName).append("\t");
	    strBuf.append("nodeName:").append(nodeName).append("\t");
	    strBuf.append("runId:").append(runId);
	    return strBuf.toString();
	}

}
