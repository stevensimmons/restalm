package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.ReleaseCycleField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionReleaseCycleTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://oaoracle:8989";

	private static final String USERNAME = "svc";

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
		final ReleaseCycle initialReleaseCycle = createMinimumReleaseCycle(name);
		initialReleaseCycle.setId(1337);
		initialReleaseCycle.setDescription("the description");
		final ReleaseCycle addedReleaseCycle = alm.addEntity(initialReleaseCycle);
		alm.deleteEntity(ReleaseCycle.class, addedReleaseCycle.getId());
		Assert.assertNotEquals(addedReleaseCycle.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedReleaseCycle.getDescription(), "the description");
		Assert.assertEquals(addedReleaseCycle.getName(), name);
		Assert.assertEquals(addedReleaseCycle.getParentId(), 1051);
		Assert.assertEquals(initialReleaseCycle.getStartDate(), addedReleaseCycle.getStartDate());
		Assert.assertEquals(initialReleaseCycle.getEndDate(), addedReleaseCycle.getEndDate());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setParentId(171);
		releaseCycle.setStartDate("1900-07-04");
		releaseCycle.setEndDate("2000-01-01");
		alm.addEntity(releaseCycle);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(releaseCycle);
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
	public void addEntityAndDelete_withReleaseCycleMininumRequiredFieldsPopulated_shouldCreateReleaseCycle() {
		final String name = createUniqueName();
		final ReleaseCycle addedReleaseCycle = alm.addEntity(createMinimumReleaseCycle(name));
		alm.deleteEntity(ReleaseCycle.class, addedReleaseCycle.getId());
		Assert.assertNotEquals(addedReleaseCycle.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedReleaseCycle.getDescription(), "");
		Assert.assertEquals(addedReleaseCycle.getName(), name);
		Assert.assertEquals(addedReleaseCycle.getParentId(), 1051);
		Assert.assertEquals(addedReleaseCycle.getStartDate(), "2014-09-02");
		Assert.assertEquals(addedReleaseCycle.getEndDate(), "2014-09-03");
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final ReleaseCycle releaseCycle : alm.getEntities(ReleaseCycle.class,
				new RestParameters().fields(ReleaseCycleField.NAME, ReleaseCycleField.PARENT_ID))) {
			Assert.assertNotEquals(releaseCycle.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(releaseCycle.getDescription(), "");
			Assert.assertNotEquals(releaseCycle.getName(), "");
			Assert.assertNotEquals(releaseCycle.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(releaseCycle.getStartDate(), "292269055-12-02");
			Assert.assertEquals(releaseCycle.getEndDate(), "292269055-12-02");
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<ReleaseCycle> releaseCycleCollection = alm.getEntities(ReleaseCycle.class,
				new RestParameters().queryFilter(ReleaseField.NAME, "getCycleByID").queryFilter(ReleaseField.PARENT_ID,
						"1001"));
		for (final ReleaseCycle releaseCycle : releaseCycleCollection) {
			Assert.assertEquals(releaseCycle.getName(), "getCycleById");
			Assert.assertEquals(releaseCycle.getParentId(), 1001);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(ReleaseCycle.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentReleaseFieldsSpecified_shouldReturnReleaseWithRelatedEntity() {
		final AlmEntityCollection<ReleaseCycle> releaseCycleCollection = alm.getEntities(ReleaseCycle.class,
				new RestParameters().queryFilter(ReleaseCycleField.ID, "1001").relatedFields(ReleaseField.NAME,
						ReleaseField.DESCRIPTION, ReleaseField.PARENT_ID, ReleaseField.END_DATE,
						ReleaseField.START_DATE));
		final Release parentRelease = new Release();
		parentRelease.setId(1001);
		parentRelease.setName("Get Cycle");
		parentRelease.setParentId(103);
		parentRelease.setDescription("Get Cycle Release Description");
		parentRelease.setEndDate("2014-08-08");
		parentRelease.setEndDate("2014-08-07");

		Assert.assertEquals(releaseCycleCollection.size(), 1);
		for (final ReleaseCycle releaseCycle : releaseCycleCollection) {
			Assert.assertEquals(releaseCycle.getParentRelease(), parentRelease);
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final ReleaseCycle releaseCycle : alm.getEntities(ReleaseCycle.class,
				new RestParameters().fields(ReleaseCycleField.NAME))) {
			Assert.assertNotEquals(releaseCycle.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(releaseCycle.getDescription(), "");
			Assert.assertNotEquals(releaseCycle.getName(), "");
			Assert.assertEquals(releaseCycle.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(releaseCycle.getStartDate(), "292269055-12-02");
			Assert.assertEquals(releaseCycle.getEndDate(), "292269055-12-02");

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnReleaseMatchingQuery() {
		final AlmEntityCollection<ReleaseCycle> releaseCycleCollection = alm.getEntities(ReleaseCycle.class,
				new RestParameters().queryFilter(ReleaseCycleField.NAME, "getCycleById"));
		for (final ReleaseCycle releaseCycle : releaseCycleCollection) {
			Assert.assertEquals(releaseCycle.getName(), "getCycleById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnReleaseStartingAtStartIndex() {
		final AlmEntityCollection<ReleaseCycle> initialReleaseCycleCollection = alm.getEntities(ReleaseCycle.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<ReleaseCycle> indexTwoCollection = alm.getEntities(ReleaseCycle.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialReleaseCycleCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final ReleaseCycle releaseCycle : indexTwoCollection) {
			Assert.assertFalse(initialReleaseCycleCollection.contains(releaseCycle));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(ReleaseCycle.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectRelease() {
		final ReleaseCycle releaseCycle = alm.getEnityById(ReleaseCycle.class, 1001);
		Assert.assertNotNull(releaseCycle);
		Assert.assertTrue(releaseCycle.isExactMatch(getReleaseCycle1001()),
				"expected:\n" + getReleaseCycle1001() + "\nbut got:\n" + releaseCycle);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final ReleaseCycle releaseCycle = alm.getEnityById(ReleaseCycle.class, 1017);
		final ReleaseCycle intialReleaseCycle = new ReleaseCycle();
		intialReleaseCycle.populateFields(releaseCycle.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		releaseCycle.setName(newName);
		releaseCycle.setDescription(newDescription);
		releaseCycle.setParentId(releaseCycle.getParentId() == 1053 ? 1054 : 1053);
		releaseCycle.setStartDate(releaseCycle.getStartDate().equals("2014-09-01") ? "2014-09-02" : "2014-09-01");
		releaseCycle.setEndDate(releaseCycle.getEndDate().equals("2014-09-30") ? "2014-09-29" : "2014-09-30");

		alm.updateEntity(releaseCycle, ReleaseCycleField.NAME, ReleaseCycleField.DESCRIPTION);
		final ReleaseCycle updatedRelease = alm.getEnityById(ReleaseCycle.class, 1017);
		Assert.assertNotEquals(updatedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(updatedRelease.getDescription(), newDescription);
		Assert.assertEquals(updatedRelease.getName(), newName);
		Assert.assertEquals(updatedRelease.getParentId(), intialReleaseCycle.getParentId());
		Assert.assertEquals(updatedRelease.getStartDate(), intialReleaseCycle.getStartDate());
		Assert.assertEquals(updatedRelease.getEndDate(), intialReleaseCycle.getEndDate());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final ReleaseCycle releaseCycle = alm.getEnityById(ReleaseCycle.class, 1018);
		final ReleaseCycle intialReleaseCycle = new ReleaseCycle();
		intialReleaseCycle.populateFields(releaseCycle.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		releaseCycle.setName(newName);
		releaseCycle.setDescription(newDescription);
		releaseCycle.setParentId(releaseCycle.getParentId() == 1053 ? 1054 : 1053);
		releaseCycle.setStartDate(releaseCycle.getStartDate().equals("2014-09-01") ? "2014-09-02" : "2014-09-01");
		releaseCycle.setEndDate(releaseCycle.getEndDate().equals("2014-09-30") ? "2014-09-29" : "2014-09-30");

		alm.updateEntity(releaseCycle);
		final ReleaseCycle updatedRelease = alm.getEnityById(ReleaseCycle.class, 1018);
		Assert.assertNotEquals(updatedRelease.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(updatedRelease.getDescription(), newDescription);
		Assert.assertEquals(updatedRelease.getName(), newName);
		Assert.assertEquals(updatedRelease.getParentId(), releaseCycle.getParentId());
		Assert.assertEquals(updatedRelease.getStartDate(), releaseCycle.getStartDate());
		Assert.assertEquals(updatedRelease.getEndDate(), releaseCycle.getEndDate());
	}

	private ReleaseCycle createMinimumReleaseCycle(final String name) {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setName(name);
		releaseCycle.setParentId(1051);
		releaseCycle.setStartDate("2014-09-02");
		releaseCycle.setEndDate("2014-09-03");
		return releaseCycle;
	}

	private String createUniqueName() {
		return "addedReleaseCycle" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private ReleaseCycle getReleaseCycle1001() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setId(1001);
		releaseCycle.setName("getCycleById");
		releaseCycle.setDescription("get cycle by id description");
		releaseCycle.setParentId(1001);
		releaseCycle.setStartDate("2014-08-07");
		releaseCycle.setEndDate("2014-08-08");
		return releaseCycle;
	}
}
