package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;


/**
 * 
 * @author Andre van Hoorn
 *
 * @param <T>
 */
public class ForecastResult<T> implements IForecastResult<T> {

	private final ITimeSeries<T> forecastSeries;
	
	public ForecastResult(final ITimeSeries<T> forecastSeries) {
		this.forecastSeries = forecastSeries;
	}
	
	@Override
	public ITimeSeries<T> getForecastTimeSeries() {
		return this.forecastSeries;
	}
}
 