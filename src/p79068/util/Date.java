package p79068.util;

import p79068.math.ArithmeticOverflowException;
import p79068.math.IntegerMath;
import p79068.math.LongMath;


/**
 * A date on the Gregorian calendar. The epoch of 2000-01-01 is used. Date objects are immutable.
 * <p>Years in CE/AD are represented normally. Years in BCE/BC count down from zero:
 *   {@code 0} is year 1 BCE, {@code -1} is year 2 BCE, {@code -2} is year 3 BCE, and so on.</p>
 * <p>Months:</p>
 * <ul>
 *  <li>{@code 01}: January</li>
 *  <li>{@code 02}: February</li>
 *  <li>{@code 03}: March</li>
 *  <li>{@code 04}: April</li>
 *  <li>{@code 05}: May</li>
 *  <li>{@code 06}: June</li>
 *  <li>{@code 07}: July</li>
 *  <li>{@code 08}: August</li>
 *  <li>{@code 09}: September</li>
 *  <li>{@code 10}: October</li>
 *  <li>{@code 11}: November</li>
 *  <li>{@code 12}: December</li>
 * </ul>
 * <p>Days of the week:</p>
 * <ul>
 *  <li>{@code 0}: Sunday</li>
 *  <li>{@code 1}: Monday</li>
 *  <li>{@code 2}: Tuesday</li>
 *  <li>{@code 3}: Wednesday</li>
 *  <li>{@code 4}: Thursday</li>
 *  <li>{@code 5}: Friday</li>
 *  <li>{@code 6}: Saturday</li>
 * </ul>
 * <p>The earliest representable date is year &minus;5877611, month 06, day 22.<br>
 *  The latest representable date is year 5881610, month 07, day 11.</p>
 * <p>All methods run in <var>O</var>(1) time for all inputs.</p>
 */
public final class Date implements Comparable<Date> {
	
	/**
	 * The number of days in the month for non-leap years. Month 0 = January, ..., month 11 = December.
	 */
	private static final int[] monthLength = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	
	/**
	 * The cumulative number of days since March 1st. 0 = March 1st, 1 = April 1st, ..., 11 = February 1st of next year.
	 */
	private static final int[] cumulativeDays = {0, 31, 61, 92, 122, 153, 184, 214, 245, 275, 306, 337};
	
	
	
