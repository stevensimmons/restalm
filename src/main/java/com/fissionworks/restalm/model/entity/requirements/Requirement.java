package com.fissionworks.restalm.model.entity.requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.field.RequirementField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a Requirement in ALM.
 *
 * @since 1.0.0
 */
public final class Requirement implements AlmEntity {

	private static final String COLLECTION_TYPE = "requirements";

	private static final String TYPE = "requirement";

	private String author = "";

	private String comments = "";

	private final Calendar creationDate = Calendar.getInstance();

	private String description = "";

	private String directCoverStatus = "";

	private String fatherName = "";

	private int id = Integer.MIN_VALUE;

	private final Calendar lastModified = Calendar.getInstance();

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private Requirement parentRequirement;

	private int typeId = Integer.MIN_VALUE;

	/**
	 * Default constructor; sets last modified to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public Requirement() {
		lastModified.setTimeInMillis(Long.MIN_VALUE);
		creationDate.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(RequirementField.AUTHOR.getName(), Arrays.asList(author)));
		fields.add(new Field(RequirementField.COMMENTS.getName(), Arrays.asList(comments)));
		fields.add(creationDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(RequirementField.CREATION_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(creationDate))));
		fields.add(new Field(RequirementField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(new Field(RequirementField.DIRECT_COVER_STATUS.getName(), Arrays.asList(directCoverStatus)));
		fields.add(new Field(RequirementField.FATHER_NAME.getName(), Arrays.asList(fatherName)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(RequirementField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(lastModified.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(RequirementField.LAST_MODIFIED.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDateTime(lastModified))));
		fields.add(new Field(RequirementField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(RequirementField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
		fields.add(typeId == Integer.MIN_VALUE ? null
				: new Field(RequirementField.TYPE_ID.getName(), Arrays.asList(String.valueOf(typeId))));
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
		final Requirement other = (Requirement) obj;
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
	 * Gets the author of this requirement.
	 *
	 * @return the author.
	 * @since 1.0.0
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Returns the comments, with all HTML removed; For the full comments with
	 * HTML, use {@link #getFullComments()}.
	 *
	 * @return the comments.
	 * @since 1.0.0
	 */
	public String getComments() {
		return ConversionUtils.removeHtml(comments);
	}

	/**
	 * Gets the requirement creation date in the format "yyyy-MM-dd".
	 *
	 * @return the creation date.
	 * @since 1.0.0
	 */
	public String getCreationDate() {
		return AlmDateFormatter.getStandardDate(creationDate);
	}

	/**
	 * Returns the description, with all HTML removed; For the full description
	 * with HTML, use {@link #getFullDescription()}.
	 *
	 * @return the decription.
	 * @since 1.0.0
	 */
	public String getDescription() {
		return ConversionUtils.removeHtml(description);
	}

