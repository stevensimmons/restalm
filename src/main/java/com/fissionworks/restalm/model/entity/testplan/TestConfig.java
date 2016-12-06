package com.fissionworks.restalm.model.entity.testplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.field.TestConfigField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a test-config in ALM.
 *
 * @since 1.0.0
 *
 */
public final class TestConfig implements AlmEntity {

	public static final String COLLECTION_TYPE = "test-configs";

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test-config";

	private String createdBy = "";

	private final Calendar creationDate = Calendar.getInstance();

	private final List<Field> customFields = new ArrayList<>();

	private int dataState;

	private String description = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private AlmTest parentAlmTest;

	private int parentId = Integer.MIN_VALUE;

	private String testName = "";

	/**
	 * Default constructor; sets creation date to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public TestConfig() {
		creationDate.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(creationDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(TestConfigField.CREATION_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(creationDate))));
		fields.add(new Field(TestConfigField.CREATED_BY.getName(), Arrays.asList(this.createdBy)));
		fields.add(new Field(TestConfigField.DATA_STATE.getName(), Arrays.asList(String.valueOf(this.dataState))));
		fields.add(new Field(TestConfigField.DESCRIPTION.getName(), Arrays.asList(this.description)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(TestConfigField.ID.getName(), Arrays.asList(String.valueOf(this.id))));
		fields.add(new Field(TestConfigField.NAME.getName(), Arrays.asList(this.name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(TestConfigField.PARENT_ID.getName(), Arrays.asList(String.valueOf(this.parentId))));
		fields.add(new Field(TestConfigField.TEST_NAME.getName(), Arrays.asList(this.testName)));
		fields.removeAll(Collections.singleton(null));
		return new GenericEntity(TYPE, fields);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TestConfig other = (TestConfig) obj;
		if (id != other.id) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		if (parentId != other.parentId) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the TestConfig created by.
	 *
	 * @return the TestConfig created by.
	 * @since 1.0.0
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Get the string representation of the creation date (fromat is
	 * "yyyy-MM-dd").
	 *
	 * @return the date string equivalent of the creation date.
	 * @since 1.0.0
	 */
	public String getCreationDate() {
		return AlmDateFormatter.getStandardDate(creationDate);
	}

	/**
	 * Get the value(s) for the custom field with the given name.
	 *
	 * @param fieldName
	 *            The name of the field to get the values of.
	 * @return The list of values for the given field.
	 * @throws IllegalArgumentException
	 *             if no field with the given name exists in this TestConfig.
	 * @since 1.0.0
	 */
	public List<String> getCustomFieldValue(final String fieldName) {
		for (final Field field : customFields) {
			if (field.getName().equals(fieldName)) {
				return field.getValues();
			}
		}
		throw new IllegalArgumentException("no field with name |" + fieldName + "| found in this AlmTest");
	}

	/**
	 * Returns the TestConfig data state.
	 *
	 * @return the TestConfig data state.
	 * @since 1.0.0
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * Returns the description, with all HTML removed; For the full description
	 * with HTML, use {@link #getFullDescription()}.
	 *
	 * @return the description.
	 * @since 1.0.0
	 */
	public String getDescription() {
		return ConversionUtils.removeHtml(description);
	}

	/**
	 * {@inheritDoc} For TestConfigs the collection type is "test-configs"
	 */
	@Override
	public String getEntityCollectionType() {
		return COLLECTION_TYPE;
	}

	/**
	 * {@inheritDoc} For TestConfigs the type is "test-config"
	 */
	@Override
	public String getEntityType() {
		return TYPE;
	}

	/**
	 * Returns the description with including all embedded HTML.
	 *
	 * @return the description with any embedded HTML.
	 * @since 1.0.0
	 */
	public String getFullDescription() {
		return description;
	}

	/**
	 * Returns the TestConfig id.
	 *
	 * @return the id.
	 * @since 1.0.0
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Returns the TestConfig name.
	 *
	 * @return the name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the parent {@link AlmTest}.
	 *
	 * @return the currently set parent {@link AlmTest}.
	 * @since 1.0.0
	 */
	public AlmTest getParentAlmTest() {
		return parentAlmTest == null ? new AlmTest() : parentAlmTest;
	}

	/**
	 * Returns the TestConfig parent id.
	 *
	 * @return the parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Returns the TestConfig test name.
	 *
	 * @return the test name.
	 * @since 1.0.0
	 */
	public String getTestName() {
		return testName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + id;
		result = (prime * result) + name.hashCode();
		result = (prime * result) + parentId;
		return result;
	}

