package com.fissionworks.restalm.model.entity.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.constants.field.ReleaseField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a Release Cycle in ALM.
 *
 * @since 1.0.0
 *
 */
public final class ReleaseCycle implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "release-cycle";

	private static final String COLLECTION_TYPE = "release-cycles";

	private String description = "";

	private final Calendar endDate = Calendar.getInstance();

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private Release parentRelease;

	private final Calendar startDate = Calendar.getInstance();

	/**
	 * Default constructor; sets start and end date times to
	 * {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public ReleaseCycle() {
		startDate.setTimeInMillis(Long.MIN_VALUE);
		endDate.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(ReleaseField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(endDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(ReleaseField.END_DATE.getName(), Arrays.asList(AlmDateFormatter.getStandardDate(endDate))));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(ReleaseField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(ReleaseField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(ReleaseField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
		fields.add(startDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(ReleaseField.START_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(startDate))));
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
		final ReleaseCycle other = (ReleaseCycle) obj;
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

	/**
	 * Gets the scheduled end date for the release cycle
	 *
	 * @return The scheduled end date in the form "yyyy-MM-dd"
	 * @since 1.0.0
	 */
	public String getEndDate() {
		return AlmDateFormatter.getStandardDate(endDate);
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
	 * Get the Release Cycle name.
	 *
	 * @return the Release Cycle name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Release Cycle parent id.
	 *
	 * @return the Release Cycle parent id.
	 * @since 1.0.0
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Gets the parent {@link Release}.
	 *
	 * @return the currently set parent {@link Release}.
	 * @since 1.0.0
	 */
	public Release getParentRelease() {
		return parentRelease == null ? new Release() : parentRelease;
	}

	/**
	 * Gets the scheduled start date for the release cycle
	 *
	 * @return The scheduled start date in the form "yyyy-MM-dd"
	 * @since 1.0.0
	 */
	public String getStartDate() {
		return AlmDateFormatter.getStandardDate(startDate);
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
	 * Is this Release Cycle an exact match (do all fields match)?
	 *
	 * @param other
	 *            The Release Cycle to determine if this Release Cycle is an
	 *            exact match of.
	 * @return True if the Release Cycles exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final ReleaseCycle other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!getDescription().equals(other.getDescription())) {
			return false;
		}
		if (endDate.getTimeInMillis() != other.endDate.getTimeInMillis()) {
			return false;
		}
		if (startDate.getTimeInMillis() != other.startDate.getTimeInMillis()) {
			return false;
		}

		if (parentRelease == null) {
			if (other.parentRelease != null) {
				return false;
			}
		} else if (!parentRelease.isExactMatch(other.parentRelease)) {
			return false;
		}
		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		description = entity.hasFieldValue(AlmTestField.DESCRIPTION.getName())
				? entity.getFieldValues(AlmTestField.DESCRIPTION.getName()).get(0) : "";
		endDate.setTime(entity.hasFieldValue(ReleaseField.END_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(ReleaseField.END_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		id = entity.hasFieldValue(ReleaseField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(ReleaseField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(ReleaseField.NAME.getName())
				? entity.getFieldValues(ReleaseField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(ReleaseField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(ReleaseField.PARENT_ID.getName()).get(0)) : Integer.MIN_VALUE;
		startDate.setTime(entity.hasFieldValue(ReleaseField.START_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(ReleaseField.START_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		if (entity.hasRelatedEntities()) {
			if (parentRelease == null) {
				parentRelease = new Release();
			}
			this.parentRelease.populateFields(entity.getRelatedEntities().get(0));
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
	 * Sets the end date.
	 *
	 * @param theEndDate
	 *            the end date to set.
	 * @throws IllegalArgumentException
	 *             thrown if the end date format does not confrom to
	 *             {@link ReleaseCycle#RELEASE_CYCLE_DATE_FORMAT}.
	 * @since 1.0.0
	 */
	public void setEndDate(final String theEndDate) {
		endDate.setTime(AlmDateFormatter.createDate(theEndDate));
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
	 * Sets the parent release for this release cycle; The currently set parent
	 * release will be overwritten.
	 *
	 * @param newParentRelease
	 *            The new parent release.
	 * @since 1.0.0
	 */
	public void setParentRelease(final Release newParentRelease) {
		if (parentRelease == null) {
			parentRelease = new Release();
		}
		this.parentRelease.populateFields(newParentRelease.createEntity());
	}

	/**
	 * Sets the start date.
	 *
	 * @param theStartDate
	 *            the start date to set.
	 * @throws IllegalArgumentException
	 *             thrown if the end date format does not confrom to
	 *             {@link ReleaseCycle#RELEASE_CYCLE_DATE_FORMAT}.
	 * @since 1.0.0
	 */
	public void setStartDate(final String theStartDate) {
		startDate.setTime(AlmDateFormatter.createDate(theStartDate));
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <ReleaseCycle> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    parentId=|").append(parentId).append("|,\n    startDate=|").append(getStartDate())
				.append("|,\n    endDate=|").append(getEndDate()).append("|,\n    parentReleaseFolder=|")
				.append(parentRelease == null ? "Not Set" : parentRelease).append("|");
		return builder.toString();
	}

}
