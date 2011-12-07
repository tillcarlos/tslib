package com.tielefeld.tslib.anomalycalculators;

import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.IForecastResult;

public interface IAnomalyCalculator<T> {
	public AnomalyScore calculateAnomaly(IForecastResult<T> forecast,
			ITimeSeriesPoint<T> current);
}
