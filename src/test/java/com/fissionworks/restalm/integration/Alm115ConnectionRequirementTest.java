package com.fissionworks.restalm.integration;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.requirements.Requirement;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionRequirementTest {

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
		final Requirement initialRequirement = createMinimumRequirement();
		initialRequirement.setAuthor("svc");
		initialRequirement.setComments("theComments");
		initialRequirement.setCreationDate("2000-01-01");
		initialRequirement.setDescription("theDescription");
		initialRequirement.setDirectCoverStatus("Failed");
		initialRequirement.setFatherName("Unit Testing");
		initialRequirement.setId(1776);
		initialRequirement.setLastModified("2001-01-01 10:11:12");
		initialRequirement.setParentId(23);
		final Requirement addedRequirement = alm.addEntity(initialRequirement);
		alm.deleteEntity(Requirement.class, addedRequirement.getId());
		Assert.assertEquals(addedRequirement.getAuthor(), initialRequirement.getAuthor());
		Assert.assertEquals(addedRequirement.getComments(), initialRequirement.getComments());
		Assert.assertEquals(addedRequirement.getCreationDate(), initialRequirement.getCreationDate());
		Assert.assertEquals(addedRequirement.getDescription(), initialRequirement.getDescription());
		Assert.assertEquals(addedRequirement.getDirectCoverStatus(), initialRequirement.getDirectCoverStatus());
		Assert.assertEquals(addedRequirement.getFatherName(), "Add Requirement");
		Assert.assertFalse(addedRequirement.getId() == Integer.MIN_VALUE);
		Assert.assertNotEquals(addedRequirement.getLastModified(), initialRequirement.getLastModified());
		Assert.assertEquals(addedRequirement.getName(), initialRequirement.getName());
		Assert.assertEquals(addedRequirement.getTypeId(), initialRequirement.getTypeId());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final Requirement requirement = new Requirement();
		requirement.setName(createUniqueName());
		alm.addEntity(requirement);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final Requirement requirement = new Requirement();
		boolean exceptionThrown = false;
		try {
			alm.addEntity(requirement);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: name,type-id")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: type-id,name"));
		}
		Assert.assertTrue(exceptionThrown);
	}

	@Test
	public void addEntityAndDelete_withMininumRequiredFieldsPopulated_shouldCreateRequirement() {
		final Requirement initialRequirement = createMinimumRequirement();
		final Requirement addedRequirement = alm.addEntity(initialRequirement);
		alm.deleteEntity(Requirement.class, addedRequirement.getId());
		Assert.assertEquals(addedRequirement.getAuthor(), initialRequirement.getAuthor());
		Assert.assertEquals(addedRequirement.getComments(), initialRequirement.getComments());
		Assert.assertNotEquals(addedRequirement.getCreationDate(), initialRequirement.getCreationDate());
		Assert.assertEquals(addedRequirement.getDescription(), initialRequirement.getDescription());
		Assert.assertEquals(addedRequirement.getDirectCoverStatus(), "Not Covered");
		Assert.assertEquals(addedRequirement.getFatherName(), "Requirements");
		Assert.assertFalse(addedRequirement.getId() == Integer.MIN_VALUE);
		Assert.assertNotEquals(addedRequirement.getLastModified(), initialRequirement.getLastModified());
		Assert.assertEquals(addedRequirement.getName(), initialRequirement.getName());
		Assert.assertEquals(addedRequirement.getTypeId(), initialRequirement.getTypeId());
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Requirement requirement : alm.getEntities(Requirement.class,
				new RestParameters().fields(RequirementField.NAME, RequirementField.AUTHOR))) {
			Assert.assertNotEquals(requirement.getAuthor(), "Author should not be blank");
			Assert.assertEquals(requirement.getComments(), "");
			Assert.assertEquals(requirement.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(requirement.getDescription(), "");
			Assert.assertEquals(requirement.getDirectCoverStatus(), "");
			Assert.assertEquals(requirement.getFatherName(), "");
			Assert.assertFalse(requirement.getId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirement.getLastModified(), "292269055-12-02 11:47:04");
			Assert.assertNotEquals(requirement.getName(), "", "Name should not be blank");
			Assert.assertTrue(requirement.getTypeId() == Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnRequirementsMatchingQuery() {
		final AlmEntityCollection<Requirement> requirementCollection = alm.getEntities(Requirement.class,
				new RestParameters().queryFilter(RequirementField.PARENT_ID, "13")
						.queryFilter(RequirementField.DIRECT_COVER_STATUS, "Failed"));
		Assert.assertTrue(requirementCollection.size() > 0);
		for (final Requirement requirement : requirementCollection) {
			Assert.assertEquals(requirement.getFatherName(), "Get Requirement");
			Assert.assertEquals(requirement.getDirectCoverStatus(), "Failed");
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(Requirement.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentRequirementFieldsSpecified_shouldReturnRequirementWithRelatedEntity() {
		final AlmEntityCollection<Requirement> requirements = alm.getEntities(Requirement.class,
				new RestParameters().queryFilter(RequirementField.ID, "12").relatedFields(
						RequirementField.CONTAINS_REQUIREMENT_AUTHOR, RequirementField.CONTAINS_REQUIREMENT_COMMENTS,
						RequirementField.CONTAINS_REQUIREMENT_CREATION_DATE,
						RequirementField.CONTAINS_REQUIREMENT_DESCRIPTION,
						RequirementField.CONTAINS_REQUIREMENT_DIRECT_COVER_STATUS,
						RequirementField.CONTAINS_REQUIREMENT_FATHER_NAME,
						RequirementField.CONTAINS_REQUIREMENT_LAST_MODIFIED, RequirementField.CONTAINS_REQUIREMENT_NAME,
						RequirementField.CONTAINS_REQUIREMENT_PARENT_ID,
						RequirementField.CONTAINS_REQUIREMENT_TYPE_ID));
		final Requirement parentRequirement = new Requirement();
		parentRequirement.setAuthor("ssimmons");
		parentRequirement.setId(13);
		parentRequirement.setName("Get Requirement");
		parentRequirement.setDescription("Get Requirement Description");
		parentRequirement.setComments("Get Requirement Comments");
		parentRequirement.setTypeId(1);
		parentRequirement.setCreationDate("2014-10-01");
		parentRequirement.setDirectCoverStatus("N/A");
		parentRequirement.setFatherName("Requirement");
		parentRequirement.setParentId(41);

		for (final Requirement requirement : requirements) {
			parentRequirement.setLastModified(requirement.getParentRequirement().getLastModified());
			Assert.assertTrue(parentRequirement.isExactMatch(requirement.getParentRequirement()));
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final Requirement requirement : alm.getEntities(Requirement.class,
				new RestParameters().fields(RequirementField.NAME))) {
			Assert.assertEquals(requirement.getAuthor(), "");
			Assert.assertEquals(requirement.getComments(), "");
			Assert.assertEquals(requirement.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(requirement.getDescription(), "");
			Assert.assertEquals(requirement.getDirectCoverStatus(), "");
			Assert.assertEquals(requirement.getFatherName(), "");
			Assert.assertFalse(requirement.getId() == Integer.MIN_VALUE);
			Assert.assertEquals(requirement.getLastModified(), "292269055-12-02 11:47:04");
			Assert.assertNotEquals(requirement.getName(), "");
			Assert.assertTrue(requirement.getTypeId() == Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnDefectsMatchingQuery() {
		final AlmEntityCollection<Requirement> requirementCollection = alm.getEntities(Requirement.class,
				new RestParameters().queryFilter(RequirementField.PARENT_ID, "13"));
		Assert.assertTrue(requirementCollection.size() > 0);
		for (final Requirement requirement : requirementCollection) {
			Assert.assertEquals(requirement.getParentId(), 13);
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnDefectssStartingAtStartIndex() {
		final AlmEntityCollection<Requirement> initialRequirementCollection = alm.getEntities(Requirement.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<Requirement> indexTwoCollection = alm.getEntities(Requirement.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialRequirementCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final Requirement requirement : indexTwoCollection) {
			Assert.assertFalse(initialRequirementCollection.contains(requirement));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withNonExistentId_shouldThrowException() {
		alm.getEnityById(Requirement.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectEntity() {
		final Requirement requirement = alm.getEnityById(Requirement.class, 12);
		Assert.assertNotNull(requirement);
		Assert.assertTrue(requirement.isExactMatch(getRequirement12()),
				"expected:\n" + getRequirement12() + "\nbut got:\n" + requirement);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final Requirement requirement = alm.getEnityById(Requirement.class, 53);
		final Requirement initialRequirement = new Requirement();
		initialRequirement.populateFields(requirement.createEntity());
		requirement.setAuthor(requirement.getAuthor().equals("svc") ? "ssimmons" : "svc");
		requirement.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		requirement.setCreationDate(requirement.getCreationDate().equals("2014-10-02") ? "2014-10-01" : "2014-10-02");
		requirement.setDescription("newDescription" + System.currentTimeMillis() + Thread.currentThread().getId());
		requirement.setDirectCoverStatus(requirement.getDirectCoverStatus().equals("Failed") ? "Passed" : "Failed");
		requirement.setFatherName("new father name");
		requirement.setLastModified("2014-10-03 11:12:13");
		requirement.setName(createUniqueName());
		requirement.setParentId(requirement.getParentId() == 51 ? 52 : 51);
		requirement.setTypeId(requirement.getTypeId() == 3 ? 5 : 3);

		alm.updateEntity(requirement, RequirementField.NAME, RequirementField.DESCRIPTION);
		final Requirement updatedRequirement = alm.getEnityById(Requirement.class, 53);
		Assert.assertEquals(updatedRequirement.getAuthor(), initialRequirement.getAuthor());
		Assert.assertEquals(updatedRequirement.getComments(), initialRequirement.getComments());
		Assert.assertEquals(updatedRequirement.getCreationDate(), initialRequirement.getCreationDate());
		Assert.assertEquals(updatedRequirement.getDescription(), requirement.getDescription());
		Assert.assertEquals(updatedRequirement.getDirectCoverStatus(), initialRequirement.getDirectCoverStatus());
		Assert.assertEquals(updatedRequirement.getFatherName(), initialRequirement.getFatherName());
		Assert.assertNotEquals(updatedRequirement.getLastModified(), requirement.getLastModified());
		Assert.assertEquals(updatedRequirement.getName(), requirement.getName());
		Assert.assertEquals(updatedRequirement.getTypeId(), initialRequirement.getTypeId());
	}

	@Test
	public void updateEntity_withoutFieldsSpecified_shouldUpdateAllEditableFields() {
		final Requirement requirement = alm.getEnityById(Requirement.class, 54);
		final Requirement initialRequirement = new Requirement();
		initialRequirement.populateFields(requirement.createEntity());
		requirement.setAuthor(requirement.getAuthor().equals("svc") ? "ssimmons" : "svc");
		requirement.setComments("newComments" + System.currentTimeMillis() + Thread.currentThread().getId());
		requirement.setCreationDate(requirement.getCreationDate().equals("2014-10-02") ? "2014-10-01" : "2014-10-02");
		requirement.setDescription("newDescription" + System.currentTimeMillis() + Thread.currentThread().getId());
		requirement.setDirectCoverStatus(requirement.getDirectCoverStatus().equals("Failed") ? "Passed" : "Failed");
		requirement.setFatherName("new father name");
		requirement.setLastModified("2014-10-03 11:12:13");
		requirement.setName(createUniqueName());
		requirement.setParentId(requirement.getParentId() == 51 ? 52 : 51);
		requirement.setTypeId(requirement.getTypeId() == 3 ? 5 : 3);

		alm.updateEntity(requirement);
		final Requirement updatedRequirement = alm.getEnityById(Requirement.class, 54);
		Assert.assertEquals(updatedRequirement.getAuthor(), requirement.getAuthor());
		Assert.assertEquals(updatedRequirement.getComments(), requirement.getComments());
		Assert.assertEquals(updatedRequirement.getCreationDate(), requirement.getCreationDate());
		Assert.assertEquals(updatedRequirement.getDescription(), requirement.getDescription());
		Assert.assertEquals(updatedRequirement.getDirectCoverStatus(), requirement.getDirectCoverStatus());
		Assert.assertNotEquals(updatedRequirement.getFatherName(), requirement.getFatherName());
		Assert.assertNotEquals(updatedRequirement.getLastModified(), requirement.getLastModified());
		Assert.assertEquals(updatedRequirement.getName(), requirement.getName());
		Assert.assertEquals(updatedRequirement.getTypeId(), requirement.getTypeId());
	}

	private Requirement createMinimumRequirement() {
		final Requirement requirement = new Requirement();
		requirement.setName(createUniqueName());
		requirement.setTypeId(3);
		return requirement;
	}

	private String createUniqueName() {
		return "addedRequirement" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private Requirement getRequirement12() {
		final Requirement requirement = new Requirement();
		requirement.setId(12);
		requirement.setName("GetRequirementById");
		requirement.setDescription("The requirement description");
		requirement.setComments("The Requirement Comments");
		requirement.setTypeId(3);
		requirement.setAuthor("ssimmons");
		requirement.setCreationDate("2014-10-01");
		requirement.setDirectCoverStatus("Not Covered");
		requirement.setFatherName("Get Requirement");
		requirement.setLastModified("2014-10-01 10:45:40");
		requirement.setParentId(13);
		return requirement;
	}
}
