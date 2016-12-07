package com.fissionworks.restalm.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.TestFolder;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionAlmTestTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxxx:xxx";

	private static final String USERNAME = "xxxx";

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
		final String name = createUniqueName();
		final AlmTest initialTest = createMinimumAlmTest(name);
		initialTest.setCreationDate("2014-03-21");
		initialTest.setId(1337);
		initialTest.setDescription("the description");
		initialTest.setDesigner("ssimmons");
		initialTest.setStatus("Ready");
		final AlmTest addedTest = alm.addEntity(initialTest);
		alm.deleteEntity(AlmTest.class, addedTest.getId());
		Assert.assertEquals(addedTest.getCreationDate(), "2014-03-21");
		Assert.assertNotEquals(addedTest.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTest.getDescription(), "the description");
		Assert.assertEquals(addedTest.getDesigner(), "ssimmons");
		Assert.assertEquals(addedTest.getName(), name);
		Assert.assertEquals(addedTest.getParentId(), 1004);
		Assert.assertEquals(addedTest.getStatus(), "Ready");
		Assert.assertEquals(addedTest.getType(), "MANUAL");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withMissingRequiredField_shouldThrowException() {
		final AlmTest test = new AlmTest();
		test.setParentId(1004);
		test.setType("MANUAL");
		alm.addEntity(test);
	}

	@Test
	public void addEntity_withMultipleMissingRequiredFields_shouldThrowExceptionThatListsMissingFields() {
		final AlmTest test = new AlmTest();
		boolean exceptionThrown = false;
		test.setType("MANUAL");
		try {
			alm.addEntity(test);
		} catch (final IllegalArgumentException exception) {
			exceptionThrown = true;
			Assert.assertTrue(StringUtils.equals(exception.getMessage(),
					"Entity is missing the following required fields: name,parent-id")
					|| StringUtils.equals(exception.getMessage(),
							"Entity is missing the following required fields: parent-id,name"));
		}
		Assert.assertTrue(exceptionThrown);

	}

	@Test
	public void addEntityAndDelete_withAlmTestMininumRequiredFieldsPopulated_shouldCreateAlmTest() {
		final String name = createUniqueName();
		final AlmTest addedTest = alm.addEntity(createMinimumAlmTest(name));
		alm.deleteEntity(AlmTest.class, addedTest.getId());
		Assert.assertNotEquals(addedTest.getCreationDate(), "292269055-12-02");
		Assert.assertNotEquals(addedTest.getId(), Integer.MIN_VALUE);
		Assert.assertEquals(addedTest.getDescription(), "");
		Assert.assertEquals(addedTest.getDesigner(), "");
		Assert.assertEquals(addedTest.getName(), name);
		Assert.assertEquals(addedTest.getParentId(), 1004);
		Assert.assertEquals(addedTest.getStatus(), "");
		Assert.assertEquals(addedTest.getType(), "MANUAL");
	}

	@Test
	public void getEntities_withCrossQuerySet_shouldReturnAlmTestsThatMatchQuery() {
		final AlmEntityCollection<AlmTest> testCollection = alm.getEntities(AlmTest.class,
				new RestParameters().crossQueryFilter(TestFolderField.NAME, "GetAlmTest"));
		for (final AlmTest test : testCollection) {
			Assert.assertEquals(test.getParentId(), 1005);
		}
	}

	@Test
	public void getEntities_withMultipleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final AlmTest test : alm.getEntities(AlmTest.class,
				new RestParameters().fields(AlmTestField.NAME, AlmTestField.TYPE))) {
			Assert.assertEquals(test.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(test.getDescription(), "");
			Assert.assertEquals(test.getDesigner(), "");
			Assert.assertTrue(test.getId() != Integer.MIN_VALUE);
			Assert.assertTrue(!test.getName().equals(""));
			Assert.assertEquals(test.getParentId(), Integer.MIN_VALUE);
			Assert.assertNotEquals(test.getType(), "");
		}
	}

	@Test
	public void getEntities_withMultipleQueriesSet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<AlmTest> testCollection = alm.getEntities(AlmTest.class, new RestParameters()
				.queryFilter(AlmTestField.NAME, "getAlmTestById").queryFilter(AlmTestField.STATUS, "Ready"));
		for (final AlmTest test : testCollection) {
			Assert.assertEquals(test.getName(), "getAlmTestById");
			Assert.assertEquals(test.getStatus(), "Ready");
		}
	}

	@Test
	public void getEntities_withPageSizeSet_shouldReturnCorrectNumberOfEntities() {
		Assert.assertEquals(alm.getEntities(AlmTest.class, new RestParameters().pageSize(3)).size(), 3);
	}

	@Test
	public void getEntities_withParentTestFolderFieldsSpecified_shouldReturnTestWithRelatedEntity() {
		final AlmEntityCollection<AlmTest> tests = alm.getEntities(AlmTest.class,
				new RestParameters().queryFilter(AlmTestField.ID, "129").relatedFields(TestFolderField.NAME,
						TestFolderField.DESCRIPTION, TestFolderField.ID, TestFolderField.PARENT_ID));
		final TestFolder parentTestFolder = new TestFolder();
		parentTestFolder.setId(1005);
		parentTestFolder.setName("GetAlmTest");
		parentTestFolder.setParentId(1002);
		parentTestFolder.setDescription("Get Alm Test Folder");

		Assert.assertEquals(tests.size(), 1);
		for (final AlmTest test : tests) {
			Assert.assertEquals(test.getParentTestFolder(), parentTestFolder);
		}

	}

	@Test
	public void getEntities_withQueryAndCrossQuerySet_shouldReturnAlmTestsThatMatchQuery() {
		final AlmEntityCollection<AlmTest> testCollection = alm.getEntities(AlmTest.class, new RestParameters()
				.crossQueryFilter(TestFolderField.NAME, "GetAlmTest").queryFilter(AlmTestField.NAME, "getAlmTestById"));
		for (final AlmTest test : testCollection) {
			Assert.assertEquals(test.getParentId(), 1005);
			Assert.assertEquals(test.getName(), "getAlmTestById");
		}
	}

	@Test
	public void getEntities_withSingleFieldFilterSet_shouldReturnRequestedFieldsOnly() {
		for (final AlmTest test : alm.getEntities(AlmTest.class, new RestParameters().fields(AlmTestField.NAME))) {
			Assert.assertEquals(test.getCreationDate(), "292269055-12-02");
			Assert.assertEquals(test.getDescription(), "");
			Assert.assertEquals(test.getDesigner(), "");
			Assert.assertTrue(test.getId() != Integer.MIN_VALUE);
			Assert.assertTrue(!test.getName().equals(""));
			Assert.assertEquals(test.getParentId(), Integer.MIN_VALUE);
			Assert.assertEquals(test.getStatus(), "");
			Assert.assertEquals(test.getType(), "");

		}
	}

	@Test
	public void getEntities_withSingleQuerySet_shouldReturnAlmTestMatchingQuery() {
		final AlmEntityCollection<AlmTest> testCollection = alm.getEntities(AlmTest.class,
				new RestParameters().queryFilter(AlmTestField.NAME, "getAlmTestById"));
		for (final AlmTest test : testCollection) {
			Assert.assertEquals(test.getName(), "getAlmTestById");
		}
	}

	@Test
	public void getEntities_withStartIndexSet_shouldReturnAlmTestStartingAtStartIndex() {
		final AlmEntityCollection<AlmTest> initialTestCollection = alm.getEntities(AlmTest.class,
				new RestParameters().pageSize(1));
		final AlmEntityCollection<AlmTest> indexTwoCollection = alm.getEntities(AlmTest.class,
				new RestParameters().pageSize(1).startIndex(2));
		Assert.assertTrue((initialTestCollection.size() == 1) && (indexTwoCollection.size() == 1));
		for (final AlmTest test : indexTwoCollection) {
			Assert.assertFalse(initialTestCollection.contains(test));
		}
	}

	@Test
	public void getEntity_withAlmTestAndValidId_shouldReturnCorrectAlmTest() {
		final AlmTest almTest = alm.getEnityById(AlmTest.class, 1);
		Assert.assertNotNull(almTest);
		Assert.assertTrue(almTest.isExactMatch(getAlmTest1()),
				"expected:\n" + getAlmTest1() + "\nbut got:\n" + almTest);
	}

	@Test(expectedExceptions = AlmRestException.class)
	public void getEntity_withTestAndNonExistentId_shouldThrowException() {
		alm.getEnityById(AlmTest.class, 99999999);
	}

	@Test
	public void updateEntity_withFieldsSpecified_shouldUpdateAllSpecifiedFields() {
		final AlmTest test = alm.getEnityById(AlmTest.class, 180);
		final AlmTest intialTest = new AlmTest();
		intialTest.populateFields(test.createEntity());
		final String newName = "newNameFieldsSpecified" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();

		test.setName(newName);
		test.setDescription(newDescription);
		test.setCreationDate("2011-01-18");
		test.setDesigner("svc");
		test.setParentId(test.getParentId() == 1006 ? 1007 : 1006);
		test.setStatus(test.getStatus().equals("Design") ? "Repair" : "Design");
		test.setType(test.getType().equals("MANUAL") ? "SYSTEM-TEST" : "MANUAL");

		alm.updateEntity(test, AlmTestField.NAME, AlmTestField.DESCRIPTION);
		final AlmTest updatedTest = alm.getEnityById(AlmTest.class, 180);
		Assert.assertEquals(updatedTest.getCreationDate(), intialTest.getCreationDate());
		Assert.assertEquals(updatedTest.getDescription(), newDescription);
		Assert.assertEquals(updatedTest.getDesigner(), intialTest.getDesigner());
		Assert.assertEquals(updatedTest.getName(), newName);
		Assert.assertEquals(updatedTest.getParentId(), intialTest.getParentId());
	}

	@Test
	public void updateEntity_withoutSpecifyingFields_shouldUpdateAllEditableFields() {
		final AlmTest test = alm.getEnityById(AlmTest.class, 164);
		final String newName = "newName" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDescription = "newDescription" + System.currentTimeMillis() + Thread.currentThread().getId();
		final String newDesigner = test.getDesigner().equals("ssimmons") ? "svc" : "ssimmons";
		final String newCreationDate = test.getCreationDate().equals("2014-06-24") ? "2011-01-18" : "2014-06-24";
		final String newStatus = test.getStatus().equals("Design") ? "Repair" : "Design";
		final String newType = test.getType().equals("MANUAL") ? "SYSTEM-TEST" : "MANUAL";
		final int newParentId = test.getParentId() == 1006 ? 1007 : 1006;
		test.setName(newName);
		test.setDescription(newDescription);
		test.setDesigner(newDesigner);
		test.setCreationDate(newCreationDate);
		test.setStatus(newStatus);
		test.setType(newType);
		test.setParentId(newParentId);
		alm.updateEntity(test);
		final AlmTest updatedTest = alm.getEnityById(AlmTest.class, 164);
		Assert.assertEquals(updatedTest.getCreationDate(), newCreationDate);
		Assert.assertEquals(updatedTest.getDescription(), newDescription);
		Assert.assertEquals(updatedTest.getDesigner(), newDesigner);
		Assert.assertEquals(updatedTest.getName(), newName);
		Assert.assertEquals(updatedTest.getParentId(), newParentId);
	}

	private AlmTest createMinimumAlmTest(final String name) {
		final AlmTest test = new AlmTest();
		test.setName(name);
		test.setParentId(1004);
		test.setType("MANUAL");
		return test;
	}

	private String createUniqueName() {
		return "addedTest" + System.currentTimeMillis() + Thread.currentThread().getId();
	}

	private AlmTest getAlmTest1() {
		final AlmTest test = new AlmTest();
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(AlmTestField.ID.getName(), Arrays.asList("1")));
		fields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList("getAlmTestById")));
		fields.add(new Field(AlmTestField.CREATION_DATE.getName(), Arrays.asList("2014-03-21")));
		fields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList("getAlmTestById description")));
		fields.add(new Field(AlmTestField.DESIGNER.getName(), Arrays.asList("ssimmons")));
		fields.add(new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList("1002")));
		fields.add(new Field(AlmTestField.TYPE.getName(), Arrays.asList("MANUAL")));
		fields.add(new Field(AlmTestField.STATUS.getName(), Arrays.asList("Design")));
		fields.add(new Field("user-01", Arrays.asList("1", "3")));
		final GenericEntity entity = new GenericEntity("test", fields);
		test.populateFields(entity);
		return test;
	}
}
