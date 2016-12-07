package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.testplan.TestFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionTestFolderTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxx:xxxx";

	private static final String USERNAME = "xxx";

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
		final TestFolder initialTestFolder = createMinimumAlmTestFolder(name);
		initialTestFolder.setId(1337);
		initialTestFolder.setDescription("the description");
		final TestFolder addedTestFolder = alm.addEntity(initialTestFolder);
		alm.deleteEntity(TestFolder.class, addedTestFolder.getId());
		Assert.assertNotEquals(addedTestFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestFolder.getDescription(), "the description");
		Assert.assertEquals(addedTestFolder.getName(), name);
		Assert.assertEquals(addedTestFolder.getParentId(), 1010);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final TestFolder testFolder = new TestFolder();
		testFolder.setParentId(1004);
		alm.addEntity(testFolder);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final TestFolder testFolder = new TestFolder();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(testFolder);
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
	public void addEntityAndDelete_withAlmTestMininumRequiredFieldsPopulated_shouldCreateAlmTest() {
		final String name = createUniqueName();
		final TestFolder addedTestFolder = alm.addEntity(createMinimumAlmTestFolder(name));
		alm.deleteEntity(TestFolder.class, addedTestFolder.getId());
		Assert.assertNotEquals(addedTestFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTestFolder.getDescription(), "");
		Assert.assertEquals(addedTestFolder.getName(), name);
		Assert.assertEquals(addedTestFolder.getParentId(), 1010);
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestFolder testFolder : alm.getEntities(TestFolder.class,
				new RestParameters().fields(TestFolderField.NAME, TestFolderField.PARENT_ID))) {
			Assert.assertEquals(testFolder.getDescription(), "");
			Assert.assertTrue(testFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testFolder.getName().equals(""));
			Assert.assertNotEquals(testFolder.getParentId(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<TestFolder> testFolderCollection = alm.getEntities(TestFolder.class,
				new RestParameters().queryFilter(TestFolderField.NAME, "GetAlmTestFolderById")
						.queryFilter(TestFolderField.PARENT_ID, "1022"));
		for (final TestFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getName(), "GetAlmTestFolderById");
			Assert.assertEquals(testFolder.getParentId(), 1022);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(TestFolder.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestFolderFieldsSpecified_shouldReturnFolderWithRelatedEntity() {
		final AlmEntityCollection<TestFolder> testFolderCollection = alm.getEntities(TestFolder.class,
				new RestParameters().queryFilter(TestFolderField.PARENT_ID, "1022").relatedFields(
						TestFolderField.CONTAINS_FOLDER_NAME, TestFolderField.CONTAINS_FOLDER_ID,
						TestFolderField.CONTAINS_FOLDER_PARENT_ID, TestFolderField.CONTAINS_FOLDER_DESCRIPTION));
		final TestFolder parentTestFolder = new TestFolder();
		parentTestFolder.setId(1022);
		parentTestFolder.setName("GetAlmTestFolder");
		parentTestFolder.setParentId(1009);
		parentTestFolder.setDescription("Get Alm Test Folder");

		Assert.assertEquals(testFolderCollection.size(), 1);
		for (final TestFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getParentTestFolder(), parentTestFolder);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final TestFolder testFolder : alm.getEntities(TestFolder.class,
				new RestParameters().fields(TestFolderField.NAME))) {
			Assert.assertEquals(testFolder.getDescription(), "");
			Assert.assertTrue(testFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(testFolder.getName().equals(""));
			Assert.assertEquals(testFolder.getParentId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<TestFolder> testFolderCollection = alm.getEntities(TestFolder.class,
				new RestParameters().queryFilter(TestFolderField.NAME, "GetAlmTestFolderById"));
		for (final TestFolder testFolder : testFolderCollection) {
			Assert.assertEquals(testFolder.getName(), "GetAlmTestFolderById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnAlmTestStartingAtStartIndex() {
		final AlmEntityCollection<TestFolder> initialTestFolderCollection = alm.getEntities(TestFolder.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<TestFolder> indexTwoCollection = alm.getEntities(TestFolder.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestFolderCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final TestFolder test : indexTwoCollection) {
			Assert.assertFalse(initialTestFolderCollection.contains(test));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(TestFolder.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectAlmTestFolder() {
		final TestFolder almTestFolder = alm.getEnityById(TestFolder.class, 1024);
		Assert.assertNotNull(almTestFolder);
		Assert.assertTrue(almTestFolder.isExactMatch(getAlmTestFolder1024()),
				"expected:\n" + getAlmTestFolder1024() + "\nbut got:\n" + almTestFolder);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final TestFolder testFolder = alm.getEnityById(TestFolder.class, 1041);
		final TestFolder intialTestFolder = new TestFolder();
		intialTestFolder.populateFields(testFolder.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		testFolder.setName(newName);
		testFolder.setDescription(newDescription);
		testFolder.setParentId(testFolder.getParentId() == 1039 ? 1040 : 1039);

		alm.updateEntity(testFolder, TestFolderField.NAME, TestFolderField.DESCRIPTION);
		final TestFolder updatedTestFolder = alm.getEnityById(TestFolder.class, 1041);
		Assert.assertEquals(updatedTestFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedTestFolder.getName(), newName);
		Assert.assertEquals(updatedTestFolder.getParentId(), intialTestFolder.getParentId());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final TestFolder testFolder = alm.getEnityById(TestFolder.class, 1044);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final int newParentId = testFolder.getParentId() == 1039 ? 1040 : 1039;
		testFolder.setName(newName);
		testFolder.setDescription(newDescription);
		testFolder.setParentId(newParentId);
		alm.updateEntity(testFolder);
		final TestFolder updatedTestFolder = alm.getEnityById(TestFolder.class, 1044);
		Assert.assertEquals(updatedTestFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedTestFolder.getName(), newName);
		Assert.assertEquals(updatedTestFolder.getParentId(), newParentId);
	}

	private TestFolder createMinimumAlmTestFolder(final String name) {
		final TestFolder testFolder = new TestFolder();
		testFolder.setName(name);
		testFolder.setParentId(1010);
		return testFolder;
	}

	private String createUniqueName() {
		return "addedTestFolder" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private TestFolder getAlmTestFolder1024() {
		final TestFolder testFolder = new TestFolder();
		testFolder.setId(1024);
		testFolder.setName("GetAlmTestFolderById");
		testFolder.setDescription("get alm test folder by id description");
		testFolder.setParentId(1022);
		return testFolder;
	}
}
