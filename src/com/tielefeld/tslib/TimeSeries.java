package com.tielefeld.tslib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.buffer.BoundedFifoBuffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import sun.misc.Queue;

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
	private final CircularFifoBuffer points;
	// approach of avh private final CopyOnWriteArrayList<ITimeSeriesPoint<T>>
	// points;
	private long oneStepMillis;

	/**
	 * 
	 * @param startTime
	 * @param deltaTime
	 * @param deltaTimeUnit
	 * @param capacity
	 */
	public TimeSeries(final Date startTime, final long deltaTime,
			final TimeUnit deltaTimeUnit, final int capacity) {
		this.startTime = startTime;
		this.deltaTime = deltaTime;
		this.deltaTimeUnit = deltaTimeUnit;
		this.capacity = capacity;
		this.oneStepMillis = TimeUnit.MILLISECONDS.convert(this.deltaTime,
				this.deltaTimeUnit);

		if (TimeSeries.INFINITE_CAPACITY == capacity) {
			this.points = new CircularFifoBuffer();
		} else {
			this.points = new CircularFifoBuffer(this.capacity);
		}
		
		this.nextTime = new Date();
		this.setNextTime();
	}
	

	public TimeSeries(final Date startTime, final long deltaTime,
			final TimeUnit deltaTimeUnit) {
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
		final ITimeSeriesPoint<T> point = new TimeSeriesPoint<T>(this.nextTime,
				value);

		this.points.add(point);
		
		this.setNextTime();
		return point;
	}

	/**
	 * 
	 */
	private void setNextTime() {
		this.nextTime.setTime(this.nextTime.getTime() + oneStepMillis);
	}

	@Override
	public List<ITimeSeriesPoint<T>> getPoints() {
		return new ArrayList<ITimeSeriesPoint<T>>(this.points);
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

	@Override
	public Date getEndTime() {
		return new Date(this.getStartTime().getTime() + oneStepMillis
				* this.size());
	}
}