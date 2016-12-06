package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Release Cycle objects in ALM; The ENUM is equal
 * to the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum ReleaseCycleField implements FieldName {

	/**
	 * The description field for relating defects to their detected in release
	 * cycle; field name is {@code description} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_DESCRIPTION("description", "defect-to-detected-rcycl-mirror"),

	/**
	 * The end date field for relating defects to their detected in release
	 * cycle; field name is {@code end-date} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_END_DATE("end-date", "defect-to-detected-rcycl-mirror"),

	/**
	 * The ID field for relating defects to their detected in release cycle;
	 * field name is {@code id} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_ID("id", "defect-to-detected-rcycl-mirror"),

	/**
	 * The name field for relating defects to their detected in release cycle;
	 * field name is {@code name} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_NAME("name", "defect-to-detected-rcycl-mirror"),

	/**
	 * The parent id field for relating defects to their detected in release
	 * cycle; field name is {@code parent-id} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_PARENT_ID("parent-id", "defect-to-detected-rcycl-mirror"),

	/**
	 * The start date field for relating defects to their detected in release
	 * cycle; field name is {@code start-date} with alias
	 * {@code defect-to-detected-rcycl-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_CYCLE_START_DATE("start-date", "defect-to-detected-rcycl-mirror"),

	/**
	 * The description field; field name is {@code description} with alias
	 * {@code release-cycle}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "release-cycle"),

	/**
	 * The end date field; field name is {@code end-date} with alias
	 * {@code release-cycle}.
	 *
	 * @since 1.0.0
	 */
	END_DATE("end-date", "release-cycle"),

	/**
	 * The ID field; field name is {@code id} with alias {@code release-cycle}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "release-cycle"),

	/**
	 * The name field; field name is {@code name} with alias {@code test}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "release-cycle"),

	/**
	 * The parent id field; field name is {@code parent-id} with alias
	 * {@code release-cycle}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "release-cycle"),

	/**
	 * The start date field; field name is {@code start-date} with alias
	 * {@code release-cycle}.
	 *
	 * @since 1.0.0
	 */
	START_DATE("start-date", "release-cycle");

	private final String alias;

	private final String name;

	private ReleaseCycleField(final String theName, final String theAlias) {
		this.name = theName;
		this.alias = theAlias;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQualifiedName() {
		return String.format("%s.%s", alias, name);
	}

}
