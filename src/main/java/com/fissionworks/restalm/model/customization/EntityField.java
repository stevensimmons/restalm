package com.fissionworks.restalm.model.customization;

import org.apache.commons.lang3.Validate;

/**
 * Contains general information about a particular field of an ALM Entity (is it
 * required, is it editable, etc).
 *
 * @since 1.0.0
 *
 */
public final class EntityField {

	private boolean editable;

	private final String label;

	private final String name;

	private boolean required;

	private boolean system;

	/**
	 * Create a new EnityField with the given name and label.
	 *
	 * @param theName
	 *            the name of the EntityField
	 * @param theLabel
	 *            the label of the EntityField(as displayed in ALM).
	 * @throws NullPointerException
	 *             thrown if the name is null.
	 * @throws IllegalArgumentException
	 *             thrown if the name is blank.
	 * @since 1.0.0
	 */
	public EntityField(final String theName, final String theLabel) {
		Validate.notBlank(theName, "Name cannot be null or blank");
		this.name = theName;
		this.label = theLabel == null ? "" : theLabel;
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
		final EntityField other = (EntityField) obj;
		if (editable != other.editable) {
			return false;
		}

		if (!label.equals(other.label)) {
			return false;
		}

		if (!name.equals(other.name)) {
			return false;
		}
		if (required != other.required) {
			return false;
		}
		if (system != other.system) {
			return false;
		}
		return true;
	}

	/**
	 * Get the label of the field(as displayed in ALM).
	 *
	 * @return the label.
	 * @since 1.0.0
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get the name of the field.
	 *
	 * @return the name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (editable ? 1231 : 1237);
		result = (prime * result) + label.hashCode();
		result = (prime * result) + name.hashCode();
		result = (prime * result) + (required ? 1231 : 1237);
		result = (prime * result) + (system ? 1231 : 1237);
		return result;
	}

	/**
	 * is this field editable?
	 *
	 * @return true if the field is editable, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Is the field required?
	 *
	 * @return true if the field is required, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Is the field a system field (not custom)?
	 *
	 * @return true if the field is a system field, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isSystem() {
		return system;
	}

	/**
	 * Set whether this field is editable or not.
	 *
	 * @param isEditable
	 *            true or false?
	 * @since 1.0.0
	 */
	public void setEditable(final boolean isEditable) {
		this.editable = isEditable;
	}

	/**
	 * Set whether this field is required or not.
	 *
	 * @param isRequired
	 *            true or false?
	 * @since 1.0.0
	 */
	public void setRequired(final boolean isRequired) {
		this.required = isRequired;
	}

	/**
	 * Set whether this field is a system field or not.
	 *
	 * @param isSystem
	 *            true or false?
	 * @since 1.0.0
	 */
	public void setSystem(final boolean isSystem) {
		this.system = isSystem;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <EntityField> {\n    editable=|").append(editable)
				.append("|,\n    label=|").append(label).append("|,\n    name=|").append(name)
				.append("|,\n    required=|").append(required).append("|,\n    system=|").append(system).append("|");
		return builder.toString();
	}

}
