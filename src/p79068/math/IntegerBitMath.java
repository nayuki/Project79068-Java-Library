package p79068.math;

import p79068.Assert;


/**
 * Contains methods for math functions that deal with bits in integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 * @see LongBitMath
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
	 * Returns a contiguous group of bits extracted from the specified bit sequence and shifted down to the lowest significant bits.
	 * For example: {@code extractBits(0xCAFE, 4, 8)} yields {@code 0xAF}.
	 * @param x the input bit sequence
	 * @param bitOffset the number of least significant bits to skip (between 0 and 32)
	 * @param bitLength the number of bits to extract (between 0 and 32)
	 * throws IndexOutOfBoundsException if the offset or length lie outside the interval [0, 32]
	 */
	public static int extractBits(int x, int bitOffset, int bitLength) {
		Assert.assertRangeInBounds(32, bitOffset, bitLength);
		if (bitLength == 32)
			return x;  // bitOffset = 0, or else an exception was already thrown.
		else
			return (x >>> bitOffset) & ((1 << bitLength) - 1);
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
