package com.fissionworks.restalm.constants.entity;

/**
 * Enum that rfields belonging to instances of a specified entity type.
 *
 * @since 1.0.0
 */
public enum EntityFieldParameter {
	/**
	 * Active/Inactive status of the entity field.
	 *
	 * @since 1.0.0
	 */
	ACTIVE("Active"),

	/**
	 * Editability of the field.
	 *
	 * @since 1.0.0
	 */
	EDITABLE("Editable"),

	/**
	 * Filterability of the field.
	 *
	 * @since 1.0.0
	 */
	FILTERABLE("Filterable"),

	/**
	 * Groupability of the field.
	 *
	 * @since 1.0.0
	 */
	GROUPABLE("Groupable"),

	/**
	 * Is this a history field?
	 *
	 * @since 1.0.0
	 */
	HISTORY("History"),

	/**
	 * The ID of the list associated with this field.
	 *
	 * @since 1.0.0
	 */
	LIST_ID("List-Id"),

	/**
	 * The associated entity type this field references.
	 *
	 * @since 1.0.0
	 */
	REFERENCES("References"),

	/**
	 * Is this field Required?
	 *
	 * @since 1.0.0
	 */
	REQUIRED("Required"),

	/**
	 * Size of the field.
	 *
	 * @since 1.0.0
	 */
	SIZE("Size"),

	/**
	 * Does the field support multiple values?
	 *
	 * @since 1.0.0
	 */
	SUPPORTS_MULTIVALUE("SupportsMultivalue"),

	/**
	 * Is this a system field?
	 *
	 * @since 1.0.0
	 */
	SYSTEM("System"),

	/**
	 * The type of the field.
	 *
	 * @since 1.0.0
	 */
	TYPE("Type"),

	/**
	 * Should the field be verified?
	 *
	 * @since 1.0.0
	 */
	VERIFY("Verify"),

	/**
	 * Is this field version controlled?
	 *
	 * @since 1.0.0
	 */
	VERSION_CONTROLLED("VersionControlled"),

	/**
	 * Is this a virtual field?
	 *
	 * @since 1.0.0
	 */
	VIRTUAL("Virtual"),

	/**
	 * Is this field visible?
	 *
	 * @since 1.0.0
	 */
	VISIBLE("Visible");

	private String parameterName;

	private EntityFieldParameter(final String theParameterName) {
		this.parameterName = theParameterName;
	}

	/**
	 * Returns the Enum constant corresponding to the provided parameter name.
	 *
	 * @param theParameterName
	 *            The name of the parameter to retrieve.
	 * @return The Enum contant corresponding to the given name.
	 * @throws IllegalArgumentException
	 *             if there is no {@link EntityFieldParameter} with the given
	 *             name.
	 * @since 1.0.0
	 */
	public static EntityFieldParameter fromParameterName(final String theParameterName) {
		for (final EntityFieldParameter fieldParameter : EntityFieldParameter.values()) {
			if (fieldParameter.parameterName.equals(theParameterName)) {
				return fieldParameter;
			}
		}
		throw new IllegalArgumentException("<" + theParameterName + "> is not a valid entity field name");
	}

}
