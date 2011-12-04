package com.tielefeld.tslib;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TimeSeriesTest {

	private TimeSeries<Double> unboundTS;
	private int bounds;
	private TimeSeries<Integer> boundedTS;

	@Before
	public void setUp() throws Exception {
		long deltaTime = 1000;
		Date startTime = new Date(System.currentTimeMillis() - deltaTime * 10);
		unboundTS = new TimeSeries<Double>(startTime, deltaTime, TimeUnit.MILLISECONDS);
		
		bounds = 3;
		boundedTS = new TimeSeries<Integer>(startTime, deltaTime, TimeUnit.MILLISECONDS, this.bounds);
	}

	@Test
	public void testAppendingValues() {
		assertEquals(0, unboundTS.size());
		unboundTS.append(666.0);
		unboundTS.append(666.0);
		assertEquals(2, unboundTS.size());
	}
	
	@Test
	public void testCapacityRestriction() {
		assertEquals(0, boundedTS.size());
		for (int i = 0; i < bounds + 1; i++) {
			boundedTS.append(10 * i);
		}
		assertEquals(bounds, boundedTS.size());
	}
	
	@Test
	public void testKeepNewerValuesInCapacity() {
		assertEquals(0, boundedTS.size());
		int i;
		for (i = 0; i < bounds*2; i++) {
			boundedTS.append(i);
		}
		assertEquals(new Integer(i), boundedTS.getPoints().get(bounds).getValue());
	}

}
