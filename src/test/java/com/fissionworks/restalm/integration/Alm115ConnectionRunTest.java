package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.RunField;
import com.fissionworks.restalm.constants.field.TestConfigField;
import com.fissionworks.restalm.constants.field.TestInstanceField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.testlab.Run;
import com.fissionworks.restalm.model.entity.testlab.TestInstance;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionRunTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxx:xxxx";

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
		final String name = createUniqueName();
		final Run initialRun = createMinimumRun(name);
		initialRun.setComments("the comments");
		initialRun.setHost("theHost");
		initialRun.setId(123345);
		initialRun.setLastModified("2013-11-12 13:14:15");
		initialRun.setTestConfigId(123);
		final Run addedRun = alm.addEntity(initialRun);
		alm.deleteEntity(Run.class, addedRun.getId());
		Assert.assertNotEquals(addedRun.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedRun.getComments(), "the comments");
		Assert.assertEquals(addedRun.getHost(), "theHost");
		Assert.assertFalse(StringUtils.startsWith(addedRun.getLastModified(), "292269055-12-02"));
		Assert.assertEquals(addedRun.getName(), name);
		Assert.assertEquals(addedRun.getOwner(), initialRun.getOwner());
		Assert.assertEquals(addedRun.getStatus(), initialRun.getStatus());
		Assert.assertEquals(addedRun.getSubtype(), initialRun.getSubtype());
		Assert.assertNotEquals(addedRun.getTestConfigId(), initialRun.getTestConfigId());
		Assert.assertEquals(addedRun.getTestId(), initialRun.getTestId());
		Assert.assertEquals(addedRun.getTestInstanceId(), initialRun.getTestInstanceId());
		Assert.assertEquals(addedRun.getTestSetId(), initialRun.getTestSetId());

	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final Run run = new Run();
		run.setStatus("Passed");
		run.setOwner("svc");
		run.setTestSetId(458);
		run.setTestInstanceId(151);
		run.setTestId(429);
		run.setSubtype("hp.qc.run.MANUAL");
		alm.addEntity(run);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final Run run = new Run();
		run.setOwner("svc");
		run.setTestSetId(458);
		run.setTestInstanceId(151);
		run.setTestId(429);
		run.setSubtype("hp.qc.run.MANUAL");
		boolean exceptionThrown = false;
		try {
			alm.addEntity(run);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: status,name")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: name,status"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withTestSetMininumRequiredFieldsPopulated_shouldCreateEntity() {
		final String name = createUniqueName();
		final Run initialRun = createMinimumRun(name);
		final Run addedRun = alm.addEntity(initialRun);
		alm.deleteEntity(Run.class, addedRun.getId());
		Assert.assertNotEquals(addedRun.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedRun.getComments(), "");
		Assert.assertEquals(addedRun.getHost(), "");
		Assert.assertFalse(StringUtils.startsWith(addedRun.getLastModified(), "292269055-12-02"));
		Assert.assertEquals(addedRun.getName(), name);
		Assert.assertEquals(addedRun.getOwner(), initialRun.getOwner());
		Assert.assertEquals(addedRun.getStatus(), initialRun.getStatus());
		Assert.assertEquals(addedRun.getSubtype(), initialRun.getSubtype());
		Assert.assertNotEquals(addedRun.getTestConfigId(), initialRun.getTestConfigId());
		Assert.assertEquals(addedRun.getTestId(), initialRun.getTestId());
		Assert.assertEquals(addedRun.getTestInstanceId(), initialRun.getTestInstanceId());
		Assert.assertEquals(addedRun.getTestSetId(), initialRun.getTestSetId());
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Run run : alm.getEntities(Run.class, new RestParameters().fields(RunField.NAME, RunField.STATUS))) {
			Assert.assertNotEquals(run.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getComments(), "");
			Assert.assertEquals(run.getHost(), "");
			Assert.assertTrue(StringUtils.startsWith(run.getLastModified(), "292269055-12-02"));
			Assert.assertNotEquals(run.getName(), "");
			Assert.assertEquals(run.getOwner(), "");
			Assert.assertNotEquals(run.getStatus(), "");
			Assert.assertEquals(run.getSubtype(), "");
			Assert.assertEquals(run.getTestConfigId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestInstanceId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestSetId(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnRunsMatchingQuery() {
		final AlmEntityCollection<Run> runCollection = alm.getEntities(Run.class,
				new RestParameters().queryFilter(RunField.NAME, "GetRunById").queryFilter(RunField.STATUS, "Failed"));
		for (final Run run : runCollection) {
			Assert.assertEquals(run.getName(), "GetRunById");
			Assert.assertEquals(run.getStatus(), "Failed");
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(Run.class, new RestParameters().pageSize(2)).size(), 2);
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Run run : alm.getEntities(Run.class, new RestParameters().fields(RunField.NAME))) {
			Assert.assertNotEquals(run.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getComments(), "");
			Assert.assertEquals(run.getHost(), "");
			Assert.assertTrue(StringUtils.startsWith(run.getLastModified(), "292269055-12-02"));
			Assert.assertNotEquals(run.getName(), "");
			Assert.assertEquals(run.getOwner(), "");
			Assert.assertEquals(run.getStatus(), "");
			Assert.assertEquals(run.getSubtype(), "");
			Assert.assertEquals(run.getTestConfigId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestInstanceId(), Integer.MIN_VALUE);
			Assert.assertEquals(run.getTestSetId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnTestSetsMatchingQuery() {
		final AlmEntityCollection<Run> runCollection = alm.getEntities(Run.class,
				new RestParameters().queryFilter(RunField.NAME, "GetRunById"));
		for (final Run run : runCollection) {
			Assert.assertEquals(run.getName(), "GetRunById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnTestSetStartingAtStartIndex() {
		final AlmEntityCollection<Run> initialRunCollection = alm.getEntities(Run.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<Run> indexTwoCollection = alm.getEntities(Run.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialRunCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final Run run : indexTwoCollection) {
			Assert.assertFalse(initialRunCollection.contains(run));
		}
	}

	@Test
	public void getEntities_withTestConfigFieldsSpecified_shouldReturnRunWithRelatedEntity() {
		final AlmEntityCollection<Run> runCollection = alm.getEntities(Run.class,
				new RestParameters().queryFilter(RunField.ID, "48").relatedFields(TestConfigField.CREATED_BY,
						TestConfigField.CREATION_DATE, TestConfigField.DATA_STATE, TestConfigField.DESCRIPTION,
						TestConfigField.NAME, TestConfigField.PARENT_ID, TestConfigField.TEST_NAME));
		final TestConfig associatedTestConfig = new TestConfig();
		associatedTestConfig.setId(1683);
		associatedTestConfig.setName("Test for Run Testing");
		associatedTestConfig.setDescription("test config for run testing");
		associatedTestConfig.setCreatedBy("ssimmons");
		associatedTestConfig.setDataState(0);
		associatedTestConfig.setParentId(429);
		associatedTestConfig.setCreationDate("2014-09-18");
		associatedTestConfig.setTestName("Test for Run Testing");

		Assert.assertEquals(runCollection.size(), 1);
		for (final Run run : runCollection) {
			Assert.assertTrue(run.getAssociatedTestConfig().isExactMatch(associatedTestConfig));
		}
	}

	@Test
	public void getEntities_withTestInstanceFieldsSpecified_shouldReturnRunWithRelatedEntity() {
		final AlmEntityCollection<Run> runCollection = alm.getEntities(Run.class,
				new RestParameters().queryFilter(RunField.ID, "48").relatedFields(TestInstanceField.LAST_MODIFIED,
						TestInstanceField.PLANNED_HOST, TestInstanceField.RESPONSIBLE_TESTER, TestInstanceField.STATUS,
						TestInstanceField.SUBTYPE, TestInstanceField.TEST_CONFIG_ID, TestInstanceField.TEST_ID,
						TestInstanceField.TEST_INSTANCE_NUMBER, TestInstanceField.TEST_ORDER,
						TestInstanceField.TEST_SET_ID));
		final TestInstance associatedTestInstance = new TestInstance();
		associatedTestInstance.setId(151);
		associatedTestInstance.setPlannedHost("runTesting");
		associatedTestInstance.setResponsibleTester("ssimmons");
		associatedTestInstance.setSubtype("hp.qc.test-instance.MANUAL");
		associatedTestInstance.setTestConfigId(1683);
		associatedTestInstance.setTestId(429);
		associatedTestInstance.setTestInstanceNumber(1);
		associatedTestInstance.setTestOrder(1);
		associatedTestInstance.setTestSetId(458);

		Assert.assertEquals(runCollection.size(), 1);
		for (final Run run : runCollection) {
			// set last modified/status since its value may change based on
			// other tests, etc.
			associatedTestInstance.setLastModified(run.getAssociatedTestInstance().getLastModified());
			associatedTestInstance.setStatus(run.getAssociatedTestInstance().getStatus());
			Assert.assertTrue(run.getAssociatedTestInstance().isExactMatch(associatedTestInstance));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(Run.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectTestSet() {
		final Run run = alm.getEnityById(Run.class, 48);
		Assert.assertNotNull(run);
		Assert.assertTrue(run.isExactMatch(getRun48(run)), "expected:\n" + getRun48(run) + "\nbut got:\n" + run);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final Run run = alm.getEnityById(Run.class, 83);
		final Run initialRun = new Run();
		initialRun.populateFields(run.createEntity());

		run.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setHost("newHost" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setName("newName" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setOwner(run.getOwner().equals("svc") ? "ssimmons" : "svc");
		run.setStatus(run.getStatus().equals("Passed") ? "Failed" : "Passed");
		run.setSubtype(run.getSubtype().equals("hp.qc.run.MANUAL") ? "hp.pc.run.performance-test" : "hp.qc.run.MANUAL");
		run.setTestConfigId(12345);
		run.setTestId(123435);
		run.setTestInstanceId(12345);
		run.setTestSetId(12345);

		alm.updateEntity(run, RunField.NAME, RunField.STATUS);
		final Run updatedrun = alm.getEnityById(Run.class, 83);
		Assert.assertEquals(updatedrun.getComments(), initialRun.getComments());
		Assert.assertEquals(updatedrun.getHost(), initialRun.getHost());
		Assert.assertEquals(updatedrun.getName(), run.getName());
		Assert.assertEquals(updatedrun.getOwner(), initialRun.getOwner());
		Assert.assertEquals(updatedrun.getStatus(), run.getStatus());
		Assert.assertEquals(updatedrun.getSubtype(), initialRun.getSubtype());
		Assert.assertEquals(updatedrun.getTestConfigId(), initialRun.getTestConfigId());
		Assert.assertEquals(updatedrun.getTestId(), initialRun.getTestId());
		Assert.assertEquals(updatedrun.getTestInstanceId(), initialRun.getTestInstanceId());
		Assert.assertEquals(updatedrun.getTestSetId(), initialRun.getTestSetId());
	}

	@Test
	public void updateEntity_withoutFieldsSpecified_shouldUpdateAllEditableFields() {
		final Run run = alm.getEnityById(Run.class, 83);
		final Run initialRun = new Run();
		initialRun.populateFields(run.createEntity());

		run.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setHost("newHost" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setName("newName" + System.currentTimeMillis() + Thread.currentThread().getId());
		run.setOwner(run.getOwner().equals("svc") ? "ssimmons" : "svc");
		run.setStatus(run.getStatus().equals("Passed") ? "Failed" : "Passed");
		run.setSubtype(run.getSubtype().equals("hp.qc.run.MANUAL") ? "hp.pc.run.performance-test" : "hp.qc.run.MANUAL");
		run.setTestConfigId(12345);
		run.setTestId(123435);
		run.setTestInstanceId(12345);
		run.setTestSetId(12345);

		alm.updateEntity(run);
		final Run updatedrun = alm.getEnityById(Run.class, 83);
		Assert.assertEquals(updatedrun.getComments(), run.getComments());
		Assert.assertEquals(updatedrun.getHost(), run.getHost());
		Assert.assertEquals(updatedrun.getName(), run.getName());
		Assert.assertEquals(updatedrun.getOwner(), run.getOwner());
		Assert.assertEquals(updatedrun.getStatus(), run.getStatus());
		Assert.assertEquals(updatedrun.getSubtype(), run.getSubtype());
		Assert.assertEquals(updatedrun.getTestConfigId(), initialRun.getTestConfigId());
		Assert.assertEquals(updatedrun.getTestId(), initialRun.getTestId());
		Assert.assertEquals(updatedrun.getTestInstanceId(), initialRun.getTestInstanceId());
		Assert.assertEquals(updatedrun.getTestSetId(), initialRun.getTestSetId());
	}

	private Run createMinimumRun(final String name) {
		final Run run = new Run();
		run.setName(name);
		run.setStatus("Passed");
		run.setOwner("svc");
		run.setTestSetId(458);
		run.setTestInstanceId(151);
		run.setTestId(429);
		run.setSubtype("hp.qc.run.MANUAL");
		return run;
	}

	private String createUniqueName() {
		return "addedRun" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private Run getRun48(final Run theRun) {
		final Run run = new Run();
		run.setId(48);
		run.setName("GetRunById");
		run.setStatus("Passed");
		run.setComments("get run by id comments");
		run.setHost("runTesting");
		run.setOwner("ssimmons");
		run.setSubtype("hp.qc.run.MANUAL");
		run.setLastModified(theRun.getLastModified());
		run.setTestConfigId(1683);
		run.setTestId(429);
		run.setTestInstanceId(151);
		run.setTestSetId(458);
		return run;
	}
}
