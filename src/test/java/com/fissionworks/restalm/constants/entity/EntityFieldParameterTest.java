package com.fissionworks.restalm.constants.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityFieldParameterTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fromParameterName_withInvalidName_shouldThrowException() {
		EntityFieldParameter.fromParameterName("notAFieldName");
	}

	@Test
	public void fromParameterName_withValidName_shouldReturnEntityFieldParamter() {
		Assert.assertEquals(EntityFieldParameter.fromParameterName("Required"), EntityFieldParameter.REQUIRED);
	}
}
