package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Test Instance objects in ALM; The ENUM is equal
 * to the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum TestInstanceField implements FieldName {
	/**
	 * The ID field; field name is {@code id} with alias {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "test-instance"),

	/**
	 * The Last Modified field; field name is {@code last-modified} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	LAST_MODIFIED("last-modified", "test-instance"),

	/**
	 * The Planned Host field; field name is {@code host-name} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	PLANNED_HOST("host-name", "test-instance"),

	/**
	 * The Responsible Tester field; field name is {@code owner} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	RESPONSIBLE_TESTER("owner", "test-instance"),

	/**
	 * The Status field; field name is {@code status} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	STATUS("status", "test-instance"),

	/**
	 * The Subtype field; field name is {@code subtype-id} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	SUBTYPE("subtype-id", "test-instance"),

	/**
	 * The Test Config ID field; field name is {@code test-config-id} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	TEST_CONFIG_ID("test-config-id", "test-instance"),

	/**
	 * The Test ID field; field name is {@code test-id} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	TEST_ID("test-id", "test-instance"),

	/**
	 * The Test Instance Number field; field name is {@code test-instance} with
	 * alias {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	TEST_INSTANCE_NUMBER("test-instance", "test-instance"),

	/**
	 * The Test Order field; field name is {@code test-order} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	TEST_ORDER("test-order", "test-instance"),

	/**
	 * The Test Set ID field; field name is {@code cycle-id} with alias
	 * {@code test-instance}.
	 *
	 * @since 1.0.0
	 */
	TEST_SET_ID("cycle-id", "test-instance");

	private final String alias;

	private final String name;

	private TestInstanceField(final String theName, final String theAlias) {
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
