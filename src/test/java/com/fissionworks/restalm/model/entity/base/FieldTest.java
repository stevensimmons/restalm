package com.fissionworks.restalm.model.entity.base;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FieldTest {

	@Test
	public void equals_comparingFieldToDifferentObjectType_shouldReturnFalse() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertFalse(fieldOne.equals(new Object()));
	}

	@Test
	public void equals_comparingFieldToFieldToEqualField_shouldReturnTrue() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final Field fieldTwo = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertTrue(fieldOne.equals(fieldTwo));
	}

	@Test
	public void equals_comparingFieldToFieldWithDifferentName_shouldReturnFalse() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final Field fieldTwo = new Field("fieldTwoName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertFalse(fieldOne.equals(fieldTwo));
	}

	@Test
	public void equals_comparingFieldToFieldWithDifferentValues_shouldReturnFalse() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final Field fieldTwo = new Field("fieldOneName", Arrays.asList("valueOne"));
		Assert.assertFalse(fieldOne.equals(fieldTwo));
	}

	@Test
	public void equals_comparingFieldToItself_shouldReturnTrue() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertTrue(fieldOne.equals(fieldOne));
	}

	@Test
	public void equals_comparingFieldToNull_shouldReturnFalse() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertFalse(fieldOne.equals(null));
	}

	@Test
	public void hashCode_forEqualfields_shouldBeEqual() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final Field fieldTwo = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertEquals(fieldOne.hashCode(), fieldTwo.hashCode());
	}

	@Test
	public void hashCode_forUnequalfields_shouldNotBeEqual() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final Field fieldTwo = new Field("fieldTwoName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertNotEquals(fieldOne.hashCode(), fieldTwo.hashCode());
	}

	@Test
	public void instantiation_withWithValidNameAndPopulatedList_shouldCreateField() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertEquals(fieldOne.getName(), "fieldOneName");
		Assert.assertTrue(fieldOne.getValues().containsAll(Arrays.asList("valueOne", "ValueTwo")));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantion_withNullName_shouldThrowException() {
		new Field(null, new ArrayList<String>());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantion_withSingleArgumentConstructorAndBlankName_shouldThrowException() {
		new Field("  ", new ArrayList<String>());
	}

	@Test
	public void isEmpty_withFieldWithNoValues_shouldReturnTrue() {
		final Field fieldOne = new Field("fieldOneName", new ArrayList<String>());
		Assert.assertTrue(fieldOne.isEmpty());
	}

	@Test
	public void isEmpty_withFieldWithValues_shouldReturnFalse() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		Assert.assertFalse(fieldOne.isEmpty());
	}

	@Test
	public void toString_shouldReturnStringContainingNameAndValues() {
		final Field fieldOne = new Field("fieldOneName", Arrays.asList("valueOne", "ValueTwo"));
		final String fieldOneString = fieldOne.toString();
		Assert.assertTrue(StringUtils.contains(fieldOneString, "fieldOneName"));
		Assert.assertTrue(StringUtils.contains(fieldOneString, "valueOne"));
		Assert.assertTrue(StringUtils.contains(fieldOneString, "ValueTwo"));
	}
}
