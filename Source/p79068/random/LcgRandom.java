package p79068.random;

import p79068.math.IntegerMath;
import p79068.math.LongBitMath;


/**
 * A pseudo-random number generator (PRNG) using the linear congruential generator (LCG) algorithm.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Unsafe</em></p>
 * <p>The general iteration is the following formula: <var>x</var><sub><var>i</var>+1</sub> = (<var>a</var> <var>x</var><sub><var>i</var></sub> + <var>b</var>) mod 2<sup><var>m</var></sup>, where <var>x</var> is the sequence of states, <var>a</var> is the multiplier, <var>b</var> is the increment, and <var>m</var> is the log of the modulus to the base 2.</p>
 * <p>Use the lower order bits produced by this generator with extreme caution.</p>
 */
public final class LcgRandom extends AbstractRandom {
	
	private long a;
	private long b;
	private int shift;  // Equal to log2(m) - 32
	
	private long x;
	
	
	
	/**
	 * Constructs an LCG PRNG with an arbitrary seed and arbitrary default parameters.
	 */
	public LcgRandom() {
		this(System.currentTimeMillis() ^ LongBitMath.reverse(System.nanoTime()));
	}
	
	
	/**
	 * Constructs an LCG PRNG with the specified seed and arbitrary default parameters.
	 */
	public LcgRandom(long seed) {
		this(0x5DEECE66DL, 0xBL, 48, seed);
	}
	
	
	/**
	 * Constructs an LCG PRNG with the specified parameters and an arbitrary seed.
	 * <p>Some well-known parameter sets:</p>
	 * <table>
	 *  <col align="right"/>
	 *  <col align="right"/>
	 *  <col align="right"/>
	 *  <thead>
	 *   <tr>
	 *    <th>Multiplier (<var>a</var>)</th>
	 *    <th>Increment (<var>b</var>)</th>
	 *    <th>Log2 of Modulus (<var>m</var>)</th>
	 *   </tr>
	 *  </thead>
	 *  <tbody>
	 *   <tr><td>69069</td><td>1</td><td>32</td></tr>
	 *   <tr><td>1664525</td><td>1013904223</td><td>32</td></tr>
	 *   <tr><td>2147001325</td><td>715136305</td><td>32</td></tr>
	 *   <tr><td>1220703125</td><td>0</td><td>35</td></tr>
	 *   <tr><td>30517578125</td><td>7261067085</td><td>35</td></tr>
	 *   <tr><td>25214903917</td><td>11</td><td>48</td></tr>
	 *   <tr><td>302875106592253</td><td>0</td><td>59</td></tr>
	 *   <tr><td>2862933555777941757</td><td>3037000493</td><td>64</td></tr>
	 *  </tbody>
	 * </table>
	 * <p>For LCGs where <var>b</var> is zero, the seed must not be zero.</p>
	 * @param a the multiplier
	 * @param b the increment
	 * @param m the log of the modulus to the base 2, which must be in the range [32,64]
	 */
	public LcgRandom(long a, long b, int m) {
		this(a, b, m, System.currentTimeMillis());
	}
	
	
	/**
	 * Constructs an LCG PRNG with the specified parameters and seed.
	 */
	public LcgRandom(long a, long b, int m, long seed) {
		if (m < 32 || m > 64)
			throw new IllegalArgumentException();
		this.a = a;
		this.b = b;
		this.shift = m - 32;
		this.x = seed;
	}
	
	
	
	@Override
	public int uniformInt() {
		x = x * a + b;
		return (int)(x >>> shift);
	}
	
	
	@Override
	public long uniformLong() {
		return (long)uniformInt() << 32 | (uniformInt() & 0xFFFFFFFFL);
	}
	
	
	@Override
	public int uniformInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		if (IntegerMath.isPowerOf2(n))
			return (int)(((uniformInt() & 0xFFFFFFFFL) * n) >>> 32);  // Shift the wanted number of upper bits into the high region, then shift the high region down.
		else {  // Unbiased
			int random;
			int result;
			do {
				random = uniformInt() >>> 1;  // In the range [0, 2^31)
				result = random % n;
			} while (random - result + (n - 1) < 0);
			return result;
		}
	}
	
}