package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

/**
 * Result of a time series forecast, e.g., computed by {@link IForecaster}. If additional fields are required,
 * {@link IForecaster}s should extend this class.
 * 
 * @author Andre van Hoorn
 * 
 * @param <T>
 */
public class ForecastResult<T> implements IForecastResult<T> {

	private final ITimeSeries<T> tsForecast;
	private final ITimeSeries<T> tsOriginal;

	private final int confidenceLevel;
	private final ITimeSeries<T> tsUpper;
	private final ITimeSeries<T> tsLower;

	public ForecastResult(final ITimeSeries<T> tsForecast, final ITimeSeries<T> tsOriginal, final int confidenceLevel, final ITimeSeries<T> tsLower,
			final ITimeSeries<T> tsUpper) {
		this.tsForecast = tsForecast;
		this.tsOriginal = tsOriginal;

		this.confidenceLevel = confidenceLevel;
		this.tsUpper = tsUpper;
		this.tsLower = tsLower;
	}

	/**
	 * Constructs a {@link ForecastResult} with confidence level <code>0</code>, where the time series returned
	 * {@link #getLower()} by {@link #getUpper()} are the forecast series.
	 * 
	 * @param tsForecast
	 */
	public ForecastResult(final ITimeSeries<T> tsForecast, final ITimeSeries<T> tsOriginal) {
		this(tsForecast, tsOriginal, 0, tsForecast, tsForecast); // tsForecast also lower/upper
	}

	@Override
	public ITimeSeries<T> getForecast() {
		return this.tsForecast;
	}

	@Override
	public int getConfidenceLevel() {
		return this.confidenceLevel;
	}

	@Override
	public ITimeSeries<T> getUpper() {
		return this.tsUpper;
	}

	@Override
	public ITimeSeries<T> getLower() {
		return this.tsLower;
	}

	@Override
	public ITimeSeries<T> getOriginal() {
		return this.tsOriginal;
	}
}
