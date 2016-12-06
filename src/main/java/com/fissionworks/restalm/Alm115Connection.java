package com.fissionworks.restalm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fissionworks.restalm.constants.ServiceUrl;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.DesignStepField;
import com.fissionworks.restalm.constants.field.FieldName;
import com.fissionworks.restalm.constants.field.ReleaseCycleField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.conversion.error.AlmResponseErrorHandler;
import com.fissionworks.restalm.conversion.marshalling.EntityCollectionMarshaller;
import com.fissionworks.restalm.conversion.marshalling.EntityFieldCollectionMarshaller;
import com.fissionworks.restalm.conversion.marshalling.EntityMarshaller;
import com.fissionworks.restalm.conversion.marshalling.SiteMarshaller;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.http.HttpHeaderManager;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntityCollection;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;
import com.fissionworks.restalm.model.entity.management.ReleaseFolder;
import com.fissionworks.restalm.model.entity.requirements.Requirement;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.DesignStep;
import com.fissionworks.restalm.model.entity.testplan.TestFolder;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;

/**
 * An implementation of the {@link ApplicationLifecycleManagement} interface
 * that is compatible with ALM 11.5. This implementation is NOT thread safe.
 *
 * @since 1.0.0
 *
 */
public final class Alm115Connection implements ApplicationLifecycleManagement {

	private static final Logger LOGGER = LoggerFactory.getLogger(Alm115Connection.class);
	private Project currentProject;
	private final Map<Class<?>, EntityFieldCollection> entityFieldMap = new HashMap<>();
	private final RestTemplate rest;
	private Site site;
	private final String url;

