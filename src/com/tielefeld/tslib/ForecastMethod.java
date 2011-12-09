package com.tielefeld.tslib;

import com.tielefeld.tslib.anomalycalculators.IAnomalyCalculator;
import com.tielefeld.tslib.anomalycalculators.SimpleAnomalyCalculator;
import com.tielefeld.tslib.forecast.IForecaster;
import com.tielefeld.tslib.forecast.mean.MeanForecasterJava;

public enum ForecastMethod {
	MEAN;

	public IForecaster<Double> getForecaster(ITimeSeries<Double> history) {
		switch (this) {
		case MEAN:
			return new MeanForecasterJava(history);
		default:
			return new MeanForecasterJava(history);
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
