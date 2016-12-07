package com.fissionworks.restalm.model.entity.management;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class ReleaseTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final Release release = createFullRelease();
		final GenericEntity createdEntity = release.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.DESCRIPTION.getName()),
				Arrays.asList(release.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.END_DATE.getName()),
				Arrays.asList(release.getEndDate()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.ID.getName()),
				Arrays.asList(String.valueOf(release.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.NAME.getName()),
				Arrays.asList(release.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(release.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseField.START_DATE.getName()),
				Arrays.asList(release.getStartDate()));
	}

	@Test
	public void createEntity_withReleaseHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final Release sourceRelease = new Release();
		final GenericEntity createdEntity = sourceRelease.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseField.PARENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseField.END_DATE.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseField.START_DATE.getName()));
	}

	@Test
	public void equals_comparingReleaseToAnEqualObject_shouldReturnTrue() {
		final Release releaseOne = createFullRelease();
		Assert.assertTrue(releaseOne.equals(createFullRelease()));
	}

	@Test
	public void equals_comparingReleaseToAnotherObjectType_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		Assert.assertFalse(releaseOne.equals(new Object()));
	}

	@Test
	public void equals_comparingReleaseToItself_shouldReturnTrue() {
		final Release releaseOne = createFullRelease();
		Assert.assertTrue(releaseOne.equals(releaseOne));
	}

	@Test
	public void equals_comparingReleaseToNull_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		Assert.assertFalse(releaseOne.equals(null));
	}

	@Test
	public void equals_comparingReleaseToReleaseWithDifferentId_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setId(1234);
		Assert.assertFalse(releaseOne.equals(releaseTwo));
	}

	@Test
	public void equals_comparingReleaseToReleaseWithDifferentName_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setName("different");
		Assert.assertFalse(releaseOne.equals(releaseTwo));
	}

	@Test
	public void equals_comparingReleaseToReleaseWithDifferentParentId_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setParentId(123456);
		Assert.assertFalse(releaseOne.equals(releaseTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new Release().getEntityCollectionType(), "releases");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new Release().getEntityType(), "release");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final Release release = new Release();
		release.setDescription("<b>bold description</b>");
		Assert.assertEquals(release.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentReleaseFolder_withNoReleaseFolderSet_shouldReturnDefaultReleaseFolder() {
		Assert.assertEquals(new Release().getParentReleaseFolder(), new ReleaseFolder());
	}

	@Test
	public void getParentReleaseFolder_withTestFolderSet_shouldReturnTestFolder() {
		final Release release = new Release();
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setId(1337);
		releaseFolder.setName("theName");
		releaseFolder.setDescription("theDescription");
		releaseFolder.setParentId(1776);
		release.setParentReleaseFolder(releaseFolder);
		Assert.assertEquals(release.getParentReleaseFolder(), releaseFolder);
	}

	@Test
	public void hashCode_forEqualTests_shouldBeEqual() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		Assert.assertEquals(releaseOne.hashCode(), releaseTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTests_shouldNotBeEqual() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseOne.setParentId(1234);
		Assert.assertNotEquals(releaseOne.hashCode(), releaseTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetEndDateAndStartDateToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final Release release = new Release();
		Assert.assertEquals(release.getEndDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(release.getStartDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingReleaseToExactlyMatchingReleaseWithParentReleaseFolder_shouldReturnTrue() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseOne.setParentReleaseFolder(new ReleaseFolder());
		releaseTwo.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertTrue(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToExactlyMatchingTestWithNoParentTestFolder_shouldReturnTrue() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		Assert.assertTrue(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToItself_shouldReturnTrue() {
		final Release releaseOne = createFullRelease();
		Assert.assertTrue(releaseOne.isExactMatch(releaseOne));
	}

	@Test
	public void isExactMatch_comparingReleaseToReleaseThatDoesNotSatisyEquals_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setParentId(123456);
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToReleaseWithDifferentDescription_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setDescription("different");
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToReleaseWithDifferentEndDate_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setEndDate("2013-01-02");
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToReleaseWithDifferentParentReleaseFolder_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setName("theName");
		releaseOne.setParentReleaseFolder(new ReleaseFolder());
		releaseTwo.setParentReleaseFolder(releaseFolder);
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseToReleaseWithDifferentStartDate_shouldReturnFalse() {
		final Release releaseOne = createFullRelease();
		final Release releaseTwo = createFullRelease();
		releaseTwo.setStartDate("2013-01-02");
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseWithNullParentTestFolderToTestWithParentTestFolder_shouldReturnFalse() {
		final Release releaseOne = new Release();
		final Release releaseTwo = new Release();
		releaseTwo.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertFalse(releaseOne.isExactMatch(releaseTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final Release release = new Release();
		release.populateFields(
				new GenericEntity("release", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(release.getDescription(), "");
		Assert.assertEquals(release.getEndDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(release.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(release.getName(), "");
		Assert.assertEquals(release.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(release.getStartDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void populateFields_withEntityHavingAllTestFieldsPopulated_shouldSetAllTestFields() {
		final Release release = new Release();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		release.populateFields(sourceEntity);
		Assert.assertEquals(release.getDescription(),
				sourceEntity.getFieldValues(ReleaseField.DESCRIPTION.getName()).get(0));
		Assert.assertEquals(release.getEndDate(), sourceEntity.getFieldValues(ReleaseField.END_DATE.getName()).get(0));
		Assert.assertTrue(
				release.getId() == Integer.valueOf(sourceEntity.getFieldValues(ReleaseField.ID.getName()).get(0)));
		Assert.assertEquals(release.getName(), sourceEntity.getFieldValues(ReleaseField.NAME.getName()).get(0));
		Assert.assertTrue(release.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(ReleaseField.PARENT_ID.getName()).get(0)));
		Assert.assertEquals(release.getStartDate(),
				sourceEntity.getFieldValues(ReleaseField.START_DATE.getName()).get(0));
	}

	@Test
	public void populateFields_withEntityHavingParentReleaseFolderWithNoPreviousFolderSet_shouldSetParentReleaseFolder() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(ReleaseFolderField.NAME.getName(), Arrays.asList("theName")));
		folderFields.add(new Field(ReleaseFolderField.ID.getName(), Arrays.asList("1337")));
		folderFields.add(new Field(ReleaseFolderField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		folderFields.add(new Field(ReleaseFolderField.PARENT_ID.getName(), Arrays.asList("1776")));
		final ReleaseFolder expectedFolder = new ReleaseFolder();
		expectedFolder.populateFields(new GenericEntity("release-folder", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("release-folder", folderFields));

		final Release actualRelease = new Release();
		actualRelease.populateFields(sourceEntity);

		Assert.assertEquals(actualRelease.getParentReleaseFolder(), expectedFolder);
	}

	@Test
	public void populateFields_withEntityHavingParentReleaseFolderWithPreviousFolderSet_shouldSetParentReleaseFolder() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> folderFields = new ArrayList<>();
		folderFields.add(new Field(ReleaseFolderField.NAME.getName(), Arrays.asList("theName")));
		final ReleaseFolder expectedFolder = new ReleaseFolder();
		expectedFolder.populateFields(new GenericEntity("release-folder", folderFields));
		sourceEntity.addRelatedEntity(new GenericEntity("release-folder", folderFields));

		final Release actualrelease = new Release();
		actualrelease.setParentReleaseFolder(new ReleaseFolder());
		actualrelease.populateFields(sourceEntity);

		Assert.assertEquals(actualrelease.getParentReleaseFolder(), expectedFolder);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setEndDate_withIncorrectDateFormat_shouldThrowException() {
		final Release releaseOne = createFullRelease();
		releaseOne.setEndDate("June 23, 1967");
	}

	@Test
	public void setParentReleaseFolder_withFolderAlreadySet_shouldSetNewParentReleaseFolder() {
		final Release releaseOne = createFullRelease();
		releaseOne.setParentReleaseFolder(new ReleaseFolder());
		final ReleaseFolder releaseFolder = new ReleaseFolder();
		releaseFolder.setName("theName");
		releaseOne.setParentReleaseFolder(releaseFolder);
		Assert.assertEquals(releaseOne.getParentReleaseFolder(), releaseFolder);

	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setStartDate_withIncorrectDateFormat_shouldThrowException() {
		final Release releaseOne = createFullRelease();
		releaseOne.setStartDate("June 23, 1967");
	}

	@Test
	public void toString_withNoParentReleaseFolder_shouldReturnNonDefaultStringWithNoParentFolderInfo() {
		final Release releaseOne = createFullRelease();
		Assert.assertTrue(StringUtils.contains(releaseOne.toString(), "Release"));
		Assert.assertTrue(StringUtils.contains(releaseOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestFolder_shouldReturnNonDefaultStringWithParentFolderInfo() {
		final Release releaseOne = createFullRelease();
		releaseOne.setParentReleaseFolder(new ReleaseFolder());
		Assert.assertTrue(StringUtils.contains(releaseOne.toString(), "Release"));
		Assert.assertTrue(StringUtils.contains(releaseOne.toString(), "<ReleaseFolder>"));
	}

	private Release createFullRelease() {
		final Release release = new Release();
		release.setDescription("release description");
		release.setEndDate("2014-09-08");
		release.setId(1337);
		release.setName("release name");
		release.setParentId(1776);
		release.setStartDate("1776-07-04");
		return release;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(ReleaseField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(
				new Field(ReleaseField.END_DATE.getName(), Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(ReleaseField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(ReleaseField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(ReleaseField.PARENT_ID.getName(), Arrays.asList("90210")));
		fields.add(new Field(ReleaseField.START_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		return new GenericEntity("release", fields);
	}
}