	/**
	 * Is this TestConfig an exact match (do all fields match)?
	 *
	 * @param other
	 *            The TestConfig to determine if this TestConfig is an exact
	 *            match of.
	 * @return True if the TestConfigs exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final TestConfig other) {
		if (!this.equals(other)) {
			return false;
		}
		if (this == other) {
			return true;
		}

		if (!createdBy.equals(other.createdBy)) {
			return false;
		}
		if (creationDate.getTimeInMillis() != other.creationDate.getTimeInMillis()) {
			return false;
		}
		if (dataState != other.dataState) {
			return false;
		}
		if (!getDescription().equals(other.getDescription())) {
			return false;
		}

		if (!testName.equals(other.testName)) {
			return false;
		}
		if (parentAlmTest == null) {
			if (other.parentAlmTest != null) {
				return false;
			}
		} else if (!parentAlmTest.isExactMatch(other.parentAlmTest)) {
			return false;
		}
		if (customFields.size() != other.customFields.size()) {
			return false;
		}
		if (!customFields.containsAll(other.customFields)) {
			return false;
		}
		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		creationDate.setTime(entity.hasFieldValue(TestConfigField.CREATION_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(TestConfigField.CREATION_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		createdBy = entity.hasFieldValue(TestConfigField.CREATED_BY.getName())
				? entity.getFieldValues(TestConfigField.CREATED_BY.getName()).get(0) : "";
		dataState = entity.hasFieldValue(TestConfigField.DATA_STATE.getName())
				? Integer.valueOf(entity.getFieldValues(TestConfigField.DATA_STATE.getName()).get(0)) : 0;
		description = entity.hasFieldValue(TestConfigField.DESCRIPTION.getName())
				? entity.getFieldValues(TestConfigField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(TestConfigField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestConfigField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(TestConfigField.NAME.getName())
				? entity.getFieldValues(TestConfigField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(TestConfigField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestConfigField.PARENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		testName = entity.hasFieldValue(TestConfigField.TEST_NAME.getName())
				? entity.getFieldValues(TestConfigField.TEST_NAME.getName()).get(0) : "";
		if (entity.hasRelatedEntities()) {
			if (parentAlmTest == null) {
				parentAlmTest = new AlmTest();
			}
			this.parentAlmTest.populateFields(entity.getRelatedEntities().get(0));
		}
		this.customFields.addAll(entity.getCustomFields());

	}

	/**
	 * Sets the createdBy.
	 *
	 * @param createdBy
	 *            the createdBy to set.
	 * @since 1.0.0
	 */
	public void setCreatedBy(final String theCreatedBy) {
		this.createdBy = theCreatedBy;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param theCreatedDate
	 *            the creation date to set.
	 * @throws IllegalArgumentException
	 *             thrown if the created date format does not confrom to
	 *             {@link TestConfig#CREATED_DATE_FORMAT}.
	 * @since 1.0.0
	 */
	public void setCreationDate(final String theCreatedDate) {
		creationDate.setTime(AlmDateFormatter.createDate(theCreatedDate));
	}

	/**
	 * Sets the data state.
	 *
	 * @param data
	 *            state the data state to set.
	 * @since 1.0.0
	 */
	public void setDataState(final int theDataState) {
		this.dataState = theDataState;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set.
	 * @since 1.0.0
	 */
	public void setDescription(final String theDescription) {
		this.description = theDescription;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set.
	 * @since 1.0.0
	 */
	public void setId(final int theId) {
		this.id = theId;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the name to set.
	 * @since 1.0.0
	 */
	public void setName(final String theName) {
		this.name = theName;
	}

	/**
	 * Sets the parent {@link AlmTest} for this test; The currently set parent
	 * test will be overwritten.
	 *
	 * @param almTest
	 *            The new parent test.
	 * @since 1.0.0
	 */
	public void setParentAlmTest(final AlmTest almTest) {
		if (parentAlmTest == null) {
			parentAlmTest = new AlmTest();
		}
		this.parentAlmTest.populateFields(almTest.createEntity());
	}

	/**
	 * Sets the parentId.
	 *
	 * @param parentId
	 *            the parentId to set.
	 * @since 1.0.0
	 */
	public void setParentId(final int theParentId) {
		this.parentId = theParentId;
	}

	/**
	 * Sets the testName.
	 *
	 * @param testName
	 *            the testName to set.
	 * @since 1.0.0
	 */
	public void setTestName(final String testName) {
		this.testName = testName;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <TestConfig> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    createdBy=|").append(createdBy).append("|,\n    creationDate=|")
				.append(getCreationDate()).append("|,\n    dataState=|").append(dataState).append("|,\n    parentId=|")
				.append(parentId).append("|,\n    testName=|").append(testName).append("|,\n    parentAlmTest=|")
				.append(parentAlmTest == null ? "Not Set" : parentAlmTest).append("|");
		return builder.toString();
	}

}
