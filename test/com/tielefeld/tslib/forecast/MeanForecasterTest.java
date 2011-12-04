package com.tielefeld.tslib.forecast;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.TimeSeries;
import com.tielefeld.tslib.forecast.mean.MeanForecaster;

public class MeanForecasterTest {
	private TimeSeries<Double> ts;
	private TimeUnit timeUnit;
	private Date startTime;
	private int steps;
	private MeanForecaster forecaster;
	private IForecastResult<Double> forecast;
	private ITimeSeries<Double> forecastSeries;
	private long deltaTime;
	private Double mean;

	@Before
	public void setUp() throws Exception {
		deltaTime = 1000;
		timeUnit = TimeUnit.MILLISECONDS;
		startTime = new Date(System.currentTimeMillis() - deltaTime * 10);

		initForecastWithTimeUnit(TimeUnit.MILLISECONDS);
	}

	/**
	 * @param timeUnit
	 * 
	 */
	private void initForecastWithTimeUnit(TimeUnit timeUnit) {
		ts = new TimeSeries<Double>(startTime, deltaTime, timeUnit);

		steps = 1;
		mean = new Double(2.0);
		ts.append(mean - 1);
		ts.append(mean);
		ts.append(mean + 1);
		forecaster = new MeanForecaster(ts);
		forecast = forecaster.forecast(steps);
		forecastSeries = forecast.getForecastTimeSeries();
	}

	@Test
	public void testForecastStartingIsAccordingToLastAppend() {
		assertEquals(ts, forecaster.getHistoryTimeSeries());

		// we added three timepoints, so we must be here:
		long expectedStartTime = startTime.getTime() + deltaTime * 4;
		assertEquals(new Date(expectedStartTime), forecastSeries.getStartTime());
	}

	/**
	 * Compute the starting point with a different time unit
	 */
	@Test
	public void testForecastStartingIsAccordingToLastAppendDayTU() {
		initForecastWithTimeUnit(TimeUnit.DAYS);

		long expectedStartTime = startTime.getTime()
				+ TimeUnit.MILLISECONDS.convert(deltaTime * 4, TimeUnit.DAYS);
		assertEquals(new Date(expectedStartTime), forecastSeries.getStartTime());
	}

	@Test
	public void testMeanCalculationOneStep() {

		assertEquals(steps, forecastSeries.size());

		ITimeSeriesPoint<Double> stepFC = forecast.getForecastTimeSeries()
				.getPoints().get(0);
		assertEquals(mean, stepFC.getValue());
	}

}
