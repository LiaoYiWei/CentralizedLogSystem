/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.List;

import com.hp.et.log.entity.AppEnvNode;

public interface IAppEnvNodeDao extends IDao<String, AppEnvNode>
{
    public AppEnvNode findNodeByEnvIdAndNodeName(String envId, String nodeName);
    
    public List<AppEnvNode> getNodesByEnvId(String envId);
}
