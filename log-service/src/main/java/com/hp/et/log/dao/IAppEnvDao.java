/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.ArrayList;

import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.Application;

public interface IAppEnvDao extends IDao<String, AppEnv>
{
    public AppEnv getAppEnvByAppIdAndEnvName(String appId, String envName);
    public Application getApplicationByAppName(String appName);
    public ArrayList<AppEnv> findAllAppEnvs();
    
}
