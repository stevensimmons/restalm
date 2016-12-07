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

import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class RequirementTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private static final ThreadLocal<DateFormat> DATE_TIME_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final Requirement sourceRequirement = createFullRequirement();
		final GenericEntity createdEntity = sourceRequirement.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.AUTHOR.getName()),
				Arrays.asList(sourceRequirement.getAuthor()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.COMMENTS.getName()),
				Arrays.asList(sourceRequirement.getComments()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.CREATION_DATE.getName()),
				Arrays.asList(sourceRequirement.getCreationDate()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.DESCRIPTION.getName()),
				Arrays.asList(sourceRequirement.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.DIRECT_COVER_STATUS.getName()),
				Arrays.asList(sourceRequirement.getDirectCoverStatus()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.FATHER_NAME.getName()),
				Arrays.asList(sourceRequirement.getFatherName()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirement.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.LAST_MODIFIED.getName()),
				Arrays.asList(sourceRequirement.getLastModified()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.NAME.getName()),
				Arrays.asList(sourceRequirement.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirement.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(RequirementField.TYPE_ID.getName()),
				Arrays.asList(String.valueOf(sourceRequirement.getTypeId())));
	}

	@Test
	public void createEntity_withRequirementHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final Requirement sourceRequirement = new Requirement();
		final GenericEntity createdEntity = sourceRequirement.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementField.PARENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(RequirementField.TYPE_ID.getName()));
	}

	@Test
	public void equals_comparingRequirementToAnEqualObject_shouldReturnTrue() {
		final Requirement requirementOne = createFullRequirement();
		Assert.assertTrue(requirementOne.equals(createFullRequirement()));
	}

	@Test
	public void equals_comparingRequirementToAnotherObjectType_shouldReturnFalse() {
		final Requirement requirement = new Requirement();
		Assert.assertFalse(requirement.equals(new Object()));
	}

	@Test
	public void equals_comparingRequirementToItself_shouldReturnTrue() {
		final Requirement requirementOne = createFullRequirement();
		Assert.assertTrue(requirementOne.equals(requirementOne));
	}

	@Test
	public void equals_comparingRequirementToNull_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		Assert.assertFalse(requirementOne.equals(null));
	}

	@Test
	public void equals_comparingRequirementToRequirementWithDifferentId_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setId(1234);
		Assert.assertFalse(requirementOne.equals(requirementTwo));
	}

	@Test
	public void equals_comparingRequirementToRequirementWithDifferentName_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setName("different");
		Assert.assertFalse(requirementOne.equals(requirementTwo));
	}

	@Test
	public void equals_comparingRequirementToRequirementWithDifferentParentId_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setParentId(123456);
		Assert.assertFalse(requirementOne.equals(requirementTwo));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCollectionType() {
		Assert.assertEquals(new Requirement().getEntityCollectionType(), "requirements");
	}

	@Test
	public void getEntityType_shouldReturnType() {
		Assert.assertEquals(new Requirement().getEntityType(), "requirement");
	}

	@Test
	public void getFullComments_withCommentsThatHaveHtml_shouldReturnFullComments() {
		final Requirement requirement = new Requirement();
		requirement.setComments("<b>bold comments</b>");
		Assert.assertEquals(requirement.getFullComments(), "<b>bold comments</b>");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final Requirement requirement = new Requirement();
		requirement.setDescription("<b>bold description</b>");
		Assert.assertEquals(requirement.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getParentRequirement_withNoParentRequirementSet_shouldReturnDefaultRequirement() {
		Assert.assertEquals(new Requirement().getParentRequirement(), new Requirement());
	}

	@Test
	public void getParentRequirement_withParentRequirmentSet_shouldReturnParentRequirement() {
		final Requirement requirement = new Requirement();
		final Requirement parent = createFullRequirement();
		requirement.setParentRequirement(parent);
		Assert.assertEquals(requirement.getParentRequirement(), parent);
	}

	@Test
	public void instantiate_withDefaultConstructor_shouldSetLastModifiedToLongMinValue() {
		cal.setTimeInMillis(Long.MIN_VALUE);
		final Requirement requirement = new Requirement();
		Assert.assertEquals(requirement.getLastModified(), DATE_TIME_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(requirement.getCreationDate(), DATE_FORMATTER.get().format(cal.getTime()));
	}

	@Test
	public void isExactMatch_comparingRequirementNonMatchingParentRequirement_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementOne.setParentRequirement(new Requirement());
		final Requirement secondRequirement = new Requirement();
		secondRequirement.setName("non matching req");
		requirementTwo.setParentRequirement(secondRequirement);
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentAuthors_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setAuthor("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentComments_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setComments("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentCreationDate_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setCreationDate("2014-01-02");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentDescription_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setDescription("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentDirectCoverStatus_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setDirectCoverStatus("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentFatherName_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setFatherName("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentLastModified_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setLastModified("2014-09-04 12:11:10");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementsWithDifferentType_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setTypeId(42);
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementToExactlyMatchingRequirement_shouldReturnTrue() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementOne.setParentRequirement(new Requirement());
		requirementTwo.setParentRequirement(new Requirement());
		Assert.assertTrue(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementToItself_shouldReturnTrue() {
		final Requirement requirementOne = createFullRequirement();
		Assert.assertTrue(requirementOne.isExactMatch(requirementOne));
	}

	@Test
	public void isExactMatch_comparingRequirementToRequirementThatDoesntSatisfyEquals_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setName("different");
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void isExactMatch_comparingRequirementWithNullParentRequiremtnToRequirementWithParentRequirementSet_shouldReturnFalse() {
		final Requirement requirementOne = createFullRequirement();
		final Requirement requirementTwo = createFullRequirement();
		requirementTwo.setParentRequirement(new Requirement());
		Assert.assertFalse(requirementOne.isExactMatch(requirementTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final Requirement requirement = new Requirement();
		cal.setTimeInMillis(Long.MIN_VALUE);
		requirement.populateFields(
				new GenericEntity("requirement", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(requirement.getAuthor(), "");
		Assert.assertEquals(requirement.getComments(), "");
		Assert.assertEquals(requirement.getCreationDate(), DATE_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(requirement.getDescription(), "");
		Assert.assertEquals(requirement.getDirectCoverStatus(), "");
		Assert.assertEquals(requirement.getFatherName(), "");
		Assert.assertEquals(requirement.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(requirement.getLastModified(), DATE_TIME_FORMATTER.get().format(cal.getTime()));
		Assert.assertEquals(requirement.getName(), "");
		Assert.assertEquals(requirement.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(requirement.getTypeId(), Integer.MIN_VALUE);
	}

	@Test
	public void populateFields_withEntityHavingAllFieldsPopulated_shouldSetAllFields() {
		final Requirement requirement = new Requirement();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		requirement.populateFields(sourceEntity);
		Assert.assertEquals(requirement.getAuthor(),
				sourceEntity.getFieldValues(RequirementField.AUTHOR.getName()).get(0));
		Assert.assertEquals(requirement.getComments(),
				sourceEntity.getFieldValues(RequirementField.COMMENTS.getName()).get(0));
		Assert.assertEquals(requirement.getCreationDate(),
				sourceEntity.getFieldValues(RequirementField.CREATION_DATE.getName()).get(0));
		Assert.assertEquals(requirement.getDescription(),
				sourceEntity.getFieldValues(RequirementField.DESCRIPTION.getName()).get(0));
		Assert.assertEquals(requirement.getDirectCoverStatus(),
				sourceEntity.getFieldValues(RequirementField.DIRECT_COVER_STATUS.getName()).get(0));
		Assert.assertEquals(requirement.getFatherName(),
				sourceEntity.getFieldValues(RequirementField.FATHER_NAME.getName()).get(0));
		Assert.assertTrue(requirement.getId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementField.ID.getName()).get(0)));
		Assert.assertEquals(requirement.getLastModified(),
				sourceEntity.getFieldValues(RequirementField.LAST_MODIFIED.getName()).get(0));
		Assert.assertEquals(requirement.getName(), sourceEntity.getFieldValues(RequirementField.NAME.getName()).get(0));
		Assert.assertTrue(requirement.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementField.PARENT_ID.getName()).get(0)));
		Assert.assertTrue(requirement.getTypeId() == Integer
				.valueOf(sourceEntity.getFieldValues(RequirementField.TYPE_ID.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingNoParentRequirementSet_shouldSetNewParentRequirement() {
		final Requirement requirement = new Requirement();
		final Requirement parent = new Requirement();
		parent.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("requirement",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parent.createEntity());
		requirement.populateFields(sourceEntity);
		Assert.assertEquals(requirement.getParentRequirement(), parent);
	}

	@Test
	public void populateFields_withEntityHavingParentRequirementSet_shouldSetNewParentRequirement() {
		final Requirement requirement = new Requirement();
		requirement.setParentRequirement(new Requirement());
		final Requirement parent = new Requirement();
		parent.setName("parent name");
		final GenericEntity sourceEntity = new GenericEntity("requirement",
				Arrays.asList(new Field("dummy", new ArrayList<String>())));
		sourceEntity.addRelatedEntity(parent.createEntity());
		requirement.populateFields(sourceEntity);
		Assert.assertEquals(requirement.getParentRequirement(), parent);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setCreationDate_withIncorrectDateFormat_shouldThrowException() {
		final Requirement requirement = new Requirement();
		requirement.setCreationDate("June 23, 1967");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setLastModifiedDate_withIncorrectDateFormat_shouldThrowException() {
		final Requirement requirement = new Requirement();
		requirement.setLastModified("June 23, 1967");
	}

	@Test
	public void setParentRequirement_withParentAlreadySet_shouldSetNewParentRequirement() {
		final Requirement requirement = new Requirement();
		requirement.setParentRequirement(new Requirement());
		final Requirement newParent = new Requirement();
		newParent.setName("new parent");
		requirement.setParentRequirement(newParent);
		Assert.assertEquals(requirement.getParentRequirement(), newParent);
	}

	@Test
	public void toString_withNoAssociatedItems_shouldReturnNonDefaultStringWithNotSetString() {
		final Requirement requirementOne = createFullRequirement();
		Assert.assertTrue(StringUtils.contains(requirementOne.toString(), "<Requirement>"));
		Assert.assertTrue(StringUtils.contains(requirementOne.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentRequirement_shouldReturnNonDefaultStringWithParentRequirementInfo() {
		final Requirement requirementOne = createFullRequirement();
		requirementOne.setParentRequirement(new Requirement());
		Assert.assertTrue(StringUtils.contains(requirementOne.toString(), "<Requirement>"));
	}

	private Requirement createFullRequirement() {
		final Requirement requirement = new Requirement();
		requirement.setAuthor("theAuthor");
		requirement.setComments("theComments");
		requirement.setCreationDate("2013-07-04");
		requirement.setDescription("theDescription");
		requirement.setDirectCoverStatus("Failed");
		requirement.setFatherName("theFather");
		requirement.setId(1337);
		requirement.setLastModified("2013-07-04 13:14:15");
		requirement.setName("theName");
		requirement.setParentId(1776);
		requirement.setTypeId(3);
		return requirement;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(RequirementField.AUTHOR.getName(), Arrays.asList("theAuthor")));
		fields.add(new Field(RequirementField.COMMENTS.getName(), Arrays.asList("theComments")));
		fields.add(new Field(RequirementField.CREATION_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(RequirementField.DESCRIPTION.getName(), Arrays.asList("theDescription")));
		fields.add(new Field(RequirementField.DIRECT_COVER_STATUS.getName(), Arrays.asList("theDirectCoverStatus")));
		fields.add(new Field(RequirementField.FATHER_NAME.getName(), Arrays.asList("theFatherName")));
		fields.add(new Field(RequirementField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(RequirementField.LAST_MODIFIED.getName(),
				Arrays.asList(DATE_TIME_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(RequirementField.NAME.getName(), Arrays.asList("theRunName")));
		fields.add(new Field(RequirementField.PARENT_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(RequirementField.TYPE_ID.getName(), Arrays.asList("2014")));
		return new GenericEntity("requirement", fields);
	}
}
