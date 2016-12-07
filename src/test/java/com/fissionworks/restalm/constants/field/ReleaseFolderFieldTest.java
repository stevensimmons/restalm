package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ReleaseFolderFieldTest {

	@Test
	public void getName_forContainsFolderDescription_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forContainsFolderId_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_ID.getName(), "id");
	}

	@Test
	public void getName_forContainsFolderName_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_NAME.getName(), "name");
	}

	@Test
	public void getName_forContainsFolderParentId_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forDescription_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forId_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shoudReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getQualifiedName_forContainsFolderDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_DESCRIPTION.getQualifiedName(),
				"contains-release-folder.description");
	}

	@Test
	public void getQualifiedName_forContainsFolderId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_ID.getQualifiedName(), "contains-release-folder.id");
	}

	@Test
	public void getQualifiedName_forContainsFolderName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_NAME.getQualifiedName(), "contains-release-folder.name");
	}

	@Test
	public void getQualifiedName_forContainsFolderParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.CONTAINS_FOLDER_PARENT_ID.getQualifiedName(),
				"contains-release-folder.parent-id");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.DESCRIPTION.getQualifiedName(), "release-folder.description");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.ID.getQualifiedName(), "release-folder.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.NAME.getQualifiedName(), "release-folder.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseFolderField.PARENT_ID.getQualifiedName(), "release-folder.parent-id");
	}
}
