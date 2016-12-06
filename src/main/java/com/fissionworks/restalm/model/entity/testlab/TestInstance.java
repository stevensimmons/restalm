package com.fissionworks.restalm.model.entity.testlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.entity.EntityType;
import com.fissionworks.restalm.constants.field.TestInstanceField;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.entity.testplan.TestConfig;

/**
 * Class that represents a Test Instance in ALM.
 *
 * @since 1.0.0
 *
 */
public final class TestInstance implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test-instance";

	private static final String COLLECTION_TYPE = "test-instances";

	private AlmTest associatedTest;

	private TestConfig associatedTestConfig;

	private int id = Integer.MIN_VALUE;

	private final Calendar lastModified = Calendar.getInstance();

	private TestSet parentTestSet;

	private String plannedHost = "";

	private String responsibleTester = "";

	private String status = "";

	private String subtype = "hp.qc.test-instance.MANUAL";

	private int testConfigId = Integer.MIN_VALUE;

	private int testId = Integer.MIN_VALUE;

	private int testInstanceNumber = Integer.MIN_VALUE;

	private int testOrder = Integer.MIN_VALUE;

	private int testSetId = Integer.MIN_VALUE;

	/**
	 * Default constructor; sets last modified to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public TestInstance() {
		lastModified.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(lastModified.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(TestInstanceField.LAST_MODIFIED.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDateTime(lastModified))));
		fields.add(new Field(TestInstanceField.RESPONSIBLE_TESTER.getName(), Arrays.asList(responsibleTester)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(TestInstanceField.PLANNED_HOST.getName(), Arrays.asList(plannedHost)));
		fields.add(new Field(TestInstanceField.STATUS.getName(), Arrays.asList(status)));
		fields.add(testConfigId == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.TEST_CONFIG_ID.getName(), Arrays.asList(String.valueOf(testConfigId))));
		fields.add(testId == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.TEST_ID.getName(), Arrays.asList(String.valueOf(testId))));
		fields.add(testInstanceNumber == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.TEST_INSTANCE_NUMBER.getName(),
						Arrays.asList(String.valueOf(testInstanceNumber))));
		fields.add(testSetId == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.TEST_SET_ID.getName(), Arrays.asList(String.valueOf(testSetId))));
		fields.add(testOrder == Integer.MIN_VALUE ? null
				: new Field(TestInstanceField.TEST_ORDER.getName(), Arrays.asList(String.valueOf(testOrder))));
		fields.add(new Field(TestInstanceField.SUBTYPE.getName(), Arrays.asList(subtype)));
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
		final TestInstance other = (TestInstance) obj;
		if (id != other.id) {
			return false;
		}
		if (testConfigId != other.testConfigId) {
			return false;
		}
		if (testId != other.testId) {
			return false;
		}
		if (testSetId != other.testSetId) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the currently set associated {@link AlmTest}.
	 *
	 * @return the currently set associated {@link AlmTest}.
	 * @since 1.0.0
	 */
	public AlmTest getAssociatedTest() {
		return associatedTest == null ? new AlmTest() : associatedTest;
	}

	/**
	 * Gets the currently set associated {@link TestConfig}.
	 *
	 * @return the currently set associated {@link TestConfig}.
	 * @since 1.0.0
	 */
	public TestConfig getAssociatedTestConfig() {
		return associatedTestConfig == null ? new TestConfig() : associatedTestConfig;
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
	 * Gets the last modified time for the Test Instance
	 *
	 * @return The last modified time in the form "yyyy-MM-dd hh:mm:ss"
	 * @since 1.0.0
	 */
	public String getLastModified() {
		return AlmDateFormatter.getStandardDateTime(lastModified);
	}

	/**
	 * Gets the parent {@link TestSet}.
	 *
	 * @return the currently set parent {@link TestSet}.
	 * @since 1.0.0
	 */
	public TestSet getParentTestSet() {
		return parentTestSet == null ? new TestSet() : parentTestSet;
	}

	/**
	 * The planned host for this Test Instance.
	 *
	 * @return The planned host
	 * @since 1.0.0
	 */
	public String getPlannedHost() {
		return plannedHost;
	}

	/**
	 * The responsible test for this Test Instance/
	 *
	 * @return The responsible testers user id.
	 * @since 1.0.0
	 */
	public String getResponsibleTester() {
		return responsibleTester;
	}

	/**
	 * Gets the status of the test instance.
	 *
	 * @return The currently set status.
	 * @since 1.0.0
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the Test Instance subtype.
	 *
	 * @return The Subtype for this Test Instance.
	 * @since 1.0.0
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Get the test config ID associated with this Test Instance.
	 *
	 * @return The ID of the associated Test Config.
	 * @since 1.0.0
	 */
	public int getTestConfigId() {
		return testConfigId;
	}

	/**
	 * Get the test ID associated with this Test Instance.
	 *
	 * @return The ID of the associated Test.
	 * @since 1.0.0
	 */
	public int getTestId() {
		return testId;
	}

	/**
	 * Get the test instance number for this Test Instance.
	 *
	 * @return the test instance number.
	 * @since 1.0.0
	 */
	public int getTestInstanceNumber() {
		return testInstanceNumber;
	}

	/**
	 * Get the test order number for this Test Instance.
	 *
	 * @return the test order number.
	 * @since 1.0.0
	 */
	public int getTestOrder() {
		return testOrder;
	}

	/**
	 * Returns the ID of the test set this Test Instance is a part of.
	 *
	 * @return The ID of the Test Set containing this Instance.
	 * @since 1.0.0
	 */
	public int getTestSetId() {
		return testSetId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + id;
		result = (prime * result) + testConfigId;
		result = (prime * result) + testId;
		result = (prime * result) + testSetId;
		return result;
	}

	/**
	 * Is this TestInstance an exact match (do all fields match)?
	 *
	 * @param other
	 *            The TestInstance to determine if this TestInstance is an exact
	 *            match of.
	 * @return True if the TestInstances exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final TestInstance other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (lastModified.getTimeInMillis() != other.lastModified.getTimeInMillis()) {
			return false;
		}
		if (!plannedHost.equals(other.plannedHost)) {
			return false;
		}
		if (!responsibleTester.equals(other.responsibleTester)) {
			return false;
		}
		if (!status.equals(other.status)) {
			return false;
		}
		if (!subtype.equals(other.subtype)) {
			return false;
		}
		if (testInstanceNumber != other.testInstanceNumber) {
			return false;
		}
		if (testOrder != other.testOrder) {
			return false;
		}

		if (associatedTest == null) {
			if (other.associatedTest != null) {
				return false;
			}
		} else if (!associatedTest.isExactMatch(other.associatedTest)) {
			return false;
		}

		if (associatedTestConfig == null) {
			if (other.associatedTestConfig != null) {
				return false;
			}
		} else if (!associatedTestConfig.isExactMatch(other.associatedTestConfig)) {
			return false;
		}

		if (parentTestSet == null) {
			if (other.parentTestSet != null) {
				return false;
			}
		} else if (!parentTestSet.isExactMatch(other.parentTestSet)) {
			return false;
		}
		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		lastModified.setTime(entity.hasFieldValue(TestInstanceField.LAST_MODIFIED.getName())
				? AlmDateFormatter
						.createDateTime(entity.getFieldValues(TestInstanceField.LAST_MODIFIED.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		plannedHost = entity.hasFieldValue(TestInstanceField.PLANNED_HOST.getName())
				? entity.getFieldValues((TestInstanceField.PLANNED_HOST.getName())).get(0) : "";
		responsibleTester = entity.hasFieldValue(TestInstanceField.RESPONSIBLE_TESTER.getName())
				? entity.getFieldValues(TestInstanceField.RESPONSIBLE_TESTER.getName()).get(0) : "";
		id = entity.hasFieldValue(TestInstanceField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		status = entity.hasFieldValue(TestInstanceField.STATUS.getName())
				? entity.getFieldValues(TestInstanceField.STATUS.getName()).get(0) : "";
		subtype = entity.hasFieldValue(TestInstanceField.SUBTYPE.getName())
				? entity.getFieldValues(TestInstanceField.SUBTYPE.getName()).get(0) : "";
		testConfigId = entity.hasFieldValue(TestInstanceField.TEST_CONFIG_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.TEST_CONFIG_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		testId = entity.hasFieldValue(TestInstanceField.TEST_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.TEST_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		testOrder = entity.hasFieldValue(TestInstanceField.TEST_ORDER.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.TEST_ORDER.getName()).get(0))
				: Integer.MIN_VALUE;
		testInstanceNumber = entity.hasFieldValue(TestInstanceField.TEST_INSTANCE_NUMBER.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.TEST_INSTANCE_NUMBER.getName()).get(0))
				: Integer.MIN_VALUE;
		testSetId = entity.hasFieldValue(TestInstanceField.TEST_SET_ID.getName())
				? Integer.valueOf(entity.getFieldValues(TestInstanceField.TEST_SET_ID.getName()).get(0))
				: Integer.MIN_VALUE;
		if (entity.hasRelatedEntities()) {
			for (final GenericEntity relatedEntity : entity.getRelatedEntities()) {
				populateRelatedEntity(relatedEntity);
			}
		}

	}

	/**
	 * Sets the {@link AlmTest} this Test Instance represents.
	 *
	 * @param associatedTest
	 *            The {@link AlmTest} to set.
	 * @since 1.0.0
	 */
	public void setAssociatedTest(final AlmTest associatedTest) {
		this.associatedTest = associatedTest;
	}

	/**
	 * Sets the {@link TestConfig} this Test Instance represents.
	 *
	 * @param associatedTest
	 *            The {@link TestConfig} to set.
	 * @since 1.0.0
	 */
	public void setAssociatedTestConfig(final TestConfig associatedTestConfig) {
		this.associatedTestConfig = associatedTestConfig;
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
	 *
	 * @since 1.0.0
	 */
	public void setLastModified(final String theLastModified) {
		this.lastModified.setTime(AlmDateFormatter.createDateTime(theLastModified));
	}

	/**
	 * Sets the parent {@link TestSet} for this Test Instance.
	 *
	 * @param theParentTestSet
	 *            The parent {@link TestSet} to set.
	 * @since 1.0.0
	 */
	public void setParentTestSet(final TestSet theParentTestSet) {
		parentTestSet = theParentTestSet;
	}

	/**
	 * Sets the planned host for this Test Instance.
	 *
	 * @param plannedHost
	 *            The planned host value to set.
	 * @since 1.0.0
	 */
	public void setPlannedHost(final String plannedHost) {
		this.plannedHost = plannedHost;
	}

	/**
	 * Set the tester responsible for this Test Instance.
	 *
	 * @param responsibleTester
	 *            the responsible user's id.
	 * @since 1.0.0
	 */
	public void setResponsibleTester(final String responsibleTester) {
		this.responsibleTester = responsibleTester;
	}

	/**
	 * Sets the status of this Test Instance.
	 *
	 * @param status
	 *            The status to set.
	 * @since 1.0.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Sets the subtype for this Test Instance.
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
	 * Sets the Id of the associated {@link AlmTest}.
	 *
	 * @param testId
	 *            The id of the associated {@link AlmTest}.
	 * @since 1.0.0
	 */
	public void setTestId(final int testId) {
		this.testId = testId;
	}

	/**
	 * Sets the test instance number.
	 *
	 * @param testInstanceNumber
	 *            the test instance number to set.
	 * @since 1.0.0
	 */
	public void setTestInstanceNumber(final int testInstanceNumber) {
		this.testInstanceNumber = testInstanceNumber;
	}

	/**
	 * Set the test order number for this Test Instance.
	 *
	 * @param testOrder
	 *            The test order number to set.
	 * @since 1.0.0
	 */
	public void setTestOrder(final int testOrder) {
		this.testOrder = testOrder;
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
		builder.append(super.toString()).append(" extended by: <TestInstance> {\n    id=|").append(id)
				.append("|,\n    lastModified=|").append(getLastModified()).append("|,\n    plannedHost=|")
				.append(plannedHost).append("|,\n    responsibleTester=|").append(responsibleTester)
				.append("|,\n    status=|").append(status).append("|,\n    subtype=|").append(subtype)
				.append("|,\n    testConfigId=|").append(testConfigId).append("|,\n    testId=|").append(testId)
				.append("|,\n    testInstanceNumber=|").append(testInstanceNumber).append("|,\n    testOrder=|")
				.append(testOrder).append("|,\n    testSetId=|").append(testSetId).append("|,\n    parentTestSet=|")
				.append(parentTestSet == null ? "Not Set" : "\n" + parentTestSet).append("|,\n    associatedTest=|")
				.append(associatedTest == null ? "Not Set" : "\n" + associatedTest)
				.append("|,\n    associatedTestConfig=|")
				.append(associatedTestConfig == null ? "Not Set" : "\n" + associatedTestConfig).append("|");
		return builder.toString();
	}

	private void populateRelatedEntity(final GenericEntity relatedEntity) {
		switch (EntityType.fromEntityName(relatedEntity.getType())) {
		case ALM_TEST:
			if (associatedTest == null) {
				associatedTest = new AlmTest();
			}
			associatedTest.populateFields(relatedEntity);
			break;
		case TEST_CONFIG:
			if (associatedTestConfig == null) {
				associatedTestConfig = new TestConfig();
			}
			associatedTestConfig.populateFields(relatedEntity);
			break;
		case TEST_SET:
			if (parentTestSet == null) {
				parentTestSet = new TestSet();
			}
			parentTestSet.populateFields(relatedEntity);
			break;
		default:
			break;
		}

	}

}
