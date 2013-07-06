/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2001-2009   All rights reserved. ======================
 */


 //     WWW   WWW    AAAA    RRRRR    NNN   NN  IIII NNN   NN   GGGGG
 //     WWW   WWW   AAAAAA   RRR  RR  NNNN  NN   II  NNNN  NN  GGG GGG
 //     WWW   WWW  AAA  AAA  RRR  RR  NNNNN NN   II  NNNNN NN  GGG
 //     WWW W WWW  AAAAAAAA  RRRRR    NNN NNNN   II  NNN NNNN  GGG GGG
 //     WWW W WWW  AAA  AAA  RRR RR   NNN  NNN   II  NNN  NNN  GGG  GG
 //     WWWW WWWW  AAA  AAA  RRR  RR  NNN   NN   II  NNN   NN  GGG GGG
 //      WW   WW   AAA  AAA  RRR  RR  NNN   NN  IIII NNN   NN   GGGGG
 //
 //     This is a generated file.  It's not a good idea to make manual
 //     changes to it as they are likely to be overwritten!


package com.hp.et.log.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "HOST")
@NamedQueries
({
    @NamedQuery(name = "Host.findAll", query = "SELECT h FROM Host h"),
    @NamedQuery(name = "Host.findByHostId", query = "SELECT h FROM Host h WHERE h.hostId = :hostId"),
    @NamedQuery(name = "Host.findByHostName", query = "SELECT h FROM Host h WHERE h.hostName = :hostName"),
    @NamedQuery(name = "Host.findByIpAddress", query = "SELECT h FROM Host h WHERE h.ipAddress = :ipAddress")
})
public class Host implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "HOST_ID")
    private String hostId;
    
    @Basic(optional = false)
    @Column(name = "HOST_NAME", length = 100)
    private String hostName;
    
    @Basic(optional = false)
    @Column(name = "IP_ADDRESS", length = 40)
    private String ipAddress;
    
    @OneToMany(targetEntity=AppEnvNode.class, cascade = CascadeType.ALL, mappedBy = "host", fetch = FetchType.LAZY)
    private Collection<AppEnvNode> appEnvNodeCollection;

    public Host()
    {
        super();
    }

    public Host(String hostId, String hostName, String ipAddress)
    {
        this.hostId = hostId;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
    }

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Collection<AppEnvNode> getAppEnvNodeCollection() {
		return appEnvNodeCollection;
	}

	public void setAppEnvNodeCollection(Collection<AppEnvNode> appEnvNodeCollection) {
		this.appEnvNodeCollection = appEnvNodeCollection;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (hostId != null ? hostId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Host)) {
            return false;
        }
        Host other = (Host) object;
        if ((this.hostId == null && other.hostId != null) || (this.hostId != null && !this.hostId.equals(other.hostId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.Host[hostId=" + hostId + "]";
	}
	
}
