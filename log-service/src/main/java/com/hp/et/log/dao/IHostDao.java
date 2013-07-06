/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.ArrayList;
import java.util.List;

import com.hp.et.log.entity.Host;

public interface IHostDao extends IDao<String, Host>
{
    public List<Host> findAllHost();
    public Host getHostByIpAddress(String ipAddress);
    
}
