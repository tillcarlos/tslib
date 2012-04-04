package com.tielefeld.tslib.forecast.windowstart;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.ForecastResult;
import com.tielefeld.tslib.forecast.IForecastResult;

/**
 * This forecaster uses the start of its timeseries window.
 * When defining a window length of e.g. a day, it gives the value of yesterday at the same time.
 * It can also be used for weeks, months, ... and ever other periodicity.
 * 
 * @author Tillmann Carlos Bielefeld
 * 
 */
public class WindowStartForecaster extends AbstractForecaster<Double> {

	public WindowStartForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(final int numForecastSteps) {
		final ITimeSeries<Double> history = this.getTsOriginal();
		final ITimeSeries<Double> tsFC = this.prepareForecastTS();
		
		for (int i = 0; i < numForecastSteps; i++) {
			ITimeSeriesPoint<Double> iTimeSeriesPoint = history.getPoints().get(i);
			if (null == iTimeSeriesPoint)
				tsFC.append(Double.NaN);
			else
				tsFC.append(iTimeSeriesPoint.getValue());
		}
		
		
		return new ForecastResult<Double>(tsFC, this.getTsOriginal());
	}

}
