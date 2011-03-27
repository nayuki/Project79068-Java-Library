package p79068.math;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.util.Random;
import org.junit.Test;


public final class KaratsubaMultiplicationTest {
	
	private static Random random = new Random();
	
	
	@Test
	public void testPositivePositive() {
		BigInteger x = new BigInteger(30000, random);
		BigInteger y = new BigInteger(30000, random);
		assertEquals(x.multiply(y), KaratsubaMultiplication.multiply(x, y));
	}
	
	
	@Test
	public void testPositiveNegative() {
		BigInteger x = new BigInteger(30000, random);
		BigInteger y = new BigInteger(30000, random).negate();
		assertEquals(x.multiply(y), KaratsubaMultiplication.multiply(x, y));
	}
	
	
	@Test
	public void testNegativePositive() {
		BigInteger x = new BigInteger(30000, random).negate();
		BigInteger y = new BigInteger(30000, random);
		assertEquals(x.multiply(y), KaratsubaMultiplication.multiply(x, y));
	}
	
	
	@Test
	public void testNegativeNegative() {
		BigInteger x = new BigInteger(30000, random).negate();
		BigInteger y = new BigInteger(30000, random).negate();
		assertEquals(x.multiply(y), KaratsubaMultiplication.multiply(x, y));
	}
	
}