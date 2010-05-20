package p79068.crypto.cipher;

import org.junit.Test;
import p79068.crypto.CryptoUtils;


public final class TeaTest {
	
	@Test
	public void testTea() {
		CryptoUtils.testCipher(Tea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "9327C49731B08BBE");
		CryptoUtils.testCipher(Tea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "9327C49731B08BBE");
		CryptoUtils.testCipher(Tea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "9327C49731B08BBE");
		CryptoUtils.testCipher(Tea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "9327C49731B08BBE");
	}
	
	
	@Test
	public void testXtea() {
		CryptoUtils.testCipher(Xtea.CIPHER, "00000000800000000000000000000000", "0000000000000000", "4F190CCFC8DEABFC");
		CryptoUtils.testCipher(Xtea.CIPHER, "80000000000000000000000000000000", "0000000000000000", "057E8C0550151937");
		CryptoUtils.testCipher(Xtea.CIPHER, "80000000000000008000000080000000", "0000000000000000", "31C4E2C6B347B2DE");
		CryptoUtils.testCipher(Xtea.CIPHER, "00000000800000008000000080000000", "0000000000000000", "ED69B78566781EF3");
	}
	
}