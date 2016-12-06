package com.fissionworks.restalm.model.entity.defects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.entity.EntityType;
import com.fissionworks.restalm.constants.field.DefectField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.management.Release;
import com.fissionworks.restalm.model.entity.management.ReleaseCycle;

/**
 * Class that represents a Defect in ALM.
 *
 * @since 1.0.0
 *
 */
public final class Defect implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "defect";

	private static final String COLLECTION_TYPE = "defects";

	private String assignedTo = "";

	private final Calendar closingDate = Calendar.getInstance();

	private String comments = "";

	private String description = "";

	private String detectedBy = "";

	private Release detectedInRelease;

	private ReleaseCycle detectedInReleaseCycle;

	private int detectedInReleaseCycleId = Integer.MIN_VALUE;

	private int detectedInReleaseId = Integer.MIN_VALUE;

	private final Calendar detectedOnDate = Calendar.getInstance();

	private int id = Integer.MIN_VALUE;

	private final Calendar modified = Calendar.getInstance();

	private String severity = "";

	private String status = "";

	private String summary = "";

	/**
	 * Default constructor; sets closing date, detected on date, and modified
	 * date to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public Defect() {
		closingDate.setTimeInMillis(Long.MIN_VALUE);
		detectedOnDate.setTimeInMillis(Long.MIN_VALUE);
		modified.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(DefectField.ASSIGNED_TO.getName(), Arrays.asList(assignedTo)));
		fields.add(closingDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(DefectField.CLOSING_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(closingDate))));
		fields.add(new Field(DefectField.COMMENTS.getName(), Arrays.asList(comments)));
		fields.add(new Field(DefectField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(new Field(DefectField.DETECTED_BY.getName(), Arrays.asList(detectedBy)));
		fields.add(detectedInReleaseCycleId == Integer.MIN_VALUE ? null
				: new Field(DefectField.DETECTED_IN_CYCLE_ID.getName(),
						Arrays.asList(String.valueOf(detectedInReleaseCycleId))));
		fields.add(detectedInReleaseId == Integer.MIN_VALUE ? null
				: new Field(DefectField.DETECTED_IN_RELEASE_ID.getName(),
						Arrays.asList(String.valueOf(detectedInReleaseId))));
		fields.add(detectedOnDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(DefectField.DETECTED_ON_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(detectedOnDate))));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(DefectField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(modified.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(DefectField.MODIFIED.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDateTime(modified))));
		fields.add(new Field(DefectField.SEVERITY.getName(), Arrays.asList(severity)));
		fields.add(new Field(DefectField.STATUS.getName(), Arrays.asList(status)));
		fields.add(new Field(DefectField.SUMMARY.getName(), Arrays.asList(summary)));
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
		final Defect other = (Defect) obj;
		if (!getDescription().equals(other.getDescription())) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!summary.equals(other.summary)) {
			return false;
		}
		return true;
	}

	/**
	 * Get the user this Defect is assigned to.
	 *
	 * @return The username of the Defect assignee.
	 * @since 1.0.0
	 */
	public String getAssignedTo() {
		return assignedTo;
	}

	/**
	 * The Date this defect was closed.
	 *
	 * @return The closing date of this Defect.
	 * @since 1.0.0
	 */
	public String getClosingDate() {
		return AlmDateFormatter.getStandardDate(closingDate);
	}

	/**
	 * Returns the comments, with all HTML removed; For the full comments with
	 * HTML, use {@link #getFullComments()}.
	 *
	 * @return the description.
	 * @since 1.0.0
	 */
	public String getComments() {
		return ConversionUtils.removeHtml(comments);
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
	 * Get the username of the person that detected this Defect.
	 *
	 * @return the username of the person that detected this defect.
	 * @since 1.0.0
	 */
	public String getDetectedBy() {
		return detectedBy;
	}

	/**
	 * Gets the details about the {@link Release} this Defect was detected in.
	 *
	 * @return The Details of the Detected in {@link Release}.
	 * @since 1.0.0
	 */
	public Release getDetectedInRelease() {
		return detectedInRelease == null ? new Release() : detectedInRelease;
	}

	/**
	 * Gets the details about the {@link ReleaseCycle} this Defect was detected
	 * in.
	 *
	 * @return The Details of the Detected in {@link ReleaseCycle}.
	 * @since 1.0.0
	 */
	public ReleaseCycle getDetectedInReleaseCycle() {
		return detectedInReleaseCycle == null ? new ReleaseCycle() : detectedInReleaseCycle;
	}

	/**
	 * Gets the Id of the {@link ReleaseCycle} this Defect was detected in.
	 *
	 * @return The id of the Detected in {@link ReleaseCycle}.
	 * @since 1.0.0
	 */
	public int getDetectedInReleaseCycleId() {
		return detectedInReleaseCycleId;
	}

	/**
	 * Gets the Id of the {@link Release} this Defect was detected in.
	 *
	 * @return The id of the Detected in {@link Release}.
	 * @since 1.0.0
	 */
	public int getDetectedInReleaseId() {
		return detectedInReleaseId;
	}

	/**
	 * Gets the date this Defect was detected on.
	 *
	 * @return The detected on date in the format yyyy-MM-dd.
	 * @since 1.0.0
	 */
	public String getDetectedOnDate() {
		return AlmDateFormatter.getStandardDate(detectedOnDate);
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
	 * Gets the last date this Defect was modified on.
	 *
	 * @return The modified date in the format yyyy-MM-dd.
	 * @since 1.0.0
	 */
	public String getModified() {
		return AlmDateFormatter.getStandardDateTime(modified);
	}

	/**
	 * Gets the severity of this Defect.
	 *
	 * @return The severity.
	 * @since 1.0.0
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * Gets the status of this Defect.
	 *
	 * @return The Defect status.
	 * @since 1.0.0
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets the summary of this Defect.
	 *
	 * @return The Defect summary.
	 * @since 1.0.0
	 */
	public String getSummary() {
		return summary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + description.hashCode();
		result = (prime * result) + id;
		result = (prime * result) + summary.hashCode();
		return result;
	}

	/**
	 * Is this Defect an exact match (do all fields match)?
	 *
	 * @param other
	 *            The Defect to determine if this Defect is an exact match of.
	 * @return True if the Defects exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final Defect other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (!assignedTo.equals(other.assignedTo)) {
			return false;
		}

		if (closingDate.getTimeInMillis() != other.closingDate.getTimeInMillis()) {
			return false;
		}
		if (!getComments().equals(other.getComments())) {
			return false;
		}
		if (!detectedBy.equals(other.detectedBy)) {
			return false;
		}
		if (detectedInReleaseCycleId != other.detectedInReleaseCycleId) {
			return false;
		}

		if (detectedInReleaseId != other.detectedInReleaseId) {
			return false;
		}

		if (detectedOnDate.getTimeInMillis() != other.detectedOnDate.getTimeInMillis()) {
			return false;
		}
		if (modified.getTimeInMillis() != other.modified.getTimeInMillis()) {
			return false;
		}
		if (!severity.equals(other.severity)) {
			return false;
		}
		if (!status.equals(other.status)) {
			return false;
		}

		if (detectedInRelease == null) {
			if (other.detectedInRelease != null) {
				return false;
			}
		} else if (!detectedInRelease.isExactMatch(other.detectedInRelease)) {
			return false;
		}

		if (detectedInReleaseCycle == null) {
			if (other.detectedInReleaseCycle != null) {
				return false;
			}
		} else if (!detectedInReleaseCycle.isExactMatch(other.detectedInReleaseCycle)) {
			return false;
		}

		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		assignedTo = entity.hasFieldValue(DefectField.ASSIGNED_TO.getName())
				? entity.getFieldValues((DefectField.ASSIGNED_TO.getName())).get(0) : "";
		closingDate.setTime(entity.hasFieldValue(DefectField.CLOSING_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(DefectField.CLOSING_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		comments = entity.hasFieldValue(DefectField.COMMENTS.getName())
				? entity.getFieldValues((DefectField.COMMENTS.getName())).get(0) : "";
		description = entity.hasFieldValue(DefectField.DESCRIPTION.getName())
				? entity.getFieldValues((DefectField.DESCRIPTION.getName())).get(0) : "";
		detectedBy = entity.hasFieldValue(DefectField.DETECTED_BY.getName())
				? entity.getFieldValues((DefectField.DETECTED_BY.getName())).get(0) : "";
		detectedInReleaseCycleId = entity.hasFieldValue(DefectField.DETECTED_IN_CYCLE_ID.getName())
				? Integer.valueOf(entity.getFieldValues(DefectField.DETECTED_IN_CYCLE_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		detectedInReleaseId = entity.hasFieldValue(DefectField.DETECTED_IN_RELEASE_ID.getName())
				? Integer.valueOf(entity.getFieldValues(DefectField.DETECTED_IN_RELEASE_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		detectedOnDate.setTime(entity.hasFieldValue(DefectField.DETECTED_ON_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(DefectField.DETECTED_ON_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		id = entity.hasFieldValue(DefectField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(DefectField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		modified.setTime(entity.hasFieldValue(DefectField.MODIFIED.getName())
				? AlmDateFormatter.createDateTime(entity.getFieldValues(DefectField.MODIFIED.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		severity = entity.hasFieldValue(DefectField.SEVERITY.getName())
				? entity.getFieldValues((DefectField.SEVERITY.getName())).get(0) : "";
		status = entity.hasFieldValue(DefectField.STATUS.getName())
				? entity.getFieldValues((DefectField.STATUS.getName())).get(0) : "";
		summary = entity.hasFieldValue(DefectField.SUMMARY.getName())
				? entity.getFieldValues((DefectField.SUMMARY.getName())).get(0) : "";
		if (entity.hasRelatedEntities()) {
			for (final GenericEntity relatedEntity : entity.getRelatedEntities()) {
				populateRelatedEntity(relatedEntity);
			}
		}
	}

	/**
	 * Sets the assigned to user of this Defect.
	 *
	 * @param assignedTo
	 *            The assigned to username.
	 * @since 1.0.0
	 */
	public void setAssignedTo(final String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * The closing date of the Defect.
	 *
	 * @param closingDate
	 *            The closing date in the form yyyy-MM-dd.
	 * @since 1.0.0
	 */
	public void setClosingDate(final String closingDate) {
		this.closingDate.setTime(AlmDateFormatter.createDate(closingDate));
	}

	/**
	 * Sets the comments for this Defect.
	 *
	 * @param comments
	 *            The Defect comments.
	 * @since 1.0.0
	 */
	public void setComments(final String comments) {
		this.comments = comments;
	}

	/**
	 * Sets the comments for this Defect.
	 *
	 * @param comments
	 *            The Defect comments.
	 * @since 1.0.0
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Sets the detected by for this Defect.
	 *
	 * @param detectedBy
	 *            the detected by.
	 * @since 1.0.0
	 */
	public void setDetectedBy(final String detectedBy) {
		this.detectedBy = detectedBy;
	}

	/**
	 * Sets the detected in {@link Release} info for this Defect.
	 *
	 * @param detectedInRelease
	 *            The detected in {@link Release} object.
	 * @since 1.0.0
	 */
	public void setDetectedInRelease(final Release detectedInRelease) {
		this.detectedInRelease = detectedInRelease;
	}

	/**
	 * Sets the detected in {@link ReleaseCycle} for this Defect.
	 *
	 * @param detectedInReleaseCycle
	 *            The detected in {@link ReleaseCycle}.
	 * @since 1.0.0
	 */
	public void setDetectedInReleaseCycle(final ReleaseCycle detectedInReleaseCycle) {
		this.detectedInReleaseCycle = detectedInReleaseCycle;
	}

	/**
	 * Sets the id of the detected in {@link ReleaseCycle}.
	 *
	 * @param detectedInReleaseCycleId
	 *            The id of the detected in {@link ReleaseCycle}.
	 * @since 1.0.0
	 */
	public void setDetectedInReleaseCycleId(final int detectedInReleaseCycleId) {
		this.detectedInReleaseCycleId = detectedInReleaseCycleId;
	}

	/**
	 * Sets the id of the detected in {@link Release}.
	 *
	 * @param detectedInReleaseId
	 *            The id of the detected in {@link Release}.
	 * @since 1.0.0
	 */
	public void setDetectedInReleaseId(final int detectedInReleaseId) {
		this.detectedInReleaseId = detectedInReleaseId;
	}

	/**
	 * Sets the detected on date.
	 *
	 * @param theDetectedOnDate
	 *            the detected on date in the format yyyy-MM-dd.
	 * @throws IllegalArgumentException
	 *             if the detected on date is not in the format yyyy-MM-dd.
	 * @since 1.0.0
	 */
	public void setDetectedOnDate(final String theDetectedOnDate) {
		this.detectedOnDate.setTime(AlmDateFormatter.createDate(theDetectedOnDate));
	}

	/**
	 * Sets the Defect id.
	 *
	 * @param id
	 *            The Defect id.
	 * @since 1.0.0
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets the last modified date time of this Defect.
	 *
	 * @param theModified
	 *            The last modified date time in the format yyyy-MM-dd hh:mm:ss.
	 * @throws IllegalArgumentException
	 *             if the Date is not in the format yyyy-MM-dd hh:mm:ss.
	 * @since 1.0.0
	 */
	public void setModified(final String theModified) {
		this.modified.setTime(AlmDateFormatter.createDateTime(theModified));
	}

	/**
	 * Sets the Defect severity.
	 *
	 * @param severity
	 *            the severity for this Defect.
	 * @since 1.0.0
	 */
	public void setSeverity(final String severity) {
		this.severity = severity;
	}

	/**
	 * Sets the status of the Defect.
	 *
	 * @param status
	 *            The status to set.
	 * @since 1.0.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Sets the summary of the Defect.
	 *
	 * @param summary
	 *            The summary to set.
	 * @since 1.0.0
	 */
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Defect> {\n    id=|").append(id)
				.append("|,\n    summary=|").append(summary).append("|,\n    description=|").append(getDescription())
				.append("|,\n    severity=|").append(severity).append("|,\n    status=|").append(status)
				.append("|,\n    comments=|").append(getComments()).append("|,\n    assignedTo=|").append(assignedTo)
				.append("|,\n    closingDate=|").append(getClosingDate()).append("|,\n    detectedBy=|")
				.append(detectedBy).append("|,\n    detectedInReleaseCycleId=|").append(detectedInReleaseCycleId)
				.append("|,\n    detectedInReleaseId=|").append(detectedInReleaseId).append("|,\n    detectedOnDate=|")
				.append(getDetectedOnDate()).append("|,\n    modified=|").append(getModified())
				.append("|,\n    detectedInRelease=|")
				.append(detectedInRelease == null ? "Not Set" : "\n" + detectedInRelease)
				.append("|,\n    detectedInReleaseCycle=|")
				.append(detectedInReleaseCycle == null ? "Not Set" : "\n" + detectedInReleaseCycle).append("|");
		return builder.toString();
	}

	private void populateRelatedEntity(final GenericEntity relatedEntity) {
		switch (EntityType.fromEntityName(relatedEntity.getType())) {
		case RELEASE:
			if (detectedInRelease == null) {
				detectedInRelease = new Release();
			}
			detectedInRelease.populateFields(relatedEntity);
			break;
		case RELEASE_CYCLE:
			if (detectedInReleaseCycle == null) {
				detectedInReleaseCycle = new ReleaseCycle();
			}
			detectedInReleaseCycle.populateFields(relatedEntity);
			break;
		default:
			break;
		}

	}

}
