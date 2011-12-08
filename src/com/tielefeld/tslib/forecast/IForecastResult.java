package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

public interface IForecastResult<T> {
	
	/**
	 * Returns the confidence level for the forecast interval.
	 * 
	 * @return
	 */
	public double getConfidenceLevel();
	
	/**
	 * Returns the upper limits for forecast interval with respect to the confidence level {@link #getConfidenceLevel()}. 
	 * 
	 * @return
	 */
	public ITimeSeries<T> getUpper();
	
	/**
	 * Returns the point forecasts.
	 * 
	 * @return
	 */
	public ITimeSeries<T> getForecast();
	
	/**
	 * Returns the lower limits for forecast interval with respect to the confidence level {@link #getConfidenceLevel()}. 
	 * 
	 * @return
	 */
	public ITimeSeries<T> getLower();
}
