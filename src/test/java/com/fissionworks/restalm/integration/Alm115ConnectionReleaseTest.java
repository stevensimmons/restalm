package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionReleaseTest {

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
		final Release initialRelease = createMinimumRelease(name);
		initialRelease.setId(1337);
		initialRelease.setDescription("the description");
		final Release addedRelease = alm.addEntity(initialRelease);
		alm.deleteEntity(Release.class, addedRelease.getId());
		Assert.assertNotEquals(addedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedRelease.getDescription(), "the description");
		Assert.assertEquals(addedRelease.getName(), name);
		Assert.assertEquals(addedRelease.getParentId(), 171);
		Assert.assertEquals(initialRelease.getStartDate(), addedRelease.getStartDate());
		Assert.assertEquals(initialRelease.getEndDate(), addedRelease.getEndDate());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final Release release = new Release();
		release.setParentId(171);
		release.setStartDate("1900-07-04");
		release.setEndDate("2000-01-01");
		alm.addEntity(release);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final Release release = new Release();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(release);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.contains(exception.getMessage(), "parent-id"));
			Assert.assertTrue(StringUtils.contains(exception.getMessage(), "start-date"));
			Assert.assertTrue(StringUtils.contains(exception.getMessage(), "end-date"));
			Assert.assertTrue(StringUtils.contains(exception.getMessage(), "name"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withReleaseMininumRequiredFieldsPopulated_shouldCreateRelease() {
		final String name = createUniqueName();
		final Release addedRelease = alm.addEntity(createMinimumRelease(name));
		alm.deleteEntity(Release.class, addedRelease.getId());
		Assert.assertNotEquals(addedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedRelease.getDescription(), "");
		Assert.assertEquals(addedRelease.getName(), name);
		Assert.assertEquals(addedRelease.getParentId(), 171);
		Assert.assertEquals(addedRelease.getStartDate(), "1900-07-04");
		Assert.assertEquals(addedRelease.getEndDate(), "2000-01-01");
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Release release : alm.getEntities(Release.class,
				new RestParameters().fields(ReleaseField.NAME, ReleaseField.PARENT_ID))) {
			Assert.assertNotEquals(release.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(release.getDescription(), "");
			Assert.assertNotEquals(release.getName(), "");
			Assert.assertNotEquals(release.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(release.getStartDate(), "292269055-12-02");
			Assert.assertEquals(release.getEndDate(), "292269055-12-02");
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<Release> releaseCollection = alm.getEntities(Release.class, new RestParameters()
				.queryFilter(ReleaseField.NAME, "'Get Release By Id'").queryFilter(ReleaseField.PARENT_ID, "165"));
		for (final Release release : releaseCollection) {
			Assert.assertEquals(release.getName(), "Get Release By Id");
			Assert.assertEquals(release.getParentId(), 165);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(Release.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentReleaseFolderFieldsSpecified_shouldReturnReleaseWithRelatedEntity() {
		final AlmEntityCollection<Release> releaseCollection = alm.getEntities(Release.class,
				new RestParameters().queryFilter(ReleaseField.ID, "1006").relatedFields(ReleaseFolderField.NAME,
						ReleaseFolderField.DESCRIPTION, ReleaseFolderField.PARENT_ID));
		final ReleaseFolder parentReleaseFolder = new ReleaseFolder();
		parentReleaseFolder.setId(165);
		parentReleaseFolder.setName("Get Release");
		parentReleaseFolder.setParentId(102);
		parentReleaseFolder.setDescription("Get Release Folder Description");

		Assert.assertEquals(releaseCollection.size(), 1);
		for (final Release release : releaseCollection) {
			Assert.assertEquals(release.getParentReleaseFolder(), parentReleaseFolder);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Release release : alm.getEntities(Release.class, new RestParameters().fields(ReleaseField.NAME))) {
			Assert.assertNotEquals(release.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(release.getDescription(), "");
			Assert.assertNotEquals(release.getName(), "");
			Assert.assertEquals(release.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(release.getStartDate(), "292269055-12-02");
			Assert.assertEquals(release.getEndDate(), "292269055-12-02");

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnReleaseMatchingQuery() {
		final AlmEntityCollection<Release> releaseCollection = alm.getEntities(Release.class,
				new RestParameters().queryFilter(ReleaseField.NAME, "'Get Release By Id'"));
		for (final Release release : releaseCollection) {
			Assert.assertEquals(release.getName(), "Get Release By Id");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnReleaseStartingAtStartIndex() {
		final AlmEntityCollection<Release> initialReleaseCollection = alm.getEntities(Release.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<Release> indexTwoCollection = alm.getEntities(Release.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialReleaseCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final Release release : indexTwoCollection) {
			Assert.assertFalse(initialReleaseCollection.contains(release));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(Release.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectRelease() {
		final Release release = alm.getEnityById(Release.class, 1006);
		Assert.assertNotNull(release);
		Assert.assertTrue(release.isExactMatch(getRelease1006()),
				"expected:\n" + getRelease1006() + "\nbut got:\n" + release);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final Release release = alm.getEnityById(Release.class, 1023);
		final Release intialRelease = new Release();
		intialRelease.populateFields(release.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		release.setName(newName);
		release.setDescription(newDescription);
		release.setParentId(release.getParentId() == 178 ? 180 : 178);
		release.setStartDate(release.getStartDate().equals("2014-09-07") ? "2014-09-08" : "2014-09-07");
		release.setEndDate(release.getEndDate().equals("2014-09-10") ? "2014-09-09" : "2014-09-10");

		alm.updateEntity(release, ReleaseField.NAME, ReleaseField.DESCRIPTION);
		final Release updatedRelease = alm.getEnityById(Release.class, 1023);
		Assert.assertNotEquals(updatedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(updatedRelease.getDescription(), newDescription);
		Assert.assertEquals(updatedRelease.getName(), newName);
		Assert.assertEquals(updatedRelease.getParentId(), intialRelease.getParentId());
		Assert.assertEquals(updatedRelease.getStartDate(), intialRelease.getStartDate());
		Assert.assertEquals(updatedRelease.getEndDate(), intialRelease.getEndDate());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final Release release = alm.getEnityById(Release.class, 1042);
		final Release intialRelease = new Release();
		intialRelease.populateFields(release.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		release.setName(newName);
		release.setDescription(newDescription);
		release.setParentId(release.getParentId() == 178 ? 180 : 178);
		release.setStartDate(release.getStartDate().equals("2014-09-07") ? "2014-09-08" : "2014-09-07");
		release.setEndDate(release.getEndDate().equals("2014-09-10") ? "2014-09-09" : "2014-09-10");

		alm.updateEntity(release);
		final Release updatedRelease = alm.getEnityById(Release.class, 1042);
		Assert.assertNotEquals(updatedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(updatedRelease.getDescription(), newDescription);
		Assert.assertEquals(updatedRelease.getName(), newName);
		Assert.assertEquals(updatedRelease.getParentId(), release.getParentId());
		Assert.assertEquals(updatedRelease.getStartDate(), release.getStartDate());
		Assert.assertEquals(updatedRelease.getEndDate(), release.getEndDate());
	}

	private Release createMinimumRelease(final String name) {
		final Release release = new Release();
		release.setName(name);
		release.setParentId(171);
		release.setStartDate("1900-07-04");
		release.setEndDate("2000-01-01");
		return release;
	}

	private String createUniqueName() {
		return "addedRelease" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private Release getRelease1006() {
		final Release release = new Release();
		release.setId(1006);
		release.setName("Get Release By Id");
		release.setDescription("Get Release By Id");
		release.setParentId(165);
		release.setStartDate("2014-09-04");
		release.setEndDate("2014-09-06");
		return release;
	}
}
