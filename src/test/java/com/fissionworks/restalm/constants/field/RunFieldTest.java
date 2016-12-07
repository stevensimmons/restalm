package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RunFieldTest {

	@Test
	public void getName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.COMMENTS.getName(), "comments");
	}

	@Test
	public void getName_forHost_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.HOST.getName(), "host");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.ID.getName(), "id");
	}

	@Test
	public void getName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.LAST_MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.NAME.getName(), "name");
	}

	@Test
	public void getName_forOwner_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.OWNER.getName(), "owner");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.STATUS.getName(), "status");
	}

	@Test
	public void getName_forSubtypeId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.SUBTYPE_ID.getName(), "subtype-id");
	}

	@Test
	public void getName_forTestConfigId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_CONFIG_ID.getName(), "test-config-id");
	}

	@Test
	public void getName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_ID.getName(), "test-id");
	}

	@Test
	public void getName_forTestInstanceID_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_INSTANCE_ID.getName(), "testcycl-id");
	}

	@Test
	public void getName_forTestSetID_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_SET_ID.getName(), "cycle-id");
	}

	@Test
	public void getQualifiedName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.COMMENTS.getQualifiedName(), "run.comments");
	}

	@Test
	public void getQualifiedName_forHost_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.HOST.getQualifiedName(), "run.host");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.ID.getQualifiedName(), "run.id");
	}

	@Test
	public void getQualifiedName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.LAST_MODIFIED.getQualifiedName(), "run.last-modified");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.NAME.getQualifiedName(), "run.name");
	}

	@Test
	public void getQualifiedName_forOwner_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.OWNER.getQualifiedName(), "run.owner");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.STATUS.getQualifiedName(), "run.status");
	}

	@Test
	public void getQualifiedName_forSubtypeId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.SUBTYPE_ID.getQualifiedName(), "run.subtype-id");
	}

	@Test
	public void getQualifiedName_forTestConfigId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_CONFIG_ID.getQualifiedName(), "run.test-config-id");
	}

	@Test
	public void getQualifiedName_forTestId_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_ID.getQualifiedName(), "run.test-id");
	}

	@Test
	public void getQualifiedName_forTestInstanceID_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_INSTANCE_ID.getQualifiedName(), "run.testcycl-id");
	}

	@Test
	public void getQualifiedName_forTestSetID_shouldReturnCorrectName() {
		Assert.assertEquals(RunField.TEST_SET_ID.getQualifiedName(), "run.cycle-id");
	}

}
