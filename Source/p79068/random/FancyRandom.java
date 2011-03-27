package p79068.random;


public final class FancyRandom implements Random {
	
	/**
	 * Multiplying a 24-bit integer with this constant yields a {@code float} in [0, 1). This value is chosen so that all the mantissa bits in the {@code float} may be non-zero when the magnitude is in [0.5, 1).
	 */
	private static final float floatScaler = 1.0F / (1 << 24);
	
	
	
	private final Random random;
	
	private boolean hasNextGaussian;
	private double nextGaussian;
	
	
	
	public FancyRandom(Random rand) {
		random = rand;
		hasNextGaussian = false;
		nextGaussian = Double.NaN;
	}
	
	
	
	/**
	 * Returns a random, uniformly distributed {@code boolean} value.
	 * @return <samp>true</samp> or <samp>false</samp>, each with equal probability
	 */
	public boolean randomBoolean() {
		return (random.randomInt() & 1) != 0;
	}
	
	
	/**
	 * Returns a random, uniformly distributed {@code int} value.
	 * @return a value in the range of {@code int}, each with equal probability
	 */
	public int randomInt() {
		return random.randomInt();
	}
	
	
	/**
	 * Returns a random, uniformly distributed integer between 0 (inclusive) and {@code n} (exclusive). {@code n} must be positive.
	 * @return an integer in the range [0, {@code n}), each with equal probability
	 * @throws IllegalArgumentException if {@code n} &le; 0
	 */
	public int randomInt(int n) {
		return random.randomInt(n);
	}
	
	
	/**
	 * Returns a random, uniformly distributed {@code long} value.
	 * @return a value in the range of {@code long}, each with equal probability
	 */
	public long randomLong() {
		return random.randomInt();
	}
	
	
	/**
	 * Returns a random {@code float} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code float} in the range [0, 1), each with equal probability
	 */
	public float randomFloat() {
		return (random.randomInt() & 0xFFFFFF) * floatScaler;
	}
	
	
	/**
	 * Returns a random {@code double} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code double} in the range [0, 1), each with equal probability
	 */
	public double randomDouble() {
		return random.randomDouble();
	}
	
	
	public void randomBytes(byte[] b) {
		random.randomBytes(b);
	}
	
	
	/**
	 * Places random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void randomBytes(byte[] b, int off, int len) {
		random.randomBytes(b, off, len);
	}
	
	
	/**
	 * Returns a random {@code double} with a Gaussian (<q>normal</q>) distribution of mean 0.0 and standard deviation 1.0.
	 * <p>To obtain a Gaussian-distributed value with mean {@code m} and standard deviation {@code s}, use this expression: {@code randomGaussian()*s + m}</p>
	 * <p>Note that the probability of producing a number outside of [&minus;10, 10] is 10<sup>&minus;23</sup>; the probability of producing a number outside of [&minus;15, 15] is 10<sup>&minus;50</sup> (i.e., effectively impossible). (Assuming that the underlying random number generator is unbiased.)</p>
	 * @return a {@code double} with a Gaussian distribution of mean 0.0 and standard deviation 1.0
	 */
	public double randomGaussian() {  // Uses the Box-Muller transform
		if (!hasNextGaussian) {
			double x, y;
			double magsqr;
			// Use rejection sampling to pick a point uniformly distributed in the unit circle
			do {
				x = random.randomDouble() * 2 - 1;
				y = random.randomDouble() * 2 - 1;
				magsqr = x * x + y * y;
			} while (magsqr >= 1 || magsqr == 0);
			double temp = Math.sqrt(-2 * Math.log(magsqr) / magsqr);
			nextGaussian = y * temp;
			hasNextGaussian = true;
			return x * temp;
		} else {
			hasNextGaussian = false;
			return nextGaussian;
		}
	}
	
	
	/**
	 * Returns a random {@code double} with an exponential distribution of mean 1.
	 * <p>To obtain a exponentially distributed value with mean {@code lambda}, use this expression: {@code randomExponential() * lambda}</p>
	 * @return a {@code double} with an exponential distribution of mean 1.
	 */
	public double randomExponential() {
		return -Math.log(random.randomDouble());
	}
	
}