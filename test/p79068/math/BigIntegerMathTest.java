package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

import p79068.util.random.JavaRandomAdapter;
import p79068.util.random.Random;


public final class BigIntegerMathTest {
	
	private static Random RANDOM = Random.DEFAULT;
	private static java.util.Random JAVA_RANDOM = new JavaRandomAdapter(RANDOM);
	
	
	@Test
	public void testMultiplyRandomly() {
		for (int i = 0; i < 100; i++) {
			int size = RANDOM.uniformInt(30000);
			BigInteger x = new BigInteger(size, JAVA_RANDOM);
			BigInteger y = new BigInteger(size, JAVA_RANDOM);
			if (RANDOM.uniformInt(2) == 1)
				x = x.negate();
			if (RANDOM.uniformInt(2) == 1)
				y = y.negate();
			assertEquals(x.multiply(y), BigIntegerMath.multiply(x, y));
		}
	}
	
	
	@Test
	public void testSqrtBasic() {
		assertEquals(BigInteger.valueOf(0), BigIntegerMath.sqrt(BigInteger.valueOf( 0)));
		assertEquals(BigInteger.valueOf(1), BigIntegerMath.sqrt(BigInteger.valueOf( 1)));
		assertEquals(BigInteger.valueOf(1), BigIntegerMath.sqrt(BigInteger.valueOf( 2)));
		assertEquals(BigInteger.valueOf(1), BigIntegerMath.sqrt(BigInteger.valueOf( 3)));
		assertEquals(BigInteger.valueOf(2), BigIntegerMath.sqrt(BigInteger.valueOf( 4)));
		assertEquals(BigInteger.valueOf(2), BigIntegerMath.sqrt(BigInteger.valueOf( 5)));
		assertEquals(BigInteger.valueOf(2), BigIntegerMath.sqrt(BigInteger.valueOf( 8)));
		assertEquals(BigInteger.valueOf(3), BigIntegerMath.sqrt(BigInteger.valueOf( 9)));
		assertEquals(BigInteger.valueOf(3), BigIntegerMath.sqrt(BigInteger.valueOf(10)));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testSqrtNegative() {
		BigIntegerMath.sqrt(BigInteger.valueOf(-1));
	}
	
	
	@Test
	public void testSqrtRandomly() {
		for (int i = 0; i < 1000; i++) {
			BigInteger x = new BigInteger(RANDOM.uniformInt(1000) + 1, JAVA_RANDOM);
			if (RANDOM.uniformDouble() < 0.1 || x.signum() == 0) {  // Test non-negative
				BigInteger y = BigIntegerMath.sqrt(x);
				assertTrue(y.signum() >= 0);
				assertTrue(y.multiply(y).compareTo(x) <= 0);
				y = y.add(BigInteger.ONE);
				assertTrue(y.multiply(y).compareTo(x) > 0);
				
			} else {  // Test negative
				try {
					BigIntegerMath.sqrt(x.negate());
					fail();
				} catch (IllegalArgumentException e) {}
			}
		}
	}
	
}
