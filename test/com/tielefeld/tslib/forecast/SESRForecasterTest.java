package com.tielefeld.tslib.forecast;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.TimeSeries;
import com.tielefeld.tslib.forecast.ses.SESRForecaster;

public class SESRForecasterTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {
		int deltaTime = 1000;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		Date startTime = new Date(System.currentTimeMillis() - deltaTime * 10);

		TimeSeries<Double> ts = new TimeSeries<Double>(startTime, deltaTime,
				timeUnit);
		ts.append(1.0);
		ts.append(2.0);
		ts.append(3.0);

		SESRForecaster forecaster = new SESRForecaster(ts);
		IForecastResult<Double> forecast = forecaster.forecast(1);
		ITimeSeries<Double> forecastSeries = forecast.getForecastTimeSeries();

		ITimeSeriesPoint<Double> stepFC = forecastSeries.getPoints().get(0);
		assertTrue(stepFC.getValue() > 2.88 && stepFC.getValue() < 3.1);
	}

}
