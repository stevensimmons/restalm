package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RequirmentCoverageFieldTest {

	@Test
	public void getName_forCoverageEntityType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName(), "entity-type");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.ID.getName(), "id");
	}

	@Test
	public void getName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.LAST_MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forRequirementId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.REQUIREMENT_ID.getName(), "requirement-id");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.STATUS.getName(), "status");
	}

	@Test
	public void getName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.TEST_ID.getName(), "test-id");
	}

	@Test
	public void getQualifiedName_forCoverageEntityType_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getQualifiedName(),
				"requirement-coverage.entity-type");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.ID.getQualifiedName(), "requirement-coverage.id");
	}

	@Test
	public void getQualifiedName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.LAST_MODIFIED.getQualifiedName(),
				"requirement-coverage.last-modified");
	}

	@Test
	public void getQualifiedName_forRequirementId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.REQUIREMENT_ID.getQualifiedName(),
				"requirement-coverage.requirement-id");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.STATUS.getQualifiedName(), "requirement-coverage.status");
	}

	@Test
	public void getQualifiedName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(RequirementCoverageField.TEST_ID.getQualifiedName(), "requirement-coverage.test-id");
	}
}
