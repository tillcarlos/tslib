package avh.informatik.cau;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rosuda.JRI.REXP;

public class REngineFacadeTest {

	private REngineFacade r;

	@Before
	public void setUp() throws Exception {

		System.out
				.println("Property 'java.library.path' must include 'lib' path");
		System.out.println(System.getProperty("java.library.path"));

		r = REngineFacade.getInstance();

	}

	@Test
	public void testSimpleEval() {

		r.assign("values", new double[] { 0.1, 0.2, 0.3 });
		r.rEvalAsync("sum(values)", new IREvalCallbackHandler() {

			@Override
			public void newREXP(REXP rExp) {
				System.out.println(" " + rExp);
				assertEquals(0.6, rExp.getContent());
			}
		});

	}

	public static void main(String[] args) {
		try {
			new REngineFacadeTest().setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
