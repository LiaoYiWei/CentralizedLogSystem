/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.dao;

import java.util.List;


import com.hp.et.log.domain.bean.QueryInformation;
import com.hp.et.log.entity.Event;

public interface IEventDao extends IDao<String, Event>
{
    public List<Event> findEventByQueryInfo(QueryInformation queryInfo);
    
    public Long getEventCountByQueryInfo(QueryInformation queryInfo);
}
