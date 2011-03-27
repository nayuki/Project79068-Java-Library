package p79068.random;


public interface FancyRandom extends Random {
	
	/**
	 * Returns a random, uniformly distributed {@code boolean} value.
	 * @return <samp>true</samp> or <samp>false</samp>, each with equal probability
	 */
	public boolean randomBoolean();
	
	
	/**
	 * Returns a random {@code float} value uniformly distributed between 0.0 (inclusive) and 1.0 (exclusive). The granularity is unspecified.
	 * @return a {@code float} in the range [0, 1), each with equal probability
	 */
	public float randomFloat();
	
	
	/**
	 * Returns a random {@code double} with a Gaussian (<q>normal</q>) distribution of mean 0.0 and standard deviation 1.0.
	 * <p>To obtain a Gaussian-distributed value with mean {@code m} and standard deviation {@code s}, use this expression: {@code randomGaussian()*s + m}</p>
	 * <p>Note that the probability of producing a number outside of [&minus;10, 10] is 10<sup>&minus;23</sup>; the probability of producing a number outside of [&minus;15, 15] is 10<sup>&minus;50</sup> (i.e., effectively impossible). (Assuming that the underlying random number generator is unbiased.)</p>
	 * @return a {@code double} with a Gaussian distribution of mean 0.0 and standard deviation 1.0
	 */
	public double randomGaussian();
	
	
	/**
	 * Returns a random {@code double} with an exponential distribution of mean 1.
	 * <p>To obtain a exponentially distributed value with mean {@code lambda}, use this expression: {@code randomExponential() / lambda}</p>
	 * @return a {@code double} with an exponential distribution of mean 1.
	 */
	public double randomExponential();
	
}