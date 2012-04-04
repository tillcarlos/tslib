package com.tielefeld.tslib.forecast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.TimeSeries;

public abstract class AbstractForecaster<T> implements IForecaster<T> {
	private final ITimeSeries<T> historyTimeseries;
	private final int confidenceLevel;
	
	/**
	 * @param historyTimeseries
	 */
	public AbstractForecaster(final ITimeSeries<T> historyTimeseries) {
		this(historyTimeseries, 0);
	}

	public AbstractForecaster(final ITimeSeries<T> historyTimeseries, final int confidenceLevel) {
		this.historyTimeseries = historyTimeseries;
		this.confidenceLevel = confidenceLevel;
	}
	
	
	/**
	 * @return the historyTimeseries
	 */
	@Override
	public ITimeSeries<T> getTsOriginal() {
		return this.historyTimeseries;
	}

	protected ITimeSeries<T> prepareForecastTS() {
		final ITimeSeries<T> history = this.getTsOriginal();

		// The starting point of the FC series is calculated by _one_ additional
		// tick...
		final long lastDistanceMillis = TimeUnit.MILLISECONDS.convert(
				history.getDeltaTime(), history.getDeltaTimeUnit());
		// ... plus the end point of the historic series
		final Date startTime = new Date(history.getEndTime().getTime());
		final TimeSeries<T> tsFC = new TimeSeries<T>(startTime,
				history.getDeltaTime(), history.getDeltaTimeUnit());

		return tsFC;
	}

	@Override
	public int getConfidenceLevel() {
		return this.confidenceLevel;
	}
}
