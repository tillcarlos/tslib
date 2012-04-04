package com.tielefeld.tslib.forecast;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.TimeSeries;
import com.tielefeld.tslib.forecast.windowstart.WindowStartForecaster;

public class WindowStartForecasterTest extends TestCase {

	private static final Log LOG = LogFactory.getLog(WindowStartForecasterTest.class);

	public void testWindowOfADay(){
		
		// Time: 2011-12-19 10:05:00 Unix: 1324285500000
		// final long startTime = 1324285500000L;
		int windowLength = 24;
		double valueStep = 1.01;
		final long delta = 1000 * 60 * 60;
		final long startTime = 0;
		final long endTime = startTime + windowLength * delta;
		
		final TimeSeries<Double> ts =
				new TimeSeries<Double>(new Date(startTime), 1, TimeUnit.HOURS);
		LOG.info("TS so far: " + ts);
		assertEquals(new Date(startTime), ts.getStartTime());
		
		Double nextValue = 0.0;
		for (long insertTime = startTime; insertTime < endTime; insertTime += delta) {
			ts.append(nextValue);
			nextValue += valueStep;
			LOG.info("Inserting point: " + new Date(insertTime));
		}
		LOG.info("TS so far: " + ts);
		
		final WindowStartForecaster forecaster = new WindowStartForecaster(ts);
		final IForecastResult<Double> forecast = forecaster.forecast(3);
		
		final ITimeSeries<Double> forecastSeries = forecast.getForecast();
		assertEquals(3, forecastSeries.getPoints().size());
		assertEquals(new Date(endTime), ts.getEndTime());
		assertEquals(new Date(endTime), forecastSeries.getStartTime());
		assertEquals(new Date(endTime + delta), forecastSeries.getPoints().get(0).getTime());
		assertEquals(0 * 1.01, forecastSeries.getPoints().get(0).getValue());
		assertEquals(1 * 1.01, forecastSeries.getPoints().get(1).getValue());
		assertEquals(2 * 1.01, forecastSeries.getPoints().get(2).getValue());

		
	}
	
}





