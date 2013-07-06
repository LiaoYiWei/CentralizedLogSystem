package com.hp.et.log.filter;

import java.util.List;

public interface IFilter<T,V> {
	
	/**
	 * @param o
	 * @return
	 */
	T doFilter(T o);
	
	void putValues(V values);

}
