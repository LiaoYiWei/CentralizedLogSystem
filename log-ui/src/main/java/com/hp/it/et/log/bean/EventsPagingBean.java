/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.it.et.log.bean;

import java.util.List;

/**
 * Description goes here.
 */
public class EventsPagingBean
{
    private Long totalCount;

    private List events;

    public EventsPagingBean()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public EventsPagingBean(Long totalCount, List events)
    {
        super();
        this.totalCount = totalCount;
        this.events = events;
    }

    public Long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(Long totalCount)
    {
        this.totalCount = totalCount;
    }

    public List getEvents()
    {
        return events;
    }

    public void setEvents(List events)
    {
        this.events = events;
    }

}
