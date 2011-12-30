package com.tielefeld.tslib.forecast.mean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.stat.StatUtils;

import com.tielefeld.tslib.ITimeSeries;
import com.tielefeld.tslib.forecast.AbstractForecaster;
import com.tielefeld.tslib.forecast.ForecastResult;
import com.tielefeld.tslib.forecast.IForecastResult;

/**
 * A Java-based time series forecaster which computes a forecast based on the
 * mean value of the historic values.
 * 
 * @author Andre van Hoorn
 * 
 */
public class MeanForecasterJava extends AbstractForecaster<Double> {

	public MeanForecasterJava(final ITimeSeries<Double> historyTimeseries) {
		super(historyTimeseries);
	}

	@Override
	public IForecastResult<Double> forecast(final int numForecastSteps) {
		final ITimeSeries<Double> history = this.getTsOriginal();
		final ITimeSeries<Double> tsFC = this.prepareForecastTS();
		
		List<Double> allHistory = new ArrayList<Double>(history.getValues());
		Double[] histValuesNotNull = MeanForecasterJava.removeNullValues(allHistory); 
		final double mean = StatUtils.mean(ArrayUtils.toPrimitive(histValuesNotNull));
		
		final Double[] forecastValues = new Double[numForecastSteps];
			Arrays.fill(forecastValues, mean);
		
		tsFC.appendAll(forecastValues); 

		// TODO: computer confidence interval and set this value along with upper and lower time series
		
		return new ForecastResult<Double>(tsFC, this.getTsOriginal());
	}

	public static Double[] removeNullValues(List<Double> allHistory) {
		List<Double> newList = new ArrayList<Double>();
		for (Object obj : allHistory) {
			if (null != obj && obj instanceof Double)
				newList.add((Double)obj);
		}
		return newList.toArray(new Double[] {});
	}
}
