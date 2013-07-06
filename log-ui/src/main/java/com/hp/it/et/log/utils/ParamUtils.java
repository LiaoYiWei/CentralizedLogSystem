package com.hp.it.et.log.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ParamUtils {
	
	public static int convertTime(String timestamp){
		if(StringUtils.isEmpty(timestamp)){
			return 0;
		}
		if(timestamp.endsWith("m")){
			String time = timestamp.substring(0, timestamp.length()-1);
			return Integer.parseInt(time);
		}
		if(timestamp.endsWith("h")){
			String time = timestamp.substring(0, timestamp.length()-1);
			return Integer.parseInt(time)*60;
		}
		if(timestamp.endsWith("d")){
			String time = timestamp.substring(0, timestamp.length()-1);
			return Integer.parseInt(time)*60*24;
		}
		return 0;
		
	}
	
	public static List convertId(String ids){
		List idList = null;
		if(StringUtils.isNotEmpty(ids) && StringUtils.isNotBlank(ids)){
			idList = new ArrayList();
			if(ids.indexOf(",")>0){
				String[] idarray = ids.split(",");
				for (int i = 0; i < idarray.length; i++) {
					String id = idarray[i];
					idList.add(id);
				}
			}else{
				idList.add(ids);
			}
			
		}
		return idList;
	}

}
