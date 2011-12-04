package com.tielefeld.tslib.forecast;

import com.tielefeld.tslib.ITimeSeries;

public interface IForecastResult<T> {
	public ITimeSeries<T> getForecastTimeSeries();
}
