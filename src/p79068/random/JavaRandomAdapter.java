package p79068.random;


@SuppressWarnings("serial")
public final class JavaRandomAdapter extends java.util.Random {
	
	private Random random;
	
	
	
	public JavaRandomAdapter(Random rand) {
		random = rand;
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
		throw new UnsupportedOperationException();
	}
	
}
