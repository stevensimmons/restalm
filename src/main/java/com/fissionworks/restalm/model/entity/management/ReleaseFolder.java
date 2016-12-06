package com.fissionworks.restalm.model.entity.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fissionworks.restalm.constants.field.ReleaseFolderField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a release folder in ALM.
 *
 * @since 1.0.0
 *
 */
public final class ReleaseFolder implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "release-folder";

	private static final String COLLECTION_TYPE = "release-folders";

	private String description = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private ReleaseFolder parentReleaseFolder;

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(ReleaseFolderField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(ReleaseFolderField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(ReleaseFolderField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(ReleaseFolderField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
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
		final ReleaseFolder other = (ReleaseFolder) obj;
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
	 * Gets the release folder name.
	 *
	 * @return the release folder name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the release folder parent id.
	 *
	 * @return the release folder parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Gets the parent {@link ReleaseFolder}.
	 *
	 * @return the currently set parent {@link ReleaseFolder}.
	 * @since 1.0.0
	 */
	public ReleaseFolder getParentReleaseFolder() {
		return parentReleaseFolder == null ? new ReleaseFolder() : parentReleaseFolder;
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
	 * @return True if the ReleaseFolders exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final ReleaseFolder other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}
		if (parentReleaseFolder == null) {
			if (other.parentReleaseFolder != null) {
				return false;
			}
		} else if (!parentReleaseFolder.isExactMatch(other.parentReleaseFolder)) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		description = entity.hasFieldValue(ReleaseFolderField.DESCRIPTION.getName())
				? entity.getFieldValues(ReleaseFolderField.DESCRIPTION.getName()).get(0) : "";
		id = entity.hasFieldValue(ReleaseFolderField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(ReleaseFolderField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(ReleaseFolderField.NAME.getName())
				? entity.getFieldValues(ReleaseFolderField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(ReleaseFolderField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(ReleaseFolderField.PARENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		if (entity.hasRelatedEntities()) {
			if (parentReleaseFolder == null) {
				parentReleaseFolder = new ReleaseFolder();
			}
			parentReleaseFolder.populateFields(entity.getRelatedEntities().get(0));
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
	 * Sets the parent release folder for this release folder; The currently set
	 * parent release folder will be overwritten.
	 *
	 * @param releaseFolder
	 *            The new parent release folder.
	 * @since 1.0.0
	 */
	public void setParentReleaseFolder(final ReleaseFolder releaseFolder) {
		if (this.parentReleaseFolder == null) {
			parentReleaseFolder = new ReleaseFolder();
		}
		parentReleaseFolder.populateFields(releaseFolder.createEntity());
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <ReleaseFolder> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    parentId=|").append(parentId).append("|,\n    parentReleaseFolder=|")
				.append(parentReleaseFolder == null ? "Not Set" : parentReleaseFolder).append("|");
		return builder.toString();
	}

}
