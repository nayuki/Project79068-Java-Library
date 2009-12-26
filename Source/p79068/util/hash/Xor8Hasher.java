package p79068.util.hash;

import p79068.lang.BoundsChecker;


final class Xor8Hasher extends Hasher {
	
	/**
	 * The running XOR of all the bytes seen. Note that the top 24 bits are irrelevant.
	 */
	private int xor;
	
	
	
	Xor8Hasher(Xor8 hashFunc) {
		super(hashFunc);
		xor = 0;
	}
	
	
	
	@Override
	public void update(byte b) {
		xor ^= b;
	}
	
	
	@Override
	public void update(byte[] b, int off, int len) {
		BoundsChecker.check(b.length, off, len);
		for (int i = off, end = off + len; i < end; i++)
			xor ^= b[i];
	}
	
	
	@Override
	public HashValue getHash() {
		return new HashValue(new byte[]{(byte)xor});
	}
	
}