package com.fissionworks.restalm.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.DesignStepField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.DesignStep;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionDesignStepTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxx:xxxx";

	private static final String USERNAME = "xxx";

	private Alm115Connection alm;

	@BeforeClass
	public void _setup() {
		alm = new Alm115Connection(URL);
		alm.authenticate(new Credentials(USERNAME, PASSWORD));
		alm.login(new Project(DOMAIN, PROJECT_NAME));
	}

	@AfterClass
	public void _teardown() {
		alm.logout();
	}

	@Test
	public void addEntity_withAllFieldsPopulated_shouldAddWithAllEditableAndRequiredFieldsPopulated() {
		final DesignStep initialStep = createMinimumDesignStep();
		initialStep.setDescription("the description");
		initialStep.setId(1337);
		initialStep.setExpectedResult("the expected result");
		initialStep.setName("the name");
		initialStep.setStepOrder(9999);

		final DesignStep addedStep = alm.addEntity(initialStep);
		alm.deleteEntity(DesignStep.class, addedStep.getId());
		Assert.assertEquals(addedStep.getDescription(), initialStep.getDescription());
		Assert.assertEquals(addedStep.getExpectedResult(), initialStep.getExpectedResult());
		Assert.assertNotEquals(addedStep.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedStep.getName(), initialStep.getName());
		Assert.assertNotEquals(addedStep.getStepOrder(), initialStep.getStepOrder());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final DesignStep step = new DesignStep();
		alm.addEntity(step);
	}

	@Test
	public void addEntityAndDelete_withStepMininumRequiredFieldsPopulated_shouldCreateStep() {
		final DesignStep addedStep = alm.addEntity(createMinimumDesignStep());
		alm.deleteEntity(DesignStep.class, addedStep.getId());
		Assert.assertNotEquals(addedStep.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedStep.getDescription(), "");
		Assert.assertEquals(addedStep.getExpectedResult(), "");
		Assert.assertEquals(addedStep.getName(), "");
		Assert.assertEquals(addedStep.getParentId(), 320);
		Assert.assertNotEquals(addedStep.getStepOrder(), Integer.MIN_VALUE);
	}

	@Test
	public void getEntities_withCrossQuerySet_shouldReturnDesignStepsThatMatchQuery() {
		final AlmEntityCollection<DesignStep> stepCollection = alm.getEntities(DesignStep.class,
				new RestParameters().crossQueryFilter(AlmTestField.HAS_PARTS_TEST_NAME, "'get design step by id'"));
		for (final DesignStep step : stepCollection) {
			Assert.assertEquals(step.getParentId(), 309);
		}
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final DesignStep step : alm.getEntities(DesignStep.class,
				new RestParameters().fields(DesignStepField.NAME, DesignStepField.DESCRIPTION))) {
			Assert.assertNotEquals(step.getDescription(), "");
			Assert.assertEquals(step.getExpectedResult(), "");
			Assert.assertTrue(step.getId() != Integer.MIN_VALUE);
			Assert.assertTrue(!step.getName().equals(""));
			Assert.assertEquals(step.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(step.getStepOrder(), Integer.MIN_VALUE);
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnStepsMatchingQuery() {
		final AlmEntityCollection<DesignStep> stepCollection = alm.getEntities(DesignStep.class, new RestParameters()
				.queryFilter(DesignStepField.NAME, "'get step by id'").queryFilter(AlmTestField.PARENT_ID, "309"));
		for (final DesignStep step : stepCollection) {
			Assert.assertEquals(step.getName(), "get step by id");
			Assert.assertEquals(step.getParentId(), 309);
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(DesignStep.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestFieldsSpecified_shouldReturnStepWithRelatedEntity() {
		final AlmEntityCollection<DesignStep> steps = alm.getEntities(DesignStep.class,
				new RestParameters().queryFilter(DesignStepField.ID, "1001")
						.relatedFields(AlmTestField.HAS_PARTS_TEST_NAME, AlmTestField.HAS_PARTS_TEST_DESCRIPTION));
		final AlmTest parentTest = new AlmTest();
		parentTest.setId(309);
		parentTest.setName("get design step by id");
		parentTest.setDescription("design step test");

		Assert.assertEquals(steps.size(), 1);
		for (final DesignStep step : steps) {
			Assert.assertEquals(step.getParentAlmTest(), parentTest);
		}

	}

	@Test
	public void getEntities_withQueryAndCrossQuerySet_shouldReturnStepsThatMatchQuery() {
		final AlmEntityCollection<DesignStep> stepCollection = alm.getEntities(DesignStep.class,
				new RestParameters().crossQueryFilter(AlmTestField.HAS_PARTS_TEST_NAME, "'get design step by id'")
						.queryFilter(DesignStepField.NAME, "'get step by id'"));
		for (final DesignStep step : stepCollection) {
			Assert.assertEquals(step.getParentId(), 309);
			Assert.assertEquals(step.getName(), "get step by id");
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final DesignStep step : alm.getEntities(DesignStep.class,
				new RestParameters().fields(DesignStepField.NAME))) {
			Assert.assertEquals(step.getDescription(), "");
			Assert.assertEquals(step.getExpectedResult(), "");
			Assert.assertNotEquals(step.getId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(step.getName(), "");
			Assert.assertEquals(step.getParentId(), Integer.MIN_VALUE);

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnStepsMatchingQuery() {
		final AlmEntityCollection<DesignStep> stepCollection = alm.getEntities(DesignStep.class,
				new RestParameters().queryFilter(DesignStepField.NAME, "'get step by id'"));
		for (final DesignStep step : stepCollection) {
			Assert.assertEquals(step.getName(), "get step by id");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnStepsStartingAtStartIndex() {
		final AlmEntityCollection<DesignStep> initialStepCollection = alm.getEntities(DesignStep.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<DesignStep> indexTwoCollection = alm.getEntities(DesignStep.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialStepCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final DesignStep step : indexTwoCollection) {
			Assert.assertFalse(initialStepCollection.contains(step));
		}
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withdNonExistentId_shouldThrowException() {
		alm.getEnityById(DesignStep.class, 99999999);
	}

	@Test
	public void getEntity_withValidId_shouldReturnCorrectDesignStep() {
		final DesignStep step = alm.getEnityById(DesignStep.class, 1001);
		Assert.assertNotNull(step);
		Assert.assertTrue(step.isExactMatch(getDesignStep1001()),
				"expected:\n" + getDesignStep1001() + "\nbut got:\n" + step);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final DesignStep step = alm.getEnityById(DesignStep.class, 1029);
		final DesignStep initialStep = new DesignStep();
		initialStep.populateFields(step.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newExpectedResult = "newExpectedResult" + System.currentTimeMillis()
				+ Thread.currentThread().getId();

		step.setName(newName);
		step.setDescription(newDescription);
		step.setExpectedResult(newExpectedResult);
		step.setParentId(1776);
		step.setStepOrder(42);

		alm.updateEntity(step, DesignStepField.NAME, DesignStepField.DESCRIPTION);
		final DesignStep updatedStep = alm.getEnityById(DesignStep.class, 1029);
		Assert.assertEquals(updatedStep.getDescription(), newDescription);
		Assert.assertNotEquals(updatedStep.getExpectedResult(), newExpectedResult);
		Assert.assertEquals(updatedStep.getName(), newName);
		Assert.assertEquals(updatedStep.getParentId(), initialStep.getParentId());
		Assert.assertEquals(updatedStep.getStepOrder(), initialStep.getStepOrder());
	}

	@Test
	public void updateEntity_withNoFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final DesignStep step = alm.getEnityById(DesignStep.class, 1030);
		final DesignStep initialStep = new DesignStep();
		initialStep.populateFields(step.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newExpectedResult = "newExpectedResult" + System.currentTimeMillis()
				+ Thread.currentThread().getId();

		step.setName(newName);
		step.setDescription(newDescription);
		step.setExpectedResult(newExpectedResult);
		step.setParentId(1776);
		step.setStepOrder(42);

		alm.updateEntity(step);
		final DesignStep updatedStep = alm.getEnityById(DesignStep.class, 1030);
		Assert.assertEquals(updatedStep.getDescription(), newDescription);
		Assert.assertEquals(updatedStep.getExpectedResult(), newExpectedResult);
		Assert.assertEquals(updatedStep.getName(), newName);
		Assert.assertEquals(updatedStep.getParentId(), initialStep.getParentId());
		Assert.assertEquals(updatedStep.getStepOrder(), initialStep.getStepOrder());
	}

	private DesignStep createMinimumDesignStep() {
		final DesignStep step = new DesignStep();
		step.setParentId(320);
		return step;
	}

	private DesignStep getDesignStep1001() {
		final DesignStep step = new DesignStep();
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(DesignStepField.ID.getName(), Arrays.asList("1001")));
		fields.add(new Field(DesignStepField.NAME.getName(), Arrays.asList("get step by id")));
		fields.add(new Field(DesignStepField.DESCRIPTION.getName(), Arrays.asList("get step by id description")));
		fields.add(new Field(DesignStepField.PARENT_ID.getName(), Arrays.asList("309")));
		fields.add(new Field(DesignStepField.STEP_ORDER.getName(), Arrays.asList("1")));
		fields.add(
				new Field(DesignStepField.EXPECTED_RESULT.getName(), Arrays.asList("get step by id expected result")));
		fields.add(new Field("user-01", Arrays.asList("custom field value")));
		final GenericEntity entity = new GenericEntity("design-step", fields);
		step.populateFields(entity);
		return step;
	}
}
