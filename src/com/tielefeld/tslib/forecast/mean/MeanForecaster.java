package com.tielefeld.tslib.forecast.mean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.TimeSeries;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.IForecastResult;

public class MeanForecaster extends AbstractForecaster<Double> {

	public MeanForecaster(ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(int numForecastSteps) {
		ITimeSeries<Double> history = this.getHistoryTimeSeries();
		
		// The starting point of the FC series is calculated by _one_ additional tick...
		long lastDistanceMillis = TimeUnit.MILLISECONDS.convert(history.getDeltaTime(), history.getDeltaTimeUnit());
		// ... plus the end point of the historic series
		Date startTime = new Date(history.getEndTime().getTime() + lastDistanceMillis);
		TimeSeries<Double> tsFC = new TimeSeries<Double>(startTime, history.getDeltaTime(), history.getDeltaTimeUnit());
		
		// For now, do the calculation in Java here
		double sum = 0.0;
		for (ITimeSeriesPoint<Double> point : history.getPoints()) {
			sum += point.getValue();
		}
		double mean = sum / history.size();
		for (int i = 0; i < numForecastSteps; i++) {
			tsFC.append(mean);
		}
		
		return new MeanForecastResult(tsFC);
	}


}
