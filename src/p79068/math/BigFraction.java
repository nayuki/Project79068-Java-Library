package p79068.math;

import java.math.BigInteger;
import p79068.Assert;


/**
 * An arbitrary precision fraction. The numerator and denominator are both BigIntegers. Objects are immutable.
 * The fraction is always in canonical form: the denominator is positive, and the GCD of the numerator and denominator is one.
 */
public final class BigFraction extends Number implements Comparable<BigFraction> {
	
	/* Public constants */
	
	/** The fraction 0, or 0/1. */
	public static final BigFraction ZERO = new BigFraction(0, 1);
	
	/** The fraction 1, or 1/1. */
	public static final BigFraction ONE = new BigFraction(1, 1);
	
	
	
	/** The numerator, which can be any number. */
	public final BigInteger numerator;
	
	/** The denominator, which is always positive. */
	public final BigInteger denominator;
	
	
	
	/* Constructors */
	
	/**
	 * Constructs a BigFraction representing the specified integer value. The denominator is 1.
	 * @param value the integer value
	 */
	public BigFraction(long value) {
		numerator = BigInteger.valueOf(value);
		denominator = BigInteger.ONE;
	}
	
	
	/**
	 * Constructs a BigFraction representing the specified fraction. The denominator must not be zero.
	 * @param num the numerator
	 * @param den the denominator, which must not be zero
	 * @throws IllegalArgumentException if the denominator is zero
	 */
	public BigFraction(long num, long den) {
		this(BigInteger.valueOf(num), BigInteger.valueOf(den));
	}
	
	
	/**
	 * Constructs a BigFraction representing the specified fraction. The denominator must not be zero.
	 * @param num the numerator
	 * @param den the denominator, which must not be zero
	 * @throws NullPointerException if the numerator or denominator is {@code null}
	 * @throws IllegalArgumentException if the denominator is zero
	 */
	public BigFraction(BigInteger num, BigInteger den) {
		Assert.assertNotNull(num, den);
		if (den.signum() == 0)
			throw new IllegalArgumentException("Denominator is zero");
		
		// Make denominator positive
		if (den.signum() == -1) {
			num = num.negate();
			den = den.negate();
		}
		
		// Reduce to lowest terms
		BigInteger gcd = num.gcd(den);
		if (!gcd.equals(BigInteger.ONE)) {
			num = num.divide(gcd);
			den = den.divide(gcd);
		}
		
		// Now each number is in its one and only canonical representation
		numerator = num;
		denominator = den;
	}
	
	
	
	/* Arithmetic methods */
	
	/**
	 * Returns {@code this + other}.
	 * @param other the other fraction
	 * @return this fraction plus the other fraction
	 */
	public BigFraction add(BigFraction other) {
		BigInteger num = numerator.multiply(other.denominator).add(other.numerator.multiply(denominator));
		BigInteger den = denominator.multiply(other.denominator);
		return new BigFraction(num, den);
	}
	
	
	/**
	 * Returns {@code this - other}.
	 * @param other the other fraction
	 * @return this fraction minus the other fraction
	 */
	public BigFraction subtract(BigFraction other) {
		BigInteger num = numerator.multiply(other.denominator).subtract(other.numerator.multiply(denominator));
		BigInteger den = denominator.multiply(other.denominator);
		return new BigFraction(num, den);
	}
	
	
	/**
	 * Returns {@code this * other}.
	 * @param other the other fraction
	 * @return this fraction times the other fraction
	 */
	public BigFraction multiply(BigFraction other) {
		BigInteger num = numerator.multiply(other.numerator);
		BigInteger den = denominator.multiply(other.denominator);
		return new BigFraction(num, den);
	}
	
	
	/**
	 * Returns {@code this / other}.
	 * @param other the other fraction
	 * @return this fraction divided by the other fraction
	 * @throws ArithmeticException if the other fraction is zero
	 */
	public BigFraction divide(BigFraction other) {
		if (other.numerator.signum() == 0)
			throw new ArithmeticException("Division by zero");
		BigInteger num = numerator.multiply(other.denominator);
		BigInteger den = denominator.multiply(other.numerator);
		return new BigFraction(num, den);
	}
	
	
	/**
	 * Returns {@code -this}. (Also called the opposite, the additive inverse.)
	 * @return the negation of this fraction
	 */
	public BigFraction negate() {
		return new BigFraction(numerator.negate(), denominator);
	}
	
	
	/**
	 * Returns {@code 1 / this}. (Also called the multiplicative inverse.)
	 * @return the reciprocal of this fraction
	 * @throws ArithmeticException if the denominator is zero
	 */
	public BigFraction reciprocal() {
		if (numerator.signum() == 0)
			throw new ArithmeticException("Division by zero");
		return new BigFraction(denominator, numerator);
	}
	
	
	/* Comparison methods */
	
