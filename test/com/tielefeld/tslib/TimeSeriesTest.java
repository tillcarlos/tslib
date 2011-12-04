package com.tielefeld.tslib;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TimeSeriesTest {

	private TimeSeries<Double> unboundTS;
	private int bounds;
	private TimeSeries<Integer> boundedTS;
	private TimeUnit timeUnit;
	private Date startTime;

	@Before
	public void setUp() throws Exception {
		long deltaTime = 1000;
		timeUnit = TimeUnit.MILLISECONDS;
		startTime = new Date(System.currentTimeMillis() - deltaTime * 10);
		unboundTS = new TimeSeries<Double>(startTime, deltaTime, timeUnit);
		
		bounds = 3;
		boundedTS = new TimeSeries<Integer>(startTime, deltaTime, timeUnit, this.bounds);
	}

	@Test 
	public void testGettersAndAppendingValues() {
		assertEquals(timeUnit, unboundTS.getDeltaTimeUnit());
		assertEquals(startTime, unboundTS.getStartTime());
		
		assertEquals(0, unboundTS.size());
		unboundTS.append(666.0);
		unboundTS.append(666.0);
		assertEquals(2, unboundTS.size());
	}
	
	@Test 
	public void testValueSort() {
		int count = 30;
		for (int i = 0; i < count; i++) {
			unboundTS.append(new Double(i));
		}

		for (int i = 0; i < count; i++) {
			assertEquals(new Double(i), unboundTS.getPoints().get(i).getValue());
		}

		assertEquals(count, unboundTS.size());
	}
	
	@Test
	public void testCapacityRestriction() {
		assertEquals(0, boundedTS.size());
		assertEquals(bounds, boundedTS.getCapacity());
		for (int i = 0; i < bounds + 1; i++) {
			boundedTS.append(10 * i);
		}
		assertEquals(bounds, boundedTS.size());
	}
	
	@Test
	public void testKeepNewerValuesInCapacity() {
		assertEquals(0, boundedTS.size());
		int i;
		int lastNumber = bounds*2;
		for (i = 0; i <= lastNumber; i++) {
			boundedTS.append(i);
		}
		assertEquals(new Integer(lastNumber), boundedTS.getPoints().get(bounds-1).getValue());
	}

}
