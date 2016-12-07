package com.fissionworks.restalm.model.entity.defects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.DefectField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;
import com.fissionworks.restalm.model.entity.testplan.TestFolder;

public class DefectTest {

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final Defect sourceDefect = createFullDefect();
		final GenericEntity createdEntity = sourceDefect.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.ASSIGNED_TO.getName()),
				Arrays.asList(sourceDefect.getAssignedTo()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.CLOSING_DATE.getName()),
				Arrays.asList(sourceDefect.getClosingDate()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.COMMENTS.getName()),
				Arrays.asList(sourceDefect.getComments()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.DESCRIPTION.getName()),
				Arrays.asList(sourceDefect.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.DETECTED_BY.getName()),
				Arrays.asList(sourceDefect.getDetectedBy()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.DETECTED_IN_CYCLE_ID.getName()),
				Arrays.asList(String.valueOf(sourceDefect.getDetectedInReleaseCycleId())));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.DETECTED_IN_RELEASE_ID.getName()),
				Arrays.asList(String.valueOf(sourceDefect.getDetectedInReleaseId())));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.DETECTED_ON_DATE.getName()),
				Arrays.asList(sourceDefect.getDetectedOnDate()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.ID.getName()),
				Arrays.asList(String.valueOf(sourceDefect.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.MODIFIED.getName()),
				Arrays.asList(sourceDefect.getModified()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.SEVERITY.getName()),
				Arrays.asList(sourceDefect.getSeverity()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.STATUS.getName()),
				Arrays.asList(sourceDefect.getStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(DefectField.SUMMARY.getName()),
				Arrays.asList(sourceDefect.getSummary()));
	}

	@Test
	public void createEntity_withDefectHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final Defect sourceDefect = new Defect();
		final GenericEntity createdEntity = sourceDefect.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.CLOSING_DATE.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.DETECTED_IN_CYCLE_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.DETECTED_IN_RELEASE_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.DETECTED_ON_DATE.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DefectField.MODIFIED.getName()));
	}

	@Test
	public void equals_comparingDefectToAnEqualObject_shouldReturnTrue() {
		final Defect defectOne = createFullDefect();
		Assert.assertTrue(defectOne.equals(createFullDefect()));
	}

	@Test
	public void equals_comparingDefectToAnotherObjectType_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		Assert.assertFalse(defectOne.equals(new Object()));
	}

	@Test
	public void equals_comparingDefectToDefectWithDifferentDescription_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectTwo.setDescription("different description");
		Assert.assertFalse(defectOne.equals(defectTwo));
	}

	@Test
	public void equals_comparingDefectToDefectWithDifferentId_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectTwo.setId(1234);
		Assert.assertFalse(defectOne.equals(defectTwo));
	}

	@Test
	public void equals_comparingDefectToDefectWithDifferentSummary_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectTwo.setSummary("different summary");
		Assert.assertFalse(defectOne.equals(defectTwo));
	}

	@Test
	public void equals_comparingDefectToItself_shouldReturnTrue() {
		final Defect defectOne = createFullDefect();
		Assert.assertTrue(defectOne.equals(defectOne));
	}

	@Test
	public void equals_comparingDefectToNull_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		Assert.assertFalse(defectOne.equals(null));
	}

	@Test
	public void getDetectedInRelease_withDetectedInReleaseSet_shouldReturnDetectedInRelease() {
		final Defect defect = new Defect();
		final Release release = new Release();
		release.setId(1337);
		release.setName("theName");
		release.setDescription("theDescription");
		release.setParentId(1776);
		defect.setDetectedInRelease(release);
		Assert.assertEquals(defect.getDetectedInRelease(), release);
	}

	@Test
	public void getDetectedInRelease_withNoDetectedInReleaseSet_shouldReturnDefaultRelease() {
		Assert.assertEquals(new Defect().getDetectedInRelease(), new Release());
	}

	@Test
	public void getDetectedInReleaseCycle_withDetectedInReleaseCycleSet_shouldReturnDetectedInReleaseCycle() {
		final Defect defect = new Defect();
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setId(1337);
		releaseCycle.setName("theName");
		releaseCycle.setDescription("theDescription");
		releaseCycle.setParentId(1776);
		defect.setDetectedInReleaseCycle(releaseCycle);
		Assert.assertEquals(defect.getDetectedInReleaseCycle(), releaseCycle);
	}

	@Test
	public void getDetectedInReleaseCycle_withNoDetectedInReleaseCycleSet_shouldReturnDefaultReleaseCycle() {
		Assert.assertEquals(new Defect().getDetectedInReleaseCycle(), new ReleaseCycle());
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new Defect().getEntityCollectionType(), "defects");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new Defect().getEntityType(), "defect");
	}

	@Test
	public void getFullComments_withCommentsThatHaveHtml_shouldReturnFullComments() {
		final Defect defect = new Defect();
		defect.setDescription("<b>bold description</b>");
		Assert.assertEquals(defect.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final Defect defect = new Defect();
		defect.setComments("<b>bold comments</b>");
		Assert.assertEquals(defect.getFullComments(), "<b>bold comments</b>");
	}

	@Test
	public void hashCode_forEqualDefects_shouldBeEqual() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		Assert.assertEquals(defectOne.hashCode(), defectTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualDefects_shouldNotBeEqual() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectTwo.setId(1234);
		Assert.assertNotEquals(defectOne.hashCode(), defectTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetAllDateFieldsToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final Defect defect = new Defect();
		Assert.assertEquals(defect.getClosingDate(), DateFormatUtils.format(cal, "yyyy-MM-dd"));
		Assert.assertEquals(defect.getDetectedOnDate(), DateFormatUtils.format(cal, "yyyy-MM-dd"));
		Assert.assertEquals(defect.getModified(), DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void isExactMatch_comparingAlmTestToItself_shouldReturnTrue() {
		final Defect defectOne = createFullDefect();
		Assert.assertTrue(defectOne.isExactMatch(defectOne));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectThatDoesNotSatisyEquals_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectTwo.setId(123456);
		Assert.assertFalse(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentAssignedTo_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setAssignedTo("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentClosingDate_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setClosingDate("2014-09-02");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentComments_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setComments("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedBy_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setDetectedBy("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedInRelease_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		final Release release = new Release();
		release.setName("theName");
		defectOne.setDetectedInRelease(new Release());
		defectTwo.setDetectedInRelease(release);
		Assert.assertFalse(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedInReleaseCycle_shouldReturnFalse() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		final ReleaseCycle releaseCycle = new ReleaseCycle();
		releaseCycle.setName("theName");
		defectOne.setDetectedInReleaseCycle(new ReleaseCycle());
		defectTwo.setDetectedInReleaseCycle(releaseCycle);
		Assert.assertFalse(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedInReleaseCycleId_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setDetectedInReleaseCycleId(123456789);
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedInReleaseId_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setDetectedInReleaseId(123456789);
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentDetectedOnDate_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setDetectedOnDate("2014-08-02");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentModified_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setModified("2014-08-02 13:14:16");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentSeverity_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setSeverity("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToDefectWithDifferentStatus_shouldReturnFalse() {
		final Defect testOne = createFullDefect();
		final Defect testTwo = createFullDefect();
		testTwo.setStatus("different");
		Assert.assertFalse(testOne.isExactMatch(testTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToExactlyMatchingDefectWithDetectedInRelease_shouldReturnTrue() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		defectOne.setDetectedInRelease(new Release());
		defectTwo.setDetectedInRelease(new Release());
		defectOne.setDetectedInReleaseCycle(new ReleaseCycle());
		defectTwo.setDetectedInReleaseCycle(new ReleaseCycle());
		Assert.assertTrue(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectToExactlyMatchingTestWithNoRelatedEntities_shouldReturnTrue() {
		final Defect defectOne = createFullDefect();
		final Defect defectTwo = createFullDefect();
		Assert.assertTrue(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectWithNullDetectedInReleaseCycleToTestWithDetectedInReleaseCycle_shouldReturnFalse() {
		final Defect defectOne = new Defect();
		final Defect defectTwo = new Defect();
		defectTwo.setDetectedInReleaseCycle(new ReleaseCycle());
		Assert.assertFalse(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void isExactMatch_comparingDefectWithNullDetectedInReleaseToTestWithDetectedInRelease_shouldReturnFalse() {
		final Defect defectOne = new Defect();
		final Defect defectTwo = new Defect();
		defectTwo.setDetectedInRelease(new Release());
		Assert.assertFalse(defectOne.isExactMatch(defectTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final Defect defect = new Defect();
		defect.populateFields(new GenericEntity("defect", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(defect.getAssignedTo(), "");
		Assert.assertEquals(defect.getClosingDate(), "292269055-12-02");
		Assert.assertEquals(defect.getComments(), "");
		Assert.assertEquals(defect.getDescription(), "");
		Assert.assertEquals(defect.getDetectedBy(), "");
		Assert.assertEquals(defect.getDetectedInReleaseCycleId(), Integer.MIN_VALUE);
		Assert.assertEquals(defect.getDetectedInReleaseId(), Integer.MIN_VALUE);
		Assert.assertEquals(defect.getDetectedOnDate(), "292269055-12-02");
		Assert.assertEquals(defect.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(defect.getModified(), "292269055-12-02 11:47:04");
		Assert.assertEquals(defect.getSeverity(), "");
		Assert.assertEquals(defect.getStatus(), "");
		Assert.assertEquals(defect.getSummary(), "");
	}

	@Test
	public void populateFields_withEntityHavingAllTestInstanceFieldsPopulated_shouldSetAllTestInstanceFields() {
		final Defect defect = new Defect();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getAssignedTo(),
				sourceEntity.getFieldValues(DefectField.ASSIGNED_TO.getName()).get(0));
		Assert.assertEquals(defect.getClosingDate(),
				sourceEntity.getFieldValues(DefectField.CLOSING_DATE.getName()).get(0));
		Assert.assertEquals(defect.getComments(), sourceEntity.getFieldValues(DefectField.COMMENTS.getName()).get(0));
		Assert.assertEquals(defect.getDescription(),
				sourceEntity.getFieldValues(DefectField.DESCRIPTION.getName()).get(0));
		Assert.assertEquals(defect.getDetectedBy(),
				sourceEntity.getFieldValues(DefectField.DETECTED_BY.getName()).get(0));
		Assert.assertTrue(defect.getDetectedInReleaseCycleId() == Integer
				.valueOf(sourceEntity.getFieldValues(DefectField.DETECTED_IN_CYCLE_ID.getName()).get(0)));
		Assert.assertTrue(defect.getDetectedInReleaseId() == Integer
				.valueOf(sourceEntity.getFieldValues(DefectField.DETECTED_IN_RELEASE_ID.getName()).get(0)));
		Assert.assertEquals(defect.getDetectedOnDate(),
				sourceEntity.getFieldValues(DefectField.DETECTED_ON_DATE.getName()).get(0));
		Assert.assertTrue(
				defect.getId() == Integer.valueOf(sourceEntity.getFieldValues(DefectField.ID.getName()).get(0)));
		Assert.assertEquals(defect.getModified(), sourceEntity.getFieldValues(DefectField.MODIFIED.getName()).get(0));
		Assert.assertEquals(defect.getSeverity(), sourceEntity.getFieldValues(DefectField.SEVERITY.getName()).get(0));
		Assert.assertEquals(defect.getStatus(), sourceEntity.getFieldValues(DefectField.STATUS.getName()).get(0));
		Assert.assertEquals(defect.getSummary(), sourceEntity.getFieldValues(DefectField.SUMMARY.getName()).get(0));
	}

	@Test
	public void populateFields_withEntityHavingAnUnrelatedEntitySet_shouldIgnoreUnrelatedEntity() {
		final Defect defect = new Defect();
		final GenericEntity sourceEntity = new GenericEntity("defect",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(new TestFolder().createEntity());
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getDetectedInRelease(), new Release());
		Assert.assertEquals(defect.getDetectedInReleaseCycle(), new ReleaseCycle());
	}

	@Test
	public void populateFields_withEntityHavingDetectedInReleaseCycleSet_shouldSetNewDetectedInReleaseCycle() {
		final Defect defect = new Defect();
		defect.setDetectedInReleaseCycle(new ReleaseCycle());
		final ReleaseCycle detectedInReleaseCycle = new ReleaseCycle();
		detectedInReleaseCycle.setName("detected In Release Cycle name");
		final GenericEntity sourceEntity = new GenericEntity("defect",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(detectedInReleaseCycle.createEntity());
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getDetectedInReleaseCycle(), detectedInReleaseCycle);
	}

	@Test
	public void populateFields_withEntityHavingDetectedInReleaseSet_shouldSetNewDetectedInRelease() {
		final Defect defect = new Defect();
		defect.setDetectedInRelease(new Release());
		final Release detectedInRelease = new Release();
		detectedInRelease.setName("detected In Release name");
		final GenericEntity sourceEntity = new GenericEntity("defect",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(detectedInRelease.createEntity());
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getDetectedInRelease(), detectedInRelease);
	}

	@Test
	public void populateFields_withEntityHavingNoDetectedInReleaseCycleSet_shouldSetNewDetectedInReleaseCycle() {
		final Defect defect = new Defect();
		final ReleaseCycle detectedInReleaseCycle = new ReleaseCycle();
		detectedInReleaseCycle.setName("detected In Release Cycle name");
		final GenericEntity sourceEntity = new GenericEntity("defect",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(detectedInReleaseCycle.createEntity());
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getDetectedInReleaseCycle(), detectedInReleaseCycle);
	}

	@Test
	public void populateFields_withEntityHavingNoDetectedInReleaseSet_shouldSetNewDetectedInRelease() {
		final Defect defect = new Defect();
		final Release detectedInRelease = new Release();
		detectedInRelease.setName("detected In Release name");
		final GenericEntity sourceEntity = new GenericEntity("defect",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(detectedInRelease.createEntity());
		defect.populateFields(sourceEntity);
		Assert.assertEquals(defect.getDetectedInRelease(), detectedInRelease);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setClosingDate_withIncorrectDateFormat_shouldThrowException() {
		final Defect defectOne = createFullDefect();
		defectOne.setClosingDate("June 23, 1967");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setDetectedOnDate_withIncorrectDateFormat_shouldThrowException() {
		final Defect defectOne = createFullDefect();
		defectOne.setDetectedOnDate("June 23, 1967");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setModified_withIncorrectDateFormat_shouldThrowException() {
		final Defect defectOne = createFullDefect();
		defectOne.setModified("June 23, 1967 13:14:15");
	}

	@Test
	public void toString_withDetectedInReleaseCycleSet_shouldReturnNonDefaultStringWithDetectedInReleaseCycleInfo() {
		final Defect defectOne = createFullDefect();
		defectOne.setDetectedInReleaseCycle(new ReleaseCycle());
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "<Defect>"));
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "<ReleaseCycle>"));
	}

	@Test
	public void toString_withDetectedInReleaseSet_shouldReturnNonDefaultStringWithDetectedInReleaseInfo() {
		final Defect defectOne = createFullDefect();
		defectOne.setDetectedInRelease(new Release());
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "<Defect>"));
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "<Release>"));
	}

	@Test
	public void toString_withNoRelatedEntitiesSet_shouldReturnNonDefaultStringWithNoRelatedEntityInfo() {
		final Defect defectOne = createFullDefect();
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "<Defect>"));
		Assert.assertFalse(StringUtils.contains(defectOne.toString(), "<Release>"));
		Assert.assertFalse(StringUtils.contains(defectOne.toString(), "<ReleaseCycle>"));
		Assert.assertTrue(StringUtils.contains(defectOne.toString(), "Not Set"));
	}

	private Defect createFullDefect() {
		final Defect defect = new Defect();
		defect.setAssignedTo("svc");
		defect.setClosingDate("2014-09-01");
		defect.setComments("the comments");
		defect.setDescription("the description");
		defect.setDetectedBy("ssimmons");
		defect.setDetectedInReleaseCycleId(1337);
		defect.setDetectedInReleaseId(1776);
		defect.setDetectedOnDate("2014-08-01");
		defect.setId(42);
		defect.setModified("2014-08-02 13:14:15");
		defect.setSeverity("5-Low");
		defect.setStatus("No Run");
		defect.setSummary("this is the summary");
		return defect;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(DefectField.ASSIGNED_TO.getName(), Arrays.asList("svc")));
		fields.add(new Field(DefectField.CLOSING_DATE.getName(), Arrays.asList("2014-09-01")));
		fields.add(new Field(DefectField.COMMENTS.getName(), Arrays.asList("comments")));
		fields.add(new Field(DefectField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(DefectField.DETECTED_BY.getName(), Arrays.asList("ssimmons")));
		fields.add(new Field(DefectField.DETECTED_IN_CYCLE_ID.getName(), Arrays.asList("42")));
		fields.add(new Field(DefectField.DETECTED_IN_RELEASE_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(DefectField.DETECTED_ON_DATE.getName(), Arrays.asList("2014-08-01")));
		fields.add(new Field(DefectField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(DefectField.MODIFIED.getName(), Arrays.asList("2014-08-02 13:14:15")));
		fields.add(new Field(DefectField.SEVERITY.getName(), Arrays.asList("5-Low")));
		fields.add(new Field(DefectField.STATUS.getName(), Arrays.asList("Fixed")));
		fields.add(new Field(DefectField.SUMMARY.getName(), Arrays.asList("stuff is broken")));
		return new GenericEntity("defect", fields);
	}

}
