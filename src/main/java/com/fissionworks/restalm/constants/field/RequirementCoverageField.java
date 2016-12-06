package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Requirement Coverage objects in ALM; The ENUM is
 * equal to the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum RequirementCoverageField implements FieldName {
	/**
	 * The Entity Type field; field name is {@code entity-type}.
	 *
	 * @since 1.0.0
	 */
	COVERAGE_ENTITY_TYPE("entity-type", "requirement-coverage"),

	/**
	 * The ID field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "requirement-coverage"),

	/**
	 * The Last Modified field; field name is {@code last-modified}.
	 *
	 * @since 1.0.0
	 */
	LAST_MODIFIED("last-modified", "requirement-coverage"),

	/**
	 * The Requirement ID field; field name is {@code requirement-id}.
	 *
	 * @since 1.0.0
	 */
	REQUIREMENT_ID("requirement-id", "requirement-coverage"),
	/**
	 * The Status field; field name is {@code status}.
	 *
	 * @since 1.0.0
	 */
	STATUS("status", "requirement-coverage"),
	/**
	 * The Test ID field; field name is {@code test-id}.
	 *
	 * @since 1.0.0
	 */
	TEST_ID("test-id", "requirement-coverage");

	private final String alias;

	private final String name;

	private RequirementCoverageField(final String theName, final String theAlias) {
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