	/**
	 * Tests whether the specified year is a leap year. A year divisible by 400 is a leap year.
	 * Otherwise, a year divisible by 4 but not by 100 is a leap year. Other years are not leap years.
	 * @param year the year to test
	 * @return {@code true} if and only if {@code year} is a leap year
	 */
	public static boolean isLeapYear(int year) {
		return (IntegerMath.mod(year, 4) == 0 && IntegerMath.mod(year, 100) != 0) || IntegerMath.mod(year, 400) == 0;
	}
	
	
	/**
	 * Returns the number of days in the specified month of the specified year.
	 * This method returns the correct result for all arguments. For example, January has 31 days,
	 * April has 30 days, February has 28 days on a non-leap year, and February has 29 days on a leap year.
	 * @param year the year
	 * @param month the month
	 * @return the number of days in the month
	 */
	public static int monthLength(int year, int month) {
		year = IntegerMath.mod(year, 400);                 // Reduce year to [0, 400)
		month = (int)LongMath.mod((long)month - 1, 4800);  // Reduce month to [0, 4800)
		year += month / 12;                                // Reduce month into year. Year is now in [0, 799).
		month %= 12;                                       // Reduce month to [0, 12)
		
		if (month != 1 || !Date.isLeapYear(year))
			return monthLength[month];
		else
			return 29;
	}
	
	
	/**
	 * Returns the day of week of the specified date. This method returns the correct result for all arguments.
	 * @param year the year
	 * @param month the month
	 * @param day the day of month
	 * @return the day of week, with {@code 0} = Sunday, {@code 1} = Monday, ..., {@code 6} = Saturday
	 */
	public static int dayOfWeek(int year, int month, int day) {
		year = IntegerMath.mod(year, 400);                 // Reduce year to [0, 400)
		month = (int)LongMath.mod((long)month - 3, 4800);  // Convert month from 1 = January to 0 = March, and reduce to [0, 4800) (i.e. 400 years)
		year += month / 12;                                // Reduce month into year. Year is now in [0, 799).
		month %= 12;                                       // Reduce month to [0, 12)
		day = IntegerMath.mod(day, 7);                     // Reduce day to [0, 7)
		return (year + year/4 - year/100 + year/400 + (13*month+2)/5 + day + 2) % 7;  // Zeller's congruence. Note: The left side of the remainder operator is in [2, 1029).
	}
	
	
	/**
	 * Returns the day of week of the specified date. This method returns the correct result for all arguments.
	 * @param daysSinceEpoch the number of days since the epoch of 2000-01-01
	 * @return the day of week, with {@code 0} = Sunday, {@code 1} = Monday, ..., {@code 6} = Saturday
	 */
	public static int dayOfWeek(int daysSinceEpoch) {
		// The epoch (2000-01-01) is a Saturday (6)
		return (IntegerMath.mod(daysSinceEpoch, 7) + 6) % 7;
	}
	
	
	/**
	 * Returns the number of days after the epoch that the specified date represents.
	 * @param year the year
	 * @param month the month
	 * @param day the day of month
	 * @throws ArithmeticOverflowException if the result does not fit in an {@code int}
	 */
	public static int daysSinceEpoch(int year, int month, int day) {
		// Use extra precision to prevent overflow
		long y = year;
		long m = month;
		long d = day;
		
		// Process the month
		m -= 3;  // Convert month from 1 = January to 0 = March. January and February are considered to belong to the "previous" year.
		y += LongMath.divideAndFloor(m, 12);       // Reduce month into year
		m -= LongMath.divideAndFloor(m, 12) * 12;  // Reduce month to [0,12)
		
		d--;  // Convert day from 1-based to 0-based. Use d as the accumulator.
		d += LongMath.divideAndFloor(y, 400) * 146097;  // Reduce year into day. There are 146097 days in any contiguous 400 years.
		y -= LongMath.divideAndFloor(y, 400) * 400;     // Reduce year to [0, 400)
		d += y*365 + y/4 - y/100 + y/400 + ((int)m * 153 + 2) / 5;  // Add the days in the years (taking leap years into account) and months
		
		d -= 730425;  // Convert epoch from 0000-03-01 to 2000-01-01
		
		if (d >= Integer.MIN_VALUE && d <= Integer.MAX_VALUE)
			return (int)d;
		else
			throw new ArithmeticOverflowException();
	}
	
	
	
	/** The number of days since the epoch of 2000-01-01. */
	public final int daysSinceEpoch;
	
	/** The year. */
	public final int year;
	
	/** The month. 1 = January, ..., 12 = December. Range: [1, 12]. */
	public final int month;
	
	/** The day of month. 1 = first day. Range: [1, 31]. */
	public final int day;
	
	/** The day of week. 0 = Sunday, ..., 6 = Saturday. Range: [0, 7). */
	public final int dayOfWeek;
	
	
	
