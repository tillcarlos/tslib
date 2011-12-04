package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

/**
 * 
 * @author Andre van Hoorn
 *
 * @param <T>
 */
public interface IForecaster<T> {
	public IForecastResult<T> forecast (final int numForecastSteps);
	
	public ITimeSeries<T> getHistoryTimeSeries();
}
