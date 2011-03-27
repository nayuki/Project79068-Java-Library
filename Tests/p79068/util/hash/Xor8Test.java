package p79068.util.hash;

import org.junit.Test;


public final class Xor8Test {
	
	@Test
	public void testXor8() {
		HashUtils.testAscii(Xor8.FUNCTION, "", "00");
		HashUtils.testAscii(Xor8.FUNCTION, "bb", "00");
		HashUtils.testAscii(Xor8.FUNCTION, "asdf", "10");
		HashUtils.testAscii(Xor8.FUNCTION, "fads", "10");
		HashUtils.testAscii(Xor8.FUNCTION, "The", "59");
		HashUtils.testAscii(Xor8.FUNCTION, "tEh", "59");
		HashUtils.testAscii(Xor8.FUNCTION, "soy", "65");
		HashUtils.testAscii(Xor8.FUNCTION, "sorry", "65");
		HashUtils.testAscii(Xor8.FUNCTION, "sorrrry", "65");
	}
	
}