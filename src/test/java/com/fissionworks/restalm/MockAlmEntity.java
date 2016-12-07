package com.fissionworks.restalm;

import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class MockAlmEntity implements AlmEntity {

	private GenericEntity entity;

	private GenericEntity populateFieldsEntity;

	private int id;

	public MockAlmEntity() {
	}

	public MockAlmEntity(final GenericEntity associatedEntity) {
		this.entity = associatedEntity;
	}

	@Override
	public GenericEntity createEntity() {
		return entity;
	}

	@Override
	public String getEntityCollectionType() {
		return "mockEntities";
	}

	@Override
	public String getEntityType() {
		return "mockEntity";
	}

	@Override
	public int getId() {
		return id;
	}

	public GenericEntity getPopulateFieldsEntity() {
		return this.populateFieldsEntity;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		this.populateFieldsEntity = entity;
	}

	public void setId(final int theId) {
		this.id = theId;
	}

}
