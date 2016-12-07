package com.fissionworks.restalm.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.TestConfigField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionTestConfigTest {

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

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final TestConfig testConfig = new TestConfig();
		testConfig.setParentId(143);
		testConfig.setDataState(0);
		alm.addEntity(testConfig);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final TestConfig testConfig = new TestConfig();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(testConfig);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: parent-id,name")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: name,parent-id"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withAllFieldsPopulated_shouldCreateTestConfig() {
		final String name = createUniqueName();
		final TestConfig initialTestConfig = createMinimumTestConfig(name);
		initialTestConfig.setCreatedBy("svc");
		initialTestConfig.setCreationDate("2014-03-21");
		initialTestConfig.setDataState(1);
		initialTestConfig.setDescription("the description");
		initialTestConfig.setId(1337);
		initialTestConfig.setTestName("the test name");
		final TestConfig addedTestConfig = alm.addEntity(initialTestConfig);
		alm.deleteEntity(TestConfig.class, addedTestConfig.getId());
		Assert.assertEquals(addedTestConfig.getCreationDate(), "2014-03-21");
		Assert.assertEquals(addedTestConfig.getCreatedBy(), "svc");
		Assert.assertEquals(addedTestConfig.getDataState(), 1);
		Assert.assertEquals(addedTestConfig.getDescription(), "the description");
		Assert.assertNotEquals(addedTestConfig.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestConfig.getName(), name);
		Assert.assertEquals(addedTestConfig.getParentId(), 143);
		Assert.assertEquals(addedTestConfig.getTestName(), "AddDeleteTestConfig");
	}

	@Test
	public void addEntityAndDelete_withTestConfigMininumRequiredFieldsPopulated_shouldCreateTestConfig() {
		final String name = createUniqueName();
		final TestConfig addedTestConfig = alm.addEntity(createMinimumTestConfig(name));
		alm.deleteEntity(TestConfig.class, addedTestConfig.getId());
		Assert.assertNotEquals(addedTestConfig.getCreationDate(), "292269055-12-02");
		Assert.assertEquals(addedTestConfig.getCreatedBy(), "");
		Assert.assertEquals(addedTestConfig.getDataState(), 0);
		Assert.assertEquals(addedTestConfig.getDescription(), "");
		Assert.assertNotEquals(addedTestConfig.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestConfig.getName(), name);
		Assert.assertEquals(addedTestConfig.getParentId(), 143);
		Assert.assertEquals(addedTestConfig.getTestName(), "AddDeleteTestConfig");
	}

	@Test
	public void getEntities_withCrossQuerySet_shouldReturnTestConfigsThatMatchQuery() {
		final AlmEntityCollection<TestConfig> testConfigCollection = alm.getEntities(TestConfig.class,
				new RestParameters().crossQueryFilter(AlmTestField.NAME, "GetTestConfigByIdTest"));
		for (final TestConfig testConfig : testConfigCollection) {
			Assert.assertEquals(testConfig.getTestName(), "GetTestConfigByIdTest");
		}
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestConfig testConfig : alm.getEntities(TestConfig.class, new RestParameters()
				.fields(TestConfigField.NAME, TestConfigField.PARENT_ID, TestConfigField.TEST_NAME))) {
			Assert.assertEquals(testConfig.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(testConfig.getCreatedBy(), "");
			Assert.assertEquals(testConfig.getDataState(), 0);
			Assert.assertEquals(testConfig.getDescription(), "");
			Assert.assertNotEquals(testConfig.getId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(testConfig.getName(), "");
			Assert.assertNotEquals(testConfig.getParentId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(testConfig.getTestName(), "");
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnTestConfigsMatchingQuery() {
		final AlmEntityCollection<TestConfig> testConfigCollection = alm.getEntities(TestConfig.class,
				new RestParameters().queryFilter(TestConfigField.NAME, "GetTestConfigById")
						.queryFilter(TestConfigField.CREATED_BY, "svc"));
		for (final TestConfig testConfig : testConfigCollection) {
			Assert.assertEquals(testConfig.getName(), "GetTestConfigById");
			Assert.assertEquals(testConfig.getCreatedBy(), "svc");
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(TestConfig.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestFieldsSpecified_shouldReturnTestWithRelatedEntity() {
		final AlmEntityCollection<TestConfig> testConfigs = alm.getEntities(TestConfig.class,
				new RestParameters().queryFilter(TestConfigField.ID, "1002").relatedFields(AlmTestField.NAME,
						AlmTestField.CREATION_DATE, AlmTestField.DESCRIPTION, AlmTestField.DESIGNER, AlmTestField.NAME,
						AlmTestField.PARENT_ID, AlmTestField.STATUS, AlmTestField.TYPE));

		Assert.assertEquals(testConfigs.size(), 1);
		for (final TestConfig testConfig : testConfigs) {
			Assert.assertEquals(testConfig.getParentAlmTest(), alm.getEnityById(AlmTest.class, 2));
		}

	}

	@Test
	public void getEntities_withQueryAndCrossQuerySet_shouldReturnTestConfigsThatMatchQuery() {
		final AlmEntityCollection<TestConfig> testConfigCollection = alm.getEntities(TestConfig.class,
				new RestParameters().crossQueryFilter(AlmTestField.NAME, "GetTestConfigByIdTest")
						.queryFilter(TestConfigField.NAME, "GetTestConfigById"));
		for (final TestConfig testConfig : testConfigCollection) {
			Assert.assertEquals(testConfig.getTestName(), "GetTestConfigByIdTest");
			Assert.assertEquals(testConfig.getName(), "GetTestConfigById");
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestConfig testConfig : alm.getEntities(TestConfig.class,
				new RestParameters().fields(TestConfigField.NAME))) {
			Assert.assertEquals(testConfig.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(testConfig.getCreatedBy(), "");
			Assert.assertEquals(testConfig.getDataState(), 0);
			Assert.assertEquals(testConfig.getDescription(), "");
			Assert.assertNotEquals(testConfig.getId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(testConfig.getName(), "");
			Assert.assertEquals(testConfig.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(testConfig.getTestName(), "");
		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnTestConfigsMatchingQuery() {
		final AlmEntityCollection<TestConfig> testConfigCollection = alm.getEntities(TestConfig.class,
				new RestParameters().queryFilter(TestConfigField.NAME, "GetTestConfigById"));
		for (final TestConfig testConfig : testConfigCollection) {
			Assert.assertEquals(testConfig.getName(), "GetTestConfigById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnTestConfigStartingAtStartIndex() {
		final AlmEntityCollection<TestConfig> initialTestCollection = alm.getEntities(TestConfig.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<TestConfig> indexTwoCollection = alm.getEntities(TestConfig.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final TestConfig testConfig : indexTwoCollection) {
			Assert.assertFalse(initialTestCollection.contains(testConfig));
		}

	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withTestConfigAndNonExistentId_shouldThrowException() {
		alm.getEnityById(TestConfig.class, 99999999);
	}

	@Test
	public void getEntity_withTestConfigAndValidId_shouldReturnCorrectTestConfig() {
		final TestConfig testConfig = alm.getEnityById(TestConfig.class, 1002);
		Assert.assertNotNull(testConfig);
		Assert.assertTrue(testConfig.isExactMatch(getTestConfig1002()),
				"expected:\n" + getTestConfig1002() + "\nbut got:\n" + testConfig);
	}

	@Test
	public void updateEntity_withoutFieldsSpecified_shouldUpdateAllEditableFields() {
		final TestConfig testConfig = alm.getEnityById(TestConfig.class, 1236);
		final TestConfig initialTestConfig = new TestConfig();
		initialTestConfig.populateFields(testConfig.createEntity());

		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newCreatedby = testConfig.getCreatedBy().equals("ssimmons") ? "svc" : "ssimmons";
		final String newCreationDate = testConfig.getCreationDate().equals("2014-06-24") ? "2011-01-18" : "2014-06-24";
		final int newDataState = testConfig.getDataState() == 0 ? 1 : 0;
		testConfig.setName(newName);
		testConfig.setDescription(newDescription);
		testConfig.setCreatedBy(newCreatedby);
		testConfig.setCreationDate(newCreationDate);
		testConfig.setDataState(newDataState);

		alm.updateEntity(testConfig, TestConfigField.NAME, TestConfigField.DESCRIPTION);
		final TestConfig updatedTestConfig = alm.getEnityById(TestConfig.class, 1236);
		Assert.assertEquals(updatedTestConfig.getCreationDate(), initialTestConfig.getCreationDate());
		Assert.assertEquals(updatedTestConfig.getDescription(), newDescription);
		Assert.assertEquals(updatedTestConfig.getCreatedBy(), initialTestConfig.getCreatedBy());
		Assert.assertEquals(updatedTestConfig.getName(), newName);
		Assert.assertEquals(updatedTestConfig.getDataState(), initialTestConfig.getDataState());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final TestConfig testConfig = alm.getEnityById(TestConfig.class, 1218);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newCreatedby = testConfig.getCreatedBy().equals("ssimmons") ? "svc" : "ssimmons";
		final String newCreationDate = testConfig.getCreationDate().equals("2014-06-24") ? "2011-01-18" : "2014-06-24";
		final int newDataState = testConfig.getDataState() == 0 ? 1 : 0;
		testConfig.setName(newName);
		testConfig.setDescription(newDescription);
		testConfig.setCreatedBy(newCreatedby);
		testConfig.setCreationDate(newCreationDate);
		testConfig.setDataState(newDataState);

		alm.updateEntity(testConfig);
		final TestConfig updatedTestConfig = alm.getEnityById(TestConfig.class, 1218);
		Assert.assertEquals(updatedTestConfig.getCreationDate(), newCreationDate);
		Assert.assertEquals(updatedTestConfig.getDescription(), newDescription);
		Assert.assertEquals(updatedTestConfig.getCreatedBy(), newCreatedby);
		Assert.assertEquals(updatedTestConfig.getName(), newName);
		Assert.assertEquals(updatedTestConfig.getDataState(), newDataState);
	}

	private TestConfig createMinimumTestConfig(final String name) {
		final TestConfig testConfig = new TestConfig();
		testConfig.setName(name);
		testConfig.setParentId(143);
		return testConfig;
	}

	private String createUniqueName() {
		return "addedTestConfig" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private TestConfig getTestConfig1002() {
		final TestConfig testConfig = new TestConfig();
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestConfigField.ID.getName(), Arrays.asList("1002")));
		fields.add(new Field(TestConfigField.NAME.getName(), Arrays.asList("GetTestConfigById")));
		fields.add(new Field(TestConfigField.CREATION_DATE.getName(), Arrays.asList("2014-03-25")));
		fields.add(new Field(TestConfigField.DESCRIPTION.getName(), Arrays.asList("GetTestConfigById description")));
		fields.add(new Field(TestConfigField.CREATED_BY.getName(), Arrays.asList("ssimmons")));
		fields.add(new Field(TestConfigField.PARENT_ID.getName(), Arrays.asList("2")));
		fields.add(new Field(TestConfigField.DATA_STATE.getName(), Arrays.asList("0")));
		fields.add(new Field(TestConfigField.TEST_NAME.getName(), Arrays.asList("GetTestConfigByIdTest")));
		fields.add(new Field("user-02", Arrays.asList("customValue")));
		final GenericEntity entity = new GenericEntity("test-config", fields);
		testConfig.populateFields(entity);
		return testConfig;
	}
}
