package com.fissionworks.restalm.model.entity.testplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fissionworks.restalm.commons.AlmDateFormatter;
import com.fissionworks.restalm.constants.field.AlmTestField;
import com.fissionworks.restalm.conversion.ConversionUtils;
import com.fissionworks.restalm.model.entity.AlmEntity;
import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

/**
 * Class that represents a test in ALM.
 *
 * @since 1.0.0
 *
 */
public final class AlmTest implements AlmEntity {

	/**
	 * The entity type name as defined in ALM.
	 */
	public static final String TYPE = "test";

	private static final String COLLECTION_TYPE = "tests";

	private final Calendar creationDate = Calendar.getInstance();

	private final List<Field> customFields = new ArrayList<>();

	private String description = "";

	private String designer = "";

	private int id = Integer.MIN_VALUE;

	private String name = "";

	private int parentId = Integer.MIN_VALUE;

	private TestFolder parentTestFolder;

	private String status = "";

	private String type = "";

	/**
	 * Default constructor; sets creation date to {@link Long#MIN_VALUE}.
	 *
	 * @since 1.0.0
	 */
	public AlmTest() {
		creationDate.setTimeInMillis(Long.MIN_VALUE);
	}

	@Override
	public GenericEntity createEntity() {
		final List<Field> fields = new ArrayList<>();
		fields.add(creationDate.getTimeInMillis() == Long.MIN_VALUE ? null
				: new Field(AlmTestField.CREATION_DATE.getName(),
						Arrays.asList(AlmDateFormatter.getStandardDate(creationDate))));
		fields.add(new Field(AlmTestField.DESCRIPTION.getName(), Arrays.asList(description)));
		fields.add(new Field(AlmTestField.DESIGNER.getName(), Arrays.asList(designer)));
		fields.add(id == Integer.MIN_VALUE ? null
				: new Field(AlmTestField.ID.getName(), Arrays.asList(String.valueOf(id))));
		fields.add(new Field(AlmTestField.NAME.getName(), Arrays.asList(name)));
		fields.add(parentId == Integer.MIN_VALUE ? null
				: new Field(AlmTestField.PARENT_ID.getName(), Arrays.asList(String.valueOf(parentId))));
		fields.add(new Field(AlmTestField.STATUS.getName(), Arrays.asList(status)));
		fields.add(new Field(AlmTestField.TYPE.getName(), Arrays.asList(type)));
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
		final AlmTest other = (AlmTest) obj;
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
	 * Get the string representation of the creation date (fromat is
	 * "yyyy-MM-dd").
	 *
	 * @return the date string equivalent of the creation date.
	 * @since 1.0.0
	 */
	public String getCreationDate() {
		return AlmDateFormatter.getStandardDate(creationDate);
	}

	/**
	 * Get the value(s) for the custom field with the given name.
	 *
	 * @param fieldName
	 *            The name of the field to get the values of.
	 * @return The list of values for the given field.
	 * @throws IllegalArgumentException
	 *             if no field with the given name exists in this AlmTest.
	 * @since 1.0.0
	 */
	public List<String> getCustomFieldValue(final String fieldName) {
		for (final Field field : customFields) {
			if (field.getName().equals(fieldName)) {
				return field.getValues();
			}
		}
		throw new IllegalArgumentException("no field with name |" + fieldName + "| found in this AlmTest");
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
	 * Returns the AlmTest designer.
	 *
	 * @return the AlmTest designer.
	 * @since 1.0.0
	 */
	public String getDesigner() {
		return designer;
	}

	/**
	 * {@inheritDoc} For AlmTests the collection type is "tests".
	 *
	 * @since 1.0.0
	 */
	@Override
	public String getEntityCollectionType() {
		return COLLECTION_TYPE;
	}

	/**
	 * {@inheritDoc} For AlmTests the type is "test".
	 *
	 * @since 1.0.0
	 */
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
	 * Get the AlmTest name.
	 *
	 * @return the AlmTest name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the AlmTest parent id.
	 *
	 * @return the AlmTest parent id.
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
		return this.parentTestFolder == null ? new TestFolder() : parentTestFolder;
	}

	/**
	 * Get the AlmTest status.
	 *
	 * @return the AlmTest status.
	 * @since 1.0.0
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get the AlmTest type.
	 *
	 * @return the AlmTest type.
	 * @since 1.0.0
	 */
	public String getType() {
		return type;
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
	 * Is this AlmTest an exact match (do all fields match)?
	 *
	 * @param other
	 *            The AlmTest to determine if this AlmTest is an exact match of.
	 * @return True if the AlmTests exactly match, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isExactMatch(final AlmTest other) {
		if (!this.equals(other)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		if (creationDate.getTimeInMillis() != other.creationDate.getTimeInMillis()) {
			return false;
		}
		if (!getDescription().equals(other.getDescription())) {
			return false;
		}
		if (!designer.equals(other.designer)) {
			return false;
		}
		if (!status.equals(other.status)) {
			return false;
		}
		if (!type.equals(other.type)) {
			return false;
		}

		if (parentTestFolder == null) {
			if (other.parentTestFolder != null) {
				return false;
			}
		} else if (!parentTestFolder.isExactMatch(other.parentTestFolder)) {
			return false;
		}
		if (customFields.size() != other.customFields.size()) {
			return false;
		}
		if (!customFields.containsAll(other.customFields)) {
			return false;
		}
		return true;
	}

	@Override
	public void populateFields(final GenericEntity entity) {
		creationDate.setTime(entity.hasFieldValue(AlmTestField.CREATION_DATE.getName())
				? AlmDateFormatter.createDate(entity.getFieldValues(AlmTestField.CREATION_DATE.getName()).get(0))
				: new Date(Long.MIN_VALUE));
		description = entity.hasFieldValue(AlmTestField.DESCRIPTION.getName())
				? entity.getFieldValues(AlmTestField.DESCRIPTION.getName()).get(0) : "";
		designer = entity.hasFieldValue(AlmTestField.DESIGNER.getName())
				? entity.getFieldValues(AlmTestField.DESIGNER.getName()).get(0) : "";
		id = entity.hasFieldValue(AlmTestField.ID.getName())
				? Integer.valueOf(entity.getFieldValues(AlmTestField.ID.getName()).get(0)) : Integer.MIN_VALUE;
		name = entity.hasFieldValue(AlmTestField.NAME.getName())
				? entity.getFieldValues(AlmTestField.NAME.getName()).get(0) : "";
		parentId = entity.hasFieldValue(AlmTestField.PARENT_ID.getName())
				? Integer.valueOf(entity.getFieldValues(AlmTestField.PARENT_ID.getName()).get(0)) : Integer.MIN_VALUE;
		status = entity.hasFieldValue(AlmTestField.STATUS.getName())
				? entity.getFieldValues(AlmTestField.STATUS.getName()).get(0) : "";
		type = entity.hasFieldValue(AlmTestField.TYPE.getName())
				? entity.getFieldValues(AlmTestField.TYPE.getName()).get(0) : "";
		if (entity.hasRelatedEntities()) {
			if (parentTestFolder == null) {
				parentTestFolder = new TestFolder();
			}
			this.parentTestFolder.populateFields(entity.getRelatedEntities().get(0));
		}
		customFields.clear();
		customFields.addAll(entity.getCustomFields());
	}

	/**
	 * Sets the creation date.
	 *
	 * @param theCreatedDate
	 *            the creation date to set.
	 * @throws IllegalArgumentException
	 *             thrown if the created date format does not confrom to
	 *             {@link AlmTest#CREATED_DATE_FORMAT}.
	 * @since 1.0.0
	 */
	public void setCreationDate(final String theCreatedDate) {
		creationDate.setTime(AlmDateFormatter.createDate(theCreatedDate));
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set.
	 * @since 1.0.0
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Sets the designer.
	 *
	 * @param designer
	 *            the designer to set.
	 * @since 1.0.0
	 */
	public void setDesigner(final String designer) {
		this.designer = designer;
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
	 * Sets the name.
	 *
	 * @param name
	 *            the name to set.
	 * @since 1.0.0
	 */
	public void setName(final String name) {
		this.name = name;
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
	 * Sets the parent test folder for this test; The currently set parent test
	 * folder will be overwritten.
	 *
	 * @param testFolder
	 *            The new parent test folder.
	 * @since 1.0.0
	 */
	public void setParentTestFolder(final TestFolder testFolder) {
		if (parentTestFolder == null) {
			parentTestFolder = new TestFolder();
		}
		this.parentTestFolder.populateFields(testFolder.createEntity());
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the status to set.
	 * @since 1.0.0
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Sets the subType.
	 *
	 * @param subType
	 *            the subType to set.
	 * @since 1.0.0
	 */
	public void setType(final String subType) {
		this.type = subType;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <AlmTest> {\n    id=|").append(id)
				.append("|,\n    name=|").append(name).append("|,\n    description=|").append(getDescription())
				.append("|,\n    designer=|").append(designer).append("|,\n    creationDate=|")
				.append(getCreationDate()).append("|,\n    parentId=|").append(parentId).append("|,\n    status=|")
				.append(status).append("|,\n    type=|").append(type).append("|,\n    parentTestFolder=|")
				.append(parentTestFolder == null ? "Not Set" : parentTestFolder).append("|");
		return builder.toString();
	}

}
