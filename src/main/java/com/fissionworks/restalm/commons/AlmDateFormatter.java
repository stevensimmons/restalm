package com.fissionworks.restalm.commons;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Utility class that encapsulates the date/datetime formats used in ALM, as
 * well as the checked {@link ParseException} thrown during date string parsing
 * that is not correctable at runtime.
 *
 * @since 1.0.0
 *
 */
public final class AlmDateFormatter {

	private static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

	private static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private AlmDateFormatter() {
		throw new IllegalStateException("class is not instantiable");
	}

	/**
	 * creates a {@link Date} by parsing the given date string.
	 *
	 * @param date
	 *            The date string to parse; Must be in the format "yyyy-MM-dd".
	 * @return A {@link Date} equivalent to the given date string.
	 * @throws IllegalArgumentException
	 *             thrown if the given date string is not in the format
	 *             "yyyy-MM-dd" or has invalid values (i.e. 2014-13-32).
	 * @since 1.0.0
	 */
	public static Date createDate(final String date) {
		try {
			return DateUtils.parseDateStrictly(date, STANDARD_DATE_FORMAT);
		} catch (final ParseException exception) {
			throw new IllegalArgumentException(
					"ParseException thrown while parsing date string; string must conform to yyyy-MM-dd", exception);
		}

	}

	/**
	 * creates a {@link Date} by parsing the given datetime string.
	 *
	 * @param datetime
	 *            The datetime string to parse; Must be in the format
	 *            "yyyy-MM-dd HH:mm:ss".
	 * @return A {@link Date} equivalent to the given datetime string.
	 * @throws IllegalArgumentException
	 *             thrown if the given date string is not in the format
	 *             "yyyy-MM-dd HH:mm:ss" or has invalid values (i.e. 2014-13-32
	 *             25:61:62).
	 * @since 1.0.0
	 */
	public static Date createDateTime(final String datetime) {
		try {
			return DateUtils.parseDateStrictly(datetime, STANDARD_DATETIME_FORMAT);
		} catch (final ParseException exception) {
			throw new IllegalArgumentException(
					"ParseException thrown while parsing datetime string; string must conform to yyyy-MM-dd hh:mm:ss",
					exception);
		}

	}

	/**
	 * Creates a string representation of the given {@link Calendar} in the
	 * standard ALM date format of "yyyy-MM-dd".
	 *
	 * @param calendar
	 *            The {@link Calendar} to create a string representation of.
	 * @return A string in the format "yyyy-MM-dd".
	 * @since 1.0.0
	 */
	public static String getStandardDate(final Calendar calendar) {
		return DateFormatUtils.format(calendar, STANDARD_DATE_FORMAT);
	}

	/**
	 * Creates a string representation of the given {@link Calendar} in the
	 * standard ALM datetime format of "yyyy-MM-dd HH:mm:ss".
	 *
	 * @param calendar
	 *            The {@link Calendar} to create a string representation of.
	 * @return A string in the format "yyyy-MM-dd HH:mm:ss".
	 * @since 1.0.0
	 */
	public static String getStandardDateTime(final Calendar calendar) {
		return DateFormatUtils.format(calendar, STANDARD_DATETIME_FORMAT);
	}

}
