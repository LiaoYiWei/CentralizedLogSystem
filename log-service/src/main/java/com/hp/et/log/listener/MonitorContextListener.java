package com.hp.et.log.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hp.et.log.monitor.DefaultMonitorManager;
import com.hp.et.log.monitor.IMonitorManager;

public class MonitorContextListener implements ServletContextListener {

	private IMonitorManager monitorMng = DefaultMonitorManager.getInstance();

	public void contextInitialized(ServletContextEvent sce) {
		monitorMng.startup();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		monitorMng.shutdown();
	}

}
