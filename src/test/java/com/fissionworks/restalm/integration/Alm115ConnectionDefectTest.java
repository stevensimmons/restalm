package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.DefectField;
import com.fissionworks.restalm.constants.field.ReleaseCycleField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.defects.Defect;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionDefectTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxx:xxx";

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
		final Defect initialDefect = createMinimumDefect();
		initialDefect.setAssignedTo("svc");
		initialDefect.setClosingDate("2014-09-06");
		initialDefect.setComments("some comments");
		initialDefect.setDescription("a description");
		initialDefect.setDetectedInReleaseCycleId(1001);
		initialDefect.setDetectedInReleaseId(1001);
		initialDefect.setId(1);
		initialDefect.setModified("2014-09-05 13:14:15");
		initialDefect.setStatus("Closed");
		final Defect addedDefect = alm.addEntity(initialDefect);
		alm.deleteEntity(Defect.class, addedDefect.getId());
		Assert.assertEquals(addedDefect.getAssignedTo(), initialDefect.getAssignedTo());
		Assert.assertEquals(addedDefect.getClosingDate(), initialDefect.getClosingDate());
		Assert.assertEquals(addedDefect.getComments(), initialDefect.getComments());
		Assert.assertEquals(addedDefect.getDescription(), initialDefect.getDescription());
		Assert.assertEquals(addedDefect.getDetectedBy(), initialDefect.getDetectedBy());
		Assert.assertEquals(addedDefect.getDetectedInReleaseCycleId(), initialDefect.getDetectedInReleaseCycleId());
		Assert.assertEquals(addedDefect.getDetectedInReleaseId(), initialDefect.getDetectedInReleaseId());
		Assert.assertEquals(addedDefect.getDetectedOnDate(), initialDefect.getDetectedOnDate());
		Assert.assertNotEquals(addedDefect.getId(), initialDefect.getId());
		Assert.assertNotEquals(addedDefect.getModified(), initialDefect.getModified());
		Assert.assertEquals(addedDefect.getSeverity(), initialDefect.getSeverity());
		Assert.assertEquals(addedDefect.getStatus(), initialDefect.getStatus());
		Assert.assertEquals(addedDefect.getSummary(), initialDefect.getSummary());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final Defect defect = new Defect();
		defect.setSummary("the summary");
		defect.setSeverity("1-Low");
		defect.setDetectedOnDate("2014-01-02");
		alm.addEntity(defect);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final Defect defect = new Defect();
		boolean exceptionThrown = false;
		defect.setSummary("the summary");
		defect.setDetectedOnDate("2014-01-02");
		try {
			alm.addEntity(defect);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: detected-by,severity")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: severity,detected-by"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withMininumRequiredFieldsPopulated_shouldCreateDefect() {
		final Defect initialDefect = createMinimumDefect();
		final Defect addedDefect = alm.addEntity(initialDefect);
		alm.deleteEntity(Defect.class, addedDefect.getId());
		Assert.assertEquals(addedDefect.getAssignedTo(), initialDefect.getAssignedTo());
		Assert.assertEquals(addedDefect.getClosingDate(), initialDefect.getClosingDate());
		Assert.assertEquals(addedDefect.getComments(), initialDefect.getComments());
		Assert.assertEquals(addedDefect.getDescription(), initialDefect.getDescription());
		Assert.assertEquals(addedDefect.getDetectedBy(), initialDefect.getDetectedBy());
		Assert.assertEquals(addedDefect.getDetectedInReleaseCycleId(), initialDefect.getDetectedInReleaseCycleId());
		Assert.assertEquals(addedDefect.getDetectedInReleaseId(), initialDefect.getDetectedInReleaseId());
		Assert.assertEquals(addedDefect.getDetectedOnDate(), initialDefect.getDetectedOnDate());
		Assert.assertNotEquals(addedDefect.getId(), initialDefect.getId());
		Assert.assertNotEquals(addedDefect.getModified(), initialDefect.getModified());
		Assert.assertEquals(addedDefect.getSeverity(), initialDefect.getSeverity());
		Assert.assertEquals(addedDefect.getStatus(), initialDefect.getStatus());
		Assert.assertEquals(addedDefect.getSummary(), initialDefect.getSummary());
	}

	@Test
	public void getEntities_withCrossQuerySet_shouldReturnDefectsThatMatchQuery() {
		final AlmEntityCollection<Defect> defectCollection = alm.getEntities(Defect.class,
				new RestParameters().crossQueryFilter(ReleaseField.DEFECT_DETECTED_RELEASE_ID, "1001")
						.crossQueryFilter(ReleaseCycleField.DEFECT_DETECTED_CYCLE_ID, "1001"));
		Assert.assertTrue(defectCollection.size() > 0);
		for (final Defect defect : defectCollection) {
			Assert.assertEquals(defect.getDetectedInReleaseCycleId(), 1001);
			Assert.assertEquals(defect.getDetectedInReleaseId(), 1001);
		}
	}

	@Test
	public void getEntities_withDetectedInReleaseCycleFieldsSpecified_shouldReturnDefectWithRelatedEntity() {
		final AlmEntityCollection<Defect> defects = alm.getEntities(Defect.class,
				new RestParameters().queryFilter(DefectField.ID, "19").relatedFields(
						ReleaseCycleField.DEFECT_DETECTED_CYCLE_DESCRIPTION,
						ReleaseCycleField.DEFECT_DETECTED_CYCLE_END_DATE, ReleaseCycleField.DEFECT_DETECTED_CYCLE_NAME,
						ReleaseCycleField.DEFECT_DETECTED_CYCLE_PARENT_ID,
						ReleaseCycleField.DEFECT_DETECTED_CYCLE_START_DATE));
		final ReleaseCycle detectedInReleaseCycle = new ReleaseCycle();
		detectedInReleaseCycle.setDescription("get cycle by id description");
		detectedInReleaseCycle.setEndDate("2014-08-08");
		detectedInReleaseCycle.setId(1001);
		detectedInReleaseCycle.setName("getCycleById");
		detectedInReleaseCycle.setParentId(1001);
		detectedInReleaseCycle.setStartDate("2014-08-07");
		for (final Defect defect : defects) {
			Assert.assertTrue(detectedInReleaseCycle.isExactMatch(defect.getDetectedInReleaseCycle()));
		}
	}

	@Test
	public void getEntities_withDetectedInReleaseFieldsSpecified_shouldReturnDefectWithRelatedEntity() {
		final AlmEntityCollection<Defect> defects = alm.getEntities(Defect.class,
				new RestParameters().queryFilter(DefectField.ID, "19").relatedFields(
						ReleaseField.DEFECT_DETECTED_RELEASE_DESCRIPTION, ReleaseField.DEFECT_DETECTED_RELEASE_END_DATE,
						ReleaseField.DEFECT_DETECTED_RELEASE_NAME, ReleaseField.DEFECT_DETECTED_RELEASE_PARENT_ID,
						ReleaseField.DEFECT_DETECTED_RELEASE_START_DATE));
		final Release detectedInRelease = new Release();
		detectedInRelease.setDescription("Get Cycle Release Description");
		detectedInRelease.setEndDate("2014-08-08");
		detectedInRelease.setId(1001);
		detectedInRelease.setName("Get Cycle");
		detectedInRelease.setParentId(103);
		detectedInRelease.setStartDate("2014-08-07");
		for (final Defect defect : defects) {
			Assert.assertTrue(detectedInRelease.isExactMatch(defect.getDetectedInRelease()));
		}
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Defect defect : alm.getEntities(Defect.class,
				new RestParameters().fields(DefectField.DETECTED_BY, DefectField.DETECTED_ON_DATE))) {
			Assert.assertEquals(defect.getAssignedTo(), "");
			Assert.assertEquals(defect.getClosingDate(), "292269055-12-02");
			Assert.assertEquals(defect.getComments(), "");
			Assert.assertEquals(defect.getDescription(), "");
			Assert.assertNotEquals(defect.getDetectedBy(), "");
			Assert.assertEquals(defect.getDetectedInReleaseCycleId(), Integer.MIN_VALUE);
			Assert.assertEquals(defect.getDetectedInReleaseId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(defect.getDetectedOnDate(), "292269055-12-02");
			Assert.assertNotEquals(defect.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(defect.getModified(), "292269055-12-02 11:47:04");
			Assert.assertEquals(defect.getSeverity(), "");
			Assert.assertEquals(defect.getStatus(), "");
			Assert.assertEquals(defect.getSummary(), "");
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnDefectsMatchingQuery() {
		final AlmEntityCollection<Defect> defectCollection = alm.getEntities(Defect.class, new RestParameters()
				.queryFilter(DefectField.DETECTED_IN_CYCLE_ID, "1001").queryFilter(DefectField.SEVERITY, "2-Medium"));
		Assert.assertTrue(defectCollection.size() > 0);
		for (final Defect defect : defectCollection) {
			Assert.assertEquals(defect.getDetectedInReleaseCycleId(), 1001);
			Assert.assertEquals(defect.getSeverity(), "2-Medium");
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(Defect.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withQueryAndCrossQuerySet_shouldReturnDefectsThatMatchQuery() {
		final AlmEntityCollection<Defect> defectCollection = alm.getEntities(Defect.class,
				new RestParameters().crossQueryFilter(ReleaseField.DEFECT_DETECTED_RELEASE_NAME, "'Get Cycle'")
						.queryFilter(DefectField.SEVERITY, "5-Urgent"));
		Assert.assertTrue(defectCollection.size() > 0);
		for (final Defect defect : defectCollection) {
			Assert.assertEquals(defect.getDetectedInReleaseId(), 1001);
			Assert.assertEquals(defect.getSeverity(), "5-Urgent");
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Defect defect : alm.getEntities(Defect.class,
				new RestParameters().fields(DefectField.DETECTED_BY))) {
			Assert.assertEquals(defect.getAssignedTo(), "");
			Assert.assertEquals(defect.getClosingDate(), "292269055-12-02");
			Assert.assertEquals(defect.getComments(), "");
			Assert.assertEquals(defect.getDescription(), "");
			Assert.assertNotEquals(defect.getDetectedBy(), "");
			Assert.assertEquals(defect.getDetectedInReleaseCycleId(), Integer.MIN_VALUE);
			Assert.assertEquals(defect.getDetectedInReleaseId(), Integer.MIN_VALUE);
			Assert.assertEquals(defect.getDetectedOnDate(), "292269055-12-02");
			Assert.assertNotEquals(defect.getId(), Integer.MIN_VALUE);
			Assert.assertEquals(defect.getModified(), "292269055-12-02 11:47:04");
			Assert.assertEquals(defect.getSeverity(), "");
			Assert.assertEquals(defect.getStatus(), "");
			Assert.assertEquals(defect.getSummary(), "");
		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnDefectsMatchingQuery() {
		final AlmEntityCollection<Defect> defectCollection = alm.getEntities(Defect.class,
				new RestParameters().queryFilter(DefectField.SEVERITY, "2-Medium"));
		Assert.assertTrue(defectCollection.size() > 0);
		for (final Defect defect : defectCollection) {
			Assert.assertEquals(defect.getSeverity(), "2-Medium");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnDefectssStartingAtStartIndex() {
		final AlmEntityCollection<Defect> initialDefectCollection = alm.getEntities(Defect.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<Defect> indexTwoCollection = alm.getEntities(Defect.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialDefectCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final Defect defect : indexTwoCollection) {
			Assert.assertFalse(initialDefectCollection.contains(defect));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withDefectAndNonExistentId_shouldThrowException() {
		alm.getEnityById(Defect.class, 99999999);
	}

	@Test
	public void getEntity_withDefectAndValidId_shouldReturnCorrectDefect() {
		final Defect defect = alm.getEnityById(Defect.class, 19);
		Assert.assertNotNull(defect);
		Assert.assertTrue(defect.isExactMatch(getDefect19()), "expected:\n" + getDefect19() + "\nbut got:\n" + defect);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final Defect defect = alm.getEnityById(Defect.class, 30);
		final Defect initialDefect = new Defect();
		initialDefect.populateFields(defect.createEntity());
		defect.setAssignedTo(defect.getAssignedTo().equals("svc") ? "ssimmons" : "svc");
		defect.setClosingDate(defect.getClosingDate().equals("2014-09-10") ? "2014-09-09" : "2014-09-10");
		defect.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		defect.setDescription("newDescription" + System.currentTimeMillis() + Thread.currentThread().getId());
		defect.setDetectedBy(defect.getDetectedBy().equals("ssimmons") ? "svc" : "ssimmons");
		defect.setDetectedInReleaseCycleId(defect.getDetectedInReleaseCycleId() == 1001 ? 1014 : 1001);
		defect.setDetectedOnDate(defect.getDetectedOnDate().equals("2014-09-09") ? "2014-09-08" : "2014-09-09");
		defect.setModified("2013-12-02 11:47:04");
		defect.setSeverity(defect.getSeverity().equals("2-Medium") ? "5-Urgent" : "2-Medium");
		defect.setStatus(defect.getStatus().equals("Closed") ? "New" : "Closed");
		defect.setSummary("newSummary" + System.currentTimeMillis() + Thread.currentThread().getId());

		alm.updateEntity(defect, DefectField.SUMMARY, DefectField.DESCRIPTION, DefectField.COMMENTS);
		final Defect updatedDefect = alm.getEnityById(Defect.class, 30);
		Assert.assertEquals(updatedDefect.getAssignedTo(), initialDefect.getAssignedTo());
		Assert.assertEquals(updatedDefect.getClosingDate(), initialDefect.getClosingDate());
		Assert.assertEquals(updatedDefect.getComments(), defect.getComments());
		Assert.assertEquals(updatedDefect.getDescription(), defect.getDescription());
		Assert.assertEquals(updatedDefect.getDetectedBy(), initialDefect.getDetectedBy());
		Assert.assertEquals(updatedDefect.getDetectedInReleaseId(), initialDefect.getDetectedInReleaseId());
		Assert.assertEquals(updatedDefect.getDetectedOnDate(), initialDefect.getDetectedOnDate());
		Assert.assertNotEquals(updatedDefect.getModified(), initialDefect.getModified());
		Assert.assertEquals(updatedDefect.getSeverity(), initialDefect.getSeverity());
		Assert.assertEquals(updatedDefect.getSummary(), defect.getSummary());
	}

	@Test
	public void updateEntity_withoutFieldsSpecified_shouldUpdateAllEditableFields() {
		final Defect defect = alm.getEnityById(Defect.class, 32);
		final Defect initialDefect = new Defect();
		initialDefect.populateFields(defect.createEntity());
		defect.setAssignedTo(defect.getAssignedTo().equals("svc") ? "ssimmons" : "svc");
		defect.setClosingDate(defect.getClosingDate().equals("2014-09-10") ? "2014-09-09" : "2014-09-10");
		defect.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		defect.setDescription("newDescription" + System.currentTimeMillis() + Thread.currentThread().getId());
		defect.setDetectedBy(defect.getDetectedBy().equals("ssimmons") ? "svc" : "ssimmons");
		defect.setDetectedInReleaseCycleId(defect.getDetectedInReleaseCycleId() == 1001 ? 1014 : 1001);
		defect.setDetectedOnDate(defect.getDetectedOnDate().equals("2014-09-09") ? "2014-09-08" : "2014-09-09");
		defect.setModified("2013-12-02 11:47:04");
		defect.setSeverity(defect.getSeverity().equals("2-Medium") ? "5-Urgent" : "2-Medium");
		defect.setStatus(defect.getStatus().equals("Closed") ? "New" : "Closed");
		defect.setSummary("newSummary" + System.currentTimeMillis() + Thread.currentThread().getId());

		alm.updateEntity(defect);
		final Defect updatedDefect = alm.getEnityById(Defect.class, 32);
		Assert.assertEquals(updatedDefect.getAssignedTo(), defect.getAssignedTo());
		Assert.assertEquals(updatedDefect.getClosingDate(), defect.getClosingDate());
		Assert.assertEquals(updatedDefect.getComments(), defect.getComments());
		Assert.assertEquals(updatedDefect.getDescription(), defect.getDescription());
		Assert.assertEquals(updatedDefect.getDetectedBy(), defect.getDetectedBy());
		Assert.assertEquals(updatedDefect.getDetectedInReleaseCycleId(), defect.getDetectedInReleaseCycleId());
		Assert.assertEquals(updatedDefect.getDetectedOnDate(), defect.getDetectedOnDate());
		Assert.assertNotEquals(updatedDefect.getModified(), initialDefect.getModified());
		Assert.assertEquals(updatedDefect.getSeverity(), defect.getSeverity());
		Assert.assertEquals(updatedDefect.getSummary(), defect.getSummary());
	}

	private Defect createMinimumDefect() {
		final Defect defect = new Defect();
		defect.setDetectedBy("svc");
		defect.setSummary("the summary");
		defect.setSeverity("1-Low");
		defect.setDetectedOnDate("2014-01-02");
		return defect;
	}

	private Defect getDefect19() {
		final Defect defect = new Defect();
		defect.setDetectedBy("ssimmons");
		defect.setSummary("Get Defect By Id");
		defect.setSeverity("2-Medium");
		defect.setDetectedOnDate("2014-09-09");
		defect.setAssignedTo("svc");
		defect.setClosingDate("2014-09-09");
		defect.setComments("Get Defect By Id Comments");
		defect.setDescription("Get Defect By Id Description");
		defect.setDetectedInReleaseCycleId(1001);
		defect.setDetectedInReleaseId(1001);
		defect.setId(19);
		defect.setModified("2014-09-10 11:58:53");
		defect.setStatus("Closed");
		return defect;
	}
}
