package p79068.crypto.hash;

import p79068.util.hash.HashFunction;
import p79068.util.hash.Hasher;


/**
 * The MD5 hash function. It is described in RFC 1321.
 * <p>Mutability: <em>Immutable</em><br>
 *  Instantiability: <em>Singleton</em></p>
 * @see HashFunction
 */
public final class Md5 extends BlockHashFunction {
	
	/**
	 * The singleton instance of the MD5 hash function.
	 */
	public final static Md5 FUNCTION = new Md5();
	
	
	
	/**
	 * Returns a new hasher of this hash function.
	 * @return a new hasher of this hash function
	 */
	@Override
	public Hasher newHasher() {
		return new BlockHasher(this, new Md5Hasher());
	}
	
	
	/**
	 * Returns the name of this hash function: <code>MD5</code>.
	 * @return <code>"MD5"</code>
	 */
	@Override
	public String getName() {
		return "MD5";
	}
	
	
	/**
	 * Returns the length of hash values produced by this hash function: <code>16</code> bytes (128 bits).
	 * @return <code>16</code>
	 */
	@Override
	public int getHashLength() {
		return 16;
	}
	
	
	/**
	 * Returns the block length of this hash function: <code>64</code> bytes (512 bits).
	 * @return <code>64</code>
	 */
	@Override
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Md5() {}
	
}