package p79068.hash;

import org.junit.Test;


public final class Xor8Test {
	
	@Test
	public void testXor8() {
		HashUtils.testAscii(Xor8.FUNCTION, "", "00");
		HashUtils.testAscii(Xor8.FUNCTION, "A", "41");
		HashUtils.testAscii(Xor8.FUNCTION, "AA", "00");
		HashUtils.testAscii(Xor8.FUNCTION, "asdf", "10");
		HashUtils.testAscii(Xor8.FUNCTION, "fsda", "10");
		HashUtils.testAscii(Xor8.FUNCTION, "The", "59");
		HashUtils.testAscii(Xor8.FUNCTION, "tEh", "59");
		HashUtils.testAscii(Xor8.FUNCTION, "ac", "02");
		HashUtils.testAscii(Xor8.FUNCTION, "abbc", "02");
		HashUtils.testAscii(Xor8.FUNCTION, "abbbbc", "02");
	}
	
}