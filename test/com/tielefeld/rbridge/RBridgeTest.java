package com.tielefeld.rbridge;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import avh.informatik.cau.REngineFacadeTest;

public class RBridgeTest {
	private static final Log LOG = LogFactory.getLog(RBridgeTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		RBridgeControl r = RBridgeControl.getInstance();

		r.e("measures <<- c(NA,NA,NA,NA,NA,NA,NA,NA,NA,NA,31.0,41.0,95.0,77.0,29.0,62.0,49.0,NA)");
		r.e("forecasts <<- c(NA,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,2.8181818181818183,6.0,12.846153846153847,17.428571428571427,18.2,20.9375,22.58823529411765)");
		r.e("anomalies <<- c(NA,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,0.8333333333333333,0.7446808510638298,0.761768901569187,0.6308623298033282,0.2288135593220339,0.49510173323285606,0.3689400164338537,NA)");
		r.e("combined <<- cbind(measures, forecasts, anomalies)");
		Object result = r
				.e("plotAnomaly(combined, '/Users/till/Documents/repositories/uni/thesis-repos/Opad4lsssExperiments/opad4lsss_r/plots/junit_testplot.pdf', 1323437034798, 2000)");

		LOG.info(result);
		
		assertTrue(result != null);
		assertTrue(result instanceof org.rosuda.REngine.REXPInteger);
	}

}
