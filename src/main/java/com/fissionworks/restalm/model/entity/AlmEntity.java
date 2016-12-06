package com.fissionworks.restalm.model.entity;

import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Interface that describes methods common to/need for all ALM resource entity
 * objects.
 *
 * @since 1.0.0
 *
 */
public interface AlmEntity {

	/**
	 * Creates an {@link GenericEntity} object based on the currently populated
	 * fields of this resource entity.
	 *
	 * @return An {@link GenericEntity} containing all currently populated
	 *         fields.
	 * @since 1.0.0
	 */
	GenericEntity createEntity();

	/**
	 * Returns the collection type string related to this entity used in ALM
	 * rest URL's.
	 *
	 * @return the collection type string.
	 * @since 1.0.0
	 */
	String getEntityCollectionType();

	/**
	 * Returns the type string related to this entity used in ALM rest URL's.
	 *
	 * @return the type string.
	 * @since 1.0.0
	 */
	String getEntityType();

	/**
	 * get the id of this entity.
	 *
	 * @return The entity id.
	 * @since 1.0.0
	 */
	int getId();

	/**
	 * Populates the entity based on the fields/values contained in the provided
	 * {@link GenericEntity}.
	 *
	 * @param entity
	 *            The {@link GenericEntity} to use to populate the resource
	 *            entity fields.
	 * @since 1.0.0
	 */
	void populateFields(final GenericEntity entity);
}
