package com.fissionworks.restalm.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlmDateFormatterTest {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String INVALID_DATE = "2014-99-02";

	private static final String INVALID_DATETIME = "2014-12-02 25:33:22";

	private static final String VALID_DATE = "2014-01-02";

	private static final String VALID_DATETIME = "2014-01-02 13:14:15";

	@Test
	public void AlmDateFormatter_shouldHaveInaccessibleConstructor() {
		final Constructor<?>[] constructors = AlmDateFormatter.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		Assert.assertFalse(constructor.isAccessible(), "Constructor should be inaccessible");
	}

	@Test
	public void AlmDateFormatter_shouldHaveOneConstructor() {
		final Constructor<?>[] constructors = AlmDateFormatter.class.getDeclaredConstructors();
		Assert.assertEquals(constructors.length, 1);
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void AlmDateFormatterInstantiationThroughReflection_shouldThrowException() throws Exception {
		final Constructor<?>[] constructors = AlmDateFormatter.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		constructor.setAccessible(true);
		Assert.assertEquals(constructor.newInstance().getClass(), AlmDateFormatter.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createDate_withInvalidDateString_shouldThrowException() throws ParseException {
		AlmDateFormatter.createDate(INVALID_DATE);
	}

	@Test
	public void createDate_withValidDateString_shouldCreateDate() throws ParseException {
		final Date actualDate = AlmDateFormatter.createDate(VALID_DATE);
		Assert.assertEquals(actualDate, DateUtils.parseDateStrictly(VALID_DATE, DATE_FORMAT));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void createDateTime_withInvalidDateTimeString_shouldThrowException() throws ParseException {
		AlmDateFormatter.createDateTime(INVALID_DATETIME);
	}

	@Test
	public void createDateTime_withValidDateTimeString_shouldCreateDate() throws ParseException {
		final Date actualDate = AlmDateFormatter.createDateTime(VALID_DATETIME);
		Assert.assertEquals(actualDate, DateUtils.parseDateStrictly(VALID_DATETIME, DATETIME_FORMAT));
	}
}
