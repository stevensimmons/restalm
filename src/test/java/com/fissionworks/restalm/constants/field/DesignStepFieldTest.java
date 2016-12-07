package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DesignStepFieldTest {

	@Test
	public void getName_forDescription_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forExpectedResult_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.EXPECTED_RESULT.getName(), "expected");
	}

	@Test
	public void getName_forId_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forStepOrder_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.STEP_ORDER.getName(), "step-order");
	}

	@Test
	public void getQualifiedName_forDescription_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.DESCRIPTION.getQualifiedName(), "design-step.description");
	}

	@Test
	public void getQualifiedName_forExpectedResult_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.EXPECTED_RESULT.getQualifiedName(), "design-step.expected");
	}

	@Test
	public void getQualifiedName_forId_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.ID.getQualifiedName(), "design-step.id");
	}

	@Test
	public void getQualifiedName_forName_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.NAME.getQualifiedName(), "design-step.name");
	}

	@Test
	public void getQualifiedName_forParentId_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.PARENT_ID.getQualifiedName(), "design-step.parent-id");
	}

	@Test
	public void getQualifiedName_forStepOrder_shoudReturnCorrectName() {
		Assert.assertEquals(DesignStepField.STEP_ORDER.getQualifiedName(), "design-step.step-order");
	}
}
