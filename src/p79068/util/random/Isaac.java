package p79068.util.random;


/**
 * The ISAAC secure pseudorandom number generator by Bob Jenkins.
 * Translated from the C source code at http://www.burtleburtle.net/bob/c/readable.c .
 */
public final class Isaac extends AbstractRandom implements Cloneable {
	
	// RNG internal state
	private int[] mm;  // Length 256
	private int aa;
	private int bb;
	private int cc;
	
	// Buffer of generated output
	private int[] gen;  // Length 256
	private int count;  // In the range [0, 256]
	
	
	
	public Isaac(int[] seed) {
		if (seed != null && seed.length != 256)
			throw new IllegalArgumentException();
		
		int a, b, c, d, e, f, g, h;
		a = b = c = d = e = f = g = h = 0x9E3779B9;  // The golden ratio
		for (int i = 0; i < 4; i++) {  // Scramble it
			a ^= b <<  11;  d += a;  b += c;
			b ^= c >>>  2;  e += b;  c += d;
			c ^= d <<   8;  f += c;  d += e;
			d ^= e >>> 16;  g += d;  e += f;
			e ^= f <<  10;  h += e;  f += g;
			f ^= g >>>  4;  a += f;  g += h;
			g ^= h <<   8;  b += g;  h += a;
			h ^= a >>>  9;  c += h;  a += b;
		}
		
		// Fill in mm with messy stuff
		mm = new int[256];
		for (int i = 0; i < 256; i += 8) {
			if (seed != null) {  // Use all the information in the seed
				a += seed[i + 0];
				b += seed[i + 1];
				c += seed[i + 2];
				d += seed[i + 3];
				e += seed[i + 4];
				f += seed[i + 5];
				g += seed[i + 6];
				h += seed[i + 7];
			}
			a ^= b <<  11;  d += a;  b += c;
			b ^= c >>>  2;  e += b;  c += d;
			c ^= d <<   8;  f += c;  d += e;
			d ^= e >>> 16;  g += d;  e += f;
			e ^= f <<  10;  h += e;  f += g;
			f ^= g >>>  4;  a += f;  g += h;
			g ^= h <<   8;  b += g;  h += a;
			h ^= a >>>  9;  c += h;  a += b;
			mm[i + 0] = a;
			mm[i + 1] = b;
			mm[i + 2] = c;
			mm[i + 3] = d;
			mm[i + 4] = e;
			mm[i + 5] = f;
			mm[i + 6] = g;
			mm[i + 7] = h;
		}
		
		// Do a second pass to make all of the seed affect all of mm
		if (seed != null) {
			for (int i = 0; i < 256; i += 8) {
				a += mm[i + 0];
				b += mm[i + 1];
				c += mm[i + 2];
				d += mm[i + 3];
				e += mm[i + 4];
				f += mm[i + 5];
				g += mm[i + 6];
				h += mm[i + 7];
				a ^= b <<  11;  d += a;  b += c;
				b ^= c >>>  2;  e += b;  c += d;
				c ^= d <<   8;  f += c;  d += e;
				d ^= e >>> 16;  g += d;  e += f;
				e ^= f <<  10;  h += e;  f += g;
				f ^= g >>>  4;  a += f;  g += h;
				g ^= h <<   8;  b += g;  h += a;
				h ^= a >>>  9;  c += h;  a += b;
				mm[i + 0] = a;
				mm[i + 1] = b;
				mm[i + 2] = c;
				mm[i + 3] = d;
				mm[i + 4] = e;
				mm[i + 5] = f;
				mm[i + 6] = g;
				mm[i + 7] = h;
			}
		}
		
		aa = bb = cc = 0;
		gen = new int[256];
		count = 0;
	}
	
	
	
	public int uniformInt() {
		if (count == 0)
			nextState();
		count--;
		return gen[count];
	}
	
	
	public long uniformLong() {
		if (count >= 2) {
			long result = (long)gen[count - 1] << 32 | (gen[count - 2] & 0xFFFFFFFFL);
			count -= 2;
			return result;
		} else
			return (long)uniformInt() << 32 | (uniformInt() & 0xFFFFFFFFL);
	}
	
	
	private void nextState() {
		cc++;
		bb += cc;
		int x, y;
		for (int i = 0; i < mm.length; i++) {
			x = mm[i];
			switch (i & 3) {
				case 0:  aa ^= aa <<  13;  break;
				case 1:  aa ^= aa >>>  6;  break;
				case 2:  aa ^= aa <<   2;  break;
				case 3:  aa ^= aa >>> 16;  break;
				default:  throw new AssertionError();
			}
			aa = mm[(i + 128) & 0xFF] + aa;
			mm[i] = y = mm[(x >>> 2) & 0xFF] + aa + bb;
			gen[i] = bb = mm[(y >>> 10) & 0xFF] + x;
		}
		count = gen.length;
	}
	
}
