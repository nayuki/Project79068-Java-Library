package p79068.random;


/**
 * A random number generator with more output types and probability distributions.
 */
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
	
	
	
	/* Random Boolean */
	
	/**
	 * Returns a random, uniformly distributed {@code boolean} value.
	 * @return <samp>true</samp> or <samp>false</samp>, each with equal probability
	 */
	public boolean uniformBoolean() {
		return (random.uniformInt() & 1) != 0;
	}
	
	
	/**
	 * Returns a random {@code boolean} value with the specified probability of being {@code true}.
	 * @param p the probability of returning true
	 * @return <samp>true</samp> with probability {@code p}, or <samp>false</samp> with probability 1 &minus; {@code p}
	 */
	public boolean bernoulliBoolean(double p) {
		return random.uniformDouble() < p;
	}
	
	
	/* Random int32 */
	
	/**
	 * Returns a random, uniformly distributed {@code int} value.
	 * @return a value in the range of {@code int}, each with equal probability
	 */
	public int uniformInt() {
		return random.uniformInt();
	}
	
	
	/**
	 * Returns a random, uniformly distributed integer between 0 (inclusive) and {@code n} (exclusive). {@code n} must be positive.
	 * @return an integer in the range [0, {@code n}), each with equal probability
	 * @throws IllegalArgumentException if {@code n} &le; 0
	 */
	public int uniformInt(int n) {
		return random.uniformInt(n);
	}
	
	
	/**
	 * Returns a random integer from the binomial distribution with the specified number of trials and the specified success probability.
	 * @param n the number of trials
	 * @param p the success probability
	 * @return a binomially distributed integer in the range [0, {@code n}]
	 */
	public int binomial(int n, double p) {
		if (n < 0 || p < 0 || p > 1)
			throw new IllegalArgumentException();
		
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (random.uniformDouble() < p)
				count++;
		}
		return count;
	}
	
	
	/**
	 * Returns a random integer from the geometric distribution with the specified success probability. The probability of getting 0 is {@code p}, of getting 1 is (1&minus;{@code p})p, etc.
	 * @param p the success probability
	 * @return a geometrically distributed integer in the range [0, {@code Integer.MAX_VALUE}]
	 */
	public int geometric(double p) {
		if (p < 0 || p > 1)
			throw new IllegalArgumentException();
		
		int count = 0;
		while (random.uniformDouble() < p && count != Integer.MAX_VALUE)
			count++;
		return count;
	}
	
	
	/* Random int64 */
	
	/**
	 * Returns a random, uniformly distributed {@code long} value.
	 * @return a value in the range of {@code long}, each with equal probability
	 */
	public long uniformLong() {
		return random.uniformInt();
	}
	
	
	/* Random byte array */
	
	/**
	 * Stores random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void uniformBytes(byte[] b) {
		random.uniformBytes(b);
	}
	
	
	/**
	 * Stores random, uniformly distributed {@code byte} values into the specified array.
	 */
	public void uniformBytes(byte[] b, int off, int len) {
		random.uniformBytes(b, off, len);
	}
	
	
	/* Random float32 */
	
	/**
	 * Returns a random {@code float} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code float} in the range [0, 1), each with equal probability
	 */
	public float uniformFloat() {
		return (random.uniformInt() & 0xFFFFFF) * floatScaler;
	}
	
	
	/* Random float64 */
	
	/**
	 * Returns a random {@code double} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code double} in the range [0, 1), each with equal probability
	 */
	public double uniformDouble() {
		return random.uniformDouble();
	}
	
	
	/**
	 * Returns a random {@code double} with an exponential distribution of mean 1.
	 * <p>To obtain a exponentially distributed value with mean {@code lambda}, use this expression: {@code exponential() * lambda}</p>
	 * @return a {@code double} with an exponential distribution of mean 1.
	 */
	public double exponential() {
		return -Math.log(random.uniformDouble());
	}
	
	
	/**
	 * Returns a random {@code double} with a Gaussian (<q>normal</q>) distribution of mean 0.0 and standard deviation 1.0.
	 * <p>To obtain a Gaussian-distributed value with mean {@code m} and standard deviation {@code s}, use this expression: {@code gaussian()*s + m}</p>
	 * <p>Note that the probability of producing a number outside of [&minus;10, 10] is 10<sup>&minus;23</sup>; the probability of producing a number outside of [&minus;15, 15] is 10<sup>&minus;50</sup> (i.e., effectively impossible). (Assuming that the underlying random number generator is unbiased.)</p>
	 * @return a {@code double} with a Gaussian distribution of mean 0.0 and standard deviation 1.0
	 */
	public double gaussian() {  // Uses the Box-Muller transform
		if (!hasNextGaussian) {
			double x, y;
			double magsqr;
			// Use rejection sampling to pick a point uniformly distributed in the unit circle
			do {
				x = random.uniformDouble() * 2 - 1;
				y = random.uniformDouble() * 2 - 1;
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
	
}
