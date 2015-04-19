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
	
	
	public byte get(int i) {
		Assert.assertIndexInBounds(length, i);
		return buffer[i];
	}
	
	
	public void get(int off, int len, byte[] b, int boff) {
		Assert.assertRangeInBounds(length, off, len);
		Assert.assertRangeInBounds(b.length, boff, len);
		System.arraycopy(buffer, off, b, boff, len);
	}
	
	
	public byte[] toByteArray() {
		return Arrays.copyOf(buffer, length);
	}
	
	
	public byte[] toByteArray(int off, int len) {
		Assert.assertRangeInBounds(length, off, len);
		return Arrays.copyOfRange(buffer, off, off + len);
	}
	
	
	/* Modification methods */
	
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
		Assert.assertNotNull(b);
		Assert.assertRangeInBounds(b.length, off, len);
		ensureCapacity(len);
		System.arraycopy(b, off, buffer, length, len);
		length += len;
		return this;
	}
	
	
	public ByteBuffer append(ByteBuffer b) {
		Assert.assertNotNull(b);
		return append(b.buffer, 0, b.length);
	}
	
	
	public ByteBuffer insert(int i, int b) {
		if (i < 0 || i > length)
			throw new IndexOutOfBoundsException();
		ensureCapacity(1);
		System.arraycopy(buffer, i, buffer, i + 1, length - i);
		buffer[i] = (byte)b;
		length++;
		return this;
	}
	
	
	public ByteBuffer insert(int i, byte[] b) {
		return insert(i, b, 0, b.length);
	}
	
	
	public ByteBuffer insert(int i, byte[] b, int off, int len) {
		if (i < 0 || i > length)
			throw new IndexOutOfBoundsException();
		Assert.assertNotNull(b);
		Assert.assertRangeInBounds(b.length, off, len);
		ensureCapacity(len);
		System.arraycopy(buffer, i, buffer, i + len, length - i);
		System.arraycopy(b, off, buffer, i, len);
		length += len;
		return this;
	}
	
	
	public ByteBuffer insert(int i, ByteBuffer b) {
		Assert.assertNotNull(b);
		return insert(i, b.buffer, 0, b.length);
	}
	
	
	public ByteBuffer remove(int i) {
		Assert.assertIndexInBounds(length, i);
		System.arraycopy(buffer, i + 1, buffer, i, length - i - 1);
		length--;
		return this;
	}
	
	
	public ByteBuffer remove(int off, int len) {
		Assert.assertRangeInBounds(length, off, len);
		System.arraycopy(buffer, off + len, buffer, off, length - off - len);
		length -= len;
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
