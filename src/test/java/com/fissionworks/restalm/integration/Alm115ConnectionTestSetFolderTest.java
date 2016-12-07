package com.fissionworks.restalm.integration;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.TestSetFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.testlab.TestSetFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionTestSetFolderTest {
	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxx:xxxx";

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
		final TestSetFolder initialTestFolder = createMinimumTestSetFolder(name);
		initialTestFolder.setParentId(5);
		initialTestFolder.setId(1337);
		initialTestFolder.setDescription("the description");
		final TestSetFolder addedTestFolder = alm.addEntity(initialTestFolder);
		alm.deleteEntity(TestSetFolder.class, addedTestFolder.getId());
		Assert.assertNotEquals(addedTestFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestFolder.getDescription(), "the description");
		Assert.assertEquals(addedTestFolder.getName(), name);
		Assert.assertEquals(addedTestFolder.getParentId(), 5);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final TestSetFolder testFolder = new TestSetFolder();
		testFolder.setParentId(5);
		alm.addEntity(testFolder);
	}

	@Test
	public void addEntityAndDelete_withTestSetFolderMininumRequiredFieldsPopulated_shouldCreateEntity() {
		final String name = createUniqueName();
		final TestSetFolder addedTestFolder = alm.addEntity(createMinimumTestSetFolder(name));
		alm.deleteEntity(TestSetFolder.class, addedTestFolder.getId());
		Assert.assertNotEquals(addedTestFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestFolder.getDescription(), "");
		Assert.assertEquals(addedTestFolder.getName(), name);
		Assert.assertEquals(addedTestFolder.getParentId(), 0);
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestSetFolder testFolder : alm.getEntities(TestSetFolder.class,
				new RestParameters().fields(TestSetFolderField.NAME, TestSetFolderField.PARENT_ID))) {
			Assert.assertEquals(testFolder.getDescription(), "");
			Assert.assertTrue(testFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testFolder.getName().equals(""));
			Assert.assertNotEquals(testFolder.getParentId(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnTestSetFolderMatchingQuery() {
		final AlmEntityCollection<TestSetFolder> testFolderCollection = alm.getEntities(TestSetFolder.class,
				new RestParameters().queryFilter(TestSetFolderField.NAME, "GetTestSetFolderFolderById")
						.queryFilter(TestSetFolderField.PARENT_ID, "5"));
		for (final TestSetFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getName(), "GetTestSetFolderFolderById");
			Assert.assertEquals(testFolder.getParentId(), 19);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(TestSetFolder.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestFolderFieldsSpecified_shouldReturnFolderWithRelatedEntity() {
		final AlmEntityCollection<TestSetFolder> testFolderCollection = alm.getEntities(TestSetFolder.class,
				new RestParameters().queryFilter(TestSetFolderField.PARENT_ID, "16").relatedFields(
						TestSetFolderField.CONTAINS_TEST_SET_FOLDER_NAME,
						TestSetFolderField.CONTAINS_TEST_SET_FOLDER_ID,
						TestSetFolderField.CONTAINS_TEST_SET_FOLDER_PARENT_ID,
						TestSetFolderField.CONTAINS_TEST_SET_FOLDER_DESCRIPTION));
		final TestSetFolder parentTestFolder = new TestSetFolder();
		parentTestFolder.setId(16);
		parentTestFolder.setName("Get Test Set Folder");
		parentTestFolder.setParentId(4);
		parentTestFolder.setDescription("Get Test Set Folder");

		Assert.assertEquals(testFolderCollection.size(), 1);
		for (final TestSetFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getParentTestSetFolder(), parentTestFolder);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestSetFolder testFolder : alm.getEntities(TestSetFolder.class,
				new RestParameters().fields(TestSetFolderField.NAME))) {
			Assert.assertEquals(testFolder.getDescription(), "");
			Assert.assertTrue(testFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testFolder.getName().equals(""));
			Assert.assertEquals(testFolder.getParentId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnTestSetFolderMatchingQuery() {
		final AlmEntityCollection<TestSetFolder> testFolderCollection = alm.getEntities(TestSetFolder.class,
				new RestParameters().queryFilter(TestSetFolderField.NAME, "GetTestSetFolderById"));
		for (final TestSetFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getName(), "GetTestSetFolderById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnTestSetFolderStartingAtStartIndex() {
		final AlmEntityCollection<TestSetFolder> initialTestFolderCollection = alm.getEntities(TestSetFolder.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<TestSetFolder> indexTwoCollection = alm.getEntities(TestSetFolder.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestFolderCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final TestSetFolder testSetFolder : indexTwoCollection) {
			Assert.assertFalse(initialTestFolderCollection.contains(testSetFolder));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(TestSetFolder.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectTestSetFolderFolder() {
		final TestSetFolder testSetFolder = alm.getEnityById(TestSetFolder.class, 19);
		Assert.assertNotNull(testSetFolder);
		Assert.assertTrue(testSetFolder.isExactMatch(getTestSetFolder19()),
				"expected:\n" + getTestSetFolder19() + "\nbut got:\n" + testSetFolder);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final TestSetFolder testFolder = alm.getEnityById(TestSetFolder.class, 51);
		final TestSetFolder intialTestFolder = new TestSetFolder();
		intialTestFolder.populateFields(testFolder.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		testFolder.setName(newName);
		testFolder.setDescription(newDescription);
		testFolder.setParentId(testFolder.getParentId() == 50 ? 52 : 50);

		alm.updateEntity(testFolder, TestSetFolderField.NAME, TestSetFolderField.DESCRIPTION);
		final TestSetFolder updatedTestFolder = alm.getEnityById(TestSetFolder.class, 51);
		Assert.assertEquals(updatedTestFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedTestFolder.getName(), newName);
		Assert.assertEquals(updatedTestFolder.getParentId(), intialTestFolder.getParentId());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final TestSetFolder testFolder = alm.getEnityById(TestSetFolder.class, 57);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final int newParentId = testFolder.getParentId() == 50 ? 52 : 50;
		testFolder.setName(newName);
		testFolder.setDescription(newDescription);
		testFolder.setParentId(newParentId);
		alm.updateEntity(testFolder);
		final TestSetFolder updatedTestFolder = alm.getEnityById(TestSetFolder.class, 57);
		Assert.assertEquals(updatedTestFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedTestFolder.getName(), newName);
		Assert.assertEquals(updatedTestFolder.getParentId(), newParentId);
	}

	private TestSetFolder createMinimumTestSetFolder(final String name) {
		final TestSetFolder testFolder = new TestSetFolder();
		testFolder.setName(name);
		return testFolder;
	}

	private String createUniqueName() {
		return "addedTestSetFolder" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private TestSetFolder getTestSetFolder19() {
		final TestSetFolder testFolder = new TestSetFolder();
		testFolder.setId(19);
		testFolder.setName("GetTestSetFolderById");
		testFolder.setDescription("Get Test Set Folder By Id description");
		testFolder.setParentId(16);
		return testFolder;
	}
}
