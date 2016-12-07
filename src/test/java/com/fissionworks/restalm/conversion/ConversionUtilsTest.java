package com.fissionworks.restalm.conversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConversionUtilsTest {

	@Test
	public void conversionUtils_shouldHaveInaccessibleConstructor() {
		final Constructor<?>[] constructors = ConversionUtils.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		Assert.assertFalse(constructor.isAccessible(), "Constructor should be inaccessible");
	}

	@Test
	public void conversionUtils_shouldHaveOneConstructor() {
		final Constructor<?>[] constructors = ConversionUtils.class.getDeclaredConstructors();
		Assert.assertEquals(constructors.length, 1);
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void conversionUtilsInstantiationThroughReflection_shouldThrowException()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Constructor<?>[] constructors = ConversionUtils.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void removeHtml_withBlankString_shouldReturnUnchangedString() {
		final String blankString = "   ";
		Assert.assertEquals(ConversionUtils.removeHtml(blankString), blankString);
	}

	@Test
	public void removeHtml_withStringWithoutHtml_shouldReturnUnchangedString() {
		final String string = "Sring with no HTML!";
		Assert.assertEquals(ConversionUtils.removeHtml(string), string);
	}

	@Test
	public void removeHtml_withStringWithSimpleHtml_shouldReturnStringTextOnly() {
		final String string = "<div>some text in a div</div><br>";
		Assert.assertEquals(ConversionUtils.removeHtml(string), "some text in a div");
	}
}
