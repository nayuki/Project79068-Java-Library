package p79068.math;


/**
 * Contains methods for math functions that deal with integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class IntegerMath {
	
	// Basic operations
	
	/**
	 * Returns the sum of the specified integers, throwing an exception if the result overflows.
	 * @param x a summand
	 * @param y a summand
	 * @return {@code x} plus {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int checkedAdd(int x, int y) {
		int z = x + y;
		if (y > 0 && z < x || y < 0 && z > x)
			throw new ArithmeticOverflowException(String.format("%d + %d", x, y));
		else
			return z;
	}
	
	
	/**
	 * Returns the difference of the specified integers, throwing an exception if the result overflows.
	 * @param x the minuend
	 * @param y the subtrahend
	 * @return {@code x} minus {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int checkedSubtract(int x, int y) {
		int z = x - y;
		if (y > 0 && z > x || y < 0 && z < x)
			throw new ArithmeticOverflowException(String.format("%d - %d", x, y));
		else
			return z;
	}
	
	
	/**
	 * Returns the product of the specified integers, throwing an exception if the result overflows.
	 * @param x a multiplicand
	 * @param y a multiplicand
	 * @return {@code x} times {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int checkedMultiply(int x, int y) {
		long z = (long)x * y;
		if (z >= Integer.MIN_VALUE && z <= Integer.MAX_VALUE)
			return x * y;
		else
			throw new ArithmeticOverflowException(String.format("%d * %d", x, y));
	}
	
	
	/**
	 * Returns the quotient of the specified integers, throwing an exception if the result overflows. The only overflow case is when {@code x} = &minus;2<sup>31</sup> and {@code y} = &minus;1.
	 * @param x the dividend
	 * @param y the divisor
	 * @return {@code x} divided by {@code y}
	 * @throws ArithmeticOverflowException if the result overflows
	 */
	public static int checkedDivide(int x, int y) {
		if (x == Integer.MIN_VALUE && y == -1)
			throw new ArithmeticOverflowException(String.format("%d / %d", x, y));
		else
			return x / y;
	}
	
	
	/**
	 * Returns the quotient of the specified unsigned 32-bit integers.
	 * @param x the dividend, interpreted as an unsigned 32-bit integer
	 * @param y the divisor, interpreted as an unsigned 32-bit integer
	 * @return the quotient, interpreted an unsigned 32-bit integer
	 * @throws ArithmeticException if {@code y} is 0 (division by zero)
	 */
	public static int divideUnsigned(int x, int y) {
		if (y >= 0) {  // y is less than 32 bits
			if (x >= 0)  // x is less than 32 bits
				return x / y;
			else {
				int i = Integer.numberOfLeadingZeros(y);  // At least 1
				if ((x >>> i) < y)
					i--;
				return (1 << i) + (x - (y << i)) / y;
			}
		} else
			return (x ^ 0x80000000) < (y ^ 0x80000000) ? 0 : 1;
	}
	
	
	/**
	 * Returns the floor of the quotient of the specified integers.
	 * @param x the dividend
	 * @param y the divisor
	 * @return the floor of {@code x} divided by {@code y}
	 * @throws ArithmeticException if {@code y} is 0
	 * @throws ArithmeticOverflowException if {@code x} = &minus;2<sup>31</sup> and {@code y} = &minus;1
	 */
	public static int divideAndFloor(int x, int y) {
		if (x == Integer.MIN_VALUE && y == -1)  // The one and only overflow case
			throw new ArithmeticOverflowException(String.format("divideAndFloor(%d, %d)", x, y));
		else if ((x >= 0) == (y >= 0))
			return x / y;  // If they have the same sign, then result is positive and already floored
		else {
			int z = x / y;
			if (z * y == x)
				return z;
			else
				return z - 1;
		}
	}
	
	
	/**
	 * Returns {@code x} modulo {@code y}. The result either has the same sign as {@code y} or is zero. Note that this is not exactly the same as the remainder operator ({@code %}) provided by the language.
	 * <p>Sample values:</p>
	 * <ul>
	 *   <li>{@code mod( 4, &nbsp;3) = &nbsp;1}</li>
	 *   <li>{@code mod(-4, &nbsp;3) = &nbsp;2}</li>
	 *   <li>{@code mod( 4, -3) = -2}</li>
	 *   <li>{@code mod(-4, -3) = -1}</li>
	 * </ul>
	 * @param x the integer to reduce
	 * @param y the modulus
	 * @return {@code x} modulo {@code y}
	 * @throws ArithmeticException if {@code y} is 0
	 */
	public static int mod(int x, int y) {
		x %= y;  // x is now in (-abs(y), abs(y))
		if (y > 0 && x < 0 || y < 0 && x > 0)
			x += y;
		return x;
	}
	
	
	
	// Simple functions
	
	/**
	 * Returns the sign of the specified integer, which is {@code -1}, {@code 0}, or {@code 1}. Also called sgn and signum.
	 * @param x the integer to whose sign will be computed
	 */
	public static int sign(int x) {
		return (x >> 31) | ((-x) >>> 31);
	}
	
	
	/**
	 * Compares the specified integers.
	 * @return {@code -1} if {@code x &lt; y}, {@code 0} if {@code x == y}, or {@code 1} if {@code x &gt; y}
	 */
	public static int compare(int x, int y) {
		if (x < y)
			return -1;
		else if (x > y)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Compares the specified unsigned integers.
	 * @param x an operand, interpreted as an unsigned 32-bit integer
	 * @param y an operand, interpreted as an unsigned 32-bit integer
	 * @return {@code -1} if {@code x &lt; y}, {@code 0} if {@code x == y}, or {@code 1} if {@code x &gt; y}
	 */
	public static int compareUnsigned(int x, int y) {
		return compare(x ^ (1 << 31), y ^ (1 << 31));  // Flip top bits
	}
	
	
	/**
	 * Returns the integer in the specified range (inclusive) nearest to the specified integer. In other words, if {@code x &lt; min} then {@code min} is returned; if {@code x &gt; max} then {@code max} is returned; otherwise {@code x} is returned. This function is equivalent to {@code Math.max(Math.min(x, max), min)}.
	 * @param x the integer to clamp
	 * @param min the lower limit (inclusive)
	 * @param max the upper limit (inclusive)
	 * @return {@code min}, {@code x}, or {@code max}, whichever is closest to {@code x}
	 * @throws IllegalArgumentException if {@code min &gt; max}
	 */
	public static int clamp(int x, int min, int max) {
		if (min > max)
			throw new IllegalArgumentException("Minimum greater than maximum");
		else if (x < min)
			return min;
		else if (x > max)
			return max;
		else
			return x;
	}
	
	
	/**
	 * Tests whether the specified number is an integral power of 2. The powers of 2 are 1, 2, 4, ..., 1073741824. Zero and negative numbers are not powers of 2.
	 * @param x the integer to test
	 * @return {@code true} if and only if {@code x} is an integral power of 2
	 */
	public static boolean isPowerOf2(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}
	
	
	
	// Elementary functions
	
	/**
	 * Returns the floor of the square root of the specified number.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li>{@code sqrt(4) = 2}</li>
	 *  <li>{@code sqrt(5) = 2}</li>
	 *  <li>{@code sqrt(9) = 3}</li>
	 * </ul>
	 * @throws IllegalArgumentException if {@code x &lt; 0}
	 */
	public static int sqrt(int x) {
		if (x < 0)
			throw new IllegalArgumentException("Square root of negative number");
		int y = 0;
		for (int i = 1 << 15; i != 0; i >>>= 1) {
			y |= i;
			if (y > 46340 || y * y > x)
				y ^= i;
		}
		return y;
	}
	
	
	/**
	 * Returns the cube root of the specified number, rounded down towards zero.
	 * <p>Sample values:</p>
	 * <ul>
	 *  <li>{@code cbrt(1) = 1}</li>
	 *  <li>{@code cbrt(5) = 1}</li>
	 *  <li>{@code cbrt(8) = 2}</li>
	 * </ul>
	 */
	public static int cbrt(int x) {
		if (x == -2147483648)
			return -1290;
		if (x < 0)
			return -cbrt(-x);
		int y = 0;
		for (int i = 1024; i >= 1; i /= 2) {
			y += i;
			if (y > 1290 || y * y * y > x)
				y -= i;
		}
		return y;
	}
	
	
	/**
	 * Returns the floor of the base 2 logarithm of the specified number. The result is in the range [0, 30].
	 * @param x the integer to log and floor
	 * @return the floor of the base 2 logarithm of the number
	 * @throws IllegalArgumentException if {@code x} &le; 0
	 */
	public static int log2Floor(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Argument must be positive");
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return Integer.bitCount(x) - 1;
	}
	
	
	/**
	 * Returns the ceiling of the base 2 logarithm of the specified number. The result is in the range [0, 31].
	 * @param x the integer to log and ceiling
	 * @return the ceiling of the base 2 logarithm of the number
	 * @throws IllegalArgumentException if {@code x} &le; 0
	 */
	public static int log2Ceiling(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Argument must be positive");
		x--;
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return Integer.bitCount(x);
	}
	
	
	/**
	 * Returns the nearest power of 2 that is less than or equal to the specified number.
	 * @param x the integer to floor to a power of 2
	 * @return a power of 2 less than or equal to {@code x}
	 * @throws IllegalArgumentException if {@code x &lt;= 0}
	 */
	public static int floorToPowerOf2(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Non-positive argument");
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return x ^ (x >>> 1);
	}
	
	
	/**
	 * Returns the nearest power of 2 that is greater than or equal to the specified number.
	 * @param x the integer to ceiling to a power of 2
	 * @return a power of 2 greater than or equal to {@code x}
	 * @throws IllegalArgumentException if {@code x &lt;= 0}
	 * @throws ArithmeticOverflowException if {@code x &gt; 1073741824}
	 */
	public static int ceilingToPowerOf2(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Non-positive argument");
		if (x > 1073741824)
			throw new ArithmeticOverflowException(String.format("ceilingToPowerOf2(%d)", x));
		x--;
		x |= x >>>  1;
		x |= x >>>  2;
		x |= x >>>  4;
		x |= x >>>  8;
		x |= x >>> 16;
		return x + 1;
	}
	
	
	
	// Number theory functions
	
	/**
	 * Tests whether the specified integer is a positive prime number. Note that 0 and 1 are not prime.
	 * @param n the integer to test for primeness
	 * @return whether {@code n} is prime
	 */
	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		else if (n < 6)
			return n != 4;
		else if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0)
			return false;
		else {
			// Trial division with wheel factorization
			int end = sqrt(n);
			for (int i = 6; i < end; i += 6) {
				if (n % (i + 1) == 0 || n % (i + 5) == 0)
					return false;
			}
			return true;
		}
	}
	
	
	/**
	 * Returns the greatest common divisor (GCD) of the specified integers. If <var>z</var> is the GCD of <var>x</var> and <var>y</var>, then <var>z</var> is the largest number such that <var>x</var>/<var>z</var> and <var>y</var>/<var>z</var> are integers.
	 */
	public static int gcd(int x, int y) {
		if (x == Integer.MIN_VALUE && y == Integer.MIN_VALUE)
			throw new ArithmeticOverflowException(String.format("gcd(%d, %d)", x, y));
		else {
			// The GCD will not be -Integer.MIN_VALUE, so we can safely remove a factor of 2
			if (x == Integer.MIN_VALUE)
				x >>>= 1;
			else if (y == Integer.MIN_VALUE)
				y >>>= 1;
			
			// Make arguments non-negative
			if (x < 0)
				x = -x;
			if (y < 0)
				y = -y;
			
			// Standard Euclidean algorithm
			while (y != 0) {
				int z = x % y;
				x = y;
				y = z;
			}
			return x;
		}
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private IntegerMath() {}
	
}