	/**
	 * Constructs an connection that allows communication with the provided URL.
	 * {@link #authenticate(Credentials)} must be called before interacting with
	 * the ALM instance.
	 *
	 * @param theUrl
	 *            The URL of the deployed ALM instance.
	 * @throws NullPointerException
	 *             Thrown if URL is null.
	 * @throws IllegalArgumentException
	 *             Thrown if URL is blank (empty string).
	 * @since 1.0.0
	 */
	public Alm115Connection(final String theUrl) {
		Validate.notBlank(theUrl, "The URL cannot be null or blank");
		this.url = theUrl;
		this.rest = new RestTemplate();
		rest.setMessageConverters(getMessageConverters());
		rest.setErrorHandler(new AlmResponseErrorHandler());
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public <T extends AlmEntity> T addEntity(final T resourceEntity) {
		Validate.notNull(resourceEntity, "resource entity cannot be null");
		final GenericEntity entity = createEntityForAdd(resourceEntity);

		// no need to check since resourceEntity is known to be of type T
		@SuppressWarnings("unchecked")
		final T addedEntity = (T) this.createEntity(resourceEntity.getClass());
		addedEntity.populateFields(rest.postForObject(ServiceUrl.ADD_ENTITY_URL.url(), entity, GenericEntity.class, url,
				currentProject.getDomain(), currentProject.getProjectName(), resourceEntity.getEntityCollectionType()));
		return addedEntity;
	}

	/**
	 * @throws NullPointerException
	 *             if the {@link Credentials} are null.
	 * @since 1.0.0
	 */
	@Override
	public void authenticate(final Credentials theCredentials) {
		Validate.notNull(theCredentials);
		if (isAuthenticated()) {
			throw new IllegalStateException(
					"Already authenticated by ALM; logout before calling authenticate with new credentials");
		}

		final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HttpHeaderManager(theCredentials));
		rest.setInterceptors(interceptors);
		rest.headForHeaders(ServiceUrl.AUTHENTICATE.url(), url);
		site = getSite();
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public <T extends AlmEntity> void deleteEntity(final Class<T> entityClass, final int... id) {
		Validate.notNull(entityClass, "entity class must not be null");
		Validate.notNull(id, "id list must not be null");
		Validate.isTrue(id.length > 0, "At least one id to delete must be provided");

		rest.delete(ServiceUrl.BULK_DELETE_URL.url(), url, currentProject.getDomain(), currentProject.getProjectName(),
				createEntity(entityClass).getEntityCollectionType(), createCommaSeparatedIdString(id));
	}

	/**
	 * @throws NullPointerException
	 *             thrown if entity class is null.
	 * @throws IllegalArgumentException
	 *             thrown if id is negative.
	 * @since 1.0.0
	 */
	@Override
	public <T extends AlmEntity> T getEnityById(final Class<T> entityClass, final int id) {
		Validate.notNull(entityClass, "entityClass cannot be null");
		Validate.isTrue(id >= 0, "id cannot be negative");
		final T entity = createEntity(entityClass);
		entity.populateFields(rest.getForObject(ServiceUrl.ENTITY_BY_ID.url(), GenericEntity.class, this.url,
				this.currentProject.getDomain(), this.currentProject.getProjectName(), entity.getEntityCollectionType(),
				id));
		return entity;
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public <T extends AlmEntity> EntityFieldCollection getEnityFields(final Class<T> entityClass) {
		Validate.notNull(entityClass, "entityClass cannot be null");
		if (this.entityFieldMap.containsKey(entityClass)) {
			return entityFieldMap.get(entityClass);
		}
		entityFieldMap.put(entityClass, getCorrectedEntityFields(entityClass));

		return entityFieldMap.get(entityClass);
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public <T extends AlmEntity> AlmEntityCollection<T> getEntities(final Class<T> entityClass,
			final RestParameters queryParameters) {
		Validate.notNull(entityClass, "entityClass cannot be null");
		final GenericEntityCollection genericEntities = rest.getForObject(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				GenericEntityCollection.class, url, currentProject.getDomain(), currentProject.getProjectName(),
				createEntity(entityClass).getEntityCollectionType(), queryParameters.getFields(),
				queryParameters.getQueryStatements(), queryParameters.getPageSize(), queryParameters.getStartIndex());
		final AlmEntityCollection<T> entities = new AlmEntityCollection<>(genericEntities.getTotalResults());
		for (final GenericEntity genericEntity : genericEntities) {
			final T entity = createEntity(entityClass);
			entity.populateFields(genericEntity);
			entities.addEntity(entity);
		}
		return entities;
	}

	/**
	 * {@inheritDoc}; Login is not required prior to using this method.
	 *
	 * @since 1.0.0
	 */
	@Override
	public Site getSite() {
		return rest.getForObject(ServiceUrl.GET_DOMAINS.url(), Site.class, this.url);
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public boolean isAuthenticated() {
		try {
			rest.headForHeaders(ServiceUrl.IS_AUTHENTICATED.url(), url);
			return true;
		} catch (final AlmRestException exception) {
			// An AlmRestExcpetion is expected to be thrown if connection has
			// not yet been authenticated.
			return false;
		}
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public boolean isLoggedIn() {
		return this.currentProject != null;
	}

	/**
	 * @throws IllegalArgumentException
	 *             Thrown if the {@link Project} does not exist in the site
	 *             currently authenticated with.
	 * @since 1.0.0
	 */
	@Override
	public void login(final Project project) {
		Validate.isTrue(site.containsProject(project),
				"Project does not exist/currently authenticated user does not have permissions for this project");
		this.currentProject = project;
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public void logout() {
		this.currentProject = null;
		this.rest.headForHeaders(ServiceUrl.LOGOUT.url(), this.url);
		this.rest.getInterceptors().clear();
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public void updateEntity(final AlmEntity almEntity) {
		Validate.notNull(almEntity, "Entity must not be null");
		Validate.isTrue(almEntity.getId() >= 0, "entity must have an ID set to be updated");
		final GenericEntity entity = createEntityForUpdate(almEntity);
		this.rest.put(ServiceUrl.ENTITY_BY_ID.url(), entity, this.url, this.currentProject.getDomain(),
				this.currentProject.getProjectName(), almEntity.getEntityCollectionType(), almEntity.getId());
	}

	/**
	 * @since 1.0.0
	 */
	@Override
	public void updateEntity(final AlmEntity almEntity, final FieldName... fieldNames) {
		Validate.notNull(almEntity, "almEntity cannot be null");
		Validate.notNull(fieldNames, "fieldNames cannot be null");
		Validate.isTrue(almEntity.getId() >= 0, "entity must have an ID set to be updated");
		Validate.isTrue(fieldNames.length > 0);
		verifyFieldsAreEditable(almEntity, fieldNames);

		final GenericEntity entity = this.createEntityForUpdate(almEntity);
		entity.removeExtraFields(Arrays.asList(fieldNames));
		this.rest.put(ServiceUrl.ENTITY_BY_ID.url(), entity, this.url, this.currentProject.getDomain(),
				this.currentProject.getProjectName(), almEntity.getEntityCollectionType(), almEntity.getId());
	}

	private String createCommaSeparatedIdString(final int... id) {
		final StringBuilder sb = new StringBuilder();
		for (final int element : id) {
			sb.append(element);
			sb.append(",");
		}
		return StringUtils.chop(sb.toString());
	}

	private <T extends AlmEntity> T createEntity(final Class<T> entityClass) {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.warn("Error creating resource entity; Resource entities must have default constructor");
		}
		throw new IllegalArgumentException(
				"Error creating resource entity; Resource entities must have default constructor");
	}

	private GenericEntity createEntityForAdd(final AlmEntity resourceEntity) {
		final GenericEntity entity = resourceEntity.createEntity();
		final EntityFieldCollection entityFields = this.getEnityFields(resourceEntity.getClass());
		entity.validateRequiredFieldsPresent(entityFields);
		entity.formatForAdd(entityFields);
		return entity;
	}

	private GenericEntity createEntityForUpdate(final AlmEntity resourceEntity) {
		final GenericEntity entity = resourceEntity.createEntity();
		final EntityFieldCollection entityFields = this.getEnityFields(resourceEntity.getClass());
		entity.formatForUpdate(entityFields);
		return entity;
	}

	/*
	 * Fixes field parameter values (editable, required, etc.) that ALM does not
	 * correctly report.
	 */
	private <T extends AlmEntity> EntityFieldCollection getCorrectedEntityFields(final Class<T> entityClass) {
		final EntityFieldCollection fields = rest.getForObject(ServiceUrl.GET_ENTITY_FIELDS.url(),
				EntityFieldCollection.class, this.url, this.currentProject.getDomain(),
				this.currentProject.getProjectName(), getEntityType(entityClass));
		if (entityClass.equals(AlmTest.class)) {
			fields.setEditable(AlmTestField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(TestFolder.class)) {
			fields.setEditable(TestFolderField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(DesignStep.class)) {
			fields.setRequired(DesignStepField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(ReleaseFolder.class)) {
			fields.setEditable(ReleaseFolderField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(Release.class)) {
			fields.setEditable(ReleaseField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(ReleaseCycle.class)) {
			fields.setEditable(ReleaseCycleField.PARENT_ID.getName(), true);
		} else if (entityClass.equals(Requirement.class)) {
			fields.setEditable(RequirementField.PARENT_ID.getName(), true);
		}
		return fields;
	}

	private String getEntityType(final Class<? extends AlmEntity> entityClass) {
		try {
			return entityClass.newInstance().getEntityType();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.warn("Error retrieving resource entity; unable to determine collection type");
		}
		throw new IllegalArgumentException("Error retrieving resource entity; unable to determine collection type");
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		final List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new SiteMarshaller());
		converters.add(new EntityMarshaller());
		converters.add(new EntityFieldCollectionMarshaller());
		converters.add(new EntityCollectionMarshaller());
		return converters;
	}

	private void verifyFieldsAreEditable(final AlmEntity almEntity, final FieldName... fieldNames) {
		final EntityFieldCollection entityFields = this.getEnityFields(almEntity.getClass());
		for (final FieldName fieldName : fieldNames) {
			Validate.isTrue(entityFields.getEntityField(fieldName.getName()).isEditable(),
					"Field with name |" + fieldName.getName() + "| is not editable");
		}
	}

}
