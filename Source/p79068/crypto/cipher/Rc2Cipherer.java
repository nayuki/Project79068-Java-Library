package p79068.crypto.cipher;

import p79068.crypto.Zeroizer;
import p79068.lang.*;


final class Rc2Cipherer extends Cipherer {
	
	private int[] keySchedule;  // 16-bit integers
	
	

	Rc2Cipherer(Rc2 cipher, byte[] key) {
		super(cipher, key);
		int t1 = cipher.getEffectiveKeyLength();
		int t8 = (t1 + 7) / 8;  // Effective key length, in bytes (rounded up)
		int tm;
		if (t1 % 8 == 0)
			tm = 0xFF;
		else
			tm = (1 << (t1 % 8)) - 1;
		int[] l = new int[128];  // Each element is an 8-bit integer
		for (int i = 0; i < key.length; i++)
			l[i] = key[i] & 0xFF;
		for (int i = key.length; i < 128; i++)
			l[i] = piTable[(l[i - 1] + l[i - key.length]) & 0xFF];
		l[128 - t8] = piTable[l[128 - t8] & tm];
		for (int i = 128 - 1 - t8; i >= 0; i--)
			l[i] = piTable[l[i + 1] ^ l[i + t8]];
		keySchedule = new int[64];
		for (int i = 0; i < 64; i++)
			keySchedule[i] = (l[i * 2] & 0xFF) | (l[i * 2 + 1] & 0xFF) << 8;
	}
	
	
	
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 7) != 0)
			throw new IllegalArgumentException("Invalid block length");
		int[] r = new int[4];
		for (int end = off + len; off < end; off += 8) {
			for (int i = 0; i < 4; i++)
				r[i] = (b[off + i*2] & 0xFF) | (b[off + i*2 + 1] & 0xFF) << 8;
			int j = 0;
			for (; j <  5; j++) mix(r, j * 4);
			mash(r);
			for (; j < 11; j++) mix(r, j * 4);
			mash(r);
			for (; j < 16; j++) mix(r, j * 4);
			for (int i = 0; i < 4; i++) {
				b[off + i*2 + 0] = (byte)(r[i] >>> 0);
				b[off + i*2 + 1] = (byte)(r[i] >>> 8);
			}
		}
	}
	
	
	public void decrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
		if ((len & 7) != 0)
			throw new IllegalArgumentException("Invalid block length");
		int[] r = new int[4];
		for (int end = off + len; off < end; off += 8) {
			for (int i = 0; i < 4; i++)
				r[i] = (b[off + i*2] & 0xFF) | (b[off + i*2 + 1] & 0xFF) << 8;
			int j = 15;
			for (; j >= 11; j--) mixInverse(r, j * 4);
			mashInverse(r);
			for (; j >=  5; j--) mixInverse(r, j * 4);
			mashInverse(r);
			for (; j >=  0; j--) mixInverse(r, j * 4);
			for (int i = 0; i < 4; i++) {
				b[off + i*2 + 0] = (byte)(r[i] >>> 0);
				b[off + i*2 + 1] = (byte)(r[i] >>> 8);
			}
		}
	}
	
	
	public Rc2Cipherer clone() {
		Rc2Cipherer result = (Rc2Cipherer)super.clone();
		result.keySchedule = result.keySchedule.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (cipher == null)
			return;
		Zeroizer.clear(keySchedule);
		keySchedule = null;
		super.zeroize();
	}
	
	
	private static final int[] piTable = {
		0xD9, 0x78, 0xF9, 0xC4, 0x19, 0xDD, 0xB5, 0xED, 0x28, 0xE9, 0xFD, 0x79, 0x4A, 0xA0, 0xD8, 0x9D,
		0xC6, 0x7E, 0x37, 0x83, 0x2B, 0x76, 0x53, 0x8E, 0x62, 0x4C, 0x64, 0x88, 0x44, 0x8B, 0xFB, 0xA2,
		0x17, 0x9A, 0x59, 0xF5, 0x87, 0xB3, 0x4F, 0x13, 0x61, 0x45, 0x6D, 0x8D, 0x09, 0x81, 0x7D, 0x32,
		0xBD, 0x8F, 0x40, 0xEB, 0x86, 0xB7, 0x7B, 0x0B, 0xF0, 0x95, 0x21, 0x22, 0x5C, 0x6B, 0x4E, 0x82,
		0x54, 0xD6, 0x65, 0x93, 0xCE, 0x60, 0xB2, 0x1C, 0x73, 0x56, 0xC0, 0x14, 0xA7, 0x8C, 0xF1, 0xDC,
		0x12, 0x75, 0xCA, 0x1F, 0x3B, 0xBE, 0xE4, 0xD1, 0x42, 0x3D, 0xD4, 0x30, 0xA3, 0x3C, 0xB6, 0x26,
		0x6F, 0xBF, 0x0E, 0xDA, 0x46, 0x69, 0x07, 0x57, 0x27, 0xF2, 0x1D, 0x9B, 0xBC, 0x94, 0x43, 0x03,
		0xF8, 0x11, 0xC7, 0xF6, 0x90, 0xEF, 0x3E, 0xE7, 0x06, 0xC3, 0xD5, 0x2F, 0xC8, 0x66, 0x1E, 0xD7,
		0x08, 0xE8, 0xEA, 0xDE, 0x80, 0x52, 0xEE, 0xF7, 0x84, 0xAA, 0x72, 0xAC, 0x35, 0x4D, 0x6A, 0x2A,
		0x96, 0x1A, 0xD2, 0x71, 0x5A, 0x15, 0x49, 0x74, 0x4B, 0x9F, 0xD0, 0x5E, 0x04, 0x18, 0xA4, 0xEC,
		0xC2, 0xE0, 0x41, 0x6E, 0x0F, 0x51, 0xCB, 0xCC, 0x24, 0x91, 0xAF, 0x50, 0xA1, 0xF4, 0x70, 0x39,
		0x99, 0x7C, 0x3A, 0x85, 0x23, 0xB8, 0xB4, 0x7A, 0xFC, 0x02, 0x36, 0x5B, 0x25, 0x55, 0x97, 0x31,
		0x2D, 0x5D, 0xFA, 0x98, 0xE3, 0x8A, 0x92, 0xAE, 0x05, 0xDF, 0x29, 0x10, 0x67, 0x6C, 0xBA, 0xC9,
		0xD3, 0x00, 0xE6, 0xCF, 0xE1, 0x9E, 0xA8, 0x2C, 0x63, 0x16, 0x01, 0x3F, 0x58, 0xE2, 0x89, 0xA9,
		0x0D, 0x38, 0x34, 0x1B, 0xAB, 0x33, 0xFF, 0xB0, 0xBB, 0x48, 0x0C, 0x5F, 0xB9, 0xB1, 0xCD, 0x2E,
		0xC5, 0xF3, 0xDB, 0x47, 0xE5, 0xA5, 0x9C, 0x77, 0x0A, 0xA6, 0x20, 0x68, 0xFE, 0x7F, 0xC1, 0xAD
	};
	
	
	private static final int[] s = {1, 2, 3, 5};
	
	
	private void mix(int[] r, int j) {
		for (int i = 0; i < 4; i++)
			mix(r, i, j + i);
	}
	
	
	private void mix(int[] r, int i, int j) {
		r[i] = (r[i] + keySchedule[j] + (r[(i + 3) & 3] & r[(i + 2) & 3]) + (~r[(i + 3) & 3] & r[(i + 1) & 3])) & 0xFFFF;
		r[i] = (r[i] << s[i] | r[i] >>> (16 - s[i])) & 0xFFFF;  // Left rotation by s[i]
	}
	
	
	private void mash(int[] r) {
		for (int i = 0; i < 4; i++)
			r[i] = (r[i] + keySchedule[r[(i + 3) & 3] & 0x3F]) & 0xFFFF;
	}
	
	
	private void mixInverse(int[] r, int j) {
		for (int i = 3; i >= 0; i--)
			mixInverse(r, i, j + i);
	}
	
	
	private void mixInverse(int[] r, int i, int j) {
		r[i] = (r[i] << (16 - s[i]) | r[i] >>> s[i]) & 0xFFFF;  // Right rotation by s[i]
		r[i] = (r[i] - keySchedule[j] - (r[(i + 3) & 3] & r[(i + 2) & 3]) - (~r[(i + 3) & 3] & r[(i + 1) & 3])) & 0xFFFF;
	}
	
	
	private void mashInverse(int[] r) {
		for (int i = 3; i >= 0; i--)
			r[i] = (r[i] - keySchedule[r[(i + 3) & 3] & 0x3F]) & 0xFFFF;
	}
	
}