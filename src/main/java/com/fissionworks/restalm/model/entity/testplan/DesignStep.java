package com.fissionworks.restalm.model.entity.testplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.DesignStepField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a design step in ALM.
 *
 * @since 1.0.0
 *
 */
public final class DesignStep implements AlmEntity {
	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "design-step";

	private static final String COLLECTION_TYPE = "design-steps";

	private final List<Field> customFields = new ArrayList<>();

	private String description = "";

	private String expectedResult = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private AlmTest parentAlmTest;

	private int parentId = Integer.MIN_VALUE;

	private int stepOrder = Integer.MIN_VALUE;

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(DesignStepField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(new Field(DesignStepField.EXPECTED_RESULT.getName(), Arrays.asList(expectedResult)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(DesignStepField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(DesignStepField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(DesignStepField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
		fields.add(stepOrder == Integer.MIN_VALUE ? null
				: new Field(DesignStepField.STEP_ORDER.getName(), Arrays.asList(String.valueOf(stepOrder))));
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
		final DesignStep other = (DesignStep) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * Get the value(s) for the custom field with the given name.
	 *
	 * @param fieldName
	 *            The name of the field to get the values of.
	 * @return The list of values for the given field.
	 * @throws IllegalArgumentException
	 *             if no field with the given name exists in this AlmTest.
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
	 * Returns the description, with all HTML removed; For the full description
	 * with HTML, use {@link #getFullDescription()}.
	 *
	 * @return the description.
	 * @since 1.0.0
	 */
	public String getDescription() {
		return ConversionUtils.removeHtml(description);
	}

	@Override
	public String getEntityCollectionType() {
		return COLLECTION_TYPE;
	}

	@Override
	public String getEntityType() {
		return TYPE;
	}

	/**
	 * Returns the expected result, with all HTML removed; For the full expected
	 * result with HTML, use {@link #getFullExpectedResult()}.
	 *
	 * @return the description.
	 * @since 1.0.0
	 */
	public String getExpectedResult() {
		return ConversionUtils.removeHtml(expectedResult);
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
	 * Returns the expected result with including all embedded HTML.
	 *
	 * @return the expected result with any embedded HTML.
	 * @since 1.0.0
	 */
	public String getFullExpectedResult() {
		return expectedResult;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Get the DesignStep name.
	 *
	 * @return the DesignStep name.
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
	 * Get the DesignStep parent id.
	 *
	 * @return the DesignStep parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Get the DesignStep step order.
	 *
	 * @return the DesignStep step order.
	 * @since 1.0.0
	 */
	public int getStepOrder() {
		return stepOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + id;
		return result;
	}

	/**
	 * Is this DesignStep an exact match (do all fields match)?
	 *
	 * @param other
	 *            The DesignStep to determine if this DesignStep is an exact
	 *            match of.
	 * @return True if the DesignSteps exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final DesignStep other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}

		if (!getExpectedResult().equals(other.getExpectedResult())) {
			return false;
		}

		if (!name.equals(other.name)) {
			return false;
		}

		if (parentId != other.parentId) {
			return false;
		}

		if (stepOrder != other.stepOrder) {
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
		description = entity.hasFieldValue(DesignStepField.DESCRIPTION.getName())
				? entity.getFieldValues(DesignStepField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(DesignStepField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(DesignStepField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(DesignStepField.NAME.getName())
				? entity.getFieldValues(DesignStepField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(AlmTestField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(AlmTestField.PARENT_ID.getName()).get(0)) : Integer.MIN_VALUE;
		expectedResult = entity.hasFieldValue(DesignStepField.EXPECTED_RESULT.getName())
				? entity.getFieldValues(DesignStepField.EXPECTED_RESULT.getName()).get(0) : "";
		stepOrder = entity.hasFieldValue(DesignStepField.STEP_ORDER.getName())
				? Integer.valueOf(entity.getFieldValues(DesignStepField.STEP_ORDER.getName()).get(0))
				: Integer.MIN_VALUE;

		if (entity.hasRelatedEntities()) {
			if (parentAlmTest == null) {
				parentAlmTest = new AlmTest();
			}
			this.parentAlmTest.populateFields(entity.getRelatedEntities().get(0));
		}
		customFields.clear();
		customFields.addAll(entity.getCustomFields());

	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set.
	 * @since 1.0.0
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Sets the expected result.
	 *
	 * @param expectedResult
	 *            the expectedResult to set.
	 * @since 1.0.0
	 */
	public void setExpectedResult(final String expectedResult) {
		this.expectedResult = expectedResult;
	}

	/**
	 * Sets the id.
	 *
	 * @param theId
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
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the parent test for this step; The currently set parent test will be
	 * overwritten.
	 *
	 * @param parentAlmTest
	 *            The new parent test.
	 * @since 1.0.0
	 */
	public void setParentAlmTest(final AlmTest parentAlmTest) {
		this.parentAlmTest = parentAlmTest;
	}

	/**
	 * Sets the parentId.
	 *
	 * @param theParentId
	 *            the parentId to set.
	 * @since 1.0.0
	 */
	public void setParentId(final int theParentId) {
		this.parentId = theParentId;
	}

	/**
	 * Sets the step order.
	 *
	 * @param stepOrder
	 *            the step order to set.
	 * @since 1.0.0
	 */
	public void setStepOrder(final int stepOrder) {
		this.stepOrder = stepOrder;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <DesignStep> {\n    id=|").append(id)
				.append("|,\n    description=|").append(description).append("|,\n    expectedResult=|")
				.append(expectedResult).append("|,\n    name=|").append(name).append("|,\n    parentId=|")
				.append(parentId).append("|,\n    stepOrder=|").append(stepOrder).append("|,\n    customFields=|")
				.append(customFields).append("|,\n    parentAlmTest=|")
				.append(parentAlmTest == null ? "Not Set" : parentAlmTest).append("|");
		return builder.toString();
	}

}
