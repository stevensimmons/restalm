package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestInstanceFieldTest {

	@Test
	public void getName_forCycleId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.TEST_SET_ID.getName(), "cycle-id");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.ID.getName(), "id");
	}

	@Test
	public void getName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.LAST_MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forResponsibleTester_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.RESPONSIBLE_TESTER.getName(), "owner");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.STATUS.getName(), "status");
	}

	@Test
	public void getName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.TEST_ID.getName(), "test-id");
	}

	@Test
	public void getName_forTestOrder_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.TEST_ORDER.getName(), "test-order");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.ID.getQualifiedName(), "test-instance.id");
	}

	@Test
	public void getQualifiedName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.LAST_MODIFIED.getQualifiedName(), "test-instance.last-modified");
	}

	@Test
	public void getQualifiedName_forResponsibleTester_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.RESPONSIBLE_TESTER.getQualifiedName(), "test-instance.owner");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.STATUS.getQualifiedName(), "test-instance.status");
	}

	@Test
	public void getQualifiedName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.TEST_ID.getQualifiedName(), "test-instance.test-id");
	}

	@Test
	public void getQualifiedName_forTestSetId_shouldReturnCorrectName() {
		Assert.assertEquals(TestInstanceField.TEST_SET_ID.getQualifiedName(), "test-instance.cycle-id");
	}
}
