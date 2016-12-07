package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AlmTestFieldTest {

	@Test
	public void getName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.CREATION_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forDesigner_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.DESIGNER.getName(), "owner");
	}

	@Test
	public void getName_forHasPartsTestCreationDate_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_CREATION_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forHasPartsTestDescription_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forHasPartsTestDesigner_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_DESIGNER.getName(), "owner");
	}

	@Test
	public void getName_forHasPartsTestId_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_ID.getName(), "id");
	}

	@Test
	public void getName_forHasPartsTestName_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_NAME.getName(), "name");
	}

	@Test
	public void getName_forHasPartsTestParentId_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forHasPartsTestStatus_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_STATUS.getName(), "status");
	}

	@Test
	public void getName_forHasPartsTestType_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_TYPE.getName(), "subtype-id");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.STATUS.getName(), "status");
	}

	@Test
	public void getName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.TYPE.getName(), "subtype-id");
	}

	@Test
	public void getQualifiedName_forCreationDate_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.CREATION_DATE.getQualifiedName(), "test.creation-time");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.DESCRIPTION.getQualifiedName(), "test.description");
	}

	@Test
	public void getQualifiedName_forDesigner_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.DESIGNER.getQualifiedName(), "test.owner");
	}

	@Test
	public void getQualifiedName_forHasPartsTestDescription_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_DESCRIPTION.getQualifiedName(), "has-parts-test.description");
	}

	@Test
	public void getQualifiedName_forHasPartsTestDesigner_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_DESIGNER.getQualifiedName(), "has-parts-test.owner");
	}

	@Test
	public void getQualifiedName_forHasPartsTestId_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_ID.getQualifiedName(), "has-parts-test.id");
	}

	@Test
	public void getQualifiedName_forHasPartsTestName_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_NAME.getQualifiedName(), "has-parts-test.name");
	}

	@Test
	public void getQualifiedName_forHasPartsTestParentId_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_PARENT_ID.getQualifiedName(), "has-parts-test.parent-id");
	}

	@Test
	public void getQualifiedName_forHasPartsTestStatus_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_STATUS.getQualifiedName(), "has-parts-test.status");
	}

	@Test
	public void getQualifiedName_forHasPartsTestType_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_TYPE.getQualifiedName(), "has-parts-test.subtype-id");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.ID.getQualifiedName(), "test.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.NAME.getQualifiedName(), "test.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.PARENT_ID.getQualifiedName(), "test.parent-id");
	}

	@Test
	public void getQualifiedName_forParentTestCreationDate_shoudReturnCorrectName() {
		Assert.assertEquals(AlmTestField.HAS_PARTS_TEST_CREATION_DATE.getQualifiedName(),
				"has-parts-test.creation-time");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.STATUS.getQualifiedName(), "test.status");
	}

	@Test
	public void getQualifiedName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(AlmTestField.TYPE.getQualifiedName(), "test.subtype-id");
	}
}
