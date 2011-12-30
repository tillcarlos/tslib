package com.tielefeld.tslib.forecast.ses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.StatUtils;

import com.tielefeld.rbridge.RBridgeControl;
import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.ForecastResult;
import com.tielefeld.tslib.forecast.IForecastResult;
import com.tielefeld.tslib.forecast.mean.MeanForecasterJava;

public class SESRForecaster extends AbstractForecaster<Double> {

	public SESRForecaster(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(final int n) {
		final ITimeSeries<Double> history = this.getTsOriginal();
		final ITimeSeries<Double> tsFC = super.prepareForecastTS();
		
		List<Double> allHistory = new ArrayList<Double>(history.getValues());
		Double[] histValuesNotNull = MeanForecasterJava.removeNullValues(allHistory); 
		final double[] values = ArrayUtils.toPrimitive(histValuesNotNull);
				
		final RBridgeControl rBridge = RBridgeControl.getInstance(new File("r_scripts"));
		rBridge.e("initTS()");
		rBridge.assign("ts_history", values);
		final double[] pred = rBridge.eDblArr("getForecast(ts_history)");

		if (pred.length > 0)
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