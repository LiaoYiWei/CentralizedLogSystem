package com.hp.et.log.domain.bean;

/**
 * this enum use to keep the log levels
 * @author 
 *
 */
public enum LogSeverityEnum implements IEnum{

	TRACE(0, "TRACE"),
	DEBUG(1, "DEBUG"),
	INFO(2, "INFO"),
	WARN(3, "WARN"),
	ERROR(4, "ERROR"),
	FATAL(5, "FATAL"),
	DISABLE(6, "DISABLE"),
	SHUTDOWN(7, "SHUTDOWN");
	
	LogSeverityEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}
	
	private int index;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
	
	public static LogSeverityEnum fromIndex(int index)
	{
	    LogSeverityEnum[]  enums = LogSeverityEnum.values();
	    
	    for(LogSeverityEnum item: enums)
	    {
	        if(item.getIndex() == index)
	        {
	            return item;
	        }
	    }
	    
	    return null;
	}
}
