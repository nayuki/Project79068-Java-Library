package p79068.math;

import p79068.Assert;


/**
 * Contains methods for math functions that deal with bits in long integers.
 * <p>Instantiability: <em>Not applicable</em></p>
 * @see IntegerBitMath
 */
public final class LongBitMath {
	
	/**
	 * Returns the value of the bit at the specified index of the specified bit sequence.
	 * @param x the bit sequence to test
	 * @param bitIndex the index of the bit in {@code x} to test
	 * @return {@code 0} or {@code 1}
	 */
	public static int extractBit(long x, int bitIndex) {
		Assert.assertIndexInBounds(64, bitIndex);
		return (int)(x >>> bitIndex) & 1;
	}
	
	
	/**
	 * Returns a contiguous group of bits extracted from the specified bit sequence and shifted down to the lowest significant bits.
	 * For example: {@code extractBits(0xC0FFEEL, 8, 16)} yields {@code 0xC0FFL}.
	 * @param x the input bit sequence
	 * @param bitOffset the number of least significant bits to skip (between 0 and 64)
	 * @param bitLength the number of bits to extract (between 0 and 64)
	 * throws IndexOutOfBoundsException if the offset or length lie outside the interval [0, 64]
	 */
	public static long extractBits(long x, int bitOffset, int bitLength) {
		Assert.assertRangeInBounds(64, bitOffset, bitLength);
		if (bitLength == 64)
			return x;  // bitOffset = 0, or else an exception was already thrown.
		else
			return (x >>> bitOffset) & ((1L << bitLength) - 1);
	}
	
	
	public static byte[] toBytesBigEndian(long[] ain) {
		byte[] aout = new byte[ain.length * 8];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 8] >>> ((7 - i % 8) * 8));
		return aout;
	}
	
	
	public static byte[] toBytesLittleEndian(long[] ain) {
		byte[] aout = new byte[ain.length * 8];
		for (int i = 0; i < aout.length; i++)
			aout[i] = (byte)(ain[i / 8] >>> (i % 8 * 8));
		return aout;
	}
	
	
	public static long[] fromBytesBigEndian(byte[] ain) {
		if (ain.length % 8 != 0)
			throw new IllegalArgumentException("Length is not multiple of 8");
		long[] aout = new long[ain.length / 8];
		for (int i = 0; i < ain.length; i++)
			aout[i / 8] |= (ain[i] & 0xFFL) << ((7 - i % 8) * 8);
		return aout;
	}
	
	
	public static long[] fromBytesLittleEndian(byte[] ain) {
		if (ain.length % 8 != 0)
			throw new IllegalArgumentException("Length is not multiple of 8");
		long[] aout = new long[ain.length / 8];
		for (int i = 0; i < ain.length; i++)
			aout[i / 8] |= (ain[i] & 0xFFL) << (i % 8 * 8);
		return aout;
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private LongBitMath() {}
	
}
