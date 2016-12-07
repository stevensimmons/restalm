package com.fissionworks.restalm.model.entity.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GenericEntityCollectionTest {

	@Test
	public void equals_comparingObjectToDifferentEntitites_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final Set<GenericEntity> entitiesTwo = new HashSet<>();
		entities.add(entity);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		final GenericEntityCollection collectionTwo = new GenericEntityCollection(10, entitiesTwo);
		Assert.assertFalse(collection.equals(collectionTwo));
	}

	@Test
	public void equals_comparingObjectToDifferentObjectType_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		Assert.assertFalse(collection.equals(new Object()));
	}

	@Test
	public void equals_comparingObjectToDifferentTotalResults_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		final GenericEntityCollection collectionTwo = new GenericEntityCollection(100, entities);
		Assert.assertFalse(collection.equals(collectionTwo));
	}

	@Test
	public void equals_comparingObjectToEqualObject_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		final GenericEntityCollection collectionTwo = new GenericEntityCollection(10, entities);
		Assert.assertTrue(collection.equals(collectionTwo));
	}

	@Test
	public void equals_comparingObjectToItself_shouldReturnTrue() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		Assert.assertTrue(collection.equals(collection));
	}

	@Test
	public void equals_comparingObjectToNull_shouldReturnFalse() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		Assert.assertFalse(collection.equals(null));
	}

	@Test
	public void hashCode_withEqualCollections_shouldBeEqual() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		final GenericEntityCollection collectionTwo = new GenericEntityCollection(10, entities);
		Assert.assertEquals(collection.hashCode(), collectionTwo.hashCode());
	}

	@Test
	public void hashCode_withUnequalCollections_shouldNotBeEqual() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final Set<GenericEntity> entitiesTwo = new HashSet<>();
		entities.add(entity);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		final GenericEntityCollection collectionTwo = new GenericEntityCollection(10, entitiesTwo);
		Assert.assertNotEquals(collection.hashCode(), collectionTwo.hashCode());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiate_withTotalResultsSizeLessThanZero_shouldThrowException() {
		new GenericEntityCollection(-1, new HashSet<GenericEntity>());
	}

	@Test
	public void size_shouldReturnSizeOfCollection() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		Assert.assertEquals(collection.size(), 2);
	}

	@Test
	public void toString_shouldReturnNonDefaultString() {
		final GenericEntity entity = new GenericEntity("theType", createFieldListOne());
		final GenericEntity entityTwo = new GenericEntity("theType", createFieldListTwo());
		final Set<GenericEntity> entities = new HashSet<>();
		entities.add(entity);
		entities.add(entityTwo);

		final GenericEntityCollection collection = new GenericEntityCollection(10, entities);
		Assert.assertTrue(StringUtils.contains(collection.toString(), "entities"));
	}

	private List<Field> createFieldListOne() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("valueOne")));
		fields.add(new Field("fieldTwo", Arrays.asList("valueTwo")));
		fields.add(new Field("fieldThree", Arrays.asList("valueOne", "valueTwo", "valueThree")));
		return fields;
	}

	private List<Field> createFieldListTwo() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldFour", Arrays.asList("valueOne")));
		fields.add(new Field("fieldFive", Arrays.asList("valueTwo")));
		fields.add(new Field("fieldSix", Arrays.asList("valueOne", "valueTwo", "valueThree")));
		return fields;
	}
}
