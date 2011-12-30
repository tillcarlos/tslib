package com.tielefeld.tslib;

import com.tielefeld.tslib.anomalycalculators.IAnomalyCalculator;
import com.tielefeld.tslib.anomalycalculators.SimpleAnomalyCalculator;
import com.tielefeld.tslib.forecast.IForecaster;
import com.tielefeld.tslib.forecast.mean.MeanForecasterJava;
import com.tielefeld.tslib.forecast.ses.SESRForecaster;

public enum ForecastMethod {
	MEAN, SES;

	public IForecaster<Double> getForecaster(ITimeSeries<Double> history) {
		switch (this) {
		case SES:
			return new SESRForecaster(history);
		case MEAN:
			return new MeanForecasterJava(history);
		default:
			return new MeanForecasterJava(history);
		}
	}

	public IAnomalyCalculator<Double> getAnomalyCalculator() {
		switch (this) {
		case SES:
			return new SimpleAnomalyCalculator();
		case MEAN:
			return new SimpleAnomalyCalculator();
		default:
			return new SimpleAnomalyCalculator();
		}
	}

}
