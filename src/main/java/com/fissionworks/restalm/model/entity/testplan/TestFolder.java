package com.fissionworks.restalm.model.entity.testplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fissionworks.restalm.constants.field.TestFolderField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a test folder in ALM.
 *
 * @since 1.0.0
 *
 */
public final class TestFolder implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test-folder";

	private static final String COLLECTION_TYPE = "test-folders";

	private String description = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private TestFolder parentTestFolder;

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(TestFolderField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(TestFolderField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(TestFolderField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(TestFolderField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
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
		final TestFolder other = (TestFolder) obj;
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
	 * Gets the test folder name.
	 *
	 * @return the test folder name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the test folder parent id.
	 *
	 * @return the test folder parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Gets the parent {@link TestFolder}.
	 *
	 * @return the currently set parent {@link TestFolder}.
	 * @since 1.0.0
	 */
	public TestFolder getParentTestFolder() {
		return parentTestFolder == null ? new TestFolder() : parentTestFolder;
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
	 * Is this ReleaseFolder an exact match (do all fields match)?
	 *
	 * @param other
	 *            The ReleaseFolder to determine if this ReleaseFolder is an
	 *            exact match of.
	 * @return True if the AlmTestFolders exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final TestFolder other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}
		if (parentTestFolder == null) {
			if (other.parentTestFolder != null) {
				return false;
			}
		} else if (!parentTestFolder.isExactMatch(other.parentTestFolder)) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		description = entity.hasFieldValue(TestFolderField.DESCRIPTION.getName())
				? entity.getFieldValues(TestFolderField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(TestFolderField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestFolderField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(TestFolderField.NAME.getName())
				? entity.getFieldValues(TestFolderField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(TestFolderField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestFolderField.PARENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		if (entity.hasRelatedEntities()) {
			if (parentTestFolder == null) {
				parentTestFolder = new TestFolder();
			}
			parentTestFolder.populateFields(entity.getRelatedEntities().get(0));
		}
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
	 * Sets the parent test folder for this test folder; The currently set
	 * parent test folder will be overwritten.
	 *
	 * @param testFolder
	 *            The new parent test folder.
	 * @since 1.0.0
	 */
	public void setParentTestFolder(final TestFolder testFolder) {
		if (this.parentTestFolder == null) {
			parentTestFolder = new TestFolder();
		}
		parentTestFolder.populateFields(testFolder.createEntity());
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <ReleaseFolder> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    parentId=|").append(parentId).append("|,\n    parentTestFolder=|")
				.append(parentTestFolder == null ? "Not Set" : parentTestFolder).append("|");
		return builder.toString();
	}

}
