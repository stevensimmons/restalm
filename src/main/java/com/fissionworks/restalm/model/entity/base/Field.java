package com.fissionworks.restalm.model.entity.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * Simple class that represents a single field within an {@link GenericEntity}.
 *
 * @since 1.0.0
 *
 */
public final class Field {

	private final String name;

	private final Set<String> values = new HashSet<>();

	/**
	 * Creates a field with the given name and values.
	 *
	 * @param theName
	 *            the name of the field to be created.
	 * @param theValues
	 *            the values for the field to be created.
	 * @throws NullPointerException
	 *             thrown if the name is null.
	 * @throws IllegalArgumentException
	 *             thrown if the name is blank.
	 * @since 1.0.0
	 */
	public Field(final String theName, final List<String> theValues) {
		Validate.notBlank(theName, "Name cannot be null or blank");
		this.name = theName;
		this.values.addAll(theValues);
		// remove empty values
		this.values.removeAll(Collections.singleton(""));
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
		final Field other = (Field) obj;
		if (!name.equals(other.name)) {
			return false;
		}
		if (!values.equals(other.values)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of the field.
	 *
	 * @return the name.
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets all values associated with the field.
	 *
	 * @return the values.
	 * @since 1.0.0
	 */
	public List<String> getValues() {
		return new ArrayList<>(values);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + name.hashCode();
		result = (prime * result) + values.hashCode();
		return result;
	}

	/**
	 * Does the field have any values?
	 *
	 * @return true if the field has no values, false otherwise.
	 * @since 1.0.0
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" extended by: <Field> {\n    name=|").append(name)
				.append("|,\n    values=|").append(values).append("|");
		return builder.toString();
	}

}
