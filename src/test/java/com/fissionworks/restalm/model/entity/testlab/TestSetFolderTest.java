package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.constants.field.TestSetFolderField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class TestSetFolderTest {

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final TestSetFolder sourceTestSetFolder = createFullTestSetFolder();
		final GenericEntity createdEntity = sourceTestSetFolder.createEntity();

		Assert.assertEquals(createdEntity.getFieldValues(TestSetFolderField.DESCRIPTION.getName()),
				Arrays.asList(sourceTestSetFolder.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetFolderField.ID.getName()),
				Arrays.asList(String.valueOf(sourceTestSetFolder.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetFolderField.NAME.getName()),
				Arrays.asList(sourceTestSetFolder.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetFolderField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestSetFolder.getParentId())));
	}

	@Test
	public void createEntity_withTestSetfolderHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final TestSetFolder sourceTestSetFolder = new TestSetFolder();
		final GenericEntity createdEntity = sourceTestSetFolder.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(TestSetFolderField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestSetFolderField.PARENT_ID.getName()));
	}

	@Test
	public void equals_comparingTestFolderToAnEqualObject_shouldReturnTrue() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertTrue(testFolderOne.equals(createFullTestSetFolder()));
	}

	@Test
	public void equals_comparingTestFolderToAnotherObjectType_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertFalse(testFolderOne.equals(new Object()));
	}

	@Test
	public void equals_comparingTestFolderToItself_shouldReturnTrue() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertTrue(testFolderOne.equals(testFolderOne));
	}

	@Test
	public void equals_comparingTestFolderToNull_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertFalse(testFolderOne.equals(null));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentId_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderTwo.setId(1234);
		Assert.assertFalse(testFolderOne.equals(testFolderTwo));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentName_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderTwo.setName("different");
		Assert.assertFalse(testFolderOne.equals(testFolderTwo));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentParentId_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderTwo.setParentId(123456);
		Assert.assertFalse(testFolderOne.equals(testFolderTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new TestSetFolder().getEntityCollectionType(), "test-set-folders");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new TestSetFolder().getEntityType(), "test-set-folder");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final TestSetFolder testFolder = new TestSetFolder();
		testFolder.setDescription("<b>bold description</b>");
		Assert.assertEquals(testFolder.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentTestFolder_withNoParentSet_shouldReturnDefaultFolder() {
		Assert.assertEquals(new TestSetFolder().getParentTestSetFolder(), new TestSetFolder());
	}

	@Test
	public void getParentTestFolder_withParentSet_shouldReturnParentFolder() {
		final TestSetFolder folder = new TestSetFolder();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		folder.setParentTestSetFolder(parentFolder);
		Assert.assertEquals(folder.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void hashCode_forEqualTestFolders_shouldBeEqual() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		Assert.assertEquals(testFolderOne.hashCode(), testFolderTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTestFolders_shouldNotBeEqual() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderOne.setParentId(1234);
		Assert.assertNotEquals(testFolderOne.hashCode(), testFolderTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingTestFolderToExactlyMatchingTestFolder_shouldReturnTrue() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToExactlyMatchingTestFolderWithParentFolders_shouldReturnTrue() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderOne.setParentTestSetFolder(new TestSetFolder());
		testFolderTwo.setParentTestSetFolder(new TestSetFolder());
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToItself_shouldReturnTrue() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderOne));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderThatDoesNotSatisyEquals_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderTwo.setParentId(123456);
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderWithDifferentDescription_shouldReturnFalse() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder testFolderTwo = createFullTestSetFolder();
		testFolderTwo.setDescription("newDescription");
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderWithDifferentParentTestFolder_shouldReturnFalse() {
		final TestSetFolder testFolderOne = new TestSetFolder();
		final TestSetFolder testFolderTwo = new TestSetFolder();
		final TestSetFolder parentTestFolder = new TestSetFolder();
		parentTestFolder.setName("different name");
		testFolderTwo.setParentTestSetFolder(parentTestFolder);
		testFolderOne.setParentTestSetFolder(new TestSetFolder());
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderWithNullParentToTestFolderWithNonNullFolder_shouldReturnFalse() {
		final TestSetFolder testFolderOne = new TestSetFolder();
		final TestSetFolder testFolderTwo = new TestSetFolder();
		final TestSetFolder parentTestFolder = new TestSetFolder();
		testFolderTwo.setParentTestSetFolder(parentTestFolder);
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final TestSetFolder testSetFolder = new TestSetFolder();
		testSetFolder.populateFields(
				new GenericEntity("test-set-folder", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(testSetFolder.getDescription(), "");
		Assert.assertEquals(testSetFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(testSetFolder.getName(), "");
		Assert.assertEquals(testSetFolder.getParentId(), Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllTestSetFolderFieldsPopulated_shouldSetAllTestSetFolderFields() {
		final TestSetFolder testSetFolder = new TestSetFolder();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		testSetFolder.populateFields(sourceEntity);
		Assert.assertEquals(testSetFolder.getDescription(),
				sourceEntity.getFieldValues(TestFolderField.DESCRIPTION.getName()).get(0));
		Assert.assertTrue(testSetFolder.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestFolderField.ID.getName()).get(0)));
		Assert.assertEquals(testSetFolder.getName(),
				sourceEntity.getFieldValues(TestFolderField.NAME.getName()).get(0));
		Assert.assertTrue(testSetFolder.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestFolderField.PARENT_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingNoParentFolderSet_shouldSetNewParentFolder() {
		final TestSetFolder testSetFolder = new TestSetFolder();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testSetFolder.populateFields(sourceEntity);
		Assert.assertEquals(testSetFolder.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentFolderSet_shouldSetNewParentFolder() {
		final TestSetFolder testFolder = new TestSetFolder();
		testFolder.setParentTestSetFolder(new TestSetFolder());
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testFolder.populateFields(sourceEntity);
		Assert.assertEquals(testFolder.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void setParentTestSetFolder_withTestSetFolderAlreadySet_shouldSetParentTestSEtFolder() {
		final TestSetFolder folder = new TestSetFolder();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("TheName");
		folder.setParentTestSetFolder(parentFolder);
		folder.setParentTestSetFolder(new TestSetFolder());
		Assert.assertEquals(folder.getParentTestSetFolder(), new TestSetFolder());

	}

	@Test
	public void toString_withNoParentTestFolderSet_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "TestSetFolder"));
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestFolderSet_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final TestSetFolder testFolderOne = createFullTestSetFolder();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("TheNameForParent");
		testFolderOne.setParentTestSetFolder(parentFolder);
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "TestSetFolder"));
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "TheNameForParent"));
	}

	private TestSetFolder createFullTestSetFolder() {
		final TestSetFolder testSetFolder = new TestSetFolder();
		testSetFolder.setDescription("description");
		testSetFolder.setId(1337);
		testSetFolder.setName("name");
		testSetFolder.setParentId(1776);
		return testSetFolder;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestSetFolderField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(TestSetFolderField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(TestSetFolderField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(TestSetFolderField.PARENT_ID.getName(), Arrays.asList("90210")));
		return new GenericEntity("test-set-folder", fields);
	}
}
