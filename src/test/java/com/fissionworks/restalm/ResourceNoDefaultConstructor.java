package com.fissionworks.restalm;

import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class for unit testing only.
 *
 */
public class ResourceNoDefaultConstructor implements AlmEntity {

	private final GenericEntity entity;

	/**
	 * paramterized constructor.
	 *
	 * @param string
	 *            a String.
	 */
	public ResourceNoDefaultConstructor(final String string, final GenericEntity associatedEntity) {
		this.entity = associatedEntity;
	}

	@Override
	public GenericEntity createEntity() {
		return entity;
	}

	@Override
	public String getEntityCollectionType() {
		return null;
	}

	@Override
	public String getEntityType() {
		return null;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void populateFields(final GenericEntity entity) {

	}

}
