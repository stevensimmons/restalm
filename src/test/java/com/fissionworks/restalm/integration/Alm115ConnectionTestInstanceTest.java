package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.TestConfigField;
import com.fissionworks.restalm.constants.field.TestInstanceField;
import com.fissionworks.restalm.constants.field.TestSetField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.testlab.TestInstance;
import com.fissionworks.restalm.model.entity.testlab.TestSet;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionTestInstanceTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxx:xxx";

	private static final String USERNAME = "xxxx";

	private Alm115Connection alm;

	@BeforeClass
	public void _setup() {
		alm = new Alm115Connection(URL);
		alm.authenticate(new Credentials(USERNAME, PASSWORD));
		alm.login(new Project(DOMAIN, PROJECT_NAME));
	}

	@AfterClass
	public void _teardown() {
		alm.logout();
	}

	@Test
	public void addEntity_withAllFieldsPopulated_shouldAddWithAllEditableAndRequiredFieldsPopulated() {
		final TestInstance initialTestInstance = createMinimumTestInstance();
		initialTestInstance.setPlannedHost("win7.64.ie");
		initialTestInstance.setResponsibleTester("ssimmons");
		initialTestInstance.setStatus("Passed");
		initialTestInstance.setLastModified("2014-11-11 11:11:11");
		initialTestInstance.setTestConfigId(1548);
		initialTestInstance.setTestInstanceNumber(9999);
		initialTestInstance.setTestOrder(42);
		final TestInstance addedTestInstance = alm.addEntity(initialTestInstance);
		alm.deleteEntity(TestInstance.class, addedTestInstance.getId());
		Assert.assertFalse(StringUtils.startsWith(addedTestInstance.getLastModified(), "292269055-12-02"));
		Assert.assertNotEquals(addedTestInstance.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestInstance.getPlannedHost(), "win7.64.ie");
		Assert.assertEquals(addedTestInstance.getResponsibleTester(), "ssimmons");
		Assert.assertNotEquals(addedTestInstance.getTestId(), Integer.MIN_VALUE);
		Assert.assertTrue(addedTestInstance.getTestInstanceNumber() > 0);
		Assert.assertNotEquals(addedTestInstance.getTestConfigId(), Integer.MIN_VALUE);
		Assert.assertNotEquals(addedTestInstance.getTestSetId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestInstance.getStatus(), "No Run");
		Assert.assertEquals(addedTestInstance.getSubtype(), "hp.qc.test-instance.MANUAL");
		Assert.assertEquals(addedTestInstance.getTestOrder(), 42);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setTestSetId(303);
		testInstance.setTestId(339);
		testInstance.setSubtype("hp.qc.test-instance.MANUAL");
		alm.addEntity(testInstance);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setTestSetId(303);
		testInstance.setSubtype("hp.qc.test-instance.MANUAL");
		boolean exceptionThrown = false;
		try {
			alm.addEntity(testInstance);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: test-order,test-id")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: test-id,test-order"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withTestInstanceMininumRequiredFieldsPopulated_shouldCreateTestInstance() {
		final TestInstance addedTestInstance = alm.addEntity(createMinimumTestInstance());
		alm.deleteEntity(TestInstance.class, addedTestInstance.getId());
		Assert.assertFalse(StringUtils.startsWith(addedTestInstance.getLastModified(), "292269055-12-02"));
		Assert.assertNotEquals(addedTestInstance.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestInstance.getPlannedHost(), "");
		Assert.assertEquals(addedTestInstance.getResponsibleTester(), "");
		Assert.assertNotEquals(addedTestInstance.getTestId(), Integer.MIN_VALUE);
		Assert.assertTrue(addedTestInstance.getTestInstanceNumber() > 0);
		Assert.assertNotEquals(addedTestInstance.getTestConfigId(), Integer.MIN_VALUE);
		Assert.assertNotEquals(addedTestInstance.getTestSetId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestInstance.getStatus(), "No Run");
		Assert.assertEquals(addedTestInstance.getSubtype(), "hp.qc.test-instance.MANUAL");
	}

	@Test
	public void getEntities_withAssociatedTestConfigFieldsSpecified_shouldReturnTestInstanceWithRelatedEntity() {
		final AlmEntityCollection<TestInstance> testInstances = alm.getEntities(TestInstance.class,
				new RestParameters().queryFilter(TestInstanceField.ID, "6").relatedFields(TestConfigField.CREATED_BY,
						TestConfigField.CREATION_DATE, TestConfigField.DATA_STATE, TestConfigField.DESCRIPTION,
						TestConfigField.NAME, TestConfigField.PARENT_ID, TestConfigField.TEST_NAME));
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setCreatedBy("ssimmons");
		associatedTestConfig.setCreationDate("2014-08-13");
		associatedTestConfig.setDataState(0);
		associatedTestConfig.setDescription("Test Config Description");
		associatedTestConfig.setId(1518);
		associatedTestConfig.setName("Get Instance By Id");
		associatedTestConfig.setParentId(339);
		associatedTestConfig.setTestName("Test Instance Test Case");
		Assert.assertEquals(testInstances.size(), 1);
		for (final TestInstance testInstance : testInstances) {
			Assert.assertEquals(testInstance.getAssociatedTestConfig(), associatedTestConfig);
		}

	}

	@Test
	public void getEntities_withAssociatedTestFieldsSpecified_shouldReturnTestInstanceWithRelatedEntity() {
		final AlmEntityCollection<TestInstance> testInstances = alm.getEntities(TestInstance.class,
				new RestParameters().queryFilter(TestInstanceField.ID, "6").relatedFields(AlmTestField.CREATION_DATE,
						AlmTestField.DESCRIPTION, AlmTestField.DESIGNER, AlmTestField.NAME, AlmTestField.PARENT_ID,
						AlmTestField.STATUS, AlmTestField.TYPE));
		final AlmTest associatedTest = new AlmTest();
		associatedTest.setCreationDate("2014-08-13");
		associatedTest.setId(339);
		associatedTest.setName("Test Instance Test Case");
		associatedTest.setDescription("Test Instance Test Case Description");
		associatedTest.setDesigner("ssimmons");
		associatedTest.setParentId(1158);
		associatedTest.setStatus("Design");
		associatedTest.setType("MANUAL");
		Assert.assertEquals(testInstances.size(), 1);
		for (final TestInstance testInstance : testInstances) {
			Assert.assertEquals(testInstance.getAssociatedTest(), associatedTest);
		}

	}

	@Test
	public void getEntities_withCrossQuerySet_shouldReturnAlmTestsThatMatchQuery() {
		final AlmEntityCollection<TestInstance> testInstanceCollection = alm.getEntities(TestInstance.class,
				new RestParameters().crossQueryFilter(TestSetField.NAME, "'Get Test Instance'"));
		Assert.assertTrue(testInstanceCollection.size() > 0);
		for (final TestInstance instance : testInstanceCollection) {
			Assert.assertEquals(instance.getTestSetId(), 103);
		}
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestInstance testInstance : alm.getEntities(TestInstance.class,
				new RestParameters().fields(TestInstanceField.SUBTYPE, TestInstanceField.STATUS))) {
			Assert.assertTrue(StringUtils.startsWith(testInstance.getLastModified(), "292269055-12-02"));
			Assert.assertNotEquals(testInstance.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getPlannedHost(), "");
			Assert.assertEquals(testInstance.getResponsibleTester(), "");
			Assert.assertEquals(testInstance.getTestId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestInstanceNumber(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestConfigId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestSetId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(testInstance.getStatus(), "");
			Assert.assertNotEquals(testInstance.getSubtype(), "");
			Assert.assertEquals(testInstance.getTestOrder(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnTestInstancesMatchingQuery() {
		final AlmEntityCollection<TestInstance> testInstanceCollection = alm.getEntities(TestInstance.class,
				new RestParameters().queryFilter(TestInstanceField.TEST_SET_ID, "103")
						.queryFilter(TestInstanceField.TEST_ID, "339"));
		for (final TestInstance instance : testInstanceCollection) {
			Assert.assertEquals(instance.getTestSetId(), 103);
			Assert.assertEquals(instance.getTestId(), 339);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(TestInstance.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestSetFieldsSpecified_shouldReturnTestInstanceWithRelatedEntity() {
		final AlmEntityCollection<TestInstance> testInstances = alm.getEntities(TestInstance.class,
				new RestParameters().queryFilter(TestInstanceField.ID, "6").relatedFields(TestSetField.NAME,
						TestSetField.DESCRIPTION, TestSetField.PARENT_ID, TestSetField.TYPE));
		final TestSet parentTestSet = new TestSet();
		parentTestSet.setDescription("Get Test Instance Test Set");
		parentTestSet.setId(103);
		parentTestSet.setName("Get Test Instance");
		parentTestSet.setParentId(82);
		parentTestSet.setSubtype("hp.qc.test-set.default");
		Assert.assertEquals(testInstances.size(), 1);
		for (final TestInstance testInstance : testInstances) {
			Assert.assertEquals(testInstance.getParentTestSet(), parentTestSet);
		}

	}

	@Test
	public void getEntities_withQueryAndCrossQuerySet_shouldReturnTestInstancesThatMatchQuery() {
		final AlmEntityCollection<TestInstance> testCollection = alm.getEntities(TestInstance.class,
				new RestParameters().crossQueryFilter(TestSetField.NAME, "'Get Test Instance'")
						.queryFilter(TestInstanceField.RESPONSIBLE_TESTER, "svc"));
		for (final TestInstance testInstance : testCollection) {
			Assert.assertEquals(testInstance.getTestSetId(), 103);
			Assert.assertEquals(testInstance.getResponsibleTester(), "svc");
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestInstance testInstance : alm.getEntities(TestInstance.class,
				new RestParameters().fields(TestInstanceField.STATUS))) {
			Assert.assertTrue(StringUtils.startsWith(testInstance.getLastModified(), "292269055-12-02"));
			Assert.assertNotEquals(testInstance.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getPlannedHost(), "");
			Assert.assertEquals(testInstance.getResponsibleTester(), "");
			Assert.assertEquals(testInstance.getTestId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestInstanceNumber(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestConfigId(), Integer.MIN_VALUE);
			Assert.assertEquals(testInstance.getTestSetId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(testInstance.getStatus(), "");
			Assert.assertEquals(testInstance.getSubtype(), "");
			Assert.assertEquals(testInstance.getTestOrder(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnTestInstancesMatchingQuery() {
		final AlmEntityCollection<TestInstance> testIntsanceCollection = alm.getEntities(TestInstance.class,
				new RestParameters().queryFilter(TestInstanceField.RESPONSIBLE_TESTER, "svc"));
		for (final TestInstance testInstance : testIntsanceCollection) {
			Assert.assertEquals(testInstance.getResponsibleTester(), "svc");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnTestInstancesStartingAtStartIndex() {
		final AlmEntityCollection<TestInstance> initialTestInstanceCollection = alm.getEntities(TestInstance.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<TestInstance> indexTwoCollection = alm.getEntities(TestInstance.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestInstanceCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final TestInstance testInstance : indexTwoCollection) {
			Assert.assertFalse(initialTestInstanceCollection.contains(testInstance));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withTestInstanceAndNonExistentId_shouldThrowException() {
		alm.getEnityById(TestInstance.class, 99999999);
	}

	@Test
	public void getEntity_withTestInstanceAndValidId_shouldReturnCorrectTestInstance() {
		final TestInstance testInstance = alm.getEnityById(TestInstance.class, 6);
		Assert.assertNotNull(testInstance);
		Assert.assertTrue(testInstance.isExactMatch(getTestInstance6()),
				"expected:\n" + getTestInstance6() + "\nbut got:\n" + testInstance);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final TestInstance testInstance = alm.getEnityById(TestInstance.class, 70);
		final TestInstance intialTestInstance = new TestInstance();
		intialTestInstance.populateFields(testInstance.createEntity());
		testInstance.setTestSetId(303);
		testInstance.setLastModified("2014-09-04 09:43:51");
		testInstance.setPlannedHost(testInstance.getPlannedHost().equals("host1") ? "host2" : "host1");
		testInstance.setResponsibleTester(testInstance.getResponsibleTester().equals("ssimmons") ? "svc" : "ssimmons");
		testInstance.setStatus(testInstance.getStatus().equals("No Run") ? "Failed" : "No Run");
		testInstance.setSubtype("hp.pc.test-instance.performance-test");
		testInstance.setTestConfigId(1234);
		testInstance.setTestId(1234);
		testInstance.setTestInstanceNumber(testInstance.getTestInstanceNumber() == 42 ? 43 : 42);
		testInstance.setTestOrder(testInstance.getTestOrder() == 42 ? 43 : 42);

		alm.updateEntity(testInstance, TestInstanceField.PLANNED_HOST, TestInstanceField.RESPONSIBLE_TESTER);
		final TestInstance updatedTestInstance = alm.getEnityById(TestInstance.class, testInstance.getId());
		Assert.assertNotEquals(updatedTestInstance.getLastModified(), testInstance.getLastModified());
		Assert.assertEquals(updatedTestInstance.getPlannedHost(), testInstance.getPlannedHost());
		Assert.assertEquals(updatedTestInstance.getResponsibleTester(), testInstance.getResponsibleTester());
		Assert.assertEquals(updatedTestInstance.getStatus(), intialTestInstance.getStatus());
		Assert.assertEquals(updatedTestInstance.getSubtype(), intialTestInstance.getSubtype());
		Assert.assertEquals(updatedTestInstance.getTestConfigId(), intialTestInstance.getTestConfigId());
		Assert.assertEquals(updatedTestInstance.getTestId(), intialTestInstance.getTestId());
		Assert.assertEquals(updatedTestInstance.getTestInstanceNumber(), intialTestInstance.getTestInstanceNumber());
		Assert.assertEquals(updatedTestInstance.getTestOrder(), intialTestInstance.getTestOrder());
		Assert.assertEquals(updatedTestInstance.getTestSetId(), intialTestInstance.getTestSetId());
	}

	@Test
	public void updateEntity_withoutFieldsSpecified_shouldUpdateAllEditableFields() {
		final TestInstance testInstance = alm.getEnityById(TestInstance.class, 71);
		final TestInstance intialTestInstance = new TestInstance();
		intialTestInstance.populateFields(testInstance.createEntity());
		testInstance.setTestSetId(303);
		testInstance.setLastModified("2014-09-04 09:43:51");
		testInstance.setPlannedHost(testInstance.getPlannedHost().equals("host1") ? "host2" : "host1");
		testInstance.setResponsibleTester(testInstance.getResponsibleTester().equals("ssimmons") ? "svc" : "ssimmons");
		testInstance.setStatus(testInstance.getStatus().equals("No Run") ? "Failed" : "No Run");
		testInstance.setSubtype("hp.pc.test-instance.performance-test");
		testInstance.setTestConfigId(1234);
		testInstance.setTestId(1234);
		testInstance.setTestInstanceNumber(testInstance.getTestInstanceNumber() == 42 ? 43 : 42);
		testInstance.setTestOrder(testInstance.getTestOrder() == 1 ? 2 : 1);

		alm.updateEntity(testInstance);
		final TestInstance updatedTestInstance = alm.getEnityById(TestInstance.class, testInstance.getId());
		Assert.assertNotEquals(updatedTestInstance.getLastModified(), testInstance.getLastModified());
		Assert.assertEquals(updatedTestInstance.getPlannedHost(), testInstance.getPlannedHost());
		Assert.assertEquals(updatedTestInstance.getResponsibleTester(), testInstance.getResponsibleTester());
		Assert.assertEquals(updatedTestInstance.getStatus(), testInstance.getStatus());
		Assert.assertEquals(updatedTestInstance.getSubtype(), testInstance.getSubtype());
		Assert.assertEquals(updatedTestInstance.getTestConfigId(), intialTestInstance.getTestConfigId());
		Assert.assertEquals(updatedTestInstance.getTestId(), intialTestInstance.getTestId());
		Assert.assertEquals(updatedTestInstance.getTestInstanceNumber(), intialTestInstance.getTestInstanceNumber());
		Assert.assertEquals(updatedTestInstance.getTestOrder(), testInstance.getTestOrder());
		Assert.assertEquals(updatedTestInstance.getTestSetId(), intialTestInstance.getTestSetId());
	}

	private TestInstance createMinimumTestInstance() {
		final TestInstance testInstance = new TestInstance();
		testInstance.setTestSetId(303);
		testInstance.setTestId(339);
		testInstance.setTestOrder(1);
		testInstance.setSubtype("hp.qc.test-instance.MANUAL");
		return testInstance;
	}

	private TestInstance getTestInstance6() {
		final TestInstance instance = new TestInstance();
		instance.setId(6);
		instance.setLastModified("2014-09-05 09:21:36");
		instance.setPlannedHost("win7.64.ie");
		instance.setResponsibleTester("ssimmons");
		instance.setStatus("No Run");
		instance.setSubtype("hp.qc.test-instance.MANUAL");
		instance.setTestConfigId(1518);
		instance.setTestId(339);
		instance.setTestInstanceNumber(1);
		instance.setTestOrder(1);
		instance.setTestSetId(103);
		return instance;
	}
}
