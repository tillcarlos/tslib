package com.tielefeld.tslib;

import com.tielefeld.tslib.anomalycalculators.IAnomalyCalculator;
import com.tielefeld.tslib.anomalycalculators.SimpleAnomalyCalculator;
import com.tielefeld.tslib.forecast.IForecaster;
import com.tielefeld.tslib.forecast.arima.ARIMA101Forecaster;
import com.tielefeld.tslib.forecast.ets.ETSForecaster;
import com.tielefeld.tslib.forecast.mean.MeanForecasterJava;
import com.tielefeld.tslib.forecast.ses.SESRForecaster;
import com.tielefeld.tslib.forecast.windowstart.WindowStartForecaster;

public enum ForecastMethod {
	MEAN, SES, ETS, ARIMA101, WINDOWSTART;

	public IForecaster<Double> getForecaster(ITimeSeries<Double> history) {
		switch (this) {
		case SES:
			return new SESRForecaster(history);
		case ETS:
			return new ETSForecaster(history);
		case MEAN:
			return new MeanForecasterJava(history);
		case ARIMA101:
			return new ARIMA101Forecaster(history);
		case WINDOWSTART:
			return new WindowStartForecaster(history);
		default:
			return new MeanForecasterJava(history);
		}
	}

	public IAnomalyCalculator<Double> getAnomalyCalculator() {
		switch (this) {
		case SES:
			return new SimpleAnomalyCalculator();
		default:
			return new SimpleAnomalyCalculator();
		}
	}

}
