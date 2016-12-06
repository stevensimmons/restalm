package com.fissionworks.restalm.model.entity.requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.field.RequirementCoverageField;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Represents a requirement coverage object in ALM.
 *
 * @since 1.0.0
 *
 */
public final class RequirementCoverage implements AlmEntity {

	private static final String COLLECTION_TYPE = "requirement-coverages";

	private static final String TYPE = "requirement-coverage";

	private Requirement associatedRequirement;

	private String coverageEntityType = "";

	private int id = Integer.MIN_VALUE;

	private final Calendar lastModified = Calendar.getInstance();

	private int requirementId = Integer.MIN_VALUE;

	private String status = "";

	private int testId = Integer.MIN_VALUE;

	/**
	 * Default constructor; sets last modified to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public RequirementCoverage() {
		lastModified.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(
				new Field(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName(), Arrays.asList(coverageEntityType)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(RequirementCoverageField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(lastModified.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(RequirementCoverageField.LAST_MODIFIED.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDateTime(lastModified))));
		fields.add(requirementId == Integer.MIN_VALUE ? null
				: new Field(RequirementCoverageField.REQUIREMENT_ID.getName(),
						Arrays.asList(String.valueOf(requirementId))));
		fields.add(new Field(RequirementCoverageField.STATUS.getName(), Arrays.asList(status)));
		fields.add(testId == Integer.MIN_VALUE ? null
				: new Field(RequirementCoverageField.TEST_ID.getName(), Arrays.asList(String.valueOf(testId))));
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
		final RequirementCoverage other = (RequirementCoverage) obj;
		if (id != other.id) {
			return false;
		}
		if (requirementId != other.requirementId) {
			return false;
		}
		if (testId != other.testId) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the {@link Requirement} object associated with this coverage.
	 *
	 * @return The associated {@link Requirement}.
	 * @since 1.0.0
	 */
	public Requirement getAssociatedRequirement() {
		return associatedRequirement == null ? new Requirement() : associatedRequirement;
	}

	/**
	 * Gets the entity type providing this coverage.
	 *
	 * @return The type of entity providing the coverage.
	 * @since 1.0.0
	 */
	public String getCoverageEntityType() {
		return coverageEntityType;
	}

	@Override
	public String getEntityCollectionType() {
		return COLLECTION_TYPE;
	}

	@Override
	public String getEntityType() {
		return TYPE;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Get the date this object was last modified in ALM.
	 *
	 * @return The last modified date/time.
	 * @since 1.0.0
	 */
	public String getLastModified() {
		return AlmDateFormatter.getStandardDateTime(lastModified);
	}

	/**
	 * Get the ID of the associated requirement.
	 *
	 * @return The ID of the associated requirement.
	 * @since 1.0.0
	 */
	public int getRequirementId() {
		return requirementId;
	}

	/**
	 * Gets the current status of this coverage.
	 *
	 * @return The current status.
	 * @since 1.0.0
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the ID of the associated Test.
	 *
	 * @return The ID of the associated Test.
	 * @since 1.0.0
	 */
	public int getTestId() {
		return testId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + id;
		result = (prime * result) + requirementId;
		result = (prime * result) + testId;
		return result;
	}

	/**
	 * Is this Requirement Coverage an exact match (do all fields match)?
	 *
	 * @param other
	 *            The Requirement Coverage to determine if this Requirement
	 *            Coverage is an exact match of.
	 * @return True if the Requirement Coverage's exactly match, false
	 *         otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final RequirementCoverage other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (associatedRequirement == null) {
			if (other.associatedRequirement != null) {
				return false;
			}
		} else if (!associatedRequirement.isExactMatch(other.associatedRequirement)) {
			return false;
		}

		if (!coverageEntityType.equals(other.coverageEntityType)) {
			return false;
		}

		if (lastModified.getTimeInMillis() != other.lastModified.getTimeInMillis()) {
			return false;
		}

		if (!status.equals(other.status)) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		coverageEntityType = entity.hasFieldValue(RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName())
				? entity.getFieldValues((RequirementCoverageField.COVERAGE_ENTITY_TYPE.getName())).get(0) : "";
		id = entity.hasFieldValue(RequirementCoverageField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementCoverageField.ID.getName()).get(0))
				: Integer.MIN_VALUE;
		lastModified.setTime(entity.hasFieldValue(RequirementCoverageField.LAST_MODIFIED.getName())
				? AlmDateFormatter
						.createDateTime(entity.getFieldValues(RequirementCoverageField.LAST_MODIFIED.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		requirementId = entity.hasFieldValue(RequirementCoverageField.REQUIREMENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementCoverageField.REQUIREMENT_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		status = entity.hasFieldValue(RequirementCoverageField.STATUS.getName())
				? entity.getFieldValues((RequirementCoverageField.STATUS.getName())).get(0) : "";
		testId = entity.hasFieldValue(RequirementCoverageField.TEST_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RequirementCoverageField.TEST_ID.getName()).get(0))
				: Integer.MIN_VALUE;

		if (entity.hasRelatedEntities()) {
			if (associatedRequirement == null) {
				associatedRequirement = new Requirement();
			}
			associatedRequirement.populateFields(entity.getRelatedEntities().get(0));

		}

	}

	/**
	 * Set the {@link Requirement} associated with this coverage.
	 *
	 * @param associatedRequirement
	 *            The {@link Requirement} associated with this coverage.
	 * @since 1.0.0
	 */
	public void setAssociatedRequirement(final Requirement associatedRequirement) {
		this.associatedRequirement = associatedRequirement;
	}

	/**
	 * Set the type of entity associated with this coverage.
	 *
	 * @param entityType
	 *            The type of entity associated with this coverage.
	 * @since 1.0.0
	 */
	public void setCoverageEntityType(final String entityType) {
		this.coverageEntityType = entityType;
	}

	/**
	 * Set the ID of this Requirement Coverage.
	 *
	 * @param id
	 *            The new ID to be set.
	 * @since 1.0.0
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Set the date/time this coverage was last modified; Requires a format of
	 * "yyyy-MM-dd HH:mm:ss"
	 *
	 * @param lastModified
	 *            The date/time last formatted in the format "yyyy-MM-dd
	 *            HH:mm:ss"
	 * @since 1.0.0
	 */
	public void setLastModified(final String lastModified) {
		this.lastModified.setTime(AlmDateFormatter.createDateTime(lastModified));
	}

	/**
	 * Sets the ID of the associated {@link Requirement}.
	 *
	 * @param requirementId
	 *            The new ID for the associated {@link Requirement}.
	 * @since 1.0.0
	 */
	public void setRequirementId(final int requirementId) {
		this.requirementId = requirementId;
	}

	/**
	 * Sets the current status of this coverage.
	 *
	 * @param status
	 *            The new status of the coverage.
	 * @since 1.0.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Sets the ID of the associated Test.
	 *
	 * @param testId
	 *            The new ID for the associated test.
	 * @since 1.0.0
	 */
	public void setTestId(final int testId) {
		this.testId = testId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <RequirementCoverage> {\n    id=|").append(id)
				.append("|,\n    coverageEntityType=|").append(coverageEntityType).append("|,\n    requirementId=|")
				.append(requirementId).append("|,\n    testId=|").append(testId).append("|,\n    status=|")
				.append(status).append("|,\n    lastModified=|").append(getLastModified())
				.append("|,\n    associatedRequirement=|")
				.append(associatedRequirement == null ? "Not Set" : "\n" + associatedRequirement).append("|");
		return builder.toString();
	}

}
