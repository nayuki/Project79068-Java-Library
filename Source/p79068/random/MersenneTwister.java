/*
 * Translated from the C code by Makoto Matsumoto and Takuji Nishimura dated 2002-01-26. The numerical output is identical.
 */

/* 
 * A C-program for MT19937, with initialization improved 2002/1/26.
 * Coded by Takuji Nishimura and Makoto Matsumoto.
 * 
 * Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura,
 * All rights reserved.                          
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *   1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *   3. The names of its contributors may not be used to endorse or promote 
 *      products derived from this software without specific prior written 
 *      permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Any feedback is very welcome.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html
 * email: m-mat @ math.sci.hiroshima-u.ac.jp (remove space)
 */


package p79068.random;


/**
 * The Mersenne Twister pseudorandom number generator.
 * <p>Mutability: <em>Mutable</em><br>
 * Thread safety: <em>Unsafe</em></p>
 */
public final class MersenneTwister extends AbstractRandom {
	
	private int[] state;
	
	private int index;
	
	
	
	public MersenneTwister() {
		this(toInt32s(new long[]{System.currentTimeMillis(), System.nanoTime()}));
	}
	
	
	public MersenneTwister(int seed) {
		setSeed(seed);
	}
	
	
	public MersenneTwister(long seed) {
		this(toInt32s(new long[]{seed}));
	}
	
	
	public MersenneTwister(int[] seed) {
		setSeed(seed);
	}
	
	
	
	@Override
	public int uniformInt() {
		if (index == 624)
			nextState();
		int x = state[index];
		index++;
		
		// Tempering
		x ^= x >>> 11;
		x ^= (x << 7) & 0x9D2C5680;
		x ^= (x << 15) & 0xEFC60000;
		return x ^ (x >>> 18);
	}
	
	
	@Override
	public long uniformLong() {
		return (long)uniformInt() << 32 | (uniformInt() & 0xFFFFFFFFL);
	}
	
	
	private void setSeed(int seed) {
		if (state == null)
			state = new int[624];
		for (index = 0; index < 624; index++) {
			state[index] = seed;
			seed = 1812433253 * (seed ^ (seed >>> 30)) + index + 1;
		}
	}
	
	
	private void setSeed(int[] seed) {
		setSeed(19650218);
		int i = 1;
		for (int j = 0, k = 0; k < Math.max(624, seed.length); k++) {
			state[i] = (state[i] ^ ((state[i - 1] ^ (state[i - 1] >>> 30)) * 1664525)) + seed[j] + j;
			i++;
			j++;
			if (i == 624) {
				state[0] = state[623];
				i = 1;
			}
			if (j >= seed.length)
				j = 0;
		}
		for (int k = 0; k < 623; k++) {
			state[i] = (state[i] ^ ((state[i - 1] ^ (state[i - 1] >>> 30)) * 1566083941)) - i;
			i++;
			if (i == 624) {
				state[0] = state[623];
				i = 1;
			}
		}
		state[0] = 0x80000000;
	}
	
	
	private void nextState() {
		int k = 0;
		for (; k < 227; k++) {
			int y = (state[k] & 0x80000000) | (state[k + 1] & 0x7FFFFFFF);
			state[k] = state[k + 397] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		}
		for (; k < 623; k++) {
			int y = (state[k] & 0x80000000) | (state[k + 1] & 0x7FFFFFFF);
			state[k] = state[k - 227] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		}
		int y = (state[623] & 0x80000000) | (state[0] & 0x7FFFFFFF);
		state[623] = state[396] ^ (y >>> 1) ^ ((y & 1) * 0x9908B0DF);
		index = 0;
	}
	
	
	private static int[] toInt32s(long[] in) {
		int[] out = new int[in.length * 2];
		for (int i = 0; i < in.length; i++) {
			out[i * 2 + 0] = (int)(in[i] >>> 32);
			out[i * 2 + 1] = (int)(in[i] >>> 0);
		}
		return out;
	}
	
}
