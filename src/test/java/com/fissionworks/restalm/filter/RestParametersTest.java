package com.fissionworks.restalm.filter;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.MockAlmEntityField;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.FieldName;

public class RestParametersTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void fields_withEmptyArray_shouldThrowException() {
		final FieldName[] fields = new FieldName[0];
		new RestParameters().fields(fields);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void fields_withNullArray_shouldThrowException() {
		final FieldName[] fields = null;
		new RestParameters().fields(fields);
	}

	@Test
	public void getFields_withMultipleFieldsAdded_shouldReturnCommaSeperatedString() {
		final RestParameters qp = new RestParameters().fields(AlmTestField.NAME, AlmTestField.ID);
		final String fieldString = qp.getFields();
		Assert.assertTrue(fieldString.contains("id") && fieldString.contains("name"));
	}

	@Test
	public void getFields_withNoFieldsAdded_shouldReturnEmptyString() {
		Assert.assertEquals(new RestParameters().getFields(), "");
	}

	@Test
	public void getFields_withOneFieldAdded_shouldReturnSingleFieldString() {
		Assert.assertEquals(new RestParameters().fields(AlmTestField.NAME).getFields(), "name");
	}

	@Test
	public void getFields_withRelatedFieldAdded_shouldReturnSingleFieldString() {
		Assert.assertEquals(new RestParameters().relatedFields(AlmTestField.NAME).getFields(), "test.name");
	}

	@Test
	public void getPageSize_withNoPageSizeSet_shouldReturnDefaultPageSize() {
		Assert.assertEquals(new RestParameters().getPageSize(), 200);
	}

	@Test
	public void getPageSize_withPageSizeSet_shouldReturnPageSize() {
		Assert.assertEquals(new RestParameters().pageSize(10).getPageSize(), 10);
	}

	@Test
	public void getQueryStatements_withBothQueryAndCrossQueryStatements_shouldReturnQueryStatement() {
		Assert.assertEquals(
				new RestParameters().queryFilter(MockAlmEntityField.FIELD_ONE, "fieldOneValue")
						.crossQueryFilter(MockAlmEntityField.FIELD_TWO, "fieldTwoValue").getQueryStatements(),
				"{fieldOne[fieldOneValue];mockAlmEntity.fieldTwo[fieldTwoValue]}");
	}

	@Test
	public void getQueryStatements_withEmptyQueryStatements_shouldReturnEmptyString() {
		Assert.assertEquals(new RestParameters().getQueryStatements(), "{}");
	}

	@Test
	public void getQueryStatements_withMultipeCrossQueryStatement_shouldReturnQueryStatement() {
		Assert.assertEquals(
				new RestParameters().crossQueryFilter(MockAlmEntityField.FIELD_ONE, "fieldOneValue")
						.crossQueryFilter(MockAlmEntityField.FIELD_TWO, "fieldTwoValue").getQueryStatements(),
				"{mockAlmEntity.fieldOne[fieldOneValue];mockAlmEntity.fieldTwo[fieldTwoValue]}");
	}

	@Test
	public void getQueryStatements_withMultipleQueryStatement_shouldReturnQueryStatement() {
		final String queryStatements = new RestParameters().queryFilter(MockAlmEntityField.FIELD_ONE, "fieldOneValue")
				.queryFilter(MockAlmEntityField.FIELD_TWO, "fieldTwoValue").getQueryStatements();
		Assert.assertTrue(queryStatements.equals("{fieldOne[fieldOneValue];fieldTwo[fieldTwoValue]}")
				|| queryStatements.equals("{fieldTwo[fieldTwoValue];fieldOne[fieldOneValue]}"));
	}

	@Test
	public void getQueryStatements_withSingleCrossQueryStatement_shouldReturnQueryStatement() {
		Assert.assertEquals(new RestParameters().crossQueryFilter(MockAlmEntityField.FIELD_ONE, "fieldOneValue")
				.getQueryStatements(), "{mockAlmEntity.fieldOne[fieldOneValue]}");
	}

	@Test
	public void getQueryStatements_withSingleQueryStatement_shouldReturnQueryStatement() {
		Assert.assertEquals(
				new RestParameters().queryFilter(MockAlmEntityField.FIELD_ONE, "fieldOneValue").getQueryStatements(),
				"{fieldOne[fieldOneValue]}");
	}

	@Test
	public void getStartIndex_afterSettingStartIndex_shouldReturnStartIndex() {
		Assert.assertEquals(new RestParameters().startIndex(3).getStartIndex(), 3);
	}

	@Test
	public void getStartIndex_withoutSettingStartIndex_shouldReturnOne() {
		Assert.assertEquals(new RestParameters().getStartIndex(), 1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void pageSize_withPageSizeLessThanZero_shouldThrowException() {
		new RestParameters().pageSize(-1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void relatedFields_withEmptyArray_shouldThrowException() {
		final FieldName[] fields = new FieldName[0];
		new RestParameters().relatedFields(fields);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void relatedFields_withNullFieldName_shouldThrowException() {
		final FieldName[] fields = null;
		new RestParameters().relatedFields(fields);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startIndex_withStartIndexLessThanZero_shouldThrowException() {
		new RestParameters().startIndex(-1);
	}

}
