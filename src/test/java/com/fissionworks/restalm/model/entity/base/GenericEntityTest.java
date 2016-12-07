package com.fissionworks.restalm.model.entity.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;

public class GenericEntityTest {

	@Test
	public void addRelatedEntity_shouldAddRelatedEntity() {
		final GenericEntity entity = new GenericEntity("test", createFieldListOne());
		entity.addRelatedEntity(new GenericEntity("related", createFieldListOne()));
		Assert.assertEquals(entity.getRelatedEntities().size(), 1);
		Assert.assertEquals(entity.getRelatedEntities().get(0), new GenericEntity("related", createFieldListOne()));
	}

	@Test
	public void equals_comparingEqualGenericEntities_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListOne());

		Assert.assertTrue(entity.equals(entityTwo));
	}

	@Test
	public void equals_comparingGenericEntityToGenericEntityWithDifferentFieldMap_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final List<Field> fieldMap = createFieldListOne();
		fieldMap.remove(0);
		final GenericEntity entityTwo = new GenericEntity("theType", fieldMap);
		Assert.assertFalse(entity.equals(entityTwo));
	}

	@Test
	public void equals_comparingGenericEntityToGenericEntityWithDifferentType_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType2", createFieldListOne());

		Assert.assertFalse(entity.equals(entityTwo));
	}

	@Test
	public void equals_comparingGenericEntityToItself_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertTrue(entity.equals(entity));
	}

	@Test
	public void equals_comparingGenericEntityToNull_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertFalse(entity.equals(null));
	}

	@Test
	public void equals_comparingGenericEntityToObjectOfDifferentType_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertFalse(entity.equals(new Object()));
	}

	@Test
	public void formatForAdd_withEntityHavingEmptyField_shouldRemoveEmptyField() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", new ArrayList<String>()));

		final GenericEntity entity = new GenericEntity("theType", fields);

		final EntityFieldCollection fieldCollection = new EntityFieldCollection();
		fieldCollection.addEntityField(createEntityField("fieldOne", "fieldOne", true, true, true));
		fieldCollection.addEntityField(createEntityField("fieldTwo", "fieldTwo", true, true, true));
		entity.formatForAdd(fieldCollection);
		final Collection<Field> formattedFields = entity.getFields();
		Assert.assertTrue(formattedFields.size() == 1);
		Assert.assertTrue(formattedFields.contains(new Field("fieldOne", Arrays.asList("value"))));

	}

	@Test
	public void formatForAdd_withEntityHavingUnaddableField_shouldRemoveUnaddableField() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));

		final GenericEntity entity = new GenericEntity("theType", fields);

		final EntityFieldCollection fieldCollection = new EntityFieldCollection();
		fieldCollection.addEntityField(createEntityField("fieldOne", "fieldOne", true, true, true));
		fieldCollection.addEntityField(createEntityField("fieldTwo", "fieldTwo", false, false, true));
		entity.formatForAdd(fieldCollection);
		final Collection<Field> formattedFields = entity.getFields();
		Assert.assertTrue(formattedFields.size() == 1);
		Assert.assertTrue(formattedFields.contains(new Field("fieldOne", Arrays.asList("value"))));

	}

	@Test
	public void formatForUpdate_withEntityHavingUneditableField_shouldRemoveUneditableField() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));

		final GenericEntity entity = new GenericEntity("theType", fields);

		final EntityFieldCollection fieldCollection = new EntityFieldCollection();
		fieldCollection.addEntityField(createEntityField("fieldOne", "fieldOne", true, true, true));
		fieldCollection.addEntityField(createEntityField("fieldTwo", "fieldTwo", false, false, true));
		entity.formatForUpdate(fieldCollection);
		final Collection<Field> formattedFields = entity.getFields();
		Assert.assertTrue(formattedFields.size() == 1);
		Assert.assertTrue(formattedFields.contains(new Field("fieldOne", Arrays.asList("value"))));

	}

	@Test
	public void getCustomFields_withEntityHavingCustomFields_shouldReturnCustomFields() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final List<Field> customFields = entity.getCustomFields();
		Assert.assertEquals(customFields.size(), 1);
		Assert.assertEquals(customFields.get(0), new Field("user-01", Arrays.asList("one")));
	}

	@Test
	public void getFieldValues_withFieldThatExists_shouldReturnValues() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertTrue(entity.getFieldValues("fieldTwo").size() == 1);
		Assert.assertEquals(entity.getFieldValues("fieldTwo").get(0), "valueTwo");
	}

	@Test
	public void getFieldValues_withNonExistentFieldName_shouldReturnEmptyList() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertTrue(entity.getFieldValues("notAField").isEmpty());
	}

	@Test
	public void hasFieldValue_withFieldThatDoesNotExist_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertFalse(entity.hasFieldValue("notPresent"));
	}

	@Test
	public void hasFieldValue_withFieldThatExists_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertTrue(entity.hasFieldValue("fieldOne"));
	}

	@Test
	public void hasFieldValue_withFieldThatHasNoValue_shouldReturnFalse() {
		final List<Field> fields = createFieldListOne();
		fields.add(new Field("emptyField", new ArrayList<String>()));
		final GenericEntity entity = new GenericEntity("theType", fields);
		Assert.assertFalse(entity.hasFieldValue("emptyField"));
	}

	@Test
	public void hashCode_withEqualObjects_shouldBeEqual() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListOne());
		Assert.assertEquals(entity.hashCode(), entityTwo.hashCode());
	}

	@Test
	public void hashCode_withUnequalObjects_shouldNotBeEqual() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType2", createFieldListOne());
		Assert.assertNotEquals(entity.hashCode(), entityTwo.hashCode());
	}

	@Test
	public void hasRelatedEntities_withEntityThatHasRelatedEntity_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("test", createFieldListOne());
		entity.addRelatedEntity(new GenericEntity("related", createFieldListOne()));
		Assert.assertTrue(entity.hasRelatedEntities());
	}

	@Test
	public void hasRelatedEntities_withNoRelatedEntity_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("test", createFieldListOne());
		Assert.assertFalse(entity.hasRelatedEntities());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiate_withBlankType_shouldThrowException() {
		new GenericEntity("  ", createFieldListOne());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiate_withEmptyFields_shouldThrowException() {
		new GenericEntity("test", new ArrayList<Field>());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiate_withNullFields_shouldThrowException() {
		new GenericEntity("test", null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiate_withNullType_shouldThrowException() {
		new GenericEntity(null, createFieldListOne());
	}

	@Test
	public void toString_shouldReturnNonDefaultString() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		Assert.assertTrue(StringUtils.contains(entity.toString(), "type"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateRequiredFieldsPresent_withRequiredFieldEmpty_shouldThrowException() {
		final List<Field> fields = createFieldListOne();
		fields.add(new Field("fieldFour", new ArrayList<String>()));
		final GenericEntity entity = new GenericEntity("theType", fields);
		final EntityFieldCollection requiredFields = new EntityFieldCollection();
		requiredFields.addEntityField(createEntityField("fieldOne", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldTwo", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldThree", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldFour", "label", true, true, true));
		entity.validateRequiredFieldsPresent(requiredFields);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void validateRequiredFieldsPresent_withRequiredFieldsMissing_shouldThrowException() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final EntityFieldCollection requiredFields = new EntityFieldCollection();
		requiredFields.addEntityField(createEntityField("fieldOne", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldTwo", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldThree", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldFour", "label", true, true, true));
		entity.validateRequiredFieldsPresent(requiredFields);
	}

	@Test
	public void validateRequiredFieldsPresent_withRequiredFieldsPresent_shouldComplete() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final EntityFieldCollection requiredFields = new EntityFieldCollection();
		requiredFields.addEntityField(createEntityField("fieldOne", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldTwo", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldThree", "label", true, true, true));
		requiredFields.addEntityField(createEntityField("fieldFour", "label", true, false, true));
		entity.validateRequiredFieldsPresent(requiredFields);
		Assert.assertTrue(true);
	}

	private EntityField createEntityField(final String name, final String label, final boolean editable,
			final boolean required, final boolean system) {
		final EntityField field = new EntityField(name, label);
		field.setEditable(editable);
		field.setRequired(required);
		field.setSystem(system);
		return field;
	}

	private List<Field> createFieldListOne() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("valueOne")));
		fields.add(new Field("fieldTwo", Arrays.asList("valueTwo")));
		fields.add(new Field("fieldThree", Arrays.asList("valueOne", "valueTwo", "valueThree")));
		return fields;
	}
}
