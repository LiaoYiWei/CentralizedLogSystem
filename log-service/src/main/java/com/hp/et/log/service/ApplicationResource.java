package com.hp.et.log.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hp.et.log.dao.IAppEnvDao;
import com.hp.et.log.dao.IApplicationDao;
import com.hp.et.log.domain.bean.ApplicationInfo;
import com.hp.et.log.domain.bean.EnvApp;
import com.hp.et.log.domain.bean.EnvInfo;
import com.hp.et.log.domain.bean.HostInfo;
import com.hp.et.log.domain.bean.NodeInfo;
import com.hp.et.log.entity.AppEnv;
import com.hp.et.log.entity.AppEnvNode;
import com.hp.et.log.entity.Application;
import com.hp.et.log.entity.Host;

@Path("/application")
public class ApplicationResource {
	
	
	
	private IApplicationDao applicationDao;
    
	
    public IApplicationDao getApplicationDao()
    {
        return applicationDao;
    }


    public void setApplicationDao(IApplicationDao applicationDao)
    {
        this.applicationDao = applicationDao;
    }


    @POST
    @Path("/findAllApplication")
    @Produces("application/java_serializable")
    public List<ApplicationInfo> findAllApplication()
    {
        ArrayList<Application> apps = (ArrayList<Application>) applicationDao.findAllApplication();
        List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
        if (null != apps) {
            for (Application app : apps) {
            	ApplicationInfo logAppInfo = new ApplicationInfo();
                logAppInfo.setAppId(app.getAppId());
                logAppInfo.setAppName(app.getAppName());
                List<EnvInfo> envInfos = new ArrayList<EnvInfo>();
                Collection<AppEnv> appEnvs = app.getAppEnvCollection();
                if (null != appEnvs) {
                    for (AppEnv appEnv : appEnvs) {
                    	EnvInfo envInfo = new EnvInfo();
                        envInfo.setId(appEnv.getEnvId());
                        envInfo.setName(appEnv.getEnvName());
                        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
                        Collection<AppEnvNode> appEnvNodes = appEnv
                                .getAppEnvNodeCollection();
                        if (null != appEnvNodes) {
                            for (AppEnvNode appEnvNode : appEnvNodes) {
                            	NodeInfo nodeInfo = new NodeInfo();
                                nodeInfo.setId(appEnvNode.getNodeId());
                                nodeInfo.setName(appEnvNode.getNodeName());
                                Host host = appEnvNode.getHost();
                                if (null != host) {
                                    HostInfo hostInfo = new HostInfo();
                                    hostInfo.setHostId(host.getHostId());
                                    hostInfo.setHostName(host.getHostName());
                                    hostInfo.setIpAddress(host.getIpAddress());
                                    nodeInfo.setHost(hostInfo);
                                }
                                nodeInfos.add(nodeInfo);
                            }
                        }
                        envInfo.setNodeInfos(nodeInfos);
                        envInfos.add(envInfo);
                    }
                }

                logAppInfo.setEnvInfos(envInfos);
                appInfos.add(logAppInfo);
            }
        }
        return appInfos;
    }
    
    private IAppEnvDao appEnvDao;

    public IAppEnvDao getAppEnvDao() {
	return appEnvDao;
    }

    public void setAppEnvDao(IAppEnvDao appEnvDao) {
	this.appEnvDao = appEnvDao;
    }

    @POST
    @Path("/findAllEnvsApp")
    @Produces("application/java_serializable")
    public List<EnvApp> findAllEnvsWithAppName() {
	ArrayList<EnvApp> envsApp = new ArrayList<EnvApp>();

	ArrayList<AppEnv> envs = appEnvDao.findAllAppEnvs();
	if (envs != null && envs.size() > 0) {
	    for (AppEnv appEnv : envs) {
		Application app = appEnv.getApplication();
		EnvApp envApp = new EnvApp();
		envApp.setAppId(app.getAppId());
		envApp.setAppName(app.getAppName());
		envApp.setEnvId(appEnv.getEnvId());
		envApp.setEnvName(appEnv.getEnvName());
		envsApp.add(envApp);
	    }
	}

	return envsApp;
    }


    
}
