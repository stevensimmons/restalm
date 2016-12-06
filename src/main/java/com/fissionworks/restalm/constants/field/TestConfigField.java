package com.fissionworks.restalm.constants.field;

import com.fissionworks.restalm.model.entity.testplan.TestConfig;

/**
 * System fields that comprise Test Config objects in ALM; The ENUM is equal to
 * the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum TestConfigField implements FieldName {
	/**
	 * The created by field; field name is {@code owner}.
	 *
	 * @since 1.0.0
	 */
	CREATED_BY("owner"),
	/**
	 * The creation date field; field name is {@code creation-time}.
	 *
	 * @since 1.0.0
	 */
	CREATION_DATE("creation-time"),
	/**
	 * The data state field; field name is {@code data-state}.
	 *
	 * @since 1.0.0
	 */
	DATA_STATE("data-state"),
	/**
	 * The description field; field name is {@code description}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description"),
	/**
	 * The ID field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id"),
	/**
	 * The name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name"),
	/**
	 * The parent id field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id"),
	/**
	 * The test name field; field name is {@code test-name}.
	 *
	 * @since 1.0.0
	 */
	TEST_NAME("test-name");

	private final String name;

	private TestConfigField(final String theName) {
		this.name = theName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQualifiedName() {
		return String.format("%s.%s", TestConfig.TYPE, name);
	}

}
