package com.tielefeld.tslib.forecast.arima;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractRForecaster;

/**
 * An R-based time series forecaster which computes a forecast based on exponential smoothing.
 * 
 * @author Andre van Hoorn
 * 
 */
public class ARIMA101Forecaster extends AbstractRForecaster {
	private final static String MODEL_FUNC_NAME = "arima"; // no explicit stochastic model
	private final static String FORECAST_FUNC_NAME = "predict";
	
	public ARIMA101Forecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries, ARIMA101Forecaster.MODEL_FUNC_NAME, ARIMA101Forecaster.FORECAST_FUNC_NAME);
	}

	public ARIMA101Forecaster(final ITimeSeries<Double> historyTimeseries, final int confidenceLevel) {
		super(historyTimeseries, ARIMA101Forecaster.MODEL_FUNC_NAME, ARIMA101Forecaster.FORECAST_FUNC_NAME, confidenceLevel);
	}
	
	@Override
	protected String[] getModelFuncParams() {
		return new String[]{"c(1,0,1)", "method=\"CSS-ML\""};
	}

	@Override
	protected String[] getForecastFuncParams() {
		return null; // no additional params required by this predictor
	}
	
	@Override
	protected String forecastOperationOnResult(final String varNameForecast) {
		return String.format("%s$pred", varNameForecast);
	}
	

	/**
	 * @param varNameForecast
	 * @return
	 */
	protected String lowerOperationOnResult(final String varNameForecast) {
		return String.format("(%s$pred - %s$se)", varNameForecast, varNameForecast);
	}
	
	/**
	 * @param varNameForecast
	 * @return
	 */
	protected String upperOperationOnResult(final String varNameForecast) {
		return String.format("(%s$pred + %s$se)", varNameForecast, varNameForecast);
	}
	
}
