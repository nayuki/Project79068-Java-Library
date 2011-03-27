package p79068.random;


@SuppressWarnings("serial")
public final class JavaRandomAdapter extends java.util.Random {
	
	private AbstractRandom random;
	
	
	
	public JavaRandomAdapter(AbstractRandom rand) {
		random = rand;
	}
	
	
	
	@Override
	protected int next(int bits) {
		return random.randomInt() >>> (32 - bits);
	}
	
	@Override
	public int nextInt() {
		return random.randomInt();
	}
	
	@Override
	public long nextLong() {
		return random.randomLong();
	}
	
	@Override
	public void setSeed(long seed) {
		throw new UnsupportedOperationException();
	}
	
}