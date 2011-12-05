package com.tielefeld.tslib.forecast.ses;

import org.rosuda.JRI.REXP;

import avh.informatik.cau.REngineFacade;
import avh.informatik.cau.REngineFacadeEvalException;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.IForecastResult;
import com.tielefeld.tslib.forecast.mean.MeanForecastResult;

public class SESRForecaster extends AbstractForecaster<Double> {

	public SESRForecaster(ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(int n) {
		ITimeSeries<Double> history = this.getHistoryTimeSeries();
		ITimeSeries<Double> tsFC = super.prepareForecastTS();
		

		REngineFacade r = REngineFacade.getInstance();
		double[] values = new double[history.size()];
		int i = 0;
		for(ITimeSeriesPoint<Double> point : history.getPoints()){
			values[0] = point.getValue();
		}
		r.assign("values", values);
		try {
			r.rEvalSync("ses <- HoltWinters(times, beta = FALSE, gamma = FALSE");
			REXP result = r.rEvalSync("predict(ses, " + n + ", prediction.interval = TRUE)");
		} catch (REngineFacadeEvalException e) {
			e.printStackTrace();
		}

		
		// For now, do the calculation in Java here
		double sum = 0.0;
		for (ITimeSeriesPoint<Double> point : history.getPoints()) {
			sum += point.getValue();
		}
		
		return new SESForecastResult(tsFC);
	}



}
