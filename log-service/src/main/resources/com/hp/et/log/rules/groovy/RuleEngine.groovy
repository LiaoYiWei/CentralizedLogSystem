package com.hp.et.log.rules.groovy;

import com.hp.et.log.domain.bean.LogEvent;
import com.hp.et.log.rules.IRuleEngine;

public class RuleEngine implements IRuleEngine {

	
	public void parseEvent(LogEvent event) {
		
		println "Entering parseEvent - com.hp.et.log.rules.groovy.RuleEngine"
	}
}


