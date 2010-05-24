package p79068.crypto.cipher;

import p79068.lang.BoundsChecker;


final class IdentityStreamCipherer extends StreamCipherer {
	
	IdentityStreamCipherer(IdentityStreamCipher cipher, byte[] key) {
		super(cipher, key);
	}
	
	
	
	@Override
	public void encrypt(byte[] b, int off, int len) {
		if (cipher == null)
			throw new IllegalStateException("Already zeroized");
		BoundsChecker.check(b.length, off, len);
	}
	
	
	@Override
	public void zeroize() {
		if (cipher == null)
			return;
		super.zeroize();
	}
	
}