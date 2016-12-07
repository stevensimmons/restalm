package com.fissionworks.restalm.model.entity.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class ReleaseFolderTest {

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final ReleaseFolder sourceReleaseFolder = createFullReleaseFolder();
		final GenericEntity createdEntity = sourceReleaseFolder.createEntity();

		Assert.assertEquals(createdEntity.getFieldValues(ReleaseFolderField.DESCRIPTION.getName()),
				Arrays.asList(sourceReleaseFolder.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseFolderField.ID.getName()),
				Arrays.asList(String.valueOf(sourceReleaseFolder.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseFolderField.NAME.getName()),
				Arrays.asList(sourceReleaseFolder.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseFolderField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceReleaseFolder.getParentId())));
	}

	@Test
	public void createEntity_withReleaseFolderHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final ReleaseFolder sourceReleaseFolder = new ReleaseFolder();
		final GenericEntity createdEntity = sourceReleaseFolder.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseFolderField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseFolderField.PARENT_ID.getName()));
	}

	@Test
	public void equals_comparingReleaseFolderToAnEqualObject_shouldReturnTrue() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertTrue(releaseFolderOne.equals(createFullReleaseFolder()));
	}

	@Test
	public void equals_comparingReleaseFolderToAnotherObjectType_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertFalse(releaseFolderOne.equals(new Object()));
	}

	@Test
	public void equals_comparingReleaseFolderToItself_shouldReturnTrue() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertTrue(releaseFolderOne.equals(releaseFolderOne));
	}

	@Test
	public void equals_comparingReleaseFolderToNull_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertFalse(releaseFolderOne.equals(null));
	}

	@Test
	public void equals_comparingReleaseFolderToReleaseFolderWithDifferentId_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderTwo.setId(1234);
		Assert.assertFalse(releaseFolderOne.equals(releaseFolderTwo));
	}

	@Test
	public void equals_comparingReleaseFolderToReleaseFolderWithDifferentName_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderTwo.setName("different");
		Assert.assertFalse(releaseFolderOne.equals(releaseFolderTwo));
	}

	@Test
	public void equals_comparingReleaseFolderToReleaseFolderWithDifferentParentId_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderTwo.setParentId(123456);
		Assert.assertFalse(releaseFolderOne.equals(releaseFolderTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new ReleaseFolder().getEntityCollectionType(), "release-folders");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new ReleaseFolder().getEntityType(), "release-folder");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		releaseFolderOne.setDescription("<b>bold description</b>");
		Assert.assertEquals(releaseFolderOne.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentReleaseFolder_withNoParentSet_shouldReturnDefaultFolder() {
		Assert.assertEquals(new ReleaseFolder().getParentReleaseFolder(), new ReleaseFolder());
	}

	@Test
	public void getParentReleaseFolder_withParentSet_shouldReturnParentFolder() {
		final ReleaseFolder folder = new ReleaseFolder();
		final ReleaseFolder parentFolder = new ReleaseFolder();
		parentFolder.setName("parent name");
		folder.setParentReleaseFolder(parentFolder);
		Assert.assertEquals(folder.getParentReleaseFolder(), parentFolder);
	}

	@Test
	public void hashCode_forEqualReleaseFolders_shouldBeEqual() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		Assert.assertEquals(releaseFolderOne.hashCode(), releaseFolderTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualReleaseFolders_shouldNotBeEqual() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderOne.setParentId(1234);
		Assert.assertNotEquals(releaseFolderOne.hashCode(), releaseFolderTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToExactlyMatchingReleaseFolder_shouldReturnTrue() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		Assert.assertTrue(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToExactlyMatchingReleaseFolderWithParentFolders_shouldReturnTrue() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderOne.setParentReleaseFolder(new ReleaseFolder());
		releaseFolderTwo.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertTrue(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToItself_shouldReturnTrue() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertTrue(releaseFolderOne.isExactMatch(releaseFolderOne));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToReleaseFolderThatDoesNotSatisyEquals_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderTwo.setParentId(123456);
		Assert.assertFalse(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToReleaseFolderWithDifferentDescription_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder releaseFolderTwo = createFullReleaseFolder();
		releaseFolderTwo.setDescription("newDescription");
		Assert.assertFalse(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderToReleaseFolderWithDifferentParentReleaseFolder_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		final ReleaseFolder releaseFolderTwo = new ReleaseFolder();
		final ReleaseFolder parentReleaseFolder = new ReleaseFolder();
		parentReleaseFolder.setName("different name");
		releaseFolderTwo.setParentReleaseFolder(parentReleaseFolder);
		releaseFolderOne.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertFalse(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseFolderWithNullParentToReleaseFolderWithNonNullFolder_shouldReturnFalse() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		final ReleaseFolder releaseFolderTwo = new ReleaseFolder();
		final ReleaseFolder parentReleaseFolder = new ReleaseFolder();
		releaseFolderTwo.setParentReleaseFolder(parentReleaseFolder);
		Assert.assertFalse(releaseFolderOne.isExactMatch(releaseFolderTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		releaseFolderOne.populateFields(
				new GenericEntity("release-folder", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(releaseFolderOne.getDescription(), "");
		Assert.assertEquals(releaseFolderOne.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(releaseFolderOne.getName(), "");
		Assert.assertEquals(releaseFolderOne.getParentId(), Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllReleaseFolderFieldsPopulated_shouldSetAllReleaseFolderFields() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		releaseFolderOne.populateFields(sourceEntity);
		Assert.assertEquals(releaseFolderOne.getDescription(),
				sourceEntity.getFieldValues(ReleaseFolderField.DESCRIPTION.getName()).get(0));
		Assert.assertTrue(releaseFolderOne.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(ReleaseFolderField.ID.getName()).get(0)));
		Assert.assertEquals(releaseFolderOne.getName(),
				sourceEntity.getFieldValues(ReleaseFolderField.NAME.getName()).get(0));
		Assert.assertTrue(releaseFolderOne.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(ReleaseFolderField.PARENT_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingNoParentFolderSet_shouldSetNewParentFolder() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		final ReleaseFolder parentFolder = new ReleaseFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("release-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		releaseFolderOne.populateFields(sourceEntity);
		Assert.assertEquals(releaseFolderOne.getParentReleaseFolder(), parentFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentFolderSet_shouldSetNewParentFolder() {
		final ReleaseFolder releaseFolderOne = new ReleaseFolder();
		releaseFolderOne.setParentReleaseFolder(new ReleaseFolder());
		final ReleaseFolder parentFolder = new ReleaseFolder();
		parentFolder.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("release-folder",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parentFolder.createEntity());
		releaseFolderOne.populateFields(sourceEntity);
		Assert.assertEquals(releaseFolderOne.getParentReleaseFolder(), parentFolder);
	}

	@Test
	public void setParentReleaseFolder_withReleaseFolderAlreadySet_shouldSetParentReleaseFolder() {
		final ReleaseFolder folder = new ReleaseFolder();
		final ReleaseFolder parentFolder = new ReleaseFolder();
		parentFolder.setName("TheName");
		folder.setParentReleaseFolder(parentFolder);
		folder.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertEquals(folder.getParentReleaseFolder(), new ReleaseFolder());
	}

	@Test
	public void toString_withNoParentReleaseFolderSet_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		Assert.assertTrue(StringUtils.contains(releaseFolderOne.toString(), "ReleaseFolder"));
		Assert.assertTrue(StringUtils.contains(releaseFolderOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentReleaseFolderSet_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final ReleaseFolder releaseFolderOne = createFullReleaseFolder();
		final ReleaseFolder parentFolder = new ReleaseFolder();
		parentFolder.setName("TheNameForParent");
		releaseFolderOne.setParentReleaseFolder(parentFolder);
		Assert.assertTrue(StringUtils.contains(releaseFolderOne.toString(), "ReleaseFolder"));
		Assert.assertTrue(StringUtils.contains(releaseFolderOne.toString(), "TheNameForParent"));
	}

	private ReleaseFolder createFullReleaseFolder() {
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setDescription("description");
		releaseFolder.setId(1337);
		releaseFolder.setName("name");
		releaseFolder.setParentId(1776);
		return releaseFolder;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(ReleaseFolderField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(ReleaseFolderField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(ReleaseFolderField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(ReleaseFolderField.PARENT_ID.getName(), Arrays.asList("90210")));
		return new GenericEntity("release-folder", fields);
	}
}
