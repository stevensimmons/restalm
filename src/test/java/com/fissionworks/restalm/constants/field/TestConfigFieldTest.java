package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestConfigFieldTest {

	@Test
	public void getName_forCreatedBy_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.CREATED_BY.getName(), "owner");
	}

	@Test
	public void getName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.CREATION_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forDataState_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.DATA_STATE.getName(), "data-state");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.TEST_NAME.getName(), "test-name");
	}

	@Test
	public void getQualifiedName_forCreatedBy_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.CREATED_BY.getQualifiedName(), "test-config.owner");
	}

	@Test
	public void getQualifiedName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.CREATION_DATE.getQualifiedName(), "test-config.creation-time");
	}

	@Test
	public void getQualifiedName_forDataState_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.DATA_STATE.getQualifiedName(), "test-config.data-state");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.DESCRIPTION.getQualifiedName(), "test-config.description");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.ID.getQualifiedName(), "test-config.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.NAME.getQualifiedName(), "test-config.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.PARENT_ID.getQualifiedName(), "test-config.parent-id");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(TestConfigField.TEST_NAME.getQualifiedName(), "test-config.test-name");
	}

}
