package com.fissionworks.restalm.constants.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityTypeTest {

	@Test
	public void collectionName_withAlmTest_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.ALM_TEST.collectionName(), "tests");
	}

	@Test
	public void collectionName_withDefect_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.DEFECT.collectionName(), "defects");
	}

	@Test
	public void collectionName_withDesignStep_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.DESIGN_STEP.collectionName(), "design-steps");
	}

	@Test
	public void collectionName_withRelease_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE.collectionName(), "releases");
	}

	@Test
	public void collectionName_withReleaseCycle_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE_CYCLE.collectionName(), "release-cycles");
	}

	@Test
	public void collectionName_withReleaseFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE_FOLDER.collectionName(), "release-folders");
	}

	@Test
	public void collectionName_withRequirement_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.REQUIREMENT.collectionName(), "requirements");
	}

	@Test
	public void collectionName_withRequirementCoverage_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.REQUIREMENT_COVERAGE.collectionName(), "requirement-coverages");
	}

	@Test
	public void collectionName_withRun_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RUN.collectionName(), "runs");
	}

	@Test
	public void collectionName_withTestConfig_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_CONFIG.collectionName(), "test-configs");
	}

	@Test
	public void collectionName_withTestFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_FOLDER.collectionName(), "test-folders");
	}

	@Test
	public void collectionName_withTestInstance_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_INSTANCE.collectionName(), "test-instances");
	}

	@Test
	public void collectionName_withTestSet_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_SET.collectionName(), "test-sets");
	}

	@Test
	public void collectionName_withTestSetFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_SET_FOLDER.collectionName(), "test-set-folders");
	}

	@Test
	public void entityName_withAlmTest_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.ALM_TEST.entityName(), "test");
	}

	@Test
	public void entityName_withDefect_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.DEFECT.entityName(), "defect");
	}

	@Test
	public void entityName_withDesignStep_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.DESIGN_STEP.entityName(), "design-step");
	}

	@Test
	public void entityName_withRelease_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE.entityName(), "release");
	}

	@Test
	public void entityName_withReleaseCycle_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE_CYCLE.entityName(), "release-cycle");
	}

	@Test
	public void entityName_withReleaseFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RELEASE_FOLDER.entityName(), "release-folder");
	}

	@Test
	public void entityName_withRequirement_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.REQUIREMENT.entityName(), "requirement");
	}

	@Test
	public void entityName_withRequirementCoverage_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.REQUIREMENT_COVERAGE.entityName(), "requirement-coverage");
	}

	@Test
	public void entityName_withRun_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.RUN.entityName(), "run");
	}

	@Test
	public void entityName_withTestConfig_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_CONFIG.entityName(), "test-config");
	}

	@Test
	public void entityName_withTestFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_FOLDER.entityName(), "test-folder");
	}

	@Test
	public void entityName_withTestInstance_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_INSTANCE.entityName(), "test-instance");
	}

	@Test
	public void entityName_withTestSet_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_SET.entityName(), "test-set");
	}

	@Test
	public void entityName_withTestSetFolder_shouldReturnCorrectName() {
		Assert.assertEquals(EntityType.TEST_SET_FOLDER.entityName(), "test-set-folder");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fromEntityName_withInvalidName_shouldThrowException() {
		EntityType.fromEntityName("notanentity");
	}

	@Test
	public void fromEntityName_withValidName_shouldReturnEnum() {
		Assert.assertEquals(EntityType.fromEntityName("test"), EntityType.ALM_TEST);
	}
}
