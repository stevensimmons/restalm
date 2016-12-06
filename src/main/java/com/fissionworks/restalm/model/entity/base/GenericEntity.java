package com.fissionworks.restalm.model.entity.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.fissionworks.restalm.constants.field.FieldName;
import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;

/**
 * POJO modeled after the common entity xml returned from the ALM rest API for
 * resource entities (test, requirements, runs, etc). Used for
 * marshalling/unmarshalling purposes only.
 *
 * @since 1.0.0
 *
 */
public final class GenericEntity {

	private final Map<String, Field> fieldMap = new HashMap<>();

	private final List<GenericEntity> relatedEntities = new ArrayList<>();

	private final String type;

	/**
	 * Creates an Entity with the given type.
	 *
	 * @param theType
	 *            The type of this entity.
	 * @throws NullPointerException
	 *             Thrown if the type is null.
	 * @throws IllegalArgumentException
	 *             Thrown if the type is blank.
	 * @since 1.0.0
	 */
	public GenericEntity(final String theType, final List<Field> fields) {
		Validate.notBlank(theType, "Type cannot be null or blank");
		Validate.notEmpty(fields, "fields object cannot be null or empty");
		this.type = theType;
		for (final Field field : fields) {
			fieldMap.put(field.getName(), field);
		}
	}

	/**
	 * Add a related entity to this entity.
	 *
	 * @param genericEntity
	 *            The related entity to be added.
	 * @since 1.0.0
	 */
	public void addRelatedEntity(final GenericEntity genericEntity) {
		this.relatedEntities.add(genericEntity);
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
		final GenericEntity other = (GenericEntity) obj;
		if (!fieldMap.equals(other.fieldMap)) {
			return false;
		}
		if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	/**
	 * Formats the entity prior to being marshaled for transport to ALM. Fields
	 * that are empty or that are not editable/required fields are removed.
	 *
	 * @param entityFields
	 *            The entity fields that correspond to this entity in the
	 *            project currently being manipulated in ALM.
	 * @since 1.0.0
	 */
	public void formatForAdd(final EntityFieldCollection entityFields) {
		final List<String> extraFields = new ArrayList<>();
		for (final Entry<String, Field> entry : fieldMap.entrySet()) {
			if (entry.getValue().isEmpty() || !entityFields.isValidAddField(entry.getKey())) {
				extraFields.add(entry.getKey());
			}
		}
		for (final String fieldName : extraFields) {
			fieldMap.remove(fieldName);
		}
	}

	/**
	 * Format the entity in preparation for being sent to ALM for update.
	 * Removes all un-editable fields from the entity.
	 *
	 * @param entityFields
	 *            The {@link EntityFieldCollection} corresponding the the entity
	 *            being updated and the ALM project connected to.
	 * @since 1.0.0
	 */
	public void formatForUpdate(final EntityFieldCollection entityFields) {
		final List<String> extraFields = new ArrayList<>();
		for (final Entry<String, Field> entry : fieldMap.entrySet()) {
			if (!entityFields.isEditableField(entry.getKey())) {
				extraFields.add(entry.getKey());
			}
		}
		for (final String fieldName : extraFields) {
			fieldMap.remove(fieldName);
		}
	}

	/**
	 * Get all custom fields contained in this entity; Custom fields are
	 * identified by having a name that starts with "user-".
	 *
	 * @return The list of custom fields.
	 * @since 1.0.0
	 */
	public List<Field> getCustomFields() {
		final List<Field> customFields = new ArrayList<>();
		for (final Entry<String, Field> fieldEntry : fieldMap.entrySet()) {
			if (StringUtils.startsWith(fieldEntry.getKey(), "user-")) {
				customFields.add(fieldEntry.getValue());
			}
		}
		return customFields;
	}

	/**
	 * Returns the collection of {@link Field} objects contained in this entity.
	 *
	 * @return The collection of {@link Field} values.
	 * @since 1.0.0
	 */
	public Collection<Field> getFields() {
		return this.fieldMap.values();
	}

	/**
	 * Get the values associated with the field with the given field name.
	 *
	 * @param fieldName
	 *            the name of the {@link Field} to get values for.
	 * @return returns a list of string values for the field (or an empty list
	 *         if the provided field name does not exist in this entity).
	 * @since 1.0.0
	 */
	public List<String> getFieldValues(final String fieldName) {
		final Field field = fieldMap.get(fieldName);
		if (field != null) {
			return field.getValues();
		}
		return Collections.emptyList();
	}

	/**
	 * Get the list of related entities currently contained in this entity.
	 *
	 * @return A list of related entities.
	 * @since 1.0.0
	 */
	public List<GenericEntity> getRelatedEntities() {
		return new ArrayList<>(this.relatedEntities);
	}

	/**
	 * Returns the type of this entity.
	 *
	 * @return the type.
	 * @since 1.0.0
	 */
	public String getType() {
		return type;
	}

	/**
	 * does a field with this name exist in this entity and contain at least one
	 * value?
	 *
	 * @param fieldName
	 *            the name of the field to determine presence of a value for.
	 * @return true if the {@link Field} exists and contains at least one value;
	 *         false otherwise.
	 * @since 1.0.0
	 */
	public boolean hasFieldValue(final String fieldName) {
		final Field field = fieldMap.get(fieldName);
		return (field != null) && !field.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + fieldMap.hashCode();
		result = (prime * result) + type.hashCode();
		return result;
	}

	/**
	 * Does this Generic Entity currently contain any related entities?
	 *
	 * @return True if there are currently any related entities set, false
	 *         otherwise.
	 * @since 1.0.0
	 */
	public boolean hasRelatedEntities() {
		return !this.relatedEntities.isEmpty();
	}

	/**
	 * Removes all fields that are not in the list of given field names.
	 *
	 * @param fieldNames
	 *            The names of the fields to keep in this entity.
	 * @since 1.0.0
	 */
	public void removeExtraFields(final List<FieldName> fieldNames) {
		final Map<String, Field> newMap = new HashMap<>();
		for (final FieldName fieldName : fieldNames) {
			newMap.put(fieldName.getName(), fieldMap.get(fieldName.getName()));
		}
		this.fieldMap.clear();
		this.fieldMap.putAll(newMap);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Entity> {\n    type=|").append(type)
				.append("|,\n    fieldMap=|").append(fieldMap).append("|");
		return builder.toString();
	}

	/**
	 * Verifies that all the passed in fields are populated.
	 *
	 * @param requiredFields
	 *            The set of {@link EntityField} objects that represents the
	 *            required fields for a given ALM object.
	 * @throws IllegalArgumentException
	 *             Thrown if not all required fields are present.
	 * @since 1.0.0
	 */
	public void validateRequiredFieldsPresent(final EntityFieldCollection fields) {
		final Set<String> missingFields = new HashSet<>();
		for (final EntityField field : fields) {
			if (field.isRequired()
					&& (!this.fieldMap.containsKey(field.getName()) || fieldMap.get(field.getName()).isEmpty())) {
				missingFields.add(field.getName());
			}
		}
		Validate.isTrue(missingFields.isEmpty(), "Entity is missing the following required fields: %s",
				StringUtils.join(missingFields, ","));
	}

}
