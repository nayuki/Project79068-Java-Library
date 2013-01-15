package p79068.math;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.util.Random;
import org.junit.Test;


public final class BigIntegerMathTest {
	
	private static Random random = new Random();
	
	
	@Test
	public void testMultiplyRandomly() {
		for (int i = 0; i < 100; i++) {
			int size = random.nextInt(30000);
			BigInteger x = new BigInteger(size, random);
			BigInteger y = new BigInteger(size, random);
			if (random.nextBoolean())
				x = x.negate();
			if (random.nextBoolean())
				y = y.negate();
			assertEquals(x.multiply(y), BigIntegerMath.multiply(x, y));
		}
	}
	
}
