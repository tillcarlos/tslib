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
	public ITimeSeries<T> getForecast() {
		return this.forecastSeries;
	}

	@Override
	public double getConfidenceLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ITimeSeries<T> getUpper() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITimeSeries<T> getLower() {
		throw new UnsupportedOperationException();
	}
}
 