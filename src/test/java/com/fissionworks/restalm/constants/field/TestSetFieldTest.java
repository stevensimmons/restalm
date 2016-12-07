package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSetFieldTest {
	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.DESCRIPTION.getName(), "comment");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.TYPE.getName(), "subtype-id");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.DESCRIPTION.getQualifiedName(), "test-set.comment");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.ID.getQualifiedName(), "test-set.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.NAME.getQualifiedName(), "test-set.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.PARENT_ID.getQualifiedName(), "test-set.parent-id");
	}

	@Test
	public void getQualifiedName_forType_shouldReturnCorrectName() {
		Assert.assertEquals(TestSetField.TYPE.getQualifiedName(), "test-set.subtype-id");
	}
}
