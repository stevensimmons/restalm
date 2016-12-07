package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RequirementFieldTest {

	@Test
	public void getName_forAuthor_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.AUTHOR.getName(), "owner");
	}

	@Test
	public void getName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.COMMENTS.getName(), "comments");
	}

	@Test
	public void getName_forContainsRequirementAuthor_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_AUTHOR.getName(), "owner");
	}

	@Test
	public void getName_forContainsRequirementComments_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_COMMENTS.getName(), "comments");
	}

	@Test
	public void getName_forContainsRequirementCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_CREATION_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forContainsRequirementDescription_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forContainsRequirementDirectCoverStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_DIRECT_COVER_STATUS.getName(), "status");
	}

	@Test
	public void getName_forContainsRequirementFatherName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_FATHER_NAME.getName(), "father-name");
	}

	@Test
	public void getName_forContainsRequirementId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_ID.getName(), "id");
	}

	@Test
	public void getName_forContainsRequirementLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_LAST_MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forContainsRequirementName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_NAME.getName(), "name");
	}

	@Test
	public void getName_forContainsRequirementParentId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forContainsRequirementType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_TYPE_ID.getName(), "type-id");
	}

	@Test
	public void getName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CREATION_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forDirectCoverStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.DIRECT_COVER_STATUS.getName(), "status");
	}

	@Test
	public void getName_forFatherName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.FATHER_NAME.getName(), "father-name");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.ID.getName(), "id");
	}

	@Test
	public void getName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.LAST_MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.TYPE_ID.getName(), "type-id");
	}

	@Test
	public void getQualifiedName_forAuthor_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.AUTHOR.getQualifiedName(), "requirement.owner");
	}

	@Test
	public void getQualifiedName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.COMMENTS.getQualifiedName(), "requirement.comments");
	}

	@Test
	public void getQualifiedName_forContainsRequirementAuthor_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_AUTHOR.getQualifiedName(),
				"contains-requirement.owner");
	}

	@Test
	public void getQualifiedName_forContainsRequirementComments_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_COMMENTS.getQualifiedName(),
				"contains-requirement.comments");
	}

	@Test
	public void getQualifiedName_forContainsRequirementCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_CREATION_DATE.getQualifiedName(),
				"contains-requirement.creation-time");
	}

	@Test
	public void getQualifiedName_forContainsRequirementDescription_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_DESCRIPTION.getQualifiedName(),
				"contains-requirement.description");
	}

	@Test
	public void getQualifiedName_forContainsRequirementDirectCoverStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_DIRECT_COVER_STATUS.getQualifiedName(),
				"contains-requirement.status");
	}

	@Test
	public void getQualifiedName_forContainsRequirementFatherName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_FATHER_NAME.getQualifiedName(),
				"contains-requirement.father-name");
	}

	@Test
	public void getQualifiedName_forContainsRequirementId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_ID.getQualifiedName(), "contains-requirement.id");
	}

	@Test
	public void getQualifiedName_forContainsRequirementLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_LAST_MODIFIED.getQualifiedName(),
				"contains-requirement.last-modified");
	}

	@Test
	public void getQualifiedName_forContainsRequirementName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_NAME.getQualifiedName(), "contains-requirement.name");
	}

	@Test
	public void getQualifiedName_forContainsRequirementParentId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_PARENT_ID.getQualifiedName(),
				"contains-requirement.parent-id");
	}

	@Test
	public void getQualifiedName_forContainsRequirementType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CONTAINS_REQUIREMENT_TYPE_ID.getQualifiedName(),
				"contains-requirement.type-id");
	}

	@Test
	public void getQualifiedName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.CREATION_DATE.getQualifiedName(), "requirement.creation-time");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.DESCRIPTION.getQualifiedName(), "requirement.description");
	}

	@Test
	public void getQualifiedName_forDirectCoverStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.DIRECT_COVER_STATUS.getQualifiedName(), "requirement.status");
	}

	@Test
	public void getQualifiedName_forFatherName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.FATHER_NAME.getQualifiedName(), "requirement.father-name");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.ID.getQualifiedName(), "requirement.id");
	}

	@Test
	public void getQualifiedName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.LAST_MODIFIED.getQualifiedName(), "requirement.last-modified");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.NAME.getQualifiedName(), "requirement.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.PARENT_ID.getQualifiedName(), "requirement.parent-id");
	}

	@Test
	public void getQualifiedName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementField.TYPE_ID.getQualifiedName(), "requirement.type-id");
	}
}
