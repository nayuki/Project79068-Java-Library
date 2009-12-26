package p79068.crypto.cipher;


/**
 * The RC2 block cipher. It is described in RFC 2268.
 * <p>Key lengths: 8 to 1024 bits at multiples of 8 bits</p>
 * <p>Block length: 64 bits (8 bytes)</p>
 */
public final class Rc2 extends BlockCipher {
	
	private int effectiveKeyLength;  // In bits
	private int keyLength;  // In bytes
	
	
	
	public Rc2(int effectiveKeyLength, int keyLength) {
		if (keyLength < 1 || keyLength > 128)
			throw new IllegalArgumentException();
		this.effectiveKeyLength = effectiveKeyLength;
		this.keyLength = keyLength;
	}
	
	
	
	@Override
	public Cipherer newCipherer(byte[] key) {
		if (key.length != keyLength)
			throw new IllegalArgumentException();
		return new Rc2Cipherer(this, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>RC2 (<var>m</var>-bit effective key length, <var>n</var>-bit key)</samp>.
	 */
	@Override
	public String getName() {
		return String.format("RC2 (%d-bit effective key length, %d-bit key)", effectiveKeyLength, keyLength * 8);
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm.
	 */
	@Override
	public int getKeyLength() {
		return keyLength;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>8</samp> bytes (64 bits).
	 * @return <code>8</code>
	 */
	@Override
	public int getBlockLength() {
		return 8;
	}
	
	
	public int getEffectiveKeyLength() {
		return effectiveKeyLength;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Rc2))
			return false;
		Rc2 cipher = (Rc2)obj;
		return effectiveKeyLength == cipher.effectiveKeyLength && keyLength == cipher.keyLength;
	}
	
	
	@Override
	public int hashCode() {
		return effectiveKeyLength << 16 | keyLength;
	}
	
}