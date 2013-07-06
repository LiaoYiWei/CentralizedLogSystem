package com.hp.et.log.rules;

import com.hp.et.log.domain.bean.LogEvent;





public interface IRuleEngine {
	public void parseEvent(LogEvent event);

}