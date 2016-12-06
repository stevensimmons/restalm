package com.fissionworks.restalm;

import com.fissionworks.restalm.constants.field.FieldName;
import com.fissionworks.restalm.filter.RestParameters;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.AlmEntityCollection;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;

/**
 * Provides connectivity to an deployed ALM instance, allowing the
 * retrieval/manipulation of ALM entities (tests, requirements, runs, etc.)
 *
 * @Since 1.0.0
 */
public interface ApplicationLifecycleManagement {
	/**
	 * Add a {@link AlmEntity} to ALM.
	 *
	 * @param entity
	 *            The resource entity to add.
	 * @return A copy of the entity as it was saved to ALM.
	 * @since 1.0.0
	 */
	<T extends AlmEntity> T addEntity(final T entity);

	/**
	 * Authenicate with ALM using the provided {@link Credentials}
	 *
	 * @param theCredentials
	 *            The credentials to authenicate with.
	 * @since 1.0.0
	 */
	void authenticate(final Credentials theCredentials);

	/**
	 * Delete a {@link AlmEntity} or list of {@link AlmEntity}s from ALM.
	 *
	 * @param entityClass
	 *            The type of entity/entities to be deleted.
	 * @param id
	 *            A list of id's to be deleted.
	 * @since 1.0.0
	 */
	<T extends AlmEntity> void deleteEntity(final Class<T> entityClass, final int... id);

	/**
	 * Returns the resource entity of the given type with the given id from ALM.
	 *
	 * @param entityClass
	 *            The type of entity to be retrieved.
	 * @param id
	 *            the id of the entity to be retrieved.
	 * @return The associated entity class.
	 * @since 1.0.0
	 */
	<T extends AlmEntity> T getEnityById(final Class<T> entityClass, final int id);

	/**
	 * Returns an {@link EntityFieldCollection} which contains information about
	 * the fields of the given entity class.
	 *
	 * @param entityClass
	 *            The entity class to retrieve field information about.
	 * @return The {@link EntityFieldCollection}.
	 * @since 1.0.0
	 */
	<T extends AlmEntity> EntityFieldCollection getEnityFields(final Class<T> entityClass);

	/**
	 * Get a collection of {@link AlmEntity} objects, using the give
	 * {@link RestParameters} to filter the return results.
	 *
	 * @param entityClass
	 *            The type of {@link AlmEntity} objects to retrieve.
	 * @param queryParameters
	 *            The parameters to use to filter the return results.
	 * @return An {@link AlmEntityCollection} with the that match the
	 *         {@link RestParameters} given.
	 * @since 1.0.0
	 */
	<T extends AlmEntity> AlmEntityCollection<T> getEntities(final Class<T> entityClass,
			final RestParameters queryParameters);

	/**
	 * Returns a {@link Site} object from the ALM instance currently
	 * authenticated with and connected to.
	 *
	 * @return The {@link Site} currently authencticated against.
	 * @since 1.0.0
	 */
	Site getSite();

	/**
	 * Has authentication with the ALM instance been completed?
	 *
	 * @return True if authenticated, false otherwise.
	 * @since 1.0.0
	 */
	boolean isAuthenticated();

	/**
	 * Is the current ALM instance logged in?
	 *
	 * @return True if the ALM instance is logged in, false otherwise.
	 * @since 1.0.0
	 */
	boolean isLoggedIn();

	/**
	 * Login to the given ALM {@link Project}.
	 *
	 * @param project
	 *            The {@link Project} to log into.
	 * @since 1.0.0
	 */
	void login(final Project project);

	/**
	 * Logs out of the currently logged in {@link Project}, and removes
	 * authentication.
	 *
	 * @since 1.0.0
	 */
	void logout();

	/**
	 * Updates all editable fields of the given {@link AlmEntity}. If attempting
	 * to edit only certain fields, use
	 * {@link #updateEntity(AlmEntity, FieldName...)} instead.
	 *
	 * @param almEntity
	 *            The {@link AlmEntity} to update.
	 * @since 1.0.0
	 */
	void updateEntity(final AlmEntity almEntity);

	/**
	 * Update the fields with the given names of the given {@link AlmEntity}.
	 * All other fields will not be altered.
	 *
	 * @param almEntity
	 *            The {@link AlmEntity} to update.
	 * @param fieldNames
	 *            The names of the fields to update.
	 * @since 1.0.0
	 */
	void updateEntity(final AlmEntity almEntity, FieldName... fieldNames);

}
