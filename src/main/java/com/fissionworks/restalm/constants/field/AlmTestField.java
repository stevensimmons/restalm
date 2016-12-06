package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Test objects in ALM, or aliased references to
 * these fields for use in cross queries.
 *
 * @since 1.0.0
 *
 */
public enum AlmTestField implements FieldName {
	/**
	 * The creation date field; field name is {@code creation-time} with alias
	 * {@code test}.
	 *
	 * @since 1.0.0
	 */
	CREATION_DATE("creation-time", "test"),
	/**
	 * The description field; field name is {@code description} with alias
	 * {@code test}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "test"),
	/**
	 * The designer field; field name is {@code owner} with alias {@code test}.
	 *
	 * @since 1.0.0
	 */
	DESIGNER("owner", "test"),
	/**
	 * The creation date field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.creation-time}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_CREATION_DATE("creation-time", "has-parts-test"),
	/**
	 * The description field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.description}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_DESCRIPTION("description", "has-parts-test"),
	/**
	 * The designer field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.owner}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_DESIGNER("owner", "has-parts-test"),
	/**
	 * The id field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.id}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_ID("id", "has-parts-test"),
	/**
	 * The name field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.name}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_NAME("name", "has-parts-test"),
	/**
	 * The parent-id field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.parent-id}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_PARENT_ID("parent-id", "has-parts-test"),
	/**
	 * The status field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.status}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_STATUS("status", "has-parts-test"),
	/**
	 * The type field using the {@code has-parts-test} alias
	 * ({@code has-parts-test.subtype-id}).
	 *
	 * @since 1.0.0
	 */
	HAS_PARTS_TEST_TYPE("subtype-id", "has-parts-test"),

	/**
	 * The ID field; field name is {@code id} with alias {@code test}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "test"),

	/**
	 * The name field; field name is {@code name} with alias {@code test}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "test"),

	/**
	 * The parent id field; field name is {@code parent-id} with alias
	 * {@code test}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "test"),

	/**
	 * The status field; field name is {@code status} with alias {@code test}.
	 *
	 * @since 1.0.0
	 */
	STATUS("status", "test"),

	/**
	 * The type field; field name is {@code subtype-id} with alias {@code test}.
	 */
	TYPE("subtype-id", "test");

	private final String alias;

	private final String name;

	private AlmTestField(final String theName, final String theAlias) {
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
