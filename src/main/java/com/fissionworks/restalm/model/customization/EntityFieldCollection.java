package com.fissionworks.restalm.model.customization;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * A collection of {@link EntityField}s for a given ALM entity.
 *
 * @since 1.0.0
 *
 */
public final class EntityFieldCollection implements Iterable<EntityField> {

	private final Set<EntityField> fields = new HashSet<>();

	/**
	 * Add an {@link EntityField} to the collection.
	 *
	 * @param entityField
	 *            the {@link EntityField} to add.
	 * @throws NullPointerException
	 *             thrown if the {@link EntityField} is null.
	 * @since 1.0.0
	 */
	public void addEntityField(final EntityField entityField) {
		Validate.notNull(entityField, "entityField cannot be null");
		this.fields.add(entityField);
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
		final EntityFieldCollection other = (EntityFieldCollection) obj;
		if (!fields.equals(other.fields)) {
			return false;
		}
		return true;
	}

	/**
	 * Get the {@link EntityField} with the given name.
	 *
	 * @param fieldName
	 *            The name of the field to retrieve.
	 * @return The {@link EntityField} with the given name.
	 * @throws IllegalArgumentException
	 *             if there is not an {@link EntityField} with the given name
	 *             present.
	 * @since 1.0.0
	 */
	public EntityField getEntityField(final String fieldName) {
		for (final EntityField field : this.fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new IllegalArgumentException("No field named |" + fieldName + "| found for this entity");
	}

	/**
	 * Returns all fields in this collection.
	 *
	 * @return all {@link EntityField}s.
	 * @since 1.0.0
	 */
	public Set<EntityField> getFields() {
		return new HashSet<>(fields);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + fields.hashCode();
		return result;
	}

	/**
	 * Is the field with the given name editable?
	 *
	 * @param fieldName
	 *            The name of the field to check the editability of.
	 * @return True if the field is editable, false otherwise.
	 * @throws IllegalArgumentException
	 *             if there is no field with the given name present.
	 * @since 1.0.0
	 */
	public boolean isEditableField(final String fieldName) {
		final EntityField field = getEntityField(fieldName);
		return field.isEditable();
	}

	/**
	 * is this field present in this collection, and editable or required?
	 *
	 * @param fieldName
	 *            the field name to check for add validity.
	 * @return true if the field is present and editable or required; false
	 *         otherwise.
	 */
	public boolean isValidAddField(final String fieldName) {
		final EntityField field = getEntityField(fieldName);
		return field.isEditable() || field.isRequired();
	}

	@Override
	public Iterator<EntityField> iterator() {
		return fields.iterator();
	}

	/**
	 * Change the editable value for the field with the given name; Used
	 * primarily for correcting fields that are erroneously reported by ALM
	 * (fields that ALM designates as un-editable that are and vice versa).
	 *
	 * @param fieldName
	 *            The name of the field to change the editable value of.
	 * @param editable
	 *            The new editable value for the given field.
	 * @throws IllegalArgumentException
	 *             if there is no field with the given name present.
	 * @since 1.0.0
	 */
	public void setEditable(final String fieldName, final boolean editable) {
		getEntityField(fieldName).setEditable(editable);
	}

	/**
	 * Change the required value for the field with the given name; Used
	 * primarily for correcting fields that are erroneously reported by ALM
	 * (fields that ALM designates as not required that are and vice versa).
	 *
	 * @param fieldName
	 *            The name of the field to change the required value of.
	 * @param required
	 *            The new required value for the given field.
	 * @since 1.0.0
	 */
	public void setRequired(final String fieldName, final boolean required) {
		getEntityField(fieldName).setRequired(required);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <EntityFieldCollection> {\n    fields=|").append(fields)
				.append("|");
		return builder.toString();
	}

}
