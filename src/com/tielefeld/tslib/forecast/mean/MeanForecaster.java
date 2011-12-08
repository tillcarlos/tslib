package com.tielefeld.tslib.forecast.mean;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.IForecastResult;

public class MeanForecaster extends AbstractForecaster<Double> {

	public MeanForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(final int numForecastSteps) {
		final ITimeSeries<Double> history = this.getHistoryTimeSeries();
		final ITimeSeries<Double> tsFC = this.prepareForecastTS();
		
		
		// For now, do the calculation in Java here
		double sum = 0.0;
		for (final ITimeSeriesPoint<Double> point : history.getPoints()) {
			sum += point.getValue();
		}
		final double mean = sum / history.size();
		
		
		
		for (int i = 0; i < numForecastSteps; i++) {
			tsFC.append(mean);
		} 
		
		return new MeanForecastResult(tsFC);
	}


}
