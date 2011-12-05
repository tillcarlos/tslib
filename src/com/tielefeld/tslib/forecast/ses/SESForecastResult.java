package com.tielefeld.tslib.forecast.ses;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.IForecastResult;

public class SESForecastResult implements IForecastResult<Double> {

	
	private ITimeSeries<Double> forecastSeries;

	public SESForecastResult(ITimeSeries<Double> tsFC) {
		this.forecastSeries = tsFC;
	}

	@Override
	public ITimeSeries<Double> getForecastTimeSeries() {
		return this.forecastSeries;
	}

}
