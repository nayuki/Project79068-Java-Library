package crypto;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import p79068.crypto.cipher.Cipherer;
import p79068.crypto.cipher.Rc4;


public class Rc4Test {
	
	@Test
	public void testRc4() {
		test("4B6579", "506C61696E74657874", "BBF316E8D940AF0AD3");
		test("57696B69", "7065646961", "1021BF0420");
		test("536563726574", "41747461636B206174206461776E", "45A01F645FC35B383552544B9BF5");
		test("EF012345", "00000000000000000000", "D6A141A7EC3C38DFBD61");
		test("0123456789ABCDEF", "0123456789ABCDEF", "75B7878099E0C596");
		test("0123456789ABCDEF", "0000000000000000", "7494C2E7104B0879");
		test("0123456789ABCDEF", "123456789ABCDEF0123456789ABCDEF0123456789ABCDEF012345678", "66A0949F8AF7D6891F7F832BA833C00C892EBE30143CE28740011ECF");
		test("FB029E3031323334", "AAAA0300000008004500004E661A00008011BE640A0001220AFFFFFF00890089003A000080A601100001000000000000204543454A4548454346434550464545494546464343414341434143414341414100002000011BD0B604", "F69C5806BD6CE84626BCBEFB9474650AAD1F7909B0F64D5F58A503A258B7ED22EB0EA64930D3A056A55742FCCE141D485F8AA836DEA18DF42C5380805AD0C61A5D6F58F41040B24B7D1A693856ED0D4398E7AEE3BF0E2A2CA8F7");
	}
	
	
	
	private static void test(String key, String plaintext, String ciphertext) {
		test(Debug.hexToBytes(key), Debug.hexToBytes(plaintext), Debug.hexToBytes(ciphertext));
	}
	
	
	private static void test(byte[] key, byte[] plaintext, byte[] ciphertext) {
		Cipherer cipherer = new Rc4(key.length).newCipherer(key);
		cipherer.encrypt(plaintext, 0, plaintext.length);
		assertArrayEquals(ciphertext, plaintext);
	}
	
}