package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Random;
import org.junit.Test;


public final class BigIntegerMathTest {
	
	private static Random random = new Random();
	
	
	@Test
	public void testMultiplyRandomly() {
		for (int i = 0; i < 100; i++) {
			int size = random.nextInt(30000);
			BigInteger x = new BigInteger(size, random);
			BigInteger y = new BigInteger(size, random);
			if (random.nextBoolean())
				x = x.negate();
			if (random.nextBoolean())
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
	public void testSqrtRandom() {
		for (int i = 0; i < 1000; i++) {
			BigInteger x = new BigInteger(random.nextInt(1000) + 1, random);
			if (random.nextDouble() < 0.1) {  // Test positive
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
