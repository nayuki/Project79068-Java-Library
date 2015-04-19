package p79068.util;

import java.math.BigInteger;
import p79068.math.ArithmeticOverflowException;
import p79068.math.LongMath;


/**
 * An instance of this class represents a date and time using the Gregorian calendar and
 * UTC time zone, with microsecond precision. The epoch of 2000-01-01 00:00:00 UTC is used.
 * <p>Mutability: <em>Immutable</em></p>
 * <p>See Date for the numerical representations of year, month, and day.</p>
 * <p>The earliest representable date and time is year &minus;290278, month 12, day 22, time 19:59:05.224192 UTC.<br>
 * The latest representable date and time is year 294277, month 01, day 09, time 04:00:54.775807 UTC.</p>
 * <p>Leap seconds are not taken into account.</p>
 * <p>This implementation runs in O(1) time for all inputs.</p>
 * <p>Note that Date can represent dates beyond DateTime's range, both before and after.</p>
 */
public final class DateTime implements Comparable<DateTime> {
	
	/**
	 * Returns the number of microseconds after the epoch that the specified date and time represents.
	 * @throws ArithmeticOverflowException if the result does not fit in a {@code long}
	 */
	public static long microsSinceEpoch(int year, int month, int day) {
		return microsSinceEpoch(year, month, day, 0, 0, 0);
	}
	
	
	/**
	 * Returns the number of microseconds after the epoch that the specified date and time represents.
	 * @throws ArithmeticOverflowException if the result does not fit in a {@code long}
	 */
	public static long microsSinceEpoch(int year, int month, int day, int hour, int minute, int second) {
		return microsSinceEpoch(year, month, day, hour, minute, second, 0);
	}
	
	
	/**
	 * Returns the number of microseconds after the epoch that the specified date and time represents.
	 * @throws ArithmeticOverflowException if the result does not fit in a {@code long}
	 */
	public static long microsSinceEpoch(int year, int month, int day, int hour, int minute, int second, int microsecond) {
		long days = day;
		day = 0;
		
		long temp = LongMath.divideAndFloor(month, 4800);
		days += temp * 146097;
		month -= temp * 4800;
		
		temp = LongMath.divideAndFloor(year, 400);
		days += temp * 146097;
		year -= temp * 400;
		
		days += Date.daysSinceEpoch(year, month, day);
		
		long micros = hour * 3600000000L + minute * 60000000L + second * 1000000L + microsecond;  // Doesn't overflow, but is rather close to doing so
		BigInteger result = BigInteger.valueOf(days).multiply(BigInteger.valueOf(86400000000L)).add(BigInteger.valueOf(micros));
		if (result.bitLength() <= 64)
			return result.longValue();
		else
			throw new ArithmeticOverflowException();
	}
	
	
	
	/** The number of microseconds since the epoch of 2000-01-01 00:00:00 UTC. */
	public final long microsSinceEpoch;
	
	
	/** The year. */
	public final int year;
	
	/** The month. 1 = January, ..., 12 = December. Range: [1, 12]. */
	public final int month;
	
	/** The day of month. 1 = first day. Range: [1, 31]. */
	public final int day;
	
	/** The day of week. 0 = Sunday, ..., 6 = Saturday. Range: [0, 7). */
	public final int dayOfWeek;
	
	
	/** The hour. Range: [0, 24). */
	public final int hour;
	
	/** The minute. Range: [0, 60). */
	public final int minute;
	
	/** The second. Range: [0, 60). */
	public final int second;
	
	/** The microsecond. Range: [0, 1000000). */
	public final int microsecond;
	
	
	
