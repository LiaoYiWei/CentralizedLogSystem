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
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RUNTIME_INSTANCE")
@NamedQueries
({
    @NamedQuery(name = "RuntimeInstance.findAll", query = "SELECT a FROM RuntimeInstance a"),
    @NamedQuery(name = "RuntimeInstance.findByRunId", query = "SELECT a FROM RuntimeInstance a WHERE a.runId = :runId"),
    @NamedQuery(name = "RuntimeInstance.findByStartTime", query = "SELECT a FROM RuntimeInstance a WHERE a.startTime = :startTime")
})
public class RuntimeInstance implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "RUN_ID")
    private String runId;
    
    @Basic(optional = false)
    @Column(name = "START_TIME")
    private Date startTime;
    
    @Basic(optional = false)
    @Column(name = "CLIENT_START_TIME")
    private Date clientStartTime;

    @ManyToOne(targetEntity=AppEnvNode.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="NODE_ID")
    private AppEnvNode appEnvNode;
    
    @OneToMany(targetEntity=Event.class, cascade = CascadeType.ALL, mappedBy = "runtimeInstance", fetch = FetchType.LAZY)
    private Collection<Event> eventCollection;

    public RuntimeInstance()
    {
        super();
    }

    public RuntimeInstance(String runId, Timestamp startTime)
    {
        this.runId = runId;
        this.startTime = startTime;
    }


	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	

	public Date getClientStartTime() {
		return clientStartTime;
	}

	public void setClientStartTime(Date clientStartTime) {
		this.clientStartTime = clientStartTime;
	}

	public AppEnvNode getAppEnvNode() {
		return appEnvNode;
	}

	public void setAppEnvNode(AppEnvNode appEnvNode) {
		this.appEnvNode = appEnvNode;
	}

	public Collection<Event> getEventCollection() {
		return eventCollection;
	}

	public void setEventCollection(Collection<Event> eventCollection) {
		this.eventCollection = eventCollection;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (runId != null ? runId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuntimeInstance)) {
            return false;
        }
        RuntimeInstance other = (RuntimeInstance) object;
        if ((this.runId == null && other.runId != null) || (this.runId != null && !this.runId.equals(other.runId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.RuntimeInstance[runId=" + runId + "]";
	}
	
}
