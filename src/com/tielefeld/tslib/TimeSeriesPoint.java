package com.tielefeld.tslib;

import java.util.Date;

public class TimeSeriesPoint<T> implements ITimeSeriesPoint<T> {

	private final Date time;
	private final T value;
	
	/**
	 * @param time
	 * @param value
	 */
	public TimeSeriesPoint(final Date time, final T value) {
		// TODO is that a good pattern or should we ensure that the object is immutable from outside?
		this.time = (Date) time.clone();
		this.value = value;
	}

	@Override
	public Date getTime() {
		return this.time;
	}

	@Override
	public T getValue() {
		return this.value;
	}
	
	
	@Override
	public String toString() {
		return "[" + this.getTime() + "=" + this.getValue() + "]";
	}
	

}