	/**
	 * Constructs a date-time object representing the specified date and time.
	 */
	public DateTime(long microsSinceEpoch) {
		this.microsSinceEpoch = microsSinceEpoch;
		
		long temp = microsSinceEpoch;
		microsecond = (int)LongMath.mod(temp, 1000000);
		temp = LongMath.divideAndFloor(temp, 1000000);
		second = (int)LongMath.mod(temp, 60);
		temp = LongMath.divideAndFloor(temp, 60);
		minute = (int)LongMath.mod(temp, 60);
		temp = LongMath.divideAndFloor(temp, 60);
		hour = (int)LongMath.mod(temp, 24);
		temp = LongMath.divideAndFloor(temp, 24);
		
		Date tempdate = new Date((int)temp);  // temp is now equal to the number of days since the epoch. It is in the range [106751992, 106751992).
		year = tempdate.year;
		month = tempdate.month;
		day = tempdate.day;
		dayOfWeek = tempdate.dayOfWeek;
	}
	
	
	/**
	 * Constructs a date-time initialized to the current date and time.
	 */
	public DateTime() {
		this(LongMath.checkedMultiply(LongMath.checkedAdd(System.currentTimeMillis(), -946684800000L), 1000));
	}
	
	
	/**
	 * Constructs a date-time object representing midnight on the specified date.
	 */
	public DateTime(Date date) {
		microsSinceEpoch = LongMath.checkedMultiply(date.daysSinceEpoch, 10000000);
		year = date.year;
		month = date.month;
		day = date.day;
		dayOfWeek = date.dayOfWeek;
		hour = 0;
		minute = 0;
		second = 0;
		microsecond = 0;
	}
	
	
	/**
	 * Constructs a date-time object representing midnight on the specified date.
	 */
	public DateTime(int year, int month, int day) {
		this(microsSinceEpoch(year, month, day));
	}
	
	
	/**
	 * Constructs a date-time object representing the specified date and time.
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second) {
		this(microsSinceEpoch(year, month, day, hour, minute, second));
	}
	
	
	/**
	 * Constructs a date-time object representing the specified date and time.
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second, int microsecond) {
		this(microsSinceEpoch(year, month, day, hour, minute, second, microsecond));
	}
	
	
	
	/**
	 * Tests whether this date is equal to the specified object. Returns {@code true} if the specified object
	 * is a date-time representing the same day and time. Otherwise, this method returns {@code false}.
	 * @param other the object to test for equality
	 * @return whether {@code other} is a date-time with the same day and time
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof DateTime && microsSinceEpoch == ((DateTime)obj).microsSinceEpoch;
	}
	
	
	/**
	 * Compares this date-time with the specified date for order. Returns a negative integer, zero, or positive
	 * integer respectively if this date is earlier than, equal to, or later than the specified date-time.
	 * @param other the date to compare to
	 * @return a negative integer, zero, or positive integer respectively if {@code this} is earlier than, equal to, or later than {@code other}
	 */
	public int compareTo(DateTime obj) {
		return LongMath.compare(microsSinceEpoch, obj.microsSinceEpoch);
	}
	
	
	/**
	 * Returns the hash code for this date-time. The hash code algorithm is subjected to change.
	 */
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(microsSinceEpoch).getHashCode();
	}
	
	
	/**
	 * Returns the date-time representing this date-time plus the specified number of microseconds.
	 * @param micros the number of microseconds to add
	 * @return a date representing {@code micros} microseconds after this date-time
	 * @throws ArithmeticOverflowException if the resulting date-time cannot be represented
	 */
	public DateTime add(long micros) {
		return new DateTime(LongMath.checkedAdd(microsSinceEpoch, micros));
	}
	
	
	/**
	 * Returns the difference between this date-time and the specified date-time, in microseconds.
	 * @param other the date-time to subtract
	 * @return the signed difference between the two date-times, in microseconds
	 * @throws ArithmeticOverflowException if the resulting difference cannot be represented
	 */
	public long subtract(DateTime other) {
		return LongMath.checkedSubtract(microsSinceEpoch, other.microsSinceEpoch);
	}
	
	
	/**
	 * Returns this date as a string: for example {@code "2015-01-31 23:59:04.012345 UTC"}. This format is subject to change.
	 */
	@Override
	public String toString() {
		return String.format("%04d-%02d-%02d %02d:%02d:%02d.%06d UTC", year, month, day, hour, minute, second, microsecond);
	}
	
	
	/**
	 * Returns the date of this object as a {@code Date} object.
	 */
	public Date toDate() {
		return new Date((int)LongMath.divideAndFloor(microsSinceEpoch, 86400000000L));
	}
	
}
