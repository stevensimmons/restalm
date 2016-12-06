package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fissionworks.restalm.constants.field.TestSetFolderField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a test set folder in ALM.
 *
 * @since 1.0.0
 *
 */
public final class TestSetFolder implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test-set-folder";

	private static final String COLLECTION_TYPE = "test-set-folders";

	private String description = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private TestSetFolder parentTestSetFolder;

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestSetFolderField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(TestSetFolderField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(TestSetFolderField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(TestSetFolderField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
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
		final TestSetFolder other = (TestSetFolder) obj;
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
	 * Gets the test set folder name.
	 *
	 * @return the test set folder name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the test set folder parent id.
	 *
	 * @return the test set folder parent id.
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
	 * Is this TestSetFolder an exact match (do all fields match)?
	 *
	 * @param other
	 *            The TestSetFolder to determine if this TestSetFolder is an
	 *            exact match of.
	 * @return True if the TestSetFolders exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final TestSetFolder other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
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
		description = entity.hasFieldValue(TestSetFolderField.DESCRIPTION.getName())
				? entity.getFieldValues(TestSetFolderField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(TestSetFolderField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestSetFolderField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(TestSetFolderField.NAME.getName())
				? entity.getFieldValues(TestSetFolderField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(TestSetFolderField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestSetFolderField.PARENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
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
	 * Sets the parent test set folder for this test set folder; The currently
	 * set parent test set folder will be overwritten.
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <TestSetFolder> {\n    description=|")
				.append(getDescription()).append("|,\n    id=|").append(id).append("|,\n    name=|").append(name)
				.append("|,\n    parentId=|").append(parentId).append("|,\n    parentTestSetFolder=|")
				.append(parentTestSetFolder == null ? "Not Set" : parentTestSetFolder).append("|");
		return builder.toString();
	}

}