	/**
	 * Gets the current direct cover status.
	 *
	 * @return the direct cover status.
	 * @since 1.0.0
	 */
	public String getDirectCoverStatus() {
		return directCoverStatus;
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
	 * Gets the name of the parent requirement.
	 *
	 * @return The parent requirements name.
	 * @since 1.0.0
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * Returns the comments with including all embedded HTML.
	 *
	 * @return the comments with any embedded HTML.
	 * @since 1.0.0
	 */
	public String getFullComments() {
		return comments;
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
	 * Gets the last modified time for the Requirement.
	 *
	 * @return The last modified time in the form "yyyy-MM-dd hh:mm:ss"
	 * @since 1.0.0
	 */
	public String getLastModified() {
		return AlmDateFormatter.getStandardDateTime(lastModified);
	}

	/**
	 * Get the Requirement name.
	 *
	 * @return the Requirement name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the id of this Requirements parent Requirement.
	 *
	 * @return the parent Requirement id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Get a Requirement object representing this Requirements parent.
	 *
	 * @return The parent Requirement.
	 * @since 1.0.0
	 */
	public Requirement getParentRequirement() {
		return parentRequirement == null ? new Requirement() : parentRequirement;
	}

	/**
	 * Gets the type id of this requirement.
	 *
	 * @return The type id.
	 * @since 1.0.0
	 */
	public int getTypeId() {
		return typeId;
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
	 * Is this Requirement an exact match (do all fields match)?
	 *
	 * @param other
	 *            The Requirement to determine if this Requirement is an exact
	 *            match of.
	 * @return True if the Requirements exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final Requirement other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (parentRequirement == null) {
			if (other.parentRequirement != null) {
				return false;
			}
		} else if (!parentRequirement.isExactMatch(other.parentRequirement)) {
			return false;
		}

		if (!author.equals(other.author)) {
			return false;
		}

		if (!getComments().equals(other.getComments())) {
			return false;
		}

		if (creationDate.getTimeInMillis() != other.creationDate.getTimeInMillis()) {
			return false;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}

		if (!directCoverStatus.equals(other.directCoverStatus)) {
			return false;
		}

		if (!fatherName.equals(other.fatherName)) {
			return false;
		}
		if (lastModified.getTimeInMillis() != other.lastModified.getTimeInMillis()) {
			return false;
		}

		if (typeId != other.typeId) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		author = entity.hasFieldValue(RequirementField.AUTHOR.getName())
				? entity.getFieldValues((RequirementField.AUTHOR.getName())).get(0) : "";
		comments = entity.hasFieldValue(RequirementField.COMMENTS.getName())
				? entity.getFieldValues((RequirementField.COMMENTS.getName())).get(0) : "";
		creationDate.setTime(entity.hasFieldValue(RequirementField.CREATION_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(RequirementField.CREATION_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		description = entity.hasFieldValue(RequirementField.DESCRIPTION.getName())
				? entity.getFieldValues((RequirementField.DESCRIPTION.getName())).get(0) : "";
		directCoverStatus = entity.hasFieldValue(RequirementField.DIRECT_COVER_STATUS.getName())
				? entity.getFieldValues((RequirementField.DIRECT_COVER_STATUS.getName())).get(0) : "";
		fatherName = entity.hasFieldValue(RequirementField.FATHER_NAME.getName())
				? entity.getFieldValues((RequirementField.FATHER_NAME.getName())).get(0) : "";
		id = entity.hasFieldValue(RequirementField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		lastModified.setTime(entity.hasFieldValue(RequirementField.LAST_MODIFIED.getName())
				? AlmDateFormatter
						.createDateTime(entity.getFieldValues(RequirementField.LAST_MODIFIED.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		name = entity.hasFieldValue(RequirementField.NAME.getName())
				? entity.getFieldValues((RequirementField.NAME.getName())).get(0) : "";
		parentId = entity.hasFieldValue(RequirementField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementField.PARENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		typeId = entity.hasFieldValue(RequirementField.TYPE_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementField.TYPE_ID.getName()).get(0)) : Integer.MIN_VALUE;
		if (entity.hasRelatedEntities()) {
			if (parentRequirement == null) {
				parentRequirement = new Requirement();
			}
			parentRequirement.populateFields(entity.getRelatedEntities().get(0));

		}

	}

	/**
	 * Sets the author of this Requirement.
	 *
	 * @param author
	 *            The new author.
	 * @since 1.0.0
	 */
	public void setAuthor(final String author) {
		this.author = author;
	}

	/**
	 * add comments to the Requirement.
	 *
	 * @param comments
	 *            The comments to add.
	 * @since 1.0.0
	 */
	public void setComments(final String comments) {
		this.comments = comments;
	}

	/**
	 * Changes the creation date.
	 *
	 * @param creationDate
	 *            The new creation date.
	 * @since 1.0.0
	 */
	public void setCreationDate(final String creationDate) {
		this.creationDate.setTime(AlmDateFormatter.createDate(creationDate));
	}

	/**
	 * Changes the description.
	 *
	 * @param description
	 *            The new description.
	 * @since 1.0.0
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Change the direct cover status.
	 *
	 * @param directCoverStatus
	 *            The new direct cover status.
	 * @since 1.0.0
	 */
	public void setDirectCoverStatus(final String directCoverStatus) {
		this.directCoverStatus = directCoverStatus;
	}

	/**
	 * Change the father name.
	 *
	 * @param fatherName
	 *            The new father name.
	 * @since 1.0.0
	 */
	public void setFatherName(final String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * Set the id of this Requirement.
	 *
	 * @param id
	 *            the new id.
	 * @since 1.0.0
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets the last modified date-time.
	 *
	 * @param lastModified
	 *            The new last modified value.
	 * @since 1.0.0
	 */
	public void setLastModified(final String lastModified) {
		this.lastModified.setTime(AlmDateFormatter.createDateTime(lastModified));
	}

	/**
	 * Sets the name of the Requirement.
	 *
	 * @param name
	 *            The name to set.
	 * @since 1.0.0
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the parent id for this Requirement.
	 *
	 * @param parentId
	 *            The parent id to set.
	 * @since 1.0.0
	 */
	public void setParentId(final int parentId) {
		this.parentId = parentId;
	}

	/**
	 * Sets the parent requirement for this requirement; The currently set
	 * parent requirement will be overwritten.
	 *
	 * @param theParentRequirement
	 *            The new parent requirement.
	 * @since 1.0.0
	 */
	public void setParentRequirement(final Requirement theParentRequirement) {
		if (parentRequirement == null) {
			parentRequirement = new Requirement();
		}
		this.parentRequirement.populateFields(theParentRequirement.createEntity());
	}

	/**
	 * Sets the type id of the requirement.
	 *
	 * @param theTypeId
	 *            The new Type Id.
	 * @since 1.0.0
	 */
	public void setTypeId(final int theTypeId) {
		this.typeId = theTypeId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Requirement> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    comments=|").append(getComments()).append("|,\n    type=|").append(typeId)
				.append("|,\n    author=|").append(author).append("|,\n    creationDate=|").append(getCreationDate())
				.append("|,\n    directCoverStatus=|").append(directCoverStatus).append("|,\n    fatherName=|")
				.append(fatherName).append("|,\n    lastModified=|").append(getLastModified())
				.append("|,\n    parentId=|").append(parentId).append("|,\n    parentRequirement=|")
				.append(parentRequirement == null ? "Not Set" : "\n" + parentRequirement).append("|");
		return builder.toString();
	}

}
