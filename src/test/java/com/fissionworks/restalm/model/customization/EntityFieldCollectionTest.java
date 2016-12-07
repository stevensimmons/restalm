package com.fissionworks.restalm.model.customization;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityFieldCollectionTest {

	@Test(expectedExceptions = NullPointerException.class)
	public void addEntityField_withNullField_shouldThrowException() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(null);
	}

	@Test
	public void addEntityField_withValidEntityFields_shouldAddEntityField() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		final EntityField fieldOne = createEntityField("nameOne", "labelOne", true, true, true);
		final EntityField fieldTwo = createEntityField("nameTwo", "labelTwo", false, false, false);

		fields.addEntityField(fieldOne);
		fields.addEntityField(fieldTwo);

		final List<EntityField> actualFields = new ArrayList<>(fields.getFields());
		Assert.assertTrue(actualFields.contains(fieldOne));
		Assert.assertTrue(actualFields.contains(fieldTwo));
	}

	@Test
	public void equals_comparingEntityCollectionToAnEqualCollection_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		final EntityFieldCollection fieldsTwo = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		fieldsTwo.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fieldsTwo.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fieldsTwo.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertTrue(fields.equals(fieldsTwo));
	}

	@Test
	public void equals_comparingEntityCollectionToAnotherObjectType_shouldReturnFalse() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertFalse(fields.equals(new Object()));
	}

	@Test
	public void equals_comparingEntityCollectionToAnUnEqualCollection_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		final EntityFieldCollection fieldsTwo = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		fieldsTwo.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fieldsTwo.addEntityField(createEntityField("nameToo", "labelToo", true, false, false));
		fieldsTwo.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertFalse(fields.equals(fieldsTwo));
	}

	@Test
	public void equals_comparingEntityCollectionToItself_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertTrue(fields.equals(fields));
	}

	@Test
	public void equals_comparingEntityCollectionToNull_shouldReturnFalse() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertFalse(fields.equals(null));
	}

	@Test
	public void getEntityField_withExistingFieldName_shouldReturnField() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));
		final EntityField field = fields.getEntityField("nameOne");

		Assert.assertNotNull(field);
		Assert.assertEquals(field.getName(), "nameOne");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getEntityField_withNonExistentFieldName_shouldThrowException() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		fields.getEntityField("iDontExist");
	}

	@Test
	public void hashCode_withEqualCollections_shouldBeEqual() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		final EntityFieldCollection fieldsTwo = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		fieldsTwo.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fieldsTwo.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fieldsTwo.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertEquals(fields.hashCode(), fieldsTwo.hashCode());
	}

	@Test
	public void hashCode_withUnEqualCollections_shouldNotBeEqual() {
		final EntityFieldCollection fields = new EntityFieldCollection();
		final EntityFieldCollection fieldsTwo = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		fieldsTwo.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fieldsTwo.addEntityField(createEntityField("nameToo", "labelToo", true, false, false));
		fieldsTwo.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertNotEquals(fields.hashCode(), fieldsTwo.hashCode());
	}

	@Test
	public void isEditableField_withFieldThatIsEditable_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));
		Assert.assertTrue(fields.isEditableField("nameOne"));
	}

	@Test
	public void isEditableField_withFieldThatIsNotEditable_shouldReturnFalse() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));
		Assert.assertFalse(fields.isEditableField("nameTwo"));
	}

	@Test
	public void isValidAddField_withFieldNameThatIsEditable_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertTrue(fields.isValidAddField("nameOne"));
	}

	@Test
	public void isValidAddField_withFieldNameThatIsNotEditableOrRequired_shouldReturnFalse() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertFalse(fields.isValidAddField("nameTwo"));
	}

	@Test
	public void isValidAddField_withFieldNameThatIsRequired_shouldReturnTrue() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));

		Assert.assertTrue(fields.isValidAddField("nameThree"));
	}

	@Test
	public void setEditable_shouldChangeEditableValue() {
		final EntityFieldCollection fields = new EntityFieldCollection();

		fields.addEntityField(createEntityField("nameOne", "labelOne", true, true, true));
		fields.addEntityField(createEntityField("nameTwo", "labelTwo", false, false, false));
		fields.addEntityField(createEntityField("nameThree", "labelThree", false, true, false));
		Assert.assertFalse(fields.isEditableField("nameTwo"));
		fields.setEditable("nameTwo", true);
		Assert.assertTrue(fields.isEditableField("nameTwo"));
	}

	@Test
	public void toString_shouldReturnNonDefaultString() {
		Assert.assertTrue(StringUtils.contains(new EntityFieldCollection().toString(), "fields"));
	}

	private EntityField createEntityField(final String name, final String label, final boolean editable,
			final boolean required, final boolean system) {
		final EntityField field = new EntityField(name, label);
		field.setEditable(editable);
		field.setRequired(required);
		field.setSystem(system);
		return field;
	}
}
