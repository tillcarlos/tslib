package com.tielefeld.tslib.forecast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.TimeSeries;

public abstract class AbstractForecaster<T> implements IForecaster<T> {
	private final ITimeSeries<T> historyTimeseries;

	/**
	 * @param historyTimeseries
	 */
	public AbstractForecaster(final ITimeSeries<T> historyTimeseries) {
		this.historyTimeseries = historyTimeseries;
	}

	/**
	 * @return the historyTimeseries
	 */
	@Override
	public ITimeSeries<T> getHistoryTimeSeries() {
		return this.historyTimeseries;
	}

	protected ITimeSeries<T> prepareForecastTS() {
		ITimeSeries<T> history = this.getHistoryTimeSeries();

		// The starting point of the FC series is calculated by _one_ additional
		// tick...
		long lastDistanceMillis = TimeUnit.MILLISECONDS.convert(
				history.getDeltaTime(), history.getDeltaTimeUnit());
		// ... plus the end point of the historic series
		Date startTime = new Date(history.getEndTime().getTime()
				+ lastDistanceMillis);
		TimeSeries<T> tsFC = new TimeSeries<T>(startTime,
				history.getDeltaTime(), history.getDeltaTimeUnit());

		return tsFC;
	}
}
