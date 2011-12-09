package com.tielefeld.tslib.forecast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.TimeSeries;
import com.tielefeld.tslib.forecast.ets.ETSForecaster;

public class ETSForecasterTest extends TestCase {
	final long startTime = 98890787;
	final long deltaTimeMillis = 1000;
	final int confidenceLevel = 90;
	final int steps = 1;

	public void testETSPredictor(){
		final Double[] values = { 1.0, 2.0, 3.0, 4.0};
		final List<Double> expectedValues = new ArrayList<Double>(values.length);
		for (final Double curVal : values) {
			expectedValues.add(curVal);
		}

		final TimeSeries<Double> ts =
				new TimeSeries<Double>(new Date(this.startTime), this.deltaTimeMillis, TimeUnit.MILLISECONDS);
		ts.appendAll(values);
		
		final ETSForecaster forecaster = new ETSForecaster(ts, this.confidenceLevel);
		final IForecastResult<Double> forecast = forecaster.forecast(this.steps);
		
		final ITimeSeries<Double> forecastSeries = forecast.getForecast();
		final double expectedForecast = 4.0;
		this.assertEqualsWithTolerance("Unexpected forecast value", expectedForecast, 0.1, forecastSeries.getPoints().get(0).getValue());
		
		final ITimeSeries<Double> upperSeries = forecast.getUpper();
		final double expectedUpper = 5.424480;
		this.assertEqualsWithTolerance("Unexpected upper value", expectedUpper, 0.1, upperSeries.getPoints().get(0).getValue());
		
		final ITimeSeries<Double> lowerSeries = forecast.getLower();
		final double expectedLower =  2.57531997;
		this.assertEqualsWithTolerance("Unexpected lower value", expectedLower, 0.1, lowerSeries.getPoints().get(0).getValue());
	}
	
	private void assertEqualsWithTolerance (final String message, final double expected, final double tolerance, final double actual) {
		if (!(expected-tolerance <= actual) || !(actual <= expected+tolerance )) {
			Assert.fail(String.format(message + ". Expected value %s with tolerance %s; found %s", expected, tolerance, actual));
		}
	}
}
