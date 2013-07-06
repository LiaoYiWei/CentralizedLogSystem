/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.appender;

import com.hp.et.log.domain.bean.LogEvent;

public interface ILogSender
{
    public void sendLog( final LogEvent logEvent);

    public void startup(final RunContext context);
}
