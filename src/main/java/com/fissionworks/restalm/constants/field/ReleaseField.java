package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Release objects in ALM; The ENUM is equal to the
 * default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum ReleaseField implements FieldName {

	/**
	 * The description field for relating defects to their detected in release;
	 * field name is {@code description} with alias
	 * {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_DESCRIPTION("description", "defect-to-detected-release-mirror"),

	/**
	 * The end date field for relating defects to their detected in release;
	 * field name is {@code end-date} with alias
	 * {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_END_DATE("end-date", "defect-to-detected-release-mirror"),

	/**
	 * The ID field for relating defects to their detected in release; field
	 * name is {@code id} with alias {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_ID("id", "defect-to-detected-release-mirror"),

	/**
	 * The name field for relating defects to their detected in release; field
	 * name is {@code name} with alias
	 * {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_NAME("name", "defect-to-detected-release-mirror"),

	/**
	 * The parent id field for relating defects to their detected in release;
	 * field name is {@code parent-id} with alias
	 * {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_PARENT_ID("parent-id", "defect-to-detected-release-mirror"),

	/**
	 * The start date field for relating defects to their detected in release;
	 * field name is {@code start-date} with alias
	 * {@code defect-to-detected-release-mirror}.
	 *
	 * @since 1.0.0
	 */
	DEFECT_DETECTED_RELEASE_START_DATE("start-date", "defect-to-detected-release-mirror"),

	/**
	 * The description field; field name is {@code description} with alias
	 * {@code release}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "release"),

	/**
	 * The end date field; field name is {@code end-date} with alias
	 * {@code release}.
	 *
	 * @since 1.0.0
	 */
	END_DATE("end-date", "release"),

	/**
	 * The ID field; field name is {@code id} with alias {@code release}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "release"),

	/**
	 * The name field; field name is {@code name} with alias {@code release}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "release"),

	/**
	 * The parent id field; field name is {@code parent-id} with alias
	 * {@code release}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "release"),

	/**
	 * The start date field; field name is {@code start-date} with alias
	 * {@code release}.
	 *
	 * @since 1.0.0
	 */
	START_DATE("start-date", "release");

	private final String alias;

	private final String name;

	private ReleaseField(final String theName, final String theAlias) {
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
