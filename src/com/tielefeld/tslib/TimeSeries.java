package com.tielefeld.tslib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

/**
 * 
 * @author Andre van Hoorn
 * 
 * @param <T>
 */
public class TimeSeries<T> implements ITimeSeries<T> {
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
		this.startTime = new Date(startTime.getTime());
		this.deltaTime = deltaTime;
		this.deltaTimeUnit = deltaTimeUnit;
		this.capacity = capacity;
		this.oneStepMillis = TimeUnit.MILLISECONDS.convert(this.deltaTime,
				this.deltaTimeUnit);

		if (ITimeSeries.INFINITE_CAPACITY == capacity) {
			this.points = new CircularFifoBuffer();
		} else {
			this.points = new CircularFifoBuffer(this.capacity);
		}
		
		this.nextTime = this.startTime;
		this.setNextTime();
	}
	

	public TimeSeries(final Date startTime, final long deltaTime,
			final TimeUnit deltaTimeUnit) {
		this(startTime, deltaTime, deltaTimeUnit, ITimeSeries.INFINITE_CAPACITY);
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
		this.nextTime.setTime(this.nextTime.getTime() + this.oneStepMillis);
	}

	@Override
	public List<ITimeSeriesPoint<T>> getPoints() {
		return new ArrayList<ITimeSeriesPoint<T>>(this.points);
	}

	@Override
	public List<T> getValues() {
		final List<ITimeSeriesPoint<T>> pointsCopy = this.getPoints();
		final List<T> retVals = new ArrayList<T>(pointsCopy.size());
		for (final ITimeSeriesPoint<T> curPoint : pointsCopy) {
			retVals.add(curPoint.getValue());
		}
		
		return retVals;
	}
	
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
		return new Date(this.getStartTime().getTime() + this.oneStepMillis
				* this.size());
	}

	@Override
	public List<ITimeSeriesPoint<T>> appendAll(final T[] values) {
		final List<ITimeSeriesPoint<T>> retVals = new ArrayList<ITimeSeriesPoint<T>>(values.length);
		
		for (final T value : values) {
			retVals.add(this.append(value));		
		}
		
		return retVals;
	}
}