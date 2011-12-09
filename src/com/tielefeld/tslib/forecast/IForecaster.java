package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

/**
 * 
 * @author Andre van Hoorn
 *
 * @param <T>
 */
public interface IForecaster<T> {
	
	/**
	 * Performs a time series forecast for the given number of steps in the future. 
	 * 
	 * @param numForecastSteps
	 * @return
	 */
	public IForecastResult<T> forecast (final int numForecastSteps);
	
	/**
	 * Returns the original time series used for the forecast.
	 * 
	 * @return
	 */
	public ITimeSeries<T> getTsOriginal();
	
	/**
 	 * Returns the confidence level to be computed for the forecast.
	 * 
	 * @return
	 */
	public int getConfidenceLevel();
}
