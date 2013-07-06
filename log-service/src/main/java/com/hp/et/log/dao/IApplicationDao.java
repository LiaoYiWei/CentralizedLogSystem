/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.ArrayList;
import java.util.List;

import com.hp.et.log.entity.Application;

public interface IApplicationDao extends IDao<String, Application>
{
    public List<Application> findAllApplication();
    public Application getApplicationByAppName(String appName);
    
}
