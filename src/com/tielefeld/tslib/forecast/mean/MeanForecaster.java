package com.tielefeld.tslib.forecast.mean;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractRForecaster;

/**
 * An R-based time series forecaster which computes a forecast based on the mean value of the historic values.
 * 
 * @author Andre van Hoorn
 * 
 */
public class MeanForecaster extends AbstractRForecaster {
	private final static String MODEL_FUNC_NAME = null; // no explicit stochastic model
	private final static String FORECAST_FUNC_NAME = "meanf";
	
	public MeanForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries, MeanForecaster.MODEL_FUNC_NAME, MeanForecaster.FORECAST_FUNC_NAME);
	}

	public MeanForecaster(final ITimeSeries<Double> historyTimeseries, final int confidenceLevel) {
		super(historyTimeseries, MeanForecaster.MODEL_FUNC_NAME, MeanForecaster.FORECAST_FUNC_NAME, confidenceLevel);
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
