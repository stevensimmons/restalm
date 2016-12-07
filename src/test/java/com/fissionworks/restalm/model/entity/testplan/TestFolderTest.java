package com.fissionworks.restalm.model.entity.testplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class TestFolderTest {

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final TestFolder sourceTestFolder = createFullTestFolder();
		final GenericEntity createdEntity = sourceTestFolder.createEntity();

		Assert.assertEquals(createdEntity.getFieldValues(TestFolderField.DESCRIPTION.getName()),
				Arrays.asList(sourceTestFolder.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(TestFolderField.ID.getName()),
				Arrays.asList(String.valueOf(sourceTestFolder.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestFolderField.NAME.getName()),
				Arrays.asList(sourceTestFolder.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(TestFolderField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestFolder.getParentId())));
	}

	@Test
	public void createEntity_withAlmTestfolderHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final TestFolder sourceTestFolder = new TestFolder();
		final GenericEntity createdEntity = sourceTestFolder.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(TestFolderField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestFolderField.PARENT_ID.getName()));
	}

	@Test
	public void equals_comparingTestFolderToAnEqualObject_shouldReturnTrue() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		Assert.assertTrue(testFolderTwoOne.equals(createFullTestFolder()));
	}

	@Test
	public void equals_comparingTestFolderToAnotherObjectType_shouldReturnFalse() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		Assert.assertFalse(testFolderTwoOne.equals(new Object()));
	}

	@Test
	public void equals_comparingTestFolderToItself_shouldReturnTrue() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		Assert.assertTrue(testFolderTwoOne.equals(testFolderTwoOne));
	}

	@Test
	public void equals_comparingTestFolderToNull_shouldReturnFalse() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		Assert.assertFalse(testFolderTwoOne.equals(null));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentId_shouldReturnFalse() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		final TestFolder testFolderTwoTwo = createFullTestFolder();
		testFolderTwoTwo.setId(1234);
		Assert.assertFalse(testFolderTwoOne.equals(testFolderTwoTwo));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentName_shouldReturnFalse() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		final TestFolder testFolderTwoTwo = createFullTestFolder();
		testFolderTwoTwo.setName("different");
		Assert.assertFalse(testFolderTwoOne.equals(testFolderTwoTwo));
	}

	@Test
	public void equals_comparingTestFolderToTestFolderWithDifferentParentId_shouldReturnFalse() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		final TestFolder testFolderTwoTwo = createFullTestFolder();
		testFolderTwoTwo.setParentId(123456);
		Assert.assertFalse(testFolderTwoOne.equals(testFolderTwoTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new TestFolder().getEntityCollectionType(), "test-folders");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new TestFolder().getEntityType(), "test-folder");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final TestFolder testFolder = new TestFolder();
		testFolder.setDescription("<b>bold description</b>");
		Assert.assertEquals(testFolder.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentTestFolder_withNoParentSet_shouldReturnDefaultFolder() {
		Assert.assertEquals(new TestFolder().getParentTestFolder(), new TestFolder());
	}

	@Test
	public void getParentTestFolder_withParentSet_shouldReturnParentFolder() {
		final TestFolder folder = new TestFolder();
		final TestFolder parentFolder = new TestFolder();
		parentFolder.setName("parent name");
		folder.setParentTestFolder(parentFolder);
		Assert.assertEquals(folder.getParentTestFolder(), parentFolder);
	}

	@Test
	public void hashCode_forEqualTestFolders_shouldBeEqual() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		final TestFolder testFolderTwoTwo = createFullTestFolder();
		Assert.assertEquals(testFolderTwoOne.hashCode(), testFolderTwoTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTestFolders_shouldNotBeEqual() {
		final TestFolder testFolderTwoOne = createFullTestFolder();
		final TestFolder testFolderTwoTwo = createFullTestFolder();
		testFolderTwoOne.setParentId(1234);
		Assert.assertNotEquals(testFolderTwoOne.hashCode(), testFolderTwoTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingTestFolderToExactlyMatchingTestFolder_shouldReturnTrue() {
		final TestFolder testFolderOne = createFullTestFolder();
		final TestFolder testFolderTwo = createFullTestFolder();
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToExactlyMatchingTestFolderWithParentFolders_shouldReturnTrue() {
		final TestFolder testFolderOne = createFullTestFolder();
		final TestFolder testFolderTwo = createFullTestFolder();
		testFolderOne.setParentTestFolder(new TestFolder());
		testFolderTwo.setParentTestFolder(new TestFolder());
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToItself_shouldReturnTrue() {
		final TestFolder testFolderOne = createFullTestFolder();
		Assert.assertTrue(testFolderOne.isExactMatch(testFolderOne));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderThatDoesNotSatisyEquals_shouldReturnFalse() {
		final TestFolder testFolderOne = createFullTestFolder();
		final TestFolder testFolderTwo = createFullTestFolder();
		testFolderTwo.setParentId(123456);
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderWithDifferentDescription_shouldReturnFalse() {
		final TestFolder testFolderOne = createFullTestFolder();
		final TestFolder testFolderTwo = createFullTestFolder();
		testFolderTwo.setDescription("newDescription");
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderToTestFolderWithDifferentParentTestFolder_shouldReturnFalse() {
		final TestFolder testFolderOne = new TestFolder();
		final TestFolder testFolderTwo = new TestFolder();
		final TestFolder parentTestFolder = new TestFolder();
		parentTestFolder.setName("different name");
		testFolderTwo.setParentTestFolder(parentTestFolder);
		testFolderOne.setParentTestFolder(new TestFolder());
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void isExactMatch_comparingTestFolderWithNullParentToTestFolderWithNonNullFolder_shouldReturnFalse() {
		final TestFolder testFolderOne = new TestFolder();
		final TestFolder testFolderTwo = new TestFolder();
		final TestFolder parentTestFolder = new TestFolder();
		testFolderTwo.setParentTestFolder(parentTestFolder);
		Assert.assertFalse(testFolderOne.isExactMatch(testFolderTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final TestFolder testFolder = new TestFolder();
		testFolder.populateFields(
				new GenericEntity("test-folder", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(testFolder.getDescription(), "");
		Assert.assertEquals(testFolder.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(testFolder.getName(), "");
		Assert.assertEquals(testFolder.getParentId(), Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllTestFolderFieldsPopulated_shouldSetAllTestFolderFields() {
		final TestFolder testFolder = new TestFolder();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		testFolder.populateFields(sourceEntity);
		Assert.assertEquals(testFolder.getDescription(),
				sourceEntity.getFieldValues(TestFolderField.DESCRIPTION.getName()).get(0));
		Assert.assertTrue(testFolder.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestFolderField.ID.getName()).get(0)));
		Assert.assertEquals(testFolder.getName(), sourceEntity.getFieldValues(TestFolderField.NAME.getName()).get(0));
		Assert.assertTrue(testFolder.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestFolderField.PARENT_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingNoParentFolderSet_shouldSetNewParentFolder() {
		final TestFolder testFolder = new TestFolder();
		final TestFolder parentFolder = new TestFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testFolder.populateFields(sourceEntity);
		Assert.assertEquals(testFolder.getParentTestFolder(), parentFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentFolderSet_shouldSetNewParentFolder() {
		final TestFolder testFolder = new TestFolder();
		testFolder.setParentTestFolder(new TestFolder());
		final TestFolder parentFolder = new TestFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testFolder.populateFields(sourceEntity);
		Assert.assertEquals(testFolder.getParentTestFolder(), parentFolder);
	}

	@Test
	public void setParentTestFolder_withTestFolderAlreadySet_shouldSetParentTestFolder() {
		final TestFolder folder = new TestFolder();
		final TestFolder parentFolder = new TestFolder();
		parentFolder.setName("TheName");
		folder.setParentTestFolder(parentFolder);
		folder.setParentTestFolder(new TestFolder());
		Assert.assertEquals(folder.getParentTestFolder(), new TestFolder());

	}

	@Test
	public void toString_withNoParentTestFolderSet_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final TestFolder testFolderOne = createFullTestFolder();
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "ReleaseFolder"));
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestFolderSet_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final TestFolder testFolderOne = createFullTestFolder();
		final TestFolder parentFolder = new TestFolder();
		parentFolder.setName("TheNameForParent");
		testFolderOne.setParentTestFolder(parentFolder);
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "ReleaseFolder"));
		Assert.assertTrue(StringUtils.contains(testFolderOne.toString(), "TheNameForParent"));
	}

	private TestFolder createFullTestFolder() {
		final TestFolder testFolder = new TestFolder();
		testFolder.setDescription("description");
		testFolder.setId(1337);
		testFolder.setName("name");
		testFolder.setParentId(1776);
		return testFolder;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestFolderField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(TestFolderField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(TestFolderField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(TestFolderField.PARENT_ID.getName(), Arrays.asList("90210")));
		return new GenericEntity("test-folder", fields);
	}
}
