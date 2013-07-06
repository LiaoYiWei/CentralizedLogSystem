package com.hp.et.log.domain.bean;

import java.io.Serializable;


public class HostInfo implements Serializable{
	
	private String hostId;

    private String hostName;

    private String ipAddress;

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String hostId)
    {
        this.hostId = hostId;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
    
    public String debugString()
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("hostId").append(hostId).append("\t");
        strBuf.append("hostName").append(hostName).append("\t");
        strBuf.append("ipAddress").append(ipAddress).append("\t");
        return strBuf.toString();
    }

}
