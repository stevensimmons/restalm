package com.fissionworks.restalm.model.entity.testplan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class AlmTestTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final AlmTest sourceTest = createFullAlmTest();
		final GenericEntity createdEntity = sourceTest.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.CREATION_DATE.getName()),
				Arrays.asList(sourceTest.getCreationDate()));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.DESCRIPTION.getName()),
				Arrays.asList(sourceTest.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.DESIGNER.getName()),
				Arrays.asList(sourceTest.getDesigner()));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.ID.getName()),
				Arrays.asList(String.valueOf(sourceTest.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.NAME.getName()),
				Arrays.asList(sourceTest.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceTest.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.STATUS.getName()),
				Arrays.asList(sourceTest.getStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(AlmTestField.TYPE.getName()),
				Arrays.asList(sourceTest.getType()));
	}

	@Test
	public void createEntity_withAlmTestHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final AlmTest sourceTest = new AlmTest();
		final GenericEntity createdEntity = sourceTest.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(AlmTestField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(AlmTestField.PARENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(AlmTestField.CREATION_DATE.getName()));
	}

	@Test
	public void equals_comparingAlmTestToAlmTestWithDifferentId_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setId(1234);
		Assert.assertFalse(testOne.equals(testTwo));
	}

	@Test
	public void equals_comparingAlmTestToAlmTestWithDifferentName_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setName("different");
		Assert.assertFalse(testOne.equals(testTwo));
	}

	@Test
	public void equals_comparingAlmTestToAlmTestWithDifferentParentId_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setParentId(123456);
		Assert.assertFalse(testOne.equals(testTwo));
	}

	@Test
	public void equals_comparingAlmTestToAnEqualObject_shouldReturnTrue() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertTrue(testOne.equals(createFullAlmTest()));
	}

	@Test
	public void equals_comparingAlmTestToAnotherObjectType_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertFalse(testOne.equals(new Object()));
	}

	@Test
	public void equals_comparingAlmTestToItself_shouldReturnTrue() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertTrue(testOne.equals(testOne));
	}

	@Test
	public void equals_comparingAlmTestToNull_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertFalse(testOne.equals(null));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getCustomFieldValue_withNonExistentFieldName_shouldThrowException() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final AlmTest sourceTest = new AlmTest();
		sourceTest.populateFields(entity);
		sourceTest.getCustomFieldValue("nonExistent");
	}

	@Test
	public void getCustomFieldValue_withValidFieldName_shouldReturnValue() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final AlmTest sourceTest = new AlmTest();
		sourceTest.populateFields(entity);
		Assert.assertEquals(sourceTest.getCustomFieldValue("user-01"), Arrays.asList("one"));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new AlmTest().getEntityCollectionType(), "tests");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new AlmTest().getEntityType(), "test");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final AlmTest test = new AlmTest();
		test.setDescription("<b>bold description</b>");
		Assert.assertEquals(test.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentTestFolder_withNoTestFolderSet_shouldReturnDefaultTestFolder() {
		Assert.assertEquals(new AlmTest().getParentTestFolder(), new TestFolder());
	}

	@Test
	public void getParentTestFolder_withTestFolderSet_shouldReturnTestFolder() {
		final AlmTest test = new AlmTest();
		final TestFolder testFolder = new TestFolder();
		testFolder.setId(1337);
		testFolder.setName("theName");
		testFolder.setDescription("theDescription");
		testFolder.setParentId(1776);
		test.setParentTestFolder(testFolder);
		Assert.assertEquals(test.getParentTestFolder(), testFolder);
	}

	@Test
	public void hashCode_forEqualTests_shouldBeEqual() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		Assert.assertEquals(testOne.hashCode(), testTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTests_shouldNotBeEqual() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testOne.setParentId(1234);
		Assert.assertNotEquals(testOne.hashCode(), testTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetCreationDateToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final AlmTest test = new AlmTest();
		Assert.assertEquals(test.getCreationDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingAlmTestToExactlyMatchingTestWithNoParentTestFolder_shouldReturnTrue() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		Assert.assertTrue(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToExactlyMatchingTestWithParentTestFolder_shouldReturnTrue() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testOne.setParentTestFolder(new TestFolder());
		testTwo.setParentTestFolder(new TestFolder());
		Assert.assertTrue(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToItself_shouldReturnTrue() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertTrue(testOne.isExactMatch(testOne));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestThatDoesNotSatisyEquals_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setParentId(123456);
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentCreationDate_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setCreationDate("2013-01-02");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentDescription_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setDescription("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentDesigner_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setDesigner("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentParentTestFolder_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		final TestFolder testFolder = new TestFolder();
		testFolder.setName("theName");
		testOne.setParentTestFolder(new TestFolder());
		testTwo.setParentTestFolder(testFolder);
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentStatus_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setStatus("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestToTestWithDifferentSubType_shouldReturnFalse() {
		final AlmTest testOne = createFullAlmTest();
		final AlmTest testTwo = createFullAlmTest();
		testTwo.setType("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestWithNullParentTestFolderToTestWithParentTestFolder_shouldReturnFalse() {
		final AlmTest testOne = new AlmTest();
		final AlmTest testTwo = new AlmTest();
		testTwo.setParentTestFolder(new TestFolder());
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestWithTestHavingDifferentCustomFields_shouldReturnFalse() {
		final AlmTest testOne = new AlmTest();
		final AlmTest testTwo = new AlmTest();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));
		fieldsTwo.add(new Field("user-03", Arrays.asList("three")));

		final GenericEntity entity = new GenericEntity("test", fields);
		final GenericEntity entityTwo = new GenericEntity("test", fieldsTwo);
		testOne.populateFields(entity);
		testTwo.populateFields(entityTwo);

		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingAlmTestWithTestHavingDifferentNumberOfCustomFields_shouldReturnFalse() {
		final AlmTest testOne = new AlmTest();
		final AlmTest testTwo = new AlmTest();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));

		final GenericEntity entity = new GenericEntity("test", fields);
		final GenericEntity entityTwo = new GenericEntity("test", fieldsTwo);
		testOne.populateFields(entity);
		testTwo.populateFields(entityTwo);

		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final AlmTest test = new AlmTest();
		test.populateFields(new GenericEntity("test", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(test.getCreationDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(test.getDescription(), "");
		Assert.assertEquals(test.getDesigner(), "");
		Assert.assertEquals(test.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(test.getName(), "");
		Assert.assertEquals(test.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(test.getStatus(), "");
		Assert.assertEquals(test.getType(), "");
	}

	@Test
	public void populateFields_withEntityHavingAllTestFieldsPopulated_shouldSetAllTestFields() {
		final AlmTest test = new AlmTest();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		test.populateFields(sourceEntity);
		Assert.assertEquals(test.getCreationDate(),
				sourceEntity.getFieldValues(AlmTestField.CREATION_DATE.getName()).get(0));
		Assert.assertEquals(test.getDescription(),
				sourceEntity.getFieldValues(AlmTestField.DESCRIPTION.getName()).get(0));
		Assert.assertEquals(test.getDesigner(), sourceEntity.getFieldValues(AlmTestField.DESIGNER.getName()).get(0));
		Assert.assertTrue(
				test.getId() == Integer.valueOf(sourceEntity.getFieldValues(AlmTestField.ID.getName()).get(0)));
		Assert.assertEquals(test.getName(), sourceEntity.getFieldValues(AlmTestField.NAME.getName()).get(0));
		Assert.assertTrue(test.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(AlmTestField.PARENT_ID.getName()).get(0)));
		Assert.assertEquals(test.getStatus(), sourceEntity.getFieldValues(AlmTestField.STATUS.getName()).get(0));
		Assert.assertEquals(test.getType(), sourceEntity.getFieldValues(AlmTestField.TYPE.getName()).get(0));
	}

	@Test
	public void populateFields_withEntityHavingParentTestFolderWithNoPreviousFolderSet_shouldSetParentTestFolder() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(TestFolderField.NAME.getName(), Arrays.asList("theName")));
		folderFields.add(new Field(TestFolderField.ID.getName(), Arrays.asList("1337")));
		folderFields.add(new Field(TestFolderField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		folderFields.add(new Field(TestFolderField.PARENT_ID.getName(), Arrays.asList("1776")));
		final TestFolder expectedFolder = new TestFolder();
		expectedFolder.populateFields(new GenericEntity("test-folder", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("test-folder", folderFields));

		final AlmTest actualTest = new AlmTest();
		actualTest.populateFields(sourceEntity);

		Assert.assertEquals(actualTest.getParentTestFolder(), expectedFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentTestFolderWithPreviousFolderSet_shouldSetParentTestFolder() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(TestFolderField.NAME.getName(), Arrays.asList("theName")));
		final TestFolder expectedFolder = new TestFolder();
		expectedFolder.populateFields(new GenericEntity("test-folder", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("test-folder", folderFields));

		final AlmTest actualTest = new AlmTest();
		actualTest.setParentTestFolder(new TestFolder());
		actualTest.populateFields(sourceEntity);

		Assert.assertEquals(actualTest.getParentTestFolder(), expectedFolder);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setCreationDate_withIncorrectDateFormat_shouldThrowException() {
		final AlmTest testOne = createFullAlmTest();
		testOne.setCreationDate("June 23, 1967");
	}

	@Test
	public void setParentTestFolder_withFolderAlreadySet_shouldSetNewTestFolder() {
		final AlmTest testOne = createFullAlmTest();
		testOne.setParentTestFolder(new TestFolder());
		final TestFolder testFolder = new TestFolder();
		testFolder.setName("theName");
		testOne.setParentTestFolder(testFolder);
		Assert.assertEquals(testOne.getParentTestFolder(), testFolder);

	}

	@Test
	public void toString_withNoParentTestFolder_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final AlmTest testOne = createFullAlmTest();
		Assert.assertTrue(StringUtils.contains(testOne.toString(), "AlmTest"));
		Assert.assertTrue(StringUtils.contains(testOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestFolder_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final AlmTest testOne = createFullAlmTest();
		testOne.setParentTestFolder(new TestFolder());
		Assert.assertTrue(StringUtils.contains(testOne.toString(), "AlmTest"));
		Assert.assertTrue(StringUtils.contains(testOne.toString(), "<ReleaseFolder>"));
	}

	private AlmTest createFullAlmTest() {
		cal.setTime(new Date());
		final AlmTest test = new AlmTest();
		test.setCreationDate(DATE_FORMATTER.get().format(cal.getTime()));
		test.setDescription("description");
		test.setDesigner("designer");
		test.setId(1337);
		test.setName("name");
		test.setParentId(90210);
		test.setStatus("status");
		test.setType("type");
		return test;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(AlmTestField.CREATION_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(AlmTestField.DESIGNER.getName(), Arrays.asList("designer")));
		fields.add(new Field(AlmTestField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList("90210")));
		fields.add(new Field(AlmTestField.STATUS.getName(), Arrays.asList("status")));
		fields.add(new Field(AlmTestField.TYPE.getName(), Arrays.asList("type")));
		return new GenericEntity("test", fields);
	}
}
