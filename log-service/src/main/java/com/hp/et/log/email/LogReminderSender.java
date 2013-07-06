/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2009   All rights reserved. ======================
 */

package com.hp.et.log.email;

import java.util.Date;
import java.util.HashMap;

import com.hp.et.log.domain.bean.AppClientBasisInfo;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.domain.bean.LogSeverityEnum;
import com.hp.et.log.entity.AppEnvRule;
import com.hp.et.log.mail.util.AddressPaser;
import com.hp.et.log.mail.util.EmailBuilder;
import com.hp.et.log.mail.util.LocalMailService;
import com.hp.et.log.mail.util.MailException;
import com.hp.et.log.mail.util.TemplateMail;
import com.hp.et.log.restful.tool.Utility;

/**
 * Description goes here.
 */
public class LogReminderSender {

    public Boolean sendLogReminderEMail(LogEvent event, AppEnvRule rule,
	    AppClientBasisInfo basis) {

	try {
	    String addrList = rule.getEmailPdl();
	    String severity = LogSeverityEnum.fromIndex(event.getSeverity())
		    .getName();

	    EmailBuilder builder = new EmailBuilder();

	    HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("event", event);
	    map.put("rule", rule);
	    map.put("severity", severity);
	    map.put("basis", basis);
	    map.put("time", Utility.dateFormat2String(
		    new Date(event.getTimestamp()), "yyyy-MM-dd HH:mm:ss"));

	    String subject = "ACTION REQUIRED ------ Log Error violated for rule ["
		    + rule.getRuleName() + "]";

	    TemplateMail mail = builder.buildTemplateMail(map,
		    subject, "LogReminderEmail.vm", addrList);
	    
	    new LocalMailService().sendMail(mail);

	  	} catch (MailException e) {
	    return false;
	}
	return true;
    }
}
