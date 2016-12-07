package com.fissionworks.restalm.integration;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.TestSetField;
import com.fissionworks.restalm.constants.field.TestSetFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.testlab.TestSet;
import com.fissionworks.restalm.model.entity.testlab.TestSetFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionTestSetTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxx:xxxx";

	private static final String USERNAME = "xxxxx";

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
		final TestSet initialTestSet = createMinimumTestSet(name);
		initialTestSet.setParentId(74);
		initialTestSet.setId(1337);
		initialTestSet.setDescription("the description");
		final TestSet addedTestSet = alm.addEntity(initialTestSet);
		alm.deleteEntity(TestSet.class, addedTestSet.getId());
		Assert.assertNotEquals(addedTestSet.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestSet.getDescription(), "the description");
		Assert.assertEquals(addedTestSet.getName(), name);
		Assert.assertEquals(addedTestSet.getParentId(), 74);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		alm.addEntity(new TestSet());
	}

	@Test
	public void addEntityAndDelete_withTestSetMininumRequiredFieldsPopulated_shouldCreateEntity() {
		final String name = createUniqueName();
		final TestSet addedTestSet = alm.addEntity(createMinimumTestSet(name));
		alm.deleteEntity(TestSet.class, addedTestSet.getId());
		Assert.assertNotEquals(addedTestSet.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestSet.getDescription(), "");
		Assert.assertEquals(addedTestSet.getName(), name);
		Assert.assertEquals(addedTestSet.getParentId(), -2);
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestSet testSet : alm.getEntities(TestSet.class,
				new RestParameters().fields(TestSetField.NAME, TestSetField.PARENT_ID))) {
			Assert.assertEquals(testSet.getDescription(), "");
			Assert.assertTrue(testSet.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testSet.getName().equals(""));
			Assert.assertNotEquals(testSet.getParentId(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnTestSetFolderMatchingQuery() {
		final AlmEntityCollection<TestSet> testSetCollection = alm.getEntities(TestSet.class, new RestParameters()
				.queryFilter(TestSetField.NAME, "GetTestSetById").queryFilter(TestSetField.PARENT_ID, "19"));
		for (final TestSet testSet : testSetCollection) {
			Assert.assertEquals(testSet.getName(), "GetTestSetFolderFolderById");
			Assert.assertEquals(testSet.getParentId(), 19);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(TestSet.class, new RestParameters().pageSize(2)).size(), 2);
	}

	@Test
	public void getEntities_withParentTestSetFolderFieldsSpecified_shouldReturnTestSetWithRelatedEntity() {
		final AlmEntityCollection<TestSet> testSetCollection = alm.getEntities(TestSet.class,
				new RestParameters().queryFilter(TestSetField.PARENT_ID, "71").relatedFields(TestSetFolderField.NAME,
						TestSetFolderField.ID, TestSetFolderField.PARENT_ID, TestSetFolderField.DESCRIPTION));
		final TestSetFolder parentTestFolder = new TestSetFolder();
		parentTestFolder.setId(71);
		parentTestFolder.setName("Get Test Set");
		parentTestFolder.setParentId(70);
		parentTestFolder.setDescription("Get Test Set folder description");

		Assert.assertEquals(testSetCollection.size(), 1);
		for (final TestSet testSet : testSetCollection) {
			Assert.assertEquals(testSet.getParentTestSetFolder(), parentTestFolder);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestSet testSet : alm.getEntities(TestSet.class, new RestParameters().fields(TestSetField.NAME))) {
			Assert.assertEquals(testSet.getDescription(), "");
			Assert.assertTrue(testSet.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testSet.getName().equals(""));
			Assert.assertEquals(testSet.getParentId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnTestSetsMatchingQuery() {
		final AlmEntityCollection<TestSet> testSetCollection = alm.getEntities(TestSet.class,
				new RestParameters().queryFilter(TestSetField.NAME, "getTestSetById"));
		for (final TestSet testSet : testSetCollection) {
			Assert.assertEquals(testSet.getName(), "getTestSetById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnTestSetStartingAtStartIndex() {
		final AlmEntityCollection<TestSet> initialTestSetCollection = alm.getEntities(TestSet.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<TestSet> indexTwoCollection = alm.getEntities(TestSet.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestSetCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final TestSet testSet : indexTwoCollection) {
			Assert.assertFalse(initialTestSetCollection.contains(testSet));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(TestSet.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectTestSet() {
		final TestSet testSet = alm.getEnityById(TestSet.class, 3);
		Assert.assertNotNull(testSet);
		Assert.assertTrue(testSet.isExactMatch(getTestSet3()),
				"expected:\n" + getTestSet3() + "\nbut got:\n" + testSet);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final TestSet testSet = alm.getEnityById(TestSet.class, 45);
		final TestSet intialTestSet = new TestSet();
		intialTestSet.populateFields(testSet.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		testSet.setName(newName);
		testSet.setDescription(newDescription);
		testSet.setParentId(77);

		alm.updateEntity(testSet, TestSetField.NAME, TestSetField.DESCRIPTION);
		final TestSet updatedTestSet = alm.getEnityById(TestSet.class, 45);
		Assert.assertEquals(updatedTestSet.getDescription(), newDescription);
		Assert.assertEquals(updatedTestSet.getName(), newName);
		Assert.assertEquals(updatedTestSet.getParentId(), intialTestSet.getParentId());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final TestSet testSet = alm.getEnityById(TestSet.class, 48);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final int newParentId = testSet.getParentId() == 76 ? 77 : 76;
		testSet.setName(newName);
		testSet.setDescription(newDescription);
		testSet.setParentId(newParentId);
		alm.updateEntity(testSet);
		final TestSet updatedTestSet = alm.getEnityById(TestSet.class, 48);
		Assert.assertEquals(updatedTestSet.getDescription(), newDescription);
		Assert.assertEquals(updatedTestSet.getName(), newName);
		Assert.assertEquals(updatedTestSet.getParentId(), newParentId);
	}

	private TestSet createMinimumTestSet(final String name) {
		final TestSet testSet = new TestSet();
		testSet.setName(name);
		return testSet;
	}

	private String createUniqueName() {
		return "addedTestSet" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private TestSet getTestSet3() {
		final TestSet testSet = new TestSet();
		testSet.setId(3);
		testSet.setName("getTestSetById");
		testSet.setDescription("get test set by id description");
		testSet.setParentId(71);
		return testSet;
	}
}
