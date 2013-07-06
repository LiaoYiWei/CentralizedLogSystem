package com.hp.et.log.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hp.et.log.dao.IHostDao;
import com.hp.et.log.domain.bean.HostInfo;
import com.hp.et.log.entity.Host;


@Path("/host")
public class HostResource {
	
	
	
	private IHostDao hostDao;
	
	
	public IHostDao getHostDao()
    {
        return hostDao;
    }


    public void setHostDao(IHostDao hostDao)
    {
        this.hostDao = hostDao;
    }


    @POST
	@Path("/findAllHost")
	@Produces("application/java_serializable")   
	public List<HostInfo> findAllHost(){
	    ArrayList<Host> hostList = (ArrayList<Host>) hostDao.findAllHost();
	    ArrayList<HostInfo> hostInfoList = new ArrayList<HostInfo>();
		if(hostList != null && hostList.size() > 0){
			for(Host host : hostList){
				HostInfo hostInfo = new HostInfo();
				hostInfo.setHostId(host.getHostId());
				hostInfo.setHostName(host.getHostName());
				hostInfo.setIpAddress(host.getIpAddress());
				hostInfoList.add(hostInfo);
			}
			
			return hostInfoList;
		}
		else{
			
			return null;
		}
	}
	
	
}
