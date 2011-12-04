package com.tielefeld.tslib;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Andre van Hoorn
 * 
 * @param <T>
 *
 */
public interface ITimeSeries<T> {
	public Date getStartTime();
	public long getDeltaTime();
	public TimeUnit getDeltaTimeUnit();
	public ITimeSeriesPoint<T> append(T value);
	public List<ITimeSeriesPoint<T>> getPoints();
	public int getCapacity();
	public int size();
	public Date getEndTime();
}
