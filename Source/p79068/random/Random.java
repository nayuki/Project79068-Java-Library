package p79068.random;

import p79068.concurrent.SynchronizedRandom;



public interface Random {
	
	/**
	 * A default, thread-safe instance provided for convenience.
	 */
	public static final Random DEFAULT = new SynchronizedRandom(new MersenneTwister());
	
	
	
	/**
	 * Returns a random, uniformly distributed {@code int} value.
	 * @return a value in the range of {@code int}, each with equal probability
	 */
	public int randomInt();
	
	
	/**
	 * Returns a random, uniformly distributed integer between 0 (inclusive) and {@code n} (exclusive). {@code n} must be positive.
	 * @return an integer in the range [0, {@code n}), each with equal probability
	 * @throws IllegalArgumentException if {@code n} &le; 0
	 */
	public int randomInt(int n);
	
	
	/**
	 * Returns a random, uniformly distributed {@code long} value.
	 * @return a value in the range of {@code long}, each with equal probability
	 */
	public long randomLong();
	
	
	/**
	 * Returns a random {@code double} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code double} in the range [0, 1), each with equal probability
	 */
	public double randomDouble();
	
	
	/**
	 * Places random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void randomBytes(byte[] b);
	
	
	/**
	 * Places random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void randomBytes(byte[] b, int off, int len);
	
}