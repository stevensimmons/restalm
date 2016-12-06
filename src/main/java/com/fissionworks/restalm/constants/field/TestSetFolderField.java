package com.fissionworks.restalm.constants.field;

/**
 * System fields that comprise Test Set Folder objects in ALM; The ENUM is equal
 * to the default label for fields that are displayed in the UI.
 *
 * @since 1.0.0
 */
public enum TestSetFolderField implements FieldName {
	/**
	 * Relation description field for the parent test set folder of a test set
	 * folder. fully qualified name uses "contains-test-set-folder" alias (i.e
	 * "contains-test-set-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_TEST_SET_FOLDER_DESCRIPTION("description", "contains-test-set-folder"),
	/**
	 * Relation id field for the parent test set folder of a test set folder.
	 * fully qualified name uses "contains-test-set-folder" alias (i.e
	 * "contains-test-set-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_TEST_SET_FOLDER_ID("id", "contains-test-set-folder"),
	/**
	 * Relation name field for the parent test set folder of a test set folder.
	 * fully qualified name uses "contains-test-set-folder" alias (i.e
	 * "contains-test-set-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_TEST_SET_FOLDER_NAME("name", "contains-test-set-folder"),
	/**
	 * Relation parent-id field for the parent test set folder of a test set
	 * folder. fully qualified name uses "contains-test-set-folder" alias (i.e
	 * "contains-test-set-folder.fieldName").
	 *
	 * @since 1.0.0
	 */
	CONTAINS_TEST_SET_FOLDER_PARENT_ID("parent-id", "contains-test-set-folder"),
	/**
	 * The description field; field name is {@code description}.
	 *
	 * @since 1.0.0
	 */
	DESCRIPTION("description", "test-set-folder"),
	/**
	 * The id field; field name is {@code id}.
	 *
	 * @since 1.0.0
	 */
	ID("id", "test-set-folder"),

	/**
	 * The name field; field name is {@code name}.
	 *
	 * @since 1.0.0
	 */
	NAME("name", "test-set-folder"),
	/**
	 * The parent id field; field name is {@code parent-id}.
	 *
	 * @since 1.0.0
	 */
	PARENT_ID("parent-id", "test-set-folder");

	private final String alias;

	private final String name;

	private TestSetFolderField(final String theName, final String theAlias) {
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
