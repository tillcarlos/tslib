package com.tielefeld.tslib.forecast.ses;

import com.tielefeld.rbridge.RBridgeControl;
import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.IForecastResult;

public class SESRForecaster extends AbstractForecaster<Double> {

	public SESRForecaster(ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(int n) {
		ITimeSeries<Double> history = this.getHistoryTimeSeries();
		ITimeSeries<Double> tsFC = super.prepareForecastTS();
		

		double[] values = new double[history.size()];
		int i = 0;
		for(ITimeSeriesPoint<Double> point : history.getPoints()){
			values[i] = point.getValue();
			i++;
		}
		
		
		RBridgeControl rBridge = RBridgeControl.getInstance();
		rBridge.e("initTS()");
		rBridge.assign("ts_history", values);
		double[] pred = rBridge.eDblArr("getForecast(ts_history)");

		tsFC.append(pred[0]);
		return new SESForecastResult(tsFC);
	}



}


/*
This was the intention to do it (with avh's code)

r.loadLibrary("tseries");
r.loadLibrary("stats");

r.assign("points", values);

try {
	Thread.sleep(1300);
} catch (InterruptedException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

try {
//	REXP hwResult = r.rEvalSync("ses <- HoltWinters(points, beta = FALSE, gamma = FALSE)");
	REXP hwResult = r.rEvalSync("HoltWinters");
	r.assign("ses", hwResult);
	REXP result = r.rEvalSync("predict(ses, " + n + ", prediction.interval = TRUE)");
} catch (REngineFacadeEvalException e) {
	e.printStackTrace();
}

*/