package com.tielefeld.rbridge;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.math.R.Rsession;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REXPVector;

import com.tielefeld.tslib.ITimeSeriesPoint;

public class RBridgeControl {

	private static final Log LOG = LogFactory.getLog(RBridgeControl.class);

	Rsession rCon;

	// TODO make a better singleton, later
	public static RBridgeControl INSTANCE = null;

	private RBridgeControl() {

		// disable all the output
		PrintStream nullPrintStream = new PrintStream(new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
			}
		});

		// this.rCon = Rsession.newLocalInstance(System.out, null);
		this.rCon = Rsession.newLocalInstance(nullPrintStream, null);

	}

	public static RBridgeControl getInstance() {
		if (null == INSTANCE) {

			INSTANCE = new RBridgeControl();
			INSTANCE.e("OPAD_CONTEXT <<- TRUE");
			// TODO: test if this is needed every time
			// TODO outsource this into a packaged text file, declare the
			// functions at runtime
			// TODO use REngine rather? RServe is not needed any more
			
			INSTANCE.e("source('"+new File("opad4lsss_r/opad_functions.r").getAbsoluteFile()+"')");
			INSTANCE.e("library('logging')");
			INSTANCE.e("initOPADfunctions()");
		}

		return INSTANCE;
	}

	/**
	 * wraps the execution of an arbitrary R expression. Logs result and error
	 * 
	 * @param input
	 * @return
	 */
	public Object e(String input) {
		Object out = null;
		try {
			out = this.rCon.eval(input);
			 LOG.info("> REXP: " + input + " return: " + out);
		} catch (Exception exc) {
			LOG.error("Error R expr.: " + input + " Cause: " + exc);
			exc.printStackTrace();
		}
		return out;
	}

	public double eDbl(String input) {
		try {
			// TODO make it error save
			return ((REXPDouble) e(input)).asDouble();
		} catch (Exception exc) {
			LOG.error("Error casting value from R: " + input + " Cause: " + exc);
			return -666.666;
		}
	}

	public String eString(String input) {
		try {
			// TODO make it error save
			REXPString str = (REXPString) e(input);
			return str.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public double[] eDblArr(String input) {
		try {
			// TODO make it error save
			REXPVector res = (REXPVector) e(input);
			return res.asDoubles();
		} catch (Exception e) {
			return new double[0];
		}
	}
	
	public void assign(String variable, double[] values) {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append(variable + " <<- c(");
			boolean first = true;
			for (double item : values) {
				if (!first) {
					buf.append(",");
				} else {
					first = false;
				}
				buf.append(item);
			}
			buf.append(")");
			this.e(buf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// TODO DRY violated!
	public void assign(String variable, Double[] values) {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append(variable + " <<- c(");
			boolean first = true;
			for (Double item : values) {
				if (!first) {
					buf.append(",");
				} else {
					first = false;
				}
				if (null == item || item.isNaN()) {
					buf.append("NA");
				} else {
					buf.append(item);
				}
			}
			buf.append(")");
			this.e(buf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

	

}




