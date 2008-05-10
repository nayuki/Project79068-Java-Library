package p79068.crypto.hash;

import p79068.crypto.Zeroizer;
import p79068.util.hash.HashFunction;
import p79068.util.hash.HashValue;


final class WhirlpoolHasher extends BlockHasher {
	
	private byte[] state;
	
	
	
	WhirlpoolHasher(Whirlpool hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	WhirlpoolHasher(Whirlpool0 hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	WhirlpoolHasher(Whirlpool1 hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		this((BlockHashFunction)hashFunc, sub, rcon, mul);
	}
	
	
	// Private constructor. hashFunc must be either Whirlpool0, Whirlpool1, or Whirlpool.
	private WhirlpoolHasher(BlockHashFunction hashFunc, byte[] sub, byte[][] rcon, byte[][] mul) {
		super(hashFunc, 64);
		if (sub == null || rcon == null || mul == null)
			throw new AssertionError();
		this.sub = sub;
		this.rcon = rcon;
		this.mul = mul;
		state = new byte[64];
	}
	
	
	
	public WhirlpoolHasher clone() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		WhirlpoolHasher result = (WhirlpoolHasher)super.clone();
		result.state = state.clone();
		return result;
	}
	
	
	public void zeroize() {
		if (hashFunction == null)
			throw new IllegalStateException("Already zeroized");
		Zeroizer.clear(state);
		state = null;
		super.zeroize();
	}
	
	
	
	// Uses Miyaguchi-Preneel construction: next state = encrypt(msg: message block, key: state) XOR state XOR message block
	protected void compress(byte[] message, int off, int len) {
		byte[] tempmsg = new byte[64];
		byte[] tempstate = new byte[64];
		byte[] temp = new byte[64];
		for (int end = off + len; off < end; off += 64) {
			System.arraycopy(message, off, tempmsg, 0, 64);
			System.arraycopy(state, 0, tempstate, 0, 64);
			w(tempmsg, tempstate, temp);
			for (int i = 0; i < 64; i++)
				state[i] ^= tempmsg[i] ^ message[off + i];
		}
	}
	
	
	protected HashValue getHashDestructively() {
		block[blockLength] = (byte)0x80;
		for (int i = blockLength + 1; i < block.length; i++)
			block[i] = 0x00;
		if (blockLength + 1 > block.length - 32) {
			compress();
			for (int i = 0; i < block.length; i++)
				block[i] = 0x00;
		}
		for (int i = 0; i < 8; i++)
			block[block.length - 1 - i] = (byte)((length * 8) >>> (i * 8));  // Whirlpool supports lengths just less than 2^256 bits (2^253 bytes), but this implementation only counts to just less than 2^64 bytes.
		compress();
		return createHash(state);
	}
	
	
	
	private byte[] sub;
	private byte[][] mul;
	private byte[][] rcon;
	
	
	// The internal block cipher. Overwrites message and key.
	private void w(byte[] message, byte[] key, byte[] temp) {
		sigma(message, key);
		for (int i = 0; i < 10; i++) {
			rho(key, rcon[i], temp);
			rho(message, key, temp);
		}
	}
	
	
	// The round function. Overwrites block and temp.
	private void rho(byte[] block, byte[] key, byte[] temp) {
		gamma(block);
		pi(block, temp);
		theta(temp, block);
		sigma(block, key);
	}
	
	
	// The non-linear layer
	private void gamma(byte[] block) {
		for (int i = 0; i < 64; i++)
			block[i] = sub[block[i] & 0xFF];
	}
	
	
	// The cyclical permutation
	private void pi(byte[] blockin, byte[] blockout) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++)
				blockout[((i + j) & 7) << 3 | j] = blockin[i << 3 | j];
		}
	}
	
	
	// The linear diffusion layer
	private void theta(byte[] blockin, byte[] blockout) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int sum = 0;
				for (int k = 0; k < 8; k++)
					sum ^= mul[k][blockin[i << 3 | (j + k) & 7] & 0xFF];
				blockout[i << 3 | j] = (byte)sum;
			}
		}
	}
	
	
	// The key addition
	private void sigma(byte[] block, byte[] key) {
		for (int i = 0; i < 64; i++)
			block[i] ^= key[i];
	}
	
}