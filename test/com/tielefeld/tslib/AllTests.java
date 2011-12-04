package com.tielefeld.tslib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TimeSeriesTest.class,
		com.tielefeld.tslib.forecast.MeanForecasterTest.class, TSLibTest.class })
public class AllTests {

}
