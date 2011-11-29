package p79068.math;

import java.math.BigInteger;
import p79068.lang.NullChecker;


/**
 * An arbitrary precision fraction. The numerator and denominator are both BigIntegers.
 * The fraction is always in canonical form: the denominator is positive, and the GCD of the numerator and denominator is one.
 */
public final class BigFraction extends Number implements Comparable<BigFraction> {
	
	/* Public constants */
	
	/** The fraction 0, or 0/1. */
	public static final BigFraction ZERO = new BigFraction(0, 1);
	
	/** The fraction 1, or 1/1. */
	public static final BigFraction ONE = new BigFraction(1, 1);
	
	
	
	// The numerator, which can be any number.
	private BigInteger numerator;
	
	// The denominator, which is always positive.
	private BigInteger denominator;
	
	
	
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
		NullChecker.check(num, den);
		if (den.equals(BigInteger.ZERO))
			throw new IllegalArgumentException("Denominator is zero");
		
		// Make denominator positive
		if (den.compareTo(BigInteger.ZERO) < 0) {
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
	
	
	
	/* Getter methods */
	
	/**
	 * Returns the numerator of this fraction.
	 * @return the numerator
	 */
	public BigInteger getNumerator() {
		return numerator;
	}
	
	
	/**
	 * Returns the denominator of this fraction.
	 * @return the denominator
	 */
	public BigInteger getDenominator() {
		return denominator;
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
	 */
	public BigFraction divide(BigFraction other) {
		if (other.numerator.equals(BigInteger.ZERO))
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
	 */
	public BigFraction reciprocal() {
		if (numerator.equals(BigInteger.ZERO))
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
		else {
			BigFraction other = (BigFraction)obj;
			return numerator.equals(other.numerator) && denominator.equals(other.denominator);
		}
	}
	
	
	/**
	 * Compares this fraction to the specified fraction. Returns a negative number if {@code this < other}, zero if {@code this == other}, or a positive number if {@code this > other}.
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
	
	
	@Override
	public int intValue() {
		return numerator.divide(denominator).intValue();
	}
	
	
	@Override
	public long longValue() {
		return numerator.divide(denominator).longValue();
	}
	
	
	@Override
	public float floatValue() {
		// FIXME: Low accuracy!
		return (float)(numerator.doubleValue() / denominator.doubleValue());
	}
	
	
	@Override
	public double doubleValue() {
		BigInteger num = numerator;
		BigInteger den = denominator;
		
		// Eliminate zero
		if (num.equals(ZERO))
			return 0.0;
		
		// Make number positive
		int sign;
		if (num.compareTo(BigInteger.ZERO) > 0)
			sign = 1;
		else {
			num = num.negate();
			sign = -1;
		}
		
		// Normalize number to the range [1, 2)
		int exponent = 0;
		while (num.compareTo(den.shiftLeft(1)) >= 0) {
			den = den.shiftLeft(1);
			exponent++;
		}
		while (num.compareTo(den) < 0) {
			num = num.shiftLeft(1);
			exponent--;
		}
		
		// Weed out definite infinity and zero
		if (exponent > 1023)
			return sign * Double.POSITIVE_INFINITY;
		if (exponent < -1075)
			return 0.0;
		
		if (exponent >= -1022) {  // Normal (probably)
			long mantissa = round(num.shiftLeft(52), den).longValue();
			if (mantissa < (1L << 52) || mantissa > (1L << 53))
				throw new AssertionError();
			else if (mantissa == (1L << 53)) {
				mantissa >>>= 1;
				exponent++;
			}
			mantissa ^= 1L << 52;
			
			if (exponent <= 1023)
				return Double.longBitsToDouble((long)((1 - sign) / 2) << 63 | (long)(exponent + 1023) << 52 | mantissa);
			else
				return sign * Double.POSITIVE_INFINITY;
			
		} else {  // Subnormal (probably)
			long mantissa = round(num.shiftLeft(exponent + 1074), den).longValue();
			if (mantissa > (1L << 52))
				throw new AssertionError();
			else if (mantissa == (1L << 52))
				return Double.longBitsToDouble((long)((1 - sign) / 2) << 63 | 1 << 52);  // Normal
			else
				return Double.longBitsToDouble((long)((1 - sign) / 2) << 63 | mantissa);  // Subnormal
		}
	}
	
	
	// Round half even for a non-negative argument. e.g. round(3, 4) = 1, round(1, 2) = 0, round(3, 2) = 2.
	private static BigInteger round(BigInteger num, BigInteger den) {
		BigInteger[] temp = num.divideAndRemainder(den);
		BigInteger quot = temp[0];
		BigInteger rem  = temp[1];
		
		int cmp = rem.shiftLeft(1).compareTo(den);
		if (cmp > 0)  // Greater than half
			quot = quot.add(BigInteger.ONE);
		else if (cmp == 0 && quot.and(BigInteger.ONE).intValue() == 1)  // Exactly half, and quotient is odd
			quot = quot.add(BigInteger.ONE);
		
		return quot;
	}
	
}
