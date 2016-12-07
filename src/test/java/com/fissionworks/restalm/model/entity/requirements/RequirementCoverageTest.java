package com.fissionworks.restalm.model.entity.requirements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.RequirementCoverageField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class RequirementCoverageTest {

	private static final ThreadLocal<DateFormat> DATE_TIME_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final RequirementCoverage sourceRequirementCoverage = createFullRequirementCoverage();
		final GenericEntity createdEntity = sourceRequirementCoverage.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName()),
				Arrays.asList(sourceRequirementCoverage.getCoverageEntityType()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirementCoverage.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.LAST_MODIFIED.getName()),
				Arrays.asList(sourceRequirementCoverage.getLastModified()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.REQUIREMENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirementCoverage.getRequirementId())));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.STATUS.getName()),
				Arrays.asList(sourceRequirementCoverage.getStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementCoverageField.TEST_ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirementCoverage.getTestId())));
	}

	@Test
	public void createEntity_withRequirementCoverageHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final RequirementCoverage sourceDefect = new RequirementCoverage();
		final GenericEntity createdEntity = sourceDefect.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementCoverageField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementCoverageField.LAST_MODIFIED.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementCoverageField.REQUIREMENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementCoverageField.TEST_ID.getName()));
	}

	@Test
	public void equals_comparingRequirementCoverageToAnEqualObject_shouldReturnTrue() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		Assert.assertTrue(requirementCoverageOne.equals(createFullRequirementCoverage()));
	}

	@Test
	public void equals_comparingRequirementCoverageToAnotherObjectType_shouldReturnFalse() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		Assert.assertFalse(requirementCoverage.equals(new Object()));
	}

	@Test
	public void equals_comparingRequirementCoverageToNull_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		Assert.assertFalse(requirementCoverageOne.equals(null));
	}

	@Test
	public void equals_comparingRequirementCoverageToRequirementCoverageWithDifferentId_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageTwo.setId(1234);
		Assert.assertFalse(requirementCoverageOne.equals(requirementCoverageTwo));
	}

	@Test
	public void equals_comparingRequirementCoverageToRequirementCoverageWithDifferentRequirementId_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageTwo.setRequirementId(1234);
		Assert.assertFalse(requirementCoverageOne.equals(requirementCoverageTwo));
	}

	@Test
	public void equals_comparingRequirementCoverageToRequirementCoverageWithDifferentTestId_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageTwo.setTestId(1234);
		Assert.assertFalse(requirementCoverageOne.equals(requirementCoverageTwo));
	}

	@Test
	public void equals_comparingRequirementCovereageToItself_shouldReturnTrue() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		Assert.assertTrue(requirementCoverageOne.equals(requirementCoverageOne));
	}

	@Test
	public void getAssociatedRequirement_withAssociatedRequirmentSet_shouldReturnAssociatedRequirement() {
		final RequirementCoverage requirementcoverage = new RequirementCoverage();
		final Requirement parent = new Requirement();
		parent.setName("the associated requirement");
		requirementcoverage.setAssociatedRequirement(parent);
		Assert.assertEquals(requirementcoverage.getAssociatedRequirement(), parent);
	}

	@Test
	public void getAssociatedRequirement_withNoAssociatedRequirementSet_shouldReturnDefaultRequirement() {
		Assert.assertEquals(new RequirementCoverage().getAssociatedRequirement(), new Requirement());
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new RequirementCoverage().getEntityCollectionType(), "requirement-coverages");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new RequirementCoverage().getEntityType(), "requirement-coverage");
	}

	@Test
	public void hashCode_forEqualRequirementCoverages_shouldBeEqual() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		Assert.assertEquals(requirementCoverageOne.hashCode(), requirementCoverageTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualRequirementCoverages_shouldNotBeEqual() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageTwo.setId(1234);
		Assert.assertNotEquals(requirementCoverageOne.hashCode(), requirementCoverageTwo.hashCode());
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetLastModifiedToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		Assert.assertEquals(requirementCoverage.getLastModified(), DATE_TIME_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToCoverageWithDifferentAssociatedRequirement_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		final Requirement requirement = new Requirement();
		requirement.setName("theName");
		requirementCoverageOne.setAssociatedRequirement(new Requirement());
		requirementCoverageTwo.setAssociatedRequirement(requirement);
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToCoverageWithDifferentCoverageEntityType_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = new RequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = new RequirementCoverage();
		requirementCoverageOne.setCoverageEntityType("Run");
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToCoverageWithDifferentLastModified_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = new RequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = new RequirementCoverage();
		requirementCoverageOne.setLastModified("2013-06-13 10:11:12");
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToCoverageWithDifferentStatus_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = new RequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = new RequirementCoverage();
		requirementCoverageOne.setStatus("Passed");
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToExactlyMatchingRequirementCoverage_shouldReturnTrue() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageOne.setAssociatedRequirement(new Requirement());
		requirementCoverageTwo.setAssociatedRequirement(new Requirement());
		Assert.assertTrue(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToItself_shouldReturnTrue() {
		final RequirementCoverage requirementCoverage = createFullRequirementCoverage();
		Assert.assertTrue(requirementCoverage.isExactMatch(requirementCoverage));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageToTestThatDoesNotSatisyEquals_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = createFullRequirementCoverage();
		requirementCoverageTwo.setId(123456);
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementCoverageWithNullAssociatedRequirementToCoverageWithAssociatedRequirement_shouldReturnFalse() {
		final RequirementCoverage requirementCoverageOne = new RequirementCoverage();
		final RequirementCoverage requirementCoverageTwo = new RequirementCoverage();
		requirementCoverageTwo.setAssociatedRequirement(new Requirement());
		Assert.assertFalse(requirementCoverageOne.isExactMatch(requirementCoverageTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		cal.setTimeInMillis(Long.MIN_VALUE);
		requirementCoverage.populateFields(
				new GenericEntity("requirement-coverage", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(requirementCoverage.getCoverageEntityType(), "");
		Assert.assertTrue(requirementCoverage.getId() == Integer.MIN_VALUE);
		Assert.assertEquals(requirementCoverage.getLastModified(), DATE_TIME_FORMATTER.get().format(cal.getTime()));
		Assert.assertTrue(requirementCoverage.getRequirementId() == Integer.MIN_VALUE);
		Assert.assertEquals(requirementCoverage.getStatus(), "");
		Assert.assertTrue(requirementCoverage.getTestId() == Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllFieldsPopulated_shouldSetAllFields() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		requirementCoverage.populateFields(sourceEntity);
		Assert.assertEquals(requirementCoverage.getCoverageEntityType(),
				sourceEntity.getFieldValues(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName()).get(0));
		Assert.assertTrue(requirementCoverage.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementCoverageField.ID.getName()).get(0)));
		Assert.assertEquals(requirementCoverage.getLastModified(),
				sourceEntity.getFieldValues(RequirementCoverageField.LAST_MODIFIED.getName()).get(0));
		Assert.assertTrue(requirementCoverage.getRequirementId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementCoverageField.REQUIREMENT_ID.getName()).get(0)));
		Assert.assertEquals(requirementCoverage.getStatus(),
				sourceEntity.getFieldValues(RequirementCoverageField.STATUS.getName()).get(0));
		Assert.assertTrue(requirementCoverage.getTestId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementCoverageField.TEST_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingAssociatedRequirementSet_shouldSetNewAssociatedRequirement() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		requirementCoverage.setAssociatedRequirement(new Requirement());
		final Requirement parent = new Requirement();
		parent.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("requirement-coverage",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parent.createEntity());
		requirementCoverage.populateFields(sourceEntity);
		Assert.assertEquals(requirementCoverage.getAssociatedRequirement(), parent);
	}

	@Test
	public void populateFields_withEntityHavingNoASsociatedRequirementSet_shouldSetNewAssociatedRequirement() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		final Requirement parent = new Requirement();
		parent.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("requirement-coverage",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parent.createEntity());
		requirementCoverage.populateFields(sourceEntity);
		Assert.assertEquals(requirementCoverage.getAssociatedRequirement(), parent);
	}

	@Test
	public void toString_withAssociatedRequirement_shouldReturnNonDefaultStringWithAssociatedRequirementInfo() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		requirementCoverageOne.setAssociatedRequirement(new Requirement());
		Assert.assertTrue(StringUtils.contains(requirementCoverageOne.toString(), "<RequirementCoverage>"));
	}

	@Test
	public void toString_withNoAssociatedItems_shouldReturnNonDefaultStringWithNotSetString() {
		final RequirementCoverage requirementCoverageOne = createFullRequirementCoverage();
		Assert.assertTrue(StringUtils.contains(requirementCoverageOne.toString(), "<RequirementCoverage>"));
		Assert.assertTrue(StringUtils.contains(requirementCoverageOne.toString(), "Not Set"));
	}

	private RequirementCoverage createFullRequirementCoverage() {
		final RequirementCoverage requirementCoverage = new RequirementCoverage();
		requirementCoverage.setCoverageEntityType("test");
		requirementCoverage.setId(1337);
		requirementCoverage.setLastModified("2014-01-01 11:12:13");
		requirementCoverage.setRequirementId(1776);
		requirementCoverage.setStatus("Failed");
		requirementCoverage.setTestId(2001);
		return requirementCoverage;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName(), Arrays.asList("coverageEntity")));
		fields.add(new Field(RequirementCoverageField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(RequirementCoverageField.LAST_MODIFIED.getName(),
				Arrays.asList(DATE_TIME_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(RequirementCoverageField.REQUIREMENT_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(RequirementCoverageField.STATUS.getName(), Arrays.asList("theStatus")));
		fields.add(new Field(RequirementCoverageField.TEST_ID.getName(), Arrays.asList("2001")));
		return new GenericEntity("requirement", fields);
	}

}
