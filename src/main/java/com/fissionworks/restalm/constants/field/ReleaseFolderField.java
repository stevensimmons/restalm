package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Release Folder objects in ALM; The ENUM is equal
 * to the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum ReleaseFolderField implements FieldName {
	/**
	 * Relation description field for the parent folder of a folder. fully
	 * qualified name uses "contains-release-folder" alias (i.e
	 * "contains-release-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_DESCRIPTION("description", "contains-release-folder"),
	/**
	 * Relation id field for the parent folder of a release folder. fully
	 * qualified name uses "contains-release-folder" alias (i.e
	 * "contains-release-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_ID("id", "contains-release-folder"),
	/**
	 * Relation name field for the parent folder of a release folder. fully
	 * qualified name uses "contains-release-folder" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_NAME("name", "contains-release-folder"),
	/**
	 * Relation parent-id field for the parent folder of a release folder. fully
	 * qualified name uses "contains-release-folder" alias (i.e
	 * "contains-release-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_PARENT_ID("parent-id", "contains-release-folder"),
	/**
	 * The description field; field name is {@code description}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "release-folder"),
	/**
	 * The id field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "release-folder"),

	/**
	 * The name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "release-folder"),
	/**
	 * The parent id field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "release-folder");

	private final String alias;

	private final String name;

	private ReleaseFolderField(final String theName, final String theAlias) {
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
