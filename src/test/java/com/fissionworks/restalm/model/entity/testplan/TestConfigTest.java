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
import com.fissionworks.restalm.constants.field.TestConfigField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class TestConfigTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_withFullyPopulatedTestConfig_shouldCreateEntityWithAllFields() {
		final TestConfig sourceConfig = createFullTestConfig();
		final GenericEntity createdEntity = sourceConfig.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.CREATED_BY.getName()),
				Arrays.asList(sourceConfig.getCreatedBy()));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.CREATION_DATE.getName()),
				Arrays.asList(sourceConfig.getCreationDate()));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.DATA_STATE.getName()),
				Arrays.asList(String.valueOf(sourceConfig.getDataState())));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.DESCRIPTION.getName()),
				Arrays.asList(sourceConfig.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.ID.getName()),
				Arrays.asList(String.valueOf(sourceConfig.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.NAME.getName()),
				Arrays.asList(sourceConfig.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(TestConfigField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceConfig.getParentId())));
	}

	@Test
	public void createEntity_withUnpopulatedTestConfig_shouldCreateEntityWithoutInvalidValues() {
		final TestConfig sourceConfig = new TestConfig();
		final GenericEntity createdEntity = sourceConfig.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(TestConfigField.CREATION_DATE.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestConfigField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestConfigField.PARENT_ID.getName()));
	}

	@Test
	public void equals_comparingTestConfigToAnEqualObject_shouldReturnTrue() {
		final TestConfig testConfigOne = createFullTestConfig();
		Assert.assertTrue(testConfigOne.equals(createFullTestConfig()));
	}

	@Test
	public void equals_comparingTestConfigToAnotherObjectType_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		Assert.assertFalse(testConfigOne.equals(new Object()));
	}

	@Test
	public void equals_comparingTestConfigToItself_shouldReturnTrue() {
		final TestConfig testConfigOne = createFullTestConfig();
		Assert.assertTrue(testConfigOne.equals(testConfigOne));
	}

	@Test
	public void equals_comparingTestConfigToNull_shouldReturnFalse() {
		final TestConfig testConfig = createFullTestConfig();
		Assert.assertFalse(testConfig.equals(null));
	}

	@Test
	public void equals_comparingTestConfigToTestConfigWithDifferentId_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setId(1234);
		Assert.assertFalse(testConfigOne.equals(testConfigTwo));
	}

	@Test
	public void equals_comparingTestConfigToTestConfigWithDifferentName_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setName("different");
		Assert.assertFalse(testConfigOne.equals(testConfigTwo));
	}

	@Test
	public void equals_comparingTestConfigToTestConfigWithDifferentParentId_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setParentId(123456);
		Assert.assertFalse(testConfigOne.equals(testConfigTwo));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getCustomFieldValue_withNonExistentFieldName_shouldThrowException() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final TestConfig sourceTestConfig = new TestConfig();
		sourceTestConfig.populateFields(entity);
		sourceTestConfig.getCustomFieldValue("nonExistent");
	}

	@Test
	public void getCustomFieldValue_withValidFieldName_shouldReturnValue() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final TestConfig sourceTestConfig = new TestConfig();
		sourceTestConfig.populateFields(entity);
		Assert.assertEquals(sourceTestConfig.getCustomFieldValue("user-01"), Arrays.asList("one"));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCorrectType() {
		Assert.assertEquals(new TestConfig().getEntityCollectionType(), "test-configs");
	}

	@Test
	public void getFullDescription_withDescriptionHavingHtml_shouldReturnFullDescription() {
		final TestConfig config = new TestConfig();
		config.setDescription("<b>html description</b>");
		Assert.assertEquals(config.getFullDescription(), "<b>html description</b>");
	}

	@Test
	public void getParentAlmTest_withNoParentTestSet_shouldReturnDefaultParent() {
		Assert.assertEquals(new TestConfig().getParentAlmTest(), new AlmTest());
	}

	@Test
	public void getParentAlmTest_withParentTestSet_shouldReturnParent() {
		final TestConfig testConfig = new TestConfig();
		final AlmTest test = new AlmTest();
		test.setName("the Name");
		testConfig.setParentAlmTest(test);
		Assert.assertEquals(testConfig.getParentAlmTest(), test);
	}

	@Test
	public void getType_shouldReturnCorrectType() {
		Assert.assertEquals(new TestConfig().getEntityType(), "test-config");
	}

	@Test
	public void hashCode_forEqualTestConfigs_shouldBeEqual() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		Assert.assertEquals(testConfigOne.hashCode(), testConfigTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTestConfigs_shouldNotBeEqual() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigOne.setParentId(1234);
		Assert.assertNotEquals(testConfigOne.hashCode(), testConfigTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingTestConfigsWithDifferentCustomFields_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));
		fieldsTwo.add(new Field("user-03", Arrays.asList("three")));

		final GenericEntity entity = new GenericEntity("test-config", fields);
		final GenericEntity entityTwo = new GenericEntity("test-config", fieldsTwo);
		testConfigOne.populateFields(entity);
		testConfigTwo.populateFields(entityTwo);

		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigsWithDifferentNumberOfCustomFields_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));

		final GenericEntity entity = new GenericEntity("test-config", fields);
		final GenericEntity entityTwo = new GenericEntity("test-config", fieldsTwo);
		testConfigOne.populateFields(entity);
		testConfigTwo.populateFields(entityTwo);

		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToExactMatch_shouldReturnTrue() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigOne.setParentAlmTest(new AlmTest());
		testConfigTwo.setParentAlmTest(new AlmTest());
		Assert.assertTrue(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToItself_shouldReturnTrue() {
		final TestConfig testConfigOne = createFullTestConfig();
		Assert.assertTrue(testConfigOne.isExactMatch(testConfigOne));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigThatDoesNotSatisyEquals_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setParentId(123456);
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentCreatedBy_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setCreatedBy("different");
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentCreationDate_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setCreationDate("2013-01-02");
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentDataState_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setDataState(5);
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentDescription_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setDescription("different");
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentParentAlmTest_shouldReturnFalse() {
		final TestConfig testConfigOne = new TestConfig();
		final TestConfig testConfigTwo = new TestConfig();
		final AlmTest parentTest = new AlmTest();
		parentTest.setName("different name");
		testConfigOne.setParentAlmTest(new AlmTest());
		testConfigTwo.setParentAlmTest(parentTest);
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigToTestConfigWithDifferentTestName_shouldReturnFalse() {
		final TestConfig testConfigOne = createFullTestConfig();
		final TestConfig testConfigTwo = createFullTestConfig();
		testConfigTwo.setTestName("different");
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void isExactMatch_comparingTestConfigWithNullParentAlmTestToConfigWithParentAlmTest_shouldReturnFalse() {
		final TestConfig testConfigOne = new TestConfig();
		final TestConfig testConfigTwo = new TestConfig();
		final AlmTest parentTest = new AlmTest();
		parentTest.setName("different name");
		testConfigTwo.setParentAlmTest(parentTest);
		Assert.assertFalse(testConfigOne.isExactMatch(testConfigTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldCreateTestConfigWithDefaultValues() {
		final TestConfig testConfig = new TestConfig();
		testConfig.populateFields(
				new GenericEntity("test-config", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(testConfig.getCreationDate(), "292269055-12-02");
		Assert.assertEquals(testConfig.getCreatedBy(), "");
		Assert.assertEquals(testConfig.getDataState(), 0);
		Assert.assertEquals(testConfig.getDescription(), "");
		Assert.assertEquals(testConfig.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(testConfig.getName(), "");
		Assert.assertEquals(testConfig.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(testConfig.getTestName(), "");
	}

	@Test
	public void populateFields_withEntityHavingAlmTestWithNoPreviousTestSet_shouldSetParentTest() {
		final GenericEntity sourceEntity = getFullTestConfigEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList("theName")));
		folderFields.add(new Field(AlmTestField.ID.getName(), Arrays.asList("1337")));
		folderFields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		folderFields.add(new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList("1776")));
		final AlmTest expectedTest = new AlmTest();
		expectedTest.populateFields(new GenericEntity("test", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("test-folder", folderFields));

		final TestConfig actualTestConfig = new TestConfig();
		actualTestConfig.populateFields(sourceEntity);

		Assert.assertEquals(actualTestConfig.getParentAlmTest(), expectedTest);
	}

	@Test
	public void populateFields_withEntityHavingAlmTestWithPreviousTestSet_shouldSetParentTest() {
		final GenericEntity sourceEntity = getFullTestConfigEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList("theName")));
		folderFields.add(new Field(AlmTestField.ID.getName(), Arrays.asList("1337")));
		folderFields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		folderFields.add(new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList("1776")));
		final AlmTest expectedTest = new AlmTest();
		expectedTest.populateFields(new GenericEntity("test", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("test-folder", folderFields));

		final TestConfig actualTestConfig = new TestConfig();
		actualTestConfig.setParentAlmTest(new AlmTest());
		actualTestConfig.populateFields(sourceEntity);

		Assert.assertEquals(actualTestConfig.getParentAlmTest(), expectedTest);
	}

	@Test
	public void populateFields_withFullTestConfigEntity_shouldPopulateAllValues() {
		final TestConfig testConfig = new TestConfig();
		testConfig.populateFields(getFullTestConfigEntity());
		Assert.assertEquals(testConfig.getCreationDate(), "1776-07-04");
		Assert.assertEquals(testConfig.getCreatedBy(), "theDesigner");
		Assert.assertEquals(testConfig.getDataState(), 1);
		Assert.assertEquals(testConfig.getDescription(), "theDescription");
		Assert.assertEquals(testConfig.getId(), 1337);
		Assert.assertEquals(testConfig.getName(), "theName");
		Assert.assertEquals(testConfig.getParentId(), 90210);
		Assert.assertEquals(testConfig.getTestName(), "theTestName");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setCreationDate_withIncorrectDateFormat_shouldThrowException() {
		final TestConfig testConfgOne = createFullTestConfig();
		testConfgOne.setCreationDate("June 23, 1967");
	}

	@Test
	public void setParentAlmTest_withParentAlreadySet_shouldSetNewTest() {
		final TestConfig testConfig = createFullTestConfig();
		testConfig.setParentAlmTest(new AlmTest());
		final AlmTest test = new AlmTest();
		test.setName("theName");
		testConfig.setParentAlmTest(test);
		Assert.assertEquals(testConfig.getParentAlmTest(), test);

	}

	@Test
	public void toString_withNoParentTestSet_shouldReturnNonDefaultStringWithNoTestData() {
		final TestConfig testConfig = createFullTestConfig();
		Assert.assertTrue(StringUtils.contains(testConfig.toString(), "TestConfig"));
		Assert.assertTrue(StringUtils.contains(testConfig.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestSet_shouldReturnNonDefaultStringWithTestData() {
		final TestConfig testConfig = createFullTestConfig();
		testConfig.setParentAlmTest(new AlmTest());
		Assert.assertTrue(StringUtils.contains(testConfig.toString(), "TestConfig"));
		Assert.assertTrue(StringUtils.contains(testConfig.toString(), "<AlmTest>"));
	}

	private TestConfig createFullTestConfig() {
		cal.setTime(new Date());
		final TestConfig config = new TestConfig();
		config.setCreatedBy("createdby");
		config.setCreationDate(DATE_FORMATTER.get().format(cal.getTime()));
		config.setDataState(1);
		config.setDescription("description");
		config.setId(1337);
		config.setName("name");
		config.setParentId(90210);
		config.setTestName("testname");
		return config;
	}

	private GenericEntity getFullTestConfigEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("creation-time", Arrays.asList("1776-07-04")));
		fields.add(new Field("description", Arrays.asList("theDescription")));
		fields.add(new Field("owner", Arrays.asList("theDesigner")));
		fields.add(new Field("data-state", Arrays.asList("1")));
		fields.add(new Field("id", Arrays.asList("1337")));
		fields.add(new Field("name", Arrays.asList("theName")));
		fields.add(new Field("parent-id", Arrays.asList("90210")));
		fields.add(new Field("test-name", Arrays.asList("theTestName")));
		return new GenericEntity("test", fields);
	}
}
