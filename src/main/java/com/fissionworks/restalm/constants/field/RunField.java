package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Run objects in ALM; The ENUM is equal to the
 * default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum RunField implements FieldName {

	/**
	 * The comments field; field name is {@code comments}.
	 *
	 * @since 1.0.0
	 */
	COMMENTS("comments", "run"),

	/**
	 * The host field; field name is {@code host}.
	 *
	 * @since 1.0.0
	 */
	HOST("host", "run"),

	/**
	 * The id field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "run"),

	/**
	 * The Last Modified field; field name is {@code last-modified}.
	 *
	 * @since 1.0.0
	 */
	LAST_MODIFIED("last-modified", "run"),

	/**
	 * The Name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "run"),

	/**
	 * The Owner field; field name is {@code owner}.
	 *
	 * @since 1.0.0
	 */
	OWNER("owner", "run"),

	/**
	 * The status field; field name is {@code status}.
	 *
	 * @since 1.0.0
	 */
	STATUS("status", "run"),

	/**
	 * The Subtype ID field; field name is {@code subtype-id}.
	 *
	 * @since 1.0.0
	 */
	SUBTYPE_ID("subtype-id", "run"),

	/**
	 * The Test Config ID field; field name is {@code test-config-id}.
	 *
	 * @since 1.0.0
	 */
	TEST_CONFIG_ID("test-config-id", "run"),

	/**
	 * The Test ID field; field name is {@code test-id}.
	 *
	 * @since 1.0.0
	 */
	TEST_ID("test-id", "run"),

	/**
	 * The Test Instance ID field; field name is {@code testcycl-id}.
	 *
	 * @since 1.0.0
	 */
	TEST_INSTANCE_ID("testcycl-id", "run"),

	/**
	 * The Test Set ID field; field name is {@code cycle-id}.
	 *
	 * @since 1.0.0
	 */
	TEST_SET_ID("cycle-id", "run");

	private final String alias;

	private final String name;

	private RunField(final String theName, final String theAlias) {
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
