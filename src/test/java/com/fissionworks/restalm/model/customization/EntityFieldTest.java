package com.fissionworks.restalm.model.customization;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityFieldTest {

	@Test
	public void equals_comparingEntityFieldToAnotherObjectType_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		Assert.assertFalse(entityFieldOne.equals(new Object()));
	}

	@Test
	public void equals_comparingEntityFieldToEntityFieldWithDifferentEditable_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelOne");
		entityFieldTwo.setEditable(false);
		Assert.assertFalse(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToEntityFieldWithDifferentLabel_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelTwo");
		Assert.assertFalse(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToEntityFieldWithDifferentName_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldTwo", "labelOne");
		Assert.assertFalse(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToEntityFieldWithDifferentRequired_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelOne");
		entityFieldTwo.setRequired(false);
		Assert.assertFalse(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToEntityFieldWithDifferentSystem_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelOne");
		entityFieldTwo.setSystem(false);
		Assert.assertFalse(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToEqualEntityField_shouldReturnTrue() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelOne");
		Assert.assertTrue(entityFieldOne.equals(entityFieldTwo));
	}

	@Test
	public void equals_comparingEntityFieldToItself_shouldReturnTrue() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		Assert.assertTrue(entityFieldOne.equals(entityFieldOne));
	}

	@Test
	public void equals_comparingEntityFieldToNull_shouldReturnFalse() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		Assert.assertFalse(entityFieldOne.equals(null));
	}

	@Test
	public void hashCode_forEqualEntityFields_shouldBeEqual() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldOne", "labelOne");
		Assert.assertEquals(entityFieldOne.hashCode(), entityFieldTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualEntityFields_shouldNotBeEqual() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final EntityField entityFieldTwo = createFullEntityField("fieldTwo", "labelOne");
		Assert.assertNotEquals(entityFieldOne.hashCode(), entityFieldTwo.hashCode());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instatiate_withBlankName_shouldThrowException() {
		new EntityField("   ", "labelOne");
	}

	@Test
	public void instatiate_withNullLabel_shouldSetLabelToEmptyString() {
		final EntityField field = new EntityField("nameOne", null);
		Assert.assertEquals(field.getLabel(), "");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instatiate_withNullName_shouldThrowException() {
		new EntityField(null, "labelOne");
	}

	@Test
	public void instatiateAndSetAllFields_shouldCreateEntityField() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		Assert.assertEquals(entityFieldOne.getName(), "fieldOne");
		Assert.assertEquals(entityFieldOne.getLabel(), "labelOne");
		Assert.assertEquals(entityFieldOne.isEditable(), true);
		Assert.assertEquals(entityFieldOne.isRequired(), true);
		Assert.assertEquals(entityFieldOne.isSystem(), true);

	}

	@Test
	public void toString_shouldReturnString_withFields() {
		final EntityField entityFieldOne = createFullEntityField("fieldOne", "labelOne");
		final String entityFieldString = entityFieldOne.toString();
		Assert.assertTrue(StringUtils.contains(entityFieldString, "name"));
		Assert.assertTrue(StringUtils.contains(entityFieldString, "label"));
		Assert.assertTrue(StringUtils.contains(entityFieldString, "editable"));
		Assert.assertTrue(StringUtils.contains(entityFieldString, "required"));
		Assert.assertTrue(StringUtils.contains(entityFieldString, "system"));
	}

	private EntityField createFullEntityField(final String name, final String label) {
		final EntityField field = new EntityField(name, label);
		field.setEditable(true);
		field.setRequired(true);
		field.setSystem(true);
		return field;
	}
}
