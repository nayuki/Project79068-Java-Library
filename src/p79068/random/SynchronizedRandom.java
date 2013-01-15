package p79068.random;



/**
 * Wraps a {@link Random} with a monitor.
 */
public final class SynchronizedRandom implements Random {
	
	/** The underlying random number generator. */
	private final Random random;
	
	/** A private lock, which prevents denial-of-service attacks. */
	private final Object lock;
	
	
	
	public SynchronizedRandom(Random rand) {
		random = rand;
		lock = new Object();
	}
	
	
	
	public int uniformInt() {
		synchronized (lock) {
			return random.uniformInt();
		}
	}
	
	
	public int uniformInt(int n) {
		synchronized (lock) {
			return random.uniformInt(n);
		}
	}
	
	
	public long uniformLong() {
		synchronized (lock) {
			return random.uniformLong();
		}
	}
	
	
	public double uniformDouble() {
		synchronized (lock) {
			return random.uniformDouble();
		}
	}
	
	
	public void uniformBytes(byte[] b) {
		synchronized (lock) {
			random.uniformBytes(b);
		}
	}
	
	
	public void uniformBytes(byte[] b, int off, int len) {
		synchronized (lock) {
			random.uniformBytes(b, off, len);
		}
	}
	
}
