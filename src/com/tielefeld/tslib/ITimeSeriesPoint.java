package com.tielefeld.tslib;

import java.util.Date;

/**
 * 
 * @author Andre van Hoorn
 *
 * @param <T>
 */
public interface ITimeSeriesPoint<T> {
	public Date getTime();
	public T getValue();
}
