package com.tielefeld.rbridge;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.math.R.Rsession;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REXPVector;

public class RBridgeControl {

	private static final Log LOG = LogFactory.getLog(RBridgeControl.class);

	Rsession rCon;

	// TODO make a better singleton, later
	public static RBridgeControl INSTANCE = null;

	private RBridgeControl() {

		// disable all the output
		final PrintStream nullPrintStream = new PrintStream(new OutputStream() {

			@Override
			public void write(final int arg0) throws IOException {
			}
		});

		// this.rCon = Rsession.newLocalInstance(System.out, null);
		this.rCon = Rsession.newLocalInstance(nullPrintStream, null);

	}

	public static RBridgeControl getInstance(File root) {
		if (null == RBridgeControl.INSTANCE) {

			RBridgeControl.INSTANCE = new RBridgeControl();
			RBridgeControl.INSTANCE.e("OPAD_CONTEXT <<- TRUE");
			// TODO: test if this is needed every time
			// TODO outsource this into a packaged text file, declare the
			// functions at runtime
			// TODO use REngine rather? RServe is not needed any more
			
			INSTANCE.e("source('"+new File(root, "r_scripts/opad_functions.r").getAbsoluteFile()+"')");
			INSTANCE.e("library('logging')");
			INSTANCE.e("initOPADfunctions()");
		}

		return RBridgeControl.INSTANCE;
	}

	/**
	 * wraps the execution of an arbitrary R expression. Logs result and error
	 * 
	 * @param input
	 * @return
	 */
	public Object e(final String input) {
		Object out = null;
		try {
			out = this.rCon.eval(input);
			 RBridgeControl.LOG.info("> REXP: " + input + " return: " + out);
		} catch (final Exception exc) {
			RBridgeControl.LOG.error("Error R expr.: " + input + " Cause: " + exc);
			exc.printStackTrace();
		}
		return out;
	}

	public double eDbl(final String input) {
		try {
			// TODO make it error save
			return ((REXPDouble) this.e(input)).asDouble();
		} catch (final Exception exc) {
			RBridgeControl.LOG.error("Error casting value from R: " + input + " Cause: " + exc);
			return -666.666;
		}
	}

	public String eString(final String input) {
		try {
			// TODO make it error save
			final REXPString str = (REXPString) this.e(input);
			return str.toString();
		} catch (final Exception e) {
			return "";
		}
	}

	public double[] eDblArr(final String input) {
		try {
			// TODO make it error save
			final REXPVector res = (REXPVector) this.e(input);
			return res.asDoubles();
		} catch (final Exception e) {
			return new double[0];
		}
	}
	
	public void assign(final String variable, final double[] values) {
		try {
			final StringBuffer buf = new StringBuffer();
			buf.append(variable + " <<- c(");
			boolean first = true;
			for (final double item : values) {
				if (!first) {
					buf.append(",");
				} else {
					first = false;
				}
				buf.append(item);
			}
			buf.append(")");
			this.e(buf.toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
	}

	// TODO DRY violated!
	public void assign(final String variable, final Double[] values) {
		try {
			final StringBuffer buf = new StringBuffer();
			buf.append(variable + " <<- c(");
			boolean first = true;
			for (final Double item : values) {
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
		} catch (final Exception e) {
			e.printStackTrace();
		}
				
	}
	
    private final static AtomicInteger nextVarId = new AtomicInteger(1);
    
    /**
     * Returns a globally unique variable name. 
     * 
     * @param prefix may be null
     * @return
     */
    public static String uniqueVarname () {
    	return String.format("var_%s", RBridgeControl.nextVarId.getAndIncrement());
    }
}




