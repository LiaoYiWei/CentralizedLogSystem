/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.List;

import com.hp.et.log.entity.RuntimeInstance;

public interface IRuntimeInstanceDao extends IDao<String, RuntimeInstance>
{
    public String getAppIdByRunId(String runId);
    
    /**
     * 
     * Find runtimeInstance by runID. 
     * This function is unlazy mode. 
     *
     * @param runID
     * @return
     */
//    public RuntimeInstance findUlRuntimeInstanceByID(String runID);
    
    public RuntimeInstance findLatestRuntimeInstanceByNodeID(String nodeID);
//    public String findEnvIdByRunId(String runId);
//    public List getHostAppEnvNodeByRunId(String runId);
    
}
