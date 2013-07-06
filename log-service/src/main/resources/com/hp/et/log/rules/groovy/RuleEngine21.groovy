package com.hp.et.log.rules.groovy;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.rules.IRuleEngine;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RuleEngine implements IRuleEngine {

	
	public void parseEvent(LogEvent event) {
		
		println "Entering parseEvent - com.hp.et.log.rules.groovy.RuleEngine"
		event.setSeverity("DEBUG");
		Map map = event.getExtraProps();
		if(map!=null){
			String unitOfWorkId = map.get("UnitOfWorkId");
			if(unitOfWorkId!=null){
				event.setAttribute1Name("UnitOfWorkId");
				event.setAttribute1Value(unitOfWorkId);
			}
		}

	}
}


