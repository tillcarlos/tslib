package com.tielefeld.tslib;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Andre van Hoorn
 * 
 * @param <T>
 */
public class TimeSeries<T> implements ITimeSeries<T> {
	public static final int INFINITE_CAPACITY = -1;

	private final Date startTime;
	private final Date nextTime;
	private final long deltaTime;
	private final TimeUnit deltaTimeUnit;
	private final int capacity;
	private final CopyOnWriteArrayList<ITimeSeriesPoint<T>> points;

	/**
	 * 
	 * @param startTime
	 * @param deltaTime
	 * @param deltaTimeUnit
	 * @param capacity
	 */
	public TimeSeries(final Date startTime, final long deltaTime, final TimeUnit deltaTimeUnit,
			final int capacity) {
		this.startTime = startTime;
		this.deltaTime = deltaTime;
		this.deltaTimeUnit = deltaTimeUnit;
		this.capacity = capacity;

		this.points = new CopyOnWriteArrayList<ITimeSeriesPoint<T>>();
		
		
		// TODO: set nextTime properly
		this.nextTime = this.startTime;
	}

	public TimeSeries(final Date startTime, final long deltaTime, final TimeUnit deltaTimeUnit) {
		this(startTime, deltaTime, deltaTimeUnit, TimeSeries.INFINITE_CAPACITY);
	}

	/**
	 * @return the startTime
	 */
	@Override
	public Date getStartTime() {
		return this.startTime;
	}

	@Override
	public long getDeltaTime() {
		return this.deltaTime;
	}

	@Override
	public TimeUnit getDeltaTimeUnit() {
		return this.deltaTimeUnit;
	}

	@Override
	public synchronized ITimeSeriesPoint<T> append(final T value) {
		final ITimeSeriesPoint<T> point = new TimeSeriesPoint<T>(this.nextTime, value);
		
		// TODO: respect capacity
		
		this.points.add(point);
		
		// TODO: set nextTime
		
		return point;
	}
	
	@Override
	public List<ITimeSeriesPoint<T>> getPoints() {
		return this.points;
	}

	/**
	 * @return the capacity
	 */
	@Override
	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public int size() {
		return this.points.size();
	}
}