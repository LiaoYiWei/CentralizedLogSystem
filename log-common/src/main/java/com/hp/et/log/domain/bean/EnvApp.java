/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.domain.bean;

import java.io.Serializable;

/**
 * Description goes here.
 */
public class EnvApp implements Serializable
{
    private String envId;

    private String envName;

    private String appId;

    private String appName;

    public String getEnvId()
    {
        return envId;
    }

    public void setEnvId(String envId)
    {
        this.envId = envId;
    }

    public String getEnvName()
    {
        return envName;
    }

    public void setEnvName(String envName)
    {
        this.envName = envName;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }
}
