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
@Table(name = "APP_ENV")
@NamedQueries
({
    @NamedQuery(name = "AppEnv.findAll", query = "SELECT a FROM AppEnv a"),
    @NamedQuery(name = "AppEnv.findByEnvId", query = "SELECT a FROM AppEnv a WHERE a.envId = :envId"),
    @NamedQuery(name = "AppEnv.findByEnvName", query = "SELECT a FROM AppEnv a WHERE a.envName = :envName")
})
public class AppEnv implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name="generator", strategy="uuid", parameters={})
    @GeneratedValue(generator="generator")
    @Basic(optional = false)
    @Column(name = "ENV_ID")
    private String envId;
    
    @Basic(optional = false)
    @Column(name = "ENV_NAME", length = 255)
    private String envName;

    @ManyToOne(targetEntity=Application.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="APP_ID")
    private Application application;
    
    @OneToMany(targetEntity=AppEnvNode.class, cascade = CascadeType.ALL, mappedBy = "appEnv", fetch = FetchType.LAZY)
    private Collection<AppEnvNode> appEnvNodeCollection;
    
    @OneToMany(targetEntity=AppEnvRule.class, cascade = CascadeType.ALL, mappedBy = "appEnv", fetch = FetchType.LAZY)
    private Collection<AppEnvRule> appEnvRuleCollection;

    public AppEnv()
    {
        super();
    }

    public AppEnv(String envId, String envName)
    {
        this.envId = envId;
        this.envName = envName;
    }

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Collection<AppEnvNode> getAppEnvNodeCollection() {
		return appEnvNodeCollection;
	}

	public void setAppEnvNodeCollection(Collection<AppEnvNode> appEnvNodeCollection) {
		this.appEnvNodeCollection = appEnvNodeCollection;
	}


	public Collection<AppEnvRule> getAppEnvRuleCollection() {
		return appEnvRuleCollection;
	}

	public void setAppEnvRuleCollection(Collection<AppEnvRule> appEnvRuleCollection) {
		this.appEnvRuleCollection = appEnvRuleCollection;
	}

	@Override
	public int hashCode(){
		 int hash = 0;
		 hash += (envId != null ? envId.hashCode() : 0);
		 return hash;
	}
	
	@Override
	public boolean equals(Object object){
		// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppEnv)) {
            return false;
        }
        AppEnv other = (AppEnv) object;
        if ((this.envId == null && other.envId != null) || (this.envId != null && !this.envId.equals(other.envId))) {
            return false;
        }
        return true;
	}
	
	@Override
	public String toString(){
		return "com.hp.et.log.entity.AppEnv[envId=" + envId + "]";
	}
	
}
