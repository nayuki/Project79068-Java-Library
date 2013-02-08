package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import p79068.util.random.Random;


public final class BigFractionTest {
	
	@Test
	public void testSimplify() {
		assertEquals(BigInteger.ZERO, new BigFraction(0, 1).numerator);
		assertEquals(BigInteger.ONE, new BigFraction(0, 1).denominator);
		assertEquals(BigInteger.ZERO, new BigFraction(0, -2).numerator);
		assertEquals(BigInteger.ONE, new BigFraction(0, -2).denominator);
		assertEquals(BigInteger.ONE, new BigFraction(1, 3).numerator);
		assertEquals(BigInteger.valueOf(3), new BigFraction(1, 3).denominator);
		assertEquals(BigInteger.valueOf(-2), new BigFraction(-2, 5).numerator);
		assertEquals(BigInteger.valueOf(5), new BigFraction(-2, 5).denominator);
		assertEquals(BigInteger.valueOf(-5), new BigFraction(5, -2).numerator);
		assertEquals(BigInteger.valueOf(2), new BigFraction(5, -2).denominator);
		assertEquals(BigInteger.valueOf(3), new BigFraction(-3, -4).numerator);
		assertEquals(BigInteger.valueOf(4), new BigFraction(-3, -4).denominator);
		assertEquals(BigInteger.valueOf(3), new BigFraction(18, 30).numerator);
		assertEquals(BigInteger.valueOf(5), new BigFraction(18, 30).denominator);
		assertEquals(BigInteger.valueOf(2), new BigFraction(-10, -35).numerator);
		assertEquals(BigInteger.valueOf(7), new BigFraction(-10, -35).denominator);
	}
	
	
	@Test
	public void testEquals() {
		assertEquals(BigFraction.ZERO, new BigFraction(0, 2));
		assertEquals(BigFraction.ZERO, new BigFraction(0, -1));
		assertEquals(BigFraction.ONE, new BigFraction(5, 5));
		assertEquals(BigFraction.ONE, new BigFraction(-8, -8));
		assertEquals(new BigFraction(3, 2), new BigFraction(9, 6));
		assertEquals(new BigFraction(-5, 7), new BigFraction(5, -7));
	}
	
	
	@Test
	public void testAdd() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.add(BigFraction.ZERO));
		assertEquals(new BigFraction(2), BigFraction.ONE.add(BigFraction.ONE));
		assertEquals(new BigFraction(5, 6), new BigFraction(1, 2).add(new BigFraction(1, 3)));
		assertEquals(new BigFraction(-49, 45), new BigFraction(-19, 18).add(new BigFraction(-1, 30)));
	}
	
	
	@Test
	public void testSubtract() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.subtract(BigFraction.ZERO));
		assertEquals(new BigFraction(1), new BigFraction(2).subtract(BigFraction.ONE));
		assertEquals(new BigFraction(1, 6), new BigFraction(1, 2).subtract(new BigFraction(1, 3)));
		assertEquals(new BigFraction(-46, 45), new BigFraction(-19, 18).subtract(new BigFraction(-1, 30)));
	}
	
	
	@Test
	public void testMultiply() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.multiply(BigFraction.ONE));
		assertEquals(BigFraction.ONE, BigFraction.ONE.multiply(BigFraction.ONE));
		assertEquals(new BigFraction(15), new BigFraction(3).multiply(new BigFraction(5)));
		assertEquals(new BigFraction(35, 6), new BigFraction(5, 3).multiply(new BigFraction(7, 2)));
		assertEquals(new BigFraction(-4, 3), new BigFraction(-1, 18).multiply(new BigFraction(24, 1)));
	}
	
	
	@Test
	public void testDivide() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.divide(BigFraction.ONE));
		assertEquals(BigFraction.ONE, BigFraction.ONE.divide(BigFraction.ONE));
		assertEquals(new BigFraction(1, 3), new BigFraction(2).divide(new BigFraction(6)));
		assertEquals(new BigFraction(-10, 21), new BigFraction(5, -3).divide(new BigFraction(7, 2)));
	}
	
	
	@Test(expected=ArithmeticException.class)
	public void testDivideInvalid() {
		BigFraction.ONE.divide(BigFraction.ZERO);
	}
	
	
	@Test
	public void testNegate() {
		assertEquals(BigFraction.ZERO, BigFraction.ZERO.negate());
		assertEquals(new BigFraction(-1), BigFraction.ONE.negate());
		assertEquals(new BigFraction(2), new BigFraction(-2).negate());
		assertEquals(new BigFraction(-3, 5), new BigFraction(3, 5).negate());
	}
	
	
	@Test
	public void testReciprocal() {
		assertEquals(BigFraction.ONE, BigFraction.ONE.reciprocal());
		assertEquals(new BigFraction(1, 2), new BigFraction(2).reciprocal());
		assertEquals(new BigFraction(7, 3), new BigFraction(3, 7).reciprocal());
		assertEquals(new BigFraction(-5, 4), new BigFraction(-4, 5).reciprocal());
	}
	
	
	@Test(expected=ArithmeticException.class)
	public void testReciprocalInvalid() {
		BigFraction.ZERO.reciprocal();
	}
	
	
	@Test
	public void testCompareTo() {
		assertTrue(new BigFraction(0).compareTo(new BigFraction(0)) == 0);
		assertTrue(new BigFraction(1, 2).compareTo(new BigFraction(1, 2)) == 0);
		assertTrue(new BigFraction(4, 4).compareTo(new BigFraction(1, 1)) == 0);
		assertTrue(new BigFraction(2, 3).compareTo(new BigFraction(3, 2)) < 0);
		assertTrue(new BigFraction(5, 2).compareTo(new BigFraction(1, 5)) > 0);
		assertTrue(new BigFraction(-1, 2).compareTo(new BigFraction(1, 2)) < 0);
		assertTrue(new BigFraction(-4, 3).compareTo(new BigFraction(1, 2)) < 0);
		assertTrue(new BigFraction(-2, 3).compareTo(new BigFraction(-7, 5)) > 0);
	}
	
	
	@Test
	public void testFloatValue() {
		assertEquals(0.0f, new BigFraction(0, 1).floatValue(), 0);
		assertEquals(-5.0f, new BigFraction(-5, 1).floatValue(), 0);
		
		assertEquals(1.0f, new BigFraction(1, 1).floatValue(), 0);
		assertEquals(2.0f, new BigFraction(2, 1).floatValue(), 0);
		assertEquals(3.0f, new BigFraction(3, 1).floatValue(), 0);
		assertEquals(4.0f, new BigFraction(4, 1).floatValue(), 0);
		assertEquals(5.0f, new BigFraction(5, 1).floatValue(), 0);
		assertEquals(7.0f, new BigFraction(7, 1).floatValue(), 0);
		
		assertEquals(0.5f, new BigFraction(1, 2).floatValue(), 0);
		assertEquals(0.25f, new BigFraction(1, 4).floatValue(), 0);
		assertEquals(0.75f, new BigFraction(3, 4).floatValue(), 0);
		
		assertEquals(Float.intBitsToFloat(0x3DCCCCCD), new BigFraction(1, 10).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0xBF333333), new BigFraction(-7, 10).floatValue(), 0);
	}
	
	
	@Test
	public void testFloatValueTiny() {
		// Normal
		assertEquals(Float.intBitsToFloat(0x00800000), new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(126)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00800000), new BigFraction(BigInteger.valueOf(1 << 24 | 1), BigInteger.ONE.shiftLeft(150)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00800001), new BigFraction(BigInteger.valueOf(1 << 25 | 3), BigInteger.ONE.shiftLeft(151)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00800000), new BigFraction(BigInteger.valueOf((1 << 24) - 1), BigInteger.ONE.shiftLeft(150)).floatValue(), 0);
		
		// Subnormal
		assertEquals(Float.intBitsToFloat(0x00000001), new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(149)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00000002), new BigFraction(BigInteger.valueOf(3), BigInteger.ONE.shiftLeft(150)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00000001), new BigFraction(BigInteger.valueOf(5), BigInteger.ONE.shiftLeft(152)).floatValue(), 0);
		assertEquals(Float.intBitsToFloat(0x00000001), new BigFraction(BigInteger.valueOf(5), BigInteger.ONE.shiftLeft(151)).floatValue(), 0);
		assertEquals(0.0f, new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(150)).floatValue(), 0);
	}
	
	
	@Test
	public void testFloatValueHuge() {
		assertEquals(Float.intBitsToFloat(0x7F000000), new BigFraction(BigInteger.ONE.shiftLeft(127), BigInteger.ONE).floatValue(), 0);
		assertEquals(Float.MAX_VALUE, new BigFraction(BigInteger.valueOf((1 << 24) - 1).shiftLeft(127), BigInteger.ONE.shiftLeft(23)).floatValue(), 0);
		assertEquals(Float.MAX_VALUE, new BigFraction(BigInteger.valueOf((1 << 25) - 2).shiftLeft(126), BigInteger.ONE.shiftLeft(23)).floatValue(), 0);
		assertEquals(Float.POSITIVE_INFINITY, new BigFraction(BigInteger.valueOf((1 << 25) - 1).shiftLeft(126), BigInteger.ONE.shiftLeft(23)).floatValue(), 0);
		assertEquals(Float.POSITIVE_INFINITY, new BigFraction(BigInteger.ONE.shiftLeft(128), BigInteger.ONE).floatValue(), 0);
	}
	
	
	@Test
	public void testFloatValueRandomly() {
		for (int i = 0; i < 1000; i++) {
			int sign = Random.DEFAULT.uniformInt(2);
			int exp = Random.DEFAULT.uniformInt(255);
			int man = Random.DEFAULT.uniformInt(1 << 23);
			int rawfloat = sign << 31 | exp << 23 | man;
			
			if (exp == 0)  // Subnormal
				exp++;
			else  // Normal
				man |= 1 << 23;
			if (sign == 1)
				man = -man;
			exp -= 127;
			BigFraction frac = new BigFraction(BigInteger.valueOf(man).shiftLeft(Math.max(exp, 0)), BigInteger.ONE.shiftLeft(Math.max(-exp, 0) + 23));
			
			assertEquals(Float.intBitsToFloat(rawfloat), frac.floatValue(), 0);
		}
	}
	
	
	@Test
	public void testDoubleValue() {
		assertEquals(0.0, new BigFraction(0, 1).doubleValue(), 0);
		assertEquals(-5.0, new BigFraction(-5, 1).doubleValue(), 0);
		
		assertEquals(1.0, new BigFraction(1, 1).doubleValue(), 0);
		assertEquals(2.0, new BigFraction(2, 1).doubleValue(), 0);
		assertEquals(3.0, new BigFraction(3, 1).doubleValue(), 0);
		assertEquals(4.0, new BigFraction(4, 1).doubleValue(), 0);
		assertEquals(5.0, new BigFraction(5, 1).doubleValue(), 0);
		assertEquals(7.0, new BigFraction(7, 1).doubleValue(), 0);
		
		assertEquals(0.5, new BigFraction(1, 2).doubleValue(), 0);
		assertEquals(0.25, new BigFraction(1, 4).doubleValue(), 0);
		assertEquals(0.75, new BigFraction(3, 4).doubleValue(), 0);
		
		assertEquals(Double.longBitsToDouble(0x3FB999999999999AL), new BigFraction(1, 10).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0xBFE6666666666666L), new BigFraction(-7, 10).doubleValue(), 0);
	}
	
	
	@Test
	public void testDoubleValueTiny() {
		// Normal
		assertEquals(Double.longBitsToDouble(0x0010000000000000L), new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(1022)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0010000000000000L), new BigFraction(BigInteger.valueOf(1L << 53 | 1), BigInteger.ONE.shiftLeft(1075)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0010000000000001L), new BigFraction(BigInteger.valueOf(1L << 54 | 3), BigInteger.ONE.shiftLeft(1076)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0010000000000000L), new BigFraction(BigInteger.valueOf((1L << 53) - 1), BigInteger.ONE.shiftLeft(1075)).doubleValue(), 0);
		
		// Subnormal
		assertEquals(Double.longBitsToDouble(0x0000000000000001), new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(1074)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0000000000000002), new BigFraction(BigInteger.valueOf(3), BigInteger.ONE.shiftLeft(1075)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0000000000000001), new BigFraction(BigInteger.valueOf(5), BigInteger.ONE.shiftLeft(1077)).doubleValue(), 0);
		assertEquals(Double.longBitsToDouble(0x0000000000000001), new BigFraction(BigInteger.valueOf(5), BigInteger.ONE.shiftLeft(1076)).doubleValue(), 0);
		assertEquals(0.0, new BigFraction(BigInteger.ONE, BigInteger.ONE.shiftLeft(1075)).doubleValue(), 0);
	}
	
	
	@Test
	public void testDoubleValueHuge() {
		assertEquals(Double.longBitsToDouble(0x7FE0000000000000L), new BigFraction(BigInteger.ONE.shiftLeft(1023), BigInteger.ONE).doubleValue(), 0);
		assertEquals(Double.MAX_VALUE, new BigFraction(BigInteger.valueOf((1L << 53) - 1).shiftLeft(1023), BigInteger.ONE.shiftLeft(52)).doubleValue(), 0);
		assertEquals(Double.MAX_VALUE, new BigFraction(BigInteger.valueOf((1L << 54) - 2).shiftLeft(1022), BigInteger.ONE.shiftLeft(52)).doubleValue(), 0);
		assertEquals(Double.POSITIVE_INFINITY, new BigFraction(BigInteger.valueOf((1L << 54) - 1).shiftLeft(1022), BigInteger.ONE.shiftLeft(52)).doubleValue(), 0);
		assertEquals(Double.POSITIVE_INFINITY, new BigFraction(BigInteger.ONE.shiftLeft(1024), BigInteger.ONE).doubleValue(), 0);
	}
	
	
	@Test
	public void testDoubleValueRandomly() {
		for (int i = 0; i < 1000; i++) {
			int sign = Random.DEFAULT.uniformInt(2);
			int exp = Random.DEFAULT.uniformInt(255);
			long man = Random.DEFAULT.uniformLong() & ((1L << 52) - 1);
			long rawdouble = (long)sign << 63 | (long)exp << 52 | man;
			
			if (exp == 0)  // Subnormal
				exp++;
			else  // Normal
				man |= 1L << 52;
			if (sign == 1)
				man = -man;
			exp -= 1023;
			BigFraction frac = new BigFraction(BigInteger.valueOf(man).shiftLeft(Math.max(exp, 0)), BigInteger.ONE.shiftLeft(Math.max(-exp, 0) + 52));
			
			assertEquals(Double.longBitsToDouble(rawdouble), frac.doubleValue(), 0);
		}
	}
	
}
