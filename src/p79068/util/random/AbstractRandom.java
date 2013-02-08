package p79068.util.random;

import p79068.Assert;
import p79068.math.IntegerMath;


/**
 * Generates a stream of random numbers. True random number generators can be implemented, but generally, implementers of this class are pseudorandom number generators (PRNGs). PRNGs are deterministic and can reproduce the same sequence when given the same seed.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Unsafe</em>, unless otherwise specified</p>
 * <p>One usage example:</p>
 * <p>{@code int i = AbstractRandom.DEFAULT.randomInt(10);  // Returns a number from 0 to 9 (inclusive) }</p>
 */
public abstract class AbstractRandom implements Random {
	
	/**
	 * Multiplying a 53-bit integer with this constant yields a {@code double} in the range [0, 1). This value is chosen so that all the mantissa bits in the {@code double} may be non-zero when the magnitude is in [0.5, 1).
	 */
	protected static final double DOUBLE_SCALER = 1.0D / (1L << 53);
	
	
	
	protected AbstractRandom() {}
	
	
	
	/**
	 * Returns a random, uniformly distributed integer between 0 (inclusive) and {@code n} (exclusive). {@code n} must be positive.
	 * @param n the upper bound of the range to generate in
	 * @return an integer in the range [0, {@code n}), each with equal probability
	 * @throws IllegalArgumentException if {@code n} &le; 0
	 */
	public int uniformInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		
		if (IntegerMath.isPowerOf2(n))
			return uniformInt() & (n - 1);  // Fast path
		else {  // Unbiased
			int random;
			int result;
			do {  // Rejection sampling
				random = uniformInt() >>> 1;  // In the range [0, 2^31)
				result = random % n;
			} while (random - result + (n - 1) < 0);
			return result;
		}
	}
	
	
	/**
	 * Returns a random, uniformly distributed {@code int} value.
	 * @return a value in the range of {@code int}, each with equal probability
	 */
	public int uniformInt() {
		return (int)uniformLong();
	}
	
	
	/**
	 * Returns a random {@code double} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code double} in the range [0, 1), each with equal probability
	 */
	public double uniformDouble() {
		return (uniformLong() & 0x1FFFFFFFFFFFFFL) * DOUBLE_SCALER;
	}
	
	
	/**
	 * Stores random, uniformly distributed {@code byte} values into the specified array.
	 * @param b the byte array to store to
	 */
	public void uniformBytes(byte[] b) {
		uniformBytes(b, 0, b.length);
	}
	
	
	/**
	 * Stores random, uniformly distributed {@code byte} values into the specified array.
	 * @param b the byte array to store to
	 * @param off the offset into the array
	 * @param len the length of the range to store
	 */
	public void uniformBytes(byte[] b, int off, int len) {
		Assert.assertRangeInBounds(b.length, off, len);
		
		// Fill efficiently, 8 bytes at a time
		int templen = len / 8 * 8;
		for (int end = off + templen; off < end; off += 8) {
			long rand = uniformLong();
			b[off + 0] = (byte)(rand >>> 56);
			b[off + 1] = (byte)(rand >>> 48);
			b[off + 2] = (byte)(rand >>> 40);
			b[off + 3] = (byte)(rand >>> 32);
			b[off + 4] = (byte)(rand >>> 24);
			b[off + 5] = (byte)(rand >>> 16);
			b[off + 6] = (byte)(rand >>>  8);
			b[off + 7] = (byte)(rand >>>  0);
		}
		len -= templen;
		
		// Fill the last few bytes (0 to 7 iterations)
		int end = off + len;
		for (long rand = uniformLong(); off < end; off++, rand >>>= 8)
			b[off] = (byte)rand;
	}
	
}
