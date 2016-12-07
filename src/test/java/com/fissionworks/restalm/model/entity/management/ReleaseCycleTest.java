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

import com.fissionworks.restalm.constants.field.ReleaseCycleField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class ReleaseCycleTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final ReleaseCycle releaseCycle = createFullReleaseCycle();
		final GenericEntity createdEntity = releaseCycle.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.DESCRIPTION.getName()),
				Arrays.asList(releaseCycle.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.END_DATE.getName()),
				Arrays.asList(releaseCycle.getEndDate()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.ID.getName()),
				Arrays.asList(String.valueOf(releaseCycle.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.NAME.getName()),
				Arrays.asList(releaseCycle.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(releaseCycle.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(ReleaseCycleField.START_DATE.getName()),
				Arrays.asList(releaseCycle.getStartDate()));
	}

	@Test
	public void createEntity_withReleaseCycleHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final ReleaseCycle sourceReleaseCycle = new ReleaseCycle();
		final GenericEntity createdEntity = sourceReleaseCycle.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseCycleField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseCycleField.PARENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseCycleField.END_DATE.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(ReleaseCycleField.START_DATE.getName()));
	}

	@Test
	public void equals_comparingReleaseCycleToAnEqualObject_shouldReturnTrue() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertTrue(releaseCycleOne.equals(createFullReleaseCycle()));
	}

	@Test
	public void equals_comparingReleaseCycleToAnotherObjectType_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertFalse(releaseCycleOne.equals(new Object()));
	}

	@Test
	public void equals_comparingReleaseCycleToItself_shouldReturnTrue() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertTrue(releaseCycleOne.equals(releaseCycleOne));
	}

	@Test
	public void equals_comparingReleaseCycleToNull_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertFalse(releaseCycleOne.equals(null));
	}

	@Test
	public void equals_comparingReleaseCycleToReleaseCycleWithDifferentId_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setId(1234);
		Assert.assertFalse(releaseCycleOne.equals(releaseCycleTwo));
	}

	@Test
	public void equals_comparingReleaseCycleToReleaseCycleWithDifferentName_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setName("different");
		Assert.assertFalse(releaseCycleOne.equals(releaseCycleTwo));
	}

	@Test
	public void equals_comparingReleaseCycleToReleaseCycleWithDifferentParentId_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setParentId(123456);
		Assert.assertFalse(releaseCycleOne.equals(releaseCycleTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new ReleaseCycle().getEntityCollectionType(), "release-cycles");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new ReleaseCycle().getEntityType(), "release-cycle");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setDescription("<b>bold description</b>");
		Assert.assertEquals(releaseCycle.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentRelease_withNoReleaseSet_shouldReturnDefaultRelease() {
		Assert.assertEquals(new ReleaseCycle().getParentRelease(), new Release());
	}

	@Test
	public void getParentRelease_withParentReleaseSet_shouldReturnRelease() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		final Release release = new Release();
		release.setDescription("release description");
		release.setEndDate("2014-09-08");
		release.setId(1337);
		release.setName("release name");
		release.setParentId(1776);
		release.setStartDate("1900-07-04");
		releaseCycle.setParentRelease(release);
		Assert.assertEquals(releaseCycle.getParentRelease(), release);
	}

	@Test
	public void hashCode_forEqualReleaseCycles_shouldBeEqual() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		Assert.assertEquals(releaseCycleOne.hashCode(), releaseCycleTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualReleaseCycles_shouldNotBeEqual() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleOne.setParentId(1234);
		Assert.assertNotEquals(releaseCycleOne.hashCode(), releaseCycleTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetEndDateAndStartDateToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		Assert.assertEquals(releaseCycle.getEndDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(releaseCycle.getStartDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToExactlyMatchingReleaseCycleWithNoParentRelease_shouldReturnTrue() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		Assert.assertTrue(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToExactlyMatchingReleaseCycleWithParentRelease_shouldReturnTrue() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleOne.setParentRelease(new Release());
		releaseCycleTwo.setParentRelease(new Release());
		Assert.assertTrue(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToItself_shouldReturnTrue() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertTrue(releaseCycleOne.isExactMatch(releaseCycleOne));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToReleasCycleeWithDifferentStartDate_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setStartDate("2013-01-02");
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToReleaseCycleThatDoesNotSatisyEquals_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setParentId(123456);
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToReleaseCycleWithDifferentDescription_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setDescription("different");
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToReleaseCycleWithDifferentEndDate_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		releaseCycleTwo.setEndDate("2013-01-02");
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleToReleaseCycleWithDifferentParentRelease_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		final ReleaseCycle releaseCycleTwo = createFullReleaseCycle();
		final Release release = new Release();
		release.setName("theName");
		releaseCycleOne.setParentRelease(new Release());
		releaseCycleTwo.setParentRelease(release);
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void isExactMatch_comparingReleaseCycleWithNullParentReleaseToReleaseCycleWithParentRelease_shouldReturnFalse() {
		final ReleaseCycle releaseCycleOne = new ReleaseCycle();
		final ReleaseCycle releaseCycleTwo = new ReleaseCycle();
		releaseCycleTwo.setParentRelease(new Release());
		Assert.assertFalse(releaseCycleOne.isExactMatch(releaseCycleTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.populateFields(
				new GenericEntity("release-cycle", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(releaseCycle.getDescription(), "");
		Assert.assertEquals(releaseCycle.getEndDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(releaseCycle.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(releaseCycle.getName(), "");
		Assert.assertEquals(releaseCycle.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(releaseCycle.getStartDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void populateFields_withEntityHavingAllReleaseCycleFieldsPopulated_shouldSetAllReleaseCycleFields() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		releaseCycle.populateFields(sourceEntity);
		Assert.assertEquals(releaseCycle.getDescription(),
				sourceEntity.getFieldValues(ReleaseCycleField.DESCRIPTION.getName()).get(0));
		Assert.assertEquals(releaseCycle.getEndDate(),
				sourceEntity.getFieldValues(ReleaseCycleField.END_DATE.getName()).get(0));
		Assert.assertTrue(releaseCycle.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(ReleaseCycleField.ID.getName()).get(0)));
		Assert.assertEquals(releaseCycle.getName(),
				sourceEntity.getFieldValues(ReleaseCycleField.NAME.getName()).get(0));
		Assert.assertTrue(releaseCycle.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(ReleaseCycleField.PARENT_ID.getName()).get(0)));
		Assert.assertEquals(releaseCycle.getStartDate(),
				sourceEntity.getFieldValues(ReleaseCycleField.START_DATE.getName()).get(0));
	}

	@Test
	public void populateFields_withEntityHavingParentReleaseWithNoPreviousParentReleaseSet_shouldSetParentRelease() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> releaseFields = new ArrayList<>();
		releaseFields.add(new Field(ReleaseField.NAME.getName(), Arrays.asList("theName")));
		releaseFields.add(new Field(ReleaseField.ID.getName(), Arrays.asList("1337")));
		releaseFields.add(new Field(ReleaseField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		releaseFields.add(new Field(ReleaseField.PARENT_ID.getName(), Arrays.asList("1776")));
		final Release expectedRelease = new Release();
		expectedRelease.populateFields(new GenericEntity("release", releaseFields));
		sourceEntity.addRelatedEntity(new GenericEntity("release", releaseFields));

		final ReleaseCycle actualReleaseCycle = new ReleaseCycle();
		actualReleaseCycle.populateFields(sourceEntity);

		Assert.assertEquals(actualReleaseCycle.getParentRelease(), expectedRelease);
	}

	@Test
	public void populateFields_withEntityHavingParentReleaseWithPreviousParentReleaseSet_shouldSetParentRelease() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final List<Field> releaseFields = new ArrayList<>();
		releaseFields.add(new Field(ReleaseField.NAME.getName(), Arrays.asList("theName")));
		final Release expectedRelease = new Release();
		expectedRelease.populateFields(new GenericEntity("release", releaseFields));
		sourceEntity.addRelatedEntity(new GenericEntity("release", releaseFields));

		final ReleaseCycle actualreleaseCycle = new ReleaseCycle();
		actualreleaseCycle.setParentRelease(new Release());
		actualreleaseCycle.populateFields(sourceEntity);

		Assert.assertEquals(actualreleaseCycle.getParentRelease(), expectedRelease);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setEndDate_withIncorrectDateFormat_shouldThrowException() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		releaseCycleOne.setEndDate("June 23, 1967");
	}

	@Test
	public void setParentRelease_withParentReleaseAlreadySet_shouldSetNewParentRelease() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		releaseCycleOne.setParentRelease(new Release());
		final Release release = new Release();
		release.setName("theName");
		releaseCycleOne.setParentRelease(release);
		Assert.assertEquals(releaseCycleOne.getParentRelease(), release);

	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setStartDate_withIncorrectDateFormat_shouldThrowException() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		releaseCycleOne.setStartDate("June 23, 1967");
	}

	@Test
	public void toString_withNoParentRelease_shouldReturnNonDefaultStringWithNoParentReleaseInfo() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		Assert.assertTrue(StringUtils.contains(releaseCycleOne.toString(), "ReleaseCycle"));
		Assert.assertTrue(StringUtils.contains(releaseCycleOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentRelease_shouldReturnNonDefaultStringWithParentReleaseInfo() {
		final ReleaseCycle releaseCycleOne = createFullReleaseCycle();
		releaseCycleOne.setParentRelease(new Release());
		Assert.assertTrue(StringUtils.contains(releaseCycleOne.toString(), "ReleaseCycle"));
		Assert.assertTrue(StringUtils.contains(releaseCycleOne.toString(), "<Release>"));
	}

	private ReleaseCycle createFullReleaseCycle() {
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setDescription("release description");
		releaseCycle.setEndDate("2014-09-08");
		releaseCycle.setId(1337);
		releaseCycle.setName("release name");
		releaseCycle.setParentId(1776);
		releaseCycle.setStartDate("1776-07-04");
		return releaseCycle;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(ReleaseCycleField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(ReleaseCycleField.END_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(ReleaseCycleField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(ReleaseCycleField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(ReleaseCycleField.PARENT_ID.getName(), Arrays.asList("90210")));
		fields.add(new Field(ReleaseCycleField.START_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		return new GenericEntity("release-cycle", fields);
	}
}
