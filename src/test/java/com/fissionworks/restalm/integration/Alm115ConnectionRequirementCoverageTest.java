package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.RequirementCoverageField;
import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.requirements.Requirement;
import com.fissionworks.restalm.model.entity.requirements.RequirementCoverage;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.site.Project;

/**
 * NOTE: There appear to be defects/unimplemented functionality in the ALM rest
 * API for RequirementCoverages; the page size filter, setting multiple query
 * filters, etc. do not appear to function, and are therefore not tested.
 *
 */
public class Alm115ConnectionRequirementCoverageTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxx";

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
		final AlmTest coveringTest = alm.addEntity(createMinimumAlmTest(createUniqueName()));
		final Requirement requirementToCover = alm.addEntity(createMinimumRequirement(createUniqueName()));
		final RequirementCoverage coverageToAdd = createMinimumRequirementCoverage(coveringTest.getId(),
				requirementToCover.getId());
		coverageToAdd.setCoverageEntityType("Run");
		coverageToAdd.setId(1337);
		coverageToAdd.setLastModified("2012-01-02 15:14:13");
		coverageToAdd.setStatus("Failed");
		final RequirementCoverage addedCoverage = alm.addEntity(coverageToAdd);
		alm.deleteEntity(AlmTest.class, coveringTest.getId());
		alm.deleteEntity(Requirement.class, requirementToCover.getId());
		Assert.assertNotEquals(addedCoverage.getCoverageEntityType(), coverageToAdd.getCoverageEntityType());
		Assert.assertFalse(addedCoverage.getId() == Integer.MIN_VALUE);
		Assert.assertNotEquals(addedCoverage.getLastModified(), coverageToAdd.getLastModified());
		Assert.assertEquals(addedCoverage.getRequirementId(), coverageToAdd.getRequirementId());
		Assert.assertNotEquals(addedCoverage.getStatus(), coverageToAdd.getStatus());
		Assert.assertEquals(addedCoverage.getTestId(), coverageToAdd.getTestId());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		requirementCoverage.setTestId(466);
		alm.addEntity(requirementCoverage);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(requirementCoverage);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: requirement-id,test-id")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: test-id,requirement-id"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final RequirementCoverage requirementCoverage : alm.getEntities(RequirementCoverage.class,
				new RestParameters().fields(RequirementCoverageField.COVERAGE_ENTITY_TYPE))) {
			Assert.assertNotEquals(requirementCoverage.getCoverageEntityType(), "");
			Assert.assertFalse(requirementCoverage.getId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirementCoverage.getLastModified(), new RequirementCoverage().getLastModified());
			Assert.assertTrue(requirementCoverage.getRequirementId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirementCoverage.getStatus(), "");
			Assert.assertTrue(requirementCoverage.getTestId() == Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnRequirementsMatchingQuery() {
		final AlmEntityCollection<RequirementCoverage> requirementCoverageCollection = alm.getEntities(
				RequirementCoverage.class,
				new RestParameters().queryFilter(RequirementCoverageField.COVERAGE_ENTITY_TYPE, "test")
						.queryFilter(RequirementCoverageField.TEST_ID, "475"));
		Assert.assertTrue(requirementCoverageCollection.size() > 0);
		for (final RequirementCoverage requirementCoverage : requirementCoverageCollection) {
			Assert.assertEquals(requirementCoverage.getTestId(), 475);
			Assert.assertEquals(requirementCoverage.getCoverageEntityType(), "test");
		}
	}

	@Test
	public void getEntities_withParentRequirementFieldsSpecified_shouldReturnRequirementWithRelatedEntity() {
		final AlmEntityCollection<RequirementCoverage> requirementCoverages = alm.getEntities(RequirementCoverage.class,
				new RestParameters().queryFilter(RequirementCoverageField.ID, "8").relatedFields(
						RequirementField.CONTAINS_REQUIREMENT_AUTHOR, RequirementField.CONTAINS_REQUIREMENT_COMMENTS,
						RequirementField.CONTAINS_REQUIREMENT_CREATION_DATE,
						RequirementField.CONTAINS_REQUIREMENT_DESCRIPTION,
						RequirementField.CONTAINS_REQUIREMENT_DIRECT_COVER_STATUS,
						RequirementField.CONTAINS_REQUIREMENT_FATHER_NAME,
						RequirementField.CONTAINS_REQUIREMENT_LAST_MODIFIED, RequirementField.CONTAINS_REQUIREMENT_NAME,
						RequirementField.CONTAINS_REQUIREMENT_PARENT_ID,
						RequirementField.CONTAINS_REQUIREMENT_TYPE_ID));
		final Requirement associatedRequirement = new Requirement();
		associatedRequirement.setAuthor("ssimmons");
		associatedRequirement.setId(75);
		associatedRequirement.setName("Requirement for coverage Testing two");
		associatedRequirement.setDescription("Requirement for coverage Testing two description");
		associatedRequirement.setComments("Requirement for coverage Testing two comments");
		associatedRequirement.setTypeId(5);
		associatedRequirement.setCreationDate("2014-10-09");
		associatedRequirement.setDirectCoverStatus("No Run");
		associatedRequirement.setFatherName("Requirement Coverages");
		associatedRequirement.setParentId(65);

		for (final RequirementCoverage coverage : requirementCoverages) {
			associatedRequirement.setLastModified(coverage.getAssociatedRequirement().getLastModified());
			Assert.assertTrue(associatedRequirement.isExactMatch(coverage.getAssociatedRequirement()));
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final RequirementCoverage requirementCoverage : alm.getEntities(RequirementCoverage.class,
				new RestParameters().fields(RequirementCoverageField.COVERAGE_ENTITY_TYPE,
						RequirementCoverageField.TEST_ID))) {
			Assert.assertNotEquals(requirementCoverage.getCoverageEntityType(), "");
			Assert.assertFalse(requirementCoverage.getId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirementCoverage.getLastModified(), new RequirementCoverage().getLastModified());
			Assert.assertTrue(requirementCoverage.getRequirementId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirementCoverage.getStatus(), "");
			Assert.assertFalse(requirementCoverage.getTestId() == Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnDefectsMatchingQuery() {
		final AlmEntityCollection<RequirementCoverage> requirementCoverageCollection = alm.getEntities(
				RequirementCoverage.class, new RestParameters().queryFilter(RequirementCoverageField.TEST_ID, "466"));
		Assert.assertTrue(requirementCoverageCollection.size() > 0);
		for (final RequirementCoverage coverage : requirementCoverageCollection) {
			Assert.assertEquals(coverage.getTestId(), 466);
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(RequirementCoverage.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectEntity() {
		final RequirementCoverage requirementCoverage = alm.getEnityById(RequirementCoverage.class, 8);
		Assert.assertNotNull(requirementCoverage);
		Assert.assertTrue(requirementCoverage.isExactMatch(getRequirementCoverage8()),
				"expected:\n" + getRequirementCoverage8() + "\nbut got:\n" + requirementCoverage);
	}

	private AlmTest createMinimumAlmTest(final String name) {
		final AlmTest test = new AlmTest();
		test.setName(name);
		test.setParentId(1272);
		test.setType("MANUAL");
		return test;
	}

	private Requirement createMinimumRequirement(final String name) {
		final Requirement requirement = new Requirement();
		requirement.setParentId(69);
		requirement.setName(name);
		requirement.setTypeId(3);
		requirement.setAuthor("svc");
		return requirement;
	}

	private RequirementCoverage createMinimumRequirementCoverage(final int testId, final int requirementId) {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		requirementCoverage.setTestId(testId);
		requirementCoverage.setRequirementId(requirementId);
		return requirementCoverage;
	}

	private String createUniqueName() {
		return "addedEntity" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private RequirementCoverage getRequirementCoverage8() {
		final RequirementCoverage coverage = new RequirementCoverage();
		coverage.setId(8);
		coverage.setCoverageEntityType("test");
		coverage.setRequirementId(75);
		coverage.setTestId(474);
		coverage.setStatus("No Run");
		coverage.setLastModified("2014-10-09 11:34:31");
		return coverage;
	}

}
