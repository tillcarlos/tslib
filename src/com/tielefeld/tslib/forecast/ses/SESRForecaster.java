package com.tielefeld.tslib.forecast.ses;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;

import com.tielefeld.rbridge.RBridgeControl;
import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.ForecastResult;
import com.tielefeld.tslib.forecast.IForecastResult;

public class SESRForecaster extends AbstractForecaster<Double> {

	public SESRForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(final int n) {
		final ITimeSeries<Double> history = this.getTsOriginal();
		final ITimeSeries<Double> tsFC = super.prepareForecastTS();
		
		final double[] values = ArrayUtils.toPrimitive(history.getValues().toArray(new Double[]{}));
				
		final RBridgeControl rBridge = RBridgeControl.getInstance(new File("."));
		rBridge.e("initTS()");
		rBridge.assign("ts_history", values);
		final double[] pred = rBridge.eDblArr("getForecast(ts_history)");

		tsFC.append(pred[0]);
		return new ForecastResult<Double>(tsFC, this.getTsOriginal());
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