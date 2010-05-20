package p79068.crypto.hash;

import p79068.crypto.Zeroizable;
import p79068.lang.NullChecker;
import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * An HMAC (keyed hash message authentication code) hash function.
 * <p>The HMAC specification calls for a block hash function because the initial state of an HMAC instance need not retain incomplete message bytes.</p>
 * <p>The underlying function's block length must not be smaller than its output hash value length.</p>
 * <p>Mutability: <em>Mutable</em></p>
 * @see HashFunction
 */
public final class Hmac extends HashFunction implements Zeroizable {
	
	private Hasher inner;
	private Hasher outer;
	
	
	
	/**
	 * Constructs an HMAC algorithm with the specified underlying block hash function and secret key.
	 * @param hashFunc the underlying block hash function
	 * @param key the secret key
	 */
	public Hmac(BlockHashFunction hashFunc, byte[] key) {
		NullChecker.check(hashFunc, key);
		if (hashFunc.getBlockLength() < hashFunc.getHashLength())
			throw new IllegalArgumentException();
		inner = hashFunc.newHasher();
		outer = hashFunc.newHasher();
		key = key.clone();
		key = preprocessKey(hashFunc, key);
		for (int i = 0; i < key.length; i++)
			key[i] ^= 0x36;
		inner.update(key);
		for (int i = 0; i < key.length; i++)
			key[i] ^= 0x6A;  // Un-XOR by 0x36, then XOR by 0x5C
		outer.update(key);
	}
	
	
	
	/**
	 * Insert description here.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public Hasher newHasher() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		return new HmacHasher(this, inner, outer);
	}
	
	
	/**
	 * Returns the name of this hash function.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public String getName() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		return String.format("HMAC-%s", outer.getHashFunction().getName());
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: the length produced by the underlying hash function.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	@Override
	public int getHashLength() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		return outer.getHashFunction().getHashLength();
	}
	
	
	/**
	 * Insert description here.
	 * @throws IllegalStateException if this object has been zeroized
	 */
	public void zeroize() {
		if (outer == null)
			throw new IllegalStateException("Already zeroized");
		if (inner instanceof Zeroizable)
			((Zeroizable)inner).zeroize();
		if (outer instanceof Zeroizable)
			((Zeroizable)outer).zeroize();
		inner = null;
		outer = null;
	}
	
	
	
	private static byte[] preprocessKey(BlockHashFunction hashFunc, byte[] key) {
		if (key.length > hashFunc.getBlockLength())
			key = hashFunc.getHash(key).toBytes();
		byte[] blocksizedkey = new byte[hashFunc.getBlockLength()];
		System.arraycopy(key, 0, blocksizedkey, 0, key.length);
		return blocksizedkey;
	}
	
}