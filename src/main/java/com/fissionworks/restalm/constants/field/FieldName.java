package com.fissionworks.restalm.constants.field;

/**
 *
 * Interface for any class (generally enums) which represents a field name of an
 * ALM entity.
 *
 * @since 1.0.0
 */
public interface FieldName {
	/**
	 * Get the name of the associated field; This is the behind the scenes name
	 * of a field, which may be different then the value shown for this field in
	 * the UI (the label).
	 *
	 * @return The name of the field.
	 * @since 1.0.0
	 */
	String getName();

	/**
	 * Gets the fully qualified name of the field as defined by alm; This name
	 * includes the entity name used by ALM (test, test-folder,requirement,
	 * etc.).
	 *
	 * @return The fully qualified field name (i.e "test.name").
	 * @since 1.0.0
	 */
	String getQualifiedName();

}
