package com.fissionworks.restalm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.constants.ServiceUrl;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.DesignStepField;
import com.fissionworks.restalm.constants.field.FieldName;
import com.fissionworks.restalm.constants.field.ReleaseCycleField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntityCollection;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;
import com.fissionworks.restalm.model.entity.management.ReleaseFolder;
import com.fissionworks.restalm.model.entity.requirements.Requirement;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.DesignStep;
import com.fissionworks.restalm.model.entity.testplan.TestFolder;
import com.fissionworks.restalm.model.site.Domain;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;

public class Alm115ConnectionTest {

	private static final Project PROJECT = new Project("domain", "project");
	private static final String URL = "http://oaoracle:8989";

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withAlmEntitywithNoDefaultConstructor_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);

		Whitebox.setInternalState(alm, "entityFieldMap",
				createEntityFieldMapWithTwoRequiredFields(ResourceNoDefaultConstructor.class));
		alm.addEntity(new ResourceNoDefaultConstructor("test", createMockEntityToAdd()));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withAlmEntitywithPrivateConstructor_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);

		Whitebox.setInternalState(alm, "entityFieldMap",
				createEntityFieldMapWithTwoRequiredFields(ResourcePrivateConstructor.class));
		alm.addEntity(new ResourcePrivateConstructor("test", createMockEntityToAdd()));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addEntity_withEntityWithoutAllRequiredFields_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity mockEntity = new MockAlmEntity(createMockEntityToAdd());
		final Map<Class<?>, EntityFieldCollection> requiredFields = createEntityFieldMapWithTwoRequiredFields(
				mockEntity.getClass());
		requiredFields.get(mockEntity.getClass())
				.addEntityField(createEntityField("fieldThree", "Field Three", false, true, true));

		Whitebox.setInternalState(alm, "entityFieldMap", requiredFields);
		alm.addEntity(mockEntity);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void addEntity_withNullEntity_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.addEntity(null);
	}

	@Test
	public void addEntity_withValidEntity_shouldReturnAddedEntity() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity mockEntity = new MockAlmEntity(createMockEntityToAdd());
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "entityFieldMap",
				createEntityFieldMapWithTwoRequiredFields(mockEntity.getClass()));
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(createAddedEntity()).when(rest).postForObject(ServiceUrl.ADD_ENTITY_URL.url(),
				mockEntity.createEntity(), GenericEntity.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType());

		final MockAlmEntity addedEntity = alm.addEntity(mockEntity);
		Assert.assertEquals(addedEntity.getPopulateFieldsEntity(), createAddedEntity());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void authenticate_withAuthenticatedAlm_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		Whitebox.setInternalState(alm, "rest", rest);

		alm.authenticate(new Credentials("user", "password"));
		Mockito.verify(rest).headForHeaders(ServiceUrl.IS_AUTHENTICATED.url(), URL);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void authenticate_withNullCredentials_shouldThrowException() {
		new Alm115Connection(URL).authenticate(null);
	}

	@Test
	public void authenticate_withValidCredentials_shouldAuthenticateWithAlm() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doThrow(AlmRestException.class).when(rest).headForHeaders(ServiceUrl.IS_AUTHENTICATED.url(), URL);

		alm.authenticate(new Credentials("user", "password"));
		Mockito.verify(rest).setInterceptors(Mockito.anyListOf(ClientHttpRequestInterceptor.class));
		Mockito.verify(rest).getForObject(ServiceUrl.GET_DOMAINS.url(), Site.class, URL);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void deleteEntity_withEmptyIdList_shouldThrowException() {
		final int[] integers = new int[0];
		new Alm115Connection(URL).deleteEntity(AlmTest.class, integers);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void deleteEntity_withNullclass_shouldThrowException() {
		new Alm115Connection(URL).deleteEntity(null, 5);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void deleteEntity_withNullIdList_shouldThrowException() {
		new Alm115Connection(URL).deleteEntity(AlmTest.class, null);
	}

	@Test
	public void deleteEntity_withValidIdList_shouldCompleteSucessfully() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);

		alm.deleteEntity(MockAlmEntity.class, 1, 2, 3);
		Mockito.verify(rest).delete(ServiceUrl.BULK_DELETE_URL.url(), URL, PROJECT.getDomain(),
				PROJECT.getProjectName(), "mockEntities", "1,2,3");
	}

	@Test
	public void getEntities_withEmptyCollectionReturnedFromRest_shouldReturnEmptyCollection() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(new GenericEntityCollection(0, new HashSet<GenericEntity>())).when(rest).getForObject(
				ServiceUrl.GET_ENTITY_COLLECTION.url(), GenericEntityCollection.class, URL, PROJECT.getDomain(),
				PROJECT.getProjectName(), mockEntity.getEntityCollectionType(), "", "{}", 200, 1);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters());
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 0);
	}

	@Test
	public void getEntities_withFieldsSetInQueryParameters_shouldExecuteQueryWithFields() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(createMockEntityToAdd());
		final GenericEntityCollection returnedCollection = new GenericEntityCollection(1337, entities);

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedCollection).when(rest).getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType(), "name,id", "{}", 200, 1);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters().fields(AlmTestField.NAME, AlmTestField.ID));
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 1337);
		Assert.assertEquals(actualCollection.size(), 1);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getEntities_withNullEntityClass_shouldThrowException() {
		new Alm115Connection(URL).getEntities(null, new RestParameters());
	}

	@Test
	public void getEntities_withPageSizeSetInQueryParameters_shouldReturnCollectionOfSpecifiedSize() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(createMockEntityToAdd());
		final GenericEntityCollection returnedCollection = new GenericEntityCollection(1337, entities);

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedCollection).when(rest).getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType(), "", "{}", 1, 1);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters().pageSize(1));
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 1337);
		Assert.assertEquals(actualCollection.size(), 1);
	}

	@Test
	public void getEntities_withPopulatedCollectionReturnedFormRest_shouldReturnCollection() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(createMockEntityToAdd());
		final GenericEntityCollection returnedCollection = new GenericEntityCollection(1337, entities);

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedCollection).when(rest).getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType(), "", "{}", 200, 1);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters());
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 1337);
		Assert.assertEquals(actualCollection.size(), 1);
	}

	@Test
	public void getEntities_withQuerySetInRestParameters_shouldExecuteQueryWithQueryStatements() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(createMockEntityToAdd());
		final GenericEntityCollection returnedCollection = new GenericEntityCollection(1337, entities);

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedCollection).when(rest).getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType(), "", "{fieldOne[one];fieldTwo[two]}", 200, 1);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters().queryFilter(MockAlmEntityField.FIELD_ONE, "one")
						.queryFilter(MockAlmEntityField.FIELD_TWO, "two"));
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 1337);
		Assert.assertEquals(actualCollection.size(), 1);
	}

	@Test
	public void getEntities_withStartIndexSetInQueryParameters_shouldExecuteQueryWithStartIndex() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(createMockEntityToAdd());
		final GenericEntityCollection returnedCollection = new GenericEntityCollection(1337, entities);

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedCollection).when(rest).getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(),
				mockEntity.getEntityCollectionType(), "", "{}", 200, 5);
		final AlmEntityCollection<MockAlmEntity> actualCollection = alm.getEntities(MockAlmEntity.class,
				new RestParameters().startIndex(5));
		Assert.assertNotNull(actualCollection);
		Assert.assertEquals(actualCollection.getTotalResults(), 1337);
		Assert.assertEquals(actualCollection.size(), 1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getEntityById_withNegativeId_shouldThrowException() {
		new Alm115Connection(URL).getEnityById(MockAlmEntity.class, -1);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getEntityById_withNullEntityClass_shouldThrowException() {
		new Alm115Connection(URL).getEnityById(null, 5);
	}

	@Test
	public void getEntityById_withValidArguments_shouldReturnEntity() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final MockAlmEntity mockEntity = new MockAlmEntity();
		final GenericEntity returnedEntity = createAddedEntity();

		Whitebox.setInternalState(alm, "rest", rest);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Mockito.doReturn(returnedEntity).when(rest).getForObject(ServiceUrl.ENTITY_BY_ID.url(), GenericEntity.class,
				URL, PROJECT.getDomain(), PROJECT.getProjectName(), mockEntity.getEntityCollectionType(), 1337);
		final MockAlmEntity actualEntity = alm.getEnityById(MockAlmEntity.class, 1337);
		Assert.assertNotNull(actualEntity);
		Assert.assertEquals(actualEntity.getPopulateFieldsEntity(), returnedEntity);
	}

	@Test
	public void getEntityFields_withAlmTest_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(createEntityField(AlmTestField.PARENT_ID.getName(), "Parent ID", false, true, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "test");

		Assert.assertTrue(alm.getEnityFields(AlmTest.class).getFields()
				.contains(createEntityField(AlmTestField.PARENT_ID.getName(), "Parent ID", true, true, true)));
	}

	@Test
	public void getEntityFields_withAlmTestFolder_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(createEntityField(TestFolderField.PARENT_ID.getName(), "Parent ID", false, true, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "test-folder");

		Assert.assertTrue(alm.getEnityFields(TestFolder.class).getFields()
				.contains(createEntityField(TestFolderField.PARENT_ID.getName(), "Parent ID", true, true, true)));
	}

	@Test
	public void getEntityFields_withDesignStep_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(createEntityField(DesignStepField.PARENT_ID.getName(), "Test ID", false, false, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "design-step");

		Assert.assertTrue(alm.getEnityFields(DesignStep.class).getFields()
				.contains(createEntityField(DesignStepField.PARENT_ID.getName(), "Test ID", false, true, true)));
	}

	@Test
	public void getEntityFields_withEmptyFieldMap_shouldRetrieveFieldsAndReturn() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity mockEntity = new MockAlmEntity(createMockEntityToAdd());
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(createEntityFieldMapWithTwoRequiredFields(mockEntity.getClass()).get(mockEntity.getClass()))
				.when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(), EntityFieldCollection.class, URL,
						PROJECT.getDomain(), PROJECT.getProjectName(), mockEntity.getEntityType());

		Assert.assertEquals(alm.getEnityFields(mockEntity.getClass()),
				createEntityFieldMapWithTwoRequiredFields(mockEntity.getClass()).get(mockEntity.getClass()));
	}

	@Test
	public void getEntityFields_withEntityClassAlreadyInFieldMap_shouldReturnEntityFieldCollection() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity mockEntity = new MockAlmEntity(createMockEntityToAdd());

		Whitebox.setInternalState(alm, "entityFieldMap",
				createEntityFieldMapWithTwoRequiredFields(mockEntity.getClass()));
		Assert.assertEquals(alm.getEnityFields(mockEntity.getClass()),
				createEntityFieldMapWithTwoRequiredFields(mockEntity.getClass()).get(mockEntity.getClass()));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getEntityFields_withEntityClassWithNoDefaultConstructor_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		alm.getEnityFields(ResourceNoDefaultConstructor.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getEntityFields_withEntityClassWithPrivateConstructor_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		alm.getEnityFields(ResourcePrivateConstructor.class);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getEntityFields_withNullEntityClass_shouldThrowException() {
		new Alm115Connection(URL).getEnityFields(null);
	}

	@Test
	public void getEntityFields_withRelease_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(createEntityField(ReleaseField.PARENT_ID.getName(), "Parent ID", false, true, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "release");

		Assert.assertTrue(alm.getEnityFields(Release.class).getFields()
				.contains(createEntityField(ReleaseField.PARENT_ID.getName(), "Parent ID", true, true, true)));
	}

	@Test
	public void getEntityFields_withReleaseCycle_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(createEntityField(ReleaseCycleField.PARENT_ID.getName(), "Parent ID", false, true, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "release-cycle");

		Assert.assertTrue(alm.getEnityFields(ReleaseCycle.class).getFields()
				.contains(createEntityField(ReleaseCycleField.PARENT_ID.getName(), "Parent ID", true, true, true)));
	}

	@Test
	public void getEntityFields_withReleaseFolder_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(
				createEntityField(ReleaseFolderField.PARENT_ID.getName(), "Parent ID", false, true, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "release-folder");

		Assert.assertTrue(alm.getEnityFields(ReleaseFolder.class).getFields()
				.contains(createEntityField(ReleaseFolderField.PARENT_ID.getName(), "Parent ID", true, true, true)));
	}

	@Test
	public void getEntityFields_withRequirement_shouldReturnEntityFieldCollectionWithCorrectedFields() {
		final Alm115Connection alm = new Alm115Connection(URL);

		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);
		final EntityFieldCollection fields = new EntityFieldCollection();
		fields.addEntityField(
				createEntityField(RequirementField.PARENT_ID.getName(), "Requirement ID", false, false, true));

		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(fields).when(rest).getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, URL, PROJECT.getDomain(), PROJECT.getProjectName(), "requirement");

		Assert.assertTrue(alm.getEnityFields(Requirement.class).getFields().contains(
				createEntityField(RequirementField.PARENT_ID.getName(), "Requirement ID", true, false, true)));
	}

	@Test
	public void getSite_shouldReturnSite() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doReturn(new Site()).when(rest).getForObject(ServiceUrl.GET_DOMAINS.url(), Site.class, URL);
		Assert.assertNotNull(alm.getSite());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiate_withBlankUrl_shouldThrowException() {
		new Alm115Connection("  ");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiate_withNullUrl_shouldThrowException() {
		new Alm115Connection(null);
	}

	@Test
	public void isAuthenticated_withAuthenticatedAlmInstance_shouldReturnTrue() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "rest", rest);
		Assert.assertTrue(alm.isAuthenticated());
	}

	@Test
	public void isAuthenticated_withUnauthenticatedAlmInstance_shouldReturnFalse() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "rest", rest);
		Mockito.doThrow(AlmRestException.class).when(rest).headForHeaders(ServiceUrl.IS_AUTHENTICATED.url(), URL);
		Assert.assertFalse(alm.isAuthenticated());
	}

	@Test
	public void isLoggedIn_withLoggedInAlmConnection_shouldReturnTrue() {
		final Alm115Connection alm = new Alm115Connection(URL);
		Whitebox.setInternalState(alm, "currentProject", PROJECT);

		Assert.assertTrue(alm.isLoggedIn());
	}

	@Test
	public void isLoggedIn_withNonLoggedInAlmConnection_shouldReturnFalse() {
		final Alm115Connection alm = new Alm115Connection(URL);
		Assert.assertFalse(alm.isLoggedIn());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void login_withNonExistentProject_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		Whitebox.setInternalState(alm, "site", new Site());

		alm.login(PROJECT);
	}

	@Test
	public void login_withValidExistingProject_shouldSetCurrentProject() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final Site site = new Site();
		final Domain domain = new Domain("domain");
		domain.addProject(PROJECT);
		site.addDomain(domain);
		Whitebox.setInternalState(alm, "site", site);

		alm.login(PROJECT);
	}

	@Test
	public void logout_shouldClearProjectAndHeaders() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "rest", rest);
		alm.logout();
		Assert.assertTrue(Whitebox.getInternalState(alm, "currentProject") == null);
		Mockito.verify(rest).headForHeaders(ServiceUrl.LOGOUT.url(), URL);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateEntity_withEntityHavingNegativeId_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity entity = new MockAlmEntity();
		entity.setId(-1);
		alm.updateEntity(entity);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateEntity_withFieldListAndEntityHavingNegativeId_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final MockAlmEntity entity = new MockAlmEntity();
		entity.setId(-1);
		alm.updateEntity(entity, MockAlmEntityField.FIELD_ONE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateEntity_withFieldNameThatIsNotEditable_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final Map<Class<?>, EntityFieldCollection> fieldMap = new HashMap<>();
		final EntityFieldCollection collection = new EntityFieldCollection();
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_ONE.getName(), "Field One", true, true, true));
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_TWO.getName(), "Field Two", true, true, true));
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_THREE.getName(), "Field Two", false, true, true));
		fieldMap.put(MockAlmEntity.class, collection);
		Whitebox.setInternalState(alm, "entityFieldMap", fieldMap);

		final MockAlmEntity entity = new MockAlmEntity();
		entity.setId(1337);
		alm.updateEntity(entity, MockAlmEntityField.FIELD_THREE);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateEntity_withFieldsThatAreNotEditable_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		Whitebox.setInternalState(alm, "entityFieldMap", createMockEntityFieldMap());

		Whitebox.setInternalState(alm, "rest", rest);
		final MockAlmEntity entity = new MockAlmEntity();
		entity.setId(1337);
		alm.updateEntity(entity, MockAlmEntityField.FIELD_THREE);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateEntity_withNullEntity_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.updateEntity(null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateEntity_withNullEntityAndFieldList_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.updateEntity(null, AlmTestField.NAME);
	}

	@Test
	public void updateEntity_withValidEntity_shouldUpdateEntity() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("normalField", Arrays.asList("one", "two")));
		fields.add(new Field("normalField2", Arrays.asList("three")));
		final GenericEntity updatedEntity = new GenericEntity("test", fields);
		fields.add(new Field("uneditableField", Arrays.asList("four")));
		final GenericEntity genericEntity = new GenericEntity("test", fields);
		final MockAlmEntity entity = new MockAlmEntity(genericEntity);

		Whitebox.setInternalState(alm, "entityFieldMap", createEntityFieldMapWithTwoEditableFields(entity.getClass()));
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		alm.updateEntity(entity);
		Mockito.verify(rest).put(ServiceUrl.ENTITY_BY_ID.url(), updatedEntity, URL, PROJECT.getDomain(),
				PROJECT.getProjectName(), "mockEntities", 0);
		Assert.assertEquals(genericEntity, updatedEntity);

	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void updateEntity_withValidEntityAndEmptyFieldList_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final FieldName[] fieldNames = new FieldName[0];
		alm.updateEntity(new MockAlmEntity(), fieldNames);
	}

	@Test
	public void updateEntity_withValidEntityAndFieldList_shouldUpdateEntity() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final RestTemplate rest = PowerMockito.mock(RestTemplate.class);

		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(MockAlmEntityField.FIELD_ONE.getName(), Arrays.asList("one", "two")));
		final GenericEntity updatedEntity = new GenericEntity("test", fields);
		fields.add(new Field(MockAlmEntityField.FIELD_TWO.getName(), Arrays.asList("three")));
		fields.add(new Field(MockAlmEntityField.FIELD_THREE.getName(), Arrays.asList("four")));
		final GenericEntity genericEntity = new GenericEntity("test", fields);
		final MockAlmEntity entity = new MockAlmEntity(genericEntity);

		Whitebox.setInternalState(alm, "entityFieldMap", createMockEntityFieldMap());
		Whitebox.setInternalState(alm, "currentProject", PROJECT);
		Whitebox.setInternalState(alm, "rest", rest);
		alm.updateEntity(entity, MockAlmEntityField.FIELD_ONE);
		Mockito.verify(rest).put(ServiceUrl.ENTITY_BY_ID.url(), updatedEntity, URL, PROJECT.getDomain(),
				PROJECT.getProjectName(), "mockEntities", 0);
		Assert.assertEquals(genericEntity, updatedEntity);

	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateEntity_withValidEntityAndNullFieldList_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		final FieldName[] fieldNames = null;
		alm.updateEntity(new MockAlmEntity(), fieldNames);
	}

	private GenericEntity createAddedEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldThree", Arrays.asList("one", "two")));
		fields.add(new Field("fieldFour", Arrays.asList("three")));
		return new GenericEntity("test", fields);
	}

	private EntityField createEntityField(final String name, final String label, final boolean editable,
			final boolean required, final boolean system) {
		final EntityField field = new EntityField(name, label);
		field.setEditable(editable);
		field.setRequired(required);
		field.setSystem(system);
		return field;
	}

	private Map<Class<?>, EntityFieldCollection> createEntityFieldMapWithTwoEditableFields(final Class<?> clazz) {
		final Map<Class<?>, EntityFieldCollection> fieldMap = new HashMap<>();
		final EntityFieldCollection collection = new EntityFieldCollection();
		collection.addEntityField(createEntityField("normalField", "Field One", true, true, true));
		collection.addEntityField(createEntityField("normalField2", "Field Two", true, true, true));
		collection.addEntityField(createEntityField("uneditableField", "Field Two", false, true, true));
		fieldMap.put(clazz, collection);
		return fieldMap;
	}

	private Map<Class<?>, EntityFieldCollection> createEntityFieldMapWithTwoRequiredFields(final Class<?> clazz) {
		final Map<Class<?>, EntityFieldCollection> fieldMap = new HashMap<>();
		final EntityFieldCollection collection = new EntityFieldCollection();
		collection.addEntityField(createEntityField("fieldOne", "Field One", false, true, true));
		collection.addEntityField(createEntityField("fieldTwo", "Field Two", false, true, true));
		fieldMap.put(clazz, collection);
		return fieldMap;
	}

	private Map<Class<?>, EntityFieldCollection> createMockEntityFieldMap() {
		final Map<Class<?>, EntityFieldCollection> fieldMap = new HashMap<>();
		final EntityFieldCollection collection = new EntityFieldCollection();
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_ONE.getName(), "Field One", true, true, true));
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_TWO.getName(), "Field Two", true, true, true));
		collection.addEntityField(
				createEntityField(MockAlmEntityField.FIELD_THREE.getName(), "Field Three", false, true, true));
		fieldMap.put(MockAlmEntity.class, collection);
		return fieldMap;
	}

	private GenericEntity createMockEntityToAdd() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("one", "two")));
		fields.add(new Field("fieldTwo", Arrays.asList("three")));
		return new GenericEntity("test", fields);
	}
}
