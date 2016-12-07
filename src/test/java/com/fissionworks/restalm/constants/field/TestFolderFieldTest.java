package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFolderFieldTest {

	@Test
	public void getName_forContainsFolderDescription_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forContainsFolderId_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_ID.getName(), "id");
	}

	@Test
	public void getName_forContainsFolderName_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_NAME.getName(), "name");
	}

	@Test
	public void getName_forContainsFolderParentId_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forDescription_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forId_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shoudReturnCorrectName() {
		Assert.assertEquals(TestFolderField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getQualifiedName_forContainsFolderDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_DESCRIPTION.getQualifiedName(),
				"contains-test-folder.description");
	}

	@Test
	public void getQualifiedName_forContainsFolderId_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_ID.getQualifiedName(), "contains-test-folder.id");
	}

	@Test
	public void getQualifiedName_forContainsFolderName_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_NAME.getQualifiedName(), "contains-test-folder.name");
	}

	@Test
	public void getQualifiedName_forContainsFolderParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.CONTAINS_FOLDER_PARENT_ID.getQualifiedName(),
				"contains-test-folder.parent-id");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.DESCRIPTION.getQualifiedName(), "test-folder.description");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.ID.getQualifiedName(), "test-folder.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.NAME.getQualifiedName(), "test-folder.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestFolderField.PARENT_ID.getQualifiedName(), "test-folder.parent-id");
	}
}
