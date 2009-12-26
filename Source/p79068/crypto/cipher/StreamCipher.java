/*
 * A stream cipher must be of the form "ciphertext = plaintext XOR pseudorandom" because it is supposed to encrypt each bit independently, not each byte.
 * Therefore, encryption and decryption are the same operation.
 */


package p79068.crypto.cipher;


/**
 * An abstract stream cipher.
 * <p>Note that a stream cipher's state must only depend on the key and the position in the pseudo-random sequence. It must not depend on any data that was encrypted or decrypted.</p>
 * @see Cipher
 */
public abstract class StreamCipher extends Cipher {
	
	@Override
	public abstract StreamCipherer newCipherer(byte[] key);
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>1</samp> byte.
	 */
	@Override
	public final int getBlockLength() {
		return 1;
	}
	
}