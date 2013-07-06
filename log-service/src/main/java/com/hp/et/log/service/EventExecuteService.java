/*
 * Copyright Notice ====================================================
 * This file contains proprietary information of Hewlett-Packard Co.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2012 All rights reserved. =============================
 */

package com.hp.et.log.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.et.log.dao.IEventDao;
import com.hp.et.log.dao.IRuntimeInstanceDao;
import com.hp.et.log.dao.jpaimpl.EventJpaDao;
import com.hp.et.log.dao.jpaimpl.RuntimeInstanceJpaDao;
import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.entity.Event;
import com.hp.et.log.entity.RuntimeInstance;

import com.hp.et.log.monitor.IMonitorManager;
import com.hp.et.log.rules.IRuleEngineService;
import com.hp.et.log.rules.RuleEngineService.RuleType;

public class EventExecuteService {

	private static Logger logger = LoggerFactory
			.getLogger(EventExecuteService.class);
	private IEventDao eventDao;

	private IRuntimeInstanceDao runtimeInstanceDao;

	private IRuleEngineService ruleEngineService;

	private IMonitorManager monitorMng;
	
	private EmailService emailService;

	public IEventDao getEventDao() {
		return eventDao;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	public IRuntimeInstanceDao getRuntimeInstanceDao() {
		return runtimeInstanceDao;
	}

	public void setRuntimeInstanceDao(IRuntimeInstanceDao runtimeInstanceDao) {
		this.runtimeInstanceDao = runtimeInstanceDao;
	}

	public void createEvent(LogEvent logEvent) {
		try {
			logger.info("ENTER createEvent");
			logger.debug(logEvent.debugString());
			// comment this grovvy loader for this version
			ruleEngineService.proceedRule(logEvent, RuleType.GROOVY);
            monitorMng.putLogEvent2MonitorQueue(logEvent);
			Event event = createEventByLogEvent(logEvent);
			eventDao.persist(event);
			logEvent.setId(event.getEventId());
			//email
			emailService.sendEmail(logEvent);

			logger.info("EXIT createEvent");
		} catch (Exception ex) {
			logger.error("could NOT handle log" + ex);
			throw new RuntimeException(ex);
		}

	}

	public IRuleEngineService getRuleEngineService() {
		return ruleEngineService;
	}

	public void setRuleEngineService(IRuleEngineService ruleEngineService) {
		this.ruleEngineService = ruleEngineService;
	}

	/**
	 * convert the LogEvent to Event
	 * 
	 * @param logEvent
	 * @return
	 */
	private Event createEventByLogEvent(LogEvent logEvent) {
		// better to call getReferenceById, we just need to rely on lazy load
		// for this case, we just want to save the child table, we don't need to actually load the runtimeInstance
		logger.info("ENTER createEventByLogEvent");
		RuntimeInstance ris = runtimeInstanceDao.getReferenceById(logEvent
				.getRunId());
		Event et = new Event();
		et.setThrowableMessage(cutStringBytesByUTF8(logEvent.getThrowableMessage(),true));
		et.setThreadName(logEvent.getThreadName());
		et.setSeverity(logEvent.getSeverity());
		et.setRuntimeInstance(ris);
		et.setMessageType(logEvent.getMessageType());
		et.setMessage(cutStringBytesByUTF8(logEvent.getMessage(),false));
		et.setLoggerName(logEvent.getLoggerName());
		et.setCreateTime(new Date(logEvent.getTimestamp()));
		et.setLogSequence(logEvent.getLogSequence());
		et.setAttribute1Name(logEvent.getAttribute1Name());
		et.setAttribute2Name(logEvent.getAttribute2Name());
		et.setAttribute3Name(logEvent.getAttribute3Name());
		et.setAttribute4Name(logEvent.getAttribute4Name());
		et.setAttribute5Name(logEvent.getAttribute5Name());
		et.setAttribute1Value(logEvent.getAttribute1Value());
		et.setAttribute2Value(logEvent.getAttribute2Value());
		et.setAttribute3Value(logEvent.getAttribute3Value());
		et.setAttribute4Value(logEvent.getAttribute4Value());
		et.setAttribute5Value(logEvent.getAttribute5Value());
		logger.info("EXIT createEventByLogEvent");
		return et;
	}

	private String cutStringBytesByUTF8(String str,boolean isThrowableMessage) {
		final int length = 3000;
		final int cutLength = 2900;
		final String message = "(Too long, Truncated the message...)";
		final String throwableMessage = "(Too long, Truncated the throwableMessage...)";
		final String encoding = "UTF8";
		if (null != str && !"".equals(str)) {
			byte[] bytes = null;
			try {
				bytes = str.getBytes(encoding);
				if(bytes.length >= length) {
					byte[] tempBytes = new byte[cutLength];
					System.arraycopy(bytes, 0, tempBytes, 0, cutLength);
					String s = new String(tempBytes, encoding);
					if(isThrowableMessage){
						s += throwableMessage;
					}else{
						s += message;
					}
					return s;
				}
				return str;
			} catch (UnsupportedEncodingException e) {
				logger.error("unsupported encoding" + e);
				throw new RuntimeException(e);
			}
		}
		return str;
	}

	public IMonitorManager getMonitorMng() {
		return monitorMng;
	}

	public void setMonitorMng(IMonitorManager monitorMng) {
		this.monitorMng = monitorMng;
	}

}
