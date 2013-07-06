package com.hp.et.log.filter;

public enum FilterEnum {

	eSeverity("severity", "com.hp.et.log.filter.SeverityFilter");

	private String name;
	private String className;

	FilterEnum(String name, String className) {
		this.name = name;
		this.className = className;
	}

	public static FilterEnum getFilterEnumByName(String name) {
		FilterEnum[] types = FilterEnum.values();
		for (FilterEnum type : types) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new EnumConstantNotPresentException(FilterEnum.class, name);
	}

	public static FilterEnum getFilterEnumByClassName(String className) {
		FilterEnum[] types = FilterEnum.values();
		for (FilterEnum type : types) {
			if (type.className.equals(className)) {
				return type;
			}
		}
		throw new EnumConstantNotPresentException(FilterEnum.class, className);
	}
	
	public static String getClassName(String name) {
		return getFilterEnumByName(name).className;
	}

	public static String getName(String className) {
		return getFilterEnumByClassName(className).name;
	}
}