	/**
	 * Constructs a date at the specified number of days after the epoch (2000-01-01). The epoch (January 1, 2000) is day 0.
	 * <p>Note: Epochs used in other systems, represented in this system:</p>
	 * <ul>
	 *  <li>1601-01-01 = day {@code -145731}</li>
	 *  <li>1900-01-01 = day {@code -36524}</li>
	 *  <li>1970-01-01 = day {@code -10957}</li>
	 *  <li>1980-01-01 = day {@code -7305}</li>
	 * </ul>
	 * @param daysSinceEpoch the number of days since the epoch of 2000-01-01
	 */
	public Date(int daysSinceEpoch) {
		this.daysSinceEpoch = daysSinceEpoch;
		dayOfWeek = dayOfWeek(daysSinceEpoch);
		
		long tempday = (long)daysSinceEpoch + 730425;  // Convert epoch from 2000-01-01 to 0000-03-01
		int y = (int)LongMath.divideAndFloor(tempday, 146097) * 400;   // Reduce tempday into year
		tempday -= LongMath.divideAndFloor(tempday, 146097) * 146097;  // Reduce tempday to [0, 146097)
		
		// Calculate the year, taking leap years into account. Valid for and only for d in [0, 146097).
		int d = (int)tempday;
		y += Math.min(d / 36524, 3) * 100;    // There are *usually* 36524 days in 100 years
		d -= Math.min(d / 36524, 3) * 36524;  // Reduce d to *approximately* [0, 100 years)
		y += d / 1461 * 4;                    // There are *usually* 1461 days in 4 years
		d -= d / 1461 * 1461;                 // Reduce d to *approximately* [0, 4 years)
		y += Math.min(d / 365, 3);            // There are *usually* 365 days in 1 year
		d -= Math.min(d / 365, 3) * 365;      // Reduce d to [0, 366)
		
		// Calculate the month
		int m = 0;
		while (m < 11 && cumulativeDays[m + 1] <= d)  // Find the month that the day belongs in
			m++;
		d = d - cumulativeDays[m] + 1;  // Convert to day of month, 1-based
		m += 3;                         // Convert from 0 = March to 1 = January
		if (m > 12) {                   // Reduce month to [1, 12]
			y++;
			m -= 12;
		}
		
		this.year = y;
		this.month = m;
		this.day = d;
	}
	
	
	/**
	 * Creates a date at the specified year, month, and day.
	 * @param year the year
	 * @param month the month
	 * @param day the day of month
	 * @throws ArithmeticOverflowException if the date cannot be represented
	 */
	public Date(int year, int month, int day) {
		this(daysSinceEpoch(year, month, day));
	}
	
	
	
	/**
	 * Returns a date representing this date plus the specified number of days.
	 * Addition and subtraction are related by the equation {@code this.add(x).subtract(this) == x}
	 * (for {@code int x}), assuming no overflow.
	 * @param days the number of days to add
	 * @return a date representing {@code days} days after this date
	 * @throws ArithmeticOverflowException if the resulting date cannot be represented
	 */
	public Date add(int days) {
		return new Date(IntegerMath.checkedAdd(daysSinceEpoch, days));
	}
	
	
	/**
	 * Returns the signed difference between this date and the specified date, in days.
	 * Addition and subtraction are related by the equation {@code this.add(x).subtract(this) == x}
	 * (for {@code int x}), assuming no overflow.
	 * @param other the date to subtract
	 * @return the number of days to add to {@code date} in order to get {@code this}
	 * @throws ArithmeticOverflowException if the resulting difference cannot be represented
	 */
	public int subtract(Date other) {
		return IntegerMath.checkedSubtract(daysSinceEpoch, other.daysSinceEpoch);
	}
	
	
	
	/**
	 * Tests whether this date is equal to the specified object. Returns {@code true} if the specified
	 * object is a date representing the same day. Otherwise, this method returns {@code false}.
	 * @param other the object to test for equality
	 * @return whether {@code other} is a date with the same day
	 */
	@Override
	public boolean equals(Object other) {
		// The two daysSinceEpoch are equal if and only if all the 4 other fields (year, month, day, dayOfWeek) are equal too
		return other instanceof Date && daysSinceEpoch == ((Date)other).daysSinceEpoch;
	}
	
	
	/**
	 * Compares this date with the specified date for order. Returns a negative integer, zero, or positive
	 * integer respectively if this date is earlier than, equal to, or later than the specified date.
	 * @param other the date to compare to
	 * @return a negative integer, zero, or positive integer respectively if {@code this} is earlier than, equal to, or later than {@code other}
	 */
	public int compareTo(Date other) {
		return IntegerMath.compare(daysSinceEpoch, other.daysSinceEpoch);
	}
	
	
	/**
	 * Returns the hash code for this date. The hash code algorithm is subjected to change.
	 */
	@Override
	public int hashCode() {
		return HashCoder.newInstance().add(daysSinceEpoch).getHashCode();
	}
	
	
	/**
	 * Returns this date as a string: e.g., {@code <var>yyyy</var>-<var>mm</var>-<var>dd</var>}. This format is subject to change.
	 */
	@Override
	public String toString() {
		return String.format("%04d-%02d-%02d", year, month, day);
	}
	
}
