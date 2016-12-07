package com.fissionworks.restalm.model.entity.testlab;

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

import com.fissionworks.restalm.constants.field.RunField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.management.ReleaseFolder;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;

public class RunTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final Run sourceRun = createFullRun();
		final GenericEntity createdEntity = sourceRun.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(RunField.COMMENTS.getName()),
				Arrays.asList(sourceRun.getComments()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.HOST.getName()), Arrays.asList(sourceRun.getHost()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.ID.getName()),
				Arrays.asList(String.valueOf(sourceRun.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.LAST_MODIFIED.getName()),
				Arrays.asList(sourceRun.getLastModified()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.NAME.getName()), Arrays.asList(sourceRun.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.OWNER.getName()),
				Arrays.asList(sourceRun.getOwner()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.STATUS.getName()),
				Arrays.asList(sourceRun.getStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.SUBTYPE_ID.getName()),
				Arrays.asList(sourceRun.getSubtype()));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.TEST_CONFIG_ID.getName()),
				Arrays.asList(String.valueOf(sourceRun.getTestConfigId())));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.TEST_ID.getName()),
				Arrays.asList(String.valueOf(sourceRun.getTestId())));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.TEST_SET_ID.getName()),
				Arrays.asList(String.valueOf(sourceRun.getTestSetId())));
		Assert.assertEquals(createdEntity.getFieldValues(RunField.TEST_INSTANCE_ID.getName()),
				Arrays.asList(String.valueOf(sourceRun.getTestInstanceId())));
	}

	@Test
	public void createEntity_withRunHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final Run sourceRun = new Run();
		final GenericEntity createdEntity = sourceRun.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(RunField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RunField.TEST_CONFIG_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RunField.TEST_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RunField.TEST_SET_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RunField.TEST_INSTANCE_ID.getName()));
	}

	@Test
	public void getAssociatedTestConfig_withNoTestconfigSet_shouldReturnDefaultTestConfig() {
		Assert.assertEquals(new Run().getAssociatedTestConfig(), new TestConfig());
	}

	@Test
	public void getAssociatedTestConfig_withTestConfigSet_shouldReturnTestconfig() {
		final Run run = new Run();
		final TestConfig testConfig = new TestConfig();
		testConfig.setId(1337);
		testConfig.setName("theName");
		testConfig.setDescription("theDescription");
		testConfig.setParentId(1776);
		run.setAssociatedTestConfig(testConfig);
		Assert.assertEquals(run.getAssociatedTestConfig(), testConfig);
	}

	@Test
	public void getAssociatedTestInstance_withNoTestInstanceSet_shouldReturnDefaultTestInstance() {
		Assert.assertEquals(new Run().getAssociatedTestInstance(), new TestInstance());
	}

	@Test
	public void getAssociatedTestInstance_withTestInstanceSet_shouldReturnTestInstance() {
		final Run run = new Run();
		final TestInstance testinstance = new TestInstance();
		testinstance.setId(1337);
		testinstance.setPlannedHost("the host");
		run.setAssociatedTestInstance(testinstance);
		Assert.assertEquals(run.getAssociatedTestInstance(), testinstance);
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new Run().getEntityCollectionType(), "runs");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new Run().getEntityType(), "run");
	}

	@Test
	public void getFullComments_withCommentsThatHaveHtml_shouldReturnFullComments() {
		final Run run = new Run();
		run.setComments("<b>bold comments</b>");
		Assert.assertEquals(run.getFullComments(), "<b>bold comments</b>");
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetLastModifiedToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final Run run = new Run();
		Assert.assertEquals(run.getLastModified(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingRunNonMatchingAssociatedTestConfigs_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runOne.setAssociatedTestConfig(new TestConfig());
		final TestConfig secondConfig = new TestConfig();
		secondConfig.setName("non matching config");
		runTwo.setAssociatedTestConfig(secondConfig);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunNonMatchingAssociatedTestInstances_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runOne.setAssociatedTestInstance(new TestInstance());
		final TestInstance secondInstance = new TestInstance();
		secondInstance.setPlannedHost("non matching host");
		runTwo.setAssociatedTestInstance(secondInstance);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentComments_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setComments("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentHosts_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setHost("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentIds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setId(101010);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentLastModifieds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setLastModified("2013-09-08 13:14:15");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentNames_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setName("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentOwners_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setOwner("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentStatuses_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setStatus("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentSubtypes_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setSubtype("different");
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentTestConfigIds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setTestConfigId(101010);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentTestIds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setTestId(101010);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentTestInstanceIds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setTestInstanceId(101010);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunsWithDifferentTestSetIds_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setTestSetId(101010);
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunToExactlyMatchingRun_shouldReturnTrue() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runOne.setAssociatedTestConfig(new TestConfig());
		runTwo.setAssociatedTestConfig(new TestConfig());
		runOne.setAssociatedTestInstance(new TestInstance());
		runTwo.setAssociatedTestInstance(new TestInstance());
		Assert.assertTrue(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunToItself_shouldReturnTrue() {
		final Run runOne = createFullRun();
		Assert.assertTrue(runOne.isExactMatch(runOne));
	}

	@Test
	public void isExactMatch_comparingRunToNull_shouldReturnFalse() {
		final Run runOne = createFullRun();
		Assert.assertFalse(runOne.isExactMatch(null));
	}

	@Test
	public void isExactMatch_comparingRunWithNullAssociatedTestConfigToRunWithAssociatedConfigSet_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setAssociatedTestConfig(new TestConfig());
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void isExactMatch_comparingRunWithNullAssociatedTestInstanceToRunWithAssociatedInstanceSet_shouldReturnFalse() {
		final Run runOne = createFullRun();
		final Run runTwo = createFullRun();
		runTwo.setAssociatedTestInstance(new TestInstance());
		Assert.assertFalse(runOne.isExactMatch(runTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final Run run = new Run();
		cal.setTimeInMillis(Long.MIN_VALUE);
		run.populateFields(new GenericEntity("run", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(run.getComments(), "");
		Assert.assertEquals(run.getHost(), "");
		Assert.assertEquals(run.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(run.getLastModified(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(run.getName(), "");
		Assert.assertEquals(run.getOwner(), "");
		Assert.assertEquals(run.getStatus(), "");
		Assert.assertEquals(run.getSubtype(), "");
		Assert.assertEquals(run.getTestConfigId(), Integer.MIN_VALUE);
		Assert.assertEquals(run.getTestId(), Integer.MIN_VALUE);
		Assert.assertEquals(run.getTestInstanceId(), Integer.MIN_VALUE);
		Assert.assertEquals(run.getTestSetId(), Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllFieldsPopulated_shouldSetAllFields() {
		final Run run = new Run();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getComments(), sourceEntity.getFieldValues(RunField.COMMENTS.getName()).get(0));
		Assert.assertEquals(run.getHost(), sourceEntity.getFieldValues(RunField.HOST.getName()).get(0));
		Assert.assertTrue(run.getId() == Integer.valueOf(sourceEntity.getFieldValues(RunField.ID.getName()).get(0)));
		Assert.assertEquals(run.getLastModified(),
				sourceEntity.getFieldValues(RunField.LAST_MODIFIED.getName()).get(0));
		Assert.assertEquals(run.getName(), sourceEntity.getFieldValues(RunField.NAME.getName()).get(0));
		Assert.assertEquals(run.getOwner(), sourceEntity.getFieldValues(RunField.OWNER.getName()).get(0));
		Assert.assertEquals(run.getStatus(), sourceEntity.getFieldValues(RunField.STATUS.getName()).get(0));
		Assert.assertEquals(run.getSubtype(), sourceEntity.getFieldValues(RunField.SUBTYPE_ID.getName()).get(0));
		Assert.assertTrue(run.getTestConfigId() == Integer
				.valueOf(sourceEntity.getFieldValues(RunField.TEST_CONFIG_ID.getName()).get(0)));
		Assert.assertTrue(
				run.getTestId() == Integer.valueOf(sourceEntity.getFieldValues(RunField.TEST_ID.getName()).get(0)));
		Assert.assertTrue(run.getTestInstanceId() == Integer
				.valueOf(sourceEntity.getFieldValues(RunField.TEST_INSTANCE_ID.getName()).get(0)));
		Assert.assertTrue(run.getTestSetId() == Integer
				.valueOf(sourceEntity.getFieldValues(RunField.TEST_SET_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingAssociatedTestConfigSet_shouldSetNewAssociatedTest() {
		final Run run = new Run();
		run.setAssociatedTestConfig(new TestConfig());
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestConfig.createEntity());
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getAssociatedTestConfig(), associatedTestConfig);
	}

	@Test
	public void populateFields_withEntityHavingAssociatedTestInstanceSet_shouldSetNewAssociatedTestInstance() {
		final Run run = new Run();
		run.setAssociatedTestInstance(new TestInstance());
		final TestInstance associatedTestInstance = new TestInstance();
		associatedTestInstance.setPlannedHost("planned host");
		final GenericEntity sourceEntity = new GenericEntity("test-instance",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestInstance.createEntity());
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getAssociatedTestInstance(), associatedTestInstance);
	}

	@Test
	public void populateFields_withEntityHavingNoAssociatedTestConfigSet_shouldSetNewAssociatedTest() {
		final Run run = new Run();
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("run",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestConfig.createEntity());
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getAssociatedTestConfig(), associatedTestConfig);
	}

	@Test
	public void populateFields_withEntityHavingNoAssociatedTestInstanceSet_shouldSetNewAssociatedTestInstance() {
		final Run run = new Run();
		final TestInstance associatedTestInstance = new TestInstance();
		associatedTestInstance.setPlannedHost("planned host");
		final GenericEntity sourceEntity = new GenericEntity("run",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(associatedTestInstance.createEntity());
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getAssociatedTestInstance(), associatedTestInstance);
	}

	@Test
	public void populateFields_withEntityHavingUnrelatedEntity_shouldIgnoreUnrelatedEntity() {
		final Run run = new Run();
		final GenericEntity sourceEntity = new GenericEntity("run",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(new ReleaseFolder().createEntity());
		run.populateFields(sourceEntity);
		Assert.assertEquals(run.getAssociatedTestInstance(), new TestInstance());
		Assert.assertEquals(run.getAssociatedTestConfig(), new TestConfig());
	}

	@Test
	public void setAssociatedTestConfig_withconfigAlreadySet_shouldSetNewTestConfig() {
		final Run runOne = new Run();
		runOne.setAssociatedTestConfig(new TestConfig());
		final TestConfig testConfig = new TestConfig();
		testConfig.setName("theName");
		runOne.setAssociatedTestConfig(testConfig);
		Assert.assertEquals(runOne.getAssociatedTestConfig(), testConfig);

	}

	@Test
	public void setAssociatedTestInstance_withInstanceAlreadySet_shouldSetNewTestInstance() {
		final Run runOne = new Run();
		runOne.setAssociatedTestInstance(new TestInstance());
		final TestInstance testInstance = new TestInstance();
		testInstance.setPlannedHost("theHost");
		runOne.setAssociatedTestInstance(testInstance);
		Assert.assertEquals(runOne.getAssociatedTestInstance(), testInstance);

	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setLastModifiedDate_withIncorrectDateFormat_shouldThrowException() {
		final Run run = new Run();
		run.setLastModified("June 23, 1967");
	}

	@Test
	public void toString_withAssociatedTestConfig_shouldReturnNonDefaultStringWithAssociatedTestConfigInfo() {
		final Run runOne = createFullRun();
		runOne.setAssociatedTestConfig(new TestConfig());
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "<Run>"));
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "<TestConfig>"));
	}

	@Test
	public void toString_withAssociatedTestInstance_shouldReturnNonDefaultStringWithAssociatedTestInstanceInfo() {
		final Run runOne = createFullRun();
		runOne.setAssociatedTestInstance(new TestInstance());
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "<Run>"));
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "<TestInstance>"));
	}

	@Test
	public void toString_withNoAssociatedItems_shouldReturnNonDefaultStringWithNotSetString() {
		final Run runOne = createFullRun();
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "<Run>"));
		Assert.assertTrue(StringUtils.contains(runOne.toString(), "Not Set"));
		Assert.assertFalse(StringUtils.contains(runOne.toString(), "<TestCofig>"));
		Assert.assertFalse(StringUtils.contains(runOne.toString(), "<TestInstance>"));
	}

	private Run createFullRun() {
		cal.setTime(new Date());
		final Run run = new Run();
		run.setComments("theComments");
		run.setHost("theHost");
		run.setId(1337);
		run.setLastModified(DATE_FORMATTER.get().format(cal.getTime()));
		run.setName("theName");
		run.setOwner("theOwner");
		run.setStatus("theStatus");
		run.setSubtype("theSubtype");
		run.setTestConfigId(1776);
		run.setTestId(42);
		run.setTestSetId(2001);
		run.setTestInstanceId(5050);
		return run;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(
				new Field(RunField.LAST_MODIFIED.getName(), Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(RunField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(RunField.HOST.getName(), Arrays.asList("theHost")));
		fields.add(new Field(RunField.NAME.getName(), Arrays.asList("theRunName")));
		fields.add(new Field(RunField.STATUS.getName(), Arrays.asList("theStatus")));
		fields.add(new Field(RunField.OWNER.getName(), Arrays.asList("theOwner")));
		fields.add(new Field(RunField.TEST_CONFIG_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(RunField.TEST_ID.getName(), Arrays.asList("2014")));
		fields.add(new Field(RunField.COMMENTS.getName(), Arrays.asList("theComments")));
		fields.add(new Field(RunField.TEST_SET_ID.getName(), Arrays.asList("1492")));
		fields.add(new Field(RunField.SUBTYPE_ID.getName(), Arrays.asList("theSubtype")));
		fields.add(new Field(RunField.TEST_INSTANCE_ID.getName(), Arrays.asList("777")));
		return new GenericEntity("run", fields);
	}
}
