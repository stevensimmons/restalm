package com.fissionworks.restalm.model.entity.testplan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.DesignStepField;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class DesignStepTest {

	private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private final Calendar cal = Calendar.getInstance();

	@Test
	public void createEntity_shouldCreateEnityWithAllFields() {
		final DesignStep sourceStep = createFullDesignStep();
		final GenericEntity createdEntity = sourceStep.createEntity();
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.DESCRIPTION.getName()),
				Arrays.asList(sourceStep.getDescription()));
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.EXPECTED_RESULT.getName()),
				Arrays.asList(sourceStep.getExpectedResult()));
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.ID.getName()),
				Arrays.asList(String.valueOf(sourceStep.getId())));
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.NAME.getName()),
				Arrays.asList(sourceStep.getName()));
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.PARENT_ID.getName()),
				Arrays.asList(String.valueOf(sourceStep.getParentId())));
		Assert.assertEquals(createdEntity.getFieldValues(DesignStepField.STEP_ORDER.getName()),
				Arrays.asList(String.valueOf(sourceStep.getStepOrder())));
	}

	@Test
	public void createEntity_withStepHavingDefaultValues__shouldCreateEntityWithoutInvalidValues() {
		final DesignStep sourceStep = new DesignStep();
		final GenericEntity createdEntity = sourceStep.createEntity();
		Assert.assertFalse(createdEntity.hasFieldValue(DesignStepField.ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DesignStepField.PARENT_ID.getName()));
		Assert.assertFalse(createdEntity.hasFieldValue(DesignStepField.STEP_ORDER.getName()));
	}

	@Test
	public void equals_comparingStepToEqualStep_shouldReturnTrue() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		Assert.assertTrue(stepOne.equals(stepTwo));
	}

	@Test
	public void equals_comparingStepToItself_shouldReturnTrue() {
		final DesignStep stepOne = createFullDesignStep();
		Assert.assertTrue(stepOne.equals(stepOne));
	}

	@Test
	public void equals_comparingStepToNull_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		Assert.assertFalse(stepOne.equals(null));
	}

	@Test
	public void equals_comparingStepToObjectOfDifferentType_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		Assert.assertFalse(stepOne.equals(new Object()));
	}

	@Test
	public void equals_comparingStepToStepWithDifferentId_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setId(123456);
		Assert.assertFalse(stepOne.equals(new Object()));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getCustomFieldValue_withNonExistentFieldName_shouldThrowException() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final DesignStep sourceStep = new DesignStep();
		sourceStep.populateFields(entity);
		sourceStep.getCustomFieldValue("nonExistent");
	}

	@Test
	public void getCustomFieldValue_withValidFieldName_shouldReturnValue() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("value")));
		fields.add(new Field("fieldTwo", Arrays.asList("value")));
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final GenericEntity entity = new GenericEntity("theType", fields);
		final DesignStep sourceStep = new DesignStep();
		sourceStep.populateFields(entity);
		Assert.assertEquals(sourceStep.getCustomFieldValue("user-01"), Arrays.asList("one"));
	}

	@Test
	public void getEntityCollectionType_shouldReturnCorrectCollectionType() {
		Assert.assertEquals(new DesignStep().getEntityCollectionType(), "design-steps");
	}

	@Test
	public void getEntityType_shouldReturnCorrectType() {
		Assert.assertEquals(new DesignStep().getEntityType(), "design-step");
	}

	@Test
	public void getFullDescription_withDescriptionThatHasHtml_shouldReturnFullDescription() {
		final DesignStep step = new DesignStep();
		step.setDescription("<b>bold description</b>");
		Assert.assertEquals(step.getFullDescription(), "<b>bold description</b>");
	}

	@Test
	public void getFullExpectedResult_withFullExpectedResultThatHasHtml_shouldReturnFullFullExpectedResult() {
		final DesignStep step = new DesignStep();
		step.setExpectedResult("<b>bold description</b>");
		Assert.assertEquals(step.getFullExpectedResult(), "<b>bold description</b>");
	}

	@Test
	public void hashCode_forEqualTests_shouldBeEqual() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		Assert.assertEquals(stepOne.hashCode(), stepTwo.hashCode());
	}

	@Test
	public void hashCode_forUnEqualTests_shouldNotBeEqual() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setId(123456);
		Assert.assertNotEquals(stepOne.hashCode(), stepTwo.hashCode());
	}

	@Test
	public void isExactMatch_comparingExactlyMatchingStepsWithNoParentAlmTest_shouldReturnTrue() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		Assert.assertTrue(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingExactlyMatchingStepsWithParentAlmTest_shouldReturnTrue() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepOne.setParentAlmTest(new AlmTest());
		stepTwo.setParentAlmTest(new AlmTest());
		Assert.assertTrue(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsThatDontSatisfyEquals_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setId(123456);
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDifferentCustomFields_shouldReturnFalse() {
		final DesignStep stepOne = new DesignStep();
		final DesignStep stepTwo = new DesignStep();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));
		fieldsTwo.add(new Field("user-03", Arrays.asList("three")));

		final GenericEntity entity = new GenericEntity("test", fields);
		final GenericEntity entityTwo = new GenericEntity("test", fieldsTwo);
		stepOne.populateFields(entity);
		stepTwo.populateFields(entityTwo);

		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDifferentNames_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setName("different name");
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDifferentParentIds_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setParentId(999999);
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDifferentSizeCustomFields_shouldReturnFalse() {
		final DesignStep stepOne = new DesignStep();
		final DesignStep stepTwo = new DesignStep();

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("user-01", Arrays.asList("one")));
		fields.add(new Field("user-02", Arrays.asList("two")));

		final List<Field> fieldsTwo = new ArrayList<>();
		fieldsTwo.add(new Field("user-01", Arrays.asList("one")));

		final GenericEntity entity = new GenericEntity("test", fields);
		final GenericEntity entityTwo = new GenericEntity("test", fieldsTwo);
		stepOne.populateFields(entity);
		stepTwo.populateFields(entityTwo);

		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDifferentStepOrders_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setStepOrder(999999);
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDiffernetDescriptions_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setDescription("different description");
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithDiffernetExpectedResults_shouldReturnFalse() {
		final DesignStep stepOne = createFullDesignStep();
		final DesignStep stepTwo = createFullDesignStep();
		stepTwo.setExpectedResult("different result");
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepsWithUnequalParentTests_shouldReturnFalse() {
		final DesignStep stepOne = new DesignStep();
		final DesignStep stepTwo = new DesignStep();
		stepOne.setParentAlmTest(new AlmTest());
		final AlmTest testTwo = new AlmTest();
		testTwo.setId(123456);
		stepTwo.setParentAlmTest(testTwo);
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void isExactMatch_comparingStepToItself_shouldReturnTrue() {
		final DesignStep stepOne = createFullDesignStep();
		Assert.assertTrue(stepOne.isExactMatch(stepOne));
	}

	@Test
	public void isExactMatch_comparingStepWithNullParentTestToStepWithParentTest_shouldReturnFalse() {
		final DesignStep stepOne = new DesignStep();
		final DesignStep stepTwo = new DesignStep();
		stepTwo.setParentAlmTest(new AlmTest());
		Assert.assertFalse(stepOne.isExactMatch(stepTwo));
	}

	@Test
	public void populateFields_withEmptyEntity_shouldSetAllFieldsAsDefault() {
		final DesignStep step = new DesignStep();
		step.populateFields(
				new GenericEntity("design-step", Arrays.asList(new Field("dummy", new ArrayList<String>()))));
		Assert.assertEquals(step.getDescription(), "");
		Assert.assertEquals(step.getExpectedResult(), "");
		Assert.assertEquals(step.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(step.getName(), "");
		Assert.assertEquals(step.getParentId(), Integer.MIN_VALUE);
		Assert.assertEquals(step.getStepOrder(), Integer.MIN_VALUE);
		Assert.assertEquals(step.getParentAlmTest(), new AlmTest());
	}

	@Test
	public void populateFields_withEntityHavingAllTestFieldsPopulated_shouldSetAllTestFields() {
		final DesignStep step = new DesignStep();
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		step.populateFields(sourceEntity);
		Assert.assertEquals(step.getDescription(),
				sourceEntity.getFieldValues(DesignStepField.DESCRIPTION.getName()).get(0));
		Assert.assertTrue(
				step.getId() == Integer.valueOf(sourceEntity.getFieldValues(DesignStepField.ID.getName()).get(0)));
		Assert.assertEquals(step.getName(), sourceEntity.getFieldValues(DesignStepField.NAME.getName()).get(0));
		Assert.assertTrue(step.getParentId() == Integer
				.valueOf(sourceEntity.getFieldValues(DesignStepField.PARENT_ID.getName()).get(0)));
		Assert.assertEquals(step.getExpectedResult(),
				sourceEntity.getFieldValues(DesignStepField.EXPECTED_RESULT.getName()).get(0));
		Assert.assertEquals(Integer.valueOf(step.getStepOrder()),
				Integer.valueOf(sourceEntity.getFieldValues(DesignStepField.STEP_ORDER.getName()).get(0)));
	}

	@Test
	public void populateFields_withEntityHavingParentTestWithNoPreviousTestSet_shouldSetParentTest() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final AlmTest expectedTest = new AlmTest();
		expectedTest.populateFields(createFullyPopulatedTestEntity());
		sourceEntity.addRelatedEntity(createFullyPopulatedTestEntity());

		final DesignStep actualStep = new DesignStep();
		actualStep.populateFields(sourceEntity);

		Assert.assertEquals(actualStep.getParentAlmTest(), expectedTest);
	}

	@Test
	public void populateFields_withEntityHavingParentTestWithPreviousTestSet_shouldSetParentTest() {
		final GenericEntity sourceEntity = createFullyPopulatedEntity();
		final AlmTest expectedTest = new AlmTest();
		expectedTest.populateFields(createFullyPopulatedTestEntity());
		sourceEntity.addRelatedEntity(createFullyPopulatedTestEntity());

		final DesignStep actualStep = new DesignStep();
		actualStep.setParentAlmTest(new AlmTest());
		actualStep.populateFields(sourceEntity);

		Assert.assertEquals(actualStep.getParentAlmTest(), expectedTest);
	}

	@Test
	public void toString_withNoParentTestSet_shouldReturnStringWithNotSetString() {
		final DesignStep step = createFullDesignStep();
		Assert.assertTrue(StringUtils.contains(step.toString(), "Not Set"));
	}

	@Test
	public void toString_withParentTestSet_shouldReturnStringWithParentTest() {
		final DesignStep step = createFullDesignStep();
		final AlmTest test = new AlmTest();
		test.populateFields(createFullyPopulatedTestEntity());
		step.setParentAlmTest(test);
		Assert.assertTrue(StringUtils.contains(step.toString(), "<AlmTest>"));
	}

	private DesignStep createFullDesignStep() {
		final DesignStep step = new DesignStep();
		step.setDescription("description");
		step.setExpectedResult("expected result");
		step.setName("name");
		step.setParentId(1337);
		step.setStepOrder(1776);
		step.setId(999);
		return step;
	}

	private GenericEntity createFullyPopulatedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(DesignStepField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(DesignStepField.EXPECTED_RESULT.getName(), Arrays.asList("expected result")));
		fields.add(new Field(DesignStepField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(DesignStepField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(DesignStepField.PARENT_ID.getName(), Arrays.asList("1776")));
		fields.add(new Field(DesignStepField.STEP_ORDER.getName(), Arrays.asList("999")));
		return new GenericEntity("design-step", fields);
	}

	private GenericEntity createFullyPopulatedTestEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(AlmTestField.CREATION_DATE.getName(),
				Arrays.asList(DATE_FORMATTER.get().format(cal.getTime()))));
		fields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList("description")));
		fields.add(new Field(AlmTestField.DESIGNER.getName(), Arrays.asList("designer")));
		fields.add(new Field(AlmTestField.ID.getName(), Arrays.asList("1337")));
		fields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList("name")));
		fields.add(new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList("90210")));
		fields.add(new Field(AlmTestField.STATUS.getName(), Arrays.asList("status")));
		fields.add(new Field(AlmTestField.TYPE.getName(), Arrays.asList("type")));
		return new GenericEntity("test", fields);
	}
}
