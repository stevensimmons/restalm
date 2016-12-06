package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Test Set objects in ALM; The ENUM is equal to the
 * default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum TestSetField implements FieldName {
	/**
	 * The description field; field name is {@code comment}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("comment", "test-set"),
	/**
	 * The id field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "test-set"),

	/**
	 * The name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "test-set"),
	/**
	 * The parent id field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "test-set"),
	/**
	 * The type field; field name is {@code subtype-id}.
	 *
	 * @since 1.0.0
	 */
	TYPE("subtype-id", "test-set");

	private final String alias;

	private final String name;

	private TestSetField(final String theName, final String theAlias) {
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
