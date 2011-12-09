package com.tielefeld.tslib.forecast.ses;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.IForecastResult;

public class SESForecastResult implements IForecastResult<Double> {

	
	private final ITimeSeries<Double> forecastSeries;

	public SESForecastResult(final ITimeSeries<Double> tsFC) {
		this.forecastSeries = tsFC;
	}

	@Override
	public ITimeSeries<Double> getForecast() {
		return this.forecastSeries;
	}

	@Override
	public double getConfidenceLevel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITimeSeries<Double> getUpper() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITimeSeries<Double> getLower() {
		throw new UnsupportedOperationException();
	}

}
