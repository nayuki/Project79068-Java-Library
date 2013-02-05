package p79068;


/**
 * Provides convenience methods for checking nulls and array bounds.
 * <p>Instantiability: <em>Not applicable</em></p>
 */
public final class Assert {
	
	/**
	 * Throws a {@code NullPointerException} if the specified object is {@code null}; otherwise returns normally.
	 * @throws NullPointerException if the object is {@code null}
	 */
	public static void assertNotNull(Object obj) {
		if (obj == null)
			throw new NullPointerException("Non-null object expected");
	}
	
	
	public static void assertNotNull(Object... obj) {
		for (Object o : obj) {
			if (o == null)
				throw new NullPointerException("Non-null object expected");
		}
	}
	
	
	/**
	 * Throws an {@code IndexOutOfBoundsException} if the specified index is outside the specified bounds; otherwise returns normally.
	 * <p>An index is out of bounds if at least one of these is true:</p>
	 * <ul>
	 *  <li>{@code accessIndex &lt; 0}</li>
	 *  <li>{@code accessIndex &gt;= arrayLength}</li>
	 * </ul>
	 * @throws IllegalArgumentException if the array length is negative (supersedes index out of bounds exception)
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public static void assertIndexInBounds(int arrayLength, int accessIndex) {
		if (arrayLength < 0)
			throw new IllegalArgumentException(String.format("Negative array length (%d)", arrayLength));
		if (accessIndex < 0 || accessIndex >= arrayLength)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access index = %d", 0, arrayLength, accessIndex));
	}
	
	
	/**
	 * Throws an {@code IndexOutOfBoundsException} if the specified range is outside the specified bounds; otherwise returns normally.
	 * <p>An index is out of bounds if at least one of these is true:</p>
	 * <ul>
	 *  <li>{@code accessOffset &lt; 0}</li>
	 *  <li>{@code accessOffset &gt; arrayLength}</li>
	 *  <li>{@code accessLength &lt; 0}</li>
	 *  <li>{@code accessOffset+accessLength &gt; arrayLength} (computed without overflowing)</li>
	 * </ul>
	 * @throws IllegalArgumentException if the array length is negative (supersedes index out of bounds exception)
	 * @throws IndexOutOfBoundsException if the range is out of bounds
	 */
	public static void assertRangeInBounds(int arrayLength, int accessOffset, int accessLength) {
		if (arrayLength < 0)
			throw new IllegalArgumentException(String.format("Negative array length (%d)", arrayLength));
		if (accessOffset < 0 || accessOffset > arrayLength || accessLength < 0 || accessLength > arrayLength - accessOffset)
			throw new IndexOutOfBoundsException(String.format("Bounds = [%d,%d), access range = [%d,%d)", 0, arrayLength, accessOffset, (long)accessOffset + accessLength));
	}
	
	
	
	/**
	 * Not instantiable.
	 */
	private Assert() {}
	
}
