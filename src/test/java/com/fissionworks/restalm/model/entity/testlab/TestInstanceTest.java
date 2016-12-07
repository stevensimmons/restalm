package com.fissionworks.restalm.model.entity.testlab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.TestInstanceField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.defects.Defect;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;

public class TestInstanceTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final TestInstance sourceTestInstance = createFullTestInstance();
		final GenericEntity createdEntity = sourceTestInstance.createEntity();

		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.ID.getName()),
				Arrays.asList(String.valueOf(sourceTestInstance.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.LAST_MODIFIED.getName()),
				Arrays.asList(sourceTestInstance.getLastModified()));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.PLANNED_HOST.getName()),
				Arrays.asList(sourceTestInstance.getPlannedHost()));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.RESPONSIBLE_TESTER.getName()),
				Arrays.asList(sourceTestInstance.getResponsibleTester()));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.STATUS.getName()),
				Arrays.asList(sourceTestInstance.getStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.TEST_CONFIG_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestInstance.getTestConfigId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.TEST_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestInstance.getTestId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.TEST_INSTANCE_NUMBER.getName()),
				Arrays.asList(String.valueOf(sourceTestInstance.getTestInstanceNumber())));
		Assert.assertEquals(createdEntity.getFieldValues(TestInstanceField.TEST_SET_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestInstance.getTestSetId())));

	}

	@Test
	public void createEntity_withTestSetHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final TestInstance sourceTestInstance = new TestInstance();
		final GenericEntity createdEntity = sourceTestInstance.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(TestInstanceField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestInstanceField.TEST_CONFIG_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestInstanceField.TEST_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestInstanceField.TEST_INSTANCE_NUMBER.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestInstanceField.TEST_SET_ID.getName()));
	}

	@Test
	public void equals_comparingTestInstanceToAnEqualObject_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertTrue(testInstanceOne.equals(createFullTestInstance()));
	}

	@Test
	public void equals_comparingTestInstanceToAnotherObjectType_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertFalse(testInstanceOne.equals(new Object()));
	}

	@Test
	public void equals_comparingTestInstanceToItself_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertTrue(testInstanceOne.equals(testInstanceOne));
	}

	@Test
	public void equals_comparingTestInstanceToNull_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertFalse(testInstanceOne.equals(null));
	}

	@Test
	public void equals_comparingTestInstanceToTestInstanceWithDifferentId_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setId(1234);
		Assert.assertFalse(testInstanceOne.equals(testInstanceTwo));
	}

	@Test
	public void equals_comparingTestInstanceToTestInstanceWithDifferentTestConfigId_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setTestConfigId(9999);
		Assert.assertFalse(testInstanceOne.equals(testInstanceTwo));
	}

	@Test
	public void equals_comparingTestInstanceToTestInstanceWithDifferentTestSetId_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setTestSetId(9999);
		Assert.assertFalse(testInstanceOne.equals(testInstanceTwo));
	}

	@Test
	public void equals_comparingTestInstanceToTestWithDifferentParentId_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstaceTwo = createFullTestInstance();
		testInstaceTwo.setTestId(123456);
		Assert.assertFalse(testInstanceOne.equals(testInstaceTwo));
	}

	@Test
	public void getAssociatedTest_withAssociatedTestSet_shouldReturnAssociatedTest() {
		final TestInstance instance = new TestInstance();
		final AlmTest associatedTest = new AlmTest();
		associatedTest.setName("parent name");
		instance.setAssociatedTest(associatedTest);
		Assert.assertEquals(instance.getAssociatedTest(), associatedTest);
	}

	@Test
	public void getAssociatedTest_withNoAssociatedTestSet_shouldReturnDefaultTest() {
		Assert.assertEquals(new TestInstance().getAssociatedTest(), new AlmTest());
	}

	@Test
	public void getAssociatedTestConfig_withAssociatedTestConfigSet_shouldReturnAssociatedTestConfig() {
		final TestInstance instance = new TestInstance();
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setName("parent name");
		instance.setAssociatedTestConfig(associatedTestConfig);
		Assert.assertEquals(instance.getAssociatedTestConfig(), associatedTestConfig);
	}

	@Test
	public void getAssociatedTestConfig_withNoAssociatedTestConfigSet_shouldReturnDefaultTest() {
		Assert.assertEquals(new TestInstance().getAssociatedTestConfig(), new TestConfig());
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new TestInstance().getEntityCollectionType(), "test-instances");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new TestInstance().getEntityType(), "test-instance");
	}

	@Test
	public void getParentTestSet_withNoParentTestSetSet_shouldReturnDefaultTestSet() {
		Assert.assertEquals(new TestInstance().getParentTestSet(), new TestSet());
	}

	@Test
	public void getParentTestSet_withparentTestSetSet_shouldReturnParentTestSet() {
		final TestInstance instance = new TestInstance();
		final TestSet parentTestSet = new TestSet();
		parentTestSet.setName("parent name");
		instance.setParentTestSet(parentTestSet);
		Assert.assertEquals(instance.getParentTestSet(), parentTestSet);
	}

	@Test
	public void hashCode_forEqualTestInstances_shouldBeEqual() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		Assert.assertEquals(testInstanceOne.hashCode(), testInstanceTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTestInstances_shouldNotBeEqual() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceOne.setId(1234);
		Assert.assertNotEquals(testInstanceOne.hashCode(), testInstanceTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetLastModifiedToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final TestInstance testInstance = new TestInstance();
		Assert.assertEquals(testInstance.getLastModified(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToExactlyMatchingTestInstanceWithNoAssociatedEntities_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		Assert.assertTrue(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToExactlyMatchingTestWithAssociatedTest_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceOne.setAssociatedTest(new AlmTest());
		testInstanceTwo.setAssociatedTest(new AlmTest());
		Assert.assertTrue(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToExactlyMatchingTestWithAssociatedTestConfig_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceOne.setAssociatedTestConfig(new TestConfig());
		testInstanceTwo.setAssociatedTestConfig(new TestConfig());
		Assert.assertTrue(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToExactlyMatchingTestWithParentTestSet_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceOne.setParentTestSet(new TestSet());
		testInstanceTwo.setParentTestSet(new TestSet());
		Assert.assertTrue(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToItself_shouldReturnTrue() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertTrue(testInstanceOne.isExactMatch(testInstanceOne));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceThatDoesNotSatisyEquals_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setTestId(123456);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentAssociatedTest_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		final AlmTest test = new AlmTest();
		test.setName("theName");
		testInstanceOne.setAssociatedTest(new AlmTest());
		testInstanceTwo.setAssociatedTest(test);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentAssociatedTestConfig_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		final TestConfig testConfig = new TestConfig();
		testConfig.setName("theName");
		testInstanceOne.setAssociatedTestConfig(new TestConfig());
		testInstanceTwo.setAssociatedTestConfig(testConfig);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentLastModifiedDate_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setLastModified("1776-07-06 11:12:13");
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentParentTestSet_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		final TestSet testSet = new TestSet();
		testSet.setName("theName");
		testInstanceOne.setParentTestSet(new TestSet());
		testInstanceTwo.setParentTestSet(testSet);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentPlannedHost_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setPlannedHost("win7.64.ie");
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentResponsibleTester_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setResponsibleTester("differentTester");
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentStatus_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setStatus("different");
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentSubType_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setSubtype("different");
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentTestInstanceNumber_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setTestInstanceNumber(123456789);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceToTestInstanceWithDifferentTestOrder_shouldReturnFalse() {
		final TestInstance testInstanceOne = createFullTestInstance();
		final TestInstance testInstanceTwo = createFullTestInstance();
		testInstanceTwo.setTestOrder(9999999);
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceWithNullAssociatedTestConfigToTestInstanceWithAssociatedTestConfig_shouldReturnFalse() {
		final TestInstance testInstanceOne = new TestInstance();
		final TestInstance testInstanceTwo = new TestInstance();
		testInstanceTwo.setAssociatedTestConfig(new TestConfig());
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceWithNullAssociatedTestToTestWithAssociatedTest_shouldReturnFalse() {
		final TestInstance testInstanceOne = new TestInstance();
		final TestInstance testInstanceTwo = new TestInstance();
		testInstanceTwo.setAssociatedTest(new AlmTest());
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void isExactMatch_comparingTestInstanceWithNullParentTestSetToTestInstanceWithParentTestSet_shouldReturnFalse() {
		final TestInstance testInstanceOne = new TestInstance();
		final TestInstance testInstanceTwo = new TestInstance();
		testInstanceTwo.setParentTestSet(new TestSet());
		Assert.assertFalse(testInstanceOne.isExactMatch(testInstanceTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final TestInstance testInstance = new TestInstance();
		cal.setTimeInMillis(Long.MIN_VALUE);
		testInstance.populateFields(
				new GenericEntity("test-set", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(testInstance.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(testInstance.getLastModified(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(testInstance.getPlannedHost(), "");
		Assert.assertEquals(testInstance.getResponsibleTester(), "");
		Assert.assertEquals(testInstance.getStatus(), "");
		Assert.assertEquals(testInstance.getSubtype(), "");
		Assert.assertEquals(testInstance.getTestConfigId(), Integer.MIN_VALUE);
		Assert.assertEquals(testInstance.getTestId(), Integer.MIN_VALUE);
		Assert.assertEquals(testInstance.getTestOrder(), Integer.MIN_VALUE);
		Assert.assertEquals(testInstance.getTestInstanceNumber(), Integer.MIN_VALUE);
		Assert.assertEquals(testInstance.getTestSetId(), Integer.MIN_VALUE);

	}

	@Test
	public void populateFields_withEntityHavingAllTestInstanceFieldsPopulated_shouldSetAllTestInstanceFields() {
		final TestInstance testInstance = new TestInstance();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		testInstance.populateFields(sourceEntity);
		Assert.assertTrue(testInstance.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.ID.getName()).get(0)));
		Assert.assertEquals(testInstance.getLastModified(),
				sourceEntity.getFieldValues(TestInstanceField.LAST_MODIFIED.getName()).get(0));
		Assert.assertEquals(testInstance.getPlannedHost(),
				sourceEntity.getFieldValues(TestInstanceField.PLANNED_HOST.getName()).get(0));
		Assert.assertEquals(testInstance.getResponsibleTester(),
				sourceEntity.getFieldValues(TestInstanceField.RESPONSIBLE_TESTER.getName()).get(0));
		Assert.assertEquals(testInstance.getStatus(),
				sourceEntity.getFieldValues(TestInstanceField.STATUS.getName()).get(0));
		Assert.assertEquals(testInstance.getSubtype(),
				sourceEntity.getFieldValues(TestInstanceField.SUBTYPE.getName()).get(0));
		Assert.assertTrue(testInstance.getTestConfigId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.TEST_CONFIG_ID.getName()).get(0)));
		Assert.assertTrue(testInstance.getTestId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.TEST_ID.getName()).get(0)));
		Assert.assertTrue(testInstance.getTestInstanceNumber() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.TEST_INSTANCE_NUMBER.getName()).get(0)));
		Assert.assertTrue(testInstance.getTestOrder() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.TEST_ORDER.getName()).get(0)));
		Assert.assertTrue(testInstance.getTestSetId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestInstanceField.TEST_SET_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingAssociatedTestConfigSet_shouldSetNewAssociatedTest() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setAssociatedTestConfig(new TestConfig());
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestConfig.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getAssociatedTestConfig(), associatedTestConfig);
	}

	@Test
	public void populateFields_withEntityHavingAssociatedTestSet_shouldSetNewAssociatedTest() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setAssociatedTest(new AlmTest());
		final AlmTest associatedTest = new AlmTest();
		associatedTest.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTest.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getAssociatedTest(), associatedTest);
	}

	@Test
	public void populateFields_withEntityHavingNoAssociatedTestConfigSet_shouldSetNewAssociatedTest() {
		final TestInstance testInstance = new TestInstance();
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestConfig.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getAssociatedTestConfig(), associatedTestConfig);
	}

	@Test
	public void populateFields_withEntityHavingNoAssociatedTestSet_shouldSetNewAssociatedTest() {
		final TestInstance testInstance = new TestInstance();
		final AlmTest associatedTest = new AlmTest();
		associatedTest.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTest.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getAssociatedTest(), associatedTest);
	}

	@Test
	public void populateFields_withEntityHavingNoParentTestSetSet_shouldSetNewParentTestSet() {
		final TestInstance testInstance = new TestInstance();
		final TestSet parentTestSet = new TestSet();
		parentTestSet.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentTestSet.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getParentTestSet(), parentTestSet);
	}

	@Test
	public void populateFields_withEntityHavingParentTestSetSet_shouldSetNewParentTestSet() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setParentTestSet(new TestSet());
		final TestSet parentTestSet = new TestSet();
		parentTestSet.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentTestSet.createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getParentTestSet(), parentTestSet);
	}

	@Test
	public void populateFields_withGenericEntityHavingUnrelatedEntity_shouldIgnoreUnrelatedEntity() {
		final TestInstance testInstance = new TestInstance();
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(new Defect().createEntity());
		testInstance.populateFields(sourceEntity);
		Assert.assertEquals(testInstance.getAssociatedTest(), new AlmTest());
		Assert.assertEquals(testInstance.getAssociatedTestConfig(), new TestConfig());
		Assert.assertEquals(testInstance.getParentTestSet(), new TestSet());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setLastModifiedDate_withIncorrectDateFormat_shouldThrowException() {
		final TestInstance testInstanceOne = createFullTestInstance();
		testInstanceOne.setLastModified("June 23, 1967");
	}

	@Test
	public void toString_withAssociatedTest_shouldReturnNonDefaultStringWithAssociatedTestInfo() {
		final TestInstance testInstanceOne = createFullTestInstance();
		testInstanceOne.setAssociatedTest(new AlmTest());
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestInstance>"));
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<AlmTest>"));
	}

	@Test
	public void toString_withAssociatedTestConfig_shouldReturnNonDefaultStringWithAssociatedTestConfigInfo() {
		final TestInstance testInstanceOne = createFullTestInstance();
		testInstanceOne.setAssociatedTestConfig(new TestConfig());
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestInstance>"));
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestConfig>"));
	}

	@Test
	public void toString_withNoParentTestFolder_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final TestInstance testInstanceOne = createFullTestInstance();
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestInstance>"));
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "Not Set"));
		Assert.assertFalse(StringUtils.contains(testInstanceOne.toString(), "<TestSet>"));
		Assert.assertFalse(StringUtils.contains(testInstanceOne.toString(), "<AlmTest>"));
		Assert.assertFalse(StringUtils.contains(testInstanceOne.toString(), "<TestConfig>"));
	}

	@Test
	public void toString_withParentTestSet_shouldReturnNonDefaultStringWithParentTestSetInfo() {
		final TestInstance testInstanceOne = createFullTestInstance();
		testInstanceOne.setParentTestSet(new TestSet());
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestInstance>"));
		Assert.assertTrue(StringUtils.contains(testInstanceOne.toString(), "<TestSet>"));
	}

	private TestInstance createFullTestInstance() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setId(1337);
		testInstance.setLastModified("1776-07-04 11:12:13");
		testInstance.setPlannedHost("win7.64.ff");
		testInstance.setResponsibleTester("tnugent");
		testInstance.setStatus("No Run");
		testInstance.setTestConfigId(1776);
		testInstance.setTestId(2001);
		testInstance.setTestInstanceNumber(42);
		testInstance.setTestSetId(101);
		testInstance.setTestOrder(42);
		testInstance.setSubtype("hp.qc.test-instance.FUNCTIONAL");
		return testInstance;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestInstanceField.LAST_MODIFIED.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(TestInstanceField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(TestInstanceField.PLANNED_HOST.getName(), Arrays.asList("theHost")));
		fields.add(new Field(TestInstanceField.RESPONSIBLE_TESTER.getName(), Arrays.asList("sclegane")));
		fields.add(new Field(TestInstanceField.STATUS.getName(), Arrays.asList("theStatus")));
		fields.add(new Field(TestInstanceField.SUBTYPE.getName(), Arrays.asList("theSubtype")));
		fields.add(new Field(TestInstanceField.TEST_CONFIG_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(TestInstanceField.TEST_ID.getName(), Arrays.asList("2014")));
		fields.add(new Field(TestInstanceField.TEST_INSTANCE_NUMBER.getName(), Arrays.asList("42")));
		fields.add(new Field(TestInstanceField.TEST_SET_ID.getName(), Arrays.asList("1492")));
		fields.add(new Field(TestInstanceField.TEST_ORDER.getName(), Arrays.asList("7")));
		return new GenericEntity("test-instance", fields);
	}
}
