package p79068.util;

import java.util.Arrays;

import p79068.Assert;
import p79068.math.IntegerMath;


public final class ByteBuffer {
	
	/* Fields */
	
	private byte[] buffer;
	
	private int length;
	
	
	
	/* Constructors */
	
	public ByteBuffer() {
		buffer = new byte[1];
		length = 0;
	}
	
	
	public ByteBuffer(byte[] b) {
		Assert.assertNotNull(b);
		if (b.length == 0)
			buffer = new byte[1];
		else
			buffer = b.clone();
		length = b.length;
	}
	
	
	public ByteBuffer(byte[] b, int off, int len) {
		Assert.assertNotNull(b);
		Assert.assertRangeInBounds(b.length, off, len);
		if (len == 0)
			buffer = new byte[1];
		else
			buffer = Arrays.copyOfRange(b, off, off + len);
		length = len;
	}
	
	
	
	/* Retrieval methods */
	
	public int length() {
		return length;
	}
	
	
	public byte[] toByteArray() {
		return Arrays.copyOf(buffer, length);
	}
	
	
	/* Modification methods */
	
	public ByteBuffer append(byte b) {
		ensureCapacity(1);
		buffer[length] = b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(int b) {
		ensureCapacity(1);
		buffer[length] = (byte)b;
		length++;
		return this;
	}
	
	
	public ByteBuffer append(byte[] b) {
		return append(b, 0, b.length);
	}
	
	
	public ByteBuffer append(byte[] b, int off, int len) {
		ensureCapacity(len);
		System.arraycopy(b, off, buffer, length, len);
		length += len;
		return this;
	}
	
	
	public void clear() {
		length = 0;
	}
	
	
	/* Private methods */
	
	private void ensureCapacity(int delta) {
		int newLength = IntegerMath.checkedAdd(length, delta);
		if (buffer.length < newLength) {
			long capacity = buffer.length;
			while (capacity < newLength)
				capacity = Math.min(capacity * 2, Integer.MAX_VALUE);
			buffer = Arrays.copyOf(buffer, (int)capacity);
		}
	}
	
}
