package com.tielefeld.tslib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		com.tielefeld.tslib.TimeSeriesTest.class,
		com.tielefeld.tslib.forecast.MeanForecasterTest.class, 
		com.tielefeld.tslib.forecast.SESRForecasterTest.class, 
		com.tielefeld.tslib.TimeSeriesTest.class, 
		com.tielefeld.tslib.TSLibTest.class, 
		//avh.informatik.cau.REngineFacadeTest.class, 
		
})
public class AllTSLibTests {

}
