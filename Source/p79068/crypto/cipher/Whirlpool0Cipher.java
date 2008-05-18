package p79068.crypto.cipher;


/**
 * The internal block cipher used in the Whirlpool hash function.
 * <p>Instantiability: <em>Singleton</em></p>
 * @see Whirlpool1Cipher
 * @see WhirlpoolCipher
 */
public final class Whirlpool0Cipher extends BlockCipher {
	
	/**
	 * The singleton instance of this cipher algorithm.
	 */
	public static final Whirlpool0Cipher CIPHER = new Whirlpool0Cipher();
	
	
	
	public Cipherer newCipherer(byte[] key) {
		if (key.length != 64)
			throw new IllegalArgumentException();
		return new WhirlpoolCiphererParent(this, SUB, SUB_INV, MUL, MUL_INV, RCON, key);
	}
	
	
	/**
	 * Returns the name of this cipher algorithm: <samp>Whirlpool-0 Cipher</samp>.
	 */
	public String getName() {
		return "Whirlpool-0 Cipher";
	}
	
	
	/**
	 * Returns the key length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 */
	public int getKeyLength() {
		return 64;
	}
	
	
	/**
	 * Returns the block length of this cipher algorithm: <samp>64</samp> bytes (512 bits).
	 */
	public int getBlockLength() {
		return 64;
	}
	
	
	
	private Whirlpool0Cipher() {}
	
	
	
	private static final int ROUNDS = 10;
	
	private static final byte[] SUB;
	private static final byte[] SUB_INV;
	
	private static final byte[][] MUL;
	private static final byte[][] MUL_INV;
	
	private static final byte[][] RCON;
	
	
	static {
		if (ROUNDS < 1 || ROUNDS > 32)
			throw new AssertionError("Invalid number of rounds");
		
		SUB = makeSub();
		SUB_INV = WhirlpoolUtils.makeInverseSbox(SUB);
		
		RCON = WhirlpoolUtils.makeRoundConstants(ROUNDS, SUB);
		
		int[] c    = {0x01, 0x05, 0x09, 0x08, 0x05, 0x01, 0x03, 0x01};  // Identical to the one in Whirlpool-T
		int[] cinv = {0xB8, 0x0E, 0x67, 0x6C, 0x0A, 0xD1, 0x71, 0xE3};
		MUL     = WhirlpoolUtils.makeMultiplicationTable(c);
		MUL_INV = WhirlpoolUtils.makeMultiplicationTable(cinv);
	}
	
	
	private static byte[] makeSub() {
		int[] temp = {
			0x68, 0xD0, 0xEB, 0x2B, 0x48, 0x9D, 0x6A, 0xE4, 0xE3, 0xA3, 0x56, 0x81, 0x7D, 0xF1, 0x85, 0x9E,
			0x2C, 0x8E, 0x78, 0xCA, 0x17, 0xA9, 0x61, 0xD5, 0x5D, 0x0B, 0x8C, 0x3C, 0x77, 0x51, 0x22, 0x42,
			0x3F, 0x54, 0x41, 0x80, 0xCC, 0x86, 0xB3, 0x18, 0x2E, 0x57, 0x06, 0x62, 0xF4, 0x36, 0xD1, 0x6B,
			0x1B, 0x65, 0x75, 0x10, 0xDA, 0x49, 0x26, 0xF9, 0xCB, 0x66, 0xE7, 0xBA, 0xAE, 0x50, 0x52, 0xAB,
			0x05, 0xF0, 0x0D, 0x73, 0x3B, 0x04, 0x20, 0xFE, 0xDD, 0xF5, 0xB4, 0x5F, 0x0A, 0xB5, 0xC0, 0xA0,
			0x71, 0xA5, 0x2D, 0x60, 0x72, 0x93, 0x39, 0x08, 0x83, 0x21, 0x5C, 0x87, 0xB1, 0xE0, 0x00, 0xC3,
			0x12, 0x91, 0x8A, 0x02, 0x1C, 0xE6, 0x45, 0xC2, 0xC4, 0xFD, 0xBF, 0x44, 0xA1, 0x4C, 0x33, 0xC5,
			0x84, 0x23, 0x7C, 0xB0, 0x25, 0x15, 0x35, 0x69, 0xFF, 0x94, 0x4D, 0x70, 0xA2, 0xAF, 0xCD, 0xD6,
			0x6C, 0xB7, 0xF8, 0x09, 0xF3, 0x67, 0xA4, 0xEA, 0xEC, 0xB6, 0xD4, 0xD2, 0x14, 0x1E, 0xE1, 0x24,
			0x38, 0xC6, 0xDB, 0x4B, 0x7A, 0x3A, 0xDE, 0x5E, 0xDF, 0x95, 0xFC, 0xAA, 0xD7, 0xCE, 0x07, 0x0F,
			0x3D, 0x58, 0x9A, 0x98, 0x9C, 0xF2, 0xA7, 0x11, 0x7E, 0x8B, 0x43, 0x03, 0xE2, 0xDC, 0xE5, 0xB2,
			0x4E, 0xC7, 0x6D, 0xE9, 0x27, 0x40, 0xD8, 0x37, 0x92, 0x8F, 0x01, 0x1D, 0x53, 0x3E, 0x59, 0xC1,
			0x4F, 0x32, 0x16, 0xFA, 0x74, 0xFB, 0x63, 0x9F, 0x34, 0x1A, 0x2A, 0x5A, 0x8D, 0xC9, 0xCF, 0xF6,
			0x90, 0x28, 0x88, 0x9B, 0x31, 0x0E, 0xBD, 0x4A, 0xE8, 0x96, 0xA6, 0x0C, 0xC8, 0x79, 0xBC, 0xBE,
			0xEF, 0x6E, 0x46, 0x97, 0x5B, 0xED, 0x19, 0xD9, 0xAC, 0x99, 0xA8, 0x29, 0x64, 0x1F, 0xAD, 0x55,
			0x13, 0xBB, 0xF7, 0x6F, 0xB9, 0x47, 0x2F, 0xEE, 0xB8, 0x7B, 0x89, 0x30, 0xD3, 0x7F, 0x76, 0x82
		};
		
		// Coerce into bytes
		byte[] sub = new byte[256];
		for (int i = 0; i < sub.length; i++)
			sub[i] = (byte)temp[i];
		return sub;
	}
	
}