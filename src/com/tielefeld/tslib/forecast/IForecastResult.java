package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

public interface IForecastResult<T> {
	
	/*
	 * TODO:
	 * Add 
	 * <ul>
	 * <li>model</li>
	 * <li>method</li>
	 * <li>x (original time series)</li>
	 * </ul>
	 */
	
	/**
	 * Returns the point forecasts.
	 * 
	 * @return
	 */
	public ITimeSeries<T> getForecast();
	
	/**
	 * Returns the confidence level for the forecast interval.
	 * 
	 * @return
	 */
	public int getConfidenceLevel();
	
	/**
	 * Returns the upper limits for forecast interval with respect to the confidence level {@link #getConfidenceLevel()}. 
	 * 
	 * @return
	 */
	public ITimeSeries<T> getUpper();
		
	/**
	 * Returns the lower limits for forecast interval with respect to the confidence level {@link #getConfidenceLevel()}. 
	 * 
	 * @return
	 */
	public ITimeSeries<T> getLower();
	
	/**
	 * Returns the original time series that was the basis for the forecast.
	 * 
	 * @return
	 */
	public ITimeSeries<T> getOriginal();
}
