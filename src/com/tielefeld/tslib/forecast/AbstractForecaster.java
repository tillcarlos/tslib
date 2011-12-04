package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

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
}
