package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.constants.field.TestSetField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class TestSetTest {

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final TestSet sourceTestSet = createFullTestSet();
		final GenericEntity createdEntity = sourceTestSet.createEntity();

		Assert.assertEquals(createdEntity.getFieldValues(TestSetField.DESCRIPTION.getName()),
				Arrays.asList(sourceTestSet.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetField.ID.getName()),
				Arrays.asList(String.valueOf(sourceTestSet.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetField.NAME.getName()),
				Arrays.asList(sourceTestSet.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceTestSet.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(TestSetField.TYPE.getName()),
				Arrays.asList(sourceTestSet.getSubtype()));
	}

	@Test
	public void createEntity_withTestSetHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final TestSet sourceTestSet = new TestSet();
		final GenericEntity createdEntity = sourceTestSet.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(TestSetField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(TestSetField.PARENT_ID.getName()));
	}

	@Test
	public void equals_comparingTestFolderToItself_shouldReturnTrue() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertTrue(testSetOne.equals(testSetOne));
	}

	@Test
	public void equals_comparingTestFolderToNull_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertFalse(testSetOne.equals(null));
	}

	@Test
	public void equals_comparingTestSetToAnEqualObject_shouldReturnTrue() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertTrue(testSetOne.equals(createFullTestSet()));
	}

	@Test
	public void equals_comparingTestSetToAnotherObjectType_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertFalse(testSetOne.equals(new Object()));
	}

	@Test
	public void equals_comparingTestSetToTestSetWithDifferentId_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setId(1234);
		Assert.assertFalse(testSetOne.equals(testSetTwo));
	}

	@Test
	public void equals_comparingTestSetToTestSetWithDifferentName_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setName("different");
		Assert.assertFalse(testSetOne.equals(testSetTwo));
	}

	@Test
	public void equals_comparingTestSetToTestSetWithDifferentParentId_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setParentId(123456);
		Assert.assertFalse(testSetOne.equals(testSetTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new TestSet().getEntityCollectionType(), "test-sets");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new TestSet().getEntityType(), "test-set");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final TestSet testSet = new TestSet();
		testSet.setDescription("<b>bold description</b>");
		Assert.assertEquals(testSet.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentTestFolder_withNoParentSet_shouldReturnDefaultFolder() {
		Assert.assertEquals(new TestSet().getParentTestSetFolder(), new TestSetFolder());
	}

	@Test
	public void getParentTestFolder_withParentSet_shouldReturnParentFolder() {
		final TestSet testSet = new TestSet();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		testSet.setParentTestSetFolder(parentFolder);
		Assert.assertEquals(testSet.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void hashCode_forEqualTestSets_shouldBeEqual() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		Assert.assertEquals(testSetOne.hashCode(), testSetTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTestSets_shouldNotBeEqual() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetOne.setParentId(1234);
		Assert.assertNotEquals(testSetOne.hashCode(), testSetTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingTestSetToExactlyMatchingTestSet_shouldReturnTrue() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		Assert.assertTrue(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetToExactlyMatchingTestSetWithParentFolders_shouldReturnTrue() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetOne.setParentTestSetFolder(new TestSetFolder());
		testSetTwo.setParentTestSetFolder(new TestSetFolder());
		Assert.assertTrue(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetToItself_shouldReturnTrue() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertTrue(testSetOne.isExactMatch(testSetOne));
	}

	@Test
	public void isExactMatch_comparingTestSetToTestSetThatDoesNotSatisyEquals_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setParentId(123456);
		Assert.assertFalse(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetToTestSetWithDifferentDescription_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setDescription("newDescription");
		Assert.assertFalse(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetToTestSetWithDifferentParentTestSetFolder_shouldReturnFalse() {
		final TestSet testSetOne = new TestSet();
		final TestSet testSetTwo = new TestSet();
		final TestSetFolder parentTestFolder = new TestSetFolder();
		parentTestFolder.setName("different name");
		testSetTwo.setParentTestSetFolder(parentTestFolder);
		testSetOne.setParentTestSetFolder(new TestSetFolder());
		Assert.assertFalse(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetToTestSetWithDifferentSubtype_shouldReturnFalse() {
		final TestSet testSetOne = createFullTestSet();
		final TestSet testSetTwo = createFullTestSet();
		testSetTwo.setSubtype("newSubType");
		Assert.assertFalse(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void isExactMatch_comparingTestSetWithNullParentToTestSetWithNonNullFolder_shouldReturnFalse() {
		final TestSet testSetOne = new TestSet();
		final TestSet testSetTwo = new TestSet();
		final TestSetFolder parentTestFolder = new TestSetFolder();
		testSetTwo.setParentTestSetFolder(parentTestFolder);
		Assert.assertFalse(testSetOne.isExactMatch(testSetTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final TestSet testSet = new TestSet();
		testSet.populateFields(
				new GenericEntity("test-set", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(testSet.getDescription(), "");
		Assert.assertEquals(testSet.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(testSet.getName(), "");
		Assert.assertEquals(testSet.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(testSet.getSubtype(), TestSet.DEFAULT_SUBTYPE);
	}

	@Test
	public void populateFields_withEntityHavingAllTestSetFieldsPopulated_shouldSetAllTestSetFields() {
		final TestSet testSet = new TestSet();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		testSet.populateFields(sourceEntity);
		Assert.assertEquals(testSet.getDescription(),
				sourceEntity.getFieldValues(TestSetField.DESCRIPTION.getName()).get(0));
		Assert.assertTrue(
				testSet.getId() == Integer.valueOf(sourceEntity.getFieldValues(TestFolderField.ID.getName()).get(0)));
		Assert.assertEquals(testSet.getName(), sourceEntity.getFieldValues(TestSetField.NAME.getName()).get(0));
		Assert.assertTrue(testSet.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(TestSetField.PARENT_ID.getName()).get(0)));
		Assert.assertEquals(testSet.getSubtype(), sourceEntity.getFieldValues(TestSetField.TYPE.getName()).get(0));
	}

	@Test
	public void populateFields_withEntityHavingNoParentFolderSet_shouldSetNewParentFolder() {
		final TestSet testSet = new TestSet();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testSet.populateFields(sourceEntity);
		Assert.assertEquals(testSet.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentFolderSet_shouldSetNewParentFolder() {
		final TestSet testSet = new TestSet();
		testSet.setParentTestSetFolder(new TestSetFolder());
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("test-set",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		testSet.populateFields(sourceEntity);
		Assert.assertEquals(testSet.getParentTestSetFolder(), parentFolder);
	}

	@Test
	public void setParentTestFolder_withTestFolderAlreadySet_shouldSetParentTestFolder() {
		final TestSet testSet = new TestSet();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("TheName");
		testSet.setParentTestSetFolder(new TestSetFolder());
		testSet.setParentTestSetFolder(parentFolder);
		Assert.assertEquals(testSet.getParentTestSetFolder(), parentFolder);

	}

	@Test
	public void toString_withNoParentTestFolderSet_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final TestSet testSetOne = createFullTestSet();
		Assert.assertTrue(StringUtils.contains(testSetOne.toString(), "TestSet"));
		Assert.assertTrue(StringUtils.contains(testSetOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestFolderSet_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final TestSet testSetOne = createFullTestSet();
		final TestSetFolder parentFolder = new TestSetFolder();
		parentFolder.setName("TheNameForParent");
		testSetOne.setParentTestSetFolder(parentFolder);
		Assert.assertTrue(StringUtils.contains(testSetOne.toString(), "TestSet"));
		Assert.assertTrue(StringUtils.contains(testSetOne.toString(), "TheNameForParent"));
	}

	private TestSet createFullTestSet() {
		final TestSet testSet = new TestSet();
		testSet.setDescription("description");
		testSet.setId(1337);
		testSet.setName("name");
		testSet.setParentId(1776);
		return testSet;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestSetField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(TestSetField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(TestSetField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(TestSetField.PARENT_ID.getName(), Arrays.asList("90210")));
		fields.add(new Field(TestSetField.TYPE.getName(), Arrays.asList("test.type")));
		return new GenericEntity("test-set", fields);
	}
}
