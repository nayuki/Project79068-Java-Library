package p79068.util.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;


public abstract class RandomTest {
	
	protected abstract Random getInstance();
	
	
	@Test
	public void testUniformIntN() {
		Random r = getInstance();
		for (int i = 1; i < 1000; i++) {
			int x = r.uniformInt(i);
			assertTrue(0 <= x && x < i);
		}
	}
	
	
	@Test
	public void testUniformIntNInvalid() {
		Random r = getInstance();
		for (int i = 0; i > -1000; i--) {
			try {
				r.uniformInt(i);
				fail();
			} catch (IllegalArgumentException e) {}
		}
	}
	
	
	@Test
	public void testUniformDouble() {
		Random r = getInstance();
		for (int i = 1; i < 1000; i++) {
			double x = r.uniformDouble();
			assertTrue(0.0 <= x && x < 1.0);
		}
	}
	
	
	@Test
	public void testUniformBytesRange() {
		Random r = getInstance();
		for (int i = 0; i < 1000; i++) {
			byte[] reference = new byte[i];
			r.uniformBytes(reference);
			
			int off = r.uniformInt(i + 1);
			int len = r.uniformInt(i - off + 1);
			byte[] refilled = reference.clone();
			r.uniformBytes(refilled, off, len);
			
			// Make sure that bytes outside of [off, off + len) are not clobbered
			for (int j = 0; j < off; j++)
				assertEquals(reference[j], refilled[j]);
			for (int j = off + len; j < i; j++)
				assertEquals(reference[j], refilled[j]);
		}
	}
	
}
