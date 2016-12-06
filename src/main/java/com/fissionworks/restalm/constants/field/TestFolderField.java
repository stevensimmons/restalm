package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Test Folder objects in ALM; The ENUM is equal to
 * the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum TestFolderField implements FieldName {
	/**
	 * Relation description field for the parent folder of a folder. fully
	 * qualified name uses "contains-test-folder" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_DESCRIPTION("description", "contains-test-folder"),
	/**
	 * Relation id field for the parent folder of a folder. fully qualified name
	 * uses "contains-test-folder" alias (i.e "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_ID("id", "contains-test-folder"),
	/**
	 * Relation name field for the parent folder of a folder. fully qualified
	 * name uses "contains-test-folder" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_NAME("name", "contains-test-folder"),
	/**
	 * Relation parent-id field for the parent folder of a folder. fully
	 * qualified name uses "contains-test-folder" alias (i.e
	 * "contains-test-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_FOLDER_PARENT_ID("parent-id", "contains-test-folder"),
	/**
	 * The description field; field name is {@code description}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "test-folder"),
	/**
	 * The id field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "test-folder"),

	/**
	 * The name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "test-folder"),
	/**
	 * The parent id field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "test-folder");

	private final String alias;

	private final String name;

	private TestFolderField(final String theName, final String theAlias) {
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
