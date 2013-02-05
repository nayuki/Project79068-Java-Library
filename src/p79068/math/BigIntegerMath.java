package p79068.math;

import java.math.BigInteger;

import p79068.Assert;


public final class BigIntegerMath {
	
	/**
	 * Returns {@code x * y} (the product of the specified integers), which gives the same result as {@code x.multiply(y)} but should be faster. The current implementation uses Karatsuba multiplication.
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return {@code x} times {@code} y
	 * @throws NullPointerException if {@code x} or {@code y} is {@code null}
	 */
	public static BigInteger multiply(BigInteger x, BigInteger y) {
		Assert.assertNotNull(x, y);
		return karatsubaMultiply(x, y);
	}
	
	
	// Requirement: CUTOFF >= 64, or else there will be infinite recursion.
	private static final int KARATSUBA_MULTIPLICATION_CUTOFF = 1536;
	
	// Requires x != null and y != null
	private static BigInteger karatsubaMultiply(BigInteger x, BigInteger y) {
		if (x.bitLength() <= KARATSUBA_MULTIPLICATION_CUTOFF || y.bitLength() <= KARATSUBA_MULTIPLICATION_CUTOFF) {  // Base case
			return x.multiply(y);
			
		} else {
			int n = Math.max(x.bitLength(), y.bitLength());
			int half = (n + 32) / 64 * 32;
			BigInteger mask = BigInteger.ONE.shiftLeft(half).subtract(BigInteger.ONE);
			BigInteger xlow = x.and(mask);
			BigInteger ylow = y.and(mask);
			BigInteger xhigh = x.shiftRight(half);
			BigInteger yhigh = y.shiftRight(half);
			
			BigInteger a = karatsubaMultiply(xhigh, yhigh);
			BigInteger b = karatsubaMultiply(xlow.add(xhigh), ylow.add(yhigh));
			BigInteger c = karatsubaMultiply(xlow, ylow);
			BigInteger d = b.subtract(a).subtract(c);
			return a.shiftLeft(half).add(d).shiftLeft(half).add(c);
		}
	}
	
	
	/**
	 * Returns floor(sqrt(<var>x</var>)), or throws an exception if {@code x < 0}.
	 * @param x the argument
	 * @return the floor of the square root of {@code x}
	 * @throws NullPointerException if {@code x} is null
	 * @throws IllegalArgumentException if {@code x} is less than zero
	 */
	public static BigInteger sqrt(BigInteger x) {
		Assert.assertNotNull(x);
		if (x.signum() == -1)
			throw new IllegalArgumentException("Square root of negative number");
		BigInteger y = BigInteger.ZERO;
		for (int i = (x.bitLength() - 1) / 2; i >= 0; i--) {
			y = y.setBit(i);
			if (y.multiply(y).compareTo(x) > 0)
				y = y.clearBit(i);
		}
		return y;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private BigIntegerMath() {}
	
}
