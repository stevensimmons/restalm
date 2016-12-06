package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fissionworks.restalm.constants.field.TestSetField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a test set in ALM.
 *
 * @since 1.0.0
 *
 */
public final class TestSet implements AlmEntity {
	/**
	 * The default subtype in ALM ("hp.qc.test-set.default")
	 */
	public static final String DEFAULT_SUBTYPE = "hp.qc.test-set.default";

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test-set";

	private static final String COLLECTION_TYPE = "test-sets";

	private String description = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private TestSetFolder parentTestSetFolder;

	private String subtype = DEFAULT_SUBTYPE;

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestSetField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(TestSetField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(TestSetField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(TestSetField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
		fields.add(new Field(TestSetField.TYPE.getName(), Arrays.asList(subtype)));
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
		final TestSet other = (TestSet) obj;
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
	 * Returns the description with including all embedded HTML.
	 *
	 * @return the description with any embedded HTML.
	 * @since 1.0.0
	 */
	public String getFullDescription() {
		return description;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Gets the test set name.
	 *
	 * @return the test set name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the test set parent id.
	 *
	 * @return the test set parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Gets the parent {@link TestSetFolder}.
	 *
	 * @return the currently set parent {@link TestSetFolder}.
	 * @since 1.0.0
	 */
	public TestSetFolder getParentTestSetFolder() {
		return parentTestSetFolder == null ? new TestSetFolder() : parentTestSetFolder;
	}

	/**
	 * Get the Test Set sub-type.
	 *
	 * @return the Test Set sub-type.
	 * @since 1.0.0
	 */
	public String getSubtype() {
		return subtype;
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
	 * Is this TestSet an exact match (do all fields match)?
	 *
	 * @param other
	 *            The TestSet to determine if this TestSet is an exact match of.
	 * @return True if the TestSets exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final TestSet other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}

		if (!subtype.equals(other.subtype)) {
			return false;
		}
		if (parentTestSetFolder == null) {
			if (other.parentTestSetFolder != null) {
				return false;
			}
		} else if (!parentTestSetFolder.isExactMatch(other.parentTestSetFolder)) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		description = entity.hasFieldValue(TestSetField.DESCRIPTION.getName())
				? entity.getFieldValues(TestSetField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(TestSetField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestSetField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(TestSetField.NAME.getName())
				? entity.getFieldValues(TestSetField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(TestSetField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestSetField.PARENT_ID.getName()).get(0)) : Integer.MIN_VALUE;
		subtype = entity.hasFieldValue(TestSetField.TYPE.getName())
				? entity.getFieldValues(TestSetField.TYPE.getName()).get(0) : DEFAULT_SUBTYPE;
		if (entity.hasRelatedEntities()) {
			if (parentTestSetFolder == null) {
				parentTestSetFolder = new TestSetFolder();
			}
			parentTestSetFolder.populateFields(entity.getRelatedEntities().get(0));
		}

	}

	/**
	 * Sets the description.
	 *
	 * @param theDescription
	 *            the description to set.
	 * @since 1.0.0
	 */
	public void setDescription(final String theDescription) {
		this.description = theDescription;
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
	 * @param theName
	 *            the name to set.
	 * @since 1.0.0
	 */
	public void setName(final String theName) {
		this.name = theName;
	}

	/**
	 * Sets the parent id.
	 *
	 * @param theParentId
	 *            the parent id to set.
	 * @since 1.0.0
	 */
	public void setParentId(final int theParentId) {
		this.parentId = theParentId;
	}

	/**
	 * Sets the parent test set folder for this test set; The currently set
	 * parent test set folder will be overwritten.
	 *
	 * @param theParentTestSetFolder
	 *            The new parent test folder.
	 * @since 1.0.0
	 */
	public void setParentTestSetFolder(final TestSetFolder theParentTestSetFolder) {
		if (this.parentTestSetFolder == null) {
			parentTestSetFolder = new TestSetFolder();
		}
		parentTestSetFolder.populateFields(theParentTestSetFolder.createEntity());
	}

	/**
	 * Sets the sub-type.
	 *
	 * @param theSubtype
	 *            the sub-type to set.
	 * @since 1.0.0
	 *
	 */
	public void setSubtype(final String theSubtype) {
		this.subtype = theSubtype;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <TestSet> {\n    description=|").append(getDescription())
				.append("|,\n    id=|").append(id).append("|,\n    name=|").append(name).append("|,\n    parentId=|")
				.append(parentId).append("|,\n    subtype=|").append(subtype).append("|,\n    parentTestSetFolder=|")
				.append(parentTestSetFolder == null ? "Not Set" : parentTestSetFolder).append("|");
		return builder.toString();
	}

}
