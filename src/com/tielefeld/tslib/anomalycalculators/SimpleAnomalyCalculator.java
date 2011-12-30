package com.tielefeld.tslib.anomalycalculators;

import com.tielefeld.tslib.ITimeSeriesPoint;
import com.tielefeld.tslib.forecast.IForecastResult;

public class SimpleAnomalyCalculator implements IAnomalyCalculator<Double> {


	@Override
	public AnomalyScore calculateAnomaly(IForecastResult<Double> forecast,
			ITimeSeriesPoint<Double> current) {
		// TODO Auto-generated method stub
		if (forecast.getForecast().getPoints().size() == 0)
			return null;
		
		Double nextpredicted = forecast.getForecast().getPoints().get(0).getValue();
		double measuredValue = 0.0;
		
		// TODO how to do the fancy generic cast / check?
		if (current.getValue() instanceof Double) {
			measuredValue = (Double) current.getValue();
		}
		
		double difference = nextpredicted - measuredValue;
		double sum = nextpredicted + measuredValue;
		
		difference = Math.abs( difference / sum );  
		
		return new AnomalyScore(difference);
	}
}
