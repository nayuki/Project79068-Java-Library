package p79068.concurrent;

import p79068.random.AbstractRandom;
import p79068.random.Random;


/**
 * Wraps a {@link Random} with a monitor.
 */
public final class SynchronizedRandom extends AbstractRandom {
	
	/** The underlying random number generator. */
	private Random random;
	
	/** A private lock, which prevents denial-of-service attacks. */
	private Object lock = new Object();
	
	
	
	public SynchronizedRandom(Random rand) {
		random = rand;
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
	
}