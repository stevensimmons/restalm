package com.fissionworks.restalm.constants.entity;

/**
 * Enum that contains the entity name, entity collection name for various ALM
 * entities.
 *
 * @since 1.0.0
 *
 */
public enum EntityType {
	/**
	 * The Test entity; entity name is {@code test} and collection name is
	 * {@code tests}.
	 *
	 * @since 1.0.0
	 */
	ALM_TEST("test", "tests"),
	/**
	 * The Defect entity; entity name is {@code defect} and collection name is
	 * {@code defects}.
	 *
	 * @since 1.0.0
	 */
	DEFECT("defect", "defects"),
	/**
	 * The Design Step entity; entity name is {@code design-step} and collection
	 * name is {@code design-steps}.
	 *
	 * @since 1.0.0
	 */
	DESIGN_STEP("design-step", "design-steps"),
	/**
	 * The Release entity; entity name is {@code release} and collection name is
	 * {@code releases}.
	 *
	 * @since 1.0.0
	 */
	RELEASE("release", "releases"),
	/**
	 * The Release Cycle entity; entity name is {@code release-cycle} and
	 * collection name is {@code release-cycles}.
	 *
	 * @since 1.0.0
	 */
	RELEASE_CYCLE("release-cycle", "release-cycles"),
	/**
	 * The Release Folder entity; entity name is {@code release-folder} and
	 * collection name is {@code release-folders}.
	 *
	 * @since 1.0.0
	 */
	RELEASE_FOLDER("release-folder", "release-folders"),
	/**
	 * The Requirement entity; entity name is {@code requirement} and collection
	 * name is {@code requirements}.
	 *
	 * @since 1.0.0
	 */
	REQUIREMENT("requirement", "requirements"),
	/**
	 * The Requirement Coverage entity; entity name is
	 * {@code requirement-coverage} and collection name is
	 * {@code requirement-coverages}.
	 *
	 * @since 1.0.0
	 */
	REQUIREMENT_COVERAGE("requirement-coverage", "requirement-coverages"),
	/**
	 * The Run entity; entity name is {@code run} and collection name is
	 * {@code runs}.
	 *
	 * @since 1.0.0
	 */
	RUN("run", "runs"),
	/**
	 * The Test Config entity; entity name is {@code test-config} and collection
	 * name is {@code test-configs}.
	 *
	 * @since 1.0.0
	 */
	TEST_CONFIG("test-config", "test-configs"),
	/**
	 * The Test Folder entity; entity name is {@code test-folder} and collection
	 * name is {@code test-folders}.
	 *
	 * @since 1.0.0
	 */
	TEST_FOLDER("test-folder", "test-folders"),
	/**
	 * The Test Instance entity; entity name is {@code test-instance} and
	 * collection name is {@code test-instances}.
	 *
	 * @since 1.0.0
	 */
	TEST_INSTANCE("test-instance", "test-instances"),
	/**
	 * The Test Set entity; entity name is {@code test-set} and collection name
	 * is {@code test-sets}.
	 *
	 * @since 1.0.0
	 */
	TEST_SET("test-set", "test-sets"),
	/**
	 * The Test Set Folder entity; entity name is {@code test-set-folder} and
	 * collection name is {@code test-set-folders}.
	 *
	 * @since 1.0.0
	 */
	TEST_SET_FOLDER("test-set-folder", "test-set-folders");

	private String collectionName;

	private String entityName;

	private EntityType(final String theEntityName, final String theCollectionName) {
		this.entityName = theEntityName;
		this.collectionName = theCollectionName;
	}

	/**
	 * Returns the Enum constant which has the given name.
	 *
	 * @param entityString
	 *            The name of the entity to retrieve.
	 * @return The Enum constant corresponding to the provided name.
	 * @throws IllegalArgumentException
	 *             if there is no entity with the given name.
	 * @since 1.0.0
	 */
	public static EntityType fromEntityName(final String entityString) {
		for (final EntityType almEntity : EntityType.values()) {
			if (almEntity.entityName.equals(entityString)) {
				return almEntity;
			}
		}
		throw new IllegalArgumentException("<" + entityString + "> is not a valid entity name");
	}

	/**
	 * Get the collection name of this entity.
	 *
	 * @return The collection name of the entity.
	 * @since 1.0.0
	 */
	public String collectionName() {
		return this.collectionName;
	}

	/**
	 * Get the name of this entity.
	 *
	 * @return The name of the entity.
	 * @since 1.0.0
	 */
	public String entityName() {
		return this.entityName;
	}
}
