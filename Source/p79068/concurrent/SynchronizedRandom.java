package p79068.concurrent;

import p79068.random.AbstractRandom;


/**
 * Wraps a {@link AbstractRandom} with a monitor.
 */
public final class SynchronizedRandom extends AbstractRandom {
	
	/** The underlying random number generator. */
	private AbstractRandom random;
	
	/** A private lock, which prevents denial-of-service attacks. */
	private Object lock = new Object();
	
	
	
	public SynchronizedRandom(AbstractRandom rand) {
		random = rand;
	}
	
	
	
	public boolean randomBoolean() {
		synchronized (lock) {
			return random.randomBoolean();
		}
	}
	
	
	public int randomInt() {
		synchronized (lock) {
			return random.randomInt();
		}
	}
	
	
	public int randomInt(int n) {
		synchronized (lock) {
			return random.randomInt(n);
		}
	}
	
	
	public long randomLong() {
		synchronized (lock) {
			return random.randomLong();
		}
	}
	
	
	public float randomFloat() {
		synchronized (lock) {
			return random.randomFloat();
		}
	}
	
	
	public double randomDouble() {
		synchronized (lock) {
			return random.randomDouble();
		}
	}
	
	
	public void randomBytes(byte[] b) {
		synchronized (lock) {
			random.randomBytes(b);
		}
	}
	
	
	public void randomBytes(byte[] b, int off, int len) {
		synchronized (lock) {
			random.randomBytes(b, off, len);
		}
	}
	
	
	public double randomGaussian() {
		synchronized (lock) {
			return random.randomGaussian();
		}
	}
	
	
	public double randomExponential() {
		synchronized (lock) {
			return random.randomExponential();
		}
	}
	
}