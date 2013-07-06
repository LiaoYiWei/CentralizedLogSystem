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
@Table(name = "APP_ENV_NODE")
@NamedQueries
({
    @NamedQuery(name = "AppEnvNode.findAll", query = "SELECT a FROM AppEnvNode a"),
    @NamedQuery(name = "AppEnvNode.findByNodeId", query = "SELECT a FROM AppEnvNode a WHERE a.nodeId = :nodeId"),
    @NamedQuery(name = "AppEnvNode.findByNodeName", query = "SELECT a FROM AppEnvNode a WHERE a.nodeName = :nodeName"),
    @NamedQuery(name = "AppEnvNode.findByStatus", query = "SELECT a FROM AppEnvNode a WHERE a.status = :status")
})
public class AppEnvNode implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "NODE_ID")
    private String nodeId;
    
    @Basic(optional = false)
    @Column(name = "NODE_NAME", length = 255)
    private String nodeName;
    
    @Basic(optional = false)
    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne(targetEntity=AppEnv.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="ENV_ID")
    private AppEnv appEnv;
    
    @ManyToOne(targetEntity=Host.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="Host_ID")
    private Host host;

    @OneToMany(targetEntity=RuntimeInstance.class, cascade = CascadeType.ALL, mappedBy = "appEnvNode", fetch = FetchType.LAZY)
    private Collection<RuntimeInstance> runtimeInstanceCollection;
    
    
    @Transient
    private RuntimeInstance latestRuntimeInstance; 
    
    public AppEnvNode()
    {
        super();
    }

    public AppEnvNode(String nodeId, String nodeName)
    {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public AppEnv getAppEnv() {
		return appEnv;
	}

	public void setAppEnv(AppEnv appEnv) {
		this.appEnv = appEnv;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public Collection<RuntimeInstance> getRuntimeInstanceCollection() {
		return runtimeInstanceCollection;
	}

	public void setRuntimeInstanceCollection(
			Collection<RuntimeInstance> runtimeInstanceCollection) {
		this.runtimeInstanceCollection = runtimeInstanceCollection;
	}
	
	

	public RuntimeInstance getLatestRuntimeInstance() {
		return latestRuntimeInstance;
	}

	public void setLatestRuntimeInstance(RuntimeInstance latestRuntimeInstance) {
		this.latestRuntimeInstance = latestRuntimeInstance;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (nodeId != null ? nodeId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppEnvNode)) {
            return false;
        }
        AppEnvNode other = (AppEnvNode) object;
        if ((this.nodeId == null && other.nodeId != null) || (this.nodeId != null && !this.nodeId.equals(other.nodeId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.AppEnvNode[nodeId=" + nodeId + "]";
	}
	
}
