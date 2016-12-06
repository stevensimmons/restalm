package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.entity.EntityType;
import com.fissionworks.restalm.constants.field.RunField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;

/**
 * Class that represents a Run in ALM.
 *
 * @since 1.0.0
 *
 */
public final class Run implements AlmEntity {

	private static final String COLLECTION_TYPE = "runs";

	private static final String TYPE = "run";

	private TestConfig associatedTestConfig;

	private TestInstance associatedTestInstance;

	private String comments = "";

	private String host = "";

	private int id = Integer.MIN_VALUE;

	private final Calendar lastModified = Calendar.getInstance();

	private String name = "";

	private String owner = "";

	private String status = "";

	private String subtype = "";

	private int testConfigId = Integer.MIN_VALUE;

	private int testId = Integer.MIN_VALUE;

	private int testInstanceId = Integer.MIN_VALUE;

	private int testSetId = Integer.MIN_VALUE;

	/**
	 * Default constructor; sets last modified to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public Run() {
		lastModified.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field(RunField.COMMENTS.getName(), Arrays.asList(comments)));
		fields.add(new Field(RunField.HOST.getName(), Arrays.asList(host)));
		fields.add(lastModified.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(RunField.LAST_MODIFIED.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDateTime(lastModified))));
		fields.add(new Field(RunField.NAME.getName(), Arrays.asList(name)));
		fields.add(new Field(RunField.OWNER.getName(), Arrays.asList(owner)));
		fields.add(
				id == Integer.MIN_VALUE ? null : new Field(RunField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(RunField.STATUS.getName(), Arrays.asList(status)));
		fields.add(testId == Integer.MIN_VALUE ? null
				: new Field(RunField.TEST_ID.getName(), Arrays.asList(String.valueOf(testId))));
		fields.add(testSetId == Integer.MIN_VALUE ? null
				: new Field(RunField.TEST_SET_ID.getName(), Arrays.asList(String.valueOf(testSetId))));
		fields.add(testInstanceId == Integer.MIN_VALUE ? null
				: new Field(RunField.TEST_INSTANCE_ID.getName(), Arrays.asList(String.valueOf(testInstanceId))));
		fields.add(testConfigId == Integer.MIN_VALUE ? null
				: new Field(RunField.TEST_CONFIG_ID.getName(), Arrays.asList(String.valueOf(testConfigId))));
		fields.add(new Field(RunField.SUBTYPE_ID.getName(), Arrays.asList(subtype)));
		fields.removeAll(Collections.singleton(null));
		return new GenericEntity(TYPE, fields);
	}

	/**
	 * Gets the {@link TestConfig} associated with this run; If no
	 * {@link TestConfig} info has been set, a blank {@link TestConfig} is
	 * returned.
	 *
	 * @return The {@link TestConfig} this Run is associated with.
	 * @since 1.0.0
	 */
	public TestConfig getAssociatedTestConfig() {
		return associatedTestConfig == null ? new TestConfig() : associatedTestConfig;
	}

