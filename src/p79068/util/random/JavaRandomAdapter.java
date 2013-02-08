package p79068.util.random;


@SuppressWarnings("serial")
public final class JavaRandomAdapter extends java.util.Random {
	
	private Random random;
	
	private boolean constructed;
	
	
	
	public JavaRandomAdapter(Random rand) {
		random = rand;
		constructed = true;
	}
	
	
	
	@Override
	protected int next(int bits) {
		return random.uniformInt() >>> (32 - bits);
	}
	
	@Override
	public int nextInt() {
		return random.uniformInt();
	}
	
	@Override
	public long nextLong() {
		return random.uniformLong();
	}
	
	
	@Override
	public void setSeed(long seed) {
		// java.util.Random's constructors always call setSeed(), so we must avoid throwing an exception at construction time
		if (constructed)
			throw new UnsupportedOperationException();
	}
	
}
