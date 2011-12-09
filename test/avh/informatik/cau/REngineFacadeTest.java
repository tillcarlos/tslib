package avh.informatik.cau;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rosuda.JRI.REXP;
import org.rosuda.REngine.JRI.JRIEngine;

public class REngineFacadeTest {
    private static final Log LOG = LogFactory.getLog(REngineFacadeTest.class);

	private REngineFacade r;

	@Before
	public void setUp() throws Exception {

		System.out
				.println("Does 'java.library.path' include 'lib' path? ");
		System.out.println(System.getProperty("java.library.path"));
		System.out.println("R_HOME=" + System.getenv("R_HOME"));
		System.out.println("LD_LIBRARY_PATH="+System.getenv("LD_LIBRARY_PATH"));
		System.out.println("R_INCLUDE_DIR="+System.getenv("R_INCLUDE_DIR"));
		System.out.println("R_SHARE_DIR="+System.getenv("R_SHARE_DIR"));
		System.out.println("R_DOC_DIR="+System.getenv("R_DOC_DIR"));
		System.out.println("R_LIBS_USER="+System.getenv("R_LIBS_USER"));

		this.r = REngineFacade.getInstance();

	}

	@Test
	public void testSimpleEval() {

		this.r.assign("values", new double[] { 0.1, 0.2, 0.3 });
		this.r.rEvalAsync("sum(values)", new IREvalCallbackHandler() {

			@Override
			public void newREXP(final REXP rExp) {
				System.out.println(" " + rExp);
				Assert.assertEquals(0.6, rExp.getContent());
			}
		});

	}
	
	@Test
	public void testHoltWinters() throws REngineFacadeEvalException {
		
		System.out.println("Begin HoltWinter ...");
		this.r.loadLibrary("graphics");
		System.out.println(this.r.rEvalSync("hw=HoltWinters(co2)").getContent());
		System.out.println(this.r.rEvalSync("hw$a").asDouble());
		System.out.println(this.r.rEvalSync("5").asDouble());
		System.out.println("End HoltWinter ...");
	}
	
	@Test
	public void testLoadingLibraries() {

		final REngineFacade r2 = REngineFacade.getInstance("/Library/Frameworks/R.framework/Versions/2.14/Resources/library");
		r2.loadLibrary("tseries");
		
		System.out.println(">>>>");
		this.r.rEvalAsync(".libPaths(\"/Library/Frameworks/R.framework/Versions/2.14/Resources/library\")", new IREvalCallbackHandler() {
			
			@Override
			public void newREXP(final REXP rExp) {
				System.out.println(".libPaths(..path...)) >> " + rExp);
			}
		});
		
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.r.rEvalAsync(".libPaths()", new IREvalCallbackHandler() {
			
			@Override
			public void newREXP(final REXP rExp) {
				System.out.println(".libPaths()) >> " + rExp);
			}
		});
		
		this.r.rEvalAsync("library(tseries)", new IREvalCallbackHandler() {
			
			@Override
			public void newREXP(final REXP rExp) {
				System.out.println("library(tse... >> " + rExp);
			}
		});
		
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.r.rEvalAsync("(.packages())", new IREvalCallbackHandler() {

			@Override
			public void newREXP(final REXP rExp) {
				System.out.println("(.packages()) >> " + rExp);
			}
		});

		
	}

	public static void main(final String[] args) {
		try {
			JRIEngine re;  
			try{ 
				System.out.println("==============");
			    re = new JRIEngine();
//			    org.rosuda.REngine.REXP oneToTenR = re.parseAndEval("1:10");
//			    System.out.println(oneToTenR);
//	        System.out.println(re.eval(".libPaths(\"/Library/Frameworks/R.framework/Versions/2.14/Resources/library\") "));
//	        System.out.println(re.eval("library(\"stats\", lib.loc=\""+libLocPath+"\")"));
//	        System.out.println(re.eval("library(\"stats\")"));
//	        System.out.println(re.eval(".libPaths() "));
//	        System.out.println(re.eval("HoltWinters"));
			    System.out.println("==============");
			}
			catch(final Exception e){
				e.printStackTrace();
			} 
			
	        
			final REngineFacadeTest test = new REngineFacadeTest();
			test.setUp();
			test.testSimpleEval();
//			test.testLoadingLibraries();
			
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
