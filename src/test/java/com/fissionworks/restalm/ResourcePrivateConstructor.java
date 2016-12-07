package com.fissionworks.restalm;

import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class for unit testing only.
 *
 */
public class ResourcePrivateConstructor implements AlmEntity {

	private GenericEntity entity;

	/**
	 * parameterized constructor.
	 *
	 * @param string
	 *            a string.
	 */
	public ResourcePrivateConstructor(final String string, final GenericEntity associatedEntity) {
		new ResourcePrivateConstructor();
		this.entity = associatedEntity;
	}

	private ResourcePrivateConstructor() {
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
