/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.domain.bean;

/**
 * Description goes here.
 */
public class AppClientBasisInfo {
    private String appName;
    private String envName;
    private String hostIp;
    private String hostName;
    private String nodeName;
    
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getEnvName() {
        return envName;
    }
    public void setEnvName(String envName) {
        this.envName = envName;
    }
    public String getHostIp() {
        return hostIp;
    }
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }
    public String getHostName() {
	return hostName;
    }
    public void setHostName(String hostName) {
	this.hostName = hostName;
    }
    public String getNodeName() {
	return nodeName;
    }
    public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
    }
    
}
