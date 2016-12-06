package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Design Step objects in ALM, or aliased references
 * to these fields for use in cross queries.
 *
 * @since 1.0.0
 *
 */
public enum DesignStepField implements FieldName {
	/**
	 * The description field; field name is {@code description} with alias
	 * {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "design-step"),
	/**
	 * The expected result field; field name is {@code expected} with alias
	 * {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	EXPECTED_RESULT("expected", "design-step"),
	/**
	 * The id field; field name is {@code id} with alias {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "design-step"),
	/**
	 * The name field; field name is {@code name} with alias
	 * {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "design-step"),
	/**
	 * The parent-id field; field name is {@code parent-id} with alias
	 * {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "design-step"),
	/**
	 * The step-order field; field name is {@code step-order} with alias
	 * {@code design-step}.
	 *
	 * @since 1.0.0
	 */
	STEP_ORDER("step-order", "design-step");

	private final String alias;

	private final String name;

	private DesignStepField(final String theName, final String theAlias) {
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
