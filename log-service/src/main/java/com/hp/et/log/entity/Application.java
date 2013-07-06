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
@Table(name = "APPLICATION")
@NamedQueries
({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByAppId", query = "SELECT a FROM Application a WHERE a.appId = :appId"),
    @NamedQuery(name = "Application.findByAppName", query = "SELECT a FROM Application a WHERE a.appName = :appName")
})
public class Application implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "APP_ID")
    private String appId;
    
    @Basic(optional = false)
    @Column(name = "APP_NAME", length = 255)
    private String appName;

    @OneToMany(targetEntity=AppEnv.class, cascade = CascadeType.ALL, mappedBy = "application", fetch = FetchType.LAZY)
    private Collection<AppEnv> appEnvCollection;

    public Application()
    {
        super();
    }

    public Application(String appId, String appName)
    {
        this.appId = appId;
        this.appName = appName;
    }

    public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Application(String appId)
    {
        this.appId = appId;
    }
	
	public Collection<AppEnv> getAppEnvCollection() {
		return appEnvCollection;
	}

	public void setAppEnvCollection(Collection<AppEnv> appEnvCollection) {
		this.appEnvCollection = appEnvCollection;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (appId != null ? appId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.appId == null && other.appId != null) || (this.appId != null && !this.appId.equals(other.appId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.Application[appId=" + appId + "]";
	}
	
}
