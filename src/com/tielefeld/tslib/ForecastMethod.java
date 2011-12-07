package com.tielefeld.tslib;

import com.tielefeld.tslib.anomalycalculators.IAnomalyCalculator;
import com.tielefeld.tslib.anomalycalculators.SimpleAnomalyCalculator;
import com.tielefeld.tslib.forecast.IForecaster;
import com.tielefeld.tslib.forecast.mean.MeanForecaster;

public enum ForecastMethod {
	MEAN;

	public IForecaster<Double> getForecaster(ITimeSeries<Double> history) {
		switch (this) {
		case MEAN:
			return new MeanForecaster(history);
		default:
			return new MeanForecaster(history);
		}
	}

	public IAnomalyCalculator<Double> getAnomalyCalculator() {
		switch (this) {
		case MEAN:
			return new SimpleAnomalyCalculator();
		default:
			return new SimpleAnomalyCalculator();
		}
	}

}
