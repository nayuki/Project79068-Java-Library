package p79068.math;

import p79068.Assert;


/**
 * Contains methods for math functions that deal with bits in integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class IntegerBitMath {
	
	/**
	 * Returns the value of the bit at the specified index of the specified bit sequence.
	 * @param x the bit sequence to test
	 * @param bitIndex the index of the bit in {@code x} to test
	 * @return {@code 0} or {@code 1}
	 */
	public static int extractBit(int x, int bitIndex) {
		Assert.assertIndexInBounds(32, bitIndex);
		return (x >>> bitIndex) & 1;
	}
	
	
	/**
	 * Returns a contiguous group of bits extracted from the specified bit sequence and placed xxx.
	 * For example: {@code extractBits(0xCAFE, 4, 8)} yields {@code 0xAF}.
	 */
	public static int extractBits(int x, int bitOffset, int bitLength) {
		Assert.assertRangeInBounds(32, bitOffset, bitLength);
		if (bitLength == 32)
			return x;  // bitOffset = 0, or else an exception was already thrown.
		else
			return (x >>> bitOffset) & ((1 << bitLength) - 1);
	}
	
	
	/**
	 * Returns the reverse of the specified bit sequence.
	 * @param x the bit sequence to reverse
	 * @return the reverse of {@code x}
	 */
	public static int reverseBits(int x) {
		x = (x & 0x55555555) <<  1 | ((x >>>  1) & 0x55555555);
		x = (x & 0x33333333) <<  2 | ((x >>>  2) & 0x33333333);
		x = (x & 0x0F0F0F0F) <<  4 | ((x >>>  4) & 0x0F0F0F0F);
		x = (x & 0x00FF00FF) <<  8 | ((x >>>  8) & 0x00FF00FF);
		x = (x & 0x0000FFFF) << 16 | ((x >>> 16) & 0x0000FFFF);
		return x;
	}
	
	
	/**
	 * Returns the number of bits set to {@code 1} in the specified integer. Also known as the Hamming weight or population count function.
	 * @return the number of bits set to {@code 1}, between {@code 0} (inclusive) and {@code 32} (inclusive)
	 */
	public static int countOnes(int x) {
		x = ((x >>>  1) & 0x55555555) + (x & 0x55555555);
		x = ((x >>>  2) & 0x33333333) + (x & 0x33333333);
		x = ((x >>>  4) & 0x0F0F0F0F) + (x & 0x0F0F0F0F);
		x = ((x >>>  8) & 0x00FF00FF) + (x & 0x00FF00FF);
		x = ((x >>> 16) & 0x0000FFFF) + (x & 0x0000FFFF);
		return x;
	}
	
	
	/**
	 * Returns the specified bit sequence rotated to the left by the specified number of places. The shift value is taken modulo 32.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the left, taken modulo 32
	 * @return {@code x} rotated to the left by {@code shift} places
	 */
	public static int rotateLeft(int x, int shift) {
		return x << shift | x >>> (32 - shift);
	}
	
	
	/**
	 * Returns the specified bit sequence rotated to the right by the specified number of places. The shift value is taken modulo 32.
	 * @param x the bit sequence to rotate
	 * @param shift the number of places to rotate to the right, taken modulo 32
	 * @return {@code x} rotated to the right by {@code shift} places
	 */
	public static int rotateRight(int x, int shift) {
		return x << (32 - shift) | x >>> shift;
	}
	
	
	public static int swapByteEndian(int x) {
		x = (x & 0x00FF00FF) <<  8 | ((x >>>  8) & 0x00FF00FF);
		x = (x & 0x0000FFFF) << 16 | ((x >>> 16) & 0x0000FFFF);
		return x;
	}
	
	
	
	public static byte[] toBytesBigEndian(int[] ain) {
		byte[] aout = new byte[ain.length * 4];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 4] >>> ((3 - i % 4) * 8));
		return aout;
	}
	
	
	public static byte[] toBytesLittleEndian(int[] ain) {
		byte[] aout = new byte[ain.length * 4];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 4] >>> (i % 4 * 8));
		return aout;
	}
	
	
	public static int[] fromBytesBigEndian(byte[] ain) {
		if (ain.length % 4 != 0)
			throw new IllegalArgumentException("Length is not multiple of 4");
		int[] aout = new int[ain.length / 4];
		for (int i = 0; i < ain.length; i++)
			aout[i / 4] |= (ain[i] & 0xFF) << ((3 - i % 4) * 8);
		return aout;
	}
	
	
	public static int[] fromBytesLittleEndian(byte[] ain) {
		if (ain.length % 4 != 0)
			throw new IllegalArgumentException("Length is not multiple of 4");
		int[] aout = new int[ain.length / 4];
		for (int i = 0; i < ain.length; i++)
			aout[i / 4] |= (ain[i] & 0xFF) << (i % 4 * 8);
		return aout;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private IntegerBitMath() {}
	
}
