package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Requirement objects in ALM; The ENUM is equal to
 * the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum RequirementField implements FieldName {

	/**
	 * The Author field; field name is {@code owner}.
	 *
	 * @since 1.0.0
	 */
	AUTHOR("owner", "requirement"),

	/**
	 * The Comments field; field name is {@code comments}.
	 *
	 * @since 1.0.0
	 */
	COMMENTS("comments", "requirement"),

	/**
	 * Relation Author field for the parent Requirement of a Requirement. fully
	 * qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_AUTHOR("owner", "contains-requirement"),

	/**
	 * Relation Comments field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_COMMENTS("comments", "contains-requirement"),

	/**
	 * Relation Creation Date field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_CREATION_DATE("creation-time", "contains-requirement"),

	/**
	 * Relation Description field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_DESCRIPTION("description", "contains-requirement"),

	/**
	 * Relation Direct Cover Status field for the parent Requirement of a
	 * Requirement. fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_DIRECT_COVER_STATUS("status", "contains-requirement"),

	/**
	 * Relation Father Name field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_FATHER_NAME("father-name", "contains-requirement"),

	/**
	 * Relation ID field for the parent Requirement of a Requirement. fully
	 * qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_ID("id", "contains-requirement"),

	/**
	 * Relation Last Modified field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_LAST_MODIFIED("last-modified", "contains-requirement"),

	/**
	 * Relation Name field for the parent Requirement of a Requirement. fully
	 * qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_NAME("name", "contains-requirement"),

	/**
	 * Relation Parent ID field for the parent Requirement of a Requirement.
	 * fully qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_PARENT_ID("parent-id", "contains-requirement"),

	/**
	 * Relation Type ID field for the parent Requirement of a Requirement. fully
	 * qualified name uses "contains-requirement" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_REQUIREMENT_TYPE_ID("type-id", "contains-requirement"),

	/**
	 * The Creation Date field; field name is {@code creation-time}.
	 *
	 * @since 1.0.0
	 */
	CREATION_DATE("creation-time", "requirement"),

	/**
	 * The Description field; field name is {@code description}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "requirement"),

	/**
	 * The Direct Cover Status field; field name is {@code status}.
	 *
	 * @since 1.0.0
	 */
	DIRECT_COVER_STATUS("status", "requirement"),

	/**
	 * The Father Name field; field name is {@code father-name}.
	 *
	 * @since 1.0.0
	 */
	FATHER_NAME("father-name", "requirement"),

	/**
	 * The ID field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "requirement"),

	/**
	 * The Last Modified field; field name is {@code last-modified}.
	 *
	 * @since 1.0.0
	 */
	LAST_MODIFIED("last-modified", "requirement"),

	/**
	 * The Name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "requirement"),

	/**
	 * The Parent ID field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "requirement"),

	/**
	 * The Type ID field; field name is {@code type-id}.
	 *
	 * @since 1.0.0
	 */
	TYPE_ID("type-id", "requirement");

	private final String alias;

	private final String name;

	private RequirementField(final String theName, final String theAlias) {
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
