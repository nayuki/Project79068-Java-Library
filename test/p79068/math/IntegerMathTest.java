package p79068.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import p79068.util.random.Random;


public final class IntegerMathTest {
	
	private static Random RANDOM = Random.DEFAULT;
	
	
	
	// Basic operations
	
	@Test public void testCheckedAdd() {
		assertEquals(3, IntegerMath.checkedAdd(1, 2));
		assertEquals(-238, IntegerMath.checkedAdd(-980, 742));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedAddInvalid0() {
		IntegerMath.checkedAdd(2147483647, 1);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedAddInvalid1() {
		IntegerMath.checkedAdd(2147483600, 1000);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedAddInvalid2() {
		IntegerMath.checkedAdd(-2147483648, -1);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedAddInvalid3() {
		IntegerMath.checkedAdd(-2147483000, -10000);
	}
	
	
	@Test public void testCheckedSubtract() {
		assertEquals(-2, IntegerMath.checkedSubtract(5, 7));
		assertEquals(-1722, IntegerMath.checkedSubtract(-980, 742));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedSubtractInvalid0() {
		IntegerMath.checkedSubtract(-2147483648, 1);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedSubtractInvalid1() {
		IntegerMath.checkedSubtract(-2147483648, 2147483647);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedSubtractInvalid2() {
		IntegerMath.checkedSubtract(2147483000, -10000);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedSubtractInvalid3() {
		IntegerMath.checkedSubtract(-2147483000, 10000);
	}
	
	
	@Test public void testCheckedMultiply() {
		assertEquals(6, IntegerMath.checkedMultiply(2, 3));
		assertEquals(-3976, IntegerMath.checkedMultiply(71, -56));
		assertEquals(-2147483648, IntegerMath.checkedMultiply(-2147483648, 1));
		assertEquals(-2147483648, IntegerMath.checkedMultiply(1073741824, -2));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedMultiplyInvalid0() {
		IntegerMath.checkedMultiply(-2147483648, -1);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedMultiplyInvalid1() {
		IntegerMath.checkedMultiply(1073741824, 2);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedMultiplyInvalid2() {
		IntegerMath.checkedMultiply(46341, -46341);
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedMultiplyInvalid3() {
		IntegerMath.checkedMultiply(-123456, -67890);
	}
	
	
	@Test public void testCheckedDivide() {
		assertEquals(2, IntegerMath.checkedDivide(2, 1));
		assertEquals(1, IntegerMath.checkedDivide(5, 3));
		assertEquals(-1, IntegerMath.checkedDivide(-5, 3));
		assertEquals(0, IntegerMath.checkedDivide(1, -4));
		assertEquals(-2147483647, IntegerMath.checkedDivide(2147483647, -1));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testCheckedDivideInvalid() {
		IntegerMath.checkedDivide(-2147483648, -1);
	}
	
	
	@Test public void testDivideUnsigned() {
		assertEquals(0, IntegerMath.divideUnsigned(0, 1));
		assertEquals(1 << 30, IntegerMath.divideUnsigned(1 << 30, 1));
		assertEquals((1 << 31) - 1, IntegerMath.divideUnsigned((1 << 31) - 1, 1));
		assertEquals((1 << 31) + 0, IntegerMath.divideUnsigned((1 << 31) + 0, 1));
		assertEquals((1 << 31) + 1, IntegerMath.divideUnsigned((1 << 31) + 1, 1));
		assertEquals(-1, IntegerMath.divideUnsigned(-1, 1));
		assertEquals((1 << 31) - 1, IntegerMath.divideUnsigned(-1, 2));
		assertEquals(1 << 30, IntegerMath.divideUnsigned(1 << 31, 2));
		assertEquals(858993459, IntegerMath.divideUnsigned(-1, 5));
		assertEquals(613566756, IntegerMath.divideUnsigned(-1, 7));
		assertEquals(0, IntegerMath.divideUnsigned(1 << 30, 1 << 31));
		assertEquals(0, IntegerMath.divideUnsigned(1 << 31, (1 << 31) + 1));
		assertEquals(1, IntegerMath.divideUnsigned((1 << 31) + 2, (1 << 31) + 1));
	}
	
	
	@Test public void testDivideUnsignedRandomly() {
		for (int i = 0; i < 1000; i++) {
			int xbits = RANDOM.uniformInt(33);
			int ybits = RANDOM.uniformInt(32) + 1;  // Prevent division by 0
			int x = 0;
			if (xbits > 0) {
				x = 1 << (xbits - 1);
				x |= RANDOM.uniformInt() & (x - 1);
			}
			int y = 1 << (ybits - 1);
			y |= RANDOM.uniformInt() & (y - 1);
			assertEquals((int)((x & 0xFFFFFFFFL) / (y & 0xFFFFFFFFL)), IntegerMath.divideUnsigned(x, y));
		}
	}
	
	
	@Test public void testDivideAndFloor() {
		assertEquals(3, IntegerMath.divideAndFloor(3, 1));
		assertEquals(-2, IntegerMath.divideAndFloor(-2, 1));
		assertEquals(2, IntegerMath.divideAndFloor(5, 2));
		assertEquals(3, IntegerMath.divideAndFloor(7, 2));
		assertEquals(0, IntegerMath.divideAndFloor(1, 3));
		assertEquals(0, IntegerMath.divideAndFloor(2, 3));
		assertEquals(-3, IntegerMath.divideAndFloor(-5, 2));
		assertEquals(-4, IntegerMath.divideAndFloor(-7, 2));
		assertEquals(-1, IntegerMath.divideAndFloor(-1, 3));
		assertEquals(-1, IntegerMath.divideAndFloor(-2, 3));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testDivideAndFloorInvalid() {
		IntegerMath.divideAndFloor(-2147483648, -1);
	}
	
	
	@Test public void testMod() {
		assertEquals(0, IntegerMath.mod(8, 4));
		assertEquals(3, IntegerMath.mod(7, 4));
		assertEquals(1, IntegerMath.mod(-7, 4));
		assertEquals(0, IntegerMath.mod(8, -4));
		assertEquals(-1, IntegerMath.mod(7, -4));
		assertEquals(-3, IntegerMath.mod(-7, -4));
		assertEquals(-1, IntegerMath.mod(Integer.MAX_VALUE, Integer.MIN_VALUE));
	}
	
	
	
	// Simple functions
	
	@Test public void testSign() {
		assertEquals( 0, IntegerMath.sign(0));
		
		assertEquals(+1, IntegerMath.sign(1));
		assertEquals(+1, IntegerMath.sign(2));
		assertEquals(+1, IntegerMath.sign(3));
		assertEquals(+1, IntegerMath.sign(700));
		assertEquals(+1, IntegerMath.sign(Integer.MAX_VALUE - 1));
		assertEquals(+1, IntegerMath.sign(Integer.MAX_VALUE));
		
		assertEquals(-1, IntegerMath.sign(-1));
		assertEquals(-1, IntegerMath.sign(-2));
		assertEquals(-1, IntegerMath.sign(-3));
		assertEquals(-1, IntegerMath.sign(-50));
		assertEquals(-1, IntegerMath.sign(Integer.MIN_VALUE + 1));
		assertEquals(-1, IntegerMath.sign(Integer.MIN_VALUE));
	}
	
	
	@Test public void testCompare() {
		assertTrue(IntegerMath.compare(0, 0) == 0);
		assertTrue(IntegerMath.compare(7, 7) == 0);
		assertTrue(IntegerMath.compare(3, 5) < 0);
		assertTrue(IntegerMath.compare(-2, -3) > 0);
		assertTrue(IntegerMath.compare(Integer.MAX_VALUE, Integer.MAX_VALUE) == 0);
		assertTrue(IntegerMath.compare(Integer.MIN_VALUE, Integer.MIN_VALUE) == 0);
		assertTrue(IntegerMath.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0);
		assertTrue(IntegerMath.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0);
		assertTrue(IntegerMath.compare(Integer.MIN_VALUE, Integer.MIN_VALUE + 10) < 0);
		assertTrue(IntegerMath.compare(Integer.MIN_VALUE + 10, Integer.MIN_VALUE) > 0);
		assertTrue(IntegerMath.compare(Integer.MAX_VALUE, Integer.MAX_VALUE - 10) > 0);
		assertTrue(IntegerMath.compare(Integer.MAX_VALUE - 10, Integer.MAX_VALUE) < 0);
	}
	
	
	@Test public void testCompareUnsigned() {
		assertTrue(IntegerMath.compareUnsigned(13, 72) < 0);
		assertTrue(IntegerMath.compareUnsigned(0xBEEF0000, 0xDEAD0000) < 0);
		assertTrue(IntegerMath.compareUnsigned(0xCAFE0000, 0xCAFE0000) == 0);
		assertTrue(IntegerMath.compareUnsigned(0xFFFFFFFF, 0x00000000) > 0);
		assertTrue(IntegerMath.compareUnsigned(0xFFFFFFFF, 0x80000000) > 0);
		assertTrue(IntegerMath.compareUnsigned(0x00000000, 0x80000000) < 0);
	}
	
	
	@Test public void testClamp() {
		assertEquals(5, IntegerMath.clamp(5, 0, 10));
		assertEquals(10, IntegerMath.clamp(12, 0, 10));
		assertEquals(0, IntegerMath.clamp(-7, 0, 10));
	}
	
	
	@Test public void testIsPowerOf2() {
		assertTrue(IntegerMath.isPowerOf2(1));
		assertTrue(IntegerMath.isPowerOf2(2));
		assertTrue(IntegerMath.isPowerOf2(4));
		assertTrue(IntegerMath.isPowerOf2(8));
		assertTrue(IntegerMath.isPowerOf2(1 << 29));
		assertTrue(IntegerMath.isPowerOf2(1 << 30));
		
		assertFalse(IntegerMath.isPowerOf2(0));
		assertFalse(IntegerMath.isPowerOf2(3));
		assertFalse(IntegerMath.isPowerOf2(5));
		assertFalse(IntegerMath.isPowerOf2(6));
		assertFalse(IntegerMath.isPowerOf2(7));
		assertFalse(IntegerMath.isPowerOf2(1073741823));
		assertFalse(IntegerMath.isPowerOf2(1073741825));
		assertFalse(IntegerMath.isPowerOf2(2147483647));
		
		assertFalse(IntegerMath.isPowerOf2(-1));
		assertFalse(IntegerMath.isPowerOf2(-2));
		assertFalse(IntegerMath.isPowerOf2(-3));
		assertFalse(IntegerMath.isPowerOf2(-4));
		assertFalse(IntegerMath.isPowerOf2(-2147483648));
	}
	
	
	
	// Elementary functions
	
	@Test public void testSqrt() {
		assertEquals(0, IntegerMath.sqrt(0));
		assertEquals(1, IntegerMath.sqrt(1));
		assertEquals(1, IntegerMath.sqrt(2));
		assertEquals(1, IntegerMath.sqrt(3));
		assertEquals(2, IntegerMath.sqrt(4));
		assertEquals(2, IntegerMath.sqrt(6));
		assertEquals(3, IntegerMath.sqrt(9));
		assertEquals(3, IntegerMath.sqrt(15));
		assertEquals(46339, IntegerMath.sqrt(2147395599));
		assertEquals(46340, IntegerMath.sqrt(2147395600));
		assertEquals(46340, IntegerMath.sqrt(2147483647));
	}
	
	
	@Test public void testSqrtRandomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt() & 0x7FFFFFFF;
			int y = IntegerMath.sqrt(x);
			assertTrue(y * y <= x && (long)(y + 1) * (y + 1) > x);
		}
	}
	
	
	@Test public void testCbrt() {
		assertEquals(0, IntegerMath.cbrt(0));
		assertEquals(1, IntegerMath.cbrt(1));
		assertEquals(1, IntegerMath.cbrt(2));
		assertEquals(1, IntegerMath.cbrt(3));
		assertEquals(2, IntegerMath.cbrt(8));
		assertEquals(2, IntegerMath.cbrt(9));
		assertEquals(2, IntegerMath.cbrt(16));
		assertEquals(3, IntegerMath.cbrt(27));
		assertEquals(3, IntegerMath.cbrt(28));
		assertEquals(1289, IntegerMath.cbrt(2146688999));
		assertEquals(1290, IntegerMath.cbrt(2146689000));
		assertEquals(1290, IntegerMath.cbrt(2147483647));
		
		assertEquals(-1, IntegerMath.cbrt(-1));
		assertEquals(-1, IntegerMath.cbrt(-4));
		assertEquals(-1, IntegerMath.cbrt(-7));
		assertEquals(-2, IntegerMath.cbrt(-8));
		assertEquals(-2, IntegerMath.cbrt(-13));
		assertEquals(-2, IntegerMath.cbrt(-24));
		assertEquals(-3, IntegerMath.cbrt(-27));
		assertEquals(-3, IntegerMath.cbrt(-50));
		assertEquals(-1289, IntegerMath.cbrt(-2146688999));
		assertEquals(-1290, IntegerMath.cbrt(-2146689000));
		assertEquals(-1290, IntegerMath.cbrt(-2147483648));
	}
	
	
	@Test public void testCbrtRandomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt();
			int y = IntegerMath.cbrt(x);
			if (x >= 0)
				assertTrue(y * y * y <= x && (long)(y + 1) * (y + 1) * (y + 1) > x);
			else
				assertTrue(y * y * y >= x && (long)(y - 1) * (y - 1) * (y - 1) < x);
		}
	}
	
	
	@Test public void testLog2Floor() {
		assertEquals(0, IntegerMath.log2Floor(1));
		assertEquals(1, IntegerMath.log2Floor(2));
		assertEquals(1, IntegerMath.log2Floor(3));
		assertEquals(2, IntegerMath.log2Floor(4));
		assertEquals(2, IntegerMath.log2Floor(5));
		assertEquals(2, IntegerMath.log2Floor(7));
		assertEquals(3, IntegerMath.log2Floor(8));
		assertEquals(3, IntegerMath.log2Floor(9));
		assertEquals(30, IntegerMath.log2Floor(Integer.MAX_VALUE));
	}
	
	
	@Test public void testLog2FloorRandomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt(Integer.MAX_VALUE - 1) + 1;  // In the range [1, Integer.MAX_VALUE]
			int y = IntegerMath.log2Floor(x);
			assertTrue((1 << y) <= x && (2L << y) > x);
		}
	}
	
	
	@Test public void testLog2Ceiling() {
		assertEquals(0, IntegerMath.log2Ceiling(1));
		assertEquals(1, IntegerMath.log2Ceiling(2));
		assertEquals(2, IntegerMath.log2Ceiling(3));
		assertEquals(2, IntegerMath.log2Ceiling(4));
		assertEquals(3, IntegerMath.log2Ceiling(5));
		assertEquals(3, IntegerMath.log2Ceiling(7));
		assertEquals(3, IntegerMath.log2Ceiling(8));
		assertEquals(4, IntegerMath.log2Ceiling(9));
		assertEquals(31, IntegerMath.log2Ceiling(Integer.MAX_VALUE));
	}
	
	
	@Test public void testLog2CeilingRandomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt(Integer.MAX_VALUE - 1) + 1;  // In the range [1, Integer.MAX_VALUE]
			int y = IntegerMath.log2Ceiling(x);
			assertTrue((1L << y) >= x);
		}
	}
	
	
	@Test public void testFloorToPowerOf2() {
		assertEquals(1, IntegerMath.floorToPowerOf2(1));
		assertEquals(2, IntegerMath.floorToPowerOf2(2));
		assertEquals(2, IntegerMath.floorToPowerOf2(3));
		assertEquals(4, IntegerMath.floorToPowerOf2(4));
		assertEquals(4, IntegerMath.floorToPowerOf2(5));
		assertEquals(4, IntegerMath.floorToPowerOf2(6));
		assertEquals(4, IntegerMath.floorToPowerOf2(7));
		assertEquals(8, IntegerMath.floorToPowerOf2(8));
		assertEquals(536870912, IntegerMath.floorToPowerOf2(1073741823));
		assertEquals(1073741824, IntegerMath.floorToPowerOf2(1073741824));
		assertEquals(1073741824, IntegerMath.floorToPowerOf2(2147483647));
	}
	
	
	@Test public void testFloorToPowerOf2Randomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt(Integer.MAX_VALUE - 1) + 1;  // In the range [1, Integer.MAX_VALUE]
			int y = IntegerMath.floorToPowerOf2(x);
			assertTrue(IntegerMath.isPowerOf2(y));
			assertTrue(y <= x && y * 2L > x);
		}
	}
	
	
	@Test public void testCeilingToPowerOf2() {
		assertEquals(1, IntegerMath.ceilingToPowerOf2(1));
		assertEquals(2, IntegerMath.ceilingToPowerOf2(2));
		assertEquals(4, IntegerMath.ceilingToPowerOf2(3));
		assertEquals(4, IntegerMath.ceilingToPowerOf2(4));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(5));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(6));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(7));
		assertEquals(8, IntegerMath.ceilingToPowerOf2(8));
		assertEquals(1073741824, IntegerMath.ceilingToPowerOf2(1073741823));
		assertEquals(1073741824, IntegerMath.ceilingToPowerOf2(1073741824));
	}
	
	
	@Test public void testCeilingToPowerOf2Randomly() {
		for (int i = 0; i < 1000; i++) {
			int x = RANDOM.uniformInt(0x3FFFFFFF) + 1;
			int y = IntegerMath.ceilingToPowerOf2(x);
			assertTrue(IntegerMath.isPowerOf2(y));
			assertTrue(y >= x);
		}
	}
	
	
	@Test public void testIsPrime() {
		int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
		for (int i = -20, j = 0; j < primes.length; i++) {  // i is the number to test, j is the index into 'primes' such that i <= primes[j]
			if (i == primes[j]) {
				assertTrue(IntegerMath.isPrime(i));
				j++;
			} else
				assertFalse(IntegerMath.isPrime(i));
		}
	}
	
	
	@Test public void testGcd() {
		assertEquals(0, IntegerMath.gcd(0, 0));
		assertEquals(5, IntegerMath.gcd(5, 0));
		assertEquals(2, IntegerMath.gcd(0, 2));
		assertEquals(3, IntegerMath.gcd(3, 3));
		assertEquals(3, IntegerMath.gcd(-3, 6));
		assertEquals(3, IntegerMath.gcd(-6, -9));
		assertEquals(15, IntegerMath.gcd(30, -315));
		assertEquals(1 << 30, IntegerMath.gcd(-1 << 31, 1 << 30));
	}
	
	
	@Test(expected=ArithmeticOverflowException.class)
	public void testGcdInvalid() {
		IntegerMath.gcd(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
	
	
	@Test public void testGcdRandomly() {
		for (int i = 0; i < 1000; i++) {
			// This tests only some of the properties of the GCD - necessary but not sufficient
			int xbits = RANDOM.uniformInt(32);
			int x = 0;
			if (xbits > 0) {
				x = 1 << (xbits - 1);
				x |= RANDOM.uniformInt() & (x - 1);
			}
			if (RANDOM.uniformInt(2) == 1)
				x = -x;
			
			int ybits = RANDOM.uniformInt(32);
			int y = 0;
			if (ybits > 0) {
				y = 1 << (ybits - 1);
				y |= RANDOM.uniformInt() & (y - 1);
			}
			if (RANDOM.uniformInt(2) == 1)
				y = -y;
			
			int gcd = IntegerMath.gcd(x, y);
			if (x != 0 || y != 0) {
				assertEquals(0, x % gcd);
				assertEquals(0, y % gcd);
			}
			assertEquals(gcd, IntegerMath.gcd(y, x));  // Commutativity
		}
	}
	
}