	/**
	 * Tests whether this fraction equals the specified object.
	 * @param obj the object to test for equality with
	 * @return whether the other object is a fraction and has the same value as this fraction
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BigFraction))
			return false;
		BigFraction other = (BigFraction)obj;
		return numerator.equals(other.numerator) && denominator.equals(other.denominator);
	}
	
	
	/**
	 * Compares this fraction to the specified fraction. Returns a negative number if {@code this < other},
	 * zero if {@code this == other}, or a positive number if {@code this > other}.
	 * @param other the fraction to compare to
	 * @return negative if {@code this < other}, zero if {@code this == other}, or positive if {@code this > other}
	 */
	public int compareTo(BigFraction other) {
		return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
	}
	
	
	/**
	 * Returns the hash code for this fraction.
	 * @return the hash code for this fraction
	 */
	@Override
	public int hashCode() {
		return numerator.hashCode() ^ denominator.hashCode();
	}
	
	
	/* String and primitive number type conversion methods */
	
	/**
	 * Returns a string representation of this fraction. The format is subject to change.
	 * @return a string representation of this fraction
	 */
	@Override
	public String toString() {
		return String.format("%d/%d", numerator, denominator);
	}
	
	
	/**
	 * Converts this fraction to an {@code int}. Fractions that are not whole numbers are rounded towards zero
	 * (e.g. 3/2 becomes 1, and -3/2 becomes -1). The returned result is this integer value modulo 2<sup>32</sup>,
	 * as per the narrowing primitive conversion defined in the Java Language Specification.
	 * @return this fraction converted to an {@code int}
	 */
	@Override
	public int intValue() {
		return numerator.divide(denominator).intValue();
	}
	
	
	/**
	 * Converts this fraction to a {@code long}. Fractions that are not whole numbers are rounded towards zero
	 * (e.g. 3/2 becomes 1, and -3/2 becomes -1). The returned result is this integer value modulo 2<sup>64</sup>,
	 * as per the narrowing primitive conversion defined in the Java Language Specification.
	 * @return this fraction converted to a {@code long}
	 */
	@Override
	public long longValue() {
		return numerator.divide(denominator).longValue();
	}
	
	
	/**
	 * Converts this fraction to a {@code float}. The result has the maximum accuracy possible and uses round-half-even.
	 * @return this fraction converted to a {@code float}
	 */
	@Override
	public float floatValue() {
		if (numerator.bitLength() <= 24 && denominator.bitLength() <= 24)
			return (float)numerator.intValue() / denominator.intValue();
		else
			return Float.intBitsToFloat((int)toFloatingPointBits(numerator, denominator, 23, 8));
	}
	
	
	/**
	 * Converts this fraction to a {@code double}. The result has the maximum accuracy possible and uses round-half-even.
	 * @return this fraction converted to a {@code double}
	 */
	@Override
	public double doubleValue() {
		if (numerator.bitLength() <= 53 && denominator.bitLength() <= 53)
			return (double)numerator.longValue() / denominator.longValue();
		else
			return Double.longBitsToDouble(toFloatingPointBits(numerator, denominator, 52, 11));
	}
	
	
	// Note: This can return zero, a subnormal number, a normal number, or infinity, but never not-a-number (NaN).
	private long toFloatingPointBits(BigInteger num, BigInteger den, int mantissaBits, int exponentBits) {
		if (mantissaBits < 1 || exponentBits < 1 || (long)mantissaBits + exponentBits > 63)
			throw new IllegalArgumentException();
		
		// Handle zero and negative numbers specially
		if (num.signum() == 0)  // Eliminate exact zero (because it can't be normalized)
			return 0;
		else if (num.signum() == -1)  // Recurse on negative numbers
			return 1L << (mantissaBits + exponentBits) | toFloatingPointBits(num.negate(), den, mantissaBits, exponentBits);
		// Reaching this point in the code, num > 0 and den > 0, so the number is strictly positive
		
		// Roughly normalize the number to somewhere near the range [1, 2)
		int exponent = num.bitLength() - 1 - den.bitLength();
		if      (exponent > 0) den = den.shiftLeft( exponent);
		else if (exponent < 0) num = num.shiftLeft(-exponent);
		
		// Normalize to the range [1, 2) by brute force
		while (num.compareTo(den.shiftLeft(1)) >= 0) {
			den = den.shiftLeft(1);
			exponent++;
		}
		while (num.compareTo(den) < 0) {
			num = num.shiftLeft(1);
			exponent--;
		}
		
		// Every finite floating-point number has an exponent in the range [minExponent, maxExponent].
		// A subnormal number has exponent = minExponent and no implicit leading 1 bit.
		// The maximum exponent is also the same value as the exponent bias.
		final int maxExponent = (1 << (exponentBits - 1)) - 1;
		final int minExponent = 1 - maxExponent;
		
		// Quickly exclude definite infinity and zero
		if (exponent > maxExponent)
			return ((1L << exponentBits) - 1) << mantissaBits;  // Infinity
		if (exponent < minExponent - mantissaBits - 1)
			return 0;
		
		if (exponent >= minExponent) {  // Normal (common) or infinity (if rounded up)
			long mantissa = round(num.shiftLeft(mantissaBits), den).longValue();
			if (mantissa < (1L << mantissaBits) || mantissa > (1L << (mantissaBits + 1)))
				throw new AssertionError();
			else if (mantissa == (1L << (mantissaBits + 1))) {
				mantissa >>>= 1;
				exponent++;
				if (exponent > maxExponent)
					return ((1L << exponentBits) - 1) << mantissaBits;  // Infinity
			}
			mantissa ^= 1L << mantissaBits;  // Remove implicit leading 1 bit
			return (long)(exponent + maxExponent) << mantissaBits | mantissa;  // Normal
			
		} else {  // Subnormal (common) or the smallest normal number (if rounded up)
			long mantissa = round(num.shiftLeft(exponent - (minExponent - mantissaBits)), den).longValue();
			if (mantissa < (1L << mantissaBits))
				return mantissa;  // Subnormal
			else if (mantissa == 1L << mantissaBits)
				return 1L << mantissaBits;  // Normal
			else
				throw new AssertionError();
		}
	}
	
	
	// Round half even for a non-negative argument. e.g. round(3, 4) = 1, round(1, 2) = 0, round(3, 2) = 2.
	private static BigInteger round(BigInteger num, BigInteger den) {
		// Pseudocode:
		//   assert num / den >= 0
		//   quot = floor(num / den)
		//   rem = (num / den) mod 1
		//   if rem > 1/2 or (rem = 1/2 and (quot mod 2 = 1)):
		//     quot++
		//   return quot
		BigInteger[] temp = num.divideAndRemainder(den);
		BigInteger quot = temp[0];
		BigInteger rem  = temp[1];
		int cmp = rem.shiftLeft(1).compareTo(den);
		if (cmp > 0 || cmp == 0 && quot.testBit(0))
			quot = quot.add(BigInteger.ONE);
		return quot;
	}
	
}
