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
import java.util.Date;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EVENT")
@NamedQueries
({
    @NamedQuery(name = "Event.findAll", query = "SELECT a FROM Event a"),
    @NamedQuery(name = "Event.findByEventId", query = "SELECT a FROM Event a WHERE a.eventId = :eventId"),
    @NamedQuery(name = "Event.findBySeverity", query = "SELECT a FROM Event a WHERE a.severity = :severity"),
    @NamedQuery(name = "Event.findByMessage", query = "SELECT a FROM Event a WHERE a.message = :message"),
    @NamedQuery(name = "Event.findByCreateTime", query = "SELECT a FROM Event a WHERE a.createTime = :createTime"),
    @NamedQuery(name = "Event.findByThrowableMessage", query = "SELECT a FROM Event a WHERE a.severity = :severity"),
    @NamedQuery(name = "Event.findByMessageType", query = "SELECT a FROM Event a WHERE a.messageType = :messageType"),
    @NamedQuery(name = "Event.findByThreadName", query = "SELECT a FROM Event a WHERE a.threadName = :threadName"),
    @NamedQuery(name = "Event.findByLoggerName", query = "SELECT a FROM Event a WHERE a.loggerName = :loggerName"),
    @NamedQuery(name = "Event.findByLogSequence", query = "SELECT a FROM Event a WHERE a.logSequence = :logSequence"),
    @NamedQuery(name = "Event.findByAttribute1Name", query = "SELECT a FROM Event a WHERE a.attribute1Name = :attribute1Name"),
    @NamedQuery(name = "Event.findByAttribute2Name", query = "SELECT a FROM Event a WHERE a.attribute2Name = :attribute2Name"),
    @NamedQuery(name = "Event.findByAttribute3Name", query = "SELECT a FROM Event a WHERE a.attribute3Name = :attribute3Name"),
    @NamedQuery(name = "Event.findByAttribute4Name", query = "SELECT a FROM Event a WHERE a.attribute4Name = :attribute4Name"),
    @NamedQuery(name = "Event.findByAttribute5Name", query = "SELECT a FROM Event a WHERE a.attribute5Name = :attribute5Name"),
    @NamedQuery(name = "Event.findByAttribute1Value", query = "SELECT a FROM Event a WHERE a.attribute1Value = :attribute1Value"),
    @NamedQuery(name = "Event.findByAttribute2Value", query = "SELECT a FROM Event a WHERE a.attribute2Value = :attribute2Value"),
    @NamedQuery(name = "Event.findByAttribute3Value", query = "SELECT a FROM Event a WHERE a.attribute3Value = :attribute3Value"),
    @NamedQuery(name = "Event.findByAttribute4Value", query = "SELECT a FROM Event a WHERE a.attribute4Value = :attribute4Value"),
    @NamedQuery(name = "Event.findByAttribute5Value", query = "SELECT a FROM Event a WHERE a.attribute5Value = :attribute5Value")
})
public class Event implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "EVENT_ID")
    private String eventId;
    
    @Basic(optional = false)
    @Column(name = "SEVERITY")
    private Integer severity;
    
    @Basic(optional = true)
    @Column(name = "MESSAGE", length = 3000)
    private String message;
    
    @Basic(optional = false)
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    @Basic(optional = false)
    @Column(name = "LOG_SEQUENCE")
    private Long logSequence;
    
    @Basic(optional = true)
    @Column(name = "THROWABLE_MESSAGE", length = 3000)
    private String throwableMessage;
    
    @Basic(optional = false)
    @Column(name = "MESSAGE_TYPE", length = 20)
    private String messageType;
    
    @Basic(optional = false)
    @Column(name = "THREAD_NAME", length = 200)
    private String threadName;
    
    @Basic(optional = false)
    @Column(name = "LOGGER_NAME", length = 255)
    private String loggerName;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE1NAME", length = 255)
    private String attribute1Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE2NAME", length = 255)
    private String attribute2Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE3NAME", length = 255)
    private String attribute3Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE4NAME", length = 255)
    private String attribute4Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE5NAME", length = 255)
    private String attribute5Name;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE1VALUE", length = 255)
    private String attribute1Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE2VALUE", length = 255)
    private String attribute2Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE3VALUE", length = 255)
    private String attribute3Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE4VALUE", length = 255)
    private String attribute4Value;
    
    @Basic(optional = true)
    @Column(name = "ATTRIBUTE5VALUE", length = 255)
    private String attribute5Value;

    @ManyToOne(targetEntity=RuntimeInstance.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="RUN_ID")
    private RuntimeInstance runtimeInstance;

    public Event()
    {
        super();
    }

    public Event(String eventId)
    {
        this.eventId = eventId;
    }


	public String getEventId() {
		return eventId;
	}

 
	public Long getLogSequence() {
		return logSequence;
	}

	public void setLogSequence(Long logSequence) {
		this.logSequence = logSequence;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getThrowableMessage() {
		return throwableMessage;
	}

	public void setThrowableMessage(String throwableMessage) {
		this.throwableMessage = throwableMessage;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getAttribute1Name() {
		return attribute1Name;
	}

	public void setAttribute1Name(String attribute1Name) {
		this.attribute1Name = attribute1Name;
	}

	public String getAttribute2Name() {
		return attribute2Name;
	}

	public void setAttribute2Name(String attribute2Name) {
		this.attribute2Name = attribute2Name;
	}

	public String getAttribute3Name() {
		return attribute3Name;
	}

	public void setAttribute3Name(String attribute3Name) {
		this.attribute3Name = attribute3Name;
	}

	public String getAttribute4Name() {
		return attribute4Name;
	}

	public void setAttribute4Name(String attribute4Name) {
		this.attribute4Name = attribute4Name;
	}

	public String getAttribute5Name() {
		return attribute5Name;
	}

	public void setAttribute5Name(String attribute5Name) {
		this.attribute5Name = attribute5Name;
	}

	public String getAttribute1Value() {
		return attribute1Value;
	}

	public void setAttribute1Value(String attribute1Value) {
		this.attribute1Value = attribute1Value;
	}

	public String getAttribute2Value() {
		return attribute2Value;
	}

	public void setAttribute2Value(String attribute2Value) {
		this.attribute2Value = attribute2Value;
	}

	public String getAttribute3Value() {
		return attribute3Value;
	}

	public void setAttribute3Value(String attribute3Value) {
		this.attribute3Value = attribute3Value;
	}

	public String getAttribute4Value() {
		return attribute4Value;
	}

	public void setAttribute4Value(String attribute4Value) {
		this.attribute4Value = attribute4Value;
	}

	public String getAttribute5Value() {
		return attribute5Value;
	}

	public void setAttribute5Value(String attribute5Value) {
		this.attribute5Value = attribute5Value;
	}

	public RuntimeInstance getRuntimeInstance() {
		return runtimeInstance;
	}

	public void setRuntimeInstance(RuntimeInstance runtimeInstance) {
		this.runtimeInstance = runtimeInstance;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (eventId != null ? eventId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.Event[eventId=" + eventId + "]";
	}
	
}
