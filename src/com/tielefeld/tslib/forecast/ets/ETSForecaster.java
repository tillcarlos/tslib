package com.tielefeld.tslib.forecast.ets;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractRForecaster;

/**
 * An R-based time series forecaster which computes a forecast based on exponential smoothing.
 * 
 * @author Andre van Hoorn
 * 
 */
public class ETSForecaster extends AbstractRForecaster {
	private final static String MODEL_FUNC_NAME = "ets"; // no explicit stochastic model
	private final static String FORECAST_FUNC_NAME = "forecast.ets";
	
	public ETSForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries, ETSForecaster.MODEL_FUNC_NAME, ETSForecaster.FORECAST_FUNC_NAME);
	}

	public ETSForecaster(final ITimeSeries<Double> historyTimeseries, final int confidenceLevel) {
		super(historyTimeseries, ETSForecaster.MODEL_FUNC_NAME, ETSForecaster.FORECAST_FUNC_NAME, confidenceLevel);
	}
	
	@Override
	protected String[] getModelFuncParams() {
		return null; // no additional params required by this predictor
	}

	@Override
	protected String[] getForecastFuncParams() {
		return null; // no additional params required by this predictor
	}
}
