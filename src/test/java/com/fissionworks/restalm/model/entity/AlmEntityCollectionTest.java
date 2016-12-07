package com.fissionworks.restalm.model.entity;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.entity.testplan.AlmTest;

public class AlmEntityCollectionTest {

	@Test
	public void addEntity_shouldAddEntity() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		final AlmTest testOne = new AlmTest();
		testOne.setId(1);
		final AlmTest testTwo = new AlmTest();
		testTwo.setId(2);
		collection.addEntity(testOne);
		collection.addEntity(testTwo);

		Assert.assertEquals(collection.size(), 2);
	}

	@Test
	public void contains_withEntityContainedInCollection_shouldReturnTrue() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		final AlmTest testOne = new AlmTest();
		collection.addEntity(testOne);
		Assert.assertTrue(collection.contains(new AlmTest()));
	}

	@Test
	public void contains_withEntityNotContainedInCollection_shouldReturnFalse() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		final AlmTest testOne = new AlmTest();
		testOne.setId(12345);
		collection.addEntity(testOne);
		Assert.assertFalse(collection.contains(new AlmTest()));
	}

	@Test
	public void instantiate_shouldSetTotalResults() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		Assert.assertEquals(collection.getTotalResults(), 1337);
	}

	@Test
	public void iterator_shouldIterateAllAddedEntities() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		final AlmTest testOne = new AlmTest();
		testOne.setId(1);
		final AlmTest testTwo = new AlmTest();
		testTwo.setId(2);
		for (final AlmTest test : collection) {
			Assert.assertTrue(test.equals(testOne) || test.equals(testTwo));
		}
	}

	@Test
	public void toString_shouldReturnNonDefaultString() {
		final AlmEntityCollection<AlmTest> collection = new AlmEntityCollection<>(1337);
		Assert.assertTrue(StringUtils.contains(collection.toString(), "totalResults"));
	}

}
