package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSetFolderFieldTest {
	@Test
	public void getName_forContainsFolderDescription_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forContainsFolderId_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_ID.getName(), "id");
	}

	@Test
	public void getName_forContainsFolderName_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_NAME.getName(), "name");
	}

	@Test
	public void getName_forContainsFolderParentId_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forDescription_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forId_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shoudReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getQualifiedName_forContainsFolderDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_DESCRIPTION.getQualifiedName(),
				"contains-test-set-folder.description");
	}

	@Test
	public void getQualifiedName_forContainsFolderId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_ID.getQualifiedName(),
				"contains-test-set-folder.id");
	}

	@Test
	public void getQualifiedName_forContainsFolderName_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_NAME.getQualifiedName(),
				"contains-test-set-folder.name");
	}

	@Test
	public void getQualifiedName_forContainsFolderParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.CONTAINS_TEST_SET_FOLDER_PARENT_ID.getQualifiedName(),
				"contains-test-set-folder.parent-id");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.DESCRIPTION.getQualifiedName(), "test-set-folder.description");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.ID.getQualifiedName(), "test-set-folder.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.NAME.getQualifiedName(), "test-set-folder.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetFolderField.PARENT_ID.getQualifiedName(), "test-set-folder.parent-id");
	}
}
