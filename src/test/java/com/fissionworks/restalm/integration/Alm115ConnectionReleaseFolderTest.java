package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.management.ReleaseFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionReleaseFolderTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxx:xxx";

	private static final String USERNAME = "xx";

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
		final ReleaseFolder initialReleaseFolder = createMinimumReleaseFolder(name);
		initialReleaseFolder.setId(1337);
		initialReleaseFolder.setDescription("the description");
		final ReleaseFolder addedReleaseFolder = alm.addEntity(initialReleaseFolder);
		alm.deleteEntity(ReleaseFolder.class, addedReleaseFolder.getId());
		Assert.assertNotEquals(addedReleaseFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedReleaseFolder.getDescription(), "the description");
		Assert.assertEquals(addedReleaseFolder.getName(), name);
		Assert.assertEquals(addedReleaseFolder.getParentId(), 108);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setParentId(108);
		alm.addEntity(releaseFolder);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(releaseFolder);
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
	public void addEntityAndDelete_withReleaseFolderMininumRequiredFieldsPopulated_shouldCreateReleaseFolder() {
		final String name = createUniqueName();
		final ReleaseFolder addedReleaseFolder = alm.addEntity(createMinimumReleaseFolder(name));
		alm.deleteEntity(ReleaseFolder.class, addedReleaseFolder.getId());
		Assert.assertNotEquals(addedReleaseFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedReleaseFolder.getDescription(), "");
		Assert.assertEquals(addedReleaseFolder.getName(), name);
		Assert.assertEquals(addedReleaseFolder.getParentId(), 108);
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final ReleaseFolder releaseFolder : alm.getEntities(ReleaseFolder.class,
				new RestParameters().fields(ReleaseFolderField.NAME, ReleaseFolderField.PARENT_ID))) {
			Assert.assertEquals(releaseFolder.getDescription(), "");
			Assert.assertTrue(releaseFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(releaseFolder.getName().equals(""));
			Assert.assertNotEquals(releaseFolder.getParentId(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<ReleaseFolder> releaseFolderCollection = alm.getEntities(ReleaseFolder.class,
				new RestParameters().queryFilter(ReleaseFolderField.NAME, "GetAlmTestFolderById")
						.queryFilter(ReleaseFolderField.PARENT_ID, "1022"));
		for (final ReleaseFolder releaseFolder : releaseFolderCollection) {
			Assert.assertEquals(releaseFolder.getName(), "GetAlmTestFolderById");
			Assert.assertEquals(releaseFolder.getParentId(), 1022);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(ReleaseFolder.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentReleaseFolderFieldsSpecified_shouldReturnFolderWithRelatedEntity() {
		final AlmEntityCollection<ReleaseFolder> releaseFolderCollection = alm.getEntities(ReleaseFolder.class,
				new RestParameters().queryFilter(ReleaseFolderField.ID, "123").relatedFields(
						ReleaseFolderField.CONTAINS_FOLDER_NAME, ReleaseFolderField.CONTAINS_FOLDER_ID,
						ReleaseFolderField.CONTAINS_FOLDER_PARENT_ID, ReleaseFolderField.CONTAINS_FOLDER_DESCRIPTION));
		final ReleaseFolder parentReleaseFolder = new ReleaseFolder();
		parentReleaseFolder.setId(122);
		parentReleaseFolder.setName("get release folder");
		parentReleaseFolder.setParentId(107);
		parentReleaseFolder.setDescription("get relase folder description");

		Assert.assertEquals(releaseFolderCollection.size(), 1);
		for (final ReleaseFolder releaseFolder : releaseFolderCollection) {
			Assert.assertEquals(releaseFolder.getParentReleaseFolder(), parentReleaseFolder);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final ReleaseFolder releaseFolder : alm.getEntities(ReleaseFolder.class,
				new RestParameters().fields(ReleaseFolderField.NAME))) {
			Assert.assertEquals(releaseFolder.getDescription(), "");
			Assert.assertTrue(releaseFolder.getId() != Integer.MIN_VALUE);
			Assert.assertFalse(releaseFolder.getName().equals(""));
			Assert.assertEquals(releaseFolder.getParentId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<ReleaseFolder> releaseFolderCollection = alm.getEntities(ReleaseFolder.class,
				new RestParameters().queryFilter(ReleaseFolderField.NAME, "'get release folder by id'"));
		for (final ReleaseFolder releaseFolder : releaseFolderCollection) {
			Assert.assertEquals(releaseFolder.getName(), "get release folder by id");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnAlmTestStartingAtStartIndex() {
		final AlmEntityCollection<ReleaseFolder> initialReleaseFolderCollection = alm.getEntities(ReleaseFolder.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<ReleaseFolder> indexTwoCollection = alm.getEntities(ReleaseFolder.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialReleaseFolderCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final ReleaseFolder releaseFolder : indexTwoCollection) {
			Assert.assertFalse(initialReleaseFolderCollection.contains(releaseFolder));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(ReleaseFolder.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectReleaseFolder() {
		final ReleaseFolder releaseFolder = alm.getEnityById(ReleaseFolder.class, 123);
		Assert.assertNotNull(releaseFolder);
		Assert.assertTrue(releaseFolder.isExactMatch(getReleaseFolder123()),
				"expected:\n" + getReleaseFolder123() + "\nbut got:\n" + releaseFolder);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final ReleaseFolder releaseFolder = alm.getEnityById(ReleaseFolder.class, 141);
		final ReleaseFolder intialReleaseFolder = new ReleaseFolder();
		intialReleaseFolder.populateFields(releaseFolder.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		releaseFolder.setName(newName);
		releaseFolder.setDescription(newDescription);
		releaseFolder.setParentId(releaseFolder.getParentId() == 139 ? 140 : 139);

		alm.updateEntity(releaseFolder, ReleaseFolderField.NAME, ReleaseFolderField.DESCRIPTION);
		final ReleaseFolder updatedReleaseFolder = alm.getEnityById(ReleaseFolder.class, 141);
		Assert.assertEquals(updatedReleaseFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedReleaseFolder.getName(), newName);
		Assert.assertEquals(updatedReleaseFolder.getParentId(), intialReleaseFolder.getParentId());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final ReleaseFolder releaseFolder = alm.getEnityById(ReleaseFolder.class, 150);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final int newParentId = releaseFolder.getParentId() == 139 ? 140 : 139;
		releaseFolder.setName(newName);
		releaseFolder.setDescription(newDescription);
		releaseFolder.setParentId(newParentId);
		alm.updateEntity(releaseFolder);
		final ReleaseFolder updatedReleaseFolder = alm.getEnityById(ReleaseFolder.class, 150);
		Assert.assertEquals(updatedReleaseFolder.getDescription(), newDescription);
		Assert.assertEquals(updatedReleaseFolder.getName(), newName);
		Assert.assertEquals(updatedReleaseFolder.getParentId(), newParentId);
	}

	private ReleaseFolder createMinimumReleaseFolder(final String name) {
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setName(name);
		releaseFolder.setParentId(108);
		return releaseFolder;
	}

	private String createUniqueName() {
		return "addedReleaseFolder" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private ReleaseFolder getReleaseFolder123() {
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setId(123);
		releaseFolder.setName("get release folder by id");
		releaseFolder.setDescription("get release folder by id description");
		releaseFolder.setParentId(122);
		return releaseFolder;
	}
}