	/**
	 * Gets the {@link TestInstance} associated with this run; If no
	 * {@link TestInstance} info has been set, a blank {@link TestInstance} is
	 * returned.
	 *
	 * @return The {@link TestInstance} this Run is associated with.
	 * @since 1.0.0
	 */
	public TestInstance getAssociatedTestInstance() {
		return associatedTestInstance == null ? new TestInstance() : associatedTestInstance;
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
	 * Get the host this Run is executed on.
	 *
	 * @return The host this Run is executed on.
	 * @since 1.0.0
	 */
	public String getHost() {
		return host;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Gets the last modified time for the Run.
	 *
	 * @return The last modified time in the form "yyyy-MM-dd hh:mm:ss"
	 * @since 1.0.0
	 */
	public String getLastModified() {
		return AlmDateFormatter.getStandardDateTime(lastModified);
	}

	/**
	 * Get the Run name.
	 *
	 * @return the Run name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the owner that is designated for this Run.
	 *
	 * @return The owner.
	 * @since 1.0.0
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Gets the status of the Run.
	 *
	 * @return The currently set status.
	 * @since 1.0.0
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the Run subtype.
	 *
	 * @return The Subtype for this Test Instance.
	 * @since 1.0.0
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Get the test config ID associated with this Run.
	 *
	 * @return The ID of the associated Test Config.
	 * @since 1.0.0
	 */
	public int getTestConfigId() {
		return testConfigId;
	}

	/**
	 * Get the test ID associated with this Run.
	 *
	 * @return The ID of the associated Test.
	 * @since 1.0.0
	 */
	public int getTestId() {
		return testId;
	}

	/**
	 * Get the Test Instance ID associated with this Run.
	 *
	 * @return The ID of the associated Test Instance.
	 * @since 1.0.0
	 */
	public int getTestInstanceId() {
		return testInstanceId;
	}

	/**
	 * Returns the ID of the test set this Run is a part of.
	 *
	 * @return The ID of the Test Set containing this Run.
	 * @since 1.0.0
	 */
	public int getTestSetId() {
		return testSetId;
	}

	/**
	 * Is this Run an exact match (do all fields match)?
	 *
	 * @param other
	 *            The Run to determine if this Run is an exact match of.
	 * @return True if the Runs exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final Run other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}

		if (associatedTestConfig == null) {
			if (other.associatedTestConfig != null) {
				return false;
			}
		} else if (!associatedTestConfig.isExactMatch(other.associatedTestConfig)) {
			return false;
		}
		if (associatedTestInstance == null) {
			if (other.associatedTestInstance != null) {
				return false;
			}
		} else if (!associatedTestInstance.isExactMatch(other.associatedTestInstance)) {
			return false;
		}
		if (!getComments().equals(other.getComments())) {
			return false;
		}
		if (!host.equals(other.host)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (lastModified.getTimeInMillis() != other.lastModified.getTimeInMillis()) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		if (!owner.equals(other.owner)) {
			return false;
		}
		if (!status.equals(other.status)) {
			return false;
		}
		if (!subtype.equals(other.subtype)) {
			return false;
		}
		if (testConfigId != other.testConfigId) {
			return false;
		}
		if (testId != other.testId) {
			return false;
		}
		if (testInstanceId != other.testInstanceId) {
			return false;
		}
		if (testSetId != other.testSetId) {
			return false;
		}
		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		comments = entity.hasFieldValue(RunField.COMMENTS.getName())
				? entity.getFieldValues((RunField.COMMENTS.getName())).get(0) : "";
		host = entity.hasFieldValue(RunField.HOST.getName()) ? entity.getFieldValues((RunField.HOST.getName())).get(0)
				: "";
		id = entity.hasFieldValue(RunField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(RunField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		lastModified.setTime(entity.hasFieldValue(RunField.LAST_MODIFIED.getName())
				? AlmDateFormatter.createDateTime(entity.getFieldValues(RunField.LAST_MODIFIED.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		name = entity.hasFieldValue(RunField.NAME.getName()) ? entity.getFieldValues((RunField.NAME.getName())).get(0)
				: "";
		owner = entity.hasFieldValue(RunField.OWNER.getName())
				? entity.getFieldValues((RunField.OWNER.getName())).get(0) : "";
		status = entity.hasFieldValue(RunField.STATUS.getName())
				? entity.getFieldValues((RunField.STATUS.getName())).get(0) : "";
		subtype = entity.hasFieldValue(RunField.SUBTYPE_ID.getName())
				? entity.getFieldValues((RunField.SUBTYPE_ID.getName())).get(0) : "";
		testConfigId = entity.hasFieldValue(RunField.TEST_CONFIG_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RunField.TEST_CONFIG_ID.getName()).get(0)) : Integer.MIN_VALUE;
		testId = entity.hasFieldValue(RunField.TEST_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RunField.TEST_ID.getName()).get(0)) : Integer.MIN_VALUE;
		testInstanceId = entity.hasFieldValue(RunField.TEST_INSTANCE_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RunField.TEST_INSTANCE_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		testSetId = entity.hasFieldValue(RunField.TEST_SET_ID.getName())
				? Integer.valueOf(entity.getFieldValues(RunField.TEST_SET_ID.getName()).get(0)) : Integer.MIN_VALUE;
		if (entity.hasRelatedEntities()) {
			for (final GenericEntity relatedEntity : entity.getRelatedEntities()) {
				populateRelatedEntity(relatedEntity);
			}

		}

	}

	/**
	 * Sets the {@link TestConfig} this Run is for.
	 *
	 * @param theAssociatedTestConfig
	 *            The {@link TestConfig} to set.
	 * @since 1.0.0
	 */
	public void setAssociatedTestConfig(final TestConfig theAssociatedTestConfig) {
		if (associatedTestConfig == null) {
			associatedTestConfig = new TestConfig();
		}
		this.associatedTestConfig.populateFields(theAssociatedTestConfig.createEntity());
	}

	/**
	 * Sets the {@link TestInstance} this Run is for.
	 *
	 * @param theAssociatedTestInstance
	 *            The {@link TestInstance} to set.
	 * @since 1.0.0
	 */
	public void setAssociatedTestInstance(final TestInstance theAssociatedTestInstance) {
		if (associatedTestInstance == null) {
			associatedTestInstance = new TestInstance();
		}
		this.associatedTestInstance.populateFields(theAssociatedTestInstance.createEntity());
	}

	/**
	 * add comments to the Run.
	 *
	 * @param comments
	 *            The comments to add.
	 * @since 1.0.0
	 */
	public void setComments(final String comments) {
		this.comments = comments;
	}

	/**
	 * Sets the host for this Run.
	 *
	 * @param host
	 *            The host name string.
	 * @since 1.0.0
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set.
	 * @since 1.0.0
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets the last modified date time.
	 *
	 * @param theLastModified
	 *            the last modified time to set.
	 * @throws IllegalArgumentException
	 *             thrown if the last modified time format does not conform to
	 *             (yyyy-MM-dd hh:mm:ss).
	 * @since 1.0.0
	 */
	public void setLastModified(final String theLastModified) {
		this.lastModified.setTime(AlmDateFormatter.createDateTime(theLastModified));
	}

	/**
	 * Sets the name of the Run.
	 *
	 * @param name
	 *            The name to set.
	 * @since 1.0.0
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the owner of this Run.
	 *
	 * @param owner
	 *            The username of the owner.
	 * @since 1.0.0
	 */
	public void setOwner(final String owner) {
		this.owner = owner;
	}

	/**
	 * Sets the status of this Run.
	 *
	 * @param status
	 *            The status to set.
	 * @since 1.0.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Sets the subtype for this Run.
	 *
	 * @param subtype
	 *            The subtype to set.
	 * @since 1.0.0
	 */
	public void setSubtype(final String subtype) {
		this.subtype = subtype;
	}

	/**
	 * Sets the Id of the associated {@link TestConfig}.
	 *
	 * @param testId
	 *            The id of the associated {@link TestConfig}.
	 * @since 1.0.0
	 */
	public void setTestConfigId(final int testConfigId) {
		this.testConfigId = testConfigId;
	}

	/**
	 * Sets the Id of the associated AlmTest.
	 *
	 * @param testId
	 *            The id of the associated AlmTest.
	 * @since 1.0.0
	 */
	public void setTestId(final int testId) {
		this.testId = testId;
	}

	/**
	 * Sets the Id of the associated {@link TestInstance}.
	 *
	 * @param testId
	 *            The id of the associated {@link TestInstance}.
	 * @since 1.0.0
	 */
	public void setTestInstanceId(final int testInstanceId) {
		this.testInstanceId = testInstanceId;
	}

	/**
	 * Set the ID of the {@link TestSet} containing this Test Instance.
	 *
	 * @param testSetId
	 *            The id of the {@link TestSet}.
	 * @since 1.0.0
	 */
	public void setTestSetId(final int testSetId) {
		this.testSetId = testSetId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Run> {\n    id=|").append(id).append("|,\n    name=|")
				.append(name).append("|,\n    status=|").append(status).append("|,\n    comments=|")
				.append(getComments()).append("|,\n    host=|").append(host).append("|,\n    lastModified=|")
				.append(getLastModified()).append("|,\n    owner=|").append(owner).append("|,\n    subtype=|")
				.append(subtype).append("|,\n    testConfigId=|").append(testConfigId).append("|,\n    testId=|")
				.append(testId).append("|,\n    testInstanceId=|").append(testInstanceId).append("|,\n    testSetId=|")
				.append(testSetId).append("|,\n    associatedTestInstance=|")
				.append(associatedTestInstance == null ? "Not Set" : "\n" + associatedTestInstance)
				.append("|,\n    associatedTestConfig=|")
				.append(associatedTestConfig == null ? "Not Set" : "\n" + associatedTestConfig).append("|");
		return builder.toString();
	}

	private void populateRelatedEntity(final GenericEntity relatedEntity) {
		switch (EntityType.fromEntityName(relatedEntity.getType())) {
		case TEST_INSTANCE:
			if (associatedTestInstance == null) {
				associatedTestInstance = new TestInstance();
			}
			associatedTestInstance.populateFields(relatedEntity);
			break;
		case TEST_CONFIG:
			if (associatedTestConfig == null) {
				associatedTestConfig = new TestConfig();
			}
			associatedTestConfig.populateFields(relatedEntity);
			break;
		default:
			break;
		}

	}

}
