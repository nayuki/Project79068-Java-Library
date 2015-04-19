package p79068;

import static org.junit.Assert.fail;
import org.junit.Test;
import p79068.Assert;


public final class AssertTest {
	
	@Test public void testAssertNotNull() {
		try {
			Assert.assertNotNull(new Object());
			Assert.assertNotNull(new String("a"));
			Assert.assertNotNull(new Integer(3));
		} catch (NullPointerException e) {
			fail();
		}
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testAssertNull() {
		Assert.assertNotNull((Object)null);
	}
	
	
	@Test public void testAssertVarargNotNull() {
		try {
			Assert.assertNotNull(new Object(), new String("a"), new Integer(3));
		} catch (NullPointerException e) {
			fail();
		}
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testAssertVarargNull() {
		Assert.assertNotNull(new Object(), null);
	}
	
	
	@Test public void testAssertIndexInBoundsValid() {
		try {
			Assert.assertIndexInBounds(1, 0);
			Assert.assertIndexInBounds(3, 0);
			Assert.assertIndexInBounds(3, 1);
			Assert.assertIndexInBounds(3, 2);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}
	
	
	@Test public void testAssertIndexInBoundsInvalid() {
		int[][] cases = {
				{0, 0},
				{3, 3},
				{4, 5},
				{4, 11},
				{719, 846},
				{Integer.MAX_VALUE - 1, Integer.MAX_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				Assert.assertIndexInBounds(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test public void testAssertIndexInBoundsNegativeIndex() {
		int[][] cases = {
				{0, -1},
				{0, Integer.MIN_VALUE},
				{100, Integer.MIN_VALUE},
				{Integer.MAX_VALUE, Integer.MIN_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				Assert.assertIndexInBounds(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test public void testAssertIndexInBoundsNegativeArrayLength() {
		int[][] cases = {
				{-1, 0},
				{Integer.MIN_VALUE, 23},
				{Integer.MIN_VALUE + 1, -45},
				{Integer.MIN_VALUE / 2, 0}
		};
		
		for (int[] thecase : cases) {
			try {
				Assert.assertIndexInBounds(thecase[0], thecase[1]);
				fail(String.format("%d %d", thecase[0], thecase[1]));
			} catch (IllegalArgumentException e) {}
		}
	}
	
	
	
	@Test public void testAssertRangeInBoundsValid() {
		try {
			Assert.assertRangeInBounds(0, 0, 0);
			Assert.assertRangeInBounds(1, 0, 0);
			Assert.assertRangeInBounds(1, 0, 1);
			Assert.assertRangeInBounds(1, 1, 0);
			Assert.assertRangeInBounds(17, 5, 6);
			Assert.assertRangeInBounds(17, 5, 12);
			Assert.assertRangeInBounds(17, 0, 17);
			Assert.assertRangeInBounds(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
			Assert.assertRangeInBounds(Integer.MAX_VALUE, 31, Integer.MAX_VALUE - 31);
			Assert.assertRangeInBounds(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}
	
	
	@Test public void testAssertRangeInBoundsInvalid() {
		int[][] cases = {
				{0, 1, 0},
				{0, 1, 2},
				{5, 3, 3},
				{5, 5, 4},
				{5, 8, 0},
				{5, -2, 7},
				{5, -3, 4},
				{5, 2, -1},
				{2000000000, 1500000000, 1500000000},
				{0, Integer.MAX_VALUE, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, 1, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}
		};
		
		for (int[] thecase : cases) {
			try {
				Assert.assertRangeInBounds(thecase[0], thecase[1], thecase[2]);
				fail(String.format("%d %d %d", thecase[0], thecase[1], thecase[2]));
			} catch (IndexOutOfBoundsException e) {}
		}
	}
	
	
	@Test public void testAssertRangeInBoundsNegativeArrayLength() {
		int[][] cases = {
				{-1, 0, 0},
				{-3, 0, -2},
				{-3, -1, 2},
				{-5, 1, 1},
				{Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
				{Integer.MIN_VALUE + 1, 0, Integer.MAX_VALUE / 2},
				{Integer.MIN_VALUE / 3, Integer.MIN_VALUE / 4, 100}
		};
		
		for (int[] thecase : cases) {
			try {
				Assert.assertRangeInBounds(thecase[0], thecase[1], thecase[2]);
				fail(String.format("%d %d %d", thecase[0], thecase[1], thecase[2]));
			} catch (IllegalArgumentException e) {}
		}
	}
	
}
